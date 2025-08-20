package it.unibo.adozione_animali.model.api;

/**
 * Representes the Componente table in the database.
 */
public interface Componente {

    void insertComponente(String codProvincia, String codCitta, int numero, String codAnimale, String codSpecie,
                          String nomeRazza, int percentuale);

    void updatePercentualeComponente(String codProvincia, String codCitta, int numero, String codAnimale, String codSpecie,
                                     String nomeRazza, int nuovaPercentuale);

    void deleteComponente(String codProvincia, String codCitta, int numero, String codAnimale, String codSpecie,
                          String nomeRazza);
}
