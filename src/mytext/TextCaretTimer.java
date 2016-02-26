package mytext;

import java.awt.*;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by andrey on 21/02/16.
 */
public class TextCaretTimer {

    private TextPanel ob;

    public TextCaretTimer (TextPanel object) {
        this.ob = object;
        final Timer time = new Timer();

        time.schedule(new TimerTask() {
            public void run() {
                int caretCordinateX = ob.getCaretCordinateX();
                int caretCordinateY = ob.getCaretCordinateY();
                Graphics2D graphics2d = (Graphics2D) ob.getGraphics();
                try {
                    Font f = getPrevFont();
                    graphics2d.setFont(f);
                } catch (Exception e){}
                graphics2d.drawString("_",caretCordinateX,caretCordinateY);
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                caretCordinateX = ob.getCaretCordinateX();
                caretCordinateY = ob.getCaretCordinateY();
                graphics2d.setColor(ob.getBackground());
                graphics2d.drawString("_",caretCordinateX,caretCordinateY);
            }
        }, 500, 1000);
    }

    public Font getPrevFont(){
        if (ob.getCaretX() == 0 && ob.getCaretY() == 0){
            return new Font(Font.MONOSPACED, 0 , 12);
        } else {
            if (ob.lines.get(ob.getCaretY()).size() != 0
                    && ob.getCaretX() != 0) {
                Char prevCh = ob.lines.get(ob.getCaretY())
                        .chars.get(ob.getCaretX() - 1);
                return new Font(prevCh.getFontType(), 0, prevCh.getFontSize());
            } else {
                int i = ob.getCaretY();
                while (ob.lines.get(i - 1).size() == 0 && i > 0) i--;
                Char prevCh = ob.lines.get(i - 1)
                        .chars.get(ob.lines.get(i - 1).chars.size() - 1);
                return new Font(prevCh.getFontType(), 0, prevCh.getFontSize());
            }
        }
    }

}
