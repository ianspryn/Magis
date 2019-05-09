package com.magis.app.page;

import com.jfoenix.controls.JFXTextField;
import com.magis.app.Main;
import com.magis.app.models.ExamsModel;
import javafx.scene.text.TextFlow;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Arrays;
import java.util.Collections;

import static com.magis.app.Configure.*;

public class QuizPageContent extends ExamPageContent {
    public QuizPageContent(int chapterIndex) {
        super(chapterIndex, Main.numQuestionsPerQuiz.getOrDefault(Main.lessonModel.getChapter(chapterIndex).getTitle(), DEFAULT_NUM_QUIZ_QUESTIONS), Main.quizzesModel.getChapter(Main.lessonModel.getChapter(chapterIndex).getTitle()));
        examSaver.setType("quiz");
    }

    @Override
    protected boolean buildQuestion() {
        //decide if the question is pulled from a bank (0) or generated (1)
        int typeOfQuestion;
        //if we have available bank questions and there exists a question generator (and still have unique questions to generate)
        if (usedBankQuestions.size() < numAvailableBankQuestions && (questionGenerator != null && numGeneratedQuestions < questionGenerator.getNumUnique())) {
            typeOfQuestion = rand.nextInt(2); //0 or 1
            //if we have a bank of questions, but don't have a question generator (or we're out of unique generated questions)
        } else if (numAvailableBankQuestions > usedBankQuestions.size() && (questionGenerator == null || numGeneratedQuestions >= questionGenerator.getNumUnique())) {
            typeOfQuestion = 0;
            //if we don't have a bank of questions, but do have a question generator (and still have unique questions to generate)
        } else if (numAvailableBankQuestions <= usedBankQuestions.size() && (questionGenerator != null && numGeneratedQuestions < questionGenerator.getNumUnique())) {
            typeOfQuestion = 1;
        } else { //if we don't have either, we're out of unique questions
            return false;
        }

        switch (typeOfQuestion) {
            case 0:
                //grab a random question from the question bank that hasn't been used before
                int questionNumber;
                do questionNumber = rand.nextInt(numAvailableBankQuestions);
                while (usedBankQuestions.contains(questionNumber));
                usedBankQuestions.add(questionNumber); //add the question to the used bank of questions
                //set the question statement
                ExamsModel.ChapterModel.QuestionsModel question = exam.getQuestion(questionNumber);
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
                numGeneratedQuestions++;
                do {
                    questionGenerator.initialize();
                    generatedQuestion = questionGenerator.getQuestion();
                }
                while (usedGeneratorQuestions.contains(generatedQuestion));
                usedGeneratorQuestions.add(generatedQuestion);
                //set the points and question index
                int questionLevel = questionGenerator.getLevel();
                pointsAndIndex = questionLevel == 1 ? questionLevel + " point" : questionLevel + " points";
                pointsAndIndex += "\nQuestion " + (questionIndex + 1);
                //save the points and question index
                examQuestion.setPointsAndQuestionIndex(pointsAndIndex);
                statement = generatedQuestion;
                //save the question
                examQuestion.setQuestion(generatedQuestion);
                //save the level
                examQuestion.setLevel(questionGenerator.getLevel());
                //get and save the correct answer
                examQuestion.addCorrectAnswer(questionGenerator.getCorrectAnswer());
                //get and save all of the answers (correct and incorrect)
                examQuestion.addAnswers(questionGenerator.getAnswers());
                break;
        }
        return true;
    }
}
