package it.algos.vaad24.backend.service;

import static it.algos.vaad24.backend.boot.VaadCost.*;
import it.algos.vaad24.backend.enumeration.*;
import it.algos.vaad24.backend.packages.utility.logger.*;
import it.algos.vaad24.backend.packages.utility.preferenza.*;
import it.algos.vaad24.backend.wrapper.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.context.*;
import org.springframework.core.env.*;

import javax.annotation.*;
import java.lang.reflect.*;
import java.util.*;

/**
 * Project vaadin23
 * Created by Algos
 * User: gac
 * Date: dom, 06-mar-2022
 * Time: 18:31
 * <p>
 * Superclasse astratta delle librerie xxxService. <br>
 * Serve per 'dichiarare' in un posto solo i riferimenti ad altre classi ed usarli nelle sottoclassi concrete <br>
 * I riferimenti sono 'public' (e non protected) per poterli usare con TestUnit <br>
 */
public abstract class AbstractService {

    public static Set<AbstractService> SERVIZI = new HashSet();

    /**
     * Istanza di una interfaccia SpringBoot <br>
     * Iniettata automaticamente dal framework SpringBoot con l'Annotation @Autowired <br>
     * Disponibile DOPO il ciclo init() del costruttore di questa classe <br>
     */
    @Autowired
    public ApplicationContext appContext;

    /**
     * Istanza unica di una classe @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON) di servizio <br>
     * Iniettata automaticamente dal framework SpringBoot/Vaadin con l'Annotation @Autowired <br>
     * Disponibile DOPO il ciclo init() del costruttore di questa classe <br>
     */
    @Autowired
    public AnnotationService annotationService;

    /**
     * Istanza unica di una classe @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON) di servizio <br>
     * Iniettata automaticamente dal framework SpringBoot/Vaadin con l'Annotation @Autowired <br>
     * Disponibile DOPO il ciclo init() del costruttore di questa classe <br>
     */
    @Autowired
    public HtmlService htmlService;

    /**
     * Istanza unica di una classe @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON) di servizio <br>
     * Iniettata automaticamente dal framework SpringBoot/Vaadin con l'Annotation @Autowired <br>
     * Disponibile DOPO il ciclo init() del costruttore di questa classe <br>
     */
    @Autowired
    public MongoService mongoService;

    /**
     * Istanza unica di una classe @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON) di servizio <br>
     * Iniettata automaticamente dal framework SpringBoot/Vaadin con l'Annotation @Autowired <br>
     * Disponibile DOPO il ciclo init() del costruttore di questa classe <br>
     */
    @Autowired
    public DateService dateService;


    /**
     * Istanza unica di una classe @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON) di servizio <br>
     * Iniettata automaticamente dal framework SpringBoot/Vaadin con l'Annotation @Autowired <br>
     * Disponibile DOPO il ciclo init() del costruttore di questa classe <br>
     */
    @Autowired
    public LogService logService;


    /**
     * Istanza unica di una classe @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON) di servizio <br>
     * Iniettata dal framework SpringBoot/Vaadin usando il metodo setter() <br>
     * al termine del ciclo init() del costruttore di questa classe <br>
     */
    @Autowired
    public Environment environment;

    /**
     * Istanza unica di una classe @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON) di servizio <br>
     * Iniettata automaticamente dal framework SpringBoot/Vaadin con l'Annotation @Autowired <br>
     * Disponibile DOPO il ciclo init() del costruttore di questa classe <br>
     */
    @Autowired
    public MailService mailService;


    /**
     * Istanza unica di una classe @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON) di servizio <br>
     * Iniettata automaticamente dal framework SpringBoot/Vaadin con l'Annotation @Autowired <br>
     * Disponibile DOPO il ciclo init() del costruttore di questa classe <br>
     */
    @Autowired
    public ReflectionService reflectionService;


    /**
     * Istanza unica di una classe @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON) di servizio <br>
     * Iniettata automaticamente dal framework SpringBoot/Vaadin con l'Annotation @Autowired <br>
     * Disponibile DOPO il ciclo init() del costruttore di questa classe <br>
     */
    @Autowired
    public TextService textService;


    /**
     * Istanza unica di una classe @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON) di servizio <br>
     * Iniettata automaticamente dal framework SpringBoot/Vaadin con l'Annotation @Autowired <br>
     * Disponibile DOPO il ciclo init() del costruttore di questa classe <br>
     */
    @Autowired
    public ArrayService arrayService;


    /**
     * Istanza unica di una classe @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON) di servizio <br>
     * Iniettata dal framework SpringBoot/Vaadin usando il metodo setter() <br>
     * al termine del ciclo init() del costruttore di questa classe <br>
     */
    @Autowired
    public ClassService classService;

    /**
     * Istanza unica di una classe @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON) di servizio <br>
     * Iniettata dal framework SpringBoot/Vaadin usando il metodo setter() <br>
     * al termine del ciclo init() del costruttore di questa classe <br>
     */
    @Autowired
    public FileService fileService;

    /**
     * Istanza unica di una classe @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON) di servizio <br>
     * Iniettata dal framework SpringBoot/Vaadin usando il metodo setter() <br>
     * al termine del ciclo init() del costruttore di questa classe <br>
     */
    @Autowired
    public ResourceService resourceService;

    /**
     * Istanza unica di una classe @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON) di servizio <br>
     * Iniettata dal framework SpringBoot/Vaadin usando il metodo setter() <br>
     * al termine del ciclo init() del costruttore di questa classe <br>
     */
    @Autowired
    public WebService webService;

    /**
     * Istanza unica di una classe @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON) di servizio <br>
     * Iniettata dal framework SpringBoot/Vaadin usando il metodo setter() <br>
     * al termine del ciclo init() del costruttore di questa classe <br>
     */
    @Autowired
    public UtilityService utilityService;

    /**
     * Istanza unica di una classe @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON) di servizio <br>
     * Iniettata dal framework SpringBoot/Vaadin usando il metodo setter() <br>
     * al termine del ciclo init() del costruttore di questa classe <br>
     */
    @Autowired
    public PreferenzaBackend preferenzaBackend;

    /**
     * Istanza unica di una classe @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON) di servizio <br>
     * Iniettata dal framework SpringBoot/Vaadin usando il metodo setter() <br>
     * al termine del ciclo init() del costruttore di questa classe <br>
     */
    @Autowired
    public ALoggerBackend aLoggerBackend;

    /**
     * Istanza unica di una classe @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON) di servizio <br>
     * Iniettata dal framework SpringBoot/Vaadin usando il metodo setter() <br>
     * al termine del ciclo init() del costruttore di questa classe <br>
     */
    @Autowired
    public PreferenceService preferenceService;

    /**
     * Istanza unica di una classe @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON) di servizio <br>
     * Iniettata dal framework SpringBoot/Vaadin usando il metodo setter() <br>
     * al termine del ciclo init() del costruttore di questa classe <br>
     */
    @Autowired
    public MathService mathService;

    /**
     * Istanza unica di una classe @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON) di servizio <br>
     * Iniettata dal framework SpringBoot/Vaadin usando il metodo setter() <br>
     * al termine del ciclo init() del costruttore di questa classe <br>
     */
    @Autowired
    public RegexService regexService;

    /**
     * Istanza unica di una classe @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON) di servizio <br>
     * Iniettata dal framework SpringBoot/Vaadin usando il metodo setter() <br>
     * al termine del ciclo init() del costruttore di questa classe <br>
     */
    @Autowired
    public BeanService beanService;


    /**
     * Istanza unica di una classe @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON) di servizio <br>
     * Iniettata dal framework SpringBoot/Vaadin usando il metodo setter() <br>
     * al termine del ciclo init() del costruttore di questa classe <br>
     */
    @Autowired
    public JarFileService jarFileService;

    public AbstractService() {
    }

    @PostConstruct
    private void postConstruct() {
        fixAllServices();
        fixLinkIncrociati();
        fixLinkPreferenze();
    }

    protected void fixAllServices() {
        SERVIZI.add(this.annotationService);
        SERVIZI.add(this.htmlService);
        SERVIZI.add(this.textService);
        SERVIZI.add(this.arrayService);
        SERVIZI.add(this.reflectionService);
        SERVIZI.add(this.mongoService);
        SERVIZI.add(this.fileService);
        SERVIZI.add(this.classService);
        SERVIZI.add(this.resourceService);
        SERVIZI.add(this.dateService);
        SERVIZI.add(this.utilityService);
        SERVIZI.add(this.beanService);
        SERVIZI.add(this.preferenceService);
        SERVIZI.add(this.webService);
        SERVIZI.add(this.mailService);
        SERVIZI.add(this.regexService);
        SERVIZI.add(this.jarFileService);
        SERVIZI.add(this.preferenzaBackend);
        SERVIZI.add(this.logService);
        SERVIZI.add(this.aLoggerBackend);
    }

    protected void fixLinkIncrociati() {
        String publicFieldName = VUOTA;
        String message;
        String clazzName = VUOTA;
        Field field;

        if (logService.slf4jLogger == null) {
            logService.postConstruct();
        }

        for (AbstractService servizio : SERVIZI) {
            for (AbstractService valoreLinkato : SERVIZI) {
                if (valoreLinkato != servizio) {
                    try {
                        clazzName = valoreLinkato.getClass().getSimpleName();
                        publicFieldName = textService.primaMinuscola(clazzName);
                    } catch (Exception unErrore) {
                        message = String.format("Non sono riuscito a trovare il field %s nel service %s", valoreLinkato, valoreLinkato);
                        System.out.println(message);
                        logService.error(new WrapLog().message(message));
                    }

                    try {
                        publicFieldName = publicFieldName.replaceAll(FIELD_NAME_ID_CON, FIELD_NAME_ID_SENZA);
                        field = valoreLinkato.getClass().getField(publicFieldName);
                        field.set(servizio, valoreLinkato);
                    } catch (Exception unErrore) {

                        clazzName = valoreLinkato.getClass().getSimpleName();
                        clazzName = textService.primaMinuscola(clazzName);
                        if (!clazzName.equals(publicFieldName)) {
                            message = String.format("Non sono riuscito ad incrociare i link di %s nel service %s", publicFieldName, clazzName);
                            System.out.println(message);
                            logService.error(new WrapLog().message(message));
                        }

                    }

                }
            }
        }
    }

    public void fixLinkPreferenze() {
        for (Pref pref : Pref.values()) {
            pref.setPreferenceService(preferenceService);
            pref.setLogger(logService);
            pref.setDate(dateService);
            pref.setText(textService);
        }
    }

}