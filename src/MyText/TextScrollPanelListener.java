package MyText;

import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;

/**
 * Created by andrey on 20/02/16.
 */
public class TextScrollPanelListener implements AdjustmentListener {

    private MainWindow ob;

    public TextScrollPanelListener(MainWindow ob){
        this.ob = ob;
    }

    public void adjustmentValueChanged(AdjustmentEvent evt) {
        ob.textPanel.repaint();
    }

}