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
package Database.Components;

import java.awt.Component;

public class Err implements ISoftwareInformation {

    private MsgBox Msg;

    void msgLoad() {
        Msg = new MsgBox();
    }

    public Err() {
    }

    /**
     * 
     * @param o
     * @param e 
     */
    public Err(Component o, Exception e) {
        msgLoad();
        String errorDes = "Error :" + e.getMessage() + " \n"
                + "Error Cause:" + e.getCause() + " \n"
                + "Error Class:" + e.getClass() + " \n"
                + "Error Raise From:" + o.getClass() + " \n"
                + "Error String:" + e.toString() + " \n"
                + "Contact with developer:" + EMAIL;
        Msg.showError(o, errorDes, "Error");
    }

    /**
     * 
     * @param o
     * @param e
     * @param title 
     */
    public Err(Component o, Exception e, String title) {
        msgLoad();
        String errorDes = "Error :" + e.getMessage() + " \n"
                + "Error Cause:" + e.getCause() + " \n"
                + "Error Class:" + e.getClass() + " \n"
                + "Error Raise From:" + o.getClass() + " \n"
                + "Error String:" + e.toString() + " \n"
                + "Contact with developer:" + EMAIL;
        Msg.showError(o, errorDes, title);
    }

    /**
     * 
     * @param o
     * @param e
     * @param title
     * @param msg 
     */
    public Err(Component o, Exception e, String title, String msg) {
        msgLoad();
        String errorDes = "Error Msg:" + msg + " \n"
                + "Error Base Message:" + e.getMessage() + " \n"
                + "Error Cause:" + e.getCause() + " \n"
                + "Error Class:" + e.getClass() + " \n"
                + "Error Raise From:" + o.getClass() + " \n"
                + "Error String:" + e.toString() + " \n"
                + "Contact with developer:" + EMAIL;
        Msg.showError(o, errorDes, title);
    }

    /**
     * 
     * @param o
     * @param e
     * @param title
     * @param msg 
     */
    public void showError(Component o, Exception e, String title, String msg) {
        msgLoad();
        String errorDes = "Error Msg:" + msg + " \n"
                + "Error Base Message:" + e.getMessage() + " \n"
                + "Error Cause:" + e.getCause() + " \n"
                + "Error Class:" + e.getClass() + " \n"
                + "Error Raise From:" + o.getClass() + " \n"
                + "Error String:" + e.toString() + " \n"
                + "Contact with developer:" + EMAIL;
        Msg.showError(o, errorDes, title);
    }

    /**
     * 
     * @param o
     * @param e 
     */
    public void showError(Component o, Exception e) {
        msgLoad();
        String errorDes = "Error Base Message:" + e.getMessage() + " \n"
                + "Error Cause:" + e.getCause() + " \n"
                + "Error Class:" + e.getClass() + " \n"
                + "Error Raise From:" + o.getClass() + " \n"
                + "Error String:" + e.toString() + " \n"
                + "Contact with developer:" + EMAIL;
        Msg.showError(o, errorDes, e.toString());
    }

    /**
     * 
     * @param o
     * @param e
     * @param Title 
     */
    public void showError(Component o, Exception e, String Title) {
        msgLoad();
        String errorDes = "Error Base Message:" + e.getMessage() + " \n"
                + "Error Cause:" + e.getCause() + " \n"
                + "Error Class:" + e.getClass() + " \n"
                + "Error Raise From:" + o.getClass() + " \n"
                + "Error String:" + e.toString() + " \n"
                + "Contact with developer:" + EMAIL;
        Msg.showError(o, errorDes, Title);
    }
}
