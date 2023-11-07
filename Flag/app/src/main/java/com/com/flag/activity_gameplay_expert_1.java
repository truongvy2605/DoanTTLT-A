package com.com.flag;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
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

public class activity_gameplay_expert_1 extends Activity implements View.OnClickListener {
    TextView Ques,Result;
    int pos = 0, pro = 0, HighScore = 0;
    Button A,B,C,D;
    ArrayList<QuestionNare> L = new ArrayList<>();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test_gamelayout_1);

        Ques = (TextView) findViewById(R.id.TextLayout1QuesID);
        Result = (TextView) findViewById(R.id.TextLayout1Result);
        A = (Button) findViewById(R.id.ButtonLayout1AnsA);
        B = (Button) findViewById(R.id.ButtonLayout1AnsB);
        C = (Button) findViewById(R.id.ButtonLayout1AnsC);
        D = (Button) findViewById(R.id.ButtonLayout1AnsD);
        LoadHighScore();
        ReadData();
        Display(pos);
        int[] Arr = {R.id.ButtonLayout1AnsA, R.id.ButtonLayout1AnsB,
                R.id.ButtonLayout1AnsC, R.id.ButtonLayout1AnsD};
        for (int id : Arr) {
            View v = (View) findViewById(id);
            v.setOnClickListener(this);
        }
    }
    @Override
    public void onClick(View v) {
        int idCheck = v.getId();
        if (idCheck == R.id.ButtonLayout1AnsA) {
            if (L.get(pos).Answer.compareTo("A") == 0)
                pro = pro + 10;
        } else if (idCheck == R.id.ButtonLayout1AnsB) {
            if (L.get(pos).Answer.compareTo("B") == 0)
                pro = pro + 10;
        } else if (idCheck == R.id.ButtonLayout1AnsC) {
            if (L.get(pos).Answer.compareTo("C") == 0)
                pro = pro + 10;
        } else if (idCheck == R.id.ButtonLayout1AnsD) {
            if (L.get(pos).Answer.compareTo("D") == 0)
                pro = pro + 10;
        }
        pos++;
        if (pos >= L.size()) {
            Intent intent = new Intent(activity_gameplay_expert_1.this, activity_result.class);
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

    private void SaveHighScore() {
        SharedPreferences sharedPreferences = getSharedPreferences("MyData",Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("H",HighScore);
        editor.apply();
    }

    private void Display(int i) {
        ((TextView) findViewById(R.id.TextLayout1QuesID)).setText("QUESTION " + L.get(i).ID);
        ((ImageView) findViewById(R.id.ImgLayout1Question)).setImageResource(this.getResources().getIdentifier(L.get(i).Q, null, this.getPackageName()));
        A.setText(L.get(i).AnswerA);
        B.setText(L.get(i).AnswerB);
        C.setText(L.get(i).AnswerC);
        D.setText(L.get(i).AnswerD);
        Result.setText("score: " + pro);
    }

    private void ReadData() {
        try {
            DocumentBuilderFactory DBF = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = DBF.newDocumentBuilder();
            InputStream in = getAssets().open("data_expert_layout1.xml");
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

    private void LoadHighScore() {
        SharedPreferences sharedPreferences = getSharedPreferences("MyData",
                Context.MODE_PRIVATE);
        if (sharedPreferences !=null){
            HighScore = sharedPreferences.getInt("H",0);
        }
    }
}
