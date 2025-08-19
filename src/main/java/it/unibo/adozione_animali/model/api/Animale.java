package it.unibo.adozione_animali.model.api;

import java.time.LocalDate;
import java.util.Optional;

public interface Animale {

    boolean insertAnimale(String provincia, String città, int numero, String animale, String specie,
                            Optional<String> nome, String razza, String prov_ritr, String cit_ritr, int num_ritr,
                            String stato_ritr, LocalDate data_nascita, LocalDate data_inserimento,
                            Optional<LocalDate> data_addozione, Optional<String> prov_att, Optional<String> cit_att,
                            Optional<Integer> num_att, Optional<String> prov_centro, Optional<String> cit_centro,
                            Optional<Integer> num_centro, Optional<String> statoAttuale, boolean idonieta,
                            float peso, float altezza);

    boolean updateAnimaleNome(String provincia, String città, int numero, String animale, String newNome);

    boolean updateAnimaleAltezza(String provincia, String città, int numero, String animale, float newAltezza);

    boolean updateAnimalePeso(String provincia, String città, int numero, String animale, float newPeso);
    
    boolean deleteAnimale(String provincia, String città, int numero, String animale);

}
