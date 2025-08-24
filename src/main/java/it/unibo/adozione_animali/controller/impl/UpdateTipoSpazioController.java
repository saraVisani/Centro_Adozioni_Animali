package it.unibo.adozione_animali.controller.impl;

import it.unibo.adozione_animali.model.impl.Model;
import it.unibo.adozione_animali.view.pulsanti.spazio.UpdateTipoSpazio;

public class UpdateTipoSpazioController {
    Model model;
    UpdateTipoSpazio view;

    public UpdateTipoSpazioController(Model model, UpdateTipoSpazio view){
        this.model = model;
        this.view = view;
        this.view.setCodice(this.model.getSpazioDAO().getCodici());
    }

    public void codiceSelected(Integer codice) {
        view.setTipo(this.model.getSpazioDAO().getTipo(codice));
    }

    public void salvaAggiornamento() {
        Integer cod = view.getCodice();
        String tipo = view.getTipo();

        view.showEsito(model.getSpazioDAO().updateSpazioTipo(cod, tipo));
    }
}
