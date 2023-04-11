package it.algos.backend;

import it.algos.*;
import it.algos.base.*;
import static it.algos.vaad24.backend.boot.VaadCost.*;
import it.algos.vaad24.backend.packages.anagrafica.*;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.provider.*;
import org.springframework.boot.test.context.*;

import java.util.stream.*;

/**
 * Project vaad24
 * Created by Algos
 * User: gac
 * Date: Wed, 22-Feb-2023
 * Time: 18:26
 */
@SpringBootTest(classes = {Vaad24SimpleApp.class})
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Tag("backend")
@DisplayName("Via Backend")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ViaBackendTest extends BackendTest {

    private ViaBackend backend;

    //--nome nella collection
    //--esiste come ID
    //--esiste come key
    protected static Stream<Arguments> VIA() {
        return Stream.of(
                Arguments.of(VUOTA, false, false),
                Arguments.of("piazzale", true, true),
                Arguments.of("termidoro", false, false),
                Arguments.of("brumaio", false, false),
                Arguments.of("galleria", true, true)
        );
    }

    //--nome della property
    //--value della property
    //--esiste entityBean
    public static Stream<Arguments> PROPERTY() {
        return Stream.of(
                Arguments.of(VUOTA, VUOTA, false),
                Arguments.of("propertyInesistente", "valoreInesistente", false),
                Arguments.of("nome", "gennaio", false),
                Arguments.of("nome", "porta", true)
        );
    }


    /**
     * Qui passa una volta sola <br>
     */
    @BeforeAll
    protected void setUpAll() {
        this.backend = super.viaBackend;
        super.entityClazz = Via.class;
        super.typeBackend = TypeBackend.via;
        super.crudBackend = backend;

        super.setUpAll();
    }

    @BeforeEach
    protected void setUpEach() {
        super.setUpEach();

        super.streamCollection = VIA();
        super.streamProperty = PROPERTY();
    }

//    @Test
//    @Order(21)
//    @DisplayName("21 - isExistById")
//    protected void isExistById() {
//        System.out.println("21 - isExistById");
//        System.out.println(VUOTA);
//
////        sorgente = "sbagliato";
////        ottenutoBooleano = super.isExistById(sorgente);
////        assertFalse(ottenutoBooleano);
////        System.out.println(VUOTA);
////
////        sorgente = "galleria";
////        ottenutoBooleano = super.isExistById(sorgente);
////        assertTrue(ottenutoBooleano);
//    }
//
//
//    @Test
//    @Order(22)
//    @DisplayName("22 - isExistByKey")
//    protected void isExistByKey() {
//        System.out.println("22 - isExistByKey");
//        System.out.println(VUOTA);
//
////        sorgente = "termidoro";
////        ottenutoBooleano = super.isExistByKey(sorgente);
////        assertFalse(ottenutoBooleano);
////        System.out.println(VUOTA);
////
////        sorgente = "quartiere";
////        ottenutoBooleano = super.isExistByKey(sorgente);
////        assertTrue(ottenutoBooleano);
//    }
//
//
//    @Test
//    @Order(23)
//    @DisplayName("23 - isExistByOrder")
//    protected void isExistByOrder() {
//        System.out.println("23 - isExistByOrder");
//        System.out.println(VUOTA);
//
////        sorgenteIntero = 87;
////        ottenutoBooleano = super.isExistByOrder(sorgenteIntero);
////        assertFalse(ottenutoBooleano);
////        System.out.println(VUOTA);
////
////        sorgenteIntero = 0;
////        ottenutoBooleano = super.isExistByOrder(sorgenteIntero);
////        assertFalse(ottenutoBooleano);
//    }
//
//
//    @Test
//    @Order(24)
//    @DisplayName("24 - isExistByProperty")
//    protected void isExistByProperty() {
//        System.out.println("24 - isExistByProperty");
//        System.out.println(VUOTA);
//
////        sorgente = "propertyInesistente";
////        sorgente2 = "termidoro";
////        ottenutoBooleano = super.isExistByProperty(sorgente, sorgente2);
////        assertFalse(ottenutoBooleano);
////        System.out.println(VUOTA);
////
////        sorgente = "nome";
////        sorgente2 = "termidoro";
////        ottenutoBooleano = super.isExistByProperty(sorgente, sorgente2);
////        assertFalse(ottenutoBooleano);
////        System.out.println(VUOTA);
////
////        sorgente = "nome";
////        sorgente2 = "sentiero";
////        ottenutoBooleano = super.isExistByProperty(sorgente, sorgente2);
////        assertTrue(ottenutoBooleano);
//    }
//
//    @Test
//    @Order(31)
//    @DisplayName("31 - findById")
//    protected void findById() {
//        System.out.println("31 - findById");
//        System.out.println(VUOTA);
//
////        sorgente = "sbagliato";
////        entityBean = super.findById(sorgente);
////        assertNull(entityBean);
////        System.out.println(VUOTA);
////
////        sorgente = "galleria";
////        entityBean = super.findById(sorgente);
////        assertNotNull(entityBean);
//    }
//
//    @Test
//    @Order(32)
//    @DisplayName("32 - findByKey")
//    protected void findByKey() {
//        System.out.println("32 - findByKey");
//        System.out.println(VUOTA);
//
////        sorgente = "termidoro";
////        entityBean = super.findByKey(sorgente);
////        assertNull(entityBean);
////        System.out.println(VUOTA);
////
////        sorgente = "quartiere";
////        entityBean = super.findByKey(sorgente);
////        assertNotNull(entityBean);
//    }
//
//    @Test
//    @Order(33)
//    @DisplayName("33 - findByOrder")
//    protected void findByOrder() {
//        System.out.println("33 - findByOrder");
//        System.out.println(VUOTA);
//
////        sorgenteIntero = 87;
////        entityBean = super.findByOrder(sorgenteIntero);
////        assertNull(entityBean);
////        System.out.println(VUOTA);
////
////        sorgenteIntero = 0;
////        entityBean = super.findByOrder(sorgenteIntero);
////        assertNull(entityBean);
//    }
//
//
//    @Test
//    @Order(34)
//    @DisplayName("34 - findByProperty")
//    protected void findByProperty() {
//        System.out.println("34 - findByProperty");
//        System.out.println(VUOTA);
//
////        sorgente = "propertyInesistente";
////        sorgente2 = "termidoro";
////        entityBean = super.findByProperty(sorgente, sorgente2);
////        assertNull(entityBean);
////        System.out.println(VUOTA);
////
////        sorgente = "nome";
////        sorgente2 = "termidoro";
////        entityBean = super.findByProperty(sorgente, sorgente2);
////        assertNull(entityBean);
////        System.out.println(VUOTA);
////
////        sorgente = "nome";
////        sorgente2 = "sentiero";
////        entityBean = super.findByProperty(sorgente, sorgente2);
////        assertNotNull(entityBean);
////        System.out.println(VUOTA);
//    }

}
