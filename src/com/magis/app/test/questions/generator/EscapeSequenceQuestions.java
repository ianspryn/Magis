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
        question = "";

        int sentencePicker = rand.nextInt(sentences.length);
        question = sentences[sentencePicker];

        int sequenePicker = rand.nextInt(5);

        switch(sequenePicker){
            case 0:
                correctAnswer = question;
                correctAnswer = correctAnswer.replace('^', '\n');
                answers.add(correctAnswer);
                answers.add(question.replace('^','\t'));
                answers.add(question.replace('^','\\'));
                answers.add(question.replace('^','\"'));
                question+="\n\nWhat will replacing the \"^\"(s) with a \"\\n\" do to the string?";
                break;
            case 1:
                correctAnswer = question;
                correctAnswer = correctAnswer.replace('^', '\t');
                answers.add(correctAnswer);
                answers.add(question.replace('^','\n'));
                answers.add(question.replace('^','\\'));
                answers.add(question.replace('^','\"'));
                question+="\n\nWhat will replacing the \"^\"(s) with a \"\\t\" do to the string?";
                break;
            case 2:
                correctAnswer = question;
                correctAnswer = correctAnswer.replace('^', '\\');
                answers.add(correctAnswer);
                answers.add(question.replace('^','\t'));
                answers.add(question.replace('^','\n'));
                answers.add(question.replace('^','\"'));
                question+="\n\nWhat will replacing the \"^\"(s) with a \"\\\\\" do to the string?";
                break;
            case 3:
                correctAnswer = question;
                correctAnswer = correctAnswer.replace('^', '\"');
                answers.add(correctAnswer);
                answers.add(question.replace('^','\t'));
                answers.add(question.replace('^','\n'));
                answers.add(question.replace('^','\''));
                question+="\n\nWhat will replacing the \"^\"(s) with a \" \\\" \" do to the string?";
                break;
            case 4:
                correctAnswer = question;
                correctAnswer = correctAnswer.replace('^', '\'');
                answers.add(correctAnswer);
                answers.add(question.replace('^','\t'));
                answers.add(question.replace('^','\n'));
                answers.add(question.replace('^','\"'));
                question+="\n\nWhat will replacing the \"^\"(s) with a \" \\\' \" do to the string?";
                break;
        }
        Collections.shuffle(answers);
        answers.add("None of the Above");
    }
}
