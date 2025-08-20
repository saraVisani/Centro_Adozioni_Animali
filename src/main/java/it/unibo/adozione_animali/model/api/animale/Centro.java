package it.unibo.adozione_animali.model.api.animale;

import java.util.List;

/**
 * Interfaccia per la gestione dei centri di adozione animali.
 * Fornisce metodi per inserire, aggiornare e cancellare centri,
 * oltre a gestire le specie che possono essere ospitate in ciascun centro.
 */
public interface Centro {

    boolean insertCentro(String nome, int capienza, String provincia, String città, int numero , List<String> specie);

    boolean updateCentroNome(String provincia, String città, int numero, String nome);

    boolean updateCentroCapienza(String provincia, String città, int numero, int capienza);

    boolean updateCentroAggiungiSpecie(String provincia, String città, int numero, List<String> specie);

    boolean updateCentroTogliSpecie(String provincia, String città, int numero, List<String> specie);

    boolean deleteCentro(String provincia, String città, int numero);
}
