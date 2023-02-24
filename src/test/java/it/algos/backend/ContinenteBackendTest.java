package it.algos.backend;

import it.algos.*;
import it.algos.base.*;
import static it.algos.vaad24.backend.boot.VaadCost.*;
import it.algos.vaad24.backend.packages.geografia.continente.*;
import it.algos.vaad24.backend.wrapper.*;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import org.mockito.*;
import org.springframework.boot.test.context.*;
import org.springframework.data.domain.*;

import java.util.*;

/**
 * Project vaad24
 * Created by Algos
 * User: gac
 * Date: Thu, 23-Feb-2023
 * Time: 09:58
 * Test senza repository <br>
 * <p>
 * isExistId()
 * isExistKey(), se esiste una key
 * isExistProperty()
 * findByID()
 * findByKey(), se esiste una key
 * findByProperty()
 * save()
 * insert()
 * update()
 * delete()
 * count()
 * findAllNoSort()
 * findAllSortCorrente()
 * findAllSort()
 * findAllKey()
 * resetOnlyEmpty()
 * deleteAll()
 */
@SpringBootTest(classes = {Vaad24SimpleApp.class})
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Tag("integration")
@Tag("backend")
@DisplayName("Continente Backend")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ContinenteBackendTest extends AlgosUnitTest {

    @InjectMocks
    private ContinenteBackend backend;

    private String backendName;

    private String collectionName;

    private String keyPropertyName;

    private List<Continente> listaEntityBeans;

    /**
     * Qui passa una volta sola <br>
     */
    @BeforeAll
    protected void setUpAll() {
        super.setUpAll();

        assertNotNull(backend);

        entityClazz = Continente.class;
        clazzName = entityClazz.getSimpleName();
        backendName = "Continente" + SUFFIX_BACKEND;
        collectionName = annotationService.getCollectionName(entityClazz);
        keyPropertyName = annotationService.getKeyPropertyName(entityClazz);
    }


    /**
     * Inizializzazione dei service <br>
     * Devono essere tutti 'mockati' prima di iniettare i riferimenti incrociati <br>
     * Può essere sovrascritto, invocando PRIMA il metodo della superclasse <br>
     */
    protected void initMocks() {
        super.initMocks();
    }


    /**
     * Regola tutti riferimenti incrociati <br>
     * Deve essere fatto dopo aver costruito tutte le referenze 'mockate' <br>
     * Nelle sottoclassi devono essere regolati i riferimenti dei service specifici <br>
     * Può essere sovrascritto, invocando PRIMA il metodo della superclasse <br>
     */
    protected void fixRiferimentiIncrociati() {
        super.fixRiferimentiIncrociati();

        backend.arrayService = arrayService;
        backend.dateService = dateService;
        backend.textService = textService;
        backend.resourceService = resourceService;
        backend.reflectionService = reflectionService;
        backend.mongoService = mongoService;
        backend.annotationService = annotationService;
        backend.logger = logger;
        backend.crudRepository = null;
    }


    @BeforeEach
    protected void setUpEach() {
        super.setUpEach();

        this.listaEntityBeans = null;
    }


    @Test
    @Order(1)
    @DisplayName("1 - collection")
    void collection() {
        System.out.println("1 - Esistenza della collection");

        ottenutoBooleano = backend.isExistsCollection();
        if (ottenutoBooleano) {
            message = String.format("Esiste la collection della classe [%s] e si chiama '%s'", clazzName, collectionName);
        }
        else {
            message = String.format("Non esiste la collection '%s' della classe [%s]", collectionName, clazzName);
        }
        System.out.println(message);
    }


    @Test
    @Order(2)
    @DisplayName("2 - count")
    void count2() {
        System.out.println("2 - count");

        ottenutoIntero = backend.count();
        if (ottenutoIntero > 0) {
            message = String.format("La collection '%s' della classe [%s] ha in totale %s entities nel database mongoDB", collectionName, clazzName, textService.format(ottenutoIntero));
        }
        else {
            if (reflectionService.isEsisteMetodo(backend.getClass(), METHOD_NAME_RESET_ONLY)) {
                message = String.format("La collection '%s' della classe [%s] è ancora vuota. Usa il metodo %s.%s()", collectionName, clazzName, backendName, METHOD_NAME_RESET_ONLY);
            }
            else {
                message = String.format("Nel database mongoDB la collection '%s' della classe [%s] è ancora vuota", collectionName, clazzName);
            }
        }
        System.out.println(message);
    }


    @Test
    @Order(21)
    @DisplayName("21 - findAll unsorted")
    void findAllNoSort() {
        System.out.println("21 - findAll unsorted");

        listaBeans = backend.findAllNoSort();
        assertNotNull(listaBeans);
        ottenutoIntero = listaBeans.size();
        message = String.format("La collection '%s' della classe [%s] ha in totale %s entities nel database mongoDB", collectionName, clazzName, textService.format(ottenutoIntero));
        System.out.println(message);
        printSubLista(listaBeans);
    }


    @Test
    @Order(22)
    @DisplayName("22 - findAll getSortKeyID")
    void findAllSortCorrente() {
        System.out.println("22 - findAll getSortKeyID");

        listaEntityBeans = backend.findAllSortCorrente();
        assertNotNull(listaEntityBeans);
        ottenutoIntero = listaEntityBeans.size();
        message = String.format("La collection '%s' della classe [%s] ha in totale %s entities nel database mongoDB", collectionName, clazzName, textService.format(ottenutoIntero));
        System.out.println(message);
        printSubLista(listaEntityBeans);
    }


    @Test
    @Order(23)
    @DisplayName("23 - findAll con sort specifico (discendente)")
    void findAllSort() {
        System.out.println("23 - findAll con sort specifico (discendente)");

        sort = Sort.by(Sort.Direction.DESC, keyPropertyName);
        listaBeans = backend.findAllSort(sort);
        assertNotNull(listaBeans);
        ottenutoIntero = listaBeans.size();
        message = String.format("La collection '%s' della classe [%s] ha in totale %s entities nel database mongoDB", collectionName, clazzName, textService.format(ottenutoIntero));
        System.out.println(message);
        printSubLista(listaBeans);
    }

    @Test
    @Order(31)
    @DisplayName("31 - findAllStringKey")
    void findAllStringKey() {
        System.out.println("31 - findAllStringKey");
        System.out.println(VUOTA);

        if (!annotationService.isKeyPropertyName(entityClazz)) {
            System.out.println("Il metodo usato da questo test presuppone che esista una keyProperty");

            message = String.format("Nella entityClazz [%s] la keyProperty non è prevista", clazzName);
            System.out.println(message);
            message = String.format("Devi scrivere un test alternativo oppure modificare la entityClazz [%s]", clazzName);
            System.out.println(message);
            message = String.format("Aggiungendo in testa alla classe un'annotazione tipo @AIEntity(keyPropertyName = \"nome\")");
            System.out.println(message);
            return;
        }

        listaStr = backend.findAllStringKey();
        assertNotNull(listaStr);
        ottenutoIntero = listaStr.size();
        sorgente = textService.format(ottenutoIntero);
        sorgente2 = keyPropertyName;
        message = String.format("La collection '%s' della classe [%s] ha in totale %s entities. Valori (String) del campo chiave '%s':", collectionName, clazzName, sorgente, sorgente2);
        System.out.println(message);

        print(listaStr);
    }

    @Test
    @Order(32)
    @DisplayName("32 - findAllStringKeyReverseOrder")
    void findAllStringKeyReverseOrder() {
        System.out.println("32 - findAllStringKeyReverseOrder");
        System.out.println(VUOTA);

        if (!annotationService.isKeyPropertyName(entityClazz)) {
            System.out.println("Il metodo usato da questo test presuppone che esista una keyProperty");

            message = String.format("Nella entityClazz [%s] la keyProperty non è prevista", clazzName);
            System.out.println(message);
            message = String.format("Devi scrivere un test alternativo oppure modificare la entityClazz [%s]", clazzName);
            System.out.println(message);
            message = String.format("Aggiungendo in testa alla classe un'annotazione tipo @AIEntity(keyPropertyName = \"nome\")");
            System.out.println(message);
            return;
        }

        listaStr = backend.findAllStringKeyReverseOrder();
        assertNotNull(listaStr);
        ottenutoIntero = listaStr.size();
        sorgente = textService.format(ottenutoIntero);
        sorgente2 = keyPropertyName;
        message = String.format("La collection '%s' della classe [%s] ha in totale %s entities. Valori (String) del campo chiave '%s' in ordine inverso:", collectionName, clazzName, sorgente, sorgente2);
        System.out.println(message);

        print(listaStr);
    }


    @Test
    @Order(41)
    @DisplayName("41 - newEntity con ID ma non registrata")
    void newEntity() {
        System.out.println("41 - newEntity con ID ma non registrata");
        System.out.println(VUOTA);
        boolean esiste;

        sorgente = "Topo Lino";
        previsto = "topolino";
        previsto2 = "Topo Lino";

        if (!reflectionService.isEsisteMetodoConParametri(backend.getClass(), METHOD_NAME_NEW_ENTITY, 1)) {
            message = String.format("Questo test presuppone che esista il metodo '%s' nella classe [%s] con un parametro", METHOD_NAME_NEW_ENTITY, backendName);
            System.out.println(message);
            message = String.format("Devi scrivere un test alternativo oppure modificare la classe [%s]", backendName);
            System.out.println(message);
            message = String.format("Aggiungendo il metodo '%s' con un parametro", METHOD_NAME_NEW_ENTITY);
            System.out.println(message);
            return;
        }

        entityBean = backend.newEntity(sorgente);
        assertNotNull(entityBean);
        ottenuto = entityBean.id;
        ottenuto2 = reflectionService.getPropertyValueStr(entityBean, keyPropertyName);
        if (annotationService.isKeyPropertyName(entityClazz)) {
            assertEquals(previsto, ottenuto);
            assertEquals(previsto2, ottenuto2);
        }
    }

    @Test
    @Order(42)
    @DisplayName("42 - CRUD operations")
    void crud() {
        System.out.println("42 - CRUD operations");
        System.out.println(VUOTA);

        if (!reflectionService.isEsisteMetodoConParametri(backend.getClass(), METHOD_NAME_NEW_ENTITY, 1)) {
            message = String.format("Questo test presuppone che esista il metodo '%s' nella classe [%s] con un parametro", METHOD_NAME_NEW_ENTITY, backendName);
            System.out.println(message);
            message = String.format("Devi scrivere un test alternativo oppure modificare la classe [%s]", backendName);
            System.out.println(message);
            message = String.format("Aggiungendo il metodo '%s' con un parametro", METHOD_NAME_NEW_ENTITY);
            System.out.println(message);
            return;
        }
        if (!annotationService.isKeyPropertyName(entityClazz)) {
            System.out.println("Le operazioni CRUD standard di questo test presuppongono che esista una keyProperty");

            message = String.format("Nella entityClazz [%s] la keyProperty non è prevista", clazzName);
            System.out.println(message);
            message = String.format("Devi scrivere un test alternativo oppure modificare la entityClazz [%s]", clazzName);
            System.out.println(message);
            message = String.format("Aggiungendo in testa alla classe un'annotazione tipo @AIEntity(keyPropertyName = \"nome\")");
            System.out.println(message);
            return;
        }

        String nomeOriginale = "Topo Lino";
        String keyID = "topolino";
        String nomeModificato = "Giuseppe";

        ottenutoBooleano = backend.isExistId(nomeOriginale);
        assertFalse(ottenutoBooleano);
        message = String.format("1) isExistId -> Nella collection '%s' non esiste (false) la entity [%s]", collectionName, nomeOriginale);
        System.out.println(message);

        ottenutoBooleano = backend.creaIfNotExist(nomeOriginale);
        assertTrue(ottenutoBooleano);
        message = String.format("2) creaIfNotExist -> Nella collection '%s' è stata creata (true) la entity [%s].%s che prima non esisteva", collectionName, keyID, nomeOriginale);
        System.out.println(message);

        ottenutoBooleano = backend.isExistId(keyID);
        assertTrue(ottenutoBooleano);
        message = String.format("3) isExistId -> Controllo l'esistenza (true) della entity [%s].%s tramite l'ID", keyID, nomeOriginale);
        System.out.println(message);

        System.out.println(VUOTA);

        ottenutoBooleano = backend.creaIfNotExist(nomeOriginale);
        assertFalse(ottenutoBooleano);
        message = String.format("4) creaIfNotExist -> La entity [%s].%s esisteva già e non è stata creata (false)", keyID, nomeOriginale);
        System.out.println(message);

        System.out.println(VUOTA);

        ottenutoBooleano = backend.isExistId(keyID);
        assertTrue(ottenutoBooleano);
        message = String.format("5) isExistId -> Controllo l'esistenza (true) della entity [%s].%s tramite l'ID", keyID, nomeOriginale);
        System.out.println(message);
        ottenutoBooleano = backend.isExistKey(nomeOriginale);
        message = String.format("6) isExistKey -> Esiste la entity [%s].%s individuata dal valore '%s' della keyProperty [%s]", keyID, nomeOriginale, nomeOriginale, keyPropertyName);
        assertTrue(ottenutoBooleano);
        System.out.println(message);
        ottenutoBooleano = backend.isExistKey(nomeModificato);
        assertFalse(ottenutoBooleano);
        message = String.format("7) isExistKey -> Non esiste la entity [%s].%s individuata dal valore '%s' della keyProperty [%s]", keyID, nomeModificato, nomeModificato, keyPropertyName);
        System.out.println(message);
        ottenutoBooleano = backend.isExistProperty(keyPropertyName, nomeOriginale);
        message = String.format("8) isExistProperty -> Esiste la entity [%s].%s individuata dal valore '%s' della property [%s]", keyID, nomeModificato, nomeOriginale, keyPropertyName);
        assertTrue(ottenutoBooleano);
        System.out.println(message);

        entityBean = backend.findById(keyID);
        assertNotNull(entityBean);
        message = String.format("9) findById -> Recupero la entity [%s].%s dalla keyID", keyID, nomeOriginale);
        System.out.println(message);

        entityBean = backend.findByKey(nomeOriginale);
        assertNotNull(entityBean);
        message = String.format("10) findByKey -> Recupero la entity [%s].%s dal valore '%s' della keyProperty [%s]", keyID, nomeOriginale, nomeOriginale, keyPropertyName);
        System.out.println(message);
        entityBean = backend.findByProperty(keyPropertyName, nomeOriginale);
        assertNotNull(entityBean);
        message = String.format("11) findByProperty -> Recupero la entity [%s].%s dal valore '%s' della property [%s]", keyID, nomeOriginale, nomeOriginale, keyPropertyName);
        System.out.println(message);

        System.out.println(VUOTA);

        reflectionService.setPropertyValue(entityBean, keyPropertyName, nomeModificato);
        entityBean = backend.save(entityBean);
        assertNotNull(entityBean);
        assertEquals(nomeModificato, reflectionService.getPropertyValue(entityBean, keyPropertyName));
        entityBean = backend.findById(keyID);
        assertNotNull(entityBean);
        assertEquals(nomeModificato, reflectionService.getPropertyValue(entityBean, keyPropertyName));
        message = String.format("12) save -> Modifica la entity [%s].%s in [%s].%s", keyID, nomeOriginale, keyID, nomeModificato);
        System.out.println(message);

        ottenutoBooleano = backend.isExistKey(nomeOriginale);
        message = String.format("13) isExistKey -> Non esiste la entity [%s].%s individuata dal valore '%s' della keyProperty [%s]", keyID, nomeOriginale, nomeOriginale, keyPropertyName);
        assertFalse(ottenutoBooleano);
        System.out.println(message);
        ottenutoBooleano = backend.isExistKey(nomeModificato);
        assertTrue(ottenutoBooleano);
        message = String.format("14) isExistKey -> Esiste la entity [%s].%s individuata dal valore '%s' della keyProperty [%s]", keyID, nomeModificato, nomeModificato, keyPropertyName);
        System.out.println(message);
        ottenutoBooleano = backend.isExistProperty(keyPropertyName, nomeModificato);
        message = String.format("15) isExistProperty -> Esiste la entity [%s].%s individuata dal valore '%s' della property [%s]", keyID, nomeModificato, nomeModificato, keyPropertyName);
        assertTrue(ottenutoBooleano);
        System.out.println(message);

        System.out.println(VUOTA);

        ottenutoBooleano = backend.delete(entityBean);
        assertTrue(ottenutoBooleano);
        message = String.format("16) delete -> Cancello la entity [%s].%s", keyID, nomeModificato);
        System.out.println(message);

        ottenutoBooleano = backend.isExistId(keyID);
        message = String.format("17) isExistId -> Alla fine, nella collection '%s' non esiste più la entity [%s] che è stata cancellata", collectionName, keyID);
        System.out.println(message);
    }

    @Test
    @Order(91)
    @DisplayName("91 - resetOnlyEmpty")
    void resetOnlyEmpty() {
        System.out.println("91 - resetOnlyEmpty");
        System.out.println(VUOTA);

        ottenutoRisultato = backend.resetOnlyEmpty();
        assertNotNull(ottenutoRisultato);

        if (ottenutoRisultato.isValido()) {
            System.out.println(ottenutoRisultato.getMessage());
            printRisultato(ottenutoRisultato);
        }
        else {
            logger.warn(new WrapLog().message(ottenutoRisultato.getErrorMessage()));
        }
    }


    @Test
    @Order(92)
    @DisplayName("92 - resetForcing")
    void resetForcing() {
        System.out.println("92 - resetForcing");
        System.out.println(VUOTA);

        ottenutoRisultato = backend.resetForcing();
        assertNotNull(ottenutoRisultato);
        if (ottenutoRisultato.isValido()) {
            System.out.println(ottenutoRisultato.getMessage());
            printRisultato(ottenutoRisultato);

            System.out.println(VUOTA);
            printSubLista(ottenutoRisultato.getLista());
        }
        else {
            logger.warn(new WrapLog().message(ottenutoRisultato.getErrorMessage()));
        }
    }


    /**
     * Qui passa al termine di ogni singolo test <br>
     */
    @AfterEach
    void tearDown() {
    }


    /**
     * Qui passa una volta sola, chiamato alla fine di tutti i tests <br>
     */
    @AfterAll
    void tearDownAll() {
    }

}
