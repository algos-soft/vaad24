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
import java.util.stream.*;

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

    //--nome nella collection
    //--esiste come ID
    //--esiste come key
    //--crea una nuova entity
    protected static Stream<Arguments> GIORNO() {
        return Stream.of(
                Arguments.of(VUOTA, false, false, false),
                Arguments.of("23 febbraio", false, true, false),
                Arguments.of("43 marzo", false, false, true),
                Arguments.of("19 dicembra", false, false, true),
                Arguments.of("4gennaio", true, false, false)
        );
    }

    //--nome della property
    //--value della property
    //--esiste entityBean
    public static Stream<Arguments> PROPERTY() {
        return Stream.of(
                Arguments.of(VUOTA, VUOTA, false),
                Arguments.of("propertyInesistente", "valoreInesistente", false),
                Arguments.of("trascorsi", 8527, false),
                Arguments.of("trascorsi", 233, true)
        );
    }

    //--value ordine
    //--esiste entityBean
    public static Stream<Arguments> ORDINE() {
        return Stream.of(
                Arguments.of(0, false),
                Arguments.of(847, false),
                Arguments.of(4, true),
                Arguments.of(27, true),
                Arguments.of(235, true),
                Arguments.of(-4, false)
        );
    }

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

    @BeforeEach
    protected void setUpEach() {
        super.setUpEach();

        super.streamCollection = GIORNO();
        super.streamProperty = PROPERTY();
        super.streamOrder = ORDINE();
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
        message = String.format("Creata correttamente (SOLO IN MEMORIA) la entity: [%s] con keyPropertyName%s'%s'", entityBean.id, FORWARD, entityBean);
        System.out.println(message);
        printBackend(List.of(entityBean));
    }

    @Test
    @Order(55)
    @DisplayName("55 - findAllByMese (entity)")
    protected void findAllByMese() {
        System.out.println("55 - findAllByMese (entity)");
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
    @Order(65)
    @DisplayName("65 - findAllForNome (String)")
    protected void findAllForNome() {
        System.out.println("65 - findAllForNome (String)");
        System.out.println("Uguale a 61 - findAllForKey");
        System.out.println(VUOTA);

        //        listaStr = backend.findAllForNome();
        //        assertNotNull(listaStr);
        //        ottenutoIntero = listaStr.size();
        //        sorgente = textService.format(ottenutoIntero);
        //        sorgente2 = keyPropertyName;
        //        message = String.format("La collection '%s' della classe [%s] ha in totale %s entities. Valori (String) del campo chiave '%s':", collectionName, clazzName, sorgente, sorgente2);
        //        System.out.println(message);
        //
        //        printSubLista(listaStr);
    }


    @Test
    @Order(66)
    @DisplayName("66 - findAllForNomeByMese (String)")
    protected void findAllForNomeByMese() {
        System.out.println("66 - findAllForNomeByMese (String)");
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