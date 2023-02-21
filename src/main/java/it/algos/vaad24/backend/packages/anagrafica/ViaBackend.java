package it.algos.vaad24.backend.packages.anagrafica;

import static it.algos.vaad24.backend.boot.VaadCost.*;
import it.algos.vaad24.backend.entity.*;
import it.algos.vaad24.backend.enumeration.*;
import it.algos.vaad24.backend.exception.*;
import it.algos.vaad24.backend.logic.*;
import it.algos.vaad24.backend.wrapper.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.data.mongodb.core.*;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.*;
import org.springframework.data.mongodb.repository.*;
import org.springframework.stereotype.*;

import java.util.*;

/**
 * Project vaadin23
 * Created by Algos
 * User: gac
 * Date: Thu, 02-Jun-2022
 * Time: 08:02
 * <p>
 * Service di una entityClazz specifica e di un package <br>
 * Garantisce i metodi di collegamento per accedere al database <br>
 * Non mantiene lo stato di una istanza entityBean <br>
 * Mantiene lo stato della entityClazz <br>
 * NOT annotated with @SpringComponent (inutile, esiste già @Service) <br>
 * NOT annotated with @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON) (inutile, esiste già @Service) <br>
 */
@Service
public class ViaBackend extends CrudBackend {

    public ViaRepository repository;

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
    public ViaBackend(@Autowired @Qualifier(TAG_VIA) final MongoRepository crudRepository) {
        super(crudRepository, Via.class);
        this.repository = (ViaRepository) crudRepository;
    }

    public boolean creaIfNotExist(final String nome) {
        return checkAndSave(newEntity(nome)) != null;
    }


    public Via checkAndSave(final Via via) {
        String collectionName = annotationService.getCollectionName(entityClazz);

        if (!isExistProperty(via.nome)) {
            if (USA_REPOSITORY && crudRepository != null) { //@todo noRepository
                return (Via) repository.insert(via);
            }
            else {
                if (textService.isValid(collectionName)) {
                    return (Via) mongoService.mongoOp.insert(via, collectionName);
                }
                else {
                    return (Via) mongoService.mongoOp.insert(via);
                }
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

        if (USA_REPOSITORY && crudRepository != null) { //@todo noRepository
            return repository.findById(keyPropertyName) != null;
        }
        else {
            query.addCriteria(Criteria.where(keyPropertyName).is(keyPropertyValue));
            if (textService.isValid(collectionName)) {
                return mongoService.mongoOp.exists(query, entityClazz.getClass(), collectionName);
            }
            else {
                return mongoService.mongoOp.exists(query, entityClazz.getClass());
            }
        }
    }


    /**
     * Creazione in memoria di una nuova entity che NON viene salvata <br>
     * Usa il @Builder di Lombok <br>
     * Eventuali regolazioni iniziali delle property <br>
     *
     * @return la nuova entity appena creata (non salvata)
     */
    public Via newEntity() {
        return newEntity(VUOTA);
    }

    /**
     * Creazione in memoria di una nuova entity che NON viene salvata <br>
     * Usa il @Builder di Lombok <br>
     * Eventuali regolazioni iniziali delle property <br>
     * All properties <br>
     *
     * @param nome (obbligatorio, unico)
     *
     * @return la nuova entity appena creata (non salvata e senza keyID)
     */
    public Via newEntity(final String nome) {
        Via newEntityBean = Via.builder()
                .nome(textService.isValid(nome) ? nome : null)
                .build();

        return (Via) fixKey(newEntityBean);
    }


    public boolean isExistId(final String keyIdValue) {
        String collectionName = annotationService.getCollectionName(entityClazz);
        String keyPropertyName = annotationService.getKeyPropertyName(entityClazz);
        Query query = new Query();

        if (USA_REPOSITORY && crudRepository != null) { //@todo noRepository
            return repository.findById(keyPropertyName) != null;
        }
        else {
            query.addCriteria(Criteria.where(FIELD_NAME_ID_CON).is(keyIdValue));
            if (textService.isValid(collectionName)) {
                return mongoService.mongoOp.exists(query, entityClazz.getClass(), collectionName);
            }
            else {
                return mongoService.mongoOp.exists(query, entityClazz.getClass());
            }
        }
    }

    public Via findById(final String keyID) {
        Via entity;
        String collectionName = annotationService.getCollectionName(entityClazz);

        if (textService.isValid(collectionName)) {
            entity = (Via) mongoService.mongoOp.findById(keyID, entityClazz, collectionName);
        }
        else {
            entity = (Via) mongoService.mongoOp.findById(keyID, entityClazz);
        }

        return entity;
    }

    public Via findByKeyCode(final String keyCodeValue) {
        Via entity;
        String collectionName = annotationService.getCollectionName(entityClazz);
        String keyPropertyName = annotationService.getKeyPropertyName(entityClazz);
        Query query = new Query();
        query.addCriteria(Criteria.where(keyPropertyName).is(keyCodeValue));

        if (textService.isValid(collectionName)) {
            entity = (Via) mongoService.mongoOp.findOne(query, entityClazz, collectionName);
        }
        else {
            entity = (Via) mongoService.mongoOp.findOne(query, entityClazz);
        }

        return entity;
    }


    public Via save(Via entity) {
        String collectionName = annotationService.getCollectionName(entityClazz);
        Query query = new Query();

        if (USA_REPOSITORY && crudRepository != null) { //@todo noRepository
            try {
                return (Via) crudRepository.save(entity);
            } catch (Exception unErrore) {
                logger.error(unErrore);
            }
            return entity;
        }
        else {
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
    }

    public boolean delete(AEntity entity) {
        String collectionName = annotationService.getCollectionName(entityClazz);
        Query query = new Query();

        if (USA_REPOSITORY && crudRepository != null) { //@todo noRepository
            try {
                crudRepository.delete(entity);
            } catch (Exception unErrore) {
                logger.error(unErrore);
            }
            return false;
        }
        else {
            query.addCriteria(Criteria.where(FIELD_NAME_ID_CON).is(entity.id));
            if (textService.isValid(collectionName)) {
                mongoService.mongoOp.findAndRemove(query, entity.getClass(), collectionName);
            }
            else {
                mongoService.mongoOp.findAndRemove(query, entity.getClass());
            }
            return true;
        }
    }

    /**
     * Creazione di alcuni dati <br>
     * Esegue SOLO se la collection NON esiste oppure esiste ma è VUOTA <br>
     * Viene invocato alla creazione del programma <br>
     * I dati possono essere presi da una Enumeration, da un file CSV locale, da un file CSV remoto o creati hardcoded <br>
     * Deve essere sovrascritto, invocando PRIMA il metodo della superclasse <br>
     */
    @Override
    public AResult resetOnlyEmpty() {
        AResult result = super.resetOnlyEmpty();
        String nomeFileCSVSulServerAlgos = "vie";
        Map<String, List<String>> mappa;
        List<String> riga;
        String nome;

        if (result.isValido() && result.getTypeResult() == AETypeResult.collectionVuota) {
            mappa = resourceService.leggeMappa(nomeFileCSVSulServerAlgos);
            if (mappa != null) {
                for (String key : mappa.keySet()) {
                    riga = mappa.get(key);
                    if (riga.size() == 1) {
                        nome = riga.get(0);
                    }
                    else {
                        return result.errorMessage("I dati non sono congruenti");
                    }
                    if (!creaIfNotExist(nome)) {
                        logger.error(new WrapLog().exception(new AlgosException(String.format("La entity %s non è stata salvata", nome))).usaDb());
                    }
                }
            }
            else {
                return result.errorMessage("Non ho trovato il file sul server");
            }
        }
        else {
            return result;
        }

        return fixResult(result.typeResult(AETypeResult.collectionCreata));
    }

}// end of crud backend class
