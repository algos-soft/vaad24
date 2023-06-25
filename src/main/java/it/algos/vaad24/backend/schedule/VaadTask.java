package it.algos.vaad24.backend.schedule;

import com.vaadin.flow.spring.annotation.*;
import it.algos.vaad24.backend.boot.*;
import static it.algos.vaad24.backend.boot.VaadCost.*;
import it.algos.vaad24.backend.enumeration.*;
import it.algos.vaad24.backend.interfaces.*;
import it.algos.vaad24.backend.service.*;
import it.algos.vaad24.backend.wrapper.*;
import it.sauronsoftware.cron4j.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.beans.factory.config.*;
import org.springframework.context.*;
import org.springframework.context.annotation.Scope;

import java.time.*;
import java.util.*;

/**
 * Project vaad24
 * Created by Algos
 * User: gac
 * Date: Wed, 28-Dec-2022
 * Time: 20:34
 */
@SpringComponent
@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
public class VaadTask extends Task {

    protected long inizio;

    protected AESchedule typeSchedule;

    protected String descrizioneTask;

    protected AIGenPref flagAttivazione;

    protected AIGenPref flagPrevisione;

    /**
     * Istanza di una interfaccia <br>
     * Iniettata automaticamente dal framework SpringBoot con l'Annotation @Autowired <br>
     * Disponibile DOPO il ciclo init() del costruttore di questa classe <br>
     */
    @Autowired
    public ApplicationContext appContext;

    /**
     * Istanza unica di una classe @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON) di servizio <br>
     * Iniettata automaticamente dal framework SpringBoot/Vaadin con l'Annotation @Autowired <br>
     * Disponibile DOPO il ciclo init() del costruttore di questa classe <br>
     */
    @Autowired
    protected LogService logger;


    /**
     * Deve essere sovrascritto, invocando PRIMA il metodo della superclasse <br>
     */
    @Override
    public void execute(TaskExecutionContext taskExecutionContext) throws RuntimeException {
    }


    public boolean execute() {
        inizio = System.currentTimeMillis();

        if (flagAttivazione == null || flagAttivazione.is()) {
            this.fixNext();
            return true;
        }
        else {
            this.loggerNoTask();
            return false;
        }
    }


    protected void fixNext() {
        int nextDays;
        LocalDateTime adesso = LocalDateTime.now();
        LocalDateTime prossimo = null;

        if (typeSchedule != null) {
            nextDays = typeSchedule.getGiorniNext();
            prossimo = adesso.plusDays(nextDays);
        }

        if (flagPrevisione != null && prossimo != null) {
            flagPrevisione.setValue(prossimo);
        }
    }

    public String getPattern() {
        return typeSchedule.getPattern();
    }


    public String getDescrizioneTask() {
        return descrizioneTask;
    }


    public AESchedule getTypeSchedule() {
        return typeSchedule;
    }

    public AIGenPref getFlagAttivazione() {
        return flagAttivazione;
    }

    public void loggerTask() {
        long fine = System.currentTimeMillis();
        String message;
        String clazzName;
        long delta = fine - inizio;
        delta = delta / 1000 / 60;

        clazzName = this.getClass().getSimpleName();
        message = String.format("%s%s%s [%s] eseguita in %s minuti", clazzName, FORWARD, descrizioneTask, getPattern(), delta);

        logger.info(new WrapLog().type(AETypeLog.task).message(message).usaDb());
    }

    public void loggerNoTask() {
        String message;
        String clazzName;

        clazzName = this.getClass().getSimpleName();
        message = String.format("%s%s%s [%s] non eseguita per flag disabilitato", clazzName, FORWARD, descrizioneTask, getPattern());

        logger.info(new WrapLog().type(AETypeLog.task).message(message));
    }


    public String info() {
        String message;
        String flagText = TASK_NO_FLAG + TASK_FLAG_SEMPRE_ATTIVA;
        String clazzName = this.getClass().getSimpleName();
        String desc = this.getDescrizioneTask();
        AESchedule type = this.getTypeSchedule();
        String pattern = type.getPattern();
        String nota = type.getNota();
        int nextDays = this.getTypeSchedule().getGiorniNext();
        AIGenPref flagTask = this.getFlagAttivazione();
        if (flagTask != null && flagTask.getPreferenceService() != null) {
            if (flagTask.is()) {
                flagText = flagTask.getKeyCode() + TASK_FLAG_ATTIVA;
            }
            else {
                flagText = flagTask.getKeyCode() + TASK_FLAG_DISATTIVA;
            }
        }

        message = String.format("%s [%s] %s (+%s)%s Eseguita %s %s", clazzName, pattern, flagText, nextDays, FORWARD, nota, desc);
        return message;
    }

    public static String info(Class taskNonIstanziata) {
        VaadTask task = getTask(taskNonIstanziata);
        return task != null ? task.info() : VUOTA;
    }


    public static VaadTask getTask(Class taskNonIstanziata) {
        List<VaadTask> listaTasks = VaadVar.taskList;

        if (listaTasks != null) {
            for (VaadTask task : listaTasks) {
                if (task.getClass().getSimpleName().equals(taskNonIstanziata.getSimpleName())) {
                    return task;
                }
            }
        }

        return null;
    }

}