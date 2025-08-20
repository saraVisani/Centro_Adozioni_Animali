package it.unibo.adozione_animali.model.api.caratteristica;

import java.util.Optional;

public interface CaratteristicaPersonale {
    boolean insertCaratteristicaPersonale(String provincia, String città, int numero, String animale,
                                            String tipo, Optional<String> codice);

    boolean ChangeCodiceCaratteristica(String provincia, String città, int numero, String animale, String tipo,
                                        String Newcodice);

    boolean deleteCaratteristicaPersonale(String provincia, String città, int numero, String animale, String tipo);
}
