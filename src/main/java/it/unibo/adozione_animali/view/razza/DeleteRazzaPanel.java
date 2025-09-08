package it.unibo.adozione_animali.view.razza;

import it.unibo.adozione_animali.model.impl.RazzaDAO;

import javax.swing.*;
import java.awt.*;

public class DeleteRazzaPanel extends JPanel {

    public DeleteRazzaPanel() {
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

        JTextField codSpecieF = new JTextField();
        JTextField nomeRazzaF = new JTextField();
        JLabel codSpecieL = new JLabel("Codice Specie");
        JLabel nomeRazzaL = new JLabel("Nome Razza");

        removeLayout.setAutoCreateContainerGaps(true);
        removeLayout.setAutoCreateGaps(true);
        removeLayout.setHorizontalGroup(
                removeLayout.createSequentialGroup()
                        .addGroup(removeLayout.createParallelGroup()
                                .addComponent(codSpecieL)
                                .addComponent(nomeRazzaL)
                        )
                        .addGroup(removeLayout.createParallelGroup()
                                .addComponent(codSpecieF)
                                .addComponent(nomeRazzaF)

                                .addComponent(cancella)
                        )
        );
        removeLayout.setVerticalGroup(
                removeLayout.createSequentialGroup()
                        .addGroup(removeLayout.createParallelGroup()
                                .addComponent(codSpecieL)
                                .addComponent(codSpecieF)
                        )
                        .addGroup(removeLayout.createParallelGroup()
                                .addComponent(nomeRazzaL)
                                .addComponent(nomeRazzaF)
                        )
                        .addComponent(cancella)
        );

        removePanelGen.add(codSpecieL);
        removePanelGen.add(codSpecieF);
        removePanelGen.add(nomeRazzaL);
        removePanelGen.add(nomeRazzaF);
        removePanelGen.add(cancella);

        cancella.addActionListener(e -> {
            try {
                new RazzaDAO().deleteRazza(codSpecieF.getText(), nomeRazzaF.getText());
                JOptionPane.showMessageDialog(this, "L'eliminazione è avvenuta correttamente");
            } catch (Exception numb) {
                JOptionPane.showMessageDialog(this, "Errore nell'inserimento." +
                        " Ricontrollare che i campi siano stati riempiti correttamente");
            }
        });

        add(removeScroll, BorderLayout.CENTER);
    }
}
