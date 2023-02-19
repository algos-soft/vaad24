package it.algos.vaad24.backend.annotation;

import static it.algos.vaad24.backend.boot.VaadCost.*;

import java.lang.annotation.*;

/**
 * Project vaad24
 * Created by Algos
 * User: gac
 * Date: ven, 13-ott-2017
 * Time: 14:55
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE) //can use in class and interface.
public @interface AIEntity {

    /**
     * (Optional) nome della collection (minuscolo) solo se diverso dal nome della EntityClass <br>
     * Di default lo stesso nome della classe Entity <br>
     */
    String collectionName() default VUOTA;


    /**
     * (Optional) entity 'ancestor' indispensabile per il reset <br>
     * Di default nessuna entity <br>
     */
    String preReset() default VUOTA;


    /**
     * (Optional) key property unica <br>
     * Di default usa la property 'id' della collection mongoDB <br>
     */
    String keyPropertyName() default FIELD_NAME_ID_CON;


    /**
     * (Optional) chiave keyId creata tutta minuscola e case-insensitive <br>
     * Di default true <br>
     *
     * @return the status
     */
    boolean usaKeyIdMinuscolaCaseInsensitive() default true;


    /**
     * (Optional) chiave keyId creata senza spazi vuoti <br>
     * Di default true <br>
     *
     * @return the status
     */
    boolean usaKeyIdSenzaSpazi() default true;

}// end of interface annotation
