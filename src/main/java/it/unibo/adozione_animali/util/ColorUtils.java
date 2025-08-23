package it.unibo.adozione_animali.util;

import java.awt.Color;

public class ColorUtils {
    public static Color fromHex(String hex) {
        if (hex.startsWith("#")) {
            hex = hex.substring(1);
        } else if (hex.startsWith("0x") || hex.startsWith("0X")) {
            hex = hex.substring(2);
        }

        // Expand 3-digit hex (e.g., "F80") to 6-digit ("FF8800")
        if (hex.length() == 3) {
            hex = "" + hex.charAt(0) + hex.charAt(0)
                        + hex.charAt(1) + hex.charAt(1)
                        + hex.charAt(2) + hex.charAt(2);
        }

        // Now decode the 6-digit hex
        return Color.decode("0x" + hex);
    }
}

