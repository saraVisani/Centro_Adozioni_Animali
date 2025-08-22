package it.unibo.adozione_animali.view.personale;

import javax.swing.*;
import java.awt.*;

public class RemoveTaskPanel extends JPanel {

    public RemoveTaskPanel() {
        setLayout(new BorderLayout());

        JPanel removePanel = new JPanel(new BorderLayout());
        JPanel removePanelGen = new JPanel();
        removePanel.add(removePanelGen, BorderLayout.NORTH);
        removePanel.add(new JPanel(), BorderLayout.CENTER);
        GroupLayout removeLayout = new GroupLayout(removePanelGen);
        removePanelGen.setLayout(removeLayout);
        JScrollPane removeScroll = new JScrollPane(removePanel);
        removeScroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        removeScroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);


        JButton cancella = new JButton("Elimina");

        JTextField numeroTurnoF = new JTextField();
        JTextField dataF = new JTextField();
        JLabel numeroTurnoL = new JLabel("Numero Turno");
        JLabel dataL = new JLabel("Data (aaaa-mm-dd)");
        JLabel CFL = new JLabel("Codice Fiscale Lavoratore");
        JTextField CFF = new JTextField();

        removeLayout.setAutoCreateContainerGaps(true);
        removeLayout.setAutoCreateGaps(true);
        removeLayout.setHorizontalGroup(
                removeLayout.createSequentialGroup()
                        .addGroup(removeLayout.createParallelGroup()
                                .addComponent(numeroTurnoL)
                                .addComponent(dataL)
                                .addComponent(CFL)
                        )
                        .addGroup(removeLayout.createParallelGroup()
                                .addComponent(numeroTurnoF)
                                .addComponent(dataF)
                                .addComponent(CFF)
                                .addComponent(cancella)
                        )
        );
        removeLayout.setVerticalGroup(
                removeLayout.createSequentialGroup()
                        .addGroup(removeLayout.createParallelGroup()
                                .addComponent(numeroTurnoL)
                                .addComponent(numeroTurnoF)
                        )
                        .addGroup(removeLayout.createParallelGroup()
                                .addComponent(dataL)
                                .addComponent(dataF)
                        )
                        .addGroup(removeLayout.createParallelGroup()
                                .addComponent(CFL)
                                .addComponent(CFF)
                        )
                        .addComponent(cancella)
        );

        removePanelGen.add(numeroTurnoL);
        removePanelGen.add(numeroTurnoF);
        removePanelGen.add(dataL);
        removePanelGen.add(dataF);
        removePanelGen.add(CFL);
        removePanelGen.add(CFF);
        removePanelGen.add(cancella);


        add(removeScroll, BorderLayout.CENTER);
    }
}
