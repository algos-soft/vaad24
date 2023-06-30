package it.algos.vaad24.backend.components;

import com.vaadin.flow.component.html.*;
import com.vaadin.flow.spring.annotation.*;
import static it.algos.vaad24.backend.boot.VaadCost.*;
import it.algos.vaad24.backend.enumeration.*;
import org.springframework.beans.factory.config.*;
import org.springframework.context.annotation.Scope;

/**
 * Project wiki24
 * Created by Algos
 * User: gac
 * Date: Fri, 30-Jun-2023
 * Time: 14:15
 */
@SpringComponent
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class WAnchor extends Anchor {

    public WAnchor() {
    }

    public WAnchor(String href, String text) {
        super(href, text);
    }

    public static WAnchor build(String href, String text) {
        WAnchor wAnchor = new WAnchor(PATH_WIKI + href, text);
        wAnchor.setTarget(AnchorTarget.BLANK);
        wAnchor.getElement().getStyle().set(AEFontWeight.HTML, AEFontWeight.bold.getTag());
        return wAnchor;
    }

}
