package it.unibo.adozione_animali.model.api.statistica;

import java.time.LocalDate;
import java.util.List;

public interface StatisticManager {
    boolean insertStatistica(String codice, String nome, LocalDate data);

    boolean insertDato(String codice, List<String> nome, List<String> valore, String statistica, LocalDate data);
}
