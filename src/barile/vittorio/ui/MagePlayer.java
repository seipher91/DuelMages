package barile.vittorio.ui;

import barile.vittorio.entites.Mage;
import barile.vittorio.ui.interfaces.OnVitalityEventListener;
import barile.vittorio.utils.Resources;
import lombok.NonNull;
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
        //setBackground(Color.white);
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

    public void restore() {
        this.statuses.clear();
        this.status = STATUS_IDLE;
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
        int offscan_w = 0;
        int offscan_h = 0;

        switch (status) {
            default:
            case STATUS_IDLE:
                result = current_moment % 3;

                offscan_w = w+4;
                x = 48 + (offscan_w*result);
                y = 14;
                break;
            case STATUS_CHARGE:
                break;
            case STATUS_ATTACK:
                result = current_moment % 3;

                switch (result) {
                    case 0:
                        offscan_w = 0;
                        break;
                    case 1:
                        offscan_w = w-3;
                        break;
                    case 2:
                        offscan_w = w;
                        w = w*2;
                        goNextStatus();
                        break;
                }

                x = 420 + (offscan_w * result);
                y = 95;
                break;
            case STATUS_DAMAGED:
                result = current_moment % 3;

                switch (result) {
                    case 0:
                        w = w;
                        offscan_w = 1;
                        break;
                    case 1:
                        w = w-3;
                        offscan_w = w-3;
                        break;
                    case 2:
                        offscan_w = w-8;
                        goNextStatus();
                        break;
                }

                x = 10 + (offscan_w * result);
                y = 375;
                break;
            case STATUS_WIN:
                result = current_moment % 9;
                w = 70;
                h = 150;

                switch (result) {
                    case 0:
                        offscan_w = w - 5;
                        offscan_h = -5;
                        break;
                    case 1:
                        offscan_w = w;
                        offscan_h = -10;
                        break;
                    case 2:
                        offscan_w = w;
                        offscan_h = -10;
                        break;
                    case 3:
                        offscan_w = w;
                        offscan_h = -20;
                        break;
                    case 4:
                        offscan_w = w;
                        offscan_h = -50;
                        h = h*2;
                        break;
                    case 5:
                        offscan_w = w;
                        offscan_h = -10;
                        break;
                    case 6:
                        offscan_w = w;
                        offscan_h = -10;
                        break;
                    case 7:
                        offscan_w = w;
                        offscan_h = -10;
                        break;
                    case 8:
                        offscan_w = w - 2;
                        offscan_h = 10;
                        break;
                    default:
                        offscan_w = w - 5;
                        break;
                }

                x = 540 + (offscan_w * result);
                y = 250 + offscan_h;
                break;
            case STATUS_LOSE:
                result = current_moment % 2;

                switch (result) {
                    case 0:
                        x = 395;
                        y = 4;
                        break;
                    case 1:
                        x = 76;
                        y = 375;
                        break;
                }

                //x = 10 + (offscan_w * result);
                //y = 375;
                break;
        }

        return this.img.getSubimage(x, y, w, h);
    }

    @NonNull
    private Point getBodyLocation() {
        Point location = null;
        int result = 0;
        int x = 0, y = 230;

        switch (status) {
            default:
            case STATUS_IDLE:
            case STATUS_ATTACK:
            case STATUS_DAMAGED:
            case STATUS_CHARGE:
                location = new Point(x, y);
                break;
            case STATUS_WIN:
                result = current_moment % 9;

                switch (result) {
                    case 0:
                        y = y + 10;
                        break;
                    case 1:
                        y = y - 10;
                        x = x + 5;
                        break;
                    case 2:
                        y = y - 50;
                        x = x + 7;
                        break;
                    case 3:
                        y = y - 90;
                        x = x - 5;
                        break;
                    case 4:
                        y = y - 150;
                        break;
                    case 5:
                        x = x + 7;
                        y = y - 85;
                        break;
                    case 6:
                        y = y - 50;
                        x = x + 5;
                        break;
                    case 7:
                        x = x + 10;
                        y = y - 50;
                        break;
                    case 8:
                        y = y + 12;
                        x = x + 5;
                        break;
                }

                //if(result == 4) y = 250;

                location = new Point(x, y);
                break;
            case STATUS_LOSE:
                result = current_moment % 2;

                switch (result) {
                    case 0:
                        break;
                    case 1:
                        x = x + 10;
                        break;
                }

                location = new Point(x, y);
                break;
        }

        if(location == null) location = new Point(x, y);
        return location;
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

        Point location = getBodyLocation();

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
            (int) location.getX(), (int) location.getY(),
            width, height,
            this);

        updateStatus();
    }

}
