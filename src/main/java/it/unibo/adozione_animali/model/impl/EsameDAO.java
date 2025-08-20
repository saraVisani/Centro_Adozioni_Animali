package it.unibo.adozione_animali.model.impl;

import it.unibo.adozione_animali.model.api.Esame;
import nu.studer.sample.Routines;
import nu.studer.sample.Tables;
import org.jooq.DSLContext;
import org.jooq.impl.DSL;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.logging.Logger;
import it.unibo.adozione_animali.util.DBConfig;

public class EsameDAO implements Esame {

    final Logger logger = Logger.getLogger("EsameLogger");

    @Override
    public void insertEsame(final Integer codFascicolo, final Short numeroProblema, final String paragrafo,
                            final LocalDate dataEsame, final String codProvincia, final String codCitta,
                            int numero, String codAnimale, List<String> codTipiEsame) {
        try (Connection conn = DBConfig.getConnection()) {
            DSLContext create = DSL.using(conn);
            final String paragrafoEsame = Routines.inserimentoEsame(create.configuration(), codFascicolo, numeroProblema,
                paragrafo, null, dataEsame, codProvincia, codCitta, numero,
                    codAnimale, codTipiEsame.getFirst());
            codTipiEsame.removeFirst();
            for (String codTipoEsame : codTipiEsame) {
                Routines.inserimentoSpecifica(create.configuration(), codFascicolo, numeroProblema, paragrafoEsame, codTipoEsame);
            }
        } catch (SQLException e) {
            this.logger.severe("La connessione non ha funzionato");
        }
    }

            @Override
    public void updateEsame(int codFascicolo, short numeroProblema, String paragrafo, LocalDate dataEsame) {
            try (Connection conn = DBConfig.getConnection()) {
                DSLContext create = DSL.using(conn);
                create.update(Tables.ESAME)
                        .set(Tables.ESAME.DATA_ESAME, dataEsame)
                        .where(Tables.ESAME.COD_FASCICOLO.eq(codFascicolo))
                        .and(Tables.ESAME.NUMERO.eq(numeroProblema))
                        .and(Tables.ESAME.PARAGRAFO.eq(paragrafo))
                        .execute();
            } catch (SQLException e) {
                this.logger.severe("La connessione non ha funzionato");
            }
    }

    @Override
    public void deleteEsame(int codFascicolo, short numeroProblema, String paragrafo) {
        try (Connection conn = DBConfig.getConnection()) {
            DSLContext create = DSL.using(conn);
            Routines.rimozionePaginaFascicolo(create.configuration(), codFascicolo, numeroProblema, paragrafo);
        } catch (SQLException e) {
            this.logger.severe("La connessione non ha funzionato");
        }
    }
}
