package it.unibo.adozione_animali.view.richiedenti;

import it.unibo.adozione_animali.model.impl.RichiedenteDAO;
import nu.studer.sample.Tables;
import org.jooq.Record;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class ShowRichiedentiPanel extends JPanel {

    public ShowRichiedentiPanel() {
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

        String[] columns = {"Cognome", "Nome", "Codice Fiscale", "Email", "Telefono"};
        DefaultTableModel model = new DefaultTableModel(columns, 0);
        table.setModel(model);
        List<Record> richiedenti = new RichiedenteDAO().getRichiedenti();
        richiedenti.forEach(richiedente -> {
            Object[] row = {
                    richiedente.get(Tables.PERSONA.COGNOME),
                    richiedente.get(Tables.PERSONA.NOME),
                    richiedente.get(Tables.PERSONA.CF),
                    richiedente.get(Tables.PERSONA.EMAIL),
                    richiedente.get(Tables.PERSONA.N_TELEFONO)
            };
            model.addRow(row);
        });

        add(searchScroll, BorderLayout.CENTER);
    }
}
