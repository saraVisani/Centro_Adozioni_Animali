package it.unibo.adozione_animali.view.statistiche;

import java.awt.*;
import java.util.List;
import javax.swing.*;

import it.unibo.adozione_animali.controller.impl.UpdateStatisticaController;
import it.unibo.adozione_animali.util.ColorUtils;

public class StatisticheUpdate extends JPanel {
    private JComboBox<String> tipo;
    private JButton inserisciBtn;
    private UpdateStatisticaController controller;

    public StatisticheUpdate() {
        setLayout(new BorderLayout(10, 10));

        JLabel title = new JLabel("Inserimento Caratteristiche");
        title.setFont(new Font("Arial", Font.BOLD, 18));
        title.setHorizontalAlignment(SwingConstants.CENTER);
        add(title, BorderLayout.NORTH);

        JPanel tablePanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Header
        String[] headers = { "Nome Statistica"};
        gbc.gridy = 0;
        for (int i = 0; i < headers.length; i++) {
            gbc.gridx = i;
            JLabel lbl = new JLabel(headers[i]);
            lbl.setFont(new Font("Arial", Font.BOLD, 14));
            tablePanel.add(lbl, gbc);
        }

        inserisciBtn = new JButton("Request");
        inserisciBtn.setEnabled(false);

        // Una sola riga di selezione
        gbc.gridy = 1;

        // Provincia
        gbc.gridx = 0;
        tipo = new JComboBox<>();
        tablePanel.add(tipo, gbc);

        // Listener combo che chiamano il controller
        tipo.addActionListener(e -> aggiornaStatoPulsante());

        // JTextField nome
        inserisciBtn.addActionListener(e -> {
            if (controller != null) controller.salvaRequest();
        });

        add(new JScrollPane(tablePanel), BorderLayout.CENTER);
        add(inserisciBtn, BorderLayout.SOUTH);
    }

    // --- Metodi per collegare il controller ---
    public void setController(UpdateStatisticaController controller) {
        this.controller = controller;
    }

    // --- Metodi pubblici per aggiornare i dati delle combo ---
    public void setTipo(List<String> valori) {
        setComboBoxWithEmptyFirst(tipo, valori, "--select--");
    }

    // --- Getters per valori selezionati ---
    public String getTipo() {
        return (String) tipo.getSelectedItem();
    }

    private void aggiornaStatoPulsante() {
        inserisciBtn.setEnabled(getTipo() != null && !getTipo().equals("--select--"));
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
