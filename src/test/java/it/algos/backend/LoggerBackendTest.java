package it.algos.backend;

import it.algos.*;
import it.algos.base.*;
import it.algos.vaad24.backend.packages.utility.logger.*;
import org.junit.jupiter.api.*;
import org.springframework.boot.test.context.*;

/**
 * Project vaad24
 * Created by Algos
 * User: gac
 * Date: Sat, 25-Feb-2023
 * Time: 19:59
 */
@SpringBootTest(classes = {Vaad24SimpleApp.class})
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Tag("backend")
@DisplayName("Logger Backend")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class LoggerBackendTest extends BackendTest {

    private ALoggerBackend backend;

    /**
     * Qui passa una volta sola <br>
     */
    @BeforeAll
    protected void setUpAll() {
        this.backend = super.loggerBackend;
        super.entityClazz = ALogger.class;
        super.typeBackend = TypeBackend.logger;
        super.crudBackend = backend;

        super.setUpAll();
    }


}
