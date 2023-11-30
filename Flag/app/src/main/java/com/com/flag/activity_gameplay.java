package com.com.flag;

import static com.com.flag.MainActivity.BackgroundMusic;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Locale;
import java.util.Objects;
import java.util.Random;

class QuestionNare {
    public String ID;
    public String Ques;
    public String AnswerA, AnswerB, AnswerC, AnswerD, Answer;
}
public class activity_gameplay extends Activity implements View.OnClickListener, View.OnLongClickListener {
    private Button stopMusicButton;
    boolean Help1 = true, Help2 = true;
    int[] ButtonArr = {R.id.ButtonAnsA, R.id.ButtonAnsB,
            R.id.ButtonAnsC, R.id.ButtonAnsD, R.id.ButtonStopMusic, R.id.ButtonHelp1, R.id.ButtonHelp2};
    int pos = 1, pro = 0, HighScore = 0, id = 0, num = 15;
    String diff = "", style = "", path = "";
    ArrayList<QuestionNare> QuesList = new ArrayList<>();
    ArrayList<String> Countries = new ArrayList<>();
    TextToSpeech AnswerTTS;
    ProgressBar CountdownPB;
    CountDownTimer CountdownTimer;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AnswerTTS = new TextToSpeech(this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status == TextToSpeech.SUCCESS)
                    AnswerTTS.setLanguage(Locale.ENGLISH);
            }
        });

        Intent intent = getIntent();
        if (intent != null)
        {
            diff = intent.getStringExtra("diff");
            style = intent.getStringExtra("style");
        }
        LoadHighScore();
        if (Objects.equals(diff, "hard"))
            path = "Data_Hard";
        else if (Objects.equals(diff, "expert"))
            path = "Data_Expert";
        else
            path = "Data_Easy";
        StorageReference listRef = FirebaseStorage.getInstance().getReference().child(path);
        listRef.listAll()
                .addOnSuccessListener(listResult -> {
                    for (StorageReference item : listResult.getItems()) {
                        Countries.add((item.getName().split("\\."))[0]);
                    }
                    ReadData(QuesList);
                    Gameplay_layout1();
                })
                .addOnFailureListener(e ->
                        Toast.makeText(activity_gameplay.this, "Error _ Retrieving Data", Toast.LENGTH_SHORT).show());
        if (Objects.equals(style, "suddendeath"))
        {
            num = 1000;
        }
    }
    @Override
    public boolean onLongClick(View v) {
        int idCheck = v.getId();
        if (pos % 2 == 0) {
        }
        else {
            if (idCheck == R.id.ButtonAnsA) {
                AnswerTTS.speak(QuesList.get(id).AnswerA, TextToSpeech.QUEUE_FLUSH, null);
            } else if (idCheck == R.id.ButtonAnsB) {
                AnswerTTS.speak(QuesList.get(id).AnswerB, TextToSpeech.QUEUE_FLUSH, null);
            } else if (idCheck == R.id.ButtonAnsC) {
                AnswerTTS.speak(QuesList.get(id).AnswerC, TextToSpeech.QUEUE_FLUSH, null);
            } else if (idCheck == R.id.ButtonAnsD) {
                AnswerTTS.speak(QuesList.get(id).AnswerD, TextToSpeech.QUEUE_FLUSH, null);
            }
        }
        return false;
    }
    @Override
    public void onClick(View v) {
        int idCheck = v.getId();
        int temp = pro;
        if (idCheck == R.id.ButtonStopMusic){
            if (BackgroundMusic.isPlaying()){
                BackgroundMusic.pause();
            }
            else {
                BackgroundMusic.start();
            }
        }
        else if (idCheck == R.id.ButtonHelp1) {
            Help1 = false;
            NextGameplay(pos);
        }
        else if (idCheck == R.id.ButtonHelp2) {
            Help2 = false;
            String temp1= RandomAnswerID(QuesList.get(id).Answer);
            String temp2 = temp1;
            while (Objects.equals(temp1, temp2)){
                temp2 = RandomAnswerID(QuesList.get(id).Answer);
            }
        }
        else {
            if (CheckAnswer(idCheck)) {
                pro = pro + 10;
            }
            else {
                if (Objects.equals(style, "suddendeath"))
                {
                    pos = num;
                }
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
            }
            else {
                NextGameplay(pos);
            }
        }
    }

    private boolean CheckAnswer(int idCheck) {
        CountdownTimer.cancel();
        for (int j : ButtonArr) {
            if (pos % 2 == 0) {
                ((ImageButton) findViewById(j)).setEnabled(false);
            } else {
                ((Button) findViewById(j)).setEnabled(false);
            }
        }
        if (idCheck == R.id.ButtonAnsA) {
            if (QuesList.get(id).Answer.compareTo("0") == 0)
                return true;
        } else if (idCheck == R.id.ButtonAnsB) {
            if (QuesList.get(id).Answer.compareTo("1") == 0)
                return true;
        } else if (idCheck == R.id.ButtonAnsC) {
            if (QuesList.get(id).Answer.compareTo("2") == 0)
                return true;
        } else if (idCheck == R.id.ButtonAnsD) {
            if (QuesList.get(id).Answer.compareTo("3") == 0)
                return true;
        }
        QuesList.remove(id);
        return false;
    }
    private void NextGameplay(int pos)
    {
        if (pos % 2 == 0)
            Gameplay_layout2();
        else
            Gameplay_layout1();
    }
    private void Gameplay_layout1(){
        setContentView(R.layout.activity_gamelayout_1);
        Display_layout1(QuesList);
        ((Button) findViewById(R.id.ButtonHelp1)).setEnabled(Help1);
        ((Button) findViewById(R.id.ButtonHelp2)).setEnabled(Help2);
        StartCountDown();
        for (int id : ButtonArr) {
            View v = findViewById(id);
            v.setOnClickListener(this);
            v.setOnLongClickListener(this);
        }
    }
    private void Gameplay_layout2(){
        setContentView(R.layout.activity_gamelayout_2);
        Display_layout2(QuesList);
        ((Button) findViewById(R.id.ButtonHelp1)).setEnabled(Help1);
        ((Button) findViewById(R.id.ButtonHelp2)).setEnabled(Help2);
        StartCountDown();
        for (int id : ButtonArr) {
            View v = findViewById(id);
            v.setOnClickListener(this);
        }
    }

    private void StartCountDown() {
        CountdownPB = findViewById(R.id.ProgressbarCountdown);
        CountdownTimer = new CountDownTimer(30000,1000) {
            int countdown = 30;
            @Override
            public void onTick(long millisUntilFinished) {
                CountdownPB.setProgress(countdown);
                countdown--;
            }
            @Override
            public void onFinish() {
                CountdownPB.setProgress(0);
                CheckAnswer(-1);
            }
        }.start();
    }

    @SuppressLint({"SetTextI18n", "DiscouragedApi"})
    private void Display_layout1(ArrayList<QuestionNare> L) {
        id = RandomExamID(0, L.size());
        TextView Result;
        Result = findViewById(R.id.TextResult);
        ((TextView) findViewById(R.id.TextQuesID)).setText("QUESTION " + pos);
        StorageReference storeref;
        storeref = FirebaseStorage.getInstance().getReference(path + "/" + L.get(id).Ques + ".png");
        try {
            File tempfile = File.createTempFile("tempfile", ".png");
            storeref.getFile(tempfile).addOnSuccessListener(taskSnapshot -> {
                Bitmap bitmap = BitmapFactory.decodeFile(tempfile.getAbsolutePath());
                ((ImageView) findViewById(R.id.Layout1_ImgQuestion)).setImageBitmap(bitmap);
            }).addOnFailureListener(e -> Toast.makeText(activity_gameplay.this, "Error _ Game Layout 1", Toast.LENGTH_SHORT).show());
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        //((ImageView) findViewById(R.id.ImgLayout1Question)).setImageResource(this.getResources().getIdentifier(L.get(id).Q, null, this.getPackageName()));
        ((Button) findViewById(R.id.ButtonAnsA)).setText(L.get(id).AnswerA);
        ((Button) findViewById(R.id.ButtonAnsB)).setText(L.get(id).AnswerB);
        ((Button) findViewById(R.id.ButtonAnsC)).setText(L.get(id).AnswerC);
        ((Button) findViewById(R.id.ButtonAnsD)).setText(L.get(id).AnswerD);
        Result.setText("" + pro);
    }
    @SuppressLint({"SetTextI18n", "DiscouragedApi"})
    private void Display_layout2(ArrayList<QuestionNare> L) {
        id = RandomExamID(0, L.size());
        TextView Result;
        Result = findViewById(R.id.TextResult);
        ((TextView) findViewById(R.id.TextQuesID)).setText("QUESTION " + pos);
        ((TextView) findViewById(R.id.Layout2_TextQuestion)).setText(L.get(id).Ques);
        StorageReference storeref;
        storeref = FirebaseStorage.getInstance().getReference(path + "/" + L.get(id).AnswerA + ".png");
        try {
            File tempfile = File.createTempFile("tempfile", ".png");
            storeref.getFile(tempfile).addOnSuccessListener(taskSnapshot -> {
                Bitmap bitmap = BitmapFactory.decodeFile(tempfile.getAbsolutePath());
                ((ImageButton) findViewById(R.id.ButtonAnsA)).setImageBitmap(bitmap);
            }).addOnFailureListener(e -> Toast.makeText(activity_gameplay.this, "Error _ Game Layout 2", Toast.LENGTH_SHORT).show());
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        storeref = FirebaseStorage.getInstance().getReference(path + "/" + L.get(id).AnswerB + ".png");
        try {
            File tempfile = File.createTempFile("tempfile", ".png");
            storeref.getFile(tempfile).addOnSuccessListener(taskSnapshot -> {
                Bitmap bitmap = BitmapFactory.decodeFile(tempfile.getAbsolutePath());
                ((ImageButton) findViewById(R.id.ButtonAnsB)).setImageBitmap(bitmap);
            }).addOnFailureListener(e -> Toast.makeText(activity_gameplay.this, "Error _ Game Layout 2", Toast.LENGTH_SHORT).show());
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        storeref = FirebaseStorage.getInstance().getReference(path + "/" + L.get(id).AnswerC + ".png");
        try {
            File tempfile = File.createTempFile("tempfile", ".png");
            storeref.getFile(tempfile).addOnSuccessListener(taskSnapshot -> {
                Bitmap bitmap = BitmapFactory.decodeFile(tempfile.getAbsolutePath());
                ((ImageButton) findViewById(R.id.ButtonAnsC)).setImageBitmap(bitmap);
            }).addOnFailureListener(e -> Toast.makeText(activity_gameplay.this, "Error _ Game Layout 2", Toast.LENGTH_SHORT).show());
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        storeref = FirebaseStorage.getInstance().getReference(path + "/" + L.get(id).AnswerD + ".png");
        try {
            File tempfile = File.createTempFile("tempfile", ".png");
            storeref.getFile(tempfile).addOnSuccessListener(taskSnapshot -> {
                Bitmap bitmap = BitmapFactory.decodeFile(tempfile.getAbsolutePath());
                ((ImageButton) findViewById(R.id.ButtonAnsD)).setImageBitmap(bitmap);
            }).addOnFailureListener(e -> Toast.makeText(activity_gameplay.this, "Error _ Game Layout 2", Toast.LENGTH_SHORT).show());
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        /*
        ((ImageButton) findViewById(R.id.IButtonLayout2AnsA)).setImageResource(this.getResources().getIdentifier(L.get(id).AnswerA + ".png", null, this.getPackageName()));
        ((ImageButton) findViewById(R.id.IButtonLayout2AnsB)).setImageResource(this.getResources().getIdentifier(L.get(id).AnswerB + ".png", null, this.getPackageName()));
        ((ImageButton) findViewById(R.id.IButtonLayout2AnsC)).setImageResource(this.getResources().getIdentifier(L.get(id).AnswerC + ".png", null, this.getPackageName()));
        ((ImageButton) findViewById(R.id.IButtonLayout2AnsD)).setImageResource(this.getResources().getIdentifier(L.get(id).AnswerD + ".png", null, this.getPackageName()));
        */
        Result.setText("" + pro);
    }

    private void ReadData(ArrayList<QuestionNare> list) {
        ArrayList<String> Temp_Country = new ArrayList<>(Countries);
        for (int i = 0; i < 100; i++) {
            if (Temp_Country.size() < 5){
                Temp_Country = new ArrayList<>(Countries);
            }
            QuestionNare Q1 = new QuestionNare();
            Q1.ID = "" + (i + 1);
            ArrayList<String> Answer = new ArrayList<>();
            for (int j = 0; j < 4; j++) {
                Answer.add(RandomCountry(Temp_Country));
                Temp_Country.remove(Answer.get(Answer.size()-1));
            }
            Q1.AnswerA = Answer.get(0);
            Q1.AnswerB = Answer.get(1);
            Q1.AnswerC = Answer.get(2);
            Q1.AnswerD = Answer.get(3);
            int temp = RandomQuesID(Answer);
            Q1.Ques =  Answer.get(temp);
            Q1.Answer = "" + temp;
            list.add(Q1);
        }
        /*try {
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
        }*/
    }
    private void LoadHighScore(){
        SharedPreferences sharedPreferences = getSharedPreferences("MyData",
                Context.MODE_PRIVATE);
        if (sharedPreferences !=null){
            HighScore = sharedPreferences.getInt("H",0);
        }
    }
    private void SaveHighScore(){
        SharedPreferences sharedPreferences = getSharedPreferences("MyData",Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("H",HighScore);
        editor.apply();
    }
    private int RandomExamID(int min, int max) {
        return (int) ((Math.random() * (max - min)) + min);
    }
    private String RandomAnswerID(String CorrectAnswer)
    {
        String temp = CorrectAnswer;
        while (Objects.equals(temp, CorrectAnswer)){
            temp = "" + ((int) ((Math.random() * 3) + 0));
        }
        return temp;
    }
    private int RandomQuesID(ArrayList<String> answer)
    {
        return (int) ((Math.random() * (answer.size())));
    }
    private String RandomCountry(ArrayList<String> data) {
        int randomID = (int) ((Math.random() * (data.size())));
        return data.get(randomID);
    }
}
