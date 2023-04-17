package it.algos.service;

import com.google.common.collect.*;
import com.google.common.reflect.*;
import it.algos.*;
import it.algos.base.*;
import it.algos.vaad24.backend.boot.*;
import static it.algos.vaad24.backend.boot.VaadCost.*;
import it.algos.vaad24.backend.exception.*;
import it.algos.vaad24.backend.service.*;
import it.algos.vaad24.backend.wrapper.*;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import org.springframework.boot.test.context.*;

import java.io.*;
import java.util.*;

/**
 * Project vaad24
 * Created by Algos
 * User: gac
 * Date: Sat, 15-Apr-2023
 * Time: 11:43
 */
@SpringBootTest(classes = {Vaad24SimpleApp.class})
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Tag("service")
@DisplayName("JarFile Service")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class JarFileServiceTest extends AlgosTest {

    /**
     * Classe principale di riferimento <br>
     * Gia 'costruita' nella superclasse <br>
     */
    private JarFileService service;


    //    private static final String JAR_PATH = "example-jar/stripe-0.0.1-SNAPSHOT.jar";
    private static final String JAR_PATH = "/Users/gac/Desktop/wiki/wiki24-1.0.jar";

    private static final Set<String> EXPECTED_CLASS_NAMES = Sets.newHashSet(
            "com.baeldung.stripe.StripeApplication",
            "com.baeldung.stripe.ChargeRequest",
            "com.baeldung.stripe.StripeService",
            "com.baeldung.stripe.ChargeRequest$Currency",
            "com.baeldung.stripe.ChargeController",
            "com.baeldung.stripe.CheckoutController"
    );


    /**
     * Qui passa una volta sola, chiamato dalle sottoclassi <br>
     * Invocare PRIMA il metodo setUpStartUp() della superclasse <br>
     * Si possono aggiungere regolazioni specifiche <br>
     */
    @BeforeAll
    protected void setUpAll() {
        super.setUpAll();

        //--reindirizzo l'istanza della superclasse
        service = jarFileService;
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


    //    @Test
    @Order(1)
    @DisplayName("1 - getClassNamesFromJarFile")
    void getClassNamesFromJarFile() {
        System.out.println("1 - getClassNamesFromJarFile");
        Set<String> classNames = null;
        File jarFile = null;

        try {
            Object alfa = getClass();
            Object beta = getClass().getClassLoader().getDefinedPackage(JAR_PATH);
            Object beta2 = getClass().getClassLoader().getResourceAsStream(JAR_PATH);
            Object gamma = getClass().getClassLoader().getResource(JAR_PATH);
            Object delta = getClass().getClassLoader().getResources(JAR_PATH);
            int a = 87;
            //            jarFile = new File(Objects.requireNonNull(getClass().getClassLoader().getResource(JAR_PATH)));
        } catch (Exception unErrore) {
            logger.error(new WrapLog().exception(new AlgosException(unErrore)));
        }

        try {
            classNames = service.getClassNamesFromJarFile(jarFile);
        } catch (Exception unErrore) {
            logger.error(new WrapLog().exception(new AlgosException(unErrore)));
        }

        for (String nome : classNames) {
            System.out.println(String.format("Nome: %s", nome));
        }

        //        assertEqual(EXPECTED_CLASS_NAMES, classNames);
    }

    //    @Test
    @Order(2)
    @DisplayName("2 - getTopLevelClassesRecursive")
    void getTopLevelClassesRecursive() {
        System.out.println("2 - getTopLevelClassesRecursive");
        Set<String> classNames = null;
        sorgente = JAR_PATH;

        try {
            ClassPath cp = ClassPath.from(Thread.currentThread().getContextClassLoader());
            ImmutableSet<ClassPath.ClassInfo> alfa = cp.getTopLevelClasses(sorgente);
            ImmutableSet<ClassPath.ClassInfo> alfa2 = cp.getAllClasses();
            ImmutableSet<ClassPath.ClassInfo> alfa3 = cp.getTopLevelClassesRecursive(sorgente);
            ImmutableSet<ClassPath.ResourceInfo> alfa4 = cp.getResources();
            int a = 87;
            //            for (String stringa : alfa) {
            //            }

        } catch (Exception unErrore) {
            logger.error(new WrapLog().exception(new AlgosException(unErrore)).usaDb());
        }
    }


    @Test
    @Order(3)
    @DisplayName("3 - getAllClassNames")
    void getAllClassNames() {
        System.out.println("3 - getAllClassNames");
        sorgente = JAR_PATH;

        listaStr = service.getAllClassNames(sorgente);
        assertNotNull(listaStr);
        printSubLista(listaStr);
    }


    @Test
    @Order(4)
    @DisplayName("4 - getBootClassNames")
    void getBootClassNames() {
        System.out.println("4 - getBootClassNames");
        sorgente = JAR_PATH;

        listaStr = service.getBootClassNames(sorgente);
        assertNotNull(listaStr);
        printSubLista(listaStr);
    }


    @Test
    @Order(5)
    @DisplayName("5 - getBootClassNamesWithoutMethods")
    void getBootClassNamesWithoutMethods() {
        System.out.println("5 - getBootClassNamesWithoutMethods");
        sorgente = JAR_PATH;

        listaStr = service.getBootClassNamesWithoutMethods(sorgente);
        assertNotNull(listaStr);
        printSubLista(listaStr);
    }

    @Test
    @Order(6)
    @DisplayName("6 - getModuloClassNames")
    void getModuloClassNames() {
        System.out.println("6 - getModuloClassNames");
        sorgente = JAR_PATH;
        sorgente2 = VaadVar.moduloVaadin24;

        listaStr = service.getModuloClassNames(sorgente, sorgente2);
        assertNotNull(listaStr);
        printSubLista(listaStr);
    }

    @Test
    @Order(7)
    @DisplayName("7 - getPackageClassNames")
    void getPackageClassNames() {
        System.out.println("7 - getPackageClassNames");
        sorgente = JAR_PATH;
        sorgente2 = VaadVar.moduloVaadin24;

        listaStr = service.getPackageClassNames(sorgente, sorgente2);
        assertNotNull(listaStr);
        printSubLista(listaStr);
    }

    @Test
    @Order(8)
    @DisplayName("8 - getBackendClassNames")
    void getBackendClassNames() {
        System.out.println("8 - getBackendClassNames");
        sorgente = JAR_PATH;
        sorgente2 = VaadVar.moduloVaadin24;

        listaStr = service.getBackendClassNames(sorgente, sorgente2);
        assertNotNull(listaStr);
        printLista(listaStr);
    }

    @Test
    @Order(9)
    @DisplayName("9 - getClazzFromName")
    void getClazzFromName() {
        System.out.println("8 - getClazzFromName");
        sorgente = JAR_PATH;
        sorgente2 = VaadVar.moduloVaadin24;

        listaStr = service.getBackendClassNames(sorgente, sorgente2);
        assertNotNull(listaStr);
        sorgente3 = listaStr.get(0);
        sorgente3 = textService.pointToSlash(sorgente3);
        sorgente3 = sorgente + SLASH + sorgente3;
        sorgente3 = sorgente3 + ".class";
        clazz = service.getClazzFromName(sorgente3);
        System.out.println(clazz != null ? clazz.getSimpleName() : VUOTA);

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
