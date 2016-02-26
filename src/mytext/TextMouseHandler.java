package mytext;

import javax.swing.event.MouseInputAdapter;
import java.awt.*;
import java.awt.event.MouseEvent;

/**
 * Created by andrey on 20/02/16.
 */
public class TextMouseHandler extends MouseInputAdapter {

    private Point one;
    private Point two;

    private MainWindow mainWindow;
    private TextPanel textPanel;

    public TextMouseHandler(MainWindow mainWindow){
        this.mainWindow = mainWindow;
        textPanel = mainWindow.textPanel;
    }

    public void mouseClicked(MouseEvent e) {
        for (Line line : textPanel.lines) {
            line.checkEndLine(e.getPoint());
            for (Char ch : line.chars) {
                if (ch.contains(e.getPoint())) {
                    textPanel.setCaretY(textPanel.lines.indexOf(line));
                    textPanel.setCaretX(line.indexOf(ch) + 1);
                }
            }
        }
        mainWindow.updateWindow();
    }

    public void mousePressed(java.awt.event.MouseEvent e){
        one = e.getPoint();
    }

    public void mouseReleased(java.awt.event.MouseEvent e){
        two = e.getPoint();
        for (Line line : textPanel.lines) {
            line.checkEndLine(two);
            for (Char ch : line.chars) {
                ch.setIsSelect(ch.contains(one, two));
                if (ch.contains(two)) {
                    textPanel.setCaretY(textPanel.lines.indexOf(line));
                    textPanel.setCaretX(line.indexOf(ch) + 1);
                }
            }
        }
        mainWindow.updateWindow();
    }

    public void mouseDragged(MouseEvent e) {
        Point now = e.getPoint();
        for (Line line : textPanel.lines) {
            line.checkEndLine(now);
            for (Char ch : line.chars) {
                ch.setIsSelect(ch.contains(one, now));
                if (ch.contains(now)) {
                    textPanel.setCaretY(textPanel.lines.indexOf(line));
                    textPanel.setCaretX(line.indexOf(ch) + 1);
                }
            }
        }
        mainWindow.updateWindow();
    }

}
