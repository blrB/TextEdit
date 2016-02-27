package mytext;

import javax.swing.*;
import java.awt.*;

/**
 * Created by andrey on 27/02/16.
 */
public class Caret {

    private MainWindow mainWindow;
    private TextPanel textPanel;
    private JFrame frame;
    private JScrollPane scrollPanel;
    private int caretX;
    private int caretY;
    private int caretCordinateX;
    private int caretCordinateY;

    public Caret(MainWindow mainWindow){
        this.mainWindow = mainWindow;
        textPanel = mainWindow.textPanel;
        frame = mainWindow.frame;
        scrollPanel = mainWindow.scrollPanel;
        caretX=0;
        caretY=0;
    }

    public void setCaretX(int x){
        caretX = x;
    }

    public int getCaretX(){
        return caretX;
    }

    public void setCaretY(int y){
        caretY = y;
    }

    public int getCaretY(){
        return caretY;
    }

    public void setCaretCordinateX(int x){
        caretCordinateX = x;
    }

    public void setCaretCordinateY(int y){
        caretCordinateY = y;
    }

    public int getCaretCordinateX(){
        return caretCordinateX;
    }

    public int getCaretCordinateY(){
        return caretCordinateY;
    }

    public void incrementCaretX() {
        if (textPanel.lines.get(getCaretY()).size() == caretX && textPanel.lines.size() == caretY +1){
        } else if (textPanel.lines.get(getCaretY()).size() > caretX){
            caretX++;
        } else {
            if (caretY < textPanel.lines.size() - 1) {
                caretY++;
                setCaretX(0);
            }
        }
    }

    public void incrementCaretY() {
        if (caretY < textPanel.lines.size() - 1) {
            caretY++;
            if (textPanel.lines.get(getCaretY()).size() < caretX){
                setCaretX(textPanel.lines.get(getCaretY()).size());
            }
        }
    }

    public void decrementCaretX() {
        if (caretX == 0 && caretY ==0) {
        } else if (caretX != 0){
            caretX--;
        } else {
            decrementCaretY();
            setCaretX(textPanel.lines.get(getCaretY()).size());
        }
    }

    public void decrementCaretY() {
        if (caretY !=0) {
            caretY--;
            if (textPanel.lines.get(getCaretY()).size() < caretX){
                setCaretX(textPanel.lines.get(getCaretY()).size());
            }
        }
    }

    public void drawCaret() {
        int caretCordinateX = getCaretCordinateX();
        int caretCordinateY = getCaretCordinateY();
        Graphics2D graphics2d = (Graphics2D) textPanel.getGraphics();
        try {
            Font f = getPrevFont();
            graphics2d.setFont(f);
        } catch (Exception e){}
        graphics2d.drawString("_",caretCordinateX,caretCordinateY);
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        graphics2d.setColor(textPanel.getBackground());
        graphics2d.drawString("_",caretCordinateX,caretCordinateY);
    }

    public Font getPrevFont(){
        if (getCaretX() == 0 && getCaretY() == 0){
            return new Font(Font.MONOSPACED, 0 , 12);
        } else {
            if (textPanel.lines.get(getCaretY()).size() != 0
                    && getCaretX() != 0) {
                Char prevCh = textPanel.lines.get(getCaretY())
                        .chars.get(getCaretX() - 1);
                return new Font(prevCh.getFontType(), 0, prevCh.getFontSize());
            } else {
                int i = getCaretY();
                while (textPanel.lines.get(i - 1).size() == 0 && i > 0) i--;
                Char prevCh = textPanel.lines.get(i - 1)
                        .chars.get(textPanel.lines.get(i - 1).chars.size() - 1);
                return new Font(prevCh.getFontType(), 0, prevCh.getFontSize());
            }
        }
    }

    void followCaret(){
        int x = 0;
        if (textPanel.caret.getCaretCordinateX() > frame.getWidth()){
            x = textPanel.caret.getCaretCordinateX();
        }
        int y = textPanel.caret.getCaretCordinateY() -
                textPanel.lines.get(textPanel.caret.getCaretY()).getMaxHight();
        JViewport scrollP = scrollPanel.getViewport();
        scrollP.setViewPosition(new Point(x, y));
        scrollPanel.setViewport(scrollP);
    }

}
