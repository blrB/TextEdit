package mytext;

import javax.swing.*;
import java.awt.*;

/**
 * Created by andrey on 27/02/16.
 */
public class Caret {

    private MainWindow mainWindow;
    private TextPanel textPanel;
    private JFrame frame;
    private JScrollPane scrollPanel;
    private int caretX;
    private int caretY;
    private int caretCordinateX;
    private int caretCordinateY;

    public Caret(MainWindow mainWindow) {
        this.mainWindow = mainWindow;
        textPanel = mainWindow.getTextPanel();
        frame = mainWindow.getFrame();
        scrollPanel = mainWindow.getScrollPanel();
        caretX = 0;
        caretY = 0;
    }

    public int getCaretX() {
        return caretX;
    }

    public void setCaretX(int x) {
        caretX = x;
    }

    public int getCaretY() {
        return caretY;
    }

    public void setCaretY(int y) {
        caretY = y;
    }

    public int getCaretCordinateX() {
        return caretCordinateX;
    }

    public void setCaretCordinateX(int x) {
        caretCordinateX = x;
    }

    public int getCaretCordinateY() {
        return caretCordinateY;
    }

    public void setCaretCordinateY(int y) {
        caretCordinateY = y;
    }

    public void incrementCaretX() {
        if (textPanel.getLines().get(getCaretY()).size() == caretX && textPanel.getLines().size() == caretY + 1) {
        } else if (textPanel.getLines().get(getCaretY()).size() > caretX) {
            caretX++;
        } else if (caretY < textPanel.getLines().size() - 1) {
            caretY++;
            setCaretX(0);
        }
    }

    public void incrementCaretY() {
        if (caretY < textPanel.getLines().size() - 1) {
            caretY++;
            if (textPanel.getLines().get(getCaretY()).size() < caretX) {
                setCaretX(textPanel.getLines().get(getCaretY()).size());
            }
        }
    }

    public void decrementCaretX() {
        if (caretX == 0 && caretY == 0) {
        } else if (caretX != 0) {
            caretX--;
        } else {
            decrementCaretY();
            setCaretX(textPanel.getLines().get(getCaretY()).size());
        }
    }

    public void decrementCaretY() {
        if (caretY != 0) {
            caretY--;
            if (textPanel.getLines().get(getCaretY()).size() < caretX) {
                setCaretX(textPanel.getLines().get(getCaretY()).size());
            }
        }
    }

    public void drawCaret() {
        int caretCordinateX = getCaretCordinateX();
        int caretCordinateY = getCaretCordinateY();
        Graphics2D graphics2d = (Graphics2D) textPanel.getGraphics();
        Font f = getPrevFont();
        graphics2d.setFont(f);
        graphics2d.drawString("_", caretCordinateX, caretCordinateY);
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        graphics2d.setColor(textPanel.getBackground());
        graphics2d.drawString("_", caretCordinateX, caretCordinateY);
    }

    public Font getPrevFont() {
        if (getCaretX() == 0 && getCaretY() == 0) {
            return new Font(Font.MONOSPACED, 0, 12);
        } else {
            if (textPanel.getLines().get(getCaretY()).size() != 0
                    && getCaretX() != 0) {
                Char prevCh = textPanel.getLines().get(getCaretY())
                        .getChars().get(getCaretX() - 1);
                return new Font(prevCh.getFontType(), 0, prevCh.getFontSize());
            } else {
                int i = getCaretY() - 1;
                while (i > 0 && textPanel.getLines().get(i).size() == 0) i--;
                if (textPanel.getLines().get(i).size() != 0) {
                    Char prevCh = textPanel.getLines().get(i)
                            .getChars().get(textPanel.getLines().get(i).getChars().size() - 1);
                    return new Font(prevCh.getFontType(), 0, prevCh.getFontSize());
                } else {
                    return new Font(Font.MONOSPACED, 0, 12);
                }
            }
        }
    }

    void followCaret() {
        int x = 0;
        if (textPanel.getCaret().getCaretCordinateX() > frame.getWidth()) {
            x = textPanel.getCaret().getCaretCordinateX();
        }
        int y = textPanel.getCaret().getCaretCordinateY() -
                textPanel.getLines().get(textPanel.getCaret().getCaretY()).getMaxHight();
        JViewport scrollP = scrollPanel.getViewport();
        scrollP.setViewPosition(new Point(x, y));
        scrollPanel.setViewport(scrollP);
    }

    public int returnSelectionCordinateCaretX() {
        if (isFirstChar()) {
            return 5;
        } else {
            return textPanel.getLines().get(getCaretY()).getChars().get(getCaretX() - 1).getX() + 1;
        }
    }

    public int returnSelectionCordinateCaretY() {
        final Line line = textPanel.getLines().get(getCaretY());
        if (isLineEmpty()) {
            return line.getCordinateY();
        } else {
            return line.getChars().get(getCaretX() - 1).getY() - 1;
        }
    }

    private boolean isFirstChar() {
        return getCaretX() == 0 || textPanel.getLines().get(getCaretY()).getChars().size() == 1;
    }

    private boolean isLineEmpty() {
        return getCaretX() == 0 || textPanel.getLines().get(getCaretY()).getChars().size() == 0;
    }

}
