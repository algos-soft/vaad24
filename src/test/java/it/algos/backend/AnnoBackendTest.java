package it.algos.backend;

import it.algos.*;
import it.algos.base.*;
import static it.algos.vaad24.backend.boot.VaadCost.*;
import it.algos.vaad24.backend.packages.crono.anno.*;
import it.algos.vaad24.backend.packages.crono.secolo.*;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.*;
import org.springframework.boot.test.context.*;

import java.util.*;

/**
 * Project vaad24
 * Created by Algos
 * User: gac
 * Date: Fri, 24-Feb-2023
 * Time: 16:50
 */
@SpringBootTest(classes = {Vaad24SimpleApp.class})
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Tag("integration")
@Tag("backend")
@DisplayName("Anno Backend")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class AnnoBackendTest extends BackendTest {


    private AnnoBackend backend;

    private List<Anno> listaBeans;

    /**
     * Qui passa una volta sola <br>
     */
    @BeforeAll
    protected void setUpAll() {
        this.backend = super.annoBackend;
        super.entityClazz = Anno.class;
        super.typeBackend = TypeBackend.anno;
        super.crudBackend = backend;

        super.setUpAll();
    }


    /**
     * Regola tutti riferimenti incrociati <br>
     * Deve essere fatto dopo aver costruito tutte le referenze 'mockate' <br>
     * Nelle sottoclassi devono essere regolati i riferimenti dei service specifici <br>
     * Può essere sovrascritto, invocando PRIMA il metodo della superclasse <br>
     */
    protected void fixRiferimentiIncrociati() {
        super.fixRiferimentiIncrociati();

        backend.secoloBackend = secoloBackend;
        backend.secoloBackend.mongoService = mongoService;
        backend.secoloBackend.annotationService = annotationService;
        backend.secoloBackend.textService = textService;
    }

    @Test
    @Order(43)
    @DisplayName("43 - newEntityConParametri")
    protected void newEntityConParametri() {
        System.out.println("43 - newEntityConParametri");
        System.out.println(VUOTA);
        Anno anno;

        sorgenteIntero = 18;
        sorgente = "1975";
        previsto = sorgente;
        Secolo secolo = secoloBackend.findByKey("XX secolo");
        assertNotNull(secolo);
        boolean dopoCristo = true;
        boolean bisestile = false;

        entityBean = backend.newEntity(sorgenteIntero, sorgente, secolo, dopoCristo, bisestile);
        assertNotNull(entityBean);
        assertTrue(entityBean instanceof Anno);
        anno = (Anno) entityBean;
        assertEquals(previsto, anno.id);
        assertEquals(sorgenteIntero, anno.ordine);
        assertEquals(sorgente, anno.nome);
        assertEquals(secolo, anno.secolo);
        assertTrue(anno.dopoCristo);
        assertFalse(anno.bisestile);
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
        System.out.println("Anno ricavato dal numero d'ordine che parte da ?");
        System.out.println(VUOTA);

        sorgenteIntero = 8527;
        entityBean = backend.findByOrder(sorgenteIntero);
        assertNull(entityBean);
        ottenuto = VUOTA;
        printValue(sorgenteIntero, ottenuto);

        sorgenteIntero = 2508;
        entityBean = backend.findByOrder(sorgenteIntero);
        assertNotNull(entityBean);
        ottenuto = entityBean.toString();
        printValue(sorgenteIntero, ottenuto);

        sorgenteIntero = 304;
        entityBean = backend.findByOrder(sorgenteIntero);
        assertNotNull(entityBean);
        ottenuto = entityBean.toString();
        printValue(sorgenteIntero, ottenuto);

        sorgenteIntero = 2963;
        entityBean = backend.findByOrder(sorgenteIntero);
        assertNotNull(entityBean);
        ottenuto = entityBean.toString();
        printValue(sorgenteIntero, ottenuto);

        sorgenteIntero = -4;
        entityBean = backend.findByOrder(sorgenteIntero);
        assertNull(entityBean);
        ottenuto = VUOTA;
        printValue(sorgenteIntero, ottenuto);
    }

    @Test
    @Order(53)
    @DisplayName("53 - findAllBySecolo (entity)")
    void findAllBySecolo() {
        System.out.println("53 - findAllBySecolo (entity)");

        for (Secolo sorgente : secoloBackend.findAllSortCorrente()) {
            listaBeans = backend.findAllBySecolo(sorgente);
            assertNotNull(listaBeans);
            message = String.format("Nel secolo %s ci sono %s anni", sorgente, textService.format(listaBeans.size()));
            System.out.println(VUOTA);
            System.out.println(message);
            printBackend(listaBeans);
        }
    }

}
