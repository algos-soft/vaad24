package it.algos.backend;

import it.algos.*;
import it.algos.base.*;
import static it.algos.vaad24.backend.boot.VaadCost.*;
import it.algos.vaad24.backend.packages.crono.giorno.*;
import it.algos.vaad24.backend.packages.crono.mese.*;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.provider.*;
import org.mockito.*;
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
@Tag("integration")
@Tag("backend")
@DisplayName("Giorno Backend")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class GiornoBackendTest extends BackendTest {

    @InjectMocks
    private GiornoBackend backend;

    @InjectMocks
    private MeseBackend meseBackend;

    //--giorno
    //--esistente
    protected static Stream<Arguments> GIORNI() {
        return Stream.of(
                Arguments.of(null, false),
                Arguments.of(VUOTA, false),
                Arguments.of("23 febbraio", true),
                Arguments.of("43 marzo", false),
                Arguments.of("19 dicembra", false),
                Arguments.of("4 gennaio", true)
        );
    }


    /**
     * Qui passa una volta sola <br>
     */
    @BeforeAll
    protected void setUpAll() {
        assertNotNull(backend);
        assertNotNull(meseBackend);
        super.entityClazz = Giorno.class;
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

    @BeforeEach
    protected void setUpEach() {
        super.setUpEach();
    }

    @Test
    @Order(51)
    @DisplayName("51 - findByOrdine")
    void findByOrdine() {
        System.out.println("51 - findByOrdine");
        System.out.println(VUOTA);
        System.out.println("Giorno ricavato dal numero progressivo nell'anno");
        System.out.println(VUOTA);

        sorgenteIntero = 857;
        entityBean = backend.findByOrdine(sorgenteIntero);
        assertNull(entityBean);
        ottenuto = VUOTA;
        printValue(sorgenteIntero, ottenuto);

        sorgenteIntero = 4;
        entityBean = backend.findByOrdine(sorgenteIntero);
        assertNotNull(entityBean);
        ottenuto = entityBean.toString();
        printValue(sorgenteIntero, ottenuto);

        sorgenteIntero = 127;
        entityBean = backend.findByOrdine(sorgenteIntero);
        assertNotNull(entityBean);
        ottenuto = entityBean.toString();
        printValue(sorgenteIntero, ottenuto);

        sorgenteIntero = 250;
        entityBean = backend.findByOrdine(sorgenteIntero);
        assertNotNull(entityBean);
        ottenuto = entityBean.toString();
        printValue(sorgenteIntero, ottenuto);

        sorgenteIntero = -4;
        entityBean = backend.findByOrdine(sorgenteIntero);
        assertNull(entityBean);
        ottenuto = VUOTA;
        printValue(sorgenteIntero, ottenuto);
    }

    @Test
    @Order(52)
    @DisplayName("52 - isExistKey")
    void isExistKey() {
        System.out.println("52 - isExistKey");
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

        ottenutoBooleano = backend.isExistKey(sorgente);
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
    @Order(53)
    @DisplayName("53 - findAllByMese (entity)")
    void findAllByMese() {
        System.out.println("53 - findAllByMese (entity)");
        List<Giorno> listaGiorni;

        for (Mese sorgente : meseBackend.findAllSortCorrente()) {
            listaGiorni = backend.findAllByMese(sorgente);
            assertNotNull(listaGiorni);
            message = String.format("Nel mese di %s ci sono %s giorni", sorgente, textService.format(listaGiorni.size()));
            System.out.println(VUOTA);
            System.out.println(message);
            printGiorni(listaGiorni);
        }
    }

    @Test
    @Order(54)
    @DisplayName("54 - findAllByMese (nomi)")
    void findAllNomiByMese() {
        System.out.println("54 - findAllByMese (nomi)");

        for (Mese sorgente : meseBackend.findAllSortCorrente()) {
            listaStr = backend.findAllNomiByMese(sorgente);
            assertNotNull(listaStr);
            message = String.format("Nel mese di %s ci sono %s giorni", sorgente, textService.format(listaStr.size()));
            System.out.println(VUOTA);
            System.out.println(message);
            print(listaStr);
        }
    }


    void printGiorni(List<Giorno> listaGiorni) {
        int k = 0;

        for (Giorno giorno : listaGiorni) {
            System.out.print(++k);
            System.out.print(PARENTESI_TONDA_END);
            System.out.print(SPAZIO);
            System.out.print(giorno.nome);
            System.out.print(SPAZIO);
            System.out.print(giorno.trascorsi);
            System.out.print(SPAZIO);
            System.out.println(giorno.mancanti);
        }
    }

}
