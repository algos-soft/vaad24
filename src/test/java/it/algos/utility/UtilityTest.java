package it.algos.utility;

import it.algos.*;
import it.algos.base.*;
import it.algos.vaad24.backend.boot.*;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.*;
import org.springframework.boot.test.context.*;
import org.springframework.test.context.junit.jupiter.*;

import java.util.*;

/**
 * Project vaad24
 * Created by Algos
 * User: gac
 * Date: Tue, 13-Dec-2022
 * Time: 14:39
 * Unit test di utility varie <br>
 * Estende la classe astratta AlgosTest che contiene le regolazioni essenziali <br>
 * Nella superclasse AlgosTest vengono iniettate (@InjectMocks) tutte le altre classi di service <br>
 * Nella superclasse AlgosTest vengono regolati tutti i link incrociati tra le varie classi singleton di service <br>
 */
@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = {Vaad24SimpleApp.class})
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Tag("integration")
@DisplayName("Utility")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class UtilityTest extends AlgosTest {


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
     * Qui passa prima di ogni test delle sottoclassi <br>
     * Invocare PRIMA il metodo setUp() della superclasse <br>
     * Si possono aggiungere regolazioni specifiche <br>
     */
    @BeforeEach
    protected void setUpEach() {
        super.setUpEach();
    }


    @Test
    @Order(1)
    @DisplayName("1 - tree")
    void resetOnlyEmpty() {
        System.out.println("1 - tree");
        sorgenteArray = Arrays.asList("Anno", "Secolo", "Continente", "Giorno", "Mese", "Via");
        previstoArray = Arrays.asList("Via", "Continente", "Secolo", "Anno", "Mese", "Giorno");
        ottenutoArray = new ArrayList<>();
        sorgente = VaadVar.moduloVaadin24;

        sorgenteArray = classService.allModuleEntityResetName(sorgente);
        ottenutoArray = arrayService.orderTree(sorgenteArray);

        Map<String, String> mappa = new HashMap();
        mappa.put("Anno", "Secolo");
        mappa.put("Secolo", "");
        mappa.put("Continente", "");
        mappa.put("Giorno", "Mese");
        mappa.put("Mese", "");
        mappa.put("Via", "");

        //        String value;
        //        for (String key : mappa.keySet()) {
        //            value = mappa.get(key);
        //            if (textService.isEmpty(value)) {
        //                if (!ottenutoArray.contains(key)) {
        //                    ottenutoArray.add(key);
        //                }
        //            }
        //            else {
        //                if (ottenutoArray.contains(value)) {
        //                }
        //                else {
        //                    if (!ottenutoArray.contains(value)) {
        //                        ottenutoArray.add(value);
        //                    }
        //                    if (!ottenutoArray.contains(key)) {
        //                        ottenutoArray.add(key);
        //                    }
        //                }
        //            }
        //        }

        System.out.println(ottenutoArray);
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