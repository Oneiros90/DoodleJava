package oneiros.sound;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Sequence;
import javax.sound.midi.Sequencer;

/**
 *
 * @author Lorenzo
 */
public class MIDI extends Sound{

    public static final int END_OF_TRACK = 47;

    public MIDI(File file) {
        super(file);
    }

    public MIDI(String path) {
        super(path);
    }

    public MIDI(URL url) {
        super(url);
    }

    public static Sequencer play(File midi) throws MidiUnavailableException, InvalidMidiDataException, IOException {
        Sequencer sequencer = MidiSystem.getSequencer();
        sequencer.open();
        Sequence song = MidiSystem.getSequence(midi);
        sequencer.setSequence(song);
        try {
            Thread.sleep(100);
        } catch (InterruptedException ex) {
        }
        sequencer.start();
        return sequencer;
    }

    @Override
    public void play() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void play(int times) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void loop() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void pause() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void stop() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
