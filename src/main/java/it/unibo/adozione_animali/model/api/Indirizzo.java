package it.unibo.adozione_animali.model.api;

import java.util.Optional;

public interface Indirizzo {

    boolean insertIndirizzo(String provincia, String città, int numero, String via, Optional<String> CF);

    boolean updateIndirizzoVia(String provincia, String città, int numero, String via);

    boolean updateIndirizzoCittà(String provincia, String città, int numero, String NewCittà);

    boolean updateIndirizzoNumero(String provincia, String città, int numero, int NewNumero);

    boolean updateIndirizzoProvincia(String provincia, String città, int numero, String NewProvincia);

    boolean updateIndirizzoCF(String provincia, String città, int numero, String NewCF);

    boolean deleteIndirizzo(String provincia, String città, int numero);
}
