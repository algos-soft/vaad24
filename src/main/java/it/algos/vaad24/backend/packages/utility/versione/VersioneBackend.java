package it.algos.vaad24.backend.packages.utility.versione;

import static it.algos.vaad24.backend.boot.VaadCost.*;
import it.algos.vaad24.backend.boot.*;
import it.algos.vaad24.backend.enumeration.*;
import it.algos.vaad24.backend.logic.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;

import java.time.*;
import java.util.*;

/**
 * Project vaadin23
 * Created by Algos
 * User: gac
 * Date: mer, 09-mar-2022
 * Time: 21:31
 * <p>
 * Service di una entityClazz specifica e di un package <br>
 * Garantisce i metodi di collegamento per accedere al database <br>
 * Non mantiene lo stato di una istanza entityBean <br>
 * Mantiene lo stato della entityClazz <br>
 * NOT annotated with @SpringComponent (inutile, esiste già @Service) <br>
 * NOT annotated with @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON) (inutile, esiste già @Service) <br>
 */
@Service
@Qualifier(TAG_VERSIONE)
public class VersioneBackend extends CrudBackend {



    public VersioneBackend() {
        super(Versione.class);
    }


    /**
     * Creazione in memoria di una nuova entity che NON viene salvata <br>
     * Usa il @Builder di Lombok <br>
     * Eventuali regolazioni iniziali delle property <br>
     *
     * @return la nuova entity appena creata (non salvata)
     */
    public Versione newEntity() {
        return newEntity(VUOTA, null, VUOTA, VUOTA, true);
    }


    /**
     * Creazione in memoria di una nuova entity che NON viene salvata <br>
     * Usa il @Builder di Lombok <br>
     * Eventuali regolazioni iniziali delle property <br>
     * Ordine messo in automatico (progressivo) <br>
     *
     * @param code             identificativo della versione
     * @param type             merceologico del type
     * @param descrizione      dettagliata della versione
     * @param company          se versione specifica in caso di multiCompany
     * @param riferitoVaadin23 se versione relativa al programma base vaadin23
     *
     * @return la nuova entity appena creata (con keyID ma non salvata)
     */
    public Versione newEntity(
            final String code,
            final AETypeVers type,
            final String descrizione,
            final String company,
            boolean riferitoVaadin23) {
        String tag = " del ";
        double release = riferitoVaadin23 ? VaadVar.vaadin24Version : VaadVar.projectVersion;

        Versione newEntityBean = Versione.builder()
                .ordine(nextOrdine())
                .code(textService.isValid(code) ? code : null)
                .type(type != null ? type : AETypeVers.addition)
                .release(release)
                .titolo(String.format("%s%s%s", release, tag, dateService.get()))
                .giorno(LocalDate.now())
                .descrizione(textService.isValid(descrizione) ? descrizione : null)
                .company(textService.isValid(company) ? company : null)
                .vaadin23(riferitoVaadin23)
                .build();

        return (Versione) fixKey(newEntityBean);
    }


    /**
     * Recupera una istanza della Entity usando la query della property specifica (obbligatoria e unica) <br>
     *
     * @param sigla     del progetto interessato (transient, obbligatorio, un solo carattere) <br>
     * @param newOrdine progressivo della versione (transient, obbligatorio) <br>
     *
     * @return true se trovata
     */
    public boolean isMancaByCode(final String sigla, final int newOrdine) {
        return findByCode(getIdKey(sigla, newOrdine)) == null;
    }

    /**
     * Recupera una istanza della Entity usando la query della property specifica (obbligatoria e unica) <br>
     *
     * @param keyCode (obbligatorio, unico)
     *
     * @return istanza della Entity, null se non trovata
     */
    public Versione findByCode(final String keyCode) {
        return (Versione)super.findByProperty(FIELD_NAME_CODE,keyCode);
    }

    /**
     * Ordine di presentazione (obbligatorio, unico per ogni project), <br>
     * Viene calcolato in automatico alla creazione della entity <br>
     * Recupera dal DB il valore massimo pre-esistente della property per lo specifico progetto <br>
     * Incrementa di uno il risultato <br>
     *
     * @param sigla     del progetto interessato (transient, obbligatorio, un solo carattere) <br>
     * @param newOrdine progressivo della versione (transient, obbligatorio) <br>
     */
    public String getIdKey(final String sigla, int newOrdine) {
        List<Versione> lista=null;
        String idKey = "0";

//        if (newOrdine == 0) {
////            lista = repository.findByIdRegexOrderByOrdineAsc(sigla);
//            if (lista != null && lista.size() > 0) {
//                idKey = lista.get(0).getId();
//                idKey = idKey.substring(1);
//                idKey = idKey.startsWith(PUNTO) ? textService.levaTesta(idKey, PUNTO) : idKey;
//                idKey = idKey.startsWith(PUNTO) ? textService.levaTesta(idKey, PUNTO) : idKey;//doppio per numeri sopra i 10 e fino a 100
//            }
//
//            try {
//                newOrdine = Integer.decode(idKey);
//                newOrdine++;
//            } catch (Exception unErrore) {
//                logger.error(unErrore);
//            }
//        }

        return sigla + newOrdine;
    }

}// end of crud backend class
