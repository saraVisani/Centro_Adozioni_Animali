package it.unibo.adozione_animali.model.impl;

import java.sql.DriverManager;
import java.util.List;

import org.jooq.DSLContext;
import org.jooq.impl.DSL;
import nu.studer.sample.Tables;
import nu.studer.sample.tables.records.CentroRecord;
import nu.studer.sample.tables.records.TipologiaRecord;

import java.sql.Connection;

import it.unibo.adozione_animali.model.api.Centro;

public class CentroDAO implements Centro{

    @Override
    public boolean insertCentro(String nome, int capienza, String provincia, String città, int numero,
            List<String> specie) {
        try (Connection conn = DBConfig.getConnection()) {
            DSLContext ctx = DSL.using(conn);

            // 1. Inserisci il centro
            ctx.insertInto(Tables.CENTRO)
               .set(Tables.CENTRO.NOME, nome)
               .set(Tables.CENTRO.CAPIENZA, capienza)
               .set(Tables.CENTRO.PROVINCIA, provincia)
               .set(Tables.CENTRO.CITTA, città)
               .set(Tables.CENTRO.NUMERO, numero)
               .execute();

            // 2. Inserisci le relazioni nella tabella tipologia
            for (String specieId : specie) {
                ctx.insertInto(Tables.TIPOLOGIA)
                   .set(Tables.TIPOLOGIA.COD_SPECIE, specieId)
                   .set(Tables.TIPOLOGIA.COD_PROVINCIA, provincia)
                   .set(Tables.TIPOLOGIA.COD_CITTA, città)
                   .set(Tables.TIPOLOGIA.NUMERO, numero)
                   .execute();
            }

            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean updateCentroNome(String provincia, String città, int numero, String nome) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'updateCentroNome'");
    }

    @Override
    public boolean updateCentroCapienza(String provincia, String città, int numero, int capienza) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'updateCentroCapienza'");
    }

    @Override
    public boolean updateCentroAggiungiSpecie(String provincia, String città, int numero, List<String> specie) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'updateCentroAggiungiSpecie'");
    }

    @Override
    public boolean updateCentroTogliSpecie(String provincia, String città, int numero, List<String> specie) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'updateCentroTogliSpecie'");
    }

    @Override
    public boolean deleteCentro(String provincia, String città, int numero) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'deleteCentro'");
    }


}
