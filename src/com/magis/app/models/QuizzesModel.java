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

    private ArrayList<ChapterModel> chapters;

    public ChapterModel getChapters(int chapterID) {
        return chapters.get(chapterID);
    }

    public QuizzesModel() throws ParserConfigurationException, IOException, SAXException {
        this.chapters = new ArrayList<>();

        File file = new File("src/com/magis/app/resources/quizzes.xml");
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        this.document = dBuilder.parse(file);
        this.document.getDocumentElement().normalize();

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
        private ArrayList<QuestionsModel> questions;

        public ArrayList<QuestionsModel> getQuestions() {
            return questions;
        }

        public int getNumQuestions() {
            return questions.size();
        }

        public ChapterModel(Node chapter) throws ParserConfigurationException {
            this.chapter = chapter;
            this.questions = new ArrayList<>();

            Element chapterElement = (Element) chapter;
            NodeList questions = chapterElement.getElementsByTagName("question");
            for (int i = 0; i < questions.getLength(); i++) {
                Node question = questions.item(i);
                QuestionsModel questionsModel = new QuestionsModel(question);
                this.questions.add(questionsModel);
            }


        }

        public class QuestionsModel {

            Node question;
            String statement;
            String correctAnswer;
            ArrayList<String> incorrectAnswers;

            public String getStatement() {
                return statement;
            }

            public String getCorrectAnswer() {
                return correctAnswer;
            }

            public ArrayList<String> getIncorrectAnswers() {
                return incorrectAnswers;
            }

            public QuestionsModel(Node question) {
                this.question = question;

                Element questionElement = (Element) question;
                statement = questionElement.getElementsByTagName("statement").item(0).getNodeValue();
                NodeList answers = questionElement.getElementsByTagName("answers");
                for (int i = 0; i < answers.getLength(); i++) {
                    Element answer = (Element) answers.item(i);
                    if (answer.hasAttribute("id")) {
                        if (answer.getAttribute("id").equals("correct")) {
                            this.correctAnswer = answer.getNodeValue();
                        } else {
                            System.err.println("FAILED to add \"" + answer.getNodeValue() + "\" to list of answer choices. Unknown answer ID with question \"" + statement + "\"");
                        }
                    } else {
                        incorrectAnswers.add(answer.getNodeName());
                    }
                }

            }
        }
    }
}
