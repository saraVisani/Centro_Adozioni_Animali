package it.unibo.adozione_animali.view.pulsanti.centri;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

import it.unibo.adozione_animali.util.ColorUtils;

public class Info extends JPanel {

    private JTable table;
    private JScrollPane scrollPane;

    public Info() {
        setLayout(new BorderLayout());

        // Label descrizione fissa
        JLabel descriptionLabel = new JLabel(
            "<html>La tabella mostra i centri disponibili e le specie che possono ospitare.<br>" +
            "Ogni centro è identificato da provincia, città e numero civico, e ha un nome e una capienza massima.<br>" +
            "La tipologia collega i centri alle specie, mentre la tabella specie elenca tutte le specie gestite dal sistema.</html>"
        );
        descriptionLabel.setOpaque(true);
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
