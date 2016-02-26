package mytext;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * Created by andrey on 21/02/16.
 */
public class TextKeyListener implements KeyListener {

    private MainWindow mainWindow;
    private TextPanel textPanel;
    private JFrame frame;
    private JScrollPane scrollPanel;
    private boolean setCaret = true;
    private String availableKey = "АаБбВвГгДдЕеЁёЖжЗзИиЙйКкЛлМмНнОоПпРрСсТтУуФфХхЦцЧчШшЩщЪъЫыЬьЭэЮюЯя";

    public TextKeyListener(MainWindow mainWindow){
        this.mainWindow = mainWindow;
        textPanel = mainWindow.textPanel;
        frame = mainWindow.frame;
        scrollPanel = mainWindow.scrollPanel;
    }

    public void keyPressed(KeyEvent keyEvent) {
        if (keyEvent.isControlDown() && keyEvent.getKeyCode() == KeyEvent.VK_C) {
            textPanel.copy();
        } else {
            setCaret = true;
            if (keyEvent.isControlDown() && keyEvent.getKeyCode() == KeyEvent.VK_X) {
                textPanel.cut();
                deleteSelectedText();
            } else if (keyEvent.isControlDown() && keyEvent.getKeyCode() == KeyEvent.VK_V) {
                deleteSelectedText();
                textPanel.paste();
            } else if (KeyEvent.VK_ENTER == keyEvent.getKeyCode()) {
                deleteSelectedText();
                textPanel.newLine();
            } else if (KeyEvent.VK_BACK_SPACE == keyEvent.getKeyCode()) {
                deleteSelectedText();
                if (setCaret) textPanel.deleteChar();
            } else if (KeyEvent.VK_DELETE == keyEvent.getKeyCode()) {
                deleteSelectedText();
                if (setCaret) textPanel.deleteNextChar();
            } else if (KeyEvent.VK_LEFT == keyEvent.getKeyCode()) {
                textPanel.decrementCaretX();
            } else if (KeyEvent.VK_RIGHT == keyEvent.getKeyCode()) {
                textPanel.incrementCaretX();
            } else if (KeyEvent.VK_UP == keyEvent.getKeyCode()) {
                textPanel.decrementCaretY();
            } else if (KeyEvent.VK_DOWN == keyEvent.getKeyCode()) {
                textPanel.incrementCaretY();
            } else if (!systemKey(keyEvent.getKeyCode())) {
                deleteSelectedText();
                textPanel.lines.get(textPanel.getCaretY())
                        .add(textPanel.getCaretX(), keyEvent.getKeyChar());
                textPanel.incrementCaretX();
            }
            followCaret();
            mainWindow.updateWindow();
        }
    }

    void followCaret(){
        int x = 0;
        if (textPanel.getCaretCordinateX() > frame.getWidth()){
            x = textPanel.getCaretCordinateX();
        }
        int y = textPanel.getCaretCordinateY() -
                textPanel.lines.get(textPanel.getCaretY()).getMaxHight();
        JViewport scrollP = scrollPanel.getViewport();
        scrollP.setViewPosition(new Point(x, y));
        scrollPanel.setViewport(scrollP);
    }

    void deleteSelectedText(){
        setCaret = true;
        for (int y = textPanel.lines.size() - 1; y >= 0; y--) {
            if (!setCaret && textPanel.getCaretX() == 0) textPanel.deleteChar();
            for (int x = textPanel.lines.get(y).chars.size() - 1; x >= 0; x--) {
                if (textPanel.lines.get(y).chars.get(x).getIsSelect()) {
                    if (setCaret) {
                        textPanel.setCaretX(x + 1);
                        textPanel.setCaretY(y);
                        setCaret = false;
                    }
                    textPanel.deleteChar();
                }
            }
        }
    }

    public void keyReleased(KeyEvent keyEvent) {
    }

    public void keyTyped(KeyEvent keyEvent) {
    }

    boolean systemKey(int e) {
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
                e == KeyEvent.VK_CONTROL);
    }

}
