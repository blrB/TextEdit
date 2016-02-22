package JText;

import javax.swing.*;
import java.awt.event.ActionListener;

/**
 * Created by andrey on 16/02/16.
 */
public class TextMenuBar extends JMenuBar {

    public TextMenuBar() {
        ActionListener clickButton = new TextToolBarButtonListener();

        JMenu fileMenu = new JMenu("File");
        JMenuItem openFile = new JMenuItem("Open");
        JMenuItem saveFile = new JMenuItem("Save");
        JMenuItem exit = new JMenuItem("Exit");
        fileMenu.add(openFile);
        openFile.setActionCommand("BUTTON_OPEN");
        saveFile.setActionCommand("BUTTON_SAVE");
        exit.setActionCommand("BUTTON_EXIT");
        openFile.addActionListener(clickButton);
        saveFile.addActionListener(clickButton);
        exit.addActionListener(clickButton);
        fileMenu.add(saveFile);
        fileMenu.addSeparator();
        fileMenu.add(exit);
        this.add(fileMenu);
    }
}
