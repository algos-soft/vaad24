package it.algos.vaad24.backend.fields;

import com.vaadin.flow.component.customfield.*;
import com.vaadin.flow.component.textfield.*;
import com.vaadin.flow.spring.annotation.*;
import static it.algos.vaad24.backend.boot.VaadCost.*;
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
public class AListaFieldH extends CustomField<List> {

    private final TextField textField = new TextField();

    public AListaFieldH() {
        add(textField);
    } // end of SpringBoot constructor

    public AListaFieldH(String caption) {
        textField.setLabel(caption);
        add(textField);
    } // end of SpringBoot constructor


    @Override
    protected List generateModelValue() {
        return null;
    }

    @Override
    protected void setPresentationValue(List value) {
        StringBuffer buffer = new StringBuffer();
        String textValue;

        if (value != null) {
            for (Object obj : value) {
                buffer.append(obj.toString());
                buffer.append(VIRGOLA_SPAZIO);
            }
        }
        textValue = buffer.toString();
        textValue = textValue.trim();
        textValue = textValue.substring(0, textValue.length() - 1);
        textValue = QUADRA_INI + textValue + QUADRA_END;

        textField.setValue(textValue);
    }

}
