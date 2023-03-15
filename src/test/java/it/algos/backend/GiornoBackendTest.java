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
    @Order(43)
    @DisplayName("43 - newEntityConParametri")
    protected void newEntityConParametri() {
        System.out.println("43 - newEntityConParametri");
        System.out.println(VUOTA);
        Giorno giorno;

        sorgenteIntero = 18;
        sorgente = "12 termidoro";
        previsto = "12termidoro";
        Mese mese = meseBackend.findByKey("aprile");
        assertNotNull(mese);
        int sorgenteIntero2 = 37;
        int sorgenteIntero3 = 118;

        entityBean = backend.newEntity(sorgenteIntero, sorgente, mese, sorgenteIntero2, sorgenteIntero3);
        assertNotNull(entityBean);
        assertTrue(entityBean instanceof Giorno);
        giorno = (Giorno) entityBean;
        assertEquals(previsto, giorno.id);
        assertEquals(sorgenteIntero, giorno.ordine);
        assertEquals(sorgente, giorno.nome);
        assertEquals(mese, giorno.mese);
        assertEquals(sorgenteIntero2, giorno.trascorsi);
        assertEquals(sorgenteIntero3, giorno.mancanti);
        message = String.format("Creata correttamente (in memoria) la entity: [%s] con keyPropertyName%s'%s'", entityBean.id, FORWARD, entityBean);
        System.out.println(message);
    }

    @Test
    @Order(44)
    @DisplayName("44 - creaIfNotExist")
    protected void creaIfNotExist() {
        System.out.println("44 - creaIfNotExist");

        sorgente = "12 termidoro";

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
    @DisplayName("51 - findAllForNome (String)")
    protected void findAllForNome() {
        System.out.println("51 - findAllForNome (String)");
        System.out.println("Uguale a 31 - findAllForKey");
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
    @Order(52)
    @DisplayName("52 - findAllByMese (entity)")
    protected void findAllByMese() {
        System.out.println("52 - findAllByMese (entity)");
        System.out.println("Rimanda a findAllByProperty(FIELD_NAME_MESE, mese)");

        for (Mese sorgente : meseBackend.findAllNoSort()) {
            listaBeans = backend.findAllByMese(sorgente);
            assertNotNull(listaBeans);
            System.out.println(VUOTA);
            printBackend(listaBeans, 3);
        }
    }

    @Test
    @Order(53)
    @DisplayName("53 - findAllForNomeByMese (nomi)")
    protected void findAllForNomeByMese() {
        System.out.println("54 - findAllForNomeByMese (nomi)");
        System.out.println("Rimanda a findAllByProperty(FIELD_NAME_MESE, mese)");
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

    @Test
    @Order(61)
    @DisplayName("61 - isExistKey")
    protected void isExistKey() {
        System.out.println("61 - isExistKey");
        System.out.println(VUOTA);
        System.out.println("Giorno ricavato dal numero progressivo nell'anno");
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
            System.out.println(String.format("Il giorno %s esiste", sorgente));
        }
        else {
            System.out.println(String.format("Il giorno %s non esiste", sorgente));
        }
        System.out.println(VUOTA);
    }

    @Test
    @Order(62)
    @DisplayName("62 - findByOrdine")
    protected void findByOrdine() {
        System.out.println("62 - findByOrdine");
        System.out.println(VUOTA);
        System.out.println("Giorno ricavato dal numero progressivo nell'anno");
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

        sorgenteIntero = 127;
        entityBean = backend.findByOrder(sorgenteIntero);
        assertNotNull(entityBean);
        ottenuto = entityBean.toString();
        printValue(sorgenteIntero, ottenuto);

        sorgenteIntero = 250;
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


}