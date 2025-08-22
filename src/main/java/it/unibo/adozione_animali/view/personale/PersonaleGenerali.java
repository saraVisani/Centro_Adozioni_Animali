package it.unibo.adozione_animali.view.personale;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;

public class PersonaleGenerali extends JPanel {

    JPanel cards;
    JComboBox<String> comboBox;

    public PersonaleGenerali() {

        setLayout(new BorderLayout());
        setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createTitledBorder(
                        BorderFactory.createEtchedBorder(),
                        "Personale dei Centri",
                        TitledBorder.CENTER,
                        TitledBorder.TOP
                ),
                BorderFactory.createEmptyBorder(25, 10, 10, 10)
        ));

        cards = new JPanel(new CardLayout());

        String[] options = {"--Seleziona--", "Visualizza il Personale di tutti i Centri", "Inserisci Personale",
        "Rimuovi Personale", "Aggiorna Personale"};
        comboBox = new JComboBox<>(options);

        JPanel selectPanel = new JPanel();
        JPanel insertPanel = new InsertPersonalePanel();
        JPanel removePanel = new RemovePersonalePanel();
        JPanel updatePanel = new UpdatePersonalePanel();
        JPanel showPanel = new ShowPersonalePanel();

        selectPanel.add(Box.createVerticalStrut(1));

        cards.add(insertPanel, "Inserisci Personale");
        cards.add(removePanel, "Rimuovi Personale");
        cards.add(selectPanel, "--Seleziona--");
        cards.add(updatePanel, "Aggiorna Personale");
        cards.add(showPanel, "Visualizza il Personale di tutti i Centri");

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
