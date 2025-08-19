package it.unibo.adozione_animali.model.api;

import java.util.List;
public interface Centro {

    boolean insertCentro(String nome, int capienza, String provincia, String città, int numero , List<String> specie);

    boolean updateCentroNome(String provincia, String città, int numero, String nome);

    boolean updateCentroCapienza(String provincia, String città, int numero, int capienza);

    boolean updateCentroAggiungiSpecie(String provincia, String città, int numero, List<String> specie);

    boolean updateCentroTogliSpecie(String provincia, String città, int numero, List<String> specie);

    boolean deleteCentro(String provincia, String città, int numero);
}
