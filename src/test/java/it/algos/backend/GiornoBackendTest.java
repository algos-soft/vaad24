package it.algos.backend;

import it.algos.*;
import it.algos.base.*;
import static it.algos.vaad24.backend.boot.VaadCost.*;
import it.algos.vaad24.backend.packages.crono.giorno.*;
import it.algos.vaad24.backend.packages.crono.mese.*;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.provider.*;
import org.springframework.boot.test.context.*;

import java.util.*;

/**
 * Project vaad24
 * Created by Algos
 * User: gac
 * Date: Wed, 22-Feb-2023
 * Time: 21:45
 */
@SpringBootTest(classes = {Vaad24SimpleApp.class})
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Tag("giorno")
@Tag("backend")
@DisplayName("Giorno Backend")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class GiornoBackendTest extends BackendTest {

    private GiornoBackend backend;

    private List<Giorno> listaBeans;


    /**
     * Qui passa una volta sola <br>
     */
    @BeforeAll
    protected void setUpAll() {
        this.backend = super.giornoBackend;
        super.entityClazz = Giorno.class;
        super.typeBackend = TypeBackend.giorno;
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

        backend.meseBackend = meseBackend;
        backend.meseBackend.mongoService = mongoService;
        backend.meseBackend.annotationService = annotationService;
        backend.meseBackend.textService = textService;
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

        sorgente = "23maggio";
        ottenutoBooleano = super.isExistById(sorgente);
        assertTrue(ottenutoBooleano);
    }


    @Test
    @Order(22)
    @DisplayName("22 - isExistByKey")
    protected void isExistByKey() {
        System.out.println("22 - isExistByKey");
        System.out.println(VUOTA);
        System.out.println("Giorno ricavato dalla keyProperty");
        System.out.println(VUOTA);

        //--giorno
        //--esistente
        System.out.println(VUOTA);
        GIORNI().forEach(this::isExistKeyBase);
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
            System.out.println(String.format("Il giorno [%s] esiste", sorgente));
        }
        else {
            System.out.println(String.format("Il giorno [%s] non esiste", sorgente));
        }
        System.out.println(VUOTA);
    }

    @Test
    @Order(23)
    @DisplayName("23 - isExistByOrder")
    protected void isExistByOrder() {
        System.out.println("23 - isExistByOrder");
        System.out.println(VUOTA);

        sorgenteIntero = 487;
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

        sorgente = "trascorsi";
        sorgenteIntero = 8527;
        ottenutoBooleano = super.isExistByProperty(sorgente, sorgenteIntero);
        assertFalse(ottenutoBooleano);
        System.out.println(VUOTA);

        sorgente = "trascorsi";
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

        sorgente = "sbagliato";
        entityBean = super.findById(sorgente);
        assertNull(entityBean);
        System.out.println(VUOTA);

        sorgente = "23maggio";
        entityBean = super.findById(sorgente);
        assertNotNull(entityBean);
    }

    @Test
    @Order(32)
    @DisplayName("32 - findByKey")
    protected void findByKey() {
        System.out.println("32 - findByKey");
        System.out.println(VUOTA);

        sorgente = "12 termidoro";
        entityBean = super.findByKey(sorgente);
        assertNull(entityBean);
        System.out.println(VUOTA);

        sorgente = "23 maggio";
        entityBean = super.findByKey(sorgente);
        assertNotNull(entityBean);
    }

    @Test
    @Order(33)
    @DisplayName("33 - findByOrder")
    protected void findByOrder() {
        System.out.println("33 - findByOrder");
        System.out.println(VUOTA);

        sorgenteIntero = 487;
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

        sorgente = "trascorsi";
        sorgenteIntero = 8527;
        entityBean = super.findByProperty(sorgente, sorgenteIntero);
        assertNull(entityBean);
        System.out.println(VUOTA);

        sorgente = "trascorsi";
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

        sorgente = "23 maggio";
        ottenutoBooleano = super.creaIfNotExist(sorgente);
        assertFalse(ottenutoBooleano);
        System.out.println(VUOTA);

        sorgente = "12 termidoro";
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
        Giorno giorno;
        Mese mese = meseBackend.findByKey("febbraio"); ;

        sorgenteIntero = 93;
        sorgente = "12 termidoro";
        previsto = "12termidoro";
        int sorgenteIntero2 = 88;
        int sorgenteIntero3 = 431;

        entityBean = backend.newEntity(sorgenteIntero, sorgente, mese, sorgenteIntero2, sorgenteIntero3);
        assertTrue(entityBean instanceof Giorno);
        assertNotNull(entityBean);
        giorno = (Giorno) entityBean;
        assertEquals(previsto, giorno.id);
        assertEquals(sorgenteIntero, giorno.ordine);
        assertEquals(sorgente, giorno.nome);
        assertEquals(sorgenteIntero2, giorno.trascorsi);
        assertEquals(sorgenteIntero3, giorno.mancanti);
        message = String.format("Creata correttamente (in memoria) la entity: [%s] con keyPropertyName%s'%s'", entityBean.id, FORWARD, entityBean);
        System.out.println(message);
    }
    @Test
    @Order(54)
    @DisplayName("54 - findAllByMese (entity)")
    protected void findAllByMese() {
        System.out.println("54 - findAllByMese (entity)");
        System.out.println("Rimanda a findAllByProperty(FIELD_NAME_MESE, mese)");

        for (Mese sorgente : meseBackend.findAllNoSort()) {
            listaBeans = backend.findAllByMese(sorgente);
            assertNotNull(listaBeans);
            System.out.println(VUOTA);
            message = String.format("Mese di %s", sorgente.nome);
            printBackend(listaBeans, 3);
        }
    }


    @Test
    @Order(64)
    @DisplayName("64 - findAllForNome (String)")
    protected void findAllForNome() {
        System.out.println("64 - findAllForNome (String)");
        System.out.println("Uguale a 61 - findAllForKey");
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
    @DisplayName("65 - findAllForNomeByMese (String)")
    protected void findAllForNomeByMese() {
        System.out.println("65 - findAllForNomeByMese (String)");
        int num = 3;

        for (Mese sorgente : meseBackend.findAllSortCorrente()) {
            listaStr = backend.findAllForNomeByMese(sorgente);
            assertNotNull(listaStr);
            message = String.format("Nel mese di %s ci sono %s giorni. Mostro solo i primi %s", sorgente, textService.format(listaStr.size()), num);
            System.out.println(VUOTA);
            System.out.println(message);
            if (num > 0) {
                print(listaStr.subList(0, num));
            }
        }
    }

}