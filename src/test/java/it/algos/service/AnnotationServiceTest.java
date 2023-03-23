package it.algos.service;

import it.algos.*;
import it.algos.base.*;
import it.algos.vaad24.backend.annotation.*;
import static it.algos.vaad24.backend.boot.VaadCost.*;
import it.algos.vaad24.backend.entity.*;
import it.algos.vaad24.backend.packages.utility.log.*;
import it.algos.vaad24.backend.service.*;
import it.algos.vaad24.ui.views.*;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.extension.*;
import org.junit.jupiter.params.*;
import org.junit.jupiter.params.provider.*;
import org.springframework.boot.test.context.*;
import org.springframework.test.context.junit.jupiter.*;

/**
 * Project vaad24
 * Created by Algos
 * User: gac
 * Date: Fri, 03-Mar-2023
 * Time: 19:00
 */
@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = {Vaad24SimpleApp.class})
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Tag("service")
@DisplayName("Annotation Service")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class AnnotationServiceTest extends AlgosTest {

    /**
     * Classe principale di riferimento <br>
     * Gia 'costruita' nella superclasse <br>
     */
    private AnnotationService service;

    private AIEntity aiEntity;

    private AIView aiView;

    private AIField aiField;

    /**
     * Qui passa una volta sola, chiamato dalle sottoclassi <br>
     * Invocare PRIMA il metodo setUpStartUp() della superclasse <br>
     * Si possono aggiungere regolazioni specifiche <br>
     */
    @BeforeAll
    protected void setUpAll() {
        super.setUpAll();

        //--reindirizzo l'istanza della superclasse
        service = annotationService;
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


    @ParameterizedTest
    @MethodSource(value = "CLAZZ_VIEW")
    @Order(1)
    @DisplayName("1 - getAIView")
        //--clazz
        //--usa AIView
    void getAIView(final Class genericClazz, final boolean usaAIView) {
        if (!CrudView.class.isAssignableFrom(genericClazz)) {
            message = String.format("La classe %s non è una classe di tipo CrudView", genericClazz.getSimpleName());
            System.out.println(message);
            return;
        }
        else {
            if (genericClazz.getSimpleName().equals(CrudView.class.getSimpleName())) {
                message = String.format("La superClasse CrudView è astratta", genericClazz.getSimpleName());
                System.out.println(message);
                return;
            }
            else {
                clazz = genericClazz;
            }
        }

        aiView = service.getAIView(clazz);
        if (aiView != null) {
            message = String.format("Esiste l'annotation @aiView per la classe %s", clazz.getSimpleName());
        }
        else {
            message = String.format("Nella classe %s, non esiste l'annotation @aiView", clazz.getSimpleName());
        }
        System.out.println(message);
        assertEquals(usaAIView, aiView != null);
    }

    @ParameterizedTest
    @MethodSource(value = "CLAZZ_FOR_NAME")
    @Order(2)
    @DisplayName("2 - getAIEntity")
        //--clazz
        //--simpleName
    void getAIEntity(final Class genericClazz, final String simpleName) {
        if (!AEntity.class.isAssignableFrom(genericClazz)) {
            message = String.format("La classe %s non è una classe di tipo AEntity", simpleName);
            System.out.println(message);
            return;
        }
        else {
            clazz = genericClazz;
        }

        aiEntity = service.getAIEntity(clazz);
        assertNotNull(aiEntity);
        message = String.format("Esiste l'annotation @AIEntity per la classe %s", clazz.getSimpleName());
        System.out.println(message);
    }

    @Test
    @Order(3)
    @DisplayName("3 - usaAnnotationAIEntity")
    void usaAnnotationAIEntity() {
        System.out.println("3 - Controlla la presenza della annotation @AIEntity in tutte le classi AEntity");
        System.out.println(VUOTA);

        // recupero i nomi di tutte le classi AEntity dei package
        listaClazz = classService.allPackagesEntityClazz();
        sorgente = "AEntity";
        message = String.format("Recupera tutte le classi di tipo %s del progetto corrente. Sono %d", sorgente, listaClazz.size());
        System.out.println(message);
        System.out.println(VUOTA);

        for (Class classe : listaClazz) {
            ottenutoBooleano = annotationService.usaAnnotationAIEntity(classe);
            clazzName = classe.getSimpleName();
            if (ottenutoBooleano) {
                message = String.format("La classe [%s] usa l'annotation @AIEntity", clazzName);
            }
            else {
                message = String.format("La classe [%s] NON usa l'annotation @AIEntity", clazzName);
            }
            System.out.println(message);
        }
    }


    @Test
    @Order(4)
    @DisplayName("4 - usaKeyPropertyName")
    void usaKeyPropertyName() {
        System.out.println("4 - Controlla la presenza della keyPropertyName in tutte le classi AEntity");
        System.out.println(VUOTA);
        String keyPropertyName;

        // recupero i nomi di tutte le classi AEntity dei package
        listaClazz = classService.allPackagesEntityClazz();
        sorgente = "AEntity";
        message = String.format("Recupera tutte le classi di tipo %s del progetto corrente. Sono %d", sorgente, listaClazz.size());
        System.out.println(message);
        System.out.println(VUOTA);

        for (Class classe : listaClazz) {
            ottenutoBooleano = annotationService.usaKeyPropertyName(classe);
            clazzName = classe.getSimpleName();
            if (ottenutoBooleano) {
                keyPropertyName = annotationService.getKeyPropertyName(classe);
                message = String.format("La classe [%s] usa la keyPropertyName che è %s", clazzName, keyPropertyName);
            }
            else {
                message = String.format("La classe [%s] NON usa la keyPropertyName", clazzName);
            }
            System.out.println(message);
        }
    }

    @Test
    @Order(5)
    @DisplayName("5 - usaSearchPropertyName")
    void usaSearchPropertyName() {
        System.out.println("5 - Controlla la presenza della searchPropertyName in tutte le classi AEntity");
        System.out.println("La presenza della searchPropertyName nella classe AEntity NON implica automaticamente che la View la usi");
        System.out.println("Nella CrudView e sottoclassi, esiste il flag usaBottoneSearch");
        System.out.println(VUOTA);
        String searchPropertyName;

        // recupero i nomi di tutte le classi AEntity dei package
        listaClazz = classService.allPackagesEntityClazz();
        sorgente = "AEntity";
        message = String.format("Recupera tutte le classi di tipo %s del progetto corrente. Sono %d", sorgente, listaClazz.size());
        System.out.println(message);
        System.out.println(VUOTA);

        for (Class classe : listaClazz) {
            ottenutoBooleano = annotationService.usaSearchPropertyName(classe);
            clazzName = classe.getSimpleName();
            if (ottenutoBooleano) {
                searchPropertyName = annotationService.getSearchPropertyName(classe);
                message = String.format("La classe [%s] usa la searchPropertyName che è %s", clazzName, searchPropertyName);
            }
            else {
                message = String.format("La classe [%s] NON usa la searchPropertyName", clazzName);
            }
            System.out.println(message);
        }
    }

    @Test
    @Order(51)
    @DisplayName("51 - isTransient")
    void getLabelHost() {
        clazz = Logger.class;

        sorgente = "linea";
        ottenutoBooleano = service.isTransient(clazz, sorgente);
        assertFalse(ottenutoBooleano);
        message = String.format("Nella classe %s, il field %s NON è @Transient", clazz.getSimpleName(), sorgente);
        System.out.println(message);

        sorgente = "giorno";
        ottenutoBooleano = service.isTransient(clazz, sorgente);
        assertTrue(ottenutoBooleano);
        message = String.format("Nella classe %s, il field %s è @Transient", clazz.getSimpleName(), sorgente);
        System.out.println(message);

        sorgente = "ora";
        ottenutoBooleano = service.isTransient(clazz, sorgente);
        assertTrue(ottenutoBooleano);
        message = String.format("Nella classe %s, il field %s è @Transient", clazz.getSimpleName(), sorgente);
        System.out.println(message);
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
