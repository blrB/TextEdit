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

    private int caretX;
    private int caretY;
    private int caretCordinateX;
    private int caretCordinateY;
    private MainWindow mainWindow;

    public List<Line> lines = new ArrayList<Line>();

    public TextPanel(MainWindow mainWindow){
        this.mainWindow = mainWindow;
        caretX=0;
        caretY=0;
        //Line newLine = new Line(mainWindow);
        //lines.add(newLine);
        TextCaretTimer textCaretTimer = new TextCaretTimer(this);
        setLayout(new BorderLayout());
    }

    protected void paintComponent(Graphics graphics) {
        Graphics2D graphics2d = (Graphics2D) graphics;
        for (Line line: lines) {
            line.setMaxHightNumber(0);
            for (Char ch : line.chars) {
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
            for (Char ch: line.chars) {
                letterX++;
                graphics2d.setColor(Color.BLACK);
                Font f = new Font(ch.getFontType(), ch.getFontStyles(), ch.getFontSize());
                graphics2d.setFont(f);
                FontMetrics fm =  graphics2d.getFontMetrics();
                if (ch.getIsSelect()) {
                    graphics2d.setColor(Color.gray);
                    Rectangle2D rect = new Rectangle
                            (x, y - fm.getHeight() + 3,fm.stringWidth(ch.getStringCh()), fm.getHeight() - 1);
                    graphics2d.fill(rect);
                    graphics2d.setColor(Color.blue);
                }
                graphics2d.drawString(ch.getStringCh(),x,y);
                ch.setHeight(fm.getHeight());
                ch.setWight(fm.stringWidth(ch.getStringCh()));
                ch.setX(x);
                ch.setY(y);
                x += fm.stringWidth(ch.getStringCh())+1;
                if (caretX == letterX && caretY == lineY){
                    caretCordinateX = x;
                    caretCordinateY = y;
                }
            }
            line.setMaxLength(x);
            line.setCordinateY(y);
            line.setNumberLine(lineY);
            xMax = xMax < x ? x : xMax;
            if (caretX == 0 && caretY == lineY){
                caretCordinateX = 10;
                caretCordinateY = y;
            }
        }
        setPreferredSize(new Dimension(xMax + 50, y + 50));
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

    public void incrementCaretX() {
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

    public void incrementCaretY() {
        if (caretY < lines.size() - 1) {
            caretY++;
            if (lines.get(getCaretY()).size() < caretX){
                setCaretX(lines.get(getCaretY()).size());
            }
        }
    }

    public void decrementCaretX() {
        if (caretX == 0 && caretY ==0) {
        } else if (caretX != 0){
            caretX--;
        } else {
            decrementCaretY();
            setCaretX(lines.get(getCaretY()).size());
        }
    }

    public void decrementCaretY() {
        if (caretY !=0) {
            caretY--;
            if (lines.get(getCaretY()).size() < caretX){
                setCaretX(lines.get(getCaretY()).size());
            }
        }
    }

    public void newLine() {
        int lost = getCaretX();
        Line newLine = lines.get(getCaretY()).copySubLine
                (getCaretX(), lines.get(getCaretY()).size());
        lines.get(getCaretY()).removeBack(lost);
        lines.add(getCaretY()+1, newLine);
        setCaretX(0);
        incrementCaretY();
    }

    public void deleteChar() {
        if (getCaretX()==0 && getCaretY()==0){
        } else if (getCaretX()==0){
            setCaretX(lines.get(getCaretY()-1).size());
            if (lines.get(getCaretY()).size() != 0){
                for (Char ch: lines.get(getCaretY()).chars){
                    lines.get(getCaretY()-1).chars.add(ch);
                }
            }
            lines.remove(getCaretY());
            decrementCaretY();
        } else{
            lines.get(getCaretY()).remove(getCaretX());
            decrementCaretX();
        }
    }

    public void deleteNextChar() {
        if (getCaretX()==lines.get(getCaretY()).chars.size() && getCaretY()==lines.size()-1){
        } else if (getCaretX()==lines.get(getCaretY()).chars.size()){
            if (lines.get(getCaretY()+1).size() != 0){
                for (Char ch: lines.get(getCaretY()+1).chars){
                    lines.get(getCaretY()).chars.add(ch);
                }
            }
            lines.remove(getCaretY()+1);
        } else{
            lines.get(getCaretY()).remove(getCaretX()+1);
        }
    }

    public int getCaretCordinateX(){
        return caretCordinateX;
    }

    public int getCaretCordinateY(){
        return caretCordinateY;
    }

    public void changeSizeFont(ActionEvent e){
        JComboBox cb = (JComboBox)e.getSource();
        String change = (String) cb.getSelectedItem();
        for (Line line: lines) {
            for (Char ch : line.chars) {
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
            for (Char ch : line.chars) {
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
            for (Char ch : line.chars) {
                if (ch.getIsSelect()) {
                    if (ch.getFontStyles() == 0 || ch.getFontStyles() == 2) notBold = true;
                    if (ch.getFontStyles() == 1 || ch.getFontStyles() == 3) bold = true;
                }
            }
        }
        for (Line line: lines) {
            for (Char ch : line.chars) {
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
            for (Char ch : line.chars) {
                if (ch.getIsSelect()){
                    if (ch.getFontStyles() == 0 || ch.getFontStyles() == 1) notItalic = true;
                    if (ch.getFontStyles() == 2 || ch.getFontStyles() == 3) italic = true;
                }
            }
        }
        for (Line line: lines) {
            for (Char ch : line.chars) {
                if (ch.getIsSelect()){
                    if (italic && notItalic) ch.setNormalizationItalic();
                    else ch.setFontStyles(2);
                }
            }
        }
        mainWindow.updateWindow();
    }

    public void copy()
    {
        String string = "";
        for (Line line: lines) {
            for (Char ch : line.chars) {
                if (ch.getIsSelect()){
                    string += ch.getStringCh();
                }
            }
            if (line.chars.size()-1 >= 0 && line.chars.get(line.chars.size()-1).getIsSelect()) string += "\n";
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

    public void paste()
    {
        try {
            Clipboard c = Toolkit.getDefaultToolkit().getSystemClipboard();
            String s = (String) c.getData(DataFlavor.stringFlavor);
            for (int i = 0; i < s.length(); i++){
                if (s.charAt(i) == '\n') {
                    newLine();
                } else {
                    lines.get(getCaretY()).add(getCaretX(), s.charAt(i));
                    incrementCaretX();
                }
            }
        } catch(Exception e) {
            JOptionPane.showMessageDialog
                    (null, "Can't past text", "ERROR", JOptionPane.ERROR_MESSAGE|JOptionPane.OK_OPTION);
        }
    }

    public void cut()
    {
        String string = "";
        for (Line line: lines) {
            for (Char ch : line.chars) {
                if (ch.getIsSelect()){
                    string += ch.getStringCh();
                }
            }
            if (line.chars.size()-1 >= 0 && line.chars.get(line.chars.size()-1).getIsSelect()) string += "\n";
        }
        StringSelection data = new StringSelection(string);
        try {
            Clipboard c = Toolkit.getDefaultToolkit().getSystemClipboard();
            c.setContents(data,null);
        } catch(Exception e) {
            JOptionPane.showMessageDialog
                    (null, "Can't cut text", "ERROR", JOptionPane.ERROR_MESSAGE|JOptionPane.OK_OPTION);
        }
    }

}
