package it.algos.vaad24.backend.service;

import static it.algos.vaad24.backend.boot.VaadCost.*;
import it.algos.vaad24.backend.exception.*;
import it.algos.vaad24.backend.wrapper.*;
import org.springframework.stereotype.*;

import java.io.*;
import java.util.*;
import java.util.jar.*;
import java.util.stream.*;
import java.util.zip.*;

/**
 * Project vaad24
 * Created by Algos
 * User: gac
 * Date: Sat, 15-Apr-2023
 * Time: 11:40
 * <p>
 * Classe di libreria; NON deve essere astratta, altrimenti SpringBoot non la costruisce <br>
 * Estende la classe astratta AbstractService che mantiene i riferimenti agli altri services <br>
 * L'istanza può essere richiamata con: <br>
 * 1) StaticContextAccessor.getBean(JarFileService.class); <br>
 * 3) @Autowired public JarFileService annotation; <br>
 * <p>
 * Annotated with @Service (obbligatorio, se si usa la catena @Autowired di SpringBoot) <br>
 * NOT annotated with @SpringComponent (inutile, esiste già @Service) <br>
 * Annotated with @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON) (obbligatorio) <br>
 */
@Service
public class JarFileService extends AbstractService {

    @Deprecated
    public static Set<String> getClassNamesFromJarFile(File givenFile) throws IOException {
        Set<String> classNames = new HashSet<>();
        try (JarFile jarFile = new JarFile(givenFile)) {
            Enumeration<JarEntry> e = jarFile.entries();
            while (e.hasMoreElements()) {
                JarEntry jarEntry = e.nextElement();
                if (jarEntry.getName().endsWith(".class")) {
                    String className = jarEntry.getName()
                            .replace("/", ".")
                            .replace(".class", "");
                    classNames.add(className);
                }
            }
            return classNames;
        }
    }


    public List<String> getAllClassNames(String pathToJarFile) {
        List<String> classNames = new ArrayList<String>();
        String className;

        try {
            ZipInputStream zip = new ZipInputStream(new FileInputStream(pathToJarFile));
            for (ZipEntry entry = zip.getNextEntry(); entry != null; entry = zip.getNextEntry()) {
                if (!entry.isDirectory() && entry.getName().endsWith(".class")) {
                    // This ZipEntry represents a class. Now, what class does it represent?
                    className = entry.getName().replace('/', '.'); // including ".class"
                    classNames.add(className.substring(0, className.length() - ".class".length()));
                }
            }
        } catch (Exception unErrore) {
            logService.error(new WrapLog().exception(new AlgosException(unErrore)));
        }

        return classNames;
    }

    public List<String> getBootClassNames(String pathToJarFile) {
        List<String> allClassNames = getAllClassNames(pathToJarFile);
        String prefix = "org.springframework.boot.loader";
        return allClassNames.stream().filter(nome -> !nome.startsWith(prefix)).collect(Collectors.toList());
    }

    public List<String> getBootClassNamesWithoutMethods(String pathToJarFile) {
        List<String> classNames = getBootClassNames(pathToJarFile);
        String dollar = ".*\\$.*";
        return classNames.stream().filter(nome -> !nome.matches(dollar)).collect(Collectors.toList());
    }

    public List<String> getModuloClassNames(String pathToJarFile, String nomeModulo) {
        List<String> classNames = getBootClassNamesWithoutMethods(pathToJarFile);
        String filtro = "BOOT-INF.classes.it.algos." + nomeModulo+".*";
        return classNames.stream().filter(nome -> nome.matches(filtro)).collect(Collectors.toList());
    }

    public List<String> getPackageClassNames(String pathToJarFile, String nomeModulo) {
        List<String> classNames = getModuloClassNames(pathToJarFile, nomeModulo);
        String filtro = "BOOT-INF.classes.it.algos." + nomeModulo + ".backend.packages.*";
        return classNames.stream().filter(nome -> nome.matches(filtro)).collect(Collectors.toList());
    }

    public List<String> getBackendClassNames(String pathToJarFile, String nomeModulo) {
        List<String> classNames = getPackageClassNames(pathToJarFile, nomeModulo);
        String filtro = ".*Backend";
        return classNames.stream().filter(nome -> nome.matches(filtro)).collect(Collectors.toList());
    }

    public Class getClazzFromName(String className) {
        Class clazz = null;
        String message;

        if (textService.isEmpty(className)) {
            return null;
        }

        if (className.endsWith(JAVA_SUFFIX)) {
            className = textService.levaCoda(className, JAVA_SUFFIX);
        }

        try {
            clazz = Class.forName(className);
        } catch (Exception unErrore) {
            message = String.format("Non esiste la classe [%s] nella directory package", className);
            logService.info(new WrapLog().exception(new AlgosException(message)));
        }

        return clazz;
    }

}