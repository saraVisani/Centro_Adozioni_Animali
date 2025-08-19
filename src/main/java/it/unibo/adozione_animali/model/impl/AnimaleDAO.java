package it.unibo.adozione_animali.model.impl;

import java.math.BigDecimal;
import java.sql.Connection;
import java.time.LocalDate;
import java.util.Optional;

import org.jooq.DSLContext;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;

import it.unibo.adozione_animali.model.api.Animale;
import nu.studer.sample.Tables;

public class AnimaleDAO implements Animale {

    @Override
    public boolean insertAnimale(String provincia, String città, int numero, String animale, String specie,
            Optional<String> nome, String razza, String prov_ritr, String cit_ritr, int num_ritr, String stato_ritr,
            LocalDate data_nascita, LocalDate data_inserimento, Optional<LocalDate> data_addozione,
            Optional<String> prov_att, Optional<String> cit_att, Optional<Integer> num_att,
            Optional<String> prov_centro, Optional<String> cit_centro, Optional<Integer> num_centro,
            Optional<String> statoAttuale, boolean idonieta, float peso, float altezza) {
        try (Connection conn = DBConfig.getConnection()) {
            DSLContext ctx = DSL.using(conn, SQLDialect.MYSQL);
            int insert = ctx.insertInto(Tables.ANIMALE)
                .set(Tables.ANIMALE.COD_PROVINCIA, provincia)
                .set(Tables.ANIMALE.COD_CITTA_, città)
                .set(Tables.ANIMALE.NUMERO, numero)
                .set(Tables.ANIMALE.COD_ANIMALE, animale)
                .set(Tables.ANIMALE.COD_SPECIE, specie)
                .set(Tables.ANIMALE.NOME, nome.orElse(null))
                .set(Tables.ANIMALE.NOME_RAZZA, razza)
                .set(Tables.ANIMALE.COD_PROVINCIA_RITROVAMENTO, prov_ritr)
                .set(Tables.ANIMALE.COD_CITTA_RITROVAMENTO, cit_ritr)
                .set(Tables.ANIMALE.NUMERO_RITROVAMENTO, num_ritr)
                .set(Tables.ANIMALE.STATO_RITROVAMENTO, stato_ritr)
                .set(Tables.ANIMALE.DATA_NASCITA, data_nascita)
                .set(Tables.ANIMALE.DATA_INSERIMENTO, data_inserimento)
                .set(Tables.ANIMALE.DATA_ADOZIONE, data_addozione.orElse(null))
                .set(Tables.ANIMALE.COD_PROVINCIA_RESIDENZA_ATT, prov_att.orElse(null))
                .set(Tables.ANIMALE.COD_CITTA_RESIDENZA_ATT, cit_att.orElse(null))
                .set(Tables.ANIMALE.NUMERO_RESIDENZA_ATT, num_att.orElse(null))
                .set(Tables.ANIMALE.COD_PROVINCIA_CENTRO, prov_centro.orElse(null))
                .set(Tables.ANIMALE.COD_CITTA_CENTRO, cit_centro.orElse(null))
                .set(Tables.ANIMALE.NUMERO_CENTRO, num_centro.orElse(null))
                .set(Tables.ANIMALE.STATO_ATTUALE, statoAttuale.orElse(null))
                .set(Tables.ANIMALE.IDONIETA_ANIMALE, (byte) (idonieta ? 1 : 0))
                .set(Tables.ANIMALE.PESO, BigDecimal.valueOf(peso))
                .set(Tables.ANIMALE.ALTEZZA, BigDecimal.valueOf(altezza))
                .execute();
            return insert > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false; // Return false if there is an error
        }
    }

    @Override
    public boolean updateAnimaleNome(String provincia, String città, int numero, String animale, String newNome) {
        try (Connection conn = DBConfig.getConnection()) {
            DSLContext ctx = DSL.using(conn, SQLDialect.MYSQL);
            int righeAggiornate = ctx.update(Tables.ANIMALE)
                .set(Tables.ANIMALE.NOME, newNome)
                .where(Tables.ANIMALE.COD_PROVINCIA.eq(provincia)
                    .and(Tables.ANIMALE.COD_CITTA_.eq(città))
                    .and(Tables.ANIMALE.NUMERO.eq(numero))
                    .and(Tables.ANIMALE.COD_ANIMALE.eq(animale)))
                .execute();
            return righeAggiornate > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false; // Return false if there is an error
        }
    }

    @Override
    public boolean updateAnimaleAltezza(String provincia, String città, int numero, String animale, float newAltezza) {
        try (Connection conn = DBConfig.getConnection()) {
            DSLContext ctx = DSL.using(conn, SQLDialect.MYSQL);
            int righeAggiornate = ctx.update(Tables.ANIMALE)
                .set(Tables.ANIMALE.ALTEZZA, BigDecimal.valueOf(newAltezza))
                .where(Tables.ANIMALE.COD_PROVINCIA.eq(provincia)
                    .and(Tables.ANIMALE.COD_CITTA_.eq(città))
                    .and(Tables.ANIMALE.NUMERO.eq(numero))
                    .and(Tables.ANIMALE.COD_ANIMALE.eq(animale)))
                .execute();
            return righeAggiornate > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false; // Return false if there is an error
        }
    }

    @Override
    public boolean updateAnimalePeso(String provincia, String città, int numero, String animale, float newPeso) {
        try (Connection conn = DBConfig.getConnection()) {
            DSLContext ctx = DSL.using(conn, SQLDialect.MYSQL);
            int righeAggiornate = ctx.update(Tables.ANIMALE)
                .set(Tables.ANIMALE.PESO, BigDecimal.valueOf(newPeso))
                .where(Tables.ANIMALE.COD_PROVINCIA.eq(provincia)
                    .and(Tables.ANIMALE.COD_CITTA_.eq(città))
                    .and(Tables.ANIMALE.NUMERO.eq(numero))
                    .and(Tables.ANIMALE.COD_ANIMALE.eq(animale)))
                .execute();
            return righeAggiornate > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false; // Return false if there is an error
        }
    }

    @Override
    public boolean deleteAnimale(String provincia, String città, int numero, String animale) {
        try (Connection conn = DBConfig.getConnection()) {
            DSLContext ctx = DSL.using(conn, SQLDialect.MYSQL);
            int righeCancellate = ctx.deleteFrom(Tables.ANIMALE)
                .where(Tables.ANIMALE.COD_PROVINCIA.eq(provincia)
                    .and(Tables.ANIMALE.COD_CITTA_.eq(città))
                    .and(Tables.ANIMALE.NUMERO.eq(numero))
                    .and(Tables.ANIMALE.COD_ANIMALE.eq(animale)))
                .execute();
            return righeCancellate > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false; // Return false if there is an error
        }
    }
}
