package it.unibo.adozione_animali.controller.impl;

import java.util.List;

import it.unibo.adozione_animali.model.impl.Model;
import it.unibo.adozione_animali.util.ItemSelezionabile;
import it.unibo.adozione_animali.view.animale.caratteristiche.Cancellazione;

public class CancellazioneCaratteristicaController {

    private final Model model;
    private final Cancellazione view;

    public CancellazioneCaratteristicaController(Model model, Cancellazione view) {
        this.model = model;
        this.view = view;
        inizializza();
    }

    private void inizializza() {
        // Popola le province all’avvio
        view.setProvince(model.getProvinciaDAO().getProvince());
    }

    public void provinciaSelezionata(String provinciaCodice) {
        if (provinciaCodice != null) {
            view.setCitta(model.getCittaDAO().getCittaByProvincia(provinciaCodice));
        }
    }

    public void cittaSelezionata(String provinciaCodice, String cittaCodice) {
        if (provinciaCodice != null && cittaCodice != null) {
            List<String> numeri = model.getIndirizzoDAO().getNumeriByCitta(provinciaCodice, cittaCodice);
            view.setNumeri(numeri);
        }
    }

    public void numeroSelezionato(String provinciaCodice, String cittaCodice, String numero) {
        if (provinciaCodice != null && cittaCodice != null && numero != null) {
            List<String> codici = model.getAnimaleDAO().getCodiciByNumero(provinciaCodice, cittaCodice, Integer.valueOf(numero));
            view.setCodiciAnimale(codici);
        }
    }

    public void animaleSelezionato(String provinciaCodice, String cittaCodice, String numero, String codAnimale, boolean nuovo) {
        if (provinciaCodice != null && cittaCodice != null && numero != null && codAnimale != null) {
            List<String> tipi = model.getCaratteristicaPersonaliDAO()
                    .getTipi(provinciaCodice, cittaCodice, numero, codAnimale, nuovo);
            view.setTipi(tipi);
        }
    }

    public void tipoSelezionato(String provinciaCodice, String cittaCodice, String numero, String codAnimale, String tipo) {
        if (provinciaCodice != null && cittaCodice != null && numero != null && codAnimale != null && tipo != null) {
            List<ItemSelezionabile> items = model.getSpecificaRazzaDAO()
                .getCodiciSpecifici(provinciaCodice, cittaCodice, numero, codAnimale, tipo);

            // Passiamo direttamente alla view
            view.setCodiciSpecifici(items);
        }
    }

    public void salvaCancellazione() {
        String provincia = view.getProvinciaSelezionata();
        String citta = view.getCittaSelezionata();
        String numero = view.getNumeroSelezionato();
        String codAnimale = view.getCodiceAnimaleSelezionato();
        String tipo = view.getTipoSelezionato();
        List<String> codiciSpecifici = view.getCodiciSpecificiSelezionati();
        boolean nuovo = view.isNuovoChecked();

        boolean esito;
        if (nuovo) {
            esito = model.getCaratteristicaPersonaliDAO()
                    .deleteCaratteristicaPersonale(provincia, citta, Integer.valueOf(numero), codAnimale, tipo);
        } else {
            esito = codiciSpecifici.stream()
                .anyMatch(cod -> model.getAppartenenzaDAO()
                        .deleteAppartenenza(cod, provincia, citta, Integer.valueOf(numero), codAnimale, tipo));
        }

        view.showEsito(esito);
    }
}
