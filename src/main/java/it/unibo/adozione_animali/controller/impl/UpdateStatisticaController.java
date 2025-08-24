package it.unibo.adozione_animali.controller.impl;

import java.time.LocalDate;
import java.time.Year;
import java.util.Arrays;
import java.util.List;


import it.unibo.adozione_animali.model.impl.Model;
import it.unibo.adozione_animali.util.Enum.NomeStatistica;
import it.unibo.adozione_animali.view.statistiche.StatisticheUpdate;
import it.unibo.adozione_animali.util.Pair;

public class UpdateStatisticaController {

    Model model;
    StatisticheUpdate view;
    StatisticControllerHelper help = new StatisticControllerHelper();

    public UpdateStatisticaController(Model model, StatisticheUpdate view){
        this.model = model;
        this.view = view;
        List<String> descrizioni = Arrays.stream(NomeStatistica.values())
                                        .map(NomeStatistica::getDescrizione)
                                        .toList();

        this.view.setTipo(descrizioni);
    }

    public void salvaRequest() {
    String descrizione = view.getTipo();
    int codiceDato = 0; // contatore iniziale

    NomeStatistica statistica = NomeStatistica.fromDescrizione(descrizione);
    if (statistica == null) {
        view.showEsito(false, "Statistica non valida: " + descrizione);
        return;
    }

    int anno = Year.now().getValue();
    Pair<List<List<String>>, List<List<String>>> setRisultati = help.getRisultati(statistica);
    List<List<String>> nomi = setRisultati.getLeft();
    List<List<String>> valori = setRisultati.getRight();

    if (nomi.size() != valori.size()) {
        view.showEsito(false, "Errore: le matrici non hanno lo stesso numero di righe");
        return;
    }

    boolean esito = true;

    if (model.getStatisticaDAO().exist(statistica.name(), anno)) {
        // Aggiorna data e cancella dati esistenti
        esito = model.getStatisticaDAO().updateDate(statistica.name(), anno, LocalDate.now());
        String codiceStat = model.getStatisticaDAO().getCodice(statistica.name(), anno);
        LocalDate dataStat = model.getStatisticaDAO().getData(codiceStat, anno);
        esito &= model.getDatoDAO().deleteDatoPerStatistica(codiceStat, dataStat);

        // Inserimento dati riga per riga
        for (int i = 0; i < nomi.size(); i++) {
            String codice = String.format("%05d", codiceDato++); // 0 -> "00000", 1 -> "00001", ...
            List<String> nomiRiga = nomi.get(i);
            List<String> valoriRiga = valori.get(i);
            esito &= model.getStatisticManager().insertDato(codice, nomiRiga, valoriRiga, codiceStat, dataStat);
        }
    } else {
        // Inserisci nuova statistica
        String codiceStat = model.getStatisticaDAO().freeCode(anno);
        esito = model.getStatisticManager().insertStatistica(codiceStat, statistica.name(), LocalDate.now());
        LocalDate dataStat = LocalDate.now();

        // Inserimento dati riga per riga
        for (int i = 0; i < nomi.size(); i++) {
            String codice = String.format("%05d", codiceDato++);
            List<String> nomiRiga = nomi.get(i);
            List<String> valoriRiga = valori.get(i);
            esito &= model.getStatisticManager().insertDato(codice, nomiRiga, valoriRiga, codiceStat, dataStat);
        }
    }

    view.showEsito(esito, statistica.getDescrizione());
}

}
