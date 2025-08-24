package it.unibo.adozione_animali.controller.impl;

import java.util.List;

import it.unibo.adozione_animali.model.impl.Model;
import it.unibo.adozione_animali.util.Enum.TipoSpazio;
import it.unibo.adozione_animali.view.pulsanti.spazio.Add;

public class InserimentoSpazioController {

    Model model;
    Add view;

    public InserimentoSpazioController(Model model, Add view){
        this.model = model;
        this.view = view;
        this.view.setTipo(List.of(TipoSpazio.ESTERNO.name(), TipoSpazio.GABBIA.name(), TipoSpazio.INTERNO.name()));
    }

    public void salvaInserimento() {
        Integer cod = view.getCodice();
        Integer dimension = view.getDimensione();
        String tipo = view.getTipo();

        view.showEsito(model.getSpazioDAO().insertSpazio(cod, tipo, dimension));
    }

}
