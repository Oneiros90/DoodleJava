package oneiros.games;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.LinkedList;

public class NoHoldingKeyListener extends KeyAdapter {

    private LinkedList<Integer> buffer;
    private LinkedList<Character> charBuffer;

    public NoHoldingKeyListener() {
        this.buffer = new LinkedList<>();
        this.charBuffer = new LinkedList<>();
    }

    public void flush(){
        this.buffer.clear();
        this.charBuffer.clear();
    }

    @Override
    public final void keyPressed(KeyEvent e) {
        int code = e.getKeyCode();
        if (!this.buffer.contains(code)) {
            this.buffer.add(code);
        }
    }

    @Override
    public final void keyReleased(KeyEvent e) {
        this.buffer.remove((Integer) e.getKeyCode());
        this.charBuffer.remove((Character) e.getKeyChar());
    }

    @Override
    public final void keyTyped(KeyEvent e) {
        char character = e.getKeyChar();
        if (!this.charBuffer.contains(character)) {
            this.charBuffer.add(character);
        }
    }

}
