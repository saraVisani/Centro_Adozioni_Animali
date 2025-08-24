package it.unibo.adozione_animali.view.animale.caratteristiche;

import javax.swing.*;

import it.unibo.adozione_animali.util.ColorUtils;
import it.unibo.adozione_animali.util.ItemSelezionabile;
import it.unibo.adozione_animali.controller.impl.AggiornamentoCaratteristicaController;
import java.awt.*;
import java.awt.event.*;
import java.util.List;

public class Aggiornamento extends JPanel {

    private JComboBox<ItemSelezionabile> provinciaBox;
    private JComboBox<ItemSelezionabile> cittaBox;
    private JComboBox<String> numeroBox;
    private JComboBox<String> codiceBox;
    private JComboBox<String> tipoBox;
    private JComboBox<ItemSelezionabile> codiceSpecBox;
    private JCheckBox nuovoCheck;
    private JButton inserisciBtn;

    private AggiornamentoCaratteristicaController controller; // riferimento al controller

    public Aggiornamento() {
        setBackground(ColorUtils.fromHex("6B82FF"));
        setLayout(new BorderLayout(10, 10));

        JLabel title = new JLabel("Aggiornamento Caratteristiche");
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
                "Codice Animale", "Codice Specifica"};
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
            }
        }

        inserisciBtn = new JButton("Aggiorna");
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
        codiceSpecBox = new JComboBox<>();
        codiceSpecBox.setEnabled(false);
        tablePanel.add(codiceSpecBox, gbc);

        // Listener che chiamano il controller
        provinciaBox.addActionListener(e -> {
            if (controller != null) controller.provinciaSelezionata((String) provinciaBox.getSelectedItem());
        });

        cittaBox.addActionListener(e -> {
            if (controller != null) {
                String provincia = getProvinciaSelezionata();
                String citta = getCittaSelezionata();
                controller.cittaSelezionata(provincia, citta);
            }
        });

        numeroBox.addActionListener(e -> {
            if (controller != null) {
                String provincia = getProvinciaSelezionata();
                String citta = getCittaSelezionata();
                String numero = getNumeroSelezionato();
                controller.numeroSelezionato(provincia, citta, numero);
            }
        });

        codiceBox.addActionListener(e -> {
            if (controller != null) {
                String provincia = getProvinciaSelezionata();
                String citta = getCittaSelezionata();
                String numero = getNumeroSelezionato();
                String codAnimale = getCodiceAnimaleSelezionato();
                controller.animaleSelezionato(provincia, citta, numero, codAnimale);
            }
        });

        tipoBox.addActionListener(e -> {
            if (controller != null) {
                String provincia = getProvinciaSelezionata();
                String citta = getCittaSelezionata();
                String numero = getNumeroSelezionato();
                String codAnimale = getCodiceAnimaleSelezionato();
                String tipo = getTipoSelezionato();
                controller.tipoSelezionato(provincia, citta, numero, codAnimale, tipo);
            }
        });


        // Pulsante inserisci
        inserisciBtn.addActionListener(e -> {
            if (controller != null) controller.salvaAggiornamento();
        });

        add(new JScrollPane(tablePanel), BorderLayout.CENTER);
        add(inserisciBtn, BorderLayout.SOUTH);
    }

    // --- Metodi per collegare il controller ---
    public void setController(AggiornamentoCaratteristicaController controller) {
        this.controller = controller;
    }

    // --- Metodi pubblici per aggiornare i dati delle combo ---
    public void setProvince(List<ItemSelezionabile> province) {
        provinciaBox.setModel(new DefaultComboBoxModel<>(province.toArray(new ItemSelezionabile[0])));
        provinciaBox.setEnabled(true);
    }

    public void setCitta(List<ItemSelezionabile> citta) {
        cittaBox.setModel(new DefaultComboBoxModel<>(citta.toArray(new ItemSelezionabile[0])));
        cittaBox.setEnabled(true);
    }

    public void setNumeri(List<String> numeri) {
        numeroBox.setModel(new DefaultComboBoxModel<>(numeri.toArray(new String[0])));
        numeroBox.setEnabled(true);
    }

    public void setCodiciAnimale(List<String> codici) {
        codiceBox.setModel(new DefaultComboBoxModel<>(codici.toArray(new String[0])));
        codiceBox.setEnabled(true);
    }

    public void setTipi(List<String> tipi) {
        tipoBox.setModel(new DefaultComboBoxModel<>(tipi.toArray(new String[0])));
        tipoBox.setEnabled(true);
        nuovoCheck.setEnabled(true);
    }

    public void setCodiciSpecifici(List<ItemSelezionabile> codici) {
        codiceSpecBox.setModel(new DefaultComboBoxModel<>(codici.toArray(new ItemSelezionabile[0])));
        codiceSpecBox.setEnabled(true);
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
    public String getCodiceSpecificoSelezionato() {
        ItemSelezionabile selected = (ItemSelezionabile) codiceSpecBox.getSelectedItem();
        return selected != null ? selected.getCodice() : null;
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
