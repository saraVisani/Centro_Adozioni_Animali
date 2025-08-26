package it.unibo.adozione_animali.view.razza;

import it.unibo.adozione_animali.view.personale.InsertTurnoPanel;
import it.unibo.adozione_animali.view.personale.RemoveTurnoPanel;
import it.unibo.adozione_animali.view.personale.ShowTurniLavorativi;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;

public class RazzaGenerali extends JPanel {

    JPanel cards;
    JComboBox<String> comboBox;

    public RazzaGenerali() {
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createTitledBorder(
                        BorderFactory.createEtchedBorder(),
                        "Razza Animali",
                        TitledBorder.CENTER,
                        TitledBorder.TOP
                ),
                BorderFactory.createEmptyBorder(25, 10, 10, 10)
        ));

        cards = new JPanel(new CardLayout());

        String[] options = {"--Seleziona--", "Inserisci Nuova Razza", "Rimuovi Razza", "Visualizza le Razze nei Centri"};
        comboBox = new JComboBox<>(options);

        JPanel selectPanel = new JPanel();
        JPanel showPanel = new ShowRazze();
        JPanel insertPanel = new InsertRazzaPanel();
        JPanel removePanel = new DeleteRazzaPanel();

        cards.add(selectPanel, "--Seleziona--");
        cards.add(insertPanel, "Inserisci Nuova Razza");
        cards.add(removePanel, "Rimuovi Razza");
        cards.add(showPanel, "Visualizza le Razze nei Centri");

        ((CardLayout) cards.getLayout()).show(cards, "--Seleziona--");

        // Listener sulla comboBox
        comboBox.addActionListener(e -> {
            String selezionato = (String) comboBox.getSelectedItem();
            CardLayout cl = (CardLayout) cards.getLayout();
            cl.show(cards, selezionato);
            cards.revalidate();
            cards.repaint();
        });

        add(comboBox, BorderLayout.NORTH);
        add(cards, BorderLayout.CENTER);
    }
}
