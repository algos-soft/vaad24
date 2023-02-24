package it.algos.vaad24.backend.packages.crono.anno;

import com.mongodb.*;
import static it.algos.vaad24.backend.boot.VaadCost.*;
import it.algos.vaad24.backend.entity.*;
import it.algos.vaad24.backend.enumeration.*;
import it.algos.vaad24.backend.exception.*;
import it.algos.vaad24.backend.logic.*;
import it.algos.vaad24.backend.packages.crono.secolo.*;
import it.algos.vaad24.backend.wrapper.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.data.domain.*;
import org.springframework.data.mongodb.core.query.*;
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
     * Usa il @Builder di Lombok <br>
     * Eventuali regolazioni iniziali delle property <br>
     *
     * @return la nuova entity appena creata (non salvata)
     */
    public Anno newEntity() {
        return newEntity(0, VUOTA, null, false, false);
    }

    /**
     * Creazione in memoria di una nuova entity che NON viene salvata <br>
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
        Anno newEntityBean = Anno.builder()
                .ordine(ordine)
                .nome(textService.isValid(nome) ? nome : null)
                .secolo(secolo)
                .dopoCristo(dopoCristo)
                .bisestile(bisestile)
                .build();

        return (Anno) fixKey(newEntityBean);
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
    public Anno findByProperty(final String propertyName, final Object propertyValue) {
        return (Anno) super.findByProperty(propertyName, propertyValue);
    }

    public Anno findByOrdine(final int ordine) {
        return findByProperty(FIELD_NAME_ORDINE, ordine);
    }

    @Override
    public List<Anno> findAllSortCorrente() {
        String collectionName = annotationService.getCollectionName(entityClazz);
        Query query = new Query();

        if (sortOrder != null) {
            query.with(sortOrder);
        }
        return (List<Anno>) mongoService.mongoOp.find(query, entityClazz, collectionName);
    }

    public List<Anno> findAllDiscendente() {
        String collectionName = annotationService.getCollectionName(entityClazz);
        Sort sort = Sort.by(Sort.Direction.DESC, FIELD_NAME_ORDINE);
        Query query = new Query();
        query.with(sort);

        return (List<Anno>) mongoService.mongoOp.find(query, entityClazz, collectionName);
    }

    public List<Anno> findAllAscendente() {
        String collectionName = annotationService.getCollectionName(entityClazz);
        Sort sort = Sort.by(Sort.Direction.ASC, FIELD_NAME_ORDINE);
        Query query = new Query();
        query.with(sort);

        return (List<Anno>) mongoService.mongoOp.find(query, entityClazz, collectionName);
    }

    public List<String> findAllStringKey() {
        return mongoService.projectionString(entityClazz, FIELD_NAME_NOME, new BasicDBObject(FIELD_NAME_ORDINE, 1));
    }

    public List<String> findAllStringKeyReverseOrder() {
        return mongoService.projectionString(entityClazz, FIELD_NAME_NOME, new BasicDBObject(FIELD_NAME_ORDINE, -1));
    }

    //    public List<Anno> findAllBySecolo(Secolo secolo) {
    //        return findAllAscendente().stream()
    //                .filter(anno -> anno.secolo.nome.equals(secolo.nome))
    //                .collect(Collectors.toList());
    //    }

    public List<Anno> findAllBySecolo(Secolo secolo) {
        return findAllBeanProperty("secolo", secolo);
    }


    public List<String> findNomiBySecolo(Secolo secolo) {
        return findAllBySecolo(secolo).stream()
                .map(anno -> anno.nome)
                .collect(Collectors.toList());
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
        AEntity entityBean;
        List<AEntity> lista;
        String message;

        if (secoloBackend.count() < 1) {
            logger.error(new WrapLog().exception(new AlgosException("Manca la collezione 'Secolo'")).usaDb());
            return result;
        }

        if (result.getTypeResult() == AETypeResult.collectionVuota) {
            lista = new ArrayList<>();

            //--costruisce gli anni prima di cristo partendo da ANTE_CRISTO_MAX che coincide con DELTA_ANNI
            for (int k = 1; k <= ANTE_CRISTO_MAX; k++) {
                entityBean = creaPrima(k);
                if (entityBean != null) {
                    lista.add(entityBean);
                }
                else {
                    logger.error(new WrapLog().exception(new AlgosException(String.format("La entity %s non è stata salvata", k))));
                }
            }

            //--costruisce gli anni dopo cristo fino all'anno DOPO_CRISTO_MAX
            for (int k = 1; k <= DOPO_CRISTO_MAX; k++) {
                entityBean = creaDopo(k);
                if (entityBean != null) {
                    lista.add(entityBean);
                }
                else {
                    logger.error(new WrapLog().exception(new AlgosException(String.format("La entity %s non è stata salvata", k))));
                }
            }
            result.setIntValue(lista.size());
            result.setLista(lista);
        }
        else {
            return result;
        }

        message = String.format("La collection '%s' della classe [%s] era vuota ed è stata creata. Contiene %s elementi.", collectionName, clazzName, lista.size());
        return result.errorMessage(VUOTA).eseguito().validMessage(message).typeResult(AETypeResult.collectionCreata);
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
