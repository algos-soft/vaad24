package it.algos.base;

import com.vaadin.flow.component.html.*;
import com.vaadin.flow.server.*;
import static it.algos.vaad24.backend.boot.VaadCost.*;
import it.algos.vaad24.backend.entity.*;
import it.algos.vaad24.backend.exception.*;
import it.algos.vaad24.backend.interfaces.*;
import it.algos.vaad24.backend.logic.*;
import it.algos.vaad24.backend.packages.anagrafica.*;
import it.algos.vaad24.backend.packages.crono.anno.*;
import it.algos.vaad24.backend.packages.crono.giorno.*;
import it.algos.vaad24.backend.packages.crono.mese.*;
import it.algos.vaad24.backend.packages.crono.secolo.*;
import it.algos.vaad24.backend.packages.geografia.continente.*;
import it.algos.vaad24.backend.packages.utility.logger.ALogger;
import it.algos.vaad24.backend.packages.utility.logger.*;
import it.algos.vaad24.backend.packages.utility.nota.*;
import it.algos.vaad24.backend.packages.utility.preferenza.*;
import it.algos.vaad24.backend.packages.utility.versione.*;
import it.algos.vaad24.backend.service.*;
import it.algos.vaad24.backend.wrapper.*;
import it.algos.vaad24.ui.views.*;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.params.provider.*;
import org.mockito.*;
import org.slf4j.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.context.*;
import org.springframework.data.domain.*;

import java.lang.reflect.*;
import java.time.*;
import java.util.*;
import java.util.stream.*;

/**
 * Project vaadin23
 * Created by Algos
 * User: gac
 * Date: lun, 07-mar-2022
 * Time: 19:47
 * Classe astratta per i test <br>
 *
 * @see <a href="https://www.baeldung.com/parameterized-tests-junit-5">...</a>
 */
public abstract class AlgosTest {

    public static final String SEP_RIGA = "====================";

    protected static final String CONTENUTO = "contenuto";

    protected static final String CONTENUTO_DUE = "mariolino";

    /**
     * The constant ARRAY_STRING.
     */
    protected static final String[] ARRAY_SHORT_STRING = {CONTENUTO};

    /**
     * The constant LIST_STRING.
     */
    protected static final List<String> LIST_SHORT_STRING = new ArrayList(Arrays.asList(ARRAY_SHORT_STRING));

    protected static final String[] ARRAY_SHORT_STRING_DUE = {CONTENUTO_DUE};

    protected static final List<String> LIST_SHORT_STRING_DUE = new ArrayList(Arrays.asList(ARRAY_SHORT_STRING_DUE));

    protected static final LocalDateTime LOCAL_DATE_TIME_UNO = LocalDateTime.of(2014, 10, 21, 7, 42);

    protected static final LocalDateTime LOCAL_DATE_TIME_DUE = LocalDateTime.of(2014, 10, 5, 7, 4);

    public org.slf4j.Logger slf4jLogger;

    protected boolean previstoBooleano;

    protected boolean ottenutoBooleano;

    protected String sorgente;

    protected String sorgente2;

    protected String sorgente3;

    protected String previsto;

    protected String previsto2;

    protected String previsto3;

    protected String ottenuto;

    protected String ottenuto2;

    protected String ottenuto3;

    protected int sorgenteIntero;

    protected int previstoIntero;

    protected int ottenutoIntero;

    protected long sorgenteLong = 0;

    protected long previstoLong = 0;

    protected long ottenutoLong = 0;

    protected double sorgenteDouble = 0;

    protected double previstoDouble = 0;

    protected double ottenutoDouble = 0;

    protected AResult previstoRisultato;

    protected AResult ottenutoRisultato;

    protected LocalDateTime dataSorgente;

    protected LocalDateTime dataPrevista;

    protected LocalDateTime dataOttenuta;

    protected Span span;

    protected Class clazz;

    protected Class entityClazz;

    protected Class sorgenteClasse;

    protected Class previstoClasse;

    protected Class ottenutoClasse;

    protected String clazzName;

    protected Field sorgenteField;

    protected Field ottenutoField;

    protected String[] sorgenteMatrice;

    protected String[] previstoMatrice;

    protected String[] ottenutoMatrice;

    protected List<String> sorgenteArray;

    protected List<String> previstoArray;

    protected List<String> ottenutoArray;

    protected Map<String, String> mappaSorgente;

    protected Map<String, String> mappaPrevista;

    protected Map<String, String> mappaOttenuta;

    protected List<Field> listaFields;

    protected List<Class> listaClazz;

    protected List<String> listaStr;

    protected List<Long> listaLong;

    protected AEntity entityBean;

    protected List<AEntity> listaBeans;

    protected List<CrudBackend> listaBackendClazz;

    protected Map<String, List<String>> mappa;

    protected String message;

    protected byte[] bytes;

    protected StreamResource streamResource;

    protected long inizio;

    protected Sort sort;

    protected TypeBackend typeBackend;

    @Autowired
    public ApplicationContext appContext;

    @Autowired
    protected TextService textService;

    @Autowired
    protected DateService dateService;

    @Autowired
    protected LogService logService;

    @Autowired
    protected MailService mailService;

    @Autowired
    protected AnnotationService annotationService;

    @Autowired
    protected ArrayService arrayService;

    @Autowired
    protected ClassService classService;

    @Autowired
    protected ReflectionService reflectionService;

    @Autowired
    protected FileService fileService;

    @Autowired
    protected ResourceService resourceService;

    @Autowired
    protected HtmlService htmlService;

    @Autowired
    protected UtilityService utilityService;

    @Autowired
    protected WebService webService;

    @Autowired
    public RegexService regexService;

    @Autowired
    protected MongoService mongoService;

    @Autowired
    public LogService logger;

    @Autowired
    public MathService mathService;

    @Autowired
    public PreferenceService preferenceService;

    @Autowired
    public BeanService beanService;

    @Autowired
    protected ViaBackend viaBackend;

    @Autowired
    protected NotaBackend notaBackend;

    @Autowired
    protected GiornoBackend giornoBackend;

    @Autowired
    protected MeseBackend meseBackend;

    @Autowired
    protected AnnoBackend annoBackend;

    @Autowired
    protected SecoloBackend secoloBackend;

    @Autowired
    protected ContinenteBackend continenteBackend;

    @Autowired
    protected ALoggerBackend loggerBackend;

    @Autowired
    protected VersioneBackend versioneBackend;

    @Autowired
    protected PreferenzaBackend preferenzaBackend;

    @Autowired
    protected JarFileService jarFileService;

    //--clazz
    //--simpleName
    protected static Stream<Arguments> CLAZZ_FOR_NAME() {
        return Stream.of(
                Arguments.of(CrudView.class, CrudView.class.getSimpleName()),
                Arguments.of(AIType.class, AIType.class.getSimpleName()),
                Arguments.of(Mese.class, Mese.class.getSimpleName()),
                Arguments.of(Continente.class, Continente.class.getSimpleName()),
                Arguments.of(Giorno.class, Giorno.class.getSimpleName()),
                Arguments.of(ALogger.class, ALogger.class.getSimpleName()),
                Arguments.of(Via.class, Via.class.getSimpleName()),
                Arguments.of(ViaView.class, ViaView.class.getSimpleName()),
                Arguments.of(SecoloView.class, SecoloView.class.getSimpleName())
        );
    }

    //--clazz
    //--usa AIView
    protected static Stream<Arguments> CLAZZ_VIEW() {
        return Stream.of(
                Arguments.of(CrudView.class, false),
                Arguments.of(AIType.class, false),
                Arguments.of(Mese.class, false),
                Arguments.of(PreferenzaView.class, true),
                Arguments.of(SecoloView.class, false)
        );
    }


    //--tag
    //--esiste nella enumeration
    protected static Stream<Arguments> TYPES() {
        return Stream.of(
                Arguments.of(null, false),
                Arguments.of(VUOTA, false),
                Arguments.of("system", true),
                Arguments.of("setup", true),
                Arguments.of("login", true),
                Arguments.of("pippoz", false),
                Arguments.of("startup", true),
                Arguments.of("checkMenu", true),
                Arguments.of("checkData", true),
                Arguments.of("preferenze", true),
                Arguments.of("newEntity", true),
                Arguments.of("edit", true),
                Arguments.of("newEntity", true),
                Arguments.of("modifica", true),
                Arguments.of("delete", true),
                Arguments.of("deleteAll", true),
                Arguments.of("mongoDB", true),
                Arguments.of("debug", true),
                Arguments.of("info", true),
                Arguments.of("warn", true),
                Arguments.of("error", true),
                Arguments.of("info", true),
                Arguments.of("wizard", true),
                Arguments.of("wizarddoc", false),
                Arguments.of("wizardDoc", true),
                Arguments.of("info", true),
                Arguments.of("import", true),
                Arguments.of("export", true),
                Arguments.of("download", true),
                Arguments.of("update", true),
                Arguments.of("Update", false),
                Arguments.of("info", true),
                Arguments.of("elabora", true),
                Arguments.of("reset", true),
                Arguments.of("utente", true),
                Arguments.of("password", true),
                Arguments.of("cicloBio", true)
        );
    }

    /**
     * Qui passa una volta sola, chiamato dalle sottoclassi <br>
     * Invocare PRIMA il metodo setUpStartUp() della superclasse <br>
     * Deve essere sovrascritto, invocando PRIMA il metodo della superclasse <br>
     * Si possono aggiungere regolazioni specifiche <br>
     */
    protected void setUpAll() {
        MockitoAnnotations.openMocks(this);

        slf4jLogger = LoggerFactory.getLogger(TAG_LOG_ADMIN);
        this.typeBackend = TypeBackend.nessuno;

        initMocks();
    }

    /**
     * Inizializzazione dei service <br>
     * Devono essere tutti 'mockati' prima di iniettare i riferimenti incrociati <br>
     * Può essere sovrascritto, invocando PRIMA il metodo della superclasse <br>
     */
    protected void initMocks() {
        assertNotNull(appContext);
        assertNotNull(textService);
        assertNotNull(slf4jLogger);
        assertNotNull(logService);
        assertNotNull(mailService);
        assertNotNull(dateService);
        assertNotNull(annotationService);
        assertNotNull(arrayService);
        assertNotNull(classService);
        assertNotNull(reflectionService);
        assertNotNull(fileService);
        assertNotNull(resourceService);
        assertNotNull(utilityService);
        assertNotNull(htmlService);
        assertNotNull(webService);
        assertNotNull(loggerBackend);
        assertNotNull(regexService);
        assertNotNull(mathService);
        assertNotNull(preferenceService);
        assertNotNull(beanService);
    }


    /**
     * Qui passa prima di ogni test delle sottoclassi <br>
     * Invocare PRIMA il metodo setUpEach() della superclasse <br>
     * Si possono aggiungere regolazioni specifiche <br>
     */
    protected void setUpEach() {
        sorgente = VUOTA;
        sorgente2 = VUOTA;
        sorgente3 = VUOTA;
        previsto = VUOTA;
        previsto2 = VUOTA;
        previsto3 = VUOTA;
        ottenuto = VUOTA;
        ottenuto2 = VUOTA;
        ottenuto3 = VUOTA;
        sorgenteIntero = 0;
        previstoIntero = 0;
        ottenutoIntero = 0;
        sorgenteLong = 0;
        previstoLong = 0;
        ottenutoLong = 0;
        sorgenteDouble = 0;
        previstoDouble = 0;
        ottenutoDouble = 0;
        previstoRisultato = null;
        ottenutoRisultato = null;
        sorgenteClasse = null;
        previstoClasse = null;
        ottenutoClasse = null;
        sorgenteField = null;
        ottenutoField = null;
        sorgenteMatrice = null;
        previstoMatrice = null;
        ottenutoMatrice = null;
        sorgenteArray = null;
        previstoArray = null;
        ottenutoArray = null;
        mappaSorgente = null;
        mappaPrevista = null;
        mappaOttenuta = null;
        clazz = null;
        listaFields = null;
        listaStr = null;
        listaBeans = null;
        entityBean = null;
        mappa = null;
        bytes = null;
        streamResource = null;
        span = null;
        inizio = System.currentTimeMillis();
        message = VUOTA;
        listaClazz = null;
        sort = null;
        listaBackendClazz = null;
        dataSorgente = ROOT_DATA_TIME;
        dataPrevista = ROOT_DATA_TIME;
        dataOttenuta = ROOT_DATA_TIME;
    }


    protected void printError(final AlgosException unErrore) {
        System.out.println(VUOTA);
        System.out.println("Errore");
        if (unErrore.getCause() != null) {
            System.out.println(String.format("Cause %s %s", FORWARD, unErrore.getCause().getClass().getSimpleName()));
        }
        System.out.println(String.format("Message %s %s", FORWARD, unErrore.getMessage()));
        if (unErrore.getEntityBean() != null) {
            System.out.println(String.format("EntityBean %s %s", FORWARD, unErrore.getEntityBean().toString()));
        }

        //@todo rimettere in AlgosException
        //        if (unErrore.getClazz() != null) {
        //            System.out.println(String.format("Clazz %s %s", FORWARD, unErrore.getClazz().getSimpleName()));
        //        }
        //        if (textService.isValid(unErrore.getMethod())) {
        //            System.out.println(String.format("Method %s %s()", FORWARD, unErrore.getMethod()));
        //        }
    }

    protected void printError(final Exception unErrore) {
        System.out.println(VUOTA);
        System.out.println("Errore");
        if (unErrore == null) {
            return;
        }

        if (unErrore instanceof AlgosException erroreAlgos) {
            System.out.println(String.format("Class %s %s", FORWARD, erroreAlgos.getClazz()));
            System.out.println(String.format("Method %s %s", FORWARD, erroreAlgos.getMethod()));
            System.out.println(String.format("Message %s %s", FORWARD, erroreAlgos.getMessage()));
        }
        else {
            System.out.println(String.format("Class %s %s", FORWARD, unErrore.getCause() != null ? unErrore.getCause().getClass().getSimpleName() : VUOTA));
            System.out.println(String.format("Message %s %s", FORWARD, unErrore.getMessage()));
            System.out.println(String.format("Cause %s %s", FORWARD, unErrore.getCause()));
        }
    }

    protected void print(String sorgente, String ottenuto) {
        System.out.println(String.format("%s%s%s", sorgente, FORWARD, ottenuto));
    }

    protected void print(long sorgenteLong, String ottenuto) {
        System.out.println(String.format("%d%s%s", sorgenteLong, FORWARD, ottenuto));
    }

    protected void printLista(final List lista) {
        int cont = 0;
        System.out.println(VUOTA);

        if (lista != null) {
            if (lista.size() > 0) {
                System.out.println(String.format("La lista contiene %d elementi", lista.size()));
                for (Object obj : lista) {
                    cont++;
                    System.out.print(cont);
                    System.out.print(PARENTESI_TONDA_END);
                    System.out.print(SPAZIO);
                    System.out.println(obj);
                }
            }
            else {
                System.out.println("Non ci sono elementi nella lista");
            }
        }
        else {
            System.out.println("Manca la lista");
        }
    }

    protected void printSubLista(final List lista) {
        printSubLista(lista, 10);
    }

    protected void printSubLista(final List lista, int max) {
        String message = VUOTA;
        int cont = 1;
        int tot;
        System.out.println(VUOTA);

        if (lista != null) {
            if (lista.size() > 0) {
                tot = Math.min(lista.size(), max);
                message = String.format("La lista contiene %d elementi.", lista.size());
                if (lista.size() > tot) {
                    message += String.format(" Mostro i primi %d", tot);
                }
                System.out.println(message);
                for (Object obj : lista.subList(0, tot)) {
                    System.out.print(cont);
                    System.out.print(PARENTESI_TONDA_END);
                    System.out.print(SPAZIO);
                    System.out.println(obj);
                    cont++;
                }
                if (lista.size() > tot) {
                    System.out.print(cont);
                    System.out.print(PARENTESI_TONDA_END);
                    System.out.print(SPAZIO);
                    System.out.println(TRE_PUNTI);
                }
            }
            else {
                System.out.println("Non ci sono elementi nella lista");
            }
        }
        else {
            System.out.println("Manca la lista");
        }
    }

    //    protected void printVuota(List<String> lista, String message) {
    //        System.out.println(VUOTA);
    //        print(lista, message);
    //    }

    protected void print(List<String> lista, String message) {
        int k = 1;
        if (lista != null && lista.size() > 0) {
            System.out.println(String.format("Ci sono %d elementi nella lista %s", lista.size(), message));
        }
        else {
            System.out.println("La lista è vuota");
        }
        System.out.println(VUOTA);
        if (arrayService.isAllValid(lista)) {
            for (String stringa : lista) {
                System.out.print(k++);
                System.out.print(PARENTESI_TONDA_END);
                System.out.print(SPAZIO);
                System.out.println(stringa);
            }
        }
    }

    protected void printLong(List<Long> lista) {
        int k = 1;
        if (arrayService.isAllValid(lista)) {
            for (Long stringa : lista) {
                System.out.print(k++);
                System.out.print(PARENTESI_TONDA_END);
                System.out.print(SPAZIO);
                System.out.println(stringa);
            }
        }
    }

    protected void print(List<String> lista) {
        int k = 1;
        if (arrayService.isAllValid(lista)) {
            for (String stringa : lista) {
                System.out.print(k++);
                System.out.print(PARENTESI_TONDA_END);
                System.out.print(SPAZIO);
                System.out.println(stringa);
            }
        }
    }

    protected void print(Map<String, Integer> mappa) {
        for (String key : mappa.keySet()) {
            System.out.print(key);
            System.out.print(FORWARD);
            System.out.println(textService.format(mappa.get(key)));
        }
    }


    protected void printMappa(Map<String, List<String>> mappa, String titoloMappa) {
        List<String> lista;
        String riga;
        if (arrayService.isAllValid(mappa)) {
            System.out.println(VUOTA);
            System.out.println(String.format("Ci sono %d elementi nella mappa %s", mappa.size(), titoloMappa));
            System.out.println(VUOTA);
            for (String key : mappa.keySet()) {
                lista = mappa.get(key);
                if (arrayService.isAllValid(lista)) {
                    System.out.print(key);
                    System.out.print(FORWARD);
                    riga = VUOTA;
                    for (String value : lista) {
                        riga += value;
                        riga += VIRGOLA;
                        riga += SPAZIO;
                    }
                    riga = textService.levaCoda(riga, VIRGOLA).trim();
                    System.out.println(riga);
                }
            }
        }
    }


    protected void printMappa(Map<String, String> mappa) {
        System.out.println(VUOTA);
        System.out.println(String.format("Nella mappa ci sono %d elementi", mappa.size()));
        System.out.println(VUOTA);
        for (String key : mappa.keySet()) {
            message = String.format("%s%s%s", key, FORWARD, mappa.get(key));
            System.out.println(message);
        }
    }

    protected void printTag(AIType enumTag) {
        System.out.println(String.format("%s%s%s", enumTag, FORWARD, enumTag.getTag()));
    }

    protected void printSpan(Span span) {
        System.out.println(span != null ? span.getElement().toString() : VUOTA);
        System.out.println(VUOTA);
    }

    protected String getTime() {
        return dateService.deltaTextEsatto(inizio);
    }

    protected void printRisultato(AResult result) {
        List lista = result.getLista();
        lista = lista != null && lista.size() > 20 ? lista.subList(0, 10) : lista;
        Map<String, Object> mappa = result.getMappa();

        System.out.println("Risultato");
        System.out.println(String.format("Valido: %s", result.isValido() ? "true" : "false"));
        System.out.println(String.format("Eseguito: %s", result.isEseguito() ? "true" : "false"));
        System.out.println(String.format("Result: %s", result.getTypeResult().name()));
        System.out.println(String.format("Method: %s", result.getMethod()));
        System.out.println(String.format("TypeLog: %s", result.getTypeLog()));
        System.out.println(String.format("TypeCopy: %s", result.getTypeCopy()));
        System.out.println(String.format("CopyType: %s", result.getTypeCopy() != null ? result.getTypeCopy().getType() : VUOTA));
        //        System.out.println(String.format("Title: %s", result.getWikiTitle()));
        System.out.println(String.format("Target: %s", result.getTarget()));
        System.out.println(String.format("TypeResultText: %s", result.getTypeResult().getTag()));
        System.out.println(String.format("TypeText: %s", result.getTypeTxt()));
        System.out.println(String.format("Message code: %s", result.getCodeMessage()));
        System.out.println(String.format("Message: %s", result.getMessage()));
        System.out.println(String.format("Exception: %s", result.getException() != null ? result.getException().getMessage() : VUOTA));
        System.out.println(String.format("Error code: %s", result.getErrorCode()));
        System.out.println(String.format("Error message: %s", result.getErrorMessage()));
        System.out.println(String.format("Valid message: %s", result.getValidMessage()));
        System.out.println(String.format("Numeric value: %s", textService.format(result.getIntValue())));
        System.out.println(String.format("List value: %s", lista));
        System.out.println("Map value: ");
        if (mappa != null) {
            for (String key : mappa.keySet()) {
                if ((mappa.get(key)) != null) {
                    System.out.println(String.format("%s%s (null)", TAB, key));
                }
                else {
                    System.out.println(String.format("%s%s (%d): %s", TAB, key, ((List<String>) mappa.get(key)).size(), mappa.get(key)));
                }
            }
        }
        System.out.println(String.format("Tempo: %s", result.deltaSec()));
    }

    protected String getSimpleName(final Class clazz) {
        return clazz != null ? clazz.getSimpleName() : "(manca la classe)";
    }

    protected void startTime() {
        inizio = System.currentTimeMillis();
    }

    protected void printTime() {
        System.out.println(dateService.deltaText(inizio));
    }

    protected void printTimeEsatto() {
        System.out.println(dateService.deltaTextEsatto(inizio));
    }

    protected void printClazz(List<Class> lista) {
        if (lista != null) {
            for (Class clazz : lista) {
                System.out.println(clazz.getSimpleName());
            }
        }
    }

    protected void printValue(Object sinistra, String destra) {
        message = String.format("%s%s%s", sinistra, FORWARD, destra);
        System.out.println(message);
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


    protected void printBackend(final List lista, int max) {
        String message = VUOTA;
        int cont = 1;
        int tot;

        if (lista != null) {
            if (lista.size() > 0) {
                tot = Math.min(lista.size(), max);
                System.out.println(VUOTA);

                message = String.format("La lista contiene %d elementi.", lista.size());
                if (lista.size() > tot) {
                    message += String.format(" Mostro solo i primi %d", tot);
                }
                if (max > 1) {
                    System.out.println(message);
                    System.out.println(VUOTA);
                }

                switch (typeBackend) {
                    case giorno -> printTestaGiorno();
                    case mese -> {
                        System.out.print("ordine");
                        System.out.print(SEP);
                        System.out.print("breve");
                        System.out.print(SEP);
                        System.out.print("nome");
                        System.out.print(SEP);
                        System.out.print("giorni");
                        System.out.print(SEP);
                        System.out.print("primo");
                        System.out.print(SEP);
                        System.out.println("ultimo");
                    }
                    case secolo -> {
                        System.out.print("ordine");
                        System.out.print(SEP);
                        System.out.print("nome");
                        System.out.print(SEP);
                        System.out.print("inizio");
                        System.out.print(SEP);
                        System.out.print("fine");
                        System.out.print(SEP);
                        System.out.println("avanti Cristo");
                    }
                    case anno -> printTestaAnno();
                    case nota -> {
                        System.out.print("type");
                        System.out.print(SEP);
                        System.out.print("livello");
                        System.out.print(SEP);
                        System.out.print("inizio");
                        System.out.print(SEP);
                        System.out.print("descrizione");
                        System.out.print(SEP);
                        System.out.print("fatto");
                        System.out.print(SEP);
                        System.out.println("fine");
                    }
                    default -> printTestaEntityBean();
                } ;

                for (Object obj : lista.subList(0, tot)) {
                    System.out.print(cont);
                    System.out.print(PARENTESI_TONDA_END);
                    System.out.print(SPAZIO);
                    switch (typeBackend) {
                        case giorno -> printGiorno(obj);
                        case mese -> printMese(obj);
                        case secolo -> printSecolo(obj);
                        case anno -> printAnno(obj);
                        case nota -> printNota(obj);
                        default -> printEntityBeans(obj);
                    } ;
                    cont++;
                }
                if (lista.size() > tot) {
                    System.out.print(cont);
                    System.out.print(PARENTESI_TONDA_END);
                    System.out.print(SPAZIO);
                    System.out.println(TRE_PUNTI);
                }
            }
            else {
                System.out.println("Non ci sono elementi nella lista");
            }
        }
        else {
            System.out.println("Manca la lista");
        }
    }

    protected void printTestaEntityBean() {
    }

    protected void printEntityBeans(Object obj) {
        if (obj instanceof AEntity entityBean) {
            System.out.print(entityBean.id);
            System.out.print(SEP);
            System.out.print(entityBean.toString());
            System.out.println(SPAZIO);
        }
    }

    protected void printMese(Object obj) {
        if (obj instanceof Mese mese) {
            System.out.print(mese.breve);
            System.out.print(SEP);
            System.out.print(mese.nome);
            System.out.print(SEP);
            System.out.print(mese.giorni);
            System.out.print(SEP);
            System.out.print(mese.primo);
            System.out.print(SEP);
            System.out.print(mese.ultimo);
            System.out.println(SPAZIO);
        }
    }


    protected void printSecolo(Object obj) {
        if (obj instanceof Secolo secolo) {
            System.out.print(secolo.nome);
            System.out.print(SEP);
            System.out.print(secolo.inizio);
            System.out.print(SEP);
            System.out.print(secolo.fine);
            System.out.print(SEP);
            System.out.print(secolo.anteCristo);
            System.out.println(SPAZIO);
        }
    }

    protected void printTestaGiorno() {
        System.out.print("ordine");
        System.out.print(SEP);
        System.out.print("nome");
        System.out.print(SEP);
        System.out.print("Trascorsi");
        System.out.print(SEP);
        System.out.print("mancanti");
        System.out.println(SPAZIO);
    }

    protected void printGiorno(Object obj) {
        if (obj instanceof Giorno giorno) {
            System.out.print(giorno.nome);
            System.out.print(SEP);
            System.out.print(giorno.trascorsi);
            System.out.print(SEP);
            System.out.print(giorno.mancanti);
            System.out.println(SPAZIO);
        }
    }


    protected void printTestaAnno() {
        System.out.print("ordine");
        System.out.print(SEP);
        System.out.print("nome");
        System.out.print(SEP);
        System.out.print("secolo");
        System.out.print(SEP);
        System.out.print("dopoCristo");
        System.out.print(SEP);
        System.out.print("bisestile");
        System.out.println(SPAZIO);
    }

    protected void printAnno(Object obj) {
        if (obj instanceof Anno anno) {
            System.out.print(anno.nome);
            System.out.print(SEP);
            System.out.print(anno.secolo);
            System.out.print(SEP);
            System.out.print(anno.dopoCristo);
            System.out.print(SEP);
            System.out.print(anno.bisestile);
            System.out.println(SPAZIO);
        }
    }


    protected void printNota(Object obj) {
        if (obj instanceof Nota nota) {
            System.out.print(nota.type);
            System.out.print(SPAZIO);
            System.out.print(nota.livello);
            System.out.print(SPAZIO);
            System.out.print(dateService.get(nota.inizio));
            System.out.print(SPAZIO);
            System.out.print(nota.descrizione);
            System.out.print(SPAZIO);
            System.out.print(nota.livello);
            System.out.print(SPAZIO);
            System.out.print(nota.fatto);
            System.out.print(SPAZIO);
            System.out.print(dateService.get(nota.fine));
            System.out.println(SPAZIO);
        }
    }

    protected void printNota() {
        System.out.print("type");
        System.out.print(SEP);
        System.out.print("livello");
        System.out.print(SEP);
        System.out.print("inizio");
        System.out.print(SEP);
        System.out.print("descrizione");
        System.out.print(SEP);
        System.out.print("fatto");
        System.out.print(SEP);
        System.out.print("fine");
        System.out.println(SPAZIO);
    }

    protected enum TypeBackend {nessuno, via, anno, giorno, mese, secolo, continente, nota, versione, logger}

}