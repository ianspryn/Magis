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

public class QuizzesModel {

    private Document document;
    private String filePath;
    private ArrayList<ChapterModel> chapters;

    public ChapterModel getChapter(String chapterName) {
        for (ChapterModel chapter : chapters) {
            if (chapterName.equals(chapter.getChapterName())) {
                return chapter;
            }
        }
        return null;
    }

    public boolean hasQuiz(String chapterName) {
        NodeList chapters = document.getElementsByTagName("chapter");
        for (int i = 0; i < chapters.getLength(); i++) {
            Node chapterNode = chapters.item(i);
            if (chapterName.equals(chapterNode.getAttributes().getNamedItem("id").getNodeValue())) {
                return true;
            }
        }
        return false;
    }

    public QuizzesModel() {
        this.chapters = new ArrayList<>();
        this.filePath = "src/com/magis/app/resources/quiz.xml";
        File file = new File(filePath);
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = null;
        try {
            dBuilder = dbFactory.newDocumentBuilder();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        }
        try {
            assert dBuilder != null;
            this.document = dBuilder.parse(file);
        } catch (SAXException | IOException e) {
            e.printStackTrace();
        }
        assert this.document != null;
        this.document.getDocumentElement().normalize();

        NodeList chapters = this.document.getElementsByTagName("chapter");
        for (int i = 0; i < chapters.getLength(); i++) {
            Node chapter = chapters.item(i);
            ChapterModel chapterModel = new ChapterModel(chapter);
            this.chapters.add(chapterModel);
        }
    }

    public void initializeQuiz(String chapterName) {
        NodeList chapters = document.getElementsByTagName("chapter");
        ChapterModel chapterModel = null;
        for (int i = 0; i < chapters.getLength(); i++) {
            Node chapterNode = chapters.item(i);
            if (chapterName.equals(chapterNode.getAttributes().getNamedItem("id").getNodeValue())) {
                chapterModel = new ChapterModel(chapterNode);
            }
        }
        this.chapters.add(chapterModel);
    }

    public class ChapterModel {
        private String chapterName;
        private ArrayList<QuestionsModel> questions;

        public ArrayList<QuestionsModel> getQuestions() {
            return questions;
        }
        public QuestionsModel getQuestion(int index) {
            if (index > questions.size() - 1) {
                return null;
            }
            return questions.get(index);
        }

        public int getNumQuestions() {
            return questions.size();
        }

        String getChapterName() {
            return chapterName;
        }

        ChapterModel(Node chapter) {
            this.chapterName = chapter.getAttributes().getNamedItem("id").getNodeValue();
            this.questions = new ArrayList<>();
            Element chapterElement = (Element) chapter;


            Element questionsElement = (Element) chapterElement.getElementsByTagName("questions").item(0);
            NodeList questions = questionsElement.getElementsByTagName("question");
            for (int i = 0; i < questions.getLength(); i++) {
                Node question = questions.item(i);
                QuestionsModel questionsModel = new QuestionsModel(question);
                this.questions.add(questionsModel);
            }


        }

        public class QuestionsModel {

            private String statement;
            private String correctAnswer;
            private ArrayList<String> incorrectAnswers;

            public String getStatement() {
                return statement;
            }

            public int getNumAnswers() {
                return incorrectAnswers.size() + 1;
            }

            public int getNumIncorrectAnswers() {
                return incorrectAnswers.size();
            }

            public String getCorrectAnswer() {
                return correctAnswer;
            }

            public ArrayList<String> getIncorrectAnswers() {
                return incorrectAnswers;
            }

            QuestionsModel(Node question) {
                this.incorrectAnswers = new ArrayList<>();
                Element questionElement = (Element) question;
                this.statement = questionElement.getElementsByTagName("statement").item(0).getTextContent();
                NodeList answers = questionElement.getElementsByTagName("answer");
                for (int i = 0; i < answers.getLength(); i++) {
                    Element answer = (Element) answers.item(i);
                    if (answer.hasAttribute("id")) {
                        if (answer.getAttributes().getNamedItem("id").getNodeValue().equals("correct")) {
                            this.correctAnswer = answer.getTextContent();
                        } else {
                            System.err.println("FAILED to add \"" + answer.getNodeValue() + "\" to list of answer choices. Unknown answer ID with question \"" + statement + "\"");
                        }
                    } else {
                        incorrectAnswers.add(answer.getTextContent());
                    }
                }

            }
        }
    }
}
