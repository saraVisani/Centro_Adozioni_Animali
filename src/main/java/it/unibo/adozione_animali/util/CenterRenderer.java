package it.unibo.adozione_animali.util;

import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.SwingConstants;

public class CenterRenderer extends DefaultTableCellRenderer {
    public CenterRenderer() {
        setHorizontalAlignment(SwingConstants.CENTER);
    }
}
