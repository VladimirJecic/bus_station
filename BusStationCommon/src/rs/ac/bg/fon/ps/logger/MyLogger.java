/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.ac.bg.fon.ps.logger;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;
import rs.ac.bg.fon.ps.util.DateUtilities;
import rs.ac.bg.fon.ps.util.constant.PathConstants;

/**
 *
 * @author Vladimir Jecić
 */
public final class MyLogger {

    private static FileHandler fh;
    private static Map<Class, Logger> loggerHashMap;
    private static Logger activeLogger;

    private static void setupLogger() {
        try {
            loggerHashMap = new HashMap();
            StringBuilder sb = new StringBuilder();
            sb.append(PathConstants.LOGGER).append("MyLogFile_")
                    .append(DateUtilities.parse(new Date(), "dd.MM.yyyy_HH.mm"))
                    .append(".00.log");
            String fileName = sb.toString();
            Path path = Paths.get(fileName);
            File file = new File(fileName);
            if (!file.exists()) {
                Files.createFile(path);
            };
            fh = new FileHandler(fileName, true);
            SimpleFormatter formatter = new SimpleFormatter();
            fh.setFormatter(formatter);
            Logger logger = Logger.getLogger(MyLogger.class.getName());
            logger.addHandler(fh);
            logger.setLevel(Level.INFO);
            logger.info("Logger Initialized");
            loggerHashMap.put(MyLogger.class, logger);
            activeLogger = logger;
        } catch (Exception e) {
            e.printStackTrace();
//            System.out.println(e.getMessage());
        }
    }

    public synchronized static Logger getLogger(Class callerClass) {
        if (activeLogger == null) {
            setupLogger();
        }
        Logger requestedLogger = loggerHashMap.get(callerClass);
        if (requestedLogger == null) {//logger doesn't exist in HashMap
            requestedLogger = Logger.getLogger(callerClass.getName());
            requestedLogger.addHandler(fh);
            loggerHashMap.put(callerClass, requestedLogger);
            activeLogger.removeHandler(fh);
            activeLogger = requestedLogger;

        } else if (!activeLogger.equals(requestedLogger)) { //logger exists in HashMap
            activeLogger.removeHandler(fh);
            activeLogger = requestedLogger;
        }
        return activeLogger;

    }

}
