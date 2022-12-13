package it.algos.vaad24.backend.utility;

import com.vaadin.flow.component.button.*;
import com.vaadin.flow.component.dependency.*;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.orderedlayout.*;
import com.vaadin.flow.router.*;
import com.vaadin.flow.spring.annotation.*;
import it.algos.vaad24.backend.boot.*;
import it.algos.vaad24.ui.views.*;

import javax.annotation.*;

/**
 * Project vaad24
 * Created by Algos
 * User: gac
 * Date: Tue, 13-Dec-2022
 * Time: 11:36
 */
@SpringComponent
@Route(value = VaadCost.TAG_UTILITY, layout = MainLayout.class)
@CssImport("./styles/shared-styles.css")
public class UtilityView extends VerticalLayout {


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

        this.paragrafoReset();

        //--spazio per distanziare i paragrafi
        this.add(new H3());
    }

    public void titolo() {
        H1 titolo = new H1("Gestione utility");
        titolo.getElement().getStyle().set("color", "green");
        this.add(titolo);
    }

    public void paragrafoReset() {
        VerticalLayout layout = new VerticalLayout();
        layout.setMargin(false);
        layout.setPadding(false);
        layout.setSpacing(false);
        H3 paragrafo = new H3("Reset (ordinato) di tutte le collection che lo implementano");
        paragrafo.getElement().getStyle().set("color", "blue");

        layout.add(new Label("Esegue il reset ordinato su tutte le collection"));

        Button bottone = new Button("Reset all");
        bottone.getElement().setAttribute("theme", "primary");
        bottone.addClickListener(event -> esegueReset());

        this.add(paragrafo);
        layout.add(bottone);
        this.add(layout);
    }


    private void esegueReset() {
    }

}
