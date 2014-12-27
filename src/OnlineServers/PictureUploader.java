/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package OnlineServers;

import CurrentDb.Tables.ImageLoadRelatedCode;
import CurrentDb.Tables.UserTable;
import Global.AppConfig;
import ImageProcessing.Picture;
import OnlineServers.Inheritable.GeneralServer;
import OnlineServers.RelatedObjects.PictureSender;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.swing.ImageIcon;

/**
 * Uploads pictures and send pictures on behalf
 *
 * @author Alim
 *
 */
public class PictureUploader extends GeneralServer<PictureSender> {

    public PictureUploader() {
        super.initialize("Picture Uploader", super._serverConfig.PictureUploaderPort, super._serverConfig.ServerIP);
    }

    /**
     * work with chatlist
     *
     * @param clientObject
     * @param pictureProcessor
     */
    public void setProfilePicture(PictureSender clientObject, Picture pictureProcessor, ImageLoadRelatedCode imageLoader) {
        String path = imageLoader.getPathForProfilePic(clientObject.getRequestedUserId());
        System.out.println("Profile pic path :" + path);
        ImageIcon icon = pictureProcessor.getImageIcon(path);
        BufferedImage img = pictureProcessor.getImage(path);
        pictureProcessor.save(AppConfig.getPictureUploadPath() + "hello.jpg");
        clientObject.setProfilePic(icon);
    }

    public void setChattingPicture(PictureSender clientObject, Picture pictureProcessor, ImageLoadRelatedCode imageLoader) {
        String path = imageLoader.getPathForThumbChatingPic(clientObject.getRequestedUserId());
        System.out.println("Chatting pic path :" + path);
        clientObject.setChattingPic(pictureProcessor.getImageIcon(path));
    }

    public void setChatListPicture(PictureSender clientObject, Picture pictureProcessor, ImageLoadRelatedCode imageLoader) {
        String path = imageLoader.getPathForThumbChatListPic(clientObject.getRequestedUserId());
        System.out.println("ChatList pic path :" + path);
        clientObject.setChatListPic(pictureProcessor.getImageIcon(path));
    }

    @Override
    public boolean doProcessInServer(PictureSender clientObject) {
        Picture pictureProcessor = new Picture();
        ImageLoadRelatedCode imageLoader = null;
        if (clientObject.isAskToGetPictures()) {
            // sending picture not uploading
            // pictures are already uploaded 
            if (clientObject.getUser() != null) {
                imageLoader = clientObject.getUser();
            } else if (clientObject.getChatListTable() != null) {
                imageLoader = clientObject.getChatListTable();

            } else {
                System.out.println("Sorry no table detected , please fix it in your request sending side.");
                clientObject = null;
                return false;
            }

            if (clientObject.getAskedPictured() == PictureSender.IAskPicture.All) {
                setProfilePicture(clientObject, pictureProcessor, imageLoader);
                setChatListPicture(clientObject, pictureProcessor, imageLoader);
                setChattingPicture(clientObject, pictureProcessor, imageLoader);
            } else if (clientObject.getAskedPictured() == PictureSender.IAskPicture.Profile) {
                setProfilePicture(clientObject, pictureProcessor, imageLoader);
            } else if (clientObject.getAskedPictured() == PictureSender.IAskPicture.Chating) {
                setChattingPicture(clientObject, pictureProcessor, imageLoader);
            } else if (clientObject.getAskedPictured() == PictureSender.IAskPicture.ChatList) {
                setChatListPicture(clientObject, pictureProcessor, imageLoader);
            } else {
                System.out.println("No type asked.");
            }
            clientObject.setIsProccesedSuccessful(true);
        } else if (clientObject.getPicture() != null) {
            // if not ask to send picture that means trying to upload
            UserTable user = clientObject.getUser();
            BufferedImage img = pictureProcessor.getBufferedImage(clientObject.getPicture());
            BufferedImage profile = pictureProcessor.ResizeImage(img, super._serverConfig.ProfilePicWidth, _serverConfig.ProfilePicHeight);
            BufferedImage chatList = pictureProcessor.ResizeImage(img, super._serverConfig.ChatListThumbWidth, _serverConfig.ChatListThumbHeight);
            BufferedImage chatting = pictureProcessor.ResizeImage(img, super._serverConfig.ChatingThumbWidth, _serverConfig.ChatingThumbHeight);
            String profilePath = user.getPathForProfilePic(user.UserID);
            String chatListPath = user.getPathForThumbChatListPic(user.UserID);
            String chattingPath = user.getPathForThumbChatingPic(user.UserID);
            System.out.println(profilePath);
            System.out.println(chatListPath);
            System.out.println(chattingPath);

            pictureProcessor.save(new File(profilePath), profile);
            pictureProcessor.save(new File(chatListPath), chatList);
            pictureProcessor.save(new File(chattingPath), chatting);
            clientObject.setPicture(null);
            clientObject.setIsProccesedSuccessful(true);
        }
//        if (clientObject.isAskToGetPictures()) {
//            BufferedImage img = pictureProcessor.getBufferedImage(clientObject.getProfilePic());
//            pictureProcessor.save(AppConfig.getPictureUploadPath() + "hello3.jpg");
//        }
        return true;
    }

    public static void main(String[] args) {
        PictureUploader uploaderServer = new PictureUploader();
        uploaderServer.startThread();

    }

}
