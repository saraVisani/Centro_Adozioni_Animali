package it.unibo.adozione_animali.view.razza;

import javax.swing.BorderFactory;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;
import java.awt.*;

public class GestioneSpecifiche extends JPanel {

    JPanel cards;
    JComboBox<String> comboBox;

    public GestioneSpecifiche() {
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createTitledBorder(
                        BorderFactory.createEtchedBorder(),
                        "Specifiche di Razza",
                        TitledBorder.CENTER,
                        TitledBorder.TOP
                ),
                BorderFactory.createEmptyBorder(25, 10, 10, 10)
        ));

        cards = new JPanel(new CardLayout());

        String[] options = {"--Seleziona--", "Inserisci Specifica di Razza", "Rimuovi Specifica di Razza"};
        comboBox = new JComboBox<>(options);

        JPanel selectPanel = new JPanel();
        JPanel insertPanel = new InsertSpecificaPanel();
        JPanel removePanel = new DeleteSpecificaPanel();

        cards.add(selectPanel, "--Seleziona--");
        cards.add(insertPanel, "Inserisci Specifica di Razza");
        cards.add(removePanel, "Rimuovi Specifica di Razza");

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
