package it.unibo.adozione_animali.model.impl;

import java.sql.Connection;

import org.jooq.DSLContext;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;

import it.unibo.adozione_animali.model.api.Abitante;
import nu.studer.sample.routines.AggiungiAbitante;
import nu.studer.sample.routines.RimuoviAbitante;
import nu.studer.sample.routines.SpostaAbitante;

public class AbitanteDAO implements Abitante {

    @Override
    public boolean insertAbitante(int numero, String citta, String provincia, int spazio, String CF, String specie,
            String nome) {
        try (Connection conn = DBConfig.getConnection()) {
            DSLContext ctx = DSL.using(conn, SQLDialect.MYSQL);
            AggiungiAbitante aggiungiAbitante = new AggiungiAbitante();
            aggiungiAbitante.setPNumero(numero);
            aggiungiAbitante.setPCodcitta(citta);
            aggiungiAbitante.setPCodprovincia(provincia);
            aggiungiAbitante.setPIdspazio(spazio);
            aggiungiAbitante.setPCf(CF);
            aggiungiAbitante.setPCodspecie(specie);
            aggiungiAbitante.setPNomerazza(nome);

            int righeInserite = aggiungiAbitante.execute(ctx.configuration());

            return righeInserite > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean updateAbitanteAnimale(int numero, String citta, String provincia, int spazio, String CF,
            String specie, String nome, String newSpecie, String newNome) {
        try (Connection conn = DBConfig.getConnection()) {
            DSLContext ctx = DSL.using(conn, SQLDialect.MYSQL);

            // Rimuove l'abitante esistente
            RimuoviAbitante rimuovi = new RimuoviAbitante();
            rimuovi.setPNumero(numero);
            rimuovi.setPCodcitta(citta);
            rimuovi.setPCodprovincia(provincia);
            rimuovi.setPIdspazio(spazio);
            rimuovi.setPCf(CF);
            rimuovi.setPCodspecie(specie);
            rimuovi.setPNomerazza(nome);

            int righeCancellate = rimuovi.execute(ctx.configuration());
            if (righeCancellate == 0) {
                return false; // Nessun abitante da aggiornare
            }

            // Inserisce l'abitante aggiornato
            AggiungiAbitante aggiungi = new AggiungiAbitante();
            aggiungi.setPNumero(numero);
            aggiungi.setPCodcitta(citta);
            aggiungi.setPCodprovincia(provincia);
            aggiungi.setPIdspazio(spazio);
            aggiungi.setPCf(CF);
            aggiungi.setPCodspecie(newSpecie);
            aggiungi.setPNomerazza(newNome);

            int righeInserite = aggiungi.execute(ctx.configuration());
            return righeInserite > 0;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean updateAbitanteIndirizzo(int numero, String citta, String provincia, int spazio, String CF,
            String specie, String nome, String newCitta, String newProvincia, int newSpazio, int newNumero) {
        try (Connection conn = DBConfig.getConnection()) {
            DSLContext ctx = DSL.using(conn, SQLDialect.MYSQL);
            SpostaAbitante spostaAbitante = new SpostaAbitante();
            spostaAbitante.setPNumeroOld(numero);
            spostaAbitante.setPCodcittaOld(citta);
            spostaAbitante.setPCodprovinciaOld(provincia);
            spostaAbitante.setPIdspazioOld(spazio);
            spostaAbitante.setPCf(CF);
            spostaAbitante.setPCodspecie(specie);
            spostaAbitante.setPNomerazza(nome);
            spostaAbitante.setPCodcittaNew(newCitta);
            spostaAbitante.setPCodprovinciaNew(newProvincia);
            spostaAbitante.setPIdspazioNew(newSpazio);
            spostaAbitante.setPNumeroNew(newNumero);

            int righeAggiornate = spostaAbitante.execute(ctx.configuration());
            return righeAggiornate > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean deleteAbitante(int numero, String citta, String provincia, int spazio, String CF, String specie,
            String nome) {
        try (Connection conn = DBConfig.getConnection()) {
            DSLContext ctx = DSL.using(conn, SQLDialect.MYSQL);

            RimuoviAbitante rimuoviAbitante = new RimuoviAbitante();
            rimuoviAbitante.setPNumero(numero);
            rimuoviAbitante.setPCodcitta(citta);
            rimuoviAbitante.setPCodprovincia(provincia);
            rimuoviAbitante.setPIdspazio(spazio);
            rimuoviAbitante.setPCf(CF);
            rimuoviAbitante.setPCodspecie(specie);
            rimuoviAbitante.setPNomerazza(nome);
            int righeCancellate = rimuoviAbitante.execute(ctx.configuration());
            return righeCancellate > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

}
