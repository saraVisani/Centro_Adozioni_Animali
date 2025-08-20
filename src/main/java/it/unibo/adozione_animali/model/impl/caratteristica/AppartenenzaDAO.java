package it.unibo.adozione_animali.model.impl.caratteristica;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.jooq.DSLContext;
import org.jooq.InsertSetMoreStep;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;

import it.unibo.adozione_animali.model.api.caratteristica.Appartenenza;
import it.unibo.adozione_animali.util.DBConfig;
import nu.studer.sample.Tables;

public class AppartenenzaDAO implements Appartenenza {

    @Override
    public boolean insertAppartenenza(List<String> codice, String provincia, String città, int numero, String animale,
            String tipo) {
        try (Connection conn = DBConfig.getConnection()) {
            DSLContext ctx = DSL.using(conn, SQLDialect.MYSQL);

            List<InsertSetMoreStep<?>> insertQueries = new ArrayList<>();

            for (String cod : codice) {
                insertQueries.add(
                    ctx.insertInto(Tables.APPARTENENZA)
                    .set(Tables.APPARTENENZA.CODICE, cod)
                    .set(Tables.APPARTENENZA.COD_PROVINCIA, provincia)
                    .set(Tables.APPARTENENZA.COD_CITTA_, città)
                    .set(Tables.APPARTENENZA.NUMERO, numero)
                    .set(Tables.APPARTENENZA.COD_ANIMALE, animale)
                    .set(Tables.APPARTENENZA.TIPO_CAR_PERSONALE, tipo)
                );
            }

            int[] result = ctx.batch(insertQueries).execute();
            return Arrays.stream(result).sum() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean changeCodiceAppartenenza(String codice, String provincia, String città, int numero, String animale,
            String tipo, String newCodice) {
        try (Connection conn = DBConfig.getConnection()) {
            DSLContext ctx = DSL.using(conn, SQLDialect.MYSQL);
            int righeAggiornate = ctx.update(Tables.APPARTENENZA)
                .set(Tables.APPARTENENZA.CODICE, newCodice)
                .where(Tables.APPARTENENZA.CODICE.eq(codice)
                    .and(Tables.APPARTENENZA.COD_PROVINCIA.eq(provincia))
                    .and(Tables.APPARTENENZA.COD_CITTA_.eq(città))
                    .and(Tables.APPARTENENZA.NUMERO.eq(numero))
                    .and(Tables.APPARTENENZA.COD_ANIMALE.eq(animale))
                    .and(Tables.APPARTENENZA.TIPO_CAR_PERSONALE.eq(tipo)))
                .execute();
            return righeAggiornate > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean deleteAppartenenza(String codice, String provincia, String città, int numero, String animale,
            String tipo) {
        try (Connection conn = DBConfig.getConnection()) {
            DSLContext ctx = DSL.using(conn, SQLDialect.MYSQL);
            int righeCancellate = ctx.deleteFrom(Tables.APPARTENENZA)
                .where(Tables.APPARTENENZA.CODICE.eq(codice)
                    .and(Tables.APPARTENENZA.COD_PROVINCIA.eq(provincia))
                    .and(Tables.APPARTENENZA.COD_CITTA_.eq(città))
                    .and(Tables.APPARTENENZA.NUMERO.eq(numero))
                    .and(Tables.APPARTENENZA.COD_ANIMALE.eq(animale))
                    .and(Tables.APPARTENENZA.TIPO_CAR_PERSONALE.eq(tipo)))
                .execute();
            return righeCancellate > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

}
