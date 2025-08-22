package it.unibo.adozione_animali.view.personale;

import javax.swing.*;
import java.awt.*;

public class UpdatePersonalePanel extends JPanel {

    public UpdatePersonalePanel() {
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
        JTextField tempoLavoro = new JTextField();
        JTextField dataAssuDipF = new JTextField();
        JTextField dataAssuVolF = new JTextField();
        JTextField dataFineDipF = new JTextField();
        JTextField dataFineVolF = new JTextField();
        JTextField stipendioF = new JTextField();
        JLabel CFL = new JLabel("Numero Turno");
        JLabel tempoLavoroL = new JLabel("Tempo di lavoro (anni)");
        JLabel dataAssuDipL = new JLabel("Data Assunzione Dipendente (aaaa-mm-dd)");
        JLabel dataAssuVolL = new JLabel("Data Assunzione Volontario (aaaa-mm-dd)");
        JLabel dataFineDipL = new JLabel("Data Fine Lavoro Dipendente (aaaa-mm-dd)");
        JLabel dataFineVolL = new JLabel("Data Fine Lavoro Volontario (aaaa-mm-dd)");
        JLabel stipendioL = new JLabel("Stipendio");
        JLabel emailL = new JLabel("Nuova Email");
        JLabel telefonoL = new JLabel("Nuovo Telefono (formato +39)");
        JLabel passwordL = new JLabel("Nuova Password");

        updateLayout.setAutoCreateContainerGaps(true);
        updateLayout.setAutoCreateGaps(true);
        updateLayout.setHorizontalGroup(
                updateLayout.createSequentialGroup()
                        .addGroup(updateLayout.createParallelGroup()
                                .addComponent(CFL)
                                .addComponent(emailL)
                                .addComponent(passwordL)
                                .addComponent(telefonoL)
                                .addComponent(tempoLavoroL)
                                .addComponent(dataAssuDipL)
                                .addComponent(dataAssuVolL)
                                .addComponent(dataFineDipL)
                                .addComponent(dataFineVolL)
                                .addComponent(stipendioL)
                        )
                        .addGroup(updateLayout.createParallelGroup()
                                .addComponent(CFF)
                                .addComponent(emailF)
                                .addComponent(passwordF)
                                .addComponent(telefonoF)
                                .addComponent(tempoLavoro)
                                .addComponent(dataAssuDipF)
                                .addComponent(dataAssuVolF)
                                .addComponent(dataFineDipF)
                                .addComponent(dataFineVolF)
                                .addComponent(stipendioF)
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
                                .addComponent(tempoLavoroL)
                                .addComponent(tempoLavoro)
                        )
                        .addGroup(updateLayout.createParallelGroup()
                                .addComponent(dataAssuDipL)
                                .addComponent(dataAssuDipF)
                        )
                        .addGroup(updateLayout.createParallelGroup()
                                .addComponent(dataAssuVolL)
                                .addComponent(dataAssuVolF)
                        )
                        .addGroup(updateLayout.createParallelGroup()
                                .addComponent(dataFineDipL)
                                .addComponent(dataFineDipF)
                        )
                        .addGroup(updateLayout.createParallelGroup()
                                .addComponent(dataFineVolL)
                                .addComponent(dataFineVolF)
                        )
                        .addGroup(updateLayout.createParallelGroup()
                                .addComponent(stipendioL)
                                .addComponent(stipendioF)
                        )
                        .addComponent(aggiorna)
        );


        add(updateScroll, BorderLayout.CENTER);
    }
}
