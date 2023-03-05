package it.algos.service;

import it.algos.*;
import it.algos.base.*;
import static it.algos.vaad24.backend.boot.VaadCost.*;
import it.algos.vaad24.backend.packages.anagrafica.*;
import it.algos.vaad24.backend.packages.geografia.continente.*;
import it.algos.vaad24.backend.service.*;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.extension.*;
import org.mockito.*;
import org.springframework.boot.test.context.*;
import org.springframework.test.context.junit.jupiter.*;

/**
 * Project vaad24
 * Created by Algos
 * User: gac
 * Date: Sun, 05-Mar-2023
 * Time: 14:01
 */
@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = {Vaad24SimpleApp.class})
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Tag("service")
@DisplayName("Bean Service")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class BeanServiceTest extends AlgosUnitTest {

    /**
     * Classe principale di riferimento <br>
     * Gia 'costruita' nella superclasse <br>
     */
    private BeanService service;

    @InjectMocks
    protected ContinenteBackend continenteBackend;

    @InjectMocks
    protected ViaBackend viaBackend;

    /**
     * Qui passa una volta sola, chiamato dalle sottoclassi <br>
     * Invocare PRIMA il metodo setUpStartUp() della superclasse <br>
     * Si possono aggiungere regolazioni specifiche <br>
     */
    @BeforeAll
    protected void setUpAll() {
        super.setUpAll();

        //--reindirizzo l'istanza della superclasse
        service = beanService;

        continenteBackend.annotationService = annotationService;
        viaBackend.annotationService = annotationService;
        continenteBackend.reflectionService = reflectionService;
        viaBackend.reflectionService = reflectionService;
        continenteBackend.textService = textService;
        viaBackend.textService = textService;
        continenteBackend.mongoService = mongoService;
        viaBackend.mongoService = mongoService;
    }


    /**
     * Qui passa a ogni test delle sottoclassi <br>
     * Invocare PRIMA il metodo setUp() della superclasse <br>
     * Si possono aggiungere regolazioni specifiche <br>
     */
    @BeforeEach
    protected void setUpEach() {
        super.setUpEach();
    }


    @Test
    @Order(1)
    @DisplayName("1 - copia")
    void getLabelHost() {
        System.out.println("1 - copia");
        System.out.println(VUOTA);
        Continente continente;
        Via via;
        sorgente3 = "nome";

        sorgente = "asia";
        continente = continenteBackend.findById(sorgente);
        assertNotNull(continente);

        sorgente2 = "piazza";
        via = viaBackend.findById(sorgente2);
        assertNotNull(via);

        System.out.println("Prima della copia:");
        System.out.println(String.format("Nome del continente: %s.%s%s%s", sorgente, sorgente3, FORWARD, continente.nome));
        System.out.println(String.format("Nome della via: %s.%s%s%s", sorgente2, sorgente3, FORWARD, via.nome));

        service.copia(continente,via);

        System.out.println(VUOTA);
        System.out.println("Dopo la copia:");
        System.out.println(String.format("Nome del continente: %s.%s%s%s", sorgente, sorgente3, FORWARD, continente.nome));
        System.out.println(String.format("Nome della via: %s.%s%s%s", sorgente2, sorgente3, FORWARD, via.nome));

    }


    /**
     * Qui passa al termine di ogni singolo test <br>
     */
    @AfterEach
    void tearDown() {
    }


    /**
     * Qui passa una volta sola, chiamato alla fine di tutti i tests <br>
     */
    @AfterAll
    void tearDownAll() {
    }

}
