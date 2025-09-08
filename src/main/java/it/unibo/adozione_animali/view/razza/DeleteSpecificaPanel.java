package it.unibo.adozione_animali.view.razza;

import it.unibo.adozione_animali.model.impl.RazzaDAO;
import it.unibo.adozione_animali.model.impl.caratteristica.SpecificaRazzaDAO;

import javax.swing.*;
import java.awt.*;

public class DeleteSpecificaPanel extends JPanel {

    public DeleteSpecificaPanel() {
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

        JTextField codSpecificaF = new JTextField();
        JLabel codSpecificaL = new JLabel("Codice Specifica");

        removeLayout.setAutoCreateContainerGaps(true);
        removeLayout.setAutoCreateGaps(true);
        removeLayout.setHorizontalGroup(
                removeLayout.createSequentialGroup()
                        .addComponent(codSpecificaL)
                        .addGroup(removeLayout.createParallelGroup()
                                .addComponent(codSpecificaF)
                                .addComponent(cancella)
                        )
        );
        removeLayout.setVerticalGroup(
                removeLayout.createSequentialGroup()
                        .addGroup(removeLayout.createParallelGroup()
                                .addComponent(codSpecificaL)
                                .addComponent(codSpecificaF)
                        )
                        .addComponent(cancella)
        );

        removePanelGen.add(codSpecificaL);
        removePanelGen.add(codSpecificaF);
        removePanelGen.add(cancella);

        cancella.addActionListener(e -> {
            try {
                if (codSpecificaF.getText().isEmpty()) {
                    throw new IllegalArgumentException();
                }
                new SpecificaRazzaDAO().deleteSpecificaRazza(codSpecificaF.getText().trim());
                JOptionPane.showMessageDialog(this, "L'eliminazione è avvenuta correttamente");
            } catch (Exception numb) {
                JOptionPane.showMessageDialog(this, "Errore nell'inserimento." +
                        " Ricontrollare che i campi siano stati riempiti correttamente");
            }
        });

        add(removeScroll, BorderLayout.CENTER);
    }
}
