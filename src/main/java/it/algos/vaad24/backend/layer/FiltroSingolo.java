package it.algos.vaad24.backend.layer;

import it.algos.vaad24.backend.enumeration.*;
import org.springframework.data.domain.*;

/**
 * Project wiki24
 * Created by Algos
 * User: gac
 * Date: Sun, 27-Aug-2023
 * Time: 14:04
 */
public class FiltroSingolo {


    private String propertyField;

    private Object propertyValue;

    private AETypeFilter type;

    private Sort sort;


    public FiltroSingolo(String propertyField, Object propertyValue) {
        this(propertyField, propertyValue, AETypeFilter.uguale, null);
    }

    public FiltroSingolo(String propertyField, Object propertyValue, AETypeFilter type) {
        this(propertyField, propertyValue, type, null);
    }

    public FiltroSingolo(String propertyField, Object propertyValue, AETypeFilter type, Sort sort) {
        this.propertyField = propertyField;
        this.propertyValue = propertyValue;
        this.type = type;
        this.sort = sort;
    }

    public String getPropertyField() {
        return propertyField;
    }

    public Object getPropertyValue() {
        return propertyValue;
    }

    public AETypeFilter getType() {
        return type;
    }

    public Sort getSort() {
        return sort;
    }

}
