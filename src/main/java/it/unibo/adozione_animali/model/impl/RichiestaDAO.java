package it.unibo.adozione_animali.model.impl;

import it.unibo.adozione_animali.model.api.Richiesta;
import it.unibo.adozione_animali.util.DBConfig;
import nu.studer.sample.Routines;
import nu.studer.sample.Tables;
import org.jooq.DSLContext;
import org.jooq.impl.DSL;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.logging.Logger;

public class RichiestaDAO implements Richiesta {

    Logger logger = Logger.getLogger("loggerRichiesta");


    @Override
    public void insertRichiesta(final String codProvinciaAnimale, final String codCittaAnimale, final int numeroAnimale,
                                final String codAnimale, final String CF, final String codProvinciaRichiedente,
                                final String codCittaRichiedente, final int numeroRichiedente) {
        try (Connection conn = DBConfig.getConnection()) {
            DSLContext create = DSL.using(conn);
            Routines.inserimentoNuovaRichiesta(create.configuration(), codProvinciaAnimale, codCittaAnimale,
                    numeroAnimale, codAnimale, CF, codProvinciaRichiedente, codCittaRichiedente,
                    numeroRichiedente);
        } catch (SQLException e) {
            this.logger.severe("La connessione non ha funzionato");
        }

    }

    @Override
    public void chiusuraPositivaRichiesta(final String codProvinciaAnimale, final String codCittaAnimale,
                                          final int numeroAnimale, final String codAnimale,
                                          final LocalDate dataRichiesta, final String CF) {
        try (Connection conn = DBConfig.getConnection()) {
            DSLContext create = DSL.using(conn);
            Routines.chiusuraRichiesta(create.configuration(), codProvinciaAnimale, codCittaAnimale,
                    numeroAnimale, codAnimale, dataRichiesta, CF);
        } catch (SQLException e) {
            this.logger.severe("La connessione non ha funzionato");
        }
    }

    @Override
    public void rifiutoRichiesta(final String codProvinciaAnimale, final String codCittaAnimale,
                                 final int numeroAnimale, final String codAnimale, final LocalDate dataRichiesta,
                                 final String CF) {
        try (Connection conn = DBConfig.getConnection()) {
            DSLContext create = DSL.using(conn);
            create.update(Tables.RICHIESTA)
                    .set(Tables.RICHIESTA.DATA_CHIUSURA, LocalDate.now())
                    .where(Tables.RICHIESTA.COD_PROVINCIA.eq(codProvinciaAnimale))
                    .and(Tables.RICHIESTA.COD_CITTA_.eq(codCittaAnimale))
                    .and(Tables.RICHIESTA.NUMERO.eq(numeroAnimale))
                    .and(Tables.RICHIESTA.DATA_RICHIESTA.eq(dataRichiesta))
                    .and(Tables.RICHIESTA.CF.eq(CF))
                    .execute();
        } catch (SQLException e) {
            this.logger.severe("La connessione non ha funzionato");
        }
    }

    @Override
    public void deleteRichiesta(final String codProvinciaAnimale, final String codCittaAnimale, final int numeroAnimale,
                                final String codAnimale, final LocalDate dataRichiesta, final String CF) {
        try (Connection conn = DBConfig.getConnection()) {
            DSLContext create = DSL.using(conn);
            create.deleteFrom(Tables.RICHIESTA)
                    .where(Tables.RICHIESTA.COD_PROVINCIA.eq(codProvinciaAnimale))
                    .and(Tables.RICHIESTA.COD_CITTA_.eq(codCittaAnimale))
                    .and(Tables.RICHIESTA.NUMERO.eq(numeroAnimale))
                    .and(Tables.RICHIESTA.DATA_RICHIESTA.eq(dataRichiesta))
                    .and(Tables.RICHIESTA.CF.eq(CF))
                    .execute();
        } catch (SQLException e) {
            this.logger.severe("La connessione non ha funzionato");
        }
    }
}
