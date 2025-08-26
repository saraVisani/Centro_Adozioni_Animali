package it.unibo.adozione_animali.view.razza;

import it.unibo.adozione_animali.model.impl.RazzaDAO;
import it.unibo.adozione_animali.model.impl.TurnoLavorativoDAO;
import org.jooq.exception.DataAccessException;

import javax.swing.*;
import java.awt.*;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.time.LocalDate;

public class InsertRazzaPanel extends JPanel {

    public InsertRazzaPanel() {
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

        JTextField codSpecieF = new JTextField();
        JTextField nomeRazzaF = new JTextField();
        JLabel codSpecieL = new JLabel("Codice Specie");
        JLabel nomeRazzaL = new JLabel("Nome Razza");
        JTextArea descriptionF = new JTextArea();
        JTextField origineF = new JTextField();
        JLabel descriptionL = new JLabel("Descrizione");
        JLabel origineL = new JLabel("Origine");
        JTextField lignaggioF = new JTextField();
        JTextField heightMaxF = new JTextField();
        JLabel lignaggioL = new JLabel("Lignaggio");
        JLabel heightMaxL = new JLabel("Altezza Massima");
        JTextField heightMinF = new JTextField();
        JTextField weightMaxF = new JTextField();
        JLabel heightMinL = new JLabel("Altezza Minima");
        JLabel weightMaxL = new JLabel("Peso Massimo");
        JTextField weightMinF = new JTextField();
        JTextField idSpace1F = new JTextField();
        JLabel weightMinL = new JLabel("Peso Minimo");
        JLabel idSpace1L = new JLabel("ID Spazio1");
        JTextField idSpace2F = new JTextField();
        JLabel idSpace2L = new JLabel("ID Spazio2 (opzionale)");


        insertLayout.setAutoCreateContainerGaps(true);
        insertLayout.setAutoCreateGaps(true);
        insertLayout.setHorizontalGroup(
                insertLayout.createSequentialGroup()
                        .addGroup(insertLayout.createParallelGroup()
                                .addComponent(codSpecieL)
                                .addComponent(nomeRazzaL)
                                .addComponent(descriptionL)
                                .addComponent(origineL)
                                .addComponent(lignaggioL)
                                .addComponent(heightMaxL)
                                .addComponent(heightMinL)
                                .addComponent(weightMaxL)
                                .addComponent(weightMinL)
                                .addComponent(idSpace1L)
                                .addComponent(idSpace2L)
                        )
                        .addGroup(insertLayout.createParallelGroup()
                                .addComponent(codSpecieF)
                                .addComponent(nomeRazzaF)
                                .addComponent(descriptionF)
                                .addComponent(origineF)
                                .addComponent(lignaggioF)
                                .addComponent(heightMaxF)
                                .addComponent(heightMinF)
                                .addComponent(weightMaxF)
                                .addComponent(weightMinF)
                                .addComponent(idSpace1F)
                                .addComponent(idSpace2F)
                                .addComponent(aggiungi)
                        )
        );
        insertLayout.setVerticalGroup(
                insertLayout.createSequentialGroup()
                        .addGroup(insertLayout.createParallelGroup()
                                .addComponent(codSpecieL)
                                .addComponent(codSpecieF)
                        )
                        .addGroup(insertLayout.createParallelGroup()
                                .addComponent(nomeRazzaL)
                                .addComponent(nomeRazzaF)
                        )
                        .addGroup(insertLayout.createParallelGroup()
                                .addComponent(descriptionL)
                                .addComponent(descriptionF)
                        )
                        .addGroup(insertLayout.createParallelGroup()
                                .addComponent(origineL)
                                .addComponent(origineF)
                        )
                        .addGroup(insertLayout.createParallelGroup()
                                .addComponent(lignaggioL)
                                .addComponent(lignaggioF)
                        )
                        .addGroup(insertLayout.createParallelGroup()
                                .addComponent(heightMaxL)
                                .addComponent(heightMaxF)
                        )
                        .addGroup(insertLayout.createParallelGroup()
                                .addComponent(heightMinL)
                                .addComponent(heightMinF)
                        )
                        .addGroup(insertLayout.createParallelGroup()
                                .addComponent(weightMaxL)
                                .addComponent(weightMaxF)
                        )
                        .addGroup(insertLayout.createParallelGroup()
                                .addComponent(weightMinL)
                                .addComponent(weightMinF)
                        )
                        .addGroup(insertLayout.createParallelGroup()
                                .addComponent(idSpace1L)
                                .addComponent(idSpace1F)
                        )
                        .addGroup(insertLayout.createParallelGroup()
                                .addComponent(idSpace2L)
                                .addComponent(idSpace2F)
                        )
                        .addComponent(aggiungi)
        );

        add(insertScroll, BorderLayout.CENTER);

        aggiungi.addActionListener(e -> {
            try {
                Integer idSp2 = idSpace2F.getText().isEmpty() ? null : Integer.parseInt(idSpace2F.getText());

                new RazzaDAO().insertRazza(codSpecieF.getText(), nomeRazzaF.getText(), descriptionF.getText(),
                        origineF.getText(), lignaggioF.getText(), BigDecimal.valueOf(Long.parseLong(heightMaxF.getText())),
                        BigDecimal.valueOf(Double.parseDouble(heightMinF.getText())), BigDecimal.valueOf(Double.parseDouble(weightMaxF.getText())),
                        BigDecimal.valueOf(Double.parseDouble(weightMinF.getText())), Integer.parseInt(idSpace1F.getText()), idSp2);

                JOptionPane.showMessageDialog(this, "L'inserimento è avvenuto correttamente");

            } catch (Exception numb) {
                JOptionPane.showMessageDialog(this, "Errore nell'inserimento. Ricontrollare che i campi siano stati riempiti correttamente");
            }
        });
    }
}
