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
    
    public static String DIRECTORY_SEPERATOR = File.separator;

    private static String _appPath;
    private static String _pictureUploadPath;
    
    
    
    /**
     * @return path like this E:\Working\GitHub\Simple-Chat-System (without
     * slash)
     * 
     */
    public static String getAppPath() {
        if (AppConfig._appPath == null) {
            try {
                AppConfig._appPath = new File(".").getCanonicalPath();
            } catch (IOException ex) {
                Logger.getLogger(AppConfig.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return _appPath;
    }
    /**
     * E:\Working\GitHub\Simple-Chat-System\PictureUpload
     * @return getAppPath() + DIRECTORY_SEPERATOR + "PictureUpload"
     */
     public static String getPictureUploadPath() {
        if (AppConfig._pictureUploadPath == null) {
            _pictureUploadPath = getAppPath() + DIRECTORY_SEPERATOR + "PictureUpload";
        }
        return _pictureUploadPath;
    }

    public AppConfig() {

    }
}
