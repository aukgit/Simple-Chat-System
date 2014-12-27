/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TestingCode;

import CurrentDb.TableNames;
import CurrentDb.Tables.UserTable;
import Database.DatabaseQuery;
import OnlineServers.PictureUploader;
import OnlineServers.RelatedObjects.PictureSender;
import java.io.IOException;

/**
 *
 * @author Alim
 */
public class PictureUploaderTest {

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        UserTable user = new UserTable();
        DatabaseQuery db = new DatabaseQuery(TableNames.USER);
        db.getResultsFirstAsObject(user);
        PictureSender sender = new PictureSender(user, PictureSender.IAskPicture.Profile);
        PictureUploader server = new PictureUploader();
        sender = server.clientSendingMethod(sender);
        user = sender.getUser();
        
        

    }
}
