package it.unibo.adozione_animali.controller.impl;

import java.util.List;

import it.unibo.adozione_animali.model.impl.Model;
import it.unibo.adozione_animali.util.Enum.TipoSpazio;
import it.unibo.adozione_animali.util.ItemSelezionabile;
import it.unibo.adozione_animali.view.pulsanti.spazio.Add;

public class InserimentoSpazioController {

    Model model;
    Add view;

    public InserimentoSpazioController(Model model, Add view){
        this.model = model;
        this.view = view;
        this.view.setTipo(List.of(
            new ItemSelezionabile(TipoSpazio.ESTERNO.name(), "Esterno"),
            new ItemSelezionabile(TipoSpazio.GABBIA.name(), "Gabbia"),
            new ItemSelezionabile(TipoSpazio.INTERNO.name(), "Interno")
        ));
    }

    public void salvaInserimento() {
        Integer cod = view.getCodice();
        Integer dimension = view.getDimensione();
        String tipo = view.getTipo();

        view.showEsito(model.getSpazioDAO().insertSpazio(cod, tipo, dimension));
    }

}
