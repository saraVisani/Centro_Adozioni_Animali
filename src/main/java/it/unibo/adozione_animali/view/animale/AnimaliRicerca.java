package it.unibo.adozione_animali.view.animale;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.List;
import it.unibo.adozione_animali.util.ItemSelezionabile;

import java.awt.*;
import java.time.LocalDate;

public class AnimaliRicerca extends JPanel {

    private JComboBox<ItemSelezionabile> provinciaBox;
    private JComboBox<ItemSelezionabile> cittaBox;
    private JComboBox<String> numeroBox;
    private JComboBox<String> codiceBox;
    private final JTextField txtNome;
    private final JComboBox<String> comboSpecie;
    private final JComboBox<String> comboStato;
    private final JSpinner startDateSpinner;
    private final JSpinner endDateSpinner;
    private final JButton btnCerca;
    private final JTable table;
    private AnimaliRicercaController controller;

    public AnimaliRicerca() {
        setLayout(new BorderLayout());

        // Pannello filtri
        JPanel filterPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;

        // Nome
        gbc.gridx = 0;
        gbc.gridy = 0;
        filterPanel.add(new JLabel("Nome:"), gbc);

        txtNome = new JTextField(15);
        gbc.gridx = 1;
        filterPanel.add(txtNome, gbc);

        // Specie
        gbc.gridx = 0;
        gbc.gridy = 1;
        filterPanel.add(new JLabel("Specie:"), gbc);

        comboSpecie = new JComboBox<>(new String[] {"", "Cane", "Gatto", "Roditore", "Rettile", "Volatile"});
        gbc.gridx = 1;
        filterPanel.add(comboSpecie, gbc);

        // Stato
        gbc.gridx = 0;
        gbc.gridy = 2;
        filterPanel.add(new JLabel("Stato Attuale:"), gbc);

        comboStato = new JComboBox<>(new String[] {"", "Disabile", "Cronico"});
        gbc.gridx = 1;
        filterPanel.add(comboStato, gbc);

        // Date pickers
        gbc.gridx = 0;
        gbc.gridy = 3;
        filterPanel.add(new JLabel("Data Inserimento Da:"), gbc);
        startDateSpinner = new JSpinner(new SpinnerDateModel());
        startDateSpinner.setEditor(new JSpinner.DateEditor(startDateSpinner, "yyyy-MM-dd"));
        gbc.gridx = 1;
        filterPanel.add(startDateSpinner, gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        filterPanel.add(new JLabel("Data Inserimento A:"), gbc);
        endDateSpinner = new JSpinner(new SpinnerDateModel());
        endDateSpinner.setEditor(new JSpinner.DateEditor(endDateSpinner, "yyyy-MM-dd"));
        gbc.gridx = 1;
        filterPanel.add(endDateSpinner, gbc);

        // Bottone Cerca
        btnCerca = new JButton("Cerca");
        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        filterPanel.add(btnCerca, gbc);

        add(filterPanel, BorderLayout.NORTH);

        provinciaBox.addActionListener(e -> {
            if (controller != null) controller.provinciaSelezionata((String) provinciaBox.getSelectedItem());
        });

        cittaBox.addActionListener(e -> {
            if (controller != null) {
                String provincia = getProvinciaSelezionata();
                String citta = getCittaSelezionata();
                controller.cittaSelezionata(provincia, citta);
            }
        });

        numeroBox.addActionListener(e -> {
            if (controller != null) {
                String provincia = getProvinciaSelezionata();
                String citta = getCittaSelezionata();
                String numero = getNumeroSelezionato();
                controller.numeroSelezionato(provincia, citta, numero);
            }
        });

        // JTable
        table = new JTable();
        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);
    }

    public void setController(AnimaliRicercaController controller){
        this.controller = controller;
    }

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

    public void setCodiciAnimale(List<String> codici) {
        codiceBox.setModel(new DefaultComboBoxModel<>(codici.toArray(new String[0])));
        codiceBox.setEnabled(true);
    }

    // Getter per controller
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

    public String getCodiceAnimaleSelezionato() {
        return (String) codiceBox.getSelectedItem();
    }

    public JTextField getTxtNome() {
        return txtNome;
    }

    public JComboBox<String> getComboSpecie() {
        return comboSpecie;
    }

    public JComboBox<String> getComboStato() {
        return comboStato;
    }

    public LocalDate getStartDate() {
        return LocalDate.parse(new java.text.SimpleDateFormat("yyyy-MM-dd").format(startDateSpinner.getValue()));
    }

    public LocalDate getEndDate() {
        return LocalDate.parse(new java.text.SimpleDateFormat("yyyy-MM-dd").format(endDateSpinner.getValue()));
    }

    public JButton getBtnCerca() {
        return btnCerca;
    }

    public JTable getTable() {
        return table;
    }

    public void setTableModel(DefaultTableModel model) {
        table.setModel(model);
    }
}
