package com.magis.app.page;
import com.magis.app.Main;

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

        switch(typeOfQuestion) {
            case 0:
                //grab a random question from the question bank that hasn't been used before
                int question;
                do question = rand.nextInt(numAvailableBankQuestions);
                while (usedBankQuestions.contains(question));
                usedBankQuestions.add(question); //add the question to the used bank of questions
                //set the question statement
                int level = exam.getQuestion(question).getLevel();
                //Determine if it should be "point" or "points". i.e. "1 point" vs. "2 points"
                pointsAndIndex = level == 1 ? level + " point" : level + " points";
                pointsAndIndex += "\nQuestion " + (questionIndex + 1);
                if (exam.getQuestion(question).getNumCorrectAnswers() == 1) {
                    statement = exam.getQuestion(question).getStatement();
                } else { //if there is more than one correct answer, hint this to the student
                    String statement = exam.getQuestion(question).getStatement();
                    statement += "\n\nNote: There may be more than one correct answer.";
                    this.statement = statement;
                }
                //save the question
                examQuestion.setQuestion(exam.getQuestion(question).getStatement());
                //get and save the level
                examQuestion.setLevel(exam.getQuestion(question).getLevel());
                //get and save the correct answers
                examQuestion.addCorrectAnswers(exam.getQuestion(question).getCorrectAnswers());
                //get and save the incorrect answers;
                examQuestion.addIncorrectAnswers(exam.getQuestion(question).getIncorrectAnswers());
                //get and save all of the answers
                examQuestion.addAnswers(exam.getQuestion(question).getAnswers());
                //shuffle the order if we're allowed to
                if (exam.getQuestion(question).isShuffle()) Collections.shuffle(examQuestion.getAnswers());
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
