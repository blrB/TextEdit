package MyText;

import java.awt.*;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by andrey on 21/02/16.
 */
public class TextCaretTimer {

    public TextCaretTimer () {
        final Timer time = new Timer();

        time.schedule(new TimerTask() {
            public void run() {
                int caretCordinateX = TextPanel.getCaretCordinateX();
                int caretCordinateY = TextPanel.getCaretCordinateY();
                Graphics2D graphics2d = (Graphics2D) MainWindow.scrollPanel.getGraphics();
                graphics2d.drawString("_",caretCordinateX,caretCordinateY);
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                caretCordinateX = TextPanel.getCaretCordinateX();
                caretCordinateY = TextPanel.getCaretCordinateY();
                graphics2d.setColor(MainWindow.scrollPanel.getBackground());
                graphics2d.drawString("_",caretCordinateX,caretCordinateY);
            }
        }, 500, 1000);
    }
}
