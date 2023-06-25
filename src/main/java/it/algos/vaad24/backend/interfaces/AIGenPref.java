package it.algos.vaad24.backend.interfaces;

import it.algos.vaad24.backend.enumeration.*;
import it.algos.vaad24.backend.service.*;

import java.math.*;

/**
 * Project vaadin23
 * Created by Algos
 * User: gac
 * Date: Sun, 26-Jun-2022
 * Time: 08:42
 */
public interface AIGenPref {

    void setPreferenceService(PreferenceService preferenceService);

    PreferenceService getPreferenceService();

    void setLogger(LogService logger);

    Object get();

    Object getValue();

    String getStr();

    boolean is();

    public int getInt();

    public BigDecimal getDecimal();

    public String getEnumAll();


    public String getEnumCurrent();

    public AITypePref getEnumCurrentObj();

    AETypePref getType();

    String getKeyCode();

    String getDescrizione();

    Object getDefaultValue();

    AITypePref getTypeEnum();

    void setDate(DateService date);

    void setText(TextService text);

    void setValue(Object javaValue);

    void setEnumCurrent(String currentValue);

    void setEnumCurrentObj(AITypePref currentValue);

    Class<?> getEnumClazz();

    boolean isDinamica();

    boolean needRiavvio();

    //--preferenza generale del framework e NON specifica di un'applicazione
    boolean isVaad24();

}// end of interface