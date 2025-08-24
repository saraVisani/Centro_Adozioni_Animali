package it.unibo.adozione_animali.view.statistiche;


import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;

public class StatisticheRicerca extends JPanel {
    private JTextField txtNomeStatistica;
    private JSpinner startDateSpinner;
    private JSpinner endDateSpinner;
    private JButton btnCerca;
    private JTable table;
    private JScrollPane scrollPane;

    public StatisticheRicerca() {
        setLayout(new BorderLayout(10, 10));

        // --- Pannello di ricerca ---
        JPanel searchPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);

        // Nome statistica
        gbc.gridx = 0;
        gbc.gridy = 0;
        searchPanel.add(new JLabel("Nome statistica:"), gbc);

        gbc.gridx = 1;
        txtNomeStatistica = new JTextField(20);
        searchPanel.add(txtNomeStatistica, gbc);

        // Data inizio
        gbc.gridx = 0;
        gbc.gridy = 1;
        searchPanel.add(new JLabel("Data inizio:"), gbc);

        gbc.gridx = 1;
        startDateSpinner = new JSpinner(new SpinnerDateModel());
        startDateSpinner.setEditor(new JSpinner.DateEditor(startDateSpinner, "yyyy-MM-dd"));
        searchPanel.add(startDateSpinner, gbc);

        // Data fine
        gbc.gridx = 0;
        gbc.gridy = 2;
        searchPanel.add(new JLabel("Data fine:"), gbc);

        gbc.gridx = 1;
        endDateSpinner = new JSpinner(new SpinnerDateModel());
        endDateSpinner.setEditor(new JSpinner.DateEditor(endDateSpinner, "yyyy-MM-dd"));
        searchPanel.add(endDateSpinner, gbc);

        // Bottone ricerca
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        btnCerca = new JButton("Cerca");
        searchPanel.add(btnCerca, gbc);

        add(searchPanel, BorderLayout.NORTH);

        // --- Tabella risultati ---
        table = new JTable();
        scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);
    }

    public JTextField getTxtNomeStatistica() {
        return txtNomeStatistica;
    }

    public LocalDate getStartDate() {
        return convertToLocalDate(startDateSpinner.getValue());
    }

    public LocalDate getEndDate() {
        return convertToLocalDate(endDateSpinner.getValue());
    }

    public JButton getBtnCerca() {
        return btnCerca;
    }

    public JTable getTable() {
        return table;
    }

    private LocalDate convertToLocalDate(Object value) {
        if (value instanceof java.util.Date date) {
            return date.toInstant().atZone(java.time.ZoneId.systemDefault()).toLocalDate();
        }
        return null;
    }
}
