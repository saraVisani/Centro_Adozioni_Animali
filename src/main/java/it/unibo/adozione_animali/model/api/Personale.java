package it.unibo.adozione_animali.model.api;

import java.time.LocalDate;

/**
 * Represents the Personale table in the database.
 */
public interface Personale{

    void insertPersonale(String CF, byte tempoLavoro, LocalDate dataAssunzioneDip, LocalDate dataAssunzioneVol,
                         LocalDate dataFineLavoroDip, LocalDate dataFineLavoroVol, Short stipendio,
                         byte exVolontario, byte exDipendente, String codProvincia, String codCitta,
                         int numero);

    void updateTempoLavoro(String CF, byte tempoLavoro);

    void updateDataAssunzioneDip(String CF, LocalDate dataAssunzioneDip);

    void updateDataAssunzioneVol(String CF, LocalDate dataAssunzioneVol);

    void updateDataFineLavoroDip(String CF, LocalDate dataFineLavoroDip);

    void updateDataFineLavoroVol(String CF, LocalDate dataFineLavoroVol);

    void updateStipendio(String CF, short stipendio);

    void updateIndirizzoCentro(String CF, String codProvincia, String codCitta, int numero);

    void deletePersonale(String CF);


}
