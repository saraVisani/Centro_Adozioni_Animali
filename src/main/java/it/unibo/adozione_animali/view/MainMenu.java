package it.unibo.adozione_animali.view;

import javax.swing.*;

import it.unibo.adozione_animali.view.animale.*;
import it.unibo.adozione_animali.view.personale.*;
import it.unibo.adozione_animali.view.pulsanti.*;
import it.unibo.adozione_animali.view.razza.*;
import it.unibo.adozione_animali.view.richiedenti.*;
import it.unibo.adozione_animali.view.statistiche.*;

import java.awt.*;

public class MainMenu {

    private Home homePanel;
    private AnimaliGenerali infoPanel;
    private AnimaliRicerca ricercaPanel;
    private StatisticheUpdate aggiornaPanel;
    private FascicoloPanel fascicoloPanel;
    private GestioneCaratteristiche gestioneCarPanel;
    private GestioneComponentiPanel gestioneCompPanel;
    private GestioneSpecifiche gestioneRazzeSpecPanel;
    private GestioneRichiestePanel gestioneRichPanel;
    private GestioneTurniTaskPanel gestioneTurniPanel;
    private PersonaleGenerali infoPersonalePanel;
    private RazzaGenerali infoRazzaPanel;
    private StatisticheRicerca ricercaStatPanel;
    private RichiedentiGenerali infoRichiedentiPanel;
    private Centri centriPanel;
    private Spazio spaziPanel;

    public MainMenu() {
        JFrame frame = new JFrame("Gestione Centri Adozione");
        frame.setSize(620, 520);
        frame.setMinimumSize(new Dimension(620, 520));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Pannello in alto con pulsante Home e menu
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.X_AXIS)); // allinea a sinistra

        JButton homeButton = new JButton("Home");
        topPanel.add(homeButton);

        // Menu a tendina
        JMenu animaliMenu = new JMenu("Animali");
        JMenu centroMenu = new JMenu("Centri");
        JMenu spazioMenu = new JMenu("Spazi");
        JMenu richiedentiMenu = new JMenu("Richiedenti");
        JMenu personaleMenu = new JMenu("Personale");
        JMenu razzaMenu = new JMenu("Razze");
        JMenu statisticheMenu = new JMenu("Statistiche");

        JMenuItem centri = new JMenuItem("Centri");
        JMenuItem spazi = new JMenuItem("Spazi");
        JMenuItem infoGenerali = new JMenuItem("Informazioni Generali Animali");
        JMenuItem ricercaAnimali = new JMenuItem("Ricerca Animali");
        JMenuItem aggiornaStatistiche = new JMenuItem("Aggiorna Statistiche");
        JMenuItem fascicoloSanitario = new JMenuItem("Fascicolo Sanitario");
        JMenuItem gestioneCaratteristiche = new JMenuItem("Gestione Caratteristiche");
        JMenuItem gestioneComponenti = new JMenuItem("Gestione Componenti");
        JMenuItem gestioneRazzeSpecifiche = new JMenuItem("Gestione Specifiche di Razza");
        JMenuItem gestioneRichieste = new JMenuItem("Gestione Richieste");
        JMenuItem gestioneTurniTask = new JMenuItem("Gestione Turni e Task");
        JMenuItem personaleInfo = new JMenuItem("Personale Informazioni Generali");
        JMenuItem razzaInfo = new JMenuItem("Informazioni Generali Razza");
        JMenuItem ricercaStatistiche = new JMenuItem("Ricerca Statistiche");
        JMenuItem richiedentiInfo = new JMenuItem("Informazioni Generali Richiedenti");
        animaliMenu.add(infoGenerali);
        animaliMenu.add(ricercaAnimali);
        animaliMenu.add(gestioneCaratteristiche);
        animaliMenu.add(gestioneComponenti);
        animaliMenu.add(fascicoloSanitario);
        razzaMenu.add(razzaInfo);
        razzaMenu.add(gestioneRazzeSpecifiche);
        richiedentiMenu.add(richiedentiInfo);
        richiedentiMenu.add(gestioneRichieste);
        personaleMenu.add(personaleInfo);
        personaleMenu.add(gestioneTurniTask);
        statisticheMenu.add(aggiornaStatistiche);
        statisticheMenu.add(ricercaStatistiche);
        centroMenu.add(centri);
        spazioMenu.add(spazi);


        JMenuBar menuBar = new JMenuBar();
        menuBar.add(centroMenu);
        menuBar.add(spazioMenu);
        menuBar.add(animaliMenu);
        menuBar.add(razzaMenu);
        menuBar.add(richiedentiMenu);
        menuBar.add(personaleMenu);
        menuBar.add(statisticheMenu);
        topPanel.add(menuBar); // aggiunge menu accanto a Home

        // Pannello centrale con CardLayout
        JPanel cards = new JPanel(new CardLayout());
        homePanel = new Home();
        homePanel.add(new JLabel("Pagina Home"));
        infoPanel = new AnimaliGenerali();
        infoPanel.add(new JLabel("Informazioni Generali Animali"));
        ricercaPanel = new AnimaliRicerca();
        ricercaPanel.add(new JLabel("Ricerca Animali"));
        aggiornaPanel = new StatisticheUpdate();
        aggiornaPanel.add(new JLabel("Aggiorna Statistiche"));
        fascicoloPanel = new FascicoloPanel();
        gestioneCarPanel = new GestioneCaratteristiche();
        gestioneCarPanel.add(new JLabel("Gestione Caratteristiche"));
        gestioneCompPanel = new GestioneComponentiPanel();
        gestioneRazzeSpecPanel = new GestioneSpecifiche();
        gestioneRazzeSpecPanel.add(new JLabel("Gestione Specifiche di Razza"));
        gestioneRichPanel = new GestioneRichiestePanel();
        gestioneTurniPanel = new GestioneTurniTaskPanel();
        infoPersonalePanel = new PersonaleGenerali();
        infoRazzaPanel = new RazzaGenerali();
        infoRazzaPanel.add(new JLabel("Informazioni Generali Razza"));
        ricercaStatPanel = new StatisticheRicerca();
        ricercaStatPanel.add(new JLabel("Ricerca Statistiche"));
        infoRichiedentiPanel = new RichiedentiGenerali();
        centriPanel = new Centri();
        centriPanel.add(new JLabel("Centri"));
        spaziPanel = new Spazio();
        spaziPanel.add(new JLabel("Spazi"));

        cards.add(homePanel, "home");
        cards.add(infoPanel, "info");
        cards.add(ricercaPanel, "ricerca");
        cards.add(aggiornaPanel, "aggiornamento");
        cards.add(fascicoloPanel, "fascicolo");
        cards.add(gestioneCarPanel, "gestioneCa");
        cards.add(gestioneCompPanel, "gestioneCo");
        cards.add(gestioneRazzeSpecPanel, "gestioneRa");
        cards.add(gestioneRichPanel, "gestioneR");
        cards.add(gestioneTurniPanel, "gestioneT");
        cards.add(infoPersonalePanel, "infoP");
        cards.add(infoRazzaPanel, "infoRa");
        cards.add(ricercaStatPanel, "ricercaS");
        cards.add(infoRichiedentiPanel, "infoR");
        cards.add(centriPanel, "centri");
        cards.add(spaziPanel, "spazi");


        // Eventi
        homeButton.addActionListener(e -> {
            CardLayout cl = (CardLayout) (cards.getLayout());
            cl.show(cards, "home");
        });

        infoGenerali.addActionListener(e -> {
            CardLayout cl = (CardLayout) (cards.getLayout());
            cl.show(cards, "info");
        });

        ricercaAnimali.addActionListener(e -> {
            CardLayout cl = (CardLayout) (cards.getLayout());
            cl.show(cards, "ricerca");
        });

        gestioneComponenti.addActionListener(e -> {
            CardLayout cl = (CardLayout) (cards.getLayout());
            cl.show(cards, "gestioneCo");
        });

        gestioneCaratteristiche.addActionListener(e -> {
            CardLayout cl = (CardLayout) (cards.getLayout());
            cl.show(cards, "gestioneCa");
        });

        fascicoloSanitario.addActionListener(e -> {
            CardLayout cl = (CardLayout) (cards.getLayout());
            cl.show(cards, "fascicolo");
        });

        razzaInfo.addActionListener(e -> {
            CardLayout cl = (CardLayout) (cards.getLayout());
            cl.show(cards, "infoRa");
        });

        gestioneRazzeSpecifiche.addActionListener(e -> {
            CardLayout cl = (CardLayout) (cards.getLayout());
            cl.show(cards, "gestioneS");
        });

        richiedentiInfo.addActionListener(e -> {
            CardLayout cl = (CardLayout) (cards.getLayout());
            cl.show(cards, "infoR");
        });

        gestioneRichieste.addActionListener(e -> {
            CardLayout cl = (CardLayout) (cards.getLayout());
            cl.show(cards, "gestioneR");
        });

        personaleInfo.addActionListener(e -> {
            CardLayout cl = (CardLayout) (cards.getLayout());
            cl.show(cards, "infoP");
        });

        gestioneTurniTask.addActionListener(e -> {
            CardLayout cl = (CardLayout) (cards.getLayout());
            cl.show(cards, "gestioneT");
        });

        aggiornaStatistiche.addActionListener(e -> {
            CardLayout cl = (CardLayout) (cards.getLayout());
            cl.show(cards, "aggiornamento");
        });

        ricercaStatistiche.addActionListener(e -> {
            CardLayout cl = (CardLayout) (cards.getLayout());
            cl.show(cards, "ricercaS");
        });

        centri.addActionListener(e -> {
            CardLayout cl = (CardLayout) (cards.getLayout());
            cl.show(cards, "centri");
        });

        spazi.addActionListener(e -> {
            CardLayout cl = (CardLayout) (cards.getLayout());
            cl.show(cards, "spazi");
        });

        frame.setLayout(new BorderLayout());
        frame.add(topPanel, BorderLayout.NORTH);
        frame.add(cards, BorderLayout.CENTER);

        frame.setVisible(true);
    }

    public Home getHomePanel() {
        return homePanel;
    }

    public AnimaliGenerali getInfoPanel() {
        return infoPanel;
    }

    public AnimaliRicerca getRicercaPanel() {
        return ricercaPanel;
    }

    public StatisticheUpdate getAggiornaPanel() {
        return aggiornaPanel;
    }

    public FascicoloPanel getFascicoloPanel() {
        return fascicoloPanel;
    }

    public GestioneCaratteristiche getGestioneCarPanel() {
        return gestioneCarPanel;
    }

    public GestioneComponentiPanel getGestioneCompPanel() {
        return gestioneCompPanel;
    }

    public GestioneSpecifiche getGestioneRazzeSpecPanel() {
        return gestioneRazzeSpecPanel;
    }

    public GestioneRichiestePanel getGestioneRichPanel() {
        return gestioneRichPanel;
    }

    public GestioneTurniTaskPanel getGestioneTurniPanel() {
        return gestioneTurniPanel;
    }

    public PersonaleGenerali getInfoPersonalePanel() {
        return infoPersonalePanel;
    }

    public RazzaGenerali getInfoRazzaPanel() {
        return infoRazzaPanel;
    }

    public StatisticheRicerca getRicercaStatPanel() {
        return ricercaStatPanel;
    }

    public RichiedentiGenerali getInfoRichiedentiPanel() {
        return infoRichiedentiPanel;
    }

    public Centri getCentriPanel() {
        return centriPanel;
    }

    public Spazio getSpaziPanel() {
        return spaziPanel;
    }

}
