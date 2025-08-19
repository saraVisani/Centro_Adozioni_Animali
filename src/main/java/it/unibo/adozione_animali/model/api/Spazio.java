package it.unibo.adozione_animali.model.api;

public interface Spazio {

    boolean insertSpazio(int spazio, String tipo, int dimensione);

    boolean updateSpazioTipo(int spazio, String newTipo);

    boolean updateSpazioDimensione(int spazio, int newDimensione);

    boolean deleteSpazio(int spazio);
}
