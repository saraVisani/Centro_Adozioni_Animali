package it.unibo.adozione_animali.model.impl;

import it.unibo.adozione_animali.model.api.Problema;
import nu.studer.sample.Routines;
import nu.studer.sample.Tables;
import org.jooq.DSLContext;
import org.jooq.impl.DSL;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Logger;
import it.unibo.adozione_animali.util.DBConfig;

public class ProblemaDAO implements Problema {

    Logger logger = Logger.getLogger("loggerProblema");

    @Override
    public void insertProblema(final int codFascicolo, final short numeroProblema, final String paragrafo,
                               final String nome, final String descrizione, final String tipoCurabile,
                               final String inCura, final String Area1, final String Area2, final int rCodFascicolo,
                               final short rNumeroProblema, final String rParagrafo, final int eCodFascicolo,
                               final short eNumeroProblema, final String eParagrafo, final String codProvincia,
                               final String codCitta, final int numero, final String codAnimale,
                               final List<String> codSintomi) {
        try (Connection conn = DBConfig.getConnection()) {
            DSLContext create = DSL.using(conn);
            Routines.inserimentoProblema(create.configuration(), codFascicolo, numeroProblema, paragrafo,
                    nome, descrizione, tipoCurabile, inCura, Area1, Area2, rCodFascicolo, rNumeroProblema, rParagrafo,
                    eCodFascicolo, eNumeroProblema, eParagrafo, codProvincia, codCitta, numero, codAnimale,
                    codSintomi.getFirst());
            codSintomi.removeFirst();
            for (String codSintomo : codSintomi) {
                Routines.inserimentoReferto(create.configuration(), codFascicolo, numeroProblema, paragrafo, codSintomo);
            }
        } catch (SQLException e) {
            this.logger.severe("La connessione non ha funzionato");
        }
    }

    @Override
    public void updateProblemaInCura(final int codFascicolo, final short numeroProblema, final String paragrafo,
                                     final String statoCura, final String codProvincia,
                                     final String codCitta, final int numero, final String codAnimale) {
        try (Connection conn = DBConfig.getConnection()) {
            DSLContext create = DSL.using(conn);
            Routines.updateInCura(create.configuration(), codFascicolo, numeroProblema, paragrafo, statoCura,
                    codProvincia, codCitta, numero, codAnimale);
        } catch (SQLException e) {
            this.logger.severe("La connessione non ha funzionato");
        }
    }

    @Override
    public void updateProblemaNome(final int codFascicolo, final short numeroProblema, final String paragrafo,
                                   final String nome) {
        try (Connection conn = DBConfig.getConnection()) {
            DSLContext create = DSL.using(conn);
            create.update(Tables.PROBLEMA)
                    .set(Tables.PROBLEMA.NOME, nome)
                    .where(Tables.PROBLEMA.COD_FASCICOLO.eq(codFascicolo))
                    .and(Tables.PROBLEMA.NUMERO.eq(numeroProblema))
                    .and(Tables.PROBLEMA.PARAGRAFO.eq(paragrafo))
                    .execute();
        } catch (SQLException e) {
            this.logger.severe("La connessione non ha funzionato");
        }
    }

    @Override
    public void updateProblemaDescrizione(final int codFascicolo, final short numeroProblema, final String paragrafo,
                                          final String descrizione) {
        try (Connection conn = DBConfig.getConnection()) {
            DSLContext create = DSL.using(conn);
            create.update(Tables.PROBLEMA)
                    .set(Tables.PROBLEMA.DESCRIZIONE, descrizione)
                    .where(Tables.PROBLEMA.COD_FASCICOLO.eq(codFascicolo))
                    .and(Tables.PROBLEMA.NUMERO.eq(numeroProblema))
                    .and(Tables.PROBLEMA.PARAGRAFO.eq(paragrafo))
                    .execute();
        } catch (SQLException e) {
            this.logger.severe("La connessione non ha funzionato");
        }
    }

    @Override
    public void updateRiferimentoRicovero(final int codFascicolo, final short numeroProblema, final String paragrafo,
                                          final int rCodFascicolo, final short rNumeroProblema, final String rParagrafo) {
        try (Connection conn = DBConfig.getConnection()) {
            DSLContext create = DSL.using(conn);
            create.update(Tables.PROBLEMA)
                    .set(Tables.PROBLEMA.R_COD_FASCICOLO, rCodFascicolo)
                    .set(Tables.PROBLEMA.R_NUMERO, rNumeroProblema)
                    .set(Tables.PROBLEMA.R_PARAGRAFO, rParagrafo)
                    .where(Tables.PROBLEMA.COD_FASCICOLO.eq(codFascicolo))
                    .and(Tables.PROBLEMA.NUMERO.eq(numeroProblema))
                    .and(Tables.PROBLEMA.PARAGRAFO.eq(paragrafo))
                    .execute();
        } catch (SQLException e) {
            this.logger.severe("La connessione non ha funzionato");
        }
    }

    @Override
    public void updateRiferimentoEsame(final int codFascicolo, final short numeroProblema, final String paragrafo,
                                       final int eCodFascicolo, final short eNumeroProblema, final String eParagrafo) {
        try (Connection conn = DBConfig.getConnection()) {
            DSLContext create = DSL.using(conn);
            create.update(Tables.PROBLEMA)
                    .set(Tables.PROBLEMA.E_COD_FASCICOLO, eCodFascicolo)
                    .set(Tables.PROBLEMA.E_NUMERO, eNumeroProblema)
                    .set(Tables.PROBLEMA.E_PARAGRAFO, eParagrafo)
                    .where(Tables.PROBLEMA.COD_FASCICOLO.eq(codFascicolo))
                    .and(Tables.PROBLEMA.NUMERO.eq(numeroProblema))
                    .and(Tables.PROBLEMA.PARAGRAFO.eq(paragrafo))
                    .execute();
        } catch (SQLException e) {
            this.logger.severe("La connessione non ha funzionato");
        }
    }

    @Override
    public void deleteProblema(final int codFascicolo, final short numeroProblema, final String paragrafo) {
        try (Connection conn = DBConfig.getConnection()) {
            DSLContext create = DSL.using(conn);
            Routines.rimozionePaginaFascicolo(create.configuration(), codFascicolo, numeroProblema, paragrafo);
        } catch (SQLException e) {
            this.logger.severe("La connessione non ha funzionato");
        }
    }
}
