package it.unibo.adozione_animali.model.impl.animale;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.jooq.DSLContext;
import org.jooq.Query;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;

import it.unibo.adozione_animali.model.api.animale.Centro;
import it.unibo.adozione_animali.util.DBConfig;
import nu.studer.sample.Tables;
import nu.studer.sample.routines.RiallocaSingoloAnimale;
import nu.studer.sample.routines.RiallocaSingoloLavoratore;

import java.sql.Connection;

/**
 * Implementazione della gestione dei centri di adozione animali.
 * Questa classe fornisce metodi per inserire, aggiornare e cancellare centri,
 * gestendo anche le operazioni transazionali necessarie.
 */
public class CentroDAO implements Centro{

    @Override
    public boolean insertCentro(String nome, int capienza, String provincia, String città, int numero,
            List<String> specie) {

        try (Connection conn = DBConfig.getConnection()) {
            DSLContext ctx = DSL.using(conn, SQLDialect.MYSQL);

            // Avvio della transazione manuale
            conn.setAutoCommit(false);
            boolean atLeastOneTipologiaInserted = false;

            try {
                // 1. Inserisci il centro
                ctx.insertInto(Tables.CENTRO)
                .set(Tables.CENTRO.NOME, nome)
                .set(Tables.CENTRO.CAPIENZA, (byte) capienza)
                .set(Tables.CENTRO.COD_PROVINCIA, provincia)
                .set(Tables.CENTRO.COD_CITTA_, città)
                .set(Tables.CENTRO.NUMERO, numero)
                .execute();

                // 2. Inserisci le tipologie in batch
                for (String specieId : specie) {
                    try {
                        ctx.insertInto(Tables.TIPOLOGIA)
                        .set(Tables.TIPOLOGIA.COD_SPECIE, specieId)
                        .set(Tables.TIPOLOGIA.COD_PROVINCIA, provincia)
                        .set(Tables.TIPOLOGIA.COD_CITTA_, città)
                        .set(Tables.TIPOLOGIA.NUMERO, numero)
                        .execute();
                        atLeastOneTipologiaInserted = true;
                    } catch (Exception e) {
                        // Logga l'errore ma continua con le altre
                        System.err.println("Errore inserimento tipologia: " + specieId);
                        e.printStackTrace();
                    }
                }

                // Controllo se almeno una tipologia è stata inserita
                if (specie.isEmpty() || atLeastOneTipologiaInserted) {
                    conn.commit(); // commit centro + eventuali tipologie riuscite
                    return true;
                } else {
                    conn.rollback(); // rollback totale perché tutte le tipologie fallite
                    return false;
                }

            } catch (Exception e) {
                conn.rollback(); // rollback totale se il centro fallisce
                e.printStackTrace();
                return false;
            } finally {
                conn.setAutoCommit(true); // ripristina autocommit
            }

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean updateCentroNome(String provincia, String città, int numero, String nome) {
            try (Connection conn = DBConfig.getConnection()) {
            DSLContext ctx = DSL.using(conn, SQLDialect.MYSQL);

            // Aggiorna il nome del centro corrispondente alla chiave primaria
            int updatedRows = ctx.update(Tables.CENTRO)
                                .set(Tables.CENTRO.NOME, nome)
                                .where(Tables.CENTRO.COD_PROVINCIA.eq(provincia)
                                    .and(Tables.CENTRO.COD_CITTA_.eq(città))
                                    .and(Tables.CENTRO.NUMERO.eq(numero)))
                                .execute();

            // Se almeno una riga è stata aggiornata ritorna true
            return updatedRows > 0;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean updateCentroCapienza(String provincia, String città, int numero, int capienza) {
        try (Connection conn = DBConfig.getConnection()) {
            conn.setAutoCommit(false); // gestione transazionale
            DSLContext ctx = DSL.using(conn, SQLDialect.MYSQL);

            // 1. Leggi capienza attuale e numero animali presenti
            Byte capienzaAttuale = ctx.select(Tables.CENTRO.CAPIENZA)
                                        .from(Tables.CENTRO)
                                        .where(Tables.CENTRO.COD_PROVINCIA.eq(provincia))
                                        .and(Tables.CENTRO.COD_CITTA_.eq(città))
                                        .and(Tables.CENTRO.NUMERO.eq(numero))
                                        .fetchOne(Tables.CENTRO.CAPIENZA);

            if (capienzaAttuale == null) {
                conn.rollback();
                return false; // centro non trovato
            }

            List<String> animaliPresenti = ctx.select(Tables.ANIMALE.COD_ANIMALE)
                                            .from(Tables.ANIMALE)
                                            .where(Tables.ANIMALE.COD_PROVINCIA.eq(provincia))
                                            .and(Tables.ANIMALE.COD_CITTA_.eq(città))
                                            .and(Tables.ANIMALE.NUMERO_CENTRO.eq(numero))
                                            .fetchInto(String.class);

            int numAnimali = animaliPresenti.size();

            if (capienza >= capienzaAttuale) {
                // caso semplice: aumento capienza
                ctx.update(Tables.CENTRO)
                .set(Tables.CENTRO.CAPIENZA, (byte) capienza)
                .where(Tables.CENTRO.COD_PROVINCIA.eq(provincia))
                .and(Tables.CENTRO.COD_CITTA_.eq(città))
                .and(Tables.CENTRO.NUMERO.eq(numero))
                .execute();
            } else {
                int eccesso = numAnimali - capienza;
                if (eccesso > 0) {
                    // seleziona casualmente gli animali in eccesso
                    Collections.shuffle(animaliPresenti);
                    List<String> animaliDaRiallocare = animaliPresenti.subList(0, eccesso);

                    for (String codAnimale : animaliDaRiallocare) {
                        RiallocaSingoloAnimale routine = new RiallocaSingoloAnimale();
                        routine.setPCodProvincia(provincia);
                        routine.setPCodCitta(città);
                        routine.setPNumero(numero);
                        routine.setPCodAnimale(codAnimale);
                        routine.execute(ctx.configuration());
                    }
                }

                // aggiorna capienza
                ctx.update(Tables.CENTRO)
                .set(Tables.CENTRO.CAPIENZA, (byte) capienza)
                .where(Tables.CENTRO.COD_PROVINCIA.eq(provincia))
                .and(Tables.CENTRO.COD_CITTA_.eq(città))
                .and(Tables.CENTRO.NUMERO.eq(numero))
                .execute();
            }

            conn.commit();
            return true;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean updateCentroAggiungiSpecie(String provincia, String città, int numero, List<String> specie) {
        try (Connection conn = DBConfig.getConnection()) {
            DSLContext ctx = DSL.using(conn, SQLDialect.MYSQL); // Sostituisci SQLDialect se necessario

            // Esegui tutto dentro una transazione
            ctx.transaction(configuration -> {
                DSLContext tCtx = DSL.using(configuration);

                // Prepara gli insert come batch
                Query[] inserts = specie.stream()
                    .map(specieId -> tCtx.insertInto(Tables.TIPOLOGIA)
                                        .set(Tables.TIPOLOGIA.COD_SPECIE, specieId)
                                        .set(Tables.TIPOLOGIA.COD_PROVINCIA, provincia)
                                        .set(Tables.TIPOLOGIA.COD_CITTA_, città)
                                        .set(Tables.TIPOLOGIA.NUMERO, numero)
                                        .onDuplicateKeyIgnore() // evita duplicati
                    )
                    .toArray(Query[]::new);

                // Esegui tutti gli insert in batch
                tCtx.batch(inserts).execute();
            });

            return true;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean updateCentroTogliSpecie(String provincia, String città, int numero, List<String> specie) {
        try (Connection conn = DBConfig.getConnection()) {
            DSLContext ctx = DSL.using(conn, SQLDialect.MYSQL);

            // Calcola il totale di tipologie presenti per le specie indicate
            int totalePresente = ctx.selectCount()
                .from(Tables.TIPOLOGIA)
                .where(Tables.TIPOLOGIA.COD_PROVINCIA.eq(provincia))
                .and(Tables.TIPOLOGIA.COD_CITTA_.eq(città))
                .and(Tables.TIPOLOGIA.COD_SPECIE.in(specie))
                .fetchOne(0, int.class);

            // Se il numero da rimuovere >= totale presente, ritorna false e esci
            if (numero >= totalePresente) {
                return false;
            }

            // Chiama il metodo privato che esegue la rimozione
            return eliminaSpecie(ctx, provincia, città, numero, specie);

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    private boolean eliminaSpecie(DSLContext ctx, String provincia, String città, int numero, List<String> specie) {
        try {
            int[] risultati = ctx.transactionResult(configuration -> {
                DSLContext tCtx = DSL.using(configuration);

                return specie.stream()
                    .map(specieId -> {
                        // 1️⃣ Rialloca animali di questa specie prima di eliminarla
                        tCtx.selectFrom(Tables.ANIMALE)
                            .where(Tables.ANIMALE.COD_PROVINCIA.eq(provincia))
                            .and(Tables.ANIMALE.COD_CITTA_.eq(città))
                            .and(Tables.ANIMALE.NUMERO_CENTRO.eq(numero))
                            .and(Tables.ANIMALE.COD_SPECIE.eq(specieId))
                            .fetch()
                            .forEach(animale -> {
                                RiallocaSingoloAnimale routine = new RiallocaSingoloAnimale();
                                routine.setPCodProvincia(animale.getCodProvincia());
                                routine.setPCodCitta(animale.getCodCitta_());
                                routine.setPNumero(animale.getNumeroCentro());
                                routine.setPCodAnimale(animale.getCodAnimale());
                                routine.execute(configuration);
                            });

                        // 2️⃣ Elimina la tipologia
                        return tCtx.deleteFrom(Tables.TIPOLOGIA)
                                .where(Tables.TIPOLOGIA.COD_PROVINCIA.eq(provincia))
                                .and(Tables.TIPOLOGIA.COD_CITTA_.eq(città))
                                .and(Tables.TIPOLOGIA.COD_SPECIE.eq(specieId))
                                .limit(numero)
                                .execute();
                    })
                    .mapToInt(Integer::intValue)
                    .toArray();
            });

            // Se nessun record è stato eliminato, ritorna false
            int totaleEliminato = Arrays.stream(risultati).sum();
            return totaleEliminato > 0;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean deleteCentro(String provincia, String città, int numero) {
        try (Connection conn = DBConfig.getConnection()) {
            DSLContext ctx = DSL.using(conn, SQLDialect.MYSQL);

            ctx.transaction(configuration -> {
                DSLContext tCtx = DSL.using(configuration);

                // Rialloca lavoratori
                tCtx.selectFrom(Tables.PERSONALE)
                    .where(Tables.PERSONALE.COD_PROVINCIA.eq(provincia))
                    .and(Tables.PERSONALE.COD_CITTA_.eq(città))
                    .and(Tables.PERSONALE.NUMERO.eq(numero))
                    .fetch()
                    .forEach(lavoratore -> {
                        RiallocaSingoloLavoratore routine = new RiallocaSingoloLavoratore();
                        routine.setPCf(lavoratore.getCf());
                        routine.execute(configuration);
                    });

                // Elimina tipologie associate al centro e rialloca animali
                List<String> specie = tCtx.select(Tables.TIPOLOGIA.COD_SPECIE)
                    .from(Tables.TIPOLOGIA)
                    .where(Tables.TIPOLOGIA.COD_PROVINCIA.eq(provincia))
                    .and(Tables.TIPOLOGIA.COD_CITTA_.eq(città))
                    .and(Tables.TIPOLOGIA.NUMERO.eq(numero))
                    .fetch(Tables.TIPOLOGIA.COD_SPECIE);

                if (!specie.isEmpty()) {
                    eliminaSpecie(tCtx, provincia, città, numero, specie);
                }

                // Elimina indirizzo
                tCtx.deleteFrom(Tables.INDIRIZZO)
                    .where(Tables.INDIRIZZO.COD_PROVINCIA.eq(provincia))
                    .and(Tables.INDIRIZZO.COD_CITTA_.eq(città))
                    .and(Tables.INDIRIZZO.NUMERO.eq(numero))
                    .execute();

                // Elimina centro
                tCtx.deleteFrom(Tables.CENTRO)
                    .where(Tables.CENTRO.COD_PROVINCIA.eq(provincia))
                    .and(Tables.CENTRO.COD_CITTA_.eq(città))
                    .and(Tables.CENTRO.NUMERO.eq(numero))
                    .execute();
            });

            return true;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}