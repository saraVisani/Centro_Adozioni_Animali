package it.unibo.adozione_animali.view.animale;

import it.unibo.adozione_animali.model.impl.EsameDAO;
import it.unibo.adozione_animali.model.impl.PaginaDAO;
import it.unibo.adozione_animali.model.impl.ProblemaDAO;
import it.unibo.adozione_animali.model.impl.RicoveroDAO;
import nu.studer.sample.tables.records.EsameRecord;
import nu.studer.sample.tables.records.ProblemaRecord;
import nu.studer.sample.tables.records.RicoveroRecord;
import org.jooq.Record;
import org.jooq.exception.DataAccessException;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FascicoloPanel extends JPanel {

    JPanel cards;
    JComboBox<String> comboBox;

    public FascicoloPanel() {
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createTitledBorder(
                        BorderFactory.createEtchedBorder(),
                        "Fascicolo Sanitario",
                        TitledBorder.CENTER,
                        TitledBorder.TOP
                ),
                BorderFactory.createEmptyBorder(25, 10, 10, 10)
        ));

        // CardLayout
        cards = new JPanel(new CardLayout());
        // Opzioni
        String[] options = {"--Seleziona--", "Inserisci Pagina", "Rimuovi Pagina",
                "Visualizza Pagine", "Aggiorna Pagina"};
        comboBox = new JComboBox<>(options);

        JPanel selectPanel = new JPanel();

        JLabel infoIns = new JLabel("<html>Se si vuole creare una nuova pagina non si inserisca il numero di pagina<br>" +
                "altrimenti si continuerà l'inserimento dall'ultimo paragrafo della pagina indicata</html>");
        JLabel codFascicoloLabel = new JLabel("Codice Fascicolo");
        JTextField codFascicoloField = new JTextField(5);
        JTextField numPagina = new JTextField();
        JLabel numeroPagina = new JLabel("Numero Pagina (opzionale)");

        //INSERTION PANEL
        JPanel insertPanel = new JPanel(new BorderLayout());
        JPanel insertPanelGen = new JPanel(new BorderLayout());
        insertPanel.add(insertPanelGen, BorderLayout.CENTER);

        JPanel cardInsert = new JPanel(new CardLayout());
        insertPanelGen.add(cardInsert, BorderLayout.CENTER);
        JPanel problemInsert = new JPanel();
        JPanel examInsert = new JPanel();
        JPanel hospitalizationInsert = new JPanel();

        JScrollPane scrollProblem = new JScrollPane(problemInsert);
        scrollProblem.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollProblem.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

        JScrollPane scrollExam =  new JScrollPane(examInsert);
        scrollExam.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollExam.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

        JScrollPane scrollHospital = new JScrollPane(hospitalizationInsert);
        scrollHospital.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollExam.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

        cardInsert.add(scrollProblem, "Nuovo Problema");
        cardInsert.add(scrollExam, "Nuovo Esame");
        cardInsert.add(scrollHospital, "Nuovo Ricovero");
        ((CardLayout) cardInsert.getLayout()).show(cardInsert, "Nuovo Problema");


        String[] optionsInsertion = {"Nuovo Problema", "Nuovo Esame", "Nuovo Ricovero"};
        JComboBox<String> comboBoxInsertion = new JComboBox<>(optionsInsertion);
        insertPanel.add(comboBoxInsertion, BorderLayout.NORTH);


        JCheckBox checkFascicolo = new JCheckBox();
        JLabel fascicoloPres = new JLabel("Il fascicolo sanitario desiderato non è esistente");

        JPanel animale = new JPanel();
        GroupLayout groupLayoutA = new GroupLayout(animale); // qui inserisco i campi in più

        JPanel check = new JPanel();
        GroupLayout checkGroupLayout = new GroupLayout(check);
        check.add(fascicoloPres);
        check.add(checkFascicolo);
        checkGroupLayout.setAutoCreateGaps(true);
        checkGroupLayout.setAutoCreateContainerGaps(true);
        checkGroupLayout.setHorizontalGroup(
                checkGroupLayout.createSequentialGroup()
                        .addComponent(fascicoloPres)
                        .addComponent(checkFascicolo)
        );
        checkGroupLayout.setVerticalGroup(
                checkGroupLayout.createSequentialGroup()
                        .addGroup(checkGroupLayout.createParallelGroup()
                                .addComponent(fascicoloPres)
                                .addComponent(checkFascicolo))
        );

        insertPanelGen.add(check, BorderLayout.NORTH);

        //solo se non è presente fascicolo
        JTextField cod_prov = new JTextField(2);
        JTextField cod_citta = new JTextField(2);
        JTextField numeroInd = new JTextField(5);
        JTextField codAnimale = new JTextField(5);
        JLabel codProv = new JLabel("Provincia del centro");
        JLabel codCitta = new JLabel("Città del centro");
        JLabel numAnimale = new JLabel("Civico del centro");
        JLabel codAnim = new JLabel("Codice dell'animale");

        groupLayoutA.setAutoCreateContainerGaps(true);
        groupLayoutA.setAutoCreateGaps(true);
        groupLayoutA.setHorizontalGroup(
                groupLayoutA.createSequentialGroup()
                        .addGroup(groupLayoutA.createParallelGroup()
                                .addComponent(codProv)
                                .addComponent(codCitta)
                                .addComponent(numAnimale)
                                .addComponent(codAnim)
                        )
                        .addGroup(groupLayoutA.createParallelGroup()
                                .addComponent(cod_prov)
                                .addComponent(cod_citta)
                                .addComponent(numeroInd)
                                .addComponent(codAnimale)
                        )
        );
        groupLayoutA.setVerticalGroup(
                groupLayoutA.createSequentialGroup()
                        .addGroup(groupLayoutA.createParallelGroup()
                                .addComponent(codProv)
                                .addComponent(cod_prov)
                        )
                        .addGroup(groupLayoutA.createParallelGroup()
                                .addComponent(codCitta)
                                .addComponent(cod_citta)
                        )
                        .addGroup(groupLayoutA.createParallelGroup()
                                .addComponent(numAnimale)
                                .addComponent(numeroInd)
                        )
                        .addGroup(groupLayoutA.createParallelGroup()
                                .addComponent(codAnim)
                                .addComponent(codAnimale)
                        )
        );

        animale.setLayout(groupLayoutA);
        animale.add(codProv);
        animale.add(cod_prov);
        animale.add(codCitta);
        animale.add(cod_citta);
        animale.add(numAnimale);
        animale.add(numeroInd);
        animale.add(codAnim);
        animale.add(codAnimale);

        JPanel wrapper = new JPanel(new FlowLayout(FlowLayout.CENTER));
        wrapper.add(animale);


        //sempre
        JTextField problemName = new JTextField(50);
        JLabel prName = new JLabel("Nome Problema");
        JTextArea description = new JTextArea(10, 50);
        JLabel desc = new JLabel("Descrizione");
        JTextField tipo_cur = new JTextField(25);
        JLabel tipoCur = new JLabel("Tipo Curabile");
        JTextField in_cura = new JTextField(15);
        JLabel inCura = new JLabel("In Cura");
        JTextField area1 = new JTextField(50);
        JLabel Area1 = new JLabel("Area Disabilità 1 (opzionale)");
        JTextField area2 = new JTextField(50);
        JLabel Area2 = new JLabel("Area Disabilità 2 (opzionale)");
        JTextField r_page_num = new JTextField(3);
        JLabel rNum = new JLabel("Numero Pagina Ricovero Associato (opzionale)");
        JTextField r_page_par = new JTextField(1);
        JLabel rPar = new JLabel("Paragrafo Pagina Ricovero Associato (opzionale)" );
        JTextField e_page_num = new JTextField(3);
        JLabel eNum = new JLabel("Numero Pagina Esame Associato (opzionale)");
        JTextField e_page_par = new JTextField(1);
        JLabel ePar = new JLabel("Paragrafo Pagina Esame Associato (opzionale)");
        JLabel sintomiLabel = new JLabel("Codici dei sintomi (separati da ,)");
        JTextArea sintomiField = new JTextArea();
        JButton aggiungi = new JButton("Aggiungi");

/// ////////////////////////////////////////////////////////////////////////

        //PROBLEM INSERT PANEL
        problemInsert.setLayout(new BorderLayout());
        //problemInsert.add(check, BorderLayout.PAGE_START);
        JPanel problemInsertGen = new JPanel();
        problemInsert.add(problemInsertGen, BorderLayout.PAGE_END);
        GroupLayout groupLayoutP = new GroupLayout(problemInsertGen);
        problemInsertGen.setLayout(groupLayoutP);
        groupLayoutP.setAutoCreateGaps(true);
        groupLayoutP.setAutoCreateContainerGaps(true);


        JCheckBox nonCurabileCheck = new JCheckBox();
        JLabel nonCurabile = new JLabel("Inserimento Problema Non Curabile");
        JPanel checkPanel = new JPanel();
        GroupLayout checkLayout = new GroupLayout(checkPanel);
        checkLayout.setAutoCreateGaps(true);
        checkLayout.setAutoCreateContainerGaps(true);

        checkPanel.add(nonCurabileCheck);
        checkPanel.add(nonCurabile);
        checkPanel.setLayout(checkLayout);
        checkLayout.setHorizontalGroup(checkLayout.createSequentialGroup()
                .addComponent(nonCurabile)
                .addComponent(nonCurabileCheck));
        checkLayout.setVerticalGroup(checkLayout.createSequentialGroup()
                .addGroup(checkLayout.createParallelGroup()
                        .addComponent(nonCurabile)
                        .addComponent(nonCurabileCheck)));
        problemInsert.add(checkPanel, BorderLayout.NORTH);

        area1.setEditable(false);
        area2.setEditable(false);
        Area1.setEnabled(false);
        Area2.setEnabled(false);
        nonCurabileCheck.addActionListener(e -> {
                tipoCur.setEnabled(!nonCurabileCheck.isSelected());
                tipo_cur.setEditable(!nonCurabileCheck.isSelected());
                inCura.setEnabled(!nonCurabileCheck.isSelected());
                in_cura.setEditable(!nonCurabileCheck.isSelected());
                Area1.setEnabled(nonCurabileCheck.isSelected());
                Area2.setEnabled(nonCurabileCheck.isSelected());
                area1.setEditable(nonCurabileCheck.isSelected());
                area2.setEditable(nonCurabileCheck.isSelected());
        });

        groupLayoutP.setHorizontalGroup(
                groupLayoutP.createSequentialGroup()
                        .addGroup(groupLayoutP.createParallelGroup()
                                .addComponent(infoIns)
                                .addComponent(codFascicoloLabel)
                                .addComponent(numeroPagina)
                                .addComponent(prName)
                                .addComponent(desc)
                                .addComponent(tipoCur)
                                .addComponent(inCura)
                                .addComponent(Area1)
                                .addComponent(Area2)
                                .addComponent(rNum)
                                .addComponent(rPar)
                                .addComponent(eNum)
                                .addComponent(ePar)
                                .addComponent(sintomiLabel)
                        )
                        .addGroup(groupLayoutP.createParallelGroup()
                                .addComponent(codFascicoloField)
                                .addComponent(numPagina)
                                .addComponent(problemName)
                                .addComponent(description)
                                .addComponent(tipo_cur)
                                .addComponent(in_cura)
                                .addComponent(area1)
                                .addComponent(area2)
                                .addComponent(r_page_num)
                                .addComponent(r_page_par)
                                .addComponent(e_page_num)
                                .addComponent(e_page_par)
                                .addComponent(sintomiField)
                                .addComponent(aggiungi)
                        )
        );
        groupLayoutP.setVerticalGroup(
                groupLayoutP.createSequentialGroup()
                        .addComponent(infoIns)
                        .addGroup(groupLayoutP.createParallelGroup()
                                .addComponent(codFascicoloLabel)
                                .addComponent(codFascicoloField)
                        )
                        .addGroup(groupLayoutP.createParallelGroup()
                                .addComponent(numeroPagina)
                                .addComponent(numPagina)
                        )
                        .addGroup(groupLayoutP.createParallelGroup()
                                .addComponent(prName)
                                .addComponent(problemName)
                        )
                        .addGroup(groupLayoutP.createParallelGroup()
                                .addComponent(desc)
                                .addComponent(description)
                        )
                        .addGroup(groupLayoutP.createParallelGroup()
                                .addComponent(tipoCur)
                                .addComponent(tipo_cur)
                        )
                        .addGroup(groupLayoutP.createParallelGroup()
                                .addComponent(inCura)
                                .addComponent(in_cura)
                        )
                        .addGroup(groupLayoutP.createParallelGroup()
                                .addComponent(Area1)
                                .addComponent(area1)
                        )
                        .addGroup(groupLayoutP.createParallelGroup()
                                .addComponent(Area2)
                                .addComponent(area2)
                        )
                        .addGroup(groupLayoutP.createParallelGroup()
                                .addComponent(rNum)
                                .addComponent(r_page_num)
                        )
                        .addGroup(groupLayoutP.createParallelGroup()
                                .addComponent(rPar)
                                .addComponent(r_page_par)
                        )
                        .addGroup(groupLayoutP.createParallelGroup()
                                .addComponent(eNum)
                                .addComponent(e_page_num)
                        )
                        .addGroup(groupLayoutP.createParallelGroup()
                                .addComponent(ePar)
                                .addComponent(e_page_par)
                        )
                        .addGroup(groupLayoutP.createParallelGroup()
                                .addComponent(sintomiLabel)
                                .addComponent(sintomiField)
                        )
                        .addComponent(aggiungi)
        );

        problemInsertGen.add(prName);
        problemInsertGen.add(problemName);
        problemInsertGen.add(desc);
        problemInsertGen.add(description);
        problemInsertGen.add(tipoCur);
        problemInsertGen.add(tipo_cur);
        problemInsertGen.add(inCura);
        problemInsertGen.add(in_cura);
        problemInsertGen.add(Area1);
        problemInsertGen.add(area1);
        problemInsertGen.add(Area2);
        problemInsertGen.add(area2);
        problemInsertGen.add(rNum);
        problemInsertGen.add(r_page_num);
        problemInsertGen.add(rPar);
        problemInsertGen.add(r_page_par);
        problemInsertGen.add(eNum);
        problemInsertGen.add(e_page_num);
        problemInsertGen.add(ePar);
        problemInsertGen.add(e_page_par);
        problemInsertGen.add(sintomiLabel);
        problemInsertGen.add(sintomiField);

        aggiungi.addActionListener(e -> {
            try {
                Integer codFasc;
                String codPr;
                String codCit;
                Integer numCiv;
                String codAni;

                if (!checkFascicolo.isSelected()) {
                    codFasc = Integer.parseInt(codFascicoloField.getText());
                    codPr = null;
                    codCit = null;
                    numCiv = null;
                    codAni = null;
                } else {
                    codFasc = null;
                    codPr = cod_prov.getText();
                    codCit = cod_citta.getText();
                    numCiv = Integer.parseInt(numeroInd.getText());
                    codAni = codAnimale.getText();
                }
                Short ePag = e_page_num.getText().isEmpty() ? null : Short.parseShort(e_page_num.getText());
                String ePara = e_page_par.getText().isEmpty() ? null : e_page_par.getText();
                Short rPag = r_page_num.getText().isEmpty() ? null : Short.parseShort(r_page_num.getText());
                String rPara = r_page_par.getText().isEmpty() ? null : r_page_par.getText();
                Short numP = numPagina.getText().isEmpty() ? null : Short.parseShort(numPagina.getText());
                String newCod;
                List<String> sintomi = new ArrayList<>(Arrays.asList(sintomiField.getText().split(",")));
                System.out.println(sintomi);
                if (nonCurabileCheck.isSelected()) {
                    String ar1 = area1.getText().isEmpty() ? null : area1.getText();
                    String ar2 = area2.getText().isEmpty() ? null : area2.getText();
                    newCod = new ProblemaDAO().insertProblema(codFasc, numP, null, problemName.getText(), description.getText(),
                            null, null, ar1, ar2, codFasc, rPag, rPara,
                            codFasc, ePag, ePara, codPr, codCit, numCiv, codAni, sintomi);

                } else {
                    newCod = new ProblemaDAO().insertProblema(codFasc, numP, null, problemName.getText(), description.getText(),
                            tipo_cur.getText().toUpperCase(), in_cura.getText().toUpperCase(), null, null, codFasc, rPag, rPara,
                            codFasc, ePag, ePara, codPr, codCit, numCiv, codAni, sintomi);
                }
                codFascicoloField.setText(newCod);
                this.repaint();
                this.revalidate();
                JOptionPane.showMessageDialog(this, "L'inserimento è avvenuto correttamente");
            } catch (DataAccessException data) {
                Throwable cause = data.getCause();
                if (cause instanceof SQLException) {
                    JOptionPane.showMessageDialog(this, "Errore nell'inserimento. Ricontrollare che i campi siano stati riempiti correttamente");
                    JOptionPane.showMessageDialog(this,cause + "\n" + data);
                }
            } catch (NumberFormatException numb) {
                JOptionPane.showMessageDialog(this, "Errore nell'inserimento. Ricontrollare che i campi siano stati riempiti correttamente");
                JOptionPane.showMessageDialog(this,numb);
            }
        });
/// ///////////////////////////////////////////////////////////

        //EXAM INSERT PANEL

        JLabel codFascicoloLabel2 = new JLabel("Codice Fascicolo");
        JTextField codFascicoloField2 = new JTextField(5);
        JLabel numeroPaginaLabel2 = new JLabel("Numero Pagina Problema");
        JTextField numeroPaginaField2 = new JTextField(3);
        JLabel paragrafoLabel2 = new JLabel("Paragrafo Pagina Problema");
        JTextField paragrafoField2 = new JTextField(1);
        JLabel dataEsame = new JLabel("Data (aaaa-mm-dd)");
        JTextField dataEx = new JTextField();
        JLabel codEsami = new JLabel("Codici Tipologie Esame \n (separati da ,)");
        JTextArea codEsamiField = new JTextArea();
        JButton aggiungi2 = new JButton("Aggiungi");


        JPanel examInsertGen = new JPanel();
        examInsert.setLayout(new BorderLayout());
        examInsert.add(examInsertGen, BorderLayout.NORTH);
        examInsert.add(new JPanel(), BorderLayout.CENTER);


        GroupLayout groupLayoutE = new GroupLayout(examInsertGen);
        examInsertGen.setLayout(groupLayoutE);
        groupLayoutE.setAutoCreateGaps(true);
        groupLayoutE.setAutoCreateContainerGaps(true);

        groupLayoutE.setHorizontalGroup(groupLayoutE.createSequentialGroup()
                .addGroup(groupLayoutE.createParallelGroup()
                        .addComponent(codFascicoloLabel2)
                        .addComponent(numeroPaginaLabel2)
                        .addComponent(paragrafoLabel2)
                        .addComponent(dataEsame)
                        .addComponent(codEsami))
                .addGroup(groupLayoutE.createParallelGroup()
                        .addComponent(codFascicoloField2)
                        .addComponent(numeroPaginaField2)
                        .addComponent(paragrafoField2)
                        .addComponent(codEsamiField)
                        .addComponent(dataEx)
                        .addComponent(aggiungi2))
        );
        groupLayoutE.setVerticalGroup(groupLayoutE.createSequentialGroup()
                .addGroup(groupLayoutE.createParallelGroup()
                        .addComponent(codFascicoloLabel2)
                        .addComponent(codFascicoloField2))
                .addGroup(groupLayoutE.createParallelGroup()
                        .addComponent(numeroPaginaLabel2)
                        .addComponent(numeroPaginaField2))
                .addGroup(groupLayoutE.createParallelGroup()
                        .addComponent(paragrafoLabel2)
                        .addComponent(paragrafoField2))
                .addGroup(groupLayoutE.createParallelGroup()
                        .addComponent(dataEsame)
                        .addComponent(dataEx))
                .addGroup(groupLayoutE.createParallelGroup()
                        .addComponent(codEsami)
                        .addComponent(codEsamiField))
                .addComponent(aggiungi2)
        );

        aggiungi2.addActionListener(e -> {
            try {
                new EsameDAO().insertEsame(Integer.parseInt(codFascicoloField2.getText()), Short.parseShort(numeroPaginaField2.getText()),
                        paragrafoField2.getText(), LocalDate.parse(dataEx.getText()),
                        new ArrayList<>(Arrays.asList(codEsamiField.getText().split(","))));
                JOptionPane.showMessageDialog(this, "L'inserimento è avvenuto correttamente");
            } catch (DataAccessException data) {
                Throwable cause = data.getCause();
                if (cause instanceof SQLException) {
                    JOptionPane.showMessageDialog(this, "Errore nell'inserimento. Ricontrollare che i campi siano stati riempiti correttamente");
                }
            } catch (NumberFormatException numb) {
                JOptionPane.showMessageDialog(this, "Errore nell'inserimento. Ricontrollare che i campi siano stati riempiti correttamente");
            }
        });
        //groupLayoutE
/// /////////////////////////////////////////////////////////////
        //HOSPITALIZATION INSERT PANEL

        JLabel codFascicoloLabel3 = new JLabel("Codice Fascicolo");
        JTextField codFascicoloField3 = new JTextField(5);
        JLabel numeroPaginaLabel3 = new JLabel("Numero Pagina Problema");
        JTextField numeroPaginaField3 = new JTextField(3);
        JLabel paragrafoLabel3 = new JLabel("Paragrafo Pagina ");
        JTextField paragrafoField3 = new JTextField(1);
        JTextField dataInizioField = new JTextField();
        JLabel dataInizio = new JLabel("Data Inizio (aaaa-mm-dd)");
        JTextField dataFineField = new JTextField();
        JLabel dataFine = new JLabel("Data Fine (aaaa-mm-dd)");
        JLabel ospedale =new JLabel("Ospedale");
        JTextField ospedaleField = new JTextField();
        JButton aggiungi3 = new JButton("Aggiungi");


        JPanel hospitalizationInsertGen = new JPanel();
        GroupLayout groupLayoutH = new GroupLayout(hospitalizationInsertGen);
        hospitalizationInsertGen.setLayout(groupLayoutH);
        hospitalizationInsert.setLayout(new BorderLayout());
        hospitalizationInsert.add(hospitalizationInsertGen, BorderLayout.NORTH);
        hospitalizationInsert.add(new JPanel(), BorderLayout.CENTER);
        groupLayoutH.setAutoCreateGaps(true);
        groupLayoutH.setAutoCreateContainerGaps(true);

        groupLayoutH.setHorizontalGroup(groupLayoutH.createSequentialGroup()
                .addGroup(groupLayoutH.createParallelGroup()
                        .addComponent(codFascicoloLabel3)
                        .addComponent(numeroPaginaLabel3)
                        .addComponent(paragrafoLabel3)
                        .addComponent(dataInizio)
                        .addComponent(dataFine)
                        .addComponent(ospedale))
                .addGroup(groupLayoutH.createParallelGroup()
                        .addComponent(codFascicoloField3)
                        .addComponent(numeroPaginaField3)
                        .addComponent(paragrafoField3)
                        .addComponent(dataInizioField)
                        .addComponent(dataFineField)
                        .addComponent(ospedaleField)
                        .addComponent(aggiungi3))
        );
        groupLayoutH.setVerticalGroup(groupLayoutH.createSequentialGroup()
                .addGroup(groupLayoutH.createParallelGroup()
                        .addComponent(codFascicoloLabel3)
                        .addComponent(codFascicoloField3))
                .addGroup(groupLayoutH.createParallelGroup()
                        .addComponent(numeroPaginaLabel3)
                        .addComponent(numeroPaginaField3))
                .addGroup(groupLayoutH.createParallelGroup()
                        .addComponent(paragrafoLabel3)
                        .addComponent(paragrafoField3))
                .addGroup(groupLayoutH.createParallelGroup()
                        .addComponent(dataInizio)
                        .addComponent(dataInizioField))
                .addGroup(groupLayoutH.createParallelGroup()
                        .addComponent(dataFine)
                        .addComponent(dataFineField))
                .addGroup(groupLayoutH.createParallelGroup()
                        .addComponent(ospedale)
                        .addComponent(ospedaleField))
                .addComponent(aggiungi3)
        );

        aggiungi3.addActionListener(e -> {
            try {
                new RicoveroDAO().insertRicovero(Integer.parseInt(codFascicoloField3.getText()),
                        Short.parseShort(numeroPaginaField3.getText()), paragrafoField3.getText(), LocalDate.parse(dataInizioField.getText()),
                        LocalDate.parse(dataFineField.getText()), ospedaleField.getText());
                JOptionPane.showMessageDialog(this, "L'inserimento è avvenuto correttamente");
            } catch (DataAccessException data) {
                Throwable cause = data.getCause();
                if (cause instanceof SQLException) {
                    JOptionPane.showMessageDialog(this, "Errore nell'inserimento. Ricontrollare che i campi siano stati riempiti correttamente");
                }
            } catch (NumberFormatException numb) {
                JOptionPane.showMessageDialog(this, "Errore nell'inserimento. Ricontrollare che i campi siano stati riempiti correttamente");
            }
        });

        comboBoxInsertion.addActionListener(e -> {
            String selected = (String) comboBoxInsertion.getSelectedItem();
            CardLayout cl = (CardLayout) cardInsert.getLayout();
            cl.show(cardInsert, selected);
            cardInsert.revalidate();
            cardInsert.repaint();
        });



        checkFascicolo.addActionListener( e -> {
            if (checkFascicolo.isSelected()) {
                problemInsert.add(wrapper, BorderLayout.CENTER);
                problemInsert.revalidate();
                problemInsert.repaint();
                JOptionPane.showMessageDialog(this,
                        "Se non è presente il fascicolo è necessario inserire i dati dell'animale richiesti nella sezione d'inserimento del problema");
                infoIns.setEnabled(false);
                codFascicoloLabel.setEnabled(false);
                numeroPagina.setEnabled(false);
                codFascicoloField.setEditable(false);
                numPagina.setEditable(false);
                codFascicoloField2.setEditable(false);
                numeroPaginaField2.setEditable(false);
                paragrafoField2.setEditable(false);
                dataEx.setEditable(false);
                codFascicoloLabel2.setEnabled(false);
                numeroPaginaLabel2.setEnabled(false);
                paragrafoLabel2.setEnabled(false);
                dataEsame.setEnabled(false);
                codEsami.setEnabled(false);
                codEsamiField.setEditable(false);
                codFascicoloField3.setEditable(false);
                numeroPaginaField3.setEditable(false);
                paragrafoField3.setEditable(false);
                codFascicoloLabel3.setEnabled(false);
                numeroPaginaLabel3.setEnabled(false);
                paragrafoLabel3.setEnabled(false);
                dataInizioField.setEditable(false);
                dataFineField.setEditable(false);
                ospedaleField.setEditable(false);
                dataInizio.setEnabled(false);
                dataFine.setEnabled(false);
                ospedale.setEnabled(false);
                aggiungi2.setEnabled(false);
                aggiungi3.setEnabled(false);
            } else {
                problemInsert.remove(wrapper);
                problemInsert.revalidate();
                problemInsert.repaint();
                infoIns.setEnabled(true);
                codFascicoloLabel.setEnabled(true);
                numeroPagina.setEnabled(true);
                codFascicoloField.setEditable(true);
                numPagina.setEditable(true);
                codFascicoloField2.setEditable(true);
                codFascicoloLabel2.setEnabled(true);
                numeroPaginaField2.setEditable(true);
                numeroPaginaLabel2.setEnabled(true);
                paragrafoField2.setEditable(true);
                paragrafoLabel2.setEnabled(true);
                dataEx.setEditable(true);
                dataEsame.setEnabled(true);
                codEsamiField.setEditable(true);
                codEsami.setEnabled(true);
                codFascicoloField3.setEditable(true);
                numeroPaginaField3.setEditable(true);
                paragrafoField3.setEditable(true);
                dataInizioField.setEditable(true);
                dataFineField.setEditable(true);
                ospedaleField.setEditable(true);
                codFascicoloLabel3.setEnabled(true);
                numeroPaginaLabel3.setEnabled(true);
                paragrafoLabel3.setEnabled(true);
                dataInizio.setEnabled(true);
                dataFine.setEnabled(true);
                ospedale.setEnabled(true);
                aggiungi2.setEnabled(true);
                aggiungi3.setEnabled(true);
            }
        });

/// ///////////////////////////////

        //DELETION PAGE

        JPanel deletePanel = new JPanel(new BorderLayout());
        JPanel deletePanelGen = new JPanel();
        JPanel deletePanelMore = new JPanel(new BorderLayout());

        JScrollPane scrollDelete = new JScrollPane(deletePanel);
        scrollDelete.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollDelete.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

        JLabel codFascicoloLabel4 = new JLabel("Codice Fascicolo");
        JTextField codFascicoloField4 = new JTextField(5);
        JLabel numeroPaginaLabel4 = new JLabel("Numero Pagina");
        JTextField numeroPaginaField4 = new JTextField(3);
        JLabel paragrafoLabel4 = new JLabel("Paragrafo Pagina (opzionale)");
        JTextField paragrafoField4 = new JTextField();
        JLabel info = new JLabel("Se si desidera eliminare un'intera pagina, comprensiva" +
                " dei relativi problemi, esami e ricoveri, allora non si inserisca il paragrafo");
        JButton cancella = new JButton("Elimina");

        deletePanel.add(info, BorderLayout.NORTH);
        deletePanelMore.add(deletePanelGen, BorderLayout.NORTH);
        deletePanelMore.add(new JPanel(), BorderLayout.CENTER);
        deletePanel.add(deletePanelMore, BorderLayout.CENTER);

        GroupLayout deleteLayout = new GroupLayout(deletePanelGen);
        deletePanelGen.setLayout(deleteLayout);

        deleteLayout.setAutoCreateGaps(true);
        deleteLayout.setAutoCreateContainerGaps(true);

        deleteLayout.setHorizontalGroup(deleteLayout.createSequentialGroup()
                .addGroup(deleteLayout.createParallelGroup()
                        .addComponent(codFascicoloLabel4)
                        .addComponent(numeroPaginaLabel4)
                        .addComponent(paragrafoLabel4))
                .addGroup(deleteLayout.createParallelGroup()
                        .addComponent(codFascicoloField4)
                        .addComponent(numeroPaginaField4)
                        .addComponent(paragrafoField4)
                        .addComponent(cancella))
        );
        deleteLayout.setVerticalGroup(deleteLayout.createSequentialGroup()
                .addGroup(deleteLayout.createParallelGroup()
                        .addComponent(codFascicoloLabel4)
                        .addComponent(codFascicoloField4))
                .addGroup(deleteLayout.createParallelGroup()
                        .addComponent(numeroPaginaLabel4)
                        .addComponent(numeroPaginaField4))
                .addGroup(deleteLayout.createParallelGroup()
                        .addComponent(paragrafoLabel4)
                        .addComponent(paragrafoField4))
                .addComponent(cancella)
        );

        cancella.addActionListener(e -> {
            try {
                if (!paragrafoField4.getText().isEmpty()) {
                    new PaginaDAO().deleteSinglePar(Integer.parseInt(codFascicoloField4.getText()),
                            Short.parseShort(numeroPaginaField4.getText()), paragrafoField4.getText());
                } else {
                    new PaginaDAO().deletePaginaEffettiva(Integer.parseInt(codFascicoloField4.getText()),
                            Short.parseShort(numeroPaginaField4.getText()));
                }
                JOptionPane.showMessageDialog(this, "L'eliminazione è avvenuta correttamente");

            } catch (DataAccessException data) {
                Throwable cause = data.getCause();
                if (cause instanceof SQLException) {
                    JOptionPane.showMessageDialog(this, "Errore nell'inserimento. Ricontrollare che i campi siano stati riempiti correttamente");
                }
            } catch (NumberFormatException numb) {
                JOptionPane.showMessageDialog(this, "Errore nell'inserimento. Ricontrollare che i campi siano stati riempiti correttamente");
            }
        });

/// /////////////////////////
        //SEARCH PAGE
        JPanel searchPanel = new JPanel(new BorderLayout());
        JPanel searchPanelMore = new JPanel(new BorderLayout());
        JPanel searchPanelGen = new JPanel();
        JScrollPane searchScroll = new JScrollPane(searchPanel);
        searchScroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        searchScroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);

        JLabel codFascicoloLabel5 = new JLabel("Codice Fascicolo");
        JTextField codFascicoloField5 = new JTextField(5);
        JLabel numeroPaginaLabel5 = new JLabel("Numero Pagina (opzionale)");
        JTextField numeroPaginaField5 = new JTextField(3);
        JLabel paragrafoLabel5 = new JLabel("Paragrafo Pagina (opzionale)");
        JTextField paragrafoField5 = new JTextField();
        JLabel info2 = new JLabel("<html> Se si desidera cercare più pagine o più paragrafi" +
                " di una stessa pagina<br>  basta ignorare i campi di Numero Pagina e/o Paragrafo Pagina</html>");
        JButton search = new JButton("Cerca");


        JPanel searchPanelMoreMore = new JPanel(new BorderLayout());
        searchPanel.add(searchPanelMoreMore, BorderLayout.NORTH);
        searchPanelMoreMore.add(info2, BorderLayout.NORTH);
        searchPanelMoreMore.add(new JPanel(), BorderLayout.EAST);
        searchPanelMoreMore.add(searchPanelMore, BorderLayout.CENTER);
        searchPanelMore.add(searchPanelGen, BorderLayout.NORTH);

        GroupLayout searchLayout = new GroupLayout(searchPanelGen);
        searchPanelGen.setLayout(searchLayout);
        searchLayout.setAutoCreateGaps(true);
        searchLayout.setAutoCreateContainerGaps(true);

        searchLayout.setHorizontalGroup(searchLayout.createSequentialGroup()
                .addGroup(searchLayout.createParallelGroup()
                        .addComponent(codFascicoloLabel5)
                        .addComponent(numeroPaginaLabel5)
                        .addComponent(paragrafoLabel5))
                .addGroup(searchLayout.createParallelGroup()
                        .addComponent(codFascicoloField5)
                        .addComponent(numeroPaginaField5)
                        .addComponent(paragrafoField5)
                        .addComponent(search))
        );
        searchLayout.setVerticalGroup(searchLayout.createSequentialGroup()
                .addGroup(searchLayout.createParallelGroup()
                        .addComponent(codFascicoloLabel5)
                        .addComponent(codFascicoloField5))
                .addGroup(searchLayout.createParallelGroup()
                        .addComponent(numeroPaginaLabel5)
                        .addComponent(numeroPaginaField5))
                .addGroup(searchLayout.createParallelGroup()
                        .addComponent(paragrafoLabel5)
                        .addComponent(paragrafoField5))
                .addComponent(search)
        );


        JPanel tablePanel = new JPanel();
        BoxLayout box = new BoxLayout(tablePanel, BoxLayout.Y_AXIS);
        tablePanel.setLayout(box);
        JScrollPane tableScroll = new JScrollPane(tablePanel);
        tableScroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        tableScroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        searchPanel.add(tableScroll);
        selectPanel.add(new JPanel(), BorderLayout.SOUTH);
        JTable tablePr = new JTable();
        JScrollPane scrollPanePr = new JScrollPane(tablePr);
        scrollPanePr.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPanePr.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPanePr.setPreferredSize(new Dimension(600, 50));
        JTable tableEx = new JTable();
        JScrollPane scrollPaneEx = new JScrollPane(tableEx);
        scrollPaneEx.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPaneEx.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPaneEx.setPreferredSize(new Dimension(600, 50));
        JTable tableHo = new JTable();
        JScrollPane scrollPaneHo = new JScrollPane(tableHo);
        scrollPaneHo.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPaneHo.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPaneHo.setPreferredSize(new Dimension(600, 50));
        tablePanel.add(scrollPanePr);
        tablePanel.add(scrollPaneEx);
        tablePanel.add(scrollPaneHo);

        search.addActionListener(e -> {
            try {
                Integer codFas = Integer.parseInt(codFascicoloField5.getText());
                Short numPag = numeroPaginaField5.getText().isEmpty() ? null : Short.parseShort(numeroPaginaField5.getText());
                String parag = paragrafoField5.getText().isEmpty() ? null : paragrafoField5.getText();

                String[] colonnePr = {"Codice Fascicolo", "Numero Pagina", "Paragrafo", "Nome", "Descrizione",
                    "Tipo Curabile", "In Cura", "Area Disabilità 1", "Area Disabilità 2", "Numero Pagina Ricovero",
                    "Paragrafo Ricovero", "Numero Pagina Esame", "Paragrafo Esame"};
                String[] colonneEx = {"Codice Fascicolo", "Numero Pagina", "Paragrafo", "Data Esame"};
                String[] colonneHo = {"Codice Fascicolo", "Numero Pagina", "Paragrafo", "Data Inizio Ricovero",
                    "Data Fine Ricovero", "Ospedale"};

                DefaultTableModel modelPr = new DefaultTableModel(colonnePr, 0);
                DefaultTableModel modelEx = new DefaultTableModel(colonneEx, 0);
                DefaultTableModel modelHo = new DefaultTableModel(colonneHo, 0);

                List<ProblemaRecord> records = new PaginaDAO().getProblemsFasc(codFas,
                        numPag, parag);
                records.forEach(record -> {
                    Object[] row = {
                            record.getCodFascicolo(),
                            record.getNumero(),
                            record.getParagrafo(),
                            record.getNome(),
                            record.getDescrizione(),
                            record.getTipoCurabile(),
                            record.getInCura(),
                            record.getArea_1(),
                            record.getArea_2(),
                            record.getRNumero(),
                            record.getRParagrafo(),
                            record.getENumero(),
                            record.getEParagrafo()
                    };
                    modelPr.addRow(row);
                });

                List<EsameRecord> esami = new PaginaDAO().getExamFasc(codFas, numPag, parag);
                esami.forEach(record -> {
                    Object[] row = {
                            record.getCodFascicolo(),
                            record.getNumero(),
                            record.getParagrafo(),
                            record.getDataEsame()
                    };
                    modelEx.addRow(row);
                });

                List<RicoveroRecord> ricoveri = new PaginaDAO().getRicovFasc(codFas, numPag, parag);
                ricoveri.forEach(record -> {
                    Object[] row = {
                            record.getCodFascicolo(),
                            record.getNumero(),
                            record.getParagrafo(),
                            record.getDataInizio(),
                            record.getDataFine(),
                            record.getOspedale()
                    };
                    modelHo.addRow(row);
                });

                tablePr.setModel(modelPr);
                tableEx.setModel(modelEx);
                tableHo.setModel(modelHo);

                searchPanel.revalidate();
                searchPanel.repaint();

                JOptionPane.showMessageDialog(this, "La ricerca è avvenuta correttamente");

            } catch (DataAccessException data) {
                Throwable cause = data.getCause();
                if (cause instanceof SQLException) {
                    JOptionPane.showMessageDialog(this, "Errore nell'inserimento. Ricontrollare che i campi siano stati riempiti correttamente");
                }
            } catch (Exception numb) {
                JOptionPane.showMessageDialog(this, "Errore nell'inserimento. Ricontrollare che i campi siano stati riempiti correttamente");
            }
        });

/// /////////////////////////////
///  //////////////////////////
///   /////////////////////////
           //UPDATE PAGE

        JPanel updatePanel = new UpdatePaginaPanel();
        

/// ///////////////////////////

        cards.add(insertPanel, "Inserisci Pagina");
        cards.add(scrollDelete, "Rimuovi Pagina");
        cards.add(searchScroll, "Visualizza Pagine");
        cards.add(selectPanel, "--Seleziona--");
        cards.add(updatePanel, "Aggiorna Pagina");

        ((CardLayout) cards.getLayout()).show(cards, "--Seleziona--");

        // Listener sulla comboBox
        comboBox.addActionListener(e -> {
            String selezionato = (String) comboBox.getSelectedItem();
            CardLayout cl = (CardLayout) cards.getLayout();
            cl.show(cards, selezionato);
            cards.revalidate();
            cards.repaint();
        });

        // Esempio: aggiungo comboBox in alto e le card al centro
        add(comboBox, BorderLayout.NORTH);
        add(cards, BorderLayout.CENTER);

    }


}
