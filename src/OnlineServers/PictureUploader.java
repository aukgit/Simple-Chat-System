/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package OnlineServers;

import CurrentDb.Tables.UserTable;
import ImageProcessing.Picture;
import OnlineServers.Inheritable.GeneralServer;
import OnlineServers.RelatedObjects.PictureSender;
import java.awt.image.BufferedImage;
import java.io.File;

/**
 *
 * @author Alim
 *
 */
public class PictureUploader extends GeneralServer<PictureSender> {

    public PictureUploader() {
        super.initialize("Picture Uploader", super._serverConfig.PictureUploaderPort, super._serverConfig.ServerIP);
    }

    @Override
    public boolean doProcessInServer(PictureSender clientObject) {
        Picture pictureProcessor = new Picture();
        if (clientObject.isAskToGetPictures()) {
            // pictures are already uploaded 

        } else if (clientObject.getPicture() != null) {
            UserTable user = clientObject.getUser();
            BufferedImage img = pictureProcessor.getBufferedImage(clientObject.getPicture());
            System.out.println("ase");
            BufferedImage profile = pictureProcessor.ResizeImage(img, super._serverConfig.ProfilePicWidth, _serverConfig.ProfilePicHeight);
            BufferedImage chatList = pictureProcessor.ResizeImage(img, super._serverConfig.ChatListThumbWidth, _serverConfig.ChatListThumbHeight);
            BufferedImage chatting = pictureProcessor.ResizeImage(img, super._serverConfig.ChatingThumbWidth, _serverConfig.ChatingThumbHeight);
            System.out.println("ase2");
            String profilePath = user.getPathForProfilePic(user.UserID);
            String chatListPath = user.getPathForThumbChatListPic(user.UserID);
            String chattingPath = user.getPathForThumbChatPic(user.UserID);
            System.out.println(profilePath);
            System.out.println(chatListPath);
            System.out.println(chattingPath);

            pictureProcessor.save(new File(profilePath), profile);
            pictureProcessor.save(new File(chatListPath), chatList);
            pictureProcessor.save(new File(chattingPath), chatting);
            clientObject.setPicture(null);
            clientObject.setIsProccesedSuccessful(true);
        }
        return true;
    }

    public static void main(String[] args) {
        PictureUploader uploaderServer = new PictureUploader();
        uploaderServer.startThread();

    }

}
