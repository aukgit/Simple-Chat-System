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
public class ChatListTable extends ImageLoadRelatedCode {

    public int ChatListID;
    public int OriginalUserID;
    public int RelatedUserID;
    public String AliasAs;

    public boolean IsOnline;

}
