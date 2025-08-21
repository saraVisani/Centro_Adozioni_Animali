package it.unibo.adozione_animali.view.animale;

import it.unibo.adozione_animali.view.DeleteRichiestaPanel;
import it.unibo.adozione_animali.view.InserimentoRichiestaPanel;
import it.unibo.adozione_animali.view.UpdateRichiestaPanel;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;

public class GestioneComponentiPanel extends JPanel {

    JPanel cards;
    JComboBox comboBox;

    public GestioneComponentiPanel() {
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createTitledBorder(
                        BorderFactory.createEtchedBorder(),
                        "Gestione Componenti",
                        TitledBorder.CENTER,
                        TitledBorder.TOP
                ),
                BorderFactory.createEmptyBorder(25, 10, 10, 10)
        ));

        cards = new JPanel(new CardLayout());

        String[] options = {"--Seleziona--", "Inserisci Componente", "Rimuovi Componente",
                "Aggiorna Componente"};
        comboBox = new JComboBox<>(options);

        JPanel selectPanel = new JPanel();
        JPanel insertPanel = new InserimentoComponentePanel();
        JPanel deletePanel = new RemoveComponentePanel();
        JPanel updatePanel = new UpdateComponentePanel();

        selectPanel.add(Box.createVerticalStrut(1));

        cards.add(insertPanel, "Inserisci Componente");
        cards.add(deletePanel, "Rimuovi Componente");
        cards.add(selectPanel, "--Seleziona--");
        cards.add(updatePanel, "Aggiorna Componente");

        ((CardLayout) cards.getLayout()).show(cards, "--Seleziona--");

        // Listener sulla comboBox
        comboBox.addActionListener(e -> {
            String selezionato = (String) comboBox.getSelectedItem();
            CardLayout cl = (CardLayout) cards.getLayout();
            cl.show(cards, selezionato);
            cards.revalidate();
            cards.repaint();
        });

        // Esempio: aggiungo comboBox in alto e le card al centro
        add(comboBox, BorderLayout.NORTH);
        add(cards, BorderLayout.CENTER);
    }

}
