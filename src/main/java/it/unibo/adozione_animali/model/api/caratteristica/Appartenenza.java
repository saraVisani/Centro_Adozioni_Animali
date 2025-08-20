package it.unibo.adozione_animali.model.api.caratteristica;

import java.util.List;

public interface Appartenenza {
    boolean insertAppartenenza(List<String> codice, String provincia, String città, int numero, String animale,
                                String tipo);

    boolean changeCodiceAppartenenza(String codice, String provincia, String città, int numero, String animale,
                                        String tipo, String newCodice);

    boolean deleteAppartenenza(String codice, String provincia, String città, int numero, String animale,
                                String tipo);
}
