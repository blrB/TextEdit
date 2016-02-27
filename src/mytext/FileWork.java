package mytext;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.xml.stream.*;
import java.io.*;
import java.util.ArrayList;

/**
 * Created by andrey on 26/02/16.
 */
public class FileWork {

    private MainWindow mainWindow;
    private TextPanel textPanel;
    private Caret caret;

    public FileWork(MainWindow mainWindow){
        this.mainWindow = mainWindow;
        textPanel = mainWindow.textPanel;
        caret = textPanel.caret;
    }

    public void openFile(){
        JFileChooser fc = new JFileChooser();
        fc.setFileFilter(new FileNameExtensionFilter( "Text files", "txt", "scs", "cpp", "mytext"));
        if(fc.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
            if ((getExtension(fc.getSelectedFile().getName()).equals("txt")) ||
                    (getExtension(fc.getSelectedFile().getName()).equals("scs")) ||
                    (getExtension(fc.getSelectedFile().getName()).equals("cpp"))) {
                openTextFile(fc.getSelectedFile().getPath());
            } else{
                openXMLFile(fc.getSelectedFile().getPath());
            }
        }
    }

    public void saveFile(){
        try {
            JFileChooser fc = new JFileChooser();
            if (fc.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
                XMLOutputFactory output = XMLOutputFactory.newInstance();
                XMLStreamWriter writer = output.createXMLStreamWriter
                        (new FileWriter(fc.getSelectedFile() + ".mytext"));
                writer.writeStartDocument("UTF-8", "1.0");
                writer.writeStartElement("text");
                for (Line line : textPanel.lines) {
                    writer.writeStartElement("line");
                    for (Char ch : line.chars) {
                        writer.writeStartElement("char");
                        writer.writeAttribute("font", ch.getFontType());
                        writer.writeAttribute("style", Integer.toString(ch.getFontStyles()));
                        writer.writeAttribute("size", Integer.toString(ch.getFontSize()));
                        writer.writeCharacters(ch.getStringCh());
                        writer.writeEndElement();
                    }
                    writer.writeEndElement();
                }
                writer.writeEndElement();
                writer.writeEndDocument();
                writer.flush();
            }
        } catch (Exception eSave) {
            JOptionPane.showMessageDialog
                    (null, "Can't save file", "ERROR", JOptionPane.ERROR_MESSAGE|JOptionPane.OK_OPTION);
        }
    }

    public void openXMLFile(String fileName){
        try {
            Line newLine = new Line(mainWindow);
            textPanel.lines = new ArrayList<Line>();
            caret.setCaretX(0);
            caret.setCaretY(0);
            XMLStreamReader xmlr = XMLInputFactory.newInstance()
                    .createXMLStreamReader(fileName, new FileInputStream(fileName));
            while (xmlr.hasNext()) {
                xmlr.next();
                if (xmlr.isStartElement()) {
                    if (xmlr.getLocalName().equals("line")){
                        newLine = new Line(mainWindow);
                    }
                    else if (xmlr.getLocalName().equals("char")){
                        String font = xmlr.getAttributeValue(null, "font");
                        String size = xmlr.getAttributeValue(null, "size");
                        String style = xmlr.getAttributeValue(null, "style");
                        xmlr.next();
                        newLine.add(xmlr.getText(), font, style, size);
                    }
                } else if (xmlr.isEndElement()) {
                    if (xmlr.getLocalName().equals("line")){
                        textPanel.lines.add(newLine);
                    }
                }
            }
            mainWindow.updateWindow();
        } catch (Exception e){
            JOptionPane.showMessageDialog
                    (null, "Can't open file", "ERROR", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void openTextFile(String fileName){
        try  {
            BufferedReader reader = new BufferedReader( new FileReader(fileName));
            String line = null;
            textPanel.lines = new ArrayList<Line>();
            caret.setCaretX(0);
            caret.setCaretY(0);
            while( ( line = reader.readLine() ) != null ) {
                Line newLine = new Line(mainWindow);
                char [] newCharArray = line.toCharArray ();
                for (char ch: newCharArray){
                    newLine.add(ch);
                }
                textPanel.lines.add(newLine);
            }
            mainWindow.updateWindow();
        }
        catch ( IOException e ) {
            JOptionPane.showMessageDialog
                    (null, "Can't open file", "ERROR", JOptionPane.ERROR_MESSAGE);
        }
    }

    private static String getExtension(String fileName) {
        String extension = null;
        int i = fileName.lastIndexOf('.');
        if (i > 0 &&  i < fileName.length() - 1) {
            extension = fileName.substring(i+1).toLowerCase();
        }
        return extension;
    }

}
