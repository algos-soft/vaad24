package it.algos.vaad24.ui.dialog;

import com.vaadin.flow.component.html.*;
import static it.algos.vaad24.backend.boot.VaadCost.*;
import it.algos.vaad24.backend.enumeration.*;

/**
 * Project vaad24
 * Created by Algos
 * User: gac
 * Date: Thu, 22-Dec-2022
 * Time: 17:47
 */
public class ASpan extends Span {

    private String text;

    private AEFontWeight weight;

    private AETypeColor color;

    private AEFontHeight fontHeight;

    private AELineHeight lineHeight;

    private AEFontStyle style;


    public static ASpan text(String text) {
        ASpan span = new ASpan();
        span.setText(text);
        return span;
    }

    public ASpan rosso() {
        this.color = AETypeColor.rosso;
        this.getElement().getStyle().set(TAG_HTML_COLOR, color.getTag());
        return this;
    }

    public ASpan verde() {
        this.color = AETypeColor.verde;
        this.getElement().getStyle().set(TAG_HTML_COLOR, color.getTag());
        return this;
    }

    public ASpan blue() {
        this.color = AETypeColor.blu;
        this.getElement().getStyle().set(TAG_HTML_COLOR, color.getTag());
        return this;
    }

    //    public static Span getSpan(String message) {
    //        Span span = new Span();
    //
    //        message = "Prova iniziale span con <b>grassetto</b>";
    //        span.getElement().setProperty("innerHTML", message);
    //
    //        return span;
    //    }

}
