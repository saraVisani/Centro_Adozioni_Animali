package it.unibo.adozione_animali.model.api;

import java.time.LocalDate;

public interface Richiesta {

    void insertRichiesta(String codProvinciaAnimale, String codCittaAnimale, int numeroAnimale, String codAnimale,
                         String CF, String codProvinciaRichiedente, String codCittaRichiedente, int numeroRichiedente);

    void chiusuraPositivaRichiesta(String codProvinciaAnimale, String codCittaAnimale, int numeroAnimale,
                                   String codAnimale, LocalDate dataRichiesta, String CF);

    void rifiutoRichiesta(String codProvinciaAnimale, String codCittaAnimale, int numeroAnimale,
                          String codAnimale, LocalDate dataRichiesta, String CF);

    void deleteRichiesta(String codProvinciaAnimale, String codCittaAnimale, int numeroAnimale,
                         String codAnimale, LocalDate dataRichiesta, String CF);

}
