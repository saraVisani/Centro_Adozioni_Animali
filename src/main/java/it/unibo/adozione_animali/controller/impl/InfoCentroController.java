package it.unibo.adozione_animali.controller.impl;

import java.awt.Color;
import java.awt.Component;
import java.sql.Connection;
import java.util.Vector;
import javax.swing.JTable;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumnModel;
import javax.swing.table.JTableHeader;

import org.jooq.DSLContext;
import org.jooq.Field;
import org.jooq.impl.DSL;

import it.unibo.adozione_animali.util.CenterRenderer;
import it.unibo.adozione_animali.util.DBConfig;
import it.unibo.adozione_animali.util.DateRenderer;
import it.unibo.adozione_animali.view.pulsanti.centri.Info;
import nu.studer.sample.Tables;

public class InfoCentroController {
    private final Info view;

    public InfoCentroController(final Info view) {
        this.view = view;
        Vector<String> columnNames = new Vector<>();
        Vector<Vector<Object>> data = new Vector<>();

        try (Connection conn = DBConfig.getConnection()) {
            DSLContext ctx = DSL.using(conn);

            var result = ctx.select(Tables.CENTRO, Tables.SPECIE.NOME_SPECIE)
                            .from(Tables.CENTRO)
                            .join(Tables.TIPOLOGIA)
                            .on(Tables.CENTRO.COD_PROVINCIA.eq(Tables.TIPOLOGIA.COD_PROVINCIA))
                            .and(Tables.CENTRO.COD_CITTA_.eq(Tables.TIPOLOGIA.COD_CITTA_))
                            .and(Tables.CENTRO.NUMERO.eq(Tables.TIPOLOGIA.NUMERO))
                            .join(Tables.SPECIE)
                            .on(Tables.TIPOLOGIA.COD_SPECIE.eq(Tables.SPECIE.COD_SPECIE))
                            .fetch();

            if (result.isNotEmpty()) {
                Field<?>[] fields = result.fields();
                for (Field<?> f : fields) columnNames.add(f.getName());

                for (var record : result) {
                    Vector<Object> row = new Vector<>();
                    for (Field<?> f : fields) row.add(record.get(f));
                    data.add(row);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(view, "Errore caricamento dati", "Errore", JOptionPane.ERROR_MESSAGE);
        }

        // JTable solo lettura
        view.getTable().setModel(new DefaultTableModel(data, columnNames) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        });

        // Centrazione e formattazione date
        applyRenderers();

        // Header in grassetto
        JTableHeader header = view.getTable().getTableHeader();
        header.setFont(header.getFont().deriveFont(java.awt.Font.BOLD));

        // Colorazione righe alternate
        view.getTable().setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value,
                    boolean isSelected, boolean hasFocus, int row, int column) {
                super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                setBackground(row % 2 == 0 ? Color.WHITE : new Color(230, 230, 255));
                setForeground(Color.BLACK);
                setHorizontalAlignment(CENTER);
                return this;
            }
        });

        // Resize colonne
        resizeColumnWidths(view.getTable());
        view.getTable().setAutoResizeMode(JTable.AUTO_RESIZE_SUBSEQUENT_COLUMNS);
    }

    private void resizeColumnWidths(JTable table) {
        final TableColumnModel columnModel = table.getColumnModel();
        for (int column = 0; column < table.getColumnCount(); column++) {
            int width = 50;
            for (int row = 0; row < table.getRowCount(); row++) {
                TableCellRenderer renderer = table.getCellRenderer(row, column);
                Component comp = table.prepareRenderer(renderer, row, column);
                width = Math.max(comp.getPreferredSize().width + 10, width);
            }
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
                columnModel.getColumn(i).setCellRenderer(new DateRenderer());
            } else {
                columnModel.getColumn(i).setCellRenderer(new CenterRenderer());
            }
        }
    }
}
