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

/**
 * Project vaad24
 * Created by Algos
 * User: gac
 * Date: Wed, 25-Jan-2023
 * Time: 11:19
 */
@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = {Wiki24App.class})
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Tag("service")
@DisplayName("Math Service")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class MathServiceTest extends AlgosTest {


    /**
     * Classe principale di riferimento <br>
     * Gia 'costruita' nella superclasse <br>
     */
    private MathService service;

    private double dividendoDouble;

    private double divisoreDouble;

    private int dividendoInt;

    private int divisoreInt;

    /**
     * Qui passa una volta sola, chiamato dalle sottoclassi <br>
     * Invocare PRIMA il metodo setUpStartUp() della superclasse <br>
     * Si possono aggiungere regolazioni specifiche <br>
     */
    @BeforeAll
    protected void setUpAll() {
        super.setUpAll();

        //--reindirizzo l'istanza della superclasse
        service = mathService;
    }


    /**
     * Qui passa a ogni test delle sottoclassi <br>
     * Invocare PRIMA il metodo setUp() della superclasse <br>
     * Si possono aggiungere regolazioni specifiche <br>
     */
    @BeforeEach
    protected void setUpEach() {
        super.setUpEach();
        dividendoDouble = 0;
        divisoreDouble = 0;
        ottenutoDouble = 0;
        dividendoInt = 0;
        divisoreInt = 0;
    }


    @Test
    @Order(1)
    @DisplayName("1 - Divisione double")
    void divisione() {
        System.out.println("1 - Divisione double");

        dividendoDouble = 7;
        divisoreDouble = 2;
        previstoDouble = 3.5;

        ottenutoDouble = service.divisione(dividendoDouble, divisoreDouble);
        assertEquals(previstoDouble, ottenutoDouble);
        printDiv(dividendoDouble, divisoreDouble, ottenutoDouble);
    }


    @Test
    @Order(2)
    @DisplayName("2 - Divisione interi")
    void divisione2() {
        System.out.println("2 - Divisione interi");

        dividendoInt = 7;
        divisoreInt = 2;
        previstoDouble = 3.5;
        ottenutoDouble = service.divisione(dividendoInt, divisoreInt);
        assertEquals(previstoDouble, ottenutoDouble);
        printDiv(dividendoInt, divisoreInt, ottenutoDouble);

        dividendoInt = 450;
        divisoreInt = 25;
        previstoDouble = 18;
        ottenutoDouble = service.divisione(dividendoInt, divisoreInt);
        assertEquals(previstoDouble, ottenutoDouble);
        printDiv(dividendoInt, divisoreInt, ottenutoDouble);

        dividendoInt = 450;
        divisoreInt = 25;
        previstoDouble = 18;
        ottenutoDouble = service.divisione(dividendoInt, divisoreInt);
        assertEquals(previstoIntero, ottenutoIntero);
        printDiv(dividendoInt, divisoreInt, ottenutoDouble);
    }


    @Test
    @Order(3)
    @DisplayName("3 - Percentuale")
    void percentuale() {
        System.out.println("3 - Percentuale");

        dividendoInt = 8;
        divisoreInt = 200;
        previstoDouble = 4;
        ottenutoDouble = service.percentuale(dividendoInt, divisoreInt);
        assertNotNull(ottenutoDouble);
        assertEquals(previstoDouble, ottenutoDouble);
        printPer(dividendoInt, divisoreInt, ottenutoDouble);
    }


    @Test
    @Order(4)
    @DisplayName("4 - Percentuale come testo con due decimali")
    void percentuale2() {
        System.out.println("4 - Percentuale come testo con due decimali");

        dividendoInt = 8;
        divisoreInt = 200;
        previsto = "4,00%";
        ottenuto = service.percentualeTxt(dividendoInt, divisoreInt);
        assertNotNull(ottenuto);
        assertEquals(previsto, ottenuto);
        printPer(dividendoInt, divisoreInt, ottenuto);

        dividendoInt = 8;
        divisoreInt = 19;
        previsto = "42,11%";
        ottenuto = service.percentualeTxt(dividendoInt, divisoreInt);
        assertNotNull(ottenuto);
        assertEquals(previsto, ottenuto);
        printPer(dividendoInt, divisoreInt, ottenuto);

        dividendoInt = 8;
        divisoreInt = 190;
        previsto = "4,21%";
        ottenuto = service.percentualeTxt(dividendoInt, divisoreInt);
        assertNotNull(ottenuto);
        assertEquals(previsto, ottenuto);
        printPer(dividendoInt, divisoreInt, ottenuto);

        dividendoInt = 8;
        divisoreInt = 1900;
        previsto = "0,42%";
        ottenuto = service.percentualeTxt(dividendoInt, divisoreInt);
        assertNotNull(ottenuto);
        assertEquals(previsto, ottenuto);
        printPer(dividendoInt, divisoreInt, ottenuto);

        dividendoInt = 8;
        divisoreInt = 19000;
        previsto = "0,04%";
        ottenuto = service.percentualeTxt(dividendoInt, divisoreInt);
        assertNotNull(ottenuto);
        assertEquals(previsto, ottenuto);
        printPer(dividendoInt, divisoreInt, ottenuto);


        System.out.println(VUOTA);
        dividendoInt = 1476;
        divisoreInt = 464908;
        previsto = "0,32%";
        ottenuto = service.percentualeTxt(dividendoInt, divisoreInt);
        assertNotNull(ottenuto);
        assertEquals(previsto, ottenuto);
        printPer(dividendoInt, divisoreInt, ottenuto);
    }


    @Test
    @Order(5)
    @DisplayName("Numero di cicli")
    void numCicli() {
        int totale = 100;
        int blocco = 25;

        //        ottenutoIntero = service.numCicli(totale, blocco);
        //        assertEquals(previstoIntero, ottenutoIntero);
        //        stampa(totale, blocco, ottenutoIntero);
        //
        //        totale = 90;
        //        blocco = 25;
        //        previstoIntero = 4;
        //        ottenutoIntero = service.numCicli(totale, blocco);
        //        assertEquals(previstoIntero, ottenutoIntero);
        //        stampa(totale, blocco, ottenutoIntero);
        //
        //        totale = 90;
        //        blocco = 90;
        //        previstoIntero = 1;
        //        ottenutoIntero = service.numCicli(totale, blocco);
        //        assertEquals(previstoIntero, ottenutoIntero);
        //        stampa(totale, blocco, ottenutoIntero);
        //
        //        totale = 70;
        //        blocco = 90;
        //        previstoIntero = 1;
        //        ottenutoIntero = service.numCicli(totale, blocco);
        //        assertEquals(previstoIntero, ottenutoIntero);
        //        stampa(totale, blocco, ottenutoIntero);
    }


    @Test
    @Order(6)
    @DisplayName("Divisibile esatto")
    void divisibileEsatto() {
        //        divisore = 4;

        //        dividendo = 1;
        //        ottenutoBooleano = service.divisibileEsatto(dividendo, divisore);
        //        assertFalse(ottenutoBooleano);
        //
        //        dividendo = 2;
        //        ottenutoBooleano = service.divisibileEsatto(dividendo, divisore);
        //        assertFalse(ottenutoBooleano);
        //
        //        dividendo = 3;
        //        ottenutoBooleano = service.divisibileEsatto(dividendo, divisore);
        //        assertFalse(ottenutoBooleano);
        //
        //        dividendo = 4;
        //        ottenutoBooleano = service.divisibileEsatto(dividendo, divisore);
        //        assertTrue(ottenutoBooleano);
        //
        //        dividendo = 5;
        //        ottenutoBooleano = service.divisibileEsatto(dividendo, divisore);
        //        assertFalse(ottenutoBooleano);
        //
        //        dividendo = 6;
        //        ottenutoBooleano = service.divisibileEsatto(dividendo, divisore);
        //        assertFalse(ottenutoBooleano);
        //
        //        dividendo = 7;
        //        ottenutoBooleano = service.divisibileEsatto(dividendo, divisore);
        //        assertFalse(ottenutoBooleano);
        //
        //        dividendo = 8;
        //        ottenutoBooleano = service.divisibileEsatto(dividendo, divisore);
        //        assertTrue(ottenutoBooleano);
        //
        //        dividendo = 9;
        //        ottenutoBooleano = service.divisibileEsatto(dividendo, divisore);
        //        assertFalse(ottenutoBooleano);
        //
        //        dividendo = 10;
        //        ottenutoBooleano = service.divisibileEsatto(dividendo, divisore);
        //        assertFalse(ottenutoBooleano);
    }


    private void printDiv(double dividendoDouble, double divisoreDouble, double risultatoDouble) {
        System.out.println(VUOTA);
        System.out.println("Valori double ingresso e uscita");

        System.out.println(String.format("Dividendo: %.2f", dividendoDouble));
        System.out.println(String.format("Divisore: %.2f", divisoreDouble));
        System.out.println(String.format("Risultato: %.2f", risultatoDouble));
    }

    private void printDiv(int dividendoInt, int divisoreInt, double risultatoDouble) {
        System.out.println(VUOTA);
        System.out.println("Valori interi ingresso e double in uscita");

        System.out.println(String.format("Dividendo: %d", dividendoInt));
        System.out.println(String.format("Divisore: %d", divisoreInt));
        System.out.println(String.format("Risultato: %.2f", risultatoDouble));
    }
    private void printPer(int percentuale, int valoreTotale, double risultatoDouble) {
        System.out.println(VUOTA);
        System.out.println("Valori interi ingresso e double in uscita");

        System.out.println(String.format("Percentuale: %d", percentuale));
        System.out.println(String.format("ValoreTotale: %d", valoreTotale));
        System.out.println(String.format("Risultato: %.2f", risultatoDouble));
    }

    private void printPer(int percentuale, int valoreTotale, String risultatoTxt) {
        System.out.println(VUOTA);
        System.out.println("Valori interi ingresso e stringa in uscita");

        System.out.println(String.format("Percentuale: %d", percentuale));
        System.out.println(String.format("ValoreTotale: %d", valoreTotale));
        System.out.println(String.format("Risultato: %s", risultatoTxt));
    }



    private void stampa(int totale, int blocco, int cicli) {
        System.out.println("Per spazzolare " + totale + " elementi a blocchi di " + blocco + " occorrono " + cicli + " cicli");
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