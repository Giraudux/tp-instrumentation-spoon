package vv.spoon.logger;

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
    private static PrintWriter fileWriter;

    public static void writeLog() {
        try {
            PrintWriter writer = getWriter();
            if (calls != null && !calls.isEmpty()) {
                for (Map.Entry<String, Integer> entry : calls.entrySet()) {
                    writer.write(entry.getKey() + ": " + entry.getValue() + "\n");
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        fileWriter.close();
    }

    public static void out(String string, boolean error) {
        try {
            PrintWriter writer = getWriter();

            if (error) {
                writer.write("ERROR: ");
            } else {
                writer.write("INFO: ");
            }
            writer.write(string + "\n");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static void call(String method) {
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

    protected static PrintWriter getWriter() throws FileNotFoundException {
        if (fileWriter == null) {
            ShutdownHookLog shutdownHook = new ShutdownHookLog();
            Runtime.getRuntime().addShutdownHook(shutdownHook);
            fileWriter = new PrintWriter("log");
        }
        return fileWriter;
    }
}
