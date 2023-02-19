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

    private String dbName;

    private String backendName = "ViaBackend";

    private Class entityClazz = Via.class;

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

        dbName = annotationService.getCollectionName(entityClazz);
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
        message = String.format("%s la collection della classe %s", ottenutoBooleano ? "Esiste" : "Non esiste", backend.entityClazz.getSimpleName());
        System.out.println(message);
        assertTrue(ottenutoBooleano);
    }
    @Test
    @Order(18)
    @DisplayName("18 - collection")
    void collection2() {
        System.out.println("18 - Esistenza della collection");
        System.out.println(VUOTA);

        ottenutoBooleano = backend.isExistsCollection();
        message = String.format("%s la collection della classe %s", ottenutoBooleano ? "Esiste" : "Non esiste", backend.entityClazz.getSimpleName());
        System.out.println(message);
        assertTrue(ottenutoBooleano);
    }

    @Test
    @Order(2)
    @DisplayName("2 - count")
    void count2() {
        System.out.println("2 - count");
        System.out.println(VUOTA);

        ottenutoIntero = backend.count();
        if (ottenutoIntero > 0) {
            message = String.format("La collection '%s' ha in totale %s entities nel database mongoDB", dbName, textService.format(ottenutoIntero));
        }
        else {
            if (reflectionService.isEsisteMetodo(backend.getClass(), TAG_RESET_ONLY)) {
                message = String.format("La collection '%s' è ancora vuota. Usa il metodo %s.%s()", dbName, backendName, TAG_RESET_ONLY);
            }
            else {
                message = String.format("Nel database mongoDB la collection '%s' è ancora vuota", dbName);
            }
        }
        System.out.println(message);
    }


    @Test
    @Order(10)
    @DisplayName("10 - findAll unsorted")
    void findAll() {
        System.out.println("10 - findAll unsorted");

        listaBeans = backend.findAll();
        assertNotNull(listaBeans);
        ottenutoIntero = listaBeans.size();
        message = String.format("La collection '%s' ha in totale %s entities nel database mongoDB", dbName, textService.format(ottenutoIntero));
        System.out.println(message);
        printSubLista(listaBeans);
    }


    @Test
    @Order(11)
    @DisplayName("11 - findAll getSortKeyID")
    void findAllSort() {
        System.out.println("11 - findAll getSortKeyID");

        listaBeans = backend.findAllSort();
        assertNotNull(listaBeans);
        ottenutoIntero = listaBeans.size();
        message = String.format("La collection '%s' ha in totale %s entities nel database mongoDB", dbName, textService.format(ottenutoIntero));
        System.out.println(message);
        printSubLista(listaBeans);
    }

    @Test
    @Order(91)
    @DisplayName("91 - resetOnlyEmpty")
    void resetOnlyEmptyPieno() {
        System.out.println("91 - resetOnlyEmpty");
        String message;

        ottenutoRisultato = backend.resetOnlyEmpty();
        printRisultato(ottenutoRisultato);

        listaBeans = backend.findAll();
        assertNotNull(listaBeans);
        System.out.println(VUOTA);
        message = String.format("Ci sono in totale %s entities di %s", textService.format(listaBeans.size()), dbName);
        System.out.println(message);
        printSubLista(listaBeans);
    }


    @Test
    @Order(92)
    @DisplayName("92 - resetForcing")
    void resetOnlyEmptyVuoto() {
        System.out.println("92 - resetForcing");
        String message;

        ottenutoRisultato = backend.resetForcing();
        printRisultato(ottenutoRisultato);

        listaBeans = backend.findAll();
        assertNotNull(listaBeans);
        System.out.println(VUOTA);
        message = String.format("Ci sono in totale %s entities di %s", textService.format(listaBeans.size()), dbName);
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
