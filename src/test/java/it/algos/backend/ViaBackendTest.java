package it.algos.backend;

import it.algos.*;
import it.algos.base.*;
import static it.algos.vaad24.backend.boot.VaadCost.*;
import it.algos.vaad24.backend.packages.anagrafica.*;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.provider.*;
import org.springframework.boot.test.context.*;

import java.util.stream.*;

/**
 * Project vaad24
 * Created by Algos
 * User: gac
 * Date: Wed, 22-Feb-2023
 * Time: 18:26
 */
@SpringBootTest(classes = {Vaad24SimpleApp.class})
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Tag("backend")
@DisplayName("Via Backend")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ViaBackendTest extends BackendTest {

    private ViaBackend backend;

    //--nome nella collection
    //--esiste come ID
    //--esiste come key
    //--crea una nuova entity
    protected static Stream<Arguments> VIA() {
        return Stream.of(
                Arguments.of(VUOTA, false, false, false),
                Arguments.of("piazzale", true, true, false),
                Arguments.of("termidoro", false, false, true),
                Arguments.of("brumaio", false, false, true),
                Arguments.of("galleria", true, true, false)
        );
    }

    //--nome della property
    //--value della property
    //--esiste entityBean
    public static Stream<Arguments> PROPERTY() {
        return Stream.of(
                Arguments.of(VUOTA, VUOTA, false),
                Arguments.of("propertyInesistente", "valoreInesistente", false),
                Arguments.of("nome", "gennaio", false),
                Arguments.of("nome", "porta", true)
        );
    }


    /**
     * Qui passa una volta sola <br>
     */
    @BeforeAll
    protected void setUpAll() {
        this.backend = super.viaBackend;
        super.entityClazz = Via.class;
        super.typeBackend = TypeBackend.via;
        super.crudBackend = backend;

        super.setUpAll();
    }

    @BeforeEach
    protected void setUpEach() {
        super.setUpEach();

        super.streamCollection = VIA();
        super.streamProperty = PROPERTY();
    }

}
