package it.unibo.adozione_animali.model.impl;

import it.unibo.adozione_animali.model.api.Task;
import nu.studer.sample.Routines;
import nu.studer.sample.Tables;
import nu.studer.sample.tables.records.PersonaleRecord;
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
                           final Optional<List<String>> animali) {
        /*TODO--> prima di fare l'inserimento controllo se la lista di animali data ha size <= 10 se sì prendo
            il primo per creare la task e poi faccio un for per creare gestione --> tutto ciò
            va fatto se la lista non è null e il tipo lavoro deve essere trattamento_medico o accudimento
         */
        try (Connection conn = DBConfig.getConnection()) {
            DSLContext create = DSL.using(conn);
            PersonaleRecord personale = create.selectFrom(Tables.PERSONALE)
                    .where(Tables.PERSONALE.CF.eq(CF))
                    .fetchOne();
            if (personale != null) {
                String lavoroFin = lavoro.trim().replace(" ", "_");
                if (lavoroFin.equalsIgnoreCase("trattamento_medico")
                        || lavoro.equalsIgnoreCase("accudimento")) {
                    if (animali.isPresent() && animali.get().size() <= 10) {
                        List<String> animals = animali.get();
                        Routines.inserimentoTask(create.configuration(), CF, numeroTurno, dataTask, lavoroFin,
                                personale.getCodProvincia(), personale.getCodCitta_(), personale.getNumero(),
                                animals.getFirst());
                        animals.removeFirst();
                        animals.forEach(animal ->
                                create.insertInto(Tables.GESTIONE)
                                        .set(Tables.GESTIONE.COD_PROVINCIA, personale.getCodProvincia())
                                        .set(Tables.GESTIONE.COD_CITTA_, personale.getCodCitta_())
                                        .set(Tables.GESTIONE.NUMERO_IND, personale.getNumero())
                                        .set(Tables.GESTIONE.COD_ANIMALE, animal.trim())
                                        .set(Tables.GESTIONE.NUMERO, numeroTurno)
                                        .set(Tables.GESTIONE.DATA_TASK, dataTask)
                                        .set(Tables.GESTIONE.CF, CF)
                                        .execute());
                    } else {
                        this.logger.severe("Non sono stati inseriti gli animali o il loro numero è superiore a 10");
                        throw new SQLException();
                    }
                } else {
                    Routines.inserimentoTask(create.configuration(), CF, numeroTurno, dataTask, lavoro,
                            null, null, null, null);
                }
            } else {
                this.logger.severe("Non esiste il CF inserito fra quelli dei lavoratori");
                throw new SQLException();
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
