package it.algos.utility;

import it.algos.*;
import it.algos.base.*;
import static it.algos.vaad24.backend.boot.VaadCost.*;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.*;
import org.springframework.boot.test.context.*;
import org.springframework.test.context.junit.jupiter.*;

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
@SpringBootTest(classes = {Vaad24SimpleApplication.class})
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Tag("integration")
@DisplayName("Reset")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ResetTest extends AlgosIntegrationTest {


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
    @DisplayName("1 - resetOnlyEmpty con collection esistenti")
    void resetOnlyEmpty() {
        System.out.println("1 - resetOnlyEmpty (ordinato) di alcune classi backend");

        sorgente = "vaad24";
        message = String.format("Reset (ordinato) di tutte le classi backend del package '%s' che implementano il metodo %s()", sorgente, TAG_RESET_ONLY);
        System.out.println(message);

        listaClazz = classService.allModuleBackendResetOrderedClass(sorgente);
        if (listaClazz != null && listaClazz.size() > 0) {
            message = String.format("Ci sono in totale %d classi nella directory package del modulo %s", listaClazz.size(), sorgente);
            System.out.println(message);
            System.out.println(VUOTA);
            printClazz(listaClazz);
        }
        else {
            message = String.format("Non esiste il modulo '%s' oppure non esiste la directory 'package' oppure non ci sono subdirectories", sorgente);
            System.out.println(message);
        }

        if (listaClazz != null && listaClazz.size() > 0) {
            for (Class clazz : listaClazz) {
                ottenutoRisultato = classService.esegueMetodo(clazz.getCanonicalName(), TAG_RESET_ONLY);
                printRisultato(ottenutoRisultato);
            }
        }
    }


    @Test
    @Order(2)
    @DisplayName("2 - resetOnlyEmpty con collection NON esistenti")
    void resetOnlyEmpty2() {
        System.out.println("2 - resetOnlyEmpty (ordinato) di alcune classi 'backend'");

        sorgente = "vaad24";
        message = String.format("Reset (ordinato) di tutte le classi 'backend' del package '%s' che implementano il metodo %s()", sorgente, TAG_RESET_ONLY);
        System.out.println(message);

        listaClazz = classService.allModuleBackendResetOrderedClass(sorgente);
        if (listaClazz != null && listaClazz.size() > 0) {
            message = String.format("Ci sono in totale %d classi nella directory package del modulo %s", listaClazz.size(), sorgente);
            System.out.println(message);
            System.out.println(VUOTA);
            printClazz(listaClazz);
        }
        else {
            message = String.format("Non esiste il modulo '%s' oppure non esiste la directory 'package' oppure non ci sono subdirectories", sorgente);
            System.out.println(message);
        }

        if (listaClazz != null && listaClazz.size() > 0) {
            for (Class backendClazz : listaClazz) {
                clazz = classService.getEntityFromBackendClazz(backendClazz).getClass();
                mongoService.deleteAll(clazz.getSimpleName().toLowerCase());
            }
            System.out.println(VUOTA);
            message = String.format("Cancellate tutte le %d classi 'backend'", listaClazz.size());
            System.out.println(message);
        }

        if (listaClazz != null && listaClazz.size() > 0) {
            for (Class backendClazz : listaClazz) {
                ottenutoRisultato = classService.esegueMetodo(backendClazz.getCanonicalName(), TAG_RESET_ONLY);
                printRisultato(ottenutoRisultato);
            }
        }
    }


    @Test
    @Order(3)
    @DisplayName("3 - resetForcing con collection NON esistenti")
    void resetForcing() {
        System.out.println("3 - resetForcing (ordinato) di alcune classi backend");

        sorgente = "vaad24";
        message = String.format("Reset (ordinato) di tutte le classi backend del package '%s' che implementano il metodo %s()", sorgente, TAG_RESET_ONLY);
        System.out.println(message);

        listaClazz = classService.allModuleBackendResetOrderedClass(sorgente);
        if (listaClazz != null && listaClazz.size() > 0) {
            message = String.format("Ci sono in totale %d classi nella directory package del modulo %s", listaClazz.size(), sorgente);
            System.out.println(message);
            System.out.println(VUOTA);
            printClazz(listaClazz);
        }
        else {
            message = String.format("Non esiste il modulo '%s' oppure non esiste la directory 'package' oppure non ci sono subdirectories", sorgente);
            System.out.println(message);
        }


        if (listaClazz != null && listaClazz.size() > 0) {
            for (Class backendClazz : listaClazz) {
                clazz = classService.getEntityFromBackendClazz(backendClazz).getClass();
                mongoService.deleteAll(clazz.getSimpleName().toLowerCase());
            }
            System.out.println(VUOTA);
            message = String.format("Cancellate tutte le %d classi 'backend'", listaClazz.size());
            System.out.println(message);
        }


        if (listaClazz != null && listaClazz.size() > 0) {
            for (Class clazz : listaClazz) {
                ottenutoRisultato = classService.esegueMetodo(clazz.getCanonicalName(), TAG_RESET_FORCING);
                printRisultato(ottenutoRisultato);
            }
        }
    }


    @Test
    @Order(4)
    @DisplayName("4 - resetForcing con collection esistenti")
    void resetForcing2() {
        System.out.println("4 - resetForcing (ordinato) di alcune classi backend");

        sorgente = "vaad24";
        message = String.format("Reset (ordinato) di tutte le classi backend del package '%s' che implementano il metodo %s()", sorgente, TAG_RESET_ONLY);
        System.out.println(message);

        listaClazz = classService.allModuleBackendResetOrderedClass(sorgente);
        if (listaClazz != null && listaClazz.size() > 0) {
            message = String.format("Ci sono in totale %d classi nella directory package del modulo %s", listaClazz.size(), sorgente);
            System.out.println(message);
            System.out.println(VUOTA);
            printClazz(listaClazz);
        }
        else {
            message = String.format("Non esiste il modulo '%s' oppure non esiste la directory 'package' oppure non ci sono subdirectories", sorgente);
            System.out.println(message);
        }

        if (listaClazz != null && listaClazz.size() > 0) {
            for (Class clazz : listaClazz) {
                ottenutoRisultato = classService.esegueMetodo(clazz.getCanonicalName(), TAG_RESET_FORCING);
                printRisultato(ottenutoRisultato);
            }
        }
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