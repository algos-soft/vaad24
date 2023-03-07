package it.algos.vaad24.backend.entity;

import it.algos.vaad24.backend.annotation.*;
import it.algos.vaad24.backend.enumeration.*;
import lombok.*;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.index.*;
import org.springframework.data.mongodb.core.mapping.*;
import org.springframework.stereotype.*;

import javax.persistence.*;

/**
 * Project vaad24
 * Created by Algos
 * User: gac
 * Date: Sun, 05-Mar-2023
 * Time: 19:21
 */
@Component
@Document
//Lombok
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(builderMethodName = "builderOrdine")
@EqualsAndHashCode(callSuper = false)
@MappedSuperclass()
@AIEntity(keyPropertyName = "nome", usaReset = true)
public  class OrdineEntity extends AEntity {

    @Indexed(unique = true, direction = IndexDirection.ASCENDING)
    @AIField(type = AETypeField.integer, header = "#", widthEM = 3, caption = "Ordinamento")
    public int ordine;

    @AIField(type = AETypeField.text, caption = "Nome corrente", sortProperty = "ordine")
    public String nome;

    @Override
    public String toString() {
        return nome;
    }

}
