package it.algos.simpleOnly;

import it.algos.*;
import it.algos.base.*;
import it.algos.vaad24simple.backend.packages.prova.*;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.*;
import org.mockito.*;
import org.springframework.boot.test.context.*;

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
@Tag("backend")
@DisplayName("Prova Backend")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ProvaBackendTest extends BackendTest {

    @InjectMocks
    private ProvaBackend backend;

    /**
     * Qui passa una volta sola <br>
     */
    @BeforeAll
    protected void setUpAll() {
        assertNotNull(backend);
        super.entityClazz = Prova.class;
        super.crudBackend = backend;
        super.setUpAll();
    }

}
