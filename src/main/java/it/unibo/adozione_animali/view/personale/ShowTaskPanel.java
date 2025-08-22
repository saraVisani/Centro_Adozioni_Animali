package it.unibo.adozione_animali.view.personale;

import javax.swing.*;
import java.awt.*;

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


        searchPanel.add(searchPanelMore, BorderLayout.CENTER);
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

        search.addActionListener(e -> {
            JPanel tablePanel = new JPanel();
            JTable table = new JTable();
            tablePanel.add(table);
            JScrollPane tableScroll = new JScrollPane(tablePanel);
            tableScroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
            tableScroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
            searchPanel.add(tableScroll, BorderLayout.CENTER);
            searchPanel.revalidate();
            searchPanel.repaint();
        });

        add(searchPanel, BorderLayout.CENTER);
    }
}
