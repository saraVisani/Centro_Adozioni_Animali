package it.unibo.adozione_animali.view.richiedenti;

import javax.swing.*;
import java.awt.*;

public class InsertRichiedentePanel extends JPanel {

    public InsertRichiedentePanel() {
        setLayout(new BorderLayout());

        JPanel insertPanel = new JPanel(new BorderLayout());
        JPanel insertPanelMore = new JPanel(new BorderLayout());
        JPanel insertPanelGen = new JPanel();
        insertPanelMore.add(insertPanelGen, BorderLayout.NORTH);
        insertPanelMore.add(new JPanel(), BorderLayout.CENTER);
        GroupLayout insertLayout = new GroupLayout(insertPanelGen);
        insertPanelGen.setLayout(insertLayout);
        JScrollPane insertScroll = new JScrollPane(insertPanelMore);
        insertScroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        insertScroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        insertPanel.add(insertScroll, BorderLayout.CENTER);


        JButton insert = new JButton("Aggiungi");
        JLabel CFL = new JLabel("Codice Fiscale");
        JTextField CFF = new JTextField(16);
        JLabel nomeL = new JLabel("Nome");
        JTextField nomeF = new JTextField();
        JLabel cognomeL = new JLabel("Cognome");
        JTextField cognomeF = new JTextField();
        JTextField emailF = new JTextField();
        JLabel emailL = new JLabel("Email");
        JTextField passwordF = new JTextField();
        JLabel passwordL = new JLabel("Password");
        JTextField telefonoF = new JTextField();
        JLabel telefonoL = new JLabel("Telefono (formato +39)");
        JTextField numeroAbF = new JTextField();
        JLabel numeroAbL = new JLabel("Numero Abbandoni");
        JTextField dataRecenteAbF = new JTextField();
        JLabel dataRecenteAbL = new JLabel("Data Abbandono Più Recente (aaaa-mm-dd)");
        JCheckBox checkAbuso = new JCheckBox();
        JLabel abuso = new JLabel("Presenza Abusi");


        insertLayout.setAutoCreateContainerGaps(true);
        insertLayout.setAutoCreateGaps(true);
        insertLayout.setHorizontalGroup(
                insertLayout.createSequentialGroup()
                        .addGroup(insertLayout.createParallelGroup()
                                .addComponent(CFL)
                                .addComponent(nomeL)
                                .addComponent(cognomeL)
                                .addComponent(emailL)
                                .addComponent(passwordL)
                                .addComponent(telefonoL)
                                .addComponent(numeroAbL)
                                .addComponent(dataRecenteAbL)
                                .addComponent(abuso)
                        )
                        .addGroup(insertLayout.createParallelGroup()
                                .addComponent(CFF)
                                .addComponent(nomeF)
                                .addComponent(cognomeF)
                                .addComponent(emailF)
                                .addComponent(passwordF)
                                .addComponent(telefonoF)
                                .addComponent(numeroAbF)
                                .addComponent(dataRecenteAbF)
                                .addComponent(checkAbuso)
                                .addComponent(insert)
                        )
        );
        insertLayout.setVerticalGroup(
                insertLayout.createSequentialGroup()
                        .addGroup(insertLayout.createParallelGroup()
                                .addComponent(CFL)
                                .addComponent(CFF)
                        )
                        .addGroup(insertLayout.createParallelGroup()
                                .addComponent(nomeL)
                                .addComponent(nomeF)
                        )
                        .addGroup(insertLayout.createParallelGroup()
                                .addComponent(cognomeL)
                                .addComponent(cognomeF)
                        )
                        .addGroup(insertLayout.createParallelGroup()
                                .addComponent(emailL)
                                .addComponent(emailF)
                        )
                        .addGroup(insertLayout.createParallelGroup()
                                .addComponent(passwordL)
                                .addComponent(passwordF)
                        )

                        .addGroup(insertLayout.createParallelGroup()
                                .addComponent(telefonoL)
                                .addComponent(telefonoF)
                        )
                        .addGroup(insertLayout.createParallelGroup()
                                .addComponent(numeroAbL)
                                .addComponent(numeroAbF)
                        )
                        .addGroup(insertLayout.createParallelGroup()
                                .addComponent(dataRecenteAbL)
                                .addComponent(dataRecenteAbF)
                        )
                        .addGroup(insertLayout.createParallelGroup()
                                .addComponent(abuso)
                                .addComponent(checkAbuso)
                        )
                        .addComponent(insert)
        );

        insertPanelGen.add(CFF);
        insertPanelGen.add(CFL);
        insertPanelGen.add(nomeF);
        insertPanelGen.add(nomeL);
        insertPanelGen.add(cognomeF);
        insertPanelGen.add(cognomeL);
        insertPanelGen.add(emailL);
        insertPanelGen.add(emailF);
        insertPanelGen.add(passwordL);
        insertPanelGen.add(passwordF);
        insertPanelGen.add(telefonoL);
        insertPanelGen.add(telefonoF);
        insertPanelGen.add(numeroAbL);
        insertPanelGen.add(numeroAbF);
        insertPanelGen.add(dataRecenteAbL);
        insertPanelGen.add(dataRecenteAbF);
        insertPanelGen.add(abuso);
        insertPanelGen.add(checkAbuso);
        insertPanelGen.add(insert);


        add(insertPanel, BorderLayout.CENTER);
    }
}
