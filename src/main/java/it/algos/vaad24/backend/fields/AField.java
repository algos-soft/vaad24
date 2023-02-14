package it.algos.vaad24.backend.fields;

import com.vaadin.flow.component.*;
import com.vaadin.flow.component.customfield.*;

/**
 * Project vaad24
 * Created by Algos
 * User: gac
 * Date: Mon, 13-Feb-2023
 * Time: 20:03
 */
public abstract class AField<O> extends CustomField implements HasValue {

    @Override
    protected Object generateModelValue() {
        return null;
    }

    @Override
    protected void setPresentationValue(Object o) {

    }

}
