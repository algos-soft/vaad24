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
    protected static Stream<Arguments> SECOLO() {
        return Stream.of(
                Arguments.of(VUOTA, false, false),
                Arguments.of("aprile", false, false),
                Arguments.of("XX secolo", false, true),
                Arguments.of("xxsecolo", true, false)
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
                Arguments.of("inizio", 401, true)
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
    void findByAnnoAC() {
        System.out.println("35 - findByAnnoAC");
        System.out.println(VUOTA);
        System.out.println("Secolo ricavato dall'anno a.C. (senza segno meno)");
        System.out.println(VUOTA);

        sorgenteIntero = 4;
        entityBean = backend.findByAnnoAC(sorgenteIntero);
        assertNotNull(entityBean);
        ottenuto = entityBean.toString();
        printValue(sorgenteIntero, ottenuto);
    }


    @Test
    @Order(36)
    @DisplayName("36 - findByAnnoDC")
    void findByAnnoDC() {
        System.out.println("36 - findByAnnoDC");
        System.out.println(VUOTA);
        System.out.println("Secolo ricavato dall'anno d.C. (senza segno più)");
        System.out.println(VUOTA);

        sorgenteIntero = 4;
        entityBean = backend.findByAnnoDC(sorgenteIntero);
        assertNotNull(entityBean);
        ottenuto = entityBean.toString();
        printValue(sorgenteIntero, ottenuto);

        sorgenteIntero = 1900;
        entityBean = backend.findByAnnoDC(sorgenteIntero);
        assertNotNull(entityBean);
        ottenuto = entityBean.toString();
        printValue(sorgenteIntero, ottenuto);

        sorgenteIntero = 1901;
        entityBean = backend.findByAnnoDC(sorgenteIntero);
        assertNotNull(entityBean);
        ottenuto = entityBean.toString();
        printValue(sorgenteIntero, ottenuto);

        sorgenteIntero = 1999;
        entityBean = backend.findByAnnoDC(sorgenteIntero);
        assertNotNull(entityBean);
        ottenuto = entityBean.toString();
        printValue(sorgenteIntero, ottenuto);

        sorgenteIntero = 2000;
        entityBean = backend.findByAnnoDC(sorgenteIntero);
        assertNotNull(entityBean);
        ottenuto = entityBean.toString();
        printValue(sorgenteIntero, ottenuto);

        sorgenteIntero = 2001;
        entityBean = backend.findByAnnoDC(sorgenteIntero);
        assertNotNull(entityBean);
        ottenuto = entityBean.toString();
        printValue(sorgenteIntero, ottenuto);
    }


    @Test
    @Order(41)
    @DisplayName("41 - creaIfNotExist")
    protected void creaIfNotExist() {
        System.out.println("41 - creaIfNotExist");
        System.out.println(VUOTA);

        sorgente = "XX secolo";
        ottenutoBooleano = super.creaIfNotExist(sorgente);
        assertFalse(ottenutoBooleano);
        System.out.println(VUOTA);

        sorgente = "termidoro";
        ottenutoBooleano = super.creaIfNotExist(sorgente);
        assertTrue(ottenutoBooleano);

        entityBean = backend.findByKey(sorgente);
        assertNotNull(entityBean);
        ottenutoBooleano = backend.delete(entityBean);
        assertTrue(ottenutoBooleano);

        ottenutoBooleano = crudBackend.isExistByKey(sorgente);
        assertFalse(ottenutoBooleano);
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


}
