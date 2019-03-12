package com.magis.app.models;


import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class QuizzesModel {

    private Document document;

    private ArrayList<ChapterModel> chapters;

    public QuizzesModel(ArrayList<ChapterModel> chapterModels) throws ParserConfigurationException, IOException, SAXException {
        this.chapters = new ArrayList<>();
        File file = new File("src/com/magis/app/resources/chapters.xml");
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        this.document = dBuilder.parse(file);
        this.document.getDocumentElement().normalize();
        this.chapters = new ArrayList<>();

        NodeList chapters = this.document.getElementsByTagName("chapter");
        for (int i = 0; i < chapters.getLength(); i++) {
            Node chapter = chapters.item(i);
            ChapterModel chapterModel = new ChapterModel(chapter);
            this.chapters.add(chapterModel);
        }
    }

    public class ChapterModel {
        Node chapter;
        int chapterID;

        public ChapterModel(Node chapter, int chapterID) {
            this.chapter = chapter;
            this.chapterID = chapterID;
        }
    }
}
