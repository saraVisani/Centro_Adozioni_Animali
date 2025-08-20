package it.unibo.adozione_animali.model.impl.spazio;

import org.jooq.DSLContext;
import org.jooq.Query;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;

import it.unibo.adozione_animali.model.api.spazio.SpazioPersona;
import it.unibo.adozione_animali.util.DBConfig;
import nu.studer.sample.Tables;
import nu.studer.sample.routines.CambiaSpazioPersonale;
import java.math.BigDecimal;
import java.sql.Connection;
import java.util.Optional;

public class SpazioPersonaDAO implements SpazioPersona {

    @Override
    public boolean insertSpazioPersona(int spazio, String persona, String provincia, String citta, int numero, Optional<Float> spazioRimanente) {
        try (Connection connection = DBConfig.getConnection()) {
            DSLContext ctx = DSL.using(connection, SQLDialect.MYSQL);

            Integer spazioRimDefault = ctx.select(Tables.SPAZIO.DIMENSIONE)
                .from(Tables.SPAZIO)
                .where(Tables.SPAZIO.ID_SPAZIO.eq(spazio))
                .fetchOptional()
                .map(r -> r.get(Tables.SPAZIO.DIMENSIONE))
                .map(BigDecimal::intValue)
                .orElse(null);

            Query query = ctx.insertInto(Tables.SPAZIO_PERSONA)
                .set(Tables.SPAZIO_PERSONA.ID_SPAZIO, spazio)
                .set(Tables.SPAZIO_PERSONA.CF, persona)
                .set(Tables.SPAZIO_PERSONA.COD_PROVINCIA, provincia)
                .set(Tables.SPAZIO_PERSONA.COD_CITTA_, citta)
                .set(Tables.SPAZIO_PERSONA.NUMERO, numero)
                .set(Tables.SPAZIO_PERSONA.SPAZIO_RIMANENTE,
                    spazioRimanente.orElse(spazioRimDefault != null ? spazioRimDefault.floatValue() : null) != null
                        ? BigDecimal.valueOf(spazioRimanente.orElse(spazioRimDefault != null ? spazioRimDefault.floatValue() : null))
                        : null);

            int righeInserite = query.execute();
            return righeInserite > 0;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean updateSpazioRimanente(int spazio, String persona, String provincia, String citta, int numero, int newSpazio) {
        try (Connection connection = DBConfig.getConnection()) {
            DSLContext ctx = DSL.using(connection, SQLDialect.MYSQL);

            CambiaSpazioPersonale cambiaSpazio = new CambiaSpazioPersonale();
            cambiaSpazio.setPIdspazioold(spazio);
            cambiaSpazio.setPCf(persona);
            cambiaSpazio.setPCodprovincia(provincia);
            cambiaSpazio.setPCodcitta(citta);
            cambiaSpazio.setPNumero(numero);
            cambiaSpazio.setPIdspazionew(newSpazio);
            cambiaSpazio.execute(ctx.configuration());


            int count = ctx.fetchCount(
                Tables.SPAZIO_PERSONA,
                Tables.SPAZIO_PERSONA.CF.eq(persona)
                    .and(Tables.SPAZIO_PERSONA.COD_PROVINCIA.eq(provincia))
                    .and(Tables.SPAZIO_PERSONA.COD_CITTA_.eq(citta))
                    .and(Tables.SPAZIO_PERSONA.NUMERO.eq(numero))
                    .and(Tables.SPAZIO_PERSONA.ID_SPAZIO.eq(newSpazio))
            );

        return count > 0;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean deleteSpazioPersona(int spazio, String persona, String provincia, String citta, int numero) {
        try (Connection connection = DBConfig.getConnection()) {
            DSLContext ctx = DSL.using(connection, SQLDialect.MYSQL);

            return ctx.transactionResult(configuration -> {
                DSLContext tCtx = DSL.using(configuration);

                //Cancella gli abitanti legati a SPAZIO_PERSONA
                tCtx.deleteFrom(Tables.ABITANTE)
                    .where(Tables.ABITANTE.ID_SPAZIO.eq(spazio))
                    .and(Tables.ABITANTE.CF.eq(persona))
                    .and(Tables.ABITANTE.COD_PROVINCIA.eq(provincia))
                    .and(Tables.ABITANTE.COD_CITTA_.eq(citta))
                    .and(Tables.ABITANTE.NUMERO.eq(numero))
                    .execute();

                //Cancella il record in SPAZIO_PERSONA
                int righeSpazioPersona = tCtx.deleteFrom(Tables.SPAZIO_PERSONA)
                    .where(Tables.SPAZIO_PERSONA.ID_SPAZIO.eq(spazio))
                    .and(Tables.SPAZIO_PERSONA.CF.eq(persona))
                    .and(Tables.SPAZIO_PERSONA.COD_PROVINCIA.eq(provincia))
                    .and(Tables.SPAZIO_PERSONA.COD_CITTA_.eq(citta))
                    .and(Tables.SPAZIO_PERSONA.NUMERO.eq(numero))
                    .execute();

                // Se non elimino almeno la riga SPAZIO_PERSONA => rollback
                if (righeSpazioPersona == 0) {
                    throw new RuntimeException("Nessun record SPAZIO_PERSONA eliminato, rollback forzato");
                }

                return true;
            });

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

}
