package it.unibo.adozione_animali.view.personale;

import javax.swing.*;
import java.awt.*;

public class TurnoPanel extends JPanel {

    JPanel cards;
    JComboBox<String> comboBox;

    public TurnoPanel() {
        setLayout(new BorderLayout());

        cards = new JPanel(new CardLayout());

        String[] options = {"Visualizza tutti i turni", "Inserisci Turno", "Rimuovi Turno"};
        comboBox = new JComboBox<>(options);

        JPanel showPanel = new ShowTurniLavorativi();
        JPanel insertPanel = new InsertTurnoPanel();
        JPanel removePanel = new RemoveTurnoPanel();

        cards.add(showPanel, "Visualizza tutti i turni");
        cards.add(insertPanel, "Inserisci Turno");
        cards.add(removePanel, "Rimuovi Turno");

        ((CardLayout) cards.getLayout()).show(cards, "Visualizza tutti i turni");

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
