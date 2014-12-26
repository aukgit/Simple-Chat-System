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

/**
 *
 * @author Alim
 */
public class PictureSender implements Serializable{
    private static final long serialVersionUID = 1L;

    private String _path;
    private BufferedImage _picture;
    
    private UserTable _user;
    private boolean _isProccesedSuccessful;
    
    

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
    
    public void generatePictureFromPaths(){
        Picture pic = new Picture(getPath());
        setPicture(pic.getImage());
    }

    /**
     * @return the _picture
     */
    public BufferedImage getPicture() {
        return _picture;
    }

    /**
     * @param picture the _picture to set
     */
    public void setPicture(BufferedImage picture) {
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

}
