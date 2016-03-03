package mytext;

import java.awt.geom.Point2D;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by andrey on 20/02/16.
 */
public class Line {

    private MainWindow mainWindow;
    private TextPanel textPanel;
    private Caret caret;
    private int maxHight;
    private int maxLength;
    private int cordinateY;
    private int numberLine;
    private List<Char> chars = new LinkedList<Char>();

    public Line(MainWindow mainWindow) {
        this.mainWindow = mainWindow;
        textPanel = mainWindow.getTextPanel();
        caret = textPanel.getCaret();
        maxHight = 15;
        maxLength = 0;
    }

    public List<Char> getChars(){
        return chars;
    }

    public void add(char ch) {
        chars.add(new Char(ch, mainWindow));
    }

    public void add(Char ch) {
        chars.add(new Char(ch, mainWindow));
    }

    public void add(String ch, String font, String style, String size) {
        chars.add(new Char(ch.charAt(0), font, style, size, mainWindow));
    }

    public void add(int i, char ch) {
        chars.add(i, new Char(ch, mainWindow));
    }

    public void remove(int caretX) {
        chars.remove(caretX-1);
    }

    public int indexOf(Char ch) {
        return chars.indexOf(ch);
    }

    public Line copySubLine(int x1, int x2){
        Line newLine = new Line(mainWindow);
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

    public int getCordinateY() {
        return cordinateY;
    }

    public void setNumberLine(int number) {
        this.numberLine = number;
    }

    public void checkEndLine(Point2D p) {
        if (cordinateY-maxHight <= p.getY()){
            caret.setCaretX(chars.size());
            caret.setCaretY(numberLine);
            if (10 >= p.getX()){
                caret.setCaretX(0);
            }
        }
    }

    public void setMaxHightNumber(int n) { maxHight = n;}

}
