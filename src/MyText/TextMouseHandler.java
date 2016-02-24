package MyText;

import javax.swing.event.MouseInputAdapter;
import java.awt.*;
import java.awt.event.MouseEvent;

/**
 * Created by andrey on 20/02/16.
 */
public class TextMouseHandler extends MouseInputAdapter {

    private Point one;
    private Point two;

    private MainWindow ob;

    public TextMouseHandler(MainWindow ob){
        this.ob = ob;
    }

    public void mouseClicked(MouseEvent e) {
        for (Line line : ob.textPanel.lines) {
            line.checkEndLine(e.getPoint());
            for (Char ch : line.chars) {
                ch.setCaret(e.getPoint());
            }
        }
        ob.scrollPanel.revalidate();
        ob.scrollPanel.repaint();
        ob.frame.requestFocus();
    }

    public void mousePressed(java.awt.event.MouseEvent e){
        one = e.getPoint();
    }

    public void mouseReleased(java.awt.event.MouseEvent e){
        two = e.getPoint();
        for (Line line : ob.textPanel.lines) {
            line.checkEndLine(two);
            for (Char ch : line.chars) {
                ch.setIsSelect(ch.contains(one, two));
            }
        }
        ob.scrollPanel.revalidate();
        ob.scrollPanel.repaint();
        ob.frame.requestFocus();
    }

    public void mouseDragged(MouseEvent e) {
        Point now = e.getPoint();
        for (Line line : ob.textPanel.lines) {
            line.checkEndLine(now);
            for (Char ch : line.chars) {
                ch.setIsSelect(ch.contains(one, now));
            }
        }
        ob.scrollPanel.revalidate();
        ob.scrollPanel.repaint();
        ob.frame.requestFocus();
    }

}
