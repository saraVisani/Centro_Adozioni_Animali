package it.unibo.adozione_animali.view.personale;

import javax.swing.*;
import java.awt.*;

public class UpdateTaskPanel extends JPanel {

    public UpdateTaskPanel() {

        setLayout(new BorderLayout());

        JPanel insertPanel = new JPanel(new BorderLayout());
        JPanel insertPanelGen = new JPanel();
        insertPanel.add(insertPanelGen, BorderLayout.NORTH);
        insertPanel.add(new JPanel(), BorderLayout.CENTER);
        GroupLayout insertLayout = new GroupLayout(insertPanelGen);
        insertPanelGen.setLayout(insertLayout);
        JScrollPane insertScroll = new JScrollPane(insertPanel);
        insertScroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        insertScroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);


        JButton aggiorna = new JButton("Aggiorna");

        JTextField numeroTurnoF = new JTextField();
        JTextField dataF = new JTextField();
        JLabel numeroTurnoL = new JLabel("Numero Turno");
        JLabel dataL = new JLabel("Data (aaaa-mm-dd)");
        JLabel CFL = new JLabel("Codice Fiscale Lavoratore");
        JTextField CFF = new JTextField();
        JTextField numeroTurnoFNew = new JTextField();
        JTextField dataFNew = new JTextField();
        JLabel numeroTurnoLNew = new JLabel("Nuovo Numero Turno");
        JLabel dataLNew = new JLabel("Nuova Data (aaaa-mm-dd)");
        JLabel CFLNew = new JLabel("Nuovo Codice Fiscale Lavoratore");
        JTextField CFFNew = new JTextField();

        insertLayout.setAutoCreateContainerGaps(true);
        insertLayout.setAutoCreateGaps(true);
        insertLayout.setHorizontalGroup(
                insertLayout.createSequentialGroup()
                        .addGroup(insertLayout.createParallelGroup()
                                .addComponent(numeroTurnoL)
                                .addComponent(dataL)
                                .addComponent(CFL)
                                .addComponent(numeroTurnoLNew)
                                .addComponent(dataLNew)
                                .addComponent(CFLNew)
                        )
                        .addGroup(insertLayout.createParallelGroup()
                                .addComponent(numeroTurnoF)
                                .addComponent(dataF)
                                .addComponent(CFF)
                                .addComponent(numeroTurnoFNew)
                                .addComponent(dataFNew)
                                .addComponent(CFFNew)
                                .addComponent(aggiorna)
                        )
        );
        insertLayout.setVerticalGroup(
                insertLayout.createSequentialGroup()
                        .addGroup(insertLayout.createParallelGroup()
                                .addComponent(numeroTurnoL)
                                .addComponent(numeroTurnoF)
                        )
                        .addGroup(insertLayout.createParallelGroup()
                                .addComponent(dataL)
                                .addComponent(dataF)
                        )
                        .addGroup(insertLayout.createParallelGroup()
                                .addComponent(CFL)
                                .addComponent(CFF)
                        )
                        .addGroup(insertLayout.createParallelGroup()
                                .addComponent(numeroTurnoLNew)
                                .addComponent(numeroTurnoFNew)
                        )
                        .addGroup(insertLayout.createParallelGroup()
                                .addComponent(dataLNew)
                                .addComponent(dataFNew)
                        )
                        .addGroup(insertLayout.createParallelGroup()
                                .addComponent(CFLNew)
                                .addComponent(CFFNew)
                        )
                        .addComponent(aggiorna)
        );

        insertPanelGen.add(numeroTurnoL);
        insertPanelGen.add(numeroTurnoF);
        insertPanelGen.add(dataL);
        insertPanelGen.add(dataF);
        insertPanelGen.add(CFL);
        insertPanelGen.add(CFF);
        insertPanelGen.add(numeroTurnoLNew);
        insertPanelGen.add(numeroTurnoFNew);
        insertPanelGen.add(dataLNew);
        insertPanelGen.add(dataFNew);
        insertPanelGen.add(CFLNew);
        insertPanelGen.add(CFFNew);
        insertPanelGen.add(aggiorna);


        add(insertScroll, BorderLayout.CENTER);
    }
}
