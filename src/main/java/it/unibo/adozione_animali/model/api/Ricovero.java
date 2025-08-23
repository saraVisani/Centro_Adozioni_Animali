package it.unibo.adozione_animali.model.api;

import java.time.LocalDate;

public interface Ricovero {

    void insertRicovero(Integer codFascicolo, Short numeroProblema, String paragrafo, LocalDate dataInizioRicovero,
                        LocalDate dataFineRicovero, String nomeOspedale);

    void updateRicovero(int codFascicolo, short numeroProblema, String paragrafo, LocalDate dataFineRicovero);

    void deleteRicovero(int codFascicolo, short numeroProblema, String paragrafo);
}
