package it.algos.vaad24.backend.packages.utility.log;

import static it.algos.vaad24.backend.boot.VaadCost.*;
import it.algos.vaad24.backend.enumeration.*;
import it.algos.vaad24.backend.logic.*;
import it.algos.vaad24.backend.wrapper.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;

import java.time.*;

/**
 * Project vaadin23
 * Created by Algos
 * User: gac
 * Date: mer, 16-mar-2022
 * Time: 19:47
 * <p>
 * Service di una entityClazz specifica e di un package <br>
 * Garantisce i metodi di collegamento per accedere al database <br>
 * Non mantiene lo stato di una istanza entityBean <br>
 * Mantiene lo stato della entityClazz <br>
 * NOT annotated with @SpringComponent (inutile, esiste già @Service) <br>
 * NOT annotated with @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON) (inutile, esiste già @Service) <br>
 */
@Service
@Qualifier(TAG_LOGGER)
public class LoggerBackend extends CrudBackend {


    public LoggerBackend() {
        super(Logger.class);
    }


    public void crea(final AELogLevel livello, final WrapLog wrap) {
        Logger entity = new Logger();
        AETypeLog type = wrap.getType();
        String message = wrap.getMessageDB();
        String companySigla = wrap.getCompanySigla();
        String userName = wrap.getUserName();
        String addressIP = wrap.getAddressIP();
        String classe = VUOTA;
        String metodo = VUOTA;
        int linea = 0;

        if (wrap.getException() != null) {
            classe = wrap.getException().getClazz();
            classe = fileService.estraeClasseFinale(classe);
            metodo = wrap.getException().getMethod();
            linea = wrap.getException().getLineNum();
        }

        entity.livello = livello;
        entity.type = type != null ? type : AETypeLog.system;
        entity.evento = LocalDateTime.now();
        entity.descrizione = textService.isValid(message) ? message : null;
        entity.company = textService.isValid(companySigla) ? companySigla : null;
        entity.user = textService.isValid(userName) ? userName : null;
        //        entity.address = textService.isValid(addressIP) ? addressIP : null;
        entity.classe = textService.isValid(classe) ? classe : null;
        entity.metodo = textService.isValid(metodo) ? metodo : null;
        entity.linea = linea;

        try {
            insert(entity);
        } catch (Exception unErrore) {
            System.out.println(unErrore);
        }
    }

}// end of crud backend class
