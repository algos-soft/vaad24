package it.algos.backend;

import it.algos.*;
import it.algos.base.*;
import static it.algos.vaad24.backend.boot.VaadCost.*;
import it.algos.vaad24.backend.packages.anagrafica.*;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import org.mockito.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.boot.test.context.*;
import org.springframework.data.domain.*;

import java.util.*;

/**
 * Project vaad24
 * Created by Algos
 * User: gac
 * Date: Tue, 13-Dec-2022
 * Time: 09:20
 * Test senza repository <br>
 * <p>
 * isExistsCollection()
 * Count()
 * FindAll()
 * FindAllSort(), se esiste un ordine
 * FindByID()
 * FindByKey(), se esiste una key
 * Reset(), se esiste resetForcing()
 * isExist()
 * DeleteAll(), se esiste resetForcing()
 * Delete()
 * New() o Insert() o Update() o Save()
 */
@SpringBootTest(classes = {Vaad24SimpleApp.class})
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Tag("integration")
@Tag("backend")
@DisplayName("Via Backend")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ViaBackendTest extends AlgosUnitTest {

    @InjectMocks
    private ViaBackend backend;

    @Autowired
    private ViaRepository repository;

    private Via entityBean;

    private List<Via> listaBeans;

    private String backendName = "ViaBackend";

    private Class entityClazz;

    private String collectionName;

    private String keyPropertyName;

    /**
     * Qui passa una volta sola <br>
     */
    @BeforeAll
    protected void setUpAll() {
        super.setUpAll();

        Assertions.assertNotNull(backend);

        backend.repository = repository;
        backend.crudRepository = repository;
        backend.arrayService = arrayService;
        backend.dateService = dateService;
        backend.textService = textService;
        backend.resourceService = resourceService;
        backend.reflectionService = reflectionService;
        backend.mongoService = mongoService;
        backend.annotationService = annotationService;
        backend.logger = logger;
        backend.repository = null;
        backend.crudRepository = null;

        entityClazz = Via.class;
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
    }


    @BeforeEach
    protected void setUpEach() {
        super.setUpEach();

        this.entityBean = null;
        this.listaBeans = null;
    }


    @Test
    @Order(1)
    @DisplayName("1 - collection")
    void collection() {
        System.out.println("1 - Esistenza della collection");
        System.out.println(VUOTA);

        ottenutoBooleano = backend.isExistsCollection();
        if (ottenutoBooleano) {
            message = String.format("Esiste la collection della classe '%s' e si chiama '%s'", backend.entityClazz.getSimpleName(), collectionName);
        }
        else {
            message = String.format("Non esiste la collection della classe '%s'", backend.entityClazz.getSimpleName());
        }
        System.out.println(message);
    }


    @Test
    @Order(2)
    @DisplayName("2 - count")
    void count2() {
        System.out.println("2 - count");
        System.out.println(VUOTA);

        ottenutoIntero = backend.count();
        if (ottenutoIntero > 0) {
            message = String.format("La collection '%s' ha in totale %s entities nel database mongoDB", collectionName, textService.format(ottenutoIntero));
        }
        else {
            if (reflectionService.isEsisteMetodo(backend.getClass(), TAG_RESET_ONLY)) {
                message = String.format("La collection '%s' è ancora vuota. Usa il metodo %s.%s()", collectionName, backendName, TAG_RESET_ONLY);
            }
            else {
                message = String.format("Nel database mongoDB la collection '%s' è ancora vuota", collectionName);
            }
        }
        System.out.println(message);
    }


    @Test
    @Order(20)
    @DisplayName("20 - findAll unsorted")
    void findAllNoSort() {
        System.out.println("20 - findAll unsorted");

        listaBeans = backend.findAllNoSort();
        assertNotNull(listaBeans);
        ottenutoIntero = listaBeans.size();
        message = String.format("La collection '%s' ha in totale %s entities nel database mongoDB", collectionName, textService.format(ottenutoIntero));
        System.out.println(message);
        printSubLista(listaBeans);
    }


    @Test
    @Order(21)
    @DisplayName("21 - findAll getSortKeyID")
    void findAllSortCorrente() {
        System.out.println("21 - findAll getSortKeyID");

        listaBeans = backend.findAllSortCorrente();
        assertNotNull(listaBeans);
        ottenutoIntero = listaBeans.size();
        message = String.format("La collection '%s' ha in totale %s entities nel database mongoDB", collectionName, textService.format(ottenutoIntero));
        System.out.println(message);
        printSubLista(listaBeans);
    }


    @Test
    @Order(22)
    @DisplayName("22 - findAll con sort specifico (discendente)")
    void findAllSort() {
        System.out.println("22 - findAll con sort specifico (discendente)");

        sort = Sort.by(Sort.Direction.DESC, "nome");
        listaBeans = backend.findAllSort(sort);
        assertNotNull(listaBeans);
        ottenutoIntero = listaBeans.size();
        message = String.format("La collection '%s' ha in totale %s entities nel database mongoDB", collectionName, textService.format(ottenutoIntero));
        System.out.println(message);
        printSubLista(listaBeans);
    }


    @Test
    @Order(31)
    @DisplayName("31 - newEntity vuota senza ID")
    void newEntityVuota() {
        System.out.println("31 - newEntity vuota senza ID");

        entityBean = backend.newEntity();
        assertNotNull(entityBean);
        assertNull(entityBean.id);
    }

    @Test
    @Order(32)
    @DisplayName("32 - newEntity con ID ma non registrata")
    void newEntity() {
        System.out.println("32 - newEntity con ID ma non registrata");

        sorgente = "Mario Lino";
        previsto = "mariolino";
        previsto2 = "Mario Lino";
        entityBean = backend.newEntity(sorgente);
        assertNotNull(entityBean);
        ottenuto = entityBean.id;
        ottenuto2 = entityBean.nome;
        assertEquals(previsto, ottenuto);
        assertEquals(previsto2, ottenuto2);
    }

    @Test
    @Order(33)
    @DisplayName("33 - CRUD operations")
    void crud() {
        System.out.println("33 - CRUD operations");
        System.out.println(VUOTA);

        String nomeOriginale = "Mario Lino";
        String keyID = "mariolino";
        String nomeModificato = "Giuseppe";

        ottenutoBooleano = backend.isExistId(nomeOriginale);
        assertFalse(ottenutoBooleano);
        message = String.format("1) isExistId -> Nella collection '%s' non esiste (false) la entity [%s]", collectionName, nomeOriginale);
        System.out.println(message);

        ottenutoBooleano = backend.creaIfNotExist(nomeOriginale);
        assertTrue(ottenutoBooleano);
        message = String.format("2) creaIfNotExist -> Adesso nella collection '%s' è stata creata la entity [%s].%s che prima non esisteva", collectionName, keyID, nomeOriginale);
        System.out.println(message);

        ottenutoBooleano = backend.isExistId(keyID);
        assertTrue(ottenutoBooleano);
        message = String.format("3) isExistId -> Controllo l'esistenza (true) della entity [%s].%s", keyID, nomeOriginale);
        System.out.println(message);

        ottenutoBooleano = backend.creaIfNotExist(nomeOriginale);
        assertFalse(ottenutoBooleano);
        message = String.format("4) creaIfNotExist -> La entity [%s].%s esisteva già e non è stata creata (false)", keyID, nomeOriginale);
        System.out.println(message);

        entityBean = backend.findById(keyID);
        assertNotNull(entityBean);
        message = String.format("5) findById -> Recupero la entity [%s].%s dalla keyID", keyID, nomeOriginale);
        System.out.println(message);

        entityBean = backend.findByKeyCode(nomeOriginale);
        assertNotNull(entityBean);
        message = String.format("6) findByKeyCode -> Recupero la entity [%s].%s dal valore '%s' della property [%s]", keyID, nomeOriginale, nomeOriginale, keyPropertyName);
        System.out.println(message);

        ottenutoBooleano = backend.isExistProperty(nomeOriginale);
        message = String.format("7) isExistProperty -> Esiste la entity [%s].%s individuata dal valore '%s' della keyProperty [%s]", keyID, nomeOriginale, nomeOriginale, keyPropertyName);
        assertTrue(ottenutoBooleano);
        System.out.println(message);
        ottenutoBooleano = backend.isExistProperty(nomeModificato);
        assertFalse(ottenutoBooleano);
        message = String.format("8) isExistProperty -> Non esiste la entity [%s].%s individuata dal valore '%s' della keyProperty [%s]", keyID, nomeModificato, nomeModificato, keyPropertyName);
        System.out.println(message);

        entityBean.nome = nomeModificato;
        entityBean = backend.save(entityBean);
        assertNotNull(entityBean);
        assertEquals(nomeModificato, entityBean.nome);
        entityBean = backend.findById(keyID);
        assertNotNull(entityBean);
        assertEquals(nomeModificato, entityBean.nome);
        message = String.format("9) save -> Modifica la entity [%s].%s in [%s].%s", keyID, nomeOriginale, keyID, nomeModificato);
        System.out.println(message);

        ottenutoBooleano = backend.isExistProperty(nomeOriginale);
        message = String.format("7) isExistProperty -> Non esiste la entity [%s].%s individuata dal valore '%s' della keyProperty [%s]", keyID, nomeOriginale, nomeOriginale, keyPropertyName);
        assertFalse(ottenutoBooleano);
        System.out.println(message);
        ottenutoBooleano = backend.isExistProperty(nomeModificato);
        assertTrue(ottenutoBooleano);
        message = String.format("8) isExistProperty -> Esiste la entity [%s].%s individuata dal valore '%s' della keyProperty [%s]", keyID, nomeModificato, nomeModificato, keyPropertyName);
        System.out.println(message);

        ottenutoBooleano = backend.delete(entityBean);
        assertTrue(ottenutoBooleano);
        message = String.format("12) delete -> Cancello la entity [%s].%s", keyID, nomeModificato);
        System.out.println(message);

        ottenutoBooleano = backend.isExistId(keyID);
        message = String.format("13) isExistId -> Alla fine, nella collection '%s' non esiste più la entity [%s] che è stata cancellata", collectionName, keyID);
        System.out.println(message);
    }

    //    @Test
    @Order(91)
    @DisplayName("91 - resetOnlyEmpty")
    void resetOnlyEmptyPieno() {
        System.out.println("91 - resetOnlyEmpty");
        String message;

        ottenutoRisultato = backend.resetOnlyEmpty();
        printRisultato(ottenutoRisultato);

        listaBeans = backend.findAllSortCorrente();
        assertNotNull(listaBeans);
        System.out.println(VUOTA);
        message = String.format("Ci sono in totale %s entities di %s", textService.format(listaBeans.size()), collectionName);
        System.out.println(message);
        printSubLista(listaBeans);
    }


    //    @Test
    @Order(92)
    @DisplayName("92 - resetForcing")
    void resetOnlyEmptyVuoto() {
        System.out.println("92 - resetForcing");
        String message;

        ottenutoRisultato = backend.resetForcing();
        printRisultato(ottenutoRisultato);

        listaBeans = backend.findAllSortCorrente();
        assertNotNull(listaBeans);
        System.out.println(VUOTA);
        message = String.format("Ci sono in totale %s entities di %s", textService.format(listaBeans.size()), collectionName);
        System.out.println(message);
        printSubLista(listaBeans);
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

    void printBeans(List<Via> listaBeans) {
        System.out.println(VUOTA);
        int k = 0;

        for (Via bean : listaBeans) {
            System.out.print(++k);
            System.out.print(PARENTESI_TONDA_END);
            System.out.print(SPAZIO);
            System.out.println(bean);
        }
    }

}
