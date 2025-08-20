package it.unibo.adozione_animali.model.impl;

import it.unibo.adozione_animali.model.api.Richiedente;
import it.unibo.adozione_animali.util.DBConfig;
import nu.studer.sample.Routines;
import nu.studer.sample.Tables;
import org.jooq.DSLContext;
import org.jooq.impl.DSL;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.logging.Logger;

public class RichiedenteDAO implements Richiedente {

    Logger logger = Logger.getLogger("loggerRichiedente");


    @Override
    public void insertRichiedente(String CF, short numeroAbbandoni, LocalDate dataAbbandonoRecente, byte abuso) {
        try (Connection conn = DBConfig.getConnection()) {
            DSLContext create = DSL.using(conn);
            create.insertInto(Tables.RICHIEDENTE)
                    .set(Tables.RICHIEDENTE.CF, CF)
                    .set(Tables.RICHIEDENTE.NUMERO, numeroAbbandoni)
                    .set(Tables.RICHIEDENTE.DATA_PIU_RECENTE, dataAbbandonoRecente)
                    .set(Tables.RICHIEDENTE.ABUSO_ANIMALI, abuso)
                    .execute();
        } catch (SQLException e) {
            this.logger.severe("La connessione non ha funzionato");
        }
    }

    @Override
    public void updateAbbandoni(String CF, LocalDate nuovaDataAbbandono) {
        try (Connection conn = DBConfig.getConnection()) {
            DSLContext create = DSL.using(conn);
            Routines.updateAbbandono(create.configuration(), CF, nuovaDataAbbandono);
        } catch (SQLException e) {
            this.logger.severe("La connessione non ha funzionato");
        }
    }

    @Override
    public void updateAbuso(String CF, boolean abuso) {
        try (Connection conn = DBConfig.getConnection()) {
            DSLContext create = DSL.using(conn);
            final byte b = (byte) (abuso ? 1 : 0);
            Routines.updateAbuso(create.configuration(), CF, b);
        } catch (SQLException e) {
            this.logger.severe("La connessione non ha funzionato");
        }
    }

    @Override
    public void deleteRichiedente(String CF) {
        try (Connection conn = DBConfig.getConnection()) {
            DSLContext create = DSL.using(conn);
            create.deleteFrom(Tables.RICHIEDENTE)
                    .where(Tables.RICHIEDENTE.CF.eq(CF))
                    .execute();
        } catch (SQLException e) {
            this.logger.severe("La connessione non ha funzionato");
        }
    }
}
