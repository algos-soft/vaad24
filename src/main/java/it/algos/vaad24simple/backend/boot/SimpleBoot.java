package it.algos.vaad24simple.backend.boot;

import com.vaadin.flow.spring.annotation.*;
import it.algos.vaad24.backend.boot.*;
import it.algos.vaad24.backend.interfaces.*;
import it.algos.vaad24.backend.packages.anagrafica.*;
import it.algos.vaad24.backend.packages.crono.anno.*;
import it.algos.vaad24.backend.packages.crono.giorno.*;
import it.algos.vaad24.backend.packages.crono.mese.*;
import it.algos.vaad24.backend.packages.crono.secolo.*;
import it.algos.vaad24.backend.packages.geografia.continente.*;
import it.algos.vaad24.ui.views.*;
import static it.algos.vaad24simple.backend.boot.SimpleCost.*;
import it.algos.vaad24simple.backend.enumeration.*;
import it.algos.vaad24simple.backend.packages.prova.*;
import it.algos.vaad24simple.backend.schedule.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.beans.factory.config.*;
import org.springframework.context.annotation.Scope;

import javax.annotation.*;
import javax.servlet.*;

/**
 * Project vaadin23
 * Created by Algos
 * User: gac
 * Date: mar, 15-mar-2022
 * Time: 09:36
 * <p>
 * Questa classe astratta riceve un @EventListener implementato nella superclasse <br>
 * 1) regola alcuni parametri standard del database MongoDB <br>
 * 2) regola le variabili generali dell'applicazione <br>
 * 3) crea i dati di alcune collections sul DB mongo <br>
 * 4) crea le preferenze standard e specifiche dell'applicazione <br>
 * 5) aggiunge le @Route (view) standard e specifiche <br>
 * 6) lancia gli schedulers in background <br>
 * 7) costruisce una versione demo <br>
 * 8) controlla l' esistenza di utenti abilitati all' accesso <br>
 */
@SpringComponent
@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
public class SimpleBoot extends VaadBoot implements ServletContextListener {


    public static void start() {
        new SimpleBoot();
    }

    /**
     * Performing the initialization in a constructor is not suggested as the state of the UI is not properly set up when the constructor is invoked. <br>
     * La injection viene fatta da SpringBoot SOLO DOPO il metodo init() del costruttore <br>
     * Si usa quindi un metodo @PostConstruct per avere disponibili tutte le istanze @Autowired <br>
     * <p>
     * Ci possono essere diversi metodi con @PostConstruct e firme diverse e funzionano tutti <br>
     * L'ordine con cui vengono chiamati (nella stessa classe) NON è garantito <br>
     * Se viene implementata una istanza di sottoclasse, passa di qui per ogni istanza <br>
     */
    @PostConstruct
    private void postConstruct() {
        super.inizia();
    }

    /**
     * Crea le Enumeration in memoria <br>
     * Aggiunge le singole Enumeration alla lista globale <br>
     * NON usa la injection di SpringBoot <br>
     * NON crea le preferenze su mondoDB <br>
     * Può essere sovrascritto, invocando PRIMA il metodo della superclasse <br>
     */
    public void creaEnumerationPreferenze() {
        super.creaEnumerationPreferenze();

        for (SPref pref : SPref.values()) {
            VaadVar.prefList.add(pref);
        }
    }

    /**
     * Costruisce alcune istanze generali dell'applicazione e ne mantiene i riferimenti nelle apposite variabili <br>
     * Le istanze (prototype) sono uniche per tutta l' applicazione <br>
     * Vengono create SOLO in questa classe o in una sua sottoclasse <br>
     * La selezione su quale istanza creare tocca a questa sottoclasse xxxBoot <br>
     * Se la sottoclasse non ha creato l'istanza, ci pensa la superclasse <br>
     * Può essere sovrascritto, invocando DOPO il metodo della superclasse <br>
     */
    @Override
    protected void fixVariabiliRiferimentoIstanzeGenerali() {
        /*
         * Classe da usare per lo startup del programma <br>
         * Di default FlowData oppure possibile sottoclasse del progetto <br>
         * Deve essere regolato in backend.boot.xxxBoot.fixVariabiliRiferimentoIstanzeGenerali() del progetto corrente <br>
         */
        VaadVar.istanzaData = appContext.getBean(SimpleData.class);

        /*
         * Classe da usare per gestire le versioni <br>
         * Di default FlowVers oppure possibile sottoclasse del progetto <br>
         * Deve essere regolato in backend.boot.xxxBoot.fixVariabili() del progetto corrente <br>
         */
        VaadVar.versionClazz = SimpleVers.class;

        super.fixVariabiliRiferimentoIstanzeGenerali();
    }

    /**
     * Set con @Autowired di una property chiamata dal costruttore <br>
     * Istanza di una classe @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON) <br>
     * Chiamata dal costruttore di questa classe con valore nullo <br>
     * Iniettata dal framework SpringBoot/Vaadin al termine del ciclo init() del costruttore di questa classe <br>
     */
    @Autowired
    @Qualifier(QUALIFIER_VERSION_SIMPLE)
    public void setVersInstance(final AIVers versInstance) {
        this.versInstance = versInstance;
    }

    //    /*
    //     * Set con @Autowired di una property chiamata dal costruttore <br>
    //     * Istanza di una classe @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON) <br>
    //     * Chiamata dal costruttore di questa classe con valore nullo <br>
    //     * Iniettata dal framework SpringBoot/Vaadin al termine del ciclo init() del costruttore di questa classe <br>
    //     */
    //    @Autowired
    //    @Qualifier(QUALIFIER_DATA_SIMPLE)
    //    public void setDataInstance(final AIData dataInstance) {
    //        this.dataInstance = dataInstance;
    //    }

    /*
     * Set con @Autowired di una property chiamata dal costruttore <br>
     * Istanza di una classe @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON) <br>
     * Chiamata dal costruttore di questa classe con valore nullo <br>
     * Iniettata dal framework SpringBoot/Vaadin al termine del ciclo init() del costruttore di questa classe <br>
     */

//    @Autowired
//    @Qualifier(QUALIFIER_PREFERENCES_SIMPLE)
//    public void setPrefInstance(final AIEnumPref prefInstance) {
//        VaadVar.prefInstance = prefInstance;
//    }


    /**
     * Eventuali task <br>
     * Può essere sovrascritto, invocando PRIMA il metodo della superclasse <br>
     */
    @Override
    public void fixSchedule() {
        appContext.getBean(Vaad24SimpleSchedule.class);

        super.fixSchedule();
    }


    @Override
    protected void fixMenuRoutes() {
        VaadVar.menuRouteListProject.add(TestView.class);
        super.fixMenuRoutes();

        VaadVar.menuRouteListProject.add(ContinenteView.class);
        VaadVar.menuRouteListProject.add(MeseView.class);
        VaadVar.menuRouteListProject.add(SecoloView.class);
        VaadVar.menuRouteListProject.add(GiornoView.class);
        VaadVar.menuRouteListProject.add(AnnoView.class);
        VaadVar.menuRouteListProject.add(ViaView.class);
        VaadVar.menuRouteListProject.add(ProvaView.class);
    }

}
