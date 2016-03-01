package mytext;

import javax.swing.*;
import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.event.ActionEvent;
import java.awt.geom.Rectangle2D;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by andrey on 19/02/16.
 */
public class TextPanel  extends JComponent{

    private MainWindow mainWindow;
    private Caret caret;
    private List<Line> lines = new ArrayList<Line>();

    public TextPanel(MainWindow mainWindow){
        this.mainWindow = mainWindow;
        setLayout(new BorderLayout());
    }

    protected void paintComponent(Graphics graphics) {
        Graphics2D graphics2d = (Graphics2D) graphics;
        for (Line line: lines) {
            line.setMaxHightNumber(0);
            for (Char ch : line.getChars()) {
                Font f = new Font(ch.getFontType(), ch.getFontStyles(), ch.getFontSize());
                graphics2d.setFont(f);
                FontMetrics fm = graphics2d.getFontMetrics();
                line.setMaxHight(fm.getHeight());
            }
            if (line.getMaxHight() == 0) line.setMaxHightNumber(15);
        }
        int y=10;
        int xMax = 0;
        int lineY=-1;
        for (Line line: lines) {
            y += line.getMaxHight();
            int x=10;
            lineY++;
            int letterX=0;
            for (Char ch: line.getChars()) {
                letterX++;
                graphics2d.setColor(Color.BLACK);
                Font f = new Font(ch.getFontType(), ch.getFontStyles(), ch.getFontSize());
                graphics2d.setFont(f);
                FontMetrics fm =  graphics2d.getFontMetrics();
                if (ch.getIsSelect()) {
                    graphics2d.setColor(Color.gray);
                    Rectangle2D rect = new Rectangle
                            (x, y-line.getMaxHight()+3, fm.stringWidth(ch.getStringCh()), line.getMaxHight()-1);
                    graphics2d.fill(rect);
                    graphics2d.setColor(Color.blue);
                }
                graphics2d.drawString(ch.getStringCh(),x,y);
                ch.setHeight(fm.getHeight());
                ch.setWight(fm.stringWidth(ch.getStringCh()));
                ch.setX(x);
                ch.setY(y);
                ch.setNumberLine(lineY);
                x += fm.stringWidth(ch.getStringCh())+1;
                if (caret.getCaretX() == letterX && caret.getCaretY()  == lineY){
                    caret.setCaretCordinateX(x);
                    caret.setCaretCordinateY(y);
                }
            }
            line.setMaxLength(x);
            line.setCordinateY(y);
            line.setNumberLine(lineY);
            xMax = xMax < x ? x : xMax;
            if (caret.getCaretX() == 0 && caret.getCaretY() == lineY){
                caret.setCaretCordinateX(10);
                caret.setCaretCordinateY(y);
            }
        }
        setPreferredSize(new Dimension(xMax + 50, y + 50));
    }

    public Caret getCaret(){
        return caret;
    }

    public List<Line> getLine(){
        return lines;
    }

    public void setLine(ArrayList<Line> lines) {
        this.lines = lines;
    }

    public void createInput(){
        caret = new Caret(mainWindow);
        CaretTimer caretTimer = new CaretTimer(this);
        Line newLine = new Line(mainWindow);
        lines.add(newLine);
    }

    public void newLine() {
        int lost = caret.getCaretX();
        Line newLine = lines.get(caret.getCaretY()).copySubLine
                (caret.getCaretX(), lines.get(caret.getCaretY()).size());
        lines.get(caret.getCaretY()).removeBack(lost);
        lines.add(caret.getCaretY()+1, newLine);
        caret.setCaretX(0);
        caret.incrementCaretY();
    }

    public void deleteChar() {
        if (caret.getCaretX()==0 && caret.getCaretY()==0){
        } else if (caret.getCaretX()==0){
            caret.setCaretX(lines.get(caret.getCaretY()-1).size());
            if (lines.get(caret.getCaretY()).size() != 0){
                for (Char ch: lines.get(caret.getCaretY()).getChars()){
                    lines.get(caret.getCaretY()-1).getChars().add(ch);
                }
            }
            lines.remove(caret.getCaretY());
            caret.decrementCaretY();
        } else{
            lines.get(caret.getCaretY()).remove(caret.getCaretX());
            caret.decrementCaretX();
        }
    }

    public void deleteNextChar() {
        if (caret.getCaretX()==lines.get(caret.getCaretY()).getChars().size() &&
                caret.getCaretY()==lines.size()-1){
        } else if (caret.getCaretX()==lines.get(caret.getCaretY()).getChars().size()){
            if (lines.get(caret.getCaretY()+1).size() != 0){
                for (Char ch: lines.get(caret.getCaretY()+1).getChars()){
                    lines.get(caret.getCaretY()).getChars().add(ch);
                }
            }
            lines.remove(caret.getCaretY()+1);
        } else{
            lines.get(caret.getCaretY()).remove(caret.getCaretX()+1);
        }
    }

    public void changeSizeFont(ActionEvent e){
        JComboBox cb = (JComboBox)e.getSource();
        String change = (String) cb.getSelectedItem();
        for (Line line: lines) {
            for (Char ch : line.getChars()) {
                if (ch.getIsSelect()) {
                    ch.setFontSize(Integer.parseInt(change));
                }
            }
        }
        mainWindow.updateWindow();
    }

    public void changeTypeFont(ActionEvent e){
        JComboBox cb = (JComboBox)e.getSource();
        String change = (String) cb.getSelectedItem();
        for (Line line: lines) {
            for (Char ch : line.getChars()) {
                if (ch.getIsSelect()) {
                    ch.setFontType(change);
                }
            }
        }
        mainWindow.updateWindow();
    }

    public void changeStyleOnBold(){
        boolean bold = false, notBold = false;
        for (Line line: lines) {
            for (Char ch : line.getChars()) {
                if (ch.getIsSelect()) {
                    if (ch.getFontStyles() == 0 || ch.getFontStyles() == 2) notBold = true;
                    if (ch.getFontStyles() == 1 || ch.getFontStyles() == 3) bold = true;
                }
            }
        }
        for (Line line: lines) {
            for (Char ch : line.getChars()) {
                if (ch.getIsSelect()) {
                    if (bold && notBold) ch.setNormalizationBold();
                    else ch.setFontStyles(1);
                }
            }
        }
        mainWindow.updateWindow();
    }

    public void changeStyleOnItalic(){
        boolean italic = false, notItalic = false;
        for (Line line: lines) {
            for (Char ch : line.getChars()) {
                if (ch.getIsSelect()){
                    if (ch.getFontStyles() == 0 || ch.getFontStyles() == 1) notItalic = true;
                    if (ch.getFontStyles() == 2 || ch.getFontStyles() == 3) italic = true;
                }
            }
        }
        for (Line line: lines) {
            for (Char ch : line.getChars()) {
                if (ch.getIsSelect()){
                    if (italic && notItalic) ch.setNormalizationItalic();
                    else ch.setFontStyles(2);
                }
            }
        }
        mainWindow.updateWindow();
    }

    public void copy() {
        String string = "";
        for (Line line: lines) {
            for (Char ch : line.getChars()) {
                if (ch.getIsSelect()){
                    string += ch.getStringCh();
                }
            }
            if (line.getChars().size()-1 >= 0 && line.getChars().get(line.getChars().size()-1).getIsSelect()) {
                string += "\n";
            }
        }
        StringSelection data = new StringSelection(string);
        try {
            Clipboard c = Toolkit.getDefaultToolkit().getSystemClipboard();
            c.setContents(data,null);
        } catch(Exception e) {
            JOptionPane.showMessageDialog
                    (null, "Can't copy text", "ERROR", JOptionPane.ERROR_MESSAGE|JOptionPane.OK_OPTION);
        }
    }

    public void paste() {
        try {
            Clipboard c = Toolkit.getDefaultToolkit().getSystemClipboard();
            String s = (String) c.getData(DataFlavor.stringFlavor);
            for (int i = 0; i < s.length(); i++){
                if (s.charAt(i) == '\n') {
                    newLine();
                } else {
                    lines.get(caret.getCaretY()).add(caret.getCaretX(), s.charAt(i));
                    caret.incrementCaretX();
                }
            }
        } catch(Exception e) {
            JOptionPane.showMessageDialog
                    (null, "Can't past text", "ERROR", JOptionPane.ERROR_MESSAGE|JOptionPane.OK_OPTION);
        }
        deleteSelectedText();
    }

    public void cut() {
        String string = "";
        for (Line line: lines) {
            for (Char ch : line.getChars()) {
                if (ch.getIsSelect()){
                    string += ch.getStringCh();
                }
            }
            if (line.getChars().size()-1 >= 0 && line.getChars().get(line.getChars().size()-1).getIsSelect()){
                string += "\n";
            }
        }
        StringSelection data = new StringSelection(string);
        try {
            Clipboard c = Toolkit.getDefaultToolkit().getSystemClipboard();
            c.setContents(data,null);
        } catch(Exception e) {
            JOptionPane.showMessageDialog
                    (null, "Can't cut text", "ERROR", JOptionPane.ERROR_MESSAGE|JOptionPane.OK_OPTION);
        }
        deleteSelectedText();
    }

    public boolean deleteSelectedText(){
        boolean setCaret = true;
        for (int y = lines.size() - 1; y >= 0; y--) {
            if (!setCaret && caret.getCaretX() == 0) deleteChar();
            for (int x = lines.get(y).getChars().size() - 1; x >= 0; x--) {
                if (lines.get(y).getChars().get(x).getIsSelect()) {
                    if (setCaret) {
                        caret.setCaretX(x + 1);
                        caret.setCaretY(y);
                        setCaret = false;
                    }
                    deleteChar();
                }
            }
        }
        return setCaret;
    }

    public void backSpaceKey(){
        if (deleteSelectedText()) deleteChar();
    }

    public void deleteKey(){
        if (deleteSelectedText()) deleteNextChar();
    }

    public void enterKey(){
        deleteSelectedText();
        newLine();
    }

    public void inputCharKey(char keyChar){
        deleteSelectedText();
        lines.get(caret.getCaretY()).add(caret.getCaretX(), keyChar);
        caret.incrementCaretX();
    }

    public void inverseSelectionNext(){
        for (Line line: lines) {
            for (Char ch : line.getChars()) {
                if (ch.getX() == caret.getCaretCordinateX()
                        && ch.getY() == caret.getCaretCordinateY()){
                    ch.setIsSelect(!ch.getIsSelect());
                }
            }
        }
        caret.incrementCaretX();
    }

    public void inverseSelectionPrev(){
        for (Line line: lines) {
            for (Char ch : line.getChars()) {
                if (ch.getX() + ch.getWight() + 1 == caret.getCaretCordinateX()
                        && ch.getY() == caret.getCaretCordinateY()){
                    ch.setIsSelect(!ch.getIsSelect());
                }
            }
        }
        caret.decrementCaretX();
    }

    public void inverseSelectionUp(){
        int oneX = caret.getCaretCordinateX() - 1;
        int oneY = caret.getCaretCordinateY() - 1;
        int hight;
        if (caret.getCaretY() > 0) {
            hight = lines.get(caret.getCaretY()).getMaxHight();
        } else {
            hight = 0;
        }
        for (Line line: lines) {
            for (Char ch : line.getChars()) {
                if (!ch.getIsSelect()) {
                    ch.setIsSelect(ch.contains(new Point(oneX, oneY), new Point(oneX, oneY - hight)));
                } else if (ch.contains(new Point(oneX, oneY), new Point(oneX, oneY - hight))) {
                    ch.setIsSelect(false);
                }
            }
        }
        caret.decrementCaretY();
    }

    public void inverseSelectionDown(){
        int oneX = caret.getCaretCordinateX() - 1;
        int oneY = caret.getCaretCordinateY() - 1;
        int hight;
        if (caret.getCaretY() < lines.size() - 1) {
            hight = lines.get(caret.getCaretY()).getMaxHight();
        } else {
            hight = 0;
        }
        for (Line line: lines) {
            for (Char ch : line.getChars()) {
                if (!ch.getIsSelect()) {
                    ch.setIsSelect(ch.contains(new Point(oneX, oneY), new Point(oneX, oneY + hight)));
                } else if (ch.contains(new Point(oneX, oneY), new Point(oneX, oneY + hight))) {
                    ch.setIsSelect(false);
                }
            }
        }
        caret.incrementCaretY();
    }

    public void click(Point point) {
        for (Line line : lines) {
            line.checkEndLine(point);
            for (Char ch : line.getChars()) {
                if (ch.contains(point)) {
                    caret.setCaretY(lines.indexOf(line));
                    caret.setCaretX(line.indexOf(ch) + 1);
                }
            }
        }
        mainWindow.updateWindow();
    }


    public void click(Point one, Point two) {
        for (Line line : lines) {
            line.checkEndLine(two);
            for (Char ch : line.getChars()) {
                ch.setIsSelect(ch.contains(one, two));
                if (ch.contains(two)) {
                    caret.setCaretY(lines.indexOf(line));
                    caret.setCaretX(line.indexOf(ch) + 1);
                }
            }
        }
        mainWindow.updateWindow();
    }

}
