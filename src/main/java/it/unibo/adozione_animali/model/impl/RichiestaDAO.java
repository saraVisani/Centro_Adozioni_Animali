package it.unibo.adozione_animali.model.impl;

import it.unibo.adozione_animali.model.api.Richiesta;
import it.unibo.adozione_animali.util.DBConfig;
import nu.studer.sample.Routines;
import nu.studer.sample.Tables;
import nu.studer.sample.tables.records.RichiestaRecord;
import org.jooq.DSLContext;
import org.jooq.impl.DSL;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.logging.Logger;

public class RichiestaDAO implements Richiesta {

    Logger logger = Logger.getLogger("loggerRichiesta");
    private RichiedenteDAO richiedente = new RichiedenteDAO();

    @Override
    public void insertRichiesta(final String codProvinciaAnimale, final String codCittaAnimale, final int numeroAnimale,
                                final String codAnimale, final String CF, final String codProvinciaRichiedente,
                                final String codCittaRichiedente, final int numeroRichiedente) {
        try (Connection conn = DBConfig.getConnection()) {
            DSLContext create = DSL.using(conn);

            this.richiedente.checkCFExistence(CF);

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

            this.richiedente.checkCFExistence(CF);

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

            this.richiedente.checkCFExistence(CF);

            create.update(Tables.RICHIESTA)
                    .set(Tables.RICHIESTA.DATA_CHIUSURA, LocalDate.now())
                    .where(Tables.RICHIESTA.COD_PROVINCIA.eq(codProvinciaAnimale))
                    .and(Tables.RICHIESTA.COD_CITTA_.eq(codCittaAnimale))
                    .and(Tables.RICHIESTA.NUMERO.eq(numeroAnimale))
                    .and(Tables.RICHIESTA.DATA_RICHIESTA.eq(dataRichiesta))
                    .and(Tables.RICHIESTA.CF.eq(CF))
                    .and(Tables.RICHIESTA.DATA_CHIUSURA.isNull())
                    .execute();
        } catch (SQLException e) {
            this.logger.severe("La connessione non ha funzionato");
        }
    }

    @Override
    public void deleteRichiesta(final String codProvinciaAnimale, final String codCittaAnimale, final int numeroAnimale,
                                final String codAnimale, final LocalDate dataRichiesta, final String CF) {
        System.out.println("Sono entrato");
        try (Connection conn = DBConfig.getConnection()) {
            DSLContext create = DSL.using(conn);

            this.richiedente.checkCFExistence(CF);

            int righeEliminate = create.deleteFrom(Tables.RICHIESTA)
                    .where(Tables.RICHIESTA.COD_PROVINCIA.eq(codProvinciaAnimale))
                    .and(Tables.RICHIESTA.COD_CITTA_.eq(codCittaAnimale))
                    .and(Tables.RICHIESTA.NUMERO.eq(numeroAnimale))
                    .and(Tables.RICHIESTA.DATA_RICHIESTA.eq(dataRichiesta))
                    .and(Tables.RICHIESTA.CF.eq(CF))
                    .execute();
            if (righeEliminate == 0) {
                throw new IllegalStateException("Non esiste la richiesta indicata");
            }
        } catch (SQLException e) {
            this.logger.severe("La connessione non ha funzionato");
        }
    }

    public List<RichiestaRecord> getRequests(String codProv, String codCit, Integer num) {

        List<RichiestaRecord> requests = null;

        try (Connection conn = DBConfig.getConnection()) {
            DSLContext create = DSL.using(conn);

            this.checkCentroExistance(codProv, codCit, num);

            requests = create.selectFrom(Tables.RICHIESTA)
                    .where(Tables.RICHIESTA.COD_PROVINCIA.eq(codProv))
                    .and(Tables.RICHIESTA.COD_CITTA_.eq(codCit))
                    .and(Tables.RICHIESTA.NUMERO.eq(num))
                    .fetch();

        } catch (SQLException e) {
            this.logger.severe("La connessione non ha funzionato");
        }
        return requests;
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
