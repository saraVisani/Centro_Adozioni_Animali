package it.unibo.adozione_animali.view.pulsanti.centri;

import java.awt.*;
import java.util.List;

import javax.swing.*;

import it.unibo.adozione_animali.controller.impl.AddCentroController;
import it.unibo.adozione_animali.util.ColorUtils;
import it.unibo.adozione_animali.util.Enum.Specie;
import it.unibo.adozione_animali.util.ItemSelezionabile;

public class Add extends JPanel {

    private JComboBox<ItemSelezionabile> provinciaBox;
    private JComboBox<ItemSelezionabile> cittaBox;
    private JComboBox<String> numeroBox;
    private JTextField nomeField;
    private JTextField capienzaField;
    private DefaultListModel<String> listModel;
    private JList<String> codiceSpecList;
    private JButton inserisciBtn;
    private AddCentroController controller;

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
        String[] headers = { "Provincia", "Città", "Numero civico",
                                "Nome", "Capienza", "Specie" };
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

        // Capienza (textfield)
        gbc.gridx = 4;
        capienzaField = new JTextField(5);
        tablePanel.add(capienzaField, gbc);

        // Lista codici specie
        gbc.gridx = 5;
        listModel = new DefaultListModel<>();
        codiceSpecList = new JList<>(listModel);
        codiceSpecList.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        codiceSpecList.setEnabled(false);
        codiceSpecList.setVisibleRowCount(3);
        JScrollPane listScroll = new JScrollPane(codiceSpecList);
        listScroll.setPreferredSize(new Dimension(100, 60));
        tablePanel.add(listScroll, gbc);

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

        numeroBox.addActionListener(e -> {
            aggiornaStatoPulsante();
        });

        // JTextField nome
        nomeField.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
            public void insertUpdate(javax.swing.event.DocumentEvent e) { aggiornaStatoPulsante(); }
            public void removeUpdate(javax.swing.event.DocumentEvent e) { aggiornaStatoPulsante(); }
            public void changedUpdate(javax.swing.event.DocumentEvent e) { aggiornaStatoPulsante(); }
        });

        // JTextField capienza
        capienzaField.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
            public void insertUpdate(javax.swing.event.DocumentEvent e) { aggiornaStatoPulsante(); }
            public void removeUpdate(javax.swing.event.DocumentEvent e) { aggiornaStatoPulsante(); }
            public void changedUpdate(javax.swing.event.DocumentEvent e) { aggiornaStatoPulsante(); }
        });

        inserisciBtn.addActionListener(e -> {
            if (controller != null) controller.salvaInserimento();
        });

        // JList codici specie
        codiceSpecList.addListSelectionListener(e -> aggiornaStatoPulsante());


        add(new JScrollPane(tablePanel), BorderLayout.CENTER);
        add(inserisciBtn, BorderLayout.SOUTH);

        setCodiciSpecifici(List.of(Specie.CANE.getDisplayName(), Specie.GATTO.getDisplayName(),
        Specie.RODITORE.getDisplayName(),Specie.RETTILE.getDisplayName(),Specie.VOLATILE.getDisplayName()));
    }

    // --- Metodi per collegare il controller ---
    public void setController(AddCentroController controller) {
        this.controller = controller;
    }

    // --- Metodi pubblici per aggiornare i dati delle combo ---
    public void setProvince(List<ItemSelezionabile> province) {
        provinciaBox.setModel(new DefaultComboBoxModel<>(province.toArray(new ItemSelezionabile[0])));
        provinciaBox.setEnabled(true);
    }

    public void setCitta(List<ItemSelezionabile> citta) {
        cittaBox.setModel(new DefaultComboBoxModel<>(citta.toArray(new ItemSelezionabile[0])));
        cittaBox.setEnabled(true);
    }

    public void setNumeri(List<String> numeri) {
        numeroBox.setModel(new DefaultComboBoxModel<>(numeri.toArray(new String[0])));
        numeroBox.setEnabled(true);
    }

    public void setCodiciSpecifici(List<String> codici) {
        listModel.clear();
        codici.forEach(listModel::addElement);
        codiceSpecList.setEnabled(true);
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

    public Integer getCapienza() {
        try {
            return Integer.parseInt(capienzaField.getText());
        } catch (NumberFormatException e) {
            return null; // oppure 0 se preferisci
        }
    }

    public List<String> getCodiciSpecificiSelezionati() {
        return codiceSpecList.getSelectedValuesList();
    }

    private void aggiornaStatoPulsante() {
        boolean completo = getProvinciaSelezionata() != null
                && getCittaSelezionata() != null
                && getNumeroSelezionato() != null
                && !getNome().trim().isEmpty()
                && getCapienza() != null
                && !getCodiciSpecificiSelezionati().isEmpty();

        inserisciBtn.setEnabled(completo);
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
