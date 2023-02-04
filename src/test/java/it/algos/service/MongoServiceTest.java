package it.algos.service;

import com.mongodb.client.*;
import it.algos.*;
import it.algos.base.*;
import it.algos.vaad24.backend.boot.*;
import static it.algos.vaad24.backend.boot.VaadCost.*;
import it.algos.vaad24.backend.service.*;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.extension.*;
import org.springframework.boot.test.context.*;
import org.springframework.test.context.junit.jupiter.*;

import java.util.*;

/**
 * Project vaad24
 * Created by Algos
 * User: gac
 * Date: Sat, 04-Feb-2023
 * Time: 18:27
 */
@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = {Vaad24SimpleApp.class})
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Tag("service")
@DisplayName("Mongo Service")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class MongoServiceTest extends AlgosIntegrationTest {


    private MongoDatabase dataBase;

    private MongoClient mongoClient;

    private MongoCollection collection;

    private List<String> listaCollections;


    /**
     * Classe principale di riferimento <br>
     * Gia 'costruita' nella superclasse <br>
     */
    private MongoService service;


    /**
     * Qui passa una volta sola, chiamato dalle sottoclassi <br>
     * Invocare PRIMA il metodo setUpStartUp() della superclasse <br>
     * Si possono aggiungere regolazioni specifiche <br>
     */
    @BeforeAll
    protected void setUpAll() {
        super.setUpAll();

        //--reindirizzo l'istanza della superclasse
        service = mongoService;
    }


    /**
     * Qui passa a ogni test delle sottoclassi <br>
     * Invocare PRIMA il metodo setUp() della superclasse <br>
     * Si possono aggiungere regolazioni specifiche <br>
     */
    @BeforeEach
    protected void setUpEach() {
        super.setUpEach();

        this.dataBase = null;
    }


    @Test
    @Order(1)
    @DisplayName("1 - getDBAdmin")
    void getDBAdmin() {
        System.out.println("1 - getDBAdmin");
        System.out.println(VUOTA);

        dataBase = service.getDBAdmin();
        assertNotNull(dataBase);
        ottenuto = dataBase.getName();
        assertEquals("admin", ottenuto);
        System.out.println(ottenuto);
    }

    @Test
    @Order(2)
    @DisplayName("2 - listCollectionNames")
    void listCollectionNames() {
        System.out.println("2 - listCollectionNames");
        System.out.println(VUOTA);

        dataBase = service.getDBAdmin();
        assertNotNull(dataBase);
        listaCollections = service.listCollectionNames(dataBase);
        message = String.format("Collezioni del database '%s'", dataBase.getName());
        print(listaCollections, message);
    }

    @Test
    @Order(3)
    @DisplayName("3 - listCollectionNamesVaad24")
    void listCollectionNamesVaad24() {
        System.out.println("3 - listCollectionNamesVaad24");
        System.out.println(VUOTA);

        sorgente= VaadVar.moduloVaadin24;
        dataBase = service.getDB(sorgente);
        assertNotNull(dataBase);
        listaCollections = service.listCollectionNames(dataBase);
        message = String.format("Collezioni del database '%s'", dataBase.getName());
        print(listaCollections, message);
    }
    @Test
    @Order(4)
    @DisplayName("4 - versione")
    void versione() {
        System.out.println("4 - versione");
        System.out.println(VUOTA);

        ottenuto = service.versione();
        System.out.println(ottenuto);
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
