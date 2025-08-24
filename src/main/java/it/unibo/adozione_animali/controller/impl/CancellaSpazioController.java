package it.unibo.adozione_animali.controller.impl;

import it.unibo.adozione_animali.model.impl.Model;
import it.unibo.adozione_animali.view.pulsanti.spazio.DeleteSpazio;

public class CancellaSpazioController {

    Model model;
    DeleteSpazio view;

    public CancellaSpazioController(Model model, DeleteSpazio view){
        this.model = model;
        this.view = view;
        this.view.setCodici(this.model.getSpazioDAO().getCodici());
    }

    public void salvaCancellazione() {
        Integer cod = view.getCodice();

        view.showEsito(model.getSpazioDAO().deleteSpazio(cod));
    }
}
