package it.algos.vaad24.backend.packages.utility.nota;

import static it.algos.vaad24.backend.boot.VaadCost.*;
import it.algos.vaad24.backend.enumeration.*;
import it.algos.vaad24.backend.logic.*;
import org.springframework.stereotype.*;

import java.time.*;

/**
 * Project vaadin23
 * Created by Algos
 * User: gac
 * Date: ven, 18-mar-2022
 * Time: 06:55
 */
@Service
public class NotaBackend extends CrudBackend {


    public NotaBackend() {
        super(Nota.class);
    }

    public Nota newEntity() {
        return newEntity(null, null, VUOTA);
    }

    @Override
    public Nota newEntity(final String keyPropertyValue) {
        return newEntity(null, null, keyPropertyValue);
    }

    /**
     * Creazione in memoria di una nuova entity che NON viene salvata <br>
     *
     * @param type        merceologico della nota
     * @param livello     di importanza o rilevanza della nota
     * @param descrizione dettagliata della nota
     *
     * @return la nuova entity appena creata (con keyID ma non salvata)
     */
    public Nota newEntity(final AETypeLog type, final AENotaLevel livello, final String descrizione) {
        Nota newEntityBean = Nota.builder()
                .type(type != null ? type : AETypeLog.system)
                .livello(livello != null ? livello : AENotaLevel.normale)
                .inizio(LocalDate.now())
                .descrizione(textService.isValid(descrizione) ? descrizione : null)
                .build();

        return (Nota) fixKey(newEntityBean);
    }


}// end of crud backend class
