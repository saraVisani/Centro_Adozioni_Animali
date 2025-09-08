package it.unibo.adozione_animali.view.pulsanti;

import java.awt.*;

import java.util.List;

import javax.swing.*;

import it.unibo.adozione_animali.util.ItemSelezionabile;

public class IndirizziPanel extends JPanel{
    private JTextField provinciaNuovo;
    private JTextField cittaNuovo;
    private JTextField numeroNuovo;
    private JComboBox<ItemSelezionabile> regione;
    private JComboBox<ItemSelezionabile> provinciaBox;
    private JComboBox<ItemSelezionabile> cittaBox;
    private JCheckBox inserireProvincia;
    private JCheckBox inserireCitta;

    private JComboBox<String> cf;

    private JTextField nomeProvincia;
    private JTextField nomeCitta;
    private JTextField indirizzo;

    private IndirizzoController controller;
    private final JButton btnCerca;

    public IndirizziPanel(){
        setLayout(new BorderLayout());

        // Pannello filtri
        JPanel filterPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;

        inserireProvincia = new JCheckBox();
        inserireProvincia.addActionListener(e -> this.controller.inserimentoProvincia());
        provinciaBox = new JComboBox<>();
        provinciaBox.addActionListener(e -> this.controller.provinciaSelezionata(this.getProvinciaSelezionata()));
        provinciaNuovo = new JTextField(30);
        provinciaNuovo.setEnabled(false);
        addUpdateListener(provinciaNuovo);

        nomeProvincia = new JTextField(30);
        nomeProvincia.setEnabled(false);
        addUpdateListener(provinciaNuovo);
        regione = new JComboBox<>();
        regione.setEnabled(false);
        regione.addActionListener(e -> this.aggiornabutton());

        inserireCitta = new JCheckBox();
        inserireCitta.addActionListener(e -> this.controller.inserimentoCitta());
        cittaBox = new JComboBox<>();
        cittaBox.setEnabled(false);
        cittaBox.addActionListener(e -> aggiornabutton());
        cittaNuovo = new JTextField(30);
        cittaNuovo.setEnabled(false);
        addUpdateListener(cittaNuovo);

        nomeCitta = new JTextField(30);
        nomeCitta.setEnabled(false);
        addUpdateListener(nomeCitta);

        numeroNuovo = new JTextField(5);
        addUpdateListener(numeroNuovo);

        indirizzo = new JTextField(30);
        addUpdateListener(indirizzo);
        cf = new JComboBox<>();

        Object[][] rows = {
            {"Nuova Provincia", inserireProvincia},
            {"Seleziona Provincia", provinciaBox},
            {"Inserisci Nuova Provincia", provinciaNuovo},
            {"Nome Provincia", nomeProvincia},
            {"Regione", regione},

            {"Nuova Città", inserireCitta},
            {"Seleziona Città", cittaBox},
            {"Inserisci Nuova Città", cittaNuovo},
            {"Nome Città", nomeCitta},

            {"Inserisci Nuovo Numero Civico", numeroNuovo},
            {"Via", indirizzo},
            {"Identificativo Persona", cf},
        };

        int cols = 5;
        for (int i = 0; i < rows.length; i++) {
            // blocco corrente (etichette+valori)
            int block = i / cols;
            int rowEtichette = block * 2;
            int rowValori = rowEtichette + 1;

            // decidi dinamicamente le colonne per questo blocco
            int colsForBlock = (block == 1) ? 4 : 5; // il blocco 1 (Città) ha solo 4 colonne

            int col = i % colsForBlock;

            // Etichetta
            gbc.gridx = col;
            gbc.gridy = rowEtichette;
            filterPanel.add(new JLabel((String) rows[i][0]), gbc);

            // Componente
            gbc.gridy = rowValori;
            filterPanel.add((Component) rows[i][1], gbc);
        }

        btnCerca = new JButton();
        btnCerca.setEnabled(false);
        btnCerca.addActionListener(e -> this.controller.salvaInserimento());
    }

    private boolean isValid(ItemSelezionabile item) {
        return item != null && item.getCodice() != null
            && !item.getCodice().isEmpty() && !item.getCodice().equals("--select--");
    }

    private boolean isValidString(String s) {
        return s != null && !s.isEmpty() && !s.equals("--select--");
    }

    private boolean isValidCod(String s) {
        return s != null && !s.isEmpty() && !s.equals("--select--") && s.strip().length() <= 5;
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
        boolean result;
        if(getInserireProvincia() && getInserireCitta()){
            result = isValidCod(getNuovaProvincia()) && isValidCod(getNuovaCitta()) && isNumber(getNuovoNumero())
                && isValidString(getNomeProvinciaString()) && isValidString(getNomeCittaString()) && isValidString(getNomeVia())
                && isValid((ItemSelezionabile)regione.getSelectedItem());

        }else if(!(getInserireProvincia() && getInserireCitta())){
            result = isValid((ItemSelezionabile)provinciaBox.getSelectedItem()) && isValid((ItemSelezionabile)cittaBox.getSelectedItem())
                && isNumber(getNuovoNumero()) && isValidString(getNomeVia());

        }else if(getInserireProvincia() && !(getInserireCitta())){
            result = isValidCod(getNuovaProvincia()) && isValidString(getNomeProvinciaString()) && isValid((ItemSelezionabile)regione.getSelectedItem());
            if(isValidString(getNomeVia()) || isValid((ItemSelezionabile)cittaBox.getSelectedItem()) || isNumber(getNuovoNumero())){
                result = result && isValidString(getNomeVia()) && isValid((ItemSelezionabile)cittaBox.getSelectedItem()) && isNumber(getNuovoNumero());
            }

        }else if(getInserireProvincia() && getInserireCitta()){
            result = isValidCod(getNuovaProvincia()) && isValidString(getNomeProvinciaString()) && isValid((ItemSelezionabile)regione.getSelectedItem())
                && isValidCod(getNuovaCitta()) && isValidString(getNomeCittaString());
            if(isValidString(getNomeVia()) || isNumber(getNuovoNumero())){
                result = result && isValidString(getNomeVia()) && isNumber(getNuovoNumero());
            }

        }else if(!(getInserireProvincia()) && getInserireCitta()){
            result = isValid((ItemSelezionabile)provinciaBox.getSelectedItem())
                && isValidCod(getNuovaCitta()) && isValidString(getNomeCittaString());
            if(isValidString(getNomeVia()) || isNumber(getNuovoNumero())){
                result = result && isValidString(getNomeVia()) && isNumber(getNuovoNumero());
            }

        }else if(!(getInserireProvincia()) && getInserireCitta()){
            result = isValid((ItemSelezionabile)provinciaBox.getSelectedItem())
                && isValidCod(getNuovaCitta()) && isValidString(getNomeCittaString())
                && isNumber(getNuovoNumero()) && isValidString(getNomeVia());

        }else if(getInserireProvincia() && !(getInserireCitta())){
            result = isValidCod(getNuovaProvincia()) && isValidString(getNomeProvinciaString()) && isValid((ItemSelezionabile)regione.getSelectedItem())
                && isNumber(getNuovoNumero()) && isValidString(getNomeVia()) && isValid((ItemSelezionabile)cittaBox.getSelectedItem());

        }else{
            result = isValid((ItemSelezionabile)provinciaBox.getSelectedItem()) && isValid((ItemSelezionabile)cittaBox.getSelectedItem())
                && isNumber(getNuovoNumero()) && isValidString(getNomeVia());
        }

        btnCerca.setEnabled(result);
    }

    public void setController(IndirizzoController controller){
        this.controller = controller;
    }

    public void setProvince(List<ItemSelezionabile> province) {
        setComboBoxWithEmptyFirst(provinciaBox, province, new ItemSelezionabile("", "--select--"));
    }

    public void setRegione(List<ItemSelezionabile> regione) {
        setComboBoxWithEmptyFirst(this.regione, regione, new ItemSelezionabile("", "--select--"));
    }

    public void setCitta(List<ItemSelezionabile> citta) {
        setComboBoxWithEmptyFirst(cittaBox, citta, new ItemSelezionabile("", "--select--"));
    }

    public void setCf(List<String> cf) {
        setComboBoxWithEmptyFirst(this.cf, cf, "--select--");
    }

    public String getProvinciaSelezionata() {
        ItemSelezionabile selected = (ItemSelezionabile) provinciaBox.getSelectedItem();
        return selected != null ? selected.getCodice() : null;
    }

    public String getNuovaProvincia() {
        return provinciaNuovo.getText();
    }

    public String getNomeProvinciaString() {
        return nomeProvincia.getText();
    }

    public String getRegioneSelezionata() {
        ItemSelezionabile selected = (ItemSelezionabile) regione.getSelectedItem();
        return selected != null ? selected.getCodice() : null;
    }

    public String getCittaSelezionata() {
        ItemSelezionabile selected = (ItemSelezionabile) cittaBox.getSelectedItem();
        return selected != null ? selected.getCodice() : null;
    }

    public String getNuovaCitta() {
        return cittaNuovo.getText();
    }

    public String getNomeCittaString() {
        return nomeCitta.getText();
    }

    public String getNuovoNumero() {
        return numeroNuovo.getText();
    }

    public String getNomeVia() {
        return indirizzo.getText();
    }

    public boolean getInserireProvincia() {
        return inserireProvincia.isSelected();
    }

    public boolean getInserireCitta() {
        return inserireCitta.isSelected();
    }

    public String getCf() {
        return (String) cf.getSelectedItem();
    }

    public JTextField getProvinciaNuovo() {
        return provinciaNuovo;
    }

    public JTextField getCittaNuovo() {
        return cittaNuovo;
    }

    public JTextField getNumeroNuovo() {
        return numeroNuovo;
    }

    public JComboBox<ItemSelezionabile> getRegione() {
        return regione;
    }

    public JComboBox<ItemSelezionabile> getProvinciaBox() {
        return provinciaBox;
    }

    public JComboBox<ItemSelezionabile> getCittaBox() {
        return cittaBox;
    }

    public JTextField getNomeProvincia() {
        return nomeProvincia;
    }

    public JTextField getNomeCitta() {
        return nomeCitta;
    }

    public JTextField getIndirizzo() {
        return indirizzo;
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

    private void addUpdateListener(JTextField field) {
        field.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
            @Override
            public void insertUpdate(javax.swing.event.DocumentEvent e) {
                aggiornabutton();
            }

            @Override
            public void removeUpdate(javax.swing.event.DocumentEvent e) {
                aggiornabutton();
            }

            @Override
            public void changedUpdate(javax.swing.event.DocumentEvent e) {
                aggiornabutton();
            }
        });
    }

    public void showEsito(boolean esito, String text) {
        if (esito) {
            JOptionPane.showMessageDialog(
                this,
                "Operazione completata con successo!\n" + text,
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
