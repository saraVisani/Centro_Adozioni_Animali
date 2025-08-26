package it.unibo.adozione_animali.view.richiedenti;

import it.unibo.adozione_animali.model.api.Richiesta;
import it.unibo.adozione_animali.model.impl.RichiestaDAO;
import org.jooq.exception.DataAccessException;

import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;

public class InserimentoRichiestaPanel extends JPanel {

    //TODO --> add action listener for button
    public InserimentoRichiestaPanel() {
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
        JTextField cod_prov_ric = new JTextField(2);
        JTextField cod_citta_ric = new JTextField(2);
        JTextField numeroInd_ric = new JTextField(5);
        JTextField CF_ric = new JTextField(16);
        JLabel codProvRic = new JLabel("Provincia del richiedente");
        JLabel codCittaRic = new JLabel("Città del richiedente");
        JLabel numRic = new JLabel("Civico del richiedente");
        JLabel CF = new JLabel("Codice Fiscale del richiedente");


        insertLayout.setAutoCreateContainerGaps(true);
        insertLayout.setAutoCreateGaps(true);
        insertLayout.setHorizontalGroup(
                insertLayout.createSequentialGroup()
                        .addGroup(insertLayout.createParallelGroup()
                                .addComponent(codProv)
                                .addComponent(codCitta)
                                .addComponent(numAnimale)
                                .addComponent(codAnim)
                                .addComponent(codProvRic)
                                .addComponent(codCittaRic)
                                .addComponent(numRic)
                                .addComponent(CF)

                        )
                        .addGroup(insertLayout.createParallelGroup()
                                .addComponent(cod_prov)
                                .addComponent(cod_citta)
                                .addComponent(numeroInd)
                                .addComponent(codAnimale)
                                .addComponent(cod_prov_ric)
                                .addComponent(cod_citta_ric)
                                .addComponent(numeroInd_ric)
                                .addComponent(CF_ric)
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
                                .addComponent(codProvRic)
                                .addComponent(cod_prov_ric)
                        )
                        .addGroup(insertLayout.createParallelGroup()
                                .addComponent(codCittaRic)
                                .addComponent(cod_citta_ric)
                        )
                        .addGroup(insertLayout.createParallelGroup()
                                .addComponent(numRic)
                                .addComponent(numeroInd_ric)
                        )
                        .addGroup(insertLayout.createParallelGroup()
                                .addComponent(CF)
                                .addComponent(CF_ric)
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
        insertPanelGen.add(codProvRic);
        insertPanelGen.add(cod_prov_ric);
        insertPanelGen.add(codCittaRic);
        insertPanelGen.add(cod_citta_ric);
        insertPanelGen.add(numRic);
        insertPanelGen.add(numeroInd_ric);
        insertPanelGen.add(CF);
        insertPanelGen.add(CF_ric);
        insertPanelGen.add(aggiungi);


        add(insertScroll, BorderLayout.CENTER);

        aggiungi.addActionListener(e -> {
            try {
                new RichiestaDAO().insertRichiesta(cod_prov.getText(), cod_citta.getText(),
                        Integer.parseInt(numeroInd.getText()), codAnimale.getText(), CF_ric.getText(),
                        cod_prov_ric.getText(), cod_citta_ric.getText(), Integer.parseInt(numeroInd_ric.getText()));
                JOptionPane.showMessageDialog(this, "L'inserimento è avvenuto correttamente");
            }  catch (DataAccessException data) {
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
