package com.magis.app.test.questions.generator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class EscapeSequenceQuestions {
    private ArrayList<String> answers;
    private String correctAnswer;
    private String question = "";
    Random rand;

    private String[] sentences = {"The dog ^left the house", "Can you smell ^what the Rock is cooking?", "What is ^the sum of these two numbers?",
    "I ran over ^the very big hill", "Can ^I have ^the money ^please?", "I want ^to do well ^on this quiz"};

    public EscapeSequenceQuestions(){
        answers = new ArrayList<>();
        rand = new Random();
    }

    public void getEscapeSequenceQuestion(){
        answers.clear();
        question = "";

        int sentencePicker = rand.nextInt(sentences.length);
        question = sentences[sentencePicker];

        int sequenePicker = rand.nextInt(8);

        switch(sequenePicker){
            case 0:
                correctAnswer = question;
                correctAnswer.replace('^', '\n');
                answers.add(correctAnswer);
                answers.add(question.replace('^','\t'));
                answers.add(question.replace('^','\\'));
                answers.add(question.replace('^','\"'));
                question+="\n\nWhat will replacing the \"^\"(s) with a \"\\n\" do to the string?";
                break;
            case 1:
                correctAnswer = question;
                correctAnswer.replace('^', '\t');
                answers.add(correctAnswer);
                answers.add(question.replace('^','\n'));
                answers.add(question.replace('^','\\'));
                answers.add(question.replace('^','\"'));
                question+="\n\nWhat will replacing the \"^\"(s) with a \"\\t\" do to the string?";
                break;
            case 2:
                correctAnswer = question;
                correctAnswer.replace('^', '\\');
                answers.add(correctAnswer);
                answers.add(question.replace('^','\t'));
                answers.add(question.replace('^','\n'));
                answers.add(question.replace('^','\"'));
                question+="\n\nWhat will replacing the \"^\"(s) with a \"\\\\\" do to the string?";
                break;
            case 3:
                correctAnswer = question;
                correctAnswer.replace('^', '\"');
                answers.add(correctAnswer);
                answers.add(question.replace('^','\t'));
                answers.add(question.replace('^','\n'));
                answers.add(question.replace('^','\''));
                question+="\n\nWhat will replacing the \"^\"(s) with a \" \\\" \" do to the string?";
                break;
            case 4:
                correctAnswer = question;
                correctAnswer.replace('^', '\'');
                answers.add(correctAnswer);
                answers.add(question.replace('^','\t'));
                answers.add(question.replace('^','\n'));
                answers.add(question.replace('^','\"'));
                question+="\n\nWhat will replacing the \"^\"(s) with a \" \\\' \" do to the string?";
                break;
            case 5:
                correctAnswer = question;
                correctAnswer.replace('^', '\\');
                answers.add(correctAnswer);
                answers.add(question.replace('^','\t'));
                answers.add(question.replace('^','\n'));
                answers.add("Turn the code into a single-line comment");
                question+="\n\nWhat will replacing the \"^\"(s) with a \"\\\\\" do to the string?";
                break;
        }
        Collections.shuffle(answers);
        answers.add("Unknown");
    }

    public ArrayList<String> getAnswers() { return answers; }

    public String getQuestion(){
        return question;
    }

    public String getCorrectAnswer(){
        return correctAnswer;
    }
}
