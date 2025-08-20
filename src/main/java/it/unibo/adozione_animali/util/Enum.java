package it.unibo.adozione_animali.util;

import java.util.HashMap;
import java.util.Map;

public class Enum {

    public enum StatoAnimale {
        DISABILE,
        CRONICO;

        /** Restituisce l'enum dato il nome, oppure null se non valido */
        public static StatoAnimale fromKey(String key) {
            if (key == null) return null;
            try {
                return StatoAnimale.valueOf(key.toUpperCase()); // safe anche se scrivi "cronico"
            } catch (IllegalArgumentException e) {
                return null;
            }
        }

        /** Controlla se la stringa corrisponde a un valore valido */
        public static boolean isValidKey(String key) {
            return fromKey(key) != null;
        }
    }

    public enum TipoCaratteristica {
        FISICO,
        COMPORTAMENTO;

        public static TipoCaratteristica fromKey(String key) {
            if (key == null) return null;
            try {
                return TipoCaratteristica.valueOf(key.toUpperCase());
            } catch (IllegalArgumentException e) {
                return null;
            }
        }

        public static boolean isValidKey(String key) {
            return fromKey(key) != null;
        }
    }

    public enum Specie {
        CANE,
        GATTO,
        RODITORE,
        VOLATILE,
        RETTILE;

        // Restituisce le prime due lettere come chiave
        public String getKey() {
            return this.name().substring(0, 2);
        }

        // Controlla se la stringa corrisponde al nome completo dell'enum
        public boolean matchesName(String s) {
            if (s == null) return false;
            return this.name().equalsIgnoreCase(s);
        }

        // Restituisce la chiave partendo dal nome completo
        public static String keyFromName(String name) {
            if (name == null) return null;
            for (Specie sp : values()) {
                if (sp.matchesName(name)) {
                    return sp.getKey();
                }
            }
            return null;
        }
    }

    public enum TipoSpazio{
        INTERNO,
        ESTERNO,
        GABBIA;

        // Controlla se la stringa corrisponde a uno dei valori dell'enum
        public static boolean matches(String s) {
            if (s == null) return false;
            for (TipoSpazio t : values()) {
                if (t.name().equalsIgnoreCase(s)) {
                    return true;
                }
            }
            return false;
        }

        // Restituisce l'enum corrispondente alla stringa, o null se non esiste
        public static TipoSpazio fromString(String s) {
            if (s == null) return null;
            for (TipoSpazio t : values()) {
                if (t.name().equalsIgnoreCase(s)) {
                    return t;
                }
            }
            return null;
        }
    }

    public enum TipoSpecificaRazza {
        OMPORTAMENTO("Comportamento"),
        COLORAZIONE("Colorazione"),
        TIPO_PELO("Tipo Pelo"),
        TIPO_COLORAZIONE("Tipo Colorazione");

        private final String label;

        TipoSpecificaRazza(String label) {
            this.label = label;
        }

        public String getLabel() {
            return label;
        }

        // Controlla se la stringa corrisponde a uno dei valori dell'enum (sia name che label)
        public static boolean matches(String s) {
            if (s == null) return false;
            for (TipoSpecificaRazza t : values()) {
                if (t.name().equalsIgnoreCase(s) || t.label.equalsIgnoreCase(s)) {
                    return true;
                }
            }
            return false;
        }

        // Restituisce l'enum corrispondente alla stringa (sia name che label)
        public static TipoSpecificaRazza fromString(String s) {
            if (s == null) return null;
            for (TipoSpecificaRazza t : values()) {
                if (t.name().equalsIgnoreCase(s) || t.label.equalsIgnoreCase(s)) {
                    return t;
                }
            }
            return null;
        }
    }

    public enum NomeStatistica {
        ETA_MEDIA_ADOZIONE("Età media di adozione"),
        PERCENTUALE_TIPOLOGIE_ANIMALI("Percentuale tipologie di animali su tutti i centri"),
        PERCENTUALE_PURA_METICCIO_ADOTTATI("Percentuale animali di razza pura e meticci adottati"),
        SPECIE_PIU_ADOTTATA("Specie di animale più adottata"),
        CLASSIFICA_SPECIE_PIU_ADOTTATE("Classifica specie di animali più adottate"),
        PERCENTUALE_ANIMALI_MALATI_SANI("Percentuale animali malati e sani"),
        PERCENTUALE_CURABILI_NON_CURABILI("Percentuale animali curabili e non curabili"),
        PERCENTUALE_DISABILI_PER_CENTRO("Percentuale animali disabili per centro"),
        PERCENTUALE_CRONICI_PER_CENTRO("Percentuale animali cronici per centro"),
        PERCENTUALE_PERSONALE_FISSO_VOLONTARIO("Percentuale personale fisso e volontario"),
        PERCENTUALE_SPECIE_PER_CENTRO("Percentuale di ogni specie di animale per centro"),
        PERCENTUALE_SPECIE_ADOTTATA_PER_CENTRO("Percentuale di ogni specie di animale adottata per centro"),
        PERCENTUALE_EX_DIPENDENTI_FISSI("Percentuale ex dipendenti fissi"),
        PERCENTUALE_EX_DIPENDENTI_VOLONTARI("Percentuale ex dipendenti volontari"),
        PERCENTUALE_DIPENDENTI_RICHIEDENTI("Percentuale dipendenti che sono anche richiedenti adozione"),
        PERCENTUALE_RITROVAMENTO("Percentuale tipologie di ritrovamento degli animali"),
        PERCENTUALE_STRANIERI_AUTOCTONI("Percentuale animali stranieri e autoctoni"),
        PERCENTUALE_STRANIERI_AUTOCTONI_PER_CENTRO("Percentuale animali stranieri e autoctoni per centro"),
        PERCENTUALE_PURA_METICCIO("Percentuale animali di razza pura e meticci"),
        PERCENTUALE_PURA_METICCIO_PER_CENTRO("Percentuale animali di razza pura e meticci per centro"),
        CENTRO_PIU_ADOZIONI("Centro con più adozioni"),
        CLASSIFICA_CENTRI_PIU_ADOZIONI("Classifica centri con più adozioni"),
        SPECIE_PIU_ADOTTATA_PER_CENTRO("Specie più adottata per centro"),
        CITTA_PIU_ADOZIONI("Città con più adozioni"),
        PROVINCIA_PIU_ADOZIONI("Provincia con più adozioni"),
        REGIONE_PIU_ADOZIONI("Regione con più adozioni"),
        CITTA_PIU_ADOZIONI_EFFETTUATE("Città che ha effettuato più adozioni"),
        PROVINCIA_PIU_ADOZIONI_EFFETTUATE("Provincia che ha effettuato più adozioni"),
        REGIONE_PIU_ADOZIONI_EFFETTUATE("Regione che ha effettuato più adozioni"),
        CLASSIFICA_CITTA_PIU_ADOZIONI("Classifica città con più adozioni"),
        CLASSIFICA_PROVINCE_PIU_ADOZIONI("Classifica province con più adozioni"),
        CLASSIFICA_REGIONI_PIU_ADOZIONI("Classifica regioni con più adozioni"),
        CLASSIFICA_CITTA_PIU_ADOZIONI_EFFETTUATE("Classifica città che hanno effettuato più adozioni"),
        CLASSIFICA_PROVINCE_PIU_ADOZIONI_EFFETTUATE("Classifica province che hanno effettuato più adozioni"),
        CLASSIFICA_REGIONI_PIU_ADOZIONI_EFFETTUATE("Classifica regioni che hanno effettuato più adozioni");

        private final String descrizione;

        private static final Map<String, NomeStatistica> LOOKUP_DESCRIZIONE = new HashMap<>();

        static {
            for (NomeStatistica n : NomeStatistica.values()) {
                LOOKUP_DESCRIZIONE.put(n.descrizione.toLowerCase(), n);
            }
        }

        NomeStatistica(String descrizione) {
            this.descrizione = descrizione;
        }

        public String getDescrizione() {
            return descrizione;
        }

        /** Restituisce l'enum a partire dalla descrizione leggibile */
        public static NomeStatistica fromDescrizione(String descrizione) {
            if (descrizione == null) return null;
            return LOOKUP_DESCRIZIONE.get(descrizione.toLowerCase());
        }

        /** Verifica se la descrizione corrisponde a una statistica valida */
        public static boolean isValidDescrizione(String descrizione) {
            return fromDescrizione(descrizione) != null;
        }

        /** Verifica se la chiave (nome enum) è valida */
        public static boolean isValidKey(String key) {
            if (key == null) return false;
            try {
                NomeStatistica.valueOf(key);
                return true;
            } catch (IllegalArgumentException e) {
                return false;
            }
        }
    }

    public enum TipoLavori{
        PULIZIA("Pulizia"),
        MANUTENZIONE("Manutenzione"),
        CURA_ANIMALI("Cura Animali"),
        ACCOGLIENZA("Accoglienza");

        private final String descrizione;

        TipoLavori(String descrizione) {
            this.descrizione = descrizione;
        }

        public String getDescrizione() {
            return descrizione;
        }

        public static boolean isValid(String tipo) {
            if (tipo == null) return false;
            for (TipoLavori t : values()) {
                if (t.name().equalsIgnoreCase(tipo)) {
                    return true;
                }
            }
            return false;
        }
    }
}

