package it.unibo.adozione_animali.model.api;

/**
 * Represents the Richiedente table in the database.
 */

import java.time.LocalDate;

public interface Richiedente {

    void insertRichiedente(String CF, short numeroAbbandoni, LocalDate dataAbbandonoRecente, byte abuso);

    void updateAbbandoni(String CF, LocalDate nuovaDataAbbandono);

    void updateAbuso(String CF, boolean abuso);

    void deleteRichiedente(String CF);
}
