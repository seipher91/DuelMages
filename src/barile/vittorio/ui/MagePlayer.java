package barile.vittorio.ui;

import barile.vittorio.entites.Mage;
import barile.vittorio.ui.interfaces.OnVitalityEventListener;
import barile.vittorio.utils.Resources;
import lombok.Setter;
import lombok.experimental.Delegate;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

/**
 * Created by Vittorio on 08/10/2017.
 */
public class MagePlayer extends JPanel {
    public static final int STATUS_IDLE = 0;
    public static final int STATUS_CHARGE = 1;
    public static final int STATUS_ATTACK = 2;
    public static final int STATUS_DAMAGED = 3;
    public static final int STATUS_WIN = 4;
    public static final int STATUS_LOSE = 5;

    private final int sprite_w = 72;
    private final int sprite_h = 72;

    @Delegate
    private Mage mage;
    private boolean is_enemy;

    private final BufferedImage img;

    private int status;
    private int current_moment;

    private ArrayList<Integer> statuses;

    public MagePlayer(String name, String accademic_class, String resource_path, OnVitalityEventListener listener) {
        super();
        setBackground(null);
        setOpaque(false);
        setSize(400, 400);

        this.img = Resources.getImage(resource_path);

        this.mage = new Mage(name, accademic_class, listener);
        this.is_enemy = false;

        this.status = STATUS_IDLE;
        this.current_moment = 0;

        this.statuses = new ArrayList<>();
    }

    public void setAsEnemy() {
        this.is_enemy = true;
    }

    public void addStatus(int status) {
        this.statuses.add(status);
        if(this.status == STATUS_IDLE) goNextStatus();
    }

    private void goNextStatus() {
        int next_status = STATUS_IDLE;

        if(!statuses.isEmpty())
            next_status = this.statuses.remove(0);

        setStatus(next_status);
    }

    private void setStatus(int status) {
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

        switch (status) {
            default:
            case STATUS_IDLE:
                result = current_moment % 3;

                offscan = w+4;
                x = 48 + (offscan*result);
                y = 14;
                break;
            case STATUS_CHARGE:
                break;
            case STATUS_ATTACK:
                result = current_moment % 3;

                switch (result) {
                    case 0:
                        offscan = 0;
                        break;
                    case 1:
                        offscan = w-3;
                        break;
                    case 2:
                        offscan = w;
                        w = w*2;
                        goNextStatus();
                        break;
                }

                x = 420 + (offscan * result);
                y = 95;
                break;
            case STATUS_DAMAGED:
                result = current_moment % 3;

                switch (result) {
                    case 0:
                        w = w;
                        offscan = 1;
                        break;
                    case 1:
                        w = w-3;
                        offscan = w-3;
                        break;
                    case 2:
                        offscan = w-8;
                        goNextStatus();
                        break;
                }

                x = 10 + (offscan * result);
                y = 375;
                break;
            case STATUS_WIN:
                result = current_moment % 7;
                offscan = w - 5;
                x = 540 + (offscan * result);
                y = 250;
                break;
            case STATUS_LOSE:
                result = current_moment % 4;

                switch (result) {
                    case 0:
                        offscan = w;
                        break;
                    case 1:
                        offscan = w + 5;
                        break;
                    case 2:
                        offscan = w + 20;
                        break;
                    case 3:
                        offscan = w - 30;
                        goNextStatus();
                        break;
                }

                x = 730 + (offscan * result);
                y = 550;
                break;
        }

        return this.img.getSubimage(x, y, w, h);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        BufferedImage pg = getBody();

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

        if(pg.getWidth() != sprite_w) {
            width = (height * pg.getWidth()) / sprite_h;
        }
        if(pg.getHeight() != sprite_h) {
            height = (width * pg.getHeight()) / sprite_w;
        }

        g.drawImage(pg,
            x, y,
            width, height,
            this);

        updateStatus();
    }

}
