package it.unibo.adozione_animali.model.impl.statistica;

import java.time.LocalDate;
import java.util.List;

import org.jooq.DSLContext;
import org.jooq.Query;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;

import it.unibo.adozione_animali.model.api.statistica.Statistica;
import it.unibo.adozione_animali.util.DBConfig;
import nu.studer.sample.Tables;
import java.sql.Connection;

public class StatisticaDAO implements Statistica{

    protected boolean insertStatistica(String codice, LocalDate data, String nome) {
        try (Connection conn = DBConfig.getConnection()) {
            DSLContext ctx = DSL.using(conn, SQLDialect.MYSQL);

            Query query = ctx.insertInto(Tables.STATISTICA)
                .set(Tables.STATISTICA.CODICE, codice)
                .set(Tables.STATISTICA.DATA_STATISTICA, data)
                .set(Tables.STATISTICA.NOME, nome);

            int righeInserite = query.execute();

            return righeInserite > 0;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean deleteStatistica(String codice, LocalDate data) {
        try (Connection conn = DBConfig.getConnection()) {
            DSLContext ctx = DSL.using(conn, SQLDialect.MYSQL);

            int righeCancellate = ctx.deleteFrom(Tables.STATISTICA)
                .where(Tables.STATISTICA.CODICE.eq(codice)
                    .and(Tables.STATISTICA.DATA_STATISTICA.eq(data)))
                .execute();
            return righeCancellate > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    //se esiste una statistica con quel nome e anno (Al massimo 1)
    public boolean exist(String name, int anno) {
        try (Connection conn = DBConfig.getConnection()) {
            DSLContext ctx = DSL.using(conn, SQLDialect.MYSQL);

            Integer count = ctx.selectCount()
                .from(Tables.STATISTICA)
                .where(Tables.STATISTICA.NOME.eq(name)
                    .and(DSL.year(Tables.STATISTICA.DATA_STATISTICA).eq(anno)))
                .fetchOne(0, Integer.class);

            return count != null && count > 0;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    //da il codice della statistica conoscendo il nome e anno
    public String getCodice(String name, int anno) {
        try (Connection conn = DBConfig.getConnection()) {
            DSLContext ctx = DSL.using(conn, SQLDialect.MYSQL);

            return ctx.select(Tables.STATISTICA.CODICE)
                .from(Tables.STATISTICA)
                .where(Tables.STATISTICA.NOME.eq(name)
                    .and(DSL.year(Tables.STATISTICA.DATA_STATISTICA).eq(anno)))
                .fetchOne(Tables.STATISTICA.CODICE);

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    //da la data della statistica conoscendo il nome e anno
    public LocalDate getData(String codice, int anno) {
        try (Connection conn = DBConfig.getConnection()) {
            DSLContext ctx = DSL.using(conn, SQLDialect.MYSQL);

            return ctx.select(Tables.STATISTICA.DATA_STATISTICA)
                .from(Tables.STATISTICA)
                .where(Tables.STATISTICA.CODICE.eq(codice)
                    .and(DSL.year(Tables.STATISTICA.DATA_STATISTICA).eq(anno)))
                .fetchOne(Tables.STATISTICA.DATA_STATISTICA);

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    //restituisce il primo codice libero guardando i codici per anno (ex 00000, 00002 -> out 00001)
    public String freeCode(int anno) {
        try (Connection conn = DBConfig.getConnection()) {
            DSLContext ctx = DSL.using(conn, SQLDialect.MYSQL);

            List<String> codici = ctx.select(Tables.STATISTICA.CODICE)
                .from(Tables.STATISTICA)
                .where(DSL.year(Tables.STATISTICA.DATA_STATISTICA).eq(anno))
                .orderBy(Tables.STATISTICA.CODICE.asc())
                .fetch(Tables.STATISTICA.CODICE);

            int code = 0;
            for (String c : codici) {
                int val = Integer.parseInt(c);
                if (val != code) break;
                code++;
            }

            return String.format("%05d", code); // formato "00000"
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    //fa update della data con input si strova la statistica dal nome e anno e si sostuisce con now
    public boolean updateDate(String name, int anno, LocalDate now) {
        try (Connection conn = DBConfig.getConnection()) {
            DSLContext ctx = DSL.using(conn, SQLDialect.MYSQL);

            int updated = ctx.update(Tables.STATISTICA)
                .set(Tables.STATISTICA.DATA_STATISTICA, now)
                .where(Tables.STATISTICA.NOME.eq(name)
                    .and(DSL.year(Tables.STATISTICA.DATA_STATISTICA).eq(anno)))
                .execute();

            return updated > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

}
