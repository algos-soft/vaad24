package it.algos.simpleOnly;

import it.algos.*;
import it.algos.base.*;
import static it.algos.vaad24.backend.boot.VaadCost.*;
import it.algos.vaad24.backend.boot.*;
import it.algos.vaad24.backend.enumeration.*;
import it.algos.vaad24.backend.interfaces.*;
import it.algos.vaad24.backend.service.*;
import it.algos.vaad24simple.backend.enumeration.*;
import static org.junit.Assert.*;
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
public class PreferenceServiceTest extends AlgosTest {

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

        assertNotNull(matricePref);
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
    public void getAllEnums2() {
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

    //    @Test
    //    @Order(30)
    //    @DisplayName("30 - Test enumeration")
    //    public void enumeration() {
    //        sorgente = "AEKeyFile.esistente";
    //        Object alfa=   AEKeyFile.esistente;
    //        Object istanza = appContext.getBean(sorgente);
    //        Assert.assertNotNull(istanza);
    //    }


    @Test
    @Order(51)
    @DisplayName("51 - Preferenze dei vari type")
    public void getString() {
        System.out.println("51 - Preferenze dei vari type");
        System.out.println(VUOTA);
        List<AETypePref> listaAll;

        listaPref = service.findAll();
        assertNotNull(listaPref);
        ottenutoIntero = listaPref.size();
        assertTrue(ottenutoIntero > 0);
        message = String.format("Ci sono in totale %d preferenze", ottenutoIntero);
        System.out.println(message);

        listaAll = AETypePref.getAllEnums();
        for (AETypePref type : listaAll) {
            listaPref = service.findAllByType(type);
            assertNotNull(listaPref);
            ottenutoIntero = listaPref.size();
            message = String.format("Di cui %d di type %s", ottenutoIntero, type.name());
            System.out.println(message);
        }
    }

    @Test
    @Order(52)
    @DisplayName("52 - Valori default per type")
    public void is() {
        System.out.println("52 - Valori default per type");
        List<AETypePref> listaAll;

        listaAll = AETypePref.getAllEnums();
        for (AETypePref type : listaAll) {
            listaPref = service.findAllByType(type);
            assertNotNull(listaPref);
            ottenutoIntero = listaPref.size();
            System.out.println(VUOTA);
            message = String.format("Le preferenze di type %s sono", type.name(), ottenutoIntero);
            System.out.println(message);
            for (AIGenPref pref : listaPref) {
                System.out.print(pref.getKeyCode());
                System.out.print(SPAZIO);
                System.out.print(PARENTESI_TONDA_INI);
                System.out.print(pref.getType().name());
                System.out.print(PARENTESI_TONDA_END);
                System.out.print(FORWARD);
                message = switch (type) {
                    case string:
                        yield String.format("(%s) %s", "getStr", pref.getStr());
                    case bool:
                        yield String.format("(%s) %s", "is" , pref.is());
                    case integer:
                        yield String.format("(%s) %s", "getInt" , pref.getInt());
                    case lungo:
                        yield String.format("(%s) %s", "getDefaultValue" , pref.getDefaultValue());
                    case decimal:
                        yield String.format("(%s) %s", "getDecimal" , pref.getDecimal());
                    case localdatetime:
                        yield String.format("(%s) %s", "getDefaultValue" , pref.getDefaultValue());
                    case localdate:
                        yield String.format("(%s) %s", "getDefaultValue" , pref.getDefaultValue());
                    case localtime:
                        yield String.format("(%s) %s", "getDefaultValue" , pref.getDefaultValue());
                    case email:
                        yield String.format("(%s) %s", "getDefaultValue" , pref.getDefaultValue());
                    case enumerationString:
                        yield String.format("(%s) %s", "getDefaultValue" , pref.getDefaultValue());
                    case enumerationType:
                        yield String.format("(%s) %s", "getDefaultValue" , pref.getDefaultValue());
                    case icona:
                        yield String.format("(%s) %s", "getDefaultValue" , pref.getDefaultValue());
                    case image:
                        yield String.format("(%s) %s", "getDefaultValue" , pref.getDefaultValue());
                    default:
                        yield "Questo non c'è";
                };
                System.out.println(message);
            }
        }

        //        listaPref = service.findAllByType(AETypePref.bool);
        //        assertNotNull(listaPref);
        //        ottenutoIntero = listaPref.size();
        //        assertTrue(ottenutoIntero > 0);
        //        message = String.format("Di cui %d di type boolean", ottenutoIntero);
        //        System.out.println(message);

        //        System.out.println(VUOTA);
        //        System.out.println("Valori (Object) di default:");
        //        for (AIGenPref pref : listaPref) {
        //            System.out.print(pref.getKeyCode());
        //            System.out.print(FORWARD);
        //            System.out.println(pref.getDefaultValue());
        //        }
        //
        //        System.out.println(VUOTA);
        //        System.out.println("Valori (Object) correnti:");
        //        for (AIGenPref pref : listaPref) {
        //            System.out.print(pref.getKeyCode());
        //            System.out.print(FORWARD);
        //            System.out.println(pref.getValue());
        //        }
        //
        //        System.out.println(VUOTA);
        //        System.out.println("Valori (boolean) di default:");
        //        for (AIGenPref pref : listaPref) {
        //            System.out.print(pref.getKeyCode());
        //            System.out.print(FORWARD);
        //            System.out.println(pref.is());
        //        }

    }
    //
    //
    //    @Test
    //    @Order(53)
    //    @DisplayName("53 - Preferenze type int")
    //    public void getInt() {
    //        System.out.println("53 - Preferenze type int");
    //        System.out.println(VUOTA);
    //
    //        listaPref = service.findAll();
    //        assertNotNull(listaPref);
    //        ottenutoIntero = listaPref.size();
    //        assertTrue(ottenutoIntero > 0);
    //        message = String.format("Ci sono in totale %d preferenze", ottenutoIntero);
    //        System.out.println(message);
    //
    //        listaPref = service.findAllByType(AETypePref.integer);
    //        assertNotNull(listaPref);
    //        ottenutoIntero = listaPref.size();
    //        assertTrue(ottenutoIntero > 0);
    //        message = String.format("Di cui %d di type Integer", ottenutoIntero);
    //        System.out.println(message);
    //
    //        System.out.println(VUOTA);
    //        System.out.println("Valori (Object) di default:");
    //        for (AIGenPref pref : listaPref) {
    //            System.out.print(pref.getKeyCode());
    //            System.out.print(FORWARD);
    //            System.out.println(pref.getDefaultValue());
    //        }
    //
    //        System.out.println(VUOTA);
    //        System.out.println("Valori (Object) correnti:");
    //        for (AIGenPref pref : listaPref) {
    //            System.out.print(pref.getKeyCode());
    //            System.out.print(FORWARD);
    //            System.out.println(pref.getValue());
    //        }
    //
    //        System.out.println(VUOTA);
    //        System.out.println("Valori (int) di default:");
    //        for (AIGenPref pref : listaPref) {
    //            System.out.print(pref.getKeyCode());
    //            System.out.print(FORWARD);
    //            System.out.println(pref.getInt());
    //        }
    //    }

    public void printPrefA(List lista) {
        assertNotNull(lista);
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
