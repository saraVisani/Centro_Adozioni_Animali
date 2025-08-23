package it.unibo.adozione_animali.controller.impl;

import java.util.List;

import it.unibo.adozione_animali.model.impl.Model;
import it.unibo.adozione_animali.view.pulsanti.centri.Add;

public class AddCentroController {
    private final Model model;
    private final Add view;

    public AddCentroController(Model model, Add view) {
        this.model = model;
        this.view = view;
        inizializza();
    }

    private void inizializza() {
        // Popola le province all’avvio
        view.setProvince(model.getProvinciaDAO().getProvince());
    }

    public void provinciaSelezionata(String provinciaCodice) {
        if (provinciaCodice != null) {
            view.setCitta(model.getCittaDAO().getCittaByProvincia(provinciaCodice));
        }
    }

    public void cittaSelezionata(String provinciaCodice, String cittaCodice) {
        if (provinciaCodice != null && cittaCodice != null) {
            List<String> numeri = model.getIndirizzoDAO().getNumeriByCitta(provinciaCodice, cittaCodice);
            view.setNumeri(numeri);
        }
    }

    public void salvaInserimento() {
        String provincia = view.getProvinciaSelezionata();
        String citta = view.getCittaSelezionata();
        String numero = view.getNumeroSelezionato();
        try {
            Integer.valueOf(numero);
        } catch (NumberFormatException e) {
            view.showEsito(false);
            return;
        }
        String nome = view.getNome();
        Integer capienza = view.getCapienza();
        List<String> codiciSpecifici = view.getCodiciSpecificiSelezionati();

        view.showEsito(model.getCentroDAO().insertCentro(nome, capienza.intValue(), provincia, citta, Integer.parseInt(numero), codiciSpecifici));
    }
}
