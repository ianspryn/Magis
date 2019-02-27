import java.util.Random;
import java.util.ArrayList;

public class QuestionGenerator {

    Random rand;

    private String[] commentQuestions = {
            "This is a comment",
            "This is\nA comment",
            "Author: Student\nClass: COMP 101\nDescription: ---",
            "This code will do [x]",
            "This code will do [x]\nIt will also do [y]"
    };
    private String[] generalCommentAnswers = {"Single-Line Comment", "Multi-Line Comment", "Java-Doc Comment", "Unknown"};

    private String[] comparableStrings = {"Dog", "Cat", "Apple", "Pineapple", "Squid", "Bean",
            "Squirrel", "Chicken", "Zoo", "Dark", "Cute", "Ape", "Burger", "Pittsburgh", "Pennsylvania",
            "Valentine", "Programming", "Computer"};
    private String[] comparableStringAnswers = {"Positive", "Negative", "Zero", "Unknown"};

    private char[] characters = {'+','-','*','/','%','<','=','>'};
    private String correctAnswer;
    private String question = "";

    public QuestionGenerator(){
        rand = new Random();
    }

    public void generateGeneralCommentQuestion(){
        int random;

        do {
            random = rand.nextInt(5);
            question = commentQuestions[random];
            commentQuestions[random] = "";
        }while(question.equals(""));

        question = question+"\n\nWhat would you use to comment the sentence above?";

        switch(random){
            case 0: correctAnswer = generalCommentAnswers[0];
                    break;
            case 1: correctAnswer = generalCommentAnswers[1];
                    break;
            case 2: correctAnswer = generalCommentAnswers[2];
                    break;
            case 3: correctAnswer = generalCommentAnswers[0];
                    break;
            case 4: correctAnswer = generalCommentAnswers[1];
                    break;
        }
    }

    public void generateComparableQuestion(){
        int random = rand.nextInt(comparableStrings.length);
        String firstPart = comparableStrings[random];
        random = rand.nextInt(comparableStrings.length);
        String secondPart = comparableStrings[random];

        question = firstPart+".compareTo("+secondPart+");\nWhat would be the value of the number returned from this compareTo?";

        if(firstPart.compareTo(secondPart)>0){
            correctAnswer = comparableStringAnswers[0];
        }
        else if(firstPart.compareTo(secondPart)<0){
            correctAnswer = comparableStringAnswers[1];
        }
        else{
            correctAnswer = comparableStringAnswers[2];
        }
    }

    public String[] getCommentAnswers(){ return generalCommentAnswers; }

    public String[] getComparableStringAnswers() { return comparableStringAnswers; }

    public String getQuestion(){
        return question;
    }

    public String getCorrectAnswer(){
        return correctAnswer;
    }
}
