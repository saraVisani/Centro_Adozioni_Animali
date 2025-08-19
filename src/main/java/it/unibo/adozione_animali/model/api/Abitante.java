package it.unibo.adozione_animali.model.api;

public interface Abitante {

    boolean insertAbitante(int numero, String citta, String provincia, int spazio, String CF, String specie, String nome);

    boolean updateAbitanteAnimale(int numero, String citta, String provincia, int spazio, String CF, String specie, String nome, String newSpecie, String newNome);

    boolean updateAbitanteIndirizzo(int numero, String citta, String provincia, int spazio, String CF, String specie, String nome, String newCitta, String newProvincia, int newSpazio, int newNumero);

    boolean deleteAbitante(int numero, String citta, String provincia, int spazio, String CF, String specie, String nome);
}
