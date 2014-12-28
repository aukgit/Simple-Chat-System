/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CurrentDb.Tables;

import OnlineServers.PictureUploader;
import OnlineServers.RelatedObjects.PictureSender;
import java.awt.Color;
import java.io.IOException;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

/**
 *
 * @author Alim
 */
@SuppressWarnings("serial")
public class UserTable extends ImageLoadRelatedCode {

    public int UserID;

    public String Username;
    public String Name;
    public String Email;
    public String Password;

    public Date LastLogin;
    public boolean IsBlocked;
    public boolean IsActive;
    public boolean IsAdmin;
    public boolean IsOnline;
    public String CurrentStatus;
    public int CurrentActiveState;

    public ImageIcon Picture;

    /**
     * by defaul load profile pic
     *
     * @param ask
     * @return
     */
    public boolean loadProfilePicFromServer() {
        return loadImageFromServer(PictureSender.IAskPicture.Profile);
    }

    private boolean loadImageFromServer(byte ask) {
        PictureSender pictureSender = new PictureSender(this, ask);
        pictureSender.setRequestForUserPicture(this, ask);
        PictureUploader server = new PictureUploader();
        try {
            pictureSender = server.clientSendingMethod(pictureSender);
            this.Picture = pictureSender.getUser().Picture;
            return true;
        } catch (IOException ex) {
            Logger.getLogger(UserTable.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(UserTable.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    /**
     * if any null then don't load
     *
     * @param username
     * @param status
     * @param email
     * @param labelPicture
     */
    public void displayUser(JLabel username, JLabel status, JLabel email, JLabel labelPicture) {

        if (username != null) {
            username.setText(this.Name);
        }
        if (status != null) {
            status.setText(this.CurrentStatus);
        }

        if (email != null) {
            email.setText(this.Email);
            email.setForeground(Color.blue);
        }

        if (labelPicture != null) {
            if (this.loadProfilePicFromServer()) {
                labelPicture.setIcon(Picture);
                labelPicture.setText("");
            }
        }
    }

    public void print() {
        System.out.println("ID :" + this.UserID + " | Username: " + this.Username);
    }

}
