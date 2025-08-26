package it.unibo.adozione_animali.view.personale;

import it.unibo.adozione_animali.model.impl.PersonaDAO;
import it.unibo.adozione_animali.model.impl.PersonaleDAO;
import org.jooq.exception.DataAccessException;

import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;
import java.time.LocalDate;

public class UpdatePersonalePanel extends JPanel {

    public UpdatePersonalePanel() {
        setLayout(new BorderLayout());

        JPanel updatePanel = new JPanel(new BorderLayout());
        JPanel updatePanelMore = new JPanel(new BorderLayout());
        JPanel updatePanelGen = new JPanel();
        updatePanelMore.add(updatePanelGen, BorderLayout.NORTH);
        updatePanelMore.add(new JPanel(), BorderLayout.CENTER);
        GroupLayout updateLayout = new GroupLayout(updatePanelGen);
        updatePanelGen.setLayout(updateLayout);
        JScrollPane updateScroll = new JScrollPane(updatePanel);
        updateScroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        updateScroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);


        JButton aggiorna = new JButton("Aggiorna");

        JTextField CFF = new JTextField();
        JTextField emailF = new JTextField();
        JTextField passwordF = new JTextField();
        JTextField telefonoF = new JTextField();
        JTextField tempoLavoro = new JTextField();
        JTextField dataAssuDipF = new JTextField();
        JTextField dataAssuVolF = new JTextField();
        JTextField dataFineDipF = new JTextField();
        JTextField dataFineVolF = new JTextField();
        JTextField stipendioF = new JTextField();
        JLabel CFL = new JLabel("Codice Fiscale");
        JLabel tempoLavoroL = new JLabel("Tempo di lavoro (anni)");
        JLabel dataAssuDipL = new JLabel("Data Assunzione Dipendente (aaaa-mm-dd)");
        JLabel dataAssuVolL = new JLabel("Data Assunzione Volontario (aaaa-mm-dd)");
        JLabel dataFineDipL = new JLabel("Data Fine Lavoro Dipendente (aaaa-mm-dd)");
        JLabel dataFineVolL = new JLabel("Data Fine Lavoro Volontario (aaaa-mm-dd)");
        JLabel stipendioL = new JLabel("Stipendio");
        JLabel emailL = new JLabel("Nuova Email");
        JLabel telefonoL = new JLabel("Nuovo Telefono (formato +39)");
        JLabel passwordL = new JLabel("Nuova Password");

        updateLayout.setAutoCreateContainerGaps(true);
        updateLayout.setAutoCreateGaps(true);
        updateLayout.setHorizontalGroup(
                updateLayout.createSequentialGroup()
                        .addGroup(updateLayout.createParallelGroup()
                                .addComponent(CFL)
                                .addComponent(emailL)
                                .addComponent(passwordL)
                                .addComponent(telefonoL)
                                .addComponent(tempoLavoroL)
                                .addComponent(dataAssuDipL)
                                .addComponent(dataAssuVolL)
                                .addComponent(dataFineDipL)
                                .addComponent(dataFineVolL)
                                .addComponent(stipendioL)
                        )
                        .addGroup(updateLayout.createParallelGroup()
                                .addComponent(CFF)
                                .addComponent(emailF)
                                .addComponent(passwordF)
                                .addComponent(telefonoF)
                                .addComponent(tempoLavoro)
                                .addComponent(dataAssuDipF)
                                .addComponent(dataAssuVolF)
                                .addComponent(dataFineDipF)
                                .addComponent(dataFineVolF)
                                .addComponent(stipendioF)
                                .addComponent(aggiorna)
                        )
        );
        updateLayout.setVerticalGroup(
                updateLayout.createSequentialGroup()
                        .addGroup(updateLayout.createParallelGroup()
                                .addComponent(CFL)
                                .addComponent(CFF)
                        )
                        .addGroup(updateLayout.createParallelGroup()
                                .addComponent(emailL)
                                .addComponent(emailF)
                        )
                        .addGroup(updateLayout.createParallelGroup()
                                .addComponent(passwordL)
                                .addComponent(passwordF)
                        )
                        .addGroup(updateLayout.createParallelGroup()
                                .addComponent(telefonoL)
                                .addComponent(telefonoF)
                        )
                        .addGroup(updateLayout.createParallelGroup()
                                .addComponent(tempoLavoroL)
                                .addComponent(tempoLavoro)
                        )
                        .addGroup(updateLayout.createParallelGroup()
                                .addComponent(dataAssuDipL)
                                .addComponent(dataAssuDipF)
                        )
                        .addGroup(updateLayout.createParallelGroup()
                                .addComponent(dataAssuVolL)
                                .addComponent(dataAssuVolF)
                        )
                        .addGroup(updateLayout.createParallelGroup()
                                .addComponent(dataFineDipL)
                                .addComponent(dataFineDipF)
                        )
                        .addGroup(updateLayout.createParallelGroup()
                                .addComponent(dataFineVolL)
                                .addComponent(dataFineVolF)
                        )
                        .addGroup(updateLayout.createParallelGroup()
                                .addComponent(stipendioL)
                                .addComponent(stipendioF)
                        )
                        .addComponent(aggiorna)
        );

        JCheckBox volontarioCheck = new JCheckBox();
        JLabel volontario = new JLabel("Update delle informazioni di un volontario");
        JPanel checkPanel = new JPanel();
        GroupLayout checkLayout = new GroupLayout(checkPanel);
        checkLayout.setAutoCreateGaps(true);
        checkLayout.setAutoCreateContainerGaps(true);

        checkPanel.add(volontarioCheck);
        checkPanel.add(volontario);
        checkPanel.setLayout(checkLayout);
        checkLayout.setHorizontalGroup(checkLayout.createSequentialGroup()
                .addComponent(volontario)
                .addComponent(volontarioCheck));
        checkLayout.setVerticalGroup(checkLayout.createSequentialGroup()
                .addGroup(checkLayout.createParallelGroup()
                        .addComponent(volontario)
                        .addComponent(volontarioCheck)));

        updatePanel.add(updatePanelMore, BorderLayout.CENTER);
        updatePanel.add(checkPanel, BorderLayout.NORTH);

        dataAssuDipF.setEditable(false);
        dataAssuDipL.setEnabled(false);
        dataFineVolF.setEditable(false);
        dataFineVolL.setEnabled(false);

        volontarioCheck.addActionListener(e -> {
            if (volontarioCheck.isSelected()) {
                dataAssuVolF.setEditable(false);
                dataAssuVolL.setEnabled(false);
                dataFineDipF.setEditable(false);
                dataFineDipL.setEnabled(false);
                stipendioF.setEditable(false);
                stipendioL.setEnabled(false);
                dataAssuDipF.setEditable(true);
                dataAssuDipL.setEnabled(true);
                dataFineVolF.setEditable(true);
                dataFineVolL.setEnabled(true);
            } else {
                dataAssuVolF.setEditable(true);
                dataAssuVolL.setEnabled(true);
                dataFineDipF.setEditable(true);
                dataFineDipL.setEnabled(true);
                stipendioF.setEditable(true);
                stipendioL.setEnabled(true);
                dataAssuDipF.setEditable(false);
                dataAssuDipL.setEnabled(false);
                dataFineVolF.setEditable(false);
                dataFineVolL.setEnabled(false);
            }
        });

        aggiorna.addActionListener(e -> {
            try {
                if (CFF.getText().isEmpty()) {
                    throw new IllegalArgumentException("Il codice fiscale deve essere inserito");
                }
                PersonaDAO persona = new PersonaDAO();
                PersonaleDAO personale = new PersonaleDAO();
                if (!emailF.getText().isEmpty()) {
                    persona.updateEmail(CFF.getText(), emailF.getText());
                }
                if (!passwordF.getText().isEmpty()) {
                    persona.updatePassword(CFF.getText(), passwordF.getText());
                }
                if (!telefonoF.getText().isEmpty()) {
                    persona.updateTelefono(CFF.getText(), telefonoF.getText());
                }
                if (!dataAssuDipF.getText().isEmpty()) {
                    personale.updateDataAssunzioneDip(CFF.getText(), LocalDate.parse(dataAssuDipF.getText()));
                }
                if (!dataAssuVolF.getText().isEmpty()) {
                    personale.updateDataAssunzioneVol(CFF.getText(), LocalDate.parse(dataAssuVolF.getText()));
                }
                if (!dataFineDipF.getText().isEmpty()) {
                    personale.updateDataFineLavoroDip(CFF.getText(), LocalDate.parse(dataFineDipF.getText()));
                }
                if (!dataFineVolF.getText().isEmpty()) {
                    personale.updateDataFineLavoroVol(CFF.getText(), LocalDate.parse(dataFineVolF.getText()));
                }
                if (!tempoLavoro.getText().isEmpty()) {
                    personale.updateTempoLavoro(CFF.getText(), (byte) Integer.parseInt(tempoLavoro.getText()));
                }
                if (!stipendioF.getText().isEmpty()) {
                    personale.updateStipendio(CFF.getText(), Short.parseShort(stipendioF.getText()));
                }

                JOptionPane.showMessageDialog(this, "L'aggiornamento è avvenuto correttamente");
            }  catch (DataAccessException data) {
                Throwable cause = data.getCause();
                if (cause instanceof SQLException) {
                    JOptionPane.showMessageDialog(this, "Errore nell'inserimento. Ricontrollare che i campi siano stati riempiti correttamente");
                }
            } catch (Exception numb) {
                JOptionPane.showMessageDialog(this, "Errore nell'inserimento. Ricontrollare che i campi siano stati riempiti correttamente");
            }

        });

        add(updateScroll, BorderLayout.CENTER);
    }
}
