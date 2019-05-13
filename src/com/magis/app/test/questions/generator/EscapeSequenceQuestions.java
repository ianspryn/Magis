package com.magis.app.test.questions.generator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class EscapeSequenceQuestions extends QuestionGenerator{

    private String[] sentences = {"The dog ^left the house", "Can you smell ^what the Rock is cooking?", "What is ^the sum of these two numbers?",
    "I ran over ^the very big hill", "Can ^I have ^the money ^please?", "I want ^to do well ^on this exam"};

    public EscapeSequenceQuestions(){
        super();
    }

    @Override
    public void initialize() {
        getEscapeSequenceQuestion();
    }

    @Override
    public int getNumUnique() {
        return sentences.length*5;
    }

    public void getEscapeSequenceQuestion(){
        answers.clear();

        int sentencePicker = rand.nextInt(sentences.length);
        question = "```String s = \""+sentences[sentencePicker]+"\";```";
        String s = sentences[sentencePicker];

        int sequenePicker = rand.nextInt(5);

        switch(sequenePicker){
            case 0:
                correctAnswer = s;
                correctAnswer = correctAnswer.replace("^","     ");
                answers.add(correctAnswer);
                answers.add(s.replace('^','\t'));
                answers.add(s.replace('^','\\'));
                answers.add(s.replace('^','\"'));
                question+="\n\nWhat will replacing the \"^\"(s) with a \"\\n\" do to the string?";
                break;
            case 1:
                correctAnswer = s;
                correctAnswer = correctAnswer.replace("^","     ");
                answers.add(correctAnswer);
                answers.add(s.replace('^','\n'));
                answers.add(s.replace('^','\\'));
                answers.add(s.replace('^','\"'));
                question+="\n\nWhat will replacing the \"^\"(s) with a \"\\t\" do to the string?";
                break;
            case 2:
                correctAnswer = s;
                correctAnswer = correctAnswer.replace('^', '\\');
                answers.add(correctAnswer);
                answers.add(s.replace("^","     "));
                answers.add(s.replace('^','\n'));
                answers.add(s.replace('^','\"'));
                question+="\n\nWhat will replacing the \"^\"(s) with a \"\\\\\" do to the string?";
                break;
            case 3:
                correctAnswer = s;
                correctAnswer = correctAnswer.replace('^', '\"');
                answers.add(correctAnswer);
                answers.add(s.replace("^","     "));
                answers.add(s.replace('^','\n'));
                answers.add(s.replace('^','\''));
                question+="\n\nWhat will replacing the \"^\"(s) with a \" \\\" \" do to the string?";
                break;
            case 4:
                correctAnswer = s;
                correctAnswer = correctAnswer.replace('^', '\'');
                answers.add(correctAnswer);
                answers.add(s.replace("^","     "));
                answers.add(s.replace('^','\n'));
                answers.add(s.replace('^','\"'));
                question+="\n\nWhat will replacing the \"^\"(s) with a \" \\\' \" do to the string?";
                break;
        }
        Collections.shuffle(answers);
        answers.add("None of the Above");
    }
}
