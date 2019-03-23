package com.magis.app.results;

/*There will be a page to see all of your results. Things like average score, worst score, etc.
Eventually you'll also be able to go back in history and see your quiz answers.
Right now I don't save them though.All your information will come from the student XML file.
You can access the information
 via `Main.studentModel.getStudent(Main.username).getStuff`
calling stuff through `studentModel` should be pretty self explanatory. Let me know if you have any questions
create a new folder (or package, as Intellij calls it) and name it `results`
Minor change: Please make your class non-static. Meaning, I will create an instance of your class and
I will pass in the student username as a parameter. That way, you won't have to say `Main.username`
when traversing the studentModel.
*/
public class StudentResults {

    private int numQuizzes; // this is the number of quizzes that exist

    // these three arrays are of size numQuizzes
    private double[] quizzesAverageScores = double[numQuizzes];
    private double[] quizzesWorstScores;
    private double[] quizzesBestScores;

    public StudentResults(String username){
        Main.studentModel.getStudent(Main.username).getStuff
    }

    public double[] getQuizzesAverageScores() {
        return quizzesAverageScores;
    }

    public double[] getQuizzesWorstScores() {
        return quizzesWorstScores;
    }

    public double[] getQuizzesBestScores() {
        return quizzesBestScores;
    }
}
