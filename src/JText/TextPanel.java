package JText;

import javax.swing.*;

/**
 * Created by andrey on 16/02/16.
 */
public class TextPanel extends JTextPane {

    public TextPanel(){
        this.setCaret(new TextCaret());
    }

}


