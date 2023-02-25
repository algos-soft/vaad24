package it.algos.backend;

import it.algos.*;
import it.algos.base.*;
import it.algos.vaad24.backend.packages.utility.versione.*;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.*;
import org.mockito.*;
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
@Tag("integration")
@Tag("backend")
@DisplayName("Versione Backend")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class VersioneBackendTest extends BackendTest {

    @InjectMocks
    private VersioneBackend backend;

    /**
     * Qui passa una volta sola <br>
     */
    @BeforeAll
    protected void setUpAll() {
        assertNotNull(backend);
        super.entityClazz = Versione.class;
        super.crudBackend = backend;
        super.setUpAll();
    }

}
