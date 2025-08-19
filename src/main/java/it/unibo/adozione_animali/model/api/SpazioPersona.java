package it.unibo.adozione_animali.model.api;

import java.util.Optional;

public interface SpazioPersona {

    boolean insertSpazioPersona(int spazio, String persona, String provincia, String citta, int numero, Optional<Float> spazioRimanente);

    boolean updateSpazioRimanente(int spazio, String persona, String provincia, String citta, int numero, int newSpazio);

    boolean deleteSpazioPersona(int spazio, String persona, String provincia, String citta, int numero);
}
