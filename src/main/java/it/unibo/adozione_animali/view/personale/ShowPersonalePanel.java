package it.unibo.adozione_animali.view.personale;

import javax.swing.*;
import java.awt.*;

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
        tablePanel.add(table);
        JScrollPane tableScroll = new JScrollPane(tablePanel);
        tableScroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        tableScroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        searchPanel.add(tableScroll, BorderLayout.CENTER);

        add(searchPanel, BorderLayout.CENTER);
    }
}
