package it.unibo.adozione_animali.model.impl;

import it.unibo.adozione_animali.model.api.Personale;
import nu.studer.sample.Tables;
import org.jooq.DSLContext;
import org.jooq.impl.DSL;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.logging.Logger;
import it.unibo.adozione_animali.util.DBConfig;

public class PersonaleDAO implements Personale {

    Logger logger = Logger.getLogger("loggerPersonale");

    @Override
    public void insertPersonale(final String CF, final byte tempoLavoro, final LocalDate dataAssunzioneDip,
                                final LocalDate dataAssunzioneVol, final LocalDate dataFineLavoroDip,
                                final LocalDate dataFineLavoroVol, final short stipendio,
                                final byte exVolontario, final byte exDipendente,
                                final String codProvincia, final String codCitta, final int numero) {
        try (Connection conn = DBConfig.getConnection()) {
            DSLContext create = DSL.using(conn);
            create.insertInto(Tables.PERSONALE)
                    .set(Tables.PERSONALE.CF, CF)
                    .set(Tables.PERSONALE.TEMPO_LAVORO, tempoLavoro)
                    .set(Tables.PERSONALE.DATA_ASSUNZIONE_DIP, dataAssunzioneDip)
                    .set(Tables.PERSONALE.DATA_ASSUNZIONE_VOL, dataAssunzioneVol)
                    .set(Tables.PERSONALE.DATA_FINE_LAVORO_DIP, dataFineLavoroDip)
                    .set(Tables.PERSONALE.DATA_FINE_LAVORO_VOL, dataFineLavoroVol)
                    .set(Tables.PERSONALE.STIPENDIO, stipendio)
                    .set(Tables.PERSONALE.EX_VOLONTARIO, exVolontario)
                    .set(Tables.PERSONALE.EX_DIPENDENTE, exDipendente)
                    .set(Tables.PERSONALE.COD_PROVINCIA, codProvincia)
                    .set(Tables.PERSONALE.COD_CITTA_, codCitta)
                    .set(Tables.PERSONALE.NUMERO, numero)
                    .execute();
        } catch (SQLException e) {
            this.logger.severe("La connessione non ha funzionato");
        }
    }

    @Override
    public void updateTempoLavoro(final String CF, final byte tempoLavoro) {
        try (Connection conn = DBConfig.getConnection()) {
            DSLContext create = DSL.using(conn);
            create.update(Tables.PERSONALE)
                    .set(Tables.PERSONALE.TEMPO_LAVORO, tempoLavoro)
                    .execute();
        } catch (SQLException e) {
            this.logger.severe("La connessione non ha funzionato");
        }
    }

    @Override
    public void updateDataAssunzioneDip(final String CF, final LocalDate dataAssunzioneDip) {
        try (Connection conn = DBConfig.getConnection()) {
            DSLContext create = DSL.using(conn);
            create.update(Tables.PERSONALE)
                    .set(Tables.PERSONALE.DATA_ASSUNZIONE_DIP, dataAssunzioneDip)
                    .execute();
        } catch (SQLException e) {
            this.logger.severe("La connessione non ha funzionato");
        }
    }

    @Override
    public void updateDataAssunzioneVol(final String CF, final LocalDate dataAssunzioneVol) {
        try (Connection conn = DBConfig.getConnection()) {
            DSLContext create = DSL.using(conn);
            create.update(Tables.PERSONALE)
                    .set(Tables.PERSONALE.DATA_ASSUNZIONE_VOL, dataAssunzioneVol)
                    .execute();
        } catch (SQLException e) {
            this.logger.severe("La connessione non ha funzionato");
        }
    }

    @Override
    public void updateDataFineLavoroDip(final String CF, final LocalDate dataFineLavoroDip) {
        try (Connection conn = DBConfig.getConnection()) {
            DSLContext create = DSL.using(conn);
            byte b = 1;
            create.update(Tables.PERSONALE)
                    .set(Tables.PERSONALE.DATA_FINE_LAVORO_DIP, dataFineLavoroDip)
                    .where(Tables.PERSONALE.CF.eq(CF))
                    .execute();
            if (dataFineLavoroDip != null) {
                create.update(Tables.PERSONALE)
                        .set(Tables.PERSONALE.EX_DIPENDENTE, b)
                        .where(Tables.PERSONALE.CF.eq(CF))
                        .execute();
            }
        } catch (SQLException e) {
            this.logger.severe("La connessione non ha funzionato");
        }
    }

    @Override
    public void updateDataFineLavoroVol(final String CF, final LocalDate dataFineLavoroVol) {
        try (Connection conn = DBConfig.getConnection()) {
            DSLContext create = DSL.using(conn);
            byte b = 1;
            create.update(Tables.PERSONALE)
                    .set(Tables.PERSONALE.DATA_FINE_LAVORO_VOL, dataFineLavoroVol)
                    .where(Tables.PERSONALE.CF.eq(CF))
                    .execute();
            if (dataFineLavoroVol != null) {
                create.update(Tables.PERSONALE)
                        .set(Tables.PERSONALE.EX_VOLONTARIO, b)
                        .where(Tables.PERSONALE.CF.eq(CF))
                        .execute();
            }
        } catch (SQLException e) {
            this.logger.severe("La connessione non ha funzionato");
        }
    }

    @Override
    public void updateStipendio(final String CF, final short stipendio) {
        try (Connection conn = DBConfig.getConnection()) {
            DSLContext create = DSL.using(conn);
            create.update(Tables.PERSONALE)
                    .set(Tables.PERSONALE.STIPENDIO, stipendio)
                    .where(Tables.PERSONALE.CF.eq(CF))
                    .execute();
        } catch (SQLException e) {
            this.logger.severe("La connessione non ha funzionato");
        }
    }

    @Override
    public void updateIndirizzoCentro(final String CF, final String codProvincia, final String codCitta,
                                      final int numero) {
        try (Connection conn = DBConfig.getConnection()) {
            DSLContext create = DSL.using(conn);
            create.update(Tables.PERSONALE)
                    .set(Tables.PERSONALE.COD_PROVINCIA, codProvincia)
                    .set(Tables.PERSONALE.COD_CITTA_, codCitta)
                    .set(Tables.PERSONALE.NUMERO, numero)
                    .where(Tables.PERSONALE.CF.eq(CF))
                    .execute();
        } catch (SQLException e) {
            this.logger.severe("La connessione non ha funzionato");
        }
    }

    @Override
    public void deletePersonale(final String CF) {
        try (Connection conn = DBConfig.getConnection()) {
            DSLContext create = DSL.using(conn);
            create.deleteFrom(Tables.PERSONALE)
                    .where(Tables.PERSONALE.CF.eq(CF))
                    .execute();
        } catch (SQLException e) {
            this.logger.severe("La connessione non ha funzionato");
        }
    }
}
