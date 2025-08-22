package it.unibo.adozione_animali.view.animale;

import javax.swing.*;
import java.awt.*;

public class UpdatePaginaPanel extends JPanel {

    JComboBox<String> comboBox;
    JPanel cards;

    public UpdatePaginaPanel() {
        setLayout(new BorderLayout());

        cards = new JPanel(new CardLayout());

        String[] options = {"Aggiorna Problema", "Aggiorna Esame", "Aggiorna Ricovero"};
        comboBox = new JComboBox<>(options);

        JPanel problemaPanel = new UpdateProblemaPanel();
        JPanel esamePanel = new UpdateEsamePanel();
        JPanel ricoveroPanel = new UpdateRicoveroPanel();


        cards.add(problemaPanel, "Aggiorna Problema");
        cards.add(esamePanel, "Aggiorna Esame");
        cards.add(ricoveroPanel, "Aggiorna Ricovero");

        ((CardLayout) cards.getLayout()).show(cards, "Aggiorna Problema");

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
