package it.unibo.adozione_animali.model.api.statistica;

//import java.time.LocalDate;

public interface Dato {
    //boolean insertDato(String codice, String nome, String valore, String statistica, LocalDate data);

    boolean deleteDato(String codice, String nome);
}
