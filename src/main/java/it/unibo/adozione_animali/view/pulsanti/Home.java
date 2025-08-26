package it.unibo.adozione_animali.view.pulsanti;

import it.unibo.adozione_animali.view.ImageViewerPanel;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;

public class Home extends JPanel{

    public Home() {
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createTitledBorder(
                        BorderFactory.createEtchedBorder(),
                        "Home",
                        TitledBorder.CENTER,
                        TitledBorder.TOP
                ),
                BorderFactory.createEmptyBorder(25, 10, 10, 10)
        ));

        JLabel welcome = new JLabel("Benvenuto!");
        this.add(welcome, BorderLayout.NORTH);
        welcome.setHorizontalAlignment(SwingConstants.CENTER);
        Font font = welcome.getFont();
        Font newFont = font.deriveFont(40f);
        welcome.setFont(newFont);
        JTextArea text = new JTextArea("Questa applicazione è stata realizzata con lo scopo " +
                "di facilitare la gestione dei centri di adozione da parte del" +
                " suo personale. Viene reso possibile l'inserimento, la cancellazione, l'aggionamento, " +
                "la ricerca e la visualizzazione di vari elementi legati ai centri. " + "Seleziona nella barra nel menù l'opzione desiderata e inizia il lavoro!\n\n\n");

        text.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createEtchedBorder(),
                BorderFactory.createEmptyBorder(25, 10, 10, 10)
        ));
        Font textFont = text.getFont();
        Font newTextFont = textFont.deriveFont(18f);
        text.setFont(newTextFont);
        text.setLineWrap(true);
        text.setWrapStyleWord(true);
        text.setEditable(false);
        text.setOpaque(false);
        text.setFocusable(false);
        Dimension d = text.getPreferredSize();
        text.setPreferredSize(d);

        JPanel panel = new JPanel();
        BoxLayout boxLayout = new BoxLayout(panel, BoxLayout.Y_AXIS);
        panel.setLayout(boxLayout);
        panel.add(text);
        panel.add(new ImageViewerPanel("/animali1.png"));
        this.add(panel, BorderLayout.CENTER);
    }

}
