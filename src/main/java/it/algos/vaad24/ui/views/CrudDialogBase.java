package it.algos.vaad24.ui.views;

import com.vaadin.flow.spring.annotation.*;
import it.algos.vaad24.backend.entity.*;
import it.algos.vaad24.backend.logic.*;
import org.springframework.beans.factory.config.*;
import org.springframework.context.annotation.Scope;
import org.vaadin.crudui.crud.*;

import java.util.*;

/**
 * Project vaadin23
 * Created by Algos
 * User: gac
 * Date: Sat, 15-Oct-2022
 * Time: 14:44
 */
@SpringComponent
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class CrudDialogBase extends CrudDialog {


    /**
     * Constructor not @Autowired. <br>
     * Non utilizzato e non necessario <br>
     * In the newest Spring release, it’s constructor does not need to be annotated with @Autowired annotation <br>
     * Se c'è un SOLO costruttore questo diventa automaticamente @Autowired e IntelliJ Idea 'segna' in rosso i
     * parametri <br>
     * Per evitare il bug (solo visivo), aggiungo un costruttore senza parametri <br>
     */
    public CrudDialogBase() {
    }// end of second constructor not @Autowired

    /**
     * Costruttore con parametri <br>
     * Not annotated with @Autowired annotation, per creare l'istanza SOLO come SCOPE_PROTOTYPE <br>
     * L'istanza DEVE essere creata con appContext.getBean(CrudDialog.class, operation, itemSaver, itemDeleter, itemAnnulla); <br>
     *
     * @param entityBean  The item to edit; it may be an existing or a newly created instance
     * @param operation   The operation being performed on the item (addNew, edit, editNoDelete, editDaLink, showOnly)
     * @param crudBackend service specifico per la businessLogic e il collegamento con la persistenza dei dati
     * @param fields      da costruire in automatico
     */
    public CrudDialogBase(final AEntity entityBean, final CrudOperation operation, final CrudBackend crudBackend, final List<String> fields) {
        this(entityBean, operation, crudBackend, fields, true);
    }// end of constructor not @Autowired

    /**
     * Costruttore con parametri <br>
     * Not annotated with @Autowired annotation, per creare l'istanza SOLO come SCOPE_PROTOTYPE <br>
     * L'istanza DEVE essere creata con appContext.getBean(CrudDialog.class, operation, itemSaver, itemDeleter, itemAnnulla); <br>
     *
     * @param entityBean        The item to edit; it may be an existing or a newly created instance
     * @param operation         The operation being performed on the item (addNew, edit, editNoDelete, editDaLink, showOnly)
     * @param crudBackend       service specifico per la businessLogic e il collegamento con la persistenza dei dati
     * @param fields            da costruire in automatico
     * @param usaUnaSolaColonna di default=true
     */
    public CrudDialogBase(final AEntity entityBean, final CrudOperation operation, final CrudBackend crudBackend, final List<String> fields, final boolean usaUnaSolaColonna) {
        this.currentItem = entityBean;
        this.operation = operation;
        this.crudBackend = crudBackend;
        this.fields = fields;
        this.usaUnaSolaColonna = usaUnaSolaColonna;
    }// end of constructor not @Autowired


}// end of crud generic Dialog class