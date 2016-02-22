package MyText;

import java.awt.*;
import java.awt.geom.Point2D;

/**
 * Created by andrey on 20/02/16.
 */
public class Char{

    private char ch;
    private int height;
    private int wight;
    private int x;
    private int y;
    private int nomberString;
    private int nomberLetter;
    private Font font;
    private Boolean isSelect;

    public Char(char ch){
        this.ch = ch;
        isSelect = false;
        font = new Font("Arial", Font.PLAIN , 12);
    }

    public String getStringCh(){
        return Character.toString(ch);
    }

    public char getCharCh(){
        return ch;
    }

    public String getFontType(){
        return font.getFontName();
    }

    public int getFontStyles(){
        return font.getStyle();
    }

    public int getFontSize(){
        return font.getSize();
    }

    public void setCaret(Point2D p){
        boolean answer = (x <= p.getX() && x+wight >= p.getX() && y-height <= p.getY() && y >= p.getY());
        if (answer){
            TextPanel.setCaretCordinateX(x);
            TextPanel.setCaretCordinateY(y);
            TextPanel.setCaretX(nomberLetter);
            TextPanel.setCaretY(nomberString);
        }
    }

    public boolean contains(Point2D p){
        return (x <= p.getX() && x+wight >= p.getX() && y-height <= p.getY() && y >= p.getY());
    }

    public boolean contains(Point one, Point two) {
        if (one.getX() == two.getX() && one.getY() == two.getY()) return contains(one);
        return true;
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

    public void setNomberLetter(int x) { nomberLetter = x;}

    public void setNomberString(int y) { nomberString = y;}

    public void selection(Point one, Point now) {
        this.setIsSelect(this.contains(one));
        this.setIsSelect(this.contains(now));
    }

}
