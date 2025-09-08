package it.unibo.adozione_animali.controller.impl;

import java.sql.Connection;
import java.util.Vector;

import java.awt.Color;
import java.awt.Component;

import javax.swing.*;
import javax.swing.table.*;

import org.jooq.DSLContext;
import org.jooq.Field;
import org.jooq.impl.DSL;

import it.unibo.adozione_animali.util.CenterRenderer;
import it.unibo.adozione_animali.util.DBConfig;
import it.unibo.adozione_animali.util.DateRenderer;
import it.unibo.adozione_animali.view.animale.AnimaliGenerali;
import nu.studer.sample.Tables;

public class InfoAnimaliController {
    AnimaliGenerali view;

    public InfoAnimaliController (AnimaliGenerali view){
        this.view = view;
        Vector<String> columnNames = new Vector<>();
        Vector<Vector<Object>> data = new Vector<>();

        try (Connection conn = DBConfig.getConnection()) {
            DSLContext ctx = DSL.using(conn);

            var result = ctx.select()
                            .from(Tables.ANIMALI_COMPLETI)
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
                setHorizontalAlignment(CENTER);
                return this;
            }
        });

        // Resize colonne
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
