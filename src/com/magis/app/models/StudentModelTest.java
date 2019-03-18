package com.magis.app.models;

import org.junit.Assert;
import org.junit.Test;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;

public class StudentModelTest {

    /*
    File
     */
    @Test
    public void canDeleteFile() throws IOException {
        new StudentModel(new LessonModel());
        File file = new File("src/com/magis/app/resources/students.xml");
        boolean result = Files.deleteIfExists(file.toPath());
        Assert.assertTrue(result);
    }










    /*
    Test with no pre-existing XML file
     */
    /*
    Student Class
     */
    @Test
    public void addNewStudent() throws IOException {
        File file = new File("src/com/magis/app/resources/students.xml");
        Files.deleteIfExists(file.toPath());
        StudentModel studentModel = new StudentModel(new LessonModel());
        int result = studentModel.addStudent("ianspryn", "Ian", "Spryn");

        Assert.assertEquals(0, result);
        Files.deleteIfExists(file.toPath());
    }

    @Test
    public void addDuplicateStudent() throws IOException {
        File file = new File("src/com/magis/app/resources/students.xml");
        Files.deleteIfExists(file.toPath());
        StudentModel studentModel = new StudentModel(new LessonModel());
        studentModel.addStudent("ianspryn", "Ian", "Spryn");
        int result = studentModel.addStudent("ianspryn", "Ian", "Spryn");

        Assert.assertEquals(-1, result);
        Files.deleteIfExists(file.toPath());
    }

    @Test
    public void getStudentFirstName() throws IOException {
        File file = new File("src/com/magis/app/resources/students.xml");
        Files.deleteIfExists(file.toPath());
        StudentModel studentModel = new StudentModel(new LessonModel());
        studentModel.addStudent("ianspryn","Ian","Spryn");
        String firstName = studentModel.getStudent("ianspryn").getFirstName();

        Assert.assertEquals("Ian",firstName);
        Files.deleteIfExists(file.toPath());
    }

    @Test
    public void getStudentLastName() throws IOException {
        File file = new File("src/com/magis/app/resources/students.xml");
        Files.deleteIfExists(file.toPath());
        StudentModel studentModel = new StudentModel(new LessonModel());
        studentModel.addStudent("ianspryn","Ian","Spryn");
        String lastName = studentModel.getStudent("ianspryn").getLastName();

        Assert.assertEquals("Spryn",lastName);
        Files.deleteIfExists(file.toPath());
    }

    @Test
    public void getStudentFullName() throws IOException {
        File file = new File("src/com/magis/app/resources/students.xml");
        Files.deleteIfExists(file.toPath());
        StudentModel studentModel = new StudentModel(new LessonModel());
        studentModel.addStudent("ianspryn","Ian","Spryn");
        String fullName = studentModel.getStudent("ianspryn").getFullName();

        Assert.assertEquals("Ian Spryn",fullName);
        Files.deleteIfExists(file.toPath());
    }

    /*
    Chapter Class
     */
    @Test
    public void getChapterProgress() throws IOException {
        File file = new File("src/com/magis/app/resources/students.xml");
        Files.deleteIfExists(file.toPath());
        StudentModel studentModel = new StudentModel(new LessonModel());
        studentModel.addStudent("ianspryn","Ian","Spryn");
        studentModel.getStudent("ianspryn").getChapter(1).setProgress(50);
        int progress = studentModel.getStudent("ianspryn").getChapter(1).getProgress();

        Assert.assertEquals(50, progress);
        Files.deleteIfExists(file.toPath());
    }

    /*
    Quiz Class
     */
    @Test
    public void getQuizChapterNumber() throws IOException {
        File file = new File("src/com/magis/app/resources/students.xml");
        Files.deleteIfExists(file.toPath());
        StudentModel studentModel = new StudentModel(new LessonModel());
        studentModel.addStudent("ianspryn","Ian","Spryn");
        studentModel.getStudent("ianspryn").getQuiz(1).addScore(100);
        int expectedChapter = 1;
        int actualChapter = studentModel.getStudent("ianspryn").getQuiz(expectedChapter).getQuizChapterNumber();

        Assert.assertEquals(expectedChapter, actualChapter);
        Files.deleteIfExists(file.toPath());
    }

    @Test
    public void getQuizScores() throws IOException {
        File file = new File("src/com/magis/app/resources/students.xml");
        Files.deleteIfExists(file.toPath());
        StudentModel studentModel = new StudentModel(new LessonModel());
        studentModel.addStudent("ianspryn","Ian","Spryn");
        studentModel.getStudent("ianspryn").getQuiz(1).addScore(50);
        studentModel.getStudent("ianspryn").getQuiz(1).addScore(92.5);

        ArrayList<Double> expectedScores = new ArrayList<>();
        expectedScores.add(50.0);
        expectedScores.add(92.5);

        ArrayList<Double> actualScores = studentModel.getStudent("ianspryn").getQuiz(1).getScores();

        Assert.assertEquals(expectedScores, actualScores);
        Files.deleteIfExists(file.toPath());
    }

    @Test
    public void getLastQuizScoreWithScores() throws IOException {
        File file = new File("src/com/magis/app/resources/students.xml");
        Files.deleteIfExists(file.toPath());
        StudentModel studentModel = new StudentModel(new LessonModel());
        studentModel.addStudent("ianspryn","Ian","Spryn");
        studentModel.getStudent("ianspryn").getQuiz(1).addScore(50);
        studentModel.getStudent("ianspryn").getQuiz(1).addScore(92.5);

        Double expectedScore = 92.5;
        Double actualScore = studentModel.getStudent("ianspryn").getQuiz(1).getLastScore();

        Assert.assertEquals(expectedScore, actualScore);
        Files.deleteIfExists(file.toPath());
    }

    @Test
    public void getLastQuizScoreWithSingleScore() throws IOException {
        File file = new File("src/com/magis/app/resources/students.xml");
        Files.deleteIfExists(file.toPath());
        StudentModel studentModel = new StudentModel(new LessonModel());
        studentModel.addStudent("ianspryn","Ian","Spryn");
        studentModel.getStudent("ianspryn").getQuiz(1).addScore(92.5);

        Double expectedScore = 92.5;
        Double actualScore = studentModel.getStudent("ianspryn").getQuiz(1).getLastScore();

        Assert.assertEquals(expectedScore, actualScore);
        Files.deleteIfExists(file.toPath());
    }

    @Test
    public void getLastQuizScoreWithNoScore() throws IOException {
        File file = new File("src/com/magis/app/resources/students.xml");
        Files.deleteIfExists(file.toPath());
        StudentModel studentModel = new StudentModel(new LessonModel());
        studentModel.addStudent("ianspryn","Ian","Spryn");

        Double expectedScore = -1.0;
        Double actualScore = studentModel.getStudent("ianspryn").getQuiz(1).getLastScore();

        Assert.assertEquals(expectedScore, actualScore);
        Files.deleteIfExists(file.toPath());
    }

    @Test
    public void getAverageQuizScore() throws IOException {
        File file = new File("src/com/magis/app/resources/students.xml");
        Files.deleteIfExists(file.toPath());
        StudentModel studentModel = new StudentModel(new LessonModel());
        studentModel.addStudent("ianspryn","Ian","Spryn");
        studentModel.getStudent("ianspryn").getQuiz(1).addScore(42.0);
        studentModel.getStudent("ianspryn").getQuiz(1).addScore(85.5);
        studentModel.getStudent("ianspryn").getQuiz(1).addScore(92.5);
        studentModel.getStudent("ianspryn").getQuiz(1).addScore(98.5);

        Double expectedAverage = (42.0 + 85.5 + 92.5 + 98.5) / 4.0;
        Double actualAverage = studentModel.getStudent("ianspryn").getQuiz(1).getAverageScore();

        Assert.assertEquals(expectedAverage, actualAverage);
        Files.deleteIfExists(file.toPath());
    }

    @Test
    public void getAverageQuizScoreWithOneScore() throws IOException {
        File file = new File("src/com/magis/app/resources/students.xml");
        Files.deleteIfExists(file.toPath());
        StudentModel studentModel = new StudentModel(new LessonModel());
        studentModel.addStudent("ianspryn","Ian","Spryn");
        studentModel.getStudent("ianspryn").getQuiz(1).addScore(42.0);

        Double expectedAverage = 42.0;
        Double actualAverage = studentModel.getStudent("ianspryn").getQuiz(1).getAverageScore();

        Assert.assertEquals(expectedAverage, actualAverage);
        Files.deleteIfExists(file.toPath());
    }

    @Test
    public void getAverageQuizScoreWithNoScore() throws IOException {
        File file = new File("src/com/magis/app/resources/students.xml");
        Files.deleteIfExists(file.toPath());
        StudentModel studentModel = new StudentModel(new LessonModel());
        studentModel.addStudent("ianspryn","Ian","Spryn");

        Double expectedAverage = 0.0;
        Double actualAverage = studentModel.getStudent("ianspryn").getQuiz(1).getAverageScore();

        Assert.assertEquals(expectedAverage, actualAverage);
        Files.deleteIfExists(file.toPath());
    }

    /*
    Exam Class
     */
    @Test
    public void getExamChapterNumber() throws IOException {
        File file = new File("src/com/magis/app/resources/students.xml");
        Files.deleteIfExists(file.toPath());
        StudentModel studentModel = new StudentModel(new LessonModel());
        studentModel.addStudent("ianspryn","Ian","Spryn");
        studentModel.getStudent("ianspryn").getExam(1).addScore(12.5);
        int expectedChapter = 1;
        int actualChapter = studentModel.getStudent("ianspryn").getExam(expectedChapter).getExamChapterNumber();

        Assert.assertEquals(expectedChapter, actualChapter);
        Files.deleteIfExists(file.toPath());
    }

    @Test
    public void getExamScores() throws IOException {
        File file = new File("src/com/magis/app/resources/students.xml");
        Files.deleteIfExists(file.toPath());
        StudentModel studentModel = new StudentModel(new LessonModel());
        studentModel.addStudent("ianspryn","Ian","Spryn");
        studentModel.getStudent("ianspryn").getExam(1).addScore(50);
        studentModel.getStudent("ianspryn").getExam(1).addScore(92.5);

        ArrayList<Double> expectedScores = new ArrayList<>();
        expectedScores.add(50.0);
        expectedScores.add(92.5);

        ArrayList<Double> actualScores = studentModel.getStudent("ianspryn").getExam(1).getScores();

        Assert.assertEquals(expectedScores, actualScores);
        Files.deleteIfExists(file.toPath());
    }

    @Test
    public void getLastExamScoreWithScores() throws IOException {
        File file = new File("src/com/magis/app/resources/students.xml");
        Files.deleteIfExists(file.toPath());
        StudentModel studentModel = new StudentModel(new LessonModel());
        studentModel.addStudent("ianspryn","Ian","Spryn");
        studentModel.getStudent("ianspryn").getExam(1).addScore(50);
        studentModel.getStudent("ianspryn").getExam(1).addScore(92.5);

        Double expectedScore = 92.5;
        Double actualScore = studentModel.getStudent("ianspryn").getExam(1).getLastScore();

        Assert.assertEquals(expectedScore, actualScore);
        Files.deleteIfExists(file.toPath());
    }

    @Test
    public void getLastExamScoreWithSingleScore() throws IOException {
        File file = new File("src/com/magis/app/resources/students.xml");
        Files.deleteIfExists(file.toPath());
        StudentModel studentModel = new StudentModel(new LessonModel());
        studentModel.addStudent("ianspryn","Ian","Spryn");
        studentModel.getStudent("ianspryn").getExam(1).addScore(92.5);

        Double expectedScore = 92.5;
        Double actualScore = studentModel.getStudent("ianspryn").getExam(1).getLastScore();

        Assert.assertEquals(expectedScore, actualScore);
        Files.deleteIfExists(file.toPath());
    }

    @Test
    public void getLastExamScoreWithNoScore() throws IOException {
        File file = new File("src/com/magis/app/resources/students.xml");
        Files.deleteIfExists(file.toPath());
        StudentModel studentModel = new StudentModel(new LessonModel());
        studentModel.addStudent("ianspryn","Ian","Spryn");

        Double expectedScore = -1.0;
        Double actualScore = studentModel.getStudent("ianspryn").getExam(1).getLastScore();

        Assert.assertEquals(expectedScore, actualScore);
        Files.deleteIfExists(file.toPath());
    }

    @Test
    public void getAverageExamScore() throws IOException {
        File file = new File("src/com/magis/app/resources/students.xml");
        Files.deleteIfExists(file.toPath());
        StudentModel studentModel = new StudentModel(new LessonModel());
        studentModel.addStudent("ianspryn","Ian","Spryn");
        studentModel.getStudent("ianspryn").getExam(1).addScore(42.0);
        studentModel.getStudent("ianspryn").getExam(1).addScore(85.5);
        studentModel.getStudent("ianspryn").getExam(1).addScore(92.5);
        studentModel.getStudent("ianspryn").getExam(1).addScore(98.5);

        Double expectedAverage = (42.0 + 85.5 + 92.5 + 98.5) / 4.0;
        Double actualAverage = studentModel.getStudent("ianspryn").getExam(1).getAverageScore();

        Assert.assertEquals(expectedAverage, actualAverage);
        Files.deleteIfExists(file.toPath());
    }

    @Test
    public void getAverageExamScoreWithOneScore() throws IOException {
        File file = new File("src/com/magis/app/resources/students.xml");
        Files.deleteIfExists(file.toPath());
        StudentModel studentModel = new StudentModel(new LessonModel());
        studentModel.addStudent("ianspryn","Ian","Spryn");
        studentModel.getStudent("ianspryn").getExam(1).addScore(42.0);

        Double expectedAverage = 42.0;
        Double actualAverage = studentModel.getStudent("ianspryn").getExam(1).getAverageScore();

        Assert.assertEquals(expectedAverage, actualAverage);
        Files.deleteIfExists(file.toPath());
    }

    @Test
    public void getAverageExamScoreWithNoScore() throws IOException {
        File file = new File("src/com/magis/app/resources/students.xml");
        Files.deleteIfExists(file.toPath());
        StudentModel studentModel = new StudentModel(new LessonModel());
        studentModel.addStudent("ianspryn","Ian","Spryn");

        Double expectedAverage = 0.0;
        Double actualAverage = studentModel.getStudent("ianspryn").getExam(1).getAverageScore();

        Assert.assertEquals(expectedAverage, actualAverage);
        Files.deleteIfExists(file.toPath());
    }










    /*
    Test with pre-existing XML file
     */
    /*
    Student Class
     */

    @Test
    public void getStudentFirstNameXML() throws IOException {
        File file = new File("src/com/magis/app/resources/students.xml");
        Files.deleteIfExists(file.toPath());
        StudentModel studentModel = new StudentModel(new LessonModel());
        studentModel.addStudent("ianspryn","Ian","Spryn");

        StudentModel studentModel2 = new StudentModel(new LessonModel());
        String firstName =  studentModel2.getStudent("ianspryn").getFirstName();

        Assert.assertEquals("Ian",firstName);
        Files.deleteIfExists(file.toPath());
    }

    @Test
    public void getStudentLastNameXML() throws IOException {
        File file = new File("src/com/magis/app/resources/students.xml");
        Files.deleteIfExists(file.toPath());
        StudentModel studentModel = new StudentModel(new LessonModel());
        studentModel.addStudent("ianspryn","Ian","Spryn");

        StudentModel studentModel2 = new StudentModel(new LessonModel());
        String lastName = studentModel2.getStudent("ianspryn").getLastName();

        Assert.assertEquals("Spryn",lastName);
        Files.deleteIfExists(file.toPath());
    }

    @Test
    public void getStudentFullNameXML() throws IOException {
        File file = new File("src/com/magis/app/resources/students.xml");
        Files.deleteIfExists(file.toPath());
        StudentModel studentModel = new StudentModel(new LessonModel());
        studentModel.addStudent("ianspryn","Ian","Spryn");

        StudentModel studentModel2 = new StudentModel(new LessonModel());
        String fullName = studentModel2.getStudent("ianspryn").getFullName();

        Assert.assertEquals("Ian Spryn",fullName);
        Files.deleteIfExists(file.toPath());
    }

    /*
    Chapter Class
     */
    @Test
    public void getChapterProgressXML() throws IOException {
        File file = new File("src/com/magis/app/resources/students.xml");
        Files.deleteIfExists(file.toPath());
        StudentModel studentModel = new StudentModel(new LessonModel());
        studentModel.addStudent("ianspryn","Ian","Spryn");
        studentModel.getStudent("ianspryn").getChapter(1).setProgress(50);

        StudentModel studentModel2 = new StudentModel(new LessonModel());
        int progress = studentModel2.getStudent("ianspryn").getChapter(1).getProgress();

        Assert.assertEquals(50, progress);
        Files.deleteIfExists(file.toPath());
    }

    /*
    Quiz Class
     */
    @Test
    public void getQuizChapterNumberXML() throws IOException {
        File file = new File("src/com/magis/app/resources/students.xml");
        Files.deleteIfExists(file.toPath());
        StudentModel studentModel = new StudentModel(new LessonModel());
        studentModel.addStudent("ianspryn","Ian","Spryn");
        studentModel.getStudent("ianspryn").getQuiz(1).addScore(100);
        StudentModel studentModel2 = new StudentModel(new LessonModel());
        int expectedChapter = 1;
        int actualChapter = studentModel2.getStudent("ianspryn").getQuiz(expectedChapter).getQuizChapterNumber();

        Assert.assertEquals(expectedChapter, actualChapter);
        Files.deleteIfExists(file.toPath());
    }

    @Test
    public void getQuizScoresXML() throws IOException {
        File file = new File("src/com/magis/app/resources/students.xml");
        Files.deleteIfExists(file.toPath());
        StudentModel studentModel = new StudentModel(new LessonModel());
        studentModel.addStudent("ianspryn","Ian","Spryn");
        studentModel.getStudent("ianspryn").getQuiz(1).addScore(50);
        studentModel.getStudent("ianspryn").getQuiz(1).addScore(92.5);

        ArrayList<Double> expectedScores = new ArrayList<>();
        expectedScores.add(50.0);
        expectedScores.add(92.5);

        StudentModel studentModel2 = new StudentModel(new LessonModel());
        ArrayList<Double> actualScores = studentModel2.getStudent("ianspryn").getQuiz(1).getScores();

        Assert.assertEquals(expectedScores, actualScores);
        Files.deleteIfExists(file.toPath());
    }

    @Test
    public void getLastQuizScoreWithScoresXML() throws IOException {
        File file = new File("src/com/magis/app/resources/students.xml");
        Files.deleteIfExists(file.toPath());
        StudentModel studentModel = new StudentModel(new LessonModel());
        studentModel.addStudent("ianspryn","Ian","Spryn");
        studentModel.getStudent("ianspryn").getQuiz(1).addScore(50);
        studentModel.getStudent("ianspryn").getQuiz(1).addScore(92.5);

        StudentModel studentModel2 = new StudentModel(new LessonModel());
        Double expectedScore = 92.5;
        Double actualScore = studentModel2.getStudent("ianspryn").getQuiz(1).getLastScore();

        Assert.assertEquals(expectedScore, actualScore);
        Files.deleteIfExists(file.toPath());
    }

    @Test
    public void getLastQuizScoreWithSingleScoreXML() throws IOException {
        File file = new File("src/com/magis/app/resources/students.xml");
        Files.deleteIfExists(file.toPath());
        StudentModel studentModel = new StudentModel(new LessonModel());
        studentModel.addStudent("ianspryn","Ian","Spryn");
        studentModel.getStudent("ianspryn").getQuiz(1).addScore(92.5);

        StudentModel studentModel2 = new StudentModel(new LessonModel());
        Double expectedScore = 92.5;
        Double actualScore = studentModel2.getStudent("ianspryn").getQuiz(1).getLastScore();

        Assert.assertEquals(expectedScore, actualScore);
        Files.deleteIfExists(file.toPath());
    }

    @Test
    public void getLastQuizScoreWithNoScoreXML() throws IOException {
        File file = new File("src/com/magis/app/resources/students.xml");
        Files.deleteIfExists(file.toPath());
        StudentModel studentModel = new StudentModel(new LessonModel());
        studentModel.addStudent("ianspryn","Ian","Spryn");

        StudentModel studentModel2 = new StudentModel(new LessonModel());
        Double expectedScore = -1.0;
        Double actualScore = studentModel2.getStudent("ianspryn").getQuiz(1).getLastScore();

        Assert.assertEquals(expectedScore, actualScore);
        Files.deleteIfExists(file.toPath());
    }

    @Test
    public void getAverageQuizScoreXML() throws IOException {
        File file = new File("src/com/magis/app/resources/students.xml");
        Files.deleteIfExists(file.toPath());
        StudentModel studentModel = new StudentModel(new LessonModel());
        studentModel.addStudent("ianspryn","Ian","Spryn");
        studentModel.getStudent("ianspryn").getQuiz(1).addScore(42.0);
        studentModel.getStudent("ianspryn").getQuiz(1).addScore(85.5);
        studentModel.getStudent("ianspryn").getQuiz(1).addScore(92.5);
        studentModel.getStudent("ianspryn").getQuiz(1).addScore(98.5);

        StudentModel studentModel2 = new StudentModel(new LessonModel());
        Double expectedAverage = (42.0 + 85.5 + 92.5 + 98.5) / 4.0;
        Double actualAverage = studentModel2.getStudent("ianspryn").getQuiz(1).getAverageScore();

        Assert.assertEquals(expectedAverage, actualAverage);
        Files.deleteIfExists(file.toPath());
    }

    @Test
    public void getAverageQuizScoreWithOneScoreXML() throws IOException {
        File file = new File("src/com/magis/app/resources/students.xml");
        Files.deleteIfExists(file.toPath());
        StudentModel studentModel = new StudentModel(new LessonModel());
        studentModel.addStudent("ianspryn","Ian","Spryn");
        studentModel.getStudent("ianspryn").getQuiz(1).addScore(42.0);

        StudentModel studentModel2 = new StudentModel(new LessonModel());
        Double expectedAverage = 42.0;
        Double actualAverage = studentModel2.getStudent("ianspryn").getQuiz(1).getAverageScore();

        Assert.assertEquals(expectedAverage, actualAverage);
        Files.deleteIfExists(file.toPath());
    }

    @Test
    public void getAverageQuizScoreWithNoScoreXML() throws IOException {
        File file = new File("src/com/magis/app/resources/students.xml");
        Files.deleteIfExists(file.toPath());
        StudentModel studentModel = new StudentModel(new LessonModel());
        studentModel.addStudent("ianspryn","Ian","Spryn");

        StudentModel studentModel2 = new StudentModel(new LessonModel());
        Double expectedAverage = 0.0;
        Double actualAverage = studentModel2.getStudent("ianspryn").getQuiz(1).getAverageScore();

        Assert.assertEquals(expectedAverage, actualAverage);
        Files.deleteIfExists(file.toPath());
    }

    /*
    Exam Class
     */
    @Test
    public void getExamChapterNumberXML() throws IOException {
        File file = new File("src/com/magis/app/resources/students.xml");
        Files.deleteIfExists(file.toPath());
        StudentModel studentModel = new StudentModel(new LessonModel());
        studentModel.addStudent("ianspryn","Ian","Spryn");
        studentModel.getStudent("ianspryn").getExam(1).addScore(12.5);
        StudentModel studentModel2 = new StudentModel(new LessonModel());
        int expectedQuiz = 1;
        int actualQuiz = studentModel2.getStudent("ianspryn").getExam(expectedQuiz).getExamChapterNumber();

        Assert.assertEquals(expectedQuiz, actualQuiz);
        Files.deleteIfExists(file.toPath());
    }

    @Test
    public void getExamScoresXML() throws IOException {
        File file = new File("src/com/magis/app/resources/students.xml");
        Files.deleteIfExists(file.toPath());
        StudentModel studentModel = new StudentModel(new LessonModel());
        studentModel.addStudent("ianspryn","Ian","Spryn");
        studentModel.getStudent("ianspryn").getExam(1).addScore(50);
        studentModel.getStudent("ianspryn").getExam(1).addScore(92.5);

        ArrayList<Double> expectedScores = new ArrayList<>();
        expectedScores.add(50.0);
        expectedScores.add(92.5);

        StudentModel studentModel2 = new StudentModel(new LessonModel());
        ArrayList<Double> actualScores = studentModel2.getStudent("ianspryn").getExam(1).getScores();

        Assert.assertEquals(expectedScores, actualScores);
        Files.deleteIfExists(file.toPath());
    }

    @Test
    public void getLastExamScoreWithScoresXML() throws IOException {
        File file = new File("src/com/magis/app/resources/students.xml");
        Files.deleteIfExists(file.toPath());
        StudentModel studentModel = new StudentModel(new LessonModel());
        studentModel.addStudent("ianspryn","Ian","Spryn");
        studentModel.getStudent("ianspryn").getExam(1).addScore(50);
        studentModel.getStudent("ianspryn").getExam(1).addScore(92.5);

        StudentModel studentModel2 = new StudentModel(new LessonModel());
        Double expectedScore = 92.5;
        Double actualScore = studentModel2.getStudent("ianspryn").getExam(1).getLastScore();

        Assert.assertEquals(expectedScore, actualScore);
        Files.deleteIfExists(file.toPath());
    }

    @Test
    public void getLastExamScoreWithSingleScoreXML() throws IOException {
        File file = new File("src/com/magis/app/resources/students.xml");
        Files.deleteIfExists(file.toPath());
        StudentModel studentModel = new StudentModel(new LessonModel());
        studentModel.addStudent("ianspryn","Ian","Spryn");
        studentModel.getStudent("ianspryn").getExam(1).addScore(92.5);

        StudentModel studentModel2 = new StudentModel(new LessonModel());
        Double expectedScore = 92.5;
        Double actualScore = studentModel2.getStudent("ianspryn").getExam(1).getLastScore();

        Assert.assertEquals(expectedScore, actualScore);
        Files.deleteIfExists(file.toPath());
    }

    @Test
    public void getLastExamScoreWithNoScoreXML() throws IOException {
        File file = new File("src/com/magis/app/resources/students.xml");
        Files.deleteIfExists(file.toPath());
        StudentModel studentModel = new StudentModel(new LessonModel());
        studentModel.addStudent("ianspryn","Ian","Spryn");

        StudentModel studentModel2 = new StudentModel(new LessonModel());
        Double expectedScore = -1.0;
        Double actualScore = studentModel2.getStudent("ianspryn").getExam(1).getLastScore();

        Assert.assertEquals(expectedScore, actualScore);
        Files.deleteIfExists(file.toPath());
    }

    @Test
    public void getAverageExamScoreXML() throws IOException {
        File file = new File("src/com/magis/app/resources/students.xml");
        Files.deleteIfExists(file.toPath());
        StudentModel studentModel = new StudentModel(new LessonModel());
        studentModel.addStudent("ianspryn","Ian","Spryn");
        studentModel.getStudent("ianspryn").getExam(1).addScore(42.0);
        studentModel.getStudent("ianspryn").getExam(1).addScore(85.5);
        studentModel.getStudent("ianspryn").getExam(1).addScore(92.5);
        studentModel.getStudent("ianspryn").getExam(1).addScore(98.5);

        StudentModel studentModel2 = new StudentModel(new LessonModel());
        Double expectedAverage = (42.0 + 85.5 + 92.5 + 98.5) / 4.0;
        Double actualAverage = studentModel2.getStudent("ianspryn").getExam(1).getAverageScore();

        Assert.assertEquals(expectedAverage, actualAverage);
        Files.deleteIfExists(file.toPath());
    }

    @Test
    public void getAverageExamScoreWithOneScoreXML() throws IOException {
        File file = new File("src/com/magis/app/resources/students.xml");
        Files.deleteIfExists(file.toPath());
        StudentModel studentModel = new StudentModel(new LessonModel());
        studentModel.addStudent("ianspryn","Ian","Spryn");
        studentModel.getStudent("ianspryn").getExam(1).addScore(42.0);

        StudentModel studentModel2 = new StudentModel(new LessonModel());
        Double expectedAverage = 42.0;
        Double actualAverage = studentModel2.getStudent("ianspryn").getExam(1).getAverageScore();

        Assert.assertEquals(expectedAverage, actualAverage);
        Files.deleteIfExists(file.toPath());
    }

    @Test
    public void getAverageExamScoreWithNoScoreXML() throws IOException {
        File file = new File("src/com/magis/app/resources/students.xml");
        Files.deleteIfExists(file.toPath());
        StudentModel studentModel = new StudentModel(new LessonModel());
        studentModel.addStudent("ianspryn","Ian","Spryn");

        StudentModel studentModel2 = new StudentModel(new LessonModel());
        Double expectedAverage = 0.0;
        Double actualAverage = studentModel2.getStudent("ianspryn").getExam(1).getAverageScore();

        Assert.assertEquals(expectedAverage, actualAverage);
        Files.deleteIfExists(file.toPath());
    }
}