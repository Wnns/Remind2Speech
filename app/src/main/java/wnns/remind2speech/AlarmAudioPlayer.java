package wnns.remind2speech;

import android.content.Context;
import android.media.AudioAttributes;
import android.media.MediaPlayer;
import android.net.Uri;

import java.io.IOException;

public class AlarmAudioPlayer {

    MediaPlayer mediaPlayer;
    Context context;

    public AlarmAudioPlayer(Context context){

        this.context = context;

        mediaPlayer = new MediaPlayer();

        mediaPlayer.setAudioAttributes(new AudioAttributes.Builder()
                .setUsage(AudioAttributes.USAGE_NOTIFICATION)
                .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                .build());

        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {

                mp.release();
            }
        });
    }

    public void setAlarmSound(Uri soundUri){

        try {

            mediaPlayer.setDataSource(context, soundUri);
            mediaPlayer.prepare();
        } catch (IOException e) {
        }
    }

    public void playAlarm(){

        mediaPlayer.start();
    }
}
