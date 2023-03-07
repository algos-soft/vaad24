package it.algos.vaad24.backend.service;

import it.algos.vaad24.backend.entity.*;
import org.springframework.beans.factory.config.*;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.*;

import java.lang.reflect.*;
import java.util.*;

/**
 * Project vaad24
 * Created by Algos
 * User: gac
 * Date: Sun, 05-Mar-2023
 * Time: 14:00
 * <p>
 * Classe di libreria; NON deve essere astratta, altrimenti SpringBoot non la costruisce <br>
 * Estende la classe astratta AbstractService che mantiene i riferimenti agli altri services <br>
 * L'istanza può essere richiamata con: <br>
 * 1) StaticContextAccessor.getBean(BeanService.class); <br>
 * 3) @Autowired public BeanService annotation; <br>
 * <p>
 * Annotated with @Service (obbligatorio, se si usa la catena @Autowired di SpringBoot) <br>
 * NOT annotated with @SpringComponent (inutile, esiste già @Service) <br>
 * Annotated with @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON) (obbligatorio) <br>
 */
@Service
@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
public class BeanService extends AbstractService {

    /**
     * Copia i valori delle property da un bean all'altro. <br>
     * Le due entity possono essere delle stesso tipo oppure no <br>
     * Esegue per tutte le property presenti in entrami i tipi di Entity <br>
     *
     * @param entityBeanOld da cui recuperare i valori delle property che corrispondono
     * @param entityBeanNew in cui inserire i valori delle property che corrispondono
     *
     * @return the status
     */
    public boolean copiaAncheID(final AEntity entityBeanOld, AEntity entityBeanNew) {
        boolean status = false;
        List<Field> oldFields = reflectionService.getAllSuperClassFields(entityBeanOld.getClass());
        Object value;
        String nome;

        if (oldFields != null) {
            for (Field field : oldFields) {
                nome = field.getName();
                if (reflectionService.isEsiste(entityBeanNew.getClass(), nome)) {
                    value = reflectionService.getPropertyValue(entityBeanOld, nome);
                    reflectionService.setPropertyValue(entityBeanNew, nome, value);
                }
            }
        }

        return status;
    }

}