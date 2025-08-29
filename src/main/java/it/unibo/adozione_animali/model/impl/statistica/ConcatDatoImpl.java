package it.unibo.adozione_animali.model.impl.statistica;

import java.util.List;

import it.unibo.adozione_animali.model.api.statistica.ConcatDato;
import it.unibo.adozione_animali.util.Enum.NomeStatistica;
import it.unibo.adozione_animali.util.Enum.Specie;

public class ConcatDatoImpl implements ConcatDato {

    @Override
    public String concatValore(List<String> valori, String statistica) {
        NomeStatistica statisticaEnum = NomeStatistica.fromDescrizione(statistica);
        if (valori == null || valori.isEmpty()) {
            return null;
        }
        if (statisticaEnum == null) {
            throw new IllegalArgumentException("Statistica non riconosciuta: " + statistica);
        }

        switch (statisticaEnum) {
            case CENTRO_PIU_ADOZIONI:
            case CLASSIFICA_CENTRI_PIU_ADOZIONI:
            case CITTA_PIU_ADOZIONI:
            case PROVINCIA_PIU_ADOZIONI:
            case REGIONE_PIU_ADOZIONI:
            case CITTA_PIU_ADOZIONI_EFFETTUATE:
            case PROVINCIA_PIU_ADOZIONI_EFFETTUATE:
            case REGIONE_PIU_ADOZIONI_EFFETTUATE:
            case CLASSIFICA_CITTA_PIU_ADOZIONI:
            case CLASSIFICA_PROVINCE_PIU_ADOZIONI:
            case CLASSIFICA_REGIONI_PIU_ADOZIONI:
            case CLASSIFICA_CITTA_PIU_ADOZIONI_EFFETTUATE:
            case CLASSIFICA_PROVINCE_PIU_ADOZIONI_EFFETTUATE:
            case CLASSIFICA_REGIONI_PIU_ADOZIONI_EFFETTUATE:
            case SPECIE_PIU_ADOTTATA:
            case CLASSIFICA_SPECIE_PIU_ADOTTATE:
            case ETA_MEDIA_ADOZIONE:
                return valori.get(0);

            case PERCENTUALE_TIPOLOGIE_ANIMALI:
            case PERCENTUALE_PURA_METICCIO_ADOTTATI:
                String num = valori.get(0);
                String valorePerc = String.format("%.2f%%", Double.parseDouble(valori.get(1)));
                return "Numero Animali Tot: " + num + "\tPercentuale: " + valorePerc;
            case SPECIE_PIU_ADOTTATA_PER_CENTRO:
                Specie sp = Specie.fromKey(valori.get(0));
                String dy = sp.getDisplayName();
                return dy + "\tNumero Animali Tot: " + valori.get(1);

            case PERCENTUALE_SPECIE_ADOTTATA_PER_CENTRO:
            case PERCENTUALE_SPECIE_PER_CENTRO:
                Specie specie = Specie.fromKey(valori.get(0));
                String displayName = specie.getDisplayName(); // Cane
                String val = valori.get(1);
                String perc = valori.get(2);
                return displayName + "\tNumero Animali Tot: " + val + "\tPercentuale: " + perc + "%";

            case PERCENTUALE_STRANIERI_AUTOCTONI_PER_CENTRO:
                if (valori.size() >= 2) {
                    return "Stranieri percentuale: " + valori.get(0) + "%, " +
                        "Autoctoni percentuale: " + valori.get(1) + "%";
                } else {
                    return "Dati incompleti";
                }


            case PERCENTUALE_PURA_METICCIO_PER_CENTRO:
                if (valori.size() >= 2) {
                    return "Puri percentuale: " + valori.get(0) + "%, " +
                        "Meticci percentuale: " + valori.get(1) + "%";
                } else {
                    return "Dati incompleti";
                }

            case PERCENTUALE_DISABILI_PER_CENTRO:
                return "Disabili percentuale: " + valori.get(0) + "%";

            case PERCENTUALE_CRONICI_PER_CENTRO:
                return "Cronici percentuale: " + valori.get(0) + "%";
            case PERCENTUALE_ANIMALI_MALATI_SANI:
            case PERCENTUALE_CURABILI_NON_CURABILI:
            case PERCENTUALE_PERSONALE_FISSO_VOLONTARIO:
            case PERCENTUALE_EX_DIPENDENTI_FISSI:
            case PERCENTUALE_EX_DIPENDENTI_VOLONTARI:
            case PERCENTUALE_DIPENDENTI_RICHIEDENTI:
            case PERCENTUALE_RITROVAMENTO:
            case PERCENTUALE_STRANIERI_AUTOCTONI:
            case PERCENTUALE_PURA_METICCIO:
                return valori.get(0) + "%";
            default:
                throw new IllegalArgumentException("Caso non implementato per statistica: " + statisticaEnum);
        }
    }

    @Override
    public String concatNome(List<String> nome, String statistica) {
        NomeStatistica statisticaEnum = NomeStatistica.fromDescrizione(statistica);

        if (statisticaEnum == null) {
            throw new IllegalArgumentException("Statistica non riconosciuta: " + statistica);
        }

        if(nome.size() > 1 ) {
            if (nome.size() < 2) {
                throw new IllegalArgumentException("Valori insufficienti per la statistica " + statisticaEnum);
            }
            switch (statisticaEnum) {
                case CITTA_PIU_ADOZIONI:
                case CITTA_PIU_ADOZIONI_EFFETTUATE:
                case CLASSIFICA_CITTA_PIU_ADOZIONI:
                case CLASSIFICA_CITTA_PIU_ADOZIONI_EFFETTUATE:
                    if (nome.size() < 4) {
                        throw new IllegalArgumentException("Valori insufficienti per la statistica " + statisticaEnum);
                    }
                    return String.join("\t", nome.get(0), nome.get(1), nome.get(2), nome.get(3), "N Adottati");

                case PROVINCIA_PIU_ADOZIONI:
                case PROVINCIA_PIU_ADOZIONI_EFFETTUATE:
                case CLASSIFICA_PROVINCE_PIU_ADOZIONI:
                case CLASSIFICA_PROVINCE_PIU_ADOZIONI_EFFETTUATE:
                case REGIONE_PIU_ADOZIONI:
                case CLASSIFICA_REGIONI_PIU_ADOZIONI:
                case REGIONE_PIU_ADOZIONI_EFFETTUATE:
                case CLASSIFICA_REGIONI_PIU_ADOZIONI_EFFETTUATE:
                    return String.join("\t", nome.get(0), nome.get(1), "N Adottati");

                default:
                    if (nome.size() < 4) {
                        throw new IllegalArgumentException("Valori insufficienti per la statistica " + statisticaEnum);
                    }
                    return String.join("\t", nome.subList(0, 4));
            }
        }else {
            switch (statisticaEnum) {
                case ETA_MEDIA_ADOZIONE:
                case PERCENTUALE_EX_DIPENDENTI_FISSI:
                case PERCENTUALE_EX_DIPENDENTI_VOLONTARI:
                case PERCENTUALE_ANIMALI_MALATI_SANI:
                case PERCENTUALE_CURABILI_NON_CURABILI:
                case PERCENTUALE_PERSONALE_FISSO_VOLONTARIO:
                case PERCENTUALE_DIPENDENTI_RICHIEDENTI:
                case PERCENTUALE_STRANIERI_AUTOCTONI:
                case PERCENTUALE_PURA_METICCIO:
                case PERCENTUALE_RITROVAMENTO:
                case PERCENTUALE_PURA_METICCIO_ADOTTATI:
                case PERCENTUALE_DISABILI_PER_CENTRO:
                case PERCENTUALE_CRONICI_PER_CENTRO:
                case PERCENTUALE_SPECIE_PER_CENTRO:
                case PERCENTUALE_STRANIERI_AUTOCTONI_PER_CENTRO:
                case PERCENTUALE_PURA_METICCIO_PER_CENTRO:
                    return nome.get(0);

                case PERCENTUALE_TIPOLOGIE_ANIMALI:
                case CLASSIFICA_SPECIE_PIU_ADOTTATE:
                case SPECIE_PIU_ADOTTATA:
                    String key = nome.get(0);
                    Specie specie = Specie.fromKey(key);

                    if (specie == null) {
                        throw new IllegalArgumentException("Key non valida per Specie: " + key);
                    }

                    return specie.getDisplayName();
                default:
                    throw new IllegalArgumentException("Caso non implementato per statistica: " + statisticaEnum);
            }
        }
    }
}
