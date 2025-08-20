package it.unibo.adozione_animali.model.impl.spazio;

import java.sql.Connection;

import org.jooq.DSLContext;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;

import it.unibo.adozione_animali.model.api.spazio.Spazio;
import it.unibo.adozione_animali.util.DBConfig;
import nu.studer.sample.Tables;
import java.math.BigDecimal;

public class SpazioDAO implements Spazio{

    @Override
    public boolean insertSpazio(int spazio, String tipo, float dimensione) {
        try (Connection conn = DBConfig.getConnection()){
            DSLContext ctx = DSL.using(conn, SQLDialect.MYSQL);

            int righeInserite = ctx.insertInto(Tables.SPAZIO)
                .set(Tables.SPAZIO.ID_SPAZIO, spazio)
                .set(Tables.SPAZIO.TIPO_SPAZIO, tipo)
                .set(Tables.SPAZIO.DIMENSIONE, BigDecimal.valueOf(dimensione))
                .execute();

            return righeInserite > 0;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean updateSpazioTipo(int spazio, String newTipo) {
        try (Connection conn = DBConfig.getConnection()) {
            DSLContext ctx = DSL.using(conn, SQLDialect.MYSQL);

            int righeAggiornate = ctx.update(Tables.SPAZIO)
                .set(Tables.SPAZIO.TIPO_SPAZIO, newTipo)
                .where(Tables.SPAZIO.ID_SPAZIO.eq(spazio))
                .execute();

            return righeAggiornate > 0;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean updateSpazioDimensione(int spazio, float newDimensione) {
        try (Connection conn = DBConfig.getConnection()) {
            DSLContext ctx = DSL.using(conn, SQLDialect.MYSQL);

            return ctx.transactionResult(configuration -> {
                DSLContext tCtx = DSL.using(configuration);

                //Recupero dimensione attuale dello spazio
                var spazioRecord = tCtx.select(Tables.SPAZIO.DIMENSIONE)
                    .from(Tables.SPAZIO)
                    .where(Tables.SPAZIO.ID_SPAZIO.eq(spazio))
                    .fetchOne();

                if (spazioRecord == null) {
                    return false; // lo spazio non esiste
                }

                int oldDimensione = spazioRecord.get(Tables.SPAZIO.DIMENSIONE).intValue();
                float differenza = newDimensione - oldDimensione;

                //Aggiorno la dimensione dello spazio
                int righeSpazio = tCtx.update(Tables.SPAZIO)
                    .set(Tables.SPAZIO.DIMENSIONE, BigDecimal.valueOf(newDimensione))
                    .where(Tables.SPAZIO.ID_SPAZIO.eq(spazio))
                    .execute();

                if (righeSpazio == 0) {
                    throw new RuntimeException("Aggiornamento SPAZIO fallito, rollback");
                }

                //Aggiorno lo spazio rimanente in tutte le relazioni SPAZIO_PERSONA
                if (differenza != 0) {
                    tCtx.update(Tables.SPAZIO_PERSONA)
                        .set(Tables.SPAZIO_PERSONA.SPAZIO_RIMANENTE,
                            Tables.SPAZIO_PERSONA.SPAZIO_RIMANENTE.plus(differenza))
                        .where(Tables.SPAZIO_PERSONA.ID_SPAZIO.eq(spazio))
                        .execute();
                }

                return true;
            });

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean deleteSpazio(int spazio) {
        try (Connection conn = DBConfig.getConnection()) {
            DSLContext ctx = DSL.using(conn, SQLDialect.MYSQL);

            int righeSpazio = ctx.deleteFrom(Tables.SPAZIO)
                .where(Tables.SPAZIO.ID_SPAZIO.eq(spazio))
                .execute();

            return righeSpazio > 0;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

}
