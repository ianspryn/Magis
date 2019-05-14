package com.magis.app.page;

import com.magis.app.Main;
import com.magis.app.models.ExamsModel;
import com.magis.app.test.questions.generator.QuestionGenerator;

import java.util.ArrayList;
import java.util.Collections;

import static com.magis.app.Configure.DEFAULT_NUM_TEST_QUESTIONS;

public class TestPageContent extends ExamPageContent {

    private ExamsModel.ChapterModel test;
    private ArrayList<Integer> usedTestBankQuestions;
    private int numAvailableTestBankQuestions;
    private ArrayList<ExamsModel.ChapterModel> quizzes;
    private ArrayList<Integer> numAvailableQuizBankQuestions;
    private ArrayList<QuestionGenerator> questionGenerators;
    private ArrayList<Integer> numGeneratedQuizQuestions;
    private ArrayList<Integer> maxedOutQuizzes;
    private int numChaptersOnTest;

    public TestPageContent(int chapterIndex) {
        super(chapterIndex, Main.numQuestionsPerTest.getOrDefault(Main.lessonModel.getChapter(chapterIndex).getTitle(), DEFAULT_NUM_TEST_QUESTIONS));
        //test
        this.test = Main.testsModel.getChapter(Main.lessonModel.getChapter(chapterIndex).getTitle());
        usedTestBankQuestions = new ArrayList<>();
        numAvailableTestBankQuestions = test != null ? test.getNumAvailableQuestions() : 0;
        //all the quizzes
        quizzes = new ArrayList<>();
        numAvailableQuizBankQuestions = new ArrayList<>();
        questionGenerators = new ArrayList<>();
        numGeneratedQuizQuestions = new ArrayList<>();
        maxedOutQuizzes = new ArrayList<>();
        int mapCounter = 0;
        //add all of the quiz banks and generators from the current chapter and previous chapters (but not any before the previous test)
        for (int i = chapterIndex; i >= 0; i--) {
            String chapterTitle = Main.lessonModel.getChapter(i).getTitle();
            if (i != chapterIndex && Main.testsModel.hasTest(chapterTitle)) break;
            numChaptersOnTest++;
            //static quiz questions
            ExamsModel.ChapterModel quiz = Main.quizzesModel.getChapter(Main.lessonModel.getChapter(i).getTitle());
            int numAvailableQuizQuestionsForChapter = quiz != null ? quiz.getNumAvailableQuestions() : 0;
            if (numAvailableQuizQuestionsForChapter > 0) {
                quizzes.add(quiz);
                usedQuizBankQuestions.put(mapCounter, new ArrayList<>()); //to keep track of which quiz question from each chapter we've used
                numAvailableQuizBankQuestions.add(numAvailableQuizQuestionsForChapter);
            } else {
                //we have to keep everything in line
                quizzes.add(null);
                usedQuizBankQuestions.put(mapCounter, new ArrayList<>());
                numAvailableQuizBankQuestions.add(0);
            }
            mapCounter++;
            //generated quiz questions
            if (Main.questionGenerator.get(i) != null) {
                questionGenerators.add(Main.questionGenerator.get(i));
                numGeneratedQuizQuestions.add(0); //initialize to 0
            } else {
                numGeneratedQuizQuestions.add(0);
                questionGenerators.add(null); //we have to keep everything in line
            }
        }
        examSaver.setType("test");
    }

    @Override
    protected boolean buildQuestion() {
        int quizQuestionOrTestQuestion;
        if (checkForAvailableQuizQuestions() && numAvailableTestBankQuestions > usedTestBankQuestions.size()) {
            quizQuestionOrTestQuestion = rand.nextInt(2); //increase to make it more likely to pull from test bank
        } else if (checkForAvailableQuizQuestions() && numAvailableTestBankQuestions <= usedTestBankQuestions.size()) {
            quizQuestionOrTestQuestion = 0; //it's a quiz question
        } else if (!checkForAvailableQuizQuestions() && numAvailableTestBankQuestions > usedTestBankQuestions.size()) {
            quizQuestionOrTestQuestion = 1; //it's a test question
        } else {
            return false; //we're out of unique questions
        }

        //if 0, then we're going to get a question from an old quiz
        if (quizQuestionOrTestQuestion == 0) {
            //pick a quiz
            int whichQuiz;
            //we are only guaranteed to get to this point if there still exists a quiz that is NOT in maxedOutQuizzes
            do whichQuiz = rand.nextInt(quizzes.size());
            while (maxedOutQuizzes.contains(whichQuiz));

            //decide if the question is pulled from a bank (0) or generated (1)
            int typeOfQuestion = -1; //this is promised to change
            //if we have available bank questions and there exists a question generator (and still have unique questions to generate)
            if (usedQuizBankQuestions.get(whichQuiz).size() < numAvailableQuizBankQuestions.get(whichQuiz) && (questionGenerators.get(whichQuiz) != null && numGeneratedQuizQuestions.get(whichQuiz) < questionGenerators.get(whichQuiz).getNumUnique())) {
                typeOfQuestion = rand.nextInt(2); //0 or 1
                //if we have a bank of questions, but don't have a question generator (or we're out of unique generated questions)
            } else if (numAvailableQuizBankQuestions.get(whichQuiz) > usedQuizBankQuestions.get(whichQuiz).size() && (questionGenerators.get(whichQuiz) == null || numGeneratedQuizQuestions.get(whichQuiz) >= questionGenerators.get(whichQuiz).getNumUnique())) {
                typeOfQuestion = 0;
                //if we don't have a bank of questions, but do have a question generator (and still have unique questions to generate)
            } else if (numAvailableQuizBankQuestions.get(whichQuiz) <= usedQuizBankQuestions.get(whichQuiz).size() && (questionGenerators.get(whichQuiz) != null && numGeneratedQuizQuestions.get(whichQuiz) < questionGenerators.get(whichQuiz).getNumUnique())) {
                typeOfQuestion = 1;
            }
            switch (typeOfQuestion) {
                case 0:
                    //grab a random question from the question bank that hasn't been used before
                    int questionNumber;
                    do questionNumber = rand.nextInt(numAvailableQuizBankQuestions.get(whichQuiz));
                    while (usedQuizBankQuestions.get(whichQuiz).contains(questionNumber));
                    usedQuizBankQuestions.get(whichQuiz).add(questionNumber); //add the question to the used bank of questions
                    //set the question statement
                    ExamsModel.ChapterModel.QuestionsModel question = quizzes.get(whichQuiz).getQuestion(questionNumber);
                    int level = question.getLevel();
                    //Determine if it should be "point" or "points". i.e. "1 point" vs. "2 points"
                    pointsAndIndex = level == 1 ? level + " point" : level + " points";
                    pointsAndIndex += "\nQuestion " + (questionIndex + 1);

                    //save the points and question index
                    examQuestion.setPointsAndQuestionIndex(pointsAndIndex);

                    if (question.getNumCorrectAnswers() == 1) {
                        statement = question.getStatement();
                    } else { //if there is more than one correct answer, hint this to the student
                        String statement = question.getStatement();
                        statement += "\n\nNote: There may be more than one correct answer.";
                        this.statement = statement;
                    }
                    //save the question
                    examQuestion.setQuestion(question.getStatement());
                    //get and save the level
                    examQuestion.setLevel(question.getLevel());
                    //set the type
                    examQuestion.setWritten(question.isWritten());
                    //get and save the correct answers
                    examQuestion.addCorrectAnswers(question.getCorrectAnswers());
                    //get and save the incorrect answers;
                    examQuestion.addIncorrectAnswers(question.getIncorrectAnswers());
                    //get and save all of the answers
                    examQuestion.addAnswers(question.getAnswers());
                    /*
                    shuffle the order if we're allowed to
                    the order for the answer of written questions is essential!
                     */
                    if (question.isShuffle() && !question.isWritten()) Collections.shuffle(examQuestion.getAnswers());
                    break;
                case 1:
                    numGeneratedQuizQuestions.set(whichQuiz, numGeneratedQuizQuestions.get(whichQuiz) + 1);
                    do {
                        questionGenerators.get(whichQuiz).initialize();
                        generatedQuestion = questionGenerators.get(whichQuiz).getQuestion();
                    }
                    while (usedGeneratorQuestions.contains(generatedQuestion));
                    usedGeneratorQuestions.add(generatedQuestion);
                    //set the points and question index
                    int questionLevel = questionGenerators.get(whichQuiz).getLevel();
                    pointsAndIndex = questionLevel == 1 ? questionLevel + " point" : questionLevel + " points";
                    pointsAndIndex += "\nQuestion " + (questionIndex + 1);
                    //save the points and question index
                    examQuestion.setPointsAndQuestionIndex(pointsAndIndex);
                    statement = generatedQuestion;
                    //save the question
                    examQuestion.setQuestion(generatedQuestion);
                    //save the level
                    examQuestion.setLevel(questionGenerators.get(whichQuiz).getLevel());
                    //get and save the correct answer
                    examQuestion.addCorrectAnswer(questionGenerators.get(whichQuiz).getCorrectAnswer());
                    //get and save all of the answers (correct and incorrect)
                    examQuestion.addAnswers(questionGenerators.get(whichQuiz).getAnswers());
                    break;
            }

        }
        //else, pull from the test bank
        else {
            //grab a random question from the question bank that hasn't been used before
            int questionNumber;
            do questionNumber = rand.nextInt(numAvailableTestBankQuestions);
            while (usedTestBankQuestions.contains(questionNumber));
            usedTestBankQuestions.add(questionNumber); //add the question to the used bank of questions
            //set the question statement
            ExamsModel.ChapterModel.QuestionsModel question = test.getQuestion(questionNumber);
            int level = question.getLevel();
            //Determine if it should be "point" or "points". i.e. "1 point" vs. "2 points"
            pointsAndIndex = level == 1 ? level + " point" : level + " points";
            pointsAndIndex += "\nQuestion " + (questionIndex + 1);

            //save the points and question index
            examQuestion.setPointsAndQuestionIndex(pointsAndIndex);

            if (question.getNumCorrectAnswers() == 1) {
                statement = question.getStatement();
            } else { //if there is more than one correct answer, hint this to the student
                String statement = question.getStatement();
                statement += "\n\nNote: There may be more than one correct answer.";
                this.statement = statement;
            }
            //save the question
            examQuestion.setQuestion(question.getStatement());
            //get and save the level
            examQuestion.setLevel(question.getLevel());
            //set the type
            examQuestion.setWritten(question.isWritten());
            //get and save the correct answers
            examQuestion.addCorrectAnswers(question.getCorrectAnswers());
            //get and save the incorrect answers;
            examQuestion.addIncorrectAnswers(question.getIncorrectAnswers());
            //get and save all of the answers
            examQuestion.addAnswers(question.getAnswers());
                /*
                shuffle the order if we're allowed to
                the order for the answer of written questions is essential!
                 */
            if (question.isShuffle() && !question.isWritten()) Collections.shuffle(examQuestion.getAnswers());
        }
        return true;
    }

    private boolean checkForAvailableQuizQuestions() {
        boolean available = false;
        for (int i = 0; i < numChaptersOnTest; i++) {
            if ((quizzes.get(i) != null && (usedQuizBankQuestions.get(i).size() < numAvailableQuizBankQuestions.get(i))) ||
                    (questionGenerators.get(i) != null && numGeneratedQuizQuestions.get(i) < questionGenerators.get(i).getNumUnique())) {
                available = true;
            } else {
                if (!maxedOutQuizzes.contains(i)) maxedOutQuizzes.add(i);
            }
        }
        return available;
    }
}
