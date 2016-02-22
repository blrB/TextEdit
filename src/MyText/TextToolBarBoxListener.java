package MyText;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by andrey on 19/02/16.
 */
public class TextToolBarBoxListener implements ActionListener {

    public void actionPerformed(ActionEvent e) {
        JComboBox cb = (JComboBox)e.getSource();
        //Action fontAction;
        //String size = (String) cb.getSelectedItem();
        if (e.getActionCommand() == "BOX_SIZE") {
            //fontAction = new StyledEditorKit.FontSizeAction(size, Integer.parseInt(size));
            System.out.print("BOX_SIZE");
        }
        else {
            //fontAction = new StyledEditorKit.FontFamilyAction(size, size);
            System.out.print("BOX_FONT");
        }
        //fontAction.actionPerformed(e);

    }

}
