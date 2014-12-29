/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SimpleChatSystem;

import CurrentDb.CommonData;
import CurrentDb.Tables.ChatListTable;
import CurrentDb.Tables.ToWhomAliasWhatTable;
import CurrentDb.Tables.UserTable;
import java.awt.Color;
import java.awt.Component;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;

/**
 *
 * @author Alim
 */
public class JLabelForListCell extends JLabel implements ListCellRenderer {

    private static final Color HIGHLIGHT_COLOR = new Color(0, 0, 128);
    private static final long serialVersionUID = 1L;

    public JLabelForListCell() {
        setOpaque(true);
        setIconTextGap(6);
    }

    @Override
    public Component getListCellRendererComponent(JList list, Object value,
            int index, boolean isSelected, boolean cellHasFocus) {
        ToWhomAliasWhatTable user = (ToWhomAliasWhatTable) value;
        setText(user.AliasAs + " . Status : " + user.CurrentStatus);
        setIcon(null);
        if (user.isImageExist(user.UserID)) {
            setIcon(new ImageIcon(user.getPathForProfilePic(user.UserID)));
        }

        if (user.IsOnline) {
            if (isSelected) {
                setBackground(HIGHLIGHT_COLOR);
                setForeground(Color.white);
            } else {
                setBackground(Color.white);

                if (user.CurrentActiveState > 0) {
                    user.CurrentActiveState -= 1;
                }
                setForeground(CommonData.getColorForActiveStatus(index));
            }
        } else {
            setText("[Offline] " + user.AliasAs);
            setToolTipText(user.AliasAs + " is offline. Status :" + user.CurrentStatus);
            if (isSelected) {
                setBackground(Color.RED);
                setForeground(Color.white);
            } else {
                setBackground(Color.white);
                setForeground(Color.GRAY);
            }
        }
        return this;
    }
}
