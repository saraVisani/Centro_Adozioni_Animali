package it.unibo.adozione_animali.model.impl;

import it.unibo.adozione_animali.model.api.Persona;
import nu.studer.sample.Tables;
import org.jooq.DSLContext;
import org.jooq.impl.DSL;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.logging.Logger;
import it.unibo.adozione_animali.util.DBConfig;

public class PersonaDAO implements Persona {

    final Logger logger = Logger.getLogger("loggerPersona");

    @Override
    public void insertPersona(final String CF, final String nome, final String cognome, final String email,
                              final String password, final String telefono) {
        try (Connection conn = DBConfig.getConnection()) {
            DSLContext create = DSL.using(conn);
            create.insertInto(Tables.PERSONA)
                    .set(Tables.PERSONA.CF, CF)
                    .set(Tables.PERSONA.NOME, nome)
                    .set(Tables.PERSONA.COGNOME, cognome)
                    .set(Tables.PERSONA.EMAIL, email)
                    .set(Tables.PERSONA.PASSWORD_PERSONA, password)
                    .set(Tables.PERSONA.N_TELEFONO, telefono)
                    .execute();
        } catch (SQLException e) {
            this.logger.severe("La connessione non ha funzionato");
        }
    }

    @Override
    public void updateEmail(final String CF, final String email) {
        try (Connection conn = DBConfig.getConnection()) {
            DSLContext create = DSL.using(conn);
            create.update(Tables.PERSONA)
                    .set(Tables.PERSONA.EMAIL, email)
                    .where(Tables.PERSONA.CF.eq(CF))
                    .execute();
        } catch (SQLException e) {
            this.logger.severe("La connessione non ha funzionato");
        }
    }

    @Override
    public void updateTelefono(final String CF, final String telefono) {
        try (Connection conn = DBConfig.getConnection()) {
            DSLContext create = DSL.using(conn);
            create.update(Tables.PERSONA)
                    .set(Tables.PERSONA.N_TELEFONO, telefono)
                    .where(Tables.PERSONA.CF.eq(CF))
                    .execute();
        } catch (SQLException e) {
            this.logger.severe("La connessione non ha funzionato");
        }
    }

    @Override
    public void updatePassword(final String CF, final String password) {
        try (Connection conn = DBConfig.getConnection()) {
            DSLContext create = DSL.using(conn);
            create.update(Tables.PERSONA)
                    .set(Tables.PERSONA.PASSWORD_PERSONA, password)
                    .where(Tables.PERSONA.CF.eq(CF))
                    .execute();
        } catch (SQLException e) {
            this.logger.severe("La connessione non ha funzionato");
        }
    }

    @Override
    public void deletePersona(final String CF) {
        try (Connection conn = DBConfig.getConnection()) {
            DSLContext create = DSL.using(conn);
            create.deleteFrom(Tables.PERSONA)
                    .where(Tables.PERSONA.CF.eq(CF))
                    .execute();
        } catch (SQLException e) {
            this.logger.severe("La connessione non ha funzionato");
        }
    }
}
