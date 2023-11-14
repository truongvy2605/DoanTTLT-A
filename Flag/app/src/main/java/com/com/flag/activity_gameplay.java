package com.com.flag;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
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
import java.util.Objects;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

class QuestionNare {
    public String ID;
    public String Q;
    public String AnswerA, AnswerB, AnswerC, AnswerD, Answer;
}
public class activity_gameplay extends Activity implements View.OnClickListener {
    int pos = 0, pro = 0, HighScore = 0, id = 0, num = 20;
    String diff = "", style = "";
    ArrayList<QuestionNare> QuesList_layout1 = new ArrayList<>();
    ArrayList<QuestionNare> QuesList_layout2 = new ArrayList<>();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        Intent intent = getIntent();
        if (intent != null)
        {
            diff = intent.getStringExtra("diff");
            style = intent.getStringExtra("style");
        }
        super.onCreate(savedInstanceState);
        LoadHighScore();
        if (Objects.equals(diff, "hard"))
        {
            ReadData("data_hard_layout1.xml", QuesList_layout1);
            ReadData("data_hard_layout2.xml", QuesList_layout2);
        }
        else if (Objects.equals(diff, "expert"))
        {
            ReadData("data_expert_layout1.xml", QuesList_layout1);
            ReadData("data_expert_layout2.xml", QuesList_layout2);
        }
        else
        {
            ReadData("data_easy_layout1.xml", QuesList_layout1);
            ReadData("data_easy_layout2.xml", QuesList_layout2);
        }
        if (Objects.equals(style, "suddendeath"))
        {
            num = 50;
        }
        Gameplay_layout2();
    }
    @Override
    public void onClick(View v) {
        int idCheck = v.getId();
        int temp = pro;
        if (pos % 2 == 0) {
            if (idCheck == R.id.IButtonLayout2AnsA) {
                if (QuesList_layout2.get(id).Answer.compareTo("A") == 0)
                    pro = pro + 10;
            } else if (idCheck == R.id.IButtonLayout2AnsB) {
                if (QuesList_layout2.get(id).Answer.compareTo("B") == 0)
                    pro = pro + 10;
            } else if (idCheck == R.id.IButtonLayout2AnsC) {
                if (QuesList_layout2.get(id).Answer.compareTo("C") == 0)
                    pro = pro + 10;
            } else if (idCheck == R.id.IButtonLayout2AnsD) {
                if (QuesList_layout2.get(id).Answer.compareTo("D") == 0)
                    pro = pro + 10;
            }
            QuesList_layout2.remove(id);
        }
        else {
            if (idCheck == R.id.ButtonLayout1AnsA) {
                if (QuesList_layout1.get(id).Answer.compareTo("A") == 0)
                    pro = pro + 10;
            } else if (idCheck == R.id.ButtonLayout1AnsB) {
                if (QuesList_layout1.get(id).Answer.compareTo("B") == 0)
                    pro = pro + 10;
            } else if (idCheck == R.id.ButtonLayout1AnsC) {
                if (QuesList_layout1.get(id).Answer.compareTo("C") == 0)
                    pro = pro + 10;
            } else if (idCheck == R.id.ButtonLayout1AnsD) {
                if (QuesList_layout1.get(id).Answer.compareTo("D") == 0)
                    pro = pro + 10;
            }
            QuesList_layout1.remove(id);
        }
        if ((temp == pro) && (Objects.equals(style, "suddendeath")))
        {
            pos = num;
        }
        pos++;
        if (pos >= num) {
            Intent intent = new Intent(activity_gameplay.this, activity_result.class);
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
        } else {
            if (pos % 2 == 0) {
                Gameplay_layout2();
            }
            else
            {
                Gameplay_layout1();
            }
        }
    }

    private void Gameplay_layout1(){
        setContentView(R.layout.activity_gamelayout_1);
        Display_layout1(QuesList_layout1);
        int[] Arr = {R.id.ButtonLayout1AnsA, R.id.ButtonLayout1AnsB,
                R.id.ButtonLayout1AnsC, R.id.ButtonLayout1AnsD};
        for (int id : Arr) {
            View v = (View) findViewById(id);
            v.setOnClickListener(this);
        }
    }
    private void Gameplay_layout2(){
        setContentView(R.layout.activity_gamelayout_2);
        Display_layout2(QuesList_layout2);
        int[] Arr = {R.id.IButtonLayout2AnsA, R.id.IButtonLayout2AnsB,
                R.id.IButtonLayout2AnsC, R.id.IButtonLayout2AnsD};
        for (int id : Arr) {
            View v = (View) findViewById(id);
            v.setOnClickListener(this);
        }
    }
    @SuppressLint({"SetTextI18n", "DiscouragedApi"})
    private void Display_layout1(ArrayList<QuestionNare> L) {
        id = RandomExamID(0, L.size());
        TextView Result;
        Result = findViewById(R.id.TextLayout1Result);
        ((TextView) findViewById(R.id.TextLayout1QuesID)).setText("QUESTION " + pos);
        ((ImageView) findViewById(R.id.ImgLayout1Question)).setImageResource(this.getResources().getIdentifier(L.get(id).Q, null, this.getPackageName()));
        ((Button) findViewById(R.id.ButtonLayout1AnsA)).setText(L.get(id).AnswerA);
        ((Button) findViewById(R.id.ButtonLayout1AnsB)).setText(L.get(id).AnswerB);
        ((Button) findViewById(R.id.ButtonLayout1AnsC)).setText(L.get(id).AnswerC);
        ((Button) findViewById(R.id.ButtonLayout1AnsD)).setText(L.get(id).AnswerD);
        Result.setText("score: " + pro);
    }
    @SuppressLint({"SetTextI18n", "DiscouragedApi"})
    void Display_layout2(ArrayList<QuestionNare> L) {
        id = RandomExamID(0, L.size());
        TextView Result;
        Result = findViewById(R.id.TextLayout2Result);
        ((TextView) findViewById(R.id.TextLayout2QuesID)).setText("QUESTION " + pos);
        ((TextView) findViewById(R.id.TextLayout2Question)).setText(L.get(id).Q);
        ((ImageButton) findViewById(R.id.IButtonLayout2AnsA)).setImageResource(this.getResources().getIdentifier(L.get(id).AnswerA, null, this.getPackageName()));
        ((ImageButton) findViewById(R.id.IButtonLayout2AnsB)).setImageResource(this.getResources().getIdentifier(L.get(id).AnswerB, null, this.getPackageName()));
        ((ImageButton) findViewById(R.id.IButtonLayout2AnsC)).setImageResource(this.getResources().getIdentifier(L.get(id).AnswerC, null, this.getPackageName()));
        ((ImageButton) findViewById(R.id.IButtonLayout2AnsD)).setImageResource(this.getResources().getIdentifier(L.get(id).AnswerD, null, this.getPackageName()));
        Result.setText("score: " + pro);
    }

    private void ReadData(String filename, ArrayList<QuestionNare> list) {
        try {
            DocumentBuilderFactory DBF = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = DBF.newDocumentBuilder();
            InputStream in = getAssets().open(filename);
            Document doc = builder.parse(in);
            Element root = doc.getDocumentElement();
            NodeList nodelist = root.getChildNodes();
            for (int i = 0; i < nodelist.getLength(); i++) {
                Node node = nodelist.item(i);
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
                    list.add(Q1);
                }
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
    public int RandomExamID(int min, int max) {
        return (int) ((Math.random() * (max - min)) + min);
    }
}
