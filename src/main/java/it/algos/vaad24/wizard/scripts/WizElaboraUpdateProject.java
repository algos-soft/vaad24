package it.algos.vaad24.wizard.scripts;

import com.vaadin.flow.component.checkbox.*;
import com.vaadin.flow.spring.annotation.*;
import static it.algos.vaad24.backend.boot.VaadCost.*;
import it.algos.vaad24.backend.boot.*;
import it.algos.vaad24.backend.enumeration.*;
import it.algos.vaad24.backend.exception.*;
import it.algos.vaad24.backend.wrapper.*;
import it.algos.vaad24.wizard.enumeration.*;
import static it.algos.vaad24.wizard.scripts.WizElaboraNewProject.*;
import org.springframework.beans.factory.config.*;
import org.springframework.context.annotation.Scope;

import java.util.*;

/**
 * Project vaadbio
 * Created by Algos
 * User: gac
 * Date: mer, 13-apr-2022
 * Time: 06:49
 */
@SpringComponent
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class WizElaboraUpdateProject extends WizElabora {

    public WizElaboraUpdateProject() {
        super();
    }// end of constructor

    public WizElaboraUpdateProject(String updateProject) {
        super();
    }// end of constructor

    public void esegue(final LinkedHashMap<String, Checkbox> mappaCheckbox) {
        AResult result = null;
        super.progettoEsistente = true;
        AEWizProject wiz;
        String path;
        String desc;
        AETypeResult type;
        String message;
        destNewProject = System.getProperty("user.dir");
        newUpdateProject = fileService.estraeClasseFinaleSenzaJava(destNewProject).toLowerCase();
        srcVaad24 = textService.levaCoda(destNewProject, newUpdateProject);
        srcVaad24 += VAADIN_PROJECT + SLASH;
        if (srcVaad24.contains("tutorial")) {
            srcVaad24 = "/Users/gac/Documents/IdeaProjects/operativi/vaadin23/";
        }

        destNewProject += SLASH;

        super.esegue();

        for (String key : mappaCheckbox.keySet()) {
            if (mappaCheckbox.get(key).getValue()) {
                wiz = AEWizProject.valueOf(key);
                switch (wiz.getCopy().getType()) {
                    case modulo -> {
                        directory(wiz);
                        eliminaSources();
                    }
                    case directory -> directory(wiz);
                    case file -> file(wiz);
                    case source -> source(wiz);
                    case elaboraFile, elaboraDir -> elabora(wiz);
                }
            }

        }
    }


    public AResult elabora(final AEWizProject wiz) {
        AResult result = null;
        String srcPath = srcVaad24 + wiz.getCopyDest() + SLASH;
        String destPath = destNewProject + wiz.getCopyDest() + SLASH;
        String dir = fileService.lastDirectory(destPath).toLowerCase();
        String oldToken = APPLICATION_VAADIN24;
        String newToken = VaadVar.projectCurrentMainApplication;
        String tag = progettoEsistente ? "Update" : "New";
        String oldText;

        switch (wiz) {
            case pomVaad24 -> {
                result = fileService.copyFile(AECopy.fileModifyEver, srcVaad24, destNewProject, wiz.getNomeFile());
                oldToken = PROJECT_VAADIN24;
                newToken = VaadVar.projectCurrent;
                result = fixToken(result, wiz, oldToken, newToken);
                mostraRisultato(result, AECopy.dirFilesModifica, dir, tag);
            }
            case testService, testBackend -> {
                result = fileService.copyDirectory(AECopy.dirFilesModifica, srcPath, destPath, oldToken, newToken);
                result = fixToken(result, wiz, oldToken, newToken);
                mostraRisultato(result, AECopy.dirFilesModifica, dir, tag);
            }
            case banner -> {
                oldToken = VaadVar.vaadin24BannerTitle;
                newToken = VaadVar.projectBannerTitle;
                result = fileService.copyFile(AECopy.fileModifyEver, srcVaad24, destNewProject, wiz.getNomeFile(), oldToken, newToken);
                logger.copy(result.typeLog(AETypeLog.wizard));
            }

            default -> {}
        }

        return result;
    }

    public AResult fixToken(AResult result, AEWizProject wiz, String oldToken, String newToken) {
        String testoBase;
        String testoSostituito;
        String path;
        String destPath = result.getTarget();
        Map<String, List> resultMap;
        List<String> allFiles = new ArrayList<>();
        AECopy copy = wiz.getCopy();
        boolean status;

        if (result == null || result.isErrato()) {
            logger.warn(AETypeLog.file, new AlgosException(result.getErrorMessage()));
            return result;
        }

        if (copy == AECopy.elaboraDir) {
            resultMap = result.getMappa();
            if (resultMap == null) {
                logger.warn(AETypeLog.file, new AlgosException(result.getErrorMessage()));
                return result;
            }

            if (resultMap.get(AEKeyMapFile.aggiuntiNuovi.name()) != null) {
                allFiles.addAll(resultMap.get(AEKeyMapFile.aggiuntiNuovi.name()));
            }
            if (resultMap.get(AEKeyMapFile.tokenUguali.name()) != null) {
                allFiles.addAll(resultMap.get(AEKeyMapFile.tokenUguali.name()));
            }
            if (resultMap.get(AEKeyMapFile.tokenModificati.name()) != null) {
                allFiles.addAll(resultMap.get(AEKeyMapFile.tokenModificati.name()));
            }

            if (allFiles != null) {
                for (String nomeFile : allFiles) {
                    path = destPath + nomeFile;
                    result = fixToken(path, oldToken, newToken, true);
                }
            }
        }
        else {
            result = fixToken(destPath, oldToken, newToken, false);
        }

        return result;
    }


    public AResult fixToken(String path, String oldToken, String newToken, boolean creaDir) {
        String testoBase;
        String testoSostituito;
        boolean status;

        testoBase = fileService.leggeFile(path);
        testoSostituito = textService.sostituisce(testoBase, oldToken, newToken);
        status = fileService.sovraScriveFileDir(path, testoSostituito, creaDir);
        if (testoSostituito.equals(testoBase)) {
            int a = 87;
        }

        return AResult.valido();
    }

    public void eliminaSources() {
        String message;

        //--elimina la directory 'sources' che deve restare unicamente nel progetto 'vaadin23' e non nei derivati
        if (fileService.isEsisteDirectory(destNewProject + SOURCE_PREFIX + VAADIN_MODULE + SOURCE_SUFFFIX)) {
            if (fileService.deleteDirectory(destNewProject + SOURCE_PREFIX + VAADIN_MODULE + SOURCE_SUFFFIX).isValido()) {
                message = String.format("Delete: cancellata la directory 'sources' dal progetto %s", newUpdateProject);
                logger.info(new WrapLog().message(message).type(AETypeLog.wizard));
            }
            else {
                message = String.format("Non sono riuscito a cancellare la directory 'sources' dal progetto %s", newUpdateProject);
                logger.warn(new WrapLog().message(message).type(AETypeLog.wizard));
            }
        }
    }

}