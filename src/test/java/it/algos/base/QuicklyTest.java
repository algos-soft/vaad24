package it.algos.base;

import it.algos.vaad24.backend.service.*;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import org.mockito.*;

/**
 * Project vaad24
 * Created by Algos
 * User: gac
 * Date: Sun, 30-Jul-2023
 * Time: 08:39
 * Unit test di una classe service o backend o query <br>
 * Estende la classe astratta AlgosTest che contiene le regolazioni essenziali <br>
 * Nella superclasse AlgosTest vengono iniettate (@InjectMocks) tutte le altre classi di service <br>
 * Nella superclasse AlgosTest vengono regolati tutti i link incrociati tra le varie classi singleton di service <br>
 */
public abstract class QuicklyTest extends AlgosTest {


    @InjectMocks
    protected TextService textService;

    @InjectMocks
    protected DateService dateService;


    /**
     * Qui passa una volta sola, chiamato dalle sottoclassi <br>
     * Invocare PRIMA il metodo setUpStartUp() della superclasse <br>
     * Si possono aggiungere regolazioni specifiche <br>
     */
    @BeforeAll
    protected void setUpAll() {
        this.initMocks();
        this.checkMocks();
        this.crossReferences();
    }

    protected void initMocks() {
        MockitoAnnotations.openMocks(this);
        MockitoAnnotations.openMocks(textService);
        MockitoAnnotations.openMocks(dateService);

    }

    protected void checkMocks() {
        assertNotNull(textService);
        assertNotNull(dateService);
    }

    protected void crossReferences() {
        dateService.textService = textService;
    }

    /**
     * Qui passa prima di ogni test delle sottoclassi <br>
     * Invocare PRIMA il metodo setUp() della superclasse <br>
     * Si possono aggiungere regolazioni specifiche <br>
     */
    @BeforeEach
    protected void setUpEach() {
        super.setUpEach();
    }


}