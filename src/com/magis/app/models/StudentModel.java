package com.magis.app.models;

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
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

public class StudentModel {

    private Document document;
    public File file;
    private String filePath;
    private ArrayList<Student> students;
    private LessonModel lessonModel;

    public Student getStudent(String username) {
        for (Student student : students) {
            if (username.equals(student.getUsername())) {
                return student;
            }
        }
        return null;
    }

    public StudentModel(LessonModel lessonModel) {
        this.students = new ArrayList<>();
        this.filePath = "src/com/magis/app/resources/students.xml";
        this.lessonModel = lessonModel;

        if (!Files.exists(Paths.get(filePath))) {
            createStudentFile();
        }
        this.file = new File(filePath);

        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = null;
        try {
            dBuilder = dbFactory.newDocumentBuilder();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        }
        try {
            assert dBuilder != null;
            this.document = dBuilder.parse(this.file);
        } catch (SAXException | IOException e) {
            e.printStackTrace();
        }
        assert this.document != null;
        this.document.getDocumentElement().normalize();
    }

    public void initializeStudent(String username) {
        if (this.document.getElementsByTagName("student") != null) {
            NodeList students = this.document.getElementsByTagName("student");
            for (int i = 0; i < students.getLength(); i++) {
                Node student = students.item(i);
                if (student.getAttributes().getNamedItem("username").getNodeValue().equals(username)) {
                    Node studentNode = students.item(i);
                    Element studentElement = (Element) studentNode;
                    String studentUsername = studentElement.getAttributes().getNamedItem("username").getNodeValue();
                    Student s = new Student(studentNode, studentUsername);
                    if (!this.students.contains(s)) {
                        this.students.add(s);
                    }
                }
            }
        }
    }

    private void createStudentFile() {
        DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder docBuilder = null;
        try {
            docBuilder = docFactory.newDocumentBuilder();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        }

        //root element
        assert docBuilder != null;
        document = docBuilder.newDocument();
        Element rootElement = document.createElement("students");
        document.appendChild(rootElement);

        //write the content into XML file
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = null;
        try {
            transformer = transformerFactory.newTransformer();
        } catch (TransformerConfigurationException e) {
            e.printStackTrace();
        }
        DOMSource source = new DOMSource(document);
        StreamResult result = new StreamResult(new File(filePath));

        try {
            assert transformer != null;
            transformer.transform(source, result);
        } catch (TransformerException e) {
            e.printStackTrace();
        }
    }

    /**
     * Add a new student to the program. Additionally, this method will automatically udpate the XML file
     * @param username username of the student
     * @param firstName first name of the student
     * @param lastName last name of the student
     * @return 0 if succeeded, or -1 if student username already exists
     */
    public int addStudent(String username, String firstName, String lastName) {

        DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder docBuilder = null;
        try {
            docBuilder = docFactory.newDocumentBuilder();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        }
        try {
            assert docBuilder != null;
            document = docBuilder.parse(filePath);
        } catch (SAXException | IOException e) {
            e.printStackTrace();
        }

        Element rootElement = document.getDocumentElement();

        //check if username already exists
        NodeList students = rootElement.getElementsByTagName("student");
        for (int i = 0; i < students.getLength(); i++) {
            Node student = students.item(i);
            if (student.hasAttributes()) {
                if (student.getAttributes().getNamedItem("username").getNodeValue().equals(username)) {
                    return -1;
                }
            }
        }

        //student attribute
        Element student = document.createElement("student");
        student.setAttribute("username", username);
        rootElement.appendChild(student);

        //first name elements
        Element firstNameElement = document.createElement("firstname");
        firstNameElement.appendChild(document.createTextNode(firstName));
        student.appendChild(firstNameElement);

        //last name elements
        Element lastNameElement = document.createElement("lastname");
        lastNameElement.appendChild(document.createTextNode(lastName));
        student.appendChild(lastNameElement);

        //chapters
        Element chapters = document.createElement("chapters");
        student.appendChild(chapters);

        //each chapter, initialized to 0
        for (int i = 0; i < lessonModel.getNumChapters(); i++) {
            Element chapter = document.createElement("chapter");
            chapter.appendChild(document.createTextNode("0"));
            chapters.appendChild(chapter);
        }

        //quizzes
        Element quizzes = document.createElement("quizzes");
        student.appendChild(quizzes);

        //exams
        Element exams = document.createElement("exams");
        student.appendChild(exams);

        UpdateModel.updateXML(new DOMSource(document), filePath);

        Student s = new Student(student, username);
        this.students.add(s);
        return 0; //success
    }

    public class Student {
        private String username;
        private String firstName;
        private String lastName;
        private String fullName;
        private ArrayList<ChapterModel> chapters;
        private ArrayList<Quiz> quizzes;
        private ArrayList<Exam> exams;

        private String getUsername() {
            return username;
        }

        public String getFirstName() {
            return firstName;
        }

        public String getLastName() {
            return lastName;
        }

        public String getFullName() {
            return fullName;
        }

        public ChapterModel getChapter(int chapterIndex) {
            return chapters.get(chapterIndex);
        }

        /**
         * This method will return a Quiz if it already exists. If it doesn't exist, this method will create a new Quiz and return that
         * @param chapterID the chapter's number (not index)
         * @return a Quiz Class
         */
        public Quiz getQuiz(int chapterID) {
            for (Quiz quiz : quizzes) {
                if (quiz.getQuizChapterNumber() == chapterID) {
                    return quiz;
                }
            }
            //create the test node since it didn't exist
            DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = null;
            try {
                documentBuilder = documentBuilderFactory.newDocumentBuilder();
            } catch (ParserConfigurationException e) {
                e.printStackTrace();
            }
            Document document = null;
            try {
                assert documentBuilder != null;
                document = documentBuilder.parse(filePath);
            } catch (SAXException | IOException e) {
                e.printStackTrace();
            }
            assert document != null;
            Element root = document.getDocumentElement();
            NodeList students = root.getElementsByTagName("student");
            Node student = null;
            for (int i = 0; i < students.getLength(); i++) {
                student = students.item(i);
                if (student.getAttributes().getNamedItem("username").getNodeValue().equals(username)) {
                    break;
                }
            }
            Element studentElement = (Element) student;
            assert studentElement != null;
            Element quizzesElement = (Element) studentElement.getElementsByTagName("quizzes").item(0);
            Element newQuiz = document.createElement("quiz");
            newQuiz.setAttribute("chapter", Integer.toString(chapterID));
            quizzesElement.appendChild(newQuiz);
            UpdateModel.updateXML(new DOMSource(document), filePath);
            Quiz q = new Quiz(newQuiz);
            quizzes.add(q);
            return q;
        }

        /**
         * This method will return an Exam if it already exists. If it doesn't exist, this method will create a new Exam and return that
         * @param chapter the chapter's number (not index)
         * @return an Exam Class
         */
        public Exam getExam(int chapter) {
            for (Exam exam : exams) {
                if (exam.getExamChapterNumber() == chapter) {
                    return exam;
                }
            }
            //create the exam node since it didn't exist
            DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = null;
            try {
                documentBuilder = documentBuilderFactory.newDocumentBuilder();
            } catch (ParserConfigurationException e) {
                e.printStackTrace();
            }
            Document document = null;
            try {
                document = documentBuilder.parse(filePath);
            } catch (SAXException | IOException e) {
                e.printStackTrace();
            }
            assert document != null;
            Element root = document.getDocumentElement();
            NodeList students = root.getElementsByTagName("student");
            Node student = null;
            for (int i = 0; i < students.getLength(); i++) {
                student = students.item(i);
                if (student.getAttributes().getNamedItem("username").getNodeValue().equals(username)) {
                    break;
                }
            }
            Element studentElement = (Element) student;
            assert studentElement != null;
            Element examsElement = (Element) studentElement.getElementsByTagName("exams").item(0);
            Element newExam = document.createElement("exam");
            newExam.setAttribute("chapter", Integer.toString(chapter));
            examsElement.appendChild(newExam);
            UpdateModel.updateXML(new DOMSource(document), filePath);
            Exam e = new Exam(newExam);
            exams.add(e);
            return e;
        }

        Student(Node student, String username) {
            this.username = username;
            this.chapters = new ArrayList<>();
            this.quizzes = new ArrayList<>();
            this.exams = new ArrayList<>();

            Element studentElement = (Element) student;
            //set the names
            this.firstName = studentElement.getElementsByTagName("firstname").item(0).getTextContent();
            this.lastName = studentElement.getElementsByTagName("lastname").item(0).getTextContent();
            this.fullName = firstName + " " + lastName;

            //chapters
            Element chaptersElement = (Element) studentElement.getElementsByTagName("chapters").item(0);
            if (chaptersElement.getElementsByTagName("chapter") != null) {
                NodeList chaptersList = chaptersElement.getElementsByTagName("chapter");
                for (int i = 0; i < chaptersList.getLength(); i++) {
                    Node chapter = chaptersList.item(i);
                    ChapterModel chapterModel = new ChapterModel(chapter);
                    chapters.add(chapterModel);
                }
            }

            //quizzes
            Element quizzesElement = (Element) studentElement.getElementsByTagName("quizzes").item(0);
            if (quizzesElement.getElementsByTagName("quiz") != null) {
                NodeList quizzesList = quizzesElement.getElementsByTagName("quiz");
                for (int i = 0; i < quizzesList.getLength(); i++) {
                    Node quizNode = quizzesList.item(i);
                    Quiz quiz = new Quiz(quizNode);
                    quizzes.add(quiz);
                }
            }

            //exams
            Element examsElement = (Element) studentElement.getElementsByTagName("exams").item(0);
            if (examsElement.getElementsByTagName("exam") != null) {
                NodeList examsList = examsElement.getElementsByTagName("exam");
                for (int i = 0; i < examsList.getLength(); i++) {
                    Node examNode = examsList.item(i);
                    Exam exam = new Exam(examNode);
                    exams.add(exam);
                }
            }
        }

        public class ChapterModel {
            private Node chapter;
            private int progress;

            ChapterModel(Node chapter) {
                this.chapter = chapter;
                this.progress = Integer.parseInt(chapter.getTextContent());
            }

            public void setProgress(int progress) {
                this.progress = progress;
                chapter.setTextContent(Integer.toString(progress));
                UpdateModel.updateXML(new DOMSource(document), filePath);
            }

            public int getProgress() {
                return progress;
            }
        }

        public class Quiz {
            Node quiz;
            private int quizChapterNumber;
            private ArrayList<Double> scores;

            private int getQuizChapterNumber() {
                return quizChapterNumber;
            }

            public ArrayList<Double> getScores() {
                return scores;
            }

            /**
             * This method returns the most recent score for a given test. If the test has not been taken, it will return -1.0
             * @return a Double of the most recent score
             */
            public Double getLastScore() {
                if (scores.size() > 0) {
                    return scores.get(scores.size() - 1);
                }
                return -1.0;
            }

            /**
             * This method calculates the average scores for a given test. If the test as not been taken, it will return 0.0
             * @return a Double of the average score
             */
            public Double getAverageScore() {
                if (scores.size() == 0) {
                    return 0.0;
                }
                Double scoreTotal = 0.0;
                for (Double score : scores) {
                    scoreTotal += score;
                }
                return scoreTotal / scores.size();
            }

            /**
             * Add a new score value to the test
             * @param scoreValue student's score on test
             */
            public void addScore(int scoreValue) {
                addScore(new Double(scoreValue));
            }

            /**
             * Add a new score value to the test
             * @param scoreValue student's score on test
             */
            public void addScore(Double scoreValue) {
                //add score to the XML file
                DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
                DocumentBuilder documentBuilder = null;
                try {
                    documentBuilder = documentBuilderFactory.newDocumentBuilder();
                } catch (ParserConfigurationException e) {
                    e.printStackTrace();
                }
                Document document = null;
                try {
                    assert documentBuilder != null;
                    document = documentBuilder.parse(filePath);
                } catch (SAXException | IOException e) {
                    e.printStackTrace();
                }
                assert document != null;
                Element root = document.getDocumentElement();
                NodeList students = root.getElementsByTagName("student");
                Node student = null;
                for (int i = 0; i < students.getLength(); i++) {
                    student = students.item(i);
                    //find the current student
                    if (student.getAttributes().getNamedItem("username").getNodeValue().equals(username)) {
                        break;
                    }
                }
                Element studentElement = (Element) student;
                assert studentElement != null;
                Element quizzesElement = (Element) studentElement.getElementsByTagName("quizzes").item(0);
                NodeList quizzes = quizzesElement.getElementsByTagName("quiz");
                Node quiz = null;
                for (int i = 0; i < quizzes.getLength(); i++) {
                    quiz = quizzes.item(i);
                    //find which test to add score to
                    if (Integer.parseInt(quiz.getAttributes().getNamedItem("chapter").getNodeValue()) == quizChapterNumber) {
                        break;
                    }
                }
                Element quizElement = (Element) quiz;
                Element score = document.createElement("score");
                score.appendChild(document.createTextNode(scoreValue.toString()));
                assert quizElement != null;
                quizElement.appendChild(score);
                //write to XML file
                UpdateModel.updateXML(new DOMSource(document), filePath);
                //add score to the scores ArrayList
                scores.add(scoreValue);
            }

            Quiz(Node quiz) {
                this.quiz = quiz;

                Element quizElement = (Element) quiz;
                this.quizChapterNumber = Integer.parseInt(quizElement.getAttributes().getNamedItem("chapter").getNodeValue());

                this.scores = new ArrayList<>();
                if (quizElement.getElementsByTagName("score") != null) {
                    NodeList scores = quizElement.getElementsByTagName("score");
                    for (int i = 0; i < scores.getLength(); i++) {
                        this.scores.add(Double.parseDouble(scores.item(i).getTextContent()));
                    }
                }
            }
        }

        class Exam {
            private int examChapterNumber;
            private ArrayList<Double> scores;

            private int getExamChapterNumber() {
                return examChapterNumber;
            }

            public ArrayList<Double> getScores() {
                return scores;
            }

            /**
             * This method returns the most recent score for a given exam. If the exam has not been taken, it will return -1.0
             * @return a Double of the most recent score
             */
            public Double getLastScore() {
                if (scores.size() > 0) {
                    return scores.get(scores.size() - 1);
                }
                return -1.0;
            }


            /**
             * This method calculates the average scores for a given exam. If the exam as not been taken, it will return 0.0
             * @return a Double of the average score
             */
            public Double getAverageScore() {
                if (scores.size() == 0) {
                    return 0.0;
                }
                Double scoreTotal = 0.0;
                for (Double score : scores) {
                    scoreTotal += score;
                }
                return scoreTotal / scores.size();
            }

            /**
             * Add a new score value to the exam
             * @param scoreValue student's score on exam
             */
            public void addScore(int scoreValue) {
                addScore(new Double(scoreValue));
            }

            /**
             * Add a new score value to the exam
             * @param scoreValue student's score on exam
             */
            public void addScore(Double scoreValue) {
                //add score to the XML file
                DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
                DocumentBuilder documentBuilder = null;
                try {
                    documentBuilder = documentBuilderFactory.newDocumentBuilder();
                } catch (ParserConfigurationException e) {
                    e.printStackTrace();
                }
                Document document = null;
                try {
                    assert documentBuilder != null;
                    document = documentBuilder.parse(filePath);
                } catch (SAXException | IOException e) {
                    e.printStackTrace();
                }
                assert document != null;
                Element root = document.getDocumentElement();
                NodeList students = root.getElementsByTagName("student");
                Node student = null;
                for (int i = 0; i < students.getLength(); i++) {
                    student = students.item(i);
                    //find the current student
                    if (student.getAttributes().getNamedItem("username").getNodeValue().equals(username)) {
                        break;
                    }
                }
                Element studentElement = (Element) student;
                assert studentElement != null;
                Element examsElement = (Element) studentElement.getElementsByTagName("exams").item(0);
                NodeList exams = examsElement.getElementsByTagName("exam");
                Node exam = null;
                for (int i = 0; i < exams.getLength(); i++) {
                    exam = exams.item(i);
                    //find which exam to add score to
                    if (Integer.parseInt(exam.getAttributes().getNamedItem("chapter").getNodeValue()) == examChapterNumber) {
                        break;
                    }
                }
                Element examElement = (Element) exam;
                Element score = document.createElement("score");
                score.appendChild(document.createTextNode(scoreValue.toString()));
                assert examElement != null;
                examElement.appendChild(score);
                //write to XML file
                UpdateModel.updateXML(new DOMSource(document), filePath);
                //add score to the scores ArrayList
                scores.add(scoreValue);
            }

            Exam(Node exam) {
                Element examElement = (Element) exam;
                this.examChapterNumber = Integer.parseInt(examElement.getAttributes().getNamedItem("chapter").getNodeValue());
                this.scores = new ArrayList<>();
                if (examElement.getElementsByTagName("score") != null) {
                    NodeList scores = examElement.getElementsByTagName("score");
                    for (int i = 0; i < scores.getLength(); i++) {
                        this.scores.add(Double.parseDouble(scores.item(i).getTextContent()));
                    }
                }
            }
        }
    }
}
