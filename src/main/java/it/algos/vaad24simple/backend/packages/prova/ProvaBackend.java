package it.algos.vaad24simple.backend.packages.prova;

import static it.algos.vaad24.backend.boot.VaadCost.*;
import it.algos.vaad24.backend.entity.*;
import it.algos.vaad24.backend.enumeration.*;
import it.algos.vaad24.backend.exception.*;
import it.algos.vaad24.backend.logic.*;
import it.algos.vaad24.backend.packages.anagrafica.*;
import it.algos.vaad24.backend.packages.geografia.continente.*;
import it.algos.vaad24.backend.wrapper.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;

import java.util.*;

/**
 * Project vaad24
 * Created by Algos
 * User: gac
 * Date: Mon, 13-Feb-2023
 * Time: 09:40
 */
@Service
public class ProvaBackend extends CrudBackend {

    @Autowired
    public ContinenteBackend continenteBackend;

    @Autowired
    public ViaBackend viaBackend;


    public ProvaBackend() {
        super(Prova.class);
    }


    public boolean creaIfNotExist(final String descrizione) {
        return insert(newEntity(descrizione, null, null, VUOTA, null, null)) != null;
    }


    /**
     * Creazione in memoria di una nuova entity che NON viene salvata <br>
     * Usa il @Builder di Lombok <br>
     * Eventuali regolazioni iniziali delle property <br>
     *
     * @return la nuova entity appena creata (non salvata)
     */
    public Prova newEntity(String descrizione) {
        return newEntity(descrizione, null, null, VUOTA, null, null);
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
    public Prova newEntity(String descrizione, Continente continenteLinkDinamicoDBRef, Via viaLinkStatico, String typeString, AETypeVers versione, AESchedule schedule) {
        Prova newEntityBean = Prova.builder()
                .descrizione(textService.isValid(descrizione) ? descrizione : null)
                .continenteLinkDinamicoDBRef(continenteLinkDinamicoDBRef)
                .viaLinkStatico(viaLinkStatico)
                .typeString(textService.isValid(typeString) ? typeString : null)
                .versione(versione)
                .schedule(schedule)
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
        String nomeFileConfig = "prova";
        Map<String, List<String>> mappa;
        List<AEntity> lista = null;
        List<String> riga;
        AEntity entityBean;

        String descrizione = VUOTA;
        Continente continenteLinkDinamicoDBRef = null;
        Via viaLinkStatico = null;
        String typeString = VUOTA;
        AETypeVers versione = null;
        AESchedule schedule = null;

        if (result.getTypeResult() == AETypeResult.collectionVuota) {
            result.setValido(true);
            mappa = resourceService.leggeMappaConfig(nomeFileConfig);
            if (mappa != null) {
                result.setValido(true);
                lista = new ArrayList<>();

                for (String key : mappa.keySet()) {
                    riga = mappa.get(key);
                    if (riga.size() == 6) {
                        descrizione = riga.get(0);
                        continenteLinkDinamicoDBRef = continenteBackend.findById(riga.get(1));
                        viaLinkStatico = viaBackend.findById(riga.get(2));
                        typeString = riga.get(3);
                        versione = AETypeVers.valueOf(riga.get(4));
                        schedule = AESchedule.valueOf(riga.get(5));
                    }
                    entityBean = insert(newEntity(descrizione, continenteLinkDinamicoDBRef, viaLinkStatico, typeString, versione, schedule));
                    if (entityBean != null) {
                        lista.add(entityBean);
                    }
                    else {
                        logger.error(new WrapLog().exception(new AlgosException(String.format("La entity %s non è stata salvata", descrizione))).usaDb());
                        result.setValido(false);
                    }
                }
                return super.fixResult(result, clazzName, collectionName, lista);
            }
        }
        else {
            return result.fine();
        }
        return result.fine();
    }

}// end of crud backend class
