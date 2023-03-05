package it.algos.backend;

import it.algos.*;
import it.algos.base.*;
import it.algos.vaad24.backend.packages.crono.mese.*;
import org.junit.jupiter.api.*;
import org.springframework.boot.test.context.*;

/**
 * Project vaad24
 * Created by Algos
 * User: gac
 * Date: Wed, 22-Feb-2023
 * Time: 18:27
 */
@SpringBootTest(classes = {Vaad24SimpleApp.class})
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Tag("integration")
@Tag("backend")
@DisplayName("Mese Backend")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class MeseBackendTest extends BackendTest {


    private MeseBackend backend;

    /**
     * Qui passa una volta sola <br>
     */
    @BeforeAll
    protected void setUpAll() {
        this.backend = super.meseBackend;
        super.entityClazz = Mese.class;
        super.typeBackend = TypeBackend.mese;
        super.crudBackend = backend;

        super.setUpAll();
    }


}
