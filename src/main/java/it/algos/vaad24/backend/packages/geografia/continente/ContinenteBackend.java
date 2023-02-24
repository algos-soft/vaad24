package it.algos.vaad24.backend.packages.geografia.continente;

import static it.algos.vaad24.backend.boot.VaadCost.*;
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
 * Date: dom, 03-apr-2022
 * Time: 08:39
 * <p>
 * Service di una entityClazz specifica e di un package <br>
 * Garantisce i metodi di collegamento per accedere al database <br>
 * Non mantiene lo stato di una istanza entityBean <br>
 * Mantiene lo stato della entityClazz <br>
 * NOT annotated with @SpringComponent (inutile, esiste già @Service) <br>
 * NOT annotated with @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON) (inutile, esiste già @Service) <br>
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
     *
     */
    //@todo registrare eventualmente come costante in VaadCost il valore del Qualifier
    public ContinenteBackend() {
        super( Continente.class);
    }


    /**
     * Creazione in memoria di una nuova entity che NON viene salvata <br>
     * Usa il @Builder di Lombok <br>
     * Eventuali regolazioni iniziali delle property <br>
     *
     * @return la nuova entity appena creata (non salvata)
     */
    public Continente newEntity() {
        return newEntity(VUOTA, 0, VUOTA, true, false);
    }

    /**
     * Creazione in memoria di una nuova entity che NON viene salvata <br>
     * Usa il @Builder di Lombok <br>
     * Eventuali regolazioni iniziali delle property <br>
     * All properties <br>
     *
     * @param id      (obbligatorio, unico)
     * @param ordine  di presentazione nel popup/combobox (obbligatorio, unico)
     * @param nome    (obbligatorio, unico)
     * @param abitato (obbligatorio)
     * @param reset   (obbligatorio)
     *
     * @return la nuova entity appena creata (con keyID ma non salvata)
     */
    public Continente newEntity(final String id, final int ordine, final String nome, final boolean abitato, final boolean reset) {
        Continente continente = Continente.builder()
                .ordine(ordine)
                .nome(textService.isValid(nome) ? nome : null)
                .abitato(abitato)
                .build();
        continente.id = id;
        continente.reset = reset;
        return continente;
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
        String clazzName = entityClazz.getSimpleName();
        String collectionName = result.getTarget();
        String nomeFile = "continenti";
        Map<String, List<String>> mappa;
        List<String> riga;
        String id;
        int ordine;
        String nome;
        boolean abitato;
        boolean reset;
        List<AEntity> lista;
        AEntity entityBean;
        String message;

        if (result.getTypeResult() == AETypeResult.collectionVuota) {
            mappa = resourceService.leggeMappa(nomeFile);
            if (mappa != null) {
                lista = new ArrayList<>();

                for (String key : mappa.keySet()) {
                    riga = mappa.get(key);
                    if (riga.size() == 4) {
                        try {
                            ordine = Integer.decode(riga.get(0));
                        } catch (Exception unErrore) {
                            logger.error(new WrapLog().exception(unErrore).usaDb());
                            ordine = 0;
                        }
                        nome = riga.get(1);
                        abitato = Boolean.valueOf(riga.get(2));
                        reset = Boolean.valueOf(riga.get(3));
                        id = nome.toLowerCase();
                    }
                    else {
                        logger.error(new WrapLog().exception(new AlgosException("I dati non sono congruenti")).usaDb());
                        return result;
                    }

                    entityBean = insert(newEntity(id, ordine, nome, abitato, reset));
                    if (entityBean != null) {
                        lista.add(entityBean);
                    }
                    else {
                        logger.error(new WrapLog().exception(new AlgosException(String.format("La entity %s non è stata salvata", nome))).usaDb());
                    }
                }
                result.setIntValue(lista.size());
                result.setLista(lista);
            }
            else {
                return result;
            }
        }
        else {
            return result;
        }

        message = String.format("La collection '%s' della classe [%s] era vuota ed è stata creata. Contiene %s elementi.", collectionName, clazzName, lista.size());
        return result.errorMessage(VUOTA).eseguito().validMessage(message).typeResult(AETypeResult.collectionCreata);
    }

}// end of crud backend class
