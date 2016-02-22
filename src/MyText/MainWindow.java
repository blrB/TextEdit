package MyText;

import javax.swing.*;
import java.awt.*;
import java.awt.event.AdjustmentListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * Created by andrey on 18/02/16.
 */
public class MainWindow {

    public static TextPanel textPanel = new TextPanel();
    public static JFrame frame = new JFrame("Text Edit");;
    public static JScrollPane scrollPanel;

    public static void createGUI() {
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
/*
        TextMenuBar menuBar = new TextMenuBar();
        frame.setJMenuBar(menuBar);
*/
        TextToolBar toolbar = new TextToolBar();
        frame.add(toolbar, BorderLayout.PAGE_START);

        scrollPanel = new JScrollPane(textPanel);
        AdjustmentListener listener = new TextScrollPanelListener();
        scrollPanel.getHorizontalScrollBar().addAdjustmentListener(listener);
        scrollPanel.getVerticalScrollBar().addAdjustmentListener(listener);
        frame.add(scrollPanel);

        frame.setFocusable(true);
        frame.addKeyListener(new TextKeyListener());

        frame.setSize(800,600);
        frame.setVisible(true);
        TextCaretTimer tm = new TextCaretTimer();
    }

    public static void main(String[] args){
        createGUI();
    }
}
