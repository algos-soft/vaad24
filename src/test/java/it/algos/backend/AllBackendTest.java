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
import it.algos.vaad24.backend.wrapper.*;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.provider.*;
import org.springframework.boot.test.context.*;

import java.util.*;
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
public class AllBackendTest extends AlgosTest {

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

    /**
     * Qui passa una volta sola <br>
     */
    @BeforeAll
    protected void setUpAll() {
        super.setUpAll();

        super.typeBackend = TypeBackend.nessuno;
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
        listaClazz = classService.allPackagesEntityClazz();

        for (Class classe : listaClazz) {
            ottenutoBooleano = mongoService.isExistsCollection(classe);
            clazzName = classe.getSimpleName();
            collectionName = annotationService.getCollectionName(classe);
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
    @DisplayName("12 - count")
    protected void count() {
        System.out.println("12 - count");
        System.out.println("Recupera tutte le classi del progetto corrente");
        System.out.println(VUOTA);

        // recupero i nomi di tutte le classi AEntity dei package
        listaClazz = classService.allPackagesEntityClazz();

        for (Class classe : listaClazz) {
            ottenutoIntero = mongoService.count(classe);
            clazzName = classe.getSimpleName();
            collectionName = annotationService.getCollectionName(classe);
            if (ottenutoIntero > 0) {
                message = String.format("La collection '%s' della classe [%s] ha in totale %s entities nel database mongoDB", collectionName, clazzName, textService.format(ottenutoIntero));
            }
            else {
                message = String.format("Non esiste la collection '%s' della classe [%s]", collectionName, clazzName);
            }
            System.out.println(message);
        }
    }


    @Test
    @Order(13)
    @DisplayName("13 - resetOnlyEmpty")
    protected void resetOnlyEmpty() {
        System.out.println("13 - resetOnlyEmpty");
        System.out.println("Recupera tutte le classi del progetto corrente");
        System.out.println(VUOTA);

        // recupero i nomi di tutte le classi AEntity dei package
        listaBackendClazz = classService.getAllBackend();

        for (CrudBackend backend : listaBackendClazz) {
            ottenutoRisultato = backend.resetOnlyEmpty();
            assertNotNull(ottenutoRisultato);
            if (ottenutoRisultato.isValido()) {
                System.out.println(ottenutoRisultato.getMessage());
                printRisultato(ottenutoRisultato);

                System.out.println(VUOTA);
                printBackend(ottenutoRisultato.getLista());
            }
            else {
                logger.warn(new WrapLog().message(ottenutoRisultato.getErrorMessage()));
            }
        }
    }


    @Test
    @Order(14)
    @DisplayName("14 - resetForcing")
    protected void resetForcing() {
        System.out.println("14 - resetForcing");
        System.out.println("Recupera tutte le classi del progetto corrente");
        System.out.println(VUOTA);

        // recupero i nomi di tutte le classi AEntity dei package
        listaBackendClazz = classService.getAllBackend();

        for (CrudBackend backend : listaBackendClazz) {
            ottenutoRisultato = backend.resetForcing();
            assertNotNull(ottenutoRisultato);
            if (ottenutoRisultato.isValido()) {
                System.out.println(ottenutoRisultato.getMessage());
                printRisultato(ottenutoRisultato);

                System.out.println(VUOTA);
                printBackend(ottenutoRisultato.getLista());
            }
            else {
                logger.warn(new WrapLog().message(ottenutoRisultato.getErrorMessage()));
            }
        }
    }

    protected void printBackend(final List lista) {
        if (lista == null) {
            return;
        }

        if (lista.size() == 1) {
            printBackend(lista, 1);
        }
        else {
            printBackend(lista, 10);
        }
    }

}
