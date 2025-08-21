package it.unibo.adozione_animali.view.animale;

import javax.swing.*;
import java.awt.*;

public class RemoveComponentePanel extends JPanel {

    public RemoveComponentePanel() {
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


        JButton cancellazione = new JButton("Elimina");

        JTextField cod_prov = new JTextField(2);
        JTextField cod_citta = new JTextField(2);
        JTextField numeroInd = new JTextField(5);
        JTextField codAnimale = new JTextField(5);
        JLabel codProv = new JLabel("Provincia del centro");
        JLabel codCitta = new JLabel("Città del centro");
        JLabel numAnimale = new JLabel("Civico del centro");
        JLabel codAnim = new JLabel("Codice dell'animale");
        JTextField cod_specie = new JTextField(2);
        JTextField nome_razza = new JTextField(2);
        JLabel codSpecie = new JLabel("Codice Specie Componente");
        JLabel nomeRazza = new JLabel("Razza Componente");


        removeLayout.setAutoCreateContainerGaps(true);
        removeLayout.setAutoCreateGaps(true);
        removeLayout.setHorizontalGroup(
                removeLayout.createSequentialGroup()
                        .addGroup(removeLayout.createParallelGroup()
                                .addComponent(codProv)
                                .addComponent(codCitta)
                                .addComponent(numAnimale)
                                .addComponent(codAnim)
                                .addComponent(codSpecie)
                                .addComponent(nomeRazza)
                        )
                        .addGroup(removeLayout.createParallelGroup()
                                .addComponent(cod_prov)
                                .addComponent(cod_citta)
                                .addComponent(numeroInd)
                                .addComponent(codAnimale)
                                .addComponent(cod_specie)
                                .addComponent(nome_razza)
                                .addComponent(cancellazione)
                        )
        );
        removeLayout.setVerticalGroup(
                removeLayout.createSequentialGroup()
                        .addGroup(removeLayout.createParallelGroup()
                                .addComponent(codProv)
                                .addComponent(cod_prov)
                        )
                        .addGroup(removeLayout.createParallelGroup()
                                .addComponent(codCitta)
                                .addComponent(cod_citta)
                        )
                        .addGroup(removeLayout.createParallelGroup()
                                .addComponent(numAnimale)
                                .addComponent(numeroInd)
                        )
                        .addGroup(removeLayout.createParallelGroup()
                                .addComponent(codAnim)
                                .addComponent(codAnimale)
                        )
                        .addGroup(removeLayout.createParallelGroup()
                                .addComponent(codSpecie)
                                .addComponent(cod_specie)
                        )
                        .addGroup(removeLayout.createParallelGroup()
                                .addComponent(nomeRazza)
                                .addComponent(nome_razza)
                        )
                        .addComponent(cancellazione)
        );

        removePanelGen.add(codProv);
        removePanelGen.add(cod_prov);
        removePanelGen.add(codCitta);
        removePanelGen.add(cod_citta);
        removePanelGen.add(numAnimale);
        removePanelGen.add(numeroInd);
        removePanelGen.add(codAnim);
        removePanelGen.add(codAnimale);
        removePanelGen.add(codSpecie);
        removePanelGen.add(cod_specie);
        removePanelGen.add(nomeRazza);
        removePanelGen.add(nome_razza);
        removePanelGen.add(cancellazione);


        add(removeScroll, BorderLayout.CENTER);
    }
}
