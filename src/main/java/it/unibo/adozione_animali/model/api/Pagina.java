package it.unibo.adozione_animali.model.api;

import nu.studer.sample.tables.records.EsameRecord;
import nu.studer.sample.tables.records.ProblemaRecord;
import nu.studer.sample.tables.records.RicoveroRecord;

import java.util.List;

/**
 * Represents the PaginaFascicoloSanitario table in the database.
 */
public interface Pagina {

    void deletePaginaEffettiva(int codFascicolo, short numeroPagina);

    void deleteSinglePar(int codFascicolo, short numeroPagina, String paragrafo);

    List<ProblemaRecord> getProblemsFasc(int codFascicolo, Short numeroPagina, String paragrafo);

    List<EsameRecord> getExamFasc(int codFascicolo, Short numeroPagina, String paragrafo);

    List<RicoveroRecord> getRicovFasc(int codFascicolo, Short numeroPagina, String paragrafo);
}
