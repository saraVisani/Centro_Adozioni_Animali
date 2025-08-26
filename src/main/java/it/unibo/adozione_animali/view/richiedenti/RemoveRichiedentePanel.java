package it.unibo.adozione_animali.view.richiedenti;

import it.unibo.adozione_animali.model.impl.RichiedenteDAO;
import it.unibo.adozione_animali.model.impl.RichiestaDAO;
import org.jooq.exception.DataAccessException;

import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;

public class RemoveRichiedentePanel extends JPanel {

    public RemoveRichiedentePanel() {
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

        JTextField CFF = new JTextField();
        JLabel CFL = new JLabel("Codice Fiscale Richiedente");


        removeLayout.setAutoCreateContainerGaps(true);
        removeLayout.setAutoCreateGaps(true);
        removeLayout.setHorizontalGroup(
                removeLayout.createSequentialGroup()
                        .addGroup(removeLayout.createParallelGroup()
                                .addComponent(CFL)
                        )
                        .addGroup(removeLayout.createParallelGroup()
                                .addComponent(CFF)
                                .addComponent(cancella)
                        )
        );
        removeLayout.setVerticalGroup(
                removeLayout.createSequentialGroup()
                        .addGroup(removeLayout.createParallelGroup()
                                .addComponent(CFL)
                                .addComponent(CFF)
                        )
                        .addComponent(cancella)
        );

        removePanelGen.add(CFL);
        removePanelGen.add(CFF);
        removePanelGen.add(cancella);

        cancella.addActionListener(e -> {
            try {
                new RichiedenteDAO().deleteRichiedente(CFF.getText());
                JOptionPane.showMessageDialog(this, "L'eliminazione è avvenuta correttamente");
            }  catch (DataAccessException data) {
                Throwable cause = data.getCause();
                if (cause instanceof SQLException) {
                    JOptionPane.showMessageDialog(this, "Errore nell'inserimento. Ricontrollare che i campi siano stati riempiti correttamente");
                }
            } catch (Exception numb) {
                JOptionPane.showMessageDialog(this, "Errore nell'inserimento. Ricontrollare che i campi siano stati riempiti correttamente");
            }
        });

        add(removeScroll, BorderLayout.CENTER);
    }
}
