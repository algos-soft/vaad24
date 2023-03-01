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
import org.springframework.data.domain.*;

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

    private List<Giorno> listaBeans;


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
        super.typeBackend = TypeBackend.giorno;
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
    @Order(23)
    @DisplayName("23 - findAll con sort specifico (discendente)")
    protected void findAllSort() {
        System.out.println("23 - findAll con sort specifico (discendente)");
        System.out.println(VUOTA);

        sort = Sort.by(Sort.Direction.DESC, FIELD_NAME_ORDINE);
        listaBeans = crudBackend.findAllSort(sort);
        assertNotNull(listaBeans);
        ottenutoIntero = listaBeans.size();
        message = String.format("La collection '%s' della classe [%s] ha in totale %s entities nel database mongoDB", collectionName, clazzName, textService.format(ottenutoIntero));
        System.out.println(message);
        printGiorni(listaBeans);
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
        int num = 3;

        for (Mese sorgente : meseBackend.findAllNoSort()) {
            listaBeans = backend.findAllByMese(sorgente);
            assertNotNull(listaBeans);
            System.out.println(VUOTA);
            message = String.format("Nel mese di %s ci sono %s giorni. Mostro solo i primi %s", sorgente, textService.format(listaBeans.size()), num);
            System.out.println(message);
            printGiorni(listaBeans.subList(0, num));
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


    @Test
    @Order(62)
    @DisplayName("62 - findByOrdine")
    protected void findByOrdine() {
        System.out.println("62 - findByOrdine");
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


}
