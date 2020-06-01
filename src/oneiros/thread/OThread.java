package oneiros.thread;

public class OThread {

    private static int THREADS;
    private Thread thread;

    public OThread() {
        this.thread = new Thread("OThread" + THREADS);
        THREADS++;
    }



}
