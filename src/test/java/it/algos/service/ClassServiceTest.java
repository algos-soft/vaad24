package it.algos.service;

import it.algos.*;
import it.algos.base.*;
import it.algos.vaad24.backend.boot.*;
import static it.algos.vaad24.backend.boot.VaadCost.*;
import it.algos.vaad24.backend.service.*;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.extension.*;
import org.junit.jupiter.params.*;
import org.junit.jupiter.params.provider.*;
import org.springframework.boot.test.context.*;
import org.springframework.test.context.junit.jupiter.*;

import java.util.*;
import java.util.stream.*;

/**
 * Project vaad24
 * Created by Algos
 * User: gac
 * Date: Tue, 13-Dec-2022
 * Time: 10:07
 */
@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = {Vaad24SimpleApplication.class})
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Tag("service")
@DisplayName("Class Service")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ClassServiceTest extends AlgosTest {

    /**
     * Classe principale di riferimento <br>
     * Gia 'costruita' nella superclasse <br>
     */
    private ClassService service;

    //--moduleName
    protected static Stream<Arguments> MODULI() {
        return Stream.of(
                Arguments.of(VUOTA),
                Arguments.of("Pippo"),
                Arguments.of("Vaad24"),
                Arguments.of("vaad24"),
                Arguments.of("vaad24Simple"),
                Arguments.of("Vaad23")
        );
    }

    /**
     * Qui passa una volta sola, chiamato dalle sottoclassi <br>
     * Invocare PRIMA il metodo setUpStartUp() della superclasse <br>
     * Si possono aggiungere regolazioni specifiche <br>
     */
    @BeforeAll
    protected void setUpAll() {
        super.setUpAll();

        //--reindirizzo l'istanza della superclasse
        service = classService;
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
    @DisplayName("1 - Test 'a freddo' (senza service)")
    void first() {
        System.out.println("1 - Test 'a freddo' (senza service)");
        System.out.println(VUOTA);

        Class clazz = VaadCost.class;
        String canonicalName = clazz.getCanonicalName();
        assertTrue(textService.isValid(canonicalName));
        System.out.println(canonicalName);
        Class clazz2 = null;

        try {
            clazz2 = Class.forName(canonicalName);
        } catch (Exception unErrore) {
            System.out.println(String.format(unErrore.getMessage()));
        }
        assertNotNull(clazz2);
        System.out.println(VUOTA);
        System.out.println(String.format("Simple: %s", clazz2.getSimpleName()));
        System.out.println(String.format("Name: %s", clazz2.getName()));
        System.out.println(String.format("Canonical: %s", clazz2.getCanonicalName()));
    }


    @ParameterizedTest
    @MethodSource(value = "CLAZZ_FOR_NAME")
    @Order(10)
    @DisplayName("10 - getClazzFromCanonicalName")
        //--clazz
        //--simpleName
    void getClazzFromCanonicalName(final Class clazzFromStream, String simpleNameNonUsato) {
        System.out.println("10 - getClazzFromCanonicalName");
        System.out.println(VUOTA);

        sorgente = clazzFromStream.getCanonicalName();
        System.out.println(sorgente);
        System.out.println(VUOTA);

        clazz = service.getClazzFromCanonicalName(sorgente);
        assertNotNull(clazz);

        System.out.println(String.format("Simple: %s", clazz.getSimpleName()));
        System.out.println(String.format("Name: %s", clazz.getName()));
        System.out.println(String.format("Canonical: %s", clazz.getCanonicalName()));
    }


    @ParameterizedTest
    @MethodSource(value = "CLAZZ_FOR_NAME")
    @Order(11)
    @DisplayName("11 - getClazzFromSimpleName")
        //--clazz
        //--simpleName
    void getClazzFromSimpleName(final Class clazzNonUsata, final String simpleName) {
        System.out.println("11 - getClazzFromSimpleName");
        System.out.println(VUOTA);

        sorgente = simpleName;
        System.out.println(sorgente);
        System.out.println(VUOTA);

        clazz = service.getClazzFromSimpleName(sorgente);
        assertNotNull(clazz);

        System.out.println(String.format("Simple: %s", clazz.getSimpleName()));
        System.out.println(String.format("Name: %s", clazz.getName()));
        System.out.println(String.format("Canonical: %s", clazz.getCanonicalName()));
    }


    @ParameterizedTest
    @MethodSource(value = "MODULI")
    @Order(20)
    @DisplayName("20 - classi nella directory package del modulo")
        //--moduleName
    void allModulePackagesClass(final String moduleName) {
        System.out.println("20 - classi nella directory package del modulo");
        System.out.println(VUOTA);

        sorgente = moduleName;
        listaClazz = service.allModulePackagesClass(sorgente);

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
    }


    @ParameterizedTest
    @MethodSource(value = "MODULI")
    @Order(21)
    @DisplayName("21 - simpleName nella directory package del modulo")
        //--moduleName
    void allModulePackagesSimpleName(final String moduleName) {
        System.out.println("21 - simpleName nella directory package del modulo");
        System.out.println(VUOTA);

        sorgente = moduleName;
        listaStr = service.allModulePackagesSimpleName(sorgente);

        if (listaStr != null && listaStr.size() > 0) {
            message = String.format("Ci sono in totale %d classi nella directory package del modulo %s", listaStr.size(), sorgente);
            System.out.println(message);
            System.out.println(VUOTA);
            print(listaStr);
        }
        else {
            message = String.format("Non esiste il modulo '%s' oppure non esiste la directory 'package' oppure non ci sono subdirectories", sorgente);
            System.out.println(message);
        }
    }


    @ParameterizedTest
    @MethodSource(value = "MODULI")
    @Order(22)
    @DisplayName("22 - canonicalName nella directory package del modulo")
        //--moduleName
    void allModulePackagesCanonicalName(final String moduleName) {
        System.out.println("22 - canonicalName nella directory package del modulo");
        System.out.println(VUOTA);

        sorgente = moduleName;
        listaStr = service.allModulePackagesCanonicalName(sorgente);

        if (listaStr != null && listaStr.size() > 0) {
            message = String.format("Ci sono in totale %d classi nella directory package del modulo %s", listaStr.size(), sorgente);
            System.out.println(message);
            System.out.println(VUOTA);
            print(listaStr);
        }
        else {
            message = String.format("Non esiste il modulo '%s' oppure non esiste la directory 'package' oppure non ci sono subdirectories", sorgente);
            System.out.println(message);
        }
    }


    @ParameterizedTest
    @MethodSource(value = "MODULI")
    @Order(23)
    @DisplayName("23 - dirName nella directory package del modulo")
        //--moduleName
    void allModulePackagesDirName(final String moduleName) {
        System.out.println("23 - dirName nella directory package del modulo");
        System.out.println(VUOTA);

        sorgente = moduleName;
        listaStr = service.allModulePackagesDirName(sorgente);

        if (listaStr != null && listaStr.size() > 0) {
            message = String.format("Ci sono in totale %d classi nella directory package del modulo %s", listaStr.size(), sorgente);
            System.out.println(message);
            System.out.println(VUOTA);
            print(listaStr);
        }
        else {
            message = String.format("Non esiste il modulo '%s' oppure non esiste la directory 'package' oppure non ci sono subdirectories", sorgente);
            System.out.println(message);
        }
    }


    @ParameterizedTest
    @MethodSource(value = "MODULI")
    @Order(30)
    @DisplayName("30 - classi 'backend' nella directory package del modulo")
        //--moduleName
    void allModuleBackendClass(final String moduleName) {
        System.out.println("30 - classi 'backend' nella directory package del modulo");
        System.out.println(VUOTA);

        sorgente = moduleName;
        listaClazz = service.allModuleBackendClass(sorgente);

        if (listaClazz != null && listaClazz.size() > 0) {
            message = String.format("Ci sono in totale %d classi 'backend' nella directory package del modulo %s", listaClazz.size(), sorgente);
            System.out.println(message);
            System.out.println(VUOTA);
            printClazz(listaClazz);
        }
        else {
            message = String.format("Non esiste il modulo '%s' oppure non esiste la directory 'package' oppure non ci sono subdirectories", sorgente);
            System.out.println(message);
        }
    }


    @ParameterizedTest
    @MethodSource(value = "MODULI")
    @Order(31)
    @DisplayName("31 - simpleName 'backend' nella directory package del modulo")
        //--moduleName
    void allModuleBackendSimpleName(final String moduleName) {
        System.out.println("31 - simpleName 'backend' nella directory package del modulo");
        System.out.println(VUOTA);

        sorgente = moduleName;
        listaStr = service.allModuleBackendSimpleName(sorgente);

        if (listaStr != null && listaStr.size() > 0) {
            message = String.format("Ci sono in totale %d classi 'backend' nella directory package del modulo %s", listaStr.size(), sorgente);
            System.out.println(message);
            System.out.println(VUOTA);
            print(listaStr);
        }
        else {
            message = String.format("Non esiste il modulo '%s' oppure non esiste la directory 'package' oppure non ci sono subdirectories", sorgente);
            System.out.println(message);
        }
    }


    @ParameterizedTest
    @MethodSource(value = "MODULI")
    @Order(32)
    @DisplayName("32 - canonicalName 'backend' nella directory package del modulo")
        //--moduleName
    void allModuleBackendCanonicalName(final String moduleName) {
        System.out.println("32 - canonicalName 'backend' nella directory package del modulo");
        System.out.println(VUOTA);

        sorgente = moduleName;
        listaStr = service.allModuleBackendCanonicalName(sorgente);

        if (listaStr != null && listaStr.size() > 0) {
            message = String.format("Ci sono in totale %d classi 'backend' nella directory package del modulo %s", listaStr.size(), sorgente);
            System.out.println(message);
            System.out.println(VUOTA);
            print(listaStr);
        }
        else {
            message = String.format("Non esiste il modulo '%s' oppure non esiste la directory 'package' oppure non ci sono subdirectories", sorgente);
            System.out.println(message);
        }
    }


    @ParameterizedTest
    @MethodSource(value = "MODULI")
    @Order(33)
    @DisplayName("33 - dirName 'backend' nella directory package del modulo")
        //--moduleName
    void allModuleBackendDirName(final String moduleName) {
        System.out.println("33 - dirName 'backend' nella directory package del modulo");
        System.out.println(VUOTA);

        sorgente = moduleName;
        listaStr = service.allModuleBackendDirName(sorgente);

        if (listaStr != null && listaStr.size() > 0) {
            message = String.format("Ci sono in totale %d classi 'backend' nella directory package del modulo %s", listaStr.size(), sorgente);
            System.out.println(message);
            System.out.println(VUOTA);
            print(listaStr);
        }
        else {
            message = String.format("Non esiste il modulo '%s' oppure non esiste la directory 'package' oppure non ci sono subdirectories", sorgente);
            System.out.println(message);
        }
    }


    @ParameterizedTest
    @MethodSource(value = "MODULI")
    @Order(40)
    @DisplayName("40 - classi 'backend' && 'reset' nella directory package del modulo")
        //--moduleName
    void allModuleBackendResetClass(final String moduleName) {
        System.out.println("40 - classi 'backend' && 'reset' nella directory package del modulo");
        System.out.println(VUOTA);

        sorgente = moduleName;
        listaClazz = service.allModuleBackendResetClass(sorgente);

        if (listaClazz != null && listaClazz.size() > 0) {
            message = String.format("Ci sono in totale %d classi 'backend' && 'reset' nella directory package del modulo %s", listaClazz.size(), sorgente);
            System.out.println(message);
            System.out.println(VUOTA);
            printClazz(listaClazz);
        }
        else {
            message = String.format("Non esiste il modulo '%s' oppure non esiste la directory 'package' oppure non ci sono subdirectories", sorgente);
            System.out.println(message);
        }
    }


    @ParameterizedTest
    @MethodSource(value = "MODULI")
    @Order(41)
    @DisplayName("41 - simpleName 'backend' && 'reset' nella directory package del modulo")
        //--moduleName
    void allModuleBackendResetSimpleName(final String moduleName) {
        System.out.println("41 - simpleName 'backend' && 'reset' nella directory package del modulo");
        System.out.println(VUOTA);

        sorgente = moduleName;
        listaStr = service.allModuleBackendResetSimpleName(sorgente);

        if (listaStr != null && listaStr.size() > 0) {
            message = String.format("Ci sono in totale %d classi 'backend' && 'reset' nella directory package del modulo %s", listaStr.size(), sorgente);
            System.out.println(message);
            System.out.println(VUOTA);
            print(listaStr);
        }
        else {
            message = String.format("Non esiste il modulo '%s' oppure non esiste la directory 'package' oppure non ci sono subdirectories", sorgente);
            System.out.println(message);
        }
    }


    @ParameterizedTest
    @MethodSource(value = "MODULI")
    @Order(42)
    @DisplayName("42 - canonicalName 'backend' && 'reset' nella directory package del modulo")
        //--moduleName
    void allModuleBackendResetCanonicalName(final String moduleName) {
        System.out.println("42 - canonicalName 'backend' && 'reset' nella directory package del modulo");
        System.out.println(VUOTA);

        sorgente = moduleName;
        listaStr = service.allModuleBackendResetCanonicalName(sorgente);

        if (listaStr != null && listaStr.size() > 0) {
            message = String.format("Ci sono in totale %d classi 'backend' && 'reset' nella directory package del modulo %s", listaStr.size(), sorgente);
            System.out.println(message);
            System.out.println(VUOTA);
            print(listaStr);
        }
        else {
            message = String.format("Non esiste il modulo '%s' oppure non esiste la directory 'package' oppure non ci sono subdirectories", sorgente);
            System.out.println(message);
        }
    }


    @ParameterizedTest
    @MethodSource(value = "MODULI")
    @Order(43)
    @DisplayName("43 - dirName 'backend' && 'reset' nella directory package del modulo")
        //--moduleName
    void allModuleBackendResetDirName(final String moduleName) {
        System.out.println("43 - dirName 'backend' && 'reset' nella directory package del modulo");
        System.out.println(VUOTA);

        sorgente = moduleName;
        listaStr = service.allModuleBackendResetDirName(sorgente);

        if (listaStr != null && listaStr.size() > 0) {
            message = String.format("Ci sono in totale %d classi 'backend' && 'reset' nella directory package del modulo %s", listaStr.size(), sorgente);
            System.out.println(message);
            System.out.println(VUOTA);
            print(listaStr);
        }
        else {
            message = String.format("Non esiste il modulo '%s' oppure non esiste la directory 'package' oppure non ci sono subdirectories", sorgente);
            System.out.println(message);
        }
    }


    @ParameterizedTest
    @MethodSource(value = "MODULI")
    @Order(80)
    @DisplayName("80 - classi ordinate 'backend' && 'reset' nella directory package del modulo")
        //--moduleName
    void allModuleBackendResetOrderedClass(final String moduleName) {
        System.out.println("80 - classi ordinate 'backend' && 'reset' nella directory package del modulo");
        System.out.println(VUOTA);

        sorgente = moduleName;
        listaClazz = service.allModuleBackendResetOrderedClass(sorgente);

        if (listaClazz != null && listaClazz.size() > 0) {
            message = String.format("Ci sono in totale %d classi ordinate 'backend' && 'reset' nella directory package del modulo %s", listaClazz.size(), sorgente);
            System.out.println(message);
            System.out.println(VUOTA);
            printClazz(listaClazz);
        }
        else {
            message = String.format("Non esiste il modulo '%s' oppure non esiste la directory 'package' oppure non ci sono subdirectories", sorgente);
            System.out.println(message);
        }
    }

    protected void printPackages(List<String> lista) {
        List<String> listaShort = new ArrayList<>();
        String tag = "packages.";
        String name;

        if (lista != null) {
            for (String stringa : lista) {
                name = textService.levaTestoPrimaDiEscluso(stringa, tag);
                name = textService.pointToSlash(name);
                listaShort.add(name);
            }
            super.print(listaShort);
        }
    }

    protected void printClazz(List<Class> lista) {
        if (lista != null) {
            for (Class clazz : lista) {
                System.out.println(clazz.getSimpleName());
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
