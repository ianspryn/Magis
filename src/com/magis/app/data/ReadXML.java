package com.magis.app.data;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;


public class ReadXML {

    private String studentID;

    public ReadXML(String studentID) {
        this.studentID = studentID;
    }

    /*
    get string of URLs from XML
     */
    public String getName() {
        String name = null;
        try {
            Document doc = getDocument("src/com/magis/app/data/student.xml");
            doc.getDocumentElement().normalize();
            NodeList studentList = doc.getElementsByTagName("student");
            Node currentStudent = getCurrentStudent(studentList);

            if (currentStudent.getNodeType() == Node.ELEMENT_NODE) {
                Element eElement = (Element) currentStudent;
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

    private ArrayList<String> getChapterContent(String elementTagName) {
        ArrayList<String> content = new ArrayList<>();
        try {
            Document doc = getDocument("src/com/magis/app/data/chapters.xml");
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
    public ArrayList getChapterImages() {
        return getChapterContent("image");
    }

    /*
    get string of descriptions from XML
     */
    public ArrayList getChapterDescriptions() {
        return getChapterContent("description");
    }

    /*
    get individual score progress
     */
    public Double getChapterProgress(int index) {
        return getAllChaptersProgress().get(index);
    }

    /*
    get numbers of progress from XML
     */
    public ArrayList<Double> getAllChaptersProgress() {
        ArrayList<Double> progress = new ArrayList<>();

        try {
            Document doc = getDocument("src/com/magis/app/data/student.xml");
            doc.getDocumentElement().normalize();
            NodeList studentList = doc.getElementsByTagName("student");
            Node currentStudent = getCurrentStudent(studentList);

            Element currentStudentElement = (Element) currentStudent;
            Element quizzesElement = (Element) currentStudentElement.getElementsByTagName("chapters").item(0);
            NodeList quizList = quizzesElement.getElementsByTagName("chapter");
            for (int temp = 0; temp < quizList.getLength(); temp++) {
                Node nNode = quizList.item(temp);
                progress.add(Double.parseDouble(nNode.getTextContent()));
            }
            return progress;
        } catch (Exception e) {
            e.printStackTrace();
            return progress;
        }

    }

    /*
    get individual score progress
     */
    public Double getQuizScore(int index) {
       return getAllQuizScores().get(index);
    }

    /*
    get numbers of progress from XML
     */
    public ArrayList<Double> getAllQuizScores() {
        ArrayList<Double> progress = new ArrayList<>();

        try {
            Document doc = getDocument("src/com/magis/app/data/student.xml");
            doc.getDocumentElement().normalize();
            NodeList studentList = doc.getElementsByTagName("student");
            Node currentStudent = getCurrentStudent(studentList);

            Element currentStudentElement = (Element) currentStudent;
            Element quizzesElement = (Element) currentStudentElement.getElementsByTagName("quizzes").item(0);
            NodeList quizList = quizzesElement.getElementsByTagName("quiz");
            for (int temp = 0; temp < quizList.getLength(); temp++) {
                Node nNode = quizList.item(temp);
                progress.add(Double.parseDouble(nNode.getTextContent()));
            }

            return progress;
        } catch (FileNotFoundException e) {
            return progress;
        }catch (Exception e) {
            e.printStackTrace();
            return progress;
        }
    }

    /*
    get greatest student ID
    used for determining next student ID when adding a new student
     */
    public String getLastStudentID() {
        try {
            Document doc = getDocument("src/com/magis/app/data/student.xml");
            doc.getDocumentElement().normalize();
            NodeList studentList = doc.getElementsByTagName("student");
            Node node = studentList.item(studentList.getLength() - 1);
            Element e = (Element)node;
            return e.getAttribute("id");
        } catch (FileNotFoundException e) {
            //File doesn't exist? Then it's the first student
            return "1";
        }
        catch (Exception e) {
            e.printStackTrace();
            return "1";
        }
    }

    private Document getDocument(String filePath) throws ParserConfigurationException, IOException, SAXException {
        File inputFile = new File(filePath);
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        return dBuilder.parse(inputFile);
    }

    private Node getCurrentStudent(NodeList studentList) {
        Node currentStudent = null;
        for (int i = 0; i < studentList.getLength(); i++) {
            currentStudent = studentList.item(i);
            if (currentStudent.getAttributes().getNamedItem("id").getNodeValue().equals(this.studentID)) {
                break;
            }
        }
        return currentStudent;
    }
}