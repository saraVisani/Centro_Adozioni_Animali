package it.unibo.adozione_animali.view.animale;

import java.awt.*;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.swing.*;
import javax.swing.border.TitledBorder;

import it.unibo.adozione_animali.controller.impl.AnimaliInserimentoController;
import it.unibo.adozione_animali.util.ItemSelezionabile;
import it.unibo.adozione_animali.view.MainMenu;

public class InserisciAnimale extends JPanel{
    private final LocalDate oggi = LocalDate.now();
    private MainMenu mainMenu;
    private JComboBox<ItemSelezionabile> provinciaBox;
    private JComboBox<ItemSelezionabile> cittaBox;
    private JComboBox<String> numeroBox;
    private JComboBox<String> codiceBox;
    private JComboBox<String> ritrovamento;
    private final JTextField txtNome;
    private final JComboBox<String> comboSpecie;
    private final JComboBox<String> comboRazza;
    private final JComboBox<String> comboStato;
    private final JSpinner spDataNascita;
    private JSpinner spPeso, spAlt;
    private JTextField provinciaRit;
    private JTextField cittaRit;
    private JTextField numeroRit;
    private final JButton btnCerca;
    private AnimaliInserimentoController controller;

    public InserisciAnimale(){

        setLayout(new BorderLayout());
        setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createTitledBorder(
                        BorderFactory.createEtchedBorder(),
                        "Inserimento Animale",
                        TitledBorder.CENTER,
                        TitledBorder.TOP
                ),
                BorderFactory.createEmptyBorder(25, 10, 10, 10)
        ));

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
        numeroBox.addActionListener(e -> {this.controller.numeroSelezionato(this.getProvinciaSelezionata(), this.getCittaSelezionata(), this.getNumeroSelezionato(), this.getSpecie());
                                            this.aggiornabutton();});
        codiceBox = new JComboBox<>();
        codiceBox.setEnabled(false);
        codiceBox.addActionListener(e -> {this.controller.animaleSelezionato(this.getProvinciaSelezionata(), this.getCittaSelezionata(), this.getNumeroSelezionato(), this.getCodiceAnimaleSelezionato(), this.getSpecie());
                                            this.aggiornabutton();});
        ritrovamento = new JComboBox<>();
        txtNome = new JTextField(15);
        comboSpecie = new JComboBox<>();
        comboSpecie.addActionListener(e -> this.controller.specieSelezionata(this.getSpecie()));
        comboRazza = new JComboBox<>();
        comboRazza.setEnabled(false);
        comboRazza.addActionListener(e -> {this.controller.razzaSelezionata(this.getSpecie(), this.getComboRazza());
                                            this.aggiornabutton();});
        comboStato = new JComboBox<>();
        comboStato.addActionListener(e -> aggiornabutton());
        spDataNascita = new JSpinner(
            new SpinnerDateModel(new Date(), null, new Date(), Calendar.DAY_OF_MONTH)
        );
        JSpinner.DateEditor editor = new JSpinner.DateEditor(spDataNascita, "dd/MM/yyyy");
        spDataNascita.setEditor(editor);
        spPeso = new JSpinner(new SpinnerNumberModel(0.05, 0.05, 100, 0.1));
        spPeso.setEnabled(false);
        spPeso.addChangeListener(e -> aggiornabutton());
        spAlt = new JSpinner(new SpinnerNumberModel(0.05, 0.05, 100, 0.1));
        spAlt.setEnabled(false);
        spAlt.addChangeListener(e -> aggiornabutton());
        provinciaRit = new JTextField(4);
        cittaRit = new JTextField(4);
        numeroRit = new JTextField(7);

        Object[][] rows = {
            {"Provincia", provinciaBox},
            {"Città", cittaBox},
            {"Numero Civico", numeroBox},
            {"Codice Animale", codiceBox},

            {"Stato di Ritrovamento", ritrovamento},
            {"Provincia di Ritrovamento", provinciaRit},
            {"Città di Ritrovamento", cittaRit},
            {"Numero Civico di Ritrovamento", numeroRit},

            {"Stato dell'Animale", comboStato},
            {"Specie", comboSpecie},
            {"Razza", comboRazza},
            {"Nome dell'Animale", txtNome},

            {"Data di Nascita", spDataNascita},
            {"Altezza", spAlt},
            {"Peso", spPeso}
        };

        // Disposizione: max 4 per riga
        int cols = 4;
        int index = 0;
        for (int i = 0; i < rows.length; i++) {
            int row = (index / cols) * 2;
            int col = index % cols;

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
        btnCerca = new JButton("Inserimento");
        btnCerca.setEnabled(false);
        gbc.gridx = 7;
        gbc.gridy = 2;
        gbc.gridwidth = 5;
        gbc.gridheight = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        filterPanel.add(btnCerca, gbc);

        add(filterPanel, BorderLayout.NORTH);

        btnCerca.addActionListener(e -> this.controller.salvaInserimento());
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
            return Integer.parseInt(s) >= 0;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private void aggiornabutton() {
        ItemSelezionabile provinciaSel = (ItemSelezionabile) provinciaBox.getSelectedItem();
        ItemSelezionabile cittaSel = (ItemSelezionabile) cittaBox.getSelectedItem();

        boolean provincia = isValid(provinciaSel);
        boolean citta = isValid(cittaSel);
        boolean numero = isNumber((String) numeroBox.getSelectedItem());

        // Dati ritrovamento
        boolean provinciaRitValida = isValidString(provinciaRit.getText());
        boolean cittaRitValida = isValidString(cittaRit.getText());
        boolean numeroRitValido = isNumber(numeroRit.getText());
        boolean ritrovamentoValido = isValidString((String) ritrovamento.getSelectedItem());

        // Dati dell’animale
        boolean stato = isValidString((String) comboStato.getSelectedItem());
        boolean specie = isValidString((String) comboSpecie.getSelectedItem());
        boolean razza = isValidString((String) comboRazza.getSelectedItem());

        // Data di nascita valida (non futura)
        LocalDate nascita = ((Date) spDataNascita.getValue())
                                .toInstant()
                                .atZone(ZoneId.systemDefault())
                                .toLocalDate();
        boolean dataNascita = nascita != null && !nascita.isAfter(oggi);

        // Peso e altezza
        boolean peso = getValueSpPeso() != null;
        boolean altezza = getValueSpAlt() != null;

        // Risultato finale
        boolean result = provincia && citta && numero
                    && provinciaRitValida && cittaRitValida && numeroRitValido && ritrovamentoValido
                    && stato && specie && razza
                    && dataNascita && peso && altezza;

        btnCerca.setEnabled(result);
    }

    public void setController(AnimaliInserimentoController controller){
        this.controller = controller;
    }

    public void setMainMenu(MainMenu mainMenu) {
        this.mainMenu = mainMenu;
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
        if(codici.size()==1){
            DefaultComboBoxModel<String> model = new DefaultComboBoxModel<>();
            model.addElement(codici.get(0));
            codiceBox.setModel(model);
            codiceBox.setEnabled(true);
        }else{
            setComboBoxWithEmptyFirst(codiceBox, codici, "--select--");
        }
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

    public String getSpecie() {
        return (String) comboSpecie.getSelectedItem();
    }

    public JComboBox<String> getComboSpecie() {
        return comboSpecie;
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

    public Float getValueSpPeso() {
        try {
            Number num = (Number) spPeso.getValue();
            if (num == null) return null;
            float f = num.floatValue();
            if (f <= 0) return null;
            return f;
        } catch (Exception e) {
            return null;
        }
    }

    public Float getValueSpAlt() {
        try {
            var num = (Number) spAlt.getValue();
            if (num == null) return null;
            float f = num.floatValue();
            if (f <= 0) return null;
            return f;
        } catch (Exception e) {
            return null;
        }
    }

    public JSpinner getSpPeso() {
        return spPeso;
    }

    public JSpinner getSpAlt() {
        return spAlt;
    }

    public String getProvinciaRit() {
        return provinciaRit.getText();
    }

    public String getCittaRit() {
        return cittaRit.getText();
    }

    public Integer getNumeroRit() {
        try {
            return Integer.parseInt(numeroRit.getText());
        } catch (Exception e) {
            return null;
        }
    }

    public LocalDate getDataNascita() {
        Date date = (Date) spDataNascita.getValue();
        return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
    }


    public JButton getBtnCerca() {
        return btnCerca;
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

    public void showEsito(boolean esito, String text) {
        if (esito) {
            JOptionPane.showMessageDialog(
                this,
                "Operazione completata con successo!\n" + text,
                "Successo",
                JOptionPane.INFORMATION_MESSAGE
            );
            if (mainMenu != null) {
                mainMenu.setMenuEnabled(false);
                mainMenu.showGestioneCarInserimento();
            }
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
