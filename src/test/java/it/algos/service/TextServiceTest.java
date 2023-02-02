package it.algos.service;

import it.algos.*;
import it.algos.base.*;
import static it.algos.vaad24.backend.boot.VaadCost.*;
import it.algos.vaad24.backend.service.*;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.extension.*;
import org.junit.jupiter.params.*;
import org.junit.jupiter.params.provider.*;
import org.springframework.boot.test.context.*;
import org.springframework.test.context.junit.jupiter.*;

import java.util.stream.*;

/**
 * Project vaad24
 * Created by Algos
 * User: gac
 * Date: Thu, 02-Feb-2023
 * Time: 15:19
 */
@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = {Vaad24SimpleApplication.class})
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Tag("service")
@DisplayName("Text Service")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class TextServiceTest extends AlgosIntegrationTest {

    /**
     * Classe principale di riferimento <br>
     * Gia 'costruita' nella superclasse <br>
     */
    private TextService service;

    //--src
    //--risultato
    //--length
    protected static Stream<Arguments> RIGHT_PAD() {
        return Stream.of(
                Arguments.of(VUOTA, VUOTA, 0),
                Arguments.of(VUOTA, "   ", 3),
                Arguments.of("alfa", "alfa", 0),
                Arguments.of("alfa", "alfa", 2),
                Arguments.of("alfa", "alfa", 4),
                Arguments.of("alfa  ", "alfa  ", 6),
                Arguments.of("  alfa", "alfa  ", 6),
                Arguments.of("  alfa  ", "alfa  ", 6),
                Arguments.of("  alfa  ", "alfa", 2),
                Arguments.of("  alfa", "alfa", 4),
                Arguments.of("alfa", "alfa  ", 6)
        );
    }


    //--src
    //--risultato
    //--length
    protected static Stream<Arguments> MAX_SIZE() {
        return Stream.of(
                Arguments.of(VUOTA, VUOTA, 0),
                Arguments.of(VUOTA, VUOTA, 3),
                Arguments.of("alfa", VUOTA, 0),
                Arguments.of("alfa", "..", 2),
                Arguments.of("alfa", "alfa", 4),
                Arguments.of("alfa  ", "alfa", 6),
                Arguments.of("  alfa", "alfa", 6),
                Arguments.of("  alfa  ", "alfa", 6),
                Arguments.of("  alfa  ", "..", 2),
                Arguments.of("  alfa", "alfa", 4),
                Arguments.of("alfa", "alfa", 6),
                Arguments.of("alfetta", "alf...", 6),
                Arguments.of("alfetta", "alfetta", 8),
                Arguments.of(" alfetta ", "...", 3)
        );
    }



    //--src
    //--risultato
    //--length
    protected static Stream<Arguments> FIX_SIZE() {
        return Stream.of(
                Arguments.of(VUOTA, VUOTA, 0),
                Arguments.of(VUOTA, "   ", 3),
                Arguments.of("alfa", VUOTA, 0),
                Arguments.of("alfa", "al", 2),
                Arguments.of("alfa", "alfa", 4),
                Arguments.of("alfa  ", "alfa  ", 6),
                Arguments.of("  alfa", "alfa  ", 6),
                Arguments.of("  alfa  ", "alfa  ", 6),
                Arguments.of("  alfa  ", "al", 2),
                Arguments.of("  alfa", "alfa", 4),
                Arguments.of("alfa", "alfa  ", 6),
                Arguments.of("alfetta", "alfett", 6),
                Arguments.of("alfetta", "alfetta ", 8),
                Arguments.of(" alfetta ", "alf", 3)
        );
    }


    //--src
    //--risultato
    //--length
    protected static Stream<Arguments> FIX_SIZE_PUNTI() {
        return Stream.of(
                Arguments.of(VUOTA, VUOTA, 0),
                Arguments.of(VUOTA, "...", 3),
                Arguments.of("alfa", VUOTA, 0),
                Arguments.of("alfa", "..", 2),
                Arguments.of("alfa", "alfa", 4),
                Arguments.of("alfa  ", "alfa  ", 6),
                Arguments.of("  alfa", "alfa  ", 6),
                Arguments.of("  alfa  ", "alfa  ", 6),
                Arguments.of("  alfa  ", "..", 2),
                Arguments.of("  alfa", "alfa", 4),
                Arguments.of("alfa", "alfa  ", 6),
                Arguments.of("alfetta", "alf...", 6),
                Arguments.of("alfetta", "alfetta ", 8),
                Arguments.of(" alfetta ", "...", 3)
        );
    }



    /**
     * Qui passa una volta sola, chiamato dalle sottoclassi <br>
     * Invocare PRIMA il metodo setUpStartUp() della superclasse <br>
     * Si possono aggiungere regolazioni specifiche <br>
     */
    @BeforeAll
    protected void setUpAll() {
        super.setUpAll();

        //--reindirizzo l'istanza della superclasse
        service = textService;
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


    @ParameterizedTest
    @MethodSource(value = "RIGHT_PAD")
    @Order(1)
    @DisplayName("1 - rightPad")
    void rightPad(final String src, final String risultato, final int length) {
        sorgente = src;
        sorgenteIntero = length;
        previsto = risultato;
        ottenuto = service.rightPad(sorgente, sorgenteIntero);
        message = String.format("[%s] (%d) %s[%s]", sorgente, sorgenteIntero, FORWARD, ottenuto);
        System.out.println(message);
        assertEquals(previsto, ottenuto);
    }


    @ParameterizedTest
    @MethodSource(value = "FIX_SIZE")
    @Order(2)
    @DisplayName("2 - fixSize")
    void fixSize(final String src, final String risultato, final int length) {
        sorgente = src;
        sorgenteIntero = length;
        previsto = risultato;
        ottenuto = service.fixSize(sorgente, sorgenteIntero);
        message = String.format("[%s] (%d) %s[%s]", sorgente, sorgenteIntero, FORWARD, ottenuto);
        System.out.println(message);
        assertEquals(previsto, ottenuto);
    }

    @ParameterizedTest
    @MethodSource(value = "FIX_SIZE_PUNTI")
    @Order(3)
    @DisplayName("3 - fixSizePunti")
    void fixSizePunti(final String src, final String risultato, final int length) {
        sorgente = src;
        sorgenteIntero = length;
        previsto = risultato;
        ottenuto = service.fixSizePunti(sorgente, sorgenteIntero);
        message = String.format("[%s] (%d) %s[%s]", sorgente, sorgenteIntero, FORWARD, ottenuto);
        System.out.println(message);
        assertEquals(previsto, ottenuto);
    }


    @ParameterizedTest
    @MethodSource(value = "MAX_SIZE")
    @Order(4)
    @DisplayName("4 - maxSize")
    void maxSize(final String src, final String risultato, final int length) {
        sorgente = src;
        sorgenteIntero = length;
        previsto = risultato;
        ottenuto = service.maxSize(sorgente, sorgenteIntero);
        message = String.format("[%s] (%d) %s[%s]", sorgente, sorgenteIntero, FORWARD, ottenuto);
        System.out.println(message);
        assertEquals(previsto, ottenuto);
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
