package it.unibo.adozione_animali.model.impl;

import it.unibo.adozione_animali.model.api.Pagina;
import it.unibo.adozione_animali.util.DBConfig;
import nu.studer.sample.Routines;
import nu.studer.sample.Tables;
import org.jooq.DSLContext;
import org.jooq.impl.DSL;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.logging.Logger;

public class PaginaDAO implements Pagina {

    final Logger logger = Logger.getLogger("loggerPagina");

    @Override
    public void deletePaginaEffettiva(final int codFascicolo, final short numeroPagina) {
        try (Connection conn = DBConfig.getConnection()) {
            DSLContext create = DSL.using(conn);
            create.select(Tables.PAGINA_FASCICOLO_SANITARIO)
                    .where(Tables.PAGINA_FASCICOLO_SANITARIO.COD_FASCICOLO.eq(codFascicolo))
                    .and(Tables.PAGINA_FASCICOLO_SANITARIO.NUMERO.eq(numeroPagina))
                    .fetch()
                    .stream()
                    .filter(pagina -> {
                        byte b = Routines.checkPaginaProblema(
                                create.configuration(),
                                pagina.get(Tables.PAGINA_FASCICOLO_SANITARIO.COD_FASCICOLO),
                                pagina.get(Tables.PAGINA_FASCICOLO_SANITARIO.NUMERO),
                                pagina.get(Tables.PAGINA_FASCICOLO_SANITARIO.PARAGRAFO)
                        );
                        return b != 0;
                        }
                    )
                    .forEach(pagina -> {
                        Routines.rimozionePaginaFascicolo(
                                create.configuration(), pagina.get(Tables.PAGINA_FASCICOLO_SANITARIO.COD_FASCICOLO),
                                pagina.get(Tables.PAGINA_FASCICOLO_SANITARIO.NUMERO),
                                pagina.get(Tables.PAGINA_FASCICOLO_SANITARIO.PARAGRAFO));

                    });
        } catch (SQLException e) {
            this.logger.severe("La connessione non ha funzionato");
        }
    }
}
