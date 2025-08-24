package it.unibo.adozione_animali;

import it.unibo.adozione_animali.controller.impl.AddCentroController;
import it.unibo.adozione_animali.controller.impl.AggiornaNomeCentroController;
import it.unibo.adozione_animali.controller.impl.AggiornamentoCaratteristicaController;
import it.unibo.adozione_animali.controller.impl.CancellaSpazioController;
import it.unibo.adozione_animali.controller.impl.CancellazioneCaratteristicaController;
import it.unibo.adozione_animali.controller.impl.DeleteCentroController;
import it.unibo.adozione_animali.controller.impl.DeleteSpecieCentroController;
import it.unibo.adozione_animali.controller.impl.InserimentoCaratteristicaController;
import it.unibo.adozione_animali.controller.impl.InserimentoSpazioController;
import it.unibo.adozione_animali.controller.impl.InserimentoSpecieCentroController;
import it.unibo.adozione_animali.controller.impl.StatisticheRicercaController;
import it.unibo.adozione_animali.controller.impl.UpdateCapienzaController;
import it.unibo.adozione_animali.controller.impl.UpdateDimensioneSpazioController;
import it.unibo.adozione_animali.controller.impl.UpdateStatisticaController;
import it.unibo.adozione_animali.controller.impl.UpdateTipoDimensioneController;
import it.unibo.adozione_animali.controller.impl.UpdateTipoSpazioController;
import it.unibo.adozione_animali.model.impl.Model;
import it.unibo.adozione_animali.util.DBConfig;
import it.unibo.adozione_animali.view.MainMenu;

import java.sql.Connection;

public class Start {

    public static void main(String[] args) {

        Model model = new Model();
        MainMenu view = new MainMenu();
        view.getGestioneCarPanel().getInserimentoView().setController(new InserimentoCaratteristicaController(model, view.getGestioneCarPanel().getInserimentoView()));
        view.getGestioneCarPanel().getCancellazioneView().setController(new CancellazioneCaratteristicaController(model, view.getGestioneCarPanel().getCancellazioneView()));
        view.getGestioneCarPanel().getAggiornamentoView().setController(new AggiornamentoCaratteristicaController(model, view.getGestioneCarPanel().getAggiornamentoView()));
        view.getCentriPanel().getAddPanel().setController(new AddCentroController(model, view.getCentriPanel().getAddPanel()));
        view.getCentriPanel().getAddSpeciePanel().setController(new InserimentoSpecieCentroController(model, view.getCentriPanel().getAddSpeciePanel()));
        view.getCentriPanel().getCapienzaPanel().setController(new UpdateCapienzaController(model, view.getCentriPanel().getCapienzaPanel()));
        view.getCentriPanel().getDeletePanel().setController(new DeleteCentroController(model, view.getCentriPanel().getDeletePanel()));
        view.getCentriPanel().getDeleteSpeciePanel().setController(new DeleteSpecieCentroController(model, view.getCentriPanel().getDeleteSpeciePanel()));
        view.getCentriPanel().getNomePanel().setController(new AggiornaNomeCentroController(model, view.getCentriPanel().getNomePanel()));
        view.getSpaziPanel().getAddPanel().setController(new InserimentoSpazioController(model, view.getSpaziPanel().getAddPanel()));
        view.getSpaziPanel().getDeletePanel().setController(new CancellaSpazioController(model, view.getSpaziPanel().getDeletePanel()));
        view.getSpaziPanel().getUpdateDimensionePanel().setController(new UpdateDimensioneSpazioController(model, view.getSpaziPanel().getUpdateDimensionePanel()));
        view.getSpaziPanel().getUpdateTipoPanel().setController(new UpdateTipoSpazioController(model, view.getSpaziPanel().getUpdateTipoPanel()));
        view.getSpaziPanel().getUpdateTipoDimensionePanel().setController(new UpdateTipoDimensioneController(model, view.getSpaziPanel().getUpdateTipoDimensionePanel()));
        new StatisticheRicercaController(view.getRicercaStatPanel());
        view.getAggiornaPanel().setController(new UpdateStatisticaController(model, view.getAggiornaPanel()));

        try (Connection conn = DBConfig.getConnection();) {

            //DSLContext create = DSL.using(conn, SQLDialect.MYSQL);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
