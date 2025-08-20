package it.unibo.adozione_animali.model.api.spazio;

public interface Spazio {

    boolean insertSpazio(int spazio, String tipo, float dimensione);

    boolean updateSpazioTipo(int spazio, String newTipo);

    boolean updateSpazioDimensione(int spazio, float newDimensione);

    boolean deleteSpazio(int spazio);
}
