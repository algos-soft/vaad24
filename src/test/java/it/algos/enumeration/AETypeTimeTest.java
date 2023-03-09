package it.algos.enumeration;

import it.algos.base.*;
import static it.algos.vaad24.backend.boot.VaadCost.*;
import it.algos.vaad24.backend.enumeration.*;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.*;
import java.util.concurrent.*;

/**
 * Project vaad24
 * Created by Algos
 * User: gac
 * Date: Sun, 29-Jan-2023
 * Time: 21:54
 * Unit test di una enumeration <br>
 * Estende la classe astratta AlgosTest che contiene costanti e metodi base <br>
 * Nella superclasse AlgosTest vengono iniettate (@InjectMocks) tutte le altre classi di service <br>
 * Nella superclasse AlgosTest vengono regolati tutti i link incrociati tra le varie classi singleton di service <br>
 */
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Tag("quickly")
@Tag("enums")
@DisplayName("Enumeration AETypeTime")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class AETypeTimeTest extends AlgosTest {


    private AETypeTime type;

    private AETypeTime[] matrice;

    private List<AETypeTime> listaEnum;

    private List<String> listaTag;

    private long inizio;
    private long fine;


    /**
     * Qui passa una volta sola, chiamato dalle sottoclassi <br>
     * Invocare PRIMA il metodo setUpStartUp() della superclasse <br>
     * Si possono aggiungere regolazioni specifiche <br>
     */
    @BeforeAll
    protected void setUpAll() {
        super.setUpAll();
        AETypeTime.getAllEnums().stream().forEach(type->type.setText(textService));
    }


    /**
     * Qui passa prima di ogni test delle sottoclassi <br>
     * Invocare PRIMA il metodo setUp() della superclasse <br>
     * Si possono aggiungere regolazioni specifiche <br>
     */
    @BeforeEach
    protected void setUpEach() {
        super.setUpEach();

        type = null;
        matrice = null;
        listaEnum = null;
        listaTag = null;

        inizio = System.currentTimeMillis();
    }


    @Test
    @Order(1)
    @DisplayName("1 - matrice dei valori")
    void matrice() {
        matrice = AETypeTime.values();
        assertNotNull(matrice);

        System.out.println("Tutti i valori della enumeration come matrice []");
        System.out.println(VUOTA);
        System.out.println(String.format("Ci sono %d elementi nella Enumeration", matrice.length));
        System.out.println(VUOTA);
        for (AETypeTime valore : matrice) {
            System.out.println(valore);
        }
    }


    @Test
    @Order(2)
    @DisplayName("2 - lista dei valori metodo forEach")
    void forEach() {
        listaEnum = AETypeTime.getAllEnums();
        assertNotNull(listaEnum);

        System.out.println("Tutte le occorrenze della enumeration come ArrayList(), metodo forEach");
        System.out.println(VUOTA);
        System.out.println(String.format("Ci sono %d elementi nella Enumeration, recuperati in %s", listaEnum.size(), dateService.deltaTextEsatto(inizio)));
        System.out.println(VUOTA);
        listaEnum.forEach(System.out::println);
        System.out.println(VUOTA);
    }


    @Test
    @Order(3)
    @DisplayName("3 - lista dei valori metodo stream")
    void stream() {
        listaEnum = AETypeTime.getAllEnums();
        assertNotNull(listaEnum);

        System.out.println("Tutte le occorrenze della enumeration come ArrayList(), metodo stream");
        System.out.println(VUOTA);
        System.out.println(String.format("Ci sono %d elementi nella Enumeration, recuperati in %s", listaEnum.size(), dateService.deltaTextEsatto(inizio)));
        System.out.println(VUOTA);
        listaEnum.stream().forEach(System.out::println);
        System.out.println(VUOTA);
    }


    @Test
    @Order(4)
    @DisplayName("4 - tag di ogni singola enumeration")
    void tag() {
        listaEnum = AETypeTime.getAllEnums();
        assertNotNull(listaEnum);

        System.out.println("Tutti i tag di ogni singola enumeration");
        System.out.println(VUOTA);
        System.out.println(String.format("Ci sono %d elementi nella Enumeration, recuperati in %s", listaEnum.size(), dateService.deltaTextEsatto(inizio)));
        System.out.println(VUOTA);
        listaEnum.forEach(e -> {
            System.out.print(e);
            System.out.print(FORWARD);
            System.out.println(e.getTag());
        });
        System.out.println(VUOTA);
    }


    @Test
    @Order(5)
    @DisplayName("5 - lista dei tag")
    void allTag() {
        listaTag = AETypeTime.getAllTags();
        assertNotNull(listaTag);

        System.out.println("Tutti i valori 'tag' della enumeration");
        System.out.println(VUOTA);
        System.out.println(String.format("Ci sono %d elementi nella Enumeration", listaTag.size()));
        System.out.println(VUOTA);
        listaTag.forEach(System.out::println);
        System.out.println(VUOTA);
    }


    @Test
    @Order(6)
    @DisplayName("6 - durata")
    void durata() {
        try {
            TimeUnit.SECONDS.sleep(3);
        } catch (Exception unErrore) {
            assertTrue(false);
        }
        fine = System.currentTimeMillis();

        for (AETypeTime type : AETypeTime.getAllEnums()) {
            ottenutoIntero = type.durata(inizio);
            if (ottenutoIntero > 0) {
                message = String.format("Tempo trascorso %s %s", ottenutoIntero, type.getTag());
                System.out.println(message);
            }
        }
    }


    @Test
    @Order(7)
    @DisplayName("7 - message")
    void message() {
        try {
            TimeUnit.SECONDS.sleep(3);
        } catch (Exception unErrore) {
            assertTrue(false);
        }
        fine = System.currentTimeMillis();

        for (AETypeTime type : AETypeTime.getAllEnums()) {
            ottenuto = type.message(inizio);
            System.out.println(ottenuto);
        }
    }


    @Test
    @Order(8)
    @DisplayName("8 - message2")
    void message2() {
        try {
            TimeUnit.SECONDS.sleep(3);
        } catch (Exception unErrore) {
            assertTrue(false);
        }
        fine = System.currentTimeMillis();
        message = "Elaborazione statistiche e upload dei giorni eseguito";

        for (AETypeTime type : AETypeTime.getAllEnums()) {
            ottenuto = type.message(inizio,message);
            System.out.println(ottenuto);
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