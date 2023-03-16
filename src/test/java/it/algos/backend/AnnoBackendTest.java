package it.algos.backend;

import it.algos.*;
import it.algos.base.*;
import static it.algos.vaad24.backend.boot.VaadCost.*;
import it.algos.vaad24.backend.packages.crono.anno.*;
import it.algos.vaad24.backend.packages.crono.secolo.*;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.provider.*;
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
    @Order(21)
    @DisplayName("21 - isExistById")
    protected void isExistById() {
        System.out.println("21 - isExistById");
        System.out.println(VUOTA);

        sorgente = "3472";
        ottenutoBooleano = super.isExistById(sorgente);
        assertFalse(ottenutoBooleano);
        System.out.println(VUOTA);

        sorgente = "986a.c.";
        ottenutoBooleano = super.isExistById(sorgente);
        assertTrue(ottenutoBooleano);
    }


    @Test
    @Order(22)
    @DisplayName("22 - isExistByKey")
    protected void isExistByKey() {
        System.out.println("22 - isExistByKey");
        System.out.println(VUOTA);
        System.out.println("Anno ricavato dalla keyProperty");
        System.out.println(VUOTA);

        //--giorno
        //--esistente
        System.out.println(VUOTA);
        ANNI().forEach(this::isExistKeyBase);
    }

    //--giorno
    //--esistente
    void isExistKeyBase(Arguments arg) {
        Object[] mat = arg.get();
        sorgente = (String) mat[0];
        previstoBooleano = (boolean) mat[1];

        ottenutoBooleano = backend.isExistByKey(sorgente);
        assertEquals(previstoBooleano, ottenutoBooleano);
        if (ottenutoBooleano) {
            System.out.println(String.format("L'anno [%s] esiste", sorgente));
        }
        else {
            System.out.println(String.format("L'anno [%s] non esiste", sorgente));
        }
        System.out.println(VUOTA);
    }

    @Test
    @Order(23)
    @DisplayName("23 - isExistByOrder")
    protected void isExistByOrder() {
        System.out.println("23 - isExistByOrder");
        System.out.println(VUOTA);

        sorgenteIntero = 4870;
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
        sorgenteIntero = 27;
        ottenutoBooleano = super.isExistByProperty(sorgente, sorgenteIntero);
        assertFalse(ottenutoBooleano);
        System.out.println(VUOTA);

        sorgente = "ordine";
        sorgenteIntero = 8527;
        ottenutoBooleano = super.isExistByProperty(sorgente, sorgenteIntero);
        assertFalse(ottenutoBooleano);
        System.out.println(VUOTA);

        sorgente = "ordine";
        sorgenteIntero = 233;
        ottenutoBooleano = super.isExistByProperty(sorgente, sorgenteIntero);
        assertTrue(ottenutoBooleano);
    }

    @Test
    @Order(31)
    @DisplayName("31 - findById")
    protected void findById() {
        System.out.println("31 - findById");
        System.out.println(VUOTA);

        sorgente = "3472";
        entityBean = super.findById(sorgente);
        assertNull(entityBean);
        System.out.println(VUOTA);

        sorgente = "986a.c.";
        entityBean = super.findById(sorgente);
        assertNotNull(entityBean);
    }

    @Test
    @Order(32)
    @DisplayName("32 - findByKey")
    protected void findByKey() {
        System.out.println("32 - findByKey");
        System.out.println(VUOTA);

        sorgente = "986a.C.";
        entityBean = super.findByKey(sorgente);
        assertNull(entityBean);
        System.out.println(VUOTA);

        sorgente = "986 a.C.";
        entityBean = super.findByKey(sorgente);
        assertNotNull(entityBean);
    }

    @Test
    @Order(33)
    @DisplayName("33 - findByOrder")
    protected void findByOrder() {
        System.out.println("33 - findByOrder");
        System.out.println(VUOTA);

        sorgenteIntero = 4870;
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
        sorgenteIntero = 27;
        entityBean = super.findByProperty(sorgente, sorgenteIntero);
        assertNull(entityBean);
        System.out.println(VUOTA);

        sorgente = "ordine";
        sorgenteIntero = 8527;
        entityBean = super.findByProperty(sorgente, sorgenteIntero);
        assertNull(entityBean);
        System.out.println(VUOTA);

        sorgente = "ordine";
        sorgenteIntero = 233;
        entityBean = super.findByProperty(sorgente, sorgenteIntero);
        assertNotNull(entityBean);
        System.out.println(VUOTA);
    }

    @Test
    @Order(41)
    @DisplayName("41 - creaIfNotExist")
    protected void creaIfNotExist() {
        System.out.println("41 - creaIfNotExist");
        System.out.println(VUOTA);

        sorgente = "1876";
        ottenutoBooleano = super.creaIfNotExist(sorgente);
        assertFalse(ottenutoBooleano);
        System.out.println(VUOTA);

        sorgente = "3472";
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
        Anno anno;
        Secolo secolo = secoloBackend.findByKey("XX secolo");

        sorgenteIntero = 18;
        sorgente = "1975";
        previsto = sorgente;
        boolean dopoCristo = true;
        boolean bisestile = false;

        entityBean = backend.newEntity(sorgenteIntero, sorgente, secolo, dopoCristo, bisestile);
        assertTrue(entityBean instanceof Anno);
        assertNotNull(entityBean);
        anno = (Anno) entityBean;
        assertEquals(previsto, anno.id);
        assertEquals(sorgenteIntero, anno.ordine);
        assertEquals(sorgente, anno.nome);
        assertEquals(dopoCristo, anno.dopoCristo);
        assertEquals(bisestile, anno.bisestile);
        message = String.format("Creata correttamente (in memoria) la entity: [%s] con keyPropertyName%s'%s'", entityBean.id, FORWARD, entityBean);
        System.out.println(message);
    }


    @Test
    @Order(54)
    @DisplayName("54 - findAllBySecolo (entity)")
    void findAllBySecolo() {
        System.out.println("54 - findAllBySecolo (entity)");

        for (Secolo sorgente : secoloBackend.findAllSortCorrente()) {
            listaBeans = backend.findAllBySecolo(sorgente);
            assertNotNull(listaBeans);
            message = String.format("Nel secolo %s ci sono %s anni", sorgente, textService.format(listaBeans.size()));
            System.out.println(VUOTA);
            System.out.println(message);
            printBackend(listaBeans);
        }
    }

    @Test
    @Order(64)
    @DisplayName("64 - findAllForNome (String)")
    protected void findAllForNome() {
        System.out.println("64 - findAllForNome (String)");
        System.out.println(VUOTA);

        listaStr = backend.findAllForNome();
        assertNotNull(listaStr);
        ottenutoIntero = listaStr.size();
        sorgente = textService.format(ottenutoIntero);
        sorgente2 = keyPropertyName;
        message = String.format("La collection '%s' della classe [%s] ha in totale %s entities. Valori (String) del campo chiave '%s':", collectionName, clazzName, sorgente, sorgente2);
        System.out.println(message);

        printSubLista(listaStr);
    }


    @Test
    @Order(65)
    @DisplayName("65 - findAllForNomeBySecolo (String)")
    protected void findAllForNomeByMese() {
        System.out.println("65 - findAllForNomeByMese (String)");
        int num = 3;

        for (Secolo sorgente : secoloBackend.findAllSortCorrente()) {
            listaStr = backend.findAllForNomeBySecolo(sorgente);
            assertNotNull(listaStr);
            message = String.format("Nel secolo di %s ci sono %s anni. Mostro solo i primi %s", sorgente, textService.format(listaStr.size()), num);
            System.out.println(VUOTA);
            System.out.println(message);
            if (num > 0) {
                print(listaStr.subList(0, num));
            }
        }
    }

}
