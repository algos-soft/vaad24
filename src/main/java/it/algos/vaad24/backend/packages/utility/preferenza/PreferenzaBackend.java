package it.algos.vaad24.backend.packages.utility.preferenza;

import static it.algos.vaad24.backend.boot.VaadCost.*;
import it.algos.vaad24.backend.boot.*;
import it.algos.vaad24.backend.enumeration.*;
import it.algos.vaad24.backend.exception.*;
import it.algos.vaad24.backend.interfaces.*;
import it.algos.vaad24.backend.logic.*;
import it.algos.vaad24.backend.wrapper.*;
import it.algos.vaad24.ui.dialog.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.data.mongodb.repository.*;
import org.springframework.stereotype.*;

import java.util.*;

/**
 * Project vaadin23
 * Created by Algos
 * User: gac
 * Date: sab, 26-mar-2022
 * Time: 14:02
 * <p>
 * Service di una entityClazz specifica e di un package <br>
 * Garantisce i metodi di collegamento per accedere al database <br>
 * Non mantiene lo stato di una istanza entityBean <br>
 * Mantiene lo stato della entityClazz <br>
 * NOT annotated with @SpringComponent (inutile, esiste già @Service) <br>
 * NOT annotated with @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON) (inutile, esiste già @Service) <br>
 */
@Service
@Qualifier(TAG_PRE)
public class PreferenzaBackend extends CrudBackend {

    public PreferenzaRepository repository;

    public Runnable refreshHandler;

    /**
     * Costruttore @Autowired (facoltativo) @Qualifier (obbligatorio) <br>
     * In the newest Spring release, it’s constructor does not need to be annotated with @Autowired annotation <br>
     * Si usa un @Qualifier(), per specificare la classe che incrementa l'interfaccia repository <br>
     * Si usa una costante statica, per essere sicuri di scriverla uguale a quella di xxxRepository <br>
     * Regola la classe di persistenza dei dati specifica e la passa al costruttore della superclasse <br>
     * Regola la entityClazz (final nella superclasse) associata a questo service <br>
     *
     * @param crudRepository per la persistenza dei dati
     */
    public PreferenzaBackend(@Autowired @Qualifier(TAG_PRE) final MongoRepository crudRepository) {
        super(crudRepository, Preferenza.class);
        this.repository = (PreferenzaRepository) crudRepository;
    }


    public boolean existsByKeyCode(final String code) {
        return findByKey(code) != null;
    }


    public Preferenza findByKey(final String keyCode) {
        return repository.findFirstByCode(keyCode);
    }


    public Object getValore(final AIGenPref enumeration) {
        return getValore(enumeration.getKeyCode());
    }

    public Object getValore(final String keyCode) {
        Preferenza preferenza = findByKey(keyCode);
        return preferenza != null ? preferenza.getValore() : null;
    }


    public boolean setValore(final AIGenPref enumeration, final Object newJavaValue) {
        return setValore(enumeration.getKeyCode(), newJavaValue);
    }


    public boolean setValore(final String keyCode, final Object newJavaValue) {
        boolean modificato = false;
        Object oldJavaValue;
        Preferenza preferenza = findByKey(keyCode);

        if (preferenza != null) {
            oldJavaValue = preferenza.getValore();
            if (!newJavaValue.equals(oldJavaValue)) {
                preferenza.setValore(newJavaValue);
                update(preferenza);
                modificato = true;
            }
        }

        return modificato;
    }

    public void creaAll() {
        for (AIGenPref preferenza : VaadVar.prefList) {
            preferenzaBackend.crea(preferenza);
        }
    }


    /**
     * Inserimento di una preferenza del progetto base Vaadin24 <br>
     * Controlla che la entity non esista già <br>
     */
    public void crea(final AIGenPref pref) {
        String keyCode = pref.getKeyCode();
        AETypePref type = pref.getType();
        Object defaultValue = pref.getDefaultValue();
        String descrizione = pref.getDescrizione();
        boolean needRiavvio = pref.needRiavvio();
        boolean vaad24 = pref.isVaad24();
        boolean dinamica = pref.isDinamica();
        Class<?> enumClazz = pref.getEnumClazz();

        Preferenza preferenza;
        String message;

        if (existsByKeyCode(keyCode)) {
            return;
        }

        if (textService.isEmpty(keyCode)) {
            logService.error(new WrapLog().exception(new AlgosException("Manca il keyCode")).usaDb());
            return;
        }
        if (type == null) {
            message = String.format("Manca il type nella preferenza %s", keyCode);
            logService.error(new WrapLog().exception(new AlgosException(message)).usaDb());
            return;
        }
        if (textService.isEmpty(descrizione)) {
            message = String.format("Manca la descrizione nella preferenza %s", keyCode);
            logService.error(new WrapLog().exception(new AlgosException(message)).usaDb());
            return;
        }
        if (defaultValue == null) {
            message = String.format("Il valore della preferenza %s è nullo", keyCode);
            logService.error(new WrapLog().exception(new AlgosException(message)).usaDb());
            return;
        }

        preferenza = new Preferenza();
        preferenza.code = keyCode;
        preferenza.type = type;
        preferenza.value = type.objectToBytes(defaultValue);
        preferenza.vaad23 = vaad24;
        preferenza.usaCompany = false; //@todo da implementare
        preferenza.needRiavvio = needRiavvio;
        preferenza.visibileAdmin = false; //@todo da implementare
        preferenza.dinamica = dinamica;
        preferenza.descrizione = descrizione;
        preferenza.descrizioneEstesa = descrizione;
        preferenza.enumClazzName = enumClazz != null ? enumClazz.getSimpleName() : null;

        this.add(preferenza);
    }

    public boolean resetStandard(final AIGenPref prefEnum) {
        boolean modificato = false;
        String keyCode;
        Object oldPrefJavaValue = null;
        Object standardEnumJavaValue;
        Preferenza preferenza;

        if (prefEnum == null) {
            return false;
        }
        keyCode = prefEnum.getKeyCode();

        preferenza = findByKey(keyCode);
        if (preferenza == null) {
            return false;
        }
        if (preferenza.isDinamica()) {
            return false;
        }

        oldPrefJavaValue = preferenza.getValore();
        standardEnumJavaValue = prefEnum.getDefaultValue();
        if (!standardEnumJavaValue.equals(oldPrefJavaValue)) {
            preferenza.setValore(standardEnumJavaValue);
            update(preferenza);
            modificato = true;
        }

        return modificato;
    }


    public void refreshDialog(Runnable refreshHandler) {
        this.refreshHandler = refreshHandler;
        appContext.getBean(DialogRefreshPreferenza.class).open(this::refreshAll);
    }

    public void refreshAll() {
        List<AIGenPref> listaPref = VaadVar.prefList;
        boolean almenoUnaModificata = false;
        String message;
        String keyCode;
        Object oldValue;

        for (AIGenPref pref : listaPref) {
            oldValue = this.getValore(pref);
            if (this.resetStandard(pref)) {
                keyCode = pref.getKeyCode();
                message = String.format("Reset preferenza [%s]: %s%s(%s)%s%s", keyCode, oldValue, FORWARD, pref.getType(), FORWARD, pref.getDefaultValue());
                logService.info(new WrapLog().type(AETypeLog.reset).message(message).usaDb());
                almenoUnaModificata = true;
            }
        }

        if (!almenoUnaModificata) {
            message = "Reset preferenze - Tutte le preferenze (escluse quelle dinamiche) avevano già il valore standard";
            logService.info(new WrapLog().type(AETypeLog.reset).message(message).usaDb());
        }

        if (refreshHandler != null) {
            refreshHandler.run();
        }
        Avviso.message("Reset preferenze non dinamiche").success().open();
    }

    public boolean deleteAll() {
        boolean status;
        String message;

        try {
            crudRepository.deleteAll();
        } catch (Exception unErrore) {
            logService.error(unErrore);
            return false;
        }

        status = crudRepository.count() == 0;
        creaAll();

        message = "Ricreate tutte le preferenze";
        logService.info(new WrapLog().type(AETypeLog.reset).message(message).usaDb());
        Avviso.message(message).success().open();

        return status;
    }

}// end of crud backend class
