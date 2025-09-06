package it.unibo.adozione_animali.view.pulsanti.spazio;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

import it.unibo.adozione_animali.util.ColorUtils;

public class Info extends JPanel {

    private JTable table;
    private JScrollPane scrollPane;

    public Info() {
        setLayout(new BorderLayout());
        setBackground(ColorUtils.fromHex("6B82FF"));

        // Label descrizione fissa
        JLabel descriptionLabel = new JLabel(
            "<html>La tabella mostra gli spazi disponibili o richiesti.<br>" +
            "Ogni spazio ha un codice identificativo (ID_SPAZIO), un tipo (INTERNO, ESTERNO o INTERNO/ESTERNO) e una dimensione minima.<br>" +
            "Gli spazi possono rappresentare quelli reali posseduti da una persona o lo spazio minimo richiesto da una razza.</html>"
        );
        descriptionLabel.setOpaque(true);
        descriptionLabel.setBackground(ColorUtils.fromHex("6B82FF"));
        descriptionLabel.setForeground(Color.WHITE);
        descriptionLabel.setHorizontalAlignment(SwingConstants.CENTER);
        descriptionLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        add(descriptionLabel, BorderLayout.NORTH);

        // JTable vuota inizialmente
        DefaultTableModel model = new DefaultTableModel();
        table = new JTable(model);
        table.setFillsViewportHeight(true);
        table.setAutoCreateRowSorter(true); // permette di ordinare
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);
    }

    public JTable getTable() {
        return table;
    }

    public JScrollPane getScrollPane() {
        return scrollPane;
    }
}
