package it.algos.vaad24.ui.dialog;

import com.vaadin.flow.component.*;
import com.vaadin.flow.component.confirmdialog.*;
import static it.algos.vaad24.backend.boot.VaadCost.*;

/**
 * Project vaad24
 * Created by Algos
 * User: gac
 * Date: Fri, 23-Dec-2022
 * Time: 18:09
 */
public class AConfirm extends ConfirmDialog {


    public AConfirm() {
        this.setConfirmText(BUTTON_CONFERMA);
        this.setCancelText(BUTTON_CANCELLA);
        this.setRejectText(BUTTON_REJECT);
    }

    public static void html(String html) {
        AConfirm dialog = new AConfirm();
        dialog.setText(new Html("<p>" + html + "</p>"));
        dialog.open();
    }

    public static void show(String message) {
        AConfirm dialog = new AConfirm();
        dialog.setText(new Html("<p>" + message + "</p>"));
        dialog.open();
    }

    public static AConfirm title(String title) {
        AConfirm dialog = new AConfirm();
        dialog.setHeader(title);
        //        dialog.setHeader(new Html("<p>" + title + "</p>"));
        return dialog;
    }

    public AConfirm message(String message) {
        this.setText(new Html("<p>" + message + "</p>"));
        return this;
    }

    public AConfirm confirm(String confirmButtonText) {
        this.setConfirmText(confirmButtonText);
        return this;
    }
    public AConfirm confirmError() {
        this.setConfirmButtonTheme("error primary");
        return this;
    }
    public AConfirm confirmError(String confirmButtonText) {
        this.setConfirmButtonTheme("error primary");
        this.setConfirmText(confirmButtonText);
        return this;
    }

    public AConfirm annulla() {
        this.setCancelable(true);
        return this;
    }
    public AConfirm annullaPrimary() {
        this.setCancelButtonTheme("primary");
        this.setCancelable(true);
        return this;
    }


    public AConfirm rifiuta() {
        this.setCancelable(true);
        this.setRejectable(true);
        return this;
    }
    public static void reset() {
        StringBuffer buffer = new StringBuffer();
        buffer.append("Ripristina nel database i valori di default annullando le eventuali modifiche apportate successivamente");
        buffer.append("</br>");
        buffer.append(ASpan.text("Sei sicuro di volerli cancellare tutti?").rosso().bold().get());
        buffer.append("</br>");
        buffer.append(ASpan.text("L'operazione è irreversibile").blue().bold().get());
        AConfirm.title("Reset").message(buffer.toString()).confirmError("Reset").annullaPrimary().open();
    }

}
