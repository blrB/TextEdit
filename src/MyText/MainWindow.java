package MyText;

import javax.swing.*;
import java.awt.*;
import java.awt.event.AdjustmentListener;

/**
 * Created by andrey on 18/02/16.
 */
public class MainWindow {

    public TextPanel textPanel;
    public JFrame frame;
    public JScrollPane scrollPanel;

    public MainWindow() {
        frame = new JFrame("Text Edit");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        TextMenuBar menuBar = new TextMenuBar(this);
        frame.setJMenuBar(menuBar);

        TextToolBar toolbar = new TextToolBar(this);
        frame.add(toolbar, BorderLayout.PAGE_START);

        textPanel = new TextPanel(this);
        scrollPanel = new JScrollPane(textPanel);
        AdjustmentListener listener = new TextScrollPanelListener(this);
        scrollPanel.getHorizontalScrollBar().addAdjustmentListener(listener);
        scrollPanel.getVerticalScrollBar().addAdjustmentListener(listener);
        frame.add(scrollPanel, BorderLayout.CENTER);

        TextMouseHandler mouseHandler = new TextMouseHandler(this);
        textPanel.addMouseListener(mouseHandler);
        textPanel.addMouseMotionListener(mouseHandler);

        frame.setFocusable(true);
        frame.addKeyListener(new TextKeyListener(this));

        frame.setSize(800,600);
        frame.setVisible(true);
    }

    public static void main(String[] args){
        MainWindow mw = new MainWindow();
    }

}
