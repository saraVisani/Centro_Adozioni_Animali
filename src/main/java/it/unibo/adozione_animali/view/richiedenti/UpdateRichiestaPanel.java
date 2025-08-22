package it.unibo.adozione_animali.view;

import javax.swing.*;
import java.awt.*;

public class UpdateRichiestaPanel extends JPanel{

    //TODO --> add action listener for buttons
    public UpdateRichiestaPanel() {

        setLayout(new BorderLayout());

        JPanel updatePanel = new JPanel(new BorderLayout());
        JPanel updatePanelGen = new JPanel();
        updatePanel.add(updatePanelGen, BorderLayout.NORTH);
        updatePanel.add(new JPanel(), BorderLayout.CENTER);
        GroupLayout updateLayout = new GroupLayout(updatePanelGen);
        updatePanelGen.setLayout(updateLayout);
        JScrollPane updateScroll = new JScrollPane(updatePanel);
        updateScroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        updateScroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);


        JButton accetta = new JButton("Accetta");
        JButton rifiuta = new JButton("Rifiuta");

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

        JPanel buttonPanel = new JPanel();
        GroupLayout buttonLayout = new GroupLayout(buttonPanel);
        buttonLayout.setAutoCreateContainerGaps(true);
        buttonLayout.setAutoCreateGaps(true);
        buttonLayout.setHorizontalGroup(
                buttonLayout.createSequentialGroup()
                        .addComponent(accetta)
                        .addComponent(rifiuta)
        );
        buttonLayout.setVerticalGroup(
                buttonLayout.createSequentialGroup()
                        .addGroup(buttonLayout.createParallelGroup()
                                .addComponent(accetta)
                                .addComponent(rifiuta))
        );
        buttonPanel.add(accetta);
        buttonPanel.add(rifiuta);
        buttonPanel.setLayout(buttonLayout);

        updateLayout.setAutoCreateContainerGaps(true);
        updateLayout.setAutoCreateGaps(true);
        updateLayout.setHorizontalGroup(
                updateLayout.createSequentialGroup()
                        .addGroup(updateLayout.createParallelGroup()
                                .addComponent(codProv)
                                .addComponent(codCitta)
                                .addComponent(numAnimale)
                                .addComponent(codAnim)
                                .addComponent(dataRichiesta)
                                .addComponent(CF)

                        )
                        .addGroup(updateLayout.createParallelGroup()
                                .addComponent(cod_prov)
                                .addComponent(cod_citta)
                                .addComponent(numeroInd)
                                .addComponent(codAnimale)
                                .addComponent(data_richiesta)
                                .addComponent(CF_ric)
                                .addComponent(buttonPanel)
                        )
        );
        updateLayout.setVerticalGroup(
                updateLayout.createSequentialGroup()
                        .addGroup(updateLayout.createParallelGroup()
                                .addComponent(codProv)
                                .addComponent(cod_prov)
                        )
                        .addGroup(updateLayout.createParallelGroup()
                                .addComponent(codCitta)
                                .addComponent(cod_citta)
                        )
                        .addGroup(updateLayout.createParallelGroup()
                                .addComponent(numAnimale)
                                .addComponent(numeroInd)
                        )
                        .addGroup(updateLayout.createParallelGroup()
                                .addComponent(codAnim)
                                .addComponent(codAnimale)
                        )
                        .addGroup(updateLayout.createParallelGroup()
                                .addComponent(dataRichiesta)
                                .addComponent(data_richiesta)
                        )
                        .addGroup(updateLayout.createParallelGroup()
                                .addComponent(CF)
                                .addComponent(CF_ric)
                        )
                        .addComponent(buttonPanel)
        );

        updatePanelGen.add(codProv);
        updatePanelGen.add(cod_prov);
        updatePanelGen.add(codCitta);
        updatePanelGen.add(cod_citta);
        updatePanelGen.add(numAnimale);
        updatePanelGen.add(numeroInd);
        updatePanelGen.add(codAnim);
        updatePanelGen.add(codAnimale);
        updatePanelGen.add(dataRichiesta);
        updatePanelGen.add(data_richiesta);
        updatePanelGen.add(CF);
        updatePanelGen.add(CF_ric);
        updatePanelGen.add(buttonPanel);


        add(updateScroll, BorderLayout.CENTER);
    }
}
