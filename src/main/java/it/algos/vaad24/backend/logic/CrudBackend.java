package it.algos.vaad24.backend.logic;

import com.mongodb.*;
import com.mongodb.client.*;
import com.vaadin.flow.data.provider.*;
import it.algos.vaad24.backend.boot.*;
import static it.algos.vaad24.backend.boot.VaadCost.*;
import it.algos.vaad24.backend.entity.*;
import it.algos.vaad24.backend.enumeration.*;
import it.algos.vaad24.backend.exception.*;
import it.algos.vaad24.backend.interfaces.*;
import it.algos.vaad24.backend.service.*;
import it.algos.vaad24.backend.wrapper.*;
import org.bson.*;
import org.bson.types.*;
import org.springframework.data.domain.*;
import org.springframework.data.mongodb.core.*;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.*;
import org.springframework.data.mongodb.repository.*;

import javax.annotation.*;
import java.util.*;
import java.util.stream.*;

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
 * isExistById()
 * isExistByKey(), se esiste una key
 * isExistByOrdine(final int ordine), se esiste FIELD_NAME_ORDINE
 * isExistByProperty()
 * <p>
 * findById(final String keyID)
 * findByKey(final String keyValue), se esiste una keyPropertyName
 * findByOrdine(final int ordine), se esiste FIELD_NAME_ORDINE
 * findByProperty(final String propertyName, final Object propertyValue)
 * <p>
 * creaIfNotExist(final Object keyPropertyValue), se esiste una keyPropertyName
 * fixKey(AEntity newEntityBean)
 * getIdKey(String keyPropertyValue), se esiste una keyPropertyName
 * <p>
 * Le sottoclassi concrete: <br>
 * Non mantengono lo stato di una istanza entityBean <br>
 * NOT annotated with @SpringComponent (inutile, esiste già @Service) <br>
 * NOT annotated with @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON) (inutile, esiste già @Service) <br>
 */
public abstract class CrudBackend extends AbstractService {


    public Sort sortOrder;

    /**
     * The Entity Class  (obbligatoria sempre e final)
     */
    public final Class<? extends AEntity> entityClazz;


    public MongoRepository crudRepository;


    /**
     * Regola la entityClazz (final nella superclasse) associata a questo service <br>
     */
    public CrudBackend(final Class<? extends AEntity> entityClazz) {
        this.entityClazz = entityClazz;
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
    }// end of constructor with @Autowired


    /**
     * La injection viene fatta da SpringBoot SOLO DOPO il metodo init() del costruttore <br>
     * Si usa quindi un metodo @PostConstruct per avere disponibili tutte le istanze @Autowired <br>
     * <p>
     * Ci possono essere diversi metodi con @PostConstruct e firme diverse e funzionano tutti, <br>
     * ma l'ordine con cui vengono chiamati (nella stessa classe) NON è garantito <br>
     * Se esistono delle sottoclassi e non viene sovrascritto, passa di qui per ognuna di esse <br>
     * Se esistono delle sottoclassi e viene sovrascritto, passa di qui per ogni sottoclasse oltre che per questa istanza <br>
     */
    @PostConstruct
    private void postConstruct() {
        //--Preferenze usate da questa 'logic'
        this.fixPreferenze();

        //--Controllo iniziale del database per tutte le classi che hanno il flag usaReset=true
        if (annotationService.usaReset(entityClazz)) {
            resetOnlyEmpty();
        }
    }

    /**
     * Preferenze usate da questa 'backend' <br>
     * Primo metodo chiamato dopo init() (implicito del costruttore) e postConstruct() (facoltativo) <br>
     * Puo essere sovrascritto, invocando PRIMA il metodo della superclasse <br>
     */
    protected void fixPreferenze() {
        if (VaadVar.prefList == null || VaadVar.prefList.size() < Pref.values().length) {
            VaadBoot.start();
        }

        for (AIGenPref pref : VaadVar.prefList) {
            pref.setText(textService);
            pref.setLogger(logService);
            pref.setDate(dateService);
            pref.setPreferenceService(preferenceService);
        }

        if (reflectionService.isEsiste(entityClazz, FIELD_NAME_ORDINE)) {
            this.sortOrder = Sort.by(Sort.Direction.ASC, FIELD_NAME_ORDINE);
        }
        else {
            this.sortOrder = Sort.by(Sort.Direction.ASC, FIELD_NAME_ID_CON);
        }
    }

    public AEntity creaIfNotExist(final Object keyPropertyValue) {
        return insert(newEntity(keyPropertyValue));
    }

    public AEntity creaIfNotExist(final String keyPropertyValue) {
        AEntity entityBean;

        if (textService.isEmpty(keyPropertyValue) || isExistByKey(keyPropertyValue)) {
            return null;
        }
        else {
            entityBean = newEntity(keyPropertyValue);
            return entityBean != null ? insert(entityBean) : null;
        }
    }

    public AEntity newEntity(Object keyPropertyValue) {
        return newEntity((String) keyPropertyValue);
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
            logService.warn(AETypeLog.nuovo, unErrore);
        }

        return newEntityBean;
    }

    public void fixOrdine(AEntity newEntityBean, final int ordine, final String nome) {
        OrdineEntity entityBeanSuperclasse = newEntityOrdine(ordine, nome);
        beanService.copiaAncheID(entityBeanSuperclasse, newEntityBean);
    }

    public OrdineEntity newEntityOrdine(final int ordine, final String nome) {
        //        return OrdineEntity.builderOrdine().ordine(ordine).nome(textService.isValid(nome) ? nome : null).build();
        return null;
    }

    public int nextOrdine() {
        int nextOrdine = 1;
        AEntity entityBean = null;
        List<AEntity> lista;
        String collectionName = annotationService.getCollectionName(entityClazz);
        Sort sort = Sort.by(Sort.Direction.DESC, FIELD_NAME_ORDINE);
        Query query = new Query();
        query.addCriteria(Criteria.where("code").exists(true));
        query.with(sort);

        if (textService.isValid(collectionName)) {
            lista = (List<AEntity>) mongoService.mongoOp.find(query, entityClazz, collectionName);
        }
        else {
            lista = (List<AEntity>) mongoService.mongoOp.find(query, entityClazz);
        }
        if (lista != null && lista.size() > 0) {
            entityBean = lista.get(0);
        }
        if (entityBean != null) {
            Object obj = reflectionService.getPropertyValue(entityBean, FIELD_NAME_ORDINE);
            if (obj instanceof Integer oldOrdine) {
                nextOrdine = oldOrdine + 1;
            }
        }

        return nextOrdine;
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

        if (textService.isValid(newEntityBean.id)) {
            return newEntityBean;
        }

        keyPropertyName = annotationService.getKeyPropertyName(entityClazz);
        if (textService.isValid(keyPropertyName) && !keyPropertyName.equals(FIELD_NAME_ID_CON)) {
            keyPropertyValue = reflectionService.getPropertyValueStr(newEntityBean, keyPropertyName);
            if (textService.isValid(keyPropertyValue)) {
                keyPropertyValue = getIdKey(keyPropertyValue);
                newEntityBean.id = keyPropertyValue;
            }
        }
        else {
            newEntityBean.id = new ObjectId().toString();
        }

        return newEntityBean;
    }

    public String getIdKey(String keyPropertyValue) {
        boolean usaKeyIdSenzaSpazi;
        boolean usaKeyIdMinuscolaCaseInsensitive;

        if (textService.isValid(keyPropertyValue)) {
            usaKeyIdSenzaSpazi = annotationService.usaKeyIdSenzaSpazi(entityClazz); ;
            usaKeyIdMinuscolaCaseInsensitive = annotationService.usaKeyIdMinuscolaCaseInsensitive(entityClazz); ;
            keyPropertyValue = usaKeyIdSenzaSpazi ? textService.levaSpazi(keyPropertyValue) : keyPropertyValue;
            keyPropertyValue = usaKeyIdMinuscolaCaseInsensitive ? keyPropertyValue.toLowerCase() : keyPropertyValue;
        }
        else {
            keyPropertyValue = new ObjectId().toString();
        }

        return keyPropertyValue;
    }

    public boolean isExistById(final String keyIdValue) {
        return isExistByProperty(FIELD_NAME_ID_CON, keyIdValue);
    }


    public boolean isExistByKey(final String keyValue) {
        String keyPropertyName;

        if (annotationService.isEsisteKeyPropertyName(entityClazz)) {
            keyPropertyName = annotationService.getKeyPropertyName(entityClazz);
            return isExistByProperty(keyPropertyName, keyValue);
        }
        else {
            return false;
        }
    }

    public boolean isExistByOrder(final int orderValue) {
        if (reflectionService.isEsiste(entityClazz, FIELD_NAME_ORDINE)) {
            return isExistByProperty(FIELD_NAME_ORDINE, orderValue);
        }
        else {
            return false;
        }
    }

    public boolean isExistByProperty(final String propertyName, final Object propertyValue) {
        String collectionName;
        Query query = new Query();

        if (reflectionService.isEsiste(entityClazz, propertyName) || propertyName.equals(FIELD_NAME_ID_CON)) {
            collectionName = annotationService.getCollectionName(entityClazz);
            query.addCriteria(Criteria.where(propertyName).is(propertyValue));
            if (textService.isValid(collectionName)) {
                return mongoService.mongoOp.exists(query, entityClazz.getClass(), collectionName);
            }
            else {
                return mongoService.mongoOp.exists(query, entityClazz.getClass());
            }
        }
        else {
            return false;
        }
    }


    /**
     * Ricerca della singola entity <br>
     * Può essere sovrascritto nella sottoclasse specifica per il casting di ritorno <br>
     *
     * @param keyId (obbligatorio, unico)
     *
     * @return la entity trovata
     */
    public AEntity findById(final String keyId) {
        return findByProperty(FIELD_NAME_ID_CON, keyId);
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
        String keyPropertyName;

        if (annotationService.isEsisteKeyPropertyName(entityClazz)) {
            keyPropertyName = annotationService.getKeyPropertyName(entityClazz);
            return findByProperty(keyPropertyName, keyValue);
        }
        else {
            return null;
        }
    }


    public AEntity findByOrder(final int orderValue) {
        if (reflectionService.isEsiste(entityClazz, FIELD_NAME_ORDINE)) {
            return findByProperty(FIELD_NAME_ORDINE, orderValue);
        }
        else {
            return null;
        }
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
        String collectionName;
        Query query = new Query();

        if (reflectionService.isEsiste(entityClazz, propertyName) || propertyName.equals(FIELD_NAME_ID_CON)) {
            collectionName = annotationService.getCollectionName(entityClazz);
            query.addCriteria(Criteria.where(propertyName).is(propertyValue));
            if (textService.isValid(collectionName)) {
                return mongoService.mongoOp.findOne(query, entityClazz, collectionName);
            }
            else {
                return mongoService.mongoOp.findOne(query, entityClazz);
            }
        }
        else {
            return null;
        }
    }


    public AEntity save(AEntity entityBean) {
        if (!isExistById(entityBean.id)) {
            return insert(entityBean);
        }
        else {
            return update(entityBean);
        }
    }

    public AEntity insert(AEntity entityBean) {
        String collectionName = annotationService.getCollectionName(entityClazz);

        //possibilità di inserire il keyID prima dell'inserimento automatico di mongoDB se manca
        entityBean = fixKey(entityBean);

        if (USA_REPOSITORY && crudRepository != null) { //@todo noRepository
            return (AEntity) crudRepository.insert(entityBean);
        }
        else {
            if (textService.isValid(collectionName)) {
                try {
                    return mongoService.mongoOp.insert(entityBean, collectionName);
                } catch (Exception unErrore) {
                    logService.error(new WrapLog().exception(new AlgosException(unErrore)).usaDb());
                }
            }
            else {
                try {
                    return mongoService.mongoOp.insert(entityBean);
                } catch (Exception unErrore) {
                    logService.error(new WrapLog().exception(new AlgosException(unErrore)).usaDb());
                }
            }
        }

        return null;
    }


    public AEntity update(final AEntity entityBean) {
        String collectionName = annotationService.getCollectionName(entityClazz);
        Query query = new Query();

        if (isExistById(entityBean.id)) {
            if (USA_REPOSITORY && crudRepository != null) { //@todo noRepository
                try {
                    return (AEntity) crudRepository.save(entityBean);
                } catch (Exception unErrore) {
                    logService.error(unErrore);
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

        if (isExistById(entityBean.id)) {
            if (USA_REPOSITORY && crudRepository != null) { //@todo noRepository
                try {
                    crudRepository.delete(entityBean);
                } catch (Exception unErrore) {
                    logService.error(unErrore);
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

    private List findQuery(Query query) {
        String collectionName = annotationService.getCollectionName(entityClazz);

        if (textService.isValid(collectionName)) {
            return mongoService.mongoOp.find(query, entityClazz, collectionName);
        }
        else {
            return mongoService.mongoOp.find(query, entityClazz);
        }
    }


    public List findAll() {
        return findAllNoSort();
    }

    public List findAllNoSort() {
        return findQuery(new Query());
    }


    public List findAllSortCorrente() {
        Query query = new Query();

        if (sortOrder != null) {
            query.with(sortOrder);
        }

        return findQuery(query);
    }


    public List findAllSortCorrenteReverse() {
        Query query = new Query();
        Sort sort;
        String sortText;
        String[] parti;
        String field;
        String order;
        Sort.Direction direction;
        String keyPropertyName;

        if (USA_REPOSITORY && crudRepository != null) { //@todo noRepository
            return crudRepository.findAll();
        }
        else {
            if (sortOrder != null) {
                sortText = sortOrder.toString();
                parti = sortText.split(DUE_PUNTI);
                field = parti[0].trim();
                order = parti[1].trim();
                if (order.equals(SORT_SPRING_ASC)) {
                    direction = Sort.Direction.DESC;
                }
                else {
                    direction = Sort.Direction.ASC;
                }
                sort = Sort.by(direction, field);
                query.with(sort);
                return findQuery(query);
            }
            if (reflectionService.isEsiste(entityClazz, FIELD_NAME_ORDINE)) {
                sort = Sort.by(Sort.Direction.DESC, FIELD_NAME_ORDINE);
                query.with(sort);
                return findQuery(query);
            }
            if (annotationService.isEsisteKeyPropertyName(entityClazz)) {
                keyPropertyName = annotationService.getKeyPropertyName(entityClazz);
                sort = Sort.by(Sort.Direction.DESC, keyPropertyName);
                query.with(sort);
                return findQuery(query);
            }

            return findQuery(query);
        }
    }

    /**
     * Controlla l'esistenza della property <br>
     * La lista funziona anche se la property del sort è errata <br>
     * Ma ovviamente il sort non viene effettuato <br>
     */
    public List findAllSort(Sort sort) {
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
                    logService.warn(new WrapLog().exception(new AlgosException(message)).usaDb());
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

    public List findAllSortKey() {
        String keyPropertyName;
        Sort sort;

        if (annotationService.isEsisteKeyPropertyName(entityClazz)) {
            keyPropertyName = annotationService.getKeyPropertyName(entityClazz);
            sort = Sort.by(Sort.Direction.ASC, keyPropertyName);
            return findAllSort(sort);
        }
        else {
            return findAllNoSort();
        }
    }

    public List findAllSortOrder() {
        Sort sort;

        if (reflectionService.isEsiste(entityClazz, FIELD_NAME_ORDINE)) {
            sort = Sort.by(Sort.Direction.ASC, FIELD_NAME_ORDINE);
            return findAllSort(sort);
        }
        else {
            return findAllNoSort();
        }
    }


    public List findAllByProperty(final String propertyName, final Object propertyValue) {
        return findAllByProperty(propertyName, propertyValue, null);
    }

    public List findAllByProperty(final String propertyName, final Object propertyValue, Sort sort) {
        Query query = new Query();

        if (textService.isEmpty(propertyName)) {
            return null;
        }
        if (propertyValue == null) {
            return null;
        }

        query.addCriteria(Criteria.where(propertyName).is(propertyValue));
        if (sort != null) {
            query.with(sort);
        }

        return findQuery(query);
    }

    /**
     * Lista della sola keyProperty indicata per tutte le entities della collezione <br>
     * Ordinata secondo la keyProperty in ordine ascendente <br>
     */
    public List<String> findAllForKeySortKey() {
        return findAllForKeySortKeyBase(KEY_ORDINE_ASCENDENTE);
    }

    /**
     * Lista della sola keyProperty indicata per tutte le entities della collezione <br>
     * Ordinata secondo la keyProperty in ordine discendente <br>
     */
    public List<String> findAllForKeySortKeyReverse() {
        return findAllForKeySortKeyBase(KEY_ORDINE_DISCENDENTE);
    }

    private List<String> findAllForKeySortKeyBase(int ordinamento) {
        String keyPropertyName;

        if (annotationService.isEsisteKeyPropertyName(entityClazz)) {
            keyPropertyName = annotationService.getKeyPropertyName(entityClazz);
            return mongoService.projectionString(entityClazz, keyPropertyName, new BasicDBObject(keyPropertyName, ordinamento));
        }
        else {
            return null;
        }
    }


    /**
     * Lista della sola keyProperty indicata per tutte le entities della collezione <br>
     * Ordinata secondo la property FIELD_NAME_ORDINE in ordine ascendente <br>
     */
    public List<String> findAllForKeySortOrdine() {
        return findAllForKeySortOrdineBase(KEY_ORDINE_ASCENDENTE);
    }

    /**
     * Lista della sola keyProperty indicata per tutte le entities della collezione <br>
     * Ordinata secondo la property FIELD_NAME_ORDINE in ordine discendente <br>
     */
    public List<String> findAllForKeySortOrdineReverse() {
        return findAllForKeySortOrdineBase(KEY_ORDINE_DISCENDENTE);
    }


    /**
     * Lista della sola keyProperty indicata per tutte le entities della collezione <br>
     * Ordinata secondo la keyProperty <br>
     * Se si vuole un ordinamento specifico, può essere sovrascritto SENZA invocare il metodo della superclasse <br>
     */
    private List<String> findAllForKeySortOrdineBase(int ordinamento) {
        String keyPropertyName;

        if (annotationService.isEsisteKeyPropertyName(entityClazz)) {
            keyPropertyName = annotationService.getKeyPropertyName(entityClazz);
            if (reflectionService.isEsiste(entityClazz, FIELD_NAME_ORDINE)) {
                return mongoService.projectionString(entityClazz, keyPropertyName, new BasicDBObject(FIELD_NAME_ORDINE, ordinamento));
            }
            else {
                return mongoService.projectionString(entityClazz, keyPropertyName, new BasicDBObject(keyPropertyName, ordinamento));
            }
        }
        else {
            return null;
        }
    }

    public DataProvider getProvider() {
        return DataProvider.fromCallbacks(
                query -> this.fetch(query.getOffset(), query.getLimit()),
                query -> this.count()
        );
    }

    public Stream<Object> fetch(final int offset, final int limit) {
        List lista;
        Query query = new Query();

        query.skip(offset);
        query.limit(limit);
        query.with(getSortOrder());
        lista = mongoService.mongoOp.find(query, entityClazz);

        return lista != null ? lista.stream() : null;
    }

    //    /**
    //     * Lista della sola keyProperty indicata per tutte le entities della collezione <br>
    //     * Ordinata al contrario della keyProperty <br>
    //     * Se si vuole un ordinamento specifico, può essere sovrascritto SENZA invocare il metodo della superclasse <br>
    //     */
    //    public List<String> findAllForKeyReverseOrder() {
    //        if (isOrdineEntity()) {
    //            return mongoService.projectionString(entityClazz, FIELD_NAME_NOME, new BasicDBObject(FIELD_NAME_ORDINE, -1));
    //        }
    //        else {
    //            return findAllForPropertyReverseOrder(annotationService.getKeyPropertyName(entityClazz));
    //        }
    //    }

    //    public boolean isOrdineEntity() {
    //        boolean isNome = reflectionService.isEsiste(entityClazz, FIELD_NAME_NOME);
    //        boolean isOrdine = reflectionService.isEsiste(entityClazz, FIELD_NAME_ORDINE);
    //
    //        return isNome && isOrdine;
    //    }

    /**
     * Lista della sola property indicata per tutte le entities della collezione <br>
     * Ordinata secondo la property stessa <br>
     * Se si vuole un ordinamento specifico, può essere sovrascritto SENZA invocare il metodo della superclasse <br>
     */
    public List<String> findAllForProperty(String keyPropertyName) {
        return mongoService.projectionString(entityClazz, keyPropertyName, new BasicDBObject(keyPropertyName, 1));
    }

    /**
     * Lista della sola property indicata per tutte le entities della collezione <br>
     * Ordinata al contrario della property stessa <br>
     * Se si vuole un ordinamento specifico, può essere sovrascritto SENZA invocare il metodo della superclasse <br>
     */
    public List<String> findAllForPropertyReverseOrder(String keyPropertyName) {
        return mongoService.projectionString(entityClazz, keyPropertyName, new BasicDBObject(keyPropertyName, 1));
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
            logService.error(unErrore);
        }
    }

    public int count() {
        if (USA_REPOSITORY && crudRepository != null) { //@todo noRepository
            return ((Long) crudRepository.count()).intValue();
        }
        else {
            return mongoService.count(entityClazz);
        }
    }

    public boolean deleteAll() {
        String collectionName;

        if (USA_REPOSITORY && crudRepository != null) { //@todo noRepository
            try {
                crudRepository.deleteAll();
            } catch (Exception unErrore) {
                logService.error(unErrore);
            }
        }
        else {
            collectionName = annotationService.getCollectionName(entityClazz);
            mongoService.mongoOp.dropCollection(collectionName);
        }

        return !isExistsCollection();
    }


    /**
     * Creazione di alcuni dati <br>
     * Viene invocato dal bottone Reset della lista o dalla UtilityView <br>
     * Può essere invocato IN PARTICOLARI CONDIZIONI alla creazione del programma <br>
     * La collezione viene svuotata <br>
     * Non deve essere sovrascritto <br>
     * <p>
     * Cancella la collection <br>
     * Invoca il metodo resetOnlyEmpty() <br>
     * Log a video e nel db del risultato <br>
     * Fix dei dati e durata dell'operazione <br>
     */
    public AResult resetForcing() {
        AResult result;
        String collectionName = annotationService.getCollectionName(entityClazz);
        String clazzName = entityClazz.getSimpleName();
        String message;
        String elementi;

        if (mongoService.isCollectionNullOrEmpty(entityClazz)) {
            result = resetDownload();
            if (result.isValido()) {
                result.typeResult(AETypeResult.collectionCreata);
            }
            result.method("resetForcing");
            elementi = textService.format(result.getIntValue());
            message = String.format("La collection '%s' della classe [%s] era vuota ed è stata creata. Contiene %s elementi.", collectionName, clazzName, elementi);
            message += result.deltaSec();
            result.validMessage(message);
            logService.info(new WrapLog().message(result.getMessage()).type(AETypeLog.resetForcing).usaDb());
            return result;
        }
        else {
            if (deleteAll()) {
                result = resetDownload();
                if (result.isValido()) {
                    result.typeResult(AETypeResult.collectionCreata);
                }
                result.method("resetForcing");
                message = String.format("La collection '%s' della classe [%s] esisteva ma è stata cancellata e i dati sono stati ricreati. ", collectionName, clazzName);
                message += result.elementi();
                message += result.delta();
                result.validMessage(message);
                logService.debugOnly(new WrapLog().message(result.getMessage()).type(AETypeLog.resetForcing).usaDb());
                return result;
            }
            else {
                message = String.format("Non sono riuscito a cancellare la collection '%s' della classe [%s]", collectionName, clazzName);
                logService.info(new WrapLog().message(message).type(AETypeLog.resetForcing).usaDb());
                return AResult.errato(message).fine();
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
        AResult result = AResult.build().method("resetOnlyEmpty");
        String clazzName = entityClazz.getSimpleName();
        String elementi;
        String message;

        if (mongoService.isCollectionNullOrEmpty(entityClazz)) {
            result = resetDownload();
            if (!result.isValido()) {
                return result;
            }

            if (result.isEseguito()) {
                result = result.typeResult(AETypeResult.collectionCreata).method("resetOnlyEmpty");
            }
            elementi = textService.format(count());
            message = String.format("La collection '%s' della classe [%s] era vuota ed è stata creata. Contiene %s elementi. %s", collectionName, clazzName, elementi, result.deltaSec());
            result = result.typeLog(AETypeLog.resetOnlyEmpty).typeResult(AETypeResult.collectionPiena).intValue(count());
            logService.debugOnly(new WrapLog().message(message).type(AETypeLog.resetOnlyEmpty).usaDb());
            return result;
        }
        else {
            elementi = textService.format(count());
            message = String.format("La collection '%s' della classe [%s] esisteva già, non era vuota e non è stata toccata. Contiene %s elementi.", collectionName, clazzName, elementi);
            result = result.valido(true).typeLog(AETypeLog.resetOnlyEmpty).typeResult(AETypeResult.collectionPiena).intValue(count());
            logService.debug(new WrapLog().message(message).type(AETypeLog.resetOnlyEmpty));
            return result;
        }
    }

    public AResult resetDownload() {
        String collectionName = annotationService.getCollectionName(entityClazz);
        return AResult.errato().typeLog(AETypeLog.reset).typeResult(AETypeResult.indeterminato).target(collectionName);
    }


    public AResult fixResult(AResult result) {
        String clazzName = entityClazz.getSimpleName();
        String collectionName = annotationService.getCollectionName(entityClazz);
        String message;
        List lista = result.getLista();

        result.fine();
        if (lista != null && lista.size() > 0) {
            result.setIntValue(lista.size());
            result.setLista(lista);
            result.valido(true).eseguito().typeResult(AETypeResult.collectionPiena);
        }
        else {
            result.typeResult(AETypeResult.error);
            message = String.format("Non sono riuscito a creare la collection '%s'. Controlla il metodo [%s].resetOnlyEmpty()", collectionName, clazzName);
            return result.errorMessage(message);
        }

        return result;
    }


    @Deprecated
    public AResult fixResult(AResult result, List lista) {
        String clazzName = entityClazz.getSimpleName();
        String collectionName = annotationService.getCollectionName(entityClazz);
        String message;

        result.fine();
        if (lista != null && lista.size() > 0) {
            result.setIntValue(lista.size());
            result.setLista(lista);
            result.valido(true).eseguito().typeResult(AETypeResult.collectionPiena);
        }
        else {
            result.typeResult(AETypeResult.error);
            message = String.format("Non sono riuscito a creare la collection '%s'. Controlla il metodo [%s].resetOnlyEmpty()", collectionName, clazzName);
            return result.errorMessage(message);
        }

        return result;
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


    public Document getDocumentById(String keyCode) {
        Document doc;
        MongoCollection<Document> collection;
        BasicDBObject whereQuery;
        MongoDatabase client = mongoService.getDB(VaadVar.mongoDatabaseName);
        String collectionName = annotationService.getCollectionName(entityClazz);
        collection = client.getCollection(collectionName);
        whereQuery = new BasicDBObject();
        whereQuery.put(FIELD_NAME_ID_CON, keyCode);
        doc = collection.find(whereQuery).first();

        return doc;
    }

    public Document getDocumentByKey(String keyCode) {
        Document doc;
        MongoCollection<Document> collection;
        BasicDBObject whereQuery;
        MongoDatabase client = mongoService.getDB(VaadVar.mongoDatabaseName);
        String collectionName = annotationService.getCollectionName(entityClazz);
        String keyProperty = annotationService.getKeyPropertyName(entityClazz);
        collection = client.getCollection(collectionName);
        whereQuery = new BasicDBObject();
        whereQuery.put(keyProperty, keyCode);
        doc = collection.find(whereQuery).first();

        return doc;
    }

}