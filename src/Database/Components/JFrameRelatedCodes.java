/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Database.Components;

import java.awt.Component;
import java.util.ArrayList;
import javax.swing.JFrame;
import javax.swing.text.JTextComponent;

/**
 *
 * @author Alim
 */
public class JFrameRelatedCodes {

    private static final int defaultListCreatingNumber = 50;

    public static ArrayList<JTextComponent> getAllTextBox(JFrame frame) {
        ArrayList<JTextComponent> list = new ArrayList<>(defaultListCreatingNumber);
        for (Component component : frame.getComponents()) {
            if (component instanceof JTextComponent) {
                JTextComponent specificObject = (JTextComponent) component;
                list.add(specificObject);
            }
        }
        
        return list;
    }
}
