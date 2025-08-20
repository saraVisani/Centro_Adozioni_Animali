package it.unibo.adozione_animali.model.api.statistica;

import java.util.List;

public interface ConcatDato {

    String concatValore(List<String> valori, String statistica);

    String concatNome(List<String> nome, String statistica);

}