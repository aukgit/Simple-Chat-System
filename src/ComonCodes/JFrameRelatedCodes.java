package ComonCodes;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import java.awt.Component;
import java.util.ArrayList;
import javax.swing.JFrame;
import javax.swing.JRootPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.text.JTextComponent;

/**
 *
 * @author Alim
 */
public class JFrameRelatedCodes {

    private static final int defaultListCreatingNumber = 50;

    public static ArrayList<JTextComponent> getAllTextBox(JFrame frame) {
        ArrayList<JTextComponent> list = new ArrayList<>(defaultListCreatingNumber);
        JRootPane paneElements = (JRootPane) frame.getComponents()[0];
        Component[] components = paneElements.getComponents();
        
        for (Component component : components) {
            if (component instanceof JTextField) {
                JTextComponent specificObject = (JTextField) component;
                list.add(specificObject);
            } else if(component instanceof JTextArea) {
                JTextComponent specificObject = (JTextArea) component;
                list.add(specificObject);
            }
        }
        
        return list;
    }


}
