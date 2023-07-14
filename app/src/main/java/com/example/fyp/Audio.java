package com.example.fyp;

import android.content.Context;
import android.speech.tts.TextToSpeech;
import android.util.Log;

import java.util.Locale;

public class Audio implements TextToSpeech.OnInitListener {

    private final Context ctx;
    private final Locale loc;
    private final TextToSpeech tts;

    public Audio(Context context, String countryCode, String regionCode) {
        super();
        this.ctx = context;
        this.loc = new Locale(countryCode, regionCode);
        this.tts = new TextToSpeech(context, this);
    }

    @Override
    public void onInit(int status) {
        if (status == TextToSpeech.SUCCESS) {
            Log.i("TTS", "avaliable language : "+ tts.getAvailableLanguages().toString());
            int result = tts.setLanguage(loc);
            if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                Log.i("TTS", "This Language is not supported");
            } else {
                Log.i("TTS", "Initilization Success!");
            }
        } else {
            Log.e("TTS", "Initilization Failed!");
        }
    }

    public void generateAudio(String value) {
        if(!tts.isSpeaking()){

            tts.speak(value, TextToSpeech.QUEUE_FLUSH, null, null);
        }
    }
}
