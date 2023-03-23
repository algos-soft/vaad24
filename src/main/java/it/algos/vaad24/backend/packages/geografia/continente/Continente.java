package it.algos.vaad24.backend.packages.geografia.continente;

import it.algos.vaad24.backend.annotation.*;
import static it.algos.vaad24.backend.boot.VaadCost.*;
import it.algos.vaad24.backend.entity.*;
import it.algos.vaad24.backend.enumeration.*;
import lombok.*;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.index.*;
import org.springframework.data.mongodb.core.mapping.*;
import org.springframework.stereotype.*;

import javax.persistence.*;

/**
 * Project vaadin23
 * Created by Algos
 * User: gac
 * Date: dom, 03-apr-2022
 * Time: 08:39
 * <p>
 * Estende la entity astratta AEntity che contiene la key property ObjectId <br>
 */
@Component
@Document
//Lombok
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder()
@EqualsAndHashCode(callSuper = false)
@MappedSuperclass()
@AIEntity(keyPropertyName = FIELD_NAME_NOME, searchPropertyName = FIELD_NAME_NOME, usaReset = true)
public class Continente extends AEntity {

    @Indexed(unique = true, direction = IndexDirection.ASCENDING)
    @AIField(type = AETypeField.integer, header = "#", widthEM = 3, caption = "Ordinamento")
    public int ordine;

    @AIField(type = AETypeField.text, caption = "Nome corrente", sortProperty = "ordine")
    public String nome;

    @AIField(type = AETypeField.booleano, widthEM = 7)
    public boolean abitato;

    @Override
    public String toString() {
        return nome;
    }

}// end of crud entity class