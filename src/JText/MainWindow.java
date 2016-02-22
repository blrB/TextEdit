package JText;

import javax.swing.*;
import java.awt.*;

/**
 * Created by andrey on 16/02/16.
 */
public class MainWindow extends JFrame {

    public static JScrollPane scrollPanel;
    public static TextPanel textPanel = new TextPanel();
    public static JFrame frame;

    public static void createGUI() {
        frame = new JFrame("Text Edit");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        TextMenuBar menuBar = new TextMenuBar();
        frame.setJMenuBar(menuBar);

        TextToolBar toolbar = new TextToolBar();
        frame.add(toolbar, BorderLayout.PAGE_START);

        scrollPanel = new JScrollPane(textPanel);
        frame.add(scrollPanel);

        frame.setSize(800,600);
        frame.setVisible(true);
    }

    public static void main(String[] args){
        createGUI();
    }
}