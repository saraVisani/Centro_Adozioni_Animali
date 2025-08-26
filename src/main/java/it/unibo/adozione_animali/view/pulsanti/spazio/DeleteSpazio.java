package it.unibo.adozione_animali.view.pulsanti.spazio;

import java.awt.*;
import java.util.List;
import javax.swing.*;

import it.unibo.adozione_animali.controller.impl.CancellaSpazioController;
import it.unibo.adozione_animali.util.ColorUtils;

public class DeleteSpazio extends JPanel{

    private JComboBox<String> codSpazio;
    private JButton inserisciBtn;
    private CancellaSpazioController controller;

    public DeleteSpazio() {
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
        String[] headers = { "Codice Spazio"};
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

        // Listener combo che chiamano il controller
        codSpazio.addActionListener(e -> aggiornaStatoPulsante());

        inserisciBtn.addActionListener(e -> {
            if (controller != null) controller.salvaCancellazione();
        });

        add(new JScrollPane(tablePanel), BorderLayout.CENTER);
        add(inserisciBtn, BorderLayout.SOUTH);
    }

    // --- Metodi per collegare il controller ---
    public void setController(CancellaSpazioController controller) {
        this.controller = controller;
    }

    // --- Metodi pubblici per aggiornare i dati delle combo ---
    public void setCodici(List<String> valori) {
        codSpazio.setModel(new DefaultComboBoxModel<>(valori.toArray(new String[0])));
    }

    // --- Getters per valori selezionati ---
    public Integer getCodice() {
        String selected = (String) codSpazio.getSelectedItem();
        try {
            return selected != null ? Integer.parseInt(selected) : null;
        } catch (NumberFormatException e) {
            return null;
        }
    }

    private void aggiornaStatoPulsante() {
        boolean completo =  getCodice() != null;
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
