package it.algos.backend;

import it.algos.*;
import it.algos.base.*;
import static it.algos.vaad24.backend.boot.VaadCost.*;
import it.algos.vaad24.backend.packages.crono.mese.*;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.*;
import org.springframework.boot.test.context.*;

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

    @Test
    @Order(21)
    @DisplayName("21 - isExistById")
    protected void isExistById() {
        System.out.println("21 - isExistById");
        System.out.println(VUOTA);

        sorgente = "sbagliato";
        ottenutoBooleano = super.isExistById(sorgente);
        assertFalse(ottenutoBooleano);
        System.out.println(VUOTA);

        sorgente = "ottobre";
        ottenutoBooleano = super.isExistById(sorgente);
        assertTrue(ottenutoBooleano);
    }


    @Test
    @Order(22)
    @DisplayName("22 - isExistByKey")
    protected void isExistByKey() {
        System.out.println("22 - isExistByKey");
        System.out.println(VUOTA);

        sorgente = "termidoro";
        ottenutoBooleano = super.isExistByKey(sorgente);
        assertFalse(ottenutoBooleano);
        System.out.println(VUOTA);

        sorgente = "ottobre";
        ottenutoBooleano = super.isExistByKey(sorgente);
        assertTrue(ottenutoBooleano);
    }


    @Test
    @Order(23)
    @DisplayName("23 - isExistByOrder")
    protected void isExistByOrder() {
        System.out.println("23 - isExistByOrder");
        System.out.println(VUOTA);

        sorgenteIntero = 87;
        ottenutoBooleano = super.isExistByOrder(sorgenteIntero);
        assertFalse(ottenutoBooleano);
        System.out.println(VUOTA);

        sorgenteIntero = 6;
        ottenutoBooleano = super.isExistByOrder(sorgenteIntero);
        assertTrue(ottenutoBooleano);
    }


    @Test
    @Order(24)
    @DisplayName("24 - isExistByProperty")
    protected void isExistByProperty() {
        System.out.println("24 - isExistByProperty");
        System.out.println(VUOTA);

        sorgente = "propertyInesistente";
        sorgente2 = "termidoro";
        ottenutoBooleano = super.isExistByProperty(sorgente, sorgente2);
        assertFalse(ottenutoBooleano);
        System.out.println(VUOTA);

        sorgente = "breve";
        sorgente2 = "termidoro";
        ottenutoBooleano = super.isExistByProperty(sorgente, sorgente2);
        assertFalse(ottenutoBooleano);
        System.out.println(VUOTA);

        sorgente = "breve";
        sorgente2 = "apr";
        ottenutoBooleano = super.isExistByProperty(sorgente, sorgente2);
        assertTrue(ottenutoBooleano);
    }

    @Test
    @Order(31)
    @DisplayName("31 - findById")
    protected void findById() {
        System.out.println("31 - findById");
        System.out.println(VUOTA);

        sorgente = "sbagliato";
        entityBean = super.findById(sorgente);
        assertNull(entityBean);
        System.out.println(VUOTA);

        sorgente = "ottobre";
        entityBean = super.findById(sorgente);
        assertNotNull(entityBean);
    }

    @Test
    @Order(32)
    @DisplayName("32 - findByKey")
    protected void findByKey() {
        System.out.println("32 - findByKey");
        System.out.println(VUOTA);

        sorgente = "sbagliato";
        entityBean = super.findByKey(sorgente);
        assertNull(entityBean);
        System.out.println(VUOTA);

        sorgente = "ottobre";
        entityBean = super.findByKey(sorgente);
        assertNotNull(entityBean);
    }

    @Test
    @Order(33)
    @DisplayName("33 - findByOrder")
    protected void findByOrder() {
        System.out.println("33 - findByOrder");
        System.out.println(VUOTA);

        sorgenteIntero = 87;
        entityBean = super.findByOrder(sorgenteIntero);
        assertNull(entityBean);
        System.out.println(VUOTA);

        sorgenteIntero = 6;
        entityBean = super.findByOrder(sorgenteIntero);
        assertNotNull(entityBean);
    }


    @Test
    @Order(34)
    @DisplayName("34 - findByProperty")
    protected void findByProperty() {
        System.out.println("34 - findByProperty");
        System.out.println(VUOTA);

        sorgente = "propertyInesistente";
        sorgente2 = "termidoro";
        entityBean = super.findByProperty(sorgente, sorgente2);
        assertNull(entityBean);
        System.out.println(VUOTA);

        sorgente = "breve";
        sorgente2 = "termidoro";
        entityBean = super.findByProperty(sorgente, sorgente2);
        assertNull(entityBean);
        System.out.println(VUOTA);

        sorgente = "breve";
        sorgente2 = "apr";
        entityBean = super.findByProperty(sorgente, sorgente2);
        assertNotNull(entityBean);
        System.out.println(VUOTA);
    }


    @Test
    @Order(41)
    @DisplayName("41 - creaIfNotExist")
    protected void creaIfNotExist() {
        System.out.println("41 - creaIfNotExist");
        System.out.println(VUOTA);

        sorgente = "ottobre";
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
        message = String.format("Creata correttamente (in memoria) la entity: [%s] con keyPropertyName%s'%s'", entityBean.id, FORWARD, entityBean);
        System.out.println(message);
    }

}
