package mytext;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;
import java.util.Arrays;

/**
 * Created by andrey on 18/02/16.
 */
public class MainWindow {

    private FileWork fileWork;
    public TextPanel textPanel;
    public JFrame frame;
    public JScrollPane scrollPanel;

    public MainWindow() {
        frame = new JFrame("Text Edit");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setJMenuBar(createFileMenu());
        frame.add(createToolBar(), BorderLayout.PAGE_START);
        textPanel = new TextPanel(this);
        Line newLine = new Line(this);
        textPanel.lines.add(newLine);
        scrollPanel = new JScrollPane(textPanel);
        TextMouseHandler textmousehandler = new TextMouseHandler(this);
        textPanel.addMouseListener(textmousehandler);
        textPanel.addMouseMotionListener(textmousehandler);
        scrollPanel.getHorizontalScrollBar().addAdjustmentListener(new AdjustmentListener() {
            public void adjustmentValueChanged(AdjustmentEvent evt) {
                updateWindow();
            }
        });
        scrollPanel.getVerticalScrollBar().addAdjustmentListener(new AdjustmentListener() {
            public void adjustmentValueChanged(AdjustmentEvent evt) {
                updateWindow();
            }
        });
        fileWork = new FileWork(this);
        frame.add(scrollPanel, BorderLayout.CENTER);
        frame.addKeyListener(new TextKeyListener(this));
        frame.setSize(800,600);
        frame.setFocusable(true);
        frame.setVisible(true);
    }

    private JMenuBar createFileMenu() {
        JMenuBar menuBar = new JMenuBar();
        JMenu fileMenu = new JMenu("File");
        JMenuItem openFile = new JMenuItem("Open");
        openFile.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                fileWork.openFile();
            }
        });
        fileMenu.add(openFile);
        JMenuItem saveFile = new JMenuItem("Save");
        saveFile.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                fileWork.saveFile();
            }
        });
        fileMenu.add(saveFile);
        fileMenu.addSeparator();
        JMenuItem exit = new JMenuItem("Exit");
        exit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        fileMenu.add(exit);
        menuBar.add(fileMenu);
        return menuBar;
    }

    private JToolBar createToolBar() {
        JToolBar toolBar = new JToolBar();
        toolBar.add(makeButton(new JButton(), "SAVE.png", new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                fileWork.saveFile();
            }
        }));
        toolBar.add(makeButton(new JButton(), "OPEN.png", new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                fileWork.openFile();
            }
        }));
        toolBar.addSeparator();
        toolBar.add(makeButton(new JButton(), "B.png", new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                textPanel.changeStyleOnBold();
            }
        }));
        toolBar.add(makeButton(new JButton(), "I.png", new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                textPanel.changeStyleOnItalic();
            }
        }));
        toolBar.addSeparator();
        String[] sizeFont = {"12", "14", "16", "18", "22", "24", "32", "36", "40", "48"};
        JComboBox sizeBox = new JComboBox(sizeFont);
        sizeBox.setMaximumSize(sizeBox.getPreferredSize());
        sizeBox.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                textPanel.changeSizeFont(e);
            }
        });
        toolBar.add(sizeBox);
        toolBar.addSeparator();
        String [] fontType = GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames();
        JComboBox fontBox = new JComboBox(fontType);
        fontBox.setMaximumSize(fontBox.getPreferredSize());
        fontBox.setSelectedIndex(Arrays.asList(fontType).indexOf(Font.MONOSPACED));
        fontBox.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                textPanel.changeTypeFont(e);
            }
        });
        toolBar.add(fontBox);
        return toolBar;
    }

    private JButton makeButton(JButton button, String imgString, ActionListener action){
        button.addActionListener(action);
        String patch = "img/" + imgString;
        ImageIcon img = new ImageIcon(patch);
        button.setIcon(img);
        return button;
    }

    public void updateWindow() {
        scrollPanel.revalidate();
        scrollPanel.repaint();
        frame.requestFocus();
    }

    public static void main(String[] args){
        MainWindow mainWinow = new MainWindow();
    }

}
