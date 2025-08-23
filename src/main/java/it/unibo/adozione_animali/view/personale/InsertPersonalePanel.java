package it.unibo.adozione_animali.view.personale;

import it.unibo.adozione_animali.model.impl.PersonaDAO;
import it.unibo.adozione_animali.model.impl.PersonaleDAO;
import org.jooq.exception.DataAccessException;

import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;
import java.time.LocalDate;

public class InsertPersonalePanel extends JPanel {

    JPanel insertPanel;

    public InsertPersonalePanel() {
        setLayout(new BorderLayout());

        insertPanel = new JPanel(new BorderLayout());
        JPanel insertPanelMore = new JPanel(new BorderLayout());
        JPanel insertPanelGen = new JPanel();
        insertPanelMore.add(insertPanelGen, BorderLayout.NORTH);
        insertPanelMore.add(new JPanel(), BorderLayout.CENTER);
        GroupLayout insertLayout = new GroupLayout(insertPanelGen);
        insertPanelGen.setLayout(insertLayout);
        JScrollPane insertScroll = new JScrollPane(insertPanelMore);
        insertScroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        insertScroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        insertPanel.add(insertScroll, BorderLayout.CENTER);

        JCheckBox volontarioCheck = new JCheckBox();
        JLabel volontario = new JLabel("Inserimento di un nuovo volontario");
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
        insertPanel.add(checkPanel, BorderLayout.NORTH);

        JButton insert = new JButton("Aggiungi");
        JLabel CFL = new JLabel("Codice Fiscale");
        JTextField CFF = new JTextField(16);
        JLabel nomeL = new JLabel("Nome");
        JTextField nomeF = new JTextField();
        JLabel cognomeL = new JLabel("Cognome");
        JTextField cognomeF = new JTextField();
        JTextField emailF = new JTextField();
        JLabel emailL = new JLabel("Email");
        JTextField passwordF = new JTextField();
        JLabel passwordL = new JLabel("Password");
        JTextField telefonoF = new JTextField();
        JLabel telefonoL = new JLabel("Telefono (formato +39)");
        JTextField dataAssunzioneDipendenteF = new JTextField();
        JLabel dataAssuDipL = new JLabel("Data Assunzione Dipendente (aaaa-mm-dd)");
        JTextField dataAssuVolF = new JTextField();
        JLabel dataAssuVolL = new JLabel("Data Assunzione Volontario (aaaa-mm-dd)");
        JTextField stipendioF = new JTextField();
        JLabel stipendioL = new JLabel("Stipendio");
        JLabel codProvL = new JLabel("Codice Provincia Centro");
        JTextField codProvF = new JTextField(2);
        JLabel codCittaL = new JLabel("Codice Città Centro");
        JTextField codCittaF = new JTextField(2);
        JLabel numeroL = new JLabel("Civico Centro");
        JTextField numeroF = new JTextField(5);

        insertLayout.setAutoCreateContainerGaps(true);
        insertLayout.setAutoCreateGaps(true);
        insertLayout.setHorizontalGroup(
                insertLayout.createSequentialGroup()
                        .addGroup(insertLayout.createParallelGroup()
                                .addComponent(CFL)
                                .addComponent(nomeL)
                                .addComponent(cognomeL)
                                .addComponent(emailL)
                                .addComponent(passwordL)
                                .addComponent(telefonoL)
                                .addComponent(dataAssuDipL)
                                .addComponent(dataAssuVolL)
                                .addComponent(codProvL)
                                .addComponent(codCittaL)
                                .addComponent(numeroL)
                                .addComponent(stipendioL)
                        )
                        .addGroup(insertLayout.createParallelGroup()
                                .addComponent(CFF)
                                .addComponent(nomeF)
                                .addComponent(cognomeF)
                                .addComponent(emailF)
                                .addComponent(passwordF)
                                .addComponent(telefonoF)
                                .addComponent(dataAssunzioneDipendenteF)
                                .addComponent(dataAssuVolF)
                                .addComponent(stipendioF)
                                .addComponent(codProvF)
                                .addComponent(codCittaF)
                                .addComponent(numeroF)
                                .addComponent(insert)
                        )
        );
        insertLayout.setVerticalGroup(
                insertLayout.createSequentialGroup()
                        .addGroup(insertLayout.createParallelGroup()
                                .addComponent(CFL)
                                .addComponent(CFF)
                        )
                        .addGroup(insertLayout.createParallelGroup()
                                .addComponent(nomeL)
                                .addComponent(nomeF)
                        )
                        .addGroup(insertLayout.createParallelGroup()
                                .addComponent(cognomeL)
                                .addComponent(cognomeF)
                        )
                        .addGroup(insertLayout.createParallelGroup()
                                .addComponent(emailL)
                                .addComponent(emailF)
                        )
                        .addGroup(insertLayout.createParallelGroup()
                                .addComponent(passwordL)
                                .addComponent(passwordF)
                        )

                        .addGroup(insertLayout.createParallelGroup()
                                .addComponent(telefonoL)
                                .addComponent(telefonoF)
                        )
                        .addGroup(insertLayout.createParallelGroup()
                                .addComponent(dataAssuDipL)
                                .addComponent(dataAssunzioneDipendenteF)
                        )
                        .addGroup(insertLayout.createParallelGroup()
                                .addComponent(dataAssuVolL)
                                .addComponent(dataAssuVolF)
                        )
                        .addGroup(insertLayout.createParallelGroup()
                                .addComponent(stipendioL)
                                .addComponent(stipendioF)
                        )
                        .addGroup(insertLayout.createParallelGroup()
                                .addComponent(codProvL)
                                .addComponent(codProvF)
                        )
                        .addGroup(insertLayout.createParallelGroup()
                                .addComponent(codCittaL)
                                .addComponent(codCittaF)
                        )
                        .addGroup(insertLayout.createParallelGroup()
                                .addComponent(numeroL)
                                .addComponent(numeroF)
                        )
                        .addComponent(insert)
        );

        insertPanelGen.add(CFF);
        insertPanelGen.add(CFL);
        insertPanelGen.add(nomeF);
        insertPanelGen.add(nomeL);
        insertPanelGen.add(cognomeF);
        insertPanelGen.add(cognomeL);
        insertPanelGen.add(emailL);
        insertPanelGen.add(emailF);
        insertPanelGen.add(passwordL);
        insertPanelGen.add(passwordF);
        insertPanelGen.add(telefonoL);
        insertPanelGen.add(telefonoF);
        insertPanelGen.add(dataAssuDipL);
        insertPanelGen.add(dataAssunzioneDipendenteF);
        insertPanelGen.add(dataAssuVolL);
        insertPanelGen.add(dataAssuVolF);
        insertPanelGen.add(stipendioL);
        insertPanelGen.add(stipendioF);
        insertPanelGen.add(insert);

        dataAssuVolF.setEditable(false);

        volontarioCheck.addActionListener(e -> {
            stipendioF.setEditable(!volontarioCheck.isSelected());
            dataAssuVolF.setEditable(volontarioCheck.isSelected());
            dataAssunzioneDipendenteF.setEditable(!volontarioCheck.isSelected());
        });

        add(insertPanel, BorderLayout.CENTER);

        insert.addActionListener(e -> {
            try {
                new PersonaDAO().insertPersona(CFF.getText(), nomeF.getText(), cognomeF.getText(),
                        emailF.getText(), passwordF.getText(), telefonoF.getText());
                if (volontarioCheck.isSelected()) {
                    new PersonaleDAO().insertPersonale(CFF.getText(), (byte) 0, null,
                            LocalDate.parse(dataAssuVolF.getText()), null, null,
                            null, (byte) 0, (byte) 0, codProvF.getText(),
                            codCittaF.getText(), Integer.parseInt(numeroF.getText()));
                } else {
                    new PersonaleDAO().insertPersonale(CFF.getText(), (byte) 0, LocalDate.parse(dataAssunzioneDipendenteF.getText()),
                            null, null, null, Short.parseShort(stipendioF.getText()),
                            (byte) 0, (byte) 0, codProvF.getText(), codCittaF.getText(), Integer.parseInt(numeroF.getText()));
                }
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
