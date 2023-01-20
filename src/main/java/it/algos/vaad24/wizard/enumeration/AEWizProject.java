package it.algos.vaad24.wizard.enumeration;

import static it.algos.vaad24.backend.boot.VaadCost.*;
import it.algos.vaad24.backend.enumeration.*;

import java.util.*;


/**
 * Project vaadflow14
 * Created by Algos
 * User: gac
 * Date: mer, 04-nov-2020
 * Time: 18:56
 * x
 */
public enum AEWizProject {
    config("Directory CONFIG di risorse on-line esterne al JAR (vaad24)", true, false, "config", AECopy.dirFilesModifica),

    documentation("Directory DOC di documentazione (vaad24)", true, false, "doc", AECopy.dirFilesModifica),

    frontend("Directory FRONTEND del Client (vaad24) [need riavvio]", true, false, "frontend", AECopy.dirFilesAddOnly),

    links("Directory LINKS a siti web utili (vaad24)", true, false, "links", AECopy.dirFilesModifica),

    snippets("Directory SNIPPETS di codice suggerito (vaad24)", true, false, "snippets", AECopy.dirFilesModifica),

    flow("Modulo BASE Vaad24 (Wizard compreso)", true, true, "src/main/java/it/algos/vaad24", AECopy.modulo),

    //    projectNew("Directory modulo del nuovo progetto (...)", false, VUOTA, AECopy.dirAddingOnly),

    resources("Directory RESOURCES (vaad24)", true, false, "src/main/resources", AECopy.dirFilesAddOnly),

    property("File application.PROPERTIES (sources)", true, false, "src/main/resources/application.properties",
            AECopy.sourceSovrascriveSempreAncheSeEsiste, "properties"
    ),

    banner("File BANNER di SpringBoot (vaad24) [need riavvio]", true, false, "src/main/resources/banner.txt", AECopy.fileModifyEver),

    git("File GIT di esclusione (sources)", true, false, ".gitignore", AECopy.fileModifyEver),

    pomVaad24("File POM.xml di Maven (vaad24)", true, false, "pom.xml", AECopy.elaboraFile),
    pomSources("File POM.xml di Maven (sources)", true, false, "pom.xml", AECopy.sourceSovrascriveSempreAncheSeEsiste, "pom"),

    read("File README con note di testo (sources)", true, false, "README.md", AECopy.sourceSoloSeNonEsiste, "readme"),

    testBase("Directory TEST /base (vaad24)", false, false, "src/test/java/it/algos/base", AECopy.dirFilesModifica),
    testEnum("Directory TEST /enum (vaad24)", false, false, "src/test/java/it/algos/enumeration", AECopy.dirFilesModifica),
    testService("Directory TEST /service (vaad24)", false, true, "src/test/java/it/algos/service", AECopy.dirFilesModificaToken,"Vaad24SimpleApplication","Application"),
    testBackend("Directory TEST /backend (vaad24)", false, false, "src/test/java/it/algos/backend", AECopy.elaboraDir),
    application("Main class java", true, false, "src/main/java/it/algos/@PROJECTUPPER@Application.java",
            AECopy.sourceSovrascriveSempreAncheSeEsiste, "application"
    ),
    boot("@PROJECTUPPER@Boot: con fixMenuRoutes()", true, false, "src/main/java/it/algos/@PROJECT@/backend/boot/@PROJECTUPPER@Boot.java",
            AECopy.sourceSovrascriveSempreAncheSeEsiste, "boot"
    ),
    cost("@PROJECTUPPER@Cost: costanti statiche del programma", true, false, "src/main/java/it/algos/@PROJECT@/backend/boot/@PROJECTUPPER@Cost.java",
            AECopy.sourceSovrascriveSempreAncheSeEsiste, "cost"
    ),
    vers("@PROJECTUPPER@Vers: versioni specifiche del programma", true, false, "src/main/java/it/algos/@PROJECT@/backend/boot/@PROJECTUPPER@Vers.java",
            AECopy.sourceSovrascriveSempreAncheSeEsiste, "vers"
    ),
    pref("@PROJECTUPPER@Pref: preferenze specifiche del programma", true, false, "src/main/java/it/algos/@PROJECT@/backend/boot/@PROJECTUPPER@Pref" +
            ".java",
            AECopy.sourceSovrascriveSempreAncheSeEsiste, "preferenza"
    ),
    enumeration("@FIRSTPROJECT@Pref: enumeration di preferenze specifiche del programma", true, false, "src/main/java/it/algos/@PROJECT" +
            "@/backend/enumeration/@FIRSTPROJECT@Pref.java",
            AECopy.sourceSovrascriveSempreAncheSeEsiste, "enumeration"
    ),
    ;


    private final String caption;

    //    private boolean accesoInizialmente;

    private final String copyDest;

    private final String fileSource;

    private final AECopy copy;

    private final boolean accesoNew;

    private final boolean accesoUpdate;

    private String srcToken;

    private String destToken;

    AEWizProject(final String caption, final boolean accesoNew, final boolean accesoUpdate, final String copyDest, final AECopy copy) {
        this(caption, accesoNew, accesoUpdate, copyDest, copy, VUOTA, VUOTA, VUOTA);
    }

    AEWizProject(final String caption, final boolean accesoNew, final boolean accesoUpdate, final String copyDest, final AECopy copy, String srcToken, String destToken) {
        this(caption, accesoNew, accesoUpdate, copyDest, copy, VUOTA, srcToken, destToken);
    }

    AEWizProject(final String caption, final boolean accesoNew, final boolean accesoUpdate, final String copyDest, final AECopy copy, final String fileSource) {
        this(caption, accesoNew, accesoUpdate, copyDest, copy, fileSource, VUOTA, VUOTA);
    }

    AEWizProject(final String caption, final boolean accesoNew, final boolean accesoUpdate, final String copyDest, final AECopy copy, final String fileSource, String srcToken, String destToken) {
        this.caption = caption;
        this.accesoNew = accesoNew;
        this.accesoUpdate = accesoUpdate;
        //        this.acceso = accesoInizialmente;
        this.copyDest = copyDest;
        this.copy = copy;
        this.fileSource = fileSource;
        this.srcToken = srcToken;
        this.destToken = destToken;
    }

    public static List<AEWizProject> getAllEnums() {
        return Arrays.stream(values()).toList();
    }

    public static List<AEWizProject> getAllNewProject() {
        ArrayList<AEWizProject> lista = new ArrayList<>();

        for (AEWizProject wiz : AEWizProject.values()) {
            if (wiz.isNew()) {
                lista.add(wiz);
            }

        }
        return lista;
    }

    //    /**
    //     * Ripristina il valore di default <br>
    //     */
    //    public static void reset() {
    //        for (AEWizProject aeCheck : AEWizProject.values()) {
    //            aeCheck.acceso = false;
    //        }
    //    }


    public boolean isNew() {
        return accesoNew && copyDest != null && copyDest.length() > 0;
    }

    public boolean isUpdate() {
        return accesoUpdate;
    }


    public String getCaption() {
        return caption;
    }

    //    public boolean isAccesoInizialmente() {
    //        return accesoInizialmente;
    //    }

    public String getCopyDest() {
        return copyDest;
    }

    public String getNomeFile() {
        return copyDest;
    }

    public AECopy getCopy() {
        return copy;
    }

    public String getSrcToken() {
        return srcToken;
    }

    public String getDestToken() {
        return destToken;
    }

    public String getFileSource() {
        return fileSource;
    }
}

