package JText;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;


/**
 * Created by andrey on 16/02/16.
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
    }

    public void openFile(String filename){
        try{
            File file = new File(filename);
            MainWindow.textPanel.setPage(file.toURI().toURL());
        }catch (IOException e){
            JOptionPane.showMessageDialog
                    (null,"Невозможно открыть файл", "Ошибка", JOptionPane.ERROR_MESSAGE|JOptionPane.OK_OPTION);
        }
    }

    public void saveFile(){
        JFileChooser fc = new JFileChooser();
        if (fc.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
            try ( FileWriter fw = new FileWriter(fc.getSelectedFile()) ) {
                fw.write(MainWindow.textPanel.getText());
            }
            catch ( IOException e ) {
                JOptionPane.showMessageDialog
                        (null,"Невозможно сохранить файл", "Ошибка", JOptionPane.ERROR_MESSAGE|JOptionPane.OK_OPTION);
            }
        }
    }

}
