package it.unibo.adozione_animali.view.richiedenti;

import it.unibo.adozione_animali.model.impl.RichiestaDAO;
import org.jooq.exception.DataAccessException;

import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;
import java.time.LocalDate;

public class DeleteRichiestaPanel extends JPanel {


    public DeleteRichiestaPanel() {
        setLayout(new BorderLayout());

        JPanel deletePanel = new JPanel(new BorderLayout());
        JPanel deletePanelGen = new JPanel();
        deletePanel.add(deletePanelGen, BorderLayout.NORTH);
        deletePanel.add(new JPanel(), BorderLayout.CENTER);
        GroupLayout deleteLayout = new GroupLayout(deletePanelGen);
        deletePanelGen.setLayout(deleteLayout);
        JScrollPane deleteScroll = new JScrollPane(deletePanel);
        deleteScroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        deleteScroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);


        JButton cancella = new JButton("Elimina");

        JTextField cod_prov = new JTextField(2);
        JTextField cod_citta = new JTextField(2);
        JTextField numeroInd = new JTextField(5);
        JTextField codAnimale = new JTextField(5);
        JLabel codProv = new JLabel("Provincia del centro");
        JLabel codCitta = new JLabel("Città del centro");
        JLabel numAnimale = new JLabel("Civico del centro");
        JLabel codAnim = new JLabel("Codice dell'animale");
        JTextField data_richiesta = new JTextField();
        JTextField CF_ric = new JTextField(16);
        JLabel dataRichiesta = new JLabel("Data richiesta \n (aaaa-mm-dd))");
        JLabel CF = new JLabel("Codice Fiscale del richiedente");


        deleteLayout.setAutoCreateContainerGaps(true);
        deleteLayout.setAutoCreateGaps(true);
        deleteLayout.setHorizontalGroup(
                deleteLayout.createSequentialGroup()
                        .addGroup(deleteLayout.createParallelGroup()
                                .addComponent(codProv)
                                .addComponent(codCitta)
                                .addComponent(numAnimale)
                                .addComponent(codAnim)
                                .addComponent(dataRichiesta)
                                .addComponent(CF)

                        )
                        .addGroup(deleteLayout.createParallelGroup()
                                .addComponent(cod_prov)
                                .addComponent(cod_citta)
                                .addComponent(numeroInd)
                                .addComponent(codAnimale)
                                .addComponent(data_richiesta)
                                .addComponent(CF_ric)
                                .addComponent(cancella)
                        )
        );
        deleteLayout.setVerticalGroup(
                deleteLayout.createSequentialGroup()
                        .addGroup(deleteLayout.createParallelGroup()
                                .addComponent(codProv)
                                .addComponent(cod_prov)
                        )
                        .addGroup(deleteLayout.createParallelGroup()
                                .addComponent(codCitta)
                                .addComponent(cod_citta)
                        )
                        .addGroup(deleteLayout.createParallelGroup()
                                .addComponent(numAnimale)
                                .addComponent(numeroInd)
                        )
                        .addGroup(deleteLayout.createParallelGroup()
                                .addComponent(codAnim)
                                .addComponent(codAnimale)
                        )
                        .addGroup(deleteLayout.createParallelGroup()
                                .addComponent(dataRichiesta)
                                .addComponent(data_richiesta)
                        )
                        .addGroup(deleteLayout.createParallelGroup()
                                .addComponent(CF)
                                .addComponent(CF_ric)
                        )
                        .addComponent(cancella)
        );

        deletePanelGen.add(codProv);
        deletePanelGen.add(cod_prov);
        deletePanelGen.add(codCitta);
        deletePanelGen.add(cod_citta);
        deletePanelGen.add(numAnimale);
        deletePanelGen.add(numeroInd);
        deletePanelGen.add(codAnim);
        deletePanelGen.add(codAnimale);
        deletePanelGen.add(dataRichiesta);
        deletePanelGen.add(data_richiesta);
        deletePanelGen.add(CF);
        deletePanelGen.add(CF_ric);
        deletePanelGen.add(cancella);

        cancella.addActionListener(e -> {
            try {
                new RichiestaDAO().deleteRichiesta(cod_prov.getText(), cod_citta.getText(),
                        Integer.parseInt(numeroInd.getText()), codAnimale.getText(),
                        LocalDate.parse(data_richiesta.getText()), CF_ric.getText());
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
        add(deleteScroll, BorderLayout.CENTER);
    }
}
