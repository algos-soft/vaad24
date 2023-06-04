package it.algos.service;

import it.algos.*;
import it.algos.base.*;
import static it.algos.vaad24.backend.boot.VaadCost.*;
import it.algos.vaad24.backend.enumeration.*;
import it.algos.vaad24.backend.service.*;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.extension.*;
import org.junit.jupiter.params.*;
import org.junit.jupiter.params.provider.*;
import org.springframework.boot.test.context.*;
import org.springframework.test.context.junit.jupiter.*;

import java.time.*;
import java.util.stream.*;

/**
 * Project vaad24
 * Created by Algos
 * User: gac
 * Date: Fri, 21-Apr-2023
 * Time: 10:06
 */
@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = {Vaad24SimpleApp.class})
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Tag("service")
@DisplayName("Date Service")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class DateServiceTest extends AlgosTest {

    /**
     * Classe principale di riferimento <br>
     * Gia 'costruita' nella superclasse <br>
     */
    private DateService service;

    protected static LocalDateTime LOCAL_DATE_TIME_UNO = LocalDateTime.of(2014, 10, 21, 8, 52, 44);

    protected static LocalDateTime LOCAL_DATE_TIME_DUE = LocalDateTime.of(2017, 4, 18, 14, 7, 20);

    protected static LocalDateTime LOCAL_DATE_TIME_UNO_MEZZANOTTE = LocalDateTime.of(2014, 10, 21, 0, 0, 0);

    protected static LocalDateTime LOCAL_DATE_TIME_DUE_MEZZANOTTE = LocalDateTime.of(2017, 4, 18, 0, 0, 0);

    protected static LocalDate LOCAL_DATE_UNO = LocalDate.of(2014, 10, 21);

    protected static LocalDate LOCAL_DATE_DUE = LocalDate.of(2017, 4, 18);

    protected static LocalDate LOCAL_DATE_TRE = LocalDate.of(2015, 10, 21);

    protected static LocalDate LOCAL_DATE_QUATTRO = LocalDate.of(2015, 4, 18);


    protected static LocalTime LOCAL_TIME_UNO = LocalTime.of(8, 52, 44);

    protected static LocalTime LOCAL_TIME_DUE = LocalTime.of(14, 7, 20);

    //--date_time
    //--iso8601
    //--standard
    //--short
    //--normal
    //--completa
    //--completa short
    //--normale orario short
    protected static Stream<Arguments> DATE_TIME() {
        return Stream.of(
                Arguments.of(LOCAL_DATE_TIME_UNO, "2014-10-21T08:52:44", "21 ott 2014", "21-10-14", "21-ott-14", "martedì, 21-ottobre-2014", "mar, 21-ott-2014", "21-ott-14 8:52"),
                Arguments.of(LOCAL_DATE_TIME_DUE, "2017-04-18T14:07:20", "18 apr 2017", "18-4-17", "18-apr-17", "martedì, 18-aprile-2017", "mar, 18-apr-2017", "18-apr-17 14:07"),
                Arguments.of(LOCAL_DATE_TIME_UNO_MEZZANOTTE, "2014-10-21T00:00:00", "21 ott 2014", "21-10-14", "21-ott-14", "martedì, 21-ottobre-2014", "mar, 21-ott-2014", "21-ott-14 0:00"),
                Arguments.of(LOCAL_DATE_TIME_DUE_MEZZANOTTE, "2017-04-18T00:00:00", "18 apr 2017", "18-4-17", "18-apr-17", "martedì, 18-aprile-2017", "mar, 18-apr-2017", "18-apr-17 0:00")
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
        service = dateService;
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
    @MethodSource(value = "DATE_TIME")
    @Order(1)
    @DisplayName("1 - iso8601")
        //--date_time
        //--iso8601
        //--standard
        //--short
        //--normal
        //--completa
        //--completa short
        //--normale orario short
    void iso8601(LocalDateTime dateTime, String iso8601) {
        message = String.format("1 - %s%s%s", "Tag", FORWARD, AETypeDate.iso8601.getTag());
        System.out.println(message);
        message = String.format("1 - %s%s%s", "Pattern", FORWARD, AETypeDate.iso8601.getPattern());
        System.out.println(message);
        System.out.println(VUOTA);

        previsto = iso8601;
        ottenuto = service.getISO(dateTime);
        message = String.format("%s%s%s", "DateTime", FORWARD, dateTime);
        System.out.println(message);
        message = String.format("%s%s%s", "Previsto", FORWARD, previsto);
        System.out.println(message);
        message = String.format("%s%s%s", "Risultato", FORWARD, ottenuto);
        System.out.println(message);
        assertEquals(previsto, ottenuto);
    }

    @ParameterizedTest
    @MethodSource(value = "DATE_TIME")
    @Order(2)
    @DisplayName("2 - standard")
        //--date_time
        //--iso8601
        //--standard
        //--short
        //--normal
        //--completa
        //--completa short
        //--normale orario short
    void standard(LocalDateTime dateTime, String iso8601, String standard, String corta, String normal, String completa, String completaShort, String normaleOrario) {
        message = String.format("2 - %s%s%s", "Tag", FORWARD, AETypeDate.standard.getTag());
        System.out.println(message);
        message = String.format("2 - %s%s%s", "Pattern", FORWARD, AETypeDate.standard.getPattern());
        System.out.println(message);
        System.out.println(VUOTA);

        previsto = standard;
        ottenuto = service.getStandard(dateTime);
        message = String.format("%s%s%s", "DateTime", FORWARD, dateTime);
        System.out.println(message);
        message = String.format("%s%s%s", "Previsto", FORWARD, previsto);
        System.out.println(message);
        message = String.format("%s%s%s", "Risultato", FORWARD, ottenuto);
        System.out.println(message);
        assertEquals(previsto, ottenuto);
    }

    @ParameterizedTest
    @MethodSource(value = "DATE_TIME")
    @Order(3)
    @DisplayName("3 - dateShort")
        //--date_time
        //--iso8601
        //--standard
        //--short
        //--normal
        //--completa
        //--completa short
        //--normale orario short
    void dateShort(LocalDateTime dateTime, String iso8601, String standard, String dateShort) {
        message = String.format("3 - %s%s%s", "Tag", FORWARD, AETypeDate.dateShort.getTag());
        System.out.println(message);
        message = String.format("3 - %s%s%s", "Pattern", FORWARD, AETypeDate.dateShort.getPattern());
        System.out.println(message);
        System.out.println(VUOTA);

        previsto = dateShort;
        ottenuto = service.getShort(dateTime);
        message = String.format("%s%s%s", "DateTime", FORWARD, dateTime);
        System.out.println(message);
        message = String.format("%s%s%s", "Previsto", FORWARD, previsto);
        System.out.println(message);
        message = String.format("%s%s%s", "Risultato", FORWARD, ottenuto);
        System.out.println(message);
        assertEquals(previsto, ottenuto);
    }

    @ParameterizedTest
    @MethodSource(value = "DATE_TIME")
    @Order(4)
    @DisplayName("4 - normal")
        //--date_time
        //--iso8601
        //--standard
        //--short
        //--normal
        //--completa
        //--completa short
        //--normale orario short
    void normal(LocalDateTime dateTime, String iso8601, String standard, String dateShort, String normal) {
        message = String.format("4 - %s%s%s", "Tag", FORWARD, AETypeDate.dateNormal.getTag());
        System.out.println(message);
        message = String.format("4 - %s%s%s", "Pattern", FORWARD, AETypeDate.dateNormal.getPattern());
        System.out.println(message);
        System.out.println(VUOTA);

        previsto = normal;
        ottenuto = service.getNormale(dateTime);
        message = String.format("%s%s%s", "DateTime", FORWARD, dateTime);
        System.out.println(message);
        message = String.format("%s%s%s", "Previsto", FORWARD, previsto);
        System.out.println(message);
        message = String.format("%s%s%s", "Risultato", FORWARD, ottenuto);
        System.out.println(message);
        assertEquals(previsto, ottenuto);
    }

    @ParameterizedTest
    @MethodSource(value = "DATE_TIME")
    @Order(5)
    @DisplayName("5 - completa")
        //--date_time
        //--iso8601
        //--standard
        //--short
        //--normal
        //--completa
        //--completa short
        //--normale orario short
    void completa(LocalDateTime dateTime, String iso8601, String standard, String dateShort, String normal, String completa) {
        message = String.format("5 - %s%s%s", "Tag", FORWARD, AETypeDate.dataCompleta.getTag());
        System.out.println(message);
        message = String.format("5 - %s%s%s", "Pattern", FORWARD, AETypeDate.dataCompleta.getPattern());
        System.out.println(message);
        System.out.println(VUOTA);

        previsto = completa;
        ottenuto = service.getCompleta(dateTime);
        message = String.format("%s%s%s", "DateTime", FORWARD, dateTime);
        System.out.println(message);
        message = String.format("%s%s%s", "Previsto", FORWARD, previsto);
        System.out.println(message);
        message = String.format("%s%s%s", "Risultato", FORWARD, ottenuto);
        System.out.println(message);
        assertEquals(previsto, ottenuto);
    }


    @ParameterizedTest
    @MethodSource(value = "DATE_TIME")
    @Order(6)
    @DisplayName("6 - completaShort")
        //--date_time
        //--iso8601
        //--standard
        //--short
        //--normal
        //--completa
        //--completa short
        //--normale orario short
    void completaShort(LocalDateTime dateTime, String iso8601, String standard, String dateShort, String normal, String completa, String completaShort) {
        message = String.format("6 - %s%s%s", "Tag", FORWARD, AETypeDate.dataCompletaShort.getTag());
        System.out.println(message);
        message = String.format("6 - %s%s%s", "Pattern", FORWARD, AETypeDate.dataCompletaShort.getPattern());
        System.out.println(message);
        System.out.println(VUOTA);

        previsto = completaShort;
        ottenuto = service.getCompletaShort(dateTime);
        message = String.format("%s%s%s", "DateTime", FORWARD, dateTime);
        System.out.println(message);
        message = String.format("%s%s%s", "Previsto", FORWARD, previsto);
        System.out.println(message);
        message = String.format("%s%s%s", "Risultato", FORWARD, ottenuto);
        System.out.println(message);
        assertEquals(previsto, ottenuto);
    }


    @ParameterizedTest
    @MethodSource(value = "DATE_TIME")
    @Order(7)
    @DisplayName("7 - normaleOrario")
        //--date_time
        //--iso8601
        //--standard
        //--short
        //--normal
        //--completa
        //--completa short
        //--normale orario short
    void normaleOrario(LocalDateTime dateTime, String iso8601, String standard, String dateShort, String normal, String completa, String completaShort, String normaleOrario) {
        message = String.format("7 - %s%s%s", "Tag", FORWARD, AETypeDate.normaleOrario.getTag());
        System.out.println(message);
        message = String.format("7 - %s%s%s", "Pattern", FORWARD, AETypeDate.normaleOrario.getPattern());
        System.out.println(message);
        System.out.println(VUOTA);

        previsto = normaleOrario;
        ottenuto = service.getNormaleOrario(dateTime);
        message = String.format("%s%s%s", "DateTime", FORWARD, dateTime);
        System.out.println(message);
        message = String.format("%s%s%s", "Previsto", FORWARD, previsto);
        System.out.println(message);
        message = String.format("%s%s%s", "Risultato", FORWARD, ottenuto);
        System.out.println(message);
        assertEquals(previsto, ottenuto);
    }

    @Test
    @Order(8)
    @DisplayName("8 - getSingleType")
    void getType() {
        System.out.println("8 - Tutte le varie possibilità di visualizzare una data (dateTime)");
        System.out.println(VUOTA);
        System.out.println(LOCAL_DATE_TIME_DUE);
        System.out.println(VUOTA);

        AETypeDate.getAllEnums().forEach(type -> {
            ottenuto = service.get(LOCAL_DATE_TIME_DUE, type);
            message = String.format("%s%s%s%s%s%s", type.getTag(), " (", type.getPattern(), ")", FORWARD, ottenuto);
            System.out.println(message);
            System.out.println(VUOTA);
        });
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
