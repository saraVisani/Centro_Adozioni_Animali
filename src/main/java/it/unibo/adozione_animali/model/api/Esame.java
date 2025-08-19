package it.unibo.adozione_animali.model.api;

import java.time.LocalDate;

/**
 * Interface that represents the Esame table.
 */
public interface Esame {

    void insertEsame(int cod_fascicolo, short numero_problema, String paragrafo, LocalDate data_esame,
                     String cod_provincia, String cod_citta, int numero, String cod_animale);

    void updateEsame(int cod_fascicolo, short numero_problema, String paragrafo, LocalDate data_esame);

    void deleteEsame(int cod_fascicolo, short numero_problema, String paragrafo);
}
