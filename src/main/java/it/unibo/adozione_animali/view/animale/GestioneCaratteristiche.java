package it.unibo.adozione_animali.view.animale;

import javax.swing.*;

import it.unibo.adozione_animali.view.animale.caratteristiche.Aggiornamento;
import it.unibo.adozione_animali.view.animale.caratteristiche.Cancellazione;
import it.unibo.adozione_animali.view.animale.caratteristiche.Inserimento;

import java.awt.*;


public class GestioneCaratteristiche extends JPanel{
    private CardLayout cardLayout;
    private JPanel rightPanel;

    private Inserimento inser;
    private Aggiornamento aggior;
    private Cancellazione canc;

    public GestioneCaratteristiche() {
        setLayout(new BorderLayout());

        // --- Colonna sinistra ---
        JPanel leftPanel = new JPanel();
        leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));
        leftPanel.setPreferredSize(new Dimension(200, 0));

        JLabel title = new JLabel("Operazioni");
        title.setFont(new Font("Arial", Font.BOLD, 25));
        title.setAlignmentX(Component.CENTER_ALIGNMENT);
        title.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        leftPanel.add(title);

        // Funzione helper per creare pulsanti uniformi
        JButton inserimento = createMenuButton("Inserimento");
        JButton update = createMenuButton("Update");
        JButton cancellazione = createMenuButton("Cancellazione");

        leftPanel.add(inserimento);
        leftPanel.add(update);
        leftPanel.add(cancellazione);

        add(leftPanel, BorderLayout.WEST);

        // --- Colonna destra ---
        cardLayout = new CardLayout();
        rightPanel = new JPanel(cardLayout);

        inser = new Inserimento();
        //inser.setController(new InserimentoCaratteristicaController(new InserimentoModel(),inser));
        aggior = new Aggiornamento();
        canc = new Cancellazione();

        rightPanel.add(inser, "add");
        rightPanel.add(aggior, "update");
        rightPanel.add(canc, "delete");

        add(rightPanel, BorderLayout.CENTER);

        // --- Azioni pulsanti ---
        inserimento.addActionListener(e -> cardLayout.show(rightPanel, "add"));
        update.addActionListener(e -> cardLayout.show(rightPanel, "update"));
        cancellazione.addActionListener(e -> cardLayout.show(rightPanel, "delete"));
    }

    // Metodo per pulsanti larghi uguali
    private JButton createMenuButton(String text) {
        JButton btn = new JButton("<html><center>"+ text + "<center><html>");
        btn.setAlignmentX(Component.CENTER_ALIGNMENT);
        btn.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40)); // larghezza completa, altezza fissa
        btn.setFont(new Font("Arial", Font.BOLD, 16));
        btn.setFocusPainted(false); // rimuove il bordo di focus blu
        btn.setBorderPainted(false); // rimuove il bordo standard
        btn.setOpaque(true);
        return btn;
    }

    public Inserimento getInserimentoView() {
        return inser;
    }

    public Aggiornamento getAggiornamentoView() {
        return aggior;
    }

    public Cancellazione getCancellazioneView() {
        return canc;
    }
}


