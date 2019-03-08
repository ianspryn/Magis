package com.magis.app.data;

import java.io.File;
import javax.print.Doc;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.jcp.xml.dsig.internal.dom.DOMCanonicalizationMethod;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.nio.file.*;

public class CreateStudentXML {

    public static void generate(String studentID, String firstName, String lastName) {

        if (Files.exists(Paths.get("students.xml"))) {
            appendFile(studentID, firstName, lastName);
        } else {
            createNewFile(studentID, firstName, lastName);
        }

//        switch (studentID) {
//            case "1":
//                createNewFile(studentID, firstName, lastName);
//                break;
//            default:
//                appendFile(studentID, firstName,lastName);
//                break;
//        }
    }

    private static void createNewFile(String studentID, String firstName, String lastName) {
        try {
            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();

            //root element
            Document doc = docBuilder.newDocument();
            Element rootElement = doc.createElement("class");


            //student attribute
            Element student = doc.createElement("student");
            student.setAttribute("id", studentID);
            rootElement.appendChild(student);

            //first name elements
            Element firstNameElement = doc.createElement("firstname");
            firstNameElement.appendChild(doc.createTextNode(firstName));
            student.appendChild(firstNameElement);

            //last name elements
            Element lastNameElement = doc.createElement("lastname");
            lastNameElement.appendChild(doc.createTextNode(lastName));
            student.appendChild(lastNameElement);

            //quizzes attribute
            Element quizzes = doc.createElement("quizzes");
            student.appendChild(quizzes);

            //each quiz
            for (int i = 0; i < 10; i++) {
                Element quiz = doc.createElement("quiz");
                quiz.appendChild(doc.createTextNode("0"));
                quizzes.appendChild(quiz);
            }

            //exams attribute
            Element exams = doc.createElement("exams");
            student.appendChild(exams);

            //each exam
            for (int i = 0; i < 3; i++) {
                Element exam = doc.createElement("exam");
                exam.appendChild(doc.createTextNode("0"));
                exams.appendChild(exam);
            }

            //write the content into XML file
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(new File("student.xml"));

            transformer.transform(source, result);

        } catch (ParserConfigurationException pce) {
            pce.printStackTrace();
        } catch (TransformerConfigurationException e) {
            e.printStackTrace();
        } catch (TransformerException e) {
            e.printStackTrace();
        }
    }

    private static void appendFile(String studentID, String firstName, String lastName) {
        File XMLFile = new File("student.xml");
        try {


            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
            Document doc = docBuilder.parse(XMLFile);
            Element rootElement = doc.getDocumentElement();

            //student attribute
            Element student = doc.createElement("student");
            student.setAttribute("id", studentID);
            rootElement.appendChild(student);

            //first name elements
            Element firstNameElement = doc.createElement("firstname");
            firstNameElement.appendChild(doc.createTextNode(firstName));
            student.appendChild(firstNameElement);

            //last name elements
            Element lastNameElement = doc.createElement("lastname");
            lastNameElement.appendChild(doc.createTextNode(lastName));
            student.appendChild(lastNameElement);

            //quizzes attribute
            Element quizzes = doc.createElement("quizzes");
            student.appendChild(quizzes);

            //each quiz
            for (int i = 0; i < 10; i++) {
                Element quiz = doc.createElement("quiz");
                quiz.appendChild(doc.createTextNode("0"));
                quizzes.appendChild(quiz);
            }

            //exams attribute
            Element exams = doc.createElement("exams");
            student.appendChild(exams);


            DOMSource source = new DOMSource(doc);

            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            StreamResult result = new StreamResult("student.xml");
            transformer.transform(source, result);

        } catch (ParserConfigurationException pce) {
        pce.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TransformerConfigurationException e) {
            e.printStackTrace();
        } catch (TransformerException e) {
            e.printStackTrace();
        }
    }
}
