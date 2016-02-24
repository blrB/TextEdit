package MyText;

/**
 * Created by andrey on 23/02/16.
 */

import javax.swing.*;
import java.awt.event.ActionListener;

public class TextMenuBar extends JMenuBar {

    private MainWindow ob;

    public TextMenuBar(MainWindow ob) {
        this.ob = ob;
        ActionListener clickButton = new TextToolBarButtonListener(ob);

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
