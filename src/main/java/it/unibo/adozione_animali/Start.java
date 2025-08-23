package it.unibo.adozione_animali;

import it.unibo.adozione_animali.controller.impl.AggiornamentoCaratteristicaController;
import it.unibo.adozione_animali.controller.impl.CancellazioneCaratteristicaController;
import it.unibo.adozione_animali.controller.impl.InserimentoCaratteristicaController;
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

        try (Connection conn = DBConfig.getConnection();) {

            //DSLContext create = DSL.using(conn, SQLDialect.MYSQL);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
