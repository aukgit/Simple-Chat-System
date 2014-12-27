/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package OnlineServers.RelatedObjects;

import CurrentDb.Tables.ChatListTable;
import CurrentDb.Tables.UserTable;
import java.io.Serializable;
import javax.swing.ImageIcon;

/**
 *
 * @author Alim
 */
public final class PictureSender implements Serializable {

    public interface IAskPicture {

        byte Profile = 0;
        byte Chating = 1;
        byte ChatList = 2;
        byte All = 3;
    }

    private static final long serialVersionUID = 1L;

    private ImageIcon _picture;

    private UserTable _user;
    private ChatListTable _chatListTable;
    private boolean _isProccesedSuccessful;

    private boolean _askToGetPictures;
    private boolean _sendAllPictures;

    private ImageIcon _profilePic;
    private ImageIcon _chattingPic;
    private ImageIcon _chatListPic;

    private int _requestedUserId;

    private byte _AskedPictured;

    public PictureSender(UserTable user) {
        _user = user;
        _requestedUserId = user.UserID;

    }

    public PictureSender(ImageIcon img, UserTable user) {
        _picture = img;
        _user = user;
        _requestedUserId = user.UserID;
        setAskToGetPictures(false);

    }

    /**
     *
     * @param chat
     * @param ask set by IAskPicture
     * @param userid
     */
    public PictureSender(ChatListTable chat, byte ask, int userid) {
        setChatListTable(chat);
        setAskedPictured(ask);
        setRequestedUserId(userid);
        setAskToGetPictures(true);
    }

    public PictureSender(UserTable user, byte ask) {
        setUser(user);
        setAskedPictured(ask);
        setRequestedUserId(user.UserID);
        setAskToGetPictures(true);
    }

    /**
     * @return the _AskedPictured
     */
    public byte getAskedPictured() {
        return _AskedPictured;
    }

    /**
     * @param _AskedPictured the _AskedPictured to set
     */
    public void setAskedPictured(byte _AskedPictured) {
        this._AskedPictured = _AskedPictured;
    }

    /**
     * @return the _chatListTable
     */
    public ChatListTable getChatListTable() {
        return _chatListTable;
    }

    /**
     * @param _chatListTable the _chatListTable to set
     */
    public void setChatListTable(ChatListTable _chatListTable) {
        this._chatListTable = _chatListTable;
    }

    /**
     * @return the _requestedUserId
     */
    public int getRequestedUserId() {
        if (_requestedUserId == 0) {
            if (getUser() != null) {
                _requestedUserId = getUser().UserID;
            }
        }
        return _requestedUserId;
    }

    /**
     * @param _requestedUserId the _requestedUserId to set
     */
    public void setRequestedUserId(int _requestedUserId) {
        this._requestedUserId = _requestedUserId;
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
    public final void setAskToGetPictures(boolean _askToGetPictures) {
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
