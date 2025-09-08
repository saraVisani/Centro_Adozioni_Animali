package it.unibo.adozione_animali.view.pulsanti.centri;

import java.awt.*;
import java.util.List;

import javax.swing.*;

import it.unibo.adozione_animali.controller.impl.DeleteSpecieCentroController;
import it.unibo.adozione_animali.util.ColorUtils;
import it.unibo.adozione_animali.util.Enum.Specie;
import it.unibo.adozione_animali.util.ItemSelezionabile;

public class DeleteSpecie extends JPanel{

    private JComboBox<ItemSelezionabile> provinciaBox;
    private JComboBox<ItemSelezionabile> cittaBox;
    private JComboBox<String> numeroBox;
    private DefaultListModel<String> listModel;
    private JList<String> codiceSpecList;
    private JButton inserisciBtn;
    private DeleteSpecieCentroController controller;

    public DeleteSpecie() {
        setLayout(new BorderLayout(10, 10));

        JLabel title = new JLabel("Elimina Specie dal Centro");
        title.setFont(new Font("Arial", Font.BOLD, 18));
        title.setHorizontalAlignment(SwingConstants.CENTER);
        add(title, BorderLayout.NORTH);

        JPanel tablePanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Header
        String[] headers = { "Provincia", "Città", "Numero civico",
                                "Specie" };
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

        // Lista codici specie
        gbc.gridx = 3;
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

        inserisciBtn.addActionListener(e -> {
            if (controller != null) controller.salvaDelete();
        });

        // JList codici specie
        codiceSpecList.addListSelectionListener(e -> aggiornaStatoPulsante());


        add(new JScrollPane(tablePanel), BorderLayout.CENTER);
        add(inserisciBtn, BorderLayout.SOUTH);

        setCodiciSpecifici(List.of(Specie.CANE.getDisplayName(), Specie.GATTO.getDisplayName(),
        Specie.RODITORE.getDisplayName(),Specie.RETTILE.getDisplayName(),Specie.VOLATILE.getDisplayName()));
    }

    // --- Metodi per collegare il controller ---
    public void setController(DeleteSpecieCentroController controller) {
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

    public void setCodiciSpecifici(List<String> codici) {
        listModel.clear();
        listModel.addElement("--select--");
        codici.forEach(listModel::addElement);
        codiceSpecList.setSelectedIndex(0);
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

    public List<String> getCodiciSpecificiSelezionati() {
        return codiceSpecList.getSelectedValuesList();
    }

    private boolean isValid(ItemSelezionabile item) {
        return item != null && item.getCodice() != null
            && !item.getCodice().isEmpty() && !item.getCodice().equals("--select--");
    }

    private boolean isListValid(List<String> lista) {
        return lista != null && !lista.isEmpty() &&
            lista.stream().allMatch(item -> item != null
                                            && item != null
                                            && !item.isEmpty()
                                            && !item.equals("--select--"));
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
        List<String> codItemSelectable = codiceSpecList.getSelectedValuesList();

        String numero = getNumeroSelezionato();

        boolean control = isValid(selectedProvincia)
            && isValid(selectedCitta)
            && isListValid(codItemSelectable)
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
