package it.unibo.adozione_animali.model.impl.indirizzo;

import java.util.List;
import java.util.Optional;

import java.sql.Connection;

import org.jooq.DSLContext;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;

import it.unibo.adozione_animali.model.api.indirizzo.Indirizzo;
import it.unibo.adozione_animali.util.DBConfig;
import it.unibo.adozione_animali.util.ItemSelezionabile;
import nu.studer.sample.Tables;

public class IndirizzoDAO implements Indirizzo {

    @Override
    public boolean insertIndirizzo(String provincia, String città, int numero, String via, Optional<String> CF) {
        try (Connection conn = DBConfig.getConnection()) {
            DSLContext ctx = DSL.using(conn, SQLDialect.MYSQL);

            int insertedRows = ctx.insertInto(Tables.INDIRIZZO)
                    .set(Tables.INDIRIZZO.COD_PROVINCIA, provincia)
                    .set(Tables.INDIRIZZO.COD_CITTA_, città)
                    .set(Tables.INDIRIZZO.NUMERO, numero)
                    .set(Tables.INDIRIZZO.VIA, via)
                    .set(Tables.INDIRIZZO.CF, CF.orElse(null))
                    .execute();

            return insertedRows > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean updateIndirizzoVia(String provincia, String città, int numero, String via) {
        try (Connection conn = DBConfig.getConnection()) {
            DSLContext ctx = DSL.using(conn, SQLDialect.MYSQL);

            int updatedRows = ctx.update(Tables.INDIRIZZO)
                    .set(Tables.INDIRIZZO.VIA, via)
                    .where(Tables.INDIRIZZO.COD_PROVINCIA.eq(provincia)
                            .and(Tables.INDIRIZZO.COD_CITTA_.eq(città))
                            .and(Tables.INDIRIZZO.NUMERO.eq(numero)))
                    .execute();

            return updatedRows > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean updateIndirizzoCittà(String provincia, String città, int numero, String NewCittà) {
        try (Connection conn = DBConfig.getConnection()) {
            DSLContext ctx = DSL.using(conn, SQLDialect.MYSQL);

            int updatedRows = ctx.update(Tables.INDIRIZZO)
                    .set(Tables.INDIRIZZO.COD_CITTA_, NewCittà)
                    .where(Tables.INDIRIZZO.COD_PROVINCIA.eq(provincia)
                            .and(Tables.INDIRIZZO.COD_CITTA_.eq(città))
                            .and(Tables.INDIRIZZO.NUMERO.eq(numero)))
                    .execute();

            return updatedRows > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean updateIndirizzoNumero(String provincia, String città, int numero, int NewNumero) {
        try (Connection conn = DBConfig.getConnection()) {
            DSLContext ctx = DSL.using(conn, SQLDialect.MYSQL);

            int updatedRows = ctx.update(Tables.INDIRIZZO)
                    .set(Tables.INDIRIZZO.NUMERO, NewNumero)
                    .where(Tables.INDIRIZZO.COD_PROVINCIA.eq(provincia)
                            .and(Tables.INDIRIZZO.COD_CITTA_.eq(città))
                            .and(Tables.INDIRIZZO.NUMERO.eq(numero)))
                    .execute();

            return updatedRows > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean updateIndirizzoProvincia(String provincia, String città, int numero, String NewProvincia) {
        try (Connection conn = DBConfig.getConnection()) {
            DSLContext ctx = DSL.using(conn, SQLDialect.MYSQL);

            int updatedRows = ctx.update(Tables.INDIRIZZO)
                    .set(Tables.INDIRIZZO.COD_PROVINCIA, NewProvincia)
                    .where(Tables.INDIRIZZO.COD_PROVINCIA.eq(provincia)
                            .and(Tables.INDIRIZZO.COD_CITTA_.eq(città))
                            .and(Tables.INDIRIZZO.NUMERO.eq(numero)))
                    .execute();

            return updatedRows > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean updateIndirizzoCF(String provincia, String città, int numero, String NewCF) {
        try (Connection conn = DBConfig.getConnection()) {
            DSLContext ctx = DSL.using(conn, SQLDialect.MYSQL);

            int updatedRows = ctx.update(Tables.INDIRIZZO)
                    .set(Tables.INDIRIZZO.CF, NewCF)
                    .where(Tables.INDIRIZZO.COD_PROVINCIA.eq(provincia)
                            .and(Tables.INDIRIZZO.COD_CITTA_.eq(città))
                            .and(Tables.INDIRIZZO.NUMERO.eq(numero)))
                            .and(Tables.INDIRIZZO.CF.isNotNull())
                    .execute();

            return updatedRows > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean deleteIndirizzo(String provincia, String città, int numero) {
        try (Connection conn = DBConfig.getConnection()) {
            DSLContext ctx = DSL.using(conn, SQLDialect.MYSQL);

            int deletedRows = ctx.deleteFrom(Tables.INDIRIZZO)
                    .where(Tables.INDIRIZZO.COD_PROVINCIA.eq(provincia)
                            .and(Tables.INDIRIZZO.COD_CITTA_.eq(città))
                            .and(Tables.INDIRIZZO.NUMERO.eq(numero)))
                    .execute();

            return deletedRows > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<String> getNumeriByCitta(String provincia, String citta) {
        try (Connection conn = DBConfig.getConnection()) {
            DSLContext ctx = DSL.using(conn, SQLDialect.MYSQL);
            return ctx.select(Tables.INDIRIZZO.NUMERO)
                        .from(Tables.INDIRIZZO)
                        .where(Tables.INDIRIZZO.COD_PROVINCIA.eq(provincia)
                            .and(Tables.INDIRIZZO.COD_CITTA_.eq(citta)))
                        .orderBy(Tables.INDIRIZZO.NUMERO.asc())
                        .fetchInto(String.class);
        } catch (Exception e) {
            e.printStackTrace();
            return List.of(); // Return empty list if there is an error
        }
    }

    public List<ItemSelezionabile> getProvince(String cf) {
        try (Connection conn = DBConfig.getConnection()) {
            DSLContext ctx = DSL.using(conn, SQLDialect.MYSQL);

            return ctx.selectDistinct(
                        Tables.PROVINCIA.COD_PROVINCIA,
                        Tables.PROVINCIA.NOME
                )
                .from(Tables.INDIRIZZO)
                .join(Tables.PROVINCIA)
                    .on(Tables.INDIRIZZO.COD_PROVINCIA.eq(Tables.PROVINCIA.COD_PROVINCIA))
                .where(Tables.INDIRIZZO.CF.eq(cf))
                .orderBy(Tables.PROVINCIA.NOME.asc())
                .fetch(record -> new ItemSelezionabile(
                        record.get(Tables.PROVINCIA.COD_PROVINCIA),
                        record.get(Tables.PROVINCIA.NOME)
                ));
        } catch (Exception e) {
            e.printStackTrace();
            return List.of();
        }
    }

    public List<String> getNumeriByPersona(String provinciaPer, String cittaPer, String cf) {
        try (Connection conn = DBConfig.getConnection()) {
            DSLContext ctx = DSL.using(conn, SQLDialect.MYSQL);

            return ctx.select(Tables.INDIRIZZO.NUMERO)
                    .from(Tables.INDIRIZZO)
                    .where(Tables.INDIRIZZO.COD_PROVINCIA.eq(provinciaPer)
                            .and(Tables.INDIRIZZO.COD_CITTA_.eq(cittaPer))
                            .and(Tables.INDIRIZZO.CF.eq(cf)))
                    .orderBy(Tables.INDIRIZZO.NUMERO.asc())
                    .fetchInto(String.class);
        } catch (Exception e) {
            e.printStackTrace();
            return List.of();
        }
    }

    public List<ItemSelezionabile> getCittaByPersona(String provinciaPer, String cf) {
        try (Connection conn = DBConfig.getConnection()) {
            DSLContext ctx = DSL.using(conn, SQLDialect.MYSQL);

            return ctx.selectDistinct(
                        Tables.CITTA_.COD_CITTA_,
                        Tables.CITTA_.NOME
                )
                .from(Tables.INDIRIZZO)
                .join(Tables.CITTA_)
                    .on(Tables.INDIRIZZO.COD_CITTA_.eq(Tables.CITTA_.COD_CITTA_)
                        .and(Tables.INDIRIZZO.COD_PROVINCIA.eq(Tables.CITTA_.COD_PROVINCIA)))
                .where(Tables.INDIRIZZO.COD_PROVINCIA.eq(provinciaPer)
                        .and(Tables.INDIRIZZO.CF.eq(cf)))
                .orderBy(Tables.CITTA_.NOME.asc())
                .fetch(record -> new ItemSelezionabile(
                        record.get(Tables.CITTA_.COD_CITTA_),
                        record.get(Tables.CITTA_.NOME)
                ));
        } catch (Exception e) {
            e.printStackTrace();
            return List.of();
        }
    }

}
