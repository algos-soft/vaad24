package it.algos.vaad24simple.backend.boot;

import com.vaadin.flow.spring.annotation.*;
import static it.algos.vaad24simple.backend.boot.SimpleCost.*;
import it.algos.vaad24.backend.boot.*;
import it.algos.vaad24simple.backend.enumeration.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.beans.factory.config.*;
import org.springframework.context.annotation.Scope;
import org.springframework.context.event.*;

/**
 * Project vaadin23
 * Created by Algos
 * User: gac
 * Date: sab, 07-mag-2022
 * Time: 13:29
 */
@SpringComponent
@Qualifier(QUALIFIER_PREFERENCES_SIMPLE)
@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
public class SimplePref extends VaadPref {

    /**
     * The ContextRefreshedEvent happens after both Vaadin and Spring are fully initialized. At the time of this
     * event, the application is ready to service Vaadin requests <br>
     */
    @EventListener(ContextRefreshedEvent.class)
    public void onContextRefreshEvent() {
        this.inizia();
    }


    /**
     * Executed on container startup <br>
     * Setup non-UI logic here <br>
     * This method is called prior to the servlet context being initialized (when the Web application is deployed). <br>
     * You can initialize servlet context related data here. <br>
     * Metodo eseguito solo quando l'applicazione viene caricata/parte nel server (Tomcat o altri) <br>
     * Eseguito quindi a ogni avvio/riavvio del server e NON a ogni sessione <br>
     */
    public void inizia() {
        super.inizia();
        for (SPref pref : SPref.getAllEnums()) {
            crea(pref);
        }
    }

    /**
     * Inserimento di una preferenza del progetto specifico <br>
     * Controlla che la entity non esista già <br>
     */
    protected void crea(final SPref pref) {
        crea(pref.getKeyCode(), pref.getType(), pref.getDefaultValue(), pref.getDescrizione(), false, false, pref.isDinamica(), pref.getEnumClazz());
    }

}
