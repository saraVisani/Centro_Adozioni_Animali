package it.unibo.adozione_animali.model.api;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * Represents the Task table in the database.
 */
public interface Task {

    void insertTask(String CF, byte numeroTurno, LocalDate dataTask, String lavoro, Optional<List<List<String>>> animali);

    void updateCF(String CF, byte numeroTurno, LocalDate dataTask, String nuovoCF);

    void updateData(String CF, byte numeroTurno, LocalDate dataTask, LocalDate nuovaData);

    void updateNumeroTurno(String CF, byte numeroTurno, LocalDate dataTask, byte nuovoNumeroTurno);

    void updateLavoro(String CF, byte numeroTurno, LocalDate dataTask, String lavoro);

    void deleteTask(String CF, byte numeroTurno, LocalDate dataTask);

}
