package it.unibo.adozione_animali.view.personale;

import javax.swing.*;
import java.awt.*;

public class TaskPanel extends JPanel {

    JPanel cards;
    JComboBox<String> comboBox;

    public TaskPanel() {
        setLayout(new BorderLayout());

        cards = new JPanel(new CardLayout());

        String[] options = {"Inserisci Task", "Rimuovi Task", "Aggiorna Task",
                "Visualizza Task di un Centro"};
        comboBox = new JComboBox<>(options);

        JPanel insertPanel = new InsertTaskPanel();
        JPanel removePanel = new RemoveTaskPanel();
        JPanel updatePanel = new UpdateTaskPanel();
        JPanel showPanel = new ShowTaskPanel();

        cards.add(insertPanel, "Inserisci Task");
        cards.add(removePanel, "Rimuovi Task");
        cards.add(showPanel, "Visualizza Task di un Centro");
        cards.add(updatePanel, "Aggiorna Task");

        ((CardLayout) cards.getLayout()).show(cards, "Inserisci Turno");

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
