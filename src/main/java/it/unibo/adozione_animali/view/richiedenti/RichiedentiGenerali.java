package it.unibo.adozione_animali.view.richiedenti;

import it.unibo.adozione_animali.view.personale.InsertPersonalePanel;
import it.unibo.adozione_animali.view.personale.RemovePersonalePanel;
import it.unibo.adozione_animali.view.personale.ShowPersonalePanel;
import it.unibo.adozione_animali.view.personale.UpdatePersonalePanel;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;

public class RichiedentiGenerali extends JPanel {

    JPanel cards;
    JComboBox<String> comboBox;

    public RichiedentiGenerali() {
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createTitledBorder(
                        BorderFactory.createEtchedBorder(),
                        "Richiedenti",
                        TitledBorder.CENTER,
                        TitledBorder.TOP
                ),
                BorderFactory.createEmptyBorder(25, 10, 10, 10)
        ));

        cards = new JPanel(new CardLayout());

        String[] options = {"--Seleziona--", "Visualizza tutti i Richiedenti", "Inserisci Richiedente",
                "Rimuovi Richiedente", "Aggiorna Richiedente"};
        comboBox = new JComboBox<>(options);

        JPanel selectPanel = new JPanel();
        JPanel insertPanel = new InsertRichiedentePanel();
        JPanel removePanel = new RemoveRichiedentePanel();
        JPanel updatePanel = new UpdateRichiedentePanel();
        JPanel showPanel = new ShowRichiedentiPanel();

        selectPanel.add(Box.createVerticalStrut(1));

        cards.add(insertPanel, "Inserisci Richiedente");
        cards.add(removePanel, "Rimuovi Richiedente");
        cards.add(selectPanel, "--Seleziona--");
        cards.add(updatePanel, "Aggiorna Richiedente");
        cards.add(showPanel, "Visualizza tutti i Richiedenti");

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
