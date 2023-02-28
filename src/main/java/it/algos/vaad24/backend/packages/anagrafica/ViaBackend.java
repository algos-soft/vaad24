package it.algos.vaad24.backend.packages.anagrafica;

import it.algos.vaad24.backend.entity.*;
import it.algos.vaad24.backend.enumeration.*;
import it.algos.vaad24.backend.exception.*;
import it.algos.vaad24.backend.logic.*;
import it.algos.vaad24.backend.wrapper.*;
import org.springframework.stereotype.*;

import java.util.*;

/**
 * Project vaadin23
 * Created by Algos
 * User: gac
 * Date: Thu, 02-Jun-2022
 * Time: 08:02
 */
@Service
public class ViaBackend extends CrudBackend {


    public ViaBackend() {
        super(Via.class);
    }


    /**
     * Creazione in memoria di una nuova entity che NON viene salvata <br>
     *
     * @param nome (obbligatorio, unico)
     *
     * @return la nuova entity appena creata (con keyID ma non salvata)
     */
    @Override
    public Via newEntity(final String nome) {
        Via newEntityBean = Via.builder()
                .nome(textService.isValid(nome) ? nome : null)
                .build();

        return (Via) fixKey(newEntityBean);
    }


    @Override
    public Via findById(final String keyID) {
        return (Via) super.findById(keyID);
    }

    @Override
    public Via findByKey(final String keyValue) {
        return (Via) super.findByKey(keyValue);
    }

    @Override
    public Via findByProperty(final String propertyName, final Object propertyValue) {
        return (Via) super.findByProperty(propertyName, propertyValue);
    }

    @Override
    public Via save(AEntity entity) {
        return (Via) super.save(entity);
    }


    @Override
    public AResult resetOnlyEmpty() {
        AResult result = super.resetOnlyEmpty();
        String clazzName = entityClazz.getSimpleName();
        String collectionName = result.getTarget();
        String nomeFileCSVSulServerAlgos = "vie";
        Map<String, List<String>> mappa;
        List<String> riga;
        String nome;
        List<AEntity> lista = null;
        AEntity entityBean = null;
        String message;

        if (result.getTypeResult() == AETypeResult.collectionVuota) {
            mappa = resourceService.leggeMappa(nomeFileCSVSulServerAlgos);
            if (mappa != null) {
                result.setValido(true);
                lista = new ArrayList<>();

                for (String key : mappa.keySet()) {
                    riga = mappa.get(key);
                    if (riga.size() == 1) {
                        nome = riga.get(0);
                    }
                    else {
                        return result.errorMessage("I dati non sono congruenti").fine();
                    }

                    entityBean = insert(newEntity(nome));
                    if (entityBean != null) {
                        lista.add(entityBean);
                    }
                    else {
                        logger.error(new WrapLog().exception(new AlgosException(String.format("La entity %s non è stata salvata", nome))).usaDb());
                        result.setValido(false);
                    }
                }
                if (lista.size() > 0) {
                    result.setIntValue(lista.size());
                    result.setLista(lista);
                }
                else {
                    result.typeResult(AETypeResult.error);
                    message = String.format("Non sono riuscito a creare la collection '%s'. Controlla il metodo [%s].resetOnlyEmpty()", collectionName, clazzName);
                    return result.errorMessage(message).fine();
                }
            }
            else {
                return result.errorMessage("Non ho trovato il file sul server").fine();
            }
        }
        else {
            return result.fine();
        }

        return super.fixResult(result, clazzName, collectionName, lista.size());
    }

}// end of crud backend class
