package com.com.flag.entity;
import java.util.ArrayList;

public class QuestionList {
    public final ArrayList<QuestionNare> List = new ArrayList<>();
    public final ArrayList<String> Countries = new ArrayList<>();
    public QuestionList() {
    }
    public void GenerateQuestion() {
        ArrayList<String> Countries4Question = new ArrayList<>(Countries);
        ArrayList<String> Countries4Answer = new ArrayList<>(Countries);
        for (int i = 0; i < Countries.size(); i++) {
            QuestionNare Q = new QuestionNare();
            Q.ID = "" + (i + 1);
            ArrayList<String> Answer = new ArrayList<>();
            for (int j = 0; j < 4; j++) {
                Answer.add(RandomCountry(Countries4Answer));
            }
            Q.AnswerA = Answer.get(0);
            Q.AnswerB = Answer.get(1);
            Q.AnswerC = Answer.get(2);
            Q.AnswerD = Answer.get(3);
            int temp = RandomQuesID(Answer);
            /*while (!Countries4Question.contains(Answer.get(temp))) {
                temp = RandomQuesID(Answer);
            }*/
            Q.Context = Answer.get(temp);
            Q.Answer = "" + temp;
            List.add(Q);
            Countries4Question.remove(Answer.get(temp));
            Countries4Answer.remove(Answer.get(temp));
        }
    }
    private int RandomQuesID(ArrayList<String> Answer)
    {
        return (int) ((Math.random() * (Answer.size())));
    }
    private String RandomCountry(ArrayList<String> Countries) {
        int randomID = (int) ((Math.random() * (Countries.size())));
        return Countries.get(randomID);
    }
}
