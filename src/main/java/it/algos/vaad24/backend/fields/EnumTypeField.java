package it.algos.vaad24.backend.fields;

import com.vaadin.flow.component.html.*;
import com.vaadin.flow.shared.*;
import com.vaadin.flow.spring.annotation.*;
import org.springframework.beans.factory.config.*;
import org.springframework.context.annotation.Scope;

/**
 * Project vaad24
 * Created by Algos
 * User: gac
 * Date: Mon, 13-Feb-2023
 * Time: 20:07
 */

@SpringComponent
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class EnumTypeField extends AField {


    public EnumTypeField() {
        add(new Span("Forse"));
    }
    @Override
    public Registration addValueChangeListener(ValueChangeListener valueChangeListener) {
        return null;
    }

    @Override
    protected Object generateModelValue() {
        return super.generateModelValue();
    }

    @Override
    protected void setPresentationValue(Object obj) {
        super.setPresentationValue(obj);
    }

}
