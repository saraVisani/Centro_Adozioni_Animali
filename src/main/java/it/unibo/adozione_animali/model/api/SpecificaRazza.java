package it.unibo.adozione_animali.model.api;

public interface SpecificaRazza {

    boolean insertSpecificaRazza(String codice, String tipo, String caratteristica);
    
    boolean deleteSpecificaRazza(String codice);
}
