package it.algos.service;

import com.mongodb.client.*;
import it.algos.*;
import it.algos.base.*;
import static it.algos.vaad24.backend.boot.VaadCost.*;
import it.algos.vaad24.backend.boot.*;
import it.algos.vaad24.backend.packages.anagrafica.*;
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
public class MongoServiceTest extends AlgosTest {


    private MongoDatabase dataBase;

    private MongoClient mongoClient;

    private MongoCollection collection;

    private List<String> listaCollectionsName;

    private List<MongoCollection> listaCollections;


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
    @DisplayName("2 - listCollectionNames di sistema")
    void listCollectionNames() {
        System.out.println("2 - listCollectionNames di sistema");
        System.out.println(VUOTA);

        dataBase = service.getDBAdmin();
        assertNotNull(dataBase);
        listaCollectionsName = service.listCollectionNames(dataBase);
        message = String.format("Collezioni del database '%s'", dataBase.getName());
        print(listaCollectionsName, message);
    }

    @Test
    @Order(3)
    @DisplayName("3 - listCollectionNames correnti")
    void listCollectionNamesVaad24() {
        System.out.println("3 - listCollectionNames correnti");
        System.out.println(VUOTA);

        sorgente = VaadVar.projectCurrent;
        dataBase = service.getDB(sorgente);
        assertNotNull(dataBase);
        listaCollectionsName = service.listCollectionNames(dataBase);
        message = String.format("Collezioni del database '%s'", dataBase.getName());
        print(listaCollectionsName, message);
    }

    @Test
    @Order(42)
    @DisplayName("4 - versione")
    void versione() {
        System.out.println("4 - versione");
        System.out.println(VUOTA);

        ottenuto = service.versione();
        System.out.println(VUOTA);
        message = String.format("Versione corrente del mongoDb installato: %s", ottenuto);
        System.out.println(message);
    }

    @Test
    @Order(52)
    @DisplayName("52 - count (senza repository)")
    void count() {
        System.out.println("5 - count (senza repository)");
        System.out.println(VUOTA);

        sorgenteClasse = Via.class;

        ottenutoIntero = service.count(sorgenteClasse);
        assertTrue(ottenutoIntero > 0);

        message = String.format("Nella classe/collection %s ci sono %d entities", sorgenteClasse.getSimpleName(), ottenutoIntero);
        System.out.println(message);
    }


    @Test
    @Order(61)
    @DisplayName("61 - esistenza di una collection")
    void collection() {
        System.out.println("61 - esistenza di una collection");
        System.out.println(VUOTA);

        sorgenteClasse = Via.class;
        ottenutoBooleano = service.isExistsCollection(sorgenteClasse);
        message = String.format("%s la collection della classe %s", ottenutoBooleano ? "Esiste" : "Non esiste", sorgenteClasse.getSimpleName());
        System.out.println(message);
        assertTrue(ottenutoBooleano);

        sorgente = sorgenteClasse.getSimpleName();
        ottenutoBooleano = service.isExistsCollection(sorgente);
        message = String.format("%s la collection della classe di nome %s", ottenutoBooleano ? "Esiste" : "Non esiste", sorgente);
        System.out.println(message);
        assertTrue(ottenutoBooleano);

        sorgenteClasse = VaadBoot.class;
        ottenutoBooleano = service.isExistsCollection(sorgenteClasse);
        message = String.format("%s la collection della classe %s", ottenutoBooleano ? "Esiste" : "Non esiste", sorgenteClasse.getSimpleName());
        System.out.println(message);
        assertFalse(ottenutoBooleano);
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
