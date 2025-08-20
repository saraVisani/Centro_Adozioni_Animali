package it.unibo.adozione_animali.model.api.caratteristica;

public interface Riferimento {
    boolean insertRiferimento(String codice, String specie, String razza);

    boolean updateRiferimentoRazza(String codice, String newSpecie, String newRazza);

    boolean deleteRiferimento(String codice);
}
