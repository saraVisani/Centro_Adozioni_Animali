package it.unibo.adozione_animali.model.impl;

import it.unibo.adozione_animali.model.api.Ricovero;
import nu.studer.sample.Routines;
import nu.studer.sample.Tables;
import nu.studer.sample.tables.records.FascicoloSanitarioRecord;
import nu.studer.sample.tables.records.RicoveroRecord;
import org.jooq.DSLContext;
import org.jooq.impl.DSL;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.logging.Logger;
import it.unibo.adozione_animali.util.DBConfig;

public class RicoveroDAO implements Ricovero {

    final Logger logger = Logger.getLogger("loggerRicovero");

    @Override
    public void insertRicovero(final Integer codFascicolo, final Short numeroProblema, final String paragrafo,
                               final LocalDate dataInizioRicovero, final LocalDate dataFineRicovero,
                               final String nomeOspedale) {
        try (Connection conn = DBConfig.getConnection()) {
            DSLContext create = DSL.using(conn);
            FascicoloSanitarioRecord fascicolo = create.selectFrom(Tables.FASCICOLO_SANITARIO)
                    .where(Tables.FASCICOLO_SANITARIO.COD_FASCICOLO.eq(codFascicolo))
                    .fetchOne();
            Routines.inserimentoRicovero(create.configuration(), codFascicolo, numeroProblema, paragrafo,
                    dataInizioRicovero, dataFineRicovero, nomeOspedale, fascicolo.getCodProvincia(),
                    fascicolo.getCodCitta_(), fascicolo.getNumero(), fascicolo.getCodAnimale());
        } catch (SQLException e) {
            this.logger.severe("La connessione non ha funzionato");
        }
    }

    @Override
    public void updateRicovero(final int codFascicolo, final short numeroProblema, final String paragrafo,
                               final LocalDate dataFineRicovero) {
        try (Connection conn = DBConfig.getConnection()) {
            DSLContext create = DSL.using(conn);
            RicoveroRecord ricovero = create.selectFrom(Tables.RICOVERO)
                    .where(Tables.RICOVERO.COD_FASCICOLO.eq(codFascicolo))
                    .and(Tables.RICOVERO.NUMERO.eq(numeroProblema))
                    .and(Tables.RICOVERO.PARAGRAFO.eq(paragrafo))
                    .fetchOne();

            if (ricovero.getDataInizio().isAfter(dataFineRicovero)) {
                throw new IllegalArgumentException();
            }

            create.update(Tables.RICOVERO)
                    .set(Tables.RICOVERO.DATA_FINE, dataFineRicovero)
                    .where(Tables.RICOVERO.COD_FASCICOLO.eq(codFascicolo))
                    .and(Tables.RICOVERO.NUMERO.eq(numeroProblema))
                    .and(Tables.RICOVERO.PARAGRAFO.eq(paragrafo))
                    .execute();
        } catch (SQLException e) {
            this.logger.severe("La connessione non ha funzionato");
        }
    }

    @Override
    public void deleteRicovero(final int codFascicolo, final short numeroProblema, String paragrafo) {
        try (Connection conn = DBConfig.getConnection()) {
            DSLContext create = DSL.using(conn);
            Routines.rimozionePaginaFascicolo(create.configuration(), codFascicolo, numeroProblema, paragrafo);
        } catch (SQLException e) {
            this.logger.severe("La connessione non ha funzionato");
        }
    }
}
