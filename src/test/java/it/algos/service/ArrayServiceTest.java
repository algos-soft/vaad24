package it.algos.service;

import static it.algos.vaad24.backend.boot.VaadCost.*;
import it.algos.vaad24.backend.service.*;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.*;
import org.mockito.*;

import java.util.*;

/**
 * Project vaad24
 * Created by Algos
 * User: gac
 * Date: Mon, 13-Feb-2023
 * Time: 14:37
 */
//@ExtendWith(SpringExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Tag("quickly")
@Tag("service")
@DisplayName("Array Service")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ArrayServiceTest {

    /**
     * Classe principale di riferimento <br>
     * Gia 'costruita' nella superclasse <br>
     */
    @InjectMocks
    protected ArrayService service;

    @InjectMocks
    protected TextService textService;

    @InjectMocks
    protected DateService dateService;

    private long inizio;

    @BeforeAll
    protected void setUpAll() {
        this.initMocks();
        this.checkMocks();
        this.crossReferences();
    }

    protected void initMocks() {
        //        super.initMocks();
        MockitoAnnotations.openMocks(this);
        MockitoAnnotations.openMocks(service);
        MockitoAnnotations.openMocks(textService);
        MockitoAnnotations.openMocks(dateService);
    }

    protected void checkMocks() {
        assertNotNull(service);
        assertNotNull(textService);
        assertNotNull(dateService);
    }

    protected void crossReferences() {
        dateService.textService = textService;
    }

    /**
     * Qui passa a ogni test delle sottoclassi <br>
     * Invocare PRIMA il metodo setUp() della superclasse <br>
     * Si possono aggiungere regolazioni specifiche <br>
     */
    @BeforeEach
    protected void setUpEach() {
        this.inizio = System.currentTimeMillis();
    }


    //    @Test
    @Order(1)
    @DisplayName("Primo test")
    void getLabelHost() {
        //        sorgente = "alfa.beta.gamma";
        //        ottenuto = textService.levaPunti(sorgente);
        //        assertTrue(textService.isValid(ottenuto));
        //        System.out.println(ottenuto);
    }


    //    @Test
    @Order(2)
    @DisplayName("2 - ordina una mappa secondo le chiavi")
    void sort() {
        System.out.println("2 - Ordina secondo le chiavi una mappa che è stata costruita NON ordinata");
        System.out.println(VUOTA);

        //        mappaSorgente = new HashMap<>();
        //        mappaSorgente.put("beta", "adesso");
        //        mappaSorgente.put("delta", "bufala");
        //        mappaSorgente.put("alfa", "comodino");
        //        mappaSorgente.put("coraggio", "domodossola");
        //        mappaOttenuta = service.sort(mappaSorgente);
        //        assertNotNull(mappaOttenuta);
        //        assertTrue(mappaOttenuta.size() == 4);
        //        printMappa(mappaOttenuta);
    }

    //    @Test
    @Order(3)
    @DisplayName("3 - ordina una mappa secondo i valori")
    void sortValue() {
        System.out.println("3 - Ordina secondo i valori una mappa che è stata costruita NON ordinata");
        System.out.println(VUOTA);

        //        mappaSorgente = new HashMap<>();
        //        mappaSorgente.put("beta", "adesso");
        //        mappaSorgente.put("delta", "bufala");
        //        mappaSorgente.put("alfa", "comodino");
        //        mappaSorgente.put("coraggio", "domodossola");
        //        mappaOttenuta = service.sortValue(mappaSorgente);
        //        assertNotNull(mappaOttenuta);
        //        assertTrue(mappaOttenuta.size() == 4);
        //        printMappa(mappaOttenuta);
    }

    //    @Test
    @Order(4)
    @DisplayName("4 - ordina una mappa secondo i valori (multipli)")
    void sortValue2() {
        System.out.println("4 - Ordina secondo i valori (multipli) una mappa che è stata costruita NON ordinata");
        System.out.println(VUOTA);

        //        mappaSorgente = new HashMap<>();
        //        mappaSorgente.put("beta", "adesso");
        //        mappaSorgente.put("delta", "bufala");
        //        mappaSorgente.put("alfa", "bufala");
        //        mappaSorgente.put("coraggio", "domodossola");
        //        mappaOttenuta = service.sortValue(mappaSorgente);
        //        assertNotNull(mappaOttenuta);
        //        assertTrue(mappaOttenuta.size() == 4);
        //        printMappa(mappaOttenuta);
    }

    @Test
    @Order(5)
    @DisplayName("5 - deltaLongStream")
    void deltaLongStream() {
        System.out.println("5 - Differenza di due liste di 'long' tramite 'stream'");
        System.out.println(VUOTA);

        List<Long> listaMaggiore = Arrays.asList(3L, 4L, 5L, 6L, 7L);
        List<Long> listaMinore = Arrays.asList(5L, 6L, 7L, 8L);
        List<Long> listaDifferenza;

        listaDifferenza = service.deltaLongStream(listaMaggiore, listaMinore);
        System.out.println(String.format("Ci sono %s long nella prima lista", listaMaggiore.size()));
        System.out.println(String.format("Prima lista %s", listaMaggiore));
        System.out.println(String.format("Ci sono %s long nella seconda lista", listaMinore.size()));
        System.out.println(String.format("Seconda lista %s", listaMinore));
        System.out.println(VUOTA);
        System.out.println(String.format("Ci sono %s long nella lista risultante", listaDifferenza.size()));
        System.out.println(String.format("Differenza -> %s", listaDifferenza));
        System.out.println(String.format("Eseguito in %s", dateService.deltaTextEsatto(inizio)));

    }

    @Test
    @Order(6)
    @DisplayName("6 - deltaBinary")
    void deltaBinary() {
        System.out.println("6 - Differenza di due liste brevi di 'oggetti' tramite 'deltaBinary'");
        System.out.println(VUOTA);

        List<Long> listaMaggiore = Arrays.asList(3L, 4L, 5L, 6L, 7L);
        List<Long> listaMinore = Arrays.asList(5L, 6L, 7L, 8L);
        List<Long> listaDifferenza;

        listaDifferenza = service.deltaBinary(listaMaggiore, listaMinore);
        System.out.println(String.format("Ci sono %s long nella prima lista", listaMaggiore.size()));
        System.out.println(String.format("Prima lista %s", listaMaggiore));
        System.out.println(String.format("Ci sono %s long nella seconda lista", listaMinore.size()));
        System.out.println(String.format("Seconda lista %s", listaMinore));
        System.out.println(VUOTA);
        System.out.println(String.format("Ci sono %s long nella lista risultante", listaDifferenza.size()));
        System.out.println(String.format("Differenza -> %s", listaDifferenza));
        System.out.println(String.format("Eseguito in %s", dateService.deltaTextEsatto(inizio)));
    }


    @Test
    @Order(7)
    @DisplayName("7 - deltaBinary2")
    void deltaBinary2() {
        System.out.println("7 - Differenza di due liste medie di 'oggetti' tramite 'deltaBinary'");
        System.out.println(VUOTA);
        int dim = 10000;

        List listaMaggiore = new ArrayList<>();
        List listaMinore = new ArrayList<>();
        List listaDifferenza;

        for (int k = 0; k < dim * 2; k = k + 2) {
            listaMaggiore.add(k);
        }
        for (int k = 0; k < dim * 2; k = k + 3) {
            listaMinore.add(k);
        }

        System.out.println(String.format("Ci sono %s valori nella prima lista", listaMaggiore.size()));
        System.out.println(String.format("Prima lista %s", listaMaggiore));
        System.out.println(String.format("Ci sono %s valori nella seconda lista", listaMinore.size()));
        System.out.println(String.format("Seconda lista %s", listaMinore));

        listaDifferenza = service.deltaBinary(listaMaggiore, listaMinore);
        System.out.println(VUOTA);
        System.out.println(String.format("Ci sono %s valori nella lista risultante", listaDifferenza.size()));
        System.out.println(String.format("Differenza -> %s", listaDifferenza));
        System.out.println(String.format("Eseguito in %s", dateService.deltaTextEsatto(inizio)));

        listaDifferenza = service.deltaBinary(listaMinore, listaMaggiore);
        System.out.println(VUOTA);
        System.out.println(String.format("Ci sono %s valori nella lista risultante (invertita)", listaDifferenza.size()));
        System.out.println(String.format("Differenza -> %s", listaDifferenza));
        System.out.println(String.format("Eseguito in %s", dateService.deltaTextEsatto(inizio)));
    }

    //    @Test
    @Order(17)
    @DisplayName("17 - addSub")
    void addSub() {
        System.out.println("17 - Differenza di due liste di 'long'");
        System.out.println(VUOTA);
        int dimMaggiore = 100;
        int dimMinore = 50;
        int startMinore = 75;

        //        List<Long> listaMaggiore = new ArrayList<>();
        //        List<Long> listaMinore = new ArrayList<>();
        List<Long> listaMaggiore = Arrays.asList(3L, 4L, 5L, 6L, 7L);
        List<Long> listaMinore = Arrays.asList(5L, 6L, 7L, 8L);
        Long longMaggiore;
        List<Long> listaMancanti = new ArrayList<>(); //3,4
        List<Long> listaPresentiEntrambi = new ArrayList<>(); //5,6,7
        Long longMancante;
        Long longPresentiEntrambi;

        //        for (long k = 1; k <= dimMaggiore; k++) {
        //            listaMaggiore.add(k);
        //        }
        //        for (long j = startMinore; j <= startMinore + dimMinore; j++) {
        //            listaMinore.add(j);
        //        }

        //        Collections.sort(listaPageIdsCategoria);
        //        Collections.sort(listaMongoIdsAll);

        long inizio = System.currentTimeMillis();
        for (int k = 0; k < listaMaggiore.size(); k++) {
            longMaggiore = listaMaggiore.get(k);
            int pos = Collections.binarySearch(listaMinore, longMaggiore);

            if (pos < 0) {
                listaMancanti.add(longMaggiore);
            }
            if (pos >= 0) {
                listaPresentiEntrambi.add(longMaggiore);
            }
        }
        long fine = System.currentTimeMillis();
        System.out.println(String.format("Tempo in %s millisecondi", (fine - inizio)));

        System.out.println(String.format("Ci sono %s long mancanti nella seconda lista", listaMancanti.size()));
        System.out.println(String.format("Ci sono %s long presenti in entrambe le liste", listaPresentiEntrambi.size()));

        //        assertNotNull(listaDifferenza);
        printLong(listaMancanti, 10);
        System.out.println(VUOTA);
        printLong(listaPresentiEntrambi, 10);
    }

    protected void printLong(List<Long> listaLong, int max) {
        if (listaLong != null) {
            System.out.println(VUOTA);
            System.out.println(String.format("Pageid"));
            System.out.println(VUOTA);
            for (Long pageId : listaLong.subList(0, Math.min(max, listaLong.size()))) {
                System.out.println(String.format("%s", pageId));
            }
        }
    }

}