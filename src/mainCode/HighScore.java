package mainCode;


import org.w3c.dom.*;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import static mainCode.MinesweeperFrame.timeLabel;
import static mainCode.MinesweeperGame.gameLevel;


public class HighScore {

    private static final String FILE_PATH = "src/files/records.xml";
    private static final String ROOT_TAG_NAME = "record";
    private static String seconds, level;
    private static DocumentBuilderFactory documentBuilderFactory;
    private static DocumentBuilder documentBuilder;
    private static Document document;
    private static Element root;

    public static void checkForHighScore() {
        seconds = timeLabel.getText();
        level = gameLevel.getLevelText();

        documentBuilderFactory = DocumentBuilderFactory.newInstance();
        try {
            documentBuilder = documentBuilderFactory.newDocumentBuilder();

            File file = new File(FILE_PATH);

            if(file.isFile()){
                document = documentBuilder.parse(file);
                root = document.getDocumentElement();

                NodeList nodeList = root.getElementsByTagName(level);
                if(nodeList.getLength() == 0){
                    Element element = document.createElement(level);
                    element.setTextContent(seconds);
                    root.appendChild(element);
                }else{
                    Node node = nodeList.item(0);
                    String textContent = node.getTextContent();
                    // check for better time
                    if (Integer.parseInt(seconds) < Integer.parseInt(textContent)){
                        node.setTextContent(String.valueOf(seconds));
                    }
                }
            }else{
                document = documentBuilder.newDocument();
                root = document.createElement(ROOT_TAG_NAME);
                document.appendChild(root);
                Element element = document.createElement(level);
                element.setTextContent(seconds);
                root.appendChild(element);
            }


            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource domSource = new DOMSource(document);
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            StreamResult streamResult = new StreamResult(fileOutputStream);
            transformer.transform(domSource, streamResult);
        } catch (ParserConfigurationException | SAXException | IOException | TransformerException e) {
            e.printStackTrace();
        }
    }
}
