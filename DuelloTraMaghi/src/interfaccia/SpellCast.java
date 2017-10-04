/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package interfaccia;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.JPanel;
import javax.swing.Timer;
/**
 *
 * @author Vittorio
 */
public class SpellCast extends Sprite {
    
    

    private final int BOARD_WIDTH = 390;
    private final int SPELL_SPEED = 2;

    public SpellCast(int x, int y) {
        super(x, y);
        
        initMissile();
    }
    
    private void initMissile() {
        
        loadImage("mages spell.png");  
        getImageDimensions();
    }


    public void move() {
        
        x += SPELL_SPEED;
        
        if (x > BOARD_WIDTH) {
            vis = false;
        }
    }
}
    
