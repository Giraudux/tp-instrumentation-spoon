package vv.spoon;

import vv.spoon.processor.CountProcessor;
import vv.spoon.processor.LogProcessor;

import java.io.IOException;

/**
 * @author Alexis Giraudet
 * @author Thomas Minier
 */
public class Main {

    public static void main(String[] args) throws Exception {
        Instru instru;
        switch (args[2]) {
            case "LogProcessor":
                instru = new Instru(args[0], args[1], new LogProcessor());
                break;
            case "CountProcessor":
                instru = new Instru(args[0], args[1], new CountProcessor());
                break;
            default:
                throw new Exception("unknown processor");
        }

        //copy the project (args[0]) in the output directory (args[1])
        instru.initOutputDirectory();

        //instrumentalize the java code of output directory with LogProcessor
        instru.instru();
    }

}
