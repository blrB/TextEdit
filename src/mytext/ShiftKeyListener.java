package mytext;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * Created by andrey on 27/02/16.
 */
public class ShiftKeyListener implements KeyListener {

    private TextPanel textPanel;

    public ShiftKeyListener(MainWindow mainWindow){
        textPanel = mainWindow.textPanel;
    }

    public void keyPressed(KeyEvent keyEvent) {
        if (keyEvent.isShiftDown() && KeyEvent.VK_LEFT == keyEvent.getKeyCode()) {
            textPanel.inverseSelectionPrev();
        } else if (keyEvent.isShiftDown() && KeyEvent.VK_RIGHT == keyEvent.getKeyCode()) {
            textPanel.inverseSelectionNext();
        } else if (keyEvent.isShiftDown() && KeyEvent.VK_UP == keyEvent.getKeyCode()) {
            textPanel.inverseSelectionUp();
        } else if (keyEvent.isShiftDown() && KeyEvent.VK_DOWN == keyEvent.getKeyCode()) {
            textPanel.inverseSelectionDown();
        }
    }

    public void keyTyped(KeyEvent keyEvent) {
    }

    public void keyReleased(KeyEvent keyEvent) {
    }
}

