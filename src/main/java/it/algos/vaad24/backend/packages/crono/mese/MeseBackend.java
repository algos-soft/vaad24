package it.algos.vaad24.backend.packages.crono.mese;

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
 * Date: dom, 01-mag-2022
 * Time: 08:51
 */
@Service
public class MeseBackend extends CrudBackend {


    public MeseBackend() {
        super(Mese.class);
    }


    /**
     * Creazione in memoria di una nuova entity che NON viene salvata <br>
     *
     * @return la nuova entity appena creata (non salvata)
     */
    @Override
    public Mese newEntity() {
        return newEntity(0, VUOTA, VUOTA, 0, 0, 0);
    }


    /**
     * Creazione in memoria di una nuova entity che NON viene salvata <br>
     *
     * @return la nuova entity appena creata (non salvata)
     */
    @Override
    public Mese newEntity(final String keyPropertyValue) {
        return newEntity(0, keyPropertyValue, VUOTA, 0, 0, 0);
    }

    /**
     * Creazione in memoria di una nuova entity che NON viene salvata <br>
     * Usa il @Builder di Lombok <br>
     * Eventuali regolazioni iniziali delle property <br>
     * All properties <br>
     *
     * @param ordine (obbligatorio, unico)
     * @param nome   (obbligatorio, unico)
     * @param breve  (obbligatorio, unico)
     * @param giorni (obbligatorio)
     * @param primo  giorno dell'anno (facoltativo)
     * @param ultimo giorno dell'anno (facoltativo)
     *
     * @return la nuova entity appena creata (con keyID ma non salvata)
     */
    public Mese newEntity(int ordine, String nome, String breve, int giorni, int primo, int ultimo) {
        Mese newEntityBean = Mese.builder()
                .ordine(ordine)
                .nome(textService.isValid(nome) ? nome : null)
                .breve(textService.isValid(breve) ? breve : null)
                .giorni(giorni)
                .primo(primo)
                .ultimo(ultimo)
                .build();

        return (Mese) super.fixKey(newEntityBean);
    }

    @Override
    public Mese findById(final String keyID) {
        return (Mese) super.findById(keyID);
    }

    @Override
    public Mese findByKey(final String keyValue) {
        return (Mese) super.findByKey(keyValue);
    }

    @Override
    public Mese findByOrder(final int ordine) {
        return (Mese) super.findByOrder(ordine);
    }

    @Override
    public Mese findByProperty(final String propertyName, final Object propertyValue) {
        return (Mese) super.findByProperty(propertyName, propertyValue);
    }


    @Override
    public List<Mese> findAllNoSort() {
        return (List<Mese>) super.findAllNoSort();
    }

    @Override
    public List<Mese> findAllSortCorrente() {
        return (List<Mese>) super.findAllSortCorrente();
    }

    @Override
    public List<Mese> findAllSortCorrenteReverse() {
        return (List<Mese>) super.findAllSortCorrenteReverse();
    }

    @Override
    public List<Mese> findAllSortOrder() {
        return (List<Mese>) super.findAllSortOrder();
    }

    @Override
    public List<Mese> findAllSortKey() {
        return (List<Mese>) super.findAllSortKey();
    }

    @Override
    public List<Mese> findAllByProperty(final String propertyName, final Object propertyValue) {
        return (List<Mese>) super.findAllByProperty(propertyName, propertyValue);
    }

    @Override
    public Mese save(AEntity entity) {
        return (Mese) super.save(entity);
    }

    @Override
    public Mese insert(AEntity entity) {
        return (Mese) super.insert(entity);
    }

    @Override
    public Mese update(AEntity entity) {
        return (Mese) super.update(entity);
    }

    @Override
    public AResult resetDownload() {
        AResult result = super.resetDownload();
        String collectionName = annotationService.getCollectionName(entityClazz);
        String clazzName = entityClazz.getSimpleName();
        AEntity entityBean;
        String nomeFileCSVSulServerAlgos = "mesi";
        Map<String, List<String>> mappa;
        List<String> riga;
        int giorni;
        String breve;
        String nome;
        List<AEntity> lista;
        String message;
        int ordine = 0;
        int primo = 0;
        int ultimo = 0;

        mappa = resourceService.leggeMappa(nomeFileCSVSulServerAlgos);
        if (mappa != null) {
            result.setValido(true);
            lista = new ArrayList<>();

            for (String key : mappa.keySet()) {
                riga = mappa.get(key);
                if (riga.size() >= 3) {
                    try {
                        giorni = Integer.decode(riga.get(0));
                    } catch (Exception unErrore) {
                        logService.error(new WrapLog().exception(unErrore).usaDb());
                        giorni = 0;
                    }
                    breve = riga.get(1);
                    nome = riga.get(2);
                }
                else {
                    logService.error(new WrapLog().exception(new AlgosException("I dati non sono congruenti")).usaDb());
                    return null;
                }
                if (riga.size() >= 4) {
                    primo = Integer.decode(riga.get(3));
                }
                if (riga.size() >= 5) {
                    ultimo = Integer.decode(riga.get(4));
                }

                if (giorni > 0 && primo > 0 && ultimo > 0) {
                    if (giorni != (ultimo - primo + 1)) {
                        message = String.format("Il numero di 'giorni' da 'primo' a 'ultimo' non coincidono per il mese di %s", nome);
                        logService.error(new WrapLog().exception(new AlgosException(message)));
                        return null;
                    }
                }

                entityBean = insert(newEntity(++ordine, nome, breve, giorni, primo, ultimo));
                if (entityBean != null) {
                    lista.add(entityBean);
                }
                else {
                    logService.error(new WrapLog().exception(new AlgosException(String.format("La entity %s non è stata salvata", nome))));
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
