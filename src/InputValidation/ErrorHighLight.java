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

    public final static Color Green = new Color(0, 153, 0);
    public final static Color Red = Color.RED;
    public final static Color White = Color.WHITE;

    //<editor-fold defaultstate="collapsed" desc="Error Validate">
    /**
     *
     * @param isError : true means error occured
     * @param label ; label object
     * @param textbox : textbox object
     * @param errorMsg : Error message if isError True
     * @param normalTooltip : if not isError then this message will be
     * displayed.s
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
        Color pickingColor = Red;

        if (label != null) {
            label.setForeground(White);
            label.setBackground(pickingColor);
            if (errorMsg != null) {
                label.setToolTipText("Invalid : " + errorMsg);
            }
        }

        if (textbox != null) {
            textbox.setForeground(White);
            textbox.setBackground(pickingColor);
            if (errorMsg != null) {
                textbox.setToolTipText("Invalid : " + errorMsg);
            }
        }
    }

    public static void ResumeErrorLabelNormal(JLabel label, JTextField textbox, String regularTooltip) {
        Color pickingColor = Color.black;
        if (label != null) {
            label.setForeground(pickingColor);
            if (regularTooltip != null) {
                label.setToolTipText(regularTooltip);
            }
        }

        if (textbox != null) {
            textbox.setForeground(pickingColor);
            if (regularTooltip != null) {
                textbox.setToolTipText(regularTooltip);
            }
        }
    }

    public static void ResumeErrorLabelGreen(JLabel label, JTextField textbox, String regularTooltip) {
        Color pickingColor = Green;
        if (label != null) {
            label.setForeground(White);
            label.setBackground(pickingColor);
            if (regularTooltip != null) {
                label.setToolTipText(regularTooltip);
            }
        }

        if (textbox != null) {
            textbox.setForeground(White);
            textbox.setBackground(pickingColor);
            if (regularTooltip != null) {
                textbox.setToolTipText(regularTooltip);
            }
        }
    }

// </editor-fold>
}
