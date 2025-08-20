package it.unibo.adozione_animali.model.impl.caratteristica;

import java.sql.Connection;
import org.jooq.DSLContext;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;

import it.unibo.adozione_animali.model.api.caratteristica.Riferimento;
import it.unibo.adozione_animali.util.DBConfig;
import nu.studer.sample.Tables;

public class RiferimentoDAO implements Riferimento{

    @Override
    public boolean insertRiferimento(String codice, String specie, String razza) {
        try (Connection connection = DBConfig.getConnection()) {
            DSLContext ctx = DSL.using(connection, SQLDialect.MYSQL);
            int rowsInserted = ctx.insertInto(Tables.RIFERIMENTO)
                    .set(Tables.RIFERIMENTO.CODICE, codice)
                    .set(Tables.RIFERIMENTO.COD_SPECIE, specie)
                    .set(Tables.RIFERIMENTO.NOME, razza)
                    .execute();
            return rowsInserted > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean updateRiferimentoRazza(String codice, String newSpecie, String newRazza) {
        try (Connection connection = DBConfig.getConnection()) {
            DSLContext ctx = DSL.using(connection, SQLDialect.MYSQL);
            int rowsUpdated = ctx.update(Tables.RIFERIMENTO)
                    .set(Tables.RIFERIMENTO.COD_SPECIE, newSpecie)
                    .set(Tables.RIFERIMENTO.NOME, newRazza)
                    .where(Tables.RIFERIMENTO.CODICE.eq(codice))
                    .execute();
            return rowsUpdated > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean deleteRiferimento(String codice) {
        try (Connection connection = DBConfig.getConnection()) {
            DSLContext ctx = DSL.using(connection, SQLDialect.MYSQL);
            int rowsDeleted = ctx.deleteFrom(Tables.RIFERIMENTO)
                    .where(Tables.RIFERIMENTO.CODICE.eq(codice))
                    .execute();
            return rowsDeleted > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

}
