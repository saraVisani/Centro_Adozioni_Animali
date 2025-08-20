package it.unibo.adozione_animali.model.impl;

import it.unibo.adozione_animali.model.api.Task;
import nu.studer.sample.Routines;
import nu.studer.sample.Tables;
import org.jooq.DSLContext;
import org.jooq.impl.DSL;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;
import it.unibo.adozione_animali.util.DBConfig;

public class TaskDAO implements Task {

    final Logger logger = Logger.getLogger("loggerProblema");

    @Override
    public void insertTask(final String CF, final byte numeroTurno, final LocalDate dataTask, final String lavoro,
                           final Optional<List<List<String>>> animali) {
        /*TODO--> prima di fare l'inserimento controllo se la lista di animali data ha size <= 10 se sì prendo
            il primo per creare la task e poi faccio un for per creare gestione --> tutto ciò
            va fatto se la lista non è null e il tipo lavoro deve essere trattamento_medico o accudimento
         */
        try (Connection conn = DBConfig.getConnection()) {
            DSLContext create = DSL.using(conn);
            if (lavoro.equalsIgnoreCase("trattamento_medico")
                    || lavoro.equalsIgnoreCase("accudimento")) {
                if (animali.isPresent() && animali.get().size() <= 10) {
                    final List<String> animale = animali.get().getFirst();
                    Routines.inserimentoTask(create.configuration(), CF, numeroTurno, dataTask, lavoro,
                            animale.get(0), animale.get(1), Integer.parseInt(animale.get(2)), animale.get(3));
                } else {
                    this.logger.severe("Non sono stati inseriti gli animali o il loro numero è superiore a 10");
                }
            } else {
                Routines.inserimentoTask(create.configuration(), CF, numeroTurno, dataTask, lavoro,
                        null, null, null, null);
            }
        } catch (SQLException e) {
            this.logger.severe("La connessione non ha funzionato");
        }
    }

    @Override
    public void updateCF(String CF, byte numeroTurno, LocalDate dataTask, String nuovoCF) {
        try (Connection conn = DBConfig.getConnection()) {
            DSLContext create = DSL.using(conn);
            create.update(Tables.TASK)
                    .set(Tables.TASK.CF, nuovoCF)
                    .where(Tables.TASK.CF.eq(CF))
                    .and(Tables.TASK.NUMERO.eq(numeroTurno))
                    .and(Tables.TASK.DATA_TASK.eq(dataTask))
                    .execute();
        } catch (SQLException e) {
            this.logger.severe("La connessione non ha funzionato");
        }
    }

    @Override
    public void updateData(String CF, byte numeroTurno, LocalDate dataTask, LocalDate nuovaData) {
        try (Connection conn = DBConfig.getConnection()) {
            DSLContext create = DSL.using(conn);
            create.update(Tables.TASK)
                    .set(Tables.TASK.DATA_TASK, nuovaData)
                    .where(Tables.TASK.CF.eq(CF))
                    .and(Tables.TASK.NUMERO.eq(numeroTurno))
                    .and(Tables.TASK.DATA_TASK.eq(dataTask))
                    .execute();
        } catch (SQLException e) {
            this.logger.severe("La connessione non ha funzionato");
        }
    }

    @Override
    public void updateNumeroTurno(String CF, byte numeroTurno, LocalDate dataTask, byte nuovoNumeroTurno) {
        try (Connection conn = DBConfig.getConnection()) {
            DSLContext create = DSL.using(conn);
            create.update(Tables.TASK)
                    .set(Tables.TASK.NUMERO, nuovoNumeroTurno)
                    .where(Tables.TASK.CF.eq(CF))
                    .and(Tables.TASK.NUMERO.eq(numeroTurno))
                    .and(Tables.TASK.DATA_TASK.eq(dataTask))
                    .execute();
        } catch (SQLException e) {
            this.logger.severe("La connessione non ha funzionato");
        }
    }

    @Override
    public void updateLavoro(String CF, byte numeroTurno, LocalDate dataTask, String lavoro) {
        try (Connection conn = DBConfig.getConnection()) {
            DSLContext create = DSL.using(conn);
            create.update(Tables.TASK)
                    .set(Tables.TASK.LAVORO, lavoro)
                    .where(Tables.TASK.CF.eq(CF))
                    .and(Tables.TASK.NUMERO.eq(numeroTurno))
                    .and(Tables.TASK.DATA_TASK.eq(dataTask))
                    .execute();
        } catch (SQLException e) {
            this.logger.severe("La connessione non ha funzionato");
        }
    }

    @Override
    public void deleteTask(String CF, byte numeroTurno, LocalDate dataTask) {
        try (Connection conn = DBConfig.getConnection()) {
            DSLContext create = DSL.using(conn);
            create.deleteFrom(Tables.TASK)
                    .where(Tables.TASK.CF.eq(CF))
                    .and(Tables.TASK.NUMERO.eq(numeroTurno))
                    .and(Tables.TASK.DATA_TASK.eq(dataTask))
                    .execute();
        } catch (SQLException e) {
            this.logger.severe("La connessione non ha funzionato");
        }
    }
}
