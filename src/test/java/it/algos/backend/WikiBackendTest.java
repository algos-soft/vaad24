package it.algos.backend;

import it.algos.*;
import it.algos.base.*;
import static it.algos.vaad24.backend.boot.VaadCost.*;
import it.algos.vaad24.backend.boot.*;
import it.algos.vaad24.backend.logic.*;
import it.algos.vaad24.backend.packages.anagrafica.*;
import it.algos.vaad24.backend.packages.crono.anno.*;
import it.algos.vaad24.backend.packages.crono.giorno.*;
import it.algos.vaad24.backend.packages.crono.mese.*;
import it.algos.vaad24.backend.packages.crono.secolo.*;
import it.algos.vaad24.backend.packages.geografia.continente.*;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.provider.*;
import org.springframework.boot.test.context.*;

import java.util.stream.*;

/**
 * Project vaad24
 * Created by Algos
 * User: gac
 * Date: Sun, 19-Mar-2023
 * Time: 07:05
 */
@SpringBootTest(classes = {Vaad24SimpleApp.class})
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Tag("backend")
@DisplayName("Wiki Backend")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class WikiBackendTest extends AlgosTest {

    private String collectionName;

    //--clazz
    //--simpleName
    protected static Stream<Arguments> CLAZZ_BACKEND() {
        return Stream.of(
                Arguments.of(ViaBackend.class),
                Arguments.of(AnnoBackend.class),
                Arguments.of(GiornoBackend.class),
                Arguments.of(MeseBackend.class),
                Arguments.of(SecoloBackend.class),
                Arguments.of(ContinenteBackend.class)
        );
    }

    @BeforeEach
    protected void setUpEach() {
        super.setUpEach();
        collectionName = VUOTA;
    }

    @Test
    @Order(1)
    @DisplayName("1 - allBackendName")
    protected void allBackendName() {
        System.out.println("1 - allBackendName");
        System.out.println(VUOTA);

        sorgente = VaadVar.moduloVaadin24;
        listaStr = classService.allModuleBackendName(sorgente);
        message = String.format("Nel modulo %s ci sono %d classi di tipo [CrudBackend]", sorgente, listaStr.size());
        System.out.println(message);
        System.out.println(listaStr);
        System.out.println(VUOTA);

        sorgente = VaadVar.projectNameModulo;
        listaStr = classService.allModuleBackendName(sorgente);
        message = String.format("Nel modulo %s ci sono %d classi di tipo [CrudBackend]", sorgente, listaStr.size());
        System.out.println(message);
        System.out.println(listaStr);
        System.out.println(VUOTA);

        listaStr = classService.allBackendName();
        message = String.format("Nel progetto corrente ci sono in totale %d classi di tipo [CrudBackend]", listaStr.size());
        System.out.println(message);
        System.out.println(listaStr);
        System.out.println(VUOTA);
    }


    @Test
    @Order(2)
    @DisplayName("2 - allBackendResetOrderedName")
    protected void allBackendResetOrderedName() {
        System.out.println("2 - allBackendResetOrderedName");
        System.out.println(VUOTA);

        sorgente = VaadVar.moduloVaadin24;
        listaStr = classService.allModuleBackendResetOrderedName(sorgente);
        message = String.format("Nel modulo %s ci sono %d classi di tipo [CrudBackend] che implementano il metodo %s", sorgente, listaStr.size(), METHOD_NAME_RESET_ONLY);
        System.out.println(message);
        System.out.println(listaStr);
        System.out.println(VUOTA);

        sorgente = VaadVar.projectNameModulo;
        listaStr = classService.allModuleBackendResetOrderedName(sorgente);
        message = String.format("Nel modulo %s ci sono %d classi di tipo [CrudBackend] che implementano il metodo %s", sorgente, listaStr.size(), METHOD_NAME_RESET_ONLY);
        System.out.println(message);
        System.out.println(listaStr);
        System.out.println(VUOTA);

        listaStr = classService.allBackendResetOrderedName();
        message = String.format("Nel progetto corrente ci sono in totale %d classi di tipo [CrudBackend] che implementano il metodo %s", listaStr.size(), METHOD_NAME_RESET_ONLY);
        System.out.println(message);
        System.out.println(listaStr);
        System.out.println(VUOTA);
    }


    @Test
    @Order(3)
    @DisplayName("3 - allEntityResetOrderedName")
    protected void allEntityResetOrderedName() {
        System.out.println("3 - allEntityResetOrderedName");
        System.out.println(VUOTA);

        sorgente = VaadVar.moduloVaadin24;
        listaStr = classService.allModuleEntityResetOrderedName(sorgente);
        message = String.format("Nel modulo %s ci sono %d classi di tipo [AEntity] che implementano il metodo %s (nella corrispondente xxxBackend)", sorgente, listaStr.size(), METHOD_NAME_RESET_ONLY);
        System.out.println(message);
        System.out.println(listaStr);
        System.out.println(VUOTA);

        sorgente = VaadVar.projectNameModulo;
        listaStr = classService.allModuleEntityResetOrderedName(sorgente);
        message = String.format("Nel modulo %s ci sono %d classi di tipo [AEntity] che implementano il metodo %s (nella corrispondente xxxBackend)", sorgente, listaStr.size(), METHOD_NAME_RESET_ONLY);
        System.out.println(message);
        System.out.println(listaStr);
        System.out.println(VUOTA);

        listaStr = classService.allEntityResetOrderedName();
        message = String.format("Nel progetto corrente ci sono in totale %d classi di tipo [AEntity] che implementano il metodo %s (nella corrispondente xxxBackend)", listaStr.size(), METHOD_NAME_RESET_ONLY);
        System.out.println(message);
        System.out.println(listaStr);
        System.out.println(VUOTA);


    }


    @Test
    @Order(11)
    @DisplayName("11 - isExistsCollection")
    void isExistsCollection() {
        System.out.println("11 - isExistsCollection");
        System.out.println("Recupera tutte le classi del progetto corrente");
        System.out.println(VUOTA);

        // recupero i nomi di tutte le classi AEntity dei package
        listaStr = classService.allEntityResetOrderedName();
        listaBackendClazz = classService.getBackendsFromEntitiesNames(listaStr);

        for (CrudBackend backend : listaBackendClazz) {
            ottenutoBooleano = backend.isExistsCollection();
            if (ottenutoBooleano) {
                message = String.format("Esiste la collection della classe [%s] e si chiama '%s'", clazzName, collectionName);
            }
            else {
                message = String.format("Non esiste la collection '%s' della classe [%s]", collectionName, clazzName);
            }
            System.out.println(message);
        }
    }

    @Test
    @Order(12)
    @DisplayName(PUNTO)
    protected void count() {
    }

    @Test
    @Order(13)
    @DisplayName(PUNTO)
    protected void resetOnlyEmpty() {
    }

    @Test
    @Order(14)
    @DisplayName(PUNTO)
    protected void resetForcing() {
    }

}
