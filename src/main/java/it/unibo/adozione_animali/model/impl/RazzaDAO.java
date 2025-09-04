package it.unibo.adozione_animali.model.impl;

import it.unibo.adozione_animali.model.api.Razza;
import it.unibo.adozione_animali.util.DBConfig;
import nu.studer.sample.Tables;
import org.jooq.DSLContext;
import org.jooq.Record2;
import org.jooq.impl.DSL;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
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
            checkSpecie(codSpecie);
            checkOrigin(origine);
            checkLineage(lignaggio);
            checkSpazio(IDSpazio1);
            if (IDSpazio2 != null) {
                checkSpazio(IDSpazio2);
            }
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

        } catch (SQLException e) {
            this.logger.severe("La connessione non ha funzionato");
            throw new IllegalArgumentException();
        }
    }

    @Override
    public void deleteRazza(final String codSpecie, final String nomeRazza) {
        try (Connection conn = DBConfig.getConnection()) {
            DSLContext create = DSL.using(conn);

            this.checkSpecie(codSpecie);
            this.checkRazza(codSpecie, nomeRazza);

            create.deleteFrom(Tables.RAZZA)
                    .where(Tables.RAZZA.COD_SPECIE.eq(codSpecie))
                    .and(Tables.RAZZA.NOME.eq(nomeRazza))
                    .execute();
        } catch (SQLException e) {
            this.logger.severe("La connessione non ha funzionato");
        }
    }

    public List<Record2<String, String>> getRaces(String codProv, String codCit, Integer num) {
        List<Record2<String, String>> races = null;
        try (Connection conn = DBConfig.getConnection()) {
            DSLContext create = DSL.using(conn);

            this.checkCentroExistance(codProv, codCit, num);

            races = create.select(Tables.RAZZA.NOME, Tables.RAZZA.COD_SPECIE)
                    .from(Tables.RAZZA)
                    .join(Tables.ANIMALE)
                    .on(Tables.RAZZA.NOME.eq(Tables.ANIMALE.NOME_RAZZA))
                    .where(Tables.ANIMALE.COD_PROVINCIA.eq(codProv))
                    .and(Tables.ANIMALE.COD_CITTA_.eq(codCit))
                    .and(Tables.ANIMALE.NUMERO.eq(num))
                    .orderBy(Tables.RAZZA.COD_SPECIE.asc(),
                            Tables.RAZZA.NOME.asc())
                    .fetch()
                    .stream()
                    .distinct()
                    .toList();

        } catch (SQLException e) {
            this.logger.severe("La connessione non ha funzionato");
        }
        return races;
    }

    private void checkSpecie(final String codSpecie) {
        try (Connection conn = DBConfig.getConnection()) {
            DSLContext create = DSL.using(conn);

            boolean exists = create.fetchExists(
                    create.selectFrom(Tables.SPECIE)
                            .where(Tables.SPECIE.COD_SPECIE.eq(codSpecie))
            );

            if (!exists) {
                throw new IllegalArgumentException("La specie indicata non esiste");
            }

        } catch (SQLException e) {
            this.logger.severe("La connessione non ha funzionato");
        }
    }

    private void checkSpazio(final int IDSpazio) {
        try (Connection conn = DBConfig.getConnection()) {
            DSLContext create = DSL.using(conn);

            boolean exists = create.fetchExists(
                    create.selectFrom(Tables.SPAZIO)
                            .where(Tables.SPAZIO.ID_SPAZIO.eq(IDSpazio))
            );

            if (!exists) {
                throw new IllegalArgumentException("Lo spazio indicato " + IDSpazio + " non esiste");
            }

        } catch (SQLException e) {
            this.logger.severe("La connessione non ha funzionato");
        }
    }

    private void checkCentroExistance(final String codProv, final String codCit, final Integer num) {
        try (Connection conn = DBConfig.getConnection()) {
            DSLContext create = DSL.using(conn);

            boolean exists = create.fetchExists(
                    create.selectFrom(Tables.CENTRO)
                            .where(Tables.CENTRO.COD_PROVINCIA.eq(codProv))
                            .and(Tables.CENTRO.COD_CITTA_.eq(codCit))
                            .and(Tables.CENTRO.NUMERO.eq(num))
            );

            if (!exists) {
                throw new IllegalArgumentException("Il centro indicato non esiste");
            }

        } catch (SQLException e) {
            this.logger.severe("La connessione non ha funzionato");
        }
    }

    private void checkRazza(final String codSpecie, final String nomeRazza) {
        try (Connection conn = DBConfig.getConnection()) {
            DSLContext create = DSL.using(conn);

            boolean exists = create.fetchExists(
                    create.selectFrom(Tables.RAZZA)
                            .where(Tables.RAZZA.COD_SPECIE.eq(codSpecie))
                            .and(Tables.RAZZA.NOME.eq(nomeRazza))
            );

            if (!exists) {
                throw new IllegalArgumentException("La razza indicata non esiste");
            }

        } catch (SQLException e) {
            this.logger.severe("La connessione non ha funzionato");
        }

    }

    private void checkLineage(String lineage) {
        if (!lineage.equalsIgnoreCase("Puro")
                && !lineage.equalsIgnoreCase("Meticcio")) {
            throw new IllegalArgumentException("Non esiste il lignaggio specificato");
        }
    }

    private void checkOrigin(String origin) {
        if (!origin.equalsIgnoreCase("Straniero")
                && !origin.equalsIgnoreCase("Autoctono")) {
            throw new IllegalArgumentException("Non esiste l'origine specificata");
        }
    }

    public List<String> getRazzaBySpecie(String comboSpecie) {
        try (Connection conn = DBConfig.getConnection()) {
            DSLContext ctx = DSL.using(conn);

            return ctx.select(Tables.RAZZA.NOME)
                    .from(Tables.RAZZA)
                    .where(Tables.RAZZA.COD_SPECIE.eq(comboSpecie))
                    .orderBy(Tables.RAZZA.NOME.asc())
                    .fetchInto(String.class);

        } catch (SQLException e) {
            logger.severe("La connessione non ha funzionato: " + e.getMessage());
            return List.of(); // ritorna lista vuota in caso di errore
        }
    }

    public List<BigDecimal> getPesiAltezza(String specie, String razza) {
        try (Connection conn = DBConfig.getConnection()) {
            DSLContext ctx = DSL.using(conn);

            // Recupera il lignaggio della razza
            String lignaggio = ctx.select(Tables.RAZZA.LIGNAGGIO)
                                .from(Tables.RAZZA)
                                .where(Tables.RAZZA.COD_SPECIE.eq(specie))
                                .and(Tables.RAZZA.NOME.eq(razza))
                                .fetchOne(Tables.RAZZA.LIGNAGGIO);

            if ("Puro".equalsIgnoreCase(lignaggio)) {
                var record = ctx.select(Tables.RAZZA.PESO_MAX, Tables.RAZZA.PESO_MIN,
                                        Tables.RAZZA.ALT_MAX, Tables.RAZZA.ALT_MIN)
                                .from(Tables.RAZZA)
                                .where(Tables.RAZZA.COD_SPECIE.eq(specie))
                                .and(Tables.RAZZA.NOME.eq(razza))
                                .fetchOne();

                if (record != null) {
                    return List.of(
                        record.get(Tables.RAZZA.PESO_MAX),
                        record.get(Tables.RAZZA.PESO_MIN),
                        record.get(Tables.RAZZA.ALT_MAX),
                        record.get(Tables.RAZZA.ALT_MIN)
                    );
                }
            } else if ("Meticcio".equalsIgnoreCase(lignaggio)) {
                var record = ctx.select(
                                    DSL.max(Tables.RAZZA.PESO_MAX),
                                    DSL.min(Tables.RAZZA.PESO_MIN),
                                    DSL.max(Tables.RAZZA.ALT_MAX),
                                    DSL.min(Tables.RAZZA.ALT_MIN)
                                )
                                .from(Tables.RAZZA)
                                .where(Tables.RAZZA.COD_SPECIE.eq(specie))
                                .fetchOne();

                if (record != null) {
                    return List.of(
                        record.get(0, BigDecimal.class),
                        record.get(1, BigDecimal.class),
                        record.get(2, BigDecimal.class),
                        record.get(3, BigDecimal.class)
                    );
                }
            }

            return List.of(); // Nessun record trovato
        } catch (SQLException e) {
            logger.severe("La connessione non ha funzionato: " + e.getMessage());
            return List.of();
        }
    }

}
