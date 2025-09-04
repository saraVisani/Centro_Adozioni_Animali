package it.unibo.adozione_animali.controller.impl;

import java.util.List;

import it.unibo.adozione_animali.model.impl.Model;
import it.unibo.adozione_animali.view.pulsanti.centri.DeleteSpecie;

public class DeleteSpecieCentroController {

    private final Model model;
    private final DeleteSpecie view;

    public DeleteSpecieCentroController(Model model, DeleteSpecie view) {
        this.model = model;
        this.view = view;
        inizializza();
    }

    private void inizializza() {
        // Popola le province all’avvio
        view.setProvince(model.getCentroDAO().getProvince());
    }

    public void provinciaSelezionata(String provinciaCodice) {
        if (provinciaCodice != null) {
            view.setCitta(model.getCentroDAO().getCittaByProvincia(provinciaCodice));
        }
    }

    public void cittaSelezionata(String provinciaCodice, String cittaCodice) {
        if (provinciaCodice != null && cittaCodice != null) {
            List<String> numeri = model.getCentroDAO().getNumeriByCitta(provinciaCodice, cittaCodice);
            view.setNumeri(numeri);
        }
    }

    public void salvaDelete() {
        String provincia = view.getProvinciaSelezionata();
        String citta = view.getCittaSelezionata();
        String numero = view.getNumeroSelezionato();
        try {
            Integer.valueOf(numero);
        } catch (NumberFormatException e) {
            view.showEsito(false);
            return;
        }
        List<String> codiciSpecifici = view.getCodiciSpecificiSelezionati();

        view.showEsito(model.getCentroDAO().updateCentroTogliSpecie(provincia, citta, Integer.parseInt(numero), codiciSpecifici));
    }
}
