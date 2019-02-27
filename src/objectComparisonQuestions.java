import java.util.Random;

public class objectComparisonQuestions {

    Random rand;

    private String[] comparableStrings = {"Dog", "Cat", "Apple", "Pineapple", "Squid", "Bean",
            "Squirrel", "Chicken", "Zoo", "Dark", "Cute", "Ape", "Burger", "Pittsburgh", "Pennsylvania",
            "Valentine", "Programming", "Computer"};
    private String[] comparableStringAnswers = {"Positive", "Negative", "Zero", "Unknown"};

    private String[] equalsStrings = {"Programming is fun!", "programming is Fun!",
            "programming is fun!", "PROGRAMMING IS FUN!"};
    private String[] equalsAnswers = {"True", "False", "Unknown"};

    private String[] answers;

    private char[] characters = {'+','-','*','/','%','<','=','>'};
    private String correctAnswer;
    private String question = "";

    public objectComparisonQuestions(){
        rand = new Random();
    }

    public void generateComparableQuestion(){
        answers = comparableStringAnswers;

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

    public void generateEqualsQuestion(){
        answers = equalsAnswers;

        int random = rand.nextInt(equalsStrings.length);
        String firstPart = equalsStrings[random];
        random = rand.nextInt(equalsStrings.length);
        String secondPart = equalsStrings[random];

        random = rand.nextInt(2);

        if(random == 0) {
            question = firstPart + ".equals" + secondPart + ");\n\nWill this statement return true or false?";
            if(firstPart.equals(secondPart)){
                correctAnswer = equalsAnswers[0];
            }
            else{
                correctAnswer = equalsAnswers[1];
            }
        }
        else if(random == 1){
            question = firstPart + "==" + secondPart + "\n\nWill this statement return true or false?";
            if(firstPart == secondPart){
                correctAnswer = equalsAnswers[0];
            }
            else{
                correctAnswer = equalsAnswers[1];
            }
        }
        else{
            question = firstPart + ".equalsIgnoreCase" + secondPart + ");\n\nWill this statement return true or false?";
            if(firstPart.equalsIgnoreCase(secondPart)){
                correctAnswer = equalsAnswers[0];
            }
            else{
                correctAnswer = equalsAnswers[1];
            }
        }
    }

    public String[] getAnswers() { return answers; }

    public String getQuestion(){
        return question;
    }

    public String getCorrectAnswer(){
        return correctAnswer;
    }
}
