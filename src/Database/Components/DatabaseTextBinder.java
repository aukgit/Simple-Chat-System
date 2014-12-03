/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Database.Components;

import java.util.ArrayList;
import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.text.JTextComponent;

/**
 *
 * @author Alim
 */
public class DatabaseTextBinder {
    public DatabaseTextBinder(){
    }
    
    public static void DbBindTextBoxes(JFrame frame, boolean read, boolean, insert, boolean update){
        ArrayList<JTextComponent> list = JFrameRelatedCodes.getAllTextBox(frame);
        for (JTextComponent textBox : list) {
            //binding property name
            String accessName = textBox.getAccessibleContext().getAccessibleName();
            
        }
    
    }
}
