package it.unibo.adozione_animali.controller.impl;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import org.jooq.DSLContext;
import org.jooq.Result;
import org.jooq.SQLDialect;
import org.jooq.Record;
import org.jooq.impl.DSL;

import it.unibo.adozione_animali.util.DBConfig;
import it.unibo.adozione_animali.util.Enum.NomeStatistica;
import nu.studer.sample.Tables;
import it.unibo.adozione_animali.util.Pair;

public class StatisticControllerHelper {

    public Pair<List<List<String>>, List<List<String>>> getRisultati(NomeStatistica statistica) {
        List<List<String>> nomi = new ArrayList<>();
        List<List<String>> valori = new ArrayList<>();

        try (Connection conn = DBConfig.getConnection()) {
            DSLContext ctx = DSL.using(conn, SQLDialect.MYSQL);

            Result<Record> rows;

            switch (statistica) {
                case CENTRO_PIU_ADOZIONI: // solo prima riga
                    rows = ctx.select()
                            .from(Tables.STATISTICA_CENTRO_ADOZIONI)
                            .limit(1)
                            .fetch();
                    break;
                case CLASSIFICA_CENTRI_PIU_ADOZIONI: // tutte
                    rows = ctx.select()
                            .from(Tables.STATISTICA_CENTRO_ADOZIONI)
                            .fetch();
                    break;
                case SPECIE_PIU_ADOTTATA_PER_CENTRO:
                    rows = ctx.select()
                            .from(Tables.STATISTICA_SPECIE_PIU_ADOTTATA_CENTRO)
                            .fetch();
                    break;
                case PERCENTUALE_SPECIE_ADOTTATA_PER_CENTRO:
                    rows = ctx.select()
                            .from(Tables.STATISTICA_SPECIE_ADOTTATA_CENTRO)
                            .fetch();
                    break;
                case CITTA_PIU_ADOZIONI:
                    rows = ctx.select()
                            .from(Tables.STATISTICA_CITTA_ADOZIONI)
                            .fetch();
                    break;
                case CLASSIFICA_CITTA_PIU_ADOZIONI:
                    rows = ctx.select()
                            .from(Tables.STATISTICA_CITTA_ADOZIONI)
                            .limit(1)
                            .fetch();
                    break;
                case PROVINCIA_PIU_ADOZIONI:
                    rows = ctx.select()
                            .from(Tables.STATISTICA_PROVINCIE_ADOZIONI)
                            .fetch();
                    break;
                case CLASSIFICA_PROVINCE_PIU_ADOZIONI:
                    rows = ctx.select()
                            .from(Tables.STATISTICA_PROVINCIE_ADOZIONI)
                            .limit(1)
                            .fetch();
                    break;
                case REGIONE_PIU_ADOZIONI:
                    rows = ctx.select()
                            .from(Tables.STATISTICA_REGIONI_ADOZIONI)
                            .fetch();
                    break;
                case CLASSIFICA_REGIONI_PIU_ADOZIONI:
                    rows = ctx.select()
                            .from(Tables.STATISTICA_REGIONI_ADOZIONI)
                            .limit(1)
                            .fetch();
                    break;
                case CITTA_PIU_ADOZIONI_EFFETTUATE:
                    rows = ctx.select()
                            .from(Tables.STATISTICA_CITTA_EFFETTIANO_ADOZIONI)
                            .fetch();
                    break;
                case CLASSIFICA_CITTA_PIU_ADOZIONI_EFFETTUATE:
                    rows = ctx.select()
                            .from(Tables.STATISTICA_CITTA_EFFETTIANO_ADOZIONI)
                            .limit(1)
                            .fetch();
                    break;
                case PROVINCIA_PIU_ADOZIONI_EFFETTUATE:
                    rows = ctx.select()
                            .from(Tables.STATISTICA_PROVINCIE_EFFETTIANO_ADOZIONI)
                            .fetch();
                    break;
                case CLASSIFICA_PROVINCE_PIU_ADOZIONI_EFFETTUATE:
                    rows = ctx.select()
                            .from(Tables.STATISTICA_PROVINCIE_EFFETTIANO_ADOZIONI)
                            .limit(1)
                            .fetch();
                    break;
                case REGIONE_PIU_ADOZIONI_EFFETTUATE:
                    rows = ctx.select()
                            .from(Tables.STATISTICA_REGIONI_EFFETTIANO_ADOZIONI)
                            .fetch();
                    break;
                case CLASSIFICA_REGIONI_PIU_ADOZIONI_EFFETTUATE:
                    rows = ctx.select()
                            .from(Tables.STATISTICA_REGIONI_EFFETTIANO_ADOZIONI)
                            .limit(1)
                            .fetch();
                    break;
                case SPECIE_PIU_ADOTTATA:
                    rows = ctx.select()
                            .from(Tables.STATISTICA_SPECIE_ADOTTATA)
                            .limit(1)
                            .fetch();
                    break;
                case CLASSIFICA_SPECIE_PIU_ADOTTATE:
                    rows = ctx.select()
                            .from(Tables.STATISTICA_SPECIE_ADOTTATA)
                            .fetch();
                    break;
                case ETA_MEDIA_ADOZIONE:
                    rows = ctx.select()
                            .from(Tables.ETA_MEDIA_ADOZIONE)
                            .fetch();
                    break;
                case PERCENTUALE_TIPOLOGIE_ANIMALI:
                    rows = ctx.select()
                            .from(Tables.STATISTICA_TIPOLOGIA_ANIMALE_CENTRI)
                            .fetch();
                    break;
                case PERCENTUALE_PURA_METICCIO_ADOTTATI:
                    rows = ctx.select()
                            .from(Tables.STATISTICA_PURI_METICCI_ADOTTATI)
                            .fetch();
                    break;
                case PERCENTUALE_STRANIERI_AUTOCTONI_PER_CENTRO:
                    rows = ctx.select()
                            .from(Tables.STATISTICA_STRANIERI_AUTOCTONI_CENTRO)
                            .fetch();
                    break;
                case PERCENTUALE_PURA_METICCIO_PER_CENTRO:
                    rows = ctx.select()
                            .from(Tables.STATISTICA_PURI_METICCI_CENTRO)
                            .fetch();
                    break;
                case PERCENTUALE_DISABILI_PER_CENTRO:
                    rows = ctx.select()
                            .from(Tables.STATISTICA_DISABILI_CENTRI)
                            .fetch();
                    break;
                case PERCENTUALE_CRONICI_PER_CENTRO:
                    rows = ctx.select()
                            .from(Tables.STATISTICA_CRONICI_CENTRI)
                            .fetch();
                    break;
                case PERCENTUALE_ANIMALI_MALATI_SANI:
                    rows = ctx.select()
                            .from(Tables.STATISTICA_MALATI_SANI)
                            .fetch();
                    break;
                case PERCENTUALE_CURABILI_NON_CURABILI:
                    rows = ctx.select()
                            .from(Tables.STATISTICA_CURABILI_NON_CURABILI)
                            .fetch();
                    break;
                case PERCENTUALE_PERSONALE_FISSO_VOLONTARIO:
                    rows = ctx.select()
                            .from(Tables.STATISTICA_FISSO_VOLONTARIO)
                            .fetch();
                    break;
                case PERCENTUALE_SPECIE_PER_CENTRO:
                    rows = ctx.select()
                            .from(Tables.STATISTICA_SPECIE_CENTRO)
                            .fetch();
                    break;
                case PERCENTUALE_EX_DIPENDENTI_FISSI:
                    rows = ctx.select()
                            .from(Tables.STATISTICA_EX_DIPENDENTI_FISSI)
                            .fetch();
                    break;
                case PERCENTUALE_EX_DIPENDENTI_VOLONTARI:
                    rows = ctx.select()
                            .from(Tables.STATISTICA_EX_DIPENDENTI_VOLONTARI)
                            .fetch();
                    break;
                case PERCENTUALE_DIPENDENTI_RICHIEDENTI:
                    rows = ctx.select()
                            .from(Tables.STATISTICA_DIPENDENTI_ANCHE_RICHIEDENTI)
                            .fetch();
                    break;
                case PERCENTUALE_RITROVAMENTO:
                    rows = ctx.select()
                            .from(Tables.STATISTICA_TIPO_RITROVAMENTO)
                            .fetch();
                    break;
                case PERCENTUALE_STRANIERI_AUTOCTONI:
                    rows = ctx.select()
                            .from(Tables.STATISTICA_STRANIERI_AUTOCTONI)
                            .fetch();
                    break;
                case PERCENTUALE_PURA_METICCIO:
                    rows = ctx.select()
                            .from(Tables.STATISTICA_PURI_METICCI)
                            .fetch();
                    break;
                default:
                    throw new IllegalArgumentException("Caso non implementato per statistica: " + statistica);
            }

            if (usaMetodo(statistica)) {
                return splitColumnsIntoRows(rows);
            }

            for (Record record : rows) {
                List<String> rNomi = new ArrayList<>();
                List<String> rValori = new ArrayList<>();

                if (usaNomiColonne(statistica)) {
                    for (int i = 0; i < record.size(); i++) {
                        rNomi.add(formatColumnName(record.field(i).getName())); // nomi dalle colonne
                        rValori.add(String.valueOf(record.get(i)));             // valori dalle celle
                    }
                } else {
                    int idxValore = indiceValore(statistica);
                    for (int i = 0; i < record.size(); i++) {
                        if (i > idxValore) {
                            rValori.add(String.valueOf(record.get(i)));
                        } else {
                            rNomi.add(String.valueOf(record.get(i)));
                        }
                    }
                }

                nomi.add(rNomi);
                valori.add(rValori);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return new Pair<>(nomi, valori);
    }

    private boolean usaNomiColonne(NomeStatistica statistica) {
        return switch (statistica) {
            case PERCENTUALE_ANIMALI_MALATI_SANI,
                    ETA_MEDIA_ADOZIONE,
                    PERCENTUALE_CURABILI_NON_CURABILI,
                    PERCENTUALE_PERSONALE_FISSO_VOLONTARIO,
                    PERCENTUALE_EX_DIPENDENTI_FISSI,
                    PERCENTUALE_EX_DIPENDENTI_VOLONTARI,
                    PERCENTUALE_DIPENDENTI_RICHIEDENTI,
                    PERCENTUALE_PURA_METICCIO,
                    PERCENTUALE_STRANIERI_AUTOCTONI ->
                true;
            default -> false;
        };
    }

    private boolean usaMetodo(NomeStatistica statistica) {
        return switch (statistica) {
            case PERCENTUALE_ANIMALI_MALATI_SANI,
                    PERCENTUALE_CURABILI_NON_CURABILI,
                    PERCENTUALE_PURA_METICCIO,
                    PERCENTUALE_PERSONALE_FISSO_VOLONTARIO,
                    PERCENTUALE_STRANIERI_AUTOCTONI ->
                true;
            default -> false;
        };
    }

    private int indiceValore(NomeStatistica statistica) {
        return switch (statistica) {
            case CENTRO_PIU_ADOZIONI,
                    CLASSIFICA_CENTRI_PIU_ADOZIONI,
                    PERCENTUALE_SPECIE_ADOTTATA_PER_CENTRO,
                    CITTA_PIU_ADOZIONI,
                    CITTA_PIU_ADOZIONI_EFFETTUATE,
                    CLASSIFICA_CITTA_PIU_ADOZIONI_EFFETTUATE,
                    CLASSIFICA_CITTA_PIU_ADOZIONI -> 3; // i valori partono dalla 4 colonna

            case PROVINCIA_PIU_ADOZIONI,
                    PROVINCIA_PIU_ADOZIONI_EFFETTUATE,
                    CLASSIFICA_PROVINCE_PIU_ADOZIONI_EFFETTUATE,
                    REGIONE_PIU_ADOZIONI,
                    CLASSIFICA_REGIONI_PIU_ADOZIONI,
                    REGIONE_PIU_ADOZIONI_EFFETTUATE,
                    SPECIE_PIU_ADOTTATA_PER_CENTRO,
                    CLASSIFICA_PROVINCE_PIU_ADOZIONI,
                    CLASSIFICA_REGIONI_PIU_ADOZIONI_EFFETTUATE -> 1;

            case SPECIE_PIU_ADOTTATA,
                    CLASSIFICA_SPECIE_PIU_ADOTTATE,
                    PERCENTUALE_TIPOLOGIE_ANIMALI,
                    PERCENTUALE_PURA_METICCIO_ADOTTATI,
                    PERCENTUALE_PURA_METICCIO_PER_CENTRO,
                    PERCENTUALE_STRANIERI_AUTOCTONI_PER_CENTRO,
                    PERCENTUALE_SPECIE_PER_CENTRO,
                    PERCENTUALE_DISABILI_PER_CENTRO,
                    PERCENTUALE_CRONICI_PER_CENTRO,
                    PERCENTUALE_RITROVAMENTO -> 0; // i valori partono dalla 2 colonna

            // quelli che usano tutte colonne come valori
            case PERCENTUALE_ANIMALI_MALATI_SANI,
                    ETA_MEDIA_ADOZIONE,
                    PERCENTUALE_CURABILI_NON_CURABILI,
                    PERCENTUALE_PERSONALE_FISSO_VOLONTARIO,
                    PERCENTUALE_EX_DIPENDENTI_FISSI,
                    PERCENTUALE_EX_DIPENDENTI_VOLONTARI,
                    PERCENTUALE_DIPENDENTI_RICHIEDENTI,
                    PERCENTUALE_PURA_METICCIO,
                    PERCENTUALE_STRANIERI_AUTOCTONI
                    -> 0;

            default -> throw new IllegalArgumentException("Indice valore non definito per: " + statistica);
        };
    }

    private String formatColumnName(String columnName) {
        if (columnName == null || columnName.isEmpty()) return columnName;

        // Sostituisci _ con spazio e dividi in parole
        String[] parts = columnName.split("_");
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < parts.length; i++) {
            String part = parts[i].toLowerCase(); // tutto minuscolo
            sb.append(Character.toUpperCase(part.charAt(0))) // prima lettera maiuscola
            .append(part.substring(1));
            if (i < parts.length - 1) sb.append(" ");
        }

        return sb.toString();
    }

    private Pair<List<List<String>>, List<List<String>>> splitColumnsIntoRows(Result<Record> rows) {
        List<List<String>> nomi = new ArrayList<>();
        List<List<String>> valori = new ArrayList<>();

        for (Record record : rows) {
            for (int i = 0; i < record.size(); i++) {
                List<String> rNomi = new ArrayList<>();
                List<String> rValori = new ArrayList<>();

                // nome colonna
                rNomi.add(formatColumnName(record.field(i).getName()));
                // valore cella
                rValori.add(String.valueOf(record.get(i)));

                nomi.add(rNomi);
                valori.add(rValori);
            }
        }

        return new Pair<>(nomi, valori);
    }

}
