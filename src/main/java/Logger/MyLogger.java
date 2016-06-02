/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Logger;

import java.io.IOException;
import java.util.logging.*;
/**
 *
 * @author Ivan Ivanov
 */

public class MyLogger {

  static private FileHandler fileTxt;
  static private SimpleFormatter formatterTxt;
  static private FileHandler fileHTML;
  static private Formatter formatterHTML;

  static public void setup() throws IOException {

    Logger logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

    Logger rootLogger = Logger.getLogger("");
    Handler[] handlers = rootLogger.getHandlers();
    if (handlers[0] instanceof ConsoleHandler) {
      rootLogger.removeHandler(handlers[0]);
    }

    logger.setLevel(Level.INFO);
    fileTxt = new FileHandler("./src/main/resources/logger/Logging.txt");
    fileHTML = new FileHandler("./src/main/resources/logger/Logging.html");

    formatterTxt = new SimpleFormatter();
    fileTxt.setFormatter(formatterTxt);
    logger.addHandler(fileTxt);

    formatterHTML = new MyHtmlFormatter();
    fileHTML.setFormatter(formatterHTML);
    logger.addHandler(fileHTML);
  }
}
 

