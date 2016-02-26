package mytext;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.*;
import java.util.ArrayList;

/**
 * Created by andrey on 26/02/16.
 */
public class FileWork {

    private MainWindow mainWindow;
    private TextPanel textPanel;

    public FileWork(MainWindow mainWindow){
        this.mainWindow = mainWindow;
        textPanel = mainWindow.textPanel;
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
                DocumentBuilder builder = null;
                DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
                builder = factory.newDocumentBuilder();
                Document doc = builder.newDocument();
                Element textXML = doc.createElement("text");
                for (Line line: textPanel.lines) {
                    Element lineXML = doc.createElement("line");
                    for (Char ch : line.chars) {
                        Element chXML = doc.createElement("char");
                        chXML.setAttribute("font", ch.getFontType());
                        chXML.setAttribute("style", Integer.toString(ch.getFontStyles()));
                        chXML.setAttribute("size", Integer.toString(ch.getFontSize()));
                        chXML.appendChild(doc.createTextNode(ch.getStringCh()));
                        lineXML.appendChild(chXML);
                    }
                    textXML.appendChild(lineXML);
                }
                doc.appendChild(textXML);
                Transformer t = TransformerFactory.newInstance().newTransformer();
                t.transform(new DOMSource(doc),
                        new StreamResult(new FileOutputStream(fc.getSelectedFile() + ".mytext")));
            }
        } catch (Exception eSave) {
            JOptionPane.showMessageDialog
                    (null, "Can't save file", "ERROR", JOptionPane.ERROR_MESSAGE|JOptionPane.OK_OPTION);
        }
    }

    public void openTextFile(String fileName){
        try  {
            BufferedReader reader = new BufferedReader( new FileReader(fileName));
            String line = null;
            textPanel.lines = new ArrayList<Line>();
            textPanel.setCaretX(0);
            textPanel.setCaretY(0);
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

    public void openXMLFile(String fileName){
        try {
            //STaX, SAX
            File xmlFile = new File(fileName);
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document doc = db.parse(xmlFile);
            doc.getDocumentElement().normalize();
            NodeList nodeList = doc.getElementsByTagName("line");
            textPanel.lines = new ArrayList<Line>();
            textPanel.setCaretX(0);
            textPanel.setCaretY(0);
            for (int i = 0; i < nodeList.getLength(); i++) {
                Line newLine = new Line(mainWindow);
                Node node = nodeList.item(i);
                if (Node.ELEMENT_NODE == node.getNodeType()) {
                    Element element = (Element) node;
                    NodeList charList = element.getElementsByTagName("char");
                    for (int y = 0; y < charList.getLength(); y++) {
                        Element ch = (Element) charList.item(y);
                        newLine.add(ch.getTextContent(),
                                ch.getAttribute("font"),
                                ch.getAttribute("style"),
                                ch.getAttribute("size"));
                    }
                }
                textPanel.lines.add(newLine);
            }
            mainWindow.updateWindow();
        }
        catch(Exception eOpen){
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
