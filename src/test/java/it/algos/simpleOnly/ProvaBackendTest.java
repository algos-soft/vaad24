package it.algos.simpleOnly;

import it.algos.*;
import it.algos.base.*;
import static it.algos.vaad24.backend.boot.VaadCost.*;
import it.algos.vaad24simple.backend.packages.prova.*;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.*;
import org.junit.jupiter.params.provider.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.boot.test.context.*;

import java.util.stream.*;

/**
 * Project vaad24
 * Created by Algos
 * User: gac
 * Date: Tue, 21-Feb-2023
 * Time: 19:06
 */
@SpringBootTest(classes = {Vaad24SimpleApp.class})
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Tag("integration")
//@Tag("backend")
@DisplayName("Prova Backend")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ProvaBackendTest extends AlgosTest {

    @Autowired
    private ProvaBackend backend;


    //--simpleName
    //--canonicalName
    //--flag esiste
    //--flag unico
    protected static Stream<Arguments> FILE_CANONICAL_NAME_MODULO() {
        return Stream.of(
                Arguments.of(VUOTA, VUOTA, false, false),
                Arguments.of("Logic", "it/algos/vaad24/backend/logic/Logic", true, true),
                Arguments.of("Pippoz", VUOTA, false, false),
                Arguments.of("View", VUOTA, false, true),
                Arguments.of("Backend", VUOTA, false, true),
                Arguments.of("Mese", "it/algos/vaad24/backend/packages/crono/mese/Mese", true, true),
                Arguments.of("MeseView", "it/algos/vaad24/backend/packages/crono/mese/MeseView", true, true),
                Arguments.of("MeseBackend", "it/algos/vaad24/backend/packages/crono/mese/MeseBackend", true, true),
                Arguments.of("MeseDialog", VUOTA, false, false),
                Arguments.of("Prova", VUOTA, false, true),
                Arguments.of("test/Prova", "it/algos/vaad24/backend/packages/utility/test/Prova", true, false),
                Arguments.of("Prova", VUOTA, false, false),
                Arguments.of("test.Prova", "it/algos/vaad24/backend/packages/utility/test/Prova", true, false),
                Arguments.of("SimpleCost", "it/algos/vaad24simple/backend/boot/SimpleCost", true, true)
        );
    }

    /**
     * Qui passa una volta sola <br>
     */
    @BeforeAll
    protected void setUpAll() {
        super.entityClazz = Prova.class;

        super.setUpAll();
    }


    @ParameterizedTest
    @MethodSource(value = "FILE_CANONICAL_NAME_MODULO")
    @Order(1)
    @DisplayName("1 - getCanonicalName")
        //--simpleName
        //--canonicalName
        //--flag esiste
        //--flag unico
    void getCanonicalName(final String simpleName, String canonicalName, boolean esiste, boolean unico) {
        System.out.println("41 - getCanonicalName");
        System.out.println(VUOTA);

        sorgente = simpleName;
        previsto = canonicalName;
        previsto = previsto.replaceAll(SLASH, PUNTO);

        ottenuto = fileService.getCanonicalName(sorgente);
        assertEquals(esiste, textService.isValid(ottenuto));
        assertEquals(previsto, ottenuto);
        if (textService.isValid(ottenuto)) {
            message = String.format("%s%s%s", sorgente, FORWARD, ottenuto);
            System.out.println(message);
        }
    }

}
