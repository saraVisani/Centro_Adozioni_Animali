package it.unibo.adozione_animali.view.pulsanti;

import javax.swing.*;
import javax.swing.border.TitledBorder;

import it.unibo.adozione_animali.view.pulsanti.centri.*;

import java.awt.*;

public class Centri extends JPanel{

    private CardLayout cardLayout;
    private JPanel rightPanel;
    // Dichiarazione dei pannelli come variabili di classe
    private Info infoPanel = new Info();
    private Add addPanel = new Add();
    private Nome nomePanel = new Nome();
    private Capienza capienzaPanel = new Capienza();
    private AddSpecie addSpeciePanel = new AddSpecie();
    private DeleteSpecie deleteSpeciePanel = new DeleteSpecie();
    private Delete deletePanel = new Delete();

	public Centri() {
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createTitledBorder(
                        BorderFactory.createEtchedBorder(),
                        "Centri",
                        TitledBorder.CENTER,
                        TitledBorder.TOP
                ),
                BorderFactory.createEmptyBorder(25, 10, 10, 10)
        ));

        // --- Colonna sinistra ---
        JPanel leftPanel = new JPanel();
        leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));
        leftPanel.setPreferredSize(new Dimension(200, 0));

        JLabel title = new JLabel("Operazioni");
        title.setFont(new Font("Arial", Font.BOLD, 25));// testo bianco
        title.setAlignmentX(Component.CENTER_ALIGNMENT);
        title.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        leftPanel.add(title);

        // Funzione helper per creare pulsanti uniformi
        JButton visualizzare = createMenuButton("Mostra Info");
        JButton aggiungiCentro = createMenuButton("Aggiungi Centro");
        JButton updateNome = createMenuButton("Update Nome Centro");
        JButton updateCapienza = createMenuButton("Update Capienza Centro");
        JButton updateAggiungiSpecie = createMenuButton("Aggiungi Specie al Centro");
        JButton updateDeleteSpecie = createMenuButton("Elimina Specie dal Centro");
        JButton deleteCentro = createMenuButton("Elimina Centro");

        leftPanel.add(visualizzare);
        leftPanel.add(aggiungiCentro);
        leftPanel.add(updateNome);
        leftPanel.add(updateCapienza);
        leftPanel.add(updateAggiungiSpecie);
        leftPanel.add(updateDeleteSpecie);
        leftPanel.add(deleteCentro);

        add(leftPanel, BorderLayout.WEST);

        // --- Colonna destra ---
        cardLayout = new CardLayout();
        rightPanel = new JPanel(cardLayout);

        rightPanel.add(infoPanel, "info");
        rightPanel.add(addPanel, "add");
        rightPanel.add(nomePanel, "nome");
        rightPanel.add(capienzaPanel, "capienza");
        rightPanel.add(addSpeciePanel, "addSpecie");
        rightPanel.add(deleteSpeciePanel, "deleteSpecie");
        rightPanel.add(deletePanel, "delete");

        add(rightPanel, BorderLayout.CENTER);

        // --- Azioni pulsanti ---
        visualizzare.addActionListener(e -> cardLayout.show(rightPanel, "info"));
        aggiungiCentro.addActionListener(e -> cardLayout.show(rightPanel, "add"));
        deleteCentro.addActionListener(e -> cardLayout.show(rightPanel, "delete"));
        updateNome.addActionListener(e -> cardLayout.show(rightPanel, "nome"));
        updateCapienza.addActionListener(e -> cardLayout.show(rightPanel, "capienza"));
        updateAggiungiSpecie.addActionListener(e -> cardLayout.show(rightPanel, "addSpecie"));
        updateDeleteSpecie.addActionListener(e -> cardLayout.show(rightPanel, "deleteSpecie"));
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

    public Info getInfoPanel() {
		return infoPanel;
	}

	public Add getAddPanel() {
		return addPanel;
	}

	public Nome getNomePanel() {
		return nomePanel;
	}

	public Capienza getCapienzaPanel() {
		return capienzaPanel;
	}

	public AddSpecie getAddSpeciePanel() {
		return addSpeciePanel;
	}

	public DeleteSpecie getDeleteSpeciePanel() {
		return deleteSpeciePanel;
	}

	public Delete getDeletePanel() {
		return deletePanel;
	}
}
