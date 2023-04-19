package it.algos.backend;

import it.algos.*;
import it.algos.base.*;
import static it.algos.vaad24.backend.boot.VaadCost.*;
import it.algos.vaad24.backend.packages.geografia.continente.*;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.provider.*;
import org.springframework.boot.test.context.*;

import java.util.stream.*;

/**
 * Project vaad24
 * Created by Algos
 * User: gac
 * Date: Thu, 23-Feb-2023
 * Time: 09:58
 */
@SpringBootTest(classes = {Vaad24SimpleApp.class})
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Tag("backend")
@DisplayName("Continente Backend")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ContinenteBackendTest extends BackendTest {

    private ContinenteBackend backend;


    //--nome nella collection
    //--esiste come ID
    //--esiste come key
    //--crea una nuova entity
    protected static Stream<Arguments> CONTINENTE() {
        return Stream.of(
                Arguments.of(VUOTA, false, false, false),
                Arguments.of("afrtica", false, false, true),
                Arguments.of("africa", true, false, false),
                Arguments.of("Africa", false, true, false),
                Arguments.of("nordamerica", true, false, false),
                Arguments.of("Nordamerica", false, true, false)
        );
    }


    //--nome della property
    //--value della property
    //--esiste entityBean
    public static Stream<Arguments> PROPERTY() {
        return Stream.of(
                Arguments.of(VUOTA, VUOTA, false),
                Arguments.of("propertyInesistente", "valoreInesistente", false),
                Arguments.of("ordine", 13, false),
                Arguments.of("ordine", 2, true)
        );
    }

    //--value ordine
    //--esiste entityBean
    public static Stream<Arguments> ORDINE() {
        return Stream.of(
                Arguments.of(0, false),
                Arguments.of(13, false),
                Arguments.of(4, true)
        );
    }

    /**
     * Qui passa una volta sola <br>
     */
    @BeforeAll
    protected void setUpAll() {
        this.backend = super.continenteBackend;
        super.entityClazz = Continente.class;
        super.typeBackend = TypeBackend.continente;
        super.crudBackend = backend;

        super.setUpAll();
    }

    @BeforeEach
    protected void setUpEach() {
        super.setUpEach();

        super.streamCollection = CONTINENTE();
        super.streamProperty = PROPERTY();
        super.streamOrder = ORDINE();
    }

}
