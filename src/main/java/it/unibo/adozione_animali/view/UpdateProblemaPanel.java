package it.unibo.adozione_animali.view;

import javax.swing.*;
import java.awt.*;

public class UpdateProblemaPanel extends JPanel{

    JPanel updatePanel;

    public UpdateProblemaPanel() {
        setLayout(new BorderLayout());

        updatePanel = new JPanel(new BorderLayout());
        JPanel updatePanelMore = new JPanel(new BorderLayout());
        JPanel updatePanelGen = new JPanel();
        updatePanelMore.add(updatePanelGen, BorderLayout.NORTH);
        updatePanelMore.add(new JPanel(), BorderLayout.CENTER);
        GroupLayout updateLayout = new GroupLayout(updatePanelGen);
        updatePanelGen.setLayout(updateLayout);
        JScrollPane updateScroll = new JScrollPane(updatePanelMore);
        updateScroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        updateScroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        updatePanel.add(updateScroll, BorderLayout.CENTER);

        JCheckBox nonCurabileCheck = new JCheckBox();
        JLabel nonCurabile = new JLabel("Update Problema Non Curabile");
        JPanel checkPanel = new JPanel();
        GroupLayout checkLayout = new GroupLayout(checkPanel);
        checkLayout.setAutoCreateGaps(true);
        checkLayout.setAutoCreateContainerGaps(true);

        checkPanel.add(nonCurabileCheck);
        checkPanel.add(nonCurabile);
        checkPanel.setLayout(checkLayout);
        checkLayout.setHorizontalGroup(checkLayout.createSequentialGroup()
                .addComponent(nonCurabile)
                .addComponent(nonCurabileCheck));
        checkLayout.setVerticalGroup(checkLayout.createSequentialGroup()
                .addGroup(checkLayout.createParallelGroup()
                        .addComponent(nonCurabile)
                        .addComponent(nonCurabileCheck)));
        updatePanel.add(checkPanel, BorderLayout.NORTH);

        JButton update = new JButton("Aggiorna");
        JLabel codFascicoloLabel = new JLabel("Codice Fascicolo");
        JTextField codFascicoloField = new JTextField(5);
        JLabel numeroPaginaLabel = new JLabel("Numero Pagina");
        JTextField numeroPaginaField = new JTextField(3);
        JLabel paragrafoLabel = new JLabel("Paragrafo Pagina");
        JTextField paragrafoField = new JTextField(1);
        JTextField problemName = new JTextField(50);
        JLabel prName = new JLabel("Nuovo Nome Problema");
        JTextArea description = new JTextArea(10, 50);
        JLabel desc = new JLabel("Nuova Descrizione");
        JTextField in_cura = new JTextField(15);
        JLabel inCura = new JLabel("Nuovo In Cura");
        JTextField r_page_num = new JTextField(3);
        JLabel rNum = new JLabel("Nuovo Numero Pagina Ricovero Associato");
        JTextField r_page_par = new JTextField(1);
        JLabel rPar = new JLabel("Nuovon Paragrafo Pagina Ricovero Associato");
        JTextField e_page_num = new JTextField(3);
        JLabel eNum = new JLabel("Nuovo Numero Pagina Esame Associato");
        JTextField e_page_par = new JTextField(1);
        JLabel ePar = new JLabel("Nuovo Paragrafo Pagina Esame Associato");

        updateLayout.setAutoCreateContainerGaps(true);
        updateLayout.setAutoCreateGaps(true);
        updateLayout.setHorizontalGroup(
                updateLayout.createSequentialGroup()
                        .addGroup(updateLayout.createParallelGroup()
                                .addComponent(codFascicoloLabel)
                                .addComponent(numeroPaginaLabel)
                                .addComponent(paragrafoLabel)
                                .addComponent(prName)
                                .addComponent(desc)
                                .addComponent(inCura)
                                .addComponent(rNum)
                                .addComponent(rPar)
                                .addComponent(eNum)
                                .addComponent(ePar)
                        )
                        .addGroup(updateLayout.createParallelGroup()
                                .addComponent(codFascicoloField)
                                .addComponent(numeroPaginaField)
                                .addComponent(paragrafoField)
                                .addComponent(problemName)
                                .addComponent(description)
                                .addComponent(in_cura)
                                .addComponent(r_page_num)
                                .addComponent(r_page_par)
                                .addComponent(e_page_num)
                                .addComponent(e_page_par)
                                .addComponent(update)
                        )
        );
        updateLayout.setVerticalGroup(
                updateLayout.createSequentialGroup()
                        .addGroup(updateLayout.createParallelGroup()
                                .addComponent(codFascicoloLabel)
                                .addComponent(codFascicoloField)
                        )
                        .addGroup(updateLayout.createParallelGroup()
                                .addComponent(numeroPaginaLabel)
                                .addComponent(numeroPaginaField)
                        )
                        .addGroup(updateLayout.createParallelGroup()
                                .addComponent(paragrafoLabel)
                                .addComponent(paragrafoField)
                        )
                        .addGroup(updateLayout.createParallelGroup()
                                .addComponent(prName)
                                .addComponent(problemName)
                        )
                        .addGroup(updateLayout.createParallelGroup()
                                .addComponent(desc)
                                .addComponent(description)
                        )

                        .addGroup(updateLayout.createParallelGroup()
                                .addComponent(inCura)
                                .addComponent(in_cura)
                        )
                        .addGroup(updateLayout.createParallelGroup()
                                .addComponent(rNum)
                                .addComponent(r_page_num)
                        )
                        .addGroup(updateLayout.createParallelGroup()
                                .addComponent(rPar)
                                .addComponent(r_page_par)
                        )
                        .addGroup(updateLayout.createParallelGroup()
                                .addComponent(eNum)
                                .addComponent(e_page_num)
                        )
                        .addGroup(updateLayout.createParallelGroup()
                                .addComponent(ePar)
                                .addComponent(e_page_par)
                        )

                        .addComponent(update)
        );

        updatePanelGen.add(codFascicoloField);
        updatePanelGen.add(codFascicoloLabel);
        updatePanelGen.add(numeroPaginaField);
        updatePanelGen.add(numeroPaginaLabel);
        updatePanelGen.add(paragrafoField);
        updatePanelGen.add(paragrafoLabel);
        updatePanelGen.add(prName);
        updatePanelGen.add(problemName);
        updatePanelGen.add(desc);
        updatePanelGen.add(description);
        updatePanelGen.add(inCura);
        updatePanelGen.add(in_cura);
        updatePanelGen.add(rNum);
        updatePanelGen.add(r_page_num);
        updatePanelGen.add(rPar);
        updatePanelGen.add(r_page_par);
        updatePanelGen.add(eNum);
        updatePanelGen.add(e_page_num);
        updatePanelGen.add(ePar);
        updatePanelGen.add(e_page_par);
        updatePanelGen.add(update);

        nonCurabileCheck.addActionListener(e -> {
            in_cura.setEditable(!nonCurabileCheck.isSelected());
        });

        add(updatePanel, BorderLayout.CENTER);
    }
}
