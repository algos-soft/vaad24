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
    @Order(43)
    @DisplayName("43 - newEntityConParametri")
    protected void newEntityConParametri() {
        System.out.println("43 - newEntityConParametri");
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

    @Test
    @Order(44)
    @DisplayName("44 - creaIfNotExist")
    protected void creaIfNotExist() {
        System.out.println("44 - creaIfNotExist");

        sorgente = "termidoro";

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
    void name() {

    }

}
