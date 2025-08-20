package it.unibo.adozione_animali.model.api;

/**
 * Represents the PaginaFascicoloSanitario table in the database.
 */
public interface Pagina {

    void deletePaginaEffettiva(int codFascicolo, short numeroPagina);
}
