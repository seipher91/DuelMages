package part;

import java.awt.Graphics;
import java.awt.Image;
import javax.swing.ImageIcon;

/**
 *
 * @author VittorioBarile
 */
public class Spell {
    
    private Image fireball;
   //private ImageIcon frostbolt;
   //private String pathfrostbolt = "/immagini/fireball.jpg";
    
    public Spell() {
        
        this.fireball = new ImageIcon(getClass().getResource("/immagini/fireball.jpg")).getImage();
        //this.frostbolt = new ImageIcon(pathfrostbolt);
    }
    
    public void draw(Graphics g) {
        g.drawImage(this.fireball, 0, 0, null);
    }
    
}
