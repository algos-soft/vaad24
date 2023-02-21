package it.algos.vaad24simple.backend.packages.prova;

import static it.algos.vaad24.backend.boot.VaadCost.*;
import it.algos.vaad24.backend.entity.*;
import it.algos.vaad24.backend.enumeration.*;
import it.algos.vaad24.backend.logic.*;
import it.algos.vaad24.backend.packages.geografia.continente.*;
import it.algos.vaad24.backend.wrapper.*;
import org.springframework.data.mongodb.core.*;
import org.springframework.data.mongodb.core.query.*;
import org.springframework.stereotype.*;

import java.util.*;

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

        if (!isExistProperty(entityBean.descrizione)) {
            if (textService.isValid(collectionName)) {
                return mongoService.mongoOp.insert(entityBean, collectionName);
            }
            else {
                return mongoService.mongoOp.insert(entityBean);
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
    public Prova newEntity(String descrizione) {
        return newEntity(descrizione, null);
    }

    /**
     * Creazione in memoria di una nuova entity che NON viene salvata <br>
     * Usa il @Builder di Lombok <br>
     * Eventuali regolazioni iniziali delle property <br>
     * All properties <br>
     *
     * @param descrizione (obbligatorio)
     *
     * @return la nuova entity appena creata (non salvata e senza keyID)
     */
    public Prova newEntity(final String descrizione, Continente continente) {
        Prova newEntityBean = Prova.builder()
                .descrizione(textService.isValid(descrizione) ? descrizione : null)
                .continenti(continente)
                .build();

        return (Prova) fixKey(newEntityBean);
    }


    public boolean isExistId(final String keyIdValue) {
        String collectionName = annotationService.getCollectionName(entityClazz);
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


    /**
     * Creazione di alcuni dati <br>
     * Esegue SOLO se la collection NON esiste oppure esiste ma è VUOTA <br>
     * Viene invocato: <br>
     * 1) alla creazione del programma da VaadData.resetData() <br>
     * 2) dal buttonDeleteReset -> CrudView.reset() <br>
     * 3) da UtilityView.reset() <br>
     * I dati possono essere presi da una Enumeration, da un file CSV locale, da un file CSV remoto o creati hardcoded <br>
     * Deve essere sovrascritto, invocando PRIMA il metodo della superclasse <br>
     */
    @Override
    public AResult resetOnlyEmpty() {
        AResult result = super.resetOnlyEmpty();
        String clazzName = entityClazz.getSimpleName();
        String collectionName = result.getTarget();
        List<AEntity> lista;
        String message;

        if (result.getTypeResult() == AETypeResult.collectionVuota) {
            lista = new ArrayList<>();
            lista.add(checkAndSave(newEntity("Aldo")));
            lista.add(checkAndSave(newEntity("Giovanni")));
            lista.add(checkAndSave(newEntity("Giacomo")));
            result.setIntValue(lista.size());
            result.setLista(lista);
        }
        else {
            return result;
        }

        message = String.format("La collection '%s' della classe [%s] era vuota ed è stata creata. Contiene %s elementi.", collectionName, clazzName, lista.size());
        return result.errorMessage(VUOTA).eseguito().validMessage(message).typeResult(AETypeResult.collectionCreata);
    }

}// end of crud backend class
