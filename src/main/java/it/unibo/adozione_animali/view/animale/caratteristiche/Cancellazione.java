package it.unibo.adozione_animali.view.animale.caratteristiche;

import javax.swing.*;

import it.unibo.adozione_animali.util.ColorUtils;
import it.unibo.adozione_animali.util.ItemSelezionabile;
import it.unibo.adozione_animali.controller.impl.CancellazioneCaratteristicaController;
import java.awt.*;
import java.awt.event.*;
import java.util.List;

public class Cancellazione extends JPanel {

    private JComboBox<ItemSelezionabile> provinciaBox;
    private JComboBox<ItemSelezionabile> cittaBox;
    private JComboBox<String> numeroBox;
    private JComboBox<String> codiceBox;
    private JComboBox<String> tipoBox;
    private DefaultListModel<ItemSelezionabile> listModel;
    private JList<ItemSelezionabile> codiceSpecList;
    private JCheckBox nuovoCheck;
    private JButton inserisciBtn;

    private CancellazioneCaratteristicaController controller; // riferimento al controller

    public Cancellazione() {
        setBackground(ColorUtils.fromHex("6B82FF"));
        setLayout(new BorderLayout(10, 10));

        JLabel title = new JLabel("Cancellazione Caratteristiche");
        title.setFont(new Font("Arial", Font.BOLD, 18));
        title.setHorizontalAlignment(SwingConstants.CENTER);
        add(title, BorderLayout.NORTH);

        JPanel tablePanel = new JPanel(new GridBagLayout());
        tablePanel.setBackground(ColorUtils.fromHex("6B82FF"));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5,5,5,5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Header
        String[] headers = {"Tipo Caratteristica", "Provincia", "Città", "Numero civico",
                "Codice Animale", "Codice Specifica", "Cancellazione Completa"};
        gbc.gridy = 0;
        for (int i = 0; i < headers.length; i++) {
            gbc.gridx = i;
            JLabel lbl = new JLabel(headers[i]);
            lbl.setForeground(Color.WHITE);
            lbl.setFont(new Font("Arial", Font.BOLD, 14));
            tablePanel.add(lbl, gbc);

            if ("Tipo Caratteristica".equals(headers[i])) {
                addCustomPopup(lbl, "Seleziona se la caratteristica è di tipo Fisico o di Carattere.");
            } else if ("Codice Specifica".equals(headers[i])) {
                addCustomPopup(lbl, "Puoi selezionare uno o più codici specifici relativi alla caratteristica.");
            } else if ("Cancellazione Completa".equals(headers[i])) {
                addCustomPopup(lbl, "Spunta se si tratta di una nuova caratteristica da aggiungere.");
            }
        }

        inserisciBtn = new JButton("Cancella");
        inserisciBtn.setEnabled(false);

        // Una sola riga di selezione
        gbc.gridy = 1;

        // Provincia
        gbc.gridx = 1;
        provinciaBox = new JComboBox<>();
        tablePanel.add(provinciaBox, gbc);

        // Città
        gbc.gridx = 2;
        cittaBox = new JComboBox<>();
        cittaBox.setEnabled(false);
        tablePanel.add(cittaBox, gbc);

        // Numero civico
        gbc.gridx = 3;
        numeroBox = new JComboBox<>();
        numeroBox.setEnabled(false);
        tablePanel.add(numeroBox, gbc);

        // Codice Animale
        gbc.gridx = 4;
        codiceBox = new JComboBox<>();
        codiceBox.setEnabled(false);
        tablePanel.add(codiceBox, gbc);

        // Tipo Caratteristica
        gbc.gridx = 0;
        tipoBox = new JComboBox<>();
        tipoBox.setEnabled(false);
        tablePanel.add(tipoBox, gbc);

        // Lista codici specifica
        gbc.gridx = 5;
        listModel = new DefaultListModel<>();
        codiceSpecList = new JList<>(listModel);
        codiceSpecList.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        codiceSpecList.setEnabled(false);
        codiceSpecList.setVisibleRowCount(3);
        JScrollPane listScroll = new JScrollPane(codiceSpecList);
        listScroll.setPreferredSize(new Dimension(100, 60));
        tablePanel.add(listScroll, gbc);

        // Checkbox Inserimento Nuovo
        gbc.gridx = 6;
        nuovoCheck = new JCheckBox();
        nuovoCheck.setEnabled(false);
        nuovoCheck.setPreferredSize(new Dimension(25,25));
        tablePanel.add(nuovoCheck, gbc);

        // Listener che chiamano il controller
        provinciaBox.addActionListener(e -> {
            if (controller != null) {
                aggiornaStatoPulsante();
                ItemSelezionabile selected = (ItemSelezionabile) provinciaBox.getSelectedItem();
                controller.provinciaSelezionata(selected != null ? selected.getCodice() : null);
            }
        });

        cittaBox.addActionListener(e -> {
            if (controller != null) {
                aggiornaStatoPulsante();
                ItemSelezionabile selectedProvincia = (ItemSelezionabile) provinciaBox.getSelectedItem();
                ItemSelezionabile selectedCitta = (ItemSelezionabile) cittaBox.getSelectedItem();
                String provincia = selectedProvincia != null && !selectedProvincia.getCodice().isEmpty() ? selectedProvincia.getCodice() : null;
                String citta = selectedCitta != null && !selectedCitta.getCodice().isEmpty() ? selectedCitta.getCodice() : null;
                controller.cittaSelezionata(provincia, citta);
            }
        });

        numeroBox.addActionListener(e -> {
            if (controller != null) {
                aggiornaStatoPulsante();
                ItemSelezionabile selectedProvincia = (ItemSelezionabile) provinciaBox.getSelectedItem();
                ItemSelezionabile selectedCitta = (ItemSelezionabile) cittaBox.getSelectedItem();
                String provincia = selectedProvincia != null && !selectedProvincia.getCodice().isEmpty() ? selectedProvincia.getCodice() : null;
                String citta = selectedCitta != null && !selectedCitta.getCodice().isEmpty() ? selectedCitta.getCodice() : null;
                String numero = getNumeroSelezionato();
                controller.numeroSelezionato(provincia, citta, numero);
            }
        });

        codiceBox.addActionListener(e -> {
            if (controller != null) {
                aggiornaStatoPulsante();
                ItemSelezionabile selectedProvincia = (ItemSelezionabile) provinciaBox.getSelectedItem();
                ItemSelezionabile selectedCitta = (ItemSelezionabile) cittaBox.getSelectedItem();
                String provincia = selectedProvincia != null && !selectedProvincia.getCodice().isEmpty() ? selectedProvincia.getCodice() : null;
                String citta = selectedCitta != null && !selectedCitta.getCodice().isEmpty() ? selectedCitta.getCodice() : null;
                String numero = getNumeroSelezionato();
                String codAnimale = getCodiceAnimaleSelezionato();
                controller.animaleSelezionato(provincia, citta, numero, codAnimale, nuovoCheck.isSelected());
            }
        });

        tipoBox.addActionListener(e -> {
            if (controller != null) {
                aggiornaStatoPulsante();
                ItemSelezionabile selectedProvincia = (ItemSelezionabile) provinciaBox.getSelectedItem();
                ItemSelezionabile selectedCitta = (ItemSelezionabile) cittaBox.getSelectedItem();
                String provincia = selectedProvincia != null && !selectedProvincia.getCodice().isEmpty() ? selectedProvincia.getCodice() : null;
                String citta = selectedCitta != null && !selectedCitta.getCodice().isEmpty() ? selectedCitta.getCodice() : null;
                String numero = getNumeroSelezionato();
                String codAnimale = getCodiceAnimaleSelezionato();
                String tipo = getTipoSelezionato();
                controller.tipoSelezionato(provincia, citta, numero, codAnimale, tipo);
            }
        });

        codiceSpecList.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) { // evita doppio evento
                aggiornaStatoPulsante();
            }
        });

        // Pulsante inserisci
        inserisciBtn.addActionListener(e -> {
            if (controller != null) controller.salvaCancellazione();
        });

        add(new JScrollPane(tablePanel), BorderLayout.CENTER);
        add(inserisciBtn, BorderLayout.SOUTH);
    }

    public void setController(CancellazioneCaratteristicaController controller){
        this.controller = controller;
    }

    // --- Metodi per collegare il controller ---
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
        nuovoCheck.setEnabled(true);
    }

    public void setTipi(List<String> tipi) {
        setComboBoxWithEmptyFirst(tipoBox, tipi, "--select--");
    }

    public void setCodiciSpecifici(List<ItemSelezionabile> codici) {
        listModel.clear();
        listModel.addElement(new ItemSelezionabile("--select--", "--select--"));
        codici.forEach(listModel::addElement);
        codiceSpecList.setSelectedIndex(0);
        codiceSpecList.setEnabled(true);
    }


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

    public String getTipoSelezionato() {
        return (String) tipoBox.getSelectedItem();
    }

    // Per la lista dei codici specifici (può avere più selezioni)
    public List<String> getCodiciSpecificiSelezionati() {
        return codiceSpecList.getSelectedValuesList().stream()
                .map(ItemSelezionabile::getCodice)
                .toList();
    }


    // Per la checkbox
    public boolean isNuovoChecked() {
        return nuovoCheck.isSelected();
    }

    // Popup personalizzato
    private void addCustomPopup(JLabel label, String testo) {
        label.addMouseListener(new MouseAdapter() {
            JWindow popup;
            @Override
            public void mouseEntered(MouseEvent e) {
                popup = new JWindow();
                JLabel content = new JLabel("<html><div style='width:250px; font-size:14px;'>" + testo + "</div></html>");
                content.setOpaque(true);
                content.setBackground(new Color(255, 255, 200));
                content.setBorder(BorderFactory.createLineBorder(Color.BLACK));
                popup.getContentPane().add(content);
                popup.pack();

                Point location = label.getLocationOnScreen();
                popup.setLocation(location.x, location.y - popup.getHeight() - 5);
                popup.setVisible(true);
            }
            @Override
            public void mouseExited(MouseEvent e) {
                if (popup != null) popup.dispose();
            }
        });
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

    private boolean isValid(ItemSelezionabile item) {
        return item != null && item.getCodice() != null
            && !item.getCodice().isEmpty() && !item.getCodice().equals("--select--");
    }

    private boolean isValidString(String s) {
        return s != null && !s.isEmpty() && !s.equals("--select--");
    }

    private boolean isListValid(List<ItemSelezionabile> lista) {
        return lista != null && !lista.isEmpty() &&
            lista.stream().allMatch(item -> item != null
                                            && item.getCodice() != null
                                            && !item.getCodice().isEmpty()
                                            && !item.getCodice().equals("--select--"));
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
        List<ItemSelezionabile> codItemSelectable = codiceSpecList.getSelectedValuesList();

        String numero = getNumeroSelezionato();
        String codAnimale = getCodiceAnimaleSelezionato();
        String tipo = getTipoSelezionato();

        boolean control = isValid(selectedProvincia)
            && isValid(selectedCitta)
            && isListValid(codItemSelectable)
            && isValidString(codAnimale)
            && isValidString(tipo)
            && isNumber(numero); // qui controlliamo che sia un numero valido

        inserisciBtn.setEnabled(control);
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
