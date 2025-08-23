package it.unibo.adozione_animali.model.impl;

import it.unibo.adozione_animali.model.api.Esame;
import nu.studer.sample.Routines;
import nu.studer.sample.Tables;
import nu.studer.sample.tables.records.FascicoloSanitarioRecord;
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
                            final LocalDate dataEsame, List<String> codTipiEsame) {
        try (Connection conn = DBConfig.getConnection()) {
            DSLContext create = DSL.using(conn);
            FascicoloSanitarioRecord fascicolo = create.selectFrom(Tables.FASCICOLO_SANITARIO)
                    .where(Tables.FASCICOLO_SANITARIO.COD_FASCICOLO.eq(codFascicolo))
                    .fetchOne();

            final String paragrafoEsame = Routines.inserimentoEsame(create.configuration(), codFascicolo, numeroProblema,
                paragrafo, dataEsame, fascicolo.getCodProvincia(), fascicolo.getCodCitta_(),
                    fascicolo.getNumero(), fascicolo.getCodAnimale(), codTipiEsame.getFirst().trim());
            codTipiEsame.removeFirst();
            for (String codTipoEsame : codTipiEsame) {
                Routines.inserimentoSpecifica(create.configuration(), codFascicolo, numeroProblema, paragrafoEsame, codTipoEsame.trim());
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
