package it.algos.vaad24.backend.packages.crono.mese;

import com.vaadin.flow.router.*;
import static it.algos.vaad24.backend.boot.VaadCost.*;
import it.algos.vaad24.ui.dialog.*;
import it.algos.vaad24.ui.views.*;
import org.springframework.beans.factory.annotation.*;

import java.util.*;

/**
 * Project vaadin23
 * Created by Algos
 * User: gac
 * Date: dom, 01-mag-2022
 * Time: 08:51
 */
@PageTitle("Mesi")
@Route(value = TAG_MESE, layout = MainLayout.class)
public class MeseView extends CrudView {




    /**
     * Costruttore @Autowired (facoltativo) <br>
     * In the newest Spring release, it’s constructor does not need to be annotated with @Autowired annotation <br>
     * Inietta con @Autowired il service con la logica specifica e lo passa al costruttore della superclasse <br>
     * Regola la entityClazz (final nella superclasse) associata a questa @Route view e la passa alla superclasse <br>
     *
     * @param crudBackend service specifico per la businessLogic e il collegamento con la persistenza dei dati
     */
    public MeseView(@Autowired final MeseBackend crudBackend) {
        super(crudBackend, Mese.class);
    }


    /**
     * Preferenze usate da questa 'view' <br>
     * Primo metodo chiamato dopo init() (implicito del costruttore) e postConstruct() (facoltativo) <br>
     * Puo essere sovrascritto, invocando PRIMA il metodo della superclasse <br>
     */
    @Override
    protected void fixPreferenze() {
        super.fixPreferenze();

        super.gridPropertyNamesList = Arrays.asList("ordine","giorni", "breve", "nome", "primo", "ultimo");
        super.formPropertyNamesList = Arrays.asList("ordine","giorni", "breve", "nome", "primo", "ultimo");

        super.usaDataProvider = true;
        super.usaBottoneReset = true;
        super.usaReset = true;
        super.usaBottoneNew = false;
        super.usaBottoneEdit = false;
    }

    /**
     * Costruisce un (eventuale) layout per informazioni aggiuntive come header della view <br>
     * Può essere sovrascritto, invocando PRIMA il metodo della superclasse <br>
     */
    @Override
    public void fixAlert() {
        super.fixAlert();

        addSpan(ASpan.text(String.format("%s%s", TEXT_CSV, "mesi")).verde());
        addSpan(ASpan.text(TEXT_HARD).rosso());
        addSpan(ASpan.text("Ordinati di default per 'ordine'. Ordinabili anche per 'nome'.").rosso());
        addSpan(ASpan.text(TEXT_RESET).rosso());
    }

}// end of crud @Route view class