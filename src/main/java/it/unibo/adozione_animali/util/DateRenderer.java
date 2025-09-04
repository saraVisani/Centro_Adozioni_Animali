package it.unibo.adozione_animali.util;

import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class DateRenderer extends DefaultTableCellRenderer {
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public DateRenderer() {
        setHorizontalAlignment(SwingConstants.CENTER);
    }

    @Override
    protected void setValue(Object value) {
        if (value instanceof LocalDate date) {
            setText(date.format(formatter));
        } else {
            super.setValue(value);
        }
    }
}

