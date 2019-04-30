package com.magis.app.models;

import com.magis.app.Main;
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
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

public class StudentModel {

    private Document document;
    public File file;
    private String filePath;
    private Student student;
    private LessonModel lessonModel;

    public Student getStudent() {
        return student;
    }

    public StudentModel(LessonModel lessonModel) {
        this.lessonModel = lessonModel;


        /*
        build version
         */
        String fileName = "students.xml";
        File jarFile = null;
        try {
            jarFile = new File(StudentModel.class.getProtectionDomain().getCodeSource().getLocation().toURI().getPath());
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        assert jarFile != null;
        this.filePath = jarFile.getParent() + File.separator + fileName;

        /*
       local version
         */
//        this.filePath = "src/com/magis/app/resources/students.xml";

        File file = new File(this.filePath);

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
                    this.student = new Student(studentNode, studentUsername);
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
     * @param password the hashed password
     * @param salt the salt associated with the password
     * @return 0 if succeeded, or -1 if student username already exists
     */
    public int addStudent(String username, String firstName, String lastName, String password, String salt) {

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

        //password elements
        Element passwordElement = document.createElement("hash");
        passwordElement.appendChild(document.createTextNode(password));
        student.appendChild(passwordElement);

        Element saltElement = document.createElement("salt");
        saltElement.appendChild(document.createTextNode(salt));
        student.appendChild(saltElement);

        //settings elements
        Element settingsElement = document.createElement("settings");
        student.appendChild(settingsElement);
        Element darkmodeElement = document.createElement("darkmode");
        darkmodeElement.appendChild(document.createTextNode("false"));
        settingsElement.appendChild(darkmodeElement);
        Element themeElement = document.createElement("theme");
        themeElement.appendChild(document.createTextNode("pink"));
        settingsElement.appendChild(themeElement);
        Element animationsElement = document.createElement("animations");
        animationsElement.appendChild(document.createTextNode("true"));
        settingsElement.appendChild(animationsElement);

        //recent activity elements
        Element recentElement = document.createElement("recent");
        student.appendChild(recentElement);
        Element chapterIndex = document.createElement("chapter");
        Element pageID = document.createElement("page");
        recentElement.appendChild(chapterIndex);
        recentElement.appendChild(pageID);

        //chapters
        Element chapters = document.createElement("chapters");
        student.appendChild(chapters);

        //each chapter, initialized to 0
        for (int i = 0; i < lessonModel.getNumChapters(); i++) {
            Element chapter = document.createElement("chapter");
            chapters.appendChild(chapter);
            Element progress = document.createElement("progress");
            progress.appendChild(document.createTextNode("0"));
            chapter.appendChild(progress);

            //each page, 0 = not visited, 1 = visited
            for (int j = 0; j < lessonModel.getChapter(i).getNumPages(); j++) {
                Element page = document.createElement("page");
                page.appendChild(document.createTextNode("0"));
                chapter.appendChild(page);
            }

        }

        //quizzes
        Element quizzes = document.createElement("quizzes");
        student.appendChild(quizzes);

        //tests
        Element tests = document.createElement("tests");
        student.appendChild(tests);

        UpdateModel.updateXML(new DOMSource(document), filePath);
        return 0; //success
    }

    public void deleteUser(String username) {

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
        assert student != null;
        student.getParentNode().removeChild(student);
        //write to XML file
        UpdateModel.updateXML(new DOMSource(document), filePath);

        //Delete student from class
        Main.studentModel = new StudentModel(Main.lessonModel);
        //remove the student's custom styling
        Main.scene.getStylesheets().removeAll();
        //default to light, with pink
        Main.scene.getStylesheets().addAll("com/magis/app/css/style.css", "com/magis/app/css/lightmode.css", "com/magis/app/css/pink.css");
        Main.isLoggedIn = false;
    }

    public class Student {
        private Node student;
        private String username;
        private String firstName;
        private String lastName;
        private String fullName;
        private String passwordHash;
        private String salt;
        private boolean darkMode;
        private String theme;
        private boolean useAnimations;
        private int recentChapter;
        private int recentPage;
        private ArrayList<ChapterModel> chapters;
        private ArrayList<Quiz> quizzes;
        private ArrayList<Test> tests;

        private String getUsername() { return username; }

        public String getFirstName() { return firstName; }

        public String getLastName() { return lastName; }

        public String getPasswordHash() { return passwordHash; }

        public String getSalt() { return salt; }

        public String getFullName() { return fullName; }

        public boolean getDarkMode() { return darkMode; }

        public String getTheme() { return theme; }

        public boolean useAnimations() { return useAnimations; }

        public int getRecentChapter() { return recentChapter; }

        public int getRecentPage() { return recentPage; }

        public void setRecentPlace(int chapterIndex, int pageIndex) {
            recentChapter = chapterIndex;
            recentPage = pageIndex;
        }

        public void setDarkMode(boolean value) {
            darkMode = value;
            writeSettings();
        }

        public void setTheme(String color) {
            switch(color) {
                case "pink":
                case "purple":
                case "cyan":
                case "green":
                case "blue-gray":
                    theme = color;
                    break;
                default:
                    System.err.println("Unrecognized theme color of \"" + color + "\"");
            }
            theme = color;
            writeSettings();
        }

        public void setAnimations(boolean value) {
            useAnimations = value;
            writeSettings();
        }

        private void writeSettings() {
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

            Element settingsElement = (Element) studentElement.getElementsByTagName("settings").item(0);
            Node darkmode = settingsElement.getElementsByTagName("darkmode").item(0);
            Node theme  = settingsElement.getElementsByTagName("theme").item(0);
            Node animations = settingsElement.getElementsByTagName("animations").item(0);
            darkmode.setTextContent(Boolean.toString(this.darkMode));
            theme.setTextContent(this.theme);
            animations.setTextContent(Boolean.toString(this.useAnimations));

            //write to XML file
            UpdateModel.updateXML(new DOMSource(document), filePath);
        }

        public ChapterModel getChapter(int chapterIndex) {
            return chapters.get(chapterIndex);
        }

        /**
         * This method will return a Quiz if it already exists. If it doesn't exist, this method will create a new Quiz and return that
         * @param chapterIndex the chapter's index
         * @return a Quiz Class
         */
        public Quiz getQuiz(int chapterIndex) {
            for (Quiz quiz : quizzes) {
                if (quiz.getQuizChapterNumber() == chapterIndex) {
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
            newQuiz.setAttribute("chapter", Integer.toString(chapterIndex));
            quizzesElement.appendChild(newQuiz);
            UpdateModel.updateXML(new DOMSource(document), filePath);
            Quiz q = new Quiz(newQuiz);
            quizzes.add(q);
            return q;
        }

        /**
         * This method will return an Test if it already exists. If it doesn't exist, this method will create a new Test and return that
         * @param chapterIndex the chapter's index
         * @return an Test Class
         */
        public Test getTest(int chapterIndex) {
            for (Test test : tests) {
                if (test.getTestChapterNumber() == chapterIndex) {
                    return test;
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
            Element testsElement = (Element) studentElement.getElementsByTagName("tests").item(0);
            Element newTest = document.createElement("test");
            newTest.setAttribute("chapter", Integer.toString(chapterIndex));
            testsElement.appendChild(newTest);
            UpdateModel.updateXML(new DOMSource(document), filePath);
            Test e = new Test(newTest);
            tests.add(e);
            return e;
        }

        /**
         * Write the progress for each page to the XML file
         */
        public void writePageProgress() {
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

            Element recentElement = (Element) studentElement.getElementsByTagName("recent").item(0);
            Node recentChapter = recentElement.getElementsByTagName("chapter").item(0);
            Node recentPage = recentElement.getElementsByTagName("page").item(0);
            recentChapter.setTextContent(Integer.toString(this.recentChapter));
            recentPage.setTextContent(Integer.toString(this.recentPage));

            Element chaptersElement = (Element) studentElement.getElementsByTagName("chapters").item(0);
            NodeList chapters = chaptersElement.getElementsByTagName("chapter");
            for (int i = 0; i < chapters.getLength(); i++) {
                Node chapter = chapters.item(i);
                Element chapterElement = (Element) chapter;
                Node progress = chapterElement.getElementsByTagName("progress").item(0);
                progress.setTextContent(Integer.toString(getChapter(i).getProgress()));
                NodeList pages = chapterElement.getElementsByTagName("page");
                for (int j = 0; j < pages.getLength(); j++) {

                    Node page = pages.item(j);
                    page.setTextContent(Integer.toString(getChapter(i).getPageVisisted().get(j)));
                }
            }
            //write to XML file
            UpdateModel.updateXML(new DOMSource(document), filePath);
        }

        Student(Node student, String username) {
            this.student = student;
            this.username = username;
            this.recentChapter = -1;
            this.recentPage = -1;
            this.chapters = new ArrayList<>();
            this.quizzes = new ArrayList<>();
            this.tests = new ArrayList<>();

            Element studentElement = (Element) student;
            //set the names
            this.firstName = studentElement.getElementsByTagName("firstname").item(0).getTextContent();
            this.lastName = studentElement.getElementsByTagName("lastname").item(0).getTextContent();
            this.fullName = firstName + " " + lastName;
            this.passwordHash = studentElement.getElementsByTagName("hash").item(0).getTextContent();
            this.salt = studentElement.getElementsByTagName("salt").item(0).getTextContent();

            //apply user-specific settings
            Element settingsElement = (Element) studentElement.getElementsByTagName("settings").item(0);
            Node darkMode = settingsElement.getElementsByTagName("darkmode").item(0);
            Node theme = settingsElement.getElementsByTagName("theme").item(0);
            Node animations = settingsElement.getElementsByTagName("animations").item(0);
            this.darkMode = Boolean.parseBoolean(darkMode.getTextContent());
            this.theme = theme.getTextContent();
            this.useAnimations = Boolean.parseBoolean(animations.getTextContent());

            Element recentElement = (Element) studentElement.getElementsByTagName("recent").item(0);
            Node recentChapter = recentElement.getElementsByTagName("chapter").item(0);
            Node recentPage = recentElement.getElementsByTagName("page").item(0);
            if (recentChapter.getTextContent().length() > 0) {
                this.recentChapter = Integer.parseInt(recentChapter.getTextContent());
            }
            if (recentPage.getTextContent().length() > 0) {
                this.recentPage = Integer.parseInt(recentPage.getTextContent());
            }

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

            //tests
            Element testsElement = (Element) studentElement.getElementsByTagName("tests").item(0);
            if (testsElement.getElementsByTagName("test") != null) {
                NodeList testsList = testsElement.getElementsByTagName("test");
                for (int i = 0; i < testsList.getLength(); i++) {
                    Node testNode = testsList.item(i);
                    Test test = new Test(testNode);
                    tests.add(test);
                }
            }
        }

        public class ChapterModel {
            private Node chapter;
            private int progress;
            private ArrayList<Integer> pageVisisted;

            ChapterModel(Node chapter) {
                this.chapter = chapter;
                pageVisisted = new ArrayList<>();
                Element chapterElement = (Element) chapter;
                this.progress = Integer.parseInt(chapterElement.getElementsByTagName("progress").item(0).getTextContent());
                NodeList pages = chapterElement.getElementsByTagName("page");
                for (int i = 0; i < pages.getLength(); i++) {
                    Node page = pages.item(i);
                    pageVisisted.add(Integer.parseInt(page.getTextContent()));
                }
            }

            public ArrayList<Integer> getPageVisisted() {
                return pageVisisted;
            }

            /**
             * Mark a page as page visited
             * @param pageIndex the index of the page of the current chapter
             */
            public void visitPage(int pageIndex) {
                //if this is our first time visiting the page, update the value to 1 and update the overall progress
                if (pageVisisted.get(pageIndex) == 0) {
                    pageVisisted.set(pageIndex, 1);
                    updateProgress();
                }
            }

            private void updateProgress() {
                int progress = 0;
                for (int page : pageVisisted) {
                    progress += page;
                }
                progress = (int) (100.0 * ((double)progress / (double)pageVisisted.size()));
                this.progress = progress;
            }

            public int getProgress() {
                return progress;
            }
        }

        public class Quiz {
            Node quiz;
            private int quizChapterNumber;
            private ArrayList<Double> scores;
            private double bestScore = -1.0;
            private double worstScore = 1000.0;

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
             * This method returns the best score for a given test. If the test has not been taken, it will return -1.0
             * @return a Double of the best score
             */
            public Double getBestScore() {
                if (scores.size() > 0) {
                    return bestScore;
                }
                return -1.0;
            }

            /**
             * This method returns the worst score for a given test. If the test has not been taken, it will return -1.0
             * @return a Double of the worst score
             */
            public Double getWorstScore() {
                if (scores.size() > 0) {
                    return worstScore;
                }
                return -1.0;
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
                // update best and worst scores
                if(scoreValue > bestScore) {
                    bestScore = scoreValue;
                }
                if(scoreValue < worstScore) {
                    worstScore = scoreValue;
                }
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

        public class Test {
            private int testChapterNumber;
            private ArrayList<Double> scores;
            private double bestScore = -1.0;
            private double worstScore = 1000.0;

            private int getTestChapterNumber() {
                return testChapterNumber;
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
             * This method returns the best score for a given test. If the test has not been taken, it will return -1.0
             * @return a Double of the best score
             */
            public Double getBestScore() {
                if (scores.size() > 0) {
                    return bestScore;
                }
                return -1.0;
            }

            /**
             * This method returns the worst score for a given test. If the test has not been taken, it will return -1.0
             * @return a Double of the worst score
             */
            public Double getWorstScore() {
                if (scores.size() > 0) {
                    return worstScore;
                }
                return -1.0;
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
                // update best and worst scores
                if(scoreValue > bestScore) {
                    bestScore = scoreValue;
                }
                if(scoreValue < worstScore) {
                    worstScore = scoreValue;
                }

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
                Element testsElement = (Element) studentElement.getElementsByTagName("tests").item(0);
                NodeList tests = testsElement.getElementsByTagName("test");
                Node test = null;
                for (int i = 0; i < tests.getLength(); i++) {
                    test = tests.item(i);
                    //find which test to add score to
                    if (Integer.parseInt(test.getAttributes().getNamedItem("chapter").getNodeValue()) == testChapterNumber) {
                        break;
                    }
                }
                Element testElement = (Element) test;
                Element score = document.createElement("score");
                score.appendChild(document.createTextNode(scoreValue.toString()));
                assert testElement != null;
                testElement.appendChild(score);
                //write to XML file
                UpdateModel.updateXML(new DOMSource(document), filePath);
                //add score to the scores ArrayList
                scores.add(scoreValue);
            }

            Test(Node test) {
                Element testElement = (Element) test;
                this.testChapterNumber = Integer.parseInt(testElement.getAttributes().getNamedItem("chapter").getNodeValue());
                this.scores = new ArrayList<>();
                if (testElement.getElementsByTagName("score") != null) {
                    NodeList scores = testElement.getElementsByTagName("score");
                    for (int i = 0; i < scores.getLength(); i++) {
                        this.scores.add(Double.parseDouble(scores.item(i).getTextContent()));
                    }
                }
            }
        }
    }
}
