package it.unibo.adozione_animali.view.animale;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;

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

        JLabel codFascicoloLabel = new JLabel("Codice Fascicolo");
        JTextField codFascicoloField = new JTextField(5);
        JLabel numeroPaginaLabel = new JLabel("Numero Pagina");
        JTextField numeroPaginaField = new JTextField(3);
        JLabel paragrafoLabel = new JLabel("Paragrafo Pagina");
        JTextField paragrafoField = new JTextField(1);

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
        JLabel Area1 = new JLabel("Area Disabilità 1");
        JTextField area2 = new JTextField(50);
        JLabel Area2 = new JLabel("Area Disabilità 2");
        JTextField r_page_num = new JTextField(3);
        JLabel rNum = new JLabel("Numero Pagina Ricovero Associato");
        JTextField r_page_par = new JTextField(1);
        JLabel rPar = new JLabel("Paragrafo Pagina Ricovero Associato");
        JTextField e_page_num = new JTextField(3);
        JLabel eNum = new JLabel("Numero Pagina Esame Associato");
        JTextField e_page_par = new JTextField(1);
        JLabel ePar = new JLabel("Paragrafo Pagina Esame Associato");
        JLabel sintomiLabel = new JLabel("Codici dei sintomi \n (separati da ,)");
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

        groupLayoutP.setHorizontalGroup(
                groupLayoutP.createSequentialGroup()
                        .addGroup(groupLayoutP.createParallelGroup()
                                .addComponent(codFascicoloLabel)
                                .addComponent(numeroPaginaLabel)
                                .addComponent(paragrafoLabel)
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
                                .addComponent(numeroPaginaField)
                                .addComponent(paragrafoField)
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
                        .addGroup(groupLayoutP.createParallelGroup()
                                .addComponent(codFascicoloLabel)
                                .addComponent(codFascicoloField)
                        )
                        .addGroup(groupLayoutP.createParallelGroup()
                                .addComponent(numeroPaginaLabel)
                                .addComponent(numeroPaginaField)
                        )
                        .addGroup(groupLayoutP.createParallelGroup()
                                .addComponent(paragrafoLabel)
                                .addComponent(paragrafoField)
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
/// ///////////////////////////////////////////////////////////

        //EXAM INSERT PANEL

        JLabel codFascicoloLabel2 = new JLabel("Codice Fascicolo");
        JTextField codFascicoloField2 = new JTextField(5);
        JLabel numeroPaginaLabel2 = new JLabel("Numero Pagina");
        JTextField numeroPaginaField2 = new JTextField(3);
        JLabel paragrafoLabel2 = new JLabel("Paragrafo Pagina");
        JTextField paragrafoField2 = new JTextField(1);
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
                        .addComponent(codEsami))
                .addGroup(groupLayoutE.createParallelGroup()
                        .addComponent(codFascicoloField2)
                        .addComponent(numeroPaginaField2)
                        .addComponent(paragrafoField2)
                        .addComponent(codEsamiField)
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
                        .addComponent(codEsami)
                        .addComponent(codEsamiField))
                .addComponent(aggiungi2)
        );
        //groupLayoutE
/// /////////////////////////////////////////////////////////////
        //HOSPITALIZATION INSERT PANEL

        JLabel codFascicoloLabel3 = new JLabel("Codice Fascicolo");
        JTextField codFascicoloField3 = new JTextField(5);
        JLabel numeroPaginaLabel3 = new JLabel("Numero Pagina");
        JTextField numeroPaginaField3 = new JTextField(3);
        JLabel paragrafoLabel3 = new JLabel("Paragrafo Pagina");
        JTextField paragrafoField3 = new JTextField(1);
        JTextField dataInizioField = new JTextField();
        JLabel dataInizio = new JLabel("Data Inizio \n (in formato aaaa-mm-dd)");
        JTextField dataFineField = new JTextField();
        JLabel dataFine = new JLabel("Data Fine \n (opzionale)");
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
                JOptionPane.showMessageDialog(null,
                        "Se non è presente il fascicolo è necessario inserire i dati dell'animale richiesti nella sezione d'inserimento del problema");
                codFascicoloField.setEditable(false);
                numeroPaginaField.setEditable(false);
                paragrafoField.setEditable(false);
                codFascicoloField2.setEditable(false);
                numeroPaginaField2.setEditable(false);
                paragrafoField2.setEditable(false);
                codEsamiField.setEditable(false);
                codFascicoloField3.setEditable(false);
                numeroPaginaField3.setEditable(false);
                paragrafoField3.setEditable(false);
                dataInizioField.setEditable(false);
                dataFineField.setEditable(false);
                ospedaleField.setEditable(false);
                aggiungi2.setEnabled(false);
                aggiungi3.setEnabled(false);
            } else {
                problemInsert.remove(wrapper);
                problemInsert.revalidate();
                problemInsert.repaint();
                codFascicoloField.setEditable(true);
                numeroPaginaField.setEditable(true);
                paragrafoField.setEditable(true);
                codFascicoloField2.setEditable(true);
                numeroPaginaField2.setEditable(true);
                paragrafoField2.setEditable(true);
                codEsamiField.setEditable(true);
                codFascicoloField3.setEditable(true);
                numeroPaginaField3.setEditable(true);
                paragrafoField3.setEditable(true);
                dataInizioField.setEditable(true);
                dataFineField.setEditable(true);
                ospedaleField.setEditable(true);
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
        JLabel paragrafoLabel4 = new JLabel("Paragrafo Pagina*");
        JTextField paragrafoField4 = new JTextField();
        JLabel info = new JLabel("Se si desidera eliminare un'intera pagina, comprensiva" +
                " dei relativi problemi, esami e ricoveri, allora non si inserisca il paragrafo (*)");
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
        JLabel numeroPaginaLabel5 = new JLabel("Numero Pagina");
        JTextField numeroPaginaField5 = new JTextField(3);
        JLabel paragrafoLabel5 = new JLabel("Paragrafo Pagina*");
        JTextField paragrafoField5 = new JTextField();
        JLabel info2 = new JLabel("Se si desidera cercare più pagine o più paragrafi" +
                " di una stessa pagina basta ignorare i campi di Numero Pagina e/o Paragrafo Pagina");
        JButton search = new JButton("Cerca");


        searchPanel.add(info2, BorderLayout.NORTH);
        searchPanel.add(searchPanelMore, BorderLayout.CENTER);
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

        search.addActionListener(e -> {
            JPanel tablePanel = new JPanel();
            JTable table = new JTable();
            tablePanel.add(table);
            JScrollPane tableScroll = new JScrollPane(tablePanel);
            tableScroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
            tableScroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
            searchPanel.add(tableScroll, BorderLayout.CENTER);
            searchPanel.revalidate();
            searchPanel.repaint();
        });

/// /////////////////////////////
///  //////////////////////////
///   /////////////////////////
           //UPDATE PAGE

        JPanel updatePanel = new JPanel();

        JPanel cardUpdate = new JPanel(new CardLayout());
        JPanel updatePanelGen = new JPanel(new BorderLayout());
        updatePanelGen.add(cardUpdate, BorderLayout.CENTER);
        JPanel problemUpdate = new JPanel();
        JPanel examUpdate = new JPanel();
        JPanel hospitalizationUpdate = new JPanel();


        cardUpdate.add(problemUpdate, "Aggiorna Problema");
        cardUpdate.add(examUpdate, "Aggiorna Esame");
        cardUpdate.add(hospitalizationUpdate, "Aggiorna Ricovero");
        ((CardLayout) cardUpdate.getLayout()).show(cardUpdate, "Aggiorna Problema");


        String[] optionsUpdate = {"Aggiorna Problema", "Aggiorna Esame", "Aggiorna Ricovero"};
        JComboBox<String> comboBoxUpdate = new JComboBox<>(optionsUpdate);
        updatePanel.add(comboBoxUpdate, BorderLayout.NORTH);


        comboBoxUpdate.addActionListener(e -> {
            String selected = (String) comboBoxUpdate.getSelectedItem();
            CardLayout cl = (CardLayout) cardUpdate.getLayout();
            cl.show(cardUpdate, selected);
            cardInsert.revalidate();
            cardInsert.repaint();
        });

        /// ////
        //UPDATE PROBLEMA
        

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
