package it.unibo.adozione_animali.view;

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
        String[] options = {"--Seleziona--", "Inserisci Pagina", "Rimuovi Pagina", "Visualizza Pagine"};
        comboBox = new JComboBox<>(options);

        JPanel selectPanel = new JPanel();

        JLabel codFascicoloLabel = new JLabel("Codice Fascicolo:");
        JTextField codFascicoloField = new JTextField(15);
        JLabel numeroPaginaLabel = new JLabel("Numero Pagina:");
        JTextField numeroPaginaField = new JTextField(3);

        //INSERTION PANEL
        JPanel insertPanel = new JPanel(new BorderLayout());
        JPanel cardInsert = new JPanel(new CardLayout());
        insertPanel.add(cardInsert, BorderLayout.CENTER);
        JPanel problemInsert = new JPanel();
        JPanel examInsert = new JPanel();
        JPanel hospitalizationInsert = new JPanel();

        cardInsert.add(problemInsert, "Nuovo Problema");
        cardInsert.add(examInsert, "Nuovo Esame");
        cardInsert.add(hospitalizationInsert, "Nuovo Ricovero");
        ((CardLayout) cardInsert.getLayout()).show(cardInsert, "Nuovo Problema");


        String[] optionsInsertion = {"Nuovo Problema", "Nuovo Esame", "Nuovo Ricovero"};
        JComboBox<String> comboBoxInsertion = new JComboBox<>(optionsInsertion);
        insertPanel.add(comboBoxInsertion, BorderLayout.NORTH);

        comboBoxInsertion.addActionListener(e -> {
            String selezionato = (String) comboBoxInsertion.getSelectedItem();
            System.out.println(selezionato);
            CardLayout cl = (CardLayout) cardInsert.getLayout();
            cl.show(cardInsert, selezionato);
            cards.revalidate();
            cards.repaint();
        });

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


        checkFascicolo.addActionListener( e -> {
            if (checkFascicolo.isSelected()) {
                problemInsert.add(wrapper, BorderLayout.CENTER);
                problemInsert.revalidate();
                problemInsert.repaint();
                JOptionPane.showMessageDialog(null,
                        "Se non è presente il fascicolo è necessario inserire i dati dell'animale richiesti nella sezione d'inserimento del problema");
            } else {
                problemInsert.remove(wrapper);
                problemInsert.revalidate();
                problemInsert.repaint();
            }
        });


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



        //PROBLEM INSERT PANEL
        problemInsert.setLayout(new BorderLayout());
        problemInsert.add(check, BorderLayout.PAGE_START);
        JPanel problemInsertGen = new JPanel();
        problemInsert.add(problemInsertGen, BorderLayout.PAGE_END);
        GroupLayout groupLayoutP = new GroupLayout(problemInsertGen);
        problemInsertGen.setLayout(groupLayoutP);
        groupLayoutP.setAutoCreateGaps(true);
        groupLayoutP.setAutoCreateContainerGaps(true);

        groupLayoutP.setHorizontalGroup(
                groupLayoutP.createSequentialGroup()
                        .addGroup(groupLayoutP.createParallelGroup()
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
                        )
                        .addGroup(groupLayoutP.createParallelGroup()
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
                        )
        );
        groupLayoutP.setVerticalGroup(
                groupLayoutP.createSequentialGroup()
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

        //EXAM INSERT PANEL
        GroupLayout groupLayoutE = new GroupLayout(examInsert);
        examInsert.setLayout(groupLayoutE);
        groupLayoutE.setAutoCreateGaps(true);
        groupLayoutE.setAutoCreateContainerGaps(true);
        //groupLayoutE

        //HOSPITALIZATION INSERT PANEL
        GroupLayout groupLayoutH = new GroupLayout(hospitalizationInsert);
        hospitalizationInsert.setLayout(groupLayoutH);
        groupLayoutH.setAutoCreateGaps(true);
        groupLayoutH.setAutoCreateContainerGaps(true);


        comboBoxInsertion.addActionListener(e -> {
            String selected = (String) comboBox.getSelectedItem();
            System.out.println(selected);
            CardLayout cl = (CardLayout) cardInsert.getLayout();
            cl.show(cardInsert, selected);
            cards.revalidate();
            cards.repaint();
        });

//        insertPanel.setLayout(new GridLayout(2, 2));
//        insertPanel.setBackground(Color.PINK);
//        JLabel codFascicoloLabel = new JLabel("Codice Fascicolo:");
//        JTextField codFascicoloField = new JTextField(15);
//        JLabel numeroPaginaLabel = new JLabel("Numero Pagina:");
//        JTextField numeroPaginaField = new JTextField(3);
//        insertPanel.add(codFascicoloLabel);
//        insertPanel.add(codFascicoloField);
//        insertPanel.add(numeroPaginaLabel);
//        insertPanel.add(numeroPaginaField);


        JPanel deletePanel = new JPanel();
        JPanel searchPanel = new JPanel();

        cards.add(insertPanel, "Inserisci Pagina");
        cards.add(deletePanel, "Rimuovi Pagina");
        cards.add(searchPanel, "Visualizza Pagine");
        cards.add(selectPanel, "--Seleziona--");

        ((CardLayout) cards.getLayout()).show(cards, "--Seleziona--");

        // Listener sulla comboBox
        comboBox.addActionListener(e -> {
            String selezionato = (String) comboBox.getSelectedItem();
            System.out.println(selezionato);
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
