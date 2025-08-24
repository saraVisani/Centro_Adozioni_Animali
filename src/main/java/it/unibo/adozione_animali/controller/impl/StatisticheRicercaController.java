package it.unibo.adozione_animali.controller.impl;

import org.jooq.DSLContext;
import org.jooq.Field;
import org.jooq.impl.DSL;

import javax.swing.table.DefaultTableModel;
import java.sql.Connection;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Vector;

import it.unibo.adozione_animali.util.DBConfig;
import it.unibo.adozione_animali.view.statistiche.StatisticheRicerca;
import nu.studer.sample.Tables;

public class StatisticheRicercaController {

    private final StatisticheRicerca view;

    public StatisticheRicercaController(StatisticheRicerca view) {
        this.view = view;

        view.getBtnCerca().addActionListener(e -> search());
        search();
    }

    private void search() {
        String nome = view.getTxtNomeStatistica().getText();
        LocalDate start = view.getStartDate();
        LocalDate end = view.getEndDate();

        DefaultTableModel tableModel = getTableModel(nome, start, end);
        view.getTable().setModel(tableModel);
    }

    private DefaultTableModel getTableModel(String nome, LocalDate start, LocalDate end) {
        Vector<String> columnNames = new Vector<>();
        Vector<Vector<Object>> data = new Vector<>();

        try (Connection conn = DBConfig.getConnection()) {
            DSLContext ctx = DSL.using(conn);

            var query = ctx.select()
                        .from(Tables.STATISTICA)
                        .join(Tables.DATO)
                        .on(Tables.STATISTICA.CODICE.eq(Tables.DATO.COD_STATISTICA));

            if (nome != null && !nome.isEmpty()) {
                query.where(Tables.STATISTICA.NOME.eq(nome));
            }

            if (start != null) {
                query.where(Tables.STATISTICA.DATA_STATISTICA.ge(start));
            }

            if (end != null) {
                query.where(Tables.STATISTICA.DATA_STATISTICA.le(end));
            }

            // **Ordinamento per data e poi per nome**
            query.orderBy(Tables.STATISTICA.DATA_STATISTICA.desc(),
                        Tables.STATISTICA.NOME.asc());

            var result = query.fetch();

            if (result.isNotEmpty()) {
                Field<?>[] fields = Tables.STATISTICA.fields();
                Arrays.stream(fields).forEach(f -> {
                    System.out.println(f.getName());
                });
                for (var record : result) {
                    Vector<Object> row = new Vector<>();
                    for (String colName : columnNames) {
                        row.add(record.get(colName));
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
