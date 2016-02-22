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

    public ArrayList<Char> chars = new ArrayList<Char>();

    public Line() {
        maxHight = 15;
        maxLength = 0;
    }

    public void add(char ch) {
        chars.add(new Char(ch));
    }

    public void add(int i, char ch) {
        chars.add(i, new Char(ch));
        TextPanel.incrementCaretX();
    }

    public void remove(int caretX) {
        chars.remove(caretX-1);
    }

    public static Line copySubLine(Line line, int x1, int x2){
        Line newLine = new Line();
        for (int i = x1; i < x2; i++){
            newLine.add(line.chars.get(i).getCharCh());
        }
        return newLine;
    }

    public static void removeBack(Line line, int x){
        int size = line.size();
        for (int i = size; i > x; i--){
            line.remove(i);
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

    public void setCordinateY(int c) { cordinateY = c; }

    public void setNomberLine(int n) { nomberLine = n; }

    public void checkEndLine(Point2D p) {
        if (p.getX() >= maxLength  && cordinateY-maxHight <= p.getY()){
            TextPanel.setCaretCordinateX(maxLength);
            TextPanel.setCaretCordinateY(cordinateY);
            TextPanel.setCaretX(chars.size());
            TextPanel.setCaretY(nomberLine);
        }
    }
}
