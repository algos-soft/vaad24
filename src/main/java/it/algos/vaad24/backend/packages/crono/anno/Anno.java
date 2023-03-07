package it.algos.vaad24.backend.packages.crono.anno;

import it.algos.vaad24.backend.annotation.*;
import it.algos.vaad24.backend.entity.*;
import it.algos.vaad24.backend.enumeration.*;
import it.algos.vaad24.backend.packages.crono.secolo.*;
import lombok.*;
import org.springframework.data.mongodb.core.mapping.*;
import org.springframework.stereotype.*;

import javax.persistence.*;


/**
 * Project vaadin23
 * Created by Algos
 * User: gac
 * Date: lun, 02-mag-2022
 * Time: 16:05
 * <p>
 * Estende la entity astratta AEntity che contiene la key property ObjectId <br>
 */
@Component
@Document
//Lombok
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(builderMethodName = "builderAnno")
@EqualsAndHashCode(callSuper = false)
@MappedSuperclass()
@AIEntity(keyPropertyName = "nome", usaReset = true, preReset = "secolo")
public class Anno extends OrdineEntity {

    @DBRef
    @AIField(type = AETypeField.linkDinamico, widthEM = 10, linkClazz = SecoloBackend.class)
    public Secolo secolo;

    @AIField(type = AETypeField.booleano, header = "d.C.")
    public boolean dopoCristo;

    @AIField(type = AETypeField.booleano, header = "BS")
    public boolean bisestile;

    @Override
    public String toString() {
        return super.toString();
    }

}// end of crud entity class