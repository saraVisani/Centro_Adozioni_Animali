package it.unibo.adozione_animali.model.impl;

import it.unibo.adozione_animali.model.api.Ricovero;
import nu.studer.sample.Routines;
import nu.studer.sample.Tables;
import org.jooq.DSLContext;
import org.jooq.impl.DSL;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.logging.Logger;

public class RicoveroDAO implements Ricovero {

    Logger logger = Logger.getLogger("loggerRicovero");

    @Override
    public void insertRicovero(final int codFascicolo, final short numeroProblema, final String paragrafo,
                               final LocalDate dataInizioRicovero, final LocalDate dataFineRicovero,
                               final String nomeOspedale, final String codProvincia, final String codCitta,
                               final int numero, final String codAnimale) {
        try (Connection conn = DBConfig.getConnection()) {
            DSLContext create = DSL.using(conn);
            Routines.inserimentoRicovero(create.configuration(), codFascicolo, numeroProblema, paragrafo,
                    dataInizioRicovero, dataFineRicovero, nomeOspedale, codProvincia, codCitta, numero, codAnimale);
        } catch (SQLException e) {
            this.logger.severe("La connessione non ha funzionato");
        }
    }

    @Override
    public void updateRicovero(final int codFascicolo, final short numeroProblema, final String paragrafo,
                               final LocalDate dataFineRicovero) {
        try (Connection conn = DBConfig.getConnection()) {
            DSLContext create = DSL.using(conn);
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
