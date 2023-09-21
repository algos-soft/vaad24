package it.algos.vaad24.backend.layer;

import com.vaadin.flow.spring.annotation.*;
import it.algos.vaad24.backend.entity.*;
import it.algos.vaad24.backend.enumeration.*;
import it.algos.vaad24.backend.service.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.beans.factory.config.*;
import org.springframework.context.annotation.Scope;
import org.springframework.data.domain.*;
import org.springframework.data.mongodb.core.query.*;

import javax.annotation.*;
import java.util.*;

/**
 * Project wiki24
 * Created by Algos
 * User: gac
 * Date: Sun, 27-Aug-2023
 * Time: 13:49
 */
@SpringComponent
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class Filtro {


    /**
     * Istanza unica di una classe @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON) di servizio <br>
     * Iniettata automaticamente dal framework SpringBoot/Vaadin con l'Annotation @Autowired <br>
     */
    @Autowired
    public TextService textService;

    /**
     * Istanza unica di una classe @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON) di servizio <br>
     * Iniettata automaticamente dal framework SpringBoot/Vaadin con l'Annotation @Autowired <br>
     */
    @Autowired
    public AnnotationService annotationService;

    /**
     * Istanza unica di una classe @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON) di servizio <br>
     * Iniettata automaticamente dal framework SpringBoot/Vaadin con l'Annotation @Autowired <br>
     */
    @Autowired
    public ReflectionService reflectionService;


    //    private Criteria criteria;
    //
    //    private Sort sort;
    //
    //    private String tag;
    LinkedHashMap<String, FiltroSingolo> mappaFiltri;

    private Class<? extends AEntity> entityClazz;

    //    private AETypeFilter type;
    //
    //    private String propertyField;
    //
    //    private Object propertyValue;

    //    public Filtro() {
    //    }

    public Filtro(Class<? extends AEntity> entityClazz) {
        this.entityClazz = entityClazz;
    }

    @PostConstruct
    private void postConstruct() {
        mappaFiltri = new LinkedHashMap<>();
    }

    //    public Filtro(Criteria criteria, Sort sort) {
    //        this.criteria = criteria;
    //        this.sort = sort;
    //    }


    /**
     * Pattern Builder <br>
     */
    public Filtro add(String propertyField, Object propertyValue) {
        return add(propertyField, propertyValue, AETypeFilter.uguale);
    }

    /**
     * Pattern Builder <br>
     */
    public Filtro add(String propertyField, Object propertyValue, AETypeFilter type) {
        return add(propertyField, propertyValue, type, null);
    }

    /**
     * Pattern Builder <br>
     */
    public Filtro add(String propertyField, Object propertyValue, AETypeFilter type, Sort sort) {
        FiltroSingolo filtroSingolo = new FiltroSingolo(propertyField, propertyValue, type, sort);
        mappaFiltri.put(propertyField, filtroSingolo);
        return this;
    }

    public Query getQuery() {
        Query query = new Query();
        FiltroSingolo filtroSingolo;
        AETypeFilter type;
        Criteria criteria;

        if (mappaFiltri != null) {
            for (String key : mappaFiltri.keySet()) {
                filtroSingolo = mappaFiltri.get(key);
                type = filtroSingolo.getType();
                criteria = type.getCriteria(filtroSingolo.getPropertyField(), filtroSingolo.getPropertyValue());
                query.addCriteria(criteria);
            }
        }

        return query;
    }

    //    public Filtro(final Class<? extends AEntity> entityClazz) {
    //        this.entityClazz = entityClazz;
    //    }

    //    public Filtro(final Class<? extends AEntity> entityClazz, AETypeFilter type, String propertyField, Object propertyValue) {
    //        this.entityClazz = entityClazz;
    //        this.type = type;
    //        this.propertyField = propertyField;
    //        this.propertyValue = propertyValue;
    //    }

//    public static Filtro start(String fieldName, String value) {
//        Filtro filtro = new Filtro();
//
//        String questionPattern = "^" + Pattern.quote(value) + ".*";
//        Criteria criteria = Criteria.where(fieldName).regex(questionPattern, "i");
//        filtro.criteria = criteria;
//
//        return filtro;
//    }

//    public static Filtro contains(String fieldName, String value) {
//        Filtro filtro = new Filtro();
//
//        String questionPattern = ".*" + Pattern.quote(value) + ".*";
//        Criteria criteria = Criteria.where(fieldName).regex(questionPattern, "i");
//        filtro.criteria = criteria;
//
//        return filtro;
//    }

    //    public static Filtro ugualeStr(String fieldName, String value) {
    //        return new Filtro(TypeFilter.uguale.getCriteria(fieldName, value));
    //    }

//    public static Filtro ugualeObj(String fieldName, Object value) {
//        Filtro filtro = new Filtro();
//        Criteria criteria;
//
//        if (AEntity.class.isAssignableFrom(value.getClass())) {
//            fieldName += FIELD_NAME_ID_LINK;
//            value = ((AEntity) value).id;
//        }
//
//        criteria = Criteria.where(fieldName).is(value);
//        //        filtro.criteria = criteria;
//
//        return filtro;
//    }

//    public static Filtro vero(String fieldName) {
//        return booleano(fieldName, true);
//    }
//
//    public static Filtro falso(String fieldName) {
//        return booleano(fieldName, false);
//    }

//    public static Filtro booleano(String fieldName, boolean value) {
//        Filtro filtro = new Filtro();
//
//        Criteria criteria = Criteria.where(fieldName).is(value);
//        //        filtro.criteria = criteria;
//
//        return filtro;
//    }

    //    public static Filtro checkBox3Vie(String fieldName, Object value) {
    //        if (value != null && value instanceof Boolean booleanValue) {
    //            return Filtro.booleano(fieldName, booleanValue);
    //        }
    //        else {
    //            return new Filtro();
    //        }
    //    }

//    public Filtro regola() throws AlgosException {
//        String message;
//        String keyField;
//
//        if (entityClazz == null) {
//            //            throw AlgosException.stack("Manca la entityClazz", this.getClass(), "regola");
//        }
//
//        if (!AEntity.class.isAssignableFrom(entityClazz)) {
//            //            throw AlgosException.stack(String.format("La entityClazz %s non è una classe valida", entityClazz.getSimpleName()), WrapFiltri.class, "regola");
//        }
//
//        if (type == null) {
//            //            throw AlgosException.stack("Manca la tipologia del filtro", this.getClass(), "regola");
//        }
//
//        if (textService.isEmpty(propertyField)) {
//            //            throw AlgosException.stack("Manca la propertyField del filtro", this.getClass(), "regola");
//        }
//
//        propertyField = textService.levaCoda(propertyField, FIELD_NAME_ID_LINK);
//        keyField = propertyField;
//
//        //        if (!reflectionService.isEsisteFieldOnSuperClass(entityClazz, propertyField)) {
//        //            message = String.format("La entityClazz %s esiste ma non esiste la property %s", entityClazz.getSimpleName(), propertyField);
//        ////            throw AlgosException.stack(message, this.getClass(), "regola");
//        //        }
//
//        if (propertyValue == null) {
//            //            throw AlgosException.stack("Manca la propertyValue del filtro", this.getClass(), "regola");
//        }
//
//        if (annotationService.isDBRef(entityClazz, propertyField)) {
//            propertyField += FIELD_NAME_ID_LINK;
//            type = AETypeFilter.link;
//        }
//
//        switch (type) {
//            case uguale:
//                if (propertyValue instanceof String) {
//                    criteria = AETypeFilter.uguale.getCriteria(propertyField, (String) propertyValue);
//                }
//                else {
//                    criteria = AETypeFilter.uguale.getCriteria(propertyField, propertyValue);
//                }
//                break;
//            case inizia:
//                criteria = AETypeFilter.inizia.getCriteria(propertyField, propertyValue);
//                break;
//            case contiene:
//                criteria = AETypeFilter.contiene.getCriteria(propertyField, propertyValue);
//                break;
//            case link:
//                if (!propertyField.endsWith(FIELD_NAME_ID_LINK)) {
//                    propertyField += FIELD_NAME_ID_LINK;
//                }
//                if (propertyValue != null && propertyValue instanceof AEntity) {
//                    propertyValue = ((AEntity) propertyValue).id;
//                }
//                criteria = Criteria.where(propertyField).is(propertyValue);
//                break;
//            default:
//                //                throw AlgosException.stack(String.format("Manca il filtro %s nello switch", type), this.getClass(), "regola");
//        }
//
//        return this;
//    }

//    public Criteria getCriteria() {
//        return criteria;
//    }

//    public Sort getSort() {
//        return sort;
//    }


//    public String getTag() {
//        return tag;
//    }

//    public Filtro getClone() {
//        Filtro deepCopy = new Filtro();
//
//        Document doc = this.criteria.getCriteriaObject();
//        deepCopy.criteria = Criteria.byExample(doc);
//
//        return deepCopy;
//    }

//    public TypeFilter getType() {
//        return type;
//    }
//
//    public void setType(TypeFilter type) {
//        this.type = type;
//    }

//    public String getPropertyField() {
//        return propertyField;
//    }
//
//    public Object getPropertyValue() {
//        return propertyValue;
//    }

}
