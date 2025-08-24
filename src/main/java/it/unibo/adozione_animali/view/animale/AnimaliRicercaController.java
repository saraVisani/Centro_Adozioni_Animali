package it.unibo.adozione_animali.view.animale;

import it.unibo.adozione_animali.model.impl.Model;
import nu.studer.sample.Tables;
import org.jooq.DSLContext;
import org.jooq.Record;
import org.jooq.impl.DSL;

import javax.swing.table.DefaultTableModel;
import java.sql.Connection;
import java.time.LocalDate;
import java.util.List;
import java.util.Vector;

import it.unibo.adozione_animali.util.DBConfig;

public class AnimaliRicercaController {

    private final AnimaliRicerca view;
    private final Model model;

    public AnimaliRicercaController(Model model, AnimaliRicerca view) {
        this.model = model;
        this.view = view;

        view.getBtnCerca().addActionListener(e -> search());
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

    private void search() {
        String nome = view.getTxtNome().getText();
        String specie = (String) view.getComboSpecie().getSelectedItem();
        String stato = (String) view.getComboStato().getSelectedItem();
        LocalDate start = view.getStartDate();
        LocalDate end = view.getEndDate();

        DefaultTableModel tableModel = getTableModel(nome, specie, stato, start, end);
        view.setTableModel(tableModel);
    }

    private DefaultTableModel getTableModel(String nome, String specie, String stato,
                                            LocalDate start, LocalDate end) {
        Vector<String> columnNames = new Vector<>();
        Vector<Vector<Object>> data = new Vector<>();

        try (Connection conn = DBConfig.getConnection()) {
            DSLContext ctx = DSL.using(conn);

            var query = ctx.select()
                    .from(Tables.ANIMALE);

            if (nome != null && !nome.isEmpty()) {
                query.where(Tables.ANIMALE.NOME.eq(nome));
            }
            if (specie != null && !specie.isEmpty()) {
                query.where(Tables.ANIMALE.COD_SPECIE.eq(specie));
            }
            if (stato != null && !stato.isEmpty()) {
                query.where(Tables.ANIMALE.STATO_ATTUALE.eq(stato));
            }
            if (start != null) {
                query.where(Tables.ANIMALE.DATA_INSERIMENTO.ge(start));
            }
            if (end != null) {
                query.where(Tables.ANIMALE.DATA_INSERIMENTO.le(end));
            }

            // Ordinamento: nuove prime
            query.orderBy(Tables.ANIMALE.DATA_INSERIMENTO.desc(), Tables.ANIMALE.NOME.asc());

            var result = query.fetch();

            if (!result.isEmpty()) {
                // colonne dinamiche
                for (var field : Tables.ANIMALE.fields()) {
                    columnNames.add(field.getName());
                }

                for (Record record : result) {
                    Vector<Object> row = new Vector<>();
                    for (var field : Tables.ANIMALE.fields()) {
                        row.add(record.get(field));
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
}
