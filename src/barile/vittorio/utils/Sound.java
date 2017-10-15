package barile.vittorio.utils;


import lombok.experimental.UtilityClass;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.rmi.server.ExportException;
import java.util.HashMap;
import javax.sound.sampled.*;

/**
 * La classe Sound modella, riproduce e gestisce un suono in formato WAVE.
 *
 * @author Vittorio Barile
 */
@UtilityClass
public class Sound {

    public static Clip getClip(File file) {
        Clip clip = null;
        if(!file.exists()) return clip;

        try {
            InputStream fileStream = new FileInputStream(file);

            InputStream bufferedIn = new BufferedInputStream(fileStream);
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(bufferedIn);

            Line.Info linfo = new Line.Info(Clip.class);
            Line line = AudioSystem.getLine(linfo);
            clip = (Clip) line;
            clip.open(audioInputStream);

        } catch (Exception ex) {
            ex.printStackTrace(System.err);
            System.exit(-1);
        }

        if(clip == null) return clip;

        clip.stop();
        return clip;
    }

    public static Clip getClip(String path) {
        return getClip(new File(path));
    }

    public static void setVolume(Clip clip, float volume) {
        FloatControl control = null;

        //FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
        //gainControl.setValue(20f * (float) Math.log10(volume));

        if (clip.isControlSupported(FloatControl.Type.VOLUME))
            control = (FloatControl) clip.getControl(FloatControl.Type.VOLUME);

        if (clip.isControlSupported(FloatControl.Type.MASTER_GAIN))
            control = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);

        if(control == null) {
            System.out.println("Impossibile settare il volume!");
            return;
        }

        volume = (control.getMaximum()/100) * volume;
        control.setValue(volume);
    }

}