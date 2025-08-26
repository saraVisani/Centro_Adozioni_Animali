package it.unibo.adozione_animali.model.impl;

import it.unibo.adozione_animali.model.api.TurnoLavorativo;
import it.unibo.adozione_animali.util.DBConfig;
import nu.studer.sample.Tables;
import nu.studer.sample.tables.records.TurnoLavorativoRecord;
import org.jooq.DSLContext;
import org.jooq.impl.DSL;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.logging.Logger;

public class TurnoLavorativoDAO implements TurnoLavorativo {

    final Logger logger = Logger.getLogger("loggerTurnoLavorativo");

    @Override
    public void insertTurnoLavorativo(byte numeroTurno, LocalDate dataTurno) {
        try (Connection conn = DBConfig.getConnection()) {
            DSLContext create = DSL.using(conn);
            create.insertInto(Tables.TURNO_LAVORATIVO)
                    .set(Tables.TURNO_LAVORATIVO.NUMERO, numeroTurno)
                    .set(Tables.TURNO_LAVORATIVO.DATA_TURNO_LAV, dataTurno)
                    .execute();
        } catch (SQLException e) {
            this.logger.severe("La connessione non ha funzionato");
        }
    }

    @Override
    public void deleteTurnoLavorativo(byte numeroTurno, LocalDate dataTurno) {
        try (Connection conn = DBConfig.getConnection()) {
            DSLContext create = DSL.using(conn);
            create.deleteFrom(Tables.TURNO_LAVORATIVO)
                    .where(Tables.TURNO_LAVORATIVO.DATA_TURNO_LAV.eq(dataTurno))
                    .and(Tables.TURNO_LAVORATIVO.NUMERO.eq(numeroTurno))
                    .execute();
        } catch (SQLException e) {
            this.logger.severe("La connessione non ha funzionato");
        }
    }

    public List<TurnoLavorativoRecord> getTurns() {

        List<TurnoLavorativoRecord> turns = null;

        try (Connection conn = DBConfig.getConnection()) {
            DSLContext create = DSL.using(conn);

            turns = create.selectFrom(Tables.TURNO_LAVORATIVO)
                    .orderBy(Tables.TURNO_LAVORATIVO.DATA_TURNO_LAV.desc(),
                            Tables.TURNO_LAVORATIVO.NUMERO.asc())
                    .fetch()
                    .stream()
                    .toList();

        } catch (SQLException e) {
            this.logger.severe("La connessione non ha funzionato");
        }

        return turns;
    }
}
