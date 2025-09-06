package it.unibo.adozione_animali.controller.impl;

import java.sql.Connection;
import java.util.Vector;

import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumnModel;

import org.jooq.DSLContext;
import org.jooq.Field;
import org.jooq.impl.DSL;

import it.unibo.adozione_animali.model.impl.Model;
import it.unibo.adozione_animali.util.CenterRenderer;
import it.unibo.adozione_animali.util.DBConfig;
import it.unibo.adozione_animali.util.DateRenderer;
import it.unibo.adozione_animali.view.pulsanti.spazio.Info;
import nu.studer.sample.Tables;

public class InfoSpazioController {

    final Info view;

    public InfoSpazioController(final Info view){
        this.view = view;
        Vector<String> columnNames = new Vector<>();
        Vector<Vector<Object>> data = new Vector<>();

        try (Connection conn = DBConfig.getConnection()) {
            DSLContext ctx = DSL.using(conn);

            var result = ctx.select()
                            .from(Tables.SPAZIO)
                            .fetch();

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

        view.getTable().setModel(new DefaultTableModel(data, columnNames) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        });
        applyRenderers();
        resizeColumnWidths(view.getTable());
        view.getTable().setAutoResizeMode(JTable.AUTO_RESIZE_SUBSEQUENT_COLUMNS);
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
