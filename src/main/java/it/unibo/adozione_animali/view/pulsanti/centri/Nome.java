package it.unibo.adozione_animali.view.pulsanti.centri;

import java.awt.*;
import java.util.List;

import javax.swing.*;

import it.unibo.adozione_animali.controller.impl.AggiornaNomeCentroController;
import it.unibo.adozione_animali.util.ColorUtils;
import it.unibo.adozione_animali.util.ItemSelezionabile;

public class Nome extends JPanel {

    private JComboBox<ItemSelezionabile> provinciaBox;
    private JComboBox<ItemSelezionabile> cittaBox;
    private JComboBox<String> numeroBox;
    private JTextField nomeField;
    private JButton inserisciBtn;
    private AggiornaNomeCentroController controller;

    public Nome() {
        setLayout(new BorderLayout(10, 10));

        JLabel title = new JLabel("Update Nome Centro");
        title.setFont(new Font("Arial", Font.BOLD, 18));
        title.setHorizontalAlignment(SwingConstants.CENTER);
        add(title, BorderLayout.NORTH);

        JPanel tablePanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Header
        String[] headers = { "Provincia", "Città", "Numero civico",
                                "Nome"};
        gbc.gridy = 0;
        for (int i = 0; i < headers.length; i++) {
            gbc.gridx = i;
            JLabel lbl = new JLabel(headers[i]);
            lbl.setFont(new Font("Arial", Font.BOLD, 14));
            tablePanel.add(lbl, gbc);
        }

        inserisciBtn = new JButton("Inserisci");
        inserisciBtn.setEnabled(false);

        // Una sola riga di selezione
        gbc.gridy = 1;

        // Provincia
        gbc.gridx = 0;
        provinciaBox = new JComboBox<>();
        tablePanel.add(provinciaBox, gbc);

        // Città
        gbc.gridx = 1;
        cittaBox = new JComboBox<>();
        cittaBox.setEnabled(false);
        tablePanel.add(cittaBox, gbc);

        // Numero civico
        gbc.gridx = 2;
        numeroBox = new JComboBox<>();
        numeroBox.setEnabled(false);
        tablePanel.add(numeroBox, gbc);

        // Nome (textfield)
        gbc.gridx = 3;
        nomeField = new JTextField(10);
        tablePanel.add(nomeField, gbc);

        // Listener combo che chiamano il controller
        provinciaBox.addActionListener(e -> {
            if (controller != null) {
                ItemSelezionabile selected = (ItemSelezionabile) provinciaBox.getSelectedItem();
                controller.provinciaSelezionata(selected != null ? selected.getCodice() : null);
            }
            aggiornaStatoPulsante();
        });

        cittaBox.addActionListener(e -> {
            if (controller != null) {
                ItemSelezionabile provincia = (ItemSelezionabile) provinciaBox.getSelectedItem();
                ItemSelezionabile citta = (ItemSelezionabile) cittaBox.getSelectedItem();
                controller.cittaSelezionata(
                    provincia != null ? provincia.getCodice() : null,
                    citta != null ? citta.getCodice() : null
                );
            }
            aggiornaStatoPulsante();
        });

        // JTextField nome
        nomeField.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
            public void insertUpdate(javax.swing.event.DocumentEvent e) { aggiornaStatoPulsante(); }
            public void removeUpdate(javax.swing.event.DocumentEvent e) { aggiornaStatoPulsante(); }
            public void changedUpdate(javax.swing.event.DocumentEvent e) { aggiornaStatoPulsante(); }
        });

        inserisciBtn.addActionListener(e -> {
            if (controller != null) controller.salvaAggiornamento();
        });

        add(new JScrollPane(tablePanel), BorderLayout.CENTER);
        add(inserisciBtn, BorderLayout.SOUTH);
    }

    // --- Metodi per collegare il controller ---
    public void setController(AggiornaNomeCentroController controller) {
        this.controller = controller;
    }

    // --- Metodi pubblici per aggiornare i dati delle combo ---
    public void setProvince(List<ItemSelezionabile> province) {
        setComboBoxWithEmptyFirst(provinciaBox, province, new ItemSelezionabile("", "--select--"));
    }

    public void setCitta(List<ItemSelezionabile> citta) {
        setComboBoxWithEmptyFirst(cittaBox, citta, new ItemSelezionabile("", "--select--"));
    }

    public void setNumeri(List<String> numeri) {
        setComboBoxWithEmptyFirst(numeroBox, numeri, "--select--");
    }

    // --- Getters per valori selezionati ---
    public String getProvinciaSelezionata() {
        ItemSelezionabile selected = (ItemSelezionabile) provinciaBox.getSelectedItem();
        return selected != null ? selected.getCodice() : null;
    }

    public String getCittaSelezionata() {
        ItemSelezionabile selected = (ItemSelezionabile) cittaBox.getSelectedItem();
        return selected != null ? selected.getCodice() : null;
    }

    public String getNumeroSelezionato() {
        return (String) numeroBox.getSelectedItem();
    }

    public String getNome() {
        return nomeField.getText();
    }

    private boolean isValid(ItemSelezionabile item) {
        return item != null && item.getCodice() != null
            && !item.getCodice().isEmpty() && !item.getCodice().equals("--select--");
    }

    private boolean isValidString(String s) {
        return s != null && !s.isEmpty() && !s.equals("--select--");
    }

    private boolean isNumber(String s) {
        if (s == null) return false;
        try {
            return Integer.parseInt(s) > 0;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private void aggiornaStatoPulsante() {
        ItemSelezionabile selectedProvincia = (ItemSelezionabile) provinciaBox.getSelectedItem();
        ItemSelezionabile selectedCitta = (ItemSelezionabile) cittaBox.getSelectedItem();

        String numero = getNumeroSelezionato();
        String nome = getNome();

        boolean control = isValid(selectedProvincia)
            && isValid(selectedCitta)
            && isValidString(nome)
            && isNumber(numero); // qui controlliamo che sia un numero valido

        inserisciBtn.setEnabled(control);
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
