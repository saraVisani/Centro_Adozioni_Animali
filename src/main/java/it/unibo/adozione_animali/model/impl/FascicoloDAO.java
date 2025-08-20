package it.unibo.adozione_animali.model.impl;

import it.unibo.adozione_animali.model.api.Fascicolo;
import nu.studer.sample.Tables;
import org.jooq.DSLContext;
import org.jooq.impl.DSL;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.logging.Logger;
import it.unibo.adozione_animali.util.DBConfig;

public class FascicoloDAO implements Fascicolo {

    Logger logger = Logger.getLogger("loggerFascicolo");

    @Override
    public void deleteFascicolo(final int codFascicolo) {
        try (Connection conn = DBConfig.getConnection()) {
            DSLContext create = DSL.using(conn);
            create.deleteFrom(Tables.FASCICOLO_SANITARIO)
                    .where(Tables.FASCICOLO_SANITARIO.COD_FASCICOLO.eq(codFascicolo))
                    .execute();
        } catch (SQLException e) {
            this.logger.severe("La connessione non ha funzionato");
        }
    }
}
