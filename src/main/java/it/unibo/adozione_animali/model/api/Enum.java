package it.unibo.adozione_animali.model.api;

public class Enum {

    public enum StatoAnimale {
        DISABILE,
        CRONICO
    }

    public enum TipoCaratteristica {
        FISICO,
        COMPORTAMENTO
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

    
}

