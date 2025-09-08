package it.unibo.adozione_animali.model.impl.indirizzo;

import java.sql.Connection;
import java.util.Arrays;
import java.util.List;

import org.jooq.DSLContext;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;

import it.unibo.adozione_animali.model.api.indirizzo.Provincia;
import it.unibo.adozione_animali.util.DBConfig;
import it.unibo.adozione_animali.util.ItemSelezionabile;
import nu.studer.sample.Tables;

public class ProvinciaDAO implements Provincia{

    @Override
    public boolean insertProvincia(String provincia, String nome, String regione) {
        try (Connection conn = DBConfig.getConnection()) {
            DSLContext ctx = DSL.using(conn, SQLDialect.MYSQL);

            int righeInserite = ctx.insertInto(Tables.PROVINCIA)
                .set(Tables.PROVINCIA.COD_PROVINCIA, provincia)
                .set(Tables.PROVINCIA.NOME, nome)
                .set(Tables.PROVINCIA.COD_REGIONE, regione)
                .execute();

            return righeInserite > 0;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean updateProvinciaNome(String provincia, String newNome) {
        try (Connection conn = DBConfig.getConnection()) {
            DSLContext ctx = DSL.using(conn, SQLDialect.MYSQL);

            int righeAggiornate = ctx.update(Tables.PROVINCIA)
                .set(Tables.PROVINCIA.NOME, newNome)
                .where(Tables.PROVINCIA.COD_PROVINCIA.eq(provincia))
                .execute();

            return righeAggiornate > 0;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean updateProvinciaRegione(String provincia, String newRegione) {
        try (Connection conn = DBConfig.getConnection()) {
            DSLContext ctx = DSL.using(conn, SQLDialect.MYSQL);

            int righeAggiornate = ctx.update(Tables.PROVINCIA)
                .set(Tables.PROVINCIA.COD_REGIONE, newRegione)
                .where(Tables.PROVINCIA.COD_PROVINCIA.eq(provincia))
                .execute();

            return righeAggiornate > 0;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean updateProvincia(String provincia, String newProvincia) {
        try (Connection conn = DBConfig.getConnection()) {
            DSLContext ctx = DSL.using(conn, SQLDialect.MYSQL);

            return ctx.transactionResult(configuration -> {
                DSLContext tCtx = DSL.using(configuration);

                // Leggi dati provincia esistente
                var provinciaRecord = tCtx.selectFrom(Tables.PROVINCIA)
                    .where(Tables.PROVINCIA.COD_PROVINCIA.eq(provincia))
                    .fetchOne();

                if (provinciaRecord == null) return false;

                String name = provinciaRecord.getNome();
                String region = provinciaRecord.getCodRegione();

                // Elimina provincia esistente usando la stessa connessione
                int deleted = tCtx.deleteFrom(Tables.PROVINCIA)
                    .where(Tables.PROVINCIA.COD_PROVINCIA.eq(provincia))
                    .execute();

                if (deleted == 0) return false;

                // Inserisci nuova provincia
                int inserted = tCtx.insertInto(Tables.PROVINCIA)
                    .set(Tables.PROVINCIA.COD_PROVINCIA, newProvincia)
                    .set(Tables.PROVINCIA.NOME, name)
                    .set(Tables.PROVINCIA.COD_REGIONE, region)
                    .execute();

                return inserted > 0;
            });

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean deleteProvincia(String provincia) {
        try (Connection conn = DBConfig.getConnection()) {
            DSLContext ctx = DSL.using(conn, SQLDialect.MYSQL);

            int righeCancellate = ctx.deleteFrom(Tables.PROVINCIA)
                .where(Tables.PROVINCIA.COD_PROVINCIA.eq(provincia))
                .execute();

            return righeCancellate > 0;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<ItemSelezionabile> getProvince() {
        try (Connection conn = DBConfig.getConnection()) {
            DSLContext ctx = DSL.using(conn, SQLDialect.MYSQL);
            return ctx.select(Tables.PROVINCIA.COD_PROVINCIA, Tables.PROVINCIA.NOME)
                            .from(Tables.PROVINCIA)
                            .orderBy(Tables.PROVINCIA.NOME.asc())
                            .fetch(record -> new ItemSelezionabile(
                                record.get(Tables.PROVINCIA.COD_PROVINCIA),
                                record.get(Tables.PROVINCIA.NOME)
                            ));
        } catch (Exception e) {
            e.printStackTrace();
            return List.of(); // Return empty list if there is an error
        }
    }

    public String getProvinciaSpecifica(String provincia) {
        try (Connection conn = DBConfig.getConnection()) {
            DSLContext ctx = DSL.using(conn, SQLDialect.MYSQL);

            var record = ctx.selectFrom(Tables.PROVINCIA)
                    .where(Tables.PROVINCIA.COD_PROVINCIA.eq(provincia))
                    .fetchOne();

            if (record == null) return "N/D";

            return Arrays.stream(record.fields())
                    .map(f -> {
                        String header = formatHeader(f.getName());
                        Object value = record.get(f);
                        String val = (value == null) ? "N/D" : value.toString();
                        return header + ": " + val;
                    })
                    .reduce((a, b) -> a + " | " + b)
                    .orElse("N/D");

        } catch (Exception e) {
            e.printStackTrace();
            return "N/D";
        }
    }

    private String formatHeader(String name) {
        String[] parts = name.split("_");
        for (int i = 0; i < parts.length; i++) {
            if (parts[i].length() > 0) {
                parts[i] = parts[i].substring(0, 1).toUpperCase() + parts[i].substring(1).toLowerCase();
            }
        }
        return String.join(" ", parts);
    }

}
