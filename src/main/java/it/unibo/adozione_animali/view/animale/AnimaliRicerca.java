package it.unibo.adozione_animali.view.animale;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;

import java.util.List;
import it.unibo.adozione_animali.util.ItemSelezionabile;

import java.awt.*;

public class AnimaliRicerca extends JPanel {

    private JComboBox<ItemSelezionabile> provinciaBox;
    private JComboBox<ItemSelezionabile> cittaBox;
    private JComboBox<String> numeroBox;
    private JComboBox<String> codiceBox;
    private JComboBox<String> ritrovamento;
    private final JTextField txtNome;
    private final JComboBox<String> comboSpecie;
    private final JComboBox<String> comboRazza;
    private final JComboBox<String> comboStato;
    private final JTextField txtEtaMax, txtEtaMin;
    private JSpinner spPesoX, spPesoN, spAltX, spAltN;
    private JCheckBox ckPesoX, ckPesoN, ckAltX, ckAltN, ckAdozione;
    private JComboBox<ItemSelezionabile> provinciaPer;
    private JComboBox<ItemSelezionabile> cittaPer;
    private JComboBox<String> numeroPer;
    private JComboBox<String> cf;
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

        provinciaBox = new JComboBox<>();
        provinciaBox.addActionListener(e -> this.controller.provinciaSelezionata(this.getProvinciaSelezionata()));
        cittaBox = new JComboBox<>();
        cittaBox.setEnabled(false);
        cittaBox.addActionListener(e -> this.controller.cittaSelezionata(this.getProvinciaSelezionata(), this.getCittaSelezionata()));
        numeroBox = new JComboBox<>();
        numeroBox.setEnabled(false);
        numeroBox.addActionListener(e -> this.controller.numeroSelezionato(this.getProvinciaSelezionata(), this.getCittaSelezionata(), this.getNumeroSelezionato()));
        codiceBox = new JComboBox<>();
        codiceBox.setEnabled(false);
        codiceBox.addActionListener(e -> this.controller.animaleSelezionato(this.getProvinciaSelezionata(), this.getCittaSelezionata(), this.getNumeroSelezionato(), this.getCodiceAnimaleSelezionato()));
        ritrovamento = new JComboBox<>();
        txtNome = new JTextField(15);
        comboSpecie = new JComboBox<>();
        comboSpecie.addActionListener(e -> this.controller.specieSelezionata(this.getComboSpecie()));
        comboRazza = new JComboBox<>();
        comboRazza.setEnabled(false);
        comboRazza.addActionListener(e -> this.controller.razzaSelezionata(this.getComboSpecie(), this.getComboRazza()));
        comboStato = new JComboBox<>();
        txtEtaMax = new JTextField(4);
        txtEtaMin = new JTextField(4);
        DocumentListener etaListener = new DocumentListener() {
            @Override public void insertUpdate(DocumentEvent e) { validaEta(txtEtaMin, txtEtaMax); }
            @Override public void removeUpdate(DocumentEvent e) { validaEta(txtEtaMin, txtEtaMax); }
            @Override public void changedUpdate(DocumentEvent e) { validaEta(txtEtaMin, txtEtaMax); }
        };

        txtEtaMin.getDocument().addDocumentListener(etaListener);
        txtEtaMax.getDocument().addDocumentListener(etaListener);
        ckPesoX = new JCheckBox();
        ckPesoX.addActionListener(e -> spPesoX.setEnabled(this.getCkPesoX()));
        ckPesoN = new JCheckBox();
        ckPesoN.addActionListener(e -> spPesoN.setEnabled(this.getCkPesoN()));
        ckAltX = new JCheckBox();
        ckAltX.addActionListener(e -> spAltX.setEnabled(this.getCkAltX()));
        ckAltN = new JCheckBox();
        ckAltN.addActionListener(e -> spAltN.setEnabled(this.getCkAltN()));
        ckAdozione = new JCheckBox();
        spPesoX = new JSpinner(new SpinnerNumberModel(0.05, 0.05, 100, 0.1));
        spPesoX.setEnabled(false);
        spPesoN = new JSpinner(new SpinnerNumberModel(0.05, 0.05, 100, 0.1));
        spPesoN.setEnabled(false);
        spAltX = new JSpinner(new SpinnerNumberModel(0.05, 0.05, 100, 0.1));
        spAltX.setEnabled(false);
        spAltN = new JSpinner(new SpinnerNumberModel(0.05, 0.05, 100, 0.1));
        spAltN.setEnabled(false);
        spPesoN.addChangeListener(e -> aggiornaLimitiDaMinimo(spPesoN, spPesoX));
        spPesoX.addChangeListener(e -> aggiornaLimitiDaMassimo(spPesoN, spPesoX));
        spAltN.addChangeListener(e -> aggiornaLimitiDaMinimo(spAltN, spAltX));
        spAltX.addChangeListener(e -> aggiornaLimitiDaMassimo(spAltN, spAltX));

        provinciaPer = new JComboBox<>();
        provinciaPer.setEnabled(false);
        provinciaPer.addActionListener(e -> this.controller.provinciaPersonaSelezionato(getProvinciaPer()));
        cittaPer = new JComboBox<>();
        cittaPer.setEnabled(false);
        cittaPer.addActionListener(e -> this.controller.cittaPersonaSelezionato(getProvinciaPer(), getCittaPer()));
        numeroPer = new JComboBox<>();
        numeroPer.setEnabled(false);
        numeroPer.addActionListener(e -> aggiornabutton());
        cf = new JComboBox<>();

        // Etichette + componenti già definiti
        Object[][] rows = {
            {"Provincia", provinciaBox},
            {"Città", cittaBox},
            {"Numero Civico", numeroBox},
            {"Codice Animale", codiceBox},

            {"Stato di Ritrovamento", ritrovamento},
            {"Stato dell'Animale", comboStato},
            {"Specie", comboSpecie},
            {"Razza", comboRazza},

            {"Stato Adozione", ckAdozione},
            {"Nome dell'Animale", txtNome},
            {"Età Massima", txtEtaMax},
            {"Età Minima", txtEtaMin},

            {"Altezza Massima", wrap(ckAltX, spAltX)},
            {"Altezza Minima", wrap(ckAltN, spAltN)},
            {"Peso Massimo", wrap(ckPesoX, spPesoX)},
            {"Peso Minimo", wrap(ckPesoN, spPesoN)},

            {"Codice Fiscale", cf},
            {"Provincia Persona", provinciaPer},
            {"Città Persona", cittaPer},
            {"Numero Civico Persona", numeroPer}
        };

        // Disposizione: max 4 per riga
        int cols = 4;
        int index = 0;
        for (int i = 0; i < rows.length; i++) {
            int row = (index / cols) * 2;
            int col = index % cols;

            if (row == 7){
                gbc.gridwidth = 2;
            }else{
                gbc.gridwidth = 1;
            }

            // Etichetta
            gbc.gridx = col;
            gbc.gridy = row;
            filterPanel.add(new JLabel((String) rows[i][0]), gbc);

            // Componente
            gbc.gridy = row + 1;
            filterPanel.add((Component) rows[i][1], gbc);

            index++;
        }

        // Bottone Cerca
        btnCerca = new JButton("Request");
        gbc.gridx = 7;
        gbc.gridy = 2;
        gbc.gridwidth = 5;
        gbc.gridheight = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        filterPanel.add(btnCerca, gbc);

        add(filterPanel, BorderLayout.NORTH);

        cf.addActionListener(e -> {this.controller.cfSelezionato(getCf());
        btnCerca.setEnabled(false);});

        // JTable
        table = new JTable();
        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);
    }

    private boolean isValid(String s) {
        return s != null && !s.isEmpty() && !s.equals("--select--");
    }

    private void aggiornabutton() {
        boolean result = isValid(getCf()) && isValid(getProvinciaPer()) && isValid(getCittaPer())
        && getNumeroPer() != null;
        btnCerca.setEnabled(result);
    }

    public void setController(AnimaliRicercaController controller){
        this.controller = controller;
    }

    public void setProvince(List<ItemSelezionabile> province) {
        setComboBoxWithEmptyFirst(provinciaBox, province, new ItemSelezionabile("", "--select--"));
    }

    public void setCitta(List<ItemSelezionabile> citta) {
        setComboBoxWithEmptyFirst(cittaBox, citta, new ItemSelezionabile("", "--select--"));
    }

    public void setNumeri(List<String> numeri) {
        setComboBoxWithEmptyFirst(numeroBox, numeri, "--select--");
    }

    public void setCodiciAnimale(List<String> codici) {
        setComboBoxWithEmptyFirst(codiceBox, codici, "--select--");
    }

    public void setRitrovamento(List<String> ritrovamento){
        setComboBoxWithEmptyFirst(this.ritrovamento, ritrovamento, "--select--");
    }

    public void setRazza(List<String> razza){
        setComboBoxWithEmptyFirst(this.comboRazza, razza, "--select--");
    }

    public void setSpecie(List<String> specie){
        setComboBoxWithEmptyFirst(this.comboSpecie, specie, "--select--");
    }

    public void setStato(List<String> stato){
        setComboBoxWithEmptyFirst(this.comboStato, stato, "--select--");
    }

    public void setProvinciaPer(List<ItemSelezionabile> provinciaPer) {
        setComboBoxWithEmptyFirst(this.provinciaPer, provinciaPer, new ItemSelezionabile("", "--select--"));
    }

    public void setCittaPer(List<ItemSelezionabile> cittaPer) {
        setComboBoxWithEmptyFirst(this.cittaPer, cittaPer, new ItemSelezionabile("", "--select--"));
    }

    public void setNumeroPer(List<String> numeroPer) {
        setComboBoxWithEmptyFirst(this.numeroPer, numeroPer, "--select--");
    }

    public void setCf(List<String> cf) {
        setComboBoxWithEmptyFirst(this.cf, cf, "--select--");
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
        try {
            Integer.parseInt((String) numeroBox.getSelectedItem());
            return (String) numeroBox.getSelectedItem();
        } catch (Exception e) {
            return null;
        }
    }

    public String getCodiceAnimaleSelezionato() {
        return (String) codiceBox.getSelectedItem();
    }

    public String getTxtNome() {
        return (String) txtNome.getText();
    }

    public String getComboSpecie() {
        return (String) comboSpecie.getSelectedItem();
    }

    public String getComboStato() {
        return (String) comboStato.getSelectedItem();
    }

    public String getRitrovamento() {
        return (String) ritrovamento.getSelectedItem();
    }

    public String getComboRazza() {
        return (String) comboRazza.getSelectedItem();
    }

    public Integer gettxtEtaMax() {
        try {
            var num = Integer.parseInt(txtEtaMax.getText());
            if(num <= 0){
                return null;
            }
            return num;
        } catch (Exception e) {
            return null;
        }
    }

    public Integer gettxtEtaMin() {
        try {
            var num = Integer.parseInt(txtEtaMin.getText());
            if(num < 0){
                return null;
            }
            return num;
        } catch (Exception e) {
            return null;
        }
    }

    public Float getValueSpPesoX() {
        try {
            var num = (Float) spPesoX.getValue();
            if(num <= 0){
                return null;
            }
            return num;
        } catch (Exception e) {
            return null;
        }
    }

    public Float getValueSpPesoN() {
        try {
            var num = (Float) spPesoN.getValue();
            if(num <= 0){
                return null;
            }
            return num;
        } catch (Exception e) {
            return null;
        }
    }

    public Float getValueSpAltX() {
        try {
            var num = (Float) spAltX.getValue();
            if(num <= 0){
                return null;
            }
            return num;
        } catch (Exception e) {
            return null;
        }
    }

    public Float getValueSpAltN() {
        try {
            var num = (Float) spAltN.getValue();
            if(num <= 0){
                return null;
            }
            return num;
        } catch (Exception e) {
            return null;
        }
    }

    public JSpinner getSpPesoX() {
        return spPesoX;
    }

    public JSpinner getSpPesoN() {
        return spPesoN;
    }

    public JSpinner getSpAltX() {
        return spAltX;
    }

    public JSpinner getSpAltN() {
        return spAltN;
    }

    public boolean getCkPesoX() {
        return ckPesoX.isSelected();
    }

    public boolean getCkPesoN() {
        return ckPesoN.isSelected();
    }

    public boolean getCkAltX() {
        return ckAltX.isSelected();
    }

    public boolean getCkAltN() {
        return ckAltN.isSelected();
    }

    public boolean getCkAdozione() {
        return ckAdozione.isSelected();
    }

    public String getProvinciaPer() {
        ItemSelezionabile selected = (ItemSelezionabile) provinciaPer.getSelectedItem();
        return selected != null ? selected.getCodice() : null;
    }

    public String getCittaPer() {
        ItemSelezionabile selected = (ItemSelezionabile) cittaPer.getSelectedItem();
        return selected != null ? selected.getCodice() : null;
    }

    public Integer getNumeroPer() {
        try {
            return Integer.parseInt((String)numeroPer.getSelectedItem());
        } catch (Exception e) {
            return null;
        }
    }

    public String getCf() {
        return (String) cf.getSelectedItem();
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

    private JPanel wrap(JCheckBox checkBox, JSpinner spinner) {
        JPanel panel = new JPanel(new BorderLayout(5, 0));
        panel.add(checkBox, BorderLayout.WEST);
        panel.add(spinner, BorderLayout.CENTER);
        return panel;
    }

    public void resetVisibility() {
        ritrovamento.setEnabled(true);
        txtNome.setEnabled(true);
        comboSpecie.setEnabled(true);
        comboStato.setEnabled(true);
        txtEtaMax.setEnabled(true);
        ckPesoX.setEnabled(true);
        ckPesoN.setEnabled(true);
        ckAltX.setEnabled(true);
        ckAltN.setEnabled(true);
        ckAdozione.setEnabled(true);
        cf.setEnabled(true);
    }

    public void animaleScelto() {
        // JComboBox
        JComboBox<?>[] comboBoxes = {
            ritrovamento, comboSpecie, comboRazza, comboStato,
            provinciaPer, cittaPer, numeroPer, cf
        };
        for (JComboBox<?> box : comboBoxes) {
            box.setSelectedIndex(-1);
            box.setEnabled(false);
        }

        // JTextField
        JTextField[] textFields = { txtNome, txtEtaMax, txtEtaMin };
        for (JTextField tf : textFields) {
            tf.setText("");
            tf.setEnabled(false);
        }

        // JCheckBox
        JCheckBox[] checkBoxes = { ckPesoX, ckPesoN, ckAltX, ckAltN, ckAdozione };
        for (JCheckBox ck : checkBoxes) {
            ck.setSelected(false);
            ck.setEnabled(false);
        }

        // JSpinner
        JSpinner[] spinners = { spPesoX, spPesoN, spAltX, spAltN };
        for (JSpinner sp : spinners) {
            sp.setValue(0); // oppure valore di default
            sp.setEnabled(false);
        }
    }

    private void aggiornaLimitiDaMinimo(JSpinner spinnerMin, JSpinner spinnerMax) {
        double min = ((Number) spinnerMin.getValue()).doubleValue();
        SpinnerNumberModel modelMax = (SpinnerNumberModel) spinnerMax.getModel();

        // min assoluto
        if (min < 0.05) {
            min = 0.05;
            spinnerMin.setValue(min);
        }

        // aggiorna limite minimo dello spinner max senza toccare il valore
        modelMax.setMinimum(min);

        // se min > max, sposto il valore dello spinner max
        double max = ((Number) spinnerMax.getValue()).doubleValue();
        if (min > max) {
            spinnerMax.setValue(min);
        }
    }

    private void aggiornaLimitiDaMassimo(JSpinner spinnerMin, JSpinner spinnerMax) {
        double max = ((Number) spinnerMax.getValue()).doubleValue();
        SpinnerNumberModel modelMin = (SpinnerNumberModel) spinnerMin.getModel();

        // max assoluto
        if (max > 50.0) {
            max = 50.0;
            spinnerMax.setValue(max);
        }

        // aggiorna limite massimo dello spinner min senza toccare il valore
        modelMin.setMaximum(max);

        // se max < min, sposto il valore dello spinner min
        double min = ((Number) spinnerMin.getValue()).doubleValue();
        if (max < min) {
            spinnerMin.setValue(max);
        }
    }

    private int parseEtaSafe(JTextField field, int defaultValue) {
        try {
            String text = field.getText().trim();
            if (text.isEmpty()) return defaultValue;
            int val = Integer.parseInt(text);
            if (val < 0) return 0;
            if (val > 150) return 150;
            return val;
        } catch (NumberFormatException e) {
            return defaultValue;
        }
    }

    private void validaEta(JTextField txtEtaMin, JTextField txtEtaMax) {
        int min = parseEtaSafe(txtEtaMin, 0);
        int max = parseEtaSafe(txtEtaMax, 150);

        if (min >= max) {
            max = Math.min(min + 1, 150);
        }

        // aggiorna i campi con i valori corretti
        txtEtaMin.setText(String.valueOf(min));
        txtEtaMax.setText(String.valueOf(max));
    }

    public void setLimitiSpinner(JSpinner spinnerMax, JSpinner spinnerMin, double min, double max){
        // imposta i limiti senza perdere i valori correnti se validi
        SpinnerNumberModel modelMin = (SpinnerNumberModel) spinnerMin.getModel();
        SpinnerNumberModel modelMax = (SpinnerNumberModel) spinnerMax.getModel();

        // aggiorna limiti
        modelMin.setMinimum(min);
        modelMin.setMaximum(max);
        modelMax.setMinimum(min);
        modelMax.setMaximum(max);

        // correggi valori se fuori dai limiti
        double valMin = ((Number) spinnerMin.getValue()).doubleValue();
        double valMax = ((Number) spinnerMax.getValue()).doubleValue();

        if(valMin < min) spinnerMin.setValue(min);
        if(valMin > max) spinnerMin.setValue(max);
        if(valMax < min) spinnerMax.setValue(min);
        if(valMax > max) spinnerMax.setValue(max);
    }
}
