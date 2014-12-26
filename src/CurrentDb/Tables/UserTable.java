/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CurrentDb.Tables;

import Global.AppConfig;
import java.io.File;
import java.util.Date;

/**
 *
 * @author Alim
 */
@SuppressWarnings("serial")
public class UserTable extends DatabaseTableClass {

    public int UserID;
    public String Username;
    public String Email;
    public String Password;

    public Date LastLogin;
    public boolean IsBlocked;
    public boolean IsActive;
    public boolean IsAdmin;
    public boolean IsOnline;

    public void print() {
        System.out.println("ID :" + this.UserID + " | Username: " + this.Username);
    }

    public boolean isImageExist() {
        String path = AppConfig.getAppPath() + this.Username + ".jpg";
        File f = new File(path);
        return f.exists();
    }

    public String getPathForProfilePic() {
        String path = AppConfig.getAppPath() + this.Username + ".jpg";
        return path;
    }
    public String getPathForThumbChatPic() {
        String path = AppConfig.getAppPath() + this.Username + "_chat.jpg";
        return path;
    }
    
    public String getPathForThumbChatListPic() {
        String path = AppConfig.getAppPath() + this.Username + "_chatList.jpg";
        return path;
    }

}
