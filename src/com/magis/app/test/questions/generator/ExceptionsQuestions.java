package com.magis.app.test.questions.generator;

import java.util.ArrayList;
import java.util.Random;

public class ExceptionsQuestions extends QuestionGenerator{

    public ExceptionsQuestions(){
        super();
    }

    @Override
    public void initialize() {
        int selection = rand.nextInt(2);
    }
}
