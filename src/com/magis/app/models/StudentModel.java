package com.magis.app.models;

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

public class StudentModel {
    int studentID;
    private ArrayList<Student> students;
    private Document document;

    public StudentModel(int studentID) throws ParserConfigurationException, IOException, SAXException {
        this.studentID = studentID;
        this.students = new ArrayList<>();

        File file = new File("src/com/magis/app/resources/studentModels.xml");
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        this.document = dBuilder.parse(file);
        this.document.getDocumentElement().normalize();

        NodeList students = this.document.getElementsByTagName("student");
        for (int i = 0; i < students.getLength(); i++) {
            Node student = students.item(i);
            Element studentElement = (Element) student;
            String username = studentElement.getAttribute("username");
           Student s = new Student(student, username);
            this.students.add(s);
        }
    }

    public class Student {

        Node student;
        String username;

        public Student(Node student, String username) {
            this.student = student;
            this.username = username;
        }
    }
}
