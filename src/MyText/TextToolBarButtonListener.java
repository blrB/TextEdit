package MyText;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.ArrayList;
import org.w3c.dom.*;

/**
 * Created by andrey on 19/02/16.
 */
public class TextToolBarButtonListener implements ActionListener {

    private MainWindow ob;

    public TextToolBarButtonListener(MainWindow ob){
        this.ob = ob;
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand() == "BUTTON_OPEN") {
            JFileChooser fc = new JFileChooser();
            fc.setFileFilter(new FileNameExtensionFilter( "Text files", "txt", "scs", "cpp", "mytext"));
            if(fc.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
                if ((getExtension(fc.getSelectedFile().getName()).equals("txt")) ||
                    (getExtension(fc.getSelectedFile().getName()).equals("scs")) ||
                    (getExtension(fc.getSelectedFile().getName()).equals("cpp"))) {
                    openFile(fc.getSelectedFile().getPath());
                } else{
                    openXMLFile(fc.getSelectedFile().getPath());
                }
            }
        } else if (e.getActionCommand() == "BUTTON_SAVE") {
            try {
                saveFile();
            } catch (Exception eSave) {
                JOptionPane.showMessageDialog
                        (null,"Невозможно открыть файл", "Ошибка", JOptionPane.ERROR_MESSAGE|JOptionPane.OK_OPTION);
            }
        } else if (e.getActionCommand() == "BUTTON_EXIT") {
            System.exit(0);
        } else if (e.getActionCommand() == "BUTTON_FONT_B"){
            boolean bold = false, notBold = false;
            for (Line line: ob.textPanel.lines) {
                for (Char ch : line.chars) {
                    if (ch.getIsSelect()) {
                        if (ch.getFontStyles() == 0 || ch.getFontStyles() == 2) notBold = true;
                        if (ch.getFontStyles() == 1 || ch.getFontStyles() == 3) bold = true;
                    }
                }
            }
            for (Line line: ob.textPanel.lines) {
                for (Char ch : line.chars) {
                    if (ch.getIsSelect()) {
                        if (bold && notBold) ch.setNormalizationBold();
                        else ch.setFontStyles(1);
                    }
                }
            }
        } else if (e.getActionCommand() == "BUTTON_FONT_I"){
            boolean italic = false, notItalic = false;
            for (Line line: ob.textPanel.lines) {
                for (Char ch : line.chars) {
                    if (ch.getIsSelect()){
                        if (ch.getFontStyles() == 0 || ch.getFontStyles() == 1) notItalic = true;
                        if (ch.getFontStyles() == 2 || ch.getFontStyles() == 3) italic = true;
                    }
                }
            }
            for (Line line: ob.textPanel.lines) {
                for (Char ch : line.chars) {
                    if (ch.getIsSelect()){
                        if (italic && notItalic) ch.setNormalizationItalic();
                        else ch.setFontStyles(2);
                    }
                }
            }
        }
        ob.scrollPanel.revalidate();
        ob.scrollPanel.repaint();
        ob.frame.requestFocus();
    }

    public void openFile(String fileName){
        try  {
            BufferedReader reader = new BufferedReader( new FileReader(fileName));
            String line = null;
            ob.textPanel.lines = new ArrayList<Line>();
            while( ( line = reader.readLine() ) != null ) {
                Line newLine = new Line(ob);
                char [] newCharArray = line.toCharArray ();
                for (char ch: newCharArray){
                    newLine.add(ch);
                }
                ob.textPanel.lines.add(newLine);
            }
        }
        catch ( IOException e ) {
            JOptionPane.showMessageDialog
                    (null,"Невозможно открыть файл", "Ошибка", JOptionPane.ERROR_MESSAGE|JOptionPane.OK_OPTION);
        }
    }

    public void openXMLFile(String fileName){
        try {
            final File xmlFile = new File(fileName);
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document doc = db.parse(xmlFile);
            doc.getDocumentElement().normalize();
            NodeList nodeList = doc.getElementsByTagName("line");
            ob.textPanel.lines = new ArrayList<Line>();
            for (int i = 0; i < nodeList.getLength(); i++) {
                Line newLine = new Line(ob);
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
                ob.textPanel.lines.add(newLine);
            }
        }
        catch(Exception eOpen){
            JOptionPane.showMessageDialog
                    (null, "Невозможно открыть файл", "Ошибка", JOptionPane.ERROR_MESSAGE | JOptionPane.OK_OPTION);
        }
    }

    public void saveFile() throws TransformerException, IOException {
        JFileChooser fc = new JFileChooser();
        if (fc.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
            FileWriter fw = new FileWriter(fc.getSelectedFile() + ".mytext");
        }
        DocumentBuilder builder = null;
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        try {
            builder = factory.newDocumentBuilder();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        }
        Document doc = builder.newDocument();
        Element textXML = doc.createElement("text");
        for (Line line: ob.textPanel.lines) {
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

    public static String getExtension(String fileName) {
        String extension = null;
        int i = fileName.lastIndexOf('.');
        if (i > 0 &&  i < fileName.length() - 1) {
            extension = fileName.substring(i+1).toLowerCase();
        }
        return extension;
    }

}

