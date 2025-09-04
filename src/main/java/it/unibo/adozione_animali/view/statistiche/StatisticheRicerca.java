package it.unibo.adozione_animali.view.statistiche;

import javax.swing.*;
import java.util.List;
import java.awt.*;
import java.time.LocalDate;

public class StatisticheRicerca extends JPanel {

    private final LocalDate oggi = LocalDate.now();
    private final int annoCorrente = oggi.getYear();
    private final int meseCorrente = oggi.getMonthValue();
    private final int giornoCorrente = oggi.getDayOfMonth();

    // --- Combo statistica ---
    private JComboBox<String> txtNomeStatistica;

    // --- Data inizio ---
    private JCheckBox chkStartAnno, chkStartMese, chkStartGiorno;
    private JSpinner spnStartAnno, spnStartMese, spnStartGiorno;

    // --- Data fine ---
    private JCheckBox chkEndAnno, chkEndMese, chkEndGiorno;
    private JSpinner spnEndAnno, spnEndMese, spnEndGiorno;

    // --- Bottone e tabella ---
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
        gbc.anchor = GridBagConstraints.WEST;
        searchPanel.add(new JLabel("Nome statistica:"), gbc);

        gbc.gridx = 1;
        gbc.gridwidth = 6;
        txtNomeStatistica = new JComboBox<>();
        searchPanel.add(txtNomeStatistica, gbc);
        gbc.gridwidth = 1;

        // --- Date Labels ---
        gbc.gridx = 1;
        gbc.gridy = 1;
        searchPanel.add(new JLabel("Anno"), gbc);
        gbc.gridx = 2;
        searchPanel.add(new JLabel("Mese"), gbc);
        gbc.gridx = 3;
        searchPanel.add(new JLabel("Giorno"), gbc);

        // --- Data inizio ---
        gbc.gridy = 2;
        gbc.gridx = 0;
        searchPanel.add(new JLabel("Inizio:"), gbc);

        chkStartAnno = new JCheckBox();
        spnStartAnno = new JSpinner(new SpinnerNumberModel(annoCorrente, 1900, annoCorrente, 1));
        spnStartAnno.setEditor(new JSpinner.NumberEditor(spnStartAnno, "#"));
        chkStartMese = new JCheckBox();
        spnStartMese = new JSpinner(new SpinnerNumberModel(1, 1, 12, 1));
        chkStartGiorno = new JCheckBox();
        spnStartGiorno = new JSpinner(new SpinnerNumberModel(1, 1, 31, 1));
        disableStartSpinners();

        gbc.gridx = 1;
        searchPanel.add(wrap(chkStartAnno, spnStartAnno), gbc);
        gbc.gridx = 2;
        searchPanel.add(wrap(chkStartMese, spnStartMese), gbc);
        gbc.gridx = 3;
        searchPanel.add(wrap(chkStartGiorno, spnStartGiorno), gbc);

        chkStartAnno.addActionListener(e -> updateStartEnabled());
        chkStartMese.addActionListener(e -> updateStartEnabled());
        chkStartGiorno.addActionListener(e -> updateStartEnabled());
        spnStartAnno.addChangeListener(e -> updateMaxMeseGiornoStart());
        spnStartMese.addChangeListener(e -> updateMaxGiornoStart());
        updateMaxMeseGiornoStart();
        updateMaxGiornoStart();

        // --- Data fine ---
        gbc.gridy = 3;
        gbc.gridx = 0;
        searchPanel.add(new JLabel("Fine:"), gbc);

        chkEndAnno = new JCheckBox();
        spnEndAnno = new JSpinner(new SpinnerNumberModel(annoCorrente, 1900, annoCorrente, 1));
        spnEndAnno.setEditor(new JSpinner.NumberEditor(spnEndAnno, "#"));
        chkEndMese = new JCheckBox();
        spnEndMese = new JSpinner(new SpinnerNumberModel(1, 1, 12, 1));
        chkEndGiorno = new JCheckBox();
        spnEndGiorno = new JSpinner(new SpinnerNumberModel(1, 1, 31, 1));
        disableEndSpinners();

        gbc.gridx = 1;
        searchPanel.add(wrap(chkEndAnno, spnEndAnno), gbc);
        gbc.gridx = 2;
        searchPanel.add(wrap(chkEndMese, spnEndMese), gbc);
        gbc.gridx = 3;
        searchPanel.add(wrap(chkEndGiorno, spnEndGiorno), gbc);

        chkEndAnno.addActionListener(e -> updateEndEnabled());
        chkEndMese.addActionListener(e -> updateEndEnabled());
        chkEndGiorno.addActionListener(e -> updateEndEnabled());
        spnEndAnno.addChangeListener(e -> updateMaxMeseGiornoEnd());
        spnEndMese.addChangeListener(e -> updateMaxGiornoEnd());
        updateMaxMeseGiornoEnd();
        updateMaxGiornoEnd();

        // --- Bottone ricerca ---
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 4;
        gbc.anchor = GridBagConstraints.CENTER;
        btnCerca = new JButton("Cerca");
        searchPanel.add(btnCerca, gbc);

        add(searchPanel, BorderLayout.NORTH);

        // --- Tabella risultati ---
        table = new JTable();
        scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);
    }

    // --- Utility per combinare checkbox+spinner ---
    private JPanel wrap(JCheckBox chk, JSpinner spn) {
        JPanel p = new JPanel(new BorderLayout());
        p.add(chk, BorderLayout.WEST);
        p.add(spn, BorderLayout.CENTER);
        return p;
    }

    private void disableStartSpinners() {
        spnStartAnno.setEnabled(false);
        spnStartMese.setEnabled(false);
        spnStartGiorno.setEnabled(false);
        chkStartMese.setEnabled(false);
        chkStartGiorno.setEnabled(false);
    }

    private void disableEndSpinners() {
        spnEndAnno.setEnabled(false);
        spnEndMese.setEnabled(false);
        spnEndGiorno.setEnabled(false);
        chkEndMese.setEnabled(false);
        chkEndGiorno.setEnabled(false);
    }

    private void updateStartEnabled() {
        spnStartAnno.setEnabled(chkStartAnno.isSelected());
        chkStartMese.setEnabled(chkStartAnno.isSelected());
        chkStartGiorno.setEnabled(chkStartMese.isSelected());
        spnStartMese.setEnabled(chkStartMese.isSelected());
        spnStartGiorno.setEnabled(chkStartGiorno.isSelected());
        if (!chkStartAnno.isSelected()) {
            chkStartMese.setSelected(false);
            chkStartGiorno.setSelected(false);
            spnStartMese.setEnabled(false);
            spnStartGiorno.setEnabled(false);
        }
        if(!chkStartMese.isSelected() && chkStartGiorno.isSelected()){
            chkStartGiorno.setSelected(false);
            spnStartGiorno.setEnabled(false);
        }
    }

    private void updateEndEnabled() {
        spnEndAnno.setEnabled(chkEndAnno.isSelected());
        chkEndMese.setEnabled(chkEndAnno.isSelected());
        chkEndGiorno.setEnabled(chkEndMese.isSelected());
        spnEndMese.setEnabled(chkEndMese.isSelected());
        spnEndGiorno.setEnabled(chkEndGiorno.isSelected());
        if (!chkEndAnno.isSelected()) {
            chkEndMese.setSelected(false);
            chkEndGiorno.setSelected(false);
            spnEndMese.setEnabled(false);
            spnEndGiorno.setEnabled(false);
        }
        if(!chkEndMese.isSelected() && chkEndGiorno.isSelected()){
            chkEndGiorno.setSelected(false);
            spnEndGiorno.setEnabled(false);
        }
    }

    private void updateMaxMeseGiornoStart() {
        int anno = (int) spnStartAnno.getValue();
        int maxMese = (anno == annoCorrente) ? meseCorrente : 12;
        SpinnerNumberModel model = (SpinnerNumberModel) spnStartMese.getModel();
        model.setMaximum(maxMese);
        if ((int) spnStartMese.getValue() > maxMese) spnStartMese.setValue(maxMese);

        updateMaxGiornoStart();
    }

    private void updateMaxGiornoStart() {
        int anno = (int) spnStartAnno.getValue();
        int mese = (int) spnStartMese.getValue();
        int maxGiorno;
        if (anno == annoCorrente && mese == meseCorrente) {
            maxGiorno = giornoCorrente;
        } else {
            maxGiorno = java.time.YearMonth.of(anno, mese).lengthOfMonth();
        }
        SpinnerNumberModel model = (SpinnerNumberModel) spnStartGiorno.getModel();
        model.setMaximum(maxGiorno);
        if ((int) spnStartGiorno.getValue() > maxGiorno) spnStartGiorno.setValue(maxGiorno);
    }

    private void updateMaxMeseGiornoEnd() {
        int anno = (int) spnEndAnno.getValue();
        int maxMese = (anno == annoCorrente) ? meseCorrente : 12;
        SpinnerNumberModel model = (SpinnerNumberModel) spnEndMese.getModel();
        model.setMaximum(maxMese);
        if ((int) spnEndMese.getValue() > maxMese) spnEndMese.setValue(maxMese);

        updateMaxGiornoEnd();
    }

    private void updateMaxGiornoEnd() {
        int anno = (int) spnEndAnno.getValue();
        int mese = (int) spnEndMese.getValue();
        int maxGiorno;
        if (anno == annoCorrente && mese == meseCorrente) {
            maxGiorno = giornoCorrente;
        } else {
            maxGiorno = java.time.YearMonth.of(anno, mese).lengthOfMonth();
        }
        SpinnerNumberModel model = (SpinnerNumberModel) spnEndGiorno.getModel();
        model.setMaximum(maxGiorno);
        if ((int) spnEndGiorno.getValue() > maxGiorno) spnEndGiorno.setValue(maxGiorno);
    }

    // --- Metodi pubblici per aggiornare i dati delle combo ---
    public void setTipo(List<String> valori) {
        DefaultComboBoxModel<String> model = new DefaultComboBoxModel<>();
        model.addElement("--select--");
        if (valori != null) {
            valori.forEach(model::addElement);
        }
        txtNomeStatistica.setModel(model);
    }

    // --- Getters per valori selezionati ---
    public String getTipo() {
        return (String) txtNomeStatistica.getSelectedItem();
    }

    public LocalDate getStartDate() {
        return buildDate(chkStartAnno, spnStartAnno, chkStartMese, spnStartMese, chkStartGiorno, spnStartGiorno, true);
    }

    public LocalDate getEndDate() {
        return buildDate(chkEndAnno, spnEndAnno, chkEndMese, spnEndMese, chkEndGiorno, spnEndGiorno, false);
    }

    private LocalDate buildDate(JCheckBox chkAnno, JSpinner spnAnno,
            JCheckBox chkMese, JSpinner spnMese,
            JCheckBox chkGiorno, JSpinner spnGiorno,
            boolean isStart) {
        if (!chkAnno.isSelected())
            return null;

        int year = (int) spnAnno.getValue();

        if (!chkMese.isSelected()) {
            return isStart ? LocalDate.of(year, 1, 1)
                    : LocalDate.of(year, 12, 31);
        }

        int month = (int) spnMese.getValue();

        if (!chkGiorno.isSelected()) {
            int day = isStart ? 1
                    : java.time.YearMonth.of(year, month).lengthOfMonth();
            return LocalDate.of(year, month, day);
        }

        int day = (int) spnGiorno.getValue();
        return LocalDate.of(year, month, day);
    }

    public JButton getBtnCerca() {
        return btnCerca;
    }

    public JTable getTable() {
        return table;
    }

}
