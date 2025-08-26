package it.unibo.adozione_animali.model.impl;

import it.unibo.adozione_animali.model.api.Richiedente;
import it.unibo.adozione_animali.util.DBConfig;
import nu.studer.sample.Routines;
import nu.studer.sample.Tables;
import org.jooq.DSLContext;
import org.jooq.Record;
import org.jooq.impl.DSL;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.logging.Logger;

public class RichiedenteDAO implements Richiedente {

    final Logger logger = Logger.getLogger("loggerRichiedente");


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

            checkCFExistence(CF);

            Routines.updateAbbandono(create.configuration(), CF, nuovaDataAbbandono);
        } catch (SQLException e) {
            this.logger.severe("La connessione non ha funzionato");
        }
    }

    @Override
    public void updateAbuso(String CF, boolean abuso) {
        try (Connection conn = DBConfig.getConnection()) {
            DSLContext create = DSL.using(conn);

            checkCFExistence(CF);

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

            checkCFExistence(CF);

            create.deleteFrom(Tables.RICHIEDENTE)
                    .where(Tables.RICHIEDENTE.CF.eq(CF))
                    .execute();

            boolean worker = create.fetchExists(
                    create.selectFrom(Tables.PERSONALE)
                            .where(Tables.PERSONALE.CF.eq(CF))
            );

            if (!worker) {
                new PersonaDAO().deletePersona(CF);
            }

        } catch (SQLException e) {
            this.logger.severe("La connessione non ha funzionato");
        }
    }

    public void checkCFExistence(String CF) {
        try (Connection conn = DBConfig.getConnection()) {
            DSLContext create = DSL.using(conn);

            boolean exists = create.fetchExists(
                    create.selectFrom(Tables.RICHIEDENTE)
                            .where(Tables.RICHIEDENTE.CF.eq(CF))
            );

            if (!exists) {
                throw new IllegalArgumentException("Il codice fiscale fornito non è corretto");
            }

        } catch (SQLException e) {
            this.logger.severe("La connessione non ha funzionato");
        }
    }

    public List<Record> getRichiedenti() {
        List<Record> richiedenti = null;
        try (Connection conn = DBConfig.getConnection()) {
            DSLContext create = DSL.using(conn);

            richiedenti = create.select()
                    .from(Tables.RICHIEDENTE)
                    .join(Tables.PERSONA)
                    .on(Tables.RICHIEDENTE.CF.eq(Tables.PERSONA.CF))
                    .orderBy(Tables.PERSONA.COGNOME.asc(),
                            Tables.PERSONA.NOME.asc())
                    .fetch()
                    .stream()
                    .toList();

        } catch (SQLException e) {
            this.logger.severe("La connessione non ha funzionato");
        }
        return richiedenti;
    }
}
