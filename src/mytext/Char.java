package mytext;

import java.awt.*;
import java.awt.geom.Point2D;

/**
 * Created by andrey on 20/02/16.
 */
public class Char{

    private TextPanel textPanel;
    private Caret caret;
    private Font font;
    private Boolean isSelect;
    private char ch;
    private int height;
    private int wight;
    private int x;
    private int y;
    private int fontStyle;
    private int numberLine;

    public Char(char ch, MainWindow mainWindow){
        textPanel = mainWindow.getTextPanel();
        caret = textPanel.getCaret();
        this.ch = ch;
        isSelect = false;
        font = getPrevFont();
    }

    public Char(Char ch, MainWindow mainWindow) {
        textPanel = mainWindow.getTextPanel();
        caret = textPanel.getCaret();
        this.ch = ch.getCharCh();
        this.font = ch.getFont();
        this.isSelect = ch.getIsSelect();
        this.fontStyle = ch.getFontStyles();
    }

    public Char(char ch, String font, String style, String size, MainWindow mainWindow) {
        textPanel = mainWindow.getTextPanel();
        caret = textPanel.getCaret();
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
        font = new Font(type, getFontStyles(), getFontSize());
    }

    public void setFontStyles(int style){
        if (fontStyle == Font.PLAIN && style == Font.BOLD) {
            fontStyle = Font.BOLD;
        } else if (fontStyle == Font.BOLD && style == Font.BOLD) {
            fontStyle = Font.PLAIN;
        } else if (fontStyle == Font.PLAIN && style == Font.ITALIC) {
            fontStyle = Font.ITALIC;
        } else if (fontStyle == Font.ITALIC && style == Font.ITALIC) {
            fontStyle = Font.PLAIN;
        } else if (fontStyle == Font.BOLD && style == Font.ITALIC) {
            fontStyle = Font.BOLD + Font.ITALIC;
        } else if (fontStyle == Font.ITALIC && style == Font.BOLD) {
            fontStyle = Font.BOLD + Font.ITALIC;
        } else if (fontStyle == Font.BOLD + Font.ITALIC && style == Font.BOLD) {
            fontStyle = Font.ITALIC;
        } else if (fontStyle == Font.BOLD + Font.ITALIC && style == Font.ITALIC) {
            fontStyle = Font.BOLD;
        }
    }

    public Font getPrevFont(){
        if (caret.getCaretX() == 0 && caret.getCaretY() == 0){
            return new Font(Font.MONOSPACED, 0 , 12);
        } else {
            if (textPanel.getLine().get(caret.getCaretY()).size() != 0
                    && caret.getCaretX() != 0) {
                Char prevCh = textPanel.getLine().get(caret.getCaretY())
                        .getChars().get(caret.getCaretX() - 1);
                fontStyle = prevCh.getFontStyles();
                return new Font(prevCh.getFontType(), 0, prevCh.getFontSize());
            } else {
                int i = caret.getCaretY() - 1;
                while (i > 0 && textPanel.getLine().get(i).size() == 0) i--;
                if (textPanel.getLine().get(i).size() != 0) {
                    Char prevCh = textPanel.getLine().get(i)
                            .getChars().get(textPanel.getLine().get(i).getChars().size() - 1);
                    fontStyle = prevCh.getFontStyles();
                    return new Font(prevCh.getFontType(), 0, prevCh.getFontSize());
                } else{
                    return new Font(Font.MONOSPACED, 0 , 12);
                }
            }
        }
    }

    public void setFontSize(int size){
        font = new Font(getFontType(), getFontStyles(), size);
    }

    public boolean contains(Point2D p){
        return (x <= p.getX() && x+wight >= p.getX() && y-height <= p.getY() && y >= p.getY());
    }

    public boolean contains(Point one, Point two) {
        int height = textPanel.getLine().get(numberLine).getMaxHight();
        Point upPoint = (one.getY() < two.getY()) ? one : two;
        Point downPoint = (one.getY() < two.getY()) ? two : one;
        Point leftPoint = (one.getX() < two.getX()) ? one : two;
        Point rightPoint = (one.getX() < two.getX()) ? two : one;
        if (y < downPoint.getY() || y-height > upPoint.getY()) {
            return ((x >= upPoint.getX()) && y - height < upPoint.getY() && y >= upPoint.getY() ||
                    (x <= downPoint.getX()) && y - height < downPoint.getY() && y >= downPoint.getY() ||
                    (y - height >= upPoint.getY() && y < downPoint.getY()));
        } else {
            return (y-height <= leftPoint.getY() && y >= leftPoint.getY()) &&
                    (y-height <= rightPoint.getY() && y >= rightPoint.getY()) &&
                    (x > leftPoint.getX() && x <= rightPoint.getX());
        }
    }

    public void setNumberLine(int number){
        numberLine = number;
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
        if (fontStyle == Font.PLAIN) {
            fontStyle = Font.BOLD;
        } else if (fontStyle == Font.ITALIC) {
            fontStyle = Font.BOLD + Font.ITALIC;
        }
    }

    public void setNormalizationItalic() {
        if (fontStyle == Font.PLAIN) {
            fontStyle = Font.ITALIC;
        } else if (fontStyle == Font.BOLD) {
            fontStyle = Font.BOLD + Font.ITALIC;
        }
    }
}
