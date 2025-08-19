package it.unibo.adozione_animali.model.api;

public interface Provincia {

    boolean insertProvincia(String provincia, String nome, String regione);

    boolean updateProvinciaNome(String provincia, String newNome);

    boolean updateProvinciaRegione(String provincia, String newRegione);

    boolean updateProvincia(String provincia, String newProvincia);

    boolean deleteProvincia(String provincia);
}
