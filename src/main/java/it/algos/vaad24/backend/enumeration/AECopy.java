package it.algos.vaad24.backend.enumeration;

import java.util.*;

/**
 * Project vaadflow14
 * Created by Algos
 * User: gac
 * Date: gio, 21-gen-2021
 * Time: 14:10
 */
public enum AECopy {

    fileCreaOnlyNotExisting("Crea il file SOLO se manca", AECopyType.file),
    fileModifyEver("Sovrascrive SEMPRE il file", AECopyType.file),
    elaboraFile("Elaborazione specifica del file", AECopyType.elaboraFile),


    modulo("Cancella e ricrea SEMPRE l'intero modulo", AECopyType.modulo),
    dirCreaOnlyNotExisting("Crea la directory SOLO se manca", AECopyType.directory),
    dirModifyEver("Cancella e ricrea SEMPRE l'intera directory", AECopyType.directory),
    dirFilesAddOnly("Aggiunge dir/files SENZA modificare quelli esistenti", AECopyType.directory),
    dirFilesModifica("Aggiunge dir/files e MODIFICA quelli esistenti", AECopyType.directory),
    //    dirFilesModificaToken("Aggiunge e MODIFICA dirs/files con check del token", AECopyType.directory),
    elaboraDir("Elaborazione specifica della directory", AECopyType.elaboraDir),

    sourceSovrascriveSempreAncheSeEsiste("", AECopyType.source),
    sourceSoloSeNonEsiste("", AECopyType.source),
    sourceCheckFlagSeEsiste("", AECopyType.source),

    noCopy("Manca il type AECopy", AECopyType.indeterminato);;

    private String descrizione;

    private AECopyType type;

    /**
     * Costruttore <br>
     */
    AECopy(String descrizione, AECopyType type) {
        this.descrizione = descrizione;
        this.type = type;
    }

    public static List<AECopy> getAllEnums() {
        return Arrays.stream(values()).toList();
    }

    public AECopyType getType() {
        return type;
    }

    public String getDescrizione() {
        return descrizione;
    }
}

