package it.unibo.adozione_animali.model.api.indirizzo;

public interface Citta {

    boolean insertCitta(String provincia, String citta, String nome);

    boolean updateCittaNome(String provincia, String citta, String newNome);

    boolean updateCittaProvincia(String citta, String newProvincia, String newCitta);

    boolean deleteCitta(String provincia, String citta);
}
