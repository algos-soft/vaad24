package it.algos.vaad24simple.backend.boot;

import com.vaadin.flow.spring.annotation.*;
import static it.algos.vaad24simple.backend.boot.SimpleCost.*;
import it.algos.vaad24.backend.boot.*;
import it.algos.vaad24.backend.enumeration.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.beans.factory.config.*;
import org.springframework.context.annotation.Scope;

/**
 * Project vaadin23
 * Created by Algos
 * User: gac
 * Date: mar, 15-mar-2022
 * Time: 09:17
 * <p>
 * Log delle versioni, modifiche e patch installate <br>
 * Executed on container startup <br>
 * Setup non-UI logic here <br>
 * Classe eseguita solo quando l'applicazione viene caricata/parte nel server (Tomcat o altri) <br>
 * Eseguita quindi a ogni avvio/riavvio del server e NON a ogni sessione <br>
 */
@SpringComponent
@Qualifier(QUALIFIER_VERSION_SIMPLE)
@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
public class SimpleVers extends VaadVers {

    /**
     * Property statica per le versioni inserite da vaadin23 direttamente <br>
     */
    private static final String CODE_PROJECT_SIMPLE = "S";

    /**
     * Executed on container startup <br>
     * Setup non-UI logic here <br>
     * This method is called prior to the servlet context being initialized (when the Web application is deployed). <br>
     * You can initialize servlet context related data here. <br>
     * Metodo eseguito solo quando l'applicazione viene caricata/parte nel server (Tomcat o altri) <br>
     * Eseguito quindi a ogni avvio/riavvio del server e NON a ogni sessione <br>
     * <p>
     * Deve essere sovrascritto, invocando PRIMA il metodo della superclasse <br>
     * Tutte le aggiunte, modifiche e patch vengono inserite con una versione <br>
     * L'ordine di inserimento è FONDAMENTALE <br>
     */
    @Override
    public void inizia() {
        super.inizia();
        int k = 0;
        codeProject = CODE_PROJECT_SIMPLE;

        //--prima installazione del progetto specifico 'Simple'
        //--non fa nulla, solo informativo
        if (installa(++k)) {
            this.crea(k, AETypeVers.setup, "Installazione iniziale del programma simple");
        }

    }

}
