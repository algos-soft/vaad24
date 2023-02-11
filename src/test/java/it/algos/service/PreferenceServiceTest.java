package it.algos.service;

import it.algos.*;
import it.algos.base.*;
import it.algos.vaad24.backend.boot.*;
import static it.algos.vaad24.backend.boot.VaadCost.*;
import it.algos.vaad24.backend.enumeration.*;
import it.algos.vaad24.backend.interfaces.*;
import it.algos.vaad24.backend.service.*;
import it.algos.vaad24simple.backend.enumeration.*;
import org.junit.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.*;
import org.springframework.boot.test.context.*;
import org.springframework.test.context.junit.jupiter.*;

import java.util.*;

/**
 * Project vaad24
 * Created by Algos
 * User: gac
 * Date: Sat, 11-Feb-2023
 * Time: 07:43
 */
@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = {Vaad24SimpleApp.class})
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Tag("service")
@DisplayName("Preference Service")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class PreferenceServiceTest extends AlgosIntegrationTest {

    /**
     * Classe principale di riferimento <br>
     * Gia 'costruita' nella superclasse <br>
     */
    private PreferenceService service;

    private List<Pref> listaPrefVaad24;

    private List<SPref> listaPrefSimple;

    private List<AIGenPref> listaPref;

    private AIGenPref[] matricePref;

    /**
     * Qui passa una volta sola, chiamato dalle sottoclassi <br>
     * Invocare PRIMA il metodo setUpStartUp() della superclasse <br>
     * Si possono aggiungere regolazioni specifiche <br>
     */
    @BeforeAll
    protected void setUpAll() {
        super.setUpAll();

        //--reindirizzo l'istanza della superclasse
        service = preferenceService;
    }


    /**
     * Qui passa a ogni test delle sottoclassi <br>
     * Invocare PRIMA il metodo setUp() della superclasse <br>
     * Si possono aggiungere regolazioni specifiche <br>
     */
    @BeforeEach
    protected void setUpEach() {
        super.setUpEach();

        listaPrefVaad24 = null;
        listaPrefSimple = null;
        listaPref = null;
        matricePref = null;
    }


    @Test
    @Order(1)
    @DisplayName("1 - Pref di Vaad24 (matrice)")
    public void values() {
        System.out.println("1 - Pref di Vaad24 (matrice)");
        System.out.println(VUOTA);

        matricePref = Pref.values();

        Assert.assertNotNull(matricePref);
        for (AIGenPref pref : matricePref) {
            System.out.println(pref.getKeyCode());
        }
    }

    @Test
    @Order(2)
    @DisplayName("2 - Pref di Vaad24 (lista generica getAll)")
    public void getAll() {
        System.out.println("2 - Pref di Vaad24 (lista generica getAll)");
        System.out.println(VUOTA);

        listaPref = Pref.getAll();
        printPrefA(listaPref);
    }

    @Test
    @Order(3)
    @DisplayName("3 - Pref di Vaad24 (lista specifica getAllEnums)")
    public void getAllEnums() {
        System.out.println("3 - Pref di Vaad24 (lista specifica getAllEnums)");
        System.out.println(VUOTA);

        listaPrefVaad24 = Pref.getAllEnums();
        printPrefA(listaPrefVaad24);
    }

    @Test
    @Order(4)
    @DisplayName("4 - Pref di Simple (lista generica getAll)")
    public void getAll2() {
        System.out.println("4 - Pref di Simple (lista generica getAll)");
        System.out.println(VUOTA);

        listaPref = SPref.getAll();
        printPrefA(listaPref);
    }


    @Test
    @Order(5)
    @DisplayName("5 - Pref di Simple (lista specifica getAllEnums)")
    public void getAlddlEnums2() {
        System.out.println("5 - Pref di Simple (lista specifica getAllEnums)");
        System.out.println(VUOTA);

        listaPrefSimple = SPref.getAllEnums();
        printPrefA(listaPrefSimple);
    }

    @Test
    @Order(6)
    @DisplayName("6 - Pref di Vaadin24 + Simple (lista generica da variabile)")
    public void prefList() {
        System.out.println("6 - Pref di Vaadin24 + Simple (lista generica da variabile)");
        System.out.println(VUOTA);

        listaPref = VaadVar.prefList;
        printPrefA(listaPref);
    }


    @Test
    @Order(7)
    @DisplayName("7 - Valori di default di tutte le preferenze")
    public void prefList2() {
        System.out.println("7 - Valori di default di tutte le preferenze");
        System.out.println(VUOTA);

        listaPref = VaadVar.prefList;
        printPrefB(listaPref);
    }

    @Test
    @Order(8)
    @DisplayName("8 - Valori correnti di tutte le preferenze")
    public void prefList4() {
        System.out.println("8 - Valori correnti di tutte le preferenze");
        System.out.println(VUOTA);

        listaPref = VaadVar.prefList;
        printPrefC(listaPref);
    }


    @Test
    @Order(20)
    @DisplayName("20 - Valore default di Pref")
    public void aEPreferenza() {
        previsto = "gac@algos.it";
        sorgente = previsto;

        ottenuto = (String) Pref.nonBreaking.getDefaultValue();
        System.out.println(ottenuto);
        //        Assert.assertNotNull(ottenuto);
        //        Assert.assertEquals(previsto, ottenuto);
    }

    public void printPrefA(List lista) {
        Assert.assertNotNull(lista);
        if (lista.size() > 0) {
            message = String.format("Ci sono %s preferenze", lista.size());
            System.out.println(message);
            System.out.println(VUOTA);

            for (Object obj : lista) {
                if (obj instanceof AIGenPref pref) {
                    System.out.println(pref.getKeyCode());
                }
            }
        }
    }

    public void printPrefB(List lista) {
        if (lista != null && lista.size() > 0) {
            System.out.print("Code");
            System.out.print(SEP);
            System.out.print("Type");
            System.out.print(SEP);
            System.out.println("Default");
            System.out.println(VUOTA);

            for (Object obj : lista) {
                if (obj instanceof AIGenPref pref) {
                    System.out.print(pref.getKeyCode());
                    System.out.print(DUE_PUNTI_SPAZIO);
                    System.out.print(pref.getType());
                    System.out.print(FORWARD);
                    System.out.println(pref.getDefaultValue());
                }
            }
        }
    }


    public void printPrefC(List lista) {
        if (lista != null && lista.size() > 0) {
            System.out.print("Code");
            System.out.print(SEP);
            System.out.print("Type");
            System.out.print(SEP);
            System.out.print("Default");
            System.out.print(SEP);
            System.out.println("Current");
            System.out.println(VUOTA);

            for (Object obj : lista) {
                if (obj instanceof AIGenPref pref) {
                    System.out.print(pref.getKeyCode());
                    System.out.print(DUE_PUNTI_SPAZIO);
                    System.out.print(pref.getType());
                    System.out.print(FORWARD);
                    System.out.print(pref.getDefaultValue());
                    System.out.print(FORWARD);
                    System.out.println(pref.get());
                }
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
