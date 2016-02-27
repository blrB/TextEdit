package mytext;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * Created by andrey on 27/02/16.
 */
public class ControlKeyListener implements KeyListener {

    private TextPanel textPanel;

    public ControlKeyListener(MainWindow mainWindow){
        textPanel = mainWindow.textPanel;
    }

    public void keyPressed(KeyEvent keyEvent) {
        if (keyEvent.isControlDown() && keyEvent.getKeyCode() == KeyEvent.VK_C) {
            textPanel.copy();
        } else if (keyEvent.isControlDown() && keyEvent.getKeyCode() == KeyEvent.VK_X) {
            textPanel.cut();
        } else if (keyEvent.isControlDown() && keyEvent.getKeyCode() == KeyEvent.VK_V) {
            textPanel.paste();
        }
    }

    public void keyTyped(KeyEvent keyEvent) {
    }

    public void keyReleased(KeyEvent keyEvent) {
    }
}
