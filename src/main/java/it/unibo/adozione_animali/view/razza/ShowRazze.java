package it.unibo.adozione_animali.view.razza;

import it.unibo.adozione_animali.model.impl.RazzaDAO;
import nu.studer.sample.Tables;
import org.jooq.Record2;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class ShowRazze extends JPanel {

    public ShowRazze() {
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
                        .addComponent(numeroL))
                .addGroup(searchLayout.createParallelGroup()
                        .addComponent(codProvF)
                        .addComponent(codCittaF)
                        .addComponent(numeroF)
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
                .addComponent(search)
        );

        JPanel tablePanel = new JPanel();
        JTable table = new JTable();

        searchPanelMore.add(tablePanel);
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setSize(600, 50);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        tablePanel.add(scrollPane);

        search.addActionListener(e -> {
            try {
                String[] columns = { "Codice Specie", "Nome Razza"};
                DefaultTableModel model = new DefaultTableModel(columns, 0);
                table.setModel(model);
                List<Record2<String, String>> races = new RazzaDAO().getRaces(codProvF.getText(), codCittaF.getText(),
                        Integer.parseInt(numeroF.getText()));
                races.forEach(race -> {
                    Object[] row = {
                            race.get(Tables.RAZZA.COD_SPECIE),
                            race.get(Tables.RAZZA.NOME)
                    };
                    model.addRow(row);
                });
                searchPanel.revalidate();
                searchPanel.repaint();
                JOptionPane.showMessageDialog(this, "La ricerca è avvenuta correttamente");
            } catch (Exception numb) {
                JOptionPane.showMessageDialog(this, "Errore nell'inserimento." +
                        " Ricontrollare che i campi siano stati riempiti correttamente");
            }

        });

        add(searchScroll, BorderLayout.CENTER);
    }
}
