package it.unibo.adozione_animali.view.personale;

import it.unibo.adozione_animali.model.impl.TaskDAO;
import org.jooq.exception.DataAccessException;

import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class InsertTaskPanel extends JPanel {

    public InsertTaskPanel() {
        setLayout(new BorderLayout());

        JPanel insertPanel = new JPanel(new BorderLayout());
        JPanel insertPanelMore = new JPanel(new BorderLayout());
        insertPanel.add(insertPanelMore, BorderLayout.CENTER);
        JPanel insertPanelGen = new JPanel();
        insertPanelMore.add(insertPanelGen, BorderLayout.NORTH);
        insertPanelMore.add(new JPanel(), BorderLayout.CENTER);
        GroupLayout insertLayout = new GroupLayout(insertPanelGen);
        insertPanelGen.setLayout(insertLayout);
        JScrollPane insertScroll = new JScrollPane(insertPanel);
        insertScroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        insertScroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);


        JLabel lavoro = new JLabel("Inserimento di un accudimento o di un trattamento medico");
        JPanel checkPanel = new JPanel();
        GroupLayout checkLayout = new GroupLayout(checkPanel);
        checkLayout.setAutoCreateGaps(true);
        checkLayout.setAutoCreateContainerGaps(true);

        JCheckBox lavoroCheck = new JCheckBox();
        checkPanel.add(lavoroCheck);
        checkPanel.add(lavoro);
        checkPanel.setLayout(checkLayout);
        checkLayout.setHorizontalGroup(checkLayout.createSequentialGroup()
                .addComponent(lavoro)
                .addComponent(lavoroCheck));
        checkLayout.setVerticalGroup(checkLayout.createSequentialGroup()
                .addGroup(checkLayout.createParallelGroup()
                        .addComponent(lavoro)
                        .addComponent(lavoroCheck)));
        insertPanel.add(checkPanel, BorderLayout.NORTH);

        JButton aggiungi = new JButton("Aggiungi");

        JTextField numeroTurnoF = new JTextField();
        JTextField dataF = new JTextField();
        JLabel numeroTurnoL = new JLabel("Numero Turno");
        JLabel dataL = new JLabel("Data (aaaa-mm-dd)");
        JLabel CFL = new JLabel("Codice Fiscale Lavoratore");
        JTextField CFF = new JTextField();
        JLabel lavoroL = new JLabel("Tipologia Lavoro");
        JTextField lavoroF = new JTextField();
        JLabel animaliL = new JLabel("Codici Animali Gestiti (separati da ,)");
        JTextArea animaliF = new JTextArea();

        insertLayout.setAutoCreateContainerGaps(true);
        insertLayout.setAutoCreateGaps(true);
        insertLayout.setHorizontalGroup(
                insertLayout.createSequentialGroup()
                        .addGroup(insertLayout.createParallelGroup()
                                .addComponent(numeroTurnoL)
                                .addComponent(dataL)
                                .addComponent(CFL)
                                .addComponent(lavoroL)
                                .addComponent(animaliL)
                        )
                        .addGroup(insertLayout.createParallelGroup()
                                .addComponent(numeroTurnoF)
                                .addComponent(dataF)
                                .addComponent(CFF)
                                .addComponent(lavoroF)
                                .addComponent(animaliF)
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
                        .addGroup(insertLayout.createParallelGroup()
                                .addComponent(CFL)
                                .addComponent(CFF)
                        )
                        .addGroup(insertLayout.createParallelGroup()
                                .addComponent(lavoroL)
                                .addComponent(lavoroF)
                        )
                        .addGroup(insertLayout.createParallelGroup()
                                .addComponent(animaliL)
                                .addComponent(animaliF)
                        )
                        .addComponent(aggiungi)
        );

        insertPanelGen.add(numeroTurnoL);
        insertPanelGen.add(numeroTurnoF);
        insertPanelGen.add(dataL);
        insertPanelGen.add(dataF);
        insertPanelGen.add(CFL);
        insertPanelGen.add(CFF);
        insertPanelGen.add(lavoroL);
        insertPanelGen.add(lavoroF);
        insertPanelGen.add(animaliL);
        insertPanelGen.add(animaliF);
        insertPanelGen.add(aggiungi);

        animaliL.setEnabled(false);
        animaliF.setEditable(false);
        animaliF.setEnabled(false);
        lavoroCheck.addActionListener(e -> {
            if (lavoroCheck.isSelected()){
                animaliL.setEnabled(true);
                animaliF.setEditable(true);
                animaliF.setEnabled(true);
            } else {
                animaliL.setEnabled(false);
                animaliF.setEditable(false);
                animaliF.setEnabled(false);
            }

        });

        add(insertScroll, BorderLayout.CENTER);
        aggiungi.addActionListener(e -> {
            Optional<List<String>> animaliList;
            if (animaliF.getText().isEmpty()) {
                animaliList = Optional.empty();
            } else {
                animaliList = Optional.of(new ArrayList<>(Arrays.asList(animaliF.getText().split(","))));
            }
            try {
                new TaskDAO().insertTask(CFF.getText(), Byte.parseByte(numeroTurnoF.getText()),
                        LocalDate.parse(dataF.getText()), lavoroF.getText(), animaliList);
                JOptionPane.showMessageDialog(this, "L'inserimento è avvenuto correttamente");

            } catch (DataAccessException data) {
                Throwable cause = data.getCause();
                if (cause instanceof SQLException) {
                    JOptionPane.showMessageDialog(this, "Errore nell'inserimento." +
                            " Ricontrollare che i campi siano stati riempiti correttamente");
                }
            } catch (NumberFormatException numb) {
                JOptionPane.showMessageDialog(this, "Errore nell'inserimento. Alcuni campi obbligatori non sono stati riempiti");
            }
        });
    }
}
