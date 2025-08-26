package it.unibo.adozione_animali.view;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.*;

public class ImageViewerPanel extends JPanel {
    private BufferedImage image;

    public ImageViewerPanel(String imagePath) {
        try {
            InputStream is = getClass().getResourceAsStream(imagePath);
            if (is == null) {
                throw new FileNotFoundException("Immagine non trovata: " + imagePath);
            }
            image = ImageIO.read(is);
            is.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (image != null) {
            int panelWidth = getWidth();
            int panelHeight = getHeight();
            int imgWidth = image.getWidth();
            int imgHeight = image.getHeight();

            float widthRatio = (float) panelWidth / imgWidth;
            float heightRatio = (float) panelHeight / imgHeight;
            float scale = Math.min(widthRatio, heightRatio);

            int drawWidth = (int) (imgWidth * scale);
            int drawHeight = (int) (imgHeight * scale);

            int x = (panelWidth - drawWidth) / 2;
            int y = (panelHeight - drawHeight) / 2;

            g.drawImage(image, x, y, drawWidth, drawHeight, this);
        }
    }

    @Override
    public Dimension getPreferredSize() {
        if (image != null) {
            return new Dimension(image.getWidth(), image.getHeight());
        } else {
            return super.getPreferredSize();
        }
    }

}
