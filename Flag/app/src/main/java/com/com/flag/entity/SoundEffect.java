package com.com.flag.entity;
import android.content.Context;
import android.media.AudioAttributes;
import android.media.SoundPool;
import com.com.flag.R;

public class SoundEffect {
    public SoundPool Effect;
    public int soundStartGame, soundGoodEG, soundBadEG, soundRightAns, soundWrongAns;

    public SoundEffect() {
        AudioAttributes attributes = new AudioAttributes.Builder()
                .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                .setFlags(AudioAttributes.CONTENT_TYPE_MUSIC)
                .setUsage(AudioAttributes.USAGE_GAME)
                .build();
        Effect = new SoundPool.Builder()
                .setAudioAttributes(attributes)
                .setMaxStreams(1).build();
    }
    public void GenerateSound(Context context) {
        soundStartGame = Effect.load(context, R.raw.startgame, 1);
        soundGoodEG = Effect.load(context, R.raw.goodendgame, 1);
        soundBadEG = Effect.load(context, R.raw.badendgame, 1);
        soundWrongAns = Effect.load(context, R.raw.wrongans, 1);
        soundRightAns = Effect.load(context, R.raw.rightans, 1);
    }
}
