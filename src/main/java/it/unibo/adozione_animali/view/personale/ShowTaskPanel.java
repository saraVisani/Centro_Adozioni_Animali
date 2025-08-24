package it.unibo.adozione_animali.view.personale;

import it.unibo.adozione_animali.model.impl.TaskDAO;
import nu.studer.sample.tables.records.TaskRecord;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.LocalDate;
import java.util.List;

public class ShowTaskPanel extends JPanel {

    public ShowTaskPanel() {
        setLayout(new BorderLayout());

        JPanel searchPanel = new JPanel(new BorderLayout());
        JPanel searchPanelMore = new JPanel(new BorderLayout());
        JPanel searchPanelGen = new JPanel();
        JScrollPane searchScroll = new JScrollPane(searchPanel);
        searchScroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        searchScroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);

        JLabel codProvL = new JLabel("Provincia Centro");
        JTextField codProvF = new JTextField(5);
        JLabel codCittaL = new JLabel("Città Centro");
        JTextField codCittaF = new JTextField(3);
        JLabel numeroL = new JLabel("Civico Centro");
        JTextField numeroF = new JTextField();
        JLabel dataL = new JLabel("Data (aaaa-mm-dd)");
        JTextField dataF = new JTextField();
        JButton search = new JButton("Cerca");


        searchPanel.add(searchPanelMore, BorderLayout.NORTH);
        searchPanelMore.add(searchPanelGen, BorderLayout.NORTH);

        GroupLayout searchLayout = new GroupLayout(searchPanelGen);
        searchPanelGen.setLayout(searchLayout);
        searchLayout.setAutoCreateGaps(true);
        searchLayout.setAutoCreateContainerGaps(true);

        searchLayout.setHorizontalGroup(searchLayout.createSequentialGroup()
                .addGroup(searchLayout.createParallelGroup()
                        .addComponent(codProvL)
                        .addComponent(codCittaL)
                        .addComponent(numeroL)
                        .addComponent(dataL))
                .addGroup(searchLayout.createParallelGroup()
                        .addComponent(codProvF)
                        .addComponent(codCittaF)
                        .addComponent(numeroF)
                        .addComponent(dataF)
                        .addComponent(search))
        );
        searchLayout.setVerticalGroup(searchLayout.createSequentialGroup()
                .addGroup(searchLayout.createParallelGroup()
                        .addComponent(codProvL)
                        .addComponent(codProvF))
                .addGroup(searchLayout.createParallelGroup()
                        .addComponent(codCittaL)
                        .addComponent(codCittaF))
                .addGroup(searchLayout.createParallelGroup()
                        .addComponent(numeroL)
                        .addComponent(numeroF))
                .addGroup(searchLayout.createParallelGroup()
                        .addComponent(dataL)
                        .addComponent(dataF))
                .addComponent(search)
        );
        //TODO--> cambia il borderlayout con il box nel pannello che contiene quello della tabella e i pulsanti
        JPanel tablePanel = new JPanel();
        JTable table = new JTable();
        //JScrollPane tableScroll = new JScrollPane(tablePanel);
        //tableScroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        //tableScroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        searchPanel.add(tablePanel, BorderLayout.SOUTH);
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setSize(600, 100);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        tablePanel.add(scrollPane);

        search.addActionListener(e -> {
            //TODO --> mettere try catch
            String[] columns = { "Codice Fiscale", "Data", "Numero Turno", "Lavoro"};
            DefaultTableModel model = new DefaultTableModel(columns, 0);
            table.setModel(model);
            List<TaskRecord> tasks = new TaskDAO().getTasks(codProvF.getText(), codCittaF.getText(),
                    Integer.parseInt(numeroF.getText()), LocalDate.parse(dataF.getText()));
            tasks.forEach(task -> {
                Object[] row = {
                        task.getCf(),
                        task.getDataTask(),
                        task.getNumero(),
                        task.getLavoro()
                };
                model.addRow(row);
            });
            searchPanel.revalidate();
            searchPanel.repaint();
        });

        add(searchPanel, BorderLayout.CENTER);
    }
}
