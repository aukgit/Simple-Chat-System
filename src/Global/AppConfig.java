/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Global;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Alim
 */
public class AppConfig {

    public static final int MAX_TRY_LOGIN = 10;
    public static final boolean IS_TESTING = true;
    /**
     * returns path like this E:\Working\GitHub\Simple-Chat-System (without
     * slash)
     */
    private static String APP_PATH;
    public static String DIRECTORY_SEPERATOR = File.separator;

    /**
     * @return path like this E:\Working\GitHub\Simple-Chat-System (without
     * slash)
     * 
     */
    public static String getAppPath() {
        if (AppConfig.APP_PATH == null) {
            try {
                AppConfig.APP_PATH = new File(".").getCanonicalPath();
            } catch (IOException ex) {
                Logger.getLogger(AppConfig.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return APP_PATH;
    }

    public AppConfig() {

    }
}
