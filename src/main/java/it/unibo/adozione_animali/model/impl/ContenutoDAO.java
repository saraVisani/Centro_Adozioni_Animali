package it.unibo.adozione_animali.model.impl;

import it.unibo.adozione_animali.model.api.Contenuto;
import java.sql.Connection;
import java.sql.SQLException;

import org.jooq.DSLContext;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;
import nu.studer.sample.Tables;

public class ContenutoDAO implements Contenuto {

    @Override
    public boolean insertContenuto(String codice, String specie) {
        try (Connection connection = DBConfig.getConnection()) {
            DSLContext ctx = DSL.using(connection, SQLDialect.MYSQL);
            int rowsInserted = ctx.insertInto(Tables.CONTENUTO)
                    .set(Tables.CONTENUTO.CODICE, codice)
                    .set(Tables.CONTENUTO.COD_SPECIE, specie)
                    .execute();
            return rowsInserted > 0;
        } catch (SQLException e) {
            throw new RuntimeException("Error inserting content", e);
        }
    }

    @Override
    public boolean updateContenutoSpecie(String codice, String newSpecie) {
        try (Connection connection = DBConfig.getConnection()) {
            DSLContext ctx = DSL.using(connection, SQLDialect.MYSQL);
            int rowsUpdated = ctx.update(Tables.CONTENUTO)
                    .set(Tables.CONTENUTO.COD_SPECIE, newSpecie)
                    .where(Tables.CONTENUTO.CODICE.eq(codice))
                    .execute();
            return rowsUpdated > 0;
        } catch (SQLException e) {
            throw new RuntimeException("Error updating content species", e);
        }
    }

    @Override
    public boolean deleteContenuto(String codice) {
        try (Connection connection = DBConfig.getConnection()) {
            DSLContext ctx = DSL.using(connection, SQLDialect.MYSQL);
            int rowsDeleted = ctx.deleteFrom(Tables.CONTENUTO)
                    .where(Tables.CONTENUTO.CODICE.eq(codice))
                    .execute();
            return rowsDeleted > 0;
        } catch (SQLException e) {
            throw new RuntimeException("Error deleting content", e);
        }
    }

}
