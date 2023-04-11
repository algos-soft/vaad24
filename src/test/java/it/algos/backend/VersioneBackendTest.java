package it.algos.backend;

import it.algos.*;
import it.algos.base.*;
import it.algos.vaad24.backend.packages.utility.versione.*;
import org.junit.jupiter.api.*;
import org.springframework.boot.test.context.*;

/**
 * Project vaad24
 * Created by Algos
 * User: gac
 * Date: Sat, 25-Feb-2023
 * Time: 12:00
 */
@SpringBootTest(classes = {Vaad24SimpleApp.class})
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Tag("backend")
@DisplayName("Versione Backend")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class VersioneBackendTest extends BackendTest {

    private VersioneBackend backend;

    /**
     * Qui passa una volta sola <br>
     */
    @BeforeAll
    protected void setUpAll() {
        this.backend = super.versioneBackend;
        super.entityClazz = Versione.class;
        super.typeBackend = TypeBackend.versione;
        super.crudBackend = backend;

        super.setUpAll();
    }

}
