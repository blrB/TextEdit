package MyText;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.Arrays;

/**
 * Created by andrey on 19/02/16.
 */
public class TextToolBar extends JToolBar {

    private MainWindow ob;

    public TextToolBar(MainWindow object){
        this.ob = object;
        ActionListener clickButton = new TextToolBarButtonListener(ob);
        ActionListener clickBox = new TextToolBarBoxListener(ob);

        makeButton(new JButton(""), "SAVE.png", "BUTTON_SAVE", clickButton);
        makeButton(new JButton(""), "OPEN.png", "BUTTON_OPEN", clickButton);
        this.addSeparator();
        makeButton(new JButton(""), "B.png", "BUTTON_FONT_B", clickButton);
        makeButton(new JButton(""), "I.png", "BUTTON_FONT_I", clickButton);
        this.addSeparator();

        String[] sizeFont = {"12", "14", "16", "18", "22", "24", "32", "36", "40", "48"};
        JComboBox sizeBox = new JComboBox(sizeFont);
        sizeBox.setMaximumSize(sizeBox.getPreferredSize());
        sizeBox.addActionListener(clickBox);
        sizeBox.setActionCommand("BOX_SIZE");

        String [] fontType = GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames();
        JComboBox fontBox = new JComboBox(fontType);
        fontBox.setSelectedIndex(Arrays.asList(fontType).indexOf("DejaVu Sans"));
        fontBox.setMaximumSize(fontBox.getPreferredSize());
        fontBox.addActionListener(clickBox);
        fontBox.setActionCommand("BOX_FONT");

        this.add(sizeBox);
        this.addSeparator();
        this.add(fontBox);

    }

    private void makeButton(JButton button, String imgString, String command, ActionListener action){
        button.addActionListener(action);
        button.setActionCommand(command);

        String patch = "img/" + imgString;
        ImageIcon img = new ImageIcon(patch);
        button.setIcon(img);

        this.add(button);
    }

}