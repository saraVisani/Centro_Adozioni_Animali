package it.unibo.adozione_animali.model.api.caratteristica;

public interface Contenuto {
    boolean insertContenuto(String codice, String specie);

    boolean updateContenutoSpecie(String codice, String newSpecie);

    boolean deleteContenuto(String codice);
}
