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


    public ArrayList<double[]> getQuizResults() {
        // each quizzesRes element has double[](avg, best, worst) scores
        ArrayList<double[]> quizzesRes = new ArrayList<double[]>();
        for (int i = 0; i < Main.lessonModel.getChapters().size(); i++) {
            // default quiz result vals
            double [] quizRes = new double[3];
            Arrays.fill(quizRes, 0.0);
            String title = Main.lessonModel.getChapter(i).getTitle();
            if (Main.quizzesModel.hasQuiz(title)) {
                StudentModel.Student.Quiz quiz = Main.studentModel.getStudent().getQuiz(i);
                quizRes[0] = quiz.getAverageScore();
                quizRes[1] = quiz.getBestScore();
                quizRes[2] = quiz.getWorstScore();
            }
            quizzesRes.add(quizRes);
        }
        return quizzesRes;
    }

    public ArrayList<double[]> getTestResults() {
        // each testsRes element has double[](avg, best, worst) scores
        ArrayList<double[]> testsRes = new ArrayList<double[]>();
        for (int i = 0; i < Main.lessonModel.getChapters().size(); i++) {
            // default test result vals
            double [] testRes = new double[3];
            Arrays.fill(testRes, 0.0);
            String title = Main.lessonModel.getChapter(i).getTitle();
            if (Main.testsModel.hasTest(title)) {
                StudentModel.Student.Test test = Main.studentModel.getStudent().getTest(i);
                testRes[0] = test.getAverageScore();
                testRes[1] = test.getBestScore();
                testRes[2] = test.getWorstScore();
            }
            testsRes.add(testRes);
        }
        return testsRes;
    }

    public ArrayList<Integer> getChaptersProgress() {
        ArrayList<Integer> chaptersProgress = new ArrayList<Integer>();
        for (int i = 0; i < Main.lessonModel.getChapters().size(); i++) {
            int chProg = Main.studentModel.getStudent().getChapter(i).getProgress();
            chaptersProgress.add(chProg);
        }
        return chaptersProgress;
    }
}
