package it.unibo.adozione_animali.view.personale;

import it.unibo.adozione_animali.model.impl.TurnoLavorativoDAO;
import org.jooq.exception.DataAccessException;

import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;
import java.time.LocalDate;

public class InsertTurnoPanel extends JPanel {

    public InsertTurnoPanel() {
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


        JButton aggiungi = new JButton("Aggiungi");

        JTextField numeroTurnoF = new JTextField();
        JTextField dataF = new JTextField();
        JLabel numeroTurnoL = new JLabel("Numero Turno");
        JLabel dataL = new JLabel("Data (aaaa-mm-dd)");


        insertLayout.setAutoCreateContainerGaps(true);
        insertLayout.setAutoCreateGaps(true);
        insertLayout.setHorizontalGroup(
                insertLayout.createSequentialGroup()
                        .addGroup(insertLayout.createParallelGroup()
                                .addComponent(numeroTurnoL)
                                .addComponent(dataL)
                        )
                        .addGroup(insertLayout.createParallelGroup()
                                .addComponent(numeroTurnoF)
                                .addComponent(dataF)
                                .addComponent(aggiungi)
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
                        .addComponent(aggiungi)
        );

        insertPanelGen.add(numeroTurnoL);
        insertPanelGen.add(numeroTurnoF);
        insertPanelGen.add(dataL);
        insertPanelGen.add(dataF);
        insertPanelGen.add(aggiungi);


        add(insertScroll, BorderLayout.CENTER);

        aggiungi.addActionListener(e -> {
            try {
                new TurnoLavorativoDAO().insertTurnoLavorativo((byte) Integer.parseInt(numeroTurnoF.getText()),
                        LocalDate.parse(dataF.getText()));
                JOptionPane.showMessageDialog(this, "L'inserimento è avvenuto correttamente");
            } catch (DataAccessException data) {
                Throwable cause = data.getCause();
                if (cause instanceof SQLException) {
                    JOptionPane.showMessageDialog(this, "Errore nell'inserimento. Ricontrollare che i campi siano stati riempiti correttamente");
                }
            } catch (NumberFormatException numb) {
            JOptionPane.showMessageDialog(this, "Errore nell'inserimento. Alcuni campi obbligatori non sono stati riempiti");
        }
        });
    }

}
