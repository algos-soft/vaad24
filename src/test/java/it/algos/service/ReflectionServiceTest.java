package it.algos.service;

import it.algos.*;
import it.algos.base.*;
import static it.algos.vaad24.backend.boot.VaadCost.*;
import it.algos.vaad24.backend.entity.*;
import it.algos.vaad24.backend.interfaces.*;
import it.algos.vaad24.backend.packages.anagrafica.*;
import it.algos.vaad24.backend.packages.crono.giorno.*;
import it.algos.vaad24.backend.packages.crono.mese.*;
import it.algos.vaad24.backend.packages.crono.secolo.*;
import it.algos.vaad24.backend.packages.geografia.continente.*;
import it.algos.vaad24.backend.packages.utility.logger.*;
import it.algos.vaad24.backend.service.*;
import it.algos.vaad24.ui.views.*;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.extension.*;
import org.junit.jupiter.params.*;
import org.junit.jupiter.params.provider.*;
import org.springframework.boot.test.context.*;
import org.springframework.test.context.junit.jupiter.*;

import java.lang.reflect.*;
import java.util.*;
import java.util.stream.*;

/**
 * Project vaad24
 * Created by Algos
 * User: gac
 * Date: Tue, 20-Dec-2022
 * Time: 20:49
 */
@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = {Vaad24SimpleApp.class})
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Tag("service")
@DisplayName("Reflection Service")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ReflectionServiceTest extends AlgosTest {

    /**
     * Classe principale di riferimento <br>
     * Gia 'iniettata' nella superclasse <br>
     */
    private ReflectionService service;

    //--clazz
    //--simpleName
    //--numero fields classe + superClassi
    //--numero only fields della classe
    //--numero only fields della classe accettabili nel database
    //--numero only fields della classe senza keyID
    protected static Stream<Arguments> CLAZZ_FOR_FIELD() {
        return Stream.of(
                Arguments.of(CrudView.class, CrudView.class.getSimpleName(), 0, 0, 0, 0),
                Arguments.of(AIType.class, AIType.class.getSimpleName(), 0, 0, 0, 0),
                Arguments.of(Mese.class, Mese.class.getSimpleName(), 10, 7, 7, 6),
                Arguments.of(Continente.class, Continente.class.getSimpleName(), 7, 4, 4, 3),
                Arguments.of(Giorno.class, Giorno.class.getSimpleName(), 9, 6, 6, 5),
                Arguments.of(ALogger.class, ALogger.class.getSimpleName(), 16, 13, 11, 10),
                Arguments.of(Via.class, Via.class.getSimpleName(), 5, 2, 2, 1),
                Arguments.of(ViaView.class, ViaView.class.getSimpleName(), 0, 0, 0, 0),
                Arguments.of(SecoloView.class, SecoloView.class.getSimpleName(), 0, 0, 0, 0)
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
        service = reflectionService;
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
    @DisplayName("1 - field semplice a livello entity base")
    void getField() {
        System.out.println("1 - field semplice a livello entity base");
        System.out.println(VUOTA);

        clazz = Secolo.class;
        sorgente = FIELD_NAME_NOME;
        ottenutoField = service.getField(clazz, sorgente);
        assertNotNull(ottenutoField);
        System.out.println(ottenutoField.getName());
    }

    @Test
    @Order(2)
    @DisplayName("2 - field a livello entity superiore")
    void getLabelHost() {
        System.out.println("2 - field a livello entity superiore");
        System.out.println(VUOTA);

        clazz = Secolo.class;
        sorgente = FIELD_NAME_NOTE;
        ottenutoField = service.getField(clazz, sorgente);
        //        assertNull(ottenutoField);

        try {
            ottenutoField = clazz.getField(sorgente);
            System.out.println(ottenutoField.getName());
        } catch (Exception unErrore) {
        }
    }

    @Test
    @Order(21)
    @DisplayName("21 - getMetodi")
    void getMetodi() {
        System.out.println("21 - getMetodi");
        List<Method> metodi;

        clazz = Secolo.class;
        metodi = service.getMetodi(clazz);
        assertNotNull(metodi);
        printMethods(metodi);

        clazz = SecoloBackend.class;
        metodi = service.getMetodi(clazz);
        assertNotNull(metodi);
        printMethods(metodi);
    }


    @Test
    @Order(22)
    @DisplayName("22 - getMetodiName")
    void getMetodiName() {
        System.out.println("22 - getMetodiName");
        System.out.println(VUOTA);

        clazz = SecoloBackend.class;
        listaStr = service.getMetodiName(clazz);
        assertNotNull(listaStr);
        message = String.format("Metodi della classe %s:", clazz.getSimpleName());
        System.out.println(message);
        print(listaStr);
    }

    @Test
    @Order(23)
    @DisplayName("23 - getMetodo")
    void getMetodo() {
        System.out.println("23 - getMetodo");
        System.out.println(VUOTA);
        Method method;

        clazz = Secolo.class;
        sorgente = METHOD_NAME_RESET_ONLY;
        method = service.getMetodo(clazz, sorgente);
        assertNull(method);

        clazz = SecoloBackend.class;
        sorgente = METHOD_NAME_RESET_ONLY;
        method = service.getMetodo(clazz, sorgente);
        assertNotNull(method);
    }

    @Test
    @Order(24)
    @DisplayName("24 - isEsisteMetodo")
    void isEsisteMetodo() {
        System.out.println("24 - isEsisteMetodo");
        System.out.println(VUOTA);

        clazz = Secolo.class;
        sorgente = METHOD_NAME_RESET_ONLY;
        sorgente2 = clazz.getSimpleName();
        sorgente3 = clazz.getCanonicalName();
        ottenutoBooleano = service.isEsisteMetodo(clazz, sorgente);
        assertFalse(ottenutoBooleano);
        ottenutoBooleano = service.isEsisteMetodo(sorgente2, sorgente);
        assertFalse(ottenutoBooleano);
        ottenutoBooleano = service.isEsisteMetodo(sorgente3, sorgente);
        assertFalse(ottenutoBooleano);

        clazz = SecoloBackend.class;
        sorgente = METHOD_NAME_RESET_ONLY;
        sorgente2 = clazz.getSimpleName();
        sorgente3 = clazz.getCanonicalName();
        ottenutoBooleano = service.isEsisteMetodo(clazz, sorgente);
        assertTrue(ottenutoBooleano);
        ottenutoBooleano = service.isEsisteMetodo(sorgente2, sorgente);
        assertTrue(ottenutoBooleano);
        ottenutoBooleano = service.isEsisteMetodo(sorgente3, sorgente);
        assertTrue(ottenutoBooleano);
    }


    @Test
    @Order(25)
    @DisplayName("25 - isEsisteMetodoConParametri")
    void isEsisteMetodoConParametri() {
        System.out.println("25 - isEsisteMetodoConParametri");
        System.out.println(VUOTA);

        clazz = Secolo.class;
        sorgente = METHOD_NAME_RESET_ONLY;
        ottenutoBooleano = service.isEsisteMetodoConParametri(clazz, sorgente, 0);
        assertFalse(ottenutoBooleano);
        ottenutoBooleano = service.isEsisteMetodoConParametri(clazz, sorgente, 1);
        assertFalse(ottenutoBooleano);

        clazz = SecoloBackend.class;
        sorgente = METHOD_NAME_NEW_ENTITY;
        ottenutoBooleano = service.isEsisteMetodoConParametri(clazz, sorgente, 5);
        assertTrue(ottenutoBooleano);
        ottenutoBooleano = service.isEsisteMetodoConParametri(clazz, sorgente, 1);
        assertTrue(ottenutoBooleano);
        ottenutoBooleano = service.isEsisteMetodoConParametri(clazz, sorgente, 3);
        assertFalse(ottenutoBooleano);
    }


    @Test
    @Order(26)
    @DisplayName("26 - isEsisteMetodoConParametri")
    void isEsisteMetodoConParametri2() {
        System.out.println("26 - isEsisteMetodoConParametri");
        System.out.println(VUOTA);

        clazz = ViaBackend.class;
        sorgente = METHOD_NAME_NEW_ENTITY;

        ottenutoBooleano = service.isEsisteMetodoConParametri(clazz, sorgente);
        assertTrue(ottenutoBooleano);

    }

    @Test
    @Order(27)
    @DisplayName("27 - isEsisteMetodoSenzaParametri")
    void isEsisteMetodoSenzaParametri() {
        System.out.println("27 - isEsisteMetodoSenzaParametri");
        System.out.println(VUOTA);

        clazz = ViaBackend.class;
        sorgente = METHOD_NAME_NEW_ENTITY;

        ottenutoBooleano = service.isEsisteMetodoSenzaParametri(clazz, sorgente);
        assertFalse(ottenutoBooleano);
    }

    @ParameterizedTest
    @MethodSource(value = "CLAZZ_FOR_FIELD")
    @Order(41)
    @DisplayName("41 - getAllSuperClassFields di una classe AEntity")
        //--clazz
        //--simpleName
        //--numero fields classe + superClassi
        //--numero only fields della classe
        //--numero only fields della classe accettabili nel database
        //--numero only fields della classe senza keyID
    void getAllSuperClassFields(Class genericClazz, String simpleName, int numSuperClassi) {
        System.out.println("41 - getAllSuperClassFields di una classe AEntity");
        System.out.println(VUOTA);

        if (!AEntity.class.isAssignableFrom(genericClazz)) {
            message = String.format("La classe %s non è una classe di tipo AEntity", simpleName);
            System.out.println(message);
            return;
        }
        else {
            clazz = genericClazz;
        }

        listaFields = service.getAllSuperClassFields(clazz);
        assertNotNull(listaFields);
        ottenutoIntero = listaFields.size();
        assertEquals(numSuperClassi, ottenutoIntero);

        message = String.format("Tutti i fields della classe e di tutte le superClassi; compreso _ID e transient");
        System.out.println(message);
        printFields(clazz, listaFields);
    }

    @ParameterizedTest
    @MethodSource(value = "CLAZZ_FOR_FIELD")
    @Order(42)
    @DisplayName("42 - getClassOnlyDeclaredFields di una classe AEntity")
        //--clazz
        //--simpleName
        //--numero fields classe + superClassi
        //--numero only fields della classe
        //--numero only fields della classe accettabili nel database
        //--numero only fields della classe senza keyID
    void getClassOnlyDeclaredFields(Class genericClazz, String simpleName, int nonUsato, int numOnlyClasse) {
        System.out.println("42 - getClassOnlyDeclaredFields di una classe AEntity");
        System.out.println(VUOTA);

        if (!AEntity.class.isAssignableFrom(genericClazz)) {
            message = String.format("La classe %s non è una classe di tipo AEntity", simpleName);
            System.out.println(message);
            return;
        }
        else {
            clazz = genericClazz;
        }

        listaFields = service.getClassOnlyDeclaredFields(clazz);
        assertNotNull(listaFields);
        ottenutoIntero = listaFields.size();
        assertEquals(numOnlyClasse, ottenutoIntero);

        message = String.format("Tutti i fields della classe; compreso _ID e transient, ma esclusi i fields della superclasse");
        System.out.println(message);
        printFields(clazz, listaFields);
    }

    @ParameterizedTest
    @MethodSource(value = "CLAZZ_FOR_FIELD")
    @Order(43)
    @DisplayName("43 - getClassOnlyDeclaredFieldsDB di una classe AEntity")
        //--clazz
        //--simpleName
        //--numero fields classe + superClassi
        //--numero only fields della classe
        //--numero only fields della classe accettabili nel database
        //--numero only fields della classe senza keyID
    void getClassOnlyDeclaredFieldsDB(Class genericClazz, String simpleName, int nonUsato, int nonUsato2, int numOnlyClasseDatabase) {
        System.out.println("43 - getClassOnlyDeclaredFieldsDB di una classe AEntity");
        System.out.println(VUOTA);

        if (!AEntity.class.isAssignableFrom(genericClazz)) {
            message = String.format("La classe %s non è una classe di tipo AEntity", simpleName);
            System.out.println(message);
            return;
        }
        else {
            clazz = genericClazz;
        }

        listaFields = service.getClassOnlyDeclaredFieldsDB(clazz);
        assertNotNull(listaFields);
        ottenutoIntero = listaFields.size();
        assertEquals(numOnlyClasseDatabase, ottenutoIntero);

        message = String.format("Tutti i fields della classe; compreso _ID, ma esclusi transient e i fields della superclasse");
        System.out.println(message);
        printFields(clazz, listaFields);
    }


    @ParameterizedTest
    @MethodSource(value = "CLAZZ_FOR_FIELD")
    @Order(44)
    @DisplayName("44 - getClassOnlyFormFields di una classe AEntity")
        //--clazz
        //--simpleName
        //--numero fields classe + superClassi
        //--numero only fields della classe
        //--numero only fields della classe accettabili nel database
        //--numero only fields della classe senza keyID
    void getClassOnlyFormFields(Class genericClazz, String simpleName, int nonUsato, int nonUsato2, int nonUsato3, int numOnlyForm) {
        System.out.println("44 - getClassOnlyFormFields di una classe AEntity");
        System.out.println(VUOTA);

        if (!AEntity.class.isAssignableFrom(genericClazz)) {
            message = String.format("La classe %s non è una classe di tipo AEntity", simpleName);
            System.out.println(message);
            return;
        }
        else {
            clazz = genericClazz;
        }

        listaFields = service.getClassOnlyFormFields(clazz);
        assertNotNull(listaFields);
        ottenutoIntero = listaFields.size();
        assertEquals(numOnlyForm, ottenutoIntero);

        message = String.format("Tutti i fields della classe; escluso _ID, esclusi transient ed esclusi i fields della superclasse");
        System.out.println(message);
        printFields(clazz, listaFields);
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

    protected void printMethods(List<Method> lista) {
        String clazz;
        String name;
        int num;
        String message = VUOTA;
        Class[] types;

        System.out.println(VUOTA);
        System.out.println(String.format("Clazz%sMethod%sParams", SEP, SEP));
        System.out.println(VUOTA);

        for (Method method : lista) {
            clazz = method.getDeclaringClass().getSimpleName();
            name = method.getName();
            num = method.getParameterCount();
            if (num > 0) {
                types = method.getParameterTypes();
                message = String.format("%s%s%s%s%d%s%s", clazz, SEP, name, SEP, num, SEP, getTypes(types));
            }
            else {
                message = String.format("%s%s%s%s%d", clazz, SEP, name, SEP, num);
            }
            System.out.println(message);
        }
    }


    protected List<String> getTypes(Class[] types) {
        List<String> lista = new ArrayList<>();

        for (Class clazz : types) {
            lista.add(clazz.getSimpleName());
        }

        return lista;
    }

    protected void printFields(Class clazz, List<Field> lista) {
        String clazzName = clazz.getSimpleName();
        String fieldName;
        String fieldClass;
        int k = 1;

        message = String.format("Nella classe %s ci sono %d fields", clazzName, lista.size());
        System.out.println(message);
        System.out.println(VUOTA);

        for (Field field : lista) {
            fieldName = field.getName();
            fieldClass = field.getType().getSimpleName();
            message = String.format("%s%s%s", fieldName, FORWARD, fieldClass);
            System.out.print(k++);
            System.out.print(PARENTESI_TONDA_END);
            System.out.print(SPAZIO);
            System.out.println(message);
        }
    }

}
