package it.unibo.adozione_animali.model.impl;

import it.unibo.adozione_animali.model.api.Task;
import nu.studer.sample.Routines;
import nu.studer.sample.Tables;
import nu.studer.sample.tables.records.PersonaleRecord;
import nu.studer.sample.tables.records.TaskRecord;
import org.jooq.DSLContext;

import org.jooq.Field;
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
    private final PersonaleDAO personaleCheck = new PersonaleDAO();

    @Override
    public void insertTask(final String CF, final byte numeroTurno, final LocalDate dataTask, final String lavoro,
                           final Optional<List<String>> animali) {

        try (Connection conn = DBConfig.getConnection()) {
            DSLContext create = DSL.using(conn);

            this.personaleCheck.checkCFExistence(CF);
            this.checkTurnoLavorativo(dataTask, numeroTurno);

            PersonaleRecord personale = create.selectFrom(Tables.PERSONALE)
                    .where(Tables.PERSONALE.CF.eq(CF))
                    .fetchOne();

            System.out.println(personale.getCodProvincia() + " " + personale.getCodCitta_() + " " + personale.getNumero());
            
            if (personale != null) {
                String lavoroFin = lavoro.trim().replace(" ", "_");
                if (lavoroFin.equalsIgnoreCase("trattamento_medico")
                        || lavoro.equalsIgnoreCase("accudimento")) {
                    if (animali.isPresent() && animali.get().size() <= 10) {
                        List<String> animals = animali.get();
                        System.out.println("Ci sono");
                        Routines.inserimentoTask(create.configuration(), CF, numeroTurno, dataTask, lavoroFin,
                                personale.getCodProvincia(), personale.getCodCitta_(), personale.getNumero(),
                                animals.getFirst());
                        System.out.println("Ci sono 2");
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
                        throw new IllegalStateException();
                    }
                } else {
                    Routines.inserimentoTask(create.configuration(), CF, numeroTurno, dataTask, lavoro,
                            null, null, null, null);
                }
            } else {
                this.logger.severe("Non esiste il CF inserito fra quelli dei lavoratori");
                throw new IllegalArgumentException(); //TODO --> da rimuovere c'è già il check
            }
        } catch (SQLException e) {
            this.logger.severe("La connessione non ha funzionato");
        }
    }

    @Override
    public void updateCF(String CF, byte numeroTurno, LocalDate dataTask, String nuovoCF) {
        try (Connection conn = DBConfig.getConnection()) {
            DSLContext create = DSL.using(conn);

            this.personaleCheck.checkCFExistence(CF);
            this.personaleCheck.checkCFExistence(nuovoCF);
            this.checkTurnoLavorativo(dataTask, numeroTurno);
            this.checkTaskExistence(CF, numeroTurno, dataTask);

            Field<Integer> numTurnsF = Routines.contaTaskGiornalieriLavoratore(nuovoCF, dataTask);
            Integer numTurns = create.select(numTurnsF)
                    .fetchOne(0, Integer.class);

            if (numTurns >= 3) {
                throw new IllegalArgumentException("Il nuovo CF indicato ha già 3 turni quel giorno");
            }

            PersonaleRecord personale = create.selectFrom(Tables.PERSONALE)
                    .where(Tables.PERSONALE.CF.eq(CF))
                    .fetchOne();

            boolean checkSameCenter = create.fetchExists(
                    create.selectFrom(Tables.PERSONALE)
                            .where(Tables.PERSONALE.CF.eq(nuovoCF))
                            .and(Tables.PERSONALE.COD_PROVINCIA.eq(personale.getCodProvincia()))
                            .and(Tables.PERSONALE.COD_CITTA_.eq(personale.getCodCitta_()))
                            .and(Tables.PERSONALE.NUMERO.eq(personale.getNumero()))
            );

            if (!checkSameCenter) {
                throw new IllegalArgumentException("Il nuovo CF fornito è di un lavoratore di un altro centro");
            }

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

            this.personaleCheck.checkCFExistence(CF);
            this.checkTurnoLavorativo(dataTask, numeroTurno);
            this.checkTurnoLavorativo(nuovaData, numeroTurno);
            this.checkTaskExistence(CF, numeroTurno, dataTask);

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

            this.personaleCheck.checkCFExistence(CF);
            this.checkTurnoLavorativo(dataTask, numeroTurno);
            this.checkTurnoLavorativo(dataTask, nuovoNumeroTurno);
            this.checkTaskExistence(CF, numeroTurno, dataTask);

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

            this.personaleCheck.checkCFExistence(CF);
            this.checkTurnoLavorativo(dataTask, numeroTurno);
            this.checkTaskExistence(CF, numeroTurno, dataTask);

            create.deleteFrom(Tables.TASK)
                    .where(Tables.TASK.CF.eq(CF))
                    .and(Tables.TASK.NUMERO.eq(numeroTurno))
                    .and(Tables.TASK.DATA_TASK.eq(dataTask))
                    .execute();
        } catch (SQLException e) {
            this.logger.severe("La connessione non ha funzionato");
        }
    }

    public List<TaskRecord> getTasks(String codProv, String codCit, Integer num, LocalDate date) {

        List<TaskRecord> tasks = null;

        try (Connection conn = DBConfig.getConnection()) {
            DSLContext create = DSL.using(conn);

            this.checkCentroExistance(codProv, codCit, num);

            tasks = create.selectFrom(Tables.TASK)
                    .where(Tables.TASK.DATA_TASK.eq(date))
                    .fetch();

            tasks.stream().filter(task -> create.fetchExists(
                    create.select()
                            .from(Tables.TASK)
                            .join(Tables.PERSONALE)
                            .on(Tables.PERSONALE.CF.eq(Tables.TASK.CF))
                            .where(Tables.PERSONALE.COD_PROVINCIA.eq(codProv))
                            .and(Tables.PERSONALE.COD_CITTA_.eq(codCit))
                            .and(Tables.PERSONALE.NUMERO.eq(num))
            )).toList();


        } catch (SQLException e) {
            this.logger.severe("La connessione non ha funzionato");
        }
        return tasks;
    }

    private void checkTurnoLavorativo(LocalDate date, byte turno) {
        try (Connection conn = DBConfig.getConnection()) {
            DSLContext create = DSL.using(conn);

            boolean exists = create.fetchExists(
                    create.selectFrom(Tables.TURNO_LAVORATIVO)
                            .where(Tables.TURNO_LAVORATIVO.NUMERO.eq(turno))
                            .and(Tables.TURNO_LAVORATIVO.DATA_TURNO_LAV.eq(date))
            );

            if (!exists) {
                throw new IllegalArgumentException("Il turno lavorativo indicato non esiste");
            }

        } catch (SQLException e) {
            this.logger.severe("La connessione non ha funzionato");
        }
    }

    private void checkTaskExistence(String CF, byte numeroTurno, LocalDate date) {
        try (Connection conn = DBConfig.getConnection()) {
            DSLContext create = DSL.using(conn);

            boolean exists = create.fetchExists(
                    create.selectFrom(Tables.TASK)
                            .where(Tables.TASK.NUMERO.eq(numeroTurno))
                            .and(Tables.TASK.DATA_TASK.eq(date))
                            .and(Tables.TASK.CF.eq(CF))
            );

            if (!exists) {
                throw new IllegalArgumentException("La task indicata non esiste");
            }

        } catch (SQLException e) {
            this.logger.severe("La connessione non ha funzionato");
        }
    }

    private void checkCentroExistance(String codProv, String codCit, Integer num) {
        try (Connection conn = DBConfig.getConnection()) {
            DSLContext create = DSL.using(conn);

            boolean exists = create.fetchExists(
                    create.selectFrom(Tables.CENTRO)
                            .where(Tables.CENTRO.COD_PROVINCIA.eq(codProv))
                            .and(Tables.CENTRO.COD_CITTA_.eq(codCit))
                            .and(Tables.CENTRO.NUMERO.eq(num))
            );

            if (!exists) {
                throw new IllegalArgumentException("Il centro indicato non esiste");
            }

        } catch (SQLException e) {
            this.logger.severe("La connessione non ha funzionato");
        }
    }


}
