package it.algos.vaad24.ui.views;


import com.vaadin.flow.component.*;
import com.vaadin.flow.component.applayout.*;
import com.vaadin.flow.component.button.*;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.icon.*;
import com.vaadin.flow.component.orderedlayout.*;
import com.vaadin.flow.router.*;
import it.algos.vaad24.backend.boot.*;
import static it.algos.vaad24.backend.boot.VaadCost.*;
import it.algos.vaad24.backend.enumeration.*;
import it.algos.vaad24.backend.security.*;
import it.algos.vaad24.backend.service.*;
import it.algos.vaad24.ui.service.*;
import org.springframework.beans.factory.annotation.*;

import javax.annotation.*;
import java.util.*;

/**
 * The main view is a top-level placeholder for other views.
 */
public class MainLayout extends AppLayout {

    private final SecurityService securityService;

    /**
     * Istanza unica di una classe @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON) di servizio <br>
     * Iniettata automaticamente dal framework SpringBoot/Vaadin con l'Annotation @Autowired <br>
     * Disponibile DOPO il ciclo init() del costruttore di questa classe <br>
     */
    @Autowired
    private LayoutService layoutService;

    /**
     * Istanza unica di una classe @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON) di servizio <br>
     * Iniettata automaticamente dal framework SpringBoot/Vaadin con l'Annotation @Autowired <br>
     * Disponibile DOPO il ciclo init() del costruttore di questa classe <br>
     */
    @Autowired
    public TextService textService;

    @Autowired
    HtmlService htmlService;

    private H1 viewTitle;


    public MainLayout(SecurityService securityService) {
        this.securityService = securityService;
    }

    /**
     * Performing the initialization in a constructor is not suggested as the state of the UI is not properly set up when the constructor is invoked. <br>
     * La injection viene fatta da SpringBoot SOLO DOPO il metodo init() del costruttore <br>
     * Si usa quindi un metodo @PostConstruct per avere disponibili tutte le istanze @Autowired <br>
     * <p>
     * Ci possono essere diversi metodi con @PostConstruct e firme diverse e funzionano tutti, ma l'ordine con cui vengono chiamati (nella stessa classe) NON è garantito <br>
     * Se viene implementata una sottoclasse, passa di qui per ogni sottoclasse oltre che per questa istanza <br>
     * Se esistono delle sottoclassi, passa di qui per ognuna di esse (oltre a questa classe madre) <br>
     */
    @PostConstruct
    private void postConstruct() {
        setPrimarySection(Section.DRAWER);

        createHeader();
        createDrawer();
    }


    private void createHeader() {
        HorizontalLayout header;
        Button logout;
        DrawerToggle toggle = new DrawerToggle();
        toggle.addClassName("text-secondary");
        toggle.addThemeVariants(ButtonVariant.LUMO_CONTRAST);
        toggle.getElement().setAttribute("aria-label", "Menu toggle");

        // Placeholder for the title of the current view.
        // The title will be set after navigation.
        viewTitle = new H1();
        viewTitle.addClassNames("m-0", "text-l");

        if (VaadVar.usaSecurity) {
            logout = new Button("Log out", e -> securityService.logout());
            header = new HorizontalLayout(toggle, viewTitle, logout);
        }
        else {
            header = new HorizontalLayout(toggle, viewTitle);
        }

        // Configure styling for the header
        header.setId("header");
        header.getThemeList().set("dark", true);
        header.setWidthFull();
        header.setSpacing(false);
        header.setAlignItems(FlexComponent.Alignment.CENTER);

        header.addClassNames("bg-base", "border-b", "border-contrast-10", "box-border", "flex", "h-xl", "items-center", "w-full");
        header.setDefaultVerticalComponentAlignment(FlexComponent.Alignment.CENTER);
        header.expand(viewTitle);

        addToNavbar(true, header);
    }

    private void createDrawer() {
        H2 vaadAppName = new H2(textService.primaMaiuscola(VaadVar.frameworkVaadin24));
        vaadAppName.addClassNames("flex", "items-center", "h-xl", "m-0", "px-m", "text-m");
        H2 currentAppName = new H2(textService.primaMaiuscola(VaadVar.projectCurrent));
        currentAppName.addClassNames("flex", "items-center", "h-xl", "m-0", "px-m", "text-m");

        com.vaadin.flow.component.html.Section section = new com.vaadin.flow.component.html.Section(vaadAppName, createNavigationVaadin(), currentAppName, createNavigationProject(), createFooter());
        section.addClassNames("flex", "flex-col", "items-stretch", "max-h-full", "min-h-full");

        addToDrawer(section);
    }

    private Nav createNavigationVaadin() {
        Nav nav = new Nav();
        nav.addClassNames("border-b", "border-contrast-10", "flex-grow", "overflow-auto");
        nav.getElement().setAttribute("aria-labelledby", "views");

        // Wrap the links in a list; improves accessibility
        UnorderedList list = new UnorderedList();
        list.addClassNames("list-none", "m-0", "p-0");
        nav.add(list);
        list.add(layoutService.getMenuItemsVaadin());

        return nav;
    }

    private Nav createNavigationProject() {
        List<Component> lista = layoutService.getMenuItemsProject();
        Nav nav = new Nav();
        nav.addClassNames("border-b", "border-contrast-10", "flex-grow", "overflow-auto");
        nav.getElement().setAttribute("aria-labelledby", "views");

        // Wrap the links in a list; improves accessibility
        UnorderedList list = new UnorderedList();
        list.addClassNames("list-none", "m-0", "p-0");
        nav.add(list);
        if (lista != null) {
            list.add(layoutService.getMenuItemsProject());
        }

        return nav;
    }


    private Footer createFooter() {
        Footer layout = new Footer();
        layout.addClassNames("flex", "items-center", "my-s", "px-m", "py-xs");

        layout.add(layoutService.bottomAlgos());

        return layout;
    }


    @Override
    protected void afterNavigation() {
        super.afterNavigation();

        viewTitle.removeAll();

        Icon icona = VaadinIcon.BUG.create();
        icona.setColor(COLOR_DEBUG);

        if (icona != null && Pref.debug.is()) {
            viewTitle.add(new Span(getCurrentPageTitle()), new Span(TAB_SPAZIO), icona);
        }
        else {
            viewTitle.add(new Span(getCurrentPageTitle()));
        }
    }

    private String getCurrentPageTitle() {
        PageTitle title = getContent().getClass().getAnnotation(PageTitle.class);
        return title == null ? "" : title.value();
    }

}
