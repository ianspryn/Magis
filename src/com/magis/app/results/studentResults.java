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

    // return all exam results in a nice String
    public ArrayList<double[]> quizResults() {

        // each quizzesRes element has double[](avg, best, worst) scores
        ArrayList<double[]> quizzesRes = new ArrayList<double[]>();

        for (int i = 0; i < Main.lessonModel.getChapters().size(); i++) {
            // default quiz vals
            double [] quizRes = new double[3];
            Arrays.fill(quizRes, 0.0);
            String title = Main.lessonModel.getChapter(i).getTitle();
            if (Main.quizzesModel.hasQuiz(title)) {
                StudentModel.Student.Quiz quiz = Main.studentModel.getStudent(Main.username).getQuiz(i);

                quizRes[0] = quiz.getAverageScore();
                ArrayList allScores = quiz.getScores();
                quizRes[1] = Collections.max(allScores);
            }
        }

        return quizzesRes;
    }

    // return all quiz results in a nice String
    private  quizResults() {
        String results = "";
        // for each quiz


        return results;
    }
}
