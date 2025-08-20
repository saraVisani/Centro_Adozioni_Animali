package it.unibo.adozione_animali.model.impl.statistica;

import java.time.LocalDate;
import org.jooq.DSLContext;
import org.jooq.Query;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;

import it.unibo.adozione_animali.model.api.statistica.Statistica;
import it.unibo.adozione_animali.util.DBConfig;
import nu.studer.sample.Tables;
import java.sql.Connection;

public class StatisticaDAO implements Statistica{

    @Override
    public boolean insertStatistica(String codice, LocalDate data, String nome) {
        try (Connection conn = DBConfig.getConnection()) {
            DSLContext ctx = DSL.using(conn, SQLDialect.MYSQL);

            Query query = ctx.insertInto(Tables.STATISTICA)
                .set(Tables.STATISTICA.CODICE, codice)
                .set(Tables.STATISTICA.DATA_STATISTICA, data)
                .set(Tables.STATISTICA.NOME, nome);

            int righeInserite = query.execute();

            return righeInserite > 0;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean deleteStatistica(String codice, LocalDate data) {
        try (Connection conn = DBConfig.getConnection()) {
            DSLContext ctx = DSL.using(conn, SQLDialect.MYSQL);

            int righeCancellate = ctx.deleteFrom(Tables.STATISTICA)
                .where(Tables.STATISTICA.CODICE.eq(codice)
                    .and(Tables.STATISTICA.DATA_STATISTICA.eq(data)))
                .execute();
            return righeCancellate > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

}
