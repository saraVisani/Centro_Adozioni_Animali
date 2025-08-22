package it.unibo.adozione_animali.view.personale;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;

public class GestioneTurniTaskPanel extends JPanel {

    JPanel cards;
    JComboBox<String> comboBox;


    public GestioneTurniTaskPanel() {

        setLayout(new BorderLayout());
        setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createTitledBorder(
                        BorderFactory.createEtchedBorder(),
                        "Gestione Turni e Task",
                        TitledBorder.CENTER,
                        TitledBorder.TOP
                ),
                BorderFactory.createEmptyBorder(25, 10, 10, 10)
        ));

        cards = new JPanel(new CardLayout());

        String[] options = {"--Seleziona--", "Turni", "Task"};
        comboBox = new JComboBox<>(options);

        JPanel selectPanel = new JPanel();
        JPanel turnoPanel = new TurnoPanel();
        JPanel taskPanel = new TaskPanel();

        selectPanel.add(Box.createVerticalStrut(1));

        cards.add(turnoPanel, "Turni");
        cards.add(taskPanel, "Task");
        cards.add(selectPanel, "--Seleziona--");

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
