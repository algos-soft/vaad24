package it.algos.vaad24simple.backend.enumeration;

import static it.algos.vaad24simple.backend.boot.SimpleCost.*;
import static it.algos.vaad24.backend.boot.VaadCost.*;
import it.algos.vaad24.backend.enumeration.*;
import it.algos.vaad24.backend.interfaces.*;
import it.algos.vaad24.backend.service.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;

import javax.annotation.*;
import java.util.*;

/**
 * Project vaadin23
 * Created by Algos
 * User: gac
 * Date: sab, 07-mag-2022
 * Time: 13:34
 */
public enum SimplePref implements AIGenPref {
    string("string", AETypePref.string, "stringa"),
    bool("bool", AETypePref.bool, false),
    integer("integer", AETypePref.integer, 0),
    lungo("lungo", AETypePref.lungo, 0L),
    localDateTime("localDateTime", AETypePref.localdatetime, ROOT_DATA_TIME),
    localDate("localDate", AETypePref.localdate, ROOT_DATA),
    localTime("localTime", AETypePref.localtime, ROOT_TIME),
    email("email", AETypePref.email, "mail"),
    enumerationType("enumerationType", AETypePref.enumerationType, AELogLevel.info, DESCRIZIONE_PREFERENZA, AELogLevel.info),
    enumerationString("enumerationString", AETypePref.enumerationString, "alfa,beta,gamma;beta"),

    ;

    private static final String DESCRIZIONE = "Test di preferenza per type. Solo in questo progetto";

    //--codice di riferimento.
    private String keyCode;

    //--tipologia di dato da memorizzare.
    //--Serve per convertire (nei due sensi) il valore nel formato byte[] usato dal mongoDb
    private AETypePref type;

    //--descrizione breve ma comprensibile. Ulteriori (eventuali) informazioni nel campo 'note'
    private String descrizione;

    //--Valore java iniziale da convertire in byte[] a seconda del type
    private Object defaultValue;

    //--Tipo AITypePref per AETypePref.enumerationType
    private AITypePref typeEnum;

    //--preferenze che necessita di un riavvio del programma per avere effetto
    private boolean needRiavvio;

    //--Link injected da un metodo static
    private PreferenceService preferenceService;

    //--Link injected da un metodo static
    private LogService logger;

    //--Link injected da un metodo static
    private DateService date;

    private TextService text;

    SimplePref(final String keyCode, final AETypePref type, final Object defaultValue) {
        this(keyCode, type, defaultValue, DESCRIZIONE_PREFERENZA);
    }// fine del costruttore

    SimplePref(final String keyCode, final AETypePref type, final Object defaultValue, final String descrizione) {
        this(keyCode, type, defaultValue, descrizione, null);
    }// fine del costruttore

    SimplePref(final String keyCode, final AETypePref type, final Object defaultValue, final String descrizione, AITypePref typeEnum) {
        this.keyCode = keyCode;
        this.type = type;
        this.defaultValue = defaultValue;
        this.descrizione = descrizione;
        this.typeEnum = typeEnum;
    }// fine del costruttore

    public static List<SimplePref> getAllEnums() {
        return Arrays.stream(values()).toList();
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


    /**
     * Valore selezionato della enum <br>
     */
    @Override
    public String getEnumCurrent() {
        return preferenceService.getEnumCurrentTxt(type, keyCode);
    }

    @Override
    public AITypePref getEnumCurrentObj() {
        return preferenceService.getEnumCurrentObj(typeEnum, type, keyCode);
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
            for (SimplePref pref : SimplePref.values()) {
                pref.setPreferenceService(preferenceService);
                pref.setLogger(logger);
                pref.setDate(date);
                pref.setText(text);
            }
        }

    }

}// end of enumeration
