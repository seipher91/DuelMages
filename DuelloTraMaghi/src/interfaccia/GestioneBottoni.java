/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package interfaccia;

import part.Spell;
import java.awt.Dimension;
import java.awt.Graphics;
import javax.swing.ImageIcon;
import javax.swing.JButton;

/**
 *
 * @author Vittorio
 */
public class GestioneBottoni extends JButton {
    
        
    public Spell fireball;
    
    public GestioneBottoni(){
        
        this.fireball = new Spell();
        setVisible(true);
        setFocusable(true);
        setContentAreaFilled(false);
        setBorderPainted(true);
        
    }
    
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        this.fireball.draw(g);
        g.dispose();

    }
}
