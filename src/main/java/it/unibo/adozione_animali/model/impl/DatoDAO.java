package it.unibo.adozione_animali.model.impl;

import java.time.LocalDate;
import org.jooq.DSLContext;
import org.jooq.Query;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;
import nu.studer.sample.Tables;
import java.sql.Connection;
import it.unibo.adozione_animali.model.api.Dato;

public class DatoDAO implements Dato{

    @Override
    public boolean insertDato(String codice, String nome, String valore, String statistica, LocalDate data) {
        try (Connection conn = DBConfig.getConnection()) {
            DSLContext ctx = DSL.using(conn, SQLDialect.MYSQL);

            Query query = ctx.insertInto(Tables.DATO)
                .set(Tables.DATO.CODICE, codice)
                .set(Tables.DATO.NOME, nome)
                .set(Tables.DATO.VALORE, valore)
                .set(Tables.DATO.COD_STATISTICA, statistica)
                .set(Tables.DATO.DATA_STATISTICA, data);

            int righeInserite = query.execute();

            return righeInserite > 0;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean deleteDato(String codice, String nome) {
        try (Connection conn = DBConfig.getConnection()) {
            DSLContext ctx = DSL.using(conn, SQLDialect.MYSQL);

            int righeCancellate = ctx.deleteFrom(Tables.DATO)
                .where(Tables.DATO.CODICE.eq(codice)
                    .and(Tables.DATO.NOME.eq(nome)))
                .execute();
            return righeCancellate > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

}
