package it.unibo.adozione_animali.model.impl;

import java.util.Optional;

import it.unibo.adozione_animali.model.api.CaratteristicaPersonale;
import java.sql.Connection;
import org.jooq.DSLContext;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;
import nu.studer.sample.Tables;

public class CaratteristichePersonaliDAO implements CaratteristicaPersonale{

    @Override
    public boolean insertCaratteristicaPersonale(String provincia, String città, int numero, String animale,
            String tipo, Optional<String> codice) {
        try (Connection conn = DBConfig.getConnection()) {
            DSLContext ctx = DSL.using(conn, SQLDialect.MYSQL);
            int righeInserite = ctx.insertInto(Tables.CARATTERISTICA_PERSONALE)
                .set(Tables.CARATTERISTICA_PERSONALE.COD_PROVINCIA, provincia)
                .set(Tables.CARATTERISTICA_PERSONALE.COD_CITTA_, città)
                .set(Tables.CARATTERISTICA_PERSONALE.NUMERO, numero)
                .set(Tables.CARATTERISTICA_PERSONALE.COD_ANIMALE, animale)
                .set(Tables.CARATTERISTICA_PERSONALE.TIPO_CAR_PERSONALE, tipo)
                .set(Tables.CARATTERISTICA_PERSONALE.CODICE, codice.orElse(null))
                .execute();
            return righeInserite > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean ChangeCodiceCaratteristica(String provincia, String città, int numero, String animale, String tipo,
            String Newcodice) {
        try (Connection conn = DBConfig.getConnection()) {
            DSLContext ctx = DSL.using(conn, SQLDialect.MYSQL);
            int righeAggiornate = ctx.update(Tables.CARATTERISTICA_PERSONALE)
                .set(Tables.CARATTERISTICA_PERSONALE.CODICE, Newcodice)
                .where(Tables.CARATTERISTICA_PERSONALE.COD_PROVINCIA.eq(provincia)
                    .and(Tables.CARATTERISTICA_PERSONALE.COD_CITTA_.eq(città))
                    .and(Tables.CARATTERISTICA_PERSONALE.NUMERO.eq(numero))
                    .and(Tables.CARATTERISTICA_PERSONALE.COD_ANIMALE.eq(animale))
                    .and(Tables.CARATTERISTICA_PERSONALE.TIPO_CAR_PERSONALE.eq(tipo)))
                .execute();
            return righeAggiornate > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean deleteCaratteristicaPersonale(String provincia, String città, int numero, String animale,
            String tipo) {
        try (Connection conn = DBConfig.getConnection()) {
            DSLContext ctx = DSL.using(conn, SQLDialect.MYSQL);
            int righeCancellate = ctx.deleteFrom(Tables.CARATTERISTICA_PERSONALE)
                .where(Tables.CARATTERISTICA_PERSONALE.COD_PROVINCIA.eq(provincia)
                    .and(Tables.CARATTERISTICA_PERSONALE.COD_CITTA_.eq(città))
                    .and(Tables.CARATTERISTICA_PERSONALE.NUMERO.eq(numero))
                    .and(Tables.CARATTERISTICA_PERSONALE.COD_ANIMALE.eq(animale))
                    .and(Tables.CARATTERISTICA_PERSONALE.TIPO_CAR_PERSONALE.eq(tipo)))
                .execute();
            return righeCancellate > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

}
