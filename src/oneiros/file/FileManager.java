package oneiros.file;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import javax.imageio.ImageIO;

/**
 * FileManager modella un gestore di file. La classe fornisce diversi metodi per leggere o scrivere
 * testo o immagini
 * @author Oneiros
 */
public class FileManager {

    public static BufferedImage getImage(InputStream byteStream) throws IOException {
        return ImageIO.read(byteStream);
    }

    public static BufferedImage getImage(URL url) throws IOException {
        return ImageIO.read(url);
    }

    /**
     * Legge il testo all'interno di un file di testo contenuto nelle risorse dell'applicazione
     * @param path Il path relativo del file
     * @return Il contenuto del file
     */
    public static String readTextFromResource(InputStream byteStream) {

        //Creo un oggetto di tipo "StringBuilder", capace di costruire una stringa
        //man mano che viene letta da un file
        StringBuilder builder = new StringBuilder();

        try {
            //Trasformo il flusso di byte in testo tramite la codifica "ISO-8859-1"
            InputStreamReader txtStream = new InputStreamReader(byteStream, "ISO-8859-1");

            //Predispongo un "BufferedReader" per leggere il testo codificato
            BufferedReader reader = new BufferedReader(txtStream);

            //Eseguo l'operazione di lettura, memorizzando il tutto nello "StringBuilder"
            String line = null;
            while ((line = reader.readLine()) != null) {
                builder.append(line);
                builder.append(System.getProperty("line.separator"));
            }

            //Chiudo tutti i flussi di dati aperti
            reader.close();
            txtStream.close();
            byteStream.close();

        } catch (IOException ex) {
            //Stampo un messaggio di errore nel caso in cui la lettura non sia andata a buon fine
            System.err.println(ex);
        }

        //Ritorno la stringa costruita dallo "StringBuilder"
        return builder.toString();
    }

    /**
     * Crea un file di testo
     * @param text Il testo da inserire nel file
     * @param path Il percorso dove salvare il file
     */
    public static void writeText(String text, String path) {        
        BufferedWriter output;
        try {
            
            //Preparo l'oggetto predisposto alla scrittura del file fornendogli il giusto path
            output = new BufferedWriter(new FileWriter(path));
            
            //Eseguo l'operazione di scrittura del testo
            output.write(text);
            
            //Chiudo il "Writer"
            output.close();
            
        } catch (IOException ex) {
            //Stampo un messaggio di errore nel caso in cui la scrittura non sia andata a buon fine
            System.err.println("File cannot be written: " + path);
        }
    }
}
