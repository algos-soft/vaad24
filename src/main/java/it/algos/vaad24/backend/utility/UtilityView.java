package it.algos.vaad24.backend.utility;

import com.vaadin.flow.component.button.*;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.orderedlayout.*;
import com.vaadin.flow.router.*;
import it.algos.vaad24.backend.boot.*;
import static it.algos.vaad24.backend.boot.VaadCost.*;
import it.algos.vaad24.backend.enumeration.*;
import it.algos.vaad24.backend.packages.utility.preferenza.*;
import it.algos.vaad24.backend.service.*;
import it.algos.vaad24.backend.wrapper.*;
import it.algos.vaad24.ui.dialog.*;
import it.algos.vaad24.ui.views.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.context.*;

import javax.annotation.*;
import java.util.*;

/**
 * Project vaad24
 * Created by Algos
 * User: gac
 * Date: Tue, 13-Dec-2022
 * Time: 11:36
 */
@Route(value = VaadCost.TAG_UTILITY, layout = MainLayout.class)
//@RouteAlias(value = TAG_ROUTE_ALIAS_PARTE_PER_PRIMA, layout = MainLayout.class)
//@CssImport("./styles/shared-styles.css")
public class UtilityView extends VerticalLayout {

    /**
     * Istanza di una interfaccia SpringBoot <br>
     * Iniettata automaticamente dal framework SpringBoot con l'Annotation @Autowired <br>
     * Disponibile DOPO il ciclo init() del costruttore di questa classe <br>
     */
    @Autowired
    public ApplicationContext appContext;

    /**
     * Istanza unica di una classe @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON) di servizio <br>
     * Iniettata dal framework SpringBoot/Vaadin usando il metodo setter() <br>
     * al termine del ciclo init() del costruttore di questa classe <br>
     */
    @Autowired
    public TextService textService;

    /**
     * Istanza unica di una classe @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON) di servizio <br>
     * Iniettata dal framework SpringBoot/Vaadin usando il metodo setter() <br>
     * al termine del ciclo init() del costruttore di questa classe <br>
     */
    @Autowired
    public ClassService classService;

    /**
     * Istanza unica di una classe @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON) di servizio <br>
     * Iniettata automaticamente dal framework SpringBoot/Vaadin con l'Annotation @Autowired <br>
     * Disponibile DOPO il ciclo init() del costruttore di questa classe <br>
     */
    @Autowired
    public FileService fileService;

    /**
     * Istanza unica di una classe @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON) di servizio <br>
     * Iniettata automaticamente dal framework SpringBoot/Vaadin con l'Annotation @Autowired <br>
     * Disponibile DOPO il ciclo init() del costruttore di questa classe <br>
     */
    @Autowired
    public LogService logger;

    @Autowired
    public PreferenzaBackend preferenzaBackend;

    protected static String RELEASE = "Da eseguire dopo una nuova release significativa. Non serve se il database è stato completamente cancellato (drop).";
    protected static String AUTOMATIC = "Viene eseguito AUTOMATICAMENTE se il database è stato completamente cancellato (drop).";
    protected static String DROP = "Da eseguire SEMPRE se il database è stato completamente cancellato (drop).";

    protected static String FLAG_DEBUG = "Mette temporaneamente a TRUE il flag 'debug' delle preferenze e poi ripristina il valore originale.";

    protected boolean debugPrefOldValue;

    /**
     * Questa classe viene costruita partendo da @Route e NON dalla catena @Autowired di SpringBoot <br>
     */
    public UtilityView() {
        super();
    }// end of Vaadin/@Route constructor


    @PostConstruct
    protected void postConstruct() {
        initView();
    }

    /**
     * Qui va tutta la logica iniziale della view principale <br>
     */
    protected void initView() {
        this.setMargin(true);
        this.setPadding(false);
        this.setSpacing(false);

        this.titolo();
        this.body();

        //--spazio per distanziare i paragrafi
        this.add(new H3());
    }

    public void titolo() {
        H2 titolo = new H2("Gestione utility");
        titolo.getElement().getStyle().set("color", "green");
        this.add(titolo);
    }

    public void body() {
        this.paragrafoPreferenze();
        this.paragrafoReset();
    }


    public void paragrafoPreferenze() {
        VerticalLayout layout = new VerticalLayout();
        layout.setMargin(false);
        layout.setPadding(false);
        layout.setSpacing(false);
        String message;
        H3 paragrafo = new H3("Reset delle preferenze");
        paragrafo.getElement().getStyle().set("color", "blue");

        message = String.format("Esegue il reset/refresh di tutte le preferenze");
        layout.add(ASpan.text(message));
        layout.add(ASpan.text(RELEASE));
        message = String.format("Il valore originale del flag 'debug' dopo il reset è TRUE");
        layout.add(ASpan.text(message));
        message = "Refresh -> ripristina nel database i valori di default (delle preferenze non dinamiche) annullando le successive modifiche.";
        layout.add(ASpan.text(message));
        message = "Reset -> ripristina nel database i valori di default di tutte le preferenze annullando le successive modifiche.";
        layout.add(ASpan.text(message));

        Button bottone = new Button("Refresh");
        bottone.getElement().setAttribute("theme", "primary");
        bottone.addClickListener(event -> refreshPreferenze());

        Button bottone2 = new Button("Reset");
        bottone2.getElement().setAttribute("theme", "primary");
        bottone2.addClickListener(event -> resetPreferenze());

        this.add(paragrafo);
        layout.add(new HorizontalLayout(bottone, bottone2));
        this.add(layout);
    }


    public void paragrafoReset() {
        VerticalLayout layout = new VerticalLayout();
        layout.setMargin(false);
        layout.setPadding(false);
        layout.setSpacing(false);
        String message;
        List<String> lista;
        H3 paragrafo = new H3("Reset di tutte le collection [ordinate]");
        paragrafo.getElement().getStyle().set("color", "blue");

        message = String.format("Esegue il %s() su tutte le collection [ordinate] che implementano %s(). ", METHOD_NAME_RESET_FORCING, METHOD_NAME_RESET_ONLY);
        layout.add(ASpan.text(message));
        layout.add(ASpan.text(AUTOMATIC));
        layout.add(ASpan.text(FLAG_DEBUG));
        lista = classService.allModuleEntityResetOrderedName(VaadVar.moduloVaadin24);
        message = String.format("Modulo %s%s%s", VaadVar.moduloVaadin24, DUE_PUNTI_SPAZIO, lista.toString());
        layout.add(ASpan.text(message));
        lista = classService.allModuleEntityResetOrderedName(VaadVar.projectNameModulo);
        message = String.format("Modulo %s%s%s", VaadVar.projectNameModulo, DUE_PUNTI_SPAZIO, lista.toString());
        layout.add(ASpan.text(message));
        Button bottone = new Button("Reset all");
        bottone.getElement().setAttribute("theme", "primary");
        bottone.addClickListener(event -> AReset.reset(this::resetCollections));

        this.add(paragrafo);
        layout.add(bottone);
        this.add(layout);
    }


    private void resetCollections() {
        inizioDebug();

        logger.info(new WrapLog().message(VUOTA).type(AETypeLog.utility));
        logger.info(new WrapLog().message("Utility: reset di tutte le collection.").type(AETypeLog.utility));
        resetSingoloModulo(VaadVar.moduloVaadin24);
        logger.info(new WrapLog().message(VUOTA).type(AETypeLog.reset));
        resetSingoloModulo(VaadVar.projectNameModulo);
        logger.info(new WrapLog().message(VUOTA).type(AETypeLog.reset));

        fineDebug();
    }

    private void resetSingoloModulo(String nomeModulo) {
        AResult risultato;
        boolean esiste;
        String message;
        List<Class> listaClazz;

        esiste = fileService.isEsisteModulo(nomeModulo);
        if (!esiste) {
            message = String.format("Non esiste il modulo '%s'", nomeModulo);
            logger.warn(new WrapLog().message(message).type(AETypeLog.reset));
            return;
        }

        listaClazz = classService.allModuleBackendResetOrderedClass(nomeModulo);
        if (listaClazz != null && listaClazz.size() > 0) {
            if (listaClazz.size() == 1) {
                message = String.format("Nel modulo %s c'è una sola classe che implementa il metodo %s", nomeModulo, METHOD_NAME_RESET_ONLY);
            }
            else {
                message = String.format("Nel modulo %s ci sono %d classi che implementano il metodo %s", nomeModulo, listaClazz.size(), METHOD_NAME_RESET_ONLY);
            }
            logger.info(new WrapLog().message(message).type(AETypeLog.reset));
            for (Class clazz : listaClazz) {
                logger.info(new WrapLog().message(VUOTA).type(AETypeLog.reset));
                classService.esegueMetodo(clazz.getCanonicalName(), METHOD_NAME_RESET_FORCING);
            }
        }
        else {
            message = String.format("Nel modulo %s non ci sono classi che implementano il metodo %s", nomeModulo, METHOD_NAME_RESET_ONLY);
            logger.info(new WrapLog().message(message).type(AETypeLog.reset));
        }
    }


    protected void refreshPreferenze() {
        logger.info(new WrapLog().message(VUOTA).type(AETypeLog.utility));
        logger.info(new WrapLog().message("Utility: refresh di tutte le preferenze non dinamiche.").type(AETypeLog.utility));
        preferenzaBackend.refreshAll();
    }


    protected void resetPreferenze() {
        logger.info(new WrapLog().message(VUOTA).type(AETypeLog.utility));
        logger.info(new WrapLog().message("Utility: reset di tutte le preferenze.").type(AETypeLog.utility));
        preferenzaBackend.deleteAll();
    }

    protected void inizioDebug() {
        debugPrefOldValue = Pref.debug.is();
        Pref.debug.setValue(true);
    }

    protected void fineDebug() {
        Pref.debug.setValue(debugPrefOldValue);
    }

}
