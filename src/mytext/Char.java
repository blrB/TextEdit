package mytext;

import java.awt.*;
import java.awt.geom.Point2D;

/**
 * Created by andrey on 20/02/16.
 */
public class Char{

    //private MainWindow mainWindow;
    private TextPanel textPanel;
    private char ch;
    private int height;
    private int wight;
    private int x;
    private int y;
    private int fontStyle;
    private Font font;
    private Boolean isSelect;

    public Char(char ch, MainWindow mainWindow){
        //this.mainWindow = mainWindow;
        textPanel = mainWindow.textPanel;
        this.ch = ch;
        isSelect = false;
        font = getPrevFont();
    }

    public Char(Char ch, MainWindow mainWindow) {
        //this.mainWindow = mainWindow;
        textPanel = mainWindow.textPanel;
        this.ch = ch.getCharCh();
        this.font = ch.getFont();
        this.isSelect = ch.getIsSelect();
        this.fontStyle = ch.getFontStyles();
    }

    public Char(char ch, String font, String style, String size, MainWindow mainWindow) {
        //this.mainWindow = mainWindow;
        textPanel = mainWindow.textPanel;
        this.ch = ch;
        this.font = new Font(font, Integer.parseInt(style) , Integer.parseInt(size));
        this.isSelect = false;
        this.fontStyle = Integer.parseInt(style);
    }

    public String getStringCh(){
        return Character.toString(ch);
    }

    public char getCharCh(){
        return ch;
    }

    public Font getFont() {
        return font;
    }

    public String getFontType(){
        return font.getFontName();
    }

    public int getFontStyles(){
        return fontStyle;
    }

    public int getFontSize(){
        return font.getSize();
    }

    public void setFontType(String type){
        font = new Font(type, getFontStyles(), font.getSize());
    }

    public void setFontStyles(int style){
        if (fontStyle == 0 && style == 1) {
            fontStyle = 1;
        } else if (fontStyle == 1 && style == 1) {
            fontStyle = 0;
        } else if (fontStyle == 0 && style == 2) {
            fontStyle = 2;
        } else if (fontStyle == 2 && style == 2) {
            fontStyle = 0;
        } else if (fontStyle == 1 && style == 2) {
            fontStyle = 3;
        } else if (fontStyle == 2 && style == 1) {
            fontStyle = 3;
        } else if (fontStyle == 3 && style == 1) {
            fontStyle = 2;
        } else if (fontStyle == 3 && style == 2) {
            fontStyle = 1;
        }
    }

    public Font getPrevFont(){
        if (textPanel.getCaretX() == 0 && textPanel.getCaretY() == 0){
            return new Font(Font.MONOSPACED, 0 , 12);
        } else {
            if (textPanel.lines.get(textPanel.getCaretY()).size() != 0
                    && textPanel.getCaretX() != 0) {
                Char prevCh = textPanel.lines.get(textPanel.getCaretY())
                        .chars.get(textPanel.getCaretX() - 1);
                fontStyle = prevCh.getFontStyles();
                return new Font(prevCh.getFontType(), 0, prevCh.getFontSize());
            } else {
                int i = textPanel.getCaretY();
                while (textPanel.lines.get(i - 1).size() == 0 && i > 0) i--;
                Char prevCh = textPanel.lines.get(i - 1)
                        .chars.get(textPanel.lines.get(i - 1).chars.size() - 1);
                fontStyle = prevCh.getFontStyles();
                return new Font(prevCh.getFontType(), 0, prevCh.getFontSize());
            }
        }
    }

    public void setFontSize(int size){
        font = new Font(font.getFontName(), getFontStyles(), size);
    }

    public boolean contains(Point2D p){
        return (x <= p.getX() && x+wight >= p.getX() && y-height <= p.getY() && y >= p.getY());
    }

    public boolean contains(Point one, Point two) {
        Point upPoint = (one.getY() < two.getY()) ? one : two;
        Point downPoint = (one.getY() < two.getY()) ? two : one;
        Point leftPoint = (one.getX() < two.getX()) ? one : two;
        Point rightPoint = (one.getX() < two.getX()) ? two : one;

        if (y < downPoint.getY() || y-height > upPoint.getY()) {
            return ((x >= upPoint.getX()) && y - height < upPoint.getY() && y >= upPoint.getY() ||
                    (x <= downPoint.getX()) && y - height <= downPoint.getY() && y >= downPoint.getY() ||
                    (y - height >= upPoint.getY() && y <= downPoint.getY()));
        } else {
            return (y-height <= leftPoint.getY() && y >= leftPoint.getY()) &&
                    (y-height <= rightPoint.getY() && y >= rightPoint.getY()) &&
                    (x > leftPoint.getX() && x <= rightPoint.getX());
        }
    }

    public void setIsSelect(boolean selected){ isSelect = selected;}

    public boolean getIsSelect(){ return isSelect; }

    public void setHeight(int height){ this.height = height;}

    public int getHeight(){ return height;}

    public void setWight(int wight){ this.wight = wight;}

    public int getWight(){ return wight;}

    public void setX(int x){ this.x = x;}

    public int getX(){ return x;}

    public void setY(int y){ this.y = y;}

    public int getY(){ return y;}

    public void setNormalizationBold() {
        if (fontStyle == 0) {
            fontStyle = 1;
        } else if (fontStyle == 2) {
            fontStyle = 3;
        }
    }

    public void setNormalizationItalic() {
        if (fontStyle == 0) {
            fontStyle = 2;
        } else if (fontStyle == 1) {
            fontStyle = 3;
        }
    }

}
