package server;

import java.util.logging.Logger;
import java.util.logging.FileHandler;
import java.util.logging.SimpleFormatter;
import java.util.logging.Level;

public class AppLogger {

    private static final String LOG_FILE_PATH = "C:\\Users\\Dmitry\\IdeaProjects\\web_lab_2\\logs\\app.log";

    public static Logger getLogger(Class<?> clazz) {
        Logger logger = Logger.getLogger(clazz.getName());


        if (logger.getHandlers().length == 0) {
            try {
                FileHandler fh = new FileHandler(LOG_FILE_PATH, true);
                fh.setFormatter(new SimpleFormatter());
                logger.addHandler(fh);
                logger.setLevel(Level.ALL);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return logger;
    }
}
