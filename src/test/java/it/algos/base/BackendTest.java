package it.algos.base;

import static it.algos.vaad24.backend.boot.VaadCost.*;
import it.algos.vaad24.backend.logic.*;
import it.algos.vaad24.backend.packages.utility.preferenza.*;
import it.algos.vaad24.backend.wrapper.*;
import static org.junit.Assert.*;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.provider.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.data.domain.*;

import java.util.stream.*;

/**
 * Project vaad24
 * Created by Algos
 * User: gac
 * Date: Fri, 24-Feb-2023
 * Time: 20:03
 * Test delle classi 'backend' senza repository <br>
 * <p>
 * isExistById()
 * isExistByKey(), se esiste una key
 * isExistByOrdine(final int ordine), se esiste FIELD_NAME_ORDINE
 * isExistByProperty()
 * <p>
 * findById(final String keyID)
 * findByKey(final String keyValue), se esiste una keyPropertyName
 * findByOrdine(final int ordine), se esiste FIELD_NAME_ORDINE
 * findByProperty(final String propertyName, final Object propertyValue)
 * <p>
 * save()
 * insert()
 * update()
 * delete()
 * count()
 * findAllNoSort()
 * findAllSortCorrente()
 * findAllSort()
 * findAllKey()
 * resetOnlyEmpty()
 * deleteAll()
 */

public abstract class BackendTest extends AlgosTest {


    @Autowired
    protected PreferenzaRepository preferenzaRepository;


    protected CrudBackend crudBackend;

    protected String backendName;

    protected String collectionName;

    protected String keyPropertyName;


    protected Sort sortOrder;

    protected Stream<Arguments> streamCollection;

    protected Stream<Arguments> streamProperty;

    protected Stream<Arguments> streamOrder;

    //    //--giorno
    //    //--esistente
    //    protected static Stream<Arguments> GIORNI() {
    //        return Stream.of(
    //                Arguments.of(null, false),
    //                Arguments.of(VUOTA, false),
    //                Arguments.of("23 febbraio", true),
    //                Arguments.of("43 marzo", false),
    //                Arguments.of("19 dicembra", false),
    //                Arguments.of("4 gennaio", true)
    //        );
    //    }

    //    //--nome
    //    //--esistente
    //    protected static Stream<Arguments> ANNI() {
    //        return Stream.of(
    //                Arguments.of(VUOTA, false),
    //                Arguments.of("0", false),
    //                Arguments.of("24", true),
    //                Arguments.of("24 A.C.", false),
    //                Arguments.of("24 a.C.", true),
    //                Arguments.of("3208", false)
    //        );
    //    }

    /**
     * Qui passa una volta sola <br>
     */
    @BeforeAll
    protected void setUpAll() {
        super.setUpAll();

        clazzName = entityClazz != null ? entityClazz.getSimpleName() : VUOTA;
        backendName = clazzName + SUFFIX_BACKEND;
        collectionName = entityClazz != null ? annotationService.getCollectionName(entityClazz) : VUOTA;
        keyPropertyName = entityClazz != null ? annotationService.getKeyPropertyName(entityClazz) : VUOTA;
        this.typeBackend = typeBackend != null ? typeBackend : TypeBackend.nessuno;

        if (reflectionService.isEsiste(entityClazz, FIELD_NAME_ORDINE)) {
            sortOrder = Sort.by(Sort.Direction.ASC, FIELD_NAME_ORDINE);
        }
        else {
            sortOrder = Sort.by(Sort.Direction.ASC, FIELD_NAME_ID_CON);
        }
        if (crudBackend != null) {
            crudBackend.sortOrder = sortOrder;
        }
    }


    @Test
    @Order(10)
    @DisplayName("10--------")
    void test10() {
        System.out.println("11 - isExistsCollection");
        System.out.println("12 - count");
        System.out.println("13 - resetOnlyEmpty");
        System.out.println("14 - resetForcing");
        System.out.println(VUOTA);
    }

    @Test
    @Order(11)
    @DisplayName("11 - isExistsCollection")
    protected void isExistsCollection() {
        System.out.println("11 - isExistsCollection");

        if (crudBackend == null) {
            System.out.println("Manca la variabile crudBackend in questo test");
            return;
        }

        ottenutoBooleano = crudBackend.isExistsCollection();
        if (ottenutoBooleano) {
            message = String.format("Esiste la collection della classe [%s] e si chiama '%s'", clazzName, collectionName);
        }
        else {
            message = String.format("Non esiste la collection '%s' della classe [%s]", collectionName, clazzName);
        }
        System.out.println(message);
    }


    @Test
    @Order(12)
    @DisplayName("12 - count")
    protected void count() {
        System.out.println("12 - count");

        if (crudBackend == null) {
            System.out.println("Manca la variabile crudBackend in questo test");
            return;
        }

        ottenutoIntero = crudBackend.count();
        if (ottenutoIntero > 0) {
            message = String.format("La collection '%s' della classe [%s] ha in totale %s entities nel database mongoDB", collectionName, clazzName, textService.format(ottenutoIntero));
        }
        else {
            if (reflectionService.isEsisteMetodo(crudBackend.getClass(), METHOD_NAME_RESET_ONLY)) {
                message = String.format("La collection '%s' della classe [%s] è ancora vuota. Usa il metodo %s.%s()", collectionName, clazzName, backendName, METHOD_NAME_RESET_ONLY);
            }
            else {
                message = String.format("Nel database mongoDB la collection '%s' della classe [%s] è ancora vuota", collectionName, clazzName);
            }
        }
        System.out.println(message);
    }


    @Test
    @Order(13)
    @DisplayName("13 - resetOnlyEmpty")
    protected void resetOnlyEmpty() {
        System.out.println("13 - resetOnlyEmpty");
        System.out.println(VUOTA);

        if (entityClazz == null) {
            System.out.println("Manca la variabile entityClazz in questo test");
            return;
        }

        if (!annotationService.usaReset(entityClazz)) {
            message = String.format("Questo test presuppone che la entity [%s] preveda la funzionalità '%s'", clazzName, METHOD_NAME_RESET_ONLY);
            System.out.println(message);
            message = String.format("Devi scrivere un test alternativo oppure modificare la entity [%s]", clazzName);
            System.out.println(message);
            message = String.format("Aggiungendo in testa alla classe un'annotazione tipo @AIEntity(usaReset = true)");
            System.out.println(message);
            return;
        }

        ottenutoRisultato = crudBackend.resetOnlyEmpty();
        assertNotNull(ottenutoRisultato);
        if (ottenutoRisultato.isValido()) {
            System.out.println(ottenutoRisultato.getMessage());
            printRisultato(ottenutoRisultato);

            System.out.println(VUOTA);
            printBackend(ottenutoRisultato.getLista());
        }
        else {
            logger.warn(new WrapLog().message(ottenutoRisultato.getErrorMessage()));
        }
        assertTrue(ottenutoRisultato.isValido());
    }


    @Test
    @Order(14)
    @DisplayName("14 - resetForcing")
    protected void resetForcing() {
        System.out.println("14 - resetForcing");
        System.out.println(VUOTA);

        if (entityClazz == null) {
            System.out.println("Manca la variabile entityClazz in questo test");
            return;
        }

        if (!annotationService.usaReset(entityClazz)) {
            message = String.format("Questo test presuppone che la entity [%s] preveda la funzionalità '%s'", clazzName, METHOD_NAME_RESET_ONLY);
            System.out.println(message);
            message = String.format("Devi scrivere un test alternativo oppure modificare la entity [%s]", clazzName);
            System.out.println(message);
            message = String.format("Aggiungendo in testa alla classe un'annotazione tipo @AIEntity(usaReset = true)");
            System.out.println(message);
            return;
        }

        ottenutoRisultato = crudBackend.resetForcing();
        assertNotNull(ottenutoRisultato);
        if (ottenutoRisultato.isValido()) {
            System.out.println(ottenutoRisultato.getMessage());
            printRisultato(ottenutoRisultato);

            System.out.println(VUOTA);
            printBackend(ottenutoRisultato.getLista());
        }
        else {
            logger.warn(new WrapLog().message(ottenutoRisultato.getErrorMessage()));
        }
        assertTrue(ottenutoRisultato.isValido());
    }

    @Test
    @Order(20)
    @DisplayName("20--------")
    void test20() {
        System.out.println("21 - isExistById");
        System.out.println("22 - isExistByKey");
        System.out.println("23 - isExistByProperty");
        System.out.println("24 - isExistByOrder");
    }

    @Test
    @Order(21)
    @DisplayName("21 - isExistById")
    protected void isExistById() {
        System.out.println("21 - isExistById");
        System.out.println(VUOTA);

        if (annotationService.usaReset(entityClazz)) {
            if (streamCollection != null) {
                streamCollection.forEach(parameters -> this.isExistById(parameters));
            }
            else {
                message = String.format("Nel metodo setUpEach() di %s non è stata regolata la property '%s'", this.getClass().getSimpleName(), "streamCollection");
                logger.warn(new WrapLog().message(message));
            }
        }
        else {
            message = String.format("La collection '%s' ha il flag reset=false e non garantisce di avere valori che possano essere usati come test (potrebbe anche essere vuota)", collectionName);
            System.out.println(message);
        }
    }

    //--nome nella collection
    //--esiste come ID
    //--esiste come key
    protected void isExistById(Arguments arg) {
        Object[] mat = arg.get();
        if (mat != null && mat.length > 0 && mat[0] instanceof String keyValue) {
            sorgente = keyValue;
        }
        else {
            assertTrue(false);
        }
        if (mat != null && mat.length > 1 && mat[1] instanceof Boolean keyValue) {
            previstoBooleano = keyValue;
        }
        else {
            assertTrue(false);
        }

        ottenutoBooleano = crudBackend.isExistById(sorgente);
        if (ottenutoBooleano) {
            message = String.format("Nella collection '%s' ESISTE (true) una entity con l'id = '%s'", collectionName, sorgente);
        }
        else {
            message = String.format("Nella collection '%s' NON esiste (false) nessuna entity con l'id = '%s'", collectionName, sorgente);
        }
        System.out.println(message);
        assertEquals(previstoBooleano, ottenutoBooleano);
    }

    @Test
    @Order(22)
    @DisplayName("22 - isExistByKey")
    protected void isExistByKey() {
        System.out.println("22 - isExistByKey");
        System.out.println(VUOTA);

        if (annotationService.usaReset(entityClazz)) {
            if (streamCollection != null) {
                streamCollection.forEach(parameters -> this.isExistByKey(parameters));
            }
            else {
                message = String.format("Nel metodo setUpEach() di %s non è stata regolata la property '%s'", this.getClass().getSimpleName(), "streamCollection");
                logger.warn(new WrapLog().message(message));
            }
        }
        else {
            message = String.format("La collection '%s' ha il flag reset=false e non garantisce di avere valori che possano essere usati come test (potrebbe anche essere vuota)", collectionName);
            System.out.println(message);
        }
    }

    //--nome nella collection
    //--esiste come ID
    //--esiste come key
    protected void isExistByKey(Arguments arg) {
        Object[] mat = arg.get();
        if (mat != null && mat.length > 0 && mat[0] instanceof String keyValue) {
            sorgente = keyValue;
        }
        else {
            assertTrue(false);
        }
        if (mat != null && mat.length > 2 && mat[2] instanceof Boolean keyValue) {
            previstoBooleano = keyValue;
        }
        else {
            assertTrue(false);
        }

        String keyPropertyName = annotationService.getKeyPropertyName(entityClazz);
        if (textService.isEmpty(keyPropertyName)) {
            message = String.format("Nella collection '%s' non esiste la keyPropertyName", collectionName);
            return;
        }

        ottenutoBooleano = crudBackend.isExistByKey(sorgente);
        if (ottenutoBooleano) {
            message = String.format("Nella collection '%s' ESISTE una entity con la keyId = '%s'", collectionName, sorgente);
        }
        else {
            message = String.format("Nella collection '%s' NON esiste nessuna entity con la keyId = '%s'", collectionName, sorgente);
        }
        System.out.println(message);
        assertEquals(previstoBooleano, ottenutoBooleano);
    }

    @Test
    @Order(23)
    @DisplayName("23 - isExistByProperty")
    protected void isExistByProperty() {
        System.out.println("23 - isExistByProperty");
        System.out.println(VUOTA);

        if (annotationService.usaReset(entityClazz)) {
            if (annotationService.isEsisteKeyPropertyName(entityClazz)) {
                if (streamProperty != null) {
                    streamProperty.forEach(parameters -> this.isExistByProperty(parameters));
                }
                else {
                    message = String.format("Nel metodo setUpEach() di %s non è stata regolata la property '%s'", this.getClass().getSimpleName(), "streamProperty");
                    logger.warn(new WrapLog().message(message));
                }
            }
            else {
                message = String.format("La collection '%s' non prevede una key property", collectionName);
                System.out.println(message);
            }
        }
        else {
            message = String.format("La collection '%s' ha il flag reset=false e non garantisce di avere valori che possano essere usati come test (potrebbe anche essere vuota)", collectionName);
            System.out.println(message);
        }
    }

    //--nome della property
    //--value della property
    //--esiste entityBean
    protected void isExistByProperty(Arguments arg) {
        Object objValue = null;
        Object[] mat = arg.get();
        if (mat != null && mat.length > 0 && mat[0] instanceof String keyValue) {
            sorgente = keyValue;
        }
        else {
            assertTrue(false);
        }

        if (mat != null && mat.length > 1) {
            objValue = mat[1];
        }
        else {
            assertTrue(false);
        }

        if (mat != null && mat.length > 2 && mat[2] instanceof Boolean keyValue) {
            previstoBooleano = keyValue;
        }
        else {
            assertTrue(false);
        }

        if (reflectionService.isEsiste(entityClazz, sorgente)) {
            ottenutoBooleano = crudBackend.isExistByProperty(sorgente, objValue);
            if (ottenutoBooleano) {
                message = String.format("Nella collection '%s' ESISTE una entity individuata dal valore '%s' della property [%s]", collectionName, objValue, sorgente);
            }
            else {
                message = String.format("Nella collection '%s' NON esiste nessuna entity col valore '%s' della property [%s]", collectionName, objValue, sorgente);
            }
        }
        else {
            message = String.format("Nella collection '%s' non esiste la property [%s]", collectionName, sorgente);
        }
        System.out.println(message);
    }


    @Test
    @Order(24)
    @DisplayName("24 - isExistByOrder")
    protected void isExistByOrder() {
        System.out.println("24 - isExistByOrder");
        System.out.println(VUOTA);

        if (annotationService.usaReset(entityClazz)) {
            if (reflectionService.isEsiste(entityClazz, FIELD_NAME_ORDINE)) {
                if (streamOrder != null) {
                    streamOrder.forEach(parameters -> this.isExistByOrder(parameters));
                }
                else {
                    message = String.format("Nel metodo setUpEach() di %s non è stata regolata la property '%s'", this.getClass().getSimpleName(), "streamOrder");
                    logger.warn(new WrapLog().message(message));
                }
            }
            else {
                message = String.format("Nella collection '%s' non esiste la property '%s'", collectionName, FIELD_NAME_ORDINE);
                System.out.println(message);
            }
        }
        else {
            message = String.format("La collection '%s' ha il flag reset=false e non garantisce di avere valori che possano essere usati come test (potrebbe anche essere vuota)", collectionName);
            System.out.println(message);
        }
    }

    //--value ordine
    //--esiste entityBean
    protected void isExistByOrder(Arguments arg) {
        Object[] mat = arg.get();
        if (mat != null && mat.length > 0 && mat[0] instanceof Integer keyValue) {
            sorgenteIntero = keyValue;
        }
        else {
            assertTrue(false);
        }
        if (mat != null && mat.length > 1 && mat[1] instanceof Boolean keyValue) {
            previstoBooleano = keyValue;
        }
        else {
            assertTrue(false);
        }

        ottenutoBooleano = crudBackend.isExistByOrder(sorgenteIntero);
        if (ottenutoBooleano) {
            message = String.format("Nella collection '%s' ESISTE una entity individuata dal valore '%d' della property [%s]", collectionName, sorgenteIntero, FIELD_NAME_ORDINE);
        }
        else {
            message = String.format("Nella collection '%s' NON esiste nessuna entity col valore '%d' della property [%s]", collectionName, sorgenteIntero, FIELD_NAME_ORDINE);
        }
        System.out.println(message);
        assertEquals(previstoBooleano, ottenutoBooleano);
    }


    @Test
    @Order(30)
    @DisplayName("30--------")
    void test30() {
        System.out.println("31 - findById");
        System.out.println("32 - findByKey");
        System.out.println("33 - findByProperty");
        System.out.println("34 - findByOrder");
    }

    @Test
    @Order(31)
    @DisplayName("31 - findById")
    protected void findById() {
        System.out.println("31 - findById");
        System.out.println(VUOTA);

        if (annotationService.usaReset(entityClazz)) {
            if (streamCollection != null) {
                streamCollection.forEach(parameters -> this.findById(parameters));
            }
            else {
                message = String.format("Nel metodo setUpEach() di %s non è stata regolata la property '%s'", this.getClass().getSimpleName(), "streamCollection");
                logger.warn(new WrapLog().message(message));
            }
        }
        else {
            message = String.format("La collection '%s' ha il flag reset=false e non garantisce di avere valori che possano essere usati come test (potrebbe anche essere vuota)", collectionName);
            System.out.println(message);
        }
    }

    //--nome nella collection
    //--esiste come ID
    //--esiste come key
    protected void findById(Arguments arg) {
        Object[] mat = arg.get();
        if (mat != null && mat.length > 0 && mat[0] instanceof String keyValue) {
            sorgente = keyValue;
        }
        else {
            assertTrue(false);
        }
        if (mat != null && mat.length > 1 && mat[1] instanceof Boolean keyValue) {
            previstoBooleano = keyValue;
        }
        else {
            assertTrue(false);
        }

        entityBean = crudBackend.findById(sorgente);
        if (entityBean != null) {
            message = String.format("Nella collection '%s' ESISTE (true) una entity con l'id = '%s'", collectionName, entityBean.id);
        }
        else {
            message = String.format("Nella collection '%s' NON esiste (false) nessuna entity con id = '%s'", collectionName, sorgente);
        }
        System.out.println(message);
        assertEquals(previstoBooleano, entityBean != null);
    }

    @Test
    @Order(32)
    @DisplayName("32 - findByKey")
    protected void findByKey() {
        System.out.println("32 - findByKey");
        System.out.println(VUOTA);

        if (annotationService.usaReset(entityClazz)) {
            if (streamCollection != null) {
                streamCollection.forEach(parameters -> this.findByKey(parameters));
            }
            else {
                message = String.format("Nel metodo setUpEach() di %s non è stata regolata la property '%s'", this.getClass().getSimpleName(), "streamCollection");
                logger.warn(new WrapLog().message(message));
            }
        }
        else {
            message = String.format("La collection '%s' ha il flag reset=false e non garantisce di avere valori che possano essere usati come test (potrebbe anche essere vuota)", collectionName);
            System.out.println(message);
        }
    }

    //--nome nella collection
    //--esiste come ID
    //--esiste come key
    protected void findByKey(Arguments arg) {
        Object[] mat = arg.get();
        if (mat != null && mat.length > 0 && mat[0] instanceof String keyValue) {
            sorgente = keyValue;
        }
        else {
            assertTrue(false);
        }
        if (mat != null && mat.length > 2 && mat[2] instanceof Boolean keyValue) {
            previstoBooleano = keyValue;
        }
        else {
            assertTrue(false);
        }

        String keyPropertyName = annotationService.getKeyPropertyName(entityClazz);
        if (textService.isEmpty(keyPropertyName)) {
            message = String.format("Nella collection '%s' non esiste la keyPropertyName", collectionName);
            return;
        }

        entityBean = crudBackend.findByKey(sorgente);
        if (entityBean != null) {
            message = String.format("Nella collection '%s' ESISTE (true) una entity con l'id = '%s'", collectionName, entityBean.id);
        }
        else {
            message = String.format("Nella collection '%s' NON esiste (false) nessuna entity con id = '%s'", collectionName, sorgente);
        }
        System.out.println(message);
        assertEquals(previstoBooleano, entityBean != null);
    }

    @Test
    @Order(33)
    @DisplayName("33 - findByProperty")
    protected void findByProperty() {
        System.out.println("33 - findByProperty");
        System.out.println(VUOTA);

        if (annotationService.usaReset(entityClazz)) {
            if (annotationService.isEsisteKeyPropertyName(entityClazz)) {
                if (streamProperty != null) {
                    streamProperty.forEach(parameters -> this.findByProperty(parameters));
                }
                else {
                    message = String.format("Nel metodo setUpEach() di %s non è stata regolata la property '%s'", this.getClass().getSimpleName(), "streamProperty");
                    logger.warn(new WrapLog().message(message));
                }
            }
            else {
                message = String.format("La collection '%s' non prevede una key property", collectionName);
                System.out.println(message);
            }
        }
        else {
            message = String.format("La collection '%s' ha il flag reset=false e non garantisce di avere valori che possano essere usati come test (potrebbe anche essere vuota)", collectionName);
            System.out.println(message);
        }
    }

    //--nome della property
    //--value della property
    //--esiste entityBean
    protected void findByProperty(Arguments arg) {
        Object objValue = null;
        Object[] mat = arg.get();
        if (mat != null && mat.length > 0 && mat[0] instanceof String keyValue) {
            sorgente = keyValue;
        }
        else {
            assertTrue(false);
        }

        if (mat != null && mat.length > 1) {
            objValue = mat[1];
        }
        else {
            assertTrue(false);
        }

        if (mat != null && mat.length > 2 && mat[2] instanceof Boolean keyValue) {
            previstoBooleano = keyValue;
        }
        else {
            assertTrue(false);
        }

        if (reflectionService.isEsiste(entityClazz, sorgente)) {
            entityBean = crudBackend.findByProperty(sorgente, objValue);
            if (entityBean != null) {
                message = String.format("Nella collection '%s' ESISTE una entity individuata dal valore '%s' della property [%s]", collectionName, objValue, sorgente);
            }
            else {
                message = String.format("Nella collection '%s' NON esiste nessuna entity col valore '%s' della property [%s]", collectionName, objValue, sorgente);
            }
        }
        else {
            message = String.format("Nella collection '%s' non esiste la property [%s]", collectionName, sorgente);
        }
        System.out.println(message);
        assertEquals(previstoBooleano, entityBean != null);
    }


    @Test
    @Order(34)
    @DisplayName("34 - findByOrder")
    protected void findByOrder() {
        System.out.println("34 - findByOrder");
        System.out.println(VUOTA);

        if (annotationService.usaReset(entityClazz)) {
            if (reflectionService.isEsiste(entityClazz, FIELD_NAME_ORDINE)) {
                if (streamOrder != null) {
                    streamOrder.forEach(parameters -> this.findByOrder(parameters));
                }
                else {
                    message = String.format("Nel metodo setUpEach() di %s non è stata regolata la property '%s'", this.getClass().getSimpleName(), "streamOrder");
                    logger.warn(new WrapLog().message(message));
                }
            }
            else {
                message = String.format("Nella collection '%s' non esiste la property '%s'", collectionName, FIELD_NAME_ORDINE);
                System.out.println(message);
            }
        }
        else {
            message = String.format("La collection '%s' ha il flag reset=false e non garantisce di avere valori che possano essere usati come test (potrebbe anche essere vuota)", collectionName);
            System.out.println(message);
        }
    }

    //--value ordine
    //--esiste entityBean
    protected void findByOrder(Arguments arg) {
        Object[] mat = arg.get();
        if (mat != null && mat.length > 0 && mat[0] instanceof Integer keyValue) {
            sorgenteIntero = keyValue;
        }
        else {
            assertTrue(false);
        }
        if (mat != null && mat.length > 1 && mat[1] instanceof Boolean keyValue) {
            previstoBooleano = keyValue;
        }
        else {
            assertTrue(false);
        }

        entityBean = crudBackend.findByOrder(sorgenteIntero);
        if (entityBean != null) {
            message = String.format("Nella collection '%s' ESISTE una entity individuata dal valore '%d' della property [%s]", collectionName, sorgenteIntero, FIELD_NAME_ORDINE);
        }
        else {
            message = String.format("Nella collection '%s' NON esiste nessuna entity col valore '%d' della property [%s]", collectionName, sorgenteIntero, FIELD_NAME_ORDINE);
        }
        System.out.println(message);
        assertEquals(previstoBooleano, entityBean != null);
    }


    //Segnaposto
    @Order(35)
    protected void libero35() {
    }

    //Segnaposto
    @Order(36)
    protected void libero36() {
    }

    //Segnaposto
    @Order(37)
    protected void libero37() {
    }

    @Test
    @Order(40)
    @DisplayName("40--------")
    void test40() {
        System.out.println("41 - creaIfNotExist");
        System.out.println("42 - newEntity con ID ma non registrata");
        System.out.println("43 - fixKey");
        System.out.println("44 - getIdKey");
        System.out.println("45 - toString");
    }

    @Order(41)
    @DisplayName("41 - creaIfNotExist")
    protected boolean creaIfNotExist(String keyValue) {
        String keyPropertyName = annotationService.getKeyPropertyName(entityClazz);

        if (textService.isEmpty(keyValue)) {
            System.out.println("La keyValue è nulla/vuota");
            return false;
        }
        if (textService.isEmpty(keyPropertyName)) {
            message = String.format("Nella collection '%s' non esiste la keyPropertyName", collectionName);
            System.out.println(message);
            return false;
        }

        ottenutoBooleano = crudBackend.creaIfNotExist(keyValue);
        if (ottenutoBooleano) {
            message = String.format("Nella collection '%s' è stata CREATA (true) SOLO IN MEMORIA una entity individuata dal valore '%s' della property [%s]", collectionName, keyValue, keyPropertyName);
        }
        else {
            message = String.format("Nella collection '%s' ESISTEVA già (false) una entity col valore '%s' della property [%s]", collectionName, keyValue, keyPropertyName);
        }
        System.out.println(message);

        return ottenutoBooleano;
    }

    @Test
    @Order(42)
    @DisplayName("42 - newEntity con ID ma non registrata")
    protected void newEntity() {
        System.out.println("42 - newEntity con ID ma non registrata");
        System.out.println(VUOTA);
        String keyPropertyName = VUOTA;

        sorgente = "Topo Lino";
        previsto = "topolino";
        previsto2 = "Topo Lino";

        if (annotationService.usaKeyPropertyName(entityClazz)) {
            keyPropertyName = annotationService.getKeyPropertyName(entityClazz);
        }

        if (reflectionService.isEsisteMetodoConParametri(crudBackend.getClass(), METHOD_NAME_NEW_ENTITY, 1)) {
            entityBean = crudBackend.newEntity(getParamEsistente());
            assertNotNull(entityBean);
            ottenuto = entityBean.id;
            ottenuto2 = reflectionService.getPropertyValueStr(entityBean, keyPropertyName);
            if (annotationService.usaKeyPropertyName(entityClazz)) {
                if (textService.isEmpty(ottenuto)) {
                    message = String.format("La entity appena creata è senza keyID mentre dovrebbe essere id=%s (valore del field %s)", sorgente, keyPropertyName);
                    System.out.println(message);
                    message = String.format("Molto probabilmente manca la chiusura del metodo base newEntity -> 'return (%s) fixKey(newEntityBean);' ", clazzName);
                    System.out.println(message);
                }

                assertEquals(previsto, ottenuto);
                assertEquals(previsto2, ottenuto2);
                assertTrue(textService.isValid(ottenuto));
                System.out.println(String.format("KeyId%s%s", FORWARD, ottenuto));
            }
            return;
        }

        if (reflectionService.isEsisteMetodoConParametri(crudBackend.getClass(), METHOD_NAME_NEW_ENTITY, 0)) {
            entityBean = crudBackend.newEntity();
            assertNotNull(entityBean);
            ottenuto = entityBean.id;
            assertTrue(textService.isEmpty(ottenuto));
            message = String.format("Nella classe [%s] esiste il metodo '%s' ma senza parametri", backendName, METHOD_NAME_NEW_ENTITY);
            System.out.println(message);
            if (annotationService.usaKeyPropertyName(entityClazz)) {
                message = String.format("Non è quindi possibile creare e testare una nuova entity che utilizzi la keyPropertyName '%s' come ID", keyPropertyName);
                System.out.println(message);
            }
            else {
                message = String.format("La classe non utilizza una keyPropertyName e la entity '%s' è senza keyID", entityBean);
                System.out.println(message);
            }
            return;
        }
    }

    @Test
    @Order(43)
    @DisplayName("43 - fixKey")
    protected void fixKey() {
        System.out.println("43 - fixKey");
    }

    @Test
    @Order(44)
    @DisplayName("44 - getIdKey")
    protected void getIdKey() {
        System.out.println("44 - getIdKey");
    }

    @Test
    @Order(45)
    @DisplayName("45 - toString")
    protected void toStringTest() {
        System.out.println("45 - toString");
        System.out.println(VUOTA);

        sorgente = "Topo Lino";

        if (annotationService.usaKeyPropertyName(entityClazz)) {
            keyPropertyName = annotationService.getKeyPropertyName(entityClazz);
        }
        else {
            message = String.format("Nella entityClazz [%s] la keyProperty non è prevista", clazzName);
            System.out.println(message);
            message = String.format("Devi scrivere un test alternativo oppure modificare la entityClazz [%s]", clazzName);
            System.out.println(message);
            message = String.format("Aggiungendo in testa alla classe un'annotazione tipo @AIEntity(keyPropertyName = \"nome\")");
            System.out.println(message);
            return;
        }

        if (reflectionService.isEsisteMetodoConParametri(crudBackend.getClass(), METHOD_NAME_NEW_ENTITY, 1)) {
            try {
                entityBean = crudBackend.newEntity(sorgente);
            } catch (Exception unErrore) {
                message = String.format("Non sono riuscito a creare una entityBean della classe [%s] col metodo newEntity() ad un solo parametro", clazzName);
                System.out.println(message);
                message = String.format("Probabilmente il valore [%s] usato per la keyPropertyName '%s' non è adeguato", sorgente, keyPropertyName);
                System.out.println(message);
                message = String.format("Devi scrivere un test alternativo per controllare la funzione toString() della classe [%s]", clazzName);
                System.out.println(message);
                return;
            }
            assertNotNull(entityBean);
            ottenuto = entityBean.toString();
            if (textService.isEmpty(ottenuto)) {
                message = String.format("Non esiste il valore toString() della entity appena creata di classe [%s]", clazzName);
                System.out.println(message);
                message = String.format("Devi creare/modificare il metodo [%s].toString()", clazzName);
                System.out.println(message);
            }
            assertTrue(textService.isValid(ottenuto));
            System.out.println(String.format("toString%s%s", FORWARD, ottenuto));
            return;
        }

        if (reflectionService.isEsisteMetodoConParametri(crudBackend.getClass(), METHOD_NAME_NEW_ENTITY, 0)) {
            entityBean = crudBackend.newEntity();
            assertNotNull(entityBean);
            assertNotNull(entityBean);
            ottenuto = entityBean.toString();
            if (textService.isEmpty(ottenuto)) {
                message = String.format("Non esiste il valore toString() della entity di classe [%s]", clazzName);
                System.out.println(message);
                message = String.format("Perché è stata creata con %s() senza parametri", METHOD_NAME_NEW_ENTITY);
                System.out.println(message);
                message = String.format("E quindi non ha recepito il valore del keyPropertyName '%s'", keyPropertyName);
                System.out.println(message);
            }
            return;
        }

        message = String.format("Questo test presuppone che esista il metodo '%s' nella classe [%s] con un parametro solo o senza", METHOD_NAME_NEW_ENTITY, backendName);
        System.out.println(message);
        message = String.format("Devi scrivere un test alternativo oppure modificare la classe [%s]", backendName);
        System.out.println(message);
        message = String.format("Aggiungendo un metodo '%s' senza parametri oppure con un parametro", METHOD_NAME_NEW_ENTITY);
        System.out.println(message);
    }

    @Test
    @Order(50)
    @DisplayName("50--------")
    void test50() {
        System.out.println("51 - findAllNoSort (entityBeans)");
        System.out.println("52 - findAllSortCorrente (entityBeans)");
        System.out.println("53 - findAllSortCorrenteReverse (entityBeans)");
        System.out.println("54 - findAllSortKey (entityBeans)");
    }

    @Test
    @Order(51)
    @DisplayName("51 - findAllNoSort (entityBeans)")
    protected void findAllNoSort() {
        System.out.println("51 - findAllNoSort (entityBeans)");
        System.out.println(VUOTA);

        listaBeans = crudBackend.findAllNoSort();
        assertNotNull(listaBeans);
        ottenutoIntero = listaBeans.size();
        message = String.format("La collection '%s' della classe [%s] ha in totale %s entities nel database mongoDB", collectionName, clazzName, textService.format(ottenutoIntero));
        System.out.println(message);
        printBackend(listaBeans);
    }

    @Test
    @Order(52)
    @DisplayName("52 - findAllSortCorrente (entityBeans)")
    protected void findAllSortCorrente() {
        System.out.println("52 - findAll findAllSortCorrente (entityBeans)");
        System.out.println(VUOTA);

        listaBeans = crudBackend.findAllSortCorrente();
        assertNotNull(listaBeans);
        ottenutoIntero = listaBeans.size();
        message = String.format("La collection '%s' della classe [%s] ha in totale %s entities nel database mongoDB", collectionName, clazzName, textService.format(ottenutoIntero));
        System.out.println(message);
        printBackend(listaBeans);
    }


    @Test
    @Order(53)
    @DisplayName("53 - findAllSortCorrenteReverse (entityBeans)")
    protected void findAllSort() {
        System.out.println("53 - findAllSortCorrenteReverse (entityBeans)");
        System.out.println(VUOTA);

        listaBeans = crudBackend.findAllSortCorrenteReverse();
        assertNotNull(listaBeans);
        ottenutoIntero = listaBeans.size();
        message = String.format("La collection '%s' della classe [%s] ha in totale %s entities nel database mongoDB", collectionName, clazzName, textService.format(ottenutoIntero));
        System.out.println(message);
        printBackend(listaBeans);
    }

    @Test
    @Order(54)
    @DisplayName("54 - findAllSortKey (entityBeans)")
    protected void findAllSortKey() {
        System.out.println("54 - findAllSortKey (entityBeans)");
        System.out.println(VUOTA);

        listaBeans = crudBackend.findAllSortKey();
        assertNotNull(listaBeans);
        ottenutoIntero = listaBeans.size();
        message = String.format("La collection '%s' della classe [%s] ha in totale %s entities nel database mongoDB", collectionName, clazzName, textService.format(ottenutoIntero));
        System.out.println(message);
        printBackend(listaBeans);
    }

    //Segnaposto
    @Order(55)
    protected void libero55() {
    }

    @Test
    @Order(60)
    @DisplayName("60--------")
    void test59() {
        System.out.println("61 - findAllForKeySortKey (String)");
        System.out.println("62 - findAllForKeySortKeyReverse (String)");
        System.out.println("63 - findAllForKeySortOrdine (String)");
        System.out.println("64 - findAllForKeySortOrdineReverse (String)");
    }


    @Test
    @Order(61)
    @DisplayName("61 - findAllForKeySortKey (String)")
    protected void findAllForKeySortKey() {
        System.out.println("61 - findAllForKeySortKey (String)");
        System.out.println(VUOTA);

        if (!annotationService.usaKeyPropertyName(entityClazz)) {
            System.out.println("Il metodo usato da questo test presuppone che esista una keyProperty");
            message = String.format("Nella entityClazz [%s] la keyProperty non è prevista", clazzName);
            System.out.println(message);
            message = String.format("Devi scrivere un test alternativo oppure modificare la entityClazz [%s]", clazzName);
            System.out.println(message);
            message = String.format("Aggiungendo in testa alla classe un'annotazione tipo @AIEntity(keyPropertyName = \"nome\")");
            System.out.println(message);
            return;
        }

        if (!crudBackend.isExistsCollection()) {
            message = String.format("Il metodo usato da questo test presuppone che esista la collection '%s' che invece è assente", collectionName);
            System.out.println(message);
            message = String.format("Non esiste la collection '%s' della classe [%s]", collectionName, clazzName);
            System.out.println(message);
            return;
        }

        listaStr = crudBackend.findAllForKeySortKey();
        assertNotNull(listaStr);
        ottenutoIntero = listaStr.size();
        sorgente = textService.format(ottenutoIntero);
        sorgente2 = keyPropertyName;
        message = String.format("La collection '%s' della classe [%s] ha in totale %s entities.", collectionName, clazzName, ottenutoIntero);
        System.out.println(message);
        message = String.format("Valori (String) del campo chiave '%s' ordinato secondo '%s' ascendente:", keyPropertyName, keyPropertyName);
        System.out.println(message);

        printSubLista(listaStr);
    }


    @Test
    @Order(62)
    @DisplayName("62 - findAllForKeySortKeyReverse (String)")
    protected void findAllForKeySortKeyReverse() {
        System.out.println("62 - findAllForKeySortKeyReverse (String)");
        System.out.println(VUOTA);

        if (!annotationService.usaKeyPropertyName(entityClazz)) {
            System.out.println("Il metodo usato da questo test presuppone che esista una keyProperty");
            message = String.format("Nella entityClazz [%s] la keyProperty non è prevista", clazzName);
            System.out.println(message);
            message = String.format("Devi scrivere un test alternativo oppure modificare la entityClazz [%s]", clazzName);
            System.out.println(message);
            message = String.format("Aggiungendo in testa alla classe un'annotazione tipo @AIEntity(keyPropertyName = \"nome\")");
            System.out.println(message);
            return;
        }

        if (!crudBackend.isExistsCollection()) {
            message = String.format("Il metodo usato da questo test presuppone che esista la collection '%s' che invece è assente", collectionName);
            System.out.println(message);
            message = String.format("Non esiste la collection '%s' della classe [%s]", collectionName, clazzName);
            System.out.println(message);
            return;
        }

        listaStr = crudBackend.findAllForKeySortKeyReverse();
        assertNotNull(listaStr);
        ottenutoIntero = listaStr.size();
        sorgente = textService.format(ottenutoIntero);
        sorgente2 = keyPropertyName;
        message = String.format("La collection '%s' della classe [%s] ha in totale %s entities.", collectionName, clazzName, ottenutoIntero);
        System.out.println(message);
        message = String.format("Valori (String) del campo chiave '%s' ordinato secondo '%s' discendente:", keyPropertyName, keyPropertyName);
        System.out.println(message);

        printSubLista(listaStr);
    }


    @Test
    @Order(63)
    @DisplayName("63 - findAllForKeySortOrdine (String)")
    protected void findAllForKeySortOrdine() {
        System.out.println("63 - findAllForKeySortOrdine (String)");
        System.out.println(VUOTA);

        if (!reflectionService.isEsiste(entityClazz, FIELD_NAME_ORDINE)) {
            message = String.format("Il metodo usato da questo test presuppone che esista una property %s", FIELD_NAME_ORDINE);
            System.out.println(message);

            message = String.format("Nella entityClazz [%s] la property %s non è prevista", clazzName, FIELD_NAME_ORDINE);
            System.out.println(message);
            message = String.format("Devi scrivere un test alternativo oppure modificare la entityClazz [%s]", clazzName);
            System.out.println(message);
            message = String.format("Aggiungendo la property %s", FIELD_NAME_ORDINE);
            System.out.println(message);
            return;
        }

        if (!crudBackend.isExistsCollection()) {
            message = String.format("Il metodo usato da questo test presuppone che esista la collection '%s' che invece è assente", collectionName);
            System.out.println(message);
            message = String.format("Non esiste la collection '%s' della classe [%s]", collectionName, clazzName);
            System.out.println(message);
            return;
        }

        listaStr = crudBackend.findAllForKeySortOrdine();
        assertNotNull(listaStr);
        ottenutoIntero = listaStr.size();
        sorgente = textService.format(ottenutoIntero);
        sorgente2 = keyPropertyName;
        message = String.format("La collection '%s' della classe [%s] ha in totale %s entities.", collectionName, clazzName, ottenutoIntero);
        System.out.println(message);
        message = String.format("Valori (String) del campo chiave '%s' ordinato secondo '%s' ascendente:", keyPropertyName, FIELD_NAME_ORDINE);
        System.out.println(message);

        printSubLista(listaStr);
    }


    @Test
    @Order(64)
    @DisplayName("64 - findAllForKeySortOrdineReverse (String)")
    protected void findAllForKeySortOrdineReverse() {
        System.out.println("64 - findAllForKeySortOrdineReverse (String)");
        System.out.println(VUOTA);

        if (!reflectionService.isEsiste(entityClazz, FIELD_NAME_ORDINE)) {
            message = String.format("Il metodo usato da questo test presuppone che esista una property %s", FIELD_NAME_ORDINE);
            System.out.println(message);

            message = String.format("Nella entityClazz [%s] la property %s non è prevista", clazzName, FIELD_NAME_ORDINE);
            System.out.println(message);
            message = String.format("Devi scrivere un test alternativo oppure modificare la entityClazz [%s]", clazzName);
            System.out.println(message);
            message = String.format("Aggiungendo la property %s", FIELD_NAME_ORDINE);
            System.out.println(message);
            return;
        }

        if (!crudBackend.isExistsCollection()) {
            message = String.format("Il metodo usato da questo test presuppone che esista la collection '%s' che invece è assente", collectionName);
            System.out.println(message);
            message = String.format("Non esiste la collection '%s' della classe [%s]", collectionName, clazzName);
            System.out.println(message);
            return;
        }

        listaStr = crudBackend.findAllForKeySortOrdineReverse();
        assertNotNull(listaStr);
        ottenutoIntero = listaStr.size();
        sorgente = textService.format(ottenutoIntero);
        sorgente2 = keyPropertyName;
        message = String.format("La collection '%s' della classe [%s] ha in totale %s entities.", collectionName, clazzName, ottenutoIntero);
        System.out.println(message);
        message = String.format("Valori (String) del campo chiave '%s' ordinato secondo '%s' discendente:", keyPropertyName, FIELD_NAME_ORDINE);
        System.out.println(message);

        printSubLista(listaStr);
    }

    //Segnaposto
    @Order(65)
    protected void libero65() {
    }

    //    @Test
    @Order(263)
    @DisplayName("263 - CRUD operations")
    protected void crud() {
        System.out.println("263 - CRUD operations");
        System.out.println(VUOTA);

        if (!reflectionService.isEsisteMetodoConParametri(crudBackend.getClass(), METHOD_NAME_NEW_ENTITY, 1)) {
            message = String.format("Questo test presuppone che esista il metodo '%s' nella classe [%s] con un parametro", METHOD_NAME_NEW_ENTITY, backendName);
            System.out.println(message);
            message = String.format("Devi scrivere un test alternativo oppure modificare la classe [%s]", backendName);
            System.out.println(message);
            message = String.format("Aggiungendo il metodo '%s' con un parametro", METHOD_NAME_NEW_ENTITY);
            System.out.println(message);
            return;
        }
        if (!annotationService.usaKeyPropertyName(entityClazz)) {
            System.out.println("Le operazioni CRUD standard di questo test presuppongono che esista una keyProperty");

            message = String.format("Nella entityClazz [%s] la keyProperty non è prevista", clazzName);
            System.out.println(message);
            message = String.format("Devi scrivere un test alternativo oppure modificare la entityClazz [%s]", clazzName);
            System.out.println(message);
            message = String.format("Aggiungendo in testa alla classe un'annotazione tipo @AIEntity(keyPropertyName = \"nome\")");
            System.out.println(message);
            return;
        }

        sorgente = "Topo Lino";
        previsto = "topolino";
        previsto2 = "Giuseppe";

        ottenutoBooleano = crudBackend.isExistById(sorgente);
        assertFalse(ottenutoBooleano);
        message = String.format("1) isExistId -> Nella collection '%s' non esiste (false) la entity [%s]", collectionName, sorgente);
        System.out.println(message);

        ottenutoBooleano = crudBackend.creaIfNotExist(sorgente);
        assertTrue(ottenutoBooleano);
        message = String.format("2) creaIfNotExist -> Nella collection '%s' è stata creata (true) la entity [%s].%s che prima non esisteva", collectionName, previsto, sorgente);
        System.out.println(message);

        ottenutoBooleano = crudBackend.isExistById(previsto);
        assertTrue(ottenutoBooleano);
        message = String.format("3) isExistId -> Controllo l'esistenza (true) della entity [%s].%s tramite l'ID", previsto, sorgente);
        System.out.println(message);

        System.out.println(VUOTA);

        ottenutoBooleano = crudBackend.creaIfNotExist(sorgente);
        assertFalse(ottenutoBooleano);
        message = String.format("4) creaIfNotExist -> La entity [%s].%s esisteva già e non è stata creata (false)", previsto, sorgente);
        System.out.println(message);

        System.out.println(VUOTA);

        ottenutoBooleano = crudBackend.isExistById(previsto);
        assertTrue(ottenutoBooleano);
        message = String.format("5) isExistId -> Controllo l'esistenza (true) della entity [%s].%s tramite l'ID", previsto, sorgente);
        System.out.println(message);
        ottenutoBooleano = crudBackend.isExistByKey(sorgente);
        message = String.format("6) isExistKey -> Esiste la entity [%s].%s individuata dal valore '%s' della keyProperty [%s]", previsto, sorgente, sorgente, keyPropertyName);
        assertTrue(ottenutoBooleano);
        System.out.println(message);
        ottenutoBooleano = crudBackend.isExistByKey(previsto2);
        assertFalse(ottenutoBooleano);
        message = String.format("7) isExistKey -> Non esiste la entity [%s].%s individuata dal valore '%s' della keyProperty [%s]", previsto, previsto2, previsto2, keyPropertyName);
        System.out.println(message);
        ottenutoBooleano = crudBackend.isExistByProperty(keyPropertyName, sorgente);
        message = String.format("8) isExistProperty -> Esiste la entity [%s].%s individuata dal valore '%s' della property [%s]", previsto, previsto2, sorgente, keyPropertyName);
        assertTrue(ottenutoBooleano);
        System.out.println(message);

        System.out.println(VUOTA);

        // findById(final String keyID)
        entityBean = crudBackend.findById(previsto);
        assertNotNull(entityBean);
        message = String.format("9) findById -> Recupero la entity [%s].%s dalla keyID", previsto, sorgente);
        System.out.println(message);

        // findByKey(final String keyValue), se esiste una keyPropertyName
        entityBean = crudBackend.findByKey(sorgente);
        assertNotNull(entityBean);
        message = String.format("10) findByKey -> Recupero la entity [%s].%s dal valore '%s' della keyProperty [%s]", previsto, sorgente, sorgente, keyPropertyName);
        System.out.println(message);

        // findByOrdine(final int ordine), se esiste FIELD_NAME_ORDINE
        if (reflectionService.isEsiste(entityClazz, FIELD_NAME_ORDINE)) {
            sorgenteIntero = 1;
            entityBean = crudBackend.findByOrder(sorgenteIntero);
            assertNotNull(entityBean);
            assertEquals(sorgenteIntero, reflectionService.getPropertyValue(entityBean, FIELD_NAME_ORDINE));
            message = String.format("11) findByOrdine -> Recupero la entity [%s] dal valore '%s' della property [%s]", entityBean, sorgenteIntero, FIELD_NAME_ORDINE);
            System.out.println(message);
        }
        else {
            message = String.format("11) findByOrdine -> La collection '%s' non prevede la property [%s]", clazzName, FIELD_NAME_ORDINE);
            System.out.println(message);
        }

        // findByProperty(final String propertyName, final Object propertyValue)
        entityBean = crudBackend.findByProperty(keyPropertyName, sorgente);
        assertNotNull(entityBean);
        message = String.format("12) findByProperty -> Recupero la entity [%s].%s dal valore '%s' della property [%s]", previsto, sorgente, sorgente, keyPropertyName);
        System.out.println(message);

        System.out.println(VUOTA);

        reflectionService.setPropertyValue(entityBean, keyPropertyName, previsto2);
        entityBean = crudBackend.save(entityBean);
        assertNotNull(entityBean);
        assertEquals(previsto2, reflectionService.getPropertyValue(entityBean, keyPropertyName));
        entityBean = crudBackend.findById(previsto);
        assertNotNull(entityBean);
        assertEquals(previsto2, reflectionService.getPropertyValue(entityBean, keyPropertyName));
        message = String.format("13) save -> Modifica la entity [%s].%s in [%s].%s", previsto, sorgente, previsto, previsto2);
        System.out.println(message);

        ottenutoBooleano = crudBackend.isExistByKey(sorgente);
        message = String.format("14) isExistKey -> Non esiste la entity [%s].%s individuata dal valore '%s' della keyProperty [%s]", previsto, sorgente, sorgente, keyPropertyName);
        assertFalse(ottenutoBooleano);
        System.out.println(message);
        ottenutoBooleano = crudBackend.isExistByKey(previsto2);
        assertTrue(ottenutoBooleano);
        message = String.format("15) isExistKey -> Esiste la entity [%s].%s individuata dal valore '%s' della keyProperty [%s]", previsto, previsto2, previsto2, keyPropertyName);
        System.out.println(message);
        ottenutoBooleano = crudBackend.isExistByProperty(keyPropertyName, previsto2);
        message = String.format("16) isExistProperty -> Esiste la entity [%s].%s individuata dal valore '%s' della property [%s]", previsto, previsto2, previsto2, keyPropertyName);
        assertTrue(ottenutoBooleano);
        System.out.println(message);

        System.out.println(VUOTA);

        ottenutoBooleano = crudBackend.delete(entityBean);
        assertTrue(ottenutoBooleano);
        message = String.format("17) delete -> Cancello la entity [%s].%s", previsto, previsto2);
        System.out.println(message);

        ottenutoBooleano = crudBackend.isExistById(previsto);
        message = String.format("18) isExistId -> Alla fine, nella collection '%s' non esiste più la entity [%s] che è stata cancellata", collectionName, previsto);
        System.out.println(message);
    }


    @Test
    @Order(70)
    @DisplayName("70--------")
    void test70() {
    }

    //Segnaposto
    @Order(71)
    protected void libero71() {
    }

    //Segnaposto
    @Order(72)
    protected void libero72() {
    }


    //Segnaposto
    @Order(73)
    protected void libero73() {
    }

    @Test
    @Order(80)
    @DisplayName("80--------")
    void test80() {
    }

    //Segnaposto
    @Order(81)
    protected void libero81() {
    }

    //Segnaposto
    @Order(82)
    protected void libero82() {
    }


    //Segnaposto
    @Order(83)
    protected void libero83() {
    }

    //    @Test
    //    @Order(90)
    //    @DisplayName("90--------")
    //    void test90() {
    //    }


    protected Object getParamEsistente() {
        return sorgente;
    }

    /**
     * Qui passa al termine di ogni singolo test <br>
     */
    @AfterEach
    void tearDown() {
    }


    /**
     * Qui passa una volta sola, chiamato alla fine di tutti i tests <br>
     */
    @AfterAll
    void tearDownAll() {
    }

}