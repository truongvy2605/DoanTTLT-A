package com.com.flag.entity;

import android.content.Context;
import android.media.MediaPlayer;

import com.com.flag.R;

public class SoundBackground {
    public MediaPlayer backgroundMusic;
    public boolean systemSound;
    public void GenerateBackgroundMusic(Context context) {
        backgroundMusic = MediaPlayer.create(context, R.raw.backgroundmusic);
        backgroundMusic.setLooping(true);
        systemSound = true;
    }
    public void Mute() {
        if (backgroundMusic != null) {
            if (backgroundMusic.isPlaying()) {
                backgroundMusic.pause();
                systemSound = false;
            }
            else if (!backgroundMusic.isPlaying()) {
                backgroundMusic.start();
                systemSound = true;
            }
        }
    }
    public void Pause() {
        if (backgroundMusic != null && systemSound)
            backgroundMusic.pause();
    }
    public void Play() {
        if (backgroundMusic != null && systemSound)
            backgroundMusic.start();
    }
}
