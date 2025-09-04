package it.unibo.adozione_animali.controller.impl;

import org.jooq.DSLContext;
import org.jooq.Field;
import org.jooq.impl.DSL;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumnModel;

import java.awt.Component;
import java.sql.Connection;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Vector;

import it.unibo.adozione_animali.util.CenterRenderer;
import it.unibo.adozione_animali.util.DBConfig;
import it.unibo.adozione_animali.util.DateRenderer;
import it.unibo.adozione_animali.util.Enum.NomeStatistica;
import it.unibo.adozione_animali.view.statistiche.StatisticheRicerca;
import nu.studer.sample.Tables;

public class StatisticheRicercaController {

    private final StatisticheRicerca view;

    public StatisticheRicercaController(StatisticheRicerca view) {
        this.view = view;

        List<String> descrizioni = Arrays.stream(NomeStatistica.values())
                                        .map(NomeStatistica::getDescrizione)
                                        .toList();

        this.view.setTipo(descrizioni);

        view.getBtnCerca().addActionListener(e -> search());
        search();
    }

    private void search() {
        String nome = view.getTipo();
        LocalDate start = view.getStartDate();
        LocalDate end = view.getEndDate();

        DefaultTableModel tableModel = getTableModel(nome, start, end);
        view.getTable().setModel(tableModel);
        applyRenderers();
        resizeColumnWidths(view.getTable());
        view.getTable().setAutoResizeMode(JTable.AUTO_RESIZE_SUBSEQUENT_COLUMNS);
    }

    private DefaultTableModel getTableModel(String nome, LocalDate start, LocalDate end) {
        Vector<String> columnNames = new Vector<>();
        Vector<Vector<Object>> data = new Vector<>();

        try (Connection conn = DBConfig.getConnection()) {
            DSLContext ctx = DSL.using(conn);

            var query = ctx.select(Tables.STATISTICA.CODICE, Tables.STATISTICA.DATA_STATISTICA,
                                    Tables.STATISTICA.NOME, Tables.DATO.CODICE, Tables.DATO.NOME,
                                    Tables.DATO.VALORE)
                    .from(Tables.STATISTICA)
                    .join(Tables.DATO)
                    .on(Tables.STATISTICA.CODICE.eq(Tables.DATO.COD_STATISTICA))
                        .and(Tables.STATISTICA.DATA_STATISTICA.eq(Tables.DATO.DATA_STATISTICA));

            if (nome != null && !nome.isEmpty() && !nome.equals("--select--")) {
                query.where(Tables.STATISTICA.NOME.eq(nome));
            }

            if (start != null) {
                query.where(Tables.STATISTICA.DATA_STATISTICA.ge(start));
            }

            if (end != null) {
                query.where(Tables.STATISTICA.DATA_STATISTICA.le(end));
            }

            query.orderBy(Tables.STATISTICA.DATA_STATISTICA.desc(),
                        Tables.STATISTICA.NOME.asc());

            var result = query.fetch();

            if (result.isNotEmpty()) {
                // intestazioni
                Field<?>[] fields = result.fields();
                for (Field<?> f : fields) {
                    columnNames.add(f.getName());
                }

                // righe
                for (var record : result) {
                    Vector<Object> row = new Vector<>();
                    for (Field<?> f : fields) {
                        row.add(record.get(f));
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
            // anche header
            TableCellRenderer headerRenderer = table.getTableHeader().getDefaultRenderer();
            Component headerComp = headerRenderer.getTableCellRendererComponent(
                    table, table.getColumnName(column), false, false, 0, column);
            width = Math.max(width, headerComp.getPreferredSize().width + 10);

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
