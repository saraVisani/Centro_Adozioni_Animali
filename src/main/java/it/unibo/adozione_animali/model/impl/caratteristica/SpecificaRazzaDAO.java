package it.unibo.adozione_animali.model.impl.caratteristica;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import org.jooq.DSLContext;
import org.jooq.Query;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;
import it.unibo.adozione_animali.model.api.caratteristica.SpecificaRazza;
import it.unibo.adozione_animali.util.DBConfig;
import it.unibo.adozione_animali.util.ItemSelezionabile;
import nu.studer.sample.Tables;

public class SpecificaRazzaDAO implements SpecificaRazza {

    @Override
    public boolean insertSpecificaRazza(String codice, String tipo, String caratteristica) {
        try (Connection conn = DBConfig.getConnection()) {
            DSLContext ctx = DSL.using(conn, SQLDialect.MYSQL);

            Query query = ctx.insertInto(Tables.SPECIFICA_RAZZA)
                .set(Tables.SPECIFICA_RAZZA.CODICE, codice)
                .set(Tables.SPECIFICA_RAZZA.TIPO_CAR_GENERALE, tipo)
                .set(Tables.SPECIFICA_RAZZA.CARATTERISTICA, caratteristica);

            int righeInserite = query.execute();
            return righeInserite > 0;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean deleteSpecificaRazza(String codice) {
        try (Connection conn = DBConfig.getConnection()) {
            DSLContext ctx = DSL.using(conn, SQLDialect.MYSQL);

            this.checkSpecificaSpecie(codice);

            int righeCancellate = ctx.deleteFrom(Tables.SPECIFICA_RAZZA)
                .where(Tables.SPECIFICA_RAZZA.CODICE.eq(codice))
                .execute();
            return righeCancellate > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<ItemSelezionabile> getCodiciSpecifici(String provincia, String citta, String numero, String codAnimale,
            String tipo) {
        try (Connection conn = DBConfig.getConnection()) {
            if (provincia == null || citta == null || numero == null || codAnimale == null || tipo == null) {
                return List.of();
            }
            DSLContext ctx = DSL.using(conn, SQLDialect.MYSQL);
            return ctx.select(Tables.SPECIFICA_RAZZA.CODICE, Tables.SPECIFICA_RAZZA.CARATTERISTICA)
                    .from(Tables.SPECIFICA_RAZZA)
                    // filtro per tipo di codice coerente con tipo di caratteristica
                    .where(tipo.equals("Carattere")
                            ? Tables.SPECIFICA_RAZZA.TIPO_CAR_GENERALE.eq("Comportamento")
                            : Tables.SPECIFICA_RAZZA.TIPO_CAR_GENERALE.ne("Comportamento"))
                    // esclude codici già inseriti
                    .andNotExists(
                        ctx.selectOne()
                            .from(Tables.CARATTERISTICA_PERSONALE)
                            .where(Tables.CARATTERISTICA_PERSONALE.CODICE.eq(Tables.SPECIFICA_RAZZA.CODICE))
                            .and(Tables.CARATTERISTICA_PERSONALE.COD_PROVINCIA.eq(provincia))
                            .and(Tables.CARATTERISTICA_PERSONALE.COD_CITTA_.eq(citta))
                            .and(Tables.CARATTERISTICA_PERSONALE.NUMERO.eq(Integer.valueOf(numero)))
                            .and(Tables.CARATTERISTICA_PERSONALE.COD_ANIMALE.eq(codAnimale))
                            .and(Tables.CARATTERISTICA_PERSONALE.TIPO_CAR_PERSONALE.eq(tipo))
                    )
                    .groupBy(Tables.SPECIFICA_RAZZA.CODICE, Tables.SPECIFICA_RAZZA.CARATTERISTICA)
                    .orderBy(Tables.SPECIFICA_RAZZA.CARATTERISTICA.asc())
                    .fetch(record -> new ItemSelezionabile(
                        record.get(Tables.SPECIFICA_RAZZA.CODICE),
                        record.get(Tables.SPECIFICA_RAZZA.CARATTERISTICA)
                ));
        } catch (Exception e) {
            e.printStackTrace();
            return List.of(); // Return empty list if there is an error
        }
    }

    private void checkSpecificaSpecie(String codSpecifica) {
        try (Connection conn = DBConfig.getConnection()) {
            DSLContext ctx = DSL.using(conn, SQLDialect.MYSQL);

            boolean exists = ctx.fetchExists(
                    ctx.selectFrom(Tables.SPECIFICA_RAZZA)
                            .where(Tables.SPECIFICA_RAZZA.CODICE.eq(codSpecifica)));

            if (!exists) {
                throw new IllegalArgumentException();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
