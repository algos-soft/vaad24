package it.algos.vaad24simple.backend.packages.prova;

import it.algos.vaad24.backend.annotation.*;
import it.algos.vaad24.backend.entity.*;
import it.algos.vaad24.backend.enumeration.*;
import it.algos.vaad24.backend.packages.geografia.continente.*;
import lombok.*;
import org.springframework.data.mongodb.core.mapping.*;
import org.springframework.stereotype.*;

import javax.persistence.*;
import javax.validation.constraints.*;

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
@Component
@Document
//Lombok
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder()
@EqualsAndHashCode(callSuper = false)
@MappedSuperclass()
@AIEntity(collectionName = "test", keyPropertyName = "descrizione", usaReset = true)
public class Prova extends AEntity {


    @NotEmpty
    @AIField(type = AETypeField.text, flexGrow = true, focus = true, search = true)
    public String descrizione;

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
        return descrizione;
    }

}// end of crud entity class