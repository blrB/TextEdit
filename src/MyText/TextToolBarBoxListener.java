package MyText;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by andrey on 19/02/16.
 */
public class TextToolBarBoxListener implements ActionListener {

    private MainWindow ob;

    public TextToolBarBoxListener(MainWindow ob){
        this.ob = ob;
    }

    public void actionPerformed(ActionEvent e) {
        JComboBox cb = (JComboBox)e.getSource();
        String change = (String) cb.getSelectedItem();
        if (e.getActionCommand() == "BOX_SIZE") {
            for (Line line: ob.textPanel.lines) {
                for (Char ch : line.chars) {
                    if (ch.getIsSelect()) {
                        ch.setFontSize(Integer.parseInt(change));
                    }
                }
            }
        }
        else {
            for (Line line: ob.textPanel.lines) {
                for (Char ch : line.chars) {
                    if (ch.getIsSelect()) {
                        ch.setFontType(change);
                    }
                }
            }
        }
        ob.scrollPanel.revalidate();
        ob.scrollPanel.repaint();
        ob.frame.requestFocus();
    }

}
