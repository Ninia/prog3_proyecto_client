package ud.binmonkey.prog3_proyecto_client.common;

import java.awt.Graphics;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

/**
 * Clase que extiende de JPanel y permite poner una imagen como fondo.
 *
 */

public class JPanelBackground extends JPanel {

    private Image background;

    public void paintComponent(Graphics g) {

        int width = this.getSize().width;
        int height = this.getSize().height;

        if (this.background != null) {
            g.drawImage(this.background, 0, 0, width, height, null);
        }

        super.paintComponent(g);
    }

    public void setImageBackground(String imagePath) {

        this.setOpaque(false);
        this.background = new ImageIcon(imagePath).getImage();
        repaint();
    }

}