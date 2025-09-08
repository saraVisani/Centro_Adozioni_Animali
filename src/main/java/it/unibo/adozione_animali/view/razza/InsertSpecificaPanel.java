package it.unibo.adozione_animali.view.razza;

import it.unibo.adozione_animali.model.impl.caratteristica.ContenutoDAO;
import it.unibo.adozione_animali.model.impl.caratteristica.RiferimentoDAO;
import it.unibo.adozione_animali.model.impl.caratteristica.SpecificaRazzaDAO;

import javax.swing.*;
import java.awt.*;
import java.util.Arrays;
import java.util.List;

public class InsertSpecificaPanel extends JPanel {

    public InsertSpecificaPanel() {
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

        JTextField codSpecificaF = new JTextField();
        JTextField tipoSpecificaF = new JTextField();
        JLabel codSpecificaL = new JLabel("Codice Specifica");
        JLabel tipoSpecificaL = new JLabel("Tipo Specifica");
        JTextArea razzeF = new JTextArea();
        JTextField specificaF = new JTextField();
        JLabel razzeL = new JLabel("Razze Associate (formato: codspecie1, razza1; codspecie2, razza2)");
        JLabel specificaL = new JLabel("Specifica");



        insertLayout.setAutoCreateContainerGaps(true);
        insertLayout.setAutoCreateGaps(true);
        insertLayout.setHorizontalGroup(
                insertLayout.createSequentialGroup()
                        .addGroup(insertLayout.createParallelGroup()
                                .addComponent(codSpecificaL)
                                .addComponent(tipoSpecificaL)
                                .addComponent(specificaL)
                                .addComponent(razzeL)
                        )
                        .addGroup(insertLayout.createParallelGroup()
                                .addComponent(codSpecificaF)
                                .addComponent(tipoSpecificaF)
                                .addComponent(specificaF)
                                .addComponent(razzeF)
                                .addComponent(aggiungi)
                        )
        );
        insertLayout.setVerticalGroup(
                insertLayout.createSequentialGroup()
                        .addGroup(insertLayout.createParallelGroup()
                                .addComponent(codSpecificaL)
                                .addComponent(codSpecificaF)
                        )
                        .addGroup(insertLayout.createParallelGroup()
                                .addComponent(tipoSpecificaL)
                                .addComponent(tipoSpecificaF)
                        )
                        .addGroup(insertLayout.createParallelGroup()
                                .addComponent(specificaL)
                                .addComponent(specificaF)
                        )
                        .addGroup(insertLayout.createParallelGroup()
                                .addComponent(razzeL)
                                .addComponent(razzeF)
                        )
                        .addComponent(aggiungi)
        );

        add(insertScroll, BorderLayout.CENTER);

        aggiungi.addActionListener(e -> {
            try {
                if (codSpecificaF.getText().isEmpty() || tipoSpecificaF.getText().isEmpty() ||
                specificaF.getText().isEmpty() || razzeF.getText().isEmpty()) {
                    throw new IllegalArgumentException();
                }
                new SpecificaRazzaDAO().insertSpecificaRazza(codSpecificaF.getText().trim(), tipoSpecificaF.getText().trim(),
                        specificaF.getText().trim());
                ContenutoDAO contenutoDAO = new ContenutoDAO();
                RiferimentoDAO riferimento = new RiferimentoDAO();
                List<String> races = Arrays.asList(razzeF.getText().split(";"));
                for (String race : races) {
                    String[] pair = race.split(",");
                    contenutoDAO.insertContenuto(codSpecificaF.getText(), pair[0].trim());
                    riferimento.insertRiferimento(codSpecificaF.getText(), pair[0].trim(), pair[1].trim());
                }

                JOptionPane.showMessageDialog(this, "L'inserimento è avvenuto correttamente");

            } catch (Exception numb) {
                JOptionPane.showMessageDialog(this, "Errore nell'inserimento. Ricontrollare che i campi siano stati riempiti correttamente");
            }
        });
    }
}
