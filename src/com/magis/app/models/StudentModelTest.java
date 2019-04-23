package com.magis.app.models;

import com.magis.app.login.Password;
import org.junit.Assert;
import org.junit.Test;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.util.ArrayList;

public class StudentModelTest {
    
    public File getFile() {
        File jarFile = null;
        try {
            jarFile = new File(StudentModel.class.getProtectionDomain().getCodeSource().getLocation().toURI().getPath());
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        assert jarFile != null;
        String filePath = jarFile.getParent() + File.separator + "students.xml";
        return new File(filePath);
    }

    /*
    File
     */
    @Test
    public void canDeleteFile() throws IOException {
        new StudentModel(new LessonModel());
        File file = getFile();
        boolean result = Files.deleteIfExists(file.toPath());
        System.out.println(file.toPath());
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
        File file = getFile();
        Files.deleteIfExists(file.toPath());
        StudentModel studentModel = new StudentModel(new LessonModel());
        int result = studentModel.addStudent("ianspryn", "Ian", "Spryn", "null", "null");
        studentModel.initializeStudent("ianspryn");
        Assert.assertEquals(0, result);
        Files.deleteIfExists(file.toPath());
    }

    @Test
    public void addDuplicateStudent() throws IOException {
        File file = getFile();
        Files.deleteIfExists(file.toPath());
        StudentModel studentModel = new StudentModel(new LessonModel());
        studentModel.addStudent("ianspryn", "Ian", "Spryn", "null", "null");
        studentModel.initializeStudent("ianspryn");
        int result = studentModel.addStudent("ianspryn", "Ian", "Spryn", "null", "null");

        Assert.assertEquals(-1, result);
        Files.deleteIfExists(file.toPath());
    }

    @Test
    public void getStudentFirstName() throws IOException {
        File file = getFile();
        Files.deleteIfExists(file.toPath());
        StudentModel studentModel = new StudentModel(new LessonModel());
        studentModel.addStudent("ianspryn", "Ian", "Spryn", "null", "null");
        studentModel.initializeStudent("ianspryn");
        String firstName = studentModel.getStudent().getFirstName();

        Assert.assertEquals("Ian",firstName);
        Files.deleteIfExists(file.toPath());
    }

    @Test
    public void getStudentLastName() throws IOException {
        File file = getFile();
        Files.deleteIfExists(file.toPath());
        StudentModel studentModel = new StudentModel(new LessonModel());
        studentModel.addStudent("ianspryn", "Ian", "Spryn", "null", "null");
        studentModel.initializeStudent("ianspryn");
        String lastName = studentModel.getStudent().getLastName();

        Assert.assertEquals("Spryn",lastName);
        Files.deleteIfExists(file.toPath());
    }

    @Test
    public void getStudentFullName() throws IOException {
        File file = getFile();
        Files.deleteIfExists(file.toPath());
        StudentModel studentModel = new StudentModel(new LessonModel());
        studentModel.addStudent("ianspryn", "Ian", "Spryn", "null", "null");
        studentModel.initializeStudent("ianspryn");
        String fullName = studentModel.getStudent().getFullName();

        Assert.assertEquals("Ian Spryn",fullName);
        Files.deleteIfExists(file.toPath());
    }

    @Test
    public void getPassword() throws IOException {
        File file = getFile();
        Files.deleteIfExists(file.toPath());
        StudentModel studentModel = new StudentModel(new LessonModel());
        String password = "password123";
        String salt = Password.generateSalt();
        password = Password.hash(password, salt);
        studentModel.addStudent("ianspryn", "Ian", "Spryn", password, salt);
        studentModel.initializeStudent("ianspryn");
        String expectedPassword = studentModel.getStudent().getPasswordHash();

        Assert.assertEquals(password, expectedPassword);
        Files.deleteIfExists(file.toPath());
    }

    @Test
    public void getSalt() throws IOException {
        File file = getFile();
        Files.deleteIfExists(file.toPath());
        StudentModel studentModel = new StudentModel(new LessonModel());
        String password = "password123";
        String salt = Password.generateSalt();
        password = Password.hash(password, salt);
        studentModel.addStudent("ianspryn", "Ian", "Spryn", password, salt);
        studentModel.initializeStudent("ianspryn");
        String expectedSalt = studentModel.getStudent().getSalt();

        Assert.assertEquals(salt, expectedSalt);
        Files.deleteIfExists(file.toPath());
    }

    @Test
    public void matchPassword() throws IOException {
        File file = getFile();
        Files.deleteIfExists(file.toPath());
        StudentModel studentModel = new StudentModel(new LessonModel());
        String password = "password123";
        String salt = Password.generateSalt();
        password = Password.hash(password, salt);
        studentModel.addStudent("ianspryn", "Ian", "Spryn", password, salt);
        studentModel.initializeStudent("ianspryn");


        String expectedSalt = studentModel.getStudent().getSalt();

        Assert.assertEquals(salt, expectedSalt);
        Files.deleteIfExists(file.toPath());
    }

    /*
    Settings
     */
    @Test
    public void getDarkMode() throws IOException {
        File file = getFile();
        Files.deleteIfExists(file.toPath());
        StudentModel studentModel = new StudentModel(new LessonModel());
        studentModel.addStudent("ianspryn", "Ian", "Spryn", "null", "null");
        studentModel.initializeStudent("ianspryn");
        boolean isDarkMode = studentModel.getStudent().getDarkMode();

        Assert.assertFalse(isDarkMode);
        Files.deleteIfExists(file.toPath());
    }

    @Test
    public void setAndGetDarkMode() throws IOException {
        File file = getFile();
        Files.deleteIfExists(file.toPath());
        StudentModel studentModel = new StudentModel(new LessonModel());
        studentModel.addStudent("ianspryn", "Ian", "Spryn", "null", "null");
        studentModel.initializeStudent("ianspryn");
        studentModel.getStudent().setDarkMode(true);

        StudentModel studentModel2 = new StudentModel(new LessonModel());
        studentModel2.addStudent("ianspryn", "Ian", "Spryn", "null", "null");
        studentModel2.initializeStudent("ianspryn");
        boolean isDarkMode = studentModel2.getStudent().getDarkMode();

        Assert.assertTrue(isDarkMode);
        Files.deleteIfExists(file.toPath());
    }

    @Test
    public void getTheme() throws IOException {
        File file = getFile();
        Files.deleteIfExists(file.toPath());
        StudentModel studentModel = new StudentModel(new LessonModel());
        studentModel.addStudent("ianspryn", "Ian", "Spryn", "null", "null");
        studentModel.initializeStudent("ianspryn");
        String theme = studentModel.getStudent().getTheme();

        Assert.assertEquals("pink", theme);
        Files.deleteIfExists(file.toPath());
    }

    @Test
    public void getAnimations() throws IOException {
        File file = getFile();
        Files.deleteIfExists(file.toPath());
        StudentModel studentModel = new StudentModel(new LessonModel());
        studentModel.addStudent("ianspryn", "Ian", "Spryn", "null", "null");
        studentModel.initializeStudent("ianspryn");
        boolean useAnimations = studentModel.getStudent().useAnimations();

        Assert.assertTrue(useAnimations);
        Files.deleteIfExists(file.toPath());
    }

    @Test
    public void setAndGetAnimations() throws IOException {
        File file = getFile();
        Files.deleteIfExists(file.toPath());
        StudentModel studentModel = new StudentModel(new LessonModel());
        studentModel.addStudent("ianspryn", "Ian", "Spryn", "null", "null");
        studentModel.initializeStudent("ianspryn");
        studentModel.getStudent().setAnimations(false);

        StudentModel studentModel2 = new StudentModel(new LessonModel());
        studentModel2.addStudent("ianspryn", "Ian", "Spryn", "null", "null");
        studentModel2.initializeStudent("ianspryn");
        boolean useAnimations = studentModel2.getStudent().useAnimations();

        Assert.assertFalse(useAnimations);
        Files.deleteIfExists(file.toPath());
    }

    @Test
    public void setAndGetTheme() throws IOException {
        File file = getFile();
        Files.deleteIfExists(file.toPath());
        StudentModel studentModel = new StudentModel(new LessonModel());
        studentModel.addStudent("ianspryn", "Ian", "Spryn", "null", "null");
        studentModel.initializeStudent("ianspryn");
        studentModel.getStudent().setTheme("purple");

        StudentModel studentModel2 = new StudentModel(new LessonModel());
        studentModel2.addStudent("ianspryn", "Ian", "Spryn", "null", "null");
        studentModel2.initializeStudent("ianspryn");
        String theme = studentModel2.getStudent().getTheme();

        Assert.assertEquals("purple", theme);
        Files.deleteIfExists(file.toPath());
    }

    /*
    Chapter Class
     */
    @Test
    public void getChapterProgress() throws IOException {
        File file = getFile();
        Files.deleteIfExists(file.toPath());
        StudentModel studentModel = new StudentModel(new LessonModel());
        studentModel.addStudent("ianspryn", "Ian", "Spryn", "null", "null");
        studentModel.initializeStudent("ianspryn");
        studentModel.getStudent().getChapter(1).visitPage(0);
        int progress = studentModel.getStudent().getChapter(1).getProgress();

        Assert.assertEquals(12, progress);
        Files.deleteIfExists(file.toPath());
    }

    /*
    Quiz Class
     */

    @Test
    public void getQuizScores() throws IOException {
        File file = getFile();
        Files.deleteIfExists(file.toPath());
        StudentModel studentModel = new StudentModel(new LessonModel());
        studentModel.addStudent("ianspryn", "Ian", "Spryn", "null", "null");
        studentModel.initializeStudent("ianspryn");
        studentModel.getStudent().getQuiz(1).addScore(50);
        studentModel.getStudent().getQuiz(1).addScore(92.5);

        ArrayList<Double> expectedScores = new ArrayList<>();
        expectedScores.add(50.0);
        expectedScores.add(92.5);

        ArrayList<Double> actualScores = studentModel.getStudent().getQuiz(1).getScores();

        Assert.assertEquals(expectedScores, actualScores);
        Files.deleteIfExists(file.toPath());
    }

    @Test
    public void getLastQuizScoreWithScores() throws IOException {
        File file = getFile();
        Files.deleteIfExists(file.toPath());
        StudentModel studentModel = new StudentModel(new LessonModel());
        studentModel.addStudent("ianspryn", "Ian", "Spryn", "null", "null");
        studentModel.initializeStudent("ianspryn");
        studentModel.getStudent().getQuiz(1).addScore(50);
        studentModel.getStudent().getQuiz(1).addScore(92.5);

        Double expectedScore = 92.5;
        Double actualScore = studentModel.getStudent().getQuiz(1).getLastScore();

        Assert.assertEquals(expectedScore, actualScore);
        Files.deleteIfExists(file.toPath());
    }

    @Test
    public void getLastQuizScoreWithSingleScore() throws IOException {
        File file = getFile();
        Files.deleteIfExists(file.toPath());
        StudentModel studentModel = new StudentModel(new LessonModel());
        studentModel.addStudent("ianspryn", "Ian", "Spryn", "null", "null");
        studentModel.initializeStudent("ianspryn");
        studentModel.getStudent().getQuiz(1).addScore(92.5);

        Double expectedScore = 92.5;
        Double actualScore = studentModel.getStudent().getQuiz(1).getLastScore();

        Assert.assertEquals(expectedScore, actualScore);
        Files.deleteIfExists(file.toPath());
    }

    @Test
    public void getLastQuizScoreWithNoScore() throws IOException {
        File file = getFile();
        Files.deleteIfExists(file.toPath());
        StudentModel studentModel = new StudentModel(new LessonModel());
        studentModel.addStudent("ianspryn", "Ian", "Spryn", "null", "null");
        studentModel.initializeStudent("ianspryn");

        Double expectedScore = -1.0;
        Double actualScore = studentModel.getStudent().getQuiz(1).getLastScore();

        Assert.assertEquals(expectedScore, actualScore);
        Files.deleteIfExists(file.toPath());
    }

    @Test
    public void getAverageQuizScore() throws IOException {
        File file = getFile();
        Files.deleteIfExists(file.toPath());
        StudentModel studentModel = new StudentModel(new LessonModel());
        studentModel.addStudent("ianspryn", "Ian", "Spryn", "null", "null");
        studentModel.initializeStudent("ianspryn");
        studentModel.getStudent().getQuiz(1).addScore(42.0);
        studentModel.getStudent().getQuiz(1).addScore(85.5);
        studentModel.getStudent().getQuiz(1).addScore(92.5);
        studentModel.getStudent().getQuiz(1).addScore(98.5);

        Double expectedAverage = (42.0 + 85.5 + 92.5 + 98.5) / 4.0;
        Double actualAverage = studentModel.getStudent().getQuiz(1).getAverageScore();

        Assert.assertEquals(expectedAverage, actualAverage);
        Files.deleteIfExists(file.toPath());
    }

    @Test
    public void getAverageQuizScoreWithOneScore() throws IOException {
        File file = getFile();
        Files.deleteIfExists(file.toPath());
        StudentModel studentModel = new StudentModel(new LessonModel());
        studentModel.addStudent("ianspryn", "Ian", "Spryn", "null", "null");
        studentModel.initializeStudent("ianspryn");
        studentModel.getStudent().getQuiz(1).addScore(42.0);

        Double expectedAverage = 42.0;
        Double actualAverage = studentModel.getStudent().getQuiz(1).getAverageScore();

        Assert.assertEquals(expectedAverage, actualAverage);
        Files.deleteIfExists(file.toPath());
    }

    @Test
    public void getAverageQuizScoreWithNoScore() throws IOException {
        File file = getFile();
        Files.deleteIfExists(file.toPath());
        StudentModel studentModel = new StudentModel(new LessonModel());
        studentModel.addStudent("ianspryn", "Ian", "Spryn", "null", "null");
        studentModel.initializeStudent("ianspryn");

        Double expectedAverage = 0.0;
        Double actualAverage = studentModel.getStudent().getQuiz(1).getAverageScore();

        Assert.assertEquals(expectedAverage, actualAverage);
        Files.deleteIfExists(file.toPath());
    }

    /*
    Exam Class
     */

    @Test
    public void getTestScores() throws IOException {
        File file = getFile();
        Files.deleteIfExists(file.toPath());
        StudentModel studentModel = new StudentModel(new LessonModel());
        studentModel.addStudent("ianspryn", "Ian", "Spryn", "null", "null");
        studentModel.initializeStudent("ianspryn");
        studentModel.getStudent().getTest(1).addScore(50);
        studentModel.getStudent().getTest(1).addScore(92.5);

        ArrayList<Double> expectedScores = new ArrayList<>();
        expectedScores.add(50.0);
        expectedScores.add(92.5);

        ArrayList<Double> actualScores = studentModel.getStudent().getTest(1).getScores();

        Assert.assertEquals(expectedScores, actualScores);
        Files.deleteIfExists(file.toPath());
    }

    @Test
    public void getLastExamScoreWithScores() throws IOException {
        File file = getFile();
        Files.deleteIfExists(file.toPath());
        StudentModel studentModel = new StudentModel(new LessonModel());
        studentModel.addStudent("ianspryn", "Ian", "Spryn", "null", "null");
        studentModel.initializeStudent("ianspryn");
        studentModel.getStudent().getTest(1).addScore(50);
        studentModel.getStudent().getTest(1).addScore(92.5);

        Double expectedScore = 92.5;
        Double actualScore = studentModel.getStudent().getTest(1).getLastScore();

        Assert.assertEquals(expectedScore, actualScore);
        Files.deleteIfExists(file.toPath());
    }

    @Test
    public void getLastExamScoreWithSingleScore() throws IOException {
        File file = getFile();
        Files.deleteIfExists(file.toPath());
        StudentModel studentModel = new StudentModel(new LessonModel());
        studentModel.addStudent("ianspryn", "Ian", "Spryn", "null", "null");
        studentModel.initializeStudent("ianspryn");
        studentModel.getStudent().getTest(1).addScore(92.5);

        Double expectedScore = 92.5;
        Double actualScore = studentModel.getStudent().getTest(1).getLastScore();

        Assert.assertEquals(expectedScore, actualScore);
        Files.deleteIfExists(file.toPath());
    }

    @Test
    public void getLastExamScoreWithNoScore() throws IOException {
        File file = getFile();
        Files.deleteIfExists(file.toPath());
        StudentModel studentModel = new StudentModel(new LessonModel());
        studentModel.addStudent("ianspryn", "Ian", "Spryn", "null", "null");
        studentModel.initializeStudent("ianspryn");

        Double expectedScore = -1.0;
        Double actualScore = studentModel.getStudent().getTest(1).getLastScore();

        Assert.assertEquals(expectedScore, actualScore);
        Files.deleteIfExists(file.toPath());
    }

    @Test
    public void getAverageExamScore() throws IOException {
        File file = getFile();
        Files.deleteIfExists(file.toPath());
        StudentModel studentModel = new StudentModel(new LessonModel());
        studentModel.addStudent("ianspryn", "Ian", "Spryn", "null", "null");
        studentModel.initializeStudent("ianspryn");
        studentModel.getStudent().getTest(1).addScore(42.0);
        studentModel.getStudent().getTest(1).addScore(85.5);
        studentModel.getStudent().getTest(1).addScore(92.5);
        studentModel.getStudent().getTest(1).addScore(98.5);

        Double expectedAverage = (42.0 + 85.5 + 92.5 + 98.5) / 4.0;
        Double actualAverage = studentModel.getStudent().getTest(1).getAverageScore();

        Assert.assertEquals(expectedAverage, actualAverage);
        Files.deleteIfExists(file.toPath());
    }

    @Test
    public void getAverageExamScoreWithOneScore() throws IOException {
        File file = getFile();
        Files.deleteIfExists(file.toPath());
        StudentModel studentModel = new StudentModel(new LessonModel());
        studentModel.addStudent("ianspryn", "Ian", "Spryn", "null", "null");
        studentModel.initializeStudent("ianspryn");
        studentModel.getStudent().getTest(1).addScore(42.0);

        Double expectedAverage = 42.0;
        Double actualAverage = studentModel.getStudent().getTest(1).getAverageScore();

        Assert.assertEquals(expectedAverage, actualAverage);
        Files.deleteIfExists(file.toPath());
    }

    @Test
    public void getAverageExamScoreWithNoScore() throws IOException {
        File file = getFile();
        Files.deleteIfExists(file.toPath());
        StudentModel studentModel = new StudentModel(new LessonModel());
        studentModel.addStudent("ianspryn", "Ian", "Spryn", "null", "null");
        studentModel.initializeStudent("ianspryn");

        Double expectedAverage = 0.0;
        Double actualAverage = studentModel.getStudent().getTest(1).getAverageScore();

        Assert.assertEquals(expectedAverage, actualAverage);
        Files.deleteIfExists(file.toPath());
    }

    @Test
    public void deleteUser() throws IOException {
        File file = getFile();
        Files.deleteIfExists(file.toPath());
        StudentModel studentModel = new StudentModel(new LessonModel());
        studentModel.addStudent("ianspryn", "Ian", "Spryn", "null", "null");
        studentModel.initializeStudent("ianspryn");

        studentModel.deleteUser("ianspryn");

        StudentModel.Student student = new StudentModel(new LessonModel()).getStudent();

        Assert.assertNull(student);
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
        File file = getFile();
        Files.deleteIfExists(file.toPath());
        StudentModel studentModel = new StudentModel(new LessonModel());
        studentModel.addStudent("ianspryn", "Ian", "Spryn", "null", "null");
        studentModel.initializeStudent("ianspryn");

        StudentModel studentModel2 = new StudentModel(new LessonModel());
        studentModel2.initializeStudent("ianspryn");
        String firstName =  studentModel2.getStudent().getFirstName();

        Assert.assertEquals("Ian",firstName);
        Files.deleteIfExists(file.toPath());
    }

    @Test
    public void getStudentLastNameXML() throws IOException {
        File file = getFile();
        Files.deleteIfExists(file.toPath());
        StudentModel studentModel = new StudentModel(new LessonModel());
        studentModel.addStudent("ianspryn", "Ian", "Spryn", "null", "null");
        studentModel.initializeStudent("ianspryn");

        StudentModel studentModel2 = new StudentModel(new LessonModel());
        studentModel2.initializeStudent("ianspryn");
        String lastName = studentModel2.getStudent().getLastName();

        Assert.assertEquals("Spryn",lastName);
        Files.deleteIfExists(file.toPath());
    }

    @Test
    public void getStudentFullNameXML() throws IOException {
        File file = getFile();
        Files.deleteIfExists(file.toPath());
        StudentModel studentModel = new StudentModel(new LessonModel());
        studentModel.addStudent("ianspryn", "Ian", "Spryn", "null", "null");
        studentModel.initializeStudent("ianspryn");

        StudentModel studentModel2 = new StudentModel(new LessonModel());
        studentModel2.initializeStudent("ianspryn");
        String fullName = studentModel2.getStudent().getFullName();

        Assert.assertEquals("Ian Spryn",fullName);
        Files.deleteIfExists(file.toPath());
    }

    /*
    Settings
     */
    @Test
    public void setAndGetDarkModeXML() throws IOException {
        File file = getFile();
        Files.deleteIfExists(file.toPath());
        StudentModel studentModel = new StudentModel(new LessonModel());
        studentModel.addStudent("ianspryn", "Ian", "Spryn", "null", "null");
        studentModel.initializeStudent("ianspryn");
        studentModel.getStudent().setDarkMode(true);
        boolean isDarkMode = studentModel.getStudent().getDarkMode();

        Assert.assertTrue(isDarkMode);
        Files.deleteIfExists(file.toPath());
    }

    @Test
    public void setAndGetThemeXML() throws IOException {
        File file = getFile();
        Files.deleteIfExists(file.toPath());
        StudentModel studentModel = new StudentModel(new LessonModel());
        studentModel.addStudent("ianspryn", "Ian", "Spryn", "null", "null");
        studentModel.initializeStudent("ianspryn");
        studentModel.getStudent().setTheme("purple");
        String theme = studentModel.getStudent().getTheme();

        Assert.assertEquals("purple", theme);
        Files.deleteIfExists(file.toPath());
    }

    @Test
    public void setAndGetAnimationsXML() throws IOException {
        File file = getFile();
        Files.deleteIfExists(file.toPath());
        StudentModel studentModel = new StudentModel(new LessonModel());
        studentModel.addStudent("ianspryn", "Ian", "Spryn", "null", "null");
        studentModel.initializeStudent("ianspryn");
        studentModel.getStudent().setAnimations(false);
        boolean useAnimations = studentModel.getStudent().useAnimations();

        Assert.assertFalse(useAnimations);
        Files.deleteIfExists(file.toPath());
    }

    /*
    Chapter Class
     */
    @Test
    public void getChapterProgressXML() throws IOException {
        File file = getFile();
        Files.deleteIfExists(file.toPath());
        StudentModel studentModel = new StudentModel(new LessonModel());
        studentModel.addStudent("ianspryn", "Ian", "Spryn", "null", "null");
        studentModel.initializeStudent("ianspryn");
        studentModel.getStudent().getChapter(1).visitPage(0);
        studentModel.getStudent().writePageProgress();

        StudentModel studentModel2 = new StudentModel(new LessonModel());
        studentModel2.initializeStudent("ianspryn");
        int progress = studentModel2.getStudent().getChapter(1).getProgress();

        Assert.assertEquals(12, progress);
        Files.deleteIfExists(file.toPath());
    }

    /*
    Quiz Class
     */

    @Test
    public void getQuizScoresXML() throws IOException {
        File file = getFile();
        Files.deleteIfExists(file.toPath());
        StudentModel studentModel = new StudentModel(new LessonModel());
        studentModel.addStudent("ianspryn", "Ian", "Spryn", "null", "null");
        studentModel.initializeStudent("ianspryn");
        studentModel.getStudent().getQuiz(1).addScore(50);
        studentModel.getStudent().getQuiz(1).addScore(92.5);

        ArrayList<Double> expectedScores = new ArrayList<>();
        expectedScores.add(50.0);
        expectedScores.add(92.5);

        StudentModel studentModel2 = new StudentModel(new LessonModel());
        studentModel2.initializeStudent("ianspryn");
        ArrayList<Double> actualScores = studentModel2.getStudent().getQuiz(1).getScores();

        Assert.assertEquals(expectedScores, actualScores);
        Files.deleteIfExists(file.toPath());
    }

    @Test
    public void getLastQuizScoreWithScoresXML() throws IOException {
        File file = getFile();
        Files.deleteIfExists(file.toPath());
        StudentModel studentModel = new StudentModel(new LessonModel());
        studentModel.addStudent("ianspryn", "Ian", "Spryn", "null", "null");
        studentModel.initializeStudent("ianspryn");
        studentModel.getStudent().getQuiz(1).addScore(50);
        studentModel.getStudent().getQuiz(1).addScore(92.5);

        StudentModel studentModel2 = new StudentModel(new LessonModel());
        studentModel2.initializeStudent("ianspryn");
        Double expectedScore = 92.5;
        Double actualScore = studentModel2.getStudent().getQuiz(1).getLastScore();

        Assert.assertEquals(expectedScore, actualScore);
        Files.deleteIfExists(file.toPath());
    }

    @Test
    public void getLastQuizScoreWithSingleScoreXML() throws IOException {
        File file = getFile();
        Files.deleteIfExists(file.toPath());
        StudentModel studentModel = new StudentModel(new LessonModel());
        studentModel.addStudent("ianspryn", "Ian", "Spryn", "null", "null");
        studentModel.initializeStudent("ianspryn");
        studentModel.getStudent().getQuiz(1).addScore(92.5);

        StudentModel studentModel2 = new StudentModel(new LessonModel());
        studentModel2.initializeStudent("ianspryn");
        Double expectedScore = 92.5;
        Double actualScore = studentModel2.getStudent().getQuiz(1).getLastScore();

        Assert.assertEquals(expectedScore, actualScore);
        Files.deleteIfExists(file.toPath());
    }

    @Test
    public void getLastQuizScoreWithNoScoreXML() throws IOException {
        File file = getFile();
        Files.deleteIfExists(file.toPath());
        StudentModel studentModel = new StudentModel(new LessonModel());
        studentModel.addStudent("ianspryn", "Ian", "Spryn", "null", "null");
        studentModel.initializeStudent("ianspryn");

        StudentModel studentModel2 = new StudentModel(new LessonModel());
        studentModel2.initializeStudent("ianspryn");
        Double expectedScore = -1.0;
        Double actualScore = studentModel2.getStudent().getQuiz(1).getLastScore();

        Assert.assertEquals(expectedScore, actualScore);
        Files.deleteIfExists(file.toPath());
    }

    @Test
    public void getAverageQuizScoreXML() throws IOException {
        File file = getFile();
        Files.deleteIfExists(file.toPath());
        StudentModel studentModel = new StudentModel(new LessonModel());
        studentModel.addStudent("ianspryn", "Ian", "Spryn", "null", "null");
        studentModel.initializeStudent("ianspryn");
        studentModel.getStudent().getQuiz(1).addScore(42.0);
        studentModel.getStudent().getQuiz(1).addScore(85.5);
        studentModel.getStudent().getQuiz(1).addScore(92.5);
        studentModel.getStudent().getQuiz(1).addScore(98.5);

        StudentModel studentModel2 = new StudentModel(new LessonModel());
        studentModel2.initializeStudent("ianspryn");
        Double expectedAverage = (42.0 + 85.5 + 92.5 + 98.5) / 4.0;
        Double actualAverage = studentModel2.getStudent().getQuiz(1).getAverageScore();

        Assert.assertEquals(expectedAverage, actualAverage);
        Files.deleteIfExists(file.toPath());
    }

    @Test
    public void getAverageQuizScoreWithOneScoreXML() throws IOException {
        File file = getFile();
        Files.deleteIfExists(file.toPath());
        StudentModel studentModel = new StudentModel(new LessonModel());
        studentModel.addStudent("ianspryn", "Ian", "Spryn", "null", "null");
        studentModel.initializeStudent("ianspryn");
        studentModel.getStudent().getQuiz(1).addScore(42.0);

        StudentModel studentModel2 = new StudentModel(new LessonModel());
        studentModel2.initializeStudent("ianspryn");
        Double expectedAverage = 42.0;
        Double actualAverage = studentModel2.getStudent().getQuiz(1).getAverageScore();

        Assert.assertEquals(expectedAverage, actualAverage);
        Files.deleteIfExists(file.toPath());
    }

    @Test
    public void getAverageQuizScoreWithNoScoreXML() throws IOException {
        File file = getFile();
        Files.deleteIfExists(file.toPath());
        StudentModel studentModel = new StudentModel(new LessonModel());
        studentModel.addStudent("ianspryn", "Ian", "Spryn", "null", "null");
        studentModel.initializeStudent("ianspryn");

        StudentModel studentModel2 = new StudentModel(new LessonModel());
        studentModel2.initializeStudent("ianspryn");
        Double expectedAverage = 0.0;
        Double actualAverage = studentModel2.getStudent().getQuiz(1).getAverageScore();

        Assert.assertEquals(expectedAverage, actualAverage);
        Files.deleteIfExists(file.toPath());
    }

    /*
    Exam Class
     */

    @Test
    public void getTestScoresXML() throws IOException {
        File file = getFile();
        Files.deleteIfExists(file.toPath());
        StudentModel studentModel = new StudentModel(new LessonModel());
        studentModel.addStudent("ianspryn", "Ian", "Spryn", "null", "null");
        studentModel.initializeStudent("ianspryn");
        studentModel.getStudent().getTest(1).addScore(50);
        studentModel.getStudent().getTest(1).addScore(92.5);

        ArrayList<Double> expectedScores = new ArrayList<>();
        expectedScores.add(50.0);
        expectedScores.add(92.5);

        StudentModel studentModel2 = new StudentModel(new LessonModel());
        studentModel2.initializeStudent("ianspryn");
        ArrayList<Double> actualScores = studentModel2.getStudent().getTest(1).getScores();

        Assert.assertEquals(expectedScores, actualScores);
        Files.deleteIfExists(file.toPath());
    }

    @Test
    public void getLastExamScoreWithScoresXML() throws IOException {
        File file = getFile();
        Files.deleteIfExists(file.toPath());
        StudentModel studentModel = new StudentModel(new LessonModel());
        studentModel.addStudent("ianspryn", "Ian", "Spryn", "null", "null");
        studentModel.initializeStudent("ianspryn");
        studentModel.getStudent().getTest(1).addScore(50);
        studentModel.getStudent().getTest(1).addScore(92.5);

        StudentModel studentModel2 = new StudentModel(new LessonModel());
        studentModel2.initializeStudent("ianspryn");
        Double expectedScore = 92.5;
        Double actualScore = studentModel2.getStudent().getTest(1).getLastScore();

        Assert.assertEquals(expectedScore, actualScore);
        Files.deleteIfExists(file.toPath());
    }

    @Test
    public void getLastExamScoreWithSingleScoreXML() throws IOException {
        File file = getFile();
        Files.deleteIfExists(file.toPath());
        StudentModel studentModel = new StudentModel(new LessonModel());
        studentModel.addStudent("ianspryn", "Ian", "Spryn", "null", "null");
        studentModel.initializeStudent("ianspryn");
        studentModel.getStudent().getTest(1).addScore(92.5);

        StudentModel studentModel2 = new StudentModel(new LessonModel());
        studentModel2.initializeStudent("ianspryn");
        Double expectedScore = 92.5;
        Double actualScore = studentModel2.getStudent().getTest(1).getLastScore();

        Assert.assertEquals(expectedScore, actualScore);
        Files.deleteIfExists(file.toPath());
    }

    @Test
    public void getLastExamScoreWithNoScoreXML() throws IOException {
        File file = getFile();
        Files.deleteIfExists(file.toPath());
        StudentModel studentModel = new StudentModel(new LessonModel());
        studentModel.addStudent("ianspryn", "Ian", "Spryn", "null", "null");
        studentModel.initializeStudent("ianspryn");

        StudentModel studentModel2 = new StudentModel(new LessonModel());
        studentModel2.initializeStudent("ianspryn");
        Double expectedScore = -1.0;
        Double actualScore = studentModel2.getStudent().getTest(1).getLastScore();

        Assert.assertEquals(expectedScore, actualScore);
        Files.deleteIfExists(file.toPath());
    }

    @Test
    public void getAverageExamScoreXML() throws IOException {
        File file = getFile();
        Files.deleteIfExists(file.toPath());
        StudentModel studentModel = new StudentModel(new LessonModel());
        studentModel.addStudent("ianspryn", "Ian", "Spryn", "null", "null");
        studentModel.initializeStudent("ianspryn");
        studentModel.getStudent().getTest(1).addScore(42.0);
        studentModel.getStudent().getTest(1).addScore(85.5);
        studentModel.getStudent().getTest(1).addScore(92.5);
        studentModel.getStudent().getTest(1).addScore(98.5);

        StudentModel studentModel2 = new StudentModel(new LessonModel());
        studentModel2.initializeStudent("ianspryn");
        Double expectedAverage = (42.0 + 85.5 + 92.5 + 98.5) / 4.0;
        Double actualAverage = studentModel2.getStudent().getTest(1).getAverageScore();

        Assert.assertEquals(expectedAverage, actualAverage);
        Files.deleteIfExists(file.toPath());
    }

    @Test
    public void getAverageExamScoreWithOneScoreXML() throws IOException {
        File file = getFile();
        Files.deleteIfExists(file.toPath());
        StudentModel studentModel = new StudentModel(new LessonModel());
        studentModel.addStudent("ianspryn", "Ian", "Spryn", "null", "null");
        studentModel.initializeStudent("ianspryn");
        studentModel.getStudent().getTest(1).addScore(42.0);

        StudentModel studentModel2 = new StudentModel(new LessonModel());
        studentModel2.initializeStudent("ianspryn");
        Double expectedAverage = 42.0;
        Double actualAverage = studentModel2.getStudent().getTest(1).getAverageScore();

        Assert.assertEquals(expectedAverage, actualAverage);
        Files.deleteIfExists(file.toPath());
    }

    @Test
    public void getAverageExamScoreWithNoScoreXML() throws IOException {
        File file = getFile();
        Files.deleteIfExists(file.toPath());
        StudentModel studentModel = new StudentModel(new LessonModel());
        studentModel.addStudent("ianspryn", "Ian", "Spryn", "null", "null");
        studentModel.initializeStudent("ianspryn");

        StudentModel studentModel2 = new StudentModel(new LessonModel());
        studentModel2.initializeStudent("ianspryn");
        Double expectedAverage = 0.0;
        Double actualAverage = studentModel2.getStudent().getTest(1).getAverageScore();

        Assert.assertEquals(expectedAverage, actualAverage);
        Files.deleteIfExists(file.toPath());
    }
}