package it.algos.backend;

import it.algos.*;
import it.algos.base.*;
import static it.algos.vaad24.backend.boot.VaadCost.*;
import it.algos.vaad24.backend.enumeration.*;
import it.algos.vaad24.backend.interfaces.*;
import it.algos.vaad24.backend.packages.utility.preferenza.*;
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
 * Date: Sat, 11-Feb-2023
 * Time: 17:22
 */
@SpringBootTest(classes = {Wiki24App.class})
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Tag("integration")
@Tag("backendx")
@DisplayName("Preferenza Backend")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class PreferenzaBackendTest extends AlgosTest {

    /**
     * The Service.
     */
    @InjectMocks
    private PreferenzaBackend backend;

    @Autowired
    private PreferenzaRepository repository;

    private Preferenza entityBean;

    private List<Preferenza> listaBeans;

    private String dbName = "Preferenza";

    private String backendName = "PreferenzaBackend";

    private Class entityClazz = Preferenza.class;

    private Object objOttenuto;

    private Object objPrevisto;

    private AIGenPref enumeration;

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
        backend.annotationService = annotationService;
        backend.resourceService = resourceService;
        backend.reflectionService = reflectionService;
        backend.mongoService = mongoService;
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
        this.objOttenuto = null;
        this.objPrevisto = null;
        this.enumeration = null;
    }


    @Test
    @Order(1)
    @DisplayName("1 - count")
    void count() {
        System.out.println("1 - count");
        String message;

        ottenutoIntero = backend.count();
        if (ottenutoIntero > 0) {
            message = String.format("Ci sono in totale %s entities di '%s' nel database mongoDB", textService.format(ottenutoIntero), dbName);
        }
        else {
            if (reflectionService.isEsisteMetodo(backend.getClass(), METHOD_NAME_RESET_ONLY)) {
                message = String.format("La collection '%s' è ancora vuota. Usa il metodo %s.%s()", dbName, backendName, METHOD_NAME_RESET_ONLY);
            }
            else {
                message = String.format("Nel database mongoDB la collection '%s' è ancora vuota", dbName);
            }
        }
        System.out.println(message);
    }


    @Test
    @Order(2)
    @DisplayName("2 - findAll")
    void findAll() {
        System.out.println("2 - findAll");
        String message;

        listaBeans = backend.findAllSortCorrente();
        assertNotNull(listaBeans);
        message = String.format("Ci sono in totale %s entities di %s", textService.format(listaBeans.size()), dbName);
        System.out.println(message);
        printSubLista(listaBeans);
    }

    @Test
    @Order(3)
    @DisplayName("3 - findByKeyCode")
    void findByKeyCode() {
        System.out.println("3 - findByKeyCode");

        sorgente = "debug";
        objPrevisto = false;
        objOttenuto = backend.findByKey(sorgente).getValore();
        assertNotNull(objOttenuto);

        sorgente = "nonBreaking";
        objPrevisto = SPAZIO_NON_BREAKING;
        objOttenuto = backend.findByKey(sorgente).getValore();
        assertNotNull(objOttenuto);
        assertEquals(objPrevisto, objOttenuto);
    }


    @Test
    @Order(4)
    @DisplayName("4 - getValore")
    void getValore() {
        System.out.println("4 - getValore");

        sorgente = "debug";
        objPrevisto = false;
        objOttenuto = backend.getValore(sorgente);
        assertNotNull(objOttenuto);

        sorgente = "nonBreaking";
        objPrevisto = SPAZIO_NON_BREAKING;
        objOttenuto = backend.getValore(sorgente);
        assertNotNull(objOttenuto);
        assertEquals(objPrevisto, objOttenuto);
    }


    @Test
    @Order(5)
    @DisplayName("5 - setValore")
    void setValore() {
        System.out.println("5 - setValore");

        sorgente = "nonBreaking";
        sorgente2 = "pippoz";
        backend.setValore(sorgente, sorgente2);

        objPrevisto = sorgente2;
        objOttenuto = backend.getValore(sorgente);
        assertNotNull(objOttenuto);
        assertEquals(objPrevisto, objOttenuto);

        sorgente2 = SPAZIO_NON_BREAKING;
        previstoBooleano = true;
        ottenutoBooleano = backend.setValore(sorgente, sorgente2);
        assertTrue(ottenutoBooleano);

        objPrevisto = SPAZIO_NON_BREAKING;
        objOttenuto = backend.getValore(sorgente);
        assertNotNull(objOttenuto);
        assertEquals(objPrevisto, objOttenuto);

        sorgente2 = SPAZIO_NON_BREAKING;
        previstoBooleano = false;
        ottenutoBooleano = backend.setValore(sorgente, sorgente2);
        assertFalse(ottenutoBooleano);
    }


    @Test
    @Order(6)
    @DisplayName("6 - getValore da Enum")
    void getValore2() {
        System.out.println("6 - getValore da Enum");

        enumeration = Pref.debug;
        objPrevisto = false;
        objOttenuto = backend.getValore(enumeration);
        assertNotNull(objOttenuto);

        enumeration = Pref.nonBreaking;
        objPrevisto = SPAZIO_NON_BREAKING;
        objOttenuto = backend.getValore(enumeration);
        assertNotNull(objOttenuto);
        assertEquals(objPrevisto, objOttenuto);
    }




    @Test
    @Order(7)
    @DisplayName("7 - setValore da Enum")
    void setValore2() {
        System.out.println("7 - setValore da Enum");

        enumeration = Pref.nonBreaking;
        sorgente2 = "pippoz";
        backend.setValore(enumeration, sorgente2);

        objPrevisto = sorgente2;
        objOttenuto = backend.getValore(enumeration);
        assertNotNull(objOttenuto);
        assertEquals(objPrevisto, objOttenuto);

        sorgente2 = SPAZIO_NON_BREAKING;
        previstoBooleano = true;
        ottenutoBooleano = backend.setValore(enumeration, sorgente2);
        assertTrue(ottenutoBooleano);

        objPrevisto = SPAZIO_NON_BREAKING;
        objOttenuto = backend.getValore(enumeration);
        assertNotNull(objOttenuto);
        assertEquals(objPrevisto, objOttenuto);

        sorgente2 = SPAZIO_NON_BREAKING;
        previstoBooleano = false;
        ottenutoBooleano = backend.setValore(enumeration, sorgente2);
        assertFalse(ottenutoBooleano);
    }





    @Test
    @Order(8)
    @DisplayName("8 - resetStandard da Enum")
    void resetStandard() {
        System.out.println("8 - resetStandard da Enum");

        enumeration = Pref.nonBreaking;
        sorgente2 = "pippoz";
        backend.setValore(enumeration, sorgente2);

        objPrevisto = sorgente2;
        objOttenuto = backend.getValore(enumeration);
        assertNotNull(objOttenuto);
        assertEquals(objPrevisto, objOttenuto);

        previstoBooleano = true;
        ottenutoBooleano = backend.resetStandard(enumeration);
        assertTrue(ottenutoBooleano);

        objPrevisto = SPAZIO_NON_BREAKING;
        objOttenuto = backend.getValore(enumeration);
        assertNotNull(objOttenuto);
        assertEquals(objPrevisto, objOttenuto);

        previstoBooleano = false;
        ottenutoBooleano = backend.resetStandard(enumeration);
        assertFalse(ottenutoBooleano);
    }

    //    @Test
    @Order(91)
    @DisplayName("91 - resetOnlyEmpty pieno")
    void resetOnlyEmptyPieno() {
        System.out.println("91 - resetOnlyEmpty pieno");
        String message;

        ottenutoRisultato = backend.resetOnlyEmpty();
        printRisultato(ottenutoRisultato);

        listaBeans = backend.findAllSortCorrente();
        assertNotNull(listaBeans);
        System.out.println(VUOTA);
        message = String.format("Ci sono in totale %s entities di %s", textService.format(listaBeans.size()), dbName);
        System.out.println(message);
        printSubLista(listaBeans);
    }


    //    @Test
    @Order(92)
    @DisplayName("92 - resetOnlyEmpty vuoto")
    void resetOnlyEmptyVuoto() {
        System.out.println("92 - resetOnlyEmpty vuoto");
        String message;

        mongoService.deleteAll(entityClazz);
        ottenutoRisultato = backend.resetOnlyEmpty();
        printRisultato(ottenutoRisultato);

        listaBeans = backend.findAllSortCorrente();
        assertNotNull(listaBeans);
        System.out.println(VUOTA);
        message = String.format("Ci sono in totale %s entities di %s", textService.format(listaBeans.size()), dbName);
        System.out.println(message);
        printSubLista(listaBeans);
    }

    //    @Test
    @Order(93)
    @DisplayName("93 - resetForcing pieno")
    void resetForcingPieno() {
        System.out.println("93 - resetForcing pieno");
        String message;

        ottenutoRisultato = backend.resetForcing();
        printRisultato(ottenutoRisultato);

        listaBeans = backend.findAllSortCorrente();
        assertNotNull(listaBeans);
        System.out.println(VUOTA);
        message = String.format("Ci sono in totale %s entities di %s", textService.format(listaBeans.size()), dbName);
        System.out.println(message);
        printSubLista(listaBeans);
    }


    //    @Test
    @Order(94)
    @DisplayName("94 - resetForcing vuoto")
    void resetForcingVuoto() {
        System.out.println("94 - resetForcing vuoto");
        String message;

        mongoService.deleteAll(entityClazz);
        ottenutoRisultato = backend.resetForcing();
        printRisultato(ottenutoRisultato);

        listaBeans = backend.findAllSortCorrente();
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

    void printBeans(List<Preferenza> listaBeans) {
        System.out.println(VUOTA);
        int k = 0;

        for (Preferenza bean : listaBeans) {
            System.out.print(++k);
            System.out.print(PARENTESI_TONDA_END);
            System.out.print(SPAZIO);
            System.out.println(bean);
        }
    }

}