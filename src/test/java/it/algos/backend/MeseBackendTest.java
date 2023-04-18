package it.algos.backend;

import it.algos.*;
import it.algos.base.*;
import static it.algos.vaad24.backend.boot.VaadCost.*;
import it.algos.vaad24.backend.packages.crono.mese.*;
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
 * Time: 18:27
 */
@SpringBootTest(classes = {Vaad24SimpleApp.class})
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Tag("mese")
@Tag("backend")
@DisplayName("Mese Backend")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class MeseBackendTest extends BackendTest {


    protected MeseBackend backend;

    //--nome nella collection
    //--esiste come ID
    //--esiste come key
    //--crea una nuova entity
    protected static Stream<Arguments> MESE() {
        return Stream.of(
                Arguments.of(VUOTA, false, false, false),
                Arguments.of("aprile", true, true, false),
                Arguments.of("termidoro", false, false, true),
                Arguments.of("brumaio", false, false, true),
                Arguments.of("settembre", true, true, false)
        );
    }

    //--nome della property
    //--value della property
    //--esiste entityBean
    public static Stream<Arguments> PROPERTY() {
        return Stream.of(
                Arguments.of(VUOTA, VUOTA, false),
                Arguments.of("propertyInesistente", "valoreInesistente", false),
                Arguments.of("breve", "genn", false),
                Arguments.of("breve", "apr", true)
        );
    }

    //--value ordine
    //--esiste entityBean
    public static Stream<Arguments> ORDINE() {
        return Stream.of(
                Arguments.of(0, false),
                Arguments.of(847, false),
                Arguments.of(1, true),
                Arguments.of(12, true),
                Arguments.of(-5, false)
        );
    }

    /**
     * Qui passa una volta sola <br>
     */
    @BeforeAll
    protected void setUpAll() {
        this.backend = super.meseBackend;
        super.entityClazz = Mese.class;
        super.typeBackend = TypeBackend.mese;
        super.crudBackend = backend;

        super.setUpAll();
    }

    @BeforeEach
    protected void setUpEach() {
        super.setUpEach();

        super.streamCollection = MESE();
        super.streamProperty = PROPERTY();
        super.streamOrder = ORDINE();
    }


    @Test
    @Order(42)
    @DisplayName("42 - newEntity")
    protected void newEntity() {
        System.out.println("42 - newEntity");
        System.out.println(VUOTA);
        Mese mese;

        sorgenteIntero = 93;
        sorgente = "termidoro";
        sorgente2 = "ter";
        previsto = sorgente;
        int sorgenteIntero2 = 88;
        int sorgenteIntero3 = 431;
        int sorgenteIntero4 = 475;

        entityBean = backend.newEntity(sorgenteIntero, sorgente, sorgente2, sorgenteIntero2, sorgenteIntero3, sorgenteIntero4);
        assertTrue(entityBean instanceof Mese);
        assertNotNull(entityBean);
        mese = (Mese) entityBean;
        assertEquals(previsto, mese.id);
        assertEquals(sorgenteIntero, mese.ordine);
        assertEquals(sorgente, mese.nome);
        assertEquals(sorgente2, mese.breve);
        assertEquals(sorgenteIntero2, mese.giorni);
        assertEquals(sorgenteIntero3, mese.primo);
        assertEquals(sorgenteIntero4, mese.ultimo);
        message = String.format("Creata correttamente (SOLO IN MEMORIA) la entity: [%s] con keyPropertyName%s'%s'", entityBean.id, FORWARD, entityBean);
        System.out.println(message);
        printBackend(List.of(entityBean));
    }

}
