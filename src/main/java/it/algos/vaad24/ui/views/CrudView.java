package it.algos.vaad24.ui.views;

import ch.carnet.kasparscherrer.*;
import com.vaadin.flow.component.*;
import com.vaadin.flow.component.button.*;
import com.vaadin.flow.component.combobox.*;
import com.vaadin.flow.component.grid.*;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.icon.*;
import com.vaadin.flow.component.orderedlayout.*;
import com.vaadin.flow.component.textfield.*;
import com.vaadin.flow.data.provider.*;
import com.vaadin.flow.data.renderer.*;
import com.vaadin.flow.data.selection.*;
import com.vaadin.flow.router.*;
import static it.algos.vaad24.backend.boot.VaadCost.*;
import it.algos.vaad24.backend.entity.*;
import it.algos.vaad24.backend.enumeration.*;
import it.algos.vaad24.backend.exception.*;
import it.algos.vaad24.backend.logic.*;
import it.algos.vaad24.backend.service.*;
import it.algos.vaad24.backend.wrapper.*;
import it.algos.vaad24.ui.dialog.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.context.*;
import org.springframework.core.env.*;
import org.springframework.data.domain.*;
import org.vaadin.crudui.crud.*;

import java.lang.reflect.*;
import java.util.*;
import java.util.function.*;
import java.util.stream.*;

/**
 * Project vaadin23
 * Created by Algos
 * User: gac
 * Date: ven, 01-apr-2022
 * Time: 06:41
 * Vista iniziale e principale di un package <br>
 * Superclasse astratta <br>
 * Presenta la Grid <br>
 * Su richiesta apre un Dialogo per gestire la singola entity <br>
 * La sottoclasse concreta usa @Route e viene chiamata dal menu generale <br>
 */
public abstract class CrudView extends VerticalLayout implements AfterNavigationObserver {

    protected static String TEXT_CSV = "Usati solo in background. File originale (CSV) sul server /www.algos.it/vaadin23/config/";

    protected static String TEXT_BACK = "Usati solo in background. Costruiti hardcoded.";

    protected static String TEXT_HARD = "Solo hard coded. Non creabili e non modificabili.";

    protected static String TEXT_RESET = "Cancellabili (dalla lista per prova). Reset ripristina la collezione.";


    /**
     * Istanza unica di una classe @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON) di servizio <br>
     * Iniettata automaticamente dal framework SpringBoot/Vaadin con l'Annotation @Autowired <br>
     * Disponibile DOPO il ciclo init() del costruttore di questa classe <br>
     */
    @Autowired
    public Environment environment;

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
    public DateService dateService;

    /**
     * Istanza unica di una classe @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON) di servizio <br>
     * Iniettata automaticamente dal framework SpringBoot/Vaadin con l'Annotation @Autowired <br>
     * Disponibile DOPO il ciclo init() del costruttore di questa classe <br>
     */
    @Autowired
    public LogService logger;

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
    public ColumnService columnService;

    protected CrudBackend crudBackend;

    protected CrudDialog crudDialog;

    protected Class entityClazz;

    protected VerticalLayout alertPlaceHolder;

    protected HorizontalLayout topPlaceHolder;

    protected VerticalLayout bottomPlaceHolder;

    protected int browserWidth;

    protected boolean cancellaColonnaKeyId;

    protected boolean autoCreateColumns;

    protected boolean usaDataProvider;

    protected boolean usaRowIndex;

    protected boolean riordinaColonne;

    protected List<String> gridPropertyNamesList;

    protected List<String> formPropertyNamesList;

    //    protected boolean usaBottoneRefresh;

    //    protected Button buttonRefresh;

    protected boolean usaBottoneDeleteAll;

    protected Button buttonDeleteAll;

    protected boolean usaBottoneReset;

    protected Button buttonReset;


    protected boolean usaBottoneSearch;

    protected TextField searchField;

    protected String searchFieldName;


    protected boolean usaReset;

    protected boolean usaBottoneNew;

    protected Button buttonNew;

    protected boolean usaBottoneEdit;

    protected Button buttonEdit;

    protected boolean usaBottoneDeleteEntity;

    protected Button buttonDeleteEntity;

    protected boolean usaBottoneExport;

    protected Button buttonExport;

    protected boolean usaComboType;

    protected boolean usaBottomTotale;

    protected boolean usaBottomInfo;

    /**
     * Flag di preferenza per la classe di dialogo. Di default CrudDialog. <br>
     */
    protected Class<?> dialogClazz = CrudDialogBase.class;

    protected CrudDialog dialog;

    protected Grid<AEntity> grid;

    protected Sort sortOrder;

    protected ComboBox<AETypeLog> comboTypeLog;

    protected IndeterminateCheckbox boxBox;

    protected int elementiFiltrati;

    protected String message;

    protected boolean usaSingleClick;

    protected boolean usaDoubleClick;

    private Function<String, Grid.Column<AEntity>> getColonna = name -> grid.getColumnByKey(name);

    protected Runnable confermaHandler;

    public CrudView(final CrudBackend crudBackend, final Class entityClazz) {
        this.crudBackend = crudBackend;
        this.entityClazz = entityClazz;
    }

    /**
     * Qui va tutta la logica della view <br>
     * Invocato da SpringBoot dopo il metodo init() del costruttore <br>
     * Sono disponibili tutte le istanze @Autowired <br>
     * Può essere sovrascritto, invocando PRIMA il metodo della superclasse <br>
     */
    @Override
    public void afterNavigation(AfterNavigationEvent beforeEnterEvent) {
        //--Layout generale della view <br>
        this.fixGeneralLayout();

        //--Preferenze usate da questa 'logica'
        this.fixPreferenze();

        //--Costruisce un (eventuale) layout per informazioni aggiuntive come header della view <br>
        this.fixAlert();

        //--Costruisce un layout (obbligatorio per la List) per i bottoni di comando della view al Top <br>
        //--Eventualmente i bottoni potrebbero andare su due righe <br>
        this.fixTopLayout();

        //--Corpo principale della Grid/Form (obbligatorio) <br>
        this.fixBodyLayout();

        //--Aggiunge i listener ai vari oggetti <br>
        this.fixListener();

        //--Costruisce un layout per gli avvisi in calce alla pagina <br>
        this.fixBottomLayout();
    }

    /**
     * Costruisce il layout generale della view <br>
     * Metodo chiamato da CrudView.afterNavigation() <br>
     * Costruisce tutti i componenti in metodi che possono essere sovrascritti <br>
     * Non può essere sovrascritto <br>
     */
    protected void fixGeneralLayout() {
        this.setPadding(true);
        this.setSpacing(false);
        this.setMargin(false);
    }

    /**
     * Preferenze usate da questa 'logica' <br>
     * Metodo chiamato da CrudView.afterNavigation() <br>
     * Primo metodo chiamato dopo init() (implicito del costruttore) e postConstruct() (facoltativo) <br>
     * Puo essere sovrascritto, invocando PRIMA il metodo della superclasse <br>
     */
    protected void fixPreferenze() {
        //--Larghezza del browser utilizzato in questa sessione <br>
        UI.getCurrent().getPage().retrieveExtendedClientDetails(details -> browserWidth = details.getBodyClientWidth());

        sortOrder = crudBackend.getSortOrder();
        sortOrder = sortOrder != null ? sortOrder : Sort.by(Sort.Direction.ASC, FIELD_NAME_ID_SENZA);
        usaRowIndex = true;
        usaDataProvider = false;
        riordinaColonne = true;
        gridPropertyNamesList = new ArrayList<>();
        formPropertyNamesList = new ArrayList<>();
        cancellaColonnaKeyId = true;
        autoCreateColumns = true;

        //        usaBottoneRefresh = false;
        usaBottoneDeleteAll = false;
        usaBottoneReset = false;
        usaReset = false;
        usaBottoneNew = true;
        usaBottoneEdit = true;
        usaBottoneSearch = true;
        usaBottoneDeleteEntity = true;
        usaBottoneExport = false;
        usaComboType = false;

        usaBottomTotale = true;
        usaBottomInfo = true;
        usaSingleClick = true;
        usaDoubleClick = true;
    }

    /**
     * Metodo chiamato da CrudView.afterNavigation() <br>
     * Costruisce un (eventuale) layout per informazioni aggiuntive come header della view <br>
     * Può essere sovrascritto, invocando PRIMA il metodo della superclasse <br>
     */
    public void fixAlert() {
        this.alertPlaceHolder = new VerticalLayout();
        this.alertPlaceHolder.setPadding(false);
        this.alertPlaceHolder.setSpacing(false);
        this.alertPlaceHolder.setMargin(false);
        this.add(alertPlaceHolder);
    }

    public Button fixButton(Button button) {
        button.getStyle().set("color", "blue");
        button.getStyle().set("font-weight", "bold");
        button.getStyle().set("margin-left", "0");
        button.getStyle().set("margin-top", "0");
        button.getStyle().set("margin-bottom", "0");
        button.getStyle().set("background-color", "transparent");
        button.setHeight("1.5rem");

        return button;
    }

    /**
     * Costruisce un layout per i componenti al Top della view <br>
     * I componenti possono essere (nell'ordine):
     * Bottoni standard (solo icone) Reset, New, Edit, Delete, ecc.. <br>
     * SearchField per il filtro testuale di ricerca <br>
     * ComboBox e CheckBox di filtro <br>
     * Bottoni specifici non standard <br>
     * <p>
     * Metodo chiamato da CrudView.afterNavigation() <br>
     * Costruisce tutti i componenti in metodi che possono essere sovrascritti <br>
     * Inserisce la istanza in topPlaceHolder della view <br>
     * Aggiunge tutti i listeners dei bottoni, searchField, comboBox, checkBox <br>
     * <p>
     * Non può essere sovrascritto <br>
     */
    protected void fixTopLayout() {
        this.topPlaceHolder = new HorizontalLayout();
        topPlaceHolder.setClassName("buttons");
        topPlaceHolder.setPadding(false);
        topPlaceHolder.setSpacing(true);
        topPlaceHolder.setMargin(false);
        topPlaceHolder.setClassName("confirm-dialog-buttons");

        this.fixBottoniTopStandard();
        this.fixFiltri();
        this.fixBottoniTopSpecifici();
        this.add(topPlaceHolder);

        //--spazio prima della grid
        Label emptyLabel = new Label(VUOTA);
        emptyLabel.setHeight("0.1em");
        this.add(emptyLabel);
    }

    /**
     * Bottoni standard (solo icone) Reset, New, Edit, Delete, ecc.. <br>
     * Può essere sovrascritto, invocando PRIMA il metodo della superclasse <br>
     */
    protected void fixBottoniTopStandard() {
        //        if (usaBottoneRefresh) {
        //            buttonRefresh = new Button();
        //            buttonRefresh.getElement().setAttribute("theme", "secondary");
        //            buttonRefresh.getElement().setProperty("title", "Refresh: ricarica dal database i valori della finestra");
        //            buttonRefresh.setIcon(new Icon(VaadinIcon.REFRESH));
        //            buttonRefresh.addClickListener(event -> refresh());
        //            topPlaceHolder.add(buttonRefresh);
        //        }

        if (usaBottoneDeleteAll) {
            buttonDeleteAll = new Button();
            buttonDeleteAll.getElement().setAttribute("theme", "primary");
            buttonDeleteAll.addThemeVariants(ButtonVariant.LUMO_ERROR);
            buttonDeleteAll.getElement().setProperty("title", "Delete: cancella completamente tutta la collezione");
            buttonDeleteAll.setIcon(new Icon(VaadinIcon.TRASH));
            buttonDeleteAll.addClickListener(event -> ADelete.deleteAll(this::deleteAll));
            topPlaceHolder.add(buttonDeleteAll);
        }

        if (usaBottoneReset && buttonReset == null) {
            buttonReset = new Button();
            buttonReset.getElement().setAttribute("theme", "primary");
            buttonReset.getElement().setProperty("title", "Reset: ripristina nel database i valori di default annullando le " +
                    "eventuali modifiche apportate successivamente\nShortcut SHIFT+R");
            buttonReset.setIcon(new Icon(VaadinIcon.REFRESH));
            buttonReset.addClickListener(event -> AReset.reset(this::resetForcing));
            buttonReset.addClickShortcut(Key.KEY_R, KeyModifier.SHIFT);
            topPlaceHolder.add(buttonReset);
        }

        if (usaBottoneNew) {
            buttonNew = new Button();
            buttonNew.getElement().setAttribute("theme", "primary");
            buttonNew.getElement().setProperty("title", "Add: aggiunge un elemento alla collezione\nShortcut SHIFT+N");
            buttonNew.setIcon(new Icon(VaadinIcon.PLUS));
            buttonNew.setEnabled(true);
            buttonNew.addClickListener(event -> newItem());
            buttonNew.addClickShortcut(Key.KEY_N, KeyModifier.SHIFT);
            topPlaceHolder.add(buttonNew);
        }

        if (usaBottoneEdit) {
            buttonEdit = new Button();
            buttonEdit.getElement().setAttribute("theme", "primary");
            buttonEdit.getElement().setProperty("title", "Update: modifica l'elemento selezionato\nShortcut SHIFT+E");
            buttonEdit.setIcon(new Icon(VaadinIcon.PENCIL));
            buttonEdit.setEnabled(false);
            buttonEdit.addClickListener(event -> updateItem());
            buttonEdit.addClickShortcut(Key.KEY_E, KeyModifier.SHIFT);
            topPlaceHolder.add(buttonEdit);
        }

        if (usaBottoneDeleteEntity) {
            buttonDeleteEntity = new Button();
            buttonDeleteEntity.getElement().setAttribute("theme", "primary");
            buttonDeleteEntity.addThemeVariants(ButtonVariant.LUMO_ERROR);
            buttonDeleteEntity.getElement().setProperty("title", "Delete: cancella l'elemento selezionato\nShortcut SHIFT+D");
            buttonDeleteEntity.setIcon(new Icon(VaadinIcon.CLOSE));
            buttonDeleteEntity.setEnabled(false);
            buttonDeleteEntity.addClickListener(event -> deleteItem());
            buttonDeleteEntity.addClickShortcut(Key.KEY_D, KeyModifier.SHIFT);
            topPlaceHolder.add(buttonDeleteEntity);
        }

        if (usaBottoneSearch && annotationService.usaSearchPropertyName(entityClazz)) {
            searchFieldName = annotationService.getSearchPropertyName(entityClazz);
            searchField = new TextField();
            searchField.setPlaceholder(TAG_ALTRE_BY + searchFieldName);
            searchField.setWidth(WIDTH_EM);
            searchField.setClearButtonVisible(true);
            searchField.addValueChangeListener(event -> sincroFiltri());
            topPlaceHolder.add(searchField);
        }

    }

    protected void fixFiltri() {
    }

    /**
     * Componenti aggiuntivi oltre quelli base <br>
     * Tipicamente bottoni di selezione/filtro <br>
     * Può essere sovrascritto, invocando PRIMA il metodo della superclasse <br>
     */
    protected void fixBottoniTopSpecifici() {
        if (usaComboType) {
            comboTypeLog = new ComboBox<>();
            comboTypeLog.setPlaceholder("Type");
            comboTypeLog.getElement().setProperty("title", "Filtro di selezione");
            comboTypeLog.setClearButtonVisible(true);
            comboTypeLog.setItems(AETypeLog.getAllEnums());
            comboTypeLog.addValueChangeListener(event -> sincroFiltri());
            topPlaceHolder.add(comboTypeLog);
        }
    }

    /**
     * Costruisce il corpo principale (obbligatorio) della Grid <br>
     * <p>
     * Metodo chiamato da CrudView.afterNavigation() <br>
     * Costruisce un' istanza dedicata con la Grid <br>
     */
    protected void fixBodyLayout() {
        // Create a listing component for a bean type
        this.fixNomiColonneFields();

        grid = new Grid(entityClazz, autoCreateColumns);

        // Crea/regola le colonne
        this.fixAutoNumbering();
        if (autoCreateColumns) {
            this.fixColumnsAutomaticallyCreated();
        }
        else {
            this.addColumnsOneByOne();
        }
        //        this.fixSearch();

        // Pass all objects to a grid from a Spring Data repository object
        this.fixItems();

        // The row-stripes theme produces a background color for every other row.
        grid.addThemeVariants(GridVariant.LUMO_ROW_STRIPES);

        // switch to single select mode
        grid.setSelectionMode(Grid.SelectionMode.SINGLE);

        // sincronizzazione delle righe
        grid.addSelectionListener(event -> sincroSelection(event));
        if (!usaSingleClick) {
            grid.setSelectionMode(Grid.SelectionMode.NONE);
        }

        // layout configuration
        setSizeFull();
        this.add(grid);

        if (!usaDataProvider) {
            sincroFiltri();
        }

    }


    /**
     * Chiamato PRIMA di creare la Grid <br>
     * Controlla la validità della lista gridPropertyNamesList <br>
     * In quanto usata/creata da una sottoclasse specifica <br>
     * Se è vuota, regola a true la variabile autoCreateColumns <br>
     * Se è vuota, la crea con tutti i fields della classe <br>
     * //     * Aggiunge la colonna di ordinamento, secondo il parametro usaRowIndex <br>
     * Rimuove la colonna della chiave keyId, secondo il parametro cancellaColonnaKeyId <br>
     */
    protected void fixNomiColonneFields() {
        if (gridPropertyNamesList.size() < 1) {
            autoCreateColumns = true;
            List<Field> lista = reflectionService.getClassOnlyDeclaredFieldsDB(entityClazz);
            for (Field field : lista) {
                gridPropertyNamesList.add(field.getName());
            }
        }
        else {
            autoCreateColumns = false;
        }

        if (formPropertyNamesList.size() < 1) {
            List<Field> lista = reflectionService.getClassOnlyFormFields(entityClazz);
            for (Field field : lista) {
                formPropertyNamesList.add(field.getName());
            }
        }
    }

    protected void fixItems() {
        DataProvider provider = null;
        List items;

        if (usaDataProvider) {
            provider = crudBackend.getProvider();
            if (provider != null) {
                grid.setDataProvider(provider);
            }
        }

        if (provider == null) {
            items = crudBackend.findAllSort(sortOrder);
            if (items != null) {
                grid.setItems(items);
            }
        }
    }


    protected void fixAutoNumbering() {
        String message;

        if (usaRowIndex && reflectionService.isEsiste(entityClazz, FIELD_KEY_ORDER)) {
            grid.addColumn(LitRenderer.of("${index + 1}")).setHeader(FIELD_KEY_ORDER).setKey(FIELD_KEY_ORDER).setWidth(getNumberingWidth()).setFlexGrow(0);
            if (gridPropertyNamesList.size() > 0) {
                try {
                    gridPropertyNamesList.add(0, FIELD_KEY_ORDER);
                } catch (Exception unErrore) {
                    message = String.format("%s - Aggiunta della property '%s' alla Grid della classe [%s]", unErrore.toString(), FIELD_KEY_ORDER, this.getClass().getSimpleName());
                    logger.error(new WrapLog().type(AETypeLog.flow).exception(new AlgosException(message)).usaDb());
                }
            }
        }
    }

    /**
     * autoCreateColumns=true <br>
     * Crea le colonne normali indicate in this.colonne <br>
     * Elimina la colonna 'keyID' -> 'id' se costruita in automatico con autoCreateColumns=true <br>
     * Le colonne costruite in automatico sono senza ordine garantito <br>
     * Riordina le colonne secondo una lista prestabilita <br>
     * Può essere sovrascritto, invocando PRIMA il metodo della superclasse <br>
     */
    protected void fixColumnsAutomaticallyCreated() {
        //        grid.addColumn(item -> VUOTA).setKey("rowIndex");
        if (cancellaColonnaKeyId) {
            try {
                grid.removeColumnByKey(FIELD_NAME_ID_SENZA);
                gridPropertyNamesList.remove(FIELD_NAME_ID_SENZA);
            } catch (Exception unErrore) {
                logger.error(new WrapLog().exception(unErrore).usaDb().message("Non ho indicato correttamente la colonna 'id' "));
                return;
            }
        }

        //--cambia solo l'ordine di presentazione delle colonne. Ha senso solo se sono state costruite in automatico.
        //--tutte le caratteristiche delle colonne create in automatico rimangono immutate
        //--se servono caratteristiche particolari per una colonna o va creata manualmente o va recuperata e modificata
        List alfa = grid.getColumns();
        if (riordinaColonne && gridPropertyNamesList.size() > 0) {
            try {
                grid.setColumnOrder(gridPropertyNamesList.stream().map(getColonna).collect(Collectors.toList()));
            } catch (Exception unErrore) {
                logger.error(new WrapLog().exception(unErrore).usaDb());
            }
        }
    }


    /**
     * autoCreateColumns=false <br>
     * Crea le colonne normali indicate in this.colonne <br>
     * Può essere sovrascritto, invocando PRIMA il metodo della superclasse <br>
     */
    protected void addColumnsOneByOne() {
        columnService.addColumnsOneByOne(grid, entityClazz, gridPropertyNamesList);
    }


    /**
     * Aggiunge alcuni listeners alla Grid <br>
     * Aggiunge alcuni listeners eventualmente non aggiunti ai bottoni, comboBox <br>
     * <p>
     * Metodo chiamato da CrudView.afterNavigation() <br>
     * Può essere sovrascritto, invocando PRIMA il metodo della superclasse <br>
     */
    protected void fixListener() {
        if (usaDoubleClick) {
            grid.addItemDoubleClickListener(listener -> updateItem(listener.getItem()));
        }
    }

    protected void fixBottomLayout() {
        this.bottomPlaceHolder = new VerticalLayout();
        this.bottomPlaceHolder.setPadding(false);
        this.bottomPlaceHolder.setSpacing(false);
        this.bottomPlaceHolder.setMargin(false);

        sicroBottomLayout();
        this.add(bottomPlaceHolder);
    }

    protected void sicroBottomLayout() {
        String view = textService.primaMaiuscola(entityClazz.getSimpleName());
        int elementiTotali = crudBackend.count();
        String totaleTxt = textService.format(elementiTotali);
        String filtratiTxtTxt = textService.format(elementiFiltrati);

        if (elementiFiltrati == 0 || elementiFiltrati == elementiTotali) {
            message = String.format("%s: in totale ci sono %s elementi", view, totaleTxt);
        }
        else {
            message = String.format("%s: filtrati %s elementi sul totale di %s", view, filtratiTxtTxt, totaleTxt);
        }

        if (bottomPlaceHolder != null) {
            bottomPlaceHolder.removeAll();
            if (usaBottomTotale) {
                bottomPlaceHolder.add(htmlService.getSpan(new WrapSpan(message).color(AETypeColor.verde).weight(AEFontWeight.bold).fontHeight(AEFontSize.em7)));
            }
        }
    }

    protected List<AEntity> sincroFiltri() {
        DataProvider provider;
        List<AEntity> items = crudBackend.findAllSort(sortOrder);

        //@todo da sistemare
        if (usaBottoneSearch && searchField != null) {
            final String textSearch = searchField != null ? searchField.getValue() : VUOTA;
            if (textService.isValid(searchFieldName) && textService.isValid(textSearch)) {
                items = items
                        .stream()
                        .filter(bean -> ((String) reflectionService.getPropertyValue(bean, searchFieldName)).matches("^(?i)" + textSearch + ".*$"))
                        .toList();
            }
        }

        if (items != null) {
            grid.setItems((List) items);
            elementiFiltrati = items.size();
            sicroBottomLayout();
        }

        return items;
    }

    protected boolean sincroSelection(SelectionEvent event) {
        boolean singoloSelezionato = event.getAllSelectedItems().size() == 1;

        if (buttonDeleteAll != null) {
            buttonDeleteAll.setEnabled(!singoloSelezionato);
        }
        if (buttonReset != null) {
            buttonReset.setEnabled(!singoloSelezionato);
        }
        if (buttonNew != null) {
            buttonNew.setEnabled(!singoloSelezionato);
        }
        if (buttonEdit != null) {
            buttonEdit.setEnabled(singoloSelezionato);
        }
        if (buttonDeleteEntity != null) {
            buttonDeleteEntity.setEnabled(singoloSelezionato);
        }

        return singoloSelezionato;
    }

    /**
     * Ricarica interamente la pagina del browser (non solo la Grid) <br>
     */
    protected void reload() {
        UI.getCurrent().getPage().reload();
    }

    protected void refresh() {
        grid.setItems(crudBackend.findAllSort(sortOrder));
    }


    protected void deleteAll() {
        int totaleEsistente = crudBackend.count();
        if (totaleEsistente == 0) {
            Avviso.message("Non ci sono entities da cancellare").primary().open();
            return;
        }

        if (crudBackend.deleteAll()) {
            grid.setItems(crudBackend.findAllSort(sortOrder));
            Avviso.message("Delete all").success().open();
        }
        else {
            Avviso.message("Non sono riuscito a cancellare la collection").error().durata(6).open();
        }
    }


    protected void resetForcing() {
        AResult result = crudBackend.resetForcing();

        if (result.isValido()) {
            grid.setItems(crudBackend.findAllSort(sortOrder));
            Avviso.message("Eseguito reset completo").success().open();
            reload();
        }
        else {
            Avviso.message(result.getErrorMessage()).error().durata(6).open();
        }
    }


    /**
     * Apre un dialogo di creazione <br>
     * Proveniente da un click sul bottone New della Top Bar <br>
     * Sempre attivo <br>
     * Passa al dialogo gli handler per annullare e creare <br>
     */
    public void newItem() {
        dialog = (CrudDialog) appContext.getBean(dialogClazz, crudBackend.newEntity(), CrudOperation.ADD, crudBackend, formPropertyNamesList);
        dialog.open(this::saveHandler, this::annullaHandler);
    }

    /**
     * Apre un dialogo di editing <br>
     * Proveniente da un click sul bottone Edit della Top Bar <br>
     * Attivo solo se è selezionata una e una sola riga <br>
     * Passa al dialogo gli handler per annullare e modificare <br>
     */
    public void updateItem() {
        Optional entityBean = grid.getSelectedItems().stream().findFirst();
        if (entityBean.isPresent()) {
            updateItem((AEntity) entityBean.get());
        }
    }

    /**
     * Apre un dialogo di editing <br>
     * Proveniente da un doppio click su una riga della Grid <br>
     * Passa al dialogo gli handler per annullare e modificare <br>
     *
     * @param entityBeanDaRegistrare (nuova o esistente)
     */
    public void updateItem(AEntity entityBeanDaRegistrare) {
        CrudOperation operation;

        if (usaBottoneEdit) {
            if (usaBottoneDeleteEntity) {
                operation = CrudOperation.DELETE;
            }
            else {
                operation = CrudOperation.UPDATE;
            }
        }
        else {
            operation = CrudOperation.READ;
        }
        dialog = (CrudDialog) appContext.getBean(dialogClazz, entityBeanDaRegistrare, operation, crudBackend, formPropertyNamesList);
        dialog.open(this::saveHandler, this::deleteHandler, this::annullaHandler);
    }

    /**
     * Apre un dialogo di cancellazione<br>
     * Proveniente da un click sul bottone Delete della Top Bar <br>
     * Attivo solo se è selezionata una e una sola riga <br>
     * Passa al dialogo gli handler per annullare e cancellare <br>
     */
    public void deleteItem() {
        Optional<AEntity> entityBean = grid.getSelectedItems().stream().findFirst();
        if (entityBean.isPresent()) {
            ADelete.delete(entityBean.toString(), this::deleteHandler);
        }
    }


    /**
     * Primo ingresso dopo il click sul bottone del dialogo <br>
     */
    protected void saveHandler(final AEntity entityBean, final CrudOperation operation) {
        grid.setItems(crudBackend.findAllSort(sortOrder));
    }


    public void deleteHandler() {
        Optional<AEntity> entityBean = grid.getSelectedItems().stream().findFirst();
        if (entityBean.isPresent()) {
            deleteHandler(entityBean.get());
        }
        else {
            Avviso.message("Nessuna entity selezionata").error().open();
        }
    }

    /**
     * Questo handler viene chiamato sia dal bottone della View che come ritorno dal Dialogo <br>
     * Forza comunque la cancellazione, anche se è stata effettuata dal Dialogo (nel caso di Dialogo) <br>
     * Esegue comunque sempre il reload della View <br>
     */
    protected void deleteHandler(final AEntity entityBean) {
        boolean cancellato;

        cancellato = crudBackend.delete(entityBean);
        if (!cancellato) {
            //Avviso interno -> Già cancellato (probabilmente nel dialogo)
        }

        Avviso.message(String.format("%s successfully deleted", entityBean)).success().open();
        //@todo Non riesco a fermare l'esecuzione PRIMA del reload della pagina per poter far vedere l'avviso
        reload();
    }

    public void annullaHandler(final AEntity entityBean) {
    }


    /**
     * Larghezza della colonna di numerazione automatica in funzione della dimensione della collezione <br>
     * Larghezza aggiustata al massimo valore numerico <br>
     */
    protected String getNumberingWidth() {
        String indexWidth = VUOTA;
        int dim = 0;
        int dim1 = 100;
        int dim2 = 1000;
        String tag1 = "3.0" + TAG_EM;
        String tag2 = "4.0" + TAG_EM;
        String tag3 = "5.0" + TAG_EM;

        try {
            dim = crudBackend.count();
        } catch (Exception unErrore) {
            logger.error(new WrapLog().exception(unErrore).usaDb());
        }

        if (dim < dim1) {
            indexWidth = tag1;
        }
        else {
            if (dim < dim2) {
                indexWidth = tag2;
            }
            else {
                indexWidth = tag3;
            }
        }

        return indexWidth;
    }


    public void addSpan(ASpan span) {
        alertPlaceHolder.add(span);
    }

}// end of crud abstract @Route view class