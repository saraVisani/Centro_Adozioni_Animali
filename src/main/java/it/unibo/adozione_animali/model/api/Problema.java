package it.unibo.adozione_animali.model.api;

/**
 * Represents the Problema table in the database.
 */
public interface Problema {

    void insertProblema(int codFascicolo, short numeroProblema, String paragrafo, String nome,
                        String descrizione, String tipoCurabile, String inCura, String Area1, String Area2,
                        int rCodFascicolo, short rNumeroProblema, String rParagrafo, int eCodFascicolo,
                        short eNumeroProblema, String eParagrafo, String codProvincia, String codCitta,
                        int numero, String codAnimale);

    void updateProblemaInCura(int codFascicolo, short numeroProblema, String paragrafo, String statoCura,
                              String codProvincia, String codCitta, int numero, String codAnimale);

    void updateProblemaNome(int codFascicolo, short numeroProblema, String paragrafo, String nome);

    void updateProblemaDescrizione(int codFascicolo, short numeroProblema, String paragrafo, String descrizione);

    void updateRiferimentoRicovero(int codFascicolo, short numeroProblema, String paragrafo, int rCodFascicolo,
                                   short rNumeroProblema, String rParagrafo);

    void updateRiferimentoEsame(int codFascicolo, short numeroProblema, String paragrafo, int eCodFascicolo,
                                short eNumeroProblema, String eParagrafo);

    void deleteProblema(int codFascicolo, short numeroProblema, String paragrafo);
}
