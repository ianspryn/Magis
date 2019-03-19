package com.magis.app.resources;

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


public class ReadStudentXML {

    private String studentID;

    public ReadStudentXML(String studentID) {
        this.studentID = studentID;
    }

    /*
    get string of URLs from XML
     */
    public String getFullName() {
        return getFirstName() + " " + getLastName();
    }

    public String getFirstName() {
        String firstName = null;
        try {
            Document doc = getDocument("src/com/magis/app/resources/student.xml");
            doc.getDocumentElement().normalize();
            NodeList studentList = doc.getElementsByTagName("student");
            Node currentStudent = getCurrentStudent(studentList);

            if (currentStudent.getNodeType() == Node.ELEMENT_NODE) {
                Element eElement = (Element) currentStudent;
                firstName = eElement
                        .getElementsByTagName("firstname")
                        .item(0)
                        .getTextContent();
            }
            return firstName;
        } catch (Exception e) {
            e.printStackTrace();
            return firstName;
        }
    }

    public String getLastName() {
        String firstName = null;
        try {
            Document doc = getDocument("src/com/magis/app/resources/student.xml");
            doc.getDocumentElement().normalize();
            NodeList studentList = doc.getElementsByTagName("student");
            Node currentStudent = getCurrentStudent(studentList);

            if (currentStudent.getNodeType() == Node.ELEMENT_NODE) {
                Element eElement = (Element) currentStudent;
                firstName = eElement
                        .getElementsByTagName("lastname")
                        .item(0)
                        .getTextContent();
            }
            return firstName;
        } catch (Exception e) {
            e.printStackTrace();
            return firstName;
        }
    }

    /*
    get individual score progress
     */
    public int getChapterProgress(int index) {
        return getAllChaptersProgress().get(index);
    }

    /*
    get numbers of progress from XML
     */
    public ArrayList<Integer> getAllChaptersProgress() {
        ArrayList<Integer> progress = new ArrayList<>();

        try {
            Document doc = getDocument("src/com/magis/app/resources/student.xml");
            doc.getDocumentElement().normalize();
            NodeList studentList = doc.getElementsByTagName("student");
            Node currentStudent = getCurrentStudent(studentList);

            Element currentStudentElement = (Element) currentStudent;
            Element quizzesElement = (Element) currentStudentElement.getElementsByTagName("chapters").item(0);
            NodeList quizList = quizzesElement.getElementsByTagName("chapter");
            for (int temp = 0; temp < quizList.getLength(); temp++) {
                Node nNode = quizList.item(temp);
                progress.add(Integer.parseInt(nNode.getTextContent()));
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

        Document doc = getDocument("src/com/magis/app/resources/student.xml");
        doc.getDocumentElement().normalize();
        NodeList studentList = doc.getElementsByTagName("student");
        Node currentStudent = getCurrentStudent(studentList);

        Element currentStudentElement = (Element) currentStudent;
        Element quizzesElement = (Element) currentStudentElement.getElementsByTagName("quiz").item(0);
        NodeList quizList = quizzesElement.getElementsByTagName("quiz");
        for (int temp = 0; temp < quizList.getLength(); temp++) {
            Node nNode = quizList.item(temp);
            progress.add(Double.parseDouble(nNode.getTextContent()));
        }

        return progress;
    }

    /*
    get greatest student ID
    used for determining next student ID when adding a new student
     */
    public String getLastStudentID() {
        Document doc = getDocument("src/com/magis/app/resources/student.xml");
        doc.getDocumentElement().normalize();
        NodeList studentList = doc.getElementsByTagName("student");
        if (studentList.item(studentList.getLength() - 1) != null) {
            Node node = studentList.item(studentList.getLength() - 1);
            Element e = (Element) node;
            return e.getAttribute("id");
        } else {
            //File doesn't exist? Then it's the first student
            return "1";
        }
    }

    private Document getDocument(String filePath) {
        File inputFile = new File(filePath);
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = null;
        try {
            dBuilder = dbFactory.newDocumentBuilder();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        }
        Document document = null;
        try {
            document = dBuilder.parse(inputFile);
        } catch (SAXException | IOException e) {
            e.printStackTrace();
        }
        return document;
    }

    private Node getCurrentStudent(NodeList studentList) {
        Node currentStudent = null;
        for (int i = 0; i < studentList.getLength(); i++) {
            currentStudent = studentList.item(i);
            if (currentStudent.getAttributes().getNamedItem("username").getNodeValue().equals(this.studentID)) {
                break;
            }
        }
        return currentStudent;
    }
}