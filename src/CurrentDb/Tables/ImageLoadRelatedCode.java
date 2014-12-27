/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CurrentDb.Tables;

import Global.AppConfig;
import java.io.File;

/**
 *
 * @author Alim
 */
public class ImageLoadRelatedCode extends DatabaseTableClass {


    public boolean isImageExist(int id) {
        String path = AppConfig.getPictureUploadPath() + id + ".jpg";
        File f = new File(path);
        return f.exists();
    }

    public String getPathForProfilePic(int id) {
        String path = AppConfig.getPictureUploadPath() + id + ".jpg";
        return path;
    }

    public String getPathForThumbChatPic(int id) {
        String path = AppConfig.getPictureUploadPath() + id + "_chat.jpg";
        return path;
    }

    public String getPathForThumbChatListPic(int id) {
        String path = AppConfig.getPictureUploadPath() + id + "_chatList.jpg";
        return path;
    }
}
