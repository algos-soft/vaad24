package it.algos.vaad24.backend.service;

import static it.algos.vaad24.backend.boot.VaadCost.*;
import it.algos.vaad24.backend.entity.*;
import it.algos.vaad24.backend.exception.*;
import it.algos.vaad24.backend.wrapper.*;
import org.springframework.beans.factory.config.*;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.*;

import javax.persistence.*;
import java.lang.reflect.*;
import java.net.*;
import java.util.*;
import java.util.stream.*;

/**
 * Project vaadin23
 * Created by Algos
 * User: gac
 * Date: mer, 09-mar-2022
 * Time: 21:13
 * <p>
 * Classe di libreria; NON deve essere astratta, altrimenti SpringBoot non la costruisce <br>
 * Estende la classe astratta AbstractService che mantiene i riferimenti agli altri services <br>
 * L'istanza può essere richiamata con: <br>
 * 1) StaticContextAccessor.getBean(ReflectionService.class); <br>
 * 3) @Autowired public ReflectionService annotation; <br>
 * <p>
 * Annotated with @Service (obbligatorio, se si usa la catena @Autowired di SpringBoot) <br>
 * NOT annotated with @SpringComponent (inutile, esiste già @Service) <br>
 * Annotated with @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON) (obbligatorio) <br>
 */
@Service
@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
public class ReflectionService extends AbstractService {


    /**
     * Field statico di una classe generica. <br>
     *
     * @param genericClazz    da cui estrarre il field statico
     * @param publicFieldName property statica e pubblica
     *
     * @return the field
     */
    public Field getField(final Class<?> genericClazz, final String publicFieldName) {
        Field field = null;
        String propertyName = publicFieldName;

        try {
            propertyName = propertyName.replaceAll(FIELD_NAME_ID_CON, FIELD_NAME_ID_SENZA);
            field = genericClazz.getField(propertyName);
        } catch (Exception unErrore) {
            logger.error(new WrapLog().exception(unErrore).usaDb());
        }

        return field;
    }


    /**
     * Valore della property corrente di una entity. <br>
     *
     * @param entityBean      oggetto su cui operare la riflessione
     * @param publicFieldName property statica e pubblica
     *
     * @return the property value
     */
    public Object getPropertyValue(final AEntity entityBean, final String publicFieldName) {
        Object value = null;
        List<Field> fieldsList = null;

        if (entityBean == null || textService.isEmpty(publicFieldName)) {
            return null;
        }

        fieldsList = getAllFields(entityBean.getClass());

        try {
            for (Field field : fieldsList) {
                if (field.getName().equals(publicFieldName)) {
                    field.setAccessible(true);
                    value = field.get(entityBean);
                    Object beta = field.getType();
                }
            }
        } catch (Exception unErrore) {
            logger.error(new WrapLog().exception(new AlgosException(unErrore)).usaDb());
        }

        return value;
    }


    /**
     * Valore stringa della property corrente di una entity. <br>
     *
     * @param entityBean      oggetto su cui operare la riflessione
     * @param publicFieldName property statica e pubblica
     *
     * @return the string value
     */
    public String getPropertyValueStr(final AEntity entityBean, final String publicFieldName) {
        String value = VUOTA;
        Object objValue = getPropertyValue(entityBean, publicFieldName);

        if (objValue != null) {
            if (objValue instanceof String) {
                value = (String) objValue;
            }
            else {
                value = objValue.toString();
            }
        }

        return value;
    }


    /**
     * Lista dei fields statici PUBBLICI dichiarati in una classe di tipo AEntity. <br>
     * Controlla che il parametro in ingresso non sia nullo <br>
     * Ricorsivo. Comprende la entity e tutte le sue superClassi (fino a ACEntity e AEntity) <br>
     * Esclusi i fields: PROPERTY_SERIAL, PROPERT_NOTE, PROPERTY_CREAZIONE, PROPERTY_MODIFICA <br>
     * Esclusi i fields PRIVATI <br>
     * Fields NON ordinati <br>
     * Class.getDeclaredFields() prende fields pubblici e privati della classe <br>
     * Class.getFields() prende fields pubblici della classe e delle superClassi <br>
     * Nomi NON ordinati <br>
     * ATTENZIONE - Comprende ANCHE eventuali fields statici pubblici che NON siano property per il DB <br>
     *
     * @param entityClazz da cui estrarre i fields statici
     *
     * @return lista di static fields della Entity e di tutte le sue superclassi
     */
    public List<Field> getAllFields(Class<? extends AEntity> entityClazz) {
        List<Field> listaFields = null;
        Field[] fieldsArray;

        if (entityClazz == null) {
            logger.error(new WrapLog().exception(new AlgosException("Manca la entityClazz")).usaDb());
        }

        if (!AEntity.class.isAssignableFrom(entityClazz)) {
            logger.error(new WrapLog().exception(new AlgosException(String.format("La classe %s non è una classe di tipo AEntity", entityClazz.getSimpleName()))).usaDb());
        }

        //--recupera tutti i fields della entity e di tutte le superClassi
        fieldsArray = entityClazz.getFields();
        if (fieldsArray != null) {
            listaFields = new ArrayList<>();
            for (Field field : fieldsArray) {
                if (ESCLUSI_SEMPRE.contains(field.getName())) {
                    continue;
                }
                //                if (field.getName().equalsIgnoreCase(PROPERTY_NOTE) && !annotationService.usaNote(entityClazz)) {
                //                    continue;
                //                }
                //                if (field.getName().equalsIgnoreCase(PROPERTY_CREAZIONE) && !annotationService.usaTimeStamp(entityClazz)) {
                //                    continue;
                //                }
                //                if (field.getName().equalsIgnoreCase(PROPERTY_MODIFICA) && !annotationService.usaTimeStamp(entityClazz)) {
                //                    continue;
                //                }
                listaFields.add(field);
            }
        }

        return listaFields;
    }

    public List<Field> getDeclaredFields(Class<? extends AEntity> entityClazz) {
        List<Field> listaFields = null;
        Field[] fieldsArray;

        if (entityClazz == null) {
            logger.error(new WrapLog().exception(new AlgosException("Manca la entityClazz")).usaDb());
        }

        if (!AEntity.class.isAssignableFrom(entityClazz)) {
            logger.error(new WrapLog().exception(new AlgosException(String.format("La classe %s non è una classe di tipo AEntity", entityClazz.getSimpleName()))).usaDb());
        }

        //--recupera tutti i fields della entity e di tutte le superClassi
        fieldsArray = entityClazz.getDeclaredFields();
        if (fieldsArray != null) {
            listaFields = new ArrayList<>();
            for (Field field : fieldsArray) {
//                Object alfa=field.getAnnotatedType();
                Object beta= field.getDeclaredAnnotations();
                Object alfa= field.getDeclaredAnnotation(Transient.class);

                listaFields.add(field);
            }
        }

        return listaFields;
    }

    /**
     * Se esiste il field della Entity
     *
     * @param entityClazz     classe su cui operare la riflessione
     * @param publicFieldName property
     *
     * @return true se esiste
     */
    public boolean isEsiste(Class<? extends AEntity> entityClazz, final String publicFieldName) {
        try {
            return entityClazz.getField(publicFieldName) != null;
        } catch (Exception unErrore) {
        }
        return false;
    }


    public boolean isJarRunning() {
        String message;
        URL url = this.getClass().getResource(VUOTA);

        if (url == null) {
            message = "Url malformato";
            logger.info(new WrapLog().message(String.format(message)).usaDb());
            return false;
        }

        if (url.toString().startsWith(REFLECTION_JAR)) {
            return true;
        }
        else {
            if (!url.toString().startsWith(REFLECTION_FILE)) {
                message = String.format("Url errato %s%s", FORWARD, url.getProtocol());
                logger.info(new WrapLog().message(String.format(message)).usaDb());
            }
            return false;
        }
    }


    public List<Method> getMetodi(Class clazz) {
        return Arrays.stream(clazz.getDeclaredMethods()).collect(Collectors.toList());
    }


    public List<String> getMetodiName(Class clazz) {
        return getMetodi(clazz).stream().map(method -> method.getName()).collect(Collectors.toList());
    }

    public boolean isEsisteMetodo(String publicClassName, String publicMethodName) {
        Class clazz = null;

        if (textService.isEmpty(publicClassName) || textService.isEmpty(publicMethodName)) {
            return false;
        }
        publicClassName = textService.slashToPoint(publicClassName);
        clazz = classService.getClazzFromName(publicClassName);

        return clazz != null && isEsisteMetodo(clazz, publicMethodName);
    }

    public boolean isEsisteMetodo(Class clazz, String publicMethodName) {
        List<String> nomiMetodi = getMetodiName(clazz);
        return nomiMetodi.contains(publicMethodName);
    }

    public boolean isEsisteMetodoSenzaParametri(Class clazz, String publicMethodName) {
        return isEsisteMetodoConParametri(clazz, publicMethodName, 0);
    }

    public boolean isEsisteMetodoConParametri(Class clazz, String publicMethodName) {
        return isEsisteMetodoConParametri(clazz, publicMethodName, -1);
    }


    public List<Method> getMetodiByName(Class clazz, String publicMethodName) {
        List<Method> metodi = new ArrayList<>();
        List<Method> metodiAll = getMetodi(clazz);

        for (Method method : metodiAll) {
            if (method.getName().equals(publicMethodName)) {
                metodi.add(method);
            }
        }
        return metodi;
    }

    public boolean isEsisteMetodoConParametri(Class clazz, String publicMethodName, int parametri) {
        List<Method> metodi = getMetodiByName(clazz,publicMethodName);

        for (Method method : metodi) {
            if (method.getName().equals(publicMethodName)) {
                if (parametri == -1 || method.getParameterCount() == parametri) {
                    return true;
                }
            }
        }
        return false;
    }

    public Method getMetodo(Class clazz, String publicMethodName) {
        List<Method> metodi = getMetodi(clazz);

        for (Method method : metodi) {
            if (method.getName().equals(publicMethodName)) {
                return method;
            }
        }

        return null;
    }


    public boolean isEsisteMetodoAncheSovrascritto(String publicClassName, String publicMethodName) {
        Class clazz = null;
        Method[] methods = null;
        List<String> nomiMetodi;

        if (textService.isEmpty(publicClassName) || textService.isEmpty(publicMethodName)) {
            return false;
        }
        publicClassName = textService.slashToPoint(publicClassName);
        publicMethodName = textService.primaMinuscola(publicMethodName);

        try {
            clazz = Class.forName(publicClassName.toString());
        } catch (Exception unErrore) {
            logger.info(new WrapLog().exception(AlgosException.crea(unErrore)));
        }
        if (clazz == null) {
            return false;
        }

        try {
            methods = clazz.getMethods();
        } catch (Exception unErrore) {
            logger.info(new WrapLog().exception(AlgosException.crea(unErrore)));
        }

        nomiMetodi = Arrays.stream(methods).map(method -> method.getName()).collect(Collectors.toList());
        return nomiMetodi.contains(publicMethodName);
    }

    //    public boolean esegueMetodo(String publicClassName, String publicMethodName) {
    //        boolean eseguito = false;
    //        Class clazz = null;
    //        Method method;
    //        Object istanza;
    //
    //        if (!isEsisteMetodoAncheSovrascritto(publicClassName, publicMethodName)) {
    //            return false;
    //        }
    //        publicClassName = textService.slashToPoint(publicClassName);
    //        publicMethodName = textService.primaMinuscola(publicMethodName);
    //
    //        try {
    //            clazz = Class.forName(publicClassName.toString());
    //        } catch (Exception unErrore) {
    //            logger.info(new WrapLog().exception(AlgosException.crea(unErrore)));
    //        }
    //        if (clazz == null) {
    //            return false;
    //        }
    //
    //        try {
    //            method = clazz.getMethod(publicMethodName);
    //            istanza = appContext.getBean(clazz);
    //            eseguito = (Boolean) method.invoke(istanza);
    //        } catch (Exception unErrore) {
    //            logger.error(new WrapLog().exception(new AlgosException(unErrore)).usaDb());
    //        }
    //
    //        return eseguito;
    //    }


    /**
     * Valore della property di una classe
     *
     * @param entityBean      oggetto su cui operare la riflessione
     * @param publicFieldName property statica e pubblica
     * @param value           da inserire nella property
     */
    public boolean setPropertyValue(final AEntity entityBean, final String publicFieldName, final Object value) {
        boolean status = false;
        Field field = getField(entityBean.getClass(), publicFieldName);

        if (field != null) {
            try {
                field.set(entityBean, value);
                status = true;
            } catch (Exception unErrore) {
                logger.error(new WrapLog().exception(new AlgosException(unErrore)).usaDb());
            }
        }

        return status;
    }

}