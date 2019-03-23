package com.magis.app.results;

import com.magis.app.Main;
import com.magis.app.models.QuizzesModel;
import com.magis.app.models.StudentModel;

import java.util.Arrays;
import java.util.Collections;
import java.util.ArrayList;

public class StudentResults {

    private String UN;
    public StudentResults(String username){

        this.UN = username;
    }


    public ArrayList<double[]> quizResults() {

        // each quizzesRes element has double[](avg, best, worst) scores
        ArrayList<double[]> quizzesRes = new ArrayList<double[]>();

        for (int i = 0; i < Main.lessonModel.getChapters().size(); i++) {
            // default quiz result vals
            double [] quizRes = new double[3];
            Arrays.fill(quizRes, 0.0);
            String title = Main.lessonModel.getChapter(i).getTitle();
            if (Main.quizzesModel.hasQuiz(title)) {
                StudentModel.Student.Quiz quiz = Main.studentModel.getStudent(Main.username).getQuiz(i);

                quizRes[0] = quiz.getAverageScore();
                quizRes[1] = quiz.getBestScore();
                quizRes[2] = quiz.getWorstScore();
            }

            quizzesRes.add(quizRes);
        }

        return quizzesRes;
    }

    public ArrayList<double[]> testResults() {

        // each testsRes element has double[](avg, best, worst) scores
        ArrayList<double[]> testsRes = new ArrayList<double[]>();

        for (int i = 0; i < Main.lessonModel.getChapters().size(); i++) {
            // default test result vals
            double [] testRes = new double[3];
            Arrays.fill(testRes, 0.0);
            String title = Main.lessonModel.getChapter(i).getTitle();
            if (Main.quizzesModel.hasQuiz(title)) {
                StudentModel.Student.Quiz quiz = Main.studentModel.getStudent(Main.username).getQuiz(i);

                testRes[0] = quiz.getAverageScore();
                testRes[1] = quiz.getBestScore();
                testRes[2] = quiz.getWorstScore();
            }

            testsRes.add(testRes);
        }

        return testsRes;
    }
}
