package MyText;

import java.awt.geom.Point2D;
import java.util.ArrayList;

/**
 * Created by andrey on 20/02/16.
 */
public class Line {

    private int maxHight;
    private int maxLength;
    private int cordinateY;
    private int nomberLine;
    private MainWindow ob;

    public ArrayList<Char> chars = new ArrayList<Char>();

    public Line(MainWindow ob) {
        this.ob = ob;
        maxHight = 15;
        maxLength = 0;
    }

    public void add(char ch) {
        chars.add(new Char(ch, ob));
    }

    public void add(int i, char ch) {
        chars.add(i, new Char(ch, ob));
        ob.textPanel.incrementCaretX();
    }

    public void add(Char ch) {
        chars.add(new Char(ch, ob));
    }

    public void add(String ch, String font, String style, String size) {
        chars.add(new Char(ch.charAt(0), font, style, size, ob));
    }

    public void remove(int caretX) {
        chars.remove(caretX-1);
    }

    public Line copySubLine(int x1, int x2){
        Line newLine = new Line(ob);
        for (int i = x1; i < x2; i++){
            newLine.add(this.chars.get(i));
        }
        return newLine;
    }

    public void removeBack(int x){
        int size = this.size();
        for (int i = size; i > x; i--){
            this.remove(i);
        }
    }

    public void setMaxHight(int now) {
        if (now > maxHight) maxHight = now;
    }

    public int getMaxHight() {
        return maxHight;
    }

    public int size() {
        return chars.size();
    }

    public void setMaxLength(int l) { maxLength = l; }

    public int getMaxLength() { return maxLength; }

    public void setCordinateY(int c) { cordinateY = c; }

    public void setNomberLine(int n) { nomberLine = n; }

    public void checkEndLine(Point2D p) {
        if (cordinateY-maxHight <= p.getY()){
            //ob.textPanel.setCaretCordinateX(maxLength);
            //ob.textPanel.setCaretCordinateY(cordinateY);
            ob.textPanel.setCaretX(chars.size());
            ob.textPanel.setCaretY(nomberLine);
            if (10 >= p.getX()){
                //ob.textPanel.setCaretCordinateX(10);
                ob.textPanel.setCaretX(0);
            }
        }
    }

    public void setMaxHightNumber(int n) { maxHight = n;}

}
