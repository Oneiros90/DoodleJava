package oneiros.sound;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

public abstract class Sound {

    private URL soundUrl;

    public Sound(String path){
        this(pathToURL(path));
    }

    public Sound(File file){
        this(pathToURL(file.getAbsolutePath()));
    }

    public Sound(URL url) {
        this.soundUrl = url;
    }

    public abstract void play();

    public abstract void play(int times);

    public abstract void loop();
    
    public abstract void pause();
    
    public abstract void stop();

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