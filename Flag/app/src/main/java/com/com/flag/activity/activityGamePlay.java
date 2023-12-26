package com.com.flag.activity;
import com.com.flag.R;
import com.com.flag.entity.*;

import static com.com.flag.MainActivity.BackgroundMusic;
import static com.com.flag.MainActivity.PlayerName;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.media.AudioManager;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.Nullable;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.database.DatabaseReference;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;


public class activityGamePlay extends Activity implements View.OnClickListener, View.OnLongClickListener {
    //private boolean Help1 = true, Help2 = true, Help3 = true;
    //private final ArrayList<QuestionNare> QuesList = new ArrayList<>();
    //private final ArrayList<String> Countries = new ArrayList<>();
    //private SoundPool Soundpool;
    //int soundStartGame, soundGoodEG, soundBadEG, soundRightAns, soundWrongAns;
    //private TextToSpeech AnswerTTS;
    private boolean AnswerCheck = false;
    private final int[] ButtonArr = {R.id.ButtonAnsA, R.id.ButtonAnsB, R.id.ButtonAnsC, R.id.ButtonAnsD,
            R.id.ButtonStopMusic, R.id.ButtonHelp1, R.id.ButtonHelp2, R.id.ButtonHelp3, R.id.ButtonNext};
    private final int quiz = 20, survival = 1000;
    private int position = 1, id = 0, num = quiz;
    private int Progress = 0, HighScore = 0;
    private String path = "";
    private ProgressBar CountdownPB;
    private CountDownTimer CountdownTimer;
    public SoundEffect soundEffect = new SoundEffect();
    public QuestionList questionList = new QuestionList();
    public Support support = new Support();
    public TextSpeech textSpeech = new TextSpeech();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        soundEffect.GenerateSound(this);
        textSpeech.GenerateTextSpeech(this);
        /*AudioAttributes attributes = new AudioAttributes.Builder()
                .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                .setFlags(AudioAttributes.CONTENT_TYPE_MUSIC)
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
        });*/
        Intent intent = getIntent();
        if (intent != null)
        {
            path = intent.getStringExtra("path");
            //diff = intent.getStringExtra("diff");
            //style = intent.getStringExtra("style");
        }
        LoadHighScore();
        /*if (Objects.equals(diff, "hard"))
            path = "Data_Hard";
            //questionList.GenerateQuestion(questionList.CountriesHard);
        else if (Objects.equals(diff, "expert"))
            path = "Data_Expert";
            //questionList.GenerateQuestion(questionList.CountriesExpert);
        else
            path = "Data_Easy";
            //questionList.GenerateQuestion(questionList.CountriesEasy);*/
        if (Objects.equals(path, "Data_Survival"))
        {
            num = survival;
            //questionList.GenerateQuestion(questionList.CountriesSurvival);
        }
        StorageReference listRef = FirebaseStorage.getInstance().getReference().child(path);
        listRef.listAll()
                .addOnSuccessListener(listResult -> {
                    for (StorageReference item : listResult.getItems()) {
                        questionList.Countries.add((item.getName().split("\\."))[0]);
                    }
                    //if (BackgroundMusic != null)
                    //    BackgroundMusic.pause();
                    setVolumeControlStream(AudioManager.STREAM_MUSIC);
                    soundEffect.Effect.play(soundEffect.soundStartGame, 1, 1, 1, 0, 1);
                    try {
                        Thread.sleep(3000);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    //BackgroundMusic.start();
                    //ReadData(questionList.List);
                    questionList.GenerateQuestion();
                    GameplayLayout1();
                })
                .addOnFailureListener(e ->
                        Toast.makeText(activityGamePlay.this, "Error _ Retrieving Data", Toast.LENGTH_SHORT).show());

    }
    @Override
    public boolean onLongClick(View v) {
        int idCheck = v.getId();
        if (position % 2 == 0) {
            if (idCheck == R.id.Layout2_TextQuestion)
                textSpeech.ToSpeech(questionList.List.get(id).Context);
                //AnswerTTS.speak(questionList.List.get(id).Context, TextToSpeech.QUEUE_FLUSH, null);
        }
        else {
            if (idCheck == R.id.ButtonAnsA) {
                textSpeech.ToSpeech(questionList.List.get(id).AnswerA);
                //AnswerTTS.speak(questionList.List.get(id).AnswerA, TextToSpeech.QUEUE_FLUSH, null);
            } else if (idCheck == R.id.ButtonAnsB) {
                textSpeech.ToSpeech(questionList.List.get(id).AnswerB);
                //AnswerTTS.speak(questionList.List.get(id).AnswerB, TextToSpeech.QUEUE_FLUSH, null);
            } else if (idCheck == R.id.ButtonAnsC) {
                textSpeech.ToSpeech(questionList.List.get(id).AnswerC);
                //AnswerTTS.speak(questionList.List.get(id).AnswerC, TextToSpeech.QUEUE_FLUSH, null);
            } else if (idCheck == R.id.ButtonAnsD) {
                textSpeech.ToSpeech(questionList.List.get(id).AnswerD);
                //AnswerTTS.speak(questionList.List.get(id).AnswerD, TextToSpeech.QUEUE_FLUSH, null);
            }
        }
        return false;
    }
    @Override
    public void onClick(View v) {
        int idCheck = v.getId();
        if (idCheck == R.id.ButtonStopMusic) {
            if (BackgroundMusic.isPlaying()) {
                BackgroundMusic.pause();
            }
            else {
                BackgroundMusic.start();
            }
        }
        else if (idCheck == R.id.ButtonHelp1) {
            if (CountdownTimer != null)
                CountdownTimer.cancel();
            support.getHelp1();
            NextGameplay(position);
        }
        else if (idCheck == R.id.ButtonHelp2) {
            DisableAnswerButton(support.getHelp2(questionList, id), -1);
        }
        else if (idCheck == R.id.ButtonHelp3) {
            DisableAnswerButton(support.getHelp3(), -1);
        }
        else if (idCheck == R.id.ButtonNext) {
            position++;
            if (CountdownTimer != null)
                CountdownTimer.cancel();
            questionList.List.remove(id);
            NextGameplay(position);
        }
        else {
            if (!AnswerCheck) {
                if (CheckAnswer(idCheck))
                    Progress = Progress + 1;
                else {
                    if (num == survival)
                        position = num;
                }
            }
            if (position >= num) {
                if (Progress >= 10)
                    soundEffect.Effect.play(soundEffect.soundGoodEG, 1, 1, 0, 0, 1);
                else
                    soundEffect.Effect.play(soundEffect.soundBadEG, 1, 1, 0, 0, 1);
                Intent intent = new Intent(activityGamePlay.this, activityResult.class);
                Bundle bundle = new Bundle();
                bundle.putInt("Progress", Progress);
                bundle.putInt("QuestionID", position);
                intent.putExtra("MyPackage", bundle);
                startActivity(intent);
                if (Progress > HighScore) {
                    HighScore = Progress;
                    SaveHighScore();
                }
                finish();
            }

        }
    }

    private boolean CheckAnswer(int idCheck) {
        if (CountdownTimer != null)
            CountdownTimer.cancel();
        DisableAnswerButton(new int[]{R.id.ButtonAnsA, R.id.ButtonAnsB, R.id.ButtonAnsC, R.id.ButtonAnsD}, idCheck);
        DisableHelpButton();
        boolean correct = false;
        if (idCheck == R.id.ButtonAnsA) {
            if (questionList.List.get(id).Answer.compareTo("0") == 0)
                correct = true;
        } else if (idCheck == R.id.ButtonAnsB) {
            if (questionList.List.get(id).Answer.compareTo("1") == 0)
                correct = true;
        } else if (idCheck == R.id.ButtonAnsC) {
            if (questionList.List.get(id).Answer.compareTo("2") == 0)
                correct = true;
        } else if (idCheck == R.id.ButtonAnsD) {
            if (questionList.List.get(id).Answer.compareTo("3") == 0)
                correct = true;
        }
        setVolumeControlStream(AudioManager.STREAM_MUSIC);
        if (correct) {
            soundEffect.Effect.play(soundEffect.soundRightAns, 1, 1, 0, 0, (float)1.5);
        }
        else {
            soundEffect.Effect.play(soundEffect.soundWrongAns, 1, 1, 0, 0, (float)1.5);
        }
        return correct;
    }
    private void NextGameplay(int pos)
    {
        AnswerCheck = false;
        if (pos % 2 == 0)
            GameplayLayout2();
        else
            GameplayLayout1();
    }
    private void GameplayLayout1(){
        setContentView(R.layout.activity_gamelayout_1);
        DisplayLayout1(questionList.List);
        EnableHelpButton();
        StartCountDown();
        for (int id : ButtonArr) {
            View v = findViewById(id);
            v.setOnClickListener(this);
            v.setOnLongClickListener(this);
        }
    }
    private void GameplayLayout2(){
        setContentView(R.layout.activity_gamelayout_2);
        DisplayLayout2(questionList.List);
        EnableHelpButton();
        StartCountDown();
        for (int id : ButtonArr) {
            View v = findViewById(id);
            v.setOnClickListener(this);
        }
        View z = findViewById(R.id.Layout2_TextQuestion);
        z.setOnLongClickListener(this);
    }

    @SuppressLint({"SetTextI18n", "DiscouragedApi"})
    private void DisplayLayout1(ArrayList<QuestionNare> L) {
        id = Utility.RandomExamID(L.size());
        TextView Result;
        Result = findViewById(R.id.TextResult);
        ((TextView) findViewById(R.id.TextQuesID)).setText("QUESTION " + position);
        StorageReference storeref;
        storeref = FirebaseStorage.getInstance().getReference(path + "/" + L.get(id).Context + ".png");
        try {
            File tempfile = File.createTempFile("tempfile", ".png");
            storeref.getFile(tempfile).addOnSuccessListener(taskSnapshot -> {
                Bitmap bitmap = BitmapFactory.decodeFile(tempfile.getAbsolutePath());
                ((ImageView) findViewById(R.id.Layout1_ImgQuestion)).setImageBitmap(bitmap);
            }).addOnFailureListener(e -> Toast.makeText(activityGamePlay.this, "Error _ Game Layout 1", Toast.LENGTH_SHORT).show());
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        ((Button) findViewById(R.id.ButtonAnsA)).setText(L.get(id).AnswerA);
        ((Button) findViewById(R.id.ButtonAnsB)).setText(L.get(id).AnswerB);
        ((Button) findViewById(R.id.ButtonAnsC)).setText(L.get(id).AnswerC);
        ((Button) findViewById(R.id.ButtonAnsD)).setText(L.get(id).AnswerD);
        Result.setText("" + Progress);
    }
    @SuppressLint({"SetTextI18n", "DiscouragedApi"})
    private void DisplayLayout2(ArrayList<QuestionNare> L) {
        id = Utility.RandomExamID(L.size());
        TextView Result;
        Result = findViewById(R.id.TextResult);
        ((TextView) findViewById(R.id.TextQuesID)).setText("QUESTION " + position);
        ((Button) findViewById(R.id.Layout2_TextQuestion)).setText(L.get(id).Context);
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
                }).addOnFailureListener(e -> Toast.makeText(activityGamePlay.this, "Error _ Game Layout 2", Toast.LENGTH_SHORT).show());
            } catch (IOException e) {
                e.printStackTrace();
            }
            index++;
        }
        Result.setText("" + Progress);
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
        Users users = new Users(PlayerName, ""+HighScore);
        FirebaseDatabase db = FirebaseDatabase.getInstance();
        DatabaseReference reference = db.getReference("Users");
        reference.child(users.name()).setValue(users);
    }

    @SuppressLint("DiscouragedApi")
    private void DisableAnswerButton(int[] arr, int idCheck) {
        for (int j : arr) {
            if (position % 2 == 0) {
                if (j == idCheck && idCheck != Utility.AnswerIDtoButtonID(questionList.List.get(id).Answer)) {
                    ((ImageButton) findViewById(j)).buildDrawingCache();
                    ((ImageButton) findViewById(j)).setImageBitmap(Utility.BlurBitmap(findViewById(j).getDrawingCache(), this));
                    ((ImageButton) findViewById(j)).setEnabled(false);
                }
                else if (j == Utility.AnswerIDtoButtonID(questionList.List.get(id).Answer)) {
                    ((ImageButton) findViewById(j)).setEnabled(false);
                }
                else {
                    if (j != Utility.AnswerIDtoButtonID(questionList.List.get(id).Answer)) {
                        ((ImageButton) findViewById(j)).setImageBitmap(null);
                        ((ImageButton) findViewById(j)).setEnabled(false);
                    }
                }
            }
            else {
                if (j == idCheck && idCheck != Utility.AnswerIDtoButtonID(questionList.List.get(id).Answer)) {
                    ((Button) findViewById(j)).setTextColor(Color.parseColor("#800000"));
                    ((Button) findViewById(j)).setEnabled(false);
                }
                else if (j == Utility.AnswerIDtoButtonID(questionList.List.get(id).Answer)) {
                    ((Button) findViewById(j)).setEnabled(false);
                }
                else {
                    if (j != Utility.AnswerIDtoButtonID(questionList.List.get(id).Answer)) {
                        ((Button) findViewById(j)).setText("");
                        ((Button) findViewById(j)).setEnabled(false);
                    }
                }
            }
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
    private void DisableHelpButton() {
        for (int i : new int[]{R.id.ButtonHelp1, R.id.ButtonHelp2, R.id.ButtonHelp3}) {
            ((Button) findViewById(i)).setEnabled(false);
        }
    }
    private void EnableHelpButton() {
        ((Button) findViewById(R.id.ButtonHelp1)).setEnabled(support.Help1);
        ((Button) findViewById(R.id.ButtonHelp2)).setEnabled(support.Help2);
        ((Button) findViewById(R.id.ButtonHelp3)).setEnabled(support.Help3);
    }
}
