package it.algos.backend;

import it.algos.*;
import it.algos.base.*;
import static it.algos.vaad24.backend.boot.VaadCost.*;
import it.algos.vaad24.backend.packages.crono.secolo.*;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.provider.*;
import org.springframework.boot.test.context.*;

import java.util.*;
import java.util.stream.*;

/**
 * Project vaad24
 * Created by Algos
 * User: gac
 * Date: Wed, 22-Feb-2023
 * Time: 18:30
 */
@SpringBootTest(classes = {Vaad24SimpleApp.class})
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Tag("backend")
@DisplayName("Secolo Backend")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class SecoloBackendTest extends BackendTest {

    private SecoloBackend backend;

    //--nome nella collection
    //--esiste come ID
    //--esiste come key
    //--crea una nuova entity
    protected static Stream<Arguments> SECOLO() {
        return Stream.of(
                Arguments.of(VUOTA, false, false, false),
                Arguments.of("aprile", false, false, true),
                Arguments.of("XX secolo", false, true, false),
                Arguments.of("xxsecolo", true, false, false)
        );
    }

    //--nome della property
    //--value della property
    //--esiste entityBean
    public static Stream<Arguments> PROPERTY() {
        return Stream.of(
                Arguments.of(VUOTA, VUOTA, false),
                Arguments.of("propertyInesistente", "valoreInesistente", false),
                Arguments.of("inizio", 374, false),
                Arguments.of("inizio", 399, false),
                Arguments.of("inizio", 400, true),
                Arguments.of("inizio", 401, true),
                Arguments.of("fine", 401, true),
                Arguments.of("fine", 599, false),
                Arguments.of("fine", 600, true)
        );
    }

    //--value ordine
    //--esiste entityBean
    public static Stream<Arguments> ORDINE() {
        return Stream.of(
                Arguments.of(0, false),
                Arguments.of(847, false),
                Arguments.of(4, true),
                Arguments.of(27, true),
                Arguments.of(35, false),
                Arguments.of(-4, false)
        );
    }

    //--value anno
    //--esiste AC
    //--esiste DC
    //--anteCristo
    private static Stream<Arguments> ANNO() {
        return Stream.of(
                Arguments.of(0, false, false, true),
                Arguments.of(0, false, false, false),
                Arguments.of(847, true, true, true),
                Arguments.of(847, true, true, false),
                Arguments.of(1937, false, true, true),
                Arguments.of(1937, false, true, false),
                Arguments.of(35, true, true, true),
                Arguments.of(35, true, true, false),
                Arguments.of(-4, false, false, true),
                Arguments.of(-4, false, false, false),
                Arguments.of(1899, false, true, false),
                Arguments.of(1900, false, true, false),
                Arguments.of(1901, false, true, false),
                Arguments.of(1999, false, true, false),
                Arguments.of(2000, false, true, false),
                Arguments.of(2001, false, true, false)
        );
    }


    /**
     * Qui passa una volta sola <br>
     */
    @BeforeAll
    protected void setUpAll() {
        this.backend = super.secoloBackend;
        super.entityClazz = Secolo.class;
        super.typeBackend = TypeBackend.secolo;
        super.crudBackend = backend;

        super.setUpAll();
    }


    @BeforeEach
    protected void setUpEach() {
        super.setUpEach();

        super.streamCollection = SECOLO();
        super.streamProperty = PROPERTY();
        super.streamOrder = ORDINE();
    }

    @Test
    @Order(35)
    @DisplayName("35 - findByAnnoAC")
    protected void findByAnnoAC() {
        System.out.println("35 - findByAnnoAC");
        System.out.println(VUOTA);
        System.out.println("Secolo ricavato dall'anno a.C. (senza segno meno)");
        System.out.println(VUOTA);

        ANNO().forEach(parameters -> this.findByAnnoAC(parameters));
    }

    //--value anno
    //--esiste AC
    //--esiste DC
    //--anteCristo
    protected void findByAnnoAC(Arguments arg) {
        Object[] mat = arg.get();
        boolean anteCristo = false;
        if (mat != null && mat.length > 0 && mat[0] instanceof Integer keyValue) {
            sorgenteIntero = keyValue;
        }
        else {
            assertTrue(false);
        }
        if (mat != null && mat.length > 1 && mat[1] instanceof Boolean keyValue) {
            previstoBooleano = keyValue;
        }
        else {
            assertTrue(false);
        }
        if (mat != null && mat.length > 3 && mat[3] instanceof Boolean keyValue) {
            anteCristo = keyValue;
        }
        else {
            assertTrue(false);
        }

        if (!anteCristo) {
            return;
        }

        entityBean = backend.findByAnnoAC(sorgenteIntero);
        if (entityBean != null) {
            message = String.format("All'anno '%d' (AC) CORRISPONDE il secolo [%s]", sorgenteIntero, entityBean.toString());
        }
        else {
            message = String.format("All'anno '%d' (AC) NON corrisponde nessun secolo", sorgenteIntero);
        }
        System.out.println(message);
        assertEquals(previstoBooleano, entityBean != null);
    }

    @Test
    @Order(36)
    @DisplayName("36 - findByAnnoDC")
    protected void findByAnnoDC() {
        System.out.println("36 - findByAnnoDC");
        System.out.println(VUOTA);
        System.out.println("Secolo ricavato dall'anno d.C. (senza segno più)");
        System.out.println(VUOTA);

        ANNO().forEach(parameters -> this.findByAnnoDC(parameters));
    }

    //--value anno
    //--esiste AC
    //--esiste DC
    //--anteCristo
    protected void findByAnnoDC(Arguments arg) {
        Object[] mat = arg.get();
        boolean anteCristo = false;
        if (mat != null && mat.length > 0 && mat[0] instanceof Integer keyValue) {
            sorgenteIntero = keyValue;
        }
        else {
            assertTrue(false);
        }
        if (mat != null && mat.length > 2 && mat[2] instanceof Boolean keyValue) {
            previstoBooleano = keyValue;
        }
        else {
            assertTrue(false);
        }
        if (mat != null && mat.length > 3 && mat[3] instanceof Boolean keyValue) {
            anteCristo = keyValue;
        }
        else {
            assertTrue(false);
        }

        if (anteCristo) {
            return;
        }

        entityBean = backend.findByAnnoDC(sorgenteIntero);
        if (entityBean != null) {
            message = String.format("All'anno '%d' (DC) CORRISPONDE il secolo [%s]", sorgenteIntero, entityBean.toString());
        }
        else {
            message = String.format("All'anno '%d' (DC) NON corrisponde nessun secolo", sorgenteIntero);
        }
        System.out.println(message);
        assertEquals(previstoBooleano, entityBean != null);
    }


    @Test
    @Order(42)
    @DisplayName("42 - newEntity con ID ma non registrata")
    protected void newEntity() {
        System.out.println("143 - newEntity con ID ma non registrata");
        System.out.println(VUOTA);
        Secolo secolo;

        sorgenteIntero = 4567;
        sorgente = "XXX secolo";
        previsto = "xxxsecolo";
        int sorgenteIntero2 = 88;
        int sorgenteIntero3 = 431;
        boolean anteCristo = false;

        entityBean = backend.newEntity(sorgenteIntero, sorgente, sorgenteIntero2, sorgenteIntero3, anteCristo);
        assertNotNull(entityBean);
        assertTrue(entityBean instanceof Secolo);
        secolo = (Secolo) entityBean;
        assertEquals(previsto, secolo.id);
        assertEquals(sorgenteIntero, secolo.ordine);
        assertEquals(sorgente, secolo.nome);
        assertEquals(sorgenteIntero2, secolo.inizio);
        assertEquals(sorgenteIntero3, secolo.fine);
        assertFalse(secolo.anteCristo);
        message = String.format("Creata correttamente (SOLO IN MEMORIA) la entity: [%s] con keyPropertyName%s'%s'", entityBean.id, FORWARD, entityBean);
        System.out.println(message);
        printBackend(List.of(entityBean));
    }

    @Test
    @Order(81)
    @DisplayName("81 - Secolo ricavato da findDocumentById")
    protected void findDocumentById() {
        System.out.println("81 - Secolo ricavato da findDocumentById");
        System.out.println(VUOTA);

        sorgente = "ixsecoloa.c.";
        entityBean = backend.findDocumentById(sorgente);
        assertNotNull(entityBean);
        assertTrue(entityBean instanceof Secolo);
    }
    @Test
    @Order(82)
    @DisplayName("82 - Secolo ricavato da findDocumentByKey")
    protected void findDocumentByKey() {
        System.out.println("82 - Secolo ricavato da findDocumentByKey");
        System.out.println(VUOTA);

        sorgente = "V secolo";
        entityBean = backend.findDocumentByKey(sorgente);
        assertNotNull(entityBean);
        assertTrue(entityBean instanceof Secolo);
    }

}
