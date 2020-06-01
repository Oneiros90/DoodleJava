package oneiros.sound;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import javax.sound.sampled.*;

/**
 * La classe Sound modella, riproduce e gestisce un suono in formato WAVE.
 *
 * @author Lorenzo
 */
public class WAVE {

    private static final Line.Info INFO = new Line.Info(Clip.class);
    private URL soundUrl;
    private Clip readyClip;

    /**
     * Crea un nuovo suono a partire dal path assoluto di un file WAVE.
     *
     * @param path Il path assoluto di un file WAVE.
     * @throws SoundException Se il path non risulta puntare ad un corretto file
     * WAVE
     */
    public WAVE(String path) throws SoundException {
        this(pathToURL(path));
    }

    /**
     * Crea un nuovo suono a partire da un file WAVE
     *
     * @param url Il file WAVE
     * @throws SoundException Se il file non risulta essere un corretto file
     * WAVE
     */
    public WAVE(URL url) throws SoundException {
        if (url == null) {
            throw new SoundException("Cannot read " + url.getPath());
        }
        this.soundUrl = url;
        this.readyClip = this.getNewClip();
    }

    /**
     * Riproduce una volta il suono
     *
     * @return Un oggetto di tipo Clip che permette di gestire il suono
     */
    public Clip play() {
        return play(1);
    }

    /**
     * Riproduce all'infinito il suono
     *
     * @return Un oggetto di tipo Clip che permette di gestire il suono
     */
    public Clip loop() {
        return play(-1);
    }

    /**
     * Riproduce n volte il suono
     *
     * @param times Il numero di volte da riprodurre il suono (un numero
     * negativo provoca una riproduzione in loop)
     * @return Un oggetto di tipo Clip che permette di gestire il suono
     */
    public Clip play(int times) {
        Clip clip = null;
        try {
            clip = getNewClip();
        } catch (SoundException ex) {
            throw new RuntimeException(ex);
        }
        if (clip != null && times != 0) {
            clip.loop(times - 1);
        }
        return clip;
    }

    /**
     * Crea e restituisce un nuovo Clip del suono, pronto per essere riprodotto
     *
     * @return Un oggetto di tipo Clip che permette di gestire il suono
     * @throws SoundException Se non è possibile ottenere un Clip dal suono
     */
    public final Clip getNewClip() throws SoundException {
        try {
            if (this.readyClip == null) {
                this.readyClip = WAVE.getNewClip(this.soundUrl);
            }
            Clip c = this.readyClip;
            this.readyClip = WAVE.getNewClip(this.soundUrl);
            return c;
        } catch (SoundException ex) {
            this.readyClip = null;
            throw ex;
        }
    }

    /**
     * Crea e restituisce un nuovo Clip di un file WAVE, pronto per essere
     * riprodotto
     *
     * @param clipURL Il file WAVE dal quale ottenere un Clip
     * @return Un oggetto di tipo Clip che permette di gestire il suono
     * @throws SoundException Se non è possibile ottenere un Clip dal file
     */
    public static Clip getNewClip(URL clipURL) throws SoundException {
        Clip clip = null;
        try {
            clip = (Clip) AudioSystem.getLine(INFO);
            clip.open(AudioSystem.getAudioInputStream(clipURL));
        } catch (LineUnavailableException | UnsupportedAudioFileException | IOException ex) {
            throw new SoundException(clipURL.getFile(), ex);
        }
        return clip;
    }

    private static URL pathToURL(String path) {
        try {
            return new File(path).toURI().toURL();
        } catch (MalformedURLException ex) {
            ex.printStackTrace(System.err);
            return null;
        }
    }

    public static class SoundException extends Exception {

        SoundException(String string) {
            super(string);
        }

        public SoundException(String fileName, Throwable cause) {
            super("Cannot read/play " + fileName + ": " + cause.getMessage());
        }

        public SoundException(Throwable cause) {
            super(cause);
        }
    }
}