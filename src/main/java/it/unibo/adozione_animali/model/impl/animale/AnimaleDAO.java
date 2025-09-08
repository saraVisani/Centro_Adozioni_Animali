package it.unibo.adozione_animali.model.impl.animale;

import java.math.BigDecimal;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Types;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.jooq.DSLContext;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;

import it.unibo.adozione_animali.model.api.animale.Animale;
import it.unibo.adozione_animali.util.DBConfig;
import it.unibo.adozione_animali.util.Enum.Specie;
import nu.studer.sample.Tables;

public class AnimaleDAO implements Animale {

    @Override
    public boolean insertAnimale(String provincia, String città, int numero, String animale, String specie,
            Optional<String> nome, String razza, String prov_ritr, String cit_ritr, int num_ritr, String stato_ritr,
            LocalDate data_nascita, LocalDate data_inserimento, Optional<LocalDate> data_addozione,
            Optional<String> prov_att, Optional<String> cit_att, Optional<Integer> num_att,
            Optional<String> prov_centro, Optional<String> cit_centro, Optional<Integer> num_centro,
            Optional<String> statoAttuale, boolean idonieta, float peso, float altezza) {
        try (Connection conn = DBConfig.getConnection()) {
            DSLContext ctx = DSL.using(conn, SQLDialect.MYSQL);
            int insert = ctx.insertInto(Tables.ANIMALE)
                .set(Tables.ANIMALE.COD_PROVINCIA, provincia)
                .set(Tables.ANIMALE.COD_CITTA_, città)
                .set(Tables.ANIMALE.NUMERO, numero)
                .set(Tables.ANIMALE.COD_ANIMALE, animale)
                .set(Tables.ANIMALE.COD_SPECIE, specie)
                .set(Tables.ANIMALE.NOME, nome.orElse(null))
                .set(Tables.ANIMALE.NOME_RAZZA, razza)
                .set(Tables.ANIMALE.COD_PROVINCIA_RITROVAMENTO, prov_ritr)
                .set(Tables.ANIMALE.COD_CITTA_RITROVAMENTO, cit_ritr)
                .set(Tables.ANIMALE.NUMERO_RITROVAMENTO, num_ritr)
                .set(Tables.ANIMALE.STATO_RITROVAMENTO, stato_ritr)
                .set(Tables.ANIMALE.DATA_NASCITA, data_nascita)
                .set(Tables.ANIMALE.DATA_INSERIMENTO, data_inserimento)
                .set(Tables.ANIMALE.DATA_ADOZIONE, data_addozione.orElse(null))
                .set(Tables.ANIMALE.COD_PROVINCIA_RESIDENZA_ATT, prov_att.orElse(null))
                .set(Tables.ANIMALE.COD_CITTA_RESIDENZA_ATT, cit_att.orElse(null))
                .set(Tables.ANIMALE.NUMERO_RESIDENZA_ATT, num_att.orElse(null))
                .set(Tables.ANIMALE.COD_PROVINCIA_CENTRO, prov_centro.orElse(null))
                .set(Tables.ANIMALE.COD_CITTA_CENTRO, cit_centro.orElse(null))
                .set(Tables.ANIMALE.NUMERO_CENTRO, num_centro.orElse(null))
                .set(Tables.ANIMALE.STATO_ATTUALE, statoAttuale.orElse(null))
                .set(Tables.ANIMALE.IDONEITA_ANIMALE, (byte) (idonieta ? 1 : 0))
                .set(Tables.ANIMALE.PESO, BigDecimal.valueOf(peso))
                .set(Tables.ANIMALE.ALTEZZA, BigDecimal.valueOf(altezza))
                .execute();
            return insert > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false; // Return false if there is an error
        }
    }

    @Override
    public boolean updateAnimaleNome(String provincia, String città, int numero, String animale, String newNome) {
        try (Connection conn = DBConfig.getConnection()) {
            DSLContext ctx = DSL.using(conn, SQLDialect.MYSQL);
            int righeAggiornate = ctx.update(Tables.ANIMALE)
                .set(Tables.ANIMALE.NOME, newNome)
                .where(Tables.ANIMALE.COD_PROVINCIA.eq(provincia)
                    .and(Tables.ANIMALE.COD_CITTA_.eq(città))
                    .and(Tables.ANIMALE.NUMERO.eq(numero))
                    .and(Tables.ANIMALE.COD_ANIMALE.eq(animale)))
                .execute();
            return righeAggiornate > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false; // Return false if there is an error
        }
    }

    @Override
    public boolean updateAnimaleAltezza(String provincia, String città, int numero, String animale, float newAltezza) {
        try (Connection conn = DBConfig.getConnection()) {
            DSLContext ctx = DSL.using(conn, SQLDialect.MYSQL);
            int righeAggiornate = ctx.update(Tables.ANIMALE)
                .set(Tables.ANIMALE.ALTEZZA, BigDecimal.valueOf(newAltezza))
                .where(Tables.ANIMALE.COD_PROVINCIA.eq(provincia)
                    .and(Tables.ANIMALE.COD_CITTA_.eq(città))
                    .and(Tables.ANIMALE.NUMERO.eq(numero))
                    .and(Tables.ANIMALE.COD_ANIMALE.eq(animale)))
                .execute();
            return righeAggiornate > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false; // Return false if there is an error
        }
    }

    @Override
    public boolean updateAnimalePeso(String provincia, String città, int numero, String animale, float newPeso) {
        try (Connection conn = DBConfig.getConnection()) {
            DSLContext ctx = DSL.using(conn, SQLDialect.MYSQL);
            int righeAggiornate = ctx.update(Tables.ANIMALE)
                .set(Tables.ANIMALE.PESO, BigDecimal.valueOf(newPeso))
                .where(Tables.ANIMALE.COD_PROVINCIA.eq(provincia)
                    .and(Tables.ANIMALE.COD_CITTA_.eq(città))
                    .and(Tables.ANIMALE.NUMERO.eq(numero))
                    .and(Tables.ANIMALE.COD_ANIMALE.eq(animale)))
                .execute();
            return righeAggiornate > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false; // Return false if there is an error
        }
    }

    @Override
    public boolean deleteAnimale(String provincia, String città, int numero, String animale) {
        try (Connection conn = DBConfig.getConnection()) {
            DSLContext ctx = DSL.using(conn, SQLDialect.MYSQL);
            int righeCancellate = ctx.deleteFrom(Tables.ANIMALE)
                .where(Tables.ANIMALE.COD_PROVINCIA.eq(provincia)
                    .and(Tables.ANIMALE.COD_CITTA_.eq(città))
                    .and(Tables.ANIMALE.NUMERO.eq(numero))
                    .and(Tables.ANIMALE.COD_ANIMALE.eq(animale)))
                .execute();
            return righeCancellate > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false; // Return false if there is an error
        }
    }

    public List<String> getCodiciByNumero(String provincia, String citta, Integer numero) {
        try (Connection conn = DBConfig.getConnection()) {

            if (provincia == null || citta == null || numero == null) {
                return Collections.emptyList();
            }

            DSLContext ctx = DSL.using(conn, SQLDialect.MYSQL);
            return ctx.select(Tables.ANIMALE.COD_ANIMALE)
                .from(Tables.ANIMALE)
                .where(Tables.ANIMALE.COD_PROVINCIA.eq(provincia)
                    .and(Tables.ANIMALE.COD_CITTA_.eq(citta))
                    .and(Tables.ANIMALE.NUMERO.eq(numero)))
                .fetchInto(String.class);

        } catch (Exception e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }

    public List<String> getCodiciLiberi(String provinciaCodice, String cittaCodice, Integer numero, String specie) {
        try (Connection conn = DBConfig.getConnection()) {
            DSLContext ctx = DSL.using(conn, SQLDialect.MYSQL);

            // Lista delle specie da usare
            List<String> specieDaUsare;

            if (specie != null && !specie.equals("--select--") && !specie.isBlank()) {
                specieDaUsare = List.of(specie);
            } else {
                // Recupera tutte le specie collegate al centro
                specieDaUsare = ctx.select(Tables.TIPOLOGIA.COD_SPECIE)
                                .from(Tables.TIPOLOGIA)
                                .where(Tables.TIPOLOGIA.COD_PROVINCIA.eq(provinciaCodice)
                                        .and(Tables.TIPOLOGIA.COD_CITTA_.eq(cittaCodice))
                                        .and(Tables.TIPOLOGIA.NUMERO.eq(numero)))
                                .fetch(Tables.TIPOLOGIA.COD_SPECIE)
                                .stream()
                                .map(key -> Specie.fromKey(key).getDisplayName())
                                .collect(Collectors.toList());
            }

            // Stream su ogni specie per generare i codici
            return specieDaUsare.stream()
                    .flatMap(s -> {
                        String codSpecie = Specie.keyFromName(s);
                        List<String> codiciSpecie = new ArrayList<>();
                        try (CallableStatement cs = conn.prepareCall("{ call genera_cod_animale_lock(?, ?, ?, ?, ?) }")){
                            cs.setString(1, provinciaCodice);
                            cs.setString(2, cittaCodice);
                            cs.setInt(3, numero);
                            cs.setString(4, codSpecie);
                            cs.registerOutParameter(5, Types.VARCHAR);

                            cs.execute();

                            String codiceAnimale = cs.getString(5);
                            codiciSpecie.add(codiceAnimale);

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        return codiciSpecie.stream();
                    })
                    .collect(Collectors.toList());

        } catch (Exception e) {
            e.printStackTrace();
            return List.of();
        }
    }

    public String getAnimale(String provinciaRit, String cittaRit, Integer numero, String animale) {
        try (Connection conn = DBConfig.getConnection()) {
            DSLContext ctx = DSL.using(conn, SQLDialect.MYSQL);

            // Recupera la riga come Record
            var record = ctx.selectFrom(Tables.ANIMALE)
                .where(Tables.ANIMALE.COD_PROVINCIA.eq(provinciaRit)
                    .and(Tables.ANIMALE.COD_CITTA_.eq(cittaRit))
                    .and(Tables.ANIMALE.NUMERO.eq(numero))
                    .and(Tables.ANIMALE.COD_ANIMALE.eq(animale)))
                .fetchOne();

            if (record == null) {
                return "Animale non trovato";
            }

            // Formatter per le date
            java.time.format.DateTimeFormatter dateFormatter =
                java.time.format.DateTimeFormatter.ofPattern("dd/MM/yyyy");

            List<String> result = new ArrayList<>();

            // Itera su tutte le colonne del record
            record.intoMap().forEach((colName, value) -> {
                // Trasforma il nome della colonna
                String nomeColonna = formatColumnName(colName);

                // Trasforma il valore
                String valore;
                if (value == null) {
                    valore = "N/D";
                } else if (value instanceof java.sql.Date sqlDate) {
                    valore = sqlDate.toLocalDate().format(dateFormatter);
                } else if (value instanceof LocalDate localDate) {
                    valore = localDate.format(dateFormatter);
                } else {
                    valore = value.toString();
                }

                result.add(nomeColonna + ": " + valore);
            });

            // Unisci tutte le righe con newline
            return String.join("\n", result);

        } catch (Exception e) {
            e.printStackTrace();
            return "Errore durante il recupero dell'animale";
        }
    }

    private String formatColumnName(String colName) {
        String formatted = colName.replace("_", " ").toLowerCase();
        return formatted.substring(0, 1).toUpperCase() + formatted.substring(1);
    }
}
