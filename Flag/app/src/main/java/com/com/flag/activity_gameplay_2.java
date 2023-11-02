package com.com.flag;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.Nullable;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

class QuestionNare {
    public String ID;
    public String Q;
    public String AnswerA, AnswerB, AnswerC, AnswerD, Answer;
}
public class activity_gameplay_2 extends Activity implements View.OnClickListener{
    TextView Question, Result;
    int pos = 0, pro = 0, HighScore = 0;
    ArrayList<QuestionNare> L = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test_gamelayout_2);
        Question = (TextView) findViewById(R.id.TextLayout2Question);
        Result = (TextView) findViewById(R.id.TextLayout2Result);
        LoadHighScore();
        ReadData();
        Display(pos);
        int[] Arr = {R.id.IButtonLayout2AnsA, R.id.IButtonLayout2AnsB,
                R.id.IButtonLayout2AnsC, R.id.IButtonLayout2AnsD};
        for (int id : Arr) {
            View v = (View) findViewById(id);
            v.setOnClickListener(this);
        }
    }
    @Override
    public void onClick(View v) {
        int idCheck = v.getId();
        if (idCheck == R.id.IButtonLayout2AnsA) {
            if (L.get(pos).Answer.compareTo("A") == 0)
                pro = pro + 10;
        } else if (idCheck == R.id.IButtonLayout2AnsB) {
            if (L.get(pos).Answer.compareTo("B") == 0)
                pro = pro + 10;
        } else if (idCheck == R.id.IButtonLayout2AnsC) {
            if (L.get(pos).Answer.compareTo("C") == 0)
                pro = pro + 10;
        } else if (idCheck == R.id.IButtonLayout2AnsD) {
            if (L.get(pos).Answer.compareTo("D") == 0)
                pro = pro + 10;
        }
        pos++;
        if (pos >= L.size()) {
            Intent intent = new Intent(activity_gameplay_2.this, activity_result.class);
            Bundle bundle = new Bundle();
            bundle.putInt("Progress", pro);
            bundle.putInt("QuestionID", pos);
            intent.putExtra("MyPackage", bundle);
            startActivity(intent);
            if (pro > HighScore) {
                HighScore = pro;
                SaveHighScore();
            }
            finish();
        } else Display(pos);
    }
    @SuppressLint({"SetTextI18n", "DiscouragedApi"})
    void Display(int i) {
        ((TextView) findViewById(R.id.TextLayout2QuesID)).setText("QUESTION " + L.get(i).ID);
        Question.setText(L.get(i).Q);
        ((ImageButton) findViewById(R.id.IButtonLayout2AnsA)).setImageResource(this.getResources().getIdentifier(L.get(i).AnswerA, null, this.getPackageName()));
        ((ImageButton) findViewById(R.id.IButtonLayout2AnsB)).setImageResource(this.getResources().getIdentifier(L.get(i).AnswerB, null, this.getPackageName()));
        ((ImageButton) findViewById(R.id.IButtonLayout2AnsC)).setImageResource(this.getResources().getIdentifier(L.get(i).AnswerC, null, this.getPackageName()));
        ((ImageButton) findViewById(R.id.IButtonLayout2AnsD)).setImageResource(this.getResources().getIdentifier(L.get(i).AnswerD, null, this.getPackageName()));
        Result.setText("score: " + pro);
    }
    void ReadData() {
        try {
            DocumentBuilderFactory DBF = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = DBF.newDocumentBuilder();
            InputStream in = getAssets().open("data_easy_layout2.xml");
            Document doc = builder.parse(in);
            Element root = doc.getDocumentElement();
            NodeList list = root.getChildNodes();
            for (int i = 0; i < list.getLength(); i++) {
                Node node = list.item(i);
                if (node instanceof Element) {
                    Element Item = (Element) node;
                    NodeList listChild = Item.getElementsByTagName("ID");
                    String ID = listChild.item(0).getTextContent();
                    listChild = Item.getElementsByTagName("Question");
                    String Question = listChild.item(0).getTextContent();
                    listChild = Item.getElementsByTagName("AnswerA");
                    String AnswerA = listChild.item(0).getTextContent();
                    listChild = Item.getElementsByTagName("AnswerB");
                    String AnswerB = listChild.item(0).getTextContent();
                    listChild = Item.getElementsByTagName("AnswerC");
                    String AnswerC = listChild.item(0).getTextContent();
                    listChild = Item.getElementsByTagName("AnswerD");
                    String AnswerD = listChild.item(0).getTextContent();
                    listChild = Item.getElementsByTagName("Answer");
                    String Answer = listChild.item(0).getTextContent();
                    QuestionNare Q1 = new QuestionNare();
                    Q1.ID = ID;
                    Q1.Q = Question;
                    Q1.AnswerA = AnswerA;
                    Q1.AnswerB = AnswerB;
                    Q1.AnswerC = AnswerC;
                    Q1.AnswerD = AnswerD;
                    Q1.Answer = Answer;
                    L.add(Q1);
                };
            }
        } catch (ParserConfigurationException | SAXException | IOException e) {
            e.printStackTrace();
        }
    }
    void LoadHighScore(){
        SharedPreferences sharedPreferences = getSharedPreferences("MyData",
                Context.MODE_PRIVATE);
        if (sharedPreferences !=null){
            HighScore = sharedPreferences.getInt("H",0);
        }
    }
    void SaveHighScore(){
        SharedPreferences sharedPreferences = getSharedPreferences("MyData",Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("H",HighScore);
        editor.apply();
    }
};


