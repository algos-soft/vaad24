package it.algos.vaad24.backend.configuration;

import it.algos.vaad24.backend.boot.*;
import it.algos.vaad24.backend.service.*;
import org.springframework.context.annotation.*;

/**
 * Project vaad24
 * Created by Algos
 * User: gac
 * Date: Mon, 02-Jan-2023
 * Time: 11:37
 * Normally Configuration class gets scanned and instantiated first.
 * This has to be the starting point to know about other configurations and beans.
 */
@Configuration
public class VaadConfiguration {

    static {
        LogService.debugBean(new VaadCost());
        LogService.debugBean(new VaadData());
        LogService.debugBean(new VaadPref());
        LogService.debugBean(new VaadVar());
        LogService.debugBean(new VaadVers());
        LogService.debugBean(new VaadCost());
        LogService.debugBean(new VaadCost());
        LogService.debugBean(new AnnotationService());
        LogService.debugBean(new ArrayService());
        LogService.debugBean(new ClassService());
        LogService.debugBean(new ColumnService());
        LogService.debugBean(new FileService());
        LogService.debugBean(new HtmlService());
        LogService.debugBean(new MailService());
        LogService.debugBean(new MathService());
        LogService.debugBean(new PreferenceService());
        LogService.debugBean(new ReflectionService());
        LogService.debugBean(new RegexService());
        LogService.debugBean(new ResourceService());
        LogService.debugBean(new RouteService());
        LogService.debugBean(new TextService());
        LogService.debugBean(new UtilityService());
        LogService.debugBean(new WebService());
    }

}
