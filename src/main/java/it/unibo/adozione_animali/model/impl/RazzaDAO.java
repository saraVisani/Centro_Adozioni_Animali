package it.unibo.adozione_animali.model.impl;

import it.unibo.adozione_animali.model.api.Razza;
import it.unibo.adozione_animali.util.DBConfig;
import it.unibo.adozione_animali.util.Enum;
import nu.studer.sample.Tables;
import org.jooq.DSLContext;
import org.jooq.impl.DSL;
import org.jooq.impl.QOM;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.logging.Logger;

public class RazzaDAO implements Razza {

    final Logger logger = Logger.getLogger("loggerRazza");

    @Override
    public void insertRazza(final String codSpecie, final String nomeRazza, final String descrizione,
                            final String origine, final String lignaggio, final BigDecimal altezzaMax,
                            final BigDecimal altezzaMin, final BigDecimal pesoMax, final BigDecimal pesoMin, final Integer IDSpazio1,
                            final Integer IDSpazio2) {
        try (Connection conn = DBConfig.getConnection()) {
            DSLContext create = DSL.using(conn);
            if (checkSpecie(codSpecie)) {
                if (checkSpazio(IDSpazio1)) {
                    if (IDSpazio2 == null || checkSpazio(IDSpazio2) ) {
                        create.insertInto(Tables.RAZZA)
                                .set(Tables.RAZZA.COD_SPECIE, codSpecie)
                                .set(Tables.RAZZA.NOME, nomeRazza)
                                .set(Tables.RAZZA.DESCRIZIONE, descrizione)
                                .set(Tables.RAZZA.ORIGINE, origine)
                                .set(Tables.RAZZA.LIGNAGGIO, lignaggio)
                                .set(Tables.RAZZA.PESO_MAX, pesoMax)
                                .set(Tables.RAZZA.PESO_MIN, pesoMin)
                                .set(Tables.RAZZA.ALT_MAX, altezzaMax)
                                .set(Tables.RAZZA.ALT_MIN, altezzaMin)
                                .set(Tables.RAZZA.ID_SPAZIO1, IDSpazio1)
                                .set(Tables.RAZZA.ID_SPAZIO2, IDSpazio2)
                                .execute();
                    } else {
                        this.logger.severe("Il secondo spazio inserito non rientra in nessun tipo di spazio presente");
                    }
                } else {
                    this.logger.severe("Il primo spazio inserito non rientra in nessun tipo di spazio presente");
                }
            } else {
                this.logger.severe("Nessun tipo di specie equivale a quella inserita");
            }
        } catch (SQLException e) {
            this.logger.severe("La connessione non ha funzionato");
        }
    }

    @Override
    public void deleteRazza(final String codSpecie, final String nomeRazza) {
        try (Connection conn = DBConfig.getConnection()) {
            DSLContext create = DSL.using(conn);
            create.deleteFrom(Tables.RAZZA)
                    .where(Tables.RAZZA.COD_SPECIE.eq(codSpecie))
                    .and(Tables.RAZZA.NOME.eq(nomeRazza))
                    .execute();
        } catch (SQLException e) {
            this.logger.severe("La connessione non ha funzionato");
        }
    }

    private boolean checkSpecie(final String codSpecie) {
        return Enum.Specie.matchesKeys(codSpecie);
    }

    private boolean checkSpazio(final int IDSpazio) {
        return Enum.TipoSpazio.matches(String.valueOf(IDSpazio));
    }
}
