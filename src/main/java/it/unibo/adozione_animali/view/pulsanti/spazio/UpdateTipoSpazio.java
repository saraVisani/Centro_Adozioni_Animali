package it.unibo.adozione_animali.view.pulsanti.spazio;

import java.awt.*;
import java.util.List;
import javax.swing.*;

import it.unibo.adozione_animali.controller.impl.UpdateTipoSpazioController;
import it.unibo.adozione_animali.util.ColorUtils;
import it.unibo.adozione_animali.util.ItemSelezionabile;

public class UpdateTipoSpazio extends JPanel {

    private JComboBox<ItemSelezionabile> tipo;
    private JComboBox<String> codSpazio;
    private JButton inserisciBtn;
    private UpdateTipoSpazioController controller;

    public UpdateTipoSpazio() {
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
        String[] headers = { "Codice Spazio", "Tipo"};
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
        codSpazio = new JComboBox<>();
        tablePanel.add(codSpazio, gbc);

        // Città
        gbc.gridx = 1;
        tipo = new JComboBox<>();
        tipo.setEnabled(false);
        tablePanel.add(tipo, gbc);

        // Listener combo che chiamano il controller
        tipo.addActionListener(e -> {
            if (controller != null) controller.codiceSelected(getCodice());
            aggiornaStatoPulsante();});

        // JTextField nome
        codSpazio.addActionListener(e -> aggiornaStatoPulsante());

        inserisciBtn.addActionListener(e -> {
            if (controller != null) controller.salvaAggiornamento();
        });

        add(new JScrollPane(tablePanel), BorderLayout.CENTER);
        add(inserisciBtn, BorderLayout.SOUTH);
    }

    // --- Metodi per collegare il controller ---
    public void setController(UpdateTipoSpazioController controller) {
        this.controller = controller;
    }

    // --- Metodi pubblici per aggiornare i dati delle combo ---
    public void setTipo(List<ItemSelezionabile> valori) {
        tipo.setModel(new DefaultComboBoxModel<>(valori.toArray(new ItemSelezionabile[0])));
        tipo.setEnabled(true);
    }

    public void setCodice(List<String> valori) {
        codSpazio.setModel(new DefaultComboBoxModel<>(valori.toArray(new String[0])));
    }

    // --- Getters per valori selezionati ---
    public String getTipo() {
        return (String) tipo.getSelectedItem();
    }

    public Integer getCodice() {
        try {
            return Integer.parseInt((String)codSpazio.getSelectedItem());
        } catch (NumberFormatException e) {
            return null; // oppure 0 se preferisci
        }
    }

    private void aggiornaStatoPulsante() {
        boolean completo = getTipo() != null
                && getCodice() != null;

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
