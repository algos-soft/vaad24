package it.algos.enumeration;

import it.algos.base.*;
import static it.algos.vaad24.backend.boot.VaadCost.*;
import it.algos.vaad24.backend.enumeration.*;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.*;

/**
 * Project vaadin23
 * Created by Algos
 * User: gac
 * Date: sab, 07-mag-2022
 * Time: 17:13
 * Unit test di una enumeration <br>
 * Estende la classe astratta AlgosTest che contiene costanti e metodi base <br>
 * Nella superclasse AlgosTest vengono iniettate (@InjectMocks) tutte le altre classi di service <br>
 * Nella superclasse AlgosTest vengono regolati tutti i link incrociati tra le varie classi singleton di service <br>
 */
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Tag("quickly")
@Tag("enums")
@DisplayName("Enumeration Pref")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class PrefTest extends AlgosUnitTest {


    private Pref type;

    private Pref[] matrice;

    private List<Pref> listaEnum;

    private List<String> listaTag;



    /**
     * Qui passa una volta sola, chiamato dalle sottoclassi <br>
     * Invocare PRIMA il metodo setUpStartUp() della superclasse <br>
     * Si possono aggiungere regolazioni specifiche <br>
     */
    @BeforeAll
    protected void setUpAll() {
        super.setUpAll();
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
    }


    @Test
    @Order(1)
    @DisplayName("1 - matrice dei valori")
    void matrice() {
        matrice = Pref.values();
        assertNotNull(matrice);

        System.out.println("Tutti i valori della enumeration come matrice []");
        System.out.println(VUOTA);
        System.out.println(String.format("Ci sono %d elementi nella Enumeration", matrice.length));
        System.out.println(VUOTA);
        for (Pref valore : matrice) {
            System.out.println(valore);
        }
    }

    @Test
    @Order(2)
    @DisplayName("2 - lista dei valori metodo forEach")
    void forEach() {
        listaEnum = Pref.getAllEnums();
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
        listaEnum = Pref.getAllEnums();
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
    @DisplayName("4 - keyCode di ogni singola enumeration")
    void tag() {
        listaEnum = Pref.getAllEnums();
        assertNotNull(listaEnum);

        System.out.println("Tutti i keyCode di ogni singola enumeration");
        System.out.println(VUOTA);
        System.out.println(String.format("Ci sono %d elementi nella Enumeration, recuperati in %s", listaEnum.size(), dateService.deltaTextEsatto(inizio)));
        System.out.println(VUOTA);
        listaEnum.forEach(e -> {System.out.print(e); System.out.print(FORWARD); System.out.println(e.getKeyCode());});
        System.out.println(VUOTA);
    }


    @Test
    @Order(5)
    @DisplayName("5 - lista dei keyCode")
    void allTag() {
        listaTag = Pref.getAllKeyCode();
        assertNotNull(listaTag);

        System.out.println("Tutti i valori 'keyCode' della enumeration");
        System.out.println(VUOTA);
        System.out.println(String.format("Ci sono %d elementi nella Enumeration", listaTag.size()));
        System.out.println(VUOTA);
        listaTag.forEach(System.out::println);
        System.out.println(VUOTA);
    }


    @Test
    @Order(6)
    @DisplayName("6 - keyCode e type e descrizione")
    void descrizione() {
        listaEnum = Pref.getAllEnums();
        assertNotNull(listaEnum);

        System.out.println("keyCode e type e descrizione di ogni singola enumeration");
        System.out.println(VUOTA);
        System.out.println(String.format("Ci sono %d elementi nella Enumeration, recuperati in %s", listaEnum.size(), dateService.deltaTextEsatto(inizio)));
        System.out.println(VUOTA);
        listaEnum.forEach(e -> {
            System.out.print(e);
            System.out.print(FORWARD);
            System.out.print(e.getKeyCode());
            System.out.print(SEP);
            System.out.print(e.getType());
            System.out.print(SEP);
            System.out.println(e.getDescrizione());
        });
        System.out.println(VUOTA);
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