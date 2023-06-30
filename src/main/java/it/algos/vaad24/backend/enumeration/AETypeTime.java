package it.algos.vaad24.backend.enumeration;

import it.algos.vaad24.backend.interfaces.*;
import it.algos.vaad24.backend.service.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;

import javax.annotation.*;
import java.util.*;

/**
 * Project vaad24
 * Created by Algos
 * User: gac
 * Date: Sun, 29-Jan-2023
 * Time: 21:27
 */
public enum AETypeTime implements AIType {
    nonUsata("nonUsata", 1),
    millisecondi("millisecondi", 1),
    secondi("secondi", 1000),
    minuti("minuti", 1000 * 60),
    ore("ore", 1000 * 60 * 60),
    ;

    private int coefficiente;

    private String tag;

    // @Autowired nella classe statica interna  @Component TypeInjector
    protected TextService textService;

    AETypeTime(String tag, int coefficiente) {
        this.tag = tag;
        this.coefficiente = coefficiente;
    }

    public static List<AETypeTime> getAllEnums() {
        return Arrays.stream(values()).toList();
    }

    public static List<String> getAllTags() {
        List<String> listaTags = new ArrayList<>();

        getAllEnums().forEach(type -> listaTags.add(type.getTag()));
        return listaTags;
    }

    @Override
    public List<AETypeTime> getAll() {
        return Arrays.stream(values()).toList();
    }

    @Override
    public String getTag() {
        return tag;
    }

    public int getCoefficiente() {
        return coefficiente;
    }

    public int durata(long inizio) {
        Long delta = 0L;

        long fine = System.currentTimeMillis();
        delta = fine - inizio;
        delta = delta / coefficiente;

        return delta.intValue();
    }


    public String message(long inizio, String info) {
        String message;
        int durata = durata(inizio);
        String delta = textService.format(durata);
        message = String.format("%s in %s %s.", info, delta, tag);

        return message;
    }

    public String message(long inizio) {
        return message(inizio, "Eseguito");
    }


    public void setText(TextService textService) {
        this.textService = textService;
    }

    @Component
    public static class TypeInjector {

        @Autowired
        public TextService textService;


        @PostConstruct
        public void postConstruct() {
            for (AETypeTime type : AETypeTime.values()) {
                type.setText(textService);
            }
        }

    }

}
