package it.unibo.adozione_animali.view.pulsanti.spazio;

import java.awt.*;
import java.util.List;
import javax.swing.*;

import it.unibo.adozione_animali.controller.impl.InserimentoSpazioController;
import it.unibo.adozione_animali.util.ColorUtils;
import it.unibo.adozione_animali.util.ItemSelezionabile;

public class Add extends JPanel {

    private JComboBox<ItemSelezionabile> tipo;
    private JTextField dimensione;
    private JTextField codSpazio;
    private JButton inserisciBtn;
    private InserimentoSpazioController controller;

    public Add() {
        setBackground(ColorUtils.fromHex("6B82FF"));
        setLayout(new BorderLayout(10, 10));

        JLabel title = new JLabel("Inserimento Caratteristiche");
        title.setFont(new Font("Arial", Font.BOLD, 18));
        title.setHorizontalAlignment(SwingConstants.CENTER);
        add(title, BorderLayout.NORTH);

        JPanel tablePanel = new JPanel(new GridBagLayout());
        tablePanel.setBackground(ColorUtils.fromHex("6B82FF"));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Header
        String[] headers = { "Codice Spazio", "Tipo", "Dimensione"};
        gbc.gridy = 0;
        for (int i = 0; i < headers.length; i++) {
            gbc.gridx = i;
            JLabel lbl = new JLabel(headers[i]);
            lbl.setForeground(Color.WHITE);
            lbl.setFont(new Font("Arial", Font.BOLD, 14));
            tablePanel.add(lbl, gbc);
        }

        inserisciBtn = new JButton("Inserisci");
        inserisciBtn.setEnabled(false);

        // Una sola riga di selezione
        gbc.gridy = 1;

        // Provincia
        gbc.gridx = 0;
        codSpazio = new JTextField(5);
        tablePanel.add(codSpazio, gbc);

        // Città
        gbc.gridx = 1;
        tipo = new JComboBox<>();
        tablePanel.add(tipo, gbc);

        // Numero civico
        gbc.gridx = 2;
        dimensione = new JTextField(5);
        tablePanel.add(dimensione, gbc);

        // Listener combo che chiamano il controller
        tipo.addActionListener(e -> aggiornaStatoPulsante());

        // JTextField nome
        codSpazio.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
            public void insertUpdate(javax.swing.event.DocumentEvent e) { aggiornaStatoPulsante(); }
            public void removeUpdate(javax.swing.event.DocumentEvent e) { aggiornaStatoPulsante(); }
            public void changedUpdate(javax.swing.event.DocumentEvent e) { aggiornaStatoPulsante(); }
        });

        // JTextField capienza
        dimensione.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
            public void insertUpdate(javax.swing.event.DocumentEvent e) { aggiornaStatoPulsante(); }
            public void removeUpdate(javax.swing.event.DocumentEvent e) { aggiornaStatoPulsante(); }
            public void changedUpdate(javax.swing.event.DocumentEvent e) { aggiornaStatoPulsante(); }
        });

        inserisciBtn.addActionListener(e -> {
            if (controller != null) controller.salvaInserimento();
        });

        add(new JScrollPane(tablePanel), BorderLayout.CENTER);
        add(inserisciBtn, BorderLayout.SOUTH);
    }

    // --- Metodi per collegare il controller ---
    public void setController(InserimentoSpazioController controller) {
        this.controller = controller;
    }

    // --- Metodi pubblici per aggiornare i dati delle combo ---
    public void setTipo(List<ItemSelezionabile> valori) {
        setComboBoxWithEmptyFirst(tipo, valori, new ItemSelezionabile("", "--select--"));
    }

    // --- Getters per valori selezionati ---
    public String getTipo() {
        ItemSelezionabile selected = (ItemSelezionabile) tipo.getSelectedItem();
        return selected != null ? selected.getCodice() : null;
    }

    public Integer getCodice() {
        try {
            return Integer.parseInt(codSpazio.getText());
        } catch (NumberFormatException e) {
            return null; // oppure 0 se preferisci
        }
    }

    public Integer getDimensione() {
        try {
            return Integer.parseInt(dimensione.getText());
        } catch (NumberFormatException e) {
            return null; // oppure 0 se preferisci
        }
    }

    private void aggiornaStatoPulsante() {
        boolean completo = getTipo() != null
                && !getTipo().equals("--select--")
                && !getTipo().isBlank()
                && getCodice() != null
                && getCodice() >= 0
                && getDimensione() != null
                && getDimensione() > 0;

        inserisciBtn.setEnabled(completo);
    }

    private <T> void setComboBoxWithEmptyFirst(JComboBox<T> combo, List<T> items, T emptyItem) {
        DefaultComboBoxModel<T> model = new DefaultComboBoxModel<>();

        // Aggiungi l'elemento vuoto come primo
        model.addElement(emptyItem);

        // Aggiungi tutti gli altri elementi
        if (items != null) {
            for (T item : items) {
                model.addElement(item);
            }
        }

        combo.setModel(model);
        combo.setEnabled(true);
    }

    public void showEsito(boolean esito) {
        if (esito) {
            JOptionPane.showMessageDialog(
                this,
                "Operazione completata con successo!",
                "Successo",
                JOptionPane.INFORMATION_MESSAGE
            );
        } else {
            JOptionPane.showMessageDialog(
                this,
                "Si è verificato un errore durante l'operazione.",
                "Errore",
                JOptionPane.ERROR_MESSAGE
            );
        }
    }
}
