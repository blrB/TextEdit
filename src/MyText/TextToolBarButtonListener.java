package MyText;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by andrey on 19/02/16.
 */
public class TextToolBarButtonListener implements ActionListener {

    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand() == "BUTTON_OPEN") {
            JFileChooser fc = new JFileChooser();
            if(fc.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
                openFile(fc.getSelectedFile().getPath());
            }
        } else if (e.getActionCommand() == "BUTTON_SAVE") {
            saveFile();
        } else if (e.getActionCommand() == "BUTTON_EXIT") {
            System.exit(0);
        }
        MainWindow.frame.requestFocus();
    }

    public void openFile(String fileName){
        try  {
            BufferedReader reader = new BufferedReader( new FileReader(fileName));
            String line = null;
            TextPanel.lines = new ArrayList<Line>();
            while( ( line = reader.readLine() ) != null ) {
                Line newLine = new Line();
                char [] newCharArray = line.toCharArray ();
                for (char ch: newCharArray){
                    newLine.add(ch);
                }
                TextPanel.lines.add(newLine);
            }
            MainWindow.scrollPanel.repaint();
        }
        catch ( IOException e ) {
            JOptionPane.showMessageDialog
                    (null,"Невозможно открыть файл", "Ошибка", JOptionPane.ERROR_MESSAGE|JOptionPane.OK_OPTION);
        }
    }

    public void saveFile(){
        JFileChooser fc = new JFileChooser();
        if (fc.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
            try ( FileWriter fw = new FileWriter(fc.getSelectedFile()) ) {
                //fw.write(JText.MainWindow.textPanel.getText());
            }
            catch ( IOException e ) {
                JOptionPane.showMessageDialog
                        (null,"Невозможно сохранить файл", "Ошибка", JOptionPane.ERROR_MESSAGE|JOptionPane.OK_OPTION);
            }
        }
    }

}

