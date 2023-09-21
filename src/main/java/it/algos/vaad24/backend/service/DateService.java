package it.algos.vaad24.backend.service;

import static it.algos.vaad24.backend.boot.VaadCost.*;
import it.algos.vaad24.backend.enumeration.*;
import it.algos.vaad24.backend.exception.*;
import it.algos.vaad24.backend.wrapper.*;
import org.springframework.beans.factory.config.*;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.*;

import java.text.*;
import java.time.*;
import java.time.format.*;
import java.util.*;

/**
 * Project vaadin23
 * Created by Algos
 * User: gac
 * Date: lun, 07-mar-2022
 * Time: 14:57
 * <p>
 * Classe di libreria; NON deve essere astratta, altrimenti SpringBoot non la costruisce <br>
 * Estende la classe astratta AbstractService che mantiene i riferimenti agli altri services <br>
 * L'istanza può essere richiamata con: <br>
 * 1) StaticContextAccessor.getBean(ADateService.class); <br>
 * 3) @Autowired public DateService annotation; <br>
 * <p>
 * Annotated with @Service (obbligatorio, se si usa la catena @Autowired di SpringBoot) <br>
 * NOT annotated with @SpringComponent (inutile, esiste già @Service) <br>
 * Annotated with @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON) (obbligatorio) <br>
 */
@Service
@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
public class DateService extends AbstractService {

    public static final String INFERIORE_SECONDO = "meno di un secondo";

    public static final String INFERIORE_MINUTO = "meno di un minuto";

    private static final String MILLI_SECONDI = " millisecondi";

    private static final String SECONDI = " sec.";

    private static final String MINUTI = " min.";

    private static final String ORA = " ora";

    private static final String ORE = " ore";

    private static final String GIORNO = " giorno";

    private static final String GIORNI = " gg.";

    private static final String ANNO = " anno";

    private static final String ANNI = " anni";

    private static final long MAX_MILLISEC = 1000;

    private static final long MAX_SECONDI = 60 * MAX_MILLISEC;

    private static final long MAX_MINUTI = 60 * MAX_SECONDI;

    private static final long MAX_ORE = 24 * MAX_MINUTI;

    private static final long MAX_GIORNI = 365 * MAX_ORE;

    /**
     * Restituisce come stringa (intelligente) un durata espressa in long <br>
     *
     * @return tempo esatto in millisecondi in forma leggibile
     */
    public String deltaTextEsatto(final long inizio) {
        return deltaTextEsatto(inizio, System.currentTimeMillis());
    }

    /**
     * Restituisce come stringa (intelligente) un durata espressa in long <br>
     *
     * @return tempo esatto in millisecondi in forma leggibile
     */
    public String deltaTextEsatto(final long inizio, final long fine) {
        return textService.format(fine - inizio) + MILLI_SECONDI;
    }

    /**
     * Restituisce come stringa (intelligente) un durata espressa in long <br>
     * Esegue degli arrotondamenti <br>
     *
     * @return tempo arrotondato in forma leggibile
     */
    public String deltaText(final long inizio) {
        long fine = System.currentTimeMillis();
        return toText(fine - inizio);
    }


    /**
     * Restituisce come stringa (intelligente) una durata espressa in long
     * - Meno di 1 secondo
     * - Meno di 1 minuto
     * - Meno di 1 ora
     * - Meno di 1 giorno
     * - Meno di 1 anno
     *
     * @param durata in millisecondi
     *
     * @return durata (arrotondata e semplificata) in forma leggibile
     */
    public static String toText(final long durata) {
        String tempo = "null";
        long div;
        long mod;

        if (durata < MAX_MILLISEC) {
            tempo = INFERIORE_SECONDO;
        }
        else {
            if (durata < MAX_SECONDI) {
                div = Math.floorDiv(durata, MAX_MILLISEC);
                mod = Math.floorMod(durata, MAX_MILLISEC);
                if (div < 60) {
                    tempo = div + SECONDI;
                }
                else {
                    tempo = "1" + MINUTI;
                }
            }
            else {
                if (durata < MAX_MINUTI) {
                    div = Math.floorDiv(durata, MAX_SECONDI);
                    mod = Math.floorMod(durata, MAX_SECONDI);
                    if (div < 60) {
                        tempo = div + MINUTI;
                    }
                    else {
                        tempo = "1" + ORA;
                    }
                }
                else {
                    if (durata < MAX_ORE) {
                        div = Math.floorDiv(durata, MAX_MINUTI);
                        mod = Math.floorMod(durata, MAX_MINUTI);
                        if (div < 24) {
                            if (div == 1) {
                                tempo = div + ORA;
                            }
                            else {
                                tempo = div + ORE;
                            }
                            tempo += " e " + toText(durata - (div * MAX_MINUTI));
                        }
                        else {
                            tempo = "1" + GIORNO;
                        }
                    }
                    else {
                        if (durata < MAX_GIORNI) {
                            div = Math.floorDiv(durata, MAX_ORE);
                            mod = Math.floorMod(durata, MAX_ORE);
                            if (div < 365) {
                                if (div == 1) {
                                    tempo = div + GIORNO;
                                }
                                else {
                                    tempo = div + GIORNI;
                                }
                            }
                            else {
                                tempo = "1" + ANNO;
                            }
                        }
                        else {
                            div = Math.floorDiv(durata, MAX_GIORNI);
                            mod = Math.floorMod(durata, MAX_GIORNI);
                            if (div == 1) {
                                tempo = div + ANNO;
                            }
                            else {
                                tempo = div + ANNI;
                            }
                        }
                    }
                }
            }
        }

        return tempo;
    }

    public String toTextSecondi(final long durata) {
        return durata < 1 ? INFERIORE_SECONDO : toText(durata * 1000);
    }

    public String toTextMinuti(final long durata) {
        return durata < 1 ? INFERIORE_MINUTO : toTextSecondi(durata * 60);
    }

    public String get() {
        return LocalDateTime.now().format(DateTimeFormatter.ofPattern("d-MMM-yy"));
    }

    public String get(LocalDate localDate) {
        return localDate != null ? localDate.format(DateTimeFormatter.ofPattern("d-MMM-yy", Locale.ITALIAN)) : NULL;
    }

    public String get(LocalDateTime localDateTime) {
        return localDateTime != null ? localDateTime.format(DateTimeFormatter.ofPattern("d-MMM-yy H:mm", Locale.ITALIAN)) : NULL;
    }

    /**
     * Costruisce tutti i giorni dell'anno <br>
     * Considera anche l'anno bisestile <br>
     * <p>
     * Restituisce un array di Map <br>
     * Ogni mappa ha: <br>
     * numeroMese <br>
     * #progressivoNormale <br>
     * #progressivoBisestile <br>
     * nome (numero per il primo del mese) <br>
     * titolo (1° per il primo del mese) <br>
     */
    public List<HashMap> getAllGiorni() {
        List<HashMap> listaAnno = new ArrayList<HashMap>();
        List<HashMap> listaMese;
        int progAnno = 0;

        for (int k = 1; k <= 12; k++) {
            listaMese = getGiorniMese(k, progAnno);
            for (HashMap mappa : listaMese) {
                listaAnno.add(mappa);
            }
            progAnno += listaMese.size();
        }

        return listaAnno;
    }

    /**
     * Costruisce tutti i giorni del mese <br>
     * Considera anche l'anno bisestile <br>
     * <p>
     * Restituisce un array di Map <br>
     * Ogni mappa ha: <br>
     * numeroMese <br>
     * nomeMese <br>
     * #progressivoNormale <br>
     * #progressivoBisestile <br>
     * nome  (numero per il primo del mese) <br>
     * titolo (1° per il primo del mese) <br>
     *
     * @param numMese  numero del mese, partendo da 1 per gennaio
     * @param progAnno numero del giorno nell'anno, partendo da 1 per il 1° gennaio
     *
     * @return lista di mappe, una per ogni giorno del mese considerato
     */
    private List<HashMap> getGiorniMese(int numMese, int progAnno) {
        List<HashMap> listaMese = new ArrayList<HashMap>();
        HashMap mappa;
        int giorniDelMese;
        String nomeMese;
        AEMese mese = AEMese.getMese(numMese);
        nomeMese = AEMese.getLong(numMese);
        giorniDelMese = AEMese.getGiorni(numMese, 2016);
        final int taglioBisestile = 60;
        String tag;
        String tagUno;

        for (int k = 1; k <= giorniDelMese; k++) {
            progAnno++;
            tag = k + SPAZIO + nomeMese;
            mappa = new HashMap();

            mappa.put(KEY_MAPPA_GIORNI_MESE_NUMERO, numMese);
            mappa.put(KEY_MAPPA_GIORNI_MESE_TESTO, nomeMese);
            mappa.put(KEY_MAPPA_GIORNI_NOME, tag);
            mappa.put(KEY_MAPPA_GIORNI_BISESTILE, progAnno);
            mappa.put(KEY_MAPPA_GIORNI_NORMALE, progAnno);
            mappa.put(KEY_MAPPA_GIORNI_MESE_MESE, mese);

            if (k == 1) {
                mappa.put(KEY_MAPPA_GIORNI_TITOLO, PRIMO_GIORNO_MESE + SPAZIO + nomeMese);
            }
            else {
                mappa.put(KEY_MAPPA_GIORNI_TITOLO, tag);
            }
            //--gestione degli anni bisestili
            if (progAnno == taglioBisestile) {
                mappa.put(KEY_MAPPA_GIORNI_NORMALE, 0);
            }
            if (progAnno > taglioBisestile) {
                mappa.put(KEY_MAPPA_GIORNI_NORMALE, progAnno - 1);
            }
            listaMese.add(mappa);
        }
        return listaMese;
    }


    /**
     * Anno bisestile
     *
     * @param anno da validare
     *
     * @return true se l'anno è bisestile
     */
    public boolean isBisestile(int anno) {
        boolean bisestile = false;
        int deltaGiuliano = 4;
        int deltaSecolo = 100;
        int deltaGregoriano = 400;
        int inizioGregoriano = 1582;
        boolean bisestileSecolo = false;

        bisestile = divisibileEsatto(anno, deltaGiuliano);
        if (anno > inizioGregoriano && bisestile) {
            if (divisibileEsatto(anno, deltaSecolo)) {
                if (divisibileEsatto(anno, deltaGregoriano)) {
                    bisestile = true;
                }
                else {
                    bisestile = false;
                }
            }
        }

        return bisestile;
    }

    public boolean divisibileEsatto(int dividendo, int divisore) {
        return dividendo % divisore == 0;
    }


    /**
     * Convert java.util.Date to java.time.LocalDateTime
     * Date HA ore, minuti e secondi
     * LocalDateTime HA ore, minuti e secondi
     * Non si perde nulla
     *
     * @param data da convertire
     *
     * @return data e ora locale
     */
    public LocalDateTime dateToLocalDateTime(Date data) {
        Instant instant = Instant.ofEpochMilli(data.getTime());
        return LocalDateTime.ofInstant(instant, ZoneId.systemDefault());
    }

    /**
     * Restituisce la data nella forma del pattern ricevuto. <br>
     * <p>
     * Returns a string representation of the date <br>
     *
     * @param localDateTime da rappresentare
     * @param pattern       per la formattazione
     *
     * @return la data sotto forma di stringa
     */
    public String get(LocalDateTime localDateTime, AETypeDate pattern) {
        return get(localDateTime, pattern.getPattern());
        //        if (pattern.isSenzaTime()) {
        //            return VUOTA;
        //        }
        //        else {
        //            return get(localDateTime, pattern.getPattern());
        //        }
    }


    /**
     * Restituisce la data nella forma del pattern ricevuto. <br>
     * <p>
     * Returns a string representation of the date <br>
     *
     * @param localDate da rappresentare
     * @param pattern   per la formattazione
     *
     * @return la data sotto forma di stringa
     */
    public String get(LocalDate localDate, AETypeDate pattern) {
        if (pattern.isSenzaTime()) {
            return get(localDate, pattern.getPattern());
        }
        else {
            return get(localDate);
        }
    }


    /**
     * Restituisce la data nella forma del pattern ricevuto. <br>
     * <p>
     * Returns a string representation of the date <br>
     *
     * @param localTime da rappresentare
     * @param pattern   per la formattazione
     *
     * @return la data sotto forma di stringa
     */
    public String get(LocalTime localTime, AETypeDate pattern) {
        return get(localTime, pattern.getPattern());
    }


    /**
     * Restituisce la data nella forma del pattern ricevuto. <br>
     * <p>
     * Returns a string representation of the date <br>
     *
     * @param localDateTime da rappresentare
     * @param pattern       per la formattazione
     *
     * @return la data sotto forma di stringa
     */
    public String get(LocalDateTime localDateTime, String pattern) {
        if (localDateTime != null) {
            return localDateTime.format(DateTimeFormatter.ofPattern(pattern, LOCALE));
        }
        else {
            return VUOTA;
        }
    }


    /**
     * Restituisce la data nella forma del pattern ricevuto. <br>
     * <p>
     * Returns a string representation of the date <br>
     *
     * @param localDate da rappresentare
     * @param pattern   per la formattazione
     *
     * @return la data sotto forma di stringa
     */
    public String get(LocalDate localDate, String pattern) {
        if (localDate != null) {
            return localDate.format(DateTimeFormatter.ofPattern(pattern, LOCALE));
        }
        else {
            return VUOTA;
        }
    }


    /**
     * Restituisce la data nella forma del pattern ricevuto. <br>
     * <p>
     * Returns a string representation of the date <br>
     *
     * @param localTime da rappresentare
     * @param pattern   per la formattazione
     *
     * @return la data sotto forma di stringa
     */
    public String get(LocalTime localTime, String pattern) {
        if (localTime != null) {
            return localTime.format(DateTimeFormatter.ofPattern(pattern, LOCALE));
        }
        else {
            return VUOTA;
        }
    }

    /**
     * Trasforma la data e l'orario nel formato standard ISO 8601.
     * <p>
     * 2017-02-16T21:00:00
     * Unsupported field: OffsetSeconds
     * Dovrebbe essere 2017-02-16T21:00:00.000+01:00 per essere completa
     *
     * @param localDateTime fornito
     *
     * @return testo standard ISO senza OffsetSeconds
     */
    public String getISO(LocalDateTime localDateTime) {
        return localDateTime.format(DateTimeFormatter.ofPattern(AETypeDate.iso8601.getPattern(), LOCALE));
    }

    /**
     * Trasforma la data e l'orario nel formato standard Algos (gac).
     * <p>
     * 2017-02-16T21:00:00
     * Unsupported field: OffsetSeconds
     * Dovrebbe essere 2017-02-16T21:00:00.000+01:00 per essere completa
     *
     * @param localDateTime fornito
     *
     * @return testo standard ISO senza OffsetSeconds
     */
    public String getStandard(LocalDateTime localDateTime) {
        return localDateTime.format(DateTimeFormatter.ofPattern(AETypeDate.standard.getPattern(), LOCALE));
    }

    /**
     * Restituisce la data nella forma del pattern previsto. <br>
     * <p>
     * Returns a string representation of the date <br>
     * Not using leading zeroes in day <br>
     * Two numbers for year <br>
     * Pattern: d-M-yy <br>
     * Esempio: 5-4-17 <br>
     *
     * @param localDate da rappresentare
     *
     * @return la data sotto forma di stringa
     */
    public String getShort(LocalDateTime localDateTime) {
        return localDateTime.format(DateTimeFormatter.ofPattern(AETypeDate.dateShort.getPattern(), LOCALE));
    }


    /**
     * Restituisce la data nella forma del pattern previsto. <br>
     * <p>
     * Returns a string representation of the date <br>
     * Pattern: d-MMM-yy <br>
     * Esempio: 5-ott-14 <br>
     *
     * @param localDate da rappresentare
     *
     * @return la data sotto forma di stringa
     */
    public String getNormale(LocalDateTime localDateTime) {
        return localDateTime.format(DateTimeFormatter.ofPattern(AETypeDate.dateNormal.getPattern(), LOCALE));
    }


    /**
     * Restituisce la data nella forma del pattern previsto. <br>
     * <p>
     * Returns a string representation of the date <br>
     * Pattern: d-MMMM-yyy <br>
     * Esempio: 5-ottobre-2014 <br>
     *
     * @param localDate da rappresentare
     *
     * @return la data sotto forma di stringa
     */
    public String getLunga(LocalDate localDate) {
        return get(localDate, AETypeDate.dateLong);
    }


    /**
     * Restituisce la data nella forma del pattern previsto. <br>
     * <p>
     * Returns a string representation of the date <br>
     * Pattern: EEEE, d-MMMM-yyy <br>
     * Esempio: domenica, 5-ottobre-2014 <br>
     *
     * @param localDate da rappresentare
     *
     * @return la data sotto forma di stringa
     */
    public String getCompleta(LocalDateTime localDateTime) {
        return localDateTime.format(DateTimeFormatter.ofPattern(AETypeDate.dataCompleta.getPattern(), LOCALE));
    }

    /**
     * Restituisce la data nella forma del pattern previsto. <br>
     * <p>
     * Returns a string representation of the date <br>
     * Pattern: EEEE, d-MMMM-yyy <br>
     * Esempio: dom, 5-ott-2014 <br>
     *
     * @param localDate da rappresentare
     *
     * @return la data sotto forma di stringa
     */
    public String getCompletaShort(LocalDateTime localDateTime) {
        return localDateTime.format(DateTimeFormatter.ofPattern(AETypeDate.dataCompletaShort.getPattern(), LOCALE));
    }


    /**
     * Restituisce la data e l' orario nella forma del pattern previsto. <br>
     * <p>
     * Returns a string representation of the date <br>
     * Pattern: d-M-yy H:mm <br>
     * Esempio: 18-4-17 13:45 <br>
     *
     * @param localDateTime da rappresentare
     *
     * @return la data sotto forma di stringa
     */
    public String getNormaleOrario(LocalDateTime localDateTime) {
        return localDateTime.format(DateTimeFormatter.ofPattern(AETypeDate.normaleOrario.getPattern(), LOCALE));
    }


    /**
     * Restituisce la data e l' orario nella forma del pattern previsto. <br>
     * <p>
     * Returns a string representation of the date <br>
     * Pattern: EEEE, d-MMMM-yyy 'alle' H:mm <br>
     * Esempio: domenica, 5-ottobre-2014 alle 13:45 <br>
     *
     * @param localDateTime da rappresentare
     *
     * @return la data sotto forma di stringa
     */
    public String getDataOrarioCompleta(LocalDateTime localDateTime) {
        return get(localDateTime, AETypeDate.completaOrario);
    }


    /**
     * Restituisce la data e l' orario nella forma del pattern previsto. <br>
     * <p>
     * Returns a string representation of the date <br>
     * Pattern: d-M-yy 'alle' H:mm <br>
     * Esempio: 5-ott-14 alle 13:45 <br>
     *
     * @param localDateTime da rappresentare
     *
     * @return la data sotto forma di stringa
     */
    public String getDataOrarioBreve(LocalDateTime localDateTime) {
        return get(localDateTime, AETypeDate.breveOrario);
    }


    /**
     * Restituisce l' orario corrente nella forma del pattern previsto. <br>
     * <p>
     * Returns a string representation of the date <br>
     *
     * @return la data sotto forma di stringa
     */
    public String getOrario() {
        return getOrario(LocalTime.now());
    }


    /**
     * Restituisce l' orario nella forma del pattern previsto. <br>
     * <p>
     * Returns a string representation of the date <br>
     *
     * @param localTime da rappresentare
     *
     * @return la data sotto forma di stringa
     */
    public String getOrario(LocalTime localTime) {
        return localTime != null ? localTime.format(DateTimeFormatter.ofPattern(AETypeDate.orario.getPattern(), LOCALE)) : VUOTA;
    }


    /**
     * Restituisce l' orario nella forma del pattern previsto. <br>
     * <p>
     * Returns a string representation of the date <br>
     *
     * @param localDateTime da rappresentare
     *
     * @return la data sotto forma di stringa
     */
    public String getOrario(LocalDateTime localDateTime) {
        return getOrario(localDateTimeToLocalTime(localDateTime));
    }


    /**
     * Restituisce l' orario nella forma del pattern previsto. <br>
     * <p>
     * Returns a string representation of the time <br>
     * Pattern: H:mm:ss <br>
     * Esempio:  13:45:08 <br>
     *
     * @param localDateTime da rappresentare
     *
     * @return la data sotto forma di stringa
     */
    public String getOrarioCompleto(LocalDateTime localDateTime) {
        return localDateTimeToLocalTime(localDateTime).format(DateTimeFormatter.ofPattern(AETypeDate.orarioLungo.getPattern(), LOCALE));
    }

    /**
     * Convert java.util.LocalDateTime to java.time.LocalTime
     * Estrae la sola parte di Time
     * LocalDateTime HA anni, giorni, ore, minuti e secondi
     * LocalTime NON ha anni e giorni
     * Si perdono quindi gli anni ed i giorni di LocalDateTime
     *
     * @param localDateTime da convertire
     *
     * @return time senza il giorno
     */
    public LocalTime localDateTimeToLocalTime(LocalDateTime localDateTime) {
        return LocalTime.of(localDateTime.getHour(), localDateTime.getMinute(), localDateTime.getSecond());
    }


    /**
     * Costruisce una data da una stringa in formato ISO 8601
     *
     * @param isoStringa da leggere
     *
     * @return data costruita
     */
    public Date oldDateFromISO(String isoStringa) {
        Date data = null;
        DateFormat format = new SimpleDateFormat(AETypeDate.iso8601.getPattern());

        try {
            data = format.parse(isoStringa);
        } catch (Exception unErrore) {
            logService.error(new WrapLog().exception(new AlgosException(unErrore)).usaDb());
        }

        return data;
    }

    /**
     * Costruisce una data da una stringa in formato ISO 8601
     *
     * @param isoStringa da leggere
     *
     * @return data costruita
     */
    public LocalDateTime dateTimeFromISO(String isoStringa) {
        Date data = oldDateFromISO(isoStringa);
        return dateToLocalDateTime(data);
    }


    /**
     * Costruisce una data completa da una stringa in formato ISO 8601
     *
     * @param isoStringa da leggere
     *
     * @return data costruita
     */
    public LocalDateTime dateFromISO(String isoStringa) {
        Date data = oldDateFromISO(isoStringa);
        return data != null ? dateToLocalDateTime(data) : null;
        //        ;
        //        //        LocalDateTime format = new LocalDateTimeDeserializer(AETypeData.iso8601.getPattern());
        //        //        return localDateTime.format(DateTimeFormatter.ofPattern(AETypeData.iso8601.getPattern(), LOCALE));
        //
        //        //        try {
        //        //            data = format.parse(isoStringa);
        //        //        } catch (Exception unErrore) {
        //        //            logService.error(unErrore, this.getClass(), "dateFromISO");
        //        //
        //        //        }
        //
        //        return data;
    }

}