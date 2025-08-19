package it.unibo.adozione_animali.model.api;

public interface Centro {

    boolean insertCentro(String nome, int capienza, String provincia, String città, int numero , List<> specie);

    boolean updateCentroNome(String provincia, String città, int numero, String nome);

    boolean updateCentroCapienza(String provincia, String città, int numero, int capienza);

    boolean updateCentroAggiungiSpecie(String provincia, String città, int numero, List<> specie);

    boolean updateCentroTogliSpecie(String provincia, String città, int numero, List<> specie);

    boolean deleteCentro(String provincia, String città, int numero);
}
