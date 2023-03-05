package it.algos.vaad24.backend.packages.crono.giorno;

import com.mongodb.*;
import static it.algos.vaad24.backend.boot.VaadCost.*;
import it.algos.vaad24.backend.entity.*;
import it.algos.vaad24.backend.enumeration.*;
import it.algos.vaad24.backend.exception.*;
import it.algos.vaad24.backend.logic.*;
import it.algos.vaad24.backend.packages.crono.mese.*;
import it.algos.vaad24.backend.wrapper.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;

import java.util.*;
import java.util.stream.*;

/**
 * Project vaadin23
 * Created by Algos
 * User: gac
 * Date: lun, 02-mag-2022
 * Time: 08:26
 */
@Service
public class GiornoBackend extends CrudBackend {


    @Autowired
    public MeseBackend meseBackend;

    public GiornoBackend() {
        super(Giorno.class);
    }


    /**
     * Creazione in memoria di una nuova entity che NON viene salvata <br>
     *
     * @return la nuova entity appena creata (non salvata)
     */
    @Override
    public Giorno newEntity() {
        return newEntity(0, VUOTA, null, 0, 0);
    }

    /**
     * Creazione in memoria di una nuova entity che NON viene salvata <br>
     *
     * @return la nuova entity appena creata (non salvata e senza keyID)
     */
    @Override
    public Giorno newEntity(final String keyPropertyValue) {
        return newEntity(0, keyPropertyValue, null, 0, 0);
    }

    /**
     * Creazione in memoria di una nuova entity che NON viene salvata <br>
     * Usa il @Builder di Lombok <br>
     * Eventuali regolazioni iniziali delle property <br>
     * All properties <br>
     *
     * @param ordine    di presentazione nel popup/combobox (obbligatorio, unico)
     * @param nome      corrente
     * @param mese      di appartenenza
     * @param trascorsi di inizio anno
     * @param mancanti  alla fine dell'anno
     *
     * @return la nuova entity appena creata (con keyID ma non salvata)
     */
    public Giorno newEntity(final int ordine, final String nome, final Mese mese, final int trascorsi, final int mancanti) {
        Giorno newEntityBean = Giorno.builder()
                .mese(mese)
                .trascorsi(trascorsi)
                .mancanti(mancanti)
                .build();

        super.fixOrdine(newEntityBean, ordine, nome);
        return (Giorno) super.fixKey(newEntityBean);
    }

    @Override
    public Giorno findById(final String keyID) {
        return (Giorno) super.findById(keyID);
    }

    @Override
    public Giorno findByKey(final String keyValue) {
        return (Giorno) super.findByKey(keyValue);
    }

    @Override
    public Giorno findByProperty(final String propertyName, final Object propertyValue) {
        return (Giorno) super.findByProperty(propertyName, propertyValue);
    }

    public Giorno findByOrdine(final int ordine) {
        return (Giorno)super.findByOrdine(ordine);
    }

    @Override
    public List<Giorno> findAllNoSort() {
        return (List<Giorno>)super.findAllNoSort();
    }

    @Override
    public List<Giorno> findAllSortCorrente() {
        return (List<Giorno>) super.findAllSortCorrente();
    }

    public List<Giorno> findAllByMese(Mese mese) {
        return super.findAllByProperty(FIELD_NAME_MESE, mese);
    }

    @Override
    public List<String> findAllForKey() {
        return mongoService.projectionString(entityClazz, FIELD_NAME_NOME, new BasicDBObject(FIELD_NAME_ORDINE, 1));
    }

    @Override
    public List<String> findAllForKeyReverseOrder() {
        return mongoService.projectionString(entityClazz, FIELD_NAME_NOME, new BasicDBObject(FIELD_NAME_ORDINE, -1));
    }

    public List<String> findAllForNome() {
        return findAllForKey();
    }


    public List<String> findAllForNomeByMese(Mese mese) {
        return findAllByMese(mese).stream().map(giorno -> giorno.nome).collect(Collectors.toList());
    }

    @Override
    public Giorno save(AEntity entity) {
        return (Giorno) super.save(entity);
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
        int ordine;
        List<HashMap> mappa;
        String nome;
        String meseTxt;
        Mese mese;
        int trascorsi;
        int mancanti;
        int tot = 365;
        String message;
        AEntity entityBean;
        List<AEntity> lista;

        if (meseBackend.count() < 1) {
            logger.error(new WrapLog().exception(new AlgosException("Manca la collezione 'Mese'")).usaDb());
            return result.fine();
        }

        if (result.getTypeResult() == AETypeResult.collectionVuota) {
            //costruisce i 366 records
            mappa = dateService.getAllGiorni();
            result.setValido(true);
            lista = new ArrayList<>();

            for (HashMap mappaGiorno : mappa) {
                nome = (String) mappaGiorno.get(KEY_MAPPA_GIORNI_TITOLO);
                meseTxt = (String) mappaGiorno.get(KEY_MAPPA_GIORNI_MESE_TESTO);
                mese = meseBackend.findByKey(meseTxt);
                if (mese == null) {
                    message = String.format("Manca il mese di %s", meseTxt);
                    logger.error(new WrapLog().exception(new AlgosException(message)).usaDb());
                }

                ordine = (int) mappaGiorno.get(KEY_MAPPA_GIORNI_BISESTILE);
                trascorsi = (int) mappaGiorno.get(KEY_MAPPA_GIORNI_NORMALE);
                mancanti = tot - trascorsi;

                entityBean = insert(newEntity(ordine, nome, mese, trascorsi, mancanti));
                if (entityBean != null) {
                    lista.add(entityBean);
                }
                else {
                    logger.error(new WrapLog().exception(new AlgosException(String.format("La entity %s non è stata salvata", nome))));
                    result.setValido(false);
                }
            }

            return super.fixResult(result, clazzName, collectionName, lista);
        }
        else {
            return result.fine();
        }
    }

}// end of crud backend class
