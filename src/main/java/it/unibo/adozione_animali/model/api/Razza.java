package it.unibo.adozione_animali.model.api;

import java.math.BigDecimal;

/**
 * Represents the Razza table in the database.
 */
public interface Razza {

    void insertRazza(String codSpecie, String nomeRazza, String descrizione, String origine, String lignaggio,
                     BigDecimal altezzaMax, BigDecimal altezzaMin, BigDecimal pesoMax, BigDecimal pesoMin,
                     Integer IDSpazio1, Integer IDSpazio2);

    void deleteRazza(String codSpecie, String nomeRazza);
}
