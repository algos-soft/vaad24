package it.algos.vaad24.backend.enumeration;

import java.util.*;

/**
 * Project it.algos.vaadflow
 * Created by Algos
 * User: gac
 * Date: gio, 12-lug-2018
 * Time: 17:38
 * <p>
 * Template di schedule preconfigurati, con nota esplicativa utilizzabile nelle info
 * Pattern:
 * minuti, ore, giornoMese, mese, giornoSettimana
 * <p>
 * minuti -> i valori ammessi vanno da 0 a 59.
 * <p>
 * ore -> i valori ammessi vanno da 0 a 23.
 * <p>
 * giornoMese -> i valori ammessi vanno da 1 a 31
 * <p>
 * mese -> i valori ammessi vanno da 1 (gennaio) a 12 (dicembre) , oppure è possibile usare le stringhe-equivalenti:
 * "jan", "feb", "mar", "apr", "may", "jun", "jul", "aug", "sep", "oct", "nov" e "dec".
 * <p>
 * I valori ammessi vanno da 0 (domenica) a 6 (sabato), oppure è possibile usare le stringhe-equivalenti:
 * "sun", "mon", "tue", "wed", "thu", "fri" e "sat".
 * <p>
 * asterisco -> tutti i minuti, tutte le ore, tutti i giorni del mese
 * <p>
 * 1,2,3,4,5 -> ogni lunedì, martedì, mercoledì, giovedì e venerdì
 * 1-5 -> ogni lunedì, martedì, mercoledì, giovedì e venerdì
 * <p>
 * 0 5 * * *|8 10 * * *|22 17 * * * -> ogni giorno di ogni mese alle ore 05:00, alle ore 10:08 e alle ore 17:22
 * <p>
 *
 * @see <a href="http://www.sauronsoftware.it/projects/cron4j/manual.php">...</a>
 */
public enum AESchedule {


    /**
     * Descrizione: ogni giorno a mezzanotte <br>
     */
    giorno("0 0 * * *", "ogni giorno a mezzanotte", 1),

    /**
     * Descrizione: ogni minuto
     */
    minuto("* * * * *", "ogni minuto."),

    /**
     * Pattern: 0 0 * * mon
     * Descrizione: ogni settimana ai cinque dopo mezzanotte tra domenica e lunedì
     */
    zeroCinqueLunedi("5 0 * * mon", "ogni settimana ai cinque dopo mezzanotte tra domenica e lunedì.", 7),

    /**
     * Pattern: 0 0 * * sun,tue,wed,thu,fri,sat
     * Descrizione: ai cinque dopo mezzanotte di ogni giorno della settimana esclusa la notte tra domenica e lunedì
     */
    zeroCinqueNoLunedi("5 0 * * sun,tue,wed,thu,fri,sat", "ai cinque dopo mezzanotte di ogni giorno della settimana esclusa la notte tra domenica e lunedì.", 1),

    /**
     * Pattern: 0 2 * * sun,tue,wed,thu,fri,sat
     * Descrizione: alle due di ogni mattina della settimana escluso il lunedì
     */
    dueNoLunedi("0 2 * * sun,tue,wed,thu,fri,sat", "alle due di ogni mattina della settimana escluso il lunedì.", 1),

    /**
     * Pattern: 0 4 * * sun,tue,wed,thu,fri,sat
     * Descrizione: alle quattro di ogni mattina della settimana escluso il lunedì
     */
    quattroNoLunedi("0 4 * * sun,tue,wed,thu,fri,sat", "alle quattro di ogni mattina della settimana escluso il lunedì.", 1),

    /**
     * Pattern: 0 10 * * tue
     * Descrizione: alle dieci di ogni martedì
     */
    dieciMartedi("0 10 * * tue", "alle dieci di ogni martedì.", 7),

    /**
     * Pattern: 0 10 * * wed
     * Descrizione: alle dieci di ogni mercoledì
     */
    dieciMercoledi("0 10 * * wed", "alle dieci di ogni mercoledì.", 7),

    /**
     * Pattern: 0 10 * * thu
     * Descrizione: alle dieci di ogni giovedì
     */
    dieciGiovedi("0 10 * * thu", "alle dieci di ogni giovedì.", 7),

    /**
     * Pattern: 0 10 * * fri
     * Descrizione: alle dieci di ogni venerdì
     */
    dieciVenerdi("0 10 * * fri", "alle dieci di ogni venerdì.", 7),

    /**
     * Pattern: 0 10 * * sat
     * Descrizione: alle dieci di ogni sabato
     */
    dieciSabato("0 10 * * sat", "alle dieci di ogni sabato.", 7),

    /**
     * Pattern: 0 10 * * sun
     * Descrizione: alle dieci di ogni domenica
     */
    dieciDomenica("0 10 * * sun", "alle dieci di ogni domenica.", 7),

    /**
     * Pattern: 0 3 * * mon
     * Descrizione: alle tre di ogni lunedì
     */
    lunedi3("0 3 * * mon", "alle 3 di ogni lunedì."),

    /**
     * Pattern: 0 7 * * mon
     * Descrizione: alle sette di ogni lunedì
     */
    lunedi7("0 7 * * mon", "alle 7 di ogni lunedì."),

    /**
     * Pattern: 0 3 * * tue
     * Descrizione: alle tre di ogni martedì
     */
    martedi3("0 3 * * tue", "alle 3 di ogni martedì."),

    /**
     * Pattern: 0 7 * * tue
     * Descrizione: alle sette di ogni martedì
     */
    martedi7("0 7 * * tue", "alle 7 di ogni martedì."),

    /**
     * Pattern: 0 3 * * wed
     * Descrizione: alle tre di ogni mercoledì
     */
    mercoledi3("0 3 * * wed", "alle 3 di ogni mercoledì."),

    /**
     * Pattern: 0 7 * * wed
     * Descrizione: alle sette di ogni mercoledì
     */
    mercoledi7("0 7 * * wed", "alle 7 di ogni mercoledì."),

    /**
     * Pattern: 0 3 * * thu
     * Descrizione: alle tre di ogni giovedì
     */
    giovedi3("0 3 * * thu", "alle 3 di ogni giovedì."),

    /**
     * Pattern: 0 7 * * thu
     * Descrizione: alle sette di ogni giovedì
     */
    giovedi7("0 7 * * thu", "alle 7 di ogni giovedì."),

    /**
     * Pattern: 0 3 * * fri
     * Descrizione: alle tre di ogni venerdì
     */
    venerdi3("0 3 * * fri", "alle 3 di ogni venerdì."),

    /**
     * Pattern: 0 7 * * fri
     * Descrizione: alle sette di ogni venerdì
     */
    venerdi7("0 7 * * fri", "alle 7 di ogni venerdì."),

    /**
     * Pattern: 0 3 * * sat
     * Descrizione: alle tre di ogni sabato
     */
    sabato3("0 3 * * sat", "alle 3 di ogni sabato."),

    /**
     * Pattern: 0 7 * * sat
     * Descrizione: alle sette di ogni sabato
     */
    sabato7("0 7 * * sat", "alle 7 di ogni sabato."),

    /**
     * Pattern: 0 3 * * sun
     * Descrizione: alle tre di ogni domenica
     */
    domenica3("0 3 * * sun", "alle 3 di ogni domenica."),

    /**
     * Pattern: 0 7 * * sun
     * Descrizione: alle sette di ogni domenica
     */
    domenica7("0 7 * * sun", "alle 7 di ogni domenica."),

    /**
     * Pattern: 0 3 * * mon,wed,fri
     * Descrizione: alle 3 di ogni giorno dispari (lunedì, mercoledì, venerdì)
     */
    dispari3("0 3 * * 1,3,5", "alle 3 di ogni giorno dispari (lunedì, mercoledì, venerdì)"),

    /**
     * Pattern: 0 3 * * mon,wed,fri,sun
     * Descrizione: alle 3 di ogni giorno dispari (lunedì, mercoledì, venerdì) più la domenica
     */
    disparipiudomenica3("0 3 * * 1,3,5,0", "alle 3 di ogni giorno dispari (lunedì, mercoledì, venerdì) più la domenica"),

    /**
     * Pattern: 0 5 * * mon,wed,fri
     * Descrizione: alle 5 di ogni giorno dispari (lunedì, mercoledì, venerdì)
     */
    dispari5("0 5 * * 1,3,5", "alle 5 di ogni giorno dispari (lunedì, mercoledì, venerdì)"),

    /**
     * Pattern: 0 3 * * tue,thu,sat
     * Descrizione: alle 3 di ogni giorno pari (martedì, giovedì, sabato)
     */
    pari3("0 3 * * 2,4,6", "alle 3 di ogni giorno pari (martedì, giovedì, sabato)"),

    /**
     * Pattern: 0 5 * * tue,thu,sat
     * Descrizione: alle 5 di ogni giorno pari (martedì, giovedì, sabato)
     */
    pari5("0 5 * * 2,4,6", "alle 5 di ogni giorno pari (martedì, giovedì, sabato)"),

    /**
     * Pattern: 0 5 * * tue,thu,sat,sun
     * Descrizione: alle 5 di ogni giorno pari (martedì, giovedì, sabato) più la domenica
     */
    paripiudomenica5("0 5 * * 2,4,6,0", "alle 5 di ogni giorno pari (martedì, giovedì, sabato) più la domenica"),

    ;

    /**
     * pattern di schedulazione di tipo 'UNIX'
     */
    private String pattern;

    /**
     * Nota esplicativa da inserire nei log
     */
    private String nota;

    private int giorniNext;

    /**
     * @param pattern di schedulazione di tipo 'UNIX'
     * @param nota    esplicativa da inserire nei log
     */
    AESchedule(String pattern, String nota) {
        this(pattern, nota, 0);
    }


    /**
     * @param pattern di schedulazione di tipo 'UNIX'
     * @param nota    esplicativa da inserire nei log
     */
    AESchedule(String pattern, String nota, int giorniNext) {
        this.pattern = pattern;
        this.nota = nota;
        this.giorniNext = giorniNext;
    }

    public static List<AESchedule> getAllEnums() {
        return Arrays.stream(values()).toList();
    }

    public static List<String> getAllPattern() {
        List<String> listaTags = new ArrayList<>();

        getAllEnums().forEach(type -> listaTags.add(type.getPattern()));
        return listaTags;
    }

    public String getPattern() {
        return pattern;
    }


    public void setPattern(String pattern) {
        this.pattern = pattern;
    }


    public String getNota() {
        return nota;
    }


    public void setNota(String nota) {
        this.nota = nota;
    }

    public int getGiorniNext() {
        return giorniNext;
    }
}// end of enum

