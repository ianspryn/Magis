import java.util.Random;

public class objectComparisonQuestions {

    Random rand;

    private String[] comparableStrings = {"Dog", "Cat", "Apple", "Pineapple", "Squid", "Bean",
            "Squirrel", "Chicken", "Zoo", "Dark", "Cute", "Ape", "Burger", "Pittsburgh", "Pennsylvania",
            "Valentine", "Programming", "Computer"};
    private String[] comparableStringAnswers = {"Positive", "Negative", "Zero", "Unknown"};

    private char[] characters = {'+','-','*','/','%','<','=','>'};
    private String correctAnswer;
    private String question = "";

    public objectComparisonQuestions(){
        rand = new Random();
    }

    public void generateComparableQuestion(){
        int random = rand.nextInt(comparableStrings.length);
        String firstPart = comparableStrings[random];
        random = rand.nextInt(comparableStrings.length);
        String secondPart = comparableStrings[random];

        question = firstPart+".compareTo("+secondPart+");\n\nWhat would be the value of the number returned from this compareTo?";

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

    public String[] getComparableStringAnswers() { return comparableStringAnswers; }

    public String getQuestion(){
        return question;
    }

    public String getCorrectAnswer(){
        return correctAnswer;
    }
}
