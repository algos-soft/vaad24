package it.algos.backend;

import it.algos.*;
import it.algos.base.*;
import it.algos.vaad24.backend.packages.anagrafica.*;
import org.junit.jupiter.api.*;
import org.springframework.boot.test.context.*;

/**
 * Project vaad24
 * Created by Algos
 * User: gac
 * Date: Wed, 22-Feb-2023
 * Time: 18:26
 */
@SpringBootTest(classes = {Vaad24SimpleApp.class})
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Tag("integration")
@Tag("backend")
@DisplayName("Via Backend")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ViaBackendTest extends BackendTest {

    private ViaBackend backend;


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

}
