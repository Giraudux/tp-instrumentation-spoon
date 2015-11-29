package fr.univ.nantes.logger;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Alexis Giraudet
 * @author Thomas Minier
 */
public class LogWriter {
    private static Map<String, Integer> calls;
    private static ShutdownHookLog shutdownHook;
    private static PrintWriter fileWriter;

    public static void writeLog() {
        try {
            initWriter();
            if (calls != null) {
                for (Map.Entry<String, Integer> entry : calls.entrySet()) {
                    fileWriter.write(entry.getKey() + ": " + entry.getValue() + "\n");
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        if (fileWriter != null) {
            fileWriter.close();
        }
    }

    public static void out(String string, boolean error) {
        try {
            initWriter();

            if (error) {
                fileWriter.write("ERROR: ");
            } else {
                fileWriter.write("INFO: ");
            }
            fileWriter.write(string + "\n");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static void call(String method) {
        initHook();

        if (calls == null) {
            calls = new HashMap<String, Integer>();
        }

        Integer count = calls.get(method);
        if (count == null) {
            count = 0;
        } else {
            count++;
        }
        calls.put(method, count);
    }

    protected static void initHook() {
        if (shutdownHook == null) {
            shutdownHook = new ShutdownHookLog();
            Runtime.getRuntime().addShutdownHook(shutdownHook);
        }
    }

    protected static void initWriter() throws FileNotFoundException {
        if (fileWriter == null) {
            fileWriter = new PrintWriter("log");
        }
    }
}
