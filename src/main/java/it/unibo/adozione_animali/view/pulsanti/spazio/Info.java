package it.unibo.adozione_animali.view.pulsanti.spazio;

import javax.swing.JLabel;
import javax.swing.JPanel;

import it.unibo.adozione_animali.util.ColorUtils;

public class Info extends JPanel{

    public Info() {
        setBackground(ColorUtils.fromHex("6B82FF"));
        add(new JLabel("Qui ci sono le info dei centri"));
    }

}
