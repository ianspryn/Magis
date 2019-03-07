package com.magis.app.data;

import java.io.File;
import java.util.ArrayList;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;


public class ReadXML {
    public String GetName() {
        //get string of URLs from XML
        String name = null;
        try {
            File inputFile = new File("student.txt");
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(inputFile);
            doc.getDocumentElement().normalize();
            NodeList nList = doc.getElementsByTagName("student");
            Node nNode = nList.item(0);

            if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                Element eElement = (Element) nNode;
                name = eElement
                        .getElementsByTagName("firstname")
                        .item(0)
                        .getTextContent()
                        + " " + eElement
                        .getElementsByTagName("lastname")
                        .item(0)
                        .getTextContent();
            }


            return name;
        } catch (Exception e) {
            e.printStackTrace();
            return name;
        }
    }

    public ArrayList GetChapterImages() {
        //get string of URLs from XML
        ArrayList<String> images = new ArrayList<String>();
        try {
            File inputFile = new File("chapters.txt");
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(inputFile);
            doc.getDocumentElement().normalize();
            NodeList nList = doc.getElementsByTagName("chapter");
            for (int temp = 0; temp < nList.getLength(); temp++) {
                Node nNode = nList.item(temp);
                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element eElement = (Element) nNode;
                    images.add(eElement
                            .getElementsByTagName("image")
                            .item(0)
                            .getTextContent());
                }
            }

            return images;
        } catch (Exception e) {
            e.printStackTrace();
            return images;
        }
    }

    public ArrayList GetChapterDescription() {
        ArrayList<String> description = new ArrayList<String>();

        //get string of descriptions from XML
        try {
            File inputFile = new File("chapters.txt");
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(inputFile);
            doc.getDocumentElement().normalize();
            NodeList nList = doc.getElementsByTagName("chapter");
            for (int temp = 0; temp < nList.getLength(); temp++) {
                Node nNode = nList.item(temp);
                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element eElement = (Element) nNode;
                    description.add(eElement
                            .getElementsByTagName("description")
                            .item(0)
                            .getTextContent());
                }
            }

            return description;
        } catch (Exception e) {
            e.printStackTrace();
            return description;
        }

    }

    public ArrayList GetChapterText() {
        ArrayList<String> text = new ArrayList<String>();

        //get string of descriptions from XML
        try {
            File inputFile = new File("chapters.txt");
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(inputFile);
            doc.getDocumentElement().normalize();
            NodeList nList = doc.getElementsByTagName("chapter");
            for (int temp = 0; temp < nList.getLength(); temp++) {
                Node nNode = nList.item(temp);
                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element eElement = (Element) nNode;
                    text.add(eElement
                            .getElementsByTagName("text")
                            .item(0)
                            .getTextContent());
                }
            }

            return text;
        } catch (Exception e) {
            e.printStackTrace();
            return text;
        }


    }

    public ArrayList GetChapterProgress() {
        ArrayList<Double> progress = new ArrayList<Double>();

        //get numbers of progress from XML
        try {
            File inputFile = new File("student.txt");
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(inputFile);
            doc.getDocumentElement().normalize();
            NodeList nList = doc.getElementsByTagName("chapter");
            for (int temp = 0; temp < nList.getLength(); temp++) {
                Node nNode = nList.item(temp);
                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element eElement = (Element) nNode;
                    progress.add(Double.parseDouble(eElement
                            .getElementsByTagName("chapter")
                            .item(0)
                            .getTextContent()));
                }
            }

            return progress;
        } catch (Exception e) {
            e.printStackTrace();
            return progress;
        }

    }

    public ArrayList GetQuizProgress() {
        ArrayList<Double> progress = new ArrayList<Double>();

        //get numbers of progress from XML
        try {
            File inputFile = new File("student.txt");
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(inputFile);
            doc.getDocumentElement().normalize();
            NodeList nList = doc.getElementsByTagName("quiz");
            for (int temp = 0; temp < nList.getLength(); temp++) {
                Node nNode = nList.item(temp);
                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element eElement = (Element) nNode;
                    progress.add(Double.parseDouble(eElement
                            .getElementsByTagName("quiz")
                            .item(0)
                            .getTextContent()));
                }
            }

            return progress;
        } catch (Exception e) {
            e.printStackTrace();
            return progress;
        }

    }
}
//anything else that might be relevant for the homepage of each chapter
