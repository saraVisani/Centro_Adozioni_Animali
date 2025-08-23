package it.unibo.adozione_animali.model.impl;

import it.unibo.adozione_animali.model.api.Problema;
import nu.studer.sample.Routines;
import nu.studer.sample.Tables;
import nu.studer.sample.routines.InserimentoProblema;
import nu.studer.sample.tables.records.FascicoloSanitarioRecord;
import org.jooq.DSLContext;
import org.jooq.impl.DSL;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Logger;
import it.unibo.adozione_animali.util.DBConfig;

public class ProblemaDAO implements Problema {

    final Logger logger = Logger.getLogger("loggerProblema");

    @Override
    public String insertProblema(final Integer codFascicolo, final Short numeroProblema, final String paragrafo,
                               final String nome, final String descrizione, final String tipoCurabile,
                               final String inCura, final String Area1, final String Area2, final Integer rCodFascicolo,
                               final Short rNumeroProblema, final String rParagrafo, final Integer eCodFascicolo,
                               final Short eNumeroProblema, final String eParagrafo, final String codProvincia,
                               final String codCitta, final Integer numero, final String codAnimale,
                               final List<String> codSintomi) {

        FascicoloSanitarioRecord fascicolo = null;
        try (Connection conn = DBConfig.getConnection()) {
            DSLContext create = DSL.using(conn);

            InserimentoProblema routine = new InserimentoProblema();

            routine.setCodFascicolo(codFascicolo);
            routine.setNumero(numeroProblema);
            routine.setParagrafo(paragrafo);
            routine.setNome(nome);
            routine.setDescrizione(descrizione);
            routine.setTipoCurabile(tipoCurabile);
            routine.setInCura(inCura);
            routine.setArea1(Area1);
            routine.setArea2(Area2);
            routine.setRNumero(rNumeroProblema);
            routine.setRParagrafo(rParagrafo);
            routine.setENumero(eNumeroProblema);
            routine.setEParagrafo(eParagrafo);
            routine.setCodProvincia(codProvincia);
            routine.setCodCitta_(codCitta);
            routine.setCodAnimale(codAnimale);
            routine.setCodSintomo(codSintomi.getFirst().trim());

            routine.execute(create.configuration());

            Integer codFascicoloAggiornato = routine.getCodFascicolo();
            Short numeroAggiornato = routine.getNumero();
            String paragrafoAggiornato = routine.getParagrafo();

            codSintomi.removeFirst();

            if (codProvincia == null) {
                fascicolo = create.selectFrom(Tables.FASCICOLO_SANITARIO)
                        .where(Tables.FASCICOLO_SANITARIO.COD_FASCICOLO.eq(codFascicolo))
                        .fetchOne();
            } else {
                fascicolo = create.selectFrom(Tables.FASCICOLO_SANITARIO)
                        .where(Tables.FASCICOLO_SANITARIO.COD_PROVINCIA.eq(codProvincia))
                        .and(Tables.FASCICOLO_SANITARIO.COD_CITTA_.eq(codCitta))
                        .and(Tables.FASCICOLO_SANITARIO.NUMERO.eq(numero))
                        .and(Tables.FASCICOLO_SANITARIO.COD_ANIMALE.eq(codAnimale))
                        .fetchOne();
            }

            for (String codSintomo : codSintomi) {
                Routines.inserimentoReferto(create.configuration(), codFascicoloAggiornato, numeroAggiornato, paragrafoAggiornato,
                        codSintomo.trim());
            }

        } catch (SQLException e) {
            this.logger.severe("La connessione non ha funzionato");
        }
        return String.valueOf(fascicolo.getCodFascicolo());
    }

    @Override
    public void updateProblemaInCura(final int codFascicolo, final short numeroProblema, final String paragrafo,
                                     final String statoCura) {
        try (Connection conn = DBConfig.getConnection()) {
            DSLContext create = DSL.using(conn);
            FascicoloSanitarioRecord fascicolo = create.selectFrom(Tables.FASCICOLO_SANITARIO)
                            .where(Tables.FASCICOLO_SANITARIO.COD_FASCICOLO.eq(codFascicolo))
                            .fetchOne();
            Routines.updateInCura(create.configuration(), codFascicolo, numeroProblema, paragrafo, statoCura.toUpperCase(),
                    fascicolo.getCodProvincia(), fascicolo.getCodCitta_(), fascicolo.getNumero(),
                    fascicolo.getCodAnimale());
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
