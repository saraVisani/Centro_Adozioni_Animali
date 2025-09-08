package it.unibo.adozione_animali.view.pulsanti;

import javax.swing.*;

import it.unibo.adozione_animali.view.pulsanti.spazio.*;
import it.unibo.adozione_animali.view.pulsanti.spazio.DeleteSpazio;
import it.unibo.adozione_animali.view.pulsanti.spazio.UpdateTipoSpazio;

import java.awt.*;

public class Spazio extends JPanel{

    private CardLayout cardLayout;
	private JPanel rightPanel;
    private Info infoPanel = new Info();
    private Add addPanel = new Add();
    private UpdateTipoSpazio updateTipoPanel = new UpdateTipoSpazio();
    private UpdateDimensione updateDimensionePanel = new UpdateDimensione();
    private UpdateTipoDimensione updateTipoDimensionePanel = new UpdateTipoDimensione();
    private DeleteSpazio deletePanel = new DeleteSpazio();

    public Spazio() {

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
        JButton visualizzare = createMenuButton("Mostra Info");
        JButton aggiungiSpazio = createMenuButton("Aggiungi Spazio");
        JButton updateTipo = createMenuButton("Update Tipo Spazio");
        JButton updateDimensione = createMenuButton("Update Dimensione Spazio");
        JButton updateTipoDimensione = createMenuButton("Update Tipo e Dimensione Spazio");
        JButton deleteSpazio = createMenuButton("Elimina Spazio");

        leftPanel.add(visualizzare);
        leftPanel.add(aggiungiSpazio);
        leftPanel.add(updateTipo);
        leftPanel.add(updateDimensione);
        leftPanel.add(updateTipoDimensione);
        leftPanel.add(deleteSpazio);

        add(leftPanel, BorderLayout.WEST);

        // --- Colonna destra ---
        cardLayout = new CardLayout();
        rightPanel = new JPanel(cardLayout);

        rightPanel.add(infoPanel, "info");
        rightPanel.add(addPanel, "add");
        rightPanel.add(updateTipoPanel, "Tipo");
        rightPanel.add(updateDimensionePanel, "Dimensione");
        rightPanel.add(updateTipoDimensionePanel, "addSpecie");
        rightPanel.add(deletePanel, "delete");

        add(rightPanel, BorderLayout.CENTER);

        // --- Azioni pulsanti ---
        visualizzare.addActionListener(e -> cardLayout.show(rightPanel, "info"));
        aggiungiSpazio.addActionListener(e -> cardLayout.show(rightPanel, "add"));
        deleteSpazio.addActionListener(e -> cardLayout.show(rightPanel, "delete"));
        updateTipo.addActionListener(e -> cardLayout.show(rightPanel, "Tipo"));
        updateDimensione.addActionListener(e -> cardLayout.show(rightPanel, "Dimensione"));
        updateTipoDimensione.addActionListener(e -> cardLayout.show(rightPanel, "addSpecie"));
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

	public UpdateTipoSpazio getUpdateTipoPanel() {
		return updateTipoPanel;
	}

	public UpdateDimensione getUpdateDimensionePanel() {
		return updateDimensionePanel;
	}

	public UpdateTipoDimensione getUpdateTipoDimensionePanel() {
		return updateTipoDimensionePanel;
	}

	public DeleteSpazio getDeletePanel() {
		return deletePanel;
	}
}