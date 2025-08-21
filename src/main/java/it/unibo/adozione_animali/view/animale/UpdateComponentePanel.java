package it.unibo.adozione_animali.view.animale;

import javax.swing.*;
import java.awt.*;

public class UpdateComponentePanel extends JPanel {

    //TODO --> Inserire action listener bottone
    public UpdateComponentePanel() {
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

        JTextField cod_prov = new JTextField(2);
        JTextField cod_citta = new JTextField(2);
        JTextField numeroInd = new JTextField(5);
        JTextField codAnimale = new JTextField(5);
        JLabel codProv = new JLabel("Provincia del centro");
        JLabel codCitta = new JLabel("Città del centro");
        JLabel numAnimale = new JLabel("Civico del centro");
        JLabel codAnim = new JLabel("Codice dell'animale");
        JTextField cod_specie = new JTextField(2);
        JTextField nome_razza = new JTextField(50);
        JTextField percentuale = new JTextField(2);
        JLabel codSpecie = new JLabel("Codice Specie Componente");
        JLabel nomeRazza = new JLabel("Razza Componente");
        JLabel perc = new JLabel("Nuova Percentuale Composizione");


        updateLayout.setAutoCreateContainerGaps(true);
        updateLayout.setAutoCreateGaps(true);
        updateLayout.setHorizontalGroup(
                updateLayout.createSequentialGroup()
                        .addGroup(updateLayout.createParallelGroup()
                                .addComponent(codProv)
                                .addComponent(codCitta)
                                .addComponent(numAnimale)
                                .addComponent(codAnim)
                                .addComponent(codSpecie)
                                .addComponent(nomeRazza)
                                .addComponent(perc)
                        )
                        .addGroup(updateLayout.createParallelGroup()
                                .addComponent(cod_prov)
                                .addComponent(cod_citta)
                                .addComponent(numeroInd)
                                .addComponent(codAnimale)
                                .addComponent(cod_specie)
                                .addComponent(nome_razza)
                                .addComponent(percentuale)
                                .addComponent(aggiorna)
                        )
        );
        updateLayout.setVerticalGroup(
                updateLayout.createSequentialGroup()
                        .addGroup(updateLayout.createParallelGroup()
                                .addComponent(codProv)
                                .addComponent(cod_prov)
                        )
                        .addGroup(updateLayout.createParallelGroup()
                                .addComponent(codCitta)
                                .addComponent(cod_citta)
                        )
                        .addGroup(updateLayout.createParallelGroup()
                                .addComponent(numAnimale)
                                .addComponent(numeroInd)
                        )
                        .addGroup(updateLayout.createParallelGroup()
                                .addComponent(codAnim)
                                .addComponent(codAnimale)
                        )
                        .addGroup(updateLayout.createParallelGroup()
                                .addComponent(codSpecie)
                                .addComponent(cod_specie)
                        )
                        .addGroup(updateLayout.createParallelGroup()
                                .addComponent(nomeRazza)
                                .addComponent(nome_razza)
                        )
                        .addGroup(updateLayout.createParallelGroup()
                                .addComponent(perc)
                                .addComponent(percentuale)
                        )
                        .addComponent(aggiorna)
        );

        updatePanelGen.add(codProv);
        updatePanelGen.add(cod_prov);
        updatePanelGen.add(codCitta);
        updatePanelGen.add(cod_citta);
        updatePanelGen.add(numAnimale);
        updatePanelGen.add(numeroInd);
        updatePanelGen.add(codAnim);
        updatePanelGen.add(codAnimale);
        updatePanelGen.add(codSpecie);
        updatePanelGen.add(cod_specie);
        updatePanelGen.add(nomeRazza);
        updatePanelGen.add(nome_razza);
        updatePanelGen.add(perc);
        updatePanelGen.add(percentuale);
        updatePanelGen.add(aggiorna);


        add(updateScroll, BorderLayout.CENTER);
    }
}
