package it.algos.backend;

import it.algos.*;
import it.algos.base.*;
import static it.algos.vaad24.backend.boot.VaadCost.*;
import it.algos.vaad24.backend.packages.crono.giorno.*;
import it.algos.vaad24.backend.packages.crono.mese.*;
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
 * Time: 09:35
 */
@SpringBootTest(classes = {Vaad24SimpleApplication.class})
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Tag("integration")
@Tag("backend")
@DisplayName("Giorno Backend")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class GiornoBackendTest extends AlgosTest {

    /**
     * The Service.
     */
    @InjectMocks
    private GiornoBackend backend;

    @Autowired
    private GiornoRepository repository;

    private Giorno entityBean;

    private List<Giorno> listaBeans;

    private String dbName = "Giorno";

    private String backendName = "GiornoBackend";

    @InjectMocks
    private MeseBackend meseBackend;

    @Autowired
    private MeseRepository meseRepository;


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
        backend.textService = textService;
        backend.dateService = dateService;
        backend.resourceService = resourceService;
        backend.reflectionService = reflectionService;
        backend.meseBackend = meseBackend;
        backend.meseBackend.repository = meseRepository;
        backend.meseBackend.crudRepository = meseRepository;
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
    @DisplayName("1 - count")
    void count() {
        System.out.println("1 - count");
        String message;

        ottenutoIntero = backend.count();
        if (ottenutoIntero > 0) {
            message = String.format("Ci sono in totale %s entities di '%s' nel database mongoDB", textService.format(ottenutoIntero), dbName);
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
    @Order(2)
    @DisplayName("2 - findAll")
    void findAll() {
        System.out.println("2 - findAll");
        String message;

        listaBeans = backend.findAll();
        assertNotNull(listaBeans);
        message = String.format("Ci sono in totale %s entities di %s", textService.format(listaBeans.size()), dbName);
        System.out.println(message);
        printSubLista(listaBeans);
    }


//    @Test
    @Order(90)
    @DisplayName("90 - reset")
    void reset() {
        System.out.println("90 - reset");
        String message;

        ottenutoBooleano = backend.resetForcing();
        if (ottenutoBooleano) {
            message = String.format("Reset effettuato correttamente");
            System.out.println(message);
        }
        else {
            message = String.format("Rest non riuscito");
            System.out.println(message);
            assertTrue(ottenutoBooleano);
        }

        listaBeans = backend.findAll();
        assertNotNull(listaBeans);
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

    void printBeans(List<Giorno> listaBeans) {
        System.out.println(VUOTA);
        int k = 0;

        for (Giorno bean : listaBeans) {
            System.out.print(++k);
            System.out.print(PARENTESI_TONDA_END);
            System.out.print(SPAZIO);
            System.out.println(bean);
        }
    }

}
