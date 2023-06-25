package it.algos.vaad24.backend.enumeration;

import static it.algos.vaad24.backend.boot.VaadCost.*;
import it.algos.vaad24.backend.interfaces.*;
import it.algos.vaad24.backend.service.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;

import javax.annotation.*;
import java.math.*;
import java.util.*;

/**
 * Project vaadin23
 * Created by Algos
 * User: gac
 * Date: mer, 30-mar-2022
 * Time: 21:29
 */
public enum Pref implements AIGenPref {
    debug("debug", AETypePref.bool, true, "Flag generale di debug"),
    durataAvviso("durataAvviso", AETypePref.integer, 2000, "Durata in millisecondi dell'avviso a video"),

    usaNonBreaking("usaNonBreaking", AETypePref.bool, false, "Uso dello spazio non-breaking"),
    nonBreaking("nonBreaking", AETypePref.string, SPAZIO_NON_BREAKING, "Spazio non-breaking"),
    ;


    //--codice di riferimento. Se è usaCompany=true, DEVE contenere anche il code della company come prefisso.
    private String keyCode;

    //--tipologia di dato da memorizzare.
    //--Serve per convertire (nei due sensi) il valore nel formato byte[] usato dal mongoDb
    private AETypePref type;

    //--Valore java iniziale da convertire in byte[] a seconda del type
    private Object defaultValue;

    //--descrizione breve ma comprensibile. Ulteriori (eventuali) informazioni nel campo 'note'
    private String descrizione;

    //--Tipo AITypePref per AETypePref.enumerationType
    private AITypePref typeEnum;

    //--preferenze che necessita di un riavvio del programma per avere effetto
    private boolean needRiavvio;

    private boolean dinamica;

    private Class<?> enumClazz;


    //--preferenze singole per ogni company; usa un prefisso col codice della company
    private boolean usaCompany; // @todo da implementare

    //--preferenze visibile agli admin se l'applicazione è usaSecurity=true
    private boolean visibileAdmin; // @todo da implementare

    //--descrizione aggiuntiva eventuale
    private String note;

    //--Link injettato da un metodo static
    private PreferenceService preferenceService;

    //--Link injettato da un metodo static
    private LogService logger;

    //--Link injettato da un metodo static
    private DateService date;

    private TextService text;


    Pref(final String keyCode, final AETypePref type, final Object defaultValue, final String descrizione) {
        this(keyCode, type, defaultValue, descrizione, false);
    }// fine del costruttore

    Pref(final String keyCode, final AETypePref type, final Object defaultValue, final String descrizione, boolean dinamica) {
        this.keyCode = keyCode;
        this.type = type;
        this.defaultValue = defaultValue;
        this.descrizione = descrizione;
        this.dinamica = dinamica;
    }// fine del costruttore


    public static List getAll() {
        return Arrays.stream(values()).toList();
    }

    public static List<Pref> getAllEnums() {
        return Arrays.stream(values()).toList();
    }

    public static List<String> getAllKeyCode() {
        List<String> listaTags = new ArrayList<>();

        getAllEnums().forEach(type -> listaTags.add(type.getKeyCode()));
        return listaTags;
    }

    //------------------------------------------------
    //--copiare tutti i metodi (Instance Method e non Static Method) nelle sottoclassi xPref
    //--cambiando in static PreferenzaServiceInjector.postConstruct() Pref.values() -> xPref.values()
    //------------------------------------------------

    @Override
    public void setPreferenceService(PreferenceService preferenceService) {
        this.preferenceService = preferenceService;
    }
    @Override
    public PreferenceService getPreferenceService() {
        return preferenceService ;
    }

    @Override
    public void setLogger(LogService logger) {
        this.logger = logger;
    }

    @Override
    public void setDate(DateService date) {
        this.date = date;
    }

    @Override
    public void setText(TextService text) {
        this.text = text;
    }

    @Override
    public void setValue(Object javaValue) {
        preferenceService.setValue(type, keyCode, javaValue);
    }


    @Override
    public Object get() {
        return getValue();
    }

    @Override
    public Object getValue() {
        return preferenceService.getValue(type, keyCode);
    }

    @Override
    public String getStr() {
        return preferenceService.getStr(type, keyCode);
    }

    @Override
    public boolean is() {
        return preferenceService.is(type, keyCode);
    }

    @Override
    public int getInt() {
        return preferenceService.getInt(type, keyCode);
    }

    @Override
    public BigDecimal getDecimal() {
        return preferenceService.getDecimal(type, keyCode);
    }

    @Override
    public AETypePref getType() {
        return type;
    }

    @Override
    public String getKeyCode() {
        return keyCode;
    }

    @Override
    public String getDescrizione() {
        return descrizione;
    }

    @Override
    public Object getDefaultValue() {
        return defaultValue;
    }

    @Override
    public AITypePref getTypeEnum() {
        return typeEnum;
    }

    /**
     * Tutti i valori della enum <br>
     */
    @Override
    public String getEnumAll() {
        return preferenceService.getEnumAll(type, keyCode);
    }

    @Override
    public AITypePref getEnumCurrentObj() {
        return preferenceService.getEnumCurrentObj(typeEnum, type, keyCode);
    }

    /**
     * Valore selezionato della enum <br>
     */
    @Override
    public String getEnumCurrent() {
        return preferenceService.getEnumCurrentTxt(type, keyCode);
    }

    /**
     * Valore selezionato della enum <br>
     */
    @Override
    public void setEnumCurrent(String currentValue) {
        preferenceService.setEnumCurrentTxt(type, keyCode, currentValue);
    }

    @Override
    public void setEnumCurrentObj(AITypePref currentValue) {
        preferenceService.setEnumCurrentObj(type, keyCode, currentValue);
    }

    public boolean isDinamica() {
        return dinamica;
    }

    public boolean needRiavvio() {
        return needRiavvio;
    }

    public boolean isVaad24() {
        return true;
    }


    public Class<?> getEnumClazz() {
        return enumClazz;
    }

    @Component
    public static class PreferenzaServiceInjector {

        @Autowired
        private PreferenceService preferenceService;

        @Autowired
        private LogService logger;

        @Autowired
        private DateService date;

        @Autowired
        private TextService text;

        @PostConstruct
        public void postConstruct() {
            for (Pref pref : Pref.values()) {
                pref.setPreferenceService(preferenceService);
                pref.setLogger(logger);
                pref.setDate(date);
                pref.setText(text);
            }
        }

    }

}// end of enumeration