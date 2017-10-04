package interfaccia;

import java.awt.*;
import java.awt.image.BufferedImage;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

/**
 *
 * @author VittorioBarile
 */
public class GestioneFrame extends JFrame {
    
    private static final int LARGHEZZA = 800;
    private static final int ALTEZZA = 600;
    private static final String NOME_GIOCO = "Duello tra maghi";
    
    
    
    public GestioneFrame() {
        
        JFrame finestra_gioco = new JFrame(NOME_GIOCO);
        
        Dimension dimensione_finestra = new Dimension(LARGHEZZA, ALTEZZA);
        
        finestra_gioco.setPreferredSize(dimensione_finestra);
        finestra_gioco.setMaximumSize(dimensione_finestra);
        finestra_gioco.setResizable(false);
        finestra_gioco.setAlwaysOnTop(true);
        finestra_gioco.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        
        
        
        GestionePanel panel = new GestionePanel();
        Container contentPane = finestra_gioco.getContentPane();
        
        
        //come cazzo faccio a mettere un bottone ed un panel?       
        GestioneBottoni button = new GestioneBottoni();
        contentPane.add(button);
        button.requestFocus();
        
        
        contentPane.add(panel);
        
        panel.requestFocus();
        
        //ridimensiona in base alle condizioni ottimali
        finestra_gioco.pack();
        finestra_gioco.setVisible(true);
              
    }
    
    
}
