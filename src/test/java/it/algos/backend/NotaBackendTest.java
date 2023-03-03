package it.algos.backend;

import it.algos.*;
import it.algos.base.*;
import static it.algos.vaad24.backend.boot.VaadCost.*;
import it.algos.vaad24.backend.packages.utility.nota.*;
import static org.junit.Assert.*;
import org.junit.jupiter.api.*;
import org.springframework.boot.test.context.*;

/**
 * Project vaad24
 * Created by Algos
 * User: gac
 * Date: Fri, 24-Feb-2023
 * Time: 20:45
 */
@SpringBootTest(classes = {Vaad24SimpleApp.class})
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Tag("integration")
@Tag("backend")
@DisplayName("Nota Backend")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class NotaBackendTest extends BackendTest {

    private Nota nota;

    /**
     * Qui passa una volta sola <br>
     */
    @BeforeAll
    protected void setUpAll() {
        super.entityClazz = Nota.class;
        super.crudBackend = notaBackend;
        super.typeBackend = TypeBackend.nota;
        super.setUpAll();
    }

    @Test
    @Order(41)
    @DisplayName("41 - newEntity con ID ma non registrata")
    protected void newEntity() {
        System.out.println("41 - newEntity con ID ma non registrata");
        System.out.println(VUOTA);
        String keyPropertyName;

        assertTrue(annotationService.usaKeyPropertyName(entityClazz));
        keyPropertyName = annotationService.getKeyPropertyName(entityClazz);

        System.out.println("Senza parametri");
        nota = notaBackend.newEntity();
        assertNotNull(nota);
        printNota();
        printNota(nota);
        crudBackend.save(nota);
    }

}
