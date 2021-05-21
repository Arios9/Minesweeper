package mainCode;


import org.w3c.dom.*;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import static mainCode.MinesweeperGame.gameLevel;
import static mainCode.MyTimerTask.secondsPassed;


public class HighScore {

    private static final String FILE_PATH = "src/files/records.xml";
    private static final String ROOT_TAG_NAME = "record";
    private static String level;
    private static int seconds;
    private static DocumentBuilderFactory documentBuilderFactory;
    private static DocumentBuilder documentBuilder;
    private static Document document;
    private static Element root;
    private static NodeList nodeList;
    private static File file;

    public static void checkForHighScore() {
        try {
            tryingIsTheFirstStepToFailure();
        } catch (ParserConfigurationException | SAXException | IOException | TransformerException e) {
            e.printStackTrace();
        }
    }

    private static void tryingIsTheFirstStepToFailure() throws ParserConfigurationException, IOException, SAXException, TransformerException {
        createDocumentBuilder();
        initializeVariables();
        makeProperActions();
        outPutDocumentToFile();
    }

    private static void createDocumentBuilder() throws ParserConfigurationException {
        documentBuilderFactory = DocumentBuilderFactory.newInstance();
        documentBuilder = documentBuilderFactory.newDocumentBuilder();
        file = new File(FILE_PATH);
    }

    private static void initializeVariables() {
        seconds = secondsPassed;
        level = gameLevel.getLevelText();
    }

    private static void makeProperActions() throws IOException, SAXException {
        if(file.isFile())
            makeProperActionsToExistingFile();
        else
            createDocumentAndNewElement();
    }

    private static void makeProperActionsToExistingFile() throws IOException, SAXException {
        parseFileAndItsElements(file);
        makeProperActionsToTheDocument();
    }

    private static void makeProperActionsToTheDocument() {
        if(ElementExist())
            setNewValueToElementIfTimeIsBetter();
        else
            createNewElementLevelRecord();
    }

    private static boolean ElementExist() {
        return nodeList.getLength() > 0;
    }

    private static void setNewValueToElementIfTimeIsBetter() {
        Node node = nodeList.item(0);
        int currentHighScore = getTextContentFromNodeToInt(node);
        if (seconds < currentHighScore)
            node.setTextContent(String.valueOf(seconds));
    }

    private static int getTextContentFromNodeToInt(Node node){
        String textContent = node.getTextContent();
        return Integer.parseInt(textContent);
    }

    private static void createDocumentAndNewElement() {
        createNewXMLDocument();
        createNewElementLevelRecord();
    }

    private static void parseFileAndItsElements(File file) throws IOException, SAXException {
        document = documentBuilder.parse(file);
        root = document.getDocumentElement();
        nodeList = root.getElementsByTagName(level);
    }

    private static void createNewElementLevelRecord() {
        Element element = document.createElement(level);
        String string = String.valueOf(seconds);
        element.setTextContent(string);
        root.appendChild(element);
    }

    private static void createNewXMLDocument() {
        document = documentBuilder.newDocument();
        root = document.createElement(ROOT_TAG_NAME);
        document.appendChild(root);
    }

    private static void outPutDocumentToFile() throws FileNotFoundException, TransformerException {
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        DOMSource domSource = new DOMSource(document);
        FileOutputStream fileOutputStream = new FileOutputStream(file);
        StreamResult streamResult = new StreamResult(fileOutputStream);
        transformer.transform(domSource, streamResult);
    }
}
