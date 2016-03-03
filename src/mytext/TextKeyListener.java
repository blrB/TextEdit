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
    private int keyBackSpace = 8;
    private int keyEnter = 10;
    private int keyEsc = 27;
    private int keyDelete = 127;

    public TextKeyListener(MainWindow mainWindow){
        this.mainWindow = mainWindow;
        textPanel = mainWindow.getTextPanel();
        caret = textPanel.getCaret();
    }

    public void keyPressed(KeyEvent keyEvent) {
        if (KeyEvent.VK_ENTER == keyEvent.getKeyCode()) {
            textPanel.enterKey();
        }
        caret.followCaret();
        mainWindow.updateWindow();
    }

    public void keyReleased(KeyEvent keyEvent) {
    }

    public void keyTyped(KeyEvent keyEvent) {
        if (!keyEvent.isControlDown() && !checkSystemKey(keyEvent)) {
            textPanel.inputCharKey(keyEvent.getKeyChar());
        }
    }

    private boolean checkSystemKey(KeyEvent keyEvent){
        return ((int)keyEvent.getKeyChar() == keyEsc ||
                (int)keyEvent.getKeyChar() == keyBackSpace ||
                (int)keyEvent.getKeyChar() == keyDelete ||
                (int)keyEvent.getKeyChar() == keyEnter);
    }
}
