package it.algos.vaad24.backend.enumeration;

import it.algos.vaad24.backend.interfaces.*;

import java.util.*;

/**
 * Project vaad24
 * Created by Algos
 * User: gac
 * Date: Sat, 14-Jan-2023
 * Time: 15:46
 */
public enum AETypeResult implements AIType {
    indeterminato("Indeterminato"),
    noAECopy("Manca il type AECopy"),
    typeNonCompatibile("Il type previsto non è compatibile col metodo"),
    noFileName("Manca il nome del file"),
    noSourceFile("Manca il file sorgente"),

    fileEsistente("Il file esisteva già"),
    fileEsistenteUguale("Il file esisteva già e non è stato modificato"),
    fileEsistenteModificato("Il file esisteva già ma è stato modificato"),
    fileCreato("Il file è stato creato"),
    fileSovrascritto("Il file è stato sovrascritto");

    private String tag;

    AETypeResult(String tag) {
        this.tag = tag;
    }

    public static List<AETypeResult> getAllEnums() {
        return Arrays.stream(values()).toList();
    }

    public static List<String> getAllTags() {
        List<String> listaTags = new ArrayList<>();

        getAllEnums().forEach(type -> listaTags.add(type.getTag()));
        return listaTags;
    }

    @Override
    public List<AETypeResult> getAll() {
        return Arrays.stream(values()).toList();
    }

    @Override
    public String getTag() {
        return tag;
    }

}
