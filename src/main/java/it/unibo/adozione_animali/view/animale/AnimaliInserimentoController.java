package it.unibo.adozione_animali.view.animale;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;

import it.unibo.adozione_animali.model.impl.Model;
import it.unibo.adozione_animali.util.Enum.*;

public class AnimaliInserimentoController {

    private Model model;
    private InserisciAnimale view;
    private boolean selezionato;

    public AnimaliInserimentoController(Model model, InserisciAnimale view){
        this.model = model;
        this.view = view;

        view.setProvince(model.getCentroDAO().getProvince());
        view.setSpecie(List.of(Specie.CANE.getDisplayName(), Specie.GATTO.getDisplayName(),
            Specie.RODITORE.getDisplayName(),Specie.RETTILE.getDisplayName(),Specie.VOLATILE.getDisplayName()));
        view.setRitrovamento(TipoRitrovamento.getDisplayNames());
        view.setStato(StatoAnimale.getDisplayNames());
    }

    private void init(){
        selezionato = false;
        view.setSpecie(List.of(Specie.CANE.getDisplayName(), Specie.GATTO.getDisplayName(),
            Specie.RODITORE.getDisplayName(),Specie.RETTILE.getDisplayName(),Specie.VOLATILE.getDisplayName()));
    }

    public void provinciaSelezionata(String provinciaCodice) {
        if (provinciaCodice != null) {
            view.setCitta(model.getCentroDAO().getCittaByProvincia(provinciaCodice));
            if(selezionato){
                init();
            }
        }
    }

    public void cittaSelezionata(String provinciaCodice, String cittaCodice) {
        if (provinciaCodice != null && cittaCodice != null) {
            List<String> numeri = model.getCentroDAO().getNumeriByCitta(provinciaCodice, cittaCodice);
            view.setNumeri(numeri);
            if(selezionato){
                init();
            }
        }
    }

    public void numeroSelezionato(String provinciaCodice, String cittaCodice, String numero, String specie) {
        if (provinciaCodice != null && cittaCodice != null && numero != null) {
            List<String> codici = model.getAnimaleDAO().getCodiciLiberi(provinciaCodice, cittaCodice, Integer.valueOf(numero), specie);
            view.setCodiciAnimale(codici);
            if(selezionato){
                init();
            }
        }
    }

    public void animaleSelezionato(String provinciaSelezionata, String cittaSelezionata, String numeroSelezionato,
            String codiceAnimaleSelezionato, String comboSpecie) {
        if (provinciaSelezionata != null && cittaSelezionata != null && numeroSelezionato != null && codiceAnimaleSelezionato != null
            && !codiceAnimaleSelezionato.equals("--select--")) {
            selezionato = true;
            var specie = codiceAnimaleSelezionato.trim().substring(0, 2);

            if (comboSpecie != null && !comboSpecie.isBlank() && comboSpecie.startsWith(specie)) {
                DefaultComboBoxModel<String> model = (DefaultComboBoxModel<String>) view.getComboSpecie().getModel();

                for (int i = model.getSize() - 1; i >= 0; i--) {
                    String item = model.getElementAt(i);
                    if (!item.equals(comboSpecie)) {
                        model.removeElementAt(i);
                    }
                }

                // Mantieni la selezione corrente
                view.getComboSpecie().setSelectedItem(comboSpecie);
                return;
            }
            view.setSpecie(List.of(Specie.fromKey(specie).getDisplayName()));
        }
    }

    public void specieSelezionata(String comboSpecie) {
        if(comboSpecie != null){
            view.setRazza(model.getRazzaDAO().getRazzaBySpecie(Specie.keyFromName(comboSpecie)));
        }
    }

    public void razzaSelezionata(String specie, String razza) {
        if(specie != null && razza != null){
            // ottieni limiti dal model
            List<BigDecimal> limiti = model.getRazzaDAO().getPesiAltezza(specie, razza);
            if(limiti != null && limiti.size() == 4){
                double pesoMax = limiti.get(0).doubleValue();
                double pesoMin = limiti.get(1).doubleValue();
                double altMax = limiti.get(2).doubleValue();
                double altMin = limiti.get(3).doubleValue();

                JSpinner spPeso = view.getSpPeso();
                double valorePeso = ((Number) spPeso.getValue()).doubleValue();
                if (valorePeso < pesoMin) valorePeso = pesoMin;
                if (valorePeso > pesoMax) valorePeso = pesoMax;
                spPeso.setModel(new SpinnerNumberModel(valorePeso, pesoMin, pesoMax, 0.1));

                JSpinner spAlt = view.getSpAlt();
                double valoreAlt = ((Number) spAlt.getValue()).doubleValue();
                if (valoreAlt < altMin) valoreAlt = altMin;
                if (valoreAlt > altMax) valoreAlt = altMax;
                spAlt.setModel(new SpinnerNumberModel(valoreAlt, altMin, altMax, 0.1));
            }
        }
    }

    public void salvaInserimento() {
        String provincia = view.getProvinciaSelezionata();
        String citta = view.getCittaSelezionata();
        Integer numero;
        try {
            numero = Integer.parseInt(view.getNumeroSelezionato());
        } catch (Exception e) {
            numero = null;
        }
        String animale = view.getCodiceAnimaleSelezionato();
        String ritrovamento = view.getRitrovamento();
        String specie = view.getSpecie();
        String razza = view.getComboRazza();
        String stato = view.getComboStato();
        String nome = view.getTxtNome();
        LocalDate Eta = view.getDataNascita();
        String provinciaRit = view.getProvinciaRit();
        String cittaRit = view.getCittaRit();
        Integer numeroRit = view.getNumeroRit();
        Float peso = view.getValueSpPeso();
        Float alt = view.getValueSpAlt();

        if(animale == null || animale.equals("--select--")){
            animale = model.getAnimaleDAO().getCodiciLiberi(provincia, citta, numero, specie).get(0);
        }

        boolean idoneità = false;
        String statoAtt = null;

        switch(StatoAnimale.fromDisplayName(stato)){
            case StatoAnimale.CRONICO:
                statoAtt = StatoAnimale.CRONICO.name();
                idoneità = true;
                break;
            case StatoAnimale.MALATO:
                statoAtt = null;
                break;
            case StatoAnimale.DISABILE:
                idoneità = true;
                statoAtt = StatoAnimale.DISABILE.name();
                break;
            case StatoAnimale.MAL_CRO:
                statoAtt = StatoAnimale.CRONICO.name();
                break;
            case StatoAnimale.MAL_DIS:
                statoAtt = StatoAnimale.DISABILE.name();
                break;
        }

        var esito = model.getAnimaleDAO().insertAnimale(provinciaRit, cittaRit, numero, animale, specie, Optional.ofNullable(nome),
        razza, provinciaRit, cittaRit, numeroRit, ritrovamento, Eta, LocalDate.now(), Optional.empty(), Optional.empty(), Optional.empty(),
        Optional.empty(), Optional.empty(), Optional.empty(), Optional.empty(), Optional.ofNullable(statoAtt), idoneità, peso, alt);

        view.showEsito(esito, model.getAnimaleDAO().getAnimale(provinciaRit, cittaRit, numero, animale));
    }

}
