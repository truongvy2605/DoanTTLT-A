package com.com.flag.entity;
import com.com.flag.R;

public class Support {
    public boolean Help1 = true, Help2 = true, Help3 = true;
    public void getHelp1() {
        Help1 = false;
    }
    public int[] getHelp2(QuestionList questionList, int id) {
        Help2 = false;
        int temp1 = 0, temp2 = 0;
        while (temp1 == temp2){
            temp1 = RandomAnswerID(new int[]{R.id.ButtonAnsA, R.id.ButtonAnsB, R.id.ButtonAnsC, R.id.ButtonAnsD}, Utility.AnswerIDtoButtonID(questionList.List.get(id).Answer));
            temp2 = RandomAnswerID(new int[]{R.id.ButtonAnsA, R.id.ButtonAnsB, R.id.ButtonAnsC, R.id.ButtonAnsD}, Utility.AnswerIDtoButtonID(questionList.List.get(id).Answer));
        }
        return new int[]{temp1, temp2};
    }
    public int[] getHelp3() {
        Help3 = false;
        return new int[]{R.id.ButtonAnsA, R.id.ButtonAnsB, R.id.ButtonAnsC, R.id.ButtonAnsD};
    }
    private int RandomAnswerID(int[] arr, int ans)
    {
        int temp;
        do {
            temp = (int) (Math.random() * arr.length);
        } while (arr[temp] == ans);
        return arr[temp];
    }
}
