package it.unibo.adozione_animali.model.api;

import java.time.LocalDate;

/**
 * Represents the Richiedente table in the database.
 */
public interface Richiedente {

    void insertRichiedente(String CF, short numeroAbbandoni, LocalDate dataAbbandonoRecente, byte abuso);

    void updateAbbandoni(String CF, LocalDate nuovaDataAbbandono);

    void updateAbuso(String CF, boolean abuso);

    void deleteRichiedente(String CF);
}
