package it.algos.backend;

import it.algos.*;
import it.algos.base.*;
import static it.algos.vaad24.backend.boot.VaadCost.*;
import it.algos.vaad24.backend.packages.crono.secolo.*;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.*;
import org.springframework.boot.test.context.*;

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
    @Order(43)
    @DisplayName("43 - newEntityConParametri")
    protected void newEntityConParametri() {
        System.out.println("43 - newEntityConParametri");
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
        message = String.format("Creata correttamente (in memoria) la entity: [%s] con keyPropertyName%s'%s'", entityBean.id, FORWARD, entityBean);
        System.out.println(message);
    }

    @Test
    @Order(44)
    @DisplayName("44 - creaIfNotExist")
    protected void creaIfNotExist() {
        System.out.println("44 - creaIfNotExist");

        sorgente = "3472";

        ottenutoBooleano = crudBackend.isExistByKey(sorgente);
        assertFalse(ottenutoBooleano);
        message = String.format("1) isExistKey -> Non esiste (false) la entity [%s]", sorgente);
        System.out.println(VUOTA);
        System.out.println(message);

        ottenutoBooleano = backend.creaIfNotExist(sorgente);
        assertTrue(ottenutoBooleano);
        ottenutoBooleano = crudBackend.isExistByKey(sorgente);
        assertTrue(ottenutoBooleano);
        message = String.format("2) creaIfNotExist -> Creata correttamente (nel database) la entity: [%s] con keyPropertyName%s'%s'", entityBean.id, FORWARD, entityBean);
        System.out.println(VUOTA);
        System.out.println(message);

        entityBean = backend.findByKey(sorgente);
        assertNotNull(entityBean);
        ottenutoBooleano = backend.delete(entityBean);
        assertTrue(ottenutoBooleano);
        ottenutoBooleano = crudBackend.isExistByKey(sorgente);
        assertFalse(ottenutoBooleano);
        message = String.format("3) delete -> Cancellata la entity: [%s] con keyPropertyName%s'%s'", entityBean.id, FORWARD, entityBean);
        System.out.println(VUOTA);
        System.out.println(message);
    }


    @Test
    @Order(51)
    @DisplayName("51 - findByOrdine")
    void findByOrdine() {
        System.out.println("51 - findByOrdine");
        System.out.println(VUOTA);
        System.out.println("Secolo ricavato dal numero d'ordine che parte dal X secolo a.C.");
        System.out.println(VUOTA);

        sorgenteIntero = 857;
        entityBean = backend.findByOrder(sorgenteIntero);
        assertNull(entityBean);
        ottenuto = VUOTA;
        printValue(sorgenteIntero, ottenuto);

        sorgenteIntero = 4;
        entityBean = backend.findByOrder(sorgenteIntero);
        assertNotNull(entityBean);
        ottenuto = entityBean.toString();
        printValue(sorgenteIntero, ottenuto);

        sorgenteIntero = 27;
        entityBean = backend.findByOrder(sorgenteIntero);
        assertNotNull(entityBean);
        ottenuto = entityBean.toString();
        printValue(sorgenteIntero, ottenuto);

        sorgenteIntero = 35;
        entityBean = backend.findByOrder(sorgenteIntero);
        assertNull(entityBean);
        ottenuto = VUOTA;
        printValue(sorgenteIntero, ottenuto);

        sorgenteIntero = -4;
        entityBean = backend.findByOrder(sorgenteIntero);
        assertNull(entityBean);
        ottenuto = VUOTA;
        printValue(sorgenteIntero, ottenuto);
    }

    @Test
    @Order(52)
    @DisplayName("52 - findByAnnoAC")
    void findByAnnoAC() {
        System.out.println("52 - findByAnnoAC");
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
    @Order(53)
    @DisplayName("53 - findByAnnoDC")
    void findByAnnoDC() {
        System.out.println("53 - findByAnnoDC");
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

}
