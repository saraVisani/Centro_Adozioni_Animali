package it.unibo.adozione_animali.model.impl;

import it.unibo.adozione_animali.model.api.Pagina;
import it.unibo.adozione_animali.util.DBConfig;
import nu.studer.sample.Routines;
import nu.studer.sample.Tables;
import nu.studer.sample.tables.records.EsameRecord;
import nu.studer.sample.tables.records.PaginaFascicoloSanitarioRecord;
import nu.studer.sample.tables.records.ProblemaRecord;
import nu.studer.sample.tables.records.RicoveroRecord;
import org.jooq.DSLContext;
import org.jooq.Record;
import org.jooq.Result;
import org.jooq.SelectConditionStep;
import org.jooq.conf.ParamType;
import org.jooq.impl.DSL;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Logger;

public class PaginaDAO implements Pagina {

    final Logger logger = Logger.getLogger("loggerPagina");

    @Override
    public void deletePaginaEffettiva(final int codFascicolo, final short numeroPagina) {
        try (Connection conn = DBConfig.getConnection()) {
            DSLContext create = DSL.using(conn);
            var query = create.select(Tables.PAGINA_FASCICOLO_SANITARIO)
                    .where(Tables.PAGINA_FASCICOLO_SANITARIO.COD_FASCICOLO.eq(codFascicolo))
                    .and(Tables.PAGINA_FASCICOLO_SANITARIO.NUMERO.eq(numeroPagina));

            System.out.println(query.getSQL(ParamType.INLINED));

            create.selectFrom(Tables.PAGINA_FASCICOLO_SANITARIO)
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

    @Override
    public void deleteSinglePar(final int codFascicolo, final short numeroPagina, final String paragrafo) {
        try (Connection conn = DBConfig.getConnection()) {
            DSLContext create = DSL.using(conn);

            Routines.rimozionePaginaFascicolo(
                    create.configuration(), codFascicolo, numeroPagina, paragrafo);
        } catch (SQLException e) {
            this.logger.severe("La connessione non ha funzionato");
        }
    }

    public List<ProblemaRecord> getProblemsFasc(final int codFascicolo, final Short numeroPagina,
                                                             final String paragrafo) {
        Result<ProblemaRecord> pages = null;
        try (Connection conn = DBConfig.getConnection()) {
            DSLContext create = DSL.using(conn);

            pages = create.selectFrom(Tables.PROBLEMA)
                    .fetch();


        } catch (SQLException e) {
            this.logger.severe("La connessione non ha funzionato");
        }
        if (paragrafo != null) {
            return pages.stream()
                    .filter(i -> i.get(Tables.PROBLEMA.COD_FASCICOLO)
                            .compareTo(codFascicolo) == 0)
                    .filter(i -> i.get(Tables.PROBLEMA.NUMERO).compareTo(numeroPagina) == 0)
                    .filter(i -> i.get(Tables.PROBLEMA.PARAGRAFO).compareTo(paragrafo) == 0)
                    .toList();
        } else if (numeroPagina != null) {
            return pages.stream()
                    .filter(i -> i.get(Tables.PROBLEMA.COD_FASCICOLO)
                            .compareTo(codFascicolo) == 0)
                    .filter(i -> i.get(Tables.PROBLEMA.NUMERO).compareTo(numeroPagina) == 0)
                    .toList();
        } else {
            return pages.stream()
                    .filter(i -> i.get(Tables.PROBLEMA.COD_FASCICOLO)
                            .compareTo(codFascicolo) == 0)
                    .toList();
        }
    }

    public List<EsameRecord> getExamFasc(final int codFascicolo, final Short numeroPagina,
                                         final String paragrafo) {
        Result<EsameRecord> pages = null;
        try (Connection conn = DBConfig.getConnection()) {
            DSLContext create = DSL.using(conn);

            pages = create.selectFrom(Tables.ESAME)
                    .fetch();


        } catch (SQLException e) {
            this.logger.severe("La connessione non ha funzionato");
        }
        if (paragrafo != null) {
            return pages.stream()
                    .filter(i -> i.get(Tables.ESAME.COD_FASCICOLO)
                            .compareTo(codFascicolo) == 0)
                    .filter(i -> i.get(Tables.ESAME.NUMERO).compareTo(numeroPagina) == 0)
                    .filter(i -> i.get(Tables.ESAME.PARAGRAFO).compareTo(paragrafo) == 0)
                    .toList();
        } else if (numeroPagina != null) {
            return pages.stream()
                    .filter(i -> i.get(Tables.ESAME.COD_FASCICOLO)
                            .compareTo(codFascicolo) == 0)
                    .filter(i -> i.get(Tables.ESAME.NUMERO).compareTo(numeroPagina) == 0)
                    .toList();
        } else {
            return pages.stream()
                    .filter(i -> i.get(Tables.ESAME.COD_FASCICOLO)
                            .compareTo(codFascicolo) == 0)
                    .toList();
        }
    }

    public List<RicoveroRecord> getRicovFasc(final int codFascicolo, final Short numeroPagina,
                                                final String paragrafo) {
        Result<RicoveroRecord> pages = null;
        try (Connection conn = DBConfig.getConnection()) {
            DSLContext create = DSL.using(conn);

            pages = create.selectFrom(Tables.RICOVERO)
                    .fetch();


        } catch (SQLException e) {
            this.logger.severe("La connessione non ha funzionato");
        }
        if (paragrafo != null) {
            return pages.stream()
                    .filter(i -> i.get(Tables.RICOVERO.COD_FASCICOLO)
                            .compareTo(codFascicolo) == 0)
                    .filter(i -> i.get(Tables.RICOVERO.NUMERO).compareTo(numeroPagina) == 0)
                    .filter(i -> i.get(Tables.RICOVERO.PARAGRAFO).compareTo(paragrafo) == 0)
                    .toList();
        } else if (numeroPagina != null) {
            return pages.stream()
                    .filter(i -> i.get(Tables.RICOVERO.COD_FASCICOLO)
                            .compareTo(codFascicolo) == 0)
                    .filter(i -> i.get(Tables.RICOVERO.NUMERO).compareTo(numeroPagina) == 0)
                    .toList();
        } else {
            return pages.stream()
                    .filter(i -> i.get(Tables.RICOVERO.COD_FASCICOLO)
                            .compareTo(codFascicolo) == 0)
                    .toList();
        }
    }
}
