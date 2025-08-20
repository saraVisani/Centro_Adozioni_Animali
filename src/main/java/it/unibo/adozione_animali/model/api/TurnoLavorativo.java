package it.unibo.adozione_animali.model.api;

import java.time.LocalDate;

/**
 * Representes the TurnoLavorativo table.
 */
public interface TurnoLavorativo {

    void insertTurnoLavorativo(byte numeroTurno, LocalDate dataTurno);

    void deleteTurnoLavorativo(byte numeroTurno, LocalDate dataTurno);

}
