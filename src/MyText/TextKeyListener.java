package MyText;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * Created by andrey on 21/02/16.
 */
public class TextKeyListener implements KeyListener {

    public void keyPressed(KeyEvent keyEvent) {
        if (KeyEvent.VK_ENTER == keyEvent.getKeyCode()) {
            TextPanel.newLine();
        } else if (KeyEvent.VK_BACK_SPACE == keyEvent.getKeyCode()) {
            TextPanel.deleteChar();
        } else if (KeyEvent.VK_LEFT == keyEvent.getKeyCode()) {
            TextPanel.decrementCaretX();
        } else if (KeyEvent.VK_RIGHT == keyEvent.getKeyCode()) {
            TextPanel.incrementCaretX();
        } else if (KeyEvent.VK_UP == keyEvent.getKeyCode()) {
            TextPanel.decrementCaretY();
        } else if (KeyEvent.VK_DOWN == keyEvent.getKeyCode()) {
            TextPanel.incrementCaretY();
        } else  {
            TextPanel.lines.get(TextPanel.getCaretY()).add(TextPanel.getCaretX(), keyEvent.getKeyChar());
        }
        MainWindow.scrollPanel.repaint();
    }

    public void keyReleased(KeyEvent keyEvent) {
    }

    public void keyTyped(KeyEvent keyEvent) {
    }

}
