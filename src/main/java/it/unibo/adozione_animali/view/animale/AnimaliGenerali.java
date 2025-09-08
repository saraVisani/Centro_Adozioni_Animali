package it.unibo.adozione_animali.view.animale;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

import it.unibo.adozione_animali.util.ColorUtils;

public class AnimaliGenerali extends JPanel {

    private JTable table;
    private JScrollPane scrollPane;

    public AnimaliGenerali() {
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createTitledBorder(
                        BorderFactory.createEtchedBorder(),
                        "Informazioni Generali Animali",
                        TitledBorder.CENTER,
                        TitledBorder.TOP
                ),
                BorderFactory.createEmptyBorder(25, 10, 10, 10)
        ));

        // Label descrizione fissa
        JLabel descriptionLabel = new JLabel(
            "<html>La vista mostra le informazioni sugli animali gestiti dal sistema.<br>" +
            "Ogni animale è identificato da un centro e un codice e contiene dati di nascita, inserimento e adozione.<br>" +
            "Sono indicati provenienza, centro o residenza attuale, stato e idoneità, oltre a caratteristiche fisiche come peso e altezza.<br>" +
            "Sono presenti anche dati descrittivi come specie, razza, origine, lignaggio e requisiti minimi di spazio.</html>"
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
