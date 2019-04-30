package com.magis.app.page;
import com.magis.app.Configure;
import com.magis.app.Main;

import java.util.Collections;

import static com.magis.app.Configure.*;

public class QuizPageContent extends ExamPageContent {
    public QuizPageContent(int chapterIndex) {
        super(chapterIndex, Main.numQuestionsPerQuiz.getOrDefault(Main.lessonModel.getChapter(chapterIndex).getTitle(), DEFAULT_NUM_QUIZ_QUESTIONS), Main.quizzesModel.getChapter(Main.lessonModel.getChapter(chapterIndex).getTitle()));
        examSaver.setType("quiz");
    }


    @Override
    protected void buildQuestions() {
        //decide if the question is pulled from a bank (0) or generated (1)
        int typeOfQuestion;
        if (numAvailableBankQuestions > usedBankQuestions.size() && questionGenerator != null) { //if we have available bank questions and there exists a question generator
            typeOfQuestion = rand.nextInt(2); //0 or 1
        } else if (numAvailableBankQuestions > usedBankQuestions.size() && questionGenerator == null) { //if we don't have a question generator but do have bank questions
            typeOfQuestion = 0;
        } else if (numAvailableBankQuestions <= usedBankQuestions.size() && questionGenerator != null) { //if we do have a question generator but don't have bank questions
            typeOfQuestion = 1;
        } else { //if we don't have either
            return;
        }

        switch(typeOfQuestion) {
            case 0:
                //grab a random question from the question bank that hasn't been used before
                int question;
                do question = rand.nextInt(numAvailableBankQuestions);
                while (usedBankQuestions.contains(question));
                usedBankQuestions.add(question); //add the question to the used bank of questions
                //set the question statement
                statement.setText(exam.getQuestion(question).getStatement());
                //save the question
                examQuestion.setQuestion(exam.getQuestion(question).getStatement());
                //add the statement for the question to the questionBox
                questionBox.getChildren().add(statement);

                //save the label
                examQuestion.setLevel(exam.getQuestion(question).getLevel());

                //get all of the correct answers (there may be 1 or more correct answers)
//                            correctAnswers.addAll(exam.getQuestion(question).getCorrectAnswers());
                //save the correct answers
                examQuestion.addCorrectAnswers(exam.getQuestion(question).getCorrectAnswers());
                ///add the incorrect answers and the correct answer to the ArrayList of possible answers
//                            answers.addAll(exam.getQuestion(question).getIncorrectAnswers());
//                            answers.addAll(correctAnswers);
                //save all of the answers
                examQuestion.addAnswers(exam.getQuestion(question).getIncorrectAnswers());
                examQuestion.addAnswers(exam.getQuestion(question).getCorrectAnswers());
                //shuffle the order
//                          Collections.shuffle(answers);
                Collections.shuffle(examQuestion.getAnswers());
                //add the correct answer to the grader for future grading
//                            grader.addCorrectAnswer(questionIndex, correctAnswers);
                break;
            case 1:
                questionGenerator.initialize();
                do generatedQuestion = questionGenerator.getQuestion();
                while (usedGeneratorQuestions.contains(generatedQuestion));
                usedGeneratorQuestions.add(generatedQuestion);
                //set the question statement
                statement.setText(generatedQuestion);
                //save the question
                examQuestion.setQuestion(generatedQuestion);
                //add the statement to the questionBox
                questionBox.getChildren().add(statement);
                //get the correct answer
//                            correctAnswers.add(questionGenerator.getCorrectAnswer());
                //save the correct answer
                examQuestion.addCorrectAnswer(questionGenerator.getCorrectAnswer());
                //add the correct answer to the grader for future grading
//                            grader.addCorrectAnswer(questionIndex, correctAnswers);
                //get all of the answers (correct and incorrect)
//                            answers = questionGenerator.getAnswers();
                //save all of the answers (correct and incorrect)
                examQuestion.addAnswers(questionGenerator.getAnswers());
                break;
        }
    }
}
