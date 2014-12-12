/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CShap;

import DesignPattern.BehaviorDialog;
import javax.swing.JFrame;

/**
 *
 * @author Alim
 */
public class Form {
    @SuppressWarnings("deprecation")
    public static JFrame show(JFrame form){
       form.show(true);
       return form;
    }
    
    public static JFrame showDialog(JFrame form){
       BehaviorDialog dialog = new BehaviorDialog(form, true);
       form.show(true);
       return form;
    }
}
