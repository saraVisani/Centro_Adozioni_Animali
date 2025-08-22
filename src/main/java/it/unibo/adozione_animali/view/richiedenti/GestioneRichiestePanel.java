package it.unibo.adozione_animali.view.richiedenti;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;

public class GestioneRichiestePanel extends JPanel {

    JPanel cards;
    JComboBox<String> comboBox;

    public GestioneRichiestePanel() {
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createTitledBorder(
                        BorderFactory.createEtchedBorder(),
                        "Gestione Richieste",
                        TitledBorder.CENTER,
                        TitledBorder.TOP
                ),
                BorderFactory.createEmptyBorder(25, 10, 10, 10)
        ));

        cards = new JPanel(new CardLayout());

        String[] options = {"--Seleziona--", "Inserisci Richiesta", "Rimuovi Richiesta",
                 "Aggiorna Richiesta"};
        comboBox = new JComboBox<>(options);

        JPanel selectPanel = new JPanel();
        JPanel insertPanel = new InserimentoRichiestaPanel();
        JPanel deletePanel = new DeleteRichiestaPanel();
        JPanel updatePanel = new UpdateRichiestaPanel();

        selectPanel.add(Box.createVerticalStrut(1));

        cards.add(insertPanel, "Inserisci Richiesta");
        cards.add(deletePanel, "Rimuovi Richiesta");
        cards.add(selectPanel, "--Seleziona--");
        cards.add(updatePanel, "Aggiorna Richiesta");

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
