package it.algos.vaad24simple.backend.packages.prova;

import it.algos.vaad24.backend.annotation.*;
import static it.algos.vaad24.backend.boot.VaadCost.*;
import it.algos.vaad24.backend.entity.*;
import it.algos.vaad24.backend.enumeration.*;
import it.algos.vaad24.backend.packages.geografia.continente.*;
import lombok.*;
import org.springframework.data.mongodb.core.mapping.*;

/**
 * Project vaad24
 * Created by Algos
 * User: gac
 * Date: Mon, 13-Feb-2023
 * Time: 09:40
 * <p>
 * Estende la entity astratta AEntity che contiene la key property ObjectId <br>
 */
//Lombok
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder()
@EqualsAndHashCode(callSuper = false)
@AIEntity(collectionName = "test", keyPropertyName = "nome")
public class Prova extends AEntity {


    public String nome;

    //    @AIField(type = AETypeField.combolinkato, linkClazz = ContinenteBackend.class, caption = "continente")
    @DBRef
    @AIField(type = AETypeField.link, linkClazz = ContinenteBackend.class)
    public Continente continenti;

    @AIField(type = AETypeField.enumerationString, valoriEnum = "alfa,beta,gamma")
    public String typeString;

    @AIField(type = AETypeField.enumerationType, enumClazz = AETypeVers.class)
    public String typeA;

    @AIField(type = AETypeField.enumerationType, enumClazz = AESchedule.class)
    public String typeB;

    @Override
    public String toString() {
        return VUOTA;
    }

}// end of crud entity class