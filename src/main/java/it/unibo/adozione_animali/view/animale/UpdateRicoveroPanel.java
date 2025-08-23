package it.unibo.adozione_animali.view.animale;

import it.unibo.adozione_animali.model.impl.RicoveroDAO;
import org.jooq.exception.DataAccessException;

import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;
import java.time.LocalDate;

public class UpdateRicoveroPanel extends JPanel {
    
    JPanel updatePanel;
    
    public UpdateRicoveroPanel() {

        setLayout(new BorderLayout());

        updatePanel = new JPanel(new BorderLayout());
        JPanel updatePanelMore = new JPanel(new BorderLayout());
        JPanel updatePanelGen = new JPanel();
        updatePanelMore.add(updatePanelGen, BorderLayout.NORTH);
        updatePanelMore.add(new JPanel(), BorderLayout.CENTER);
        GroupLayout updateLayout = new GroupLayout(updatePanelGen);
        updatePanelGen.setLayout(updateLayout);
        JScrollPane updateScroll = new JScrollPane(updatePanelMore);
        updateScroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        updateScroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        updatePanel.add(updateScroll, BorderLayout.CENTER);


        JButton update = new JButton("Aggiorna");
        JLabel codFascicoloLabel = new JLabel("Codice Fascicolo");
        JTextField codFascicoloField = new JTextField(5);
        JLabel numeroPaginaLabel = new JLabel("Numero Pagina");
        JTextField numeroPaginaField = new JTextField(3);
        JLabel paragrafoLabel = new JLabel("Paragrafo Pagina");
        JTextField paragrafoField = new JTextField(1);
        JTextField data_ricovero = new JTextField(50);
        JLabel dataRicovero = new JLabel("Nuova Data Fine Ricovero (aaaa-mm-dd)");

        updateLayout.setAutoCreateContainerGaps(true);
        updateLayout.setAutoCreateGaps(true);
        updateLayout.setHorizontalGroup(
                updateLayout.createSequentialGroup()
                        .addGroup(updateLayout.createParallelGroup()
                                .addComponent(codFascicoloLabel)
                                .addComponent(numeroPaginaLabel)
                                .addComponent(paragrafoLabel)
                                .addComponent(dataRicovero)
                        )
                        .addGroup(updateLayout.createParallelGroup()
                                .addComponent(codFascicoloField)
                                .addComponent(numeroPaginaField)
                                .addComponent(paragrafoField)
                                .addComponent(data_ricovero)
                                .addComponent(update)
                        )
        );
        updateLayout.setVerticalGroup(
                updateLayout.createSequentialGroup()
                        .addGroup(updateLayout.createParallelGroup()
                                .addComponent(codFascicoloLabel)
                                .addComponent(codFascicoloField)
                        )
                        .addGroup(updateLayout.createParallelGroup()
                                .addComponent(numeroPaginaLabel)
                                .addComponent(numeroPaginaField)
                        )
                        .addGroup(updateLayout.createParallelGroup()
                                .addComponent(paragrafoLabel)
                                .addComponent(paragrafoField)
                        )
                        .addGroup(updateLayout.createParallelGroup()
                                .addComponent(dataRicovero)
                                .addComponent(data_ricovero)
                        )
                        .addComponent(update)
        );

        updatePanelGen.add(codFascicoloField);
        updatePanelGen.add(codFascicoloLabel);
        updatePanelGen.add(numeroPaginaField);
        updatePanelGen.add(numeroPaginaLabel);
        updatePanelGen.add(paragrafoField);
        updatePanelGen.add(paragrafoLabel);
        updatePanelGen.add(dataRicovero);
        updatePanelGen.add(data_ricovero);
        updatePanelGen.add(update);

        update.addActionListener(e -> {
            try {
                Integer codFas = Integer.parseInt(codFascicoloField.getText());
                Short numPag = Short.parseShort(numeroPaginaField.getText());
                String par = paragrafoField.getText();

                new RicoveroDAO().updateRicovero(codFas, numPag, par, LocalDate.parse(data_ricovero.getText()));
                JOptionPane.showMessageDialog(this, "L'aggiornamento è avvenuto correttamente");

            } catch (DataAccessException data) {
                Throwable cause = data.getCause();
                if (cause instanceof SQLException) {
                    JOptionPane.showMessageDialog(this, "Errore nell'inserimento. Ricontrollare che i campi siano stati riempiti correttamente");
                }
            } catch (Exception numb) {
                JOptionPane.showMessageDialog(this, "Errore nell'inserimento. Ricontrollare che i campi siano stati riempiti correttamente");
            }
        });

        add(updatePanel, BorderLayout.CENTER);
        
    }
}
