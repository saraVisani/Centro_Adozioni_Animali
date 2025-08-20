package it.unibo.adozione_animali.model.impl.statistica;

import java.time.LocalDate;
import java.util.List;

import it.unibo.adozione_animali.model.api.statistica.ConcatDato;
import it.unibo.adozione_animali.model.api.statistica.Dato;
import it.unibo.adozione_animali.model.api.statistica.StatisticManager;
import it.unibo.adozione_animali.model.api.statistica.Statistica;
import it.unibo.adozione_animali.util.Enum.NomeStatistica;

public class StatisticManagerImpl implements StatisticManager{

    private final ConcatDato concatDato = new ConcatDatoImpl();

    @Override
    public boolean insertStatistica(String codice, String nome, LocalDate data) {
        if (codice == null || nome == null || data == null) {
            return false;
        }

        if(NomeStatistica.isValidDescrizione(nome)) {
            nome = NomeStatistica.getDescrizioneFrom(nome);
            int annoCorrente = LocalDate.now().getYear();
            if(data.getYear() < annoCorrente) {
                return false;
            }
            Statistica statisticaDAO = new StatisticaDAO();
            return statisticaDAO.insertStatistica(codice, data, nome);
        } else {
            return false;
        }
    }

    @Override
    public boolean insertDato(String codice, List<String> nome, List<String> valore, String statistica, LocalDate data) {
        if (codice == null || nome == null || nome.isEmpty() || valore == null
                || valore.isEmpty() || statistica == null || data == null) {
            return false;
        }
        String realName;
        String realValue;
        try {
            realName = concatDato.concatNome(nome, statistica);
            realValue = concatDato.concatValore(valore, statistica);
            if (realName == null || realValue == null) {
                return false;
            }
        } catch (Exception e) {
            return false;
        }

        statistica = NomeStatistica.getDescrizioneFrom(statistica);
        Dato datoDAO = new DatoDAO();
        if (!datoDAO.insertDato(codice, realName, realValue, statistica, data)) {
            return false;
        }
        return true;
    }
}
