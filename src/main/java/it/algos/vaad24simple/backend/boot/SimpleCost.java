package it.algos.vaad24simple.backend.boot;

import com.vaadin.flow.spring.annotation.*;
import org.springframework.beans.factory.config.*;
import org.springframework.context.annotation.Scope;

/**
 * Project vaadin23
 * Created by Algos
 * User: gac
 * Date: mar, 15-mar-2022
 * Time: 09:22
 */
@SpringComponent
@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
public class SimpleCost {

    public static final String QUALIFIER_VERSION_SIMPLE = "versionSimple";

    public static final String QUALIFIER_DATA_SIMPLE = "dataSimple";

    public static final String QUALIFIER_PREFERENCES_SIMPLE = "preferencesSimple";

    public static final String DESCRIZIONE_PREFERENZA = "Test di preferenza per type. Solo in questo progetto";

}
