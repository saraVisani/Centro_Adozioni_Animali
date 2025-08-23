package it.unibo.adozione_animali.util;

public class ItemSelezionabile {
    private final String codice;
    private final String nome;

    public ItemSelezionabile(String codice, String nome) {
        this.codice = codice;
        this.nome = nome;
    }

    public String getCodice() { return codice; }
    public String getNome() { return nome; }

    @Override
    public String toString() {
        return nome; // Mostrato nella JComboBox
    }
}
