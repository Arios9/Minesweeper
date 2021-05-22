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
import java.util.ArrayList;

import static mainCode.MinesweeperGame.currentGameLevel;
import static mainCode.MyTimerTask.secondsPassed;
import static mainMenuPackage.MainMenu.gameLevels;


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
        level = currentGameLevel.getLevelText();
    }

    private static void makeProperActions() throws IOException, SAXException {
        if(file.isFile())
            makeProperActionsToExistingFile();
        else
            createNewXMLDocument();
    }

    private static void makeProperActionsToExistingFile() throws IOException, SAXException {
        parseFileAndItsElements(file);
        setTheLevelElement();
        setNewValueToElementIfTimeIsBetter();
    }

    private static void setTheLevelElement() {
        nodeList = root.getElementsByTagName(level);
    }

    private static void setNewValueToElementIfTimeIsBetter() {
        Node node = nodeList.item(0);
        String textContent = node.getTextContent();
        if(textContent.equals("")||seconds<Integer.parseInt(textContent))
            node.setTextContent(String.valueOf(seconds));
    }

    private static void parseFileAndItsElements(File file) throws IOException, SAXException {
        document = documentBuilder.parse(file);
        root = document.getDocumentElement();
    }

    private static void createNewXMLDocument() {
        document = documentBuilder.newDocument();
        root = document.createElement(ROOT_TAG_NAME);
        document.appendChild(root);
        gameLevels.forEach(gameLevel -> {
            String levelText = gameLevel.getLevelText();
            Element element = document.createElement(levelText);
            root.appendChild(element);
        });
    }

    private static void outPutDocumentToFile() throws FileNotFoundException, TransformerException {
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        DOMSource domSource = new DOMSource(document);
        FileOutputStream fileOutputStream = new FileOutputStream(file);
        StreamResult streamResult = new StreamResult(fileOutputStream);
        transformer.transform(domSource, streamResult);
    }

    public static ArrayList<String> getRecords() throws ParserConfigurationException, IOException, SAXException {
        createDocumentBuilder();
        if(file.isFile()){
                parseFileAndItsElements(file);
                NodeList childNodes = root.getChildNodes();
                ArrayList<String> strings = new ArrayList<>();
                for(int i=0; i<childNodes.getLength(); i++)
                    strings.add(childNodes.item(i).getTextContent());
                return strings;
        }
        return null;
    }
}
