package mytext;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by andrey on 21/02/16.
 */
public class CaretTimer {

    private Caret caret;

    public CaretTimer(TextPanel textPanel) {
        caret = textPanel.getCaret();
        final Timer time = new Timer();
        time.schedule(new TimerTask() {
            public void run() {
                caret.drawCaret();
            }
        }, 500, 1000);
    }

}
