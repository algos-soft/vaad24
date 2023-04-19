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
import java.util.stream.*;

/**
 * Project vaad24
 * Created by Algos
 * User: gac
 * Date: Fri, 24-Feb-2023
 * Time: 16:50
 */
@SpringBootTest(classes = {Vaad24SimpleApp.class})
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Tag("anno")
@Tag("backend")
@DisplayName("Anno Backend")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class AnnoBackendTest extends BackendTest {


    private AnnoBackend backend;

    private List<Anno> listaBeans;


    //--nome nella collection
    //--esiste come ID
    //--esiste come key
    //--crea una nuova entity
    public static Stream<Arguments> ANNO() {
        return Stream.of(
                Arguments.of(VUOTA, false, false, false),
                Arguments.of("0", false, false, true),
                Arguments.of("24", true, true, false),
                Arguments.of("secolo", false, false, true),
                Arguments.of("994a.c.", true, false, false),
                Arguments.of("994 a.C.", false, true, false),
                Arguments.of("24 A.C.", false, false, false),
                Arguments.of("24 a.C.", false, true, false),
                Arguments.of("24a.c.", true, false, false),
                Arguments.of("3208", false, false, true)
        );
    }


    //--nome della property
    //--value della property
    //--esiste entityBean
    public static Stream<Arguments> PROPERTY() {
        return Stream.of(
                Arguments.of(VUOTA, VUOTA, false),
                Arguments.of("propertyInesistente", "valoreInesistente", false),
                Arguments.of("ordine", 84527, false),
                Arguments.of("ordine", 233, true)
        );
    }

    //--value ordine
    //--esiste entityBean
    public static Stream<Arguments> ORDINE() {
        return Stream.of(
                Arguments.of(0, false),
                Arguments.of(847, true),
                Arguments.of(4, true),
                Arguments.of(27, true),
                Arguments.of(77235, false),
                Arguments.of(-4, false)
        );
    }

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

    @BeforeEach
    protected void setUpEach() {
        super.setUpEach();

        super.streamCollection = ANNO();
        super.streamProperty = PROPERTY();
        super.streamOrder = ORDINE();
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
        message = String.format("Creata correttamente (SOLO IN MEMORIA) la entity: [%s] con keyPropertyName%s'%s'", entityBean.id, FORWARD, entityBean);
        System.out.println(message);
        printBackend(List.of(entityBean));
    }


    @Test
    @Order(56)
    @DisplayName("56 - findAllBySecolo (entity)")
    void findAllBySecolo() {
        System.out.println("56 - findAllBySecolo (entity)");

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
    @Order(65)
    @DisplayName("65 - findAllForNome (String)")
    protected void findAllForNome() {
        System.out.println("65 - findAllForNome (String)");
        System.out.println("Uguale a 61 - findAllForKeySortKey");
        System.out.println(VUOTA);
    }


    @Test
    @Order(66)
    @DisplayName("66 - findAllForNomeBySecolo (String)")
    protected void findAllForNomeByMese() {
        System.out.println("66 - findAllForNomeByMese (String)");
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
