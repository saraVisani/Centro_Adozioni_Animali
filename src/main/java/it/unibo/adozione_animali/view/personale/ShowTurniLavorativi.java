package it.unibo.adozione_animali.view.personale;

import it.unibo.adozione_animali.model.impl.TurnoLavorativoDAO;
import nu.studer.sample.Tables;
import nu.studer.sample.tables.records.TurnoLavorativoRecord;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class ShowTurniLavorativi extends JPanel {

    public ShowTurniLavorativi() {
        setLayout(new BorderLayout());

        setLayout(new BorderLayout());

        JPanel searchPanel = new JPanel(new BorderLayout());
        JPanel searchPanelMore = new JPanel(new BorderLayout());
        JPanel searchPanelGen = new JPanel();
        JScrollPane searchScroll = new JScrollPane(searchPanel);
        searchScroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        searchScroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);

        searchPanel.add(searchPanelMore, BorderLayout.CENTER);
        searchPanelMore.add(searchPanelGen, BorderLayout.NORTH);

        GroupLayout searchLayout = new GroupLayout(searchPanelGen);
        searchPanelGen.setLayout(searchLayout);
        searchLayout.setAutoCreateGaps(true);
        searchLayout.setAutoCreateContainerGaps(true);

        JPanel tablePanel = new JPanel();
        JTable table = new JTable();
        JScrollPane tableScroll = new JScrollPane(table);
        tablePanel.add(tableScroll);
        tableScroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        tableScroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        searchPanel.add(tableScroll, BorderLayout.CENTER);

        String[] columns = {"Data", "Numero Turno"};
        DefaultTableModel model = new DefaultTableModel(columns, 0);
        table.setModel(model);
        List<TurnoLavorativoRecord> turns = new TurnoLavorativoDAO().getTurns();
        turns.forEach(turn -> {
            Object[] row = {
                    turn.get(Tables.TURNO_LAVORATIVO.DATA_TURNO_LAV),
                    turn.get(Tables.TURNO_LAVORATIVO.NUMERO),
            };
            model.addRow(row);
        });

        add(searchScroll, BorderLayout.CENTER);
    }
}
