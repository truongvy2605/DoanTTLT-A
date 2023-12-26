package com.com.flag.entity;

import android.content.Context;
import android.speech.tts.TextToSpeech;

import java.util.Locale;

public class TextSpeech {
    public TextToSpeech textToSpeech;

    public void GenerateTextSpeech(Context context) {
        this.textToSpeech = new TextToSpeech(context, status -> {
            if (status == TextToSpeech.SUCCESS)
                textToSpeech.setLanguage(Locale.ENGLISH);
        });
    }
    public void ToSpeech(String content) {
        textToSpeech.speak(content, TextToSpeech.QUEUE_FLUSH, null);
    }
}
