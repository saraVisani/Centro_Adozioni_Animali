package it.unibo.adozione_animali.controller.impl;

import java.util.List;

import it.unibo.adozione_animali.model.impl.Model;
import it.unibo.adozione_animali.view.pulsanti.centri.Capienza;

public class UpdateCapienzaController {

    private final Model model;
    private final Capienza view;

    public UpdateCapienzaController(Model model, Capienza view) {
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

    public void salvaAggiornamento() {
        String provincia = view.getProvinciaSelezionata();
        String citta = view.getCittaSelezionata();
        String numero = view.getNumeroSelezionato();
        try {
            Integer.valueOf(numero);
        } catch (NumberFormatException e) {
            view.showEsito(false);
            return;
        }
        Integer capienza = view.getCapienza();
        view.showEsito(model.getCentroDAO().updateCentroCapienza(provincia, citta, Integer.parseInt(numero), capienza));
    }
}
