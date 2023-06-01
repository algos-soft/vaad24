package it.algos.vaad24.backend.service;

import com.vaadin.flow.component.*;
import com.vaadin.flow.component.datepicker.*;
import com.vaadin.flow.component.datetimepicker.*;
import com.vaadin.flow.component.textfield.*;
import com.vaadin.flow.component.timepicker.*;
import it.algos.vaad24.backend.enumeration.*;
import it.algos.vaad24.backend.fields.*;
import org.springframework.beans.factory.config.*;
import org.springframework.stereotype.*;

import java.lang.reflect.*;

/**
 * Project vaad24
 * Created by Algos
 * User: gac
 * Date: Sat, 27-May-2023
 * Time: 19:54
 * <p>
 * Classe di libreria; NON deve essere astratta, altrimenti SpringBoot non la costruisce <br>
 * Estende la classe astratta AbstractService che mantiene i riferimenti agli altri services <br>
 * L'istanza può essere richiamata con: <br>
 * 1) StaticContextAccessor.getBean(FieldService.class); <br>
 * 3) @Autowired public FieldService annotation; <br>
 * <p>
 * Annotated with @Service (obbligatorio, se si usa la catena @Autowired di SpringBoot) <br>
 * NOT annotated with @SpringComponent (inutile, esiste già @Service) <br>
 * Annotated with @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON) (obbligatorio) <br>
 */
@Service
@org.springframework.context.annotation.Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
public class FieldService extends AbstractService {

    public AbstractField crea(Class entityClazz, String propertyName) {
        AbstractField field = null;
        Field reflectionJavaField = creaReflectionField(entityClazz, propertyName);

        if (reflectionJavaField != null) {
            field = creaOnly(reflectionJavaField);
        }

        return field;
    }


    /**
     * Create a single reflection field.
     *
     * @param entityClazz  di riferimento
     * @param propertyName del field
     */
    public Field creaReflectionField(Class entityClazz, String propertyName) {
        return reflectionService.getField(entityClazz, propertyName);
    }

    /**
     * Create a single field.
     *
     * @param reflectionJavaField di riferimento
     */
    public AbstractField creaOnly(Field reflectionJavaField) {
        AbstractField field;
        AETypeField type = annotationService.getFormType(reflectionJavaField);

        field = switch (type) {
            case text:
            case phone:
                yield new TextField();
            case enumType:
                yield new AComboField();
            case localDateTime:
                yield new DateTimePicker();
            case localDate:
                yield new DatePicker();
            case localTime:
                yield new TimePicker();
            default:
                yield null;
        };

        return field;
    }

}