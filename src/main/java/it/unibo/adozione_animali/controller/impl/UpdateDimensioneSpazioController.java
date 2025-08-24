package it.unibo.adozione_animali.controller.impl;

import it.unibo.adozione_animali.model.impl.Model;
import it.unibo.adozione_animali.view.pulsanti.spazio.UpdateDimensione;

public class UpdateDimensioneSpazioController {

    Model model;
    UpdateDimensione view;

    public UpdateDimensioneSpazioController(Model model, UpdateDimensione view){
        this.model = model;
        this.view = view;
        this.view.setCodici(this.model.getSpazioDAO().getCodici());
    }

    public void salvaAggiornamento() {
        Integer cod = view.getCodice();
        Integer dimension = view.getDimensione();

        view.showEsito(model.getSpazioDAO().updateSpazioDimensione(cod, dimension));
    }
}
