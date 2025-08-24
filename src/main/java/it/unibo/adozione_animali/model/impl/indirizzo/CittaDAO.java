package it.unibo.adozione_animali.model.impl.indirizzo;

import java.sql.Connection;
import java.util.List;

import org.jooq.DSLContext;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;

import it.unibo.adozione_animali.model.api.indirizzo.Citta;
import it.unibo.adozione_animali.util.DBConfig;
import it.unibo.adozione_animali.util.ItemSelezionabile;
import nu.studer.sample.Tables;

public class CittaDAO implements Citta {

    @Override
    public boolean insertCitta(String provincia, String citta, String nome) {
        try (Connection conn = DBConfig.getConnection()) {
            DSLContext ctx = DSL.using(conn, SQLDialect.MYSQL);

            int righeInserite = ctx.insertInto(Tables.CITTA_)
                .set(Tables.CITTA_.COD_PROVINCIA, provincia)
                .set(Tables.CITTA_.COD_CITTA_, citta)
                .set(Tables.CITTA_.NOME, nome)
                .execute();

            return righeInserite > 0;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean updateCittaNome(String provincia, String citta, String newNome) {
        try (Connection conn = DBConfig.getConnection()) {
            DSLContext ctx = DSL.using(conn, SQLDialect.MYSQL);

            int righeInserite = ctx.update(Tables.CITTA_)
                .set(Tables.CITTA_.NOME, newNome)
                .where(Tables.CITTA_.COD_PROVINCIA.eq(provincia)
                    .and(Tables.CITTA_.COD_CITTA_.eq(citta)))
                .execute();

            return righeInserite > 0;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean updateCittaProvincia(String citta, String newProvincia, String newCitta) {
        try (Connection conn = DBConfig.getConnection()) {
            DSLContext ctx = DSL.using(conn, SQLDialect.MYSQL);

            int righeAggiornate = ctx.update(Tables.CITTA_)
                .set(Tables.CITTA_.COD_PROVINCIA, newProvincia)
                .set(Tables.CITTA_.COD_CITTA_, newCitta)
                .where(Tables.CITTA_.COD_CITTA_.eq(citta))
                .execute();

            return righeAggiornate > 0;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean deleteCitta(String provincia, String citta) {
        try (Connection conn = DBConfig.getConnection()) {
            DSLContext ctx = DSL.using(conn, SQLDialect.MYSQL);

            int righeCancellate = ctx.deleteFrom(Tables.CITTA_)
                .where(Tables.CITTA_.COD_PROVINCIA.eq(provincia)
                    .and(Tables.CITTA_.COD_CITTA_.eq(citta)))
                .execute();

            return righeCancellate > 0;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<ItemSelezionabile> getCittaByProvincia(String provincia) {
        try (Connection conn = DBConfig.getConnection()) {
            DSLContext ctx = DSL.using(conn, SQLDialect.MYSQL);
            return ctx.select(Tables.CITTA_.COD_CITTA_, Tables.CITTA_.NOME)
                        .from(Tables.CITTA_)
                        .where(Tables.CITTA_.COD_PROVINCIA.eq(provincia))
                        .orderBy(Tables.CITTA_.NOME.asc())
                        .fetch(record -> new ItemSelezionabile(
                            record.get(Tables.CITTA_.COD_CITTA_),
                            record.get(Tables.CITTA_.NOME)
                        ));
        } catch (Exception e) {
            e.printStackTrace();
            return List.of(); // Return empty list if there is an error
        }
    }

}
