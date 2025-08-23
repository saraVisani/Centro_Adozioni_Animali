package it.unibo.adozione_animali.model.impl;


import it.unibo.adozione_animali.model.api.Componente;
import it.unibo.adozione_animali.util.DBConfig;
import nu.studer.sample.Routines;
import nu.studer.sample.Tables;
import org.jooq.DSLContext;
import org.jooq.Record;
import org.jooq.Record1;
import org.jooq.Result;
import org.jooq.impl.DSL;
import static org.jooq.impl.DSL.*;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.logging.Logger;

public class ComponenteDAO implements Componente {

    final Logger logger = Logger.getLogger("loggerComponente");

    @Override
    public void insertComponente(final String codProvincia, final String codCitta, final int numero,
                                 final String codAnimale, final String codSpecie, final String nomeRazza,
                                 final int percentuale) {
        try (Connection conn = DBConfig.getConnection()) {
            DSLContext create = DSL.using(conn);
            Routines.inserimentoNuovaComponente(create.configuration(), codProvincia, codCitta, numero, codAnimale,
                    codSpecie, nomeRazza, percentuale);
        } catch (SQLException e) {
            this.logger.severe("La connessione non ha funzionato");
        }
    }

    @Override
    public void updatePercentualeComponente(final String codProvincia, final String codCitta, final int numero,
                                            final String codAnimale, final String codSpecie, final String nomeRazza,
                                            final int nuovaPercentuale) {
        try (Connection conn = DBConfig.getConnection()) {
            DSLContext create = DSL.using(conn);
            System.out.println("Qui");
            Integer perc = create.select(sum(Tables.COMPONENTE.PERCENTUALE))
                            .from(Tables.COMPONENTE)
                            .where(Tables.COMPONENTE.COD_PROVINCIA.eq(codProvincia))
                            .and(Tables.COMPONENTE.COD_CITTA_.eq(codCitta))
                            .and(Tables.COMPONENTE.NUMERO.eq(numero))
                            .and(Tables.COMPONENTE.COD_ANIMALE.eq(codAnimale))
                            .and(Tables.COMPONENTE.COD_SPECIE.eq(codSpecie))
                            .and(Tables.COMPONENTE.NOME.ne(nomeRazza))
                            .fetchOne(0, Integer.class);


            System.out.println(perc + nuovaPercentuale);
            if (perc + nuovaPercentuale > 100) {
                throw new IllegalArgumentException();
            }

            create.update(Tables.COMPONENTE)
                    .set(Tables.COMPONENTE.PERCENTUALE, (byte) nuovaPercentuale)
                    .where(Tables.COMPONENTE.COD_PROVINCIA.eq(codProvincia))
                    .and(Tables.COMPONENTE.COD_CITTA_.eq(codCitta))
                    .and(Tables.COMPONENTE.NUMERO.eq(numero))
                    .and(Tables.COMPONENTE.COD_ANIMALE.eq(codAnimale))
                    .and(Tables.COMPONENTE.COD_SPECIE.eq(codSpecie))
                    .and(Tables.COMPONENTE.NOME.eq(nomeRazza))
                    .execute();
        } catch (SQLException e) {
            this.logger.severe("La connessione non ha funzionato");
        }
    }

    @Override
    public void deleteComponente(final String codProvincia, final String codCitta, final int numero,
                                 final String codAnimale, final String codSpecie, final String nomeRazza) {
        try (Connection conn = DBConfig.getConnection()) {
            DSLContext create = DSL.using(conn);

            Boolean checkAnimale = create.fetchExists(selectFrom(Tables.ANIMALE)
                    .where(Tables.ANIMALE.COD_PROVINCIA.eq(codProvincia))
                    .and(Tables.ANIMALE.COD_CITTA_.eq(codCitta))
                    .and(Tables.ANIMALE.NUMERO.eq(numero))
                    .and(Tables.ANIMALE.COD_ANIMALE.eq(codAnimale))
            );

            if (!checkAnimale) {
                throw new IllegalArgumentException();
            }

            create.deleteFrom(Tables.COMPONENTE)
                    .where(Tables.COMPONENTE.COD_PROVINCIA.eq(codProvincia))
                    .and(Tables.COMPONENTE.COD_CITTA_.eq(codCitta))
                    .and(Tables.COMPONENTE.NUMERO.eq(numero))
                    .and(Tables.COMPONENTE.COD_ANIMALE.eq(codAnimale))
                    .and(Tables.COMPONENTE.COD_SPECIE.eq(codSpecie))
                    .and(Tables.COMPONENTE.NOME.eq(nomeRazza))
                    .execute();
        } catch (SQLException e) {
            this.logger.severe("La connessione non ha funzionato");
        }
    }
}
