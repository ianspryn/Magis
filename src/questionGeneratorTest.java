import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.Scanner;

class questionGeneratorTest {

    @Test
    void testCommentQuestion(){
        commentQuestions cq = new commentQuestions();
        cq.generateGeneralCommentQuestion();
        String[] answers = cq.getCommentAnswers();
        String question = cq.getQuestion();
        String correctAnswer = cq.getCorrectAnswer();

        System.out.println(question+"\n");

        for(int i=0; i<answers.length; i++){
            System.out.println((i+1)+". "+answers[i]);
        }

        System.out.println("\nCorrect Answer: "+correctAnswer);
    }

    @Test
    void testDataTypeQuestion(){
        datatypeQuestions dtq = new datatypeQuestions();
        dtq.datatypeMatchingQuestion();
        String[] answers = dtq.getAnswers();
        String question = dtq.getQuestion();
        String correctAnswer = dtq.getCorrectAnswer();

        System.out.println(question+"\n");

        for(int i=0; i<answers.length; i++){
            System.out.println((i+1)+". "+answers[i]);
        }

        System.out.println("\nCorrect Answer: "+correctAnswer);
    }

    @Test
    void testObjectComparisonQuestion(){
        objectComparisonQuestions objc  = new objectComparisonQuestions();
        objc.generateEqualsQuestion();
        String[] answers = objc.getAnswers();
        String question = objc.getQuestion();
        String correctAnswer = objc.getCorrectAnswer();

        System.out.println(question+"\n");

        for(int i=0; i<answers.length; i++){
            System.out.println((i+1)+". "+answers[i]);
        }

        System.out.println("\nCorrect Answer: "+correctAnswer);
    }
}