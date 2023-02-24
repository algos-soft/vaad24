package it.algos.vaad24.backend.logic;

import static it.algos.vaad24.backend.boot.VaadCost.*;
import it.algos.vaad24.backend.entity.*;
import it.algos.vaad24.backend.enumeration.*;
import it.algos.vaad24.backend.exception.*;
import it.algos.vaad24.backend.service.*;
import it.algos.vaad24.backend.wrapper.*;
import org.bson.*;
import org.bson.types.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.data.domain.*;
import org.springframework.data.mongodb.core.*;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.*;
import org.springframework.data.mongodb.repository.*;

import java.util.*;

/**
 * Project vaadin23
 * Created by Algos
 * User: gac
 * Date: gio, 10-mar-2022
 * Time: 21:02
 * Layer di collegamento del backend con mongoDB <br>
 * Classe astratta di servizio per la Entity di un package <br>
 * Garantisce i metodi di collegamento per accedere al database <br>
 * Service di una entityClazz specifica e di un package <br>
 * L'unico dato mantenuto nelle sottoclassi concrete: la property final entityClazz <br>
 * Se la sottoclasse xxxService non esiste (non è indispensabile), usa la classe generica GenericService; <br>
 * i metodi esistono ma occorre un cast in uscita <br>
 * <p>
 * Le sottoclassi concrete: <br>
 * Non mantengono lo stato di una istanza entityBean <br>
 * NOT annotated with @SpringComponent (inutile, esiste già @Service) <br>
 * NOT annotated with @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON) (inutile, esiste già @Service) <br>
 */
public abstract class CrudBackend extends AbstractService {

    protected Sort sortOrder;

    /**
     * The Entity Class  (obbligatoria sempre e final)
     */
    public final Class<? extends AEntity> entityClazz;

    /**
     * Istanza unica di una classe @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON) di servizio <br>
     * Iniettata automaticamente dal framework SpringBoot/Vaadin con l'Annotation @Autowired <br>
     * Disponibile DOPO il ciclo init() del costruttore di questa classe <br>
     */
    @Autowired
    public ResourceService resourceService;

    /**
     * Istanza unica di una classe @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON) di servizio <br>
     * Iniettata automaticamente dal framework SpringBoot/Vaadin con l'Annotation @Autowired <br>
     * Disponibile DOPO il ciclo init() del costruttore di questa classe <br>
     */
    @Autowired
    public MongoService mongoService;


    /**
     * Istanza unica di una classe @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON) di servizio <br>
     * Iniettata automaticamente dal framework SpringBoot/Vaadin con l'Annotation @Autowired <br>
     * Disponibile DOPO il ciclo init() del costruttore di questa classe <br>
     */
    @Autowired
    public TextService textService;

    public MongoRepository crudRepository;


    /**
     * Regola la entityClazz (final nella superclasse) associata a questo service <br>
     */
    public CrudBackend(final Class<? extends AEntity> entityClazz) {
        this.entityClazz = entityClazz;

        //--Preferenze usate da questa 'logic'
        this.fixPreferenze();

    }// end of constructor


    /**
     * Constructor @Autowired. <br>
     * In the newest Spring release, it’s constructor does not need to be annotated with @Autowired annotation <br>
     * L' @Autowired (esplicito o implicito) funziona SOLO per UN costruttore <br>
     * Se ci sono DUE o più costruttori, va in errore <br>
     * Se ci sono DUE costruttori, di cui uno senza parametri, inietta quello senza parametri <br>
     */
    public CrudBackend(final MongoRepository crudRepository, final Class<? extends AEntity> entityClazz) {
        this.crudRepository = crudRepository;
        this.entityClazz = entityClazz;

        //--Preferenze usate da questa 'logic'
        this.fixPreferenze();

    }// end of constructor with @Autowired


    /**
     * Preferenze usate da questa 'backend' <br>
     * Primo metodo chiamato dopo init() (implicito del costruttore) e postConstruct() (facoltativo) <br>
     * Puo essere sovrascritto, invocando PRIMA il metodo della superclasse <br>
     */
    protected void fixPreferenze() {
        this.sortOrder = Sort.by(Sort.Direction.ASC, FIELD_NAME_ID_CON);
    }

    public boolean creaIfNotExist(final String keyPropertyValue) {
        return insert(newEntity(keyPropertyValue)) != null;
    }

    public AEntity newEntity(String keyPropertyValue) {
        return null;
    }

    public AEntity newEntity(Document doc) {
        return null;
    }

    /**
     * Creazione in memoria di una nuova entityBean che NON viene salvata <br>
     * Eventuali regolazioni iniziali delle property <br>
     * Senza properties per compatibilità con la superclasse <br>
     *
     * @return la nuova entityBean appena creata (non salvata)
     */
    public AEntity newEntity() {
        AEntity newEntityBean = null;

        try {
            newEntityBean = entityClazz.getDeclaredConstructor().newInstance();
        } catch (Exception unErrore) {
            logger.warn(AETypeLog.nuovo, unErrore);
        }

        return newEntityBean;
    }

    /**
     * Regola la chiave se esiste il campo keyPropertyName. <br>
     *
     * @param newEntityBean to be checked
     *
     * @return the checked entity
     */
    public AEntity fixKey(AEntity newEntityBean) {
        String keyPropertyValue;
        String keyPropertyName;
        boolean usaKeyIdSenzaSpazi;
        boolean usaKeyIdMinuscolaCaseInsensitive;

        if (textService.isValid(newEntityBean.id)) {
            return newEntityBean;
        }

        keyPropertyName = annotationService.getKeyPropertyName(entityClazz);
        if (textService.isValid(keyPropertyName) && !keyPropertyName.equals(FIELD_NAME_ID_CON)) {
            keyPropertyValue = reflectionService.getPropertyValueStr(newEntityBean, keyPropertyName);
            if (textService.isValid(keyPropertyValue)) {
                usaKeyIdSenzaSpazi = annotationService.usaKeyIdSenzaSpazi(newEntityBean.getClass()); ;
                usaKeyIdMinuscolaCaseInsensitive = annotationService.usaKeyIdMinuscolaCaseInsensitive(newEntityBean.getClass()); ;
                keyPropertyValue = usaKeyIdSenzaSpazi ? textService.levaSpazi(keyPropertyValue) : keyPropertyValue;
                keyPropertyValue = usaKeyIdMinuscolaCaseInsensitive ? keyPropertyValue.toLowerCase() : keyPropertyValue;
                newEntityBean.id = keyPropertyValue;
            }
        }
        else {
            newEntityBean.id = new ObjectId().toString();
        }

        return newEntityBean;
    }

    public boolean isExistId(final String keyIdValue) {
        return isExistProperty(FIELD_NAME_ID_CON, keyIdValue);
    }


    public boolean isExistKey(final String keyValue) {
        String keyPropertyName = annotationService.getKeyPropertyName(entityClazz);
        return isExistProperty(keyPropertyName, keyValue);
    }

    public boolean isExistProperty(final String propertyName, final String propertyValue) {
        String collectionName = annotationService.getCollectionName(entityClazz);
        String keyPropertyName = annotationService.getKeyPropertyName(entityClazz);
        Query query = new Query();

        if (USA_REPOSITORY && crudRepository != null) { //@todo noRepository
            return crudRepository.findById(keyPropertyName) != null;
        }
        else {
            query.addCriteria(Criteria.where(propertyName).is(propertyValue));
            if (textService.isValid(collectionName)) {
                return mongoService.mongoOp.exists(query, entityClazz.getClass(), collectionName);
            }
            else {
                return mongoService.mongoOp.exists(query, entityClazz.getClass());
            }
        }
    }

    /**
     * Ricerca della singola entity <br>
     * Può essere sovrascritto nella sottoclasse specifica per il casting di ritorno <br>
     *
     * @param keyID (obbligatorio, unico)
     *
     * @return la entity trovata
     */
    public AEntity findById(final String keyID) {
        return findByProperty(FIELD_NAME_ID_CON, keyID);
    }

    /**
     * Ricerca della singola entity <br>
     * Può essere sovrascritto nella sottoclasse specifica per il casting di ritorno <br>
     *
     * @param keyValue (obbligatorio, unico) della keyPropertyName
     *
     * @return la entity trovata
     */
    public AEntity findByKey(final String keyValue) {
        String keyPropertyName = annotationService.getKeyPropertyName(entityClazz);
        return findByProperty(keyPropertyName, keyValue);
    }

    /**
     * Ricerca della singola entity <br>
     * Può essere sovrascritto nella sottoclasse specifica per il casting di ritorno <br>
     *
     * @param propertyName  (obbligatorio, unico)
     * @param propertyValue (obbligatorio)
     *
     * @return la entity trovata
     */
    public AEntity findByProperty(final String propertyName, final Object propertyValue) {
        AEntity entity;
        String collectionName = annotationService.getCollectionName(entityClazz);
        Query query = new Query();
        query.addCriteria(Criteria.where(propertyName).is(propertyValue));

        if (textService.isValid(collectionName)) {
            entity = mongoService.mongoOp.findOne(query, entityClazz, collectionName);
        }
        else {
            entity = mongoService.mongoOp.findOne(query, entityClazz);
        }

        return entity;
    }

    public AEntity save(AEntity entityBean) {
        if (!isExistId(entityBean.id)) {
            return insert(entityBean);
        }
        else {
            return update(entityBean);
        }
    }

    public AEntity insert(final AEntity entityBean) {
        String collectionName = annotationService.getCollectionName(entityClazz);

        if (!isExistId(entityBean.id)) {
            if (USA_REPOSITORY && crudRepository != null) { //@todo noRepository
                return (AEntity) crudRepository.insert(entityBean);
            }
            else {
                if (textService.isValid(collectionName)) {
                    return mongoService.mongoOp.insert(entityBean, collectionName);
                }
                else {
                    return mongoService.mongoOp.insert(entityBean);
                }
            }
        }
        else {
            return null;
        }
    }


    public AEntity update(final AEntity entityBean) {
        String collectionName = annotationService.getCollectionName(entityClazz);
        Query query = new Query();

        if (isExistId(entityBean.id)) {
            if (USA_REPOSITORY && crudRepository != null) { //@todo noRepository
                try {
                    return (AEntity) crudRepository.save(entityBean);
                } catch (Exception unErrore) {
                    logger.error(unErrore);
                }
                return entityBean;
            }
            else {
                query.addCriteria(Criteria.where(FIELD_NAME_ID_CON).is(entityBean.id));
                FindAndReplaceOptions options = new FindAndReplaceOptions();
                options.returnNew();
                if (textService.isValid(collectionName)) {
                    return mongoService.mongoOp.findAndReplace(query, entityBean, options, collectionName);
                }
                else {
                    return mongoService.mongoOp.findAndReplace(query, entityBean, options);
                }
            }
        }
        else {
            return null;
        }
    }

    public boolean delete(AEntity entityBean) {
        String collectionName = annotationService.getCollectionName(entityClazz);
        Query query = new Query();

        if (isExistId(entityBean.id)) {
            if (USA_REPOSITORY && crudRepository != null) { //@todo noRepository
                try {
                    crudRepository.delete(entityBean);
                } catch (Exception unErrore) {
                    logger.error(unErrore);
                }
                return false;
            }
            else {
                query.addCriteria(Criteria.where(FIELD_NAME_ID_CON).is(entityBean.id));
                if (textService.isValid(collectionName)) {
                    mongoService.mongoOp.findAndRemove(query, entityBean.getClass(), collectionName);
                }
                else {
                    mongoService.mongoOp.findAndRemove(query, entityBean.getClass());
                }
                return true;
            }
        }
        else {
            return false;
        }
    }

    /**
     * Check the existence of a collection. <br>
     *
     * @return true if the collection exist
     */
    public boolean isExistsCollection() {
        return mongoService.isExistsCollection(entityClazz);
    }


    public List findAllNoSort() {
        String collectionName = annotationService.getCollectionName(entityClazz);

        if (USA_REPOSITORY && crudRepository != null) { //@todo noRepository
            return crudRepository.findAll();
        }
        else {
            return mongoService.mongoOp.find(new Query(), entityClazz, collectionName);
        }
    }

    public List findAllSortCorrente() {
        String collectionName = annotationService.getCollectionName(entityClazz);
        Query query = new Query();

        if (USA_REPOSITORY && crudRepository != null) { //@todo noRepository
            return crudRepository.findAll();
        }
        else {
            if (sortOrder != null) {
                query.with(sortOrder);
            }
            return mongoService.mongoOp.find(query, entityClazz, collectionName);
        }
    }

    /**
     * Controlla l'esistenza della property <br>
     * La lista funziona anche se la property del sort è errata <br>
     * Ma ovviamente il sort non viene effettuato <br>
     */
    public List findAllSort(Sort sort) {
        String collectionName = annotationService.getCollectionName(entityClazz);
        boolean esiste;
        Sort.Order order;
        String property;
        String message;

        if (sort == null) {
            return findAllNoSort();
        }
        else {
            if (sort.stream().count() == 1) {
                order = sort.stream().toList().get(0);
                property = order.getProperty();
                esiste = reflectionService.isEsiste(entityClazz, property);
                if (esiste || property.equals(FIELD_NAME_ID_CON)) {
                    if (USA_REPOSITORY && crudRepository != null) { //@todo noRepository
                        return crudRepository.findAll(sort);
                    }
                    else {
                        return mongoService.query(entityClazz, sort);
                    }
                }
                else {
                    message = String.format("Non esiste la property %s per l'ordinamento della classe %s", property, entityClazz.getSimpleName());
                    logger.warn(new WrapLog().exception(new AlgosException(message)).usaDb());
                    if (USA_REPOSITORY && crudRepository != null) { //@todo noRepository
                        return crudRepository.findAll(sort);
                    }
                    else {
                        return mongoService.query(entityClazz, sort);
                    }
                }
            }
            else {
                if (USA_REPOSITORY && crudRepository != null) { //@todo noRepository
                    return crudRepository.findAll(sort);
                }
                else {
                    return mongoService.query(entityClazz, sort);
                }
            }
        }
    }


    public List findAllBeanProperty(final String propertyName, final Object propertyValue) {
        String collectionName = annotationService.getCollectionName(entityClazz);
        Query query = new Query();

        query.addCriteria(Criteria.where(propertyName).is(propertyValue));

        if (textService.isValid(collectionName)) {
            return mongoService.mongoOp.find(query, entityClazz, collectionName);
        }
        else {
            return mongoService.mongoOp.find(query, entityClazz);
        }
    }

    public List<String> findAllStringKey() {
        String keyPropertyName = annotationService.getKeyPropertyName(entityClazz);
        return mongoService.projectionString(entityClazz, keyPropertyName);
    }

    public List<String> findAllStringKeyReverseOrder() {
        String keyPropertyName = annotationService.getKeyPropertyName(entityClazz);
        return mongoService.projectionStringReverseOrder(entityClazz, keyPropertyName);
    }

    public List<String> findAllStringProperty(String keyPropertyName) {
        return mongoService.projectionString(entityClazz, keyPropertyName);
    }

    @Deprecated
    public AEntity add(Object objEntity) {
        AEntity entity = (AEntity) objEntity;

        return (AEntity) crudRepository.insert(entity);
    }

    @Deprecated
    public AEntity save(Object entity) {
        return (AEntity) crudRepository.save(entity);
    }

    @Deprecated
    public AEntity update(Object entity) {
        return (AEntity) crudRepository.save(entity);
    }

    @Deprecated
    public void delete(Object entity) {
        try {
            crudRepository.delete(entity);
        } catch (Exception unErrore) {
            logger.error(unErrore);
        }
    }

    public boolean deleteAll() {
        String collectionName;

        if (USA_REPOSITORY && crudRepository != null) { //@todo noRepository
            try {
                crudRepository.deleteAll();
            } catch (Exception unErrore) {
                logger.error(unErrore);
            }
        }
        else {
            collectionName = annotationService.getCollectionName(entityClazz);
            mongoService.mongoOp.dropCollection(collectionName);
        }

        return !isExistsCollection();
    }

    public int count() {
        if (USA_REPOSITORY && crudRepository != null) { //@todo noRepository
            return ((Long) crudRepository.count()).intValue();
        }
        else {
            return mongoService.count(entityClazz);
        }
    }


    /**
     * Creazione di alcuni dati <br>
     * Viene invocato dal bottone Reset della lista o dalla UtilityView <br>
     * Può essere invocato IN PARTICOLARI CONDIZIONI alla creazione del programma <br>
     * La collezione viene svuotata <br>
     * Non deve essere sovrascritto <br>
     */
    public AResult resetForcing() {
        AResult result = AResult.build();
        String collectionName = annotationService.getCollectionName(entityClazz);
        String clazzName = entityClazz.getSimpleName();
        String message;
        String elementi;

        if (mongoService.isCollectionNullOrEmpty(entityClazz)) {
            return resetOnlyEmpty().method("resetForcing");
        }
        else {
            if (deleteAll()) {
                result = resetOnlyEmpty().method("resetForcing");
                elementi = textService.format(result.getIntValue());
                message = String.format("La collection '%s' della classe [%s] esisteva ma è stata cancellata e i dati sono stati ricreati. Contiene %s elementi.", collectionName, clazzName, elementi);
                return result.validMessage(message);
            }
            else {
                message = String.format("Non sono riuscito a cancellare la collection '%s' della classe [%s]", collectionName, clazzName);
                return result.errorMessage(message);
            }
        }
    }


    /**
     * Reset/creazione di alcuni dati <br>
     * Esegue SOLO se la collection NON esiste oppure esiste ma è VUOTA <br>
     * Viene invocato: <br>
     * 1) alla creazione del programma da VaadData.resetData() <br>
     * 2) dal buttonDeleteReset -> CrudView.reset() <br>
     * 3) da UtilityView.reset() <br>
     * I dati possono essere presi da una Enumeration, da un file CSV locale, da un file CSV remoto o creati hardcoded <br>
     * Deve essere sovrascritto, invocando PRIMA il metodo della superclasse <br>
     */
    public AResult resetOnlyEmpty() {
        String collectionName = annotationService.getCollectionName(entityClazz);
        AResult result = AResult.build().method("resetOnlyEmpty").target(collectionName);
        String clazzName = entityClazz.getSimpleName();
        String backendName = clazzName + SUFFIX_BACKEND;
        String elementi;
        String message;

        if (mongoService.isCollectionNullOrEmpty(entityClazz)) {
            message = String.format("La collection '%s' della classe [%s] era vuota ma non sono riuscito a crearla. Probabilmente manca il metodo [%s].resetOnlyEmpty()", collectionName, clazzName, backendName);
            return result.errorMessage(message).typeResult(AETypeResult.collectionVuota).typeLog(AETypeLog.reset);
        }
        else {
            elementi = textService.format(count());
            message = String.format("La collection '%s' della classe [%s] esisteva già, non era vuota e non è stata toccata. Contiene %s elementi.", collectionName, clazzName, elementi);
            return result.validMessage(message).typeResult(AETypeResult.collectionPiena).intValue(count());
        }
    }

    @Deprecated
    public AResult fixResult(AResult result) {
        String message = String.format(" %d elementi.", count());
        return result.addValidMessage(message).intValue(count());
    }

    /**
     * Esegue un azione di download, specifica del programma/package in corso <br>
     * Deve essere sovrascritto, senza invocare il metodo della superclasse <br>
     */
    public void download() {
    }

    /**
     * Esegue un azione di download, specifica del programma/package in corso <br>
     * Deve essere sovrascritto, senza invocare il metodo della superclasse <br>
     *
     * @param wikiTitle della pagina sul web
     */
    public void download(final String wikiTitle) {
    }

    public Sort getSortOrder() {
        return sortOrder;
    }

    public Sort getSortMongo() {
        return Sort.unsorted();
    }

    public Sort getSortKeyID() {
        String keyPropertyName = annotationService.getKeyPropertyName(entityClazz);

        if (textService.isValid(keyPropertyName)) {
            return Sort.by(Sort.Direction.ASC, keyPropertyName);
        }

        return sortOrder;
    }

    public Sort getSort(String property) {
        if (textService.isValid(property)) {
            return Sort.by(Sort.Direction.ASC, property);
        }
        return sortOrder;
    }

}