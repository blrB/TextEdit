package MyText;

import javax.swing.*;
import java.awt.*;
import java.util.*;

/**
 * Created by andrey on 19/02/16.
 */
public class TextPanel  extends JComponent{

    private static int caretX;
    private static int caretY;
    private static int caretCordinateX;
    private static int caretCordinateY;
    private static TextMouseHandler mouseHandler = new TextMouseHandler();
    public static ArrayList<Line> lines = new ArrayList<Line>();

    public TextPanel(){
        setPreferredSize(new Dimension(3000, 3000));
        addMouseListener(mouseHandler);
        addMouseMotionListener(mouseHandler);
        Line newLine = new Line();
        lines.add(newLine);
        caretX=0;
        caretY=0;
    }

    protected void paintComponent(Graphics graphics) {
        Graphics2D graphics2d = (Graphics2D) graphics;
        int y=20;
        int letterY=-1;
        for (Line line: lines) {
            int x=10;
            letterY++;
            int letterX=0;
            for (Char ch: line.chars) {
                letterX++;
                graphics2d.setColor(Color.BLACK);
                Font f = new Font(ch.getFontType(), ch.getFontStyles(), ch.getFontSize());
                graphics2d.setFont(f);
                FontMetrics fm =  graphics2d.getFontMetrics();
                if (ch.getIsSelect()) graphics2d.setColor(Color.red);
                graphics2d.drawString(ch.getStringCh(),x,y);
                ch.setHeight(fm.getHeight());
                ch.setWight(fm.stringWidth(ch.getStringCh()));
                ch.setX(x);
                ch.setY(y);
                ch.setNomberLetter(letterX);
                ch.setNomberString(letterY);
                x += fm.stringWidth(ch.getStringCh());
                line.setMaxHight(fm.getHeight());
                if (caretX == letterX && caretY == letterY){
                    caretCordinateX = x;
                    caretCordinateY = y;
                }
            }
            line.setMaxLength(x);
            line.setCordinateY(y);
            line.setNomberLine(letterY);
            y += line.getMaxHight();
            if (caretX == 0 && caretY == letterY){
                caretCordinateX = 10;
                caretCordinateY = y - line.getMaxHight();
            }
        }
    }

    public static void setCaretX(int x){
        caretX = x;
    }

    public static int getCaretX(){
        return caretX;
    }

    public static void setCaretY(int y){
        caretY = y;
    }

    public static int getCaretY(){
        return caretY;
    }

    public static void incrementCaretX() {
        if (lines.get(getCaretY()).size() == caretX && lines.size() == caretY +1){
        } else if (lines.get(getCaretY()).size() > caretX){
            caretX++;
        } else {
            if (caretY < lines.size() - 1) {
                caretY++;
                setCaretX(0);
            }
        }
    }

    public static void incrementCaretY() {
        if (caretY < lines.size() - 1) {
            caretY++;
            if (lines.get(getCaretY()).size() < caretX){
                setCaretX(lines.get(getCaretY()).size());
            }
        }
    }

    public static void decrementCaretX() {
        if (caretX == 0 && caretY ==0) {
        } else if (caretX != 0){
            caretX--;
        } else {
            decrementCaretY();
            setCaretX(lines.get(getCaretY()).size());
        }
    }

    public static void decrementCaretY() {
        if (caretY !=0) {
            caretY--;
            if (lines.get(getCaretY()).size() < caretX){
                setCaretX(lines.get(getCaretY()).size());
            }
        }
    }

    public static void newLine() {
        int lost = getCaretX();
        Line newLine = Line.copySubLine
                (lines.get(getCaretY()), getCaretX(), lines.get(getCaretY()).size());
        Line.removeBack(lines.get(getCaretY()), lost);
        lines.add(getCaretY()+1, newLine);
        setCaretX(0);
        incrementCaretY();
    }

    public static void deleteChar() {
        if (getCaretX()==0 && getCaretY()==0){
        } else if (getCaretX()==0){
            lines.remove(getCaretY());
            decrementCaretY();
            setCaretX(lines.get(getCaretY()).size());
        } else{
            lines.get(getCaretY()).remove(getCaretX());
            decrementCaretX();
        }
    }

    public static int getCaretCordinateX(){
        return caretCordinateX;
    }

    public static int getCaretCordinateY(){
        return caretCordinateY;
    }

    public static void setCaretCordinateX(int x){
        caretCordinateX = x;
    }

    public static void setCaretCordinateY(int y) { caretCordinateY = y; }

}
