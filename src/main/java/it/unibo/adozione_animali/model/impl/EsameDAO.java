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

public class EsameDAO implements Esame {

    final Logger logger = Logger.getLogger("EsameLogger");

    @Override
    public void insertEsame(final int cod_fascicolo, final short numero_problema, final String paragrafo,
                            final LocalDate data_esame, final String cod_provincia, final String cod_citta,
                            int numero, String cod_animale, List<String> cod_tipi_esame) {
        try (Connection conn = DBConfig.getConnection()) {
            DSLContext create = DSL.using(conn);
            final String paragrafo_esame = Routines.inserimentoEsame(create.configuration(), cod_fascicolo, numero_problema,
                paragrafo, null, data_esame, cod_provincia, cod_citta, numero,
                    cod_animale, cod_tipi_esame.getFirst());
            cod_tipi_esame.removeFirst();
            for (String cod_tipo_esame : cod_tipi_esame) {
                Routines.inserimentoSpecifica(create.configuration(), cod_fascicolo, numero_problema, paragrafo_esame, cod_tipo_esame);
            }
        } catch (SQLException e) {
            this.logger.severe("La connessione non ha funzionato");
        }
    }

            @Override
    public void updateEsame(int cod_fascicolo, short numero_problema, String paragrafo, LocalDate data_esame) {
            try (Connection conn = DBConfig.getConnection()) {
                DSLContext create = DSL.using(conn);
                create.update(Tables.ESAME)
                        .set(Tables.ESAME.DATA_ESAME, data_esame)
                        .where(Tables.ESAME.COD_FASCICOLO.eq(cod_fascicolo))
                        .and(Tables.ESAME.NUMERO.eq(numero_problema))
                        .and(Tables.ESAME.PARAGRAFO.eq(paragrafo))
                        .execute();
            } catch (SQLException e) {
                this.logger.severe("La connessione non ha funzionato");
            }
    }

    @Override
    public void deleteEsame(int cod_fascicolo, short numero_problema, String paragrafo) {
        try (Connection conn = DBConfig.getConnection()) {
            DSLContext create = DSL.using(conn);
            Routines.rimozionePaginaFascicolo(create.configuration(), cod_fascicolo, numero_problema, paragrafo);
        } catch (SQLException e) {
            this.logger.severe("La connessione non ha funzionato");
        }
    }
}
