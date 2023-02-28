package it.algos.vaad24simple.backend.packages.prova;

import it.algos.vaad24.backend.entity.*;
import it.algos.vaad24.backend.enumeration.*;
import it.algos.vaad24.backend.logic.*;
import it.algos.vaad24.backend.packages.geografia.continente.*;
import it.algos.vaad24.backend.wrapper.*;
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
     */
    //@todo registrare eventualmente come costante in VaadCost il valore del Qualifier
    public ProvaBackend() {
        super(null, Prova.class);
    }


    public boolean creaIfNotExist(final String nome) {
        return insert(newEntity(nome, null)) != null;
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
     * @return la nuova entity appena creata (con keyID ma non salvata)
     */
    public Prova newEntity(final String descrizione, Continente continente) {
        Prova newEntityBean = Prova.builder()
                .descrizione(textService.isValid(descrizione) ? descrizione : null)
                .continenti(continente)
                .build();

        return (Prova) fixKey(newEntityBean);
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

        if (result.getTypeResult() == AETypeResult.collectionVuota) {
            lista = new ArrayList<>();
            lista.add(insert(newEntity("Aldo", null)));
            lista.add(insert(newEntity("Giovanni", null)));
            lista.add(insert(newEntity("Giacomo", null)));

            return super.fixResult(result, clazzName, collectionName, lista);
        }
        else {
            return result.fine();
        }
    }

}// end of crud backend class
