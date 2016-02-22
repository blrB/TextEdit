package MyText;

import javax.swing.event.MouseInputAdapter;
import java.awt.*;
import java.awt.event.MouseEvent;

/**
 * Created by andrey on 20/02/16.
 */
public class TextMouseHandler extends MouseInputAdapter {

    private static Point one;
    private static Point two;

    public void mouseClicked(MouseEvent e) {
        for (Line line : TextPanel.lines) {
            line.checkEndLine(e.getPoint());
            for (Char ch : line.chars) {
                ch.setCaret(e.getPoint());
            }
        }
        MainWindow.scrollPanel.revalidate();
        MainWindow.scrollPanel.repaint();
        MainWindow.frame.requestFocus();
    }

    public void mousePressed(java.awt.event.MouseEvent e){
        one = e.getPoint();
        System.out.println("The button ONE " + one.getX() + " " + one.getY() + "\n");
    }

    public void mouseReleased(java.awt.event.MouseEvent e){
        two = e.getPoint();
        for (Line line : TextPanel.lines) {
            line.checkEndLine(two);
            for (Char ch : line.chars) {
                ch.setIsSelect(ch.contains(one, two));
            }
        }
        System.out.println("The button TWO " + two.getX() + " " + two.getY() + "\n");
        MainWindow.scrollPanel.revalidate();
        MainWindow.scrollPanel.repaint();
        MainWindow.frame.requestFocus();
    }

    public void mouseDragged(MouseEvent e) {
        Point now = e.getPoint();
        for (Line line : TextPanel.lines) {
            line.checkEndLine(now);
            for (Char ch : line.chars) {
                ch.setIsSelect(ch.contains(one, now));
            }
        }
        System.out.println("The button have been dragged " + now.getX() + " " + now.getY() + "\n");
        MainWindow.scrollPanel.revalidate();
        MainWindow.scrollPanel.repaint();
        MainWindow.frame.requestFocus();
    }

}
