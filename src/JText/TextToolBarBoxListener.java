package JText;

import javax.swing.*;
import javax.swing.text.StyledEditorKit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by andrey on 17/02/16.
 */
public class TextToolBarBoxListener implements ActionListener {

    public void actionPerformed(ActionEvent e) {
        JComboBox cb = (JComboBox)e.getSource();
        Action fontAction;
        String size = (String) cb.getSelectedItem();
        if (e.getActionCommand() == "BOX_SIZE") {
            fontAction = new StyledEditorKit.FontSizeAction(size, Integer.parseInt(size));
        }
        else {
            fontAction = new StyledEditorKit.FontFamilyAction(size, size);
        }
        fontAction.actionPerformed(e);

    }

}
