package oneiros.file;

import java.awt.image.BufferedImage;
import java.io.*;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.security.CodeSource;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Properties;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import javax.imageio.ImageIO;
import oneiros.sound.WAVE;

/**
 * Resources è un gestore delle risorse interne o esterne ad un'applicazione. La
 * classe fornisce diversi metodi per leggere, estrarre o riprodurre testo,
 * immagini, suoni
 *
 * @author Oneiros
 */
public class Resources {

    private static Class SOURCE = Resources.class;
    private static HashMap<URL, Object> MAP = new HashMap<>();

    /**
     * Costruttore privato (Resources è una classe statica)
     */
    private Resources() {
    }

    /**
     * Imposta la classe di riferimento dalla quale leggere le risorse. La
     * classe di riferimento di default è {@link Resources} stessa.
     *
     * @param source la classe di riferimento dalla quale leggere le risorse
     */
    public static void setSourceClass(Class source) {
        Resources.SOURCE = source;
    }
    
    // ========================================================================
    // TESTO
    // ========================================================================

    /**
     * Carica in memoria una stringa di testo. La risorsa verrà memorizzata in una
     * struttura dati HashMap: richiamare uno dei metodi {@link getText()} per
     * accedere alla risorsa.
     *
     * @param path Il path relativo o assoluto della risorsa. Se relativo, la
     * ricerca verrà effettuata nelle risorse interne dell'applicazione.
     * @return La risorsa caricata
     */
    public static String loadText(String path) {
        return loadText(pathToURL(path));
    }

    /**
     * Carica in memoria una stringa di testo. La risorsa verrà memorizzata in una
     * struttura dati HashMap: richiamare uno dei metodi {@link getText()} per
     * accedere alla risorsa.
     *
     * @param file Il file della risorsa
     * @return La risorsa caricata
     */
    public static String loadText(File file) {
        return loadText(fileToURL(file));
    }

    /**
     * Carica in memoria una stringa di testo. La risorsa verrà memorizzata in una
     * struttura dati HashMap: richiamare uno dei metodi {@link getText()} per
     * accedere alla risorsa.
     *
     * @param url L'URL della risorsa
     * @return La risorsa caricata
     */
    public static String loadText(URL url) {
        MAP.remove(url);
        String text = getText(url);
        MAP.put(url, text);
        return text;
    }

    /**
     * Carica in memoria una stringa di testo e la restituisce
     *
     * @param path Il path relativo o assoluto della risorsa. Se relativo, la
     * ricerca verrà effettuata nelle risorse interne dell'applicazione.
     * @return La risorsa caricata
     * @return L'immagine corrispondente al path
     */
    public static String getText(String path) {
        return getText(pathToURL(path));
    }

    /**
     * Carica in memoria una stringa di testo e la restituisce
     *
     * @param file Il file della risorsa
     * @return La risorsa caricata
     */
    public static String getText(File file) {
        return getText(fileToURL(file));
    }

    /**
     * Carica in memoria una stringa di testo e la restituisce
     *
     * @param url L'URL della risorsa
     * @return La risorsa caricata
     */
    public static String getText(URL url) {
        if (MAP.containsKey(url)) {
            Object object = MAP.get(url);
            if (object instanceof String) {
                return (String) object;
            }
        }
        StringBuilder builder = new StringBuilder();

        InputStream byteStream = null;
        InputStreamReader txtStream = null;
        BufferedReader reader = null;
        try {
            byteStream = url.openStream();
            txtStream = new InputStreamReader(byteStream, "ISO-8859-1");
            reader = new BufferedReader(txtStream);
            String line;
            while ((line = reader.readLine()) != null) {
                builder.append(line);
                builder.append(System.getProperty("line.separator"));
            }
        } catch (IOException ex) {
            ex.printStackTrace(System.err);
        } finally {
            try {
                byteStream.close();
                txtStream.close();
                reader.close();
            } catch (Exception e) {
            }
        }

        return builder.toString();
    }
    
    // ========================================================================
    // IMMAGINI
    // ========================================================================

    /**
     * Carica in memoria un'immagine. La risorsa verrà memorizzata in una
     * struttura dati HashMap: richiamare uno dei metodi {@link getImage()} per
     * accedere alla risorsa.
     *
     * @param path Il path relativo o assoluto della risorsa. Se relativo, la
     * ricerca verrà effettuata nelle risorse interne dell'applicazione.
     * @return La risorsa caricata
     */
    public static BufferedImage loadImage(String path) {
        return loadImage(pathToURL(path));
    }

    /**
     * Carica in memoria un'immagine. La risorsa verrà memorizzata in una
     * struttura dati HashMap: richiamare uno dei metodi {@link getImage()} per
     * accedere alla risorsa.
     *
     * @param file Il file della risorsa
     * @return La risorsa caricata
     */
    public static BufferedImage loadImage(File file) {
        return loadImage(fileToURL(file));
    }

    /**
     * Carica in memoria un'immagine. La risorsa verrà memorizzata in una
     * struttura dati HashMap: richiamare uno dei metodi {@link getImage()} per
     * accedere alla risorsa.
     *
     * @param url L'URL della risorsa
     * @return La risorsa caricata
     */
    public static BufferedImage loadImage(URL url) {
        MAP.remove(url);
        BufferedImage image = getImage(url);
        MAP.put(url, image);
        return image;
    }

    /**
     * Carica in memoria un'immagine e la restituisce
     *
     * @param path Il path relativo o assoluto della risorsa. Se relativo, la
     * ricerca verrà effettuata nelle risorse interne dell'applicazione.
     * @return La risorsa caricata
     * @return L'immagine corrispondente al path
     */
    public static BufferedImage getImage(String path) {
        return getImage(pathToURL(path));
    }

    /**
     * Carica in memoria un'immagine e la restituisce
     *
     * @param file Il file della risorsa
     * @return La risorsa caricata
     */
    public static BufferedImage getImage(File file) {
        return getImage(fileToURL(file));
    }

    /**
     * Carica in memoria un'immagine e la restituisce
     *
     * @param url L'URL della risorsa
     * @return La risorsa caricata
     */
    public static BufferedImage getImage(URL url) {
        if (MAP.containsKey(url)) {
            Object object = MAP.get(url);
            if (object instanceof BufferedImage) {
                return (BufferedImage) object;
            }
        }
        BufferedImage image = null;
        try {
            image = ImageIO.read(url);
        } catch (Exception ex) {
            ex.printStackTrace(System.err);
        }
        return image;
    }
    
    // ========================================================================
    // SUONI
    // ========================================================================

    /**
     * Carica in memoria un file audio. La risorsa verrà memorizzata in una
     * struttura dati HashMap: richiamare uno dei metodi {@link getSound()} per
     * accedere alla risorsa.
     *
     * @param path Il path relativo o assoluto della risorsa. Se relativo, la
     * ricerca verrà effettuata nelle risorse interne dell'applicazione.
     * @return La risorsa caricata
     */
    public static WAVE loadSound(String path) {
        return loadSound(pathToURL(path));
    }

    /**
     * Carica in memoria un file audio. La risorsa verrà memorizzata in una
     * struttura dati HashMap: richiamare uno dei metodi {@link getSound()} per
     * accedere alla risorsa.
     *
     * @param file Il file della risorsa
     * @return La risorsa caricata
     */
    public static WAVE loadSound(File file) {
        return loadSound(fileToURL(file));
    }

    /**
     * Carica in memoria un file audio. La risorsa verrà memorizzata in una
     * struttura dati HashMap: richiamare uno dei metodi {@link getSound()} per
     * accedere alla risorsa.
     *
     * @param url L'URL della risorsa
     * @return La risorsa caricata
     */
    public static WAVE loadSound(URL url) {
        MAP.remove(url);
        WAVE sound = getSound(url);
        MAP.put(url, sound);
        return sound;
    }

    /**
     * Carica in memoria un file audio e lo restituisce
     *
     * @param path Il path relativo o assoluto della risorsa. Se relativo, la
     * ricerca verrà effettuata nelle risorse interne dell'applicazione.
     * @return La risorsa caricata
     * @return L'immagine corrispondente al path
     */
    public static WAVE getSound(String path) {
        return getSound(pathToURL(path));
    }

    /**
     * Carica in memoria un file audio e lo restituisce
     *
     * @param file Il file della risorsa
     * @return La risorsa caricata
     */
    public static WAVE getSound(File file) {
        return getSound(fileToURL(file));
    }

    /**
     * Carica in memoria un file audio e lo restituisce
     *
     * @param url L'URL della risorsa
     * @return La risorsa caricata
     */
    public static WAVE getSound(URL url) {
        if (MAP.containsKey(url)) {
            Object object = MAP.get(url);
            if (object instanceof WAVE) {
                return (WAVE) object;
            }
        }
        WAVE sound = null;
        try {
            sound = new WAVE(url);
        } catch (Exception ex) {
            ex.printStackTrace(System.err);
        }
        return sound;
    }

    public static Properties getProperties(String propertiesPath) {
        Properties data = new Properties();
        InputStream input = null;
        try {
            input = getResourceAsStream(propertiesPath);
            data.load(input);
            return data;
        } catch (IOException ex) {
            ex.printStackTrace(System.err);
            return null;
        } finally {
            try {
                input.close();
            } catch (Exception e) {
            }
        }
    }

    /**
     * Estrae un file interno al jar nella stessa cartella del jar e ne
     * restituisce il path. Nel caso in cui il programma risulti non essere
     * eseguito da un jar ma da un file .class, il metodo ritorna direttamente
     * il path del file presente nelle risorse del file .class
     *
     * @param path Il path del file interno al jar
     * @return Il path del file estratto
     */
    public static File extract(String path) throws IOException {
        return extract(path, null);
    }

    /**
     * Estrae un file interno al jar nella cartella specificata e ne restituisce
     * il path. Nel caso in cui il programma risulti non essere eseguito da un
     * jar ma da un file .class, il metodo ritorna direttamente il path del file
     * presente nelle risorse del file .class
     *
     * @param source Il path del file interno al jar
     * @param destination Il path della cartella di destinazione
     * @return Il path del file estratto
     */
    public static File extract(String source, File destination) throws IOException {
        if (source.startsWith("/")) {
            source = source.substring(1);
        }
        File homeDir = getHomeDir();
        if (destination == null) {
            destination = homeDir;
        }

        InputStream input;
        if (isRunningFromJar()) {
            JarFile jar = new JarFile(getRunningSource());
            JarEntry sourceFile = jar.getJarEntry(source);
            if (sourceFile == null) {
                throw new FileNotFoundException(source);
            }
            if (sourceFile.isDirectory()) {
                Enumeration e = jar.entries();
                while (e.hasMoreElements()) {
                    JarEntry file = (JarEntry) e.nextElement();
                    String path = file.getName();
                    if (path.startsWith(source) && !file.isDirectory()) {
                        path = path.replaceFirst(source, "");
                        copy(file, new File(destination, path), jar);
                    }

                }
            } else {
                String path = sourceFile.getName();
                path = path.replaceFirst(source, "");
                copy(sourceFile, new File(destination, path), jar);
            }
        } else {
            File sourceFile = new File(homeDir, source);
            input = new FileInputStream(sourceFile);
            String fileName = sourceFile.getName();
            copy(input, new File(destination, fileName), true);
        }
        return destination;
    }

    private static void copy(JarEntry file, File destination, JarFile jar) throws IOException {
        InputStream input = jar.getInputStream(file);
        String path = file.getName();
        if (input == null) {
            throw new FileNotFoundException(path);
        }
        copy(input, destination, true);
    }

    private static void copy(InputStream input, File destination, boolean overwrite) throws IOException {
        if (!destination.exists() || overwrite) {
            OutputStream output = null;
            try {
                destination.getParentFile().mkdirs();
                output = new FileOutputStream(destination);
                while (input.available() > 0) {
                    output.write(input.read());
                }
            } finally {
                try {
                    input.close();
                    if (output != null) {
                        output.close();
                    }
                } catch (Exception e) {
                }
            }
        }
    }

    /**
     * @return Restituisce la sorgente di esecuzione dell'applicazione
     */
    public static File getRunningSource() {
        CodeSource codeSource = SOURCE.getProtectionDomain().getCodeSource();
        try {
            return new File(codeSource.getLocation().toURI().getPath());
        } catch (URISyntaxException ex) {
            return null;
        }
    }

    /**
     * @return Restituisce il path dal quale si sta eseguendo l'applicazione
     */
    public static File getHomeDir() {
        File runningSource = getRunningSource();
        if (isRunningFromJar()) {
            return runningSource.getParentFile();
        }
        return runningSource;
    }

    /**
     * @return Restituisce una sottocartella al path dal quale si sta eseguendo
     * l'applicazione
     */
    public static File getHomeChild(String sub) {
        File home = getHomeDir();
        return new File(home, sub);
    }

    /**
     * @return True se si sta eseguendo l'applicazione da un file .jar, False
     * altrimenti
     */
    public static boolean isRunningFromJar() {
        String className = Resources.class.getName().replace('.', '/');
        String classJar = Resources.class.getResource("/" + className + ".class").toString();
        return classJar.startsWith("jar:");
    }

    /**
     * Chiama il metodo getResource() sulla classe di riferimento
     *
     * @see java.lang.Class#getResource(java.lang.String)
     */
    public static URL getResource(String resource) {
        return Resources.SOURCE.getResource(resource);
    }

    /**
     * Chiama il metodo getResourceAsStream() sulla classe di riferimento
     *
     * @see java.lang.Class#getResourceAsStream(java.lang.String)
     */
    public static InputStream getResourceAsStream(String resource) {
        return Resources.SOURCE.getResourceAsStream(resource);
    }

    private static URL pathToURL(String path) {
        File file = new File(path);
        if (file.isAbsolute()) {
            return fileToURL(file);
        } else {
            return getResource(path);
        }
    }

    private static URL fileToURL(File f) {
        try {
            return f.toURI().toURL();
        } catch (MalformedURLException ex) {
            ex.printStackTrace(System.err);
            return null;
        }
    }
}
