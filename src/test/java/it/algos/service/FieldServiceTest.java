package it.algos.service;

import com.vaadin.flow.component.*;
import com.vaadin.flow.component.datepicker.*;
import com.vaadin.flow.component.datetimepicker.*;
import com.vaadin.flow.component.textfield.*;
import it.algos.*;
import it.algos.base.*;
import it.algos.vaad24.backend.fields.*;
import it.algos.vaad24.backend.packages.anagrafica.*;
import it.algos.vaad24.backend.packages.utility.log.*;
import it.algos.vaad24.backend.packages.utility.nota.*;
import it.algos.vaad24.backend.service.*;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.Tag;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.extension.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.boot.test.context.*;
import org.springframework.test.context.junit.jupiter.*;

/**
 * Project vaad24
 * Created by Algos
 * User: gac
 * Date: Sun, 28-May-2023
 * Time: 06:15
 */
@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = {Vaad24SimpleApp.class})
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Tag("service")
@DisplayName("Field Service")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class FieldServiceTest extends AlgosTest {

    /**
     * Classe principale di riferimento <br>
     * Gia 'costruita' nella superclasse <br>
     */
    @Autowired
    private FieldService service;


    /**
     * Qui passa una volta sola, chiamato dalle sottoclassi <br>
     * Invocare PRIMA il metodo setUpStartUp() della superclasse <br>
     * Si possono aggiungere regolazioni specifiche <br>
     */
    @BeforeAll
    protected void setUpAll() {
        super.setUpAll();
    }


    /**
     * Qui passa a ogni test delle sottoclassi <br>
     * Invocare PRIMA il metodo setUp() della superclasse <br>
     * Si possono aggiungere regolazioni specifiche <br>
     */
    @BeforeEach
    protected void setUpEach() {
        super.setUpEach();
    }


    @Test
    @Order(1)
    @DisplayName("crea")
    void crea() {
        AbstractField field = null;

        clazz = Via.class;
        sorgente = "nessuno";
        field = service.crea(clazz, sorgente);
        assertNull(field);

        sorgente = "nome";
        field = service.crea(clazz, sorgente);
        assertNotNull(field);
        assertTrue(field instanceof TextField);

        clazz = Nota.class;
        sorgente = "livello";
        field = service.crea(clazz, sorgente);
        assertNotNull(field);
        assertTrue(field instanceof AComboField);

        sorgente = "inizio";
        field = service.crea(clazz, sorgente);
        assertNotNull(field);
        assertTrue(field instanceof DatePicker);

        clazz = ALogger.class;
        sorgente = "evento";
        field = service.crea(clazz, sorgente);
        assertNotNull(field);
        assertTrue(field instanceof DateTimePicker);
    }


    /**
     * Qui passa al termine di ogni singolo test <br>
     */
    @AfterEach
    void tearDown() {
    }


    /**
     * Qui passa una volta sola, chiamato alla fine di tutti i tests <br>
     */
    @AfterAll
    void tearDownAll() {
    }

}
