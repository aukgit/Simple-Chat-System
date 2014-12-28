/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CurrentDb.Tables;

/**
 *
 * @author Alim
 */
@SuppressWarnings("serial")
public class MessageTable extends DatabaseTableClass {

    public long MessageID;

    public int SendFromUserID;
    public String ChatSessionID;
    public String Message1;

    public String Message2;
    public boolean IsFileExit;


}
