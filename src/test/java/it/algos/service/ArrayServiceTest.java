package it.algos.service;

import it.algos.*;
import it.algos.base.*;
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
 * Date: Mon, 13-Feb-2023
 * Time: 14:37
 */
@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = {Vaad24SimpleApp.class})
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Tag("quickly")
@Tag("service")
@DisplayName("Array Service")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ArrayServiceTest extends QuicklyTest {

    /**
     * Classe principale di riferimento <br>
     * Gia 'costruita' nella superclasse <br>
     */
    private ArrayService service;


    /**
     * Qui passa una volta sola, chiamato dalle sottoclassi <br>
     * Invocare PRIMA il metodo setUpStartUp() della superclasse <br>
     * Si possono aggiungere regolazioni specifiche <br>
     */
    @BeforeAll
    protected void setUpAll() {
        super.setUpAll();

        //--reindirizzo l'istanza della superclasse
        service = arrayService;
    }


    /**
     * Qui passa a ogni test delle sottoclassi <br>
     * Invocare PRIMA il metodo setUp() della superclasse <br>
     * Si possono aggiungere regolazioni specifiche <br>
     */
    @BeforeEach
    protected void setUpEach() {
        super.setUpEach();
    }


    @Test
    @Order(1)
    @DisplayName("Primo test")
    void getLabelHost() {
        sorgente = "alfa.beta.gamma";
        ottenuto = textService.levaPunti(sorgente);
        assertTrue(textService.isValid(ottenuto));
        System.out.println(ottenuto);
    }


    @Test
    @Order(2)
    @DisplayName("2 - ordina una mappa secondo le chiavi")
    void sort() {
        System.out.println("2 - Ordina secondo le chiavi una mappa che è stata costruita NON ordinata");
        System.out.println(VUOTA);

        mappaSorgente = new HashMap<>();
        mappaSorgente.put("beta", "adesso");
        mappaSorgente.put("delta", "bufala");
        mappaSorgente.put("alfa", "comodino");
        mappaSorgente.put("coraggio", "domodossola");
        mappaOttenuta = service.sort(mappaSorgente);
        assertNotNull(mappaOttenuta);
        assertTrue(mappaOttenuta.size() == 4);
        printMappa(mappaOttenuta);
    }

    @Test
    @Order(3)
    @DisplayName("3 - ordina una mappa secondo i valori")
    void sortValue() {
        System.out.println("3 - Ordina secondo i valori una mappa che è stata costruita NON ordinata");
        System.out.println(VUOTA);

        mappaSorgente = new HashMap<>();
        mappaSorgente.put("beta", "adesso");
        mappaSorgente.put("delta", "bufala");
        mappaSorgente.put("alfa", "comodino");
        mappaSorgente.put("coraggio", "domodossola");
        mappaOttenuta = service.sortValue(mappaSorgente);
        assertNotNull(mappaOttenuta);
        assertTrue(mappaOttenuta.size() == 4);
        printMappa(mappaOttenuta);
    }

    @Test
    @Order(4)
    @DisplayName("4 - ordina una mappa secondo i valori (multipli)")
    void sortValue2() {
        System.out.println("4 - Ordina secondo i valori (multipli) una mappa che è stata costruita NON ordinata");
        System.out.println(VUOTA);

        mappaSorgente = new HashMap<>();
        mappaSorgente.put("beta", "adesso");
        mappaSorgente.put("delta", "bufala");
        mappaSorgente.put("alfa", "bufala");
        mappaSorgente.put("coraggio", "domodossola");
        mappaOttenuta = service.sortValue(mappaSorgente);
        assertNotNull(mappaOttenuta);
        assertTrue(mappaOttenuta.size() == 4);
        printMappa(mappaOttenuta);
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
