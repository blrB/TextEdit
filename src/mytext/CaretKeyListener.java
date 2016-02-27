package mytext;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * Created by andrey on 27/02/16.
 */
public class CaretKeyListener implements KeyListener {

    private Caret caret;

    public CaretKeyListener(MainWindow mainWindow){
        caret = mainWindow.textPanel.caret;
    }

    public void keyPressed(KeyEvent keyEvent) {
        if (!keyEvent.isShiftDown() && KeyEvent.VK_LEFT == keyEvent.getKeyCode()) {
            caret.decrementCaretX();
        } else if (!keyEvent.isShiftDown() && KeyEvent.VK_RIGHT == keyEvent.getKeyCode()) {
            caret.incrementCaretX();
        } else if (!keyEvent.isShiftDown() && KeyEvent.VK_UP == keyEvent.getKeyCode()) {
            caret.decrementCaretY();
        } else if (!keyEvent.isShiftDown() && KeyEvent.VK_DOWN == keyEvent.getKeyCode()) {
            caret.incrementCaretY();
        }
    }

    public void keyTyped(KeyEvent keyEvent) {
    }

    public void keyReleased(KeyEvent keyEvent) {
    }
}

