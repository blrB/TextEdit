package MyText;

import java.awt.*;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by andrey on 21/02/16.
 */
public class TextCaretTimer {

    private MainWindow ob;

    public TextCaretTimer (Object object) {
        this.ob = (MainWindow)object;
        final Timer time = new Timer();

        time.schedule(new TimerTask() {
            public void run() {
                int caretCordinateX = ob.textPanel.getCaretCordinateX();
                int caretCordinateY = ob.textPanel.getCaretCordinateY();
                Graphics2D graphics2d = (Graphics2D) ob.textPanel.getGraphics();
                try {
                    Char ch = ob.textPanel.lines.get(ob.textPanel.getCaretY())
                            .chars.get(ob.textPanel.getCaretX()-1);
                    Font f = new Font(ch.getFontType(), ch.getFontStyles(), ch.getFontSize());
                    graphics2d.setFont(f);
                } catch (Exception e){}
                graphics2d.drawString("_",caretCordinateX,caretCordinateY);
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                caretCordinateX = ob.textPanel.getCaretCordinateX();
                caretCordinateY = ob.textPanel.getCaretCordinateY();
                graphics2d.setColor(ob.scrollPanel.getBackground());
                graphics2d.drawString("_",caretCordinateX,caretCordinateY);
            }
        }, 500, 1000);
    }
}
