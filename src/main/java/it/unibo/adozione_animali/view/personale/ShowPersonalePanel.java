package it.unibo.adozione_animali.view.personale;

import it.unibo.adozione_animali.model.impl.PersonaleDAO;
import nu.studer.sample.Tables;
import org.jooq.Record;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class ShowPersonalePanel extends JPanel {

    public ShowPersonalePanel() {
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

        String[] columns = {"Codice Provincia Centro", "Codice Città Centro", "Civico Centro", "Nome",
                "Cognome", "Codice Fiscale"};
        DefaultTableModel model = new DefaultTableModel(columns, 0);
        table.setModel(model);
        List<Record> personale = new PersonaleDAO().getPersonale();
        personale.forEach(worker -> {
            Object[] row = {
                    worker.get(Tables.PERSONALE.COD_PROVINCIA),
                    worker.get(Tables.PERSONALE.COD_CITTA_),
                    worker.get(Tables.PERSONALE.NUMERO),
                    worker.get(Tables.PERSONA.NOME),
                    worker.get(Tables.PERSONA.COGNOME),
                    worker.get(Tables.PERSONA.CF)
            };
            model.addRow(row);
        });

        add(searchScroll, BorderLayout.CENTER);
    }
}
