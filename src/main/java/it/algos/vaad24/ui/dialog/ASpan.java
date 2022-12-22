package it.algos.vaad24.ui.dialog;

import com.vaadin.flow.component.html.*;
import it.algos.vaad24.backend.enumeration.*;

/**
 * Project vaad24
 * Created by Algos
 * User: gac
 * Date: Thu, 22-Dec-2022
 * Time: 17:47
 */
public class ASpan {

    private String message;

    private AEFontWeight weight;

    private AETypeColor color;

    private AEFontHeight fontHeight;

    private AELineHeight lineHeight;

    private AEFontStyle style;


    public static Span getSpan(String message) {
        Span span = new Span();

        message = "Prova iniziale span con <b>grassetto</b>";
        span.getElement().setProperty("innerHTML", message);

        return span;
    }

}
