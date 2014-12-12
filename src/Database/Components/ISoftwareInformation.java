/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Database.Components;

import CShap.ErrorHandle;
import Database.DatabaseQuery;

/*------------------------------------------------
 *------------------------------------------------
 * ||id     		:   112 0821 042
 * |name   		:   Alim Ul Karim
 * |email  		:   alim.karim.nsu@gmail.com
 * |blog   		:   http://bit.ly/auk-blog
 * |linkedin            :   http://linkd.in/alim-ul-karim
 *------------------------------------------------
 *------------------------------------------------
 */

public interface ISoftwareInformation {
    public String PRODUCTNAME = "Simple Chat System";
    public String VENDOR = "Developers Organism";
    public String DEVELOPER = "Alim Ul Karim";
    public String WEBSITE = "NONE";
    public String EMAIL = "auk.junk@live.com";

    public String DatabaseURL = "jdbc:mysql://localhost:3306/chatdatabase?zeroDateTimeBehavior=convertToNull";
    public ArrayMore arr = new ArrayMore(); //for getting or readding array in print
    public ErrorHandle err = new ErrorHandle();    
    public DatabaseQuery dbq = new DatabaseQuery();        
    

}
