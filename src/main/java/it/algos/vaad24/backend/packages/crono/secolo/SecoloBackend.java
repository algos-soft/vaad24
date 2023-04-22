package it.algos.vaad24.backend.packages.crono.secolo;

import static it.algos.vaad24.backend.boot.VaadCost.*;
import it.algos.vaad24.backend.entity.*;
import it.algos.vaad24.backend.exception.*;
import it.algos.vaad24.backend.logic.*;
import it.algos.vaad24.backend.wrapper.*;
import org.springframework.data.domain.*;
import org.springframework.data.mongodb.core.query.*;
import org.springframework.stereotype.*;

import java.util.*;

/**
 * Project vaadin23
 * Created by Algos
 * User: gac
 * Date: dom, 01-mag-2022
 * Time: 21:24
 */
@Service
public class SecoloBackend extends CrudBackend {


    public SecoloBackend() {
        super(Secolo.class);
    }

    @Override
    protected void fixPreferenze() {
        this.sortOrder = Sort.by(Sort.Direction.DESC, FIELD_NAME_ORDINE);
    }

    @Override
    public Secolo newEntity() {
        return newEntity(0, VUOTA, 0, 0, false);
    }


    @Override
    public Secolo newEntity(String nome) {
        return newEntity(0, nome, 0, 0, false);
    }

    /**
     * Creazione in memoria di una nuova entity che NON viene salvata <br>
     *
     * @param ordine     di presentazione nel popup/combobox (obbligatorio, unico)
     * @param nome       descrittivo e visualizzabile
     * @param inizio     primo anno del secolo
     * @param fine       ultimo anno del secolo
     * @param anteCristo secolo prima o dopo Cristo
     *
     * @return la nuova entity appena creata (con keyID ma non salvata)
     */
    public Secolo newEntity(final int ordine, final String nome, final int inizio, final int fine, final boolean anteCristo) {
        Secolo newEntityBean = Secolo.builder()
                .ordine(ordine)
                .nome(textService.isValid(nome) ? nome : null)
                .inizio(inizio)
                .fine(fine)
                .anteCristo(anteCristo)
                .build();

        return (Secolo) super.fixKey(newEntityBean);
    }


    @Override
    public Secolo findById(final String keyID) {
        return (Secolo) super.findById(keyID);
    }

    @Override
    public Secolo findByKey(final String keyValue) {
        return (Secolo) super.findByKey(keyValue);
    }

    @Override
    public Secolo findByOrder(final int ordine) {
        return (Secolo) super.findByOrder(ordine);
    }

    @Override
    public Secolo findByProperty(final String propertyName, final Object propertyValue) {
        return (Secolo) super.findByProperty(propertyName, propertyValue);
    }

    @Override
    public List<Secolo> findAllNoSort() {
        return (List<Secolo>) super.findAllNoSort();
    }

    @Override
    public List<Secolo> findAllSortCorrente() {
        return (List<Secolo>) super.findAllSortCorrente();
    }

    @Override
    public List<Secolo> findAllSortCorrenteReverse() {
        return (List<Secolo>) super.findAllSortCorrenteReverse();
    }

    @Override
    public List<Secolo> findAllSort(Sort sort) {
        return (List<Secolo>) super.findAllSort(sort);
    }

    @Override
    public List<Secolo> findAllSortKey() {
        return (List<Secolo>) super.findAllSortKey();
    }

    @Override
    public List<Secolo> findAllSortOrder() {
        return (List<Secolo>) super.findAllSortOrder();
    }

    @Override
    public List<Secolo> findAllByProperty(final String propertyName, final Object propertyValue) {
        return (List<Secolo>) super.findAllByProperty(propertyName, propertyValue);
    }

    /**
     * Seleziona un secolo dato l'anno <br>
     * SOLO per secoli AC <br>
     *
     * @param anno indicato per la selezione del secolo
     *
     * @return secolo Ante Cristo selezionato
     */
    public Secolo findByAnnoAC(final int anno) {
        return findByAnno(anno, true);
    }


    /**
     * Seleziona un secolo dato l'anno <br>
     * SOLO per secoli DC <br>
     *
     * @param anno indicato per la selezione del secolo
     *
     * @return secolo Dopo Cristo selezionato
     */
    public Secolo findByAnnoDC(final int anno) {
        return findByAnno(anno, false);
    }


    private Secolo findByAnno(final int anno, final boolean anteCristo) {
        Secolo entity;
        String collectionName = annotationService.getCollectionName(entityClazz);
        Query query = new Query();

        if (anteCristo) {
            query.addCriteria(Criteria.where("inizio").gte(anno));
            query.addCriteria(Criteria.where("fine").lte(anno));
            query.addCriteria(Criteria.where("anteCristo").is(anteCristo));
        }
        else {
            query.addCriteria(Criteria.where("inizio").lte(anno));
            query.addCriteria(Criteria.where("fine").gte(anno));
            query.addCriteria(Criteria.where("anteCristo").is(anteCristo));
        }

        if (textService.isValid(collectionName)) {
            entity = (Secolo) mongoService.mongoOp.findOne(query, entityClazz, collectionName);
        }
        else {
            entity = (Secolo) mongoService.mongoOp.findOne(query, entityClazz);
        }

        return entity;
    }


    @Override
    public Secolo save(AEntity entity) {
        return (Secolo) super.save(entity);
    }

    @Override
    public Secolo insert(AEntity entity) {
        return (Secolo) super.insert(entity);
    }

    @Override
    public Secolo update(AEntity entity) {
        return (Secolo) super.update(entity);
    }

    @Override
    public AResult resetDownload() {
        AResult result = super.resetDownload();
        String collectionName = annotationService.getCollectionName(entityClazz);
        String clazzName = entityClazz.getSimpleName();
        AEntity entityBean;
        String nomeFile = "secoli";
        Map<String, List<String>> mappa;
        List<String> riga;
        List<AEntity> lista;
        int ordine;
        String nome;
        int inizio;
        int fine;
        boolean anteCristo = false;
        String anteCristoText;
        String message;

        mappa = resourceService.leggeMappa(nomeFile);
        if (mappa != null) {
            result.setValido(true);
            lista = new ArrayList<>();

            for (String key : mappa.keySet()) {
                riga = mappa.get(key);
                if (riga.size() == 5) {
                    try {
                        ordine = Integer.decode(riga.get(0));
                    } catch (Exception unErrore) {
                        logService.error(new WrapLog().exception(unErrore).usaDb());
                        ordine = 0;
                    }
                    nome = riga.get(1);
                    try {
                        inizio = Integer.decode(riga.get(2));
                    } catch (Exception unErrore) {
                        logService.error(new WrapLog().exception(unErrore).usaDb());
                        inizio = 0;
                    }
                    try {
                        fine = Integer.decode(riga.get(3));
                    } catch (Exception unErrore) {
                        logService.error(new WrapLog().exception(unErrore).usaDb());
                        fine = 0;
                    }
                    anteCristoText = riga.get(4);
                    anteCristo = anteCristoText.equals("true") || anteCristoText.equals("vero") || anteCristoText.equals("si");
                }
                else {
                    logService.error(new WrapLog().exception(new AlgosException("I dati non sono congruenti")).usaDb());
                    return result;
                }
                nome += anteCristo ? " secolo a.C." : " secolo";

                entityBean = insert(newEntity(ordine, nome, inizio, fine, anteCristo));
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
            logService.error(new WrapLog().exception(new AlgosException("Non ho trovato il file sul server")).usaDb());
            return result.fine();
        }
    }


}// end of crud backend class
