package it.algos.vaad24.backend.fields;

import com.vaadin.flow.component.customfield.*;
import com.vaadin.flow.component.orderedlayout.*;
import com.vaadin.flow.spring.annotation.*;
import static it.algos.vaad24.backend.boot.VaadCost.*;
import it.algos.vaad24.ui.dialog.*;
import org.springframework.beans.factory.config.*;
import org.springframework.context.annotation.Scope;

import java.util.*;

/**
 * Project vaad24
 * Created by Algos
 * User: gac
 * Date: Fri, 07-Apr-2023
 * Time: 15:47
 */
@SpringComponent
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class AListaFieldV extends CustomField<List> {

    private final VerticalLayout placeHolder = new VerticalLayout();

    private String caption;

    public AListaFieldV() {
    } // end of SpringBoot constructor

    public AListaFieldV(String caption) {
        this.caption = caption;
        this.placeHolder.setPadding(false);
        this.placeHolder.setSpacing(false);
        this.placeHolder.setMargin(false);
        this.add(placeHolder);
    } // end of SpringBoot constructor


    @Override
    protected List generateModelValue() {
        return null;
    }

    @Override
    protected void setPresentationValue(List listaValori) {
        String message = VUOTA;
        ASpan span;

        span = ASpan.text(SPAZIO_NON_BREAKING);
        placeHolder.add(span);
        span = ASpan.text(caption).blue();
        placeHolder.add(span);

        for (Object obj : listaValori) {
            message = obj.toString();
            span = ASpan.text(message).verde();
            placeHolder.add(span);
        }
    }

}
