package barile.vittorio.ui;

import barile.vittorio.engine.Spell;
import barile.vittorio.utils.Resources;
import lombok.experimental.Delegate;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Created by Vittorio on 11/10/2017.
 * Classe per tentare di far vedere la spell castata
 * TO DO -
 */
public class SpellFrames extends JPanel {

    private final int sprite_w = 30;
    private final int sprite_h = 30;

    @Delegate
    private Spell spell;
    private boolean is_enemy_spell;

    private final BufferedImage sp;

    private int status;
    private int current_moment;
    private final String resource_path = "/assets/images/mage_bronze.jpg";

    public SpellFrames(){
        super();
        setBackground(null);
        setOpaque(false);
        setSize(30, 30);

        this.sp = Resources.getImage(this.resource_path);
        this.status = 0;
        this.current_moment = 0;
    }

    public void setAsEnemy() {
        this.is_enemy_spell = true;
    }

    public void setStatus(int status) {
        this.status = status;
        current_moment = 0;
    }

    private void updateStatus() {
        current_moment++;
    }

    private BufferedImage getBody() {
        int x = 0,
                y = 0,
                w = sprite_w,
                h = sprite_h;
        int result = 0;
        int offscan = 0;


        return this.sp.getSubimage(x, y, w, h);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        BufferedImage sp = getBody();

        /*
        if(is_enemy) {
            AffineTransform tx = AffineTransform.getScaleInstance(-1, 1);
            tx.translate(-pg.getWidth(null), 0);
            AffineTransformOp op = new AffineTransformOp(tx, AffineTransformOp.TYPE_NEAREST_NEIGHBOR);
            pg = op.filter(pg, null);
        }
        */

        int x = 0;
        int y = 0;

        int width = getWidth();
        int height = getHeight();


        width = 130;
        height = 130;

        if(sp.getWidth() != sprite_w) {
            width = (height * sp.getWidth()) / sprite_h;
        }
        if(sp.getHeight() != sprite_h) {
            height = (width * sp.getHeight()) / sprite_w;
        }

        g.drawImage(sp,
                x, y,
                width, height,
                this);

        updateStatus();
    }
}
