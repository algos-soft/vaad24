package it.algos.backend;

import it.algos.*;
import it.algos.base.*;
import static it.algos.vaad24.backend.boot.VaadCost.*;
import it.algos.vaad24.backend.packages.crono.secolo.*;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.*;
import org.springframework.boot.test.context.*;

import java.util.*;

/**
 * Project vaad24
 * Created by Algos
 * User: gac
 * Date: Wed, 22-Feb-2023
 * Time: 18:30
 */
@SpringBootTest(classes = {Vaad24SimpleApp.class})
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Tag("integration")
@Tag("backend")
@DisplayName("Secolo Backend")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class SecoloBackendTest extends BackendTest {

    private SecoloBackend backend;


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

    @Test
    @Order(21)
    @DisplayName("21 - isExistById")
    protected void isExistById() {
        System.out.println("21 - isExistById");
        System.out.println(VUOTA);

        sorgente = "XX secolo";
        ottenutoBooleano = super.isExistById(sorgente);
        assertFalse(ottenutoBooleano);
        System.out.println(VUOTA);

        sorgente = "xxsecolo";
        ottenutoBooleano = super.isExistById(sorgente);
        assertTrue(ottenutoBooleano);
    }


    @Test
    @Order(22)
    @DisplayName("22 - isExistByKey")
    protected void isExistByKey() {
        System.out.println("22 - isExistByKey");
        System.out.println(VUOTA);

        sorgente = "xxsecolo";
        ottenutoBooleano = super.isExistByKey(sorgente);
        assertFalse(ottenutoBooleano);
        System.out.println(VUOTA);

        sorgente = "XX secolo";
        ottenutoBooleano = super.isExistByKey(sorgente);
        assertTrue(ottenutoBooleano);
    }


    @Test
    @Order(23)
    @DisplayName("23 - isExistByOrder")
    protected void isExistByOrder() {
        System.out.println("23 - isExistByOrder");
        System.out.println(VUOTA);

        sorgenteIntero = 857;
        ottenutoBooleano = super.isExistByOrder(sorgenteIntero);
        assertFalse(ottenutoBooleano);

        sorgenteIntero = 4;
        ottenutoBooleano = super.isExistByOrder(sorgenteIntero);
        assertTrue(ottenutoBooleano);

        sorgenteIntero = 27;
        ottenutoBooleano = super.isExistByOrder(sorgenteIntero);
        assertTrue(ottenutoBooleano);

        sorgenteIntero = 35;
        ottenutoBooleano = super.isExistByOrder(sorgenteIntero);
        assertFalse(ottenutoBooleano);

        sorgenteIntero = -4;
        ottenutoBooleano = super.isExistByOrder(sorgenteIntero);
        assertFalse(ottenutoBooleano);
    }


    @Test
    @Order(24)
    @DisplayName("24 - isExistByProperty")
    protected void isExistByProperty() {
        System.out.println("24 - isExistByProperty");
        System.out.println(VUOTA);

        sorgente = "propertyInesistente";
        sorgenteIntero = 401;
        ottenutoBooleano = super.isExistByProperty(sorgente, sorgenteIntero);
        assertFalse(ottenutoBooleano);
        System.out.println(VUOTA);

        sorgente = "inizio";
        sorgenteIntero = 374;
        ottenutoBooleano = super.isExistByProperty(sorgente, sorgenteIntero);
        assertFalse(ottenutoBooleano);
        System.out.println(VUOTA);

        assertFalse(ottenutoBooleano);
        sorgenteIntero = 401;
        ottenutoBooleano = super.isExistByProperty(sorgente, sorgenteIntero);
        assertTrue(ottenutoBooleano);
    }


    @Test
    @Order(31)
    @DisplayName("31 - findById")
    protected void findById() {
        System.out.println("31 - findById");
        System.out.println(VUOTA);

        sorgente = "XX secolo";
        entityBean = super.findById(sorgente);
        assertNull(entityBean);
        System.out.println(VUOTA);

        sorgente = "xxsecolo";
        entityBean = super.findById(sorgente);
        assertNotNull(entityBean);
    }

    @Test
    @Order(32)
    @DisplayName("32 - findByKey")
    protected void findByKey() {
        System.out.println("32 - findByKey");
        System.out.println(VUOTA);

        sorgente = "xxsecolo";
        entityBean = super.findByKey(sorgente);
        assertNull(entityBean);
        System.out.println(VUOTA);

        sorgente = "XX secolo";
        entityBean = super.findByKey(sorgente);
        assertNotNull(entityBean);
    }

    @Test
    @Order(33)
    @DisplayName("33 - findByOrder")
    protected void findByOrder() {
        System.out.println("33 - findByOrder");
        System.out.println("Secolo ricavato dal numero d'ordine che parte dal X secolo a.C.");
        System.out.println(VUOTA);

        sorgenteIntero = 857;
        entityBean = super.findByOrder(sorgenteIntero);
        assertNull(entityBean);

        sorgenteIntero = 4;
        entityBean = super.findByOrder(sorgenteIntero);
        assertNotNull(entityBean);

        sorgenteIntero = 27;
        entityBean = super.findByOrder(sorgenteIntero);
        assertNotNull(entityBean);

        sorgenteIntero = 35;
        entityBean = super.findByOrder(sorgenteIntero);
        assertNull(entityBean);

        sorgenteIntero = -4;
        entityBean = super.findByOrder(sorgenteIntero);
        assertNull(entityBean);
    }


    @Test
    @Order(34)
    @DisplayName("34 - findByProperty")
    protected void findByProperty() {
        System.out.println("34 - findByProperty");
        System.out.println(VUOTA);

        sorgente = "propertyInesistente";
        sorgenteIntero = 401;
        entityBean = super.findByProperty(sorgente, sorgenteIntero);
        assertNull(entityBean);
        System.out.println(VUOTA);

        sorgente = "inizio";
        sorgenteIntero = 374;
        entityBean = super.findByProperty(sorgente, sorgenteIntero);
        assertNull(entityBean);
        System.out.println(VUOTA);

        sorgente = "inizio";
        sorgenteIntero = 401;
        entityBean = super.findByProperty(sorgente, sorgenteIntero);
        assertNotNull(entityBean);
        System.out.println(VUOTA);
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
