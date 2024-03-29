package it.algos.vaad24simple.backend.packages.prova;

import it.algos.vaad24.backend.annotation.*;
import it.algos.vaad24.backend.entity.*;
import it.algos.vaad24.backend.enumeration.*;
import it.algos.vaad24.backend.packages.anagrafica.*;
import it.algos.vaad24.backend.packages.geografia.continente.*;
import lombok.*;
import org.springframework.data.mongodb.core.mapping.*;
import org.springframework.stereotype.*;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.time.*;
import java.util.*;

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

    @Transient
    private static final double lar = 14;

    @NotEmpty
    @AIField(type = AETypeField.text, widthEM = 20, focus = true, search = true)
    public String descrizione;

    @AIField(type = AETypeField.booleano, typeBool = AETypeBoolCol.boolGrezzo)
    public boolean boolGrezzo;

    @AIField(type = AETypeField.booleano, typeBool = AETypeBoolCol.checkBox)
    public boolean checkBox;

    @AIField(type = AETypeField.booleano, typeBool = AETypeBoolCol.checkIcon)
    public boolean checkIcon;

    @AIField(type = AETypeField.booleano, typeBool = AETypeBoolCol.checkIconReverse)
    public boolean checkIconReverse;

    @AIField(type = AETypeField.booleano, typeBool = AETypeBoolCol.yesNo)
    public boolean yesNo;

    @AIField(type = AETypeField.booleano, typeBool = AETypeBoolCol.yesNoReverse)
    public boolean yesNoReverse;

    @AIField(type = AETypeField.booleano, typeBool = AETypeBoolCol.yesNoBold)
    public boolean yesNoBold;

    @AIField(type = AETypeField.booleano, typeBool = AETypeBoolCol.thumb)
    public boolean thumb;

    @AIField(type = AETypeField.booleano, typeBool = AETypeBoolCol.thumbReverse)
    public boolean thumbReverse;

    @DBRef
    @AIField(type = AETypeField.linkDinamico, widthEM = lar, linkClazz = ContinenteBackend.class)
    public Continente continenteLinkDinamicoDBRef;

    @AIField(type = AETypeField.linkStatico, widthEM = lar, linkClazz = ViaBackend.class)
    public Via viaLinkStatico;

    @AIField(type = AETypeField.listaH, widthEM = lar, linkClazz = Via.class)
    public List<Via> listaVie;

    @AIField(type = AETypeField.listaV, widthEM = lar, linkClazz = Continente.class)
    public List<Continente> listaContinenti;

    @AIField(type = AETypeField.enumString, widthEM = lar, valoriEnum = "alfa,beta,gamma")
    public String typeString;

    @AIField(type = AETypeField.enumType, widthEM = lar, enumClazz = AETypeVers.class)
    public AETypeVers versione;

    @AIField(type = AETypeField.enumType, widthEM = lar, enumClazz = AESchedule.class)
    public AESchedule schedule;

    @AIField(type = AETypeField.localDateTime, typeDate = AETypeDate.iso8601)
    public LocalDateTime creazione2;


    @Override
    public String toString() {
        return descrizione;
    }

}// end of crud entity class