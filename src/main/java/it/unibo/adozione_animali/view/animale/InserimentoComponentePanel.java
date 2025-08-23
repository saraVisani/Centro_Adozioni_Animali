package it.unibo.adozione_animali.view.animale;

import it.unibo.adozione_animali.model.impl.ComponenteDAO;
import org.jooq.exception.DataAccessException;

import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;

public class InserimentoComponentePanel extends JPanel {

    //TODO --> add action listener for button
    public InserimentoComponentePanel() {
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
        JLabel perc = new JLabel("Percentuale Composizione");


        insertLayout.setAutoCreateContainerGaps(true);
        insertLayout.setAutoCreateGaps(true);
        insertLayout.setHorizontalGroup(
                insertLayout.createSequentialGroup()
                        .addGroup(insertLayout.createParallelGroup()
                                .addComponent(codProv)
                                .addComponent(codCitta)
                                .addComponent(numAnimale)
                                .addComponent(codAnim)
                                .addComponent(codSpecie)
                                .addComponent(nomeRazza)
                                .addComponent(perc)
                        )
                        .addGroup(insertLayout.createParallelGroup()
                                .addComponent(cod_prov)
                                .addComponent(cod_citta)
                                .addComponent(numeroInd)
                                .addComponent(codAnimale)
                                .addComponent(cod_specie)
                                .addComponent(nome_razza)
                                .addComponent(percentuale)
                                .addComponent(aggiungi)
                        )
        );
        insertLayout.setVerticalGroup(
                insertLayout.createSequentialGroup()
                        .addGroup(insertLayout.createParallelGroup()
                                .addComponent(codProv)
                                .addComponent(cod_prov)
                        )
                        .addGroup(insertLayout.createParallelGroup()
                                .addComponent(codCitta)
                                .addComponent(cod_citta)
                        )
                        .addGroup(insertLayout.createParallelGroup()
                                .addComponent(numAnimale)
                                .addComponent(numeroInd)
                        )
                        .addGroup(insertLayout.createParallelGroup()
                                .addComponent(codAnim)
                                .addComponent(codAnimale)
                        )
                        .addGroup(insertLayout.createParallelGroup()
                                .addComponent(codSpecie)
                                .addComponent(cod_specie)
                        )
                        .addGroup(insertLayout.createParallelGroup()
                                .addComponent(nomeRazza)
                                .addComponent(nome_razza)
                        )
                        .addGroup(insertLayout.createParallelGroup()
                                .addComponent(perc)
                                .addComponent(percentuale)
                        )
                        .addComponent(aggiungi)
        );

        insertPanelGen.add(codProv);
        insertPanelGen.add(cod_prov);
        insertPanelGen.add(codCitta);
        insertPanelGen.add(cod_citta);
        insertPanelGen.add(numAnimale);
        insertPanelGen.add(numeroInd);
        insertPanelGen.add(codAnim);
        insertPanelGen.add(codAnimale);
        insertPanelGen.add(codSpecie);
        insertPanelGen.add(cod_specie);
        insertPanelGen.add(nomeRazza);
        insertPanelGen.add(nome_razza);
        insertPanelGen.add(perc);
        insertPanelGen.add(percentuale);
        insertPanelGen.add(aggiungi);

        aggiungi.addActionListener(e -> {
            try {
                new ComponenteDAO().insertComponente(cod_prov.getText(), cod_citta.getText(),
                        Integer.parseInt(numeroInd.getText()), codAnimale.getText(), cod_specie.getText(),
                        nome_razza.getText(), Integer.parseInt(percentuale.getText()));
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

        add(insertScroll, BorderLayout.CENTER);
    }
}
