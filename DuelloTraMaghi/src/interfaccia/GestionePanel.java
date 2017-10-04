package interfaccia;

import java.awt.FlowLayout;
import java.awt.Graphics;
import part.Wallpaper;
import javax.swing.JPanel;

/**
 *
 * @author VittorioBarile
 */
public class GestionePanel extends JPanel {

    
    
    public Wallpaper sfondo;
    
    

    public GestionePanel() {

        this.sfondo = new Wallpaper();
    }
    
    @Override
    public void paintComponent(Graphics g) {

        super.paintComponent(g);
        this.sfondo.draw(g);
        g.dispose();
    }

    
}
