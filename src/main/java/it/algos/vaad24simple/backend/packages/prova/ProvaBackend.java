package it.algos.vaad24simple.backend.packages.prova;

import static it.algos.vaad24.backend.boot.VaadCost.*;
import it.algos.vaad24.backend.entity.*;
import it.algos.vaad24.backend.logic.*;
import it.algos.vaad24.backend.packages.geografia.continente.*;
import org.springframework.data.mongodb.core.*;
import org.springframework.data.mongodb.core.query.*;
import org.springframework.stereotype.*;

/**
 * Project vaad24
 * Created by Algos
 * User: gac
 * Date: Mon, 13-Feb-2023
 * Time: 09:40
 * <p>
 * Service di una entityClazz specifica e di un package <br>
 * Garantisce i metodi di collegamento per accedere al database <br>
 * Non mantiene lo stato di una istanza entityBean <br>
 * Mantiene lo stato della entityClazz <br>
 * NOT annotated with @SpringComponent (inutile, esiste già @Service) <br>
 * NOT annotated with @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON) (inutile, esiste già @Service) <br>
 */
@Service
public class ProvaBackend extends CrudBackend {


    /**
     * Costruttore @Autowired (facoltativo) @Qualifier (obbligatorio) <br>
     * In the newest Spring release, it’s constructor does not need to be annotated with @Autowired annotation <br>
     * Si usa un @Qualifier(), per specificare la classe che incrementa l'interfaccia repository <br>
     * Si usa una costante statica, per essere sicuri di scriverla uguale a quella di xxxRepository <br>
     * Regola la classe di persistenza dei dati specifica e la passa al costruttore della superclasse <br>
     * Regola la entityClazz (final nella superclasse) associata a questo service <br>
     *
     * @param crudRepository per la persistenza dei dati
     */
    //@todo registrare eventualmente come costante in VaadCost il valore del Qualifier
    public ProvaBackend() {
        super(null, Prova.class);
    }


    public boolean creaIfNotExist(final String nome) {
        return checkAndSave(newEntity(nome, null)) != null;
    }


    public Prova checkAndSave(final Prova entityBean) {
        String collectionName = annotationService.getCollectionName(entityClazz);

        if (!isExistProperty(entityBean.nome)) {
            if (textService.isValid(collectionName)) {
                return (Prova) mongoService.mongoOp.insert(entityBean, collectionName);
            }
            else {
                return (Prova) mongoService.mongoOp.insert(entityBean);
            }
        }
        else {
            return null;
        }
    }

    public boolean isExistProperty(final String keyPropertyValue) {
        String collectionName = annotationService.getCollectionName(entityClazz);
        String keyPropertyName = annotationService.getKeyPropertyName(entityClazz);
        Query query = new Query();

        query.addCriteria(Criteria.where(keyPropertyName).is(keyPropertyValue));
        if (textService.isValid(collectionName)) {
            return mongoService.mongoOp.exists(query, entityClazz.getClass(), collectionName);
        }
        else {
            return mongoService.mongoOp.exists(query, entityClazz.getClass());
        }
    }


    /**
     * Creazione in memoria di una nuova entity che NON viene salvata <br>
     * Usa il @Builder di Lombok <br>
     * Eventuali regolazioni iniziali delle property <br>
     *
     * @return la nuova entity appena creata (non salvata)
     */
    public Prova newEntity() {
        return newEntity(VUOTA, null);
    }
    /**
     * Creazione in memoria di una nuova entity che NON viene salvata <br>
     * Usa il @Builder di Lombok <br>
     * Eventuali regolazioni iniziali delle property <br>
     *
     * @return la nuova entity appena creata (non salvata)
     */
    public Prova newEntity(String nome) {
        return newEntity(nome, null);
    }

    /**
     * Creazione in memoria di una nuova entity che NON viene salvata <br>
     * Usa il @Builder di Lombok <br>
     * Eventuali regolazioni iniziali delle property <br>
     * All properties <br>
     *
     * @param nome (obbligatorio)
     *
     * @return la nuova entity appena creata (non salvata e senza keyID)
     */
    public Prova newEntity(final String nome, Continente continente) {
        Prova newEntityBean = Prova.builder()
                .nome(textService.isValid(nome) ? nome : null)
                .continenti(continente)
                .build();

        return (Prova) fixKey(newEntityBean);
    }


    public boolean isExistId(final String keyIdValue) {
        String collectionName = annotationService.getCollectionName(entityClazz);
        String keyPropertyName = annotationService.getKeyPropertyName(entityClazz);
        Query query = new Query();

        query.addCriteria(Criteria.where(FIELD_NAME_ID_CON).is(keyIdValue));
        if (textService.isValid(collectionName)) {
            return mongoService.mongoOp.exists(query, entityClazz.getClass(), collectionName);
        }
        else {
            return mongoService.mongoOp.exists(query, entityClazz.getClass());
        }
    }

    public Prova findById(final String keyID) {
        Prova entity;
        String collectionName = annotationService.getCollectionName(entityClazz);

        if (textService.isValid(collectionName)) {
            entity = (Prova) mongoService.mongoOp.findById(keyID, entityClazz, collectionName);
        }
        else {
            entity = (Prova) mongoService.mongoOp.findById(keyID, entityClazz);
        }

        return entity;
    }

    public Prova findByKeyCode(final String keyCodeValue) {
        Prova entity;
        String collectionName = annotationService.getCollectionName(entityClazz);
        String keyPropertyName = annotationService.getKeyPropertyName(entityClazz);
        Query query = new Query();
        query.addCriteria(Criteria.where(keyPropertyName).is(keyCodeValue));

        if (textService.isValid(collectionName)) {
            entity = (Prova) mongoService.mongoOp.findOne(query, entityClazz, collectionName);
        }
        else {
            entity = (Prova) mongoService.mongoOp.findOne(query, entityClazz);
        }

        return entity;
    }


    public Prova save(Prova entity) {
        String collectionName = annotationService.getCollectionName(entityClazz);
        Query query = new Query();

        query.addCriteria(Criteria.where(FIELD_NAME_ID_CON).is(entity.id));
        FindAndReplaceOptions options = new FindAndReplaceOptions();
        options.returnNew();
        if (textService.isValid(collectionName)) {
            return mongoService.mongoOp.findAndReplace(query, entity, options, collectionName);
        }
        else {
            return mongoService.mongoOp.findAndReplace(query, entity, options);
        }
    }

    public boolean delete(AEntity entity) {
        String collectionName = annotationService.getCollectionName(entityClazz);
        Query query = new Query();

        query.addCriteria(Criteria.where(FIELD_NAME_ID_CON).is(entity.id));
        if (textService.isValid(collectionName)) {
            mongoService.mongoOp.findAndRemove(query, entity.getClass(), collectionName);
        }
        else {
            mongoService.mongoOp.findAndRemove(query, entity.getClass());
        }
        return true;
    }

}// end of crud backend class
