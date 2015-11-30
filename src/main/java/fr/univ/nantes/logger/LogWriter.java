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
    private static Integer level;
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
        initHook();

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
            count = 1;
        } else {
            count++;
        }
        calls.put(method, count);
    }

    public static void enterMethod(String method) {
        initHook();

        if (level == null) {
            level = new Integer(0);
        } else {
            level++;
        }

        try {
            initWriter();

            for (int i = 0; i < level; i++) {
                fileWriter.write(" | ");
            }
            fileWriter.write(method + "\n");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static void leaveMethod() {
        initHook();

        if (level == null) {
            level = new Integer(0);
        } else {
            level--;
        }
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
