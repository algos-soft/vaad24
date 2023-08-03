package it.algos.vaad24simple.backend.packages.prova;

import com.vaadin.flow.spring.annotation.SpringComponent;
import it.algos.vaad24.backend.logic.*;
import org.springframework.context.annotation.Scope;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;

/**
 * Project vaad24
 * Created by Algos
 * User: gac
 * Date: Sat, 22-Jul-2023
 * Time: 15:15
 */
@SpringComponent
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class ProvaBuilder implements AlgosBuilderPattern {


    @Override
    public boolean isCostruttoreValido() {
        return false;
    }

    @Override
    public boolean isPatternCompleto() {
        return false;
    }

    @Override
    public String getNome() {
        return null;
    }

}
