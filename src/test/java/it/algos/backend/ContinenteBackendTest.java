package it.algos.backend;

import it.algos.*;
import it.algos.base.*;
import it.algos.vaad24.backend.packages.geografia.continente.*;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.*;
import org.mockito.*;
import org.springframework.boot.test.context.*;

/**
 * Project vaad24
 * Created by Algos
 * User: gac
 * Date: Thu, 23-Feb-2023
 * Time: 09:58
 */
@SpringBootTest(classes = {Vaad24SimpleApp.class})
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Tag("integration")
@Tag("backend")
@DisplayName("Continente Backend")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ContinenteBackendTest extends BackendTest {

    @InjectMocks
    private ContinenteBackend backend;

    /**
     * Qui passa una volta sola <br>
     */
    @BeforeAll
    protected void setUpAll() {
        assertNotNull(backend);
        super.entityClazz = Continente.class;
        super.crudBackend = backend;
        super.setUpAll();
    }

}
