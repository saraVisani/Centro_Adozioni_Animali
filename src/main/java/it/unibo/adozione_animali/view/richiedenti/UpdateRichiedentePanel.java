package it.unibo.adozione_animali.view.richiedenti;

import javax.swing.*;
import java.awt.*;

public class UpdateRichiedentePanel extends JPanel {

    public UpdateRichiedentePanel() {
        setLayout(new BorderLayout());

        JPanel updatePanel = new JPanel(new BorderLayout());
        JPanel updatePanelGen = new JPanel();
        updatePanel.add(updatePanelGen, BorderLayout.NORTH);
        updatePanel.add(new JPanel(), BorderLayout.CENTER);
        GroupLayout updateLayout = new GroupLayout(updatePanelGen);
        updatePanelGen.setLayout(updateLayout);
        JScrollPane updateScroll = new JScrollPane(updatePanel);
        updateScroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        updateScroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);


        JButton aggiorna = new JButton("Aggiorna");

        JTextField CFF = new JTextField();
        JTextField emailF = new JTextField();
        JTextField passwordF = new JTextField();
        JTextField telefonoF = new JTextField();
        JLabel CFL = new JLabel("Codice Fiscale");
        JLabel emailL = new JLabel("Nuova Email");
        JLabel telefonoL = new JLabel("Nuovo Telefono (formato +39)");
        JLabel passwordL = new JLabel("Nuova Password");
        JTextField dataRecenteAbF = new JTextField();
        JLabel dataRecenteAbL = new JLabel("Nuova Data Abbandono Più Recente (aaaa-mm-dd)");
        JCheckBox checkAbuso = new JCheckBox();
        JLabel abuso = new JLabel("Presenza Abusi");

        updateLayout.setAutoCreateContainerGaps(true);
        updateLayout.setAutoCreateGaps(true);
        updateLayout.setHorizontalGroup(
                updateLayout.createSequentialGroup()
                        .addGroup(updateLayout.createParallelGroup()
                                .addComponent(CFL)
                                .addComponent(emailL)
                                .addComponent(passwordL)
                                .addComponent(telefonoL)
                                .addComponent(dataRecenteAbL)
                                .addComponent(abuso)
                        )
                        .addGroup(updateLayout.createParallelGroup()
                                .addComponent(CFF)
                                .addComponent(emailF)
                                .addComponent(passwordF)
                                .addComponent(telefonoF)
                                .addComponent(dataRecenteAbF)
                                .addComponent(checkAbuso)
                                .addComponent(aggiorna)
                        )
        );
        updateLayout.setVerticalGroup(
                updateLayout.createSequentialGroup()
                        .addGroup(updateLayout.createParallelGroup()
                                .addComponent(CFL)
                                .addComponent(CFF)
                        )
                        .addGroup(updateLayout.createParallelGroup()
                                .addComponent(emailL)
                                .addComponent(emailF)
                        )
                        .addGroup(updateLayout.createParallelGroup()
                                .addComponent(passwordL)
                                .addComponent(passwordF)
                        )
                        .addGroup(updateLayout.createParallelGroup()
                                .addComponent(telefonoL)
                                .addComponent(telefonoF)
                        )
                        .addGroup(updateLayout.createParallelGroup()
                                .addComponent(dataRecenteAbL)
                                .addComponent(dataRecenteAbF)
                        )
                        .addGroup(updateLayout.createParallelGroup()
                                .addComponent(abuso)
                                .addComponent(checkAbuso)
                        )
                        .addComponent(aggiorna)
        );


        add(updateScroll, BorderLayout.CENTER);
    }
}
