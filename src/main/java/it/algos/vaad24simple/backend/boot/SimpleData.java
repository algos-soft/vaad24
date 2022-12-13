package it.algos.vaad24simple.backend.boot;

import com.vaadin.flow.spring.annotation.*;
import it.algos.vaad24.backend.boot.*;
import org.springframework.beans.factory.config.*;
import org.springframework.context.annotation.Scope;

/**
 * Project vaadin23
 * Created by Algos
 * User: gac
 * Date: Wed, 15-Jun-2022
 * Time: 15:00
 */
@SpringComponent
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class SimpleData extends VaadData {


    /**
     * Costruttore senza parametri <br>
     * Not annotated with @Autowired annotation, per creare l'istanza SOLO come SCOPE_PROTOTYPE <br>
     * L'istanza DEVE essere creata con appContext.getBean(SimpleData.class); <br>
     * Non utilizzato e non necessario <br>
     * Per evitare il bug (solo visivo), aggiungo un costruttore senza parametri <br>
     */
    public SimpleData() {
    }// end of constructor not @Autowired


    /**
     * Check iniziale. A ogni avvio del programma spazzola tutte le collections <br>
     * Ognuna viene ricreata (mantenendo le entities che hanno reset=false) se:
     * - xxx->@AIEntity usaBoot=true,
     * - esiste xxxService.reset(),
     * - la collezione non contiene nessuna entity che abbia la property reset=true
     * Può essere sovrascritto, invocando PRIMA il metodo della superclasse <br>
     * L' ordine con cui vengono create le collections è significativo <br>
     *
     * @since java 8
     */
    @Override
    protected void resetData() {
        super.resetData();

        //--altre eventuali regolazioni specifiche di questa applicazione
    }

}
