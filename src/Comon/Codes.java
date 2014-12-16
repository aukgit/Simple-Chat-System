package Comon;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import ConsolePackage.Console;
import CurrentDb.Tables.UserTable;
import java.awt.Component;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Rectangle;
import java.lang.reflect.Field;
import java.util.ArrayList;
import javax.swing.JFrame;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.text.JTextComponent;

/**
 *
 * @author Alim
 */
public class Codes {

    private static final int defaultListCreatingNumber = 50;

    public static ArrayList<JTextComponent> getAllTextBox(JFrame frame) {
        ArrayList<JTextComponent> list;
        list = new ArrayList<>(defaultListCreatingNumber);
        Component[] components = frame.getRootPane().getComponents();
        //Component[] components = paneElements.getComponents();

        for (Component component : components) {
            if (component instanceof JTextField) {
                JTextComponent specificObject = (JTextField) component;
                list.add(specificObject);
            } else if (component instanceof JTextArea) {
                JTextComponent specificObject = (JTextArea) component;
                list.add(specificObject);
            }
        }

        return list;
    }

    public static void displayRightBottom(JFrame f) {

        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice defaultScreen = ge.getDefaultScreenDevice();
        Rectangle rect = defaultScreen.getDefaultConfiguration().getBounds();
        int x = (int) rect.getMaxX() - f.getWidth();
        int y = (int) rect.getMaxY() - f.getHeight();
        f.setLocation(x, y);
        f.setVisible(true);
    }

    public static void displayRightMiddle(JFrame f) {

        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice defaultScreen = ge.getDefaultScreenDevice();
        Rectangle rect = defaultScreen.getDefaultConfiguration().getBounds();
        int x = (int) rect.getMaxX() - f.getWidth();
        int y = (int) rect.getMaxY() - f.getHeight();
        f.setLocation(x, y / 2);
        f.setVisible(true);
    }
    
    /**
     * Before sending the class : call class.getClass() method
     * @param classType
     * @return 
     */
    public static Field[] getAllFields(Class classType) {
        return classType.getFields();
    }
    
    /**
     * Before sending the class : call class.getClass() method
     * @param classType
     * @return 
     */
    public static ArrayList<String> getAllFieldNames(Class classType) {
        Field[] fs = classType.getFields();
        if (fs != null) {
            ArrayList<String> list = new ArrayList<>(30);
            for (Field f : fs) {
                list.add(f.getName());
            }
            return list;
        }
        return null;
    }

    //<editor-fold defaultstate="collapsed" desc="Test Code">
//    public static void main(String args[]) {
//        
//        UserTable _user = new UserTable();
//        ArrayList<String> fs = Codes.getAllFieldNames(_user.getClass());
//        for (String f : fs) {
//            Console.writeLine(f);
//        }
//    }
    //</editor-fold>

}
