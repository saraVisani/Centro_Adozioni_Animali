package it.unibo.adozione_animali.model.api;

import java.time.LocalDate;
import java.util.List;

/**
 * Interface that represents the Esame table.
 */
public interface Esame {

    void insertEsame(Integer codFascicolo, Short numeroProblema, String paragrafo, LocalDate dataEsame,
                     List<String> codTipiEsame);

    void updateEsame(int codFascicolo, short numeroProblema, String paragrafo, LocalDate dataEsame);

    void deleteEsame(int codFascicolo, short numeroProblema, String paragrafo);
}
