package com.magis.app.resources;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class ReadChapterXML {

    public static int getNumChapters() {
        try {
            Document doc = getDocument("src/com/magis/app/resources/chapters.xml");
            doc.getDocumentElement().normalize();
            return doc.getElementsByTagName("chapter").getLength();
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    private static ArrayList<String> getChapterContent(String elementTagName) {
        ArrayList<String> content = new ArrayList<>();
        try {
            Document doc = getDocument("src/com/magis/app/resources/chapters.xml");
            doc.getDocumentElement().normalize();
            NodeList chapterList = doc.getElementsByTagName("chapter");
            for (int i = 0; i < chapterList.getLength(); i++) {
                Node chapter = chapterList.item(i);
                Element chapterElement = (Element) chapter;
                String description = chapterElement.getElementsByTagName(elementTagName).item(0).getTextContent();
                content.add(description);
            }
            return content;
        } catch (Exception e) {
            e.printStackTrace();
            return content;
        }
    }

    /*
    get string of URLs from XML
     */
    public static ArrayList<String> getChapterImages() {
        return getChapterContent("image");
    }

    /*
    get string of URLs from XML
     */
    public static ArrayList<String> getChapterTitles() {
        return getChapterContent("title");
    }

    /*
    get string of descriptions from XML
     */
    public static ArrayList<String> getChapterDescriptions() {
        return getChapterContent("description");
    }

    /*
    get number of pages for chapter
     */
    public static int getNumPages(int chapterIndex) {
        try {
            Node chapter = getChapter(chapterIndex);
            Element chapterElement = (Element) chapter;
            Node pages = chapterElement.getElementsByTagName("pages").item(0);
            Element pagesElement = (Element) pages;
            NodeList list = pagesElement.getElementsByTagName("page");
            return list.getLength();
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    private static Node getChapter(int chapterIndex) throws ParserConfigurationException, SAXException, IOException {
        return getChapterList().item(chapterIndex);
    }

    private static NodeList getChapterList() throws IOException, SAXException, ParserConfigurationException {
        Document doc = getDocument("src/com/magis/app/resources/chapters.xml");
        doc.getDocumentElement().normalize();
        return doc.getElementsByTagName("chapter");
    }

    private static Document getDocument(String filePath) throws ParserConfigurationException, IOException, SAXException {
        File inputFile = new File(filePath);
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        return dBuilder.parse(inputFile);
    }

}
