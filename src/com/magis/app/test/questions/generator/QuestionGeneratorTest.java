package com.magis.app.test.questions.generator;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;

class QuestionGeneratorTest {

    @Test
    void testCommentQuestion(){
        CommentQuestions cq = new CommentQuestions();
        cq.generateGeneralCommentQuestion();
        String[] answers = cq.getAnswers().toArray(new String[0]);
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
        DataTypeQuestions dtq = new DataTypeQuestions();
        dtq.datatypeMatchingQuestion();
        String[] answers = dtq.getAnswers().toArray(new String[0]);
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
        ObjectComparisonQuestions objc  = new ObjectComparisonQuestions();
        objc.generateEqualsQuestion();
        String[] answers = objc.getAnswers().toArray(new String[0]);
        String question = objc.getQuestion();
        String correctAnswer = objc.getCorrectAnswer();

        System.out.println(question+"\n");

        for(int i=0; i<answers.length; i++){
            System.out.println((i+1)+". "+answers[i]);
        }

        System.out.println("\nCorrect Answer: "+correctAnswer);
    }

    @Test
    void testSubstringQuestion(){
        OperatorQuestions objc  = new OperatorQuestions();
        objc.getSubstringQuestion();
        ArrayList<String> answers = objc.getAnswers();
        String question = objc.getQuestion();
        String correctAnswer = objc.getCorrectAnswer();

        System.out.println(question+"\n");

        for(int i=0; i<answers.size(); i++){
            System.out.println((i+1)+". "+answers.get(i));
        }

        System.out.println("\nCorrect Answer: "+correctAnswer);
    }

    @Test
    void testIntegerDivision(){
        OperatorQuestions objc  = new OperatorQuestions();
        objc.getIntegerDivisionQuestion();
        String[] answers = objc.getAnswers().toArray(new String[0]);
        String question = objc.getQuestion();
        String correctAnswer = objc.getCorrectAnswer();
        System.out.println(question+"\n");

        for(int i=0; i<answers.length; i++){
            System.out.println((i+1)+". "+answers[i]);
        }

        System.out.println("\nCorrect Answer: "+correctAnswer);
    }

    @Test
    void testIncremental(){
        OperatorQuestions objc  = new OperatorQuestions();
        objc.getIncrementalQuestion();
        String[] answers = objc.getAnswers().toArray(new String[0]);
        String question = objc.getQuestion();
        String correctAnswer = objc.getCorrectAnswer();
        System.out.println(question+"\n");

        for(int i=0; i<answers.length; i++){
            System.out.println((i+1)+". "+answers[i]);
        }

        System.out.println("\nCorrect Answer: "+correctAnswer);
    }

    @Test
    void testModular(){
        OperatorQuestions objc  = new OperatorQuestions();
        objc.getModularQuestion();
        String[] answers = objc.getAnswers().toArray(new String[0]);
        String question = objc.getQuestion();
        String correctAnswer = objc.getCorrectAnswer();
        System.out.println(question+"\n");

        for(int i=0; i<answers.length; i++){
            System.out.println((i+1)+". "+answers[i]);
        }

        System.out.println("\nCorrect Answer: "+correctAnswer);
    }

//    @Test
//    void testMathMethod(){
//        MethodQuestions objc  = new MethodQuestions();
//        objc.getMathMethodQuestion();
//        String[] answers = objc.getAnswers().toArray(new String[0]);
//        String question = objc.getQuestion();
//        String correctAnswer = objc.getCorrectAnswer();
//        System.out.println(question+"\n");
//
//        for(int i=0; i<answers.length; i++){
//            System.out.println((i+1)+". "+answers[i]);
//        }
//
//        System.out.println("\nCorrect Answer: "+correctAnswer);
//    }

    @Test
    void testStringMethod(){
        ObjectComparisonQuestions objc  = new ObjectComparisonQuestions();
        objc.getStringMethodQuestion();
        String[] answers = objc.getAnswers().toArray(new String[0]);
        String question = objc.getQuestion();
        String correctAnswer = objc.getCorrectAnswer();
        System.out.println(question+"\n");

        for(int i=0; i<answers.length; i++){
            System.out.println((i+1)+". "+answers[i]);
        }

        System.out.println("\nCorrect Answer: "+correctAnswer);
    }

    @Test
    void testVariableNames(){
        VariableQuestions objc  = new VariableQuestions();
        objc.getVariableNameQuestion();
        String[] answers = objc.getAnswers().toArray(new String[0]);
        String question = objc.getQuestion();
        String correctAnswer = objc.getCorrectAnswer();
        System.out.println(question+"\n");

        for(int i=0; i<answers.length; i++){
            System.out.println((i+1)+". "+answers[i]);
        }

        System.out.println("\nCorrect Answer: "+correctAnswer);
    }

    @Test
    void testInstanceVariable(){
        VariableQuestions objc  = new VariableQuestions();
        objc.getInstanceVariableQuestion();
        String[] answers = objc.getAnswers().toArray(new String[0]);
        String question = objc.getQuestion();
        String correctAnswer = objc.getCorrectAnswer();
        System.out.println(question+"\n");

        for(int i=0; i<answers.length; i++){
            System.out.println((i+1)+". "+answers[i]);
        }

        System.out.println("\nCorrect Answer: "+correctAnswer);
    }

    @Test
    void testArrays(){
        ArraysQuestions objc  = new ArraysQuestions();
        objc.getArrayIndexQuestion();
        String[] answers = objc.getAnswers().toArray(new String[0]);
        String question = objc.getQuestion();
        String correctAnswer = objc.getCorrectAnswer();
        System.out.println(question+"\n");

        for(int i=0; i<answers.length; i++){
            System.out.println((i+1)+". "+answers[i]);
        }

        System.out.println("\nCorrect Answer: "+correctAnswer);
    }

    @Test
    void testArrays2(){
        ArraysQuestions objc  = new ArraysQuestions();
        objc.getIndexQuestion();
        String[] answers = objc.getAnswers().toArray(new String[0]);
        String question = objc.getQuestion();
        String correctAnswer = objc.getCorrectAnswer();
        System.out.println(question+"\n");

        for(int i=0; i<answers.length; i++){
            System.out.println((i+1)+". "+answers[i]);
        }

        System.out.println("\nCorrect Answer: "+correctAnswer);
    }

    @Test
    void testEscapeSequences(){
        EscapeSequenceQuestions objc  = new EscapeSequenceQuestions();
        objc.getEscapeSequenceQuestion();
        String[] answers = objc.getAnswers().toArray(new String[0]);
        String question = objc.getQuestion();
        String correctAnswer = objc.getCorrectAnswer();
        System.out.println(question+"\n");

        for(int i=0; i<answers.length; i++){
            System.out.println((i+1)+". "+answers[i]);
        }

        System.out.println("\nCorrect Answer: "+correctAnswer);
    }

    @Test
    void testConstructer(){
        QuestionGenerator objc  = new ConstructerQuestions();
        ((ConstructerQuestions) objc).generateContentQuestion();
        String[] answers = objc.getAnswers().toArray(new String[0]);
        String question = objc.getQuestion();
        String correctAnswer = objc.getCorrectAnswer();
        System.out.println(question+"\n");

        for(int i=0; i<answers.length; i++){
            System.out.println((i+1)+". "+answers[i]);
        }

        System.out.println("\nCorrect Answer: "+correctAnswer);
    }

}