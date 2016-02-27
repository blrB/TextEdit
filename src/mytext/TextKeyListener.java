package mytext;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * Created by andrey on 21/02/16.
 */
public class TextKeyListener implements KeyListener {

    private MainWindow mainWindow;
    private TextPanel textPanel;
    private Caret caret;

    public TextKeyListener(MainWindow mainWindow){
        this.mainWindow = mainWindow;
        textPanel = mainWindow.textPanel;
        caret = textPanel.caret;
    }

    public void keyPressed(KeyEvent keyEvent) {
        if (KeyEvent.VK_ENTER == keyEvent.getKeyCode()) {
            textPanel.enterKey();
        } else if (textPanel.availableKey.indexOf(keyEvent.getKeyChar()) >= 0 ) {
            textPanel.inputCharKey(keyEvent.getKeyChar());
        }
        caret.followCaret();
        mainWindow.updateWindow();
    }

    public void keyReleased(KeyEvent keyEvent) {
    }

    public void keyTyped(KeyEvent keyEvent) {
    }

}
