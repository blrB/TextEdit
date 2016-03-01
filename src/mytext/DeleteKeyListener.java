package mytext;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * Created by andrey on 27/02/16.
 */
public class DeleteKeyListener implements KeyListener {

    private TextPanel textPanel;

    public DeleteKeyListener(MainWindow mainWindow){
        textPanel = mainWindow.getTextPanel();
    }

    public void keyPressed(KeyEvent keyEvent) {
        if (KeyEvent.VK_BACK_SPACE == keyEvent.getKeyCode()) {
            textPanel.backSpaceKey();
        } else if (KeyEvent.VK_DELETE == keyEvent.getKeyCode()) {
            textPanel.deleteKey();
        }
    }

    public void keyTyped(KeyEvent keyEvent) {
    }

    public void keyReleased(KeyEvent keyEvent) {
    }
}
