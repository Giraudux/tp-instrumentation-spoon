package fr.univ.nantes.logger;

/**
 * @author Alexis Giraudet
 * @author Thomas Minier
 */
public class ShutdownHookLog extends Thread {
    public void run() {
        LogWriter.writeLog();
    }
}
