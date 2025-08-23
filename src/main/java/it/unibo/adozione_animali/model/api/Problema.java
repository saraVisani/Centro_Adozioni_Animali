package it.unibo.adozione_animali.model.api;

import java.util.List;

/**
 * Represents the Problema table in the database.
 */
public interface Problema {

    String insertProblema(Integer codFascicolo, Short numeroProblema, String paragrafo, String nome,
                        String descrizione, String tipoCurabile, String inCura, String Area1, String Area2,
                        Integer rCodFascicolo, Short rNumeroProblema, String rParagrafo, Integer eCodFascicolo,
                        Short eNumeroProblema, String eParagrafo, String codProvincia, String codCitta,
                        Integer numero, String codAnimale, List<String> codSintomi);

    void updateProblemaInCura(int codFascicolo, short numeroProblema, String paragrafo, String statoCura);

    void updateProblemaNome(int codFascicolo, short numeroProblema, String paragrafo, String nome);

    void updateProblemaDescrizione(int codFascicolo, short numeroProblema, String paragrafo, String descrizione);

    void updateRiferimentoRicovero(int codFascicolo, short numeroProblema, String paragrafo, int rCodFascicolo,
                                   short rNumeroProblema, String rParagrafo);

    void updateRiferimentoEsame(int codFascicolo, short numeroProblema, String paragrafo, int eCodFascicolo,
                                short eNumeroProblema, String eParagrafo);

    void deleteProblema(int codFascicolo, short numeroProblema, String paragrafo);
}
