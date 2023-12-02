package com.com.flag;

import static com.com.flag.MainActivity.BackgroundMusic;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.AudioAttributes;
import android.media.SoundPool;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.speech.tts.TextToSpeech;
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
import java.util.Locale;
import java.util.Objects;

class QuestionNare {
    public String ID;
    public String Ques;
    public String AnswerA, AnswerB, AnswerC, AnswerD, Answer;
}
public class activity_gameplay extends Activity implements View.OnClickListener, View.OnLongClickListener {
    private boolean Help1 = true, Help2 = true, Help3 = true, AnswerCheck = false;
    private final int[] ButtonArr = {R.id.ButtonAnsA, R.id.ButtonAnsB, R.id.ButtonAnsC, R.id.ButtonAnsD,
            R.id.ButtonStopMusic, R.id.ButtonHelp1, R.id.ButtonHelp2, R.id.ButtonHelp3, R.id.ButtonNext};
    private int pos = 1, pro = 0, HighScore = 0, id = 0, num = 15;
    private String diff = "", style = "", path = "";
    private final ArrayList<QuestionNare> QuesList = new ArrayList<>();
    private final ArrayList<String> Countries = new ArrayList<>();
    private TextToSpeech AnswerTTS;
    private ProgressBar CountdownPB;
    private CountDownTimer CountdownTimer;
    private SoundPool Soundpool;
    int soundStartGame, soundGoodEG, soundBadEG, soundRightAns, soundWrongAns;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AudioAttributes attributes = new AudioAttributes.Builder()
                .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                .setFlags(AudioAttributes.FLAG_AUDIBILITY_ENFORCED)
                .setUsage(AudioAttributes.USAGE_GAME)
                .build();
        Soundpool = new SoundPool.Builder()
                .setAudioAttributes(attributes)
                .setMaxStreams(1).build();
        soundStartGame = Soundpool.load(this, R.raw.startgame, 1);
        soundGoodEG = Soundpool.load(this, R.raw.goodendgame, 1);
        soundBadEG = Soundpool.load(this, R.raw.badendgame, 1);
        soundWrongAns = Soundpool.load(this, R.raw.wrongans, 1);
        soundRightAns = Soundpool.load(this, R.raw.rightans, 1);
        AnswerTTS = new TextToSpeech(this, status -> {
            if (status == TextToSpeech.SUCCESS)
                AnswerTTS.setLanguage(Locale.ENGLISH);
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
                    BackgroundMusic.pause();
                    Soundpool.play(soundStartGame, 1, 1, 0, 0, 1);
                    ReadData(QuesList);
                    try {
                        Thread.sleep(5000);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    BackgroundMusic.start();
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
            if (idCheck == R.id.Layout2_TextQuestion)
                AnswerTTS.speak(QuesList.get(id).Ques, TextToSpeech.QUEUE_FLUSH, null);
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
            int temp1 = 0, temp2 = 0;
            while (temp1 == temp2){
                temp1 = RandomAnswerID(new int[]{R.id.ButtonAnsA, R.id.ButtonAnsB, R.id.ButtonAnsC, R.id.ButtonAnsD}, AnswerIDtoButtonID(QuesList.get(id).Answer));
                temp2 = RandomAnswerID(new int[]{R.id.ButtonAnsA, R.id.ButtonAnsB, R.id.ButtonAnsC, R.id.ButtonAnsD}, AnswerIDtoButtonID(QuesList.get(id).Answer));
            }
            DisableAnswerButton(new int[]{temp1, temp2});
        }
        else if (idCheck == R.id.ButtonHelp3) {
            Help3 = false;
            DisableAnswerButton(new int[]{R.id.ButtonAnsA, R.id.ButtonAnsB, R.id.ButtonAnsC, R.id.ButtonAnsD});
        }
        else if (idCheck == R.id.ButtonNext) {
            pos++;
            if (CountdownTimer != null)
                CountdownTimer.cancel();
            QuesList.remove(id);
            NextGameplay(pos);
        }
        else {
            if (!AnswerCheck) {
                if (CheckAnswer(idCheck))
                    pro = pro + 1;
                else {
                    if (Objects.equals(style, "suddendeath"))
                        pos = num;
                }
            }
            if (pos >= num) {
                if (pro >= 10)
                    Soundpool.play(soundGoodEG, 1, 1, 0, 0, 1);
                else
                    Soundpool.play(soundBadEG, 1, 1, 0, 0, 1);
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

        }
    }

    private boolean CheckAnswer(int idCheck) {
        if (CountdownTimer != null)
            CountdownTimer.cancel();
        DisableAnswerButton(new int[]{R.id.ButtonAnsA, R.id.ButtonAnsB, R.id.ButtonAnsC, R.id.ButtonAnsD});
        DisableHelpButton();
        boolean correct = false;
        if (idCheck == R.id.ButtonAnsA) {
            if (QuesList.get(id).Answer.compareTo("0") == 0)
                correct = true;
        } else if (idCheck == R.id.ButtonAnsB) {
            if (QuesList.get(id).Answer.compareTo("1") == 0)
                correct = true;
        } else if (idCheck == R.id.ButtonAnsC) {
            if (QuesList.get(id).Answer.compareTo("2") == 0)
                correct = true;
        } else if (idCheck == R.id.ButtonAnsD) {
            if (QuesList.get(id).Answer.compareTo("3") == 0)
                correct = true;
        }
        if (correct) {
            Soundpool.play(soundRightAns, 1, 1, 0, 0, (float)1.5);
        }
        else {
            Soundpool.play(soundWrongAns, 1, 1, 0, 0, (float)1.5);
        }
        return correct;
    }
    private void NextGameplay(int pos)
    {
        AnswerCheck = false;
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
        ((Button) findViewById(R.id.ButtonHelp3)).setEnabled(Help3);
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
        ((Button) findViewById(R.id.ButtonHelp3)).setEnabled(Help3);
        StartCountDown();
        for (int id : ButtonArr) {
            View v = findViewById(id);
            v.setOnClickListener(this);
        }
        View z = findViewById(R.id.Layout2_TextQuestion);
        z.setOnLongClickListener(this);
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
        id = RandomExamID(L.size());
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
        id = RandomExamID(L.size());
        TextView Result;
        Result = findViewById(R.id.TextResult);
        ((TextView) findViewById(R.id.TextQuesID)).setText("QUESTION " + pos);
        ((Button) findViewById(R.id.Layout2_TextQuestion)).setText(L.get(id).Ques);
        int index = 0;
        StorageReference storeref;
        for (String context : new String[]{L.get(id).AnswerA, L.get(id).AnswerB, L.get(id).AnswerC, L.get(id).AnswerD}) {
            storeref = FirebaseStorage.getInstance().getReference(path + "/" + context + ".png");
            try {
                File tempfile = File.createTempFile("tempfile", ".png");
                int finalIndex = index;
                storeref.getFile(tempfile).addOnSuccessListener(taskSnapshot -> {
                    Bitmap bitmap = BitmapFactory.decodeFile(tempfile.getAbsolutePath());
                    ((ImageButton) findViewById(ButtonArr[finalIndex])).setImageBitmap(bitmap);
                }).addOnFailureListener(e -> Toast.makeText(activity_gameplay.this, "Error _ Game Layout 2", Toast.LENGTH_SHORT).show());
            }
            catch (IOException e) {
                e.printStackTrace();
            }
            index++;
        }
        Result.setText("" + pro);
        /*storeref = FirebaseStorage.getInstance().getReference(path + "/" + L.get(id).AnswerA + ".png");
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
        }*/
        /*
        ((ImageButton) findViewById(R.id.IButtonLayout2AnsA)).setImageResource(this.getResources().getIdentifier(L.get(id).AnswerA + ".png", null, this.getPackageName()));
        ((ImageButton) findViewById(R.id.IButtonLayout2AnsB)).setImageResource(this.getResources().getIdentifier(L.get(id).AnswerB + ".png", null, this.getPackageName()));
        ((ImageButton) findViewById(R.id.IButtonLayout2AnsC)).setImageResource(this.getResources().getIdentifier(L.get(id).AnswerC + ".png", null, this.getPackageName()));
        ((ImageButton) findViewById(R.id.IButtonLayout2AnsD)).setImageResource(this.getResources().getIdentifier(L.get(id).AnswerD + ".png", null, this.getPackageName()));
        */
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
    private int RandomExamID(int max) {
        return (int) ((Math.random() * (max)) + 0);
    }
    private int RandomAnswerID(int[] arr, int ans)
    {
        int temp;
        do {
            temp = (int) (Math.random() * arr.length);
        } while (arr[temp] == ans);
        return arr[temp];
    }
    private int RandomQuesID(ArrayList<String> answer)
    {
        return (int) ((Math.random() * (answer.size())));
    }
    private String RandomCountry(ArrayList<String> data) {
        int randomID = (int) ((Math.random() * (data.size())));
        return data.get(randomID);
    }
    private int AnswerIDtoButtonID(String id) {
        if (Objects.equals(id, "0"))
            return R.id.ButtonAnsA;
        else if (Objects.equals(id, "1"))
            return R.id.ButtonAnsB;
        else if (Objects.equals(id, "2"))
            return R.id.ButtonAnsC;
        else
            return R.id.ButtonAnsD;
    }
    private void DisableAnswerButton(int[] arr) {
        for (int j : arr) {
            if (j == AnswerIDtoButtonID(QuesList.get(id).Answer))
                continue;
            if (pos % 2 == 0) {
                ((ImageButton) findViewById(j)).setImageBitmap(null);
                ((ImageButton) findViewById(j)).setEnabled(false);
            }
            else {
                ((Button) findViewById(j)).setText("");
                ((Button) findViewById(j)).setEnabled(false);
            }
        }
    }
    private void DisableHelpButton() {
        for (int i : new int[]{R.id.ButtonHelp1, R.id.ButtonHelp2, R.id.ButtonHelp3}) {
            ((Button) findViewById(i)).setEnabled(false);
        }
    }
}
