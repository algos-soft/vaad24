package it.algos.vaad24.backend.packages.geografia.continente;

import static it.algos.vaad24.backend.boot.VaadCost.*;
import it.algos.vaad24.backend.entity.*;
import it.algos.vaad24.backend.exception.*;
import it.algos.vaad24.backend.logic.*;
import it.algos.vaad24.backend.wrapper.*;
import org.springframework.stereotype.*;

import java.util.*;

/**
 * Project vaadin23
 * Created by Algos
 * User: gac
 * Date: dom, 03-apr-2022
 * Time: 08:39
 */
@Service
public class ContinenteBackend extends CrudBackend {


    /**
     * Costruttore @Autowired (facoltativo) @Qualifier (obbligatorio) <br>
     * In the newest Spring release, it’s constructor does not need to be annotated with @Autowired annotation <br>
     * Si usa un @Qualifier(), per specificare la classe che incrementa l'interfaccia repository <br>
     * Si usa una costante statica, per essere sicuri di scriverla uguale a quella di xxxRepository <br>
     * Regola la classe di persistenza dei dati specifica e la passa al costruttore della superclasse <br>
     * Regola la entityClazz (final nella superclasse) associata a questo service <br>
     */
    public ContinenteBackend() {
        super(Continente.class);
    }


    /**
     * Creazione in memoria di una nuova entity che NON viene salvata <br>
     *
     * @return la nuova entity appena creata (non salvata)
     */
    public Continente newEntity() {
        return newEntity(0, VUOTA, true);
    }

    @Override
    public Continente newEntity(final String keyPropertyValue) {
        return newEntity(0, keyPropertyValue, true);
    }

    /**
     * Creazione in memoria di una nuova entity che NON viene salvata <br>
     * Usa il @Builder di Lombok <br>
     * Eventuali regolazioni iniziali delle property <br>
     * All properties <br>
     *
     * @param ordine  di presentazione nel popup/combobox (obbligatorio, unico)
     * @param nome    (obbligatorio, unico)
     * @param abitato (obbligatorio)
     *
     * @return la nuova entity appena creata (con keyID ma non salvata)
     */
    public Continente newEntity(final int ordine, final String nome, final boolean abitato) {
        Continente newEntityBean = Continente.builder()
                .ordine(ordine)
                .nome(textService.isValid(nome) ? nome : null)
                .abitato(abitato)
                .build();

        return (Continente) super.fixKey(newEntityBean);
    }

    @Override
    public Continente findById(final String keyID) {
        return (Continente) super.findById(keyID);
    }

    @Override
    public Continente findByKey(final String keyValue) {
        return (Continente) super.findByKey(keyValue);
    }

    @Override
    public Continente findByOrder(final int ordine) {
        return (Continente) super.findByOrder(ordine);
    }

    @Override
    public Continente findByProperty(final String propertyName, final Object propertyValue) {
        return (Continente) super.findByProperty(propertyName, propertyValue);
    }


    /**
     * Creazione di alcuni dati <br>
     * Esegue SOLO se la collection NON esiste oppure esiste ma è VUOTA <br>
     * Viene invocato alla creazione del programma <br>
     * I dati possono essere presi da una Enumeration, da un file CSV locale, da un file CSV remoto o creati hardcoded <br>
     * Deve essere sovrascritto, invocando PRIMA il metodo della superclasse <br>
     */
    @Override
    public AResult resetDownload() {
        AResult result = super.resetDownload();
        String collectionName = annotationService.getCollectionName(entityClazz);
        String clazzName = entityClazz.getSimpleName();
        AEntity entityBean;
        String nomeFileConfig = "continenti";
        Map<String, List<String>> mappa;
        List<String> riga;
        int ordine;
        String nome;
        boolean abitato;
        boolean reset;
        List<AEntity> lista;
        String message;

        mappa = resourceService.leggeMappa(nomeFileConfig);
        if (mappa != null) {
            result.setValido(true);
            lista = new ArrayList<>();

            for (String key : mappa.keySet()) {
                riga = mappa.get(key);
                if (riga.size() == 4) {
                    try {
                        ordine = Integer.decode(riga.get(0));
                    } catch (Exception unErrore) {
                        logService.error(new WrapLog().exception(unErrore).usaDb());
                        ordine = 0;
                    }
                    nome = riga.get(1);
                    abitato = Boolean.valueOf(riga.get(2));
                    reset = Boolean.valueOf(riga.get(3));
                }
                else {
                    logService.error(new WrapLog().exception(new AlgosException("I dati non sono congruenti")).usaDb());
                    return result;
                }

                entityBean = insert(newEntity(ordine, nome, abitato));
                if (entityBean != null) {
                    lista.add(entityBean);
                }
                else {
                    logService.error(new WrapLog().exception(new AlgosException(String.format("La entity %s non è stata salvata", nome))).usaDb());
                    result.setValido(false);
                }
            }

            return super.fixResult(result, lista);
        }
        else {
            return result.errorMessage("Non ho trovato il file sul server").fine();
        }
    }


}// end of crud backend class
