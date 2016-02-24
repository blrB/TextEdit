package MyText;

import javax.swing.*;
import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * Created by andrey on 21/02/16.
 */
public class TextKeyListener implements KeyListener {

    private MainWindow ob;
    private boolean setCaret = true;

    public TextKeyListener(Object ob){
        this.ob = (MainWindow)ob;
    }

    public void keyPressed(KeyEvent keyEvent) {
        if (keyEvent.isControlDown() && keyEvent.getKeyCode() == KeyEvent.VK_C) {
            copy();
        } else {
            setCaret = true;
            if (!keyEvent.isControlDown()) deleteSelectedText();
            if (keyEvent.isControlDown() && keyEvent.getKeyCode() == KeyEvent.VK_X) {
                cut();
                deleteSelectedText();
            } else if (keyEvent.isControlDown() && keyEvent.getKeyCode() == KeyEvent.VK_V) {
                deleteSelectedText();
                paste();
            } else if (KeyEvent.VK_ENTER == keyEvent.getKeyCode()) {
                ob.textPanel.newLine();
            } else if (KeyEvent.VK_BACK_SPACE == keyEvent.getKeyCode()) {
                if (setCaret) ob.textPanel.deleteChar();
            } else if (KeyEvent.VK_DELETE == keyEvent.getKeyCode()) {
                if (setCaret) ob.textPanel.deleteNextChar();
            } else if (KeyEvent.VK_LEFT == keyEvent.getKeyCode()) {
                ob.textPanel.decrementCaretX();
            } else if (KeyEvent.VK_RIGHT == keyEvent.getKeyCode()) {
                ob.textPanel.incrementCaretX();
            } else if (KeyEvent.VK_UP == keyEvent.getKeyCode()) {
                ob.textPanel.decrementCaretY();
            } else if (KeyEvent.VK_DOWN == keyEvent.getKeyCode()) {
                ob.textPanel.incrementCaretY();
            } else if (!systemKey(keyEvent.getKeyCode())) {
                ob.textPanel.lines.get(ob.textPanel.getCaretY())
                        .add(ob.textPanel.getCaretX(), keyEvent.getKeyChar());
            }
            ob.scrollPanel.revalidate();
            ob.scrollPanel.repaint();
            ob.frame.requestFocus();
        }
    }

    public void keyReleased(KeyEvent keyEvent) {
    }

    public void keyTyped(KeyEvent keyEvent) {
    }

    boolean systemKey(int e)
    {
        return (e == KeyEvent.VK_ALT ||
                e == KeyEvent.VK_SHIFT ||
                e == KeyEvent.VK_CAPS_LOCK ||
                e == KeyEvent.VK_ESCAPE ||
                e == KeyEvent.VK_F1 ||
                e == KeyEvent.VK_F2 ||
                e == KeyEvent.VK_F3 ||
                e == KeyEvent.VK_F4 ||
                e == KeyEvent.VK_F5 ||
                e == KeyEvent.VK_F6 ||
                e == KeyEvent.VK_F7 ||
                e == KeyEvent.VK_F8 ||
                e == KeyEvent.VK_F9 ||
                e == KeyEvent.VK_F10 ||
                e == KeyEvent.VK_F11 ||
                e == KeyEvent.VK_F12 ||
                e == KeyEvent.VK_TAB ||
                e == KeyEvent.VK_WINDOWS ||
                e == KeyEvent.VK_INSERT ||
                e == KeyEvent.VK_PAGE_DOWN ||
                e == KeyEvent.VK_PAGE_UP ||
                e == KeyEvent.VK_END ||
                e == KeyEvent.VK_PAUSE ||
                e == KeyEvent.VK_HOME ||
                e == KeyEvent.VK_NUM_LOCK ||
                e == KeyEvent.VK_CONTROL
        );
    }

    void deleteSelectedText(){
        setCaret = true;
        for (int y = ob.textPanel.lines.size() - 1; y >= 0; y--) {
            if (!setCaret && ob.textPanel.getCaretX() == 0) ob.textPanel.deleteChar();
            for (int x = ob.textPanel.lines.get(y).chars.size() - 1; x >= 0; x--) {
                if (ob.textPanel.lines.get(y).chars.get(x).getIsSelect()) {
                    if (setCaret) {
                        ob.textPanel.setCaretX(ob.textPanel.lines.get(y).chars.get(x).getNomberLetter());
                        ob.textPanel.setCaretY(ob.textPanel.lines.get(y).chars.get(x).getNomberString());
                        setCaret = false;
                    }
                    ob.textPanel.deleteChar();
                }
            }
        }
    }

    public void copy()
    {
        String string = "";
        for (Line line: ob.textPanel.lines) {
            for (Char ch : line.chars) {
                if (ch.getIsSelect()){
                    string += ch.getStringCh();
                }
            }
            if (line.chars.get(line.chars.size()-1).getIsSelect()) string += "\n";
        }
        StringSelection data = new StringSelection(string);
        try{
            Clipboard c = Toolkit.getDefaultToolkit().getSystemClipboard();
            c.setContents(data,null);
        }catch(Exception e)
        {
            JOptionPane.showMessageDialog
                    (null,"Невозможно скопировать", "Ошибка", JOptionPane.ERROR_MESSAGE|JOptionPane.OK_OPTION);
        }
    }

    public void paste()
    {
        try {
            Clipboard c = Toolkit.getDefaultToolkit().getSystemClipboard();
            String s = (String) c.getData(DataFlavor.stringFlavor);
            for (int i = 0; i < s.length(); i++){
                if (s.charAt(i) == '\n') {
                    ob.textPanel.newLine();
                } else {
                    ob.textPanel.lines.get(ob.textPanel.getCaretY())
                            .add(ob.textPanel.getCaretX(), s.charAt(i));
                }
            }
        }catch(Exception e)
        {
            JOptionPane.showMessageDialog
                    (null,"Невозможно вставить текст", "Ошибка", JOptionPane.ERROR_MESSAGE|JOptionPane.OK_OPTION);
        }
    }

    public void cut()
    {
        String string = "";
        for (Line line: ob.textPanel.lines) {
            for (Char ch : line.chars) {
                if (ch.getIsSelect()){
                    string += ch.getStringCh();
                }
            }
            if (line.chars.get(line.chars.size()-1).getIsSelect()) string += "\n";
        }
        StringSelection data = new StringSelection(string);
        try {
            Clipboard c = Toolkit.getDefaultToolkit().getSystemClipboard();
            c.setContents(data,null);
        }catch(Exception e)
        {
            JOptionPane.showMessageDialog
                    (null,"Невозможно вырезать текст", "Ошибка", JOptionPane.ERROR_MESSAGE|JOptionPane.OK_OPTION);
        }
    }

}
