package part;

import java.awt.Graphics;
import java.awt.Image;
import javax.swing.ImageIcon;

/**
 *
 * @author VittorioBarile
 */
public class Wallpaper {
    
    private Image sfondo;
    
    public Wallpaper() {
        
        this.sfondo = new ImageIcon(getClass().getResource("/immagini/sfondo.jpg")).getImage();
    }
    
    public void draw(Graphics g) {
        g.drawImage(sfondo, 0, 0, null);
    }
    
}
