package it.unibo.adozione_animali.model.impl;

import java.sql.Connection;

import it.unibo.adozione_animali.model.api.SpecificaRazza;

import org.jooq.DSLContext;
import org.jooq.Query;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;
import nu.studer.sample.Tables;

public class SpecificaRazzaDAO implements SpecificaRazza {

    @Override
    public boolean insertSpecificaRazza(String codice, String tipo, String caratteristica) {
        try (Connection conn = DBConfig.getConnection()) {
            DSLContext ctx = DSL.using(conn, SQLDialect.MYSQL);

            Query query = ctx.insertInto(Tables.SPECIFICA_RAZZA)
                .set(Tables.SPECIFICA_RAZZA.CODICE, codice)
                .set(Tables.SPECIFICA_RAZZA.TIPO_CAR_GENERALE, tipo)
                .set(Tables.SPECIFICA_RAZZA.CARATTERISTICA, caratteristica);

            int righeInserite = query.execute();
            return righeInserite > 0;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean deleteSpecificaRazza(String codice) {
        try (Connection conn = DBConfig.getConnection()) {
            DSLContext ctx = DSL.using(conn, SQLDialect.MYSQL);

            int righeCancellate = ctx.deleteFrom(Tables.SPECIFICA_RAZZA)
                .where(Tables.SPECIFICA_RAZZA.CODICE.eq(codice))
                .execute();
            return righeCancellate > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

}
