package wnns.remind2speech;

import android.app.Activity;
import android.content.Context;
import android.speech.tts.TextToSpeech;
import android.speech.tts.UtteranceProgressListener;
import android.widget.Toast;

import java.io.File;

public class SpeechManager {

    TextToSpeech textToSpeech;
    Context context;

    public SpeechManager(Context _context){

        this.context = _context;
    }

    public void speechToFile(String _name, String _text, final Activity _activity) {

        final String name = _name;
        final String text = _text;

        textToSpeech = new TextToSpeech(context, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {

                textToSpeech.setOnUtteranceProgressListener(new UtteranceProgressListener() {

                    @Override
                    public void onStart(String utteranceId) {}

                    @Override
                    public void onDone(String utteranceId) {

                        textToSpeech.shutdown();
                        _activity.finish();
                    }

                    @Override
                    public void onError(String utteranceId) {

                        _activity.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {

                                Toast.makeText(_activity, context.getString(R.string.toast_reminder_set_error), Toast.LENGTH_LONG).show();
                            }
                        });
                    }
                });

                File speechFile = new File(context.getExternalFilesDir(null), name + ".mp3");
                textToSpeech.synthesizeToFile(text, null, speechFile, "ttsfile");
            }
        });
    }
}
