package it.unibo.adozione_animali.view.animale;

import it.unibo.adozione_animali.model.impl.Model;
import nu.studer.sample.Tables;
import org.jooq.DSLContext;
import org.jooq.Record;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumnModel;

import java.awt.Component;
import java.math.BigDecimal;
import java.sql.Connection;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import it.unibo.adozione_animali.util.CenterRenderer;
import it.unibo.adozione_animali.util.DBConfig;
import it.unibo.adozione_animali.util.DateRenderer;
import it.unibo.adozione_animali.util.Enum;
import it.unibo.adozione_animali.util.Enum.Specie;

public class AnimaliRicercaController {

    private final AnimaliRicerca view;
    private final Model model;
    private boolean called;

    public AnimaliRicercaController(Model model, AnimaliRicerca view) {
        this.model = model;
        this.view = view;

        view.getBtnCerca().addActionListener(e -> search());
        init();
        view.setProvince(model.getCentroDAO().getProvince());
        search();
    }

    private void init(){
        view.setSpecie(List.of(Specie.CANE.getDisplayName(), Specie.GATTO.getDisplayName(),
            Specie.RODITORE.getDisplayName(),Specie.RETTILE.getDisplayName(),Specie.VOLATILE.getDisplayName()));
        view.setRitrovamento(Enum.TipoRitrovamento.getDisplayNames());
        view.setStato(Enum.StatoAnimale.getDisplayNames());
        try (Connection conn = DBConfig.getConnection()) {
            DSLContext ctx = DSL.using(conn, SQLDialect.MYSQL);

            List<String> cfs = ctx.select(Tables.PERSONA.CF)
                                .from(Tables.PERSONA)
                                .fetch(Tables.PERSONA.CF);

            view.setCf(cfs);
        } catch (Exception e) {
            e.printStackTrace();
        }
        view.resetVisibility();
    }

    public void provinciaSelezionata(String provinciaCodice) {
        if (provinciaCodice != null) {
            view.setCitta(model.getCentroDAO().getCittaByProvincia(provinciaCodice));
        }
    }

    public void cittaSelezionata(String provinciaCodice, String cittaCodice) {
        if (provinciaCodice != null && cittaCodice != null) {
            List<String> numeri = model.getCentroDAO().getNumeriByCitta(provinciaCodice, cittaCodice);
            view.setNumeri(numeri);
        }
    }

    public void numeroSelezionato(String provinciaCodice, String cittaCodice, String numero) {
        if (provinciaCodice != null && cittaCodice != null && numero != null) {
            List<String> codici = model.getAnimaleDAO().getCodiciByNumero(provinciaCodice, cittaCodice, Integer.valueOf(numero));
            view.setCodiciAnimale(codici);
        }
    }

    public void animaleSelezionato(String provinciaSelezionata, String cittaSelezionata, String numeroSelezionato,
            String codiceAnimaleSelezionato) {
        if (provinciaSelezionata != null && cittaSelezionata != null && numeroSelezionato != null && codiceAnimaleSelezionato != null) {
            if(!codiceAnimaleSelezionato.equals("--select--")){
                called = true;
                view.animaleScelto();
            }else if(called && codiceAnimaleSelezionato.equals("--select--")){
                called = false;
                init();
            }
        }
    }

    public void specieSelezionata(String comboSpecie) {
        if(comboSpecie != null){
            view.setRazza(model.getRazzaDAO().getRazzaBySpecie(Enum.Specie.keyFromName(comboSpecie)));
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

                // aggiorna la view
                view.setLimitiSpinner(view.getSpPesoX(), view.getSpPesoN(), pesoMin, pesoMax);
                view.setLimitiSpinner(view.getSpAltX(), view.getSpAltN(), altMin, altMax);
            }
        }
    }

    public void cfSelezionato(String cf) {
        if(!(cf.isBlank() || cf.equals("--select--"))){
            view.setProvinciaPer(model.getIndirizzoDAO().getProvince(cf));
        }
    }

    public void cittaPersonaSelezionato(String provinciaPer, String cittaPer) {
        if (provinciaPer != null && cittaPer != null) {
            List<String> numeri = model.getIndirizzoDAO().getNumeriByPersona(provinciaPer, cittaPer, view.getCf());
            view.setNumeroPer(numeri);
        }
    }

    public void provinciaPersonaSelezionato(String provinciaPer) {
        if (provinciaPer != null) {
            view.setCittaPer(model.getIndirizzoDAO().getCittaByPersona(provinciaPer, view.getCf()));
        }
    }

    private void search() {
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
        String specie = view.getComboSpecie();
        String razza = view.getComboRazza();
        String stato = view.getComboStato();
        String nome = view.getTxtNome();
        Integer txtEtaMax = view.gettxtEtaMax();
        Integer txtEtaMin = view.gettxtEtaMin();
        boolean ckPesoX = view.getCkPesoX();
        boolean ckPesoN = view.getCkPesoN();
        boolean ckAltX = view.getCkAltX();
        boolean ckAltN = view.getCkAltN();
        boolean ckAdozione = view.getCkAdozione();
        String provinciaPer = view.getProvinciaPer();
        String cittaPer = view.getCittaPer();
        Integer numeroPer = view.getNumeroPer();
        String cf = view.getCf();

        DefaultTableModel tableModel = getTableModel(provincia, citta, numero, animale, ritrovamento,
        specie, razza, stato, nome, txtEtaMax, txtEtaMin, ckPesoX, ckPesoN, ckAltX, ckAltN, ckAdozione,
        provinciaPer, cittaPer, numeroPer, cf);
        view.setTableModel(tableModel);
        applyRenderers();
        resizeColumnWidths(view.getTable());
        view.getTable().setAutoResizeMode(JTable.AUTO_RESIZE_SUBSEQUENT_COLUMNS);
    }

    private DefaultTableModel getTableModel(String provincia, String citta, Integer numero, String animale, String ritrovamento,
                                            String specie, String razza, String stato, String nome, Integer txtEtaMax,
                                            Integer txtEtaMin, boolean ckPesoX, boolean ckPesoN, boolean ckAltX, boolean ckAltN,
                                            boolean ckAdozione, String provinciaPer, String cittaPer, Integer numeroPer, String cf){

        Vector<String> columnNames = new Vector<>();
        Vector<Vector<Object>> data = new Vector<>();

        try (Connection conn = DBConfig.getConnection()) {
            DSLContext ctx = DSL.using(conn);

            var query = ctx.select(Tables.ANIMALE.fields())
                            .from(Tables.ANIMALE);

            if(animale != null && !animale.isBlank() && !animale.equals("--select--")){
                query.where(Tables.ANIMALE.COD_PROVINCIA.eq(provincia))
                    .and(Tables.ANIMALE.COD_CITTA_.eq(citta))
                    .and(Tables.ANIMALE.NUMERO.eq(numero))
                    .and(Tables.ANIMALE.COD_ANIMALE.eq(animale));
            } else {

                if(provincia != null && !provincia.isBlank() && !provincia.equals("--select--")){
                    query.where(Tables.ANIMALE.COD_PROVINCIA.equalIgnoreCase(provincia));
                }
                if(citta != null && !citta.isBlank() && !citta.equals("--select--")){
                    query.where(Tables.ANIMALE.COD_CITTA_.equalIgnoreCase(citta));
                }
                if(numero != null){
                    query.where(Tables.ANIMALE.NUMERO.eq(numero));
                }
                if(ritrovamento != null && !ritrovamento.isBlank() && !ritrovamento.equals("--select--")){
                    query.where(Tables.ANIMALE.STATO_RITROVAMENTO.equalIgnoreCase(ritrovamento));
                }
                if (specie != null && !specie.isEmpty() && !specie.equals("--select--")) {
                    query.where(Tables.ANIMALE.COD_SPECIE.equalIgnoreCase(Enum.Specie.keyFromName(specie)));
                }
                if(razza != null && !razza.isBlank() && !razza.equals("--select--")){
                    query.where(Tables.ANIMALE.NOME_RAZZA.equalIgnoreCase(razza));
                }
                if (stato != null && !stato.isEmpty() && !stato.equals("--select--")) {
                    if(stato.equalsIgnoreCase(Enum.StatoAnimale.CRONICO.name())||stato.equalsIgnoreCase(Enum.StatoAnimale.DISABILE.name())){
                        query.where(Tables.ANIMALE.STATO_ATTUALE.equalIgnoreCase(stato));
                    }else if(stato.equalsIgnoreCase(Enum.StatoAnimale.MALATO.name())){
                        query.where(Tables.ANIMALE.IDONIETA_ANIMALE.eq((byte) 0));
                    }else if(Enum.StatoAnimale.isValid(stato)){
                        if(Enum.StatoAnimale.fromDisplayName(stato).equals(Enum.StatoAnimale.MAL_CRO)){
                            query.where(Tables.ANIMALE.IDONIETA_ANIMALE.eq((byte) 0))
                                    .and(Tables.ANIMALE.STATO_ATTUALE.eq(Enum.StatoAnimale.CRONICO.name()));
                        }else{
                            query.where(Tables.ANIMALE.IDONIETA_ANIMALE.eq((byte) 0))
                                    .and(Tables.ANIMALE.STATO_ATTUALE.eq(Enum.StatoAnimale.DISABILE.name()));
                        }
                    }
                }
                if (nome != null && !nome.isEmpty()) {
                    if(nome.equalsIgnoreCase("N/D")||nome.equalsIgnoreCase("N\\D")
                    || nome.equalsIgnoreCase("Nessuno") || nome.equalsIgnoreCase("Nessun nome")
                    || nome.equalsIgnoreCase("null")){
                        query.where(Tables.ANIMALE.NOME.isNull());
                    }else{
                        query.where(Tables.ANIMALE.NOME.eq(nome));
                    }
                }
                if(txtEtaMax != null){
                    LocalDate maxBirthDate = LocalDate.now().minusYears(txtEtaMax);
                    query.where(Tables.ANIMALE.DATA_NASCITA.ge(maxBirthDate));
                }
                if(txtEtaMin != null){
                    LocalDate minBirthDate = LocalDate.now().minusYears(txtEtaMin);
                    query.where(Tables.ANIMALE.DATA_NASCITA.le(minBirthDate));
                }
                if(ckPesoX){
                    Float spPesoX = view.getValueSpPesoX();
                    if (spPesoX != null) {
                        BigDecimal pesoMax = BigDecimal.valueOf(spPesoX);
                        query.where(Tables.ANIMALE.PESO.le(pesoMax));
                    }
                }
                if(ckPesoN){
                    Float spPesoN=view.getValueSpPesoN();
                    if (spPesoN != null) {
                        BigDecimal pesoMin = BigDecimal.valueOf(spPesoN);
                        query.where(Tables.ANIMALE.PESO.ge(pesoMin));
                    }
                }
                if(ckAltX){
                    Float spAltX=view.getValueSpAltX();
                    if (spAltX != null) {
                        BigDecimal altMax = BigDecimal.valueOf(spAltX);
                        query.where(Tables.ANIMALE.ALTEZZA.le(altMax));
                    }
                }
                if(ckAltN){
                    Float spAltN=view.getValueSpAltN();
                    if (spAltN != null) {
                        BigDecimal altMin = BigDecimal.valueOf(spAltN);
                        query.where(Tables.ANIMALE.ALTEZZA.ge(altMin));
                    }
                }
                if(ckAdozione){
                    query.where(Tables.ANIMALE.DATA_ADOZIONE.isNotNull());
                }else{
                    query.where(Tables.ANIMALE.DATA_ADOZIONE.isNull());
                }

                /*if(cf != null && !cf.isBlank() && !cf.equals("--select--")){
                    var spazioIds = ctx.select(Tables.SPAZIO_PERSONA.ID_SPAZIO)
                                    .from(Tables.SPAZIO_PERSONA)
                                    .where(Tables.SPAZIO_PERSONA.CF.eq(cf))
                                    .and(Tables.SPAZIO_PERSONA.COD_PROVINCIA.eq(provinciaPer))
                                    .and(Tables.SPAZIO_PERSONA.COD_CITTA_.eq(cittaPer))
                                    .and(Tables.SPAZIO_PERSONA.NUMERO.eq(numeroPer))
                                    .fetchInto(Long.class);

                    Condition finalCondition = DSL.falseCondition();

                    if(!spazioIds.isEmpty()){

                        Condition condition = DSL.falseCondition(); // parte neutra
                        for(Long idSpazio : spazioIds){
                            condition = condition.or(
                                DSL.function(
                                    "animale_compatibile_spazio",
                                    Integer.class,
                                    Tables.ANIMALE.COD_PROVINCIA,
                                    Tables.ANIMALE.COD_CITTA_,
                                    Tables.ANIMALE.NUMERO,
                                    Tables.ANIMALE.COD_ANIMALE,
                                    DSL.val(cf),
                                    DSL.val(idSpazio),
                                    DSL.val(provinciaPer),
                                    DSL.val(cittaPer),
                                    DSL.val(numeroPer),
                                    DSL.val(1)
                                ).eq(1)
                            );
                        }
                        finalCondition = condition;
                    }
                    query.where(finalCondition);
                }*/
            }

            // Ordinamento: nuove prime
            query.orderBy(Tables.ANIMALE.DATA_INSERIMENTO.desc(), Tables.ANIMALE.NOME.asc());

            var result = query.fetch();

            List<Record> filtered = new ArrayList<>();

            if (cf != null && !cf.isBlank() && !cf.equals("--select--")) {
                // prendo gli spazi della persona
                var spazioIds = ctx.select(Tables.SPAZIO_PERSONA.ID_SPAZIO)
                        .from(Tables.SPAZIO_PERSONA)
                        .where(Tables.SPAZIO_PERSONA.CF.eq(cf))
                        .and(Tables.SPAZIO_PERSONA.COD_PROVINCIA.eq(provinciaPer))
                        .and(Tables.SPAZIO_PERSONA.COD_CITTA_.eq(cittaPer))
                        .and(Tables.SPAZIO_PERSONA.NUMERO.eq(numeroPer))
                        .fetchInto(Long.class);

                if (!spazioIds.isEmpty()) {
                    for (Record record : result) {
                        boolean compatibile = false;

                        for (Long idSpazio : spazioIds) {
                            Integer res = ctx.select(
                                    DSL.function(
                                            "animale_compatibile_spazio",
                                            Integer.class,
                                            DSL.val(record.get(Tables.ANIMALE.COD_PROVINCIA)),
                                            DSL.val(record.get(Tables.ANIMALE.COD_CITTA_)),
                                            DSL.val(record.get(Tables.ANIMALE.NUMERO)),
                                            DSL.val(record.get(Tables.ANIMALE.COD_ANIMALE)),
                                            DSL.val(cf),
                                            DSL.val(idSpazio),
                                            DSL.val(provinciaPer),
                                            DSL.val(cittaPer),
                                            DSL.val(numeroPer),
                                            DSL.val(1)
                                    )
                            ).fetchOneInto(Integer.class);

                            if (res != null && res == 1) {
                                compatibile = true;
                                break;
                            }
                        }

                        if (compatibile) {
                            filtered.add(record);
                        }
                    }
                }
            } else {
                filtered = result.stream().toList();
            }

            if (!result.isEmpty()) {
                // colonne dinamiche
                for (var field : Tables.ANIMALE.fields()) {
                    columnNames.add(field.getName());
                }

                for (Record record : result) {
                    Vector<Object> row = new Vector<>();
                    for (var field : Tables.ANIMALE.fields()) {
                        Object value = record.get(field);

                        if (value == null) {
                            row.add("N/D");
                        } else if (field.getName().equalsIgnoreCase("Idonieta_Animale")) {
                            // se il campo è numerico 0/1 → boolean
                            if (value instanceof Number) {
                                row.add(((Number) value).intValue() == 1);
                            } else {
                                // fallback (in caso di tipo non atteso)
                                row.add(value);
                            }
                        } else {
                            row.add(value);
                        }
                    }
                    data.add(row);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return new DefaultTableModel(data, columnNames) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
    }

    private void resizeColumnWidths(JTable table) {
        final var columnModel = table.getColumnModel();
        for (int column = 0; column < table.getColumnCount(); column++) {
            int width = 50; // larghezza minima
            for (int row = 0; row < table.getRowCount(); row++) {
                TableCellRenderer renderer = table.getCellRenderer(row, column);
                Component comp = table.prepareRenderer(renderer, row, column);
                width = Math.max(comp.getPreferredSize().width + 10, width);
            }

            columnModel.getColumn(column).setPreferredWidth(width);
        }
    }

    private void applyRenderers() {
        TableColumnModel columnModel = view.getTable().getColumnModel();

        for (int i = 0; i < columnModel.getColumnCount(); i++) {
            String colName = view.getTable().getColumnName(i).toLowerCase();

            if (colName.contains("data")) {
                // colonne data → formato + centrato
                columnModel.getColumn(i).setCellRenderer(new DateRenderer());
            } else {
                // tutte le altre colonne → centrato
                columnModel.getColumn(i).setCellRenderer(new CenterRenderer());
            }
        }
    }
}
