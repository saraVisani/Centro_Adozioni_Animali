package it.unibo.adozione_animali.view.pulsanti;

import java.sql.Connection;
import java.util.List;
import java.util.Optional;

import org.jooq.DSLContext;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;

import it.unibo.adozione_animali.model.impl.Model;
import it.unibo.adozione_animali.util.DBConfig;
import it.unibo.adozione_animali.util.ItemSelezionabile;
import nu.studer.sample.Tables;

public class IndirizzoController {

    private final IndirizziPanel view;
    private final Model model;

    public IndirizzoController (IndirizziPanel view, Model model){
        this.model = model;
        this.view = view;

        view.setProvince(model.getProvinciaDAO().getProvince());
        try (Connection conn = DBConfig.getConnection()) {
            DSLContext ctx = DSL.using(conn, SQLDialect.MYSQL);

            List<String> cfs = ctx.select(Tables.PERSONA.CF)
                                .from(Tables.PERSONA)
                                .fetch(Tables.PERSONA.CF);

            view.setCf(cfs);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setRegione(){
        try (Connection conn = DBConfig.getConnection()) {
            DSLContext ctx = DSL.using(conn, SQLDialect.MYSQL);

            List<ItemSelezionabile> reg = ctx.select(Tables.REGIONE.COD_REGIONE, Tables.REGIONE.NOME)
                                .from(Tables.REGIONE)
                                .fetch(record -> new ItemSelezionabile(
                                record.get(Tables.REGIONE.COD_REGIONE),
                                record.get(Tables.REGIONE.NOME)
                            ));

            view.setRegione(reg);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void inserimentoProvincia() {
        if(view.getInserireProvincia()){
            view.getProvinciaNuovo().setEnabled(true);
            view.getCittaNuovo().setEnabled(true);
            view.getProvinciaBox().setEnabled(false);
            view.getCittaBox().setEnabled(false);
            setRegione();
            view.getNomeProvincia().setEnabled(true);
            view.getNomeCitta().setEnabled(true);
        }else{
            view.getProvinciaNuovo().setEnabled(false);
            view.getCittaNuovo().setEnabled(false);
            view.getProvinciaBox().setEnabled(true);
            view.getCittaBox().setEnabled(true);
            view.getRegione().setEnabled(false);
            view.getNomeProvincia().setEnabled(false);
            view.getNomeCitta().setEnabled(false);
        }
    }

    public void provinciaSelezionata(String provinciaSelezionata) {
        if (provinciaSelezionata != null) {
            view.setCitta(model.getCittaDAO().getCittaByProvincia(provinciaSelezionata));
        }
    }

    public void inserimentoCitta() {
        if(view.getInserireCitta()){
            view.getCittaNuovo().setEnabled(true);
            view.getCittaBox().setEnabled(false);
            view.getNomeCitta().setEnabled(true);
        }else{
            view.getCittaNuovo().setEnabled(false);
            view.getCittaBox().setEnabled(true);
            view.getNomeCitta().setEnabled(false);
        }
    }

    public void salvaInserimento() {
        boolean esito;
        if(view.getInserireCitta() && view.getInserireProvincia()){
            String provincia = view.getNuovaProvincia();
            String nomeP = view.getNomeProvinciaString();
            String regione = view.getRegioneSelezionata();

            esito = model.getProvinciaDAO().insertProvincia(provincia, nomeP, regione);

            String citta = view.getNuovaCitta();
            String nomeC = view.getNomeCittaString();

            esito = model.getCittaDAO().insertCitta(provincia, citta, nomeC);

            Integer numero;
            try {
                numero = Integer.parseInt(view.getNuovoNumero());
            } catch (Exception e) {
                numero = null;
            }
            String via = view.getNomeVia();
            String cf = view.getCf();

            if (cf == null || cf.isBlank()) {
                cf = null;
            }

            List<String> list = List.of(model.getProvinciaDAO().getProvinciaSpecifica(provincia),
                                model.getCittaDAO().getCitta(provincia, citta));

            if(numero != null && via != null){
                esito = model.getIndirizzoDAO().insertIndirizzo(provincia, citta, numero, via, Optional.ofNullable(cf));
                list.add(model.getIndirizzoDAO().getIndirizzo(provincia, citta, numero));
            }

            view.showEsito(esito, String.join("\n", list));

        }else if(view.getInserireCitta() && !(view.getInserireProvincia())){
            String provincia = view.getProvinciaSelezionata();
            String citta = view.getNuovaCitta();
            String nomeC = view.getNomeCittaString();

            esito = model.getCittaDAO().insertCitta(provincia, citta, nomeC);

            Integer numero;
            try {
                numero = Integer.parseInt(view.getNuovoNumero());
            } catch (Exception e) {
                numero = null;
            }
            String via = view.getNomeVia();
            String cf = view.getCf();

            if (cf == null || cf.isBlank()) {
                cf = null;
            }

            List<String> list = List.of(model.getCittaDAO().getCitta(provincia, citta));

            if(numero != null && via != null){
                esito = model.getIndirizzoDAO().insertIndirizzo(provincia, citta, numero, via, Optional.ofNullable(cf));
                list.add(model.getIndirizzoDAO().getIndirizzo(provincia, citta, numero));
            }

            view.showEsito(esito, String.join("\n", list));
        }else {
            String provincia = view.getProvinciaSelezionata();
            String citta = view.getCittaSelezionata();
            Integer numero;
            try {
                numero = Integer.parseInt(view.getNuovoNumero());
            } catch (Exception e) {
                numero = null;
            }
            String via = view.getNomeVia();
            String cf = view.getCf();

            if (cf == null || cf.isBlank()) {
                cf = null;
            }
            esito = model.getIndirizzoDAO().insertIndirizzo(provincia, citta, numero, via, Optional.ofNullable(cf));
            view.showEsito(esito, model.getIndirizzoDAO().getIndirizzo(provincia, citta, numero));
        }
    }

}
