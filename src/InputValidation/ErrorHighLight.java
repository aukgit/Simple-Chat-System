/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package InputValidation;

import java.awt.Color;

import javax.swing.JLabel;
import javax.swing.JTextField;

/**
 *
 * @author Alim
 */
public class ErrorHighLight {
    
    public final static String Green = "009900";
    

    //<editor-fold defaultstate="collapsed" desc="Error Validate">
    /**
     * 
     * @param isError : true means error occured
     * @param label ; label object
     * @param textbox : textbox object
     * @param errorMsg : Error message if isError True
     * @param normalTooltip : if not isError then this message will be displayed.s
     */
    public static void ErrorValidate(boolean isError, JLabel label, JTextField textbox, String errorMsg, String normalTooltip) {
        if (isError) {
            ErrorLabel(label, textbox, errorMsg);
        } else {
            ResumeErrorLabelGreen(label, textbox, normalTooltip);
        }
    }
    //</editor-fold>

    // <editor-fold defaultstate="collapsed" desc=" Error Labeling and coloring ">
    @SuppressWarnings("null")
    public static void ErrorLabel(JLabel label, JTextField textbox, String errorMsg) {
        if (label != null) {
            label.setForeground(Color.getColor(Green));
            if (errorMsg != null) {
                label.setToolTipText("Invalid : " + errorMsg);
            }
        }

        if (textbox != null) {
            textbox.setForeground(Color.red);
            if (errorMsg != null) {
                textbox.setToolTipText("Invalid : " + errorMsg);
            }
        }
    }

    public static void ResumeErrorLabelNormal(JLabel label, JTextField textbox, String regularTooltip) {
        if (label != null) {
            label.setForeground(Color.black);
            if (regularTooltip != null) {
                label.setToolTipText(regularTooltip);
            }
        }

        if (textbox != null) {
            textbox.setForeground(Color.black);
            if (regularTooltip != null) {
                textbox.setToolTipText(regularTooltip);
            }
        }
    }

    public static void ResumeErrorLabelGreen(JLabel label, JTextField textbox, String regularTooltip) {
        if (label != null) {
            label.setForeground(Color.getColor(Green));
            if (regularTooltip != null) {
                label.setToolTipText(regularTooltip);
            }
        }

        if (textbox != null) {
            label.setForeground(Color.getColor(Green));
            if (regularTooltip != null) {
                textbox.setToolTipText(regularTooltip);
            }
        }
    }

// </editor-fold>
}
