package it.algos.vaad24.backend.packages.crono.anno;

import static it.algos.vaad24.backend.boot.VaadCost.*;
import it.algos.vaad24.backend.entity.*;
import it.algos.vaad24.backend.exception.*;
import it.algos.vaad24.backend.logic.*;
import it.algos.vaad24.backend.packages.crono.secolo.*;
import it.algos.vaad24.backend.wrapper.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.data.domain.*;
import org.springframework.stereotype.*;

import java.util.*;
import java.util.stream.*;

/**
 * Project vaadin23
 * Created by Algos
 * User: gac
 * Date: lun, 02-mag-2022
 * Time: 16:05
 */
@Service
public class AnnoBackend extends CrudBackend {

    @Autowired
    public SecoloBackend secoloBackend;


    public AnnoBackend() {
        super(Anno.class);
    }


    /**
     * Creazione in memoria di una nuova entity che NON viene salvata <br>
     *
     * @return la nuova entity appena creata (non salvata)
     */
    @Override
    public Anno newEntity() {
        return newEntity(0, VUOTA, null, false, false);
    }

    /**
     * Creazione in memoria di una nuova entity che NON viene salvata <br>
     *
     * @return la nuova entity appena creata (non salvata e senza keyID)
     */
    @Override
    public Anno newEntity(final String keyPropertyValue) {
        return newEntity(0, keyPropertyValue, null, false, false);
    }

    /**
     * Creazione in memoria di una nuova entity che NON viene salvata <br>
     * Usa il @Builder di Lombok <br>
     * Eventuali regolazioni iniziali delle property <br>
     * All properties <br>
     *
     * @param ordine     di presentazione nel popup/combobox (obbligatorio, unico)
     * @param nome       corrente
     * @param secolo     di appartenenza
     * @param dopoCristo flag per gli anni prima/dopo cristo
     * @param bisestile  flag per gli anni bisestili
     *
     * @return la nuova entity appena creata (con keyID ma non salvata)
     */
    public Anno newEntity(final int ordine, final String nome, final Secolo secolo, final boolean dopoCristo, final boolean bisestile) {
        Anno newEntityBean = Anno.builderAnno()
                .ordine(ordine)
                .nome(textService.isValid(nome) ? nome : null)
                .secolo(secolo)
                .dopoCristo(dopoCristo)
                .bisestile(bisestile)
                .build();

        return (Anno) super.fixKey(newEntityBean);
    }

    @Override
    public Anno findById(final String keyID) {
        return (Anno) super.findById(keyID);
    }

    @Override
    public Anno findByKey(final String keyValue) {
        return (Anno) super.findByKey(keyValue);
    }

    @Override
    public Anno findByOrder(final int ordine) {
        return (Anno) super.findByOrder(ordine);
    }

    @Override
    public Anno findByProperty(final String propertyName, final Object propertyValue) {
        return (Anno) super.findByProperty(propertyName, propertyValue);
    }


    @Override
    public List<Anno> findAllNoSort() {
        return (List<Anno>) super.findAllNoSort();
    }

    @Override
    public List<Anno> findAllSortCorrente() {
        return (List<Anno>) super.findAllSortCorrente();
    }

    @Override
    public List<Anno> findAllSortCorrenteReverse() {
        return (List<Anno>) super.findAllSortCorrenteReverse();
    }

    @Override
    public List<Anno> findAllSort(Sort sort) {
        return (List<Anno>) super.findAllSort(sort);
    }
    @Override
    public List<Anno> findAllSortKey() {
        return (List<Anno>) super.findAllSortKey();
    }

    @Override
    public List<Anno> findAllSortOrder() {
        return (List<Anno>) super.findAllSortOrder();
    }
    @Override
    public List<Anno> findAllByProperty(final String propertyName, final Object propertyValue) {
        return (List<Anno>) super.findAllByProperty(propertyName, propertyValue);
    }

    public List<Anno> findAllBySecolo(Secolo secolo) {
        return findAllByProperty(FIELD_NAME_SECOLO, secolo);
    }


    public List<String> findAllForNomeBySecolo(Secolo secolo) {
        return findAllBySecolo(secolo).stream().map(anno -> anno.nome).collect(Collectors.toList());
    }


    @Override
    public Anno save(AEntity entity) {
        return (Anno) super.save(entity);
    }

    @Override
    public Anno insert(AEntity entity) {
        return (Anno) super.insert(entity);
    }

    @Override
    public Anno update(AEntity entity) {
        return (Anno) super.update(entity);
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
        List<AEntity> lista = new ArrayList<>(); ;
        String message;

        if (secoloBackend.count() < 1) {
            AResult resultMese = secoloBackend.resetOnlyEmpty();
            if (resultMese.isErrato()) {
                logService.error(new WrapLog().exception(new AlgosException("Manca la collezione 'Secolo'")).usaDb());
                return result.fine();
            }
        }

        result.setValido(true);
        //--costruisce gli anni prima di cristo partendo da ANTE_CRISTO_MAX che coincide con DELTA_ANNI
        for (int k = 1; k <= ANTE_CRISTO_MAX; k++) {
            entityBean = creaPrima(k);
            if (entityBean != null) {
                lista.add(entityBean);
            }
            else {
                logService.error(new WrapLog().exception(new AlgosException(String.format("La entity %s non è stata salvata", k))));
            }
        }

        //--costruisce gli anni dopo cristo fino all'anno DOPO_CRISTO_MAX
        for (int k = 1; k <= DOPO_CRISTO_MAX; k++) {
            entityBean = creaDopo(k);
            if (entityBean != null) {
                lista.add(entityBean);
            }
            else {
                logService.error(new WrapLog().exception(new AlgosException(String.format("La entity %s non è stata salvata", k))));
                result.setValido(false);
            }
        }

        return super.fixResult(result, lista);
    }


    public AEntity creaPrima(int numeroProgressivo) {
        int delta = DELTA_ANNI;
        int numeroAnno = delta - numeroProgressivo + 1;
        int ordine = numeroProgressivo;
        String tagPrima = " a.C.";
        String nomeVisibile = numeroAnno + tagPrima;
        Secolo secolo = secoloBackend.findByAnnoAC(numeroAnno);

        return insert(newEntity(ordine, nomeVisibile, secolo, false, false));

    }

    public AEntity creaDopo(int numeroProgressivo) {
        int delta = DELTA_ANNI;
        int numeroAnno = numeroProgressivo;
        int ordine = numeroProgressivo + delta;
        String nomeVisibile = numeroProgressivo + VUOTA;
        Secolo secolo = secoloBackend.findByAnnoDC(numeroAnno);
        boolean bisestile = dateService.isBisestile(numeroAnno);

        return insert(newEntity(ordine, nomeVisibile, secolo, true, bisestile));
    }

}// end of crud backend class
