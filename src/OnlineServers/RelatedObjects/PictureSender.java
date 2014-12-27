/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package OnlineServers.RelatedObjects;

import CurrentDb.Tables.UserTable;
import ImageProcessing.Picture;
import java.awt.image.BufferedImage;
import java.io.Serializable;
import javax.swing.ImageIcon;

/**
 *
 * @author Alim
 */
public class PictureSender implements Serializable{
    private static final long serialVersionUID = 1L;

    private String _path;
    private ImageIcon _picture;
    
    private UserTable _user;
    private boolean _isProccesedSuccessful;
    
    private boolean _askToGetPictures;
    private boolean _sendAllPictures;
    
    private ImageIcon _profilePic;
    private ImageIcon _chattingPic;
    private ImageIcon _chatListPic;
    
    

    /**
     * @return the _path
     */
    public String getPath() {
        return _path;
    }

    /**
     * @param relativePath the _path to set
     */
    public void setPath(String relativePath) {
        this._path = relativePath;
    }
    


    /**
     * @return the _picture
     */
    public ImageIcon getPicture() {
        return _picture;
    }

    /**
     * @param picture the _picture to set
     */
    public void setPicture(ImageIcon picture) {
        this._picture = picture;
    }

    /**
     * @return the _user
     */
    public UserTable getUser() {
        return _user;
    }

    /**
     * @param _user the _user to set
     */
    public void setUser(UserTable _user) {
        this._user = _user;
    }

    /**
     * @return the _isProccesedSuccessful
     */
    public boolean isIsProccesedSuccessful() {
        return _isProccesedSuccessful;
    }

    /**
     * @param _isProccesedSuccessful the _isProccesedSuccessful to set
     */
    public void setIsProccesedSuccessful(boolean _isProccesedSuccessful) {
        this._isProccesedSuccessful = _isProccesedSuccessful;
    }

    /**
     * @return the _askToGetPictures
     */
    public boolean isAskToGetPictures() {
        return _askToGetPictures;
    }

    /**
     * @param _askToGetPictures the _askToGetPictures to set
     */
    public void setAskToGetPictures(boolean _askToGetPictures) {
        this._askToGetPictures = _askToGetPictures;
    }

    /**
     * @return the _sendAllPictures
     */
    public boolean isSendAllPictures() {
        return _sendAllPictures;
    }

    /**
     * @param _sendAllPictures the _sendAllPictures to set
     */
    public void setSendAllPictures(boolean _sendAllPictures) {
        this._sendAllPictures = _sendAllPictures;
    }

    /**
     * @return the _profilePic
     */
    public ImageIcon getProfilePic() {
        return _profilePic;
    }

    /**
     * @param _profilePic the _profilePic to set
     */
    public void setProfilePic(ImageIcon _profilePic) {
        this._profilePic = _profilePic;
    }

    /**
     * @return the _chattingPic
     */
    public ImageIcon getChattingPic() {
        return _chattingPic;
    }

    /**
     * @param _chattingPic the _chattingPic to set
     */
    public void setChattingPic(ImageIcon _chattingPic) {
        this._chattingPic = _chattingPic;
    }

    /**
     * @return the _chatListPic
     */
    public ImageIcon getChatListPic() {
        return _chatListPic;
    }

    /**
     * @param _chatListPic the _chatListPic to set
     */
    public void setChatListPic(ImageIcon _chatListPic) {
        this._chatListPic = _chatListPic;
    }

}
