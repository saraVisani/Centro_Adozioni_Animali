package it.unibo.adozione_animali.model.api;

import java.time.LocalDate;

public interface Statistica {
    boolean insertStatistica(String codice, LocalDate data, String nome);

    boolean deleteStatistica(String codice, LocalDate data);
}
