package wnns.remind2speech;

import android.content.Context;
import android.text.TextUtils;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class AlarmUtils {

    String alarmError;
    Context context;

    public AlarmUtils(Context context){

        this.context = context;
    }

    public boolean isAlarmOk(String alarmTitle, String alarmText, Date alarmDate){

        if(!alarmDate.after(new Date())){

            alarmError = context.getString(R.string.toast_date_future);
            return false;
        }

        if(TextUtils.isEmpty(alarmTitle)){

            alarmError = context.getString(R.string.toast_empty_title);
            return false;
        }

        if(TextUtils.isEmpty(alarmText)){

            alarmError = context.getString(R.string.toast_empty_text);
            return false;
        }

        return true;
    }

    public String getAlarmError(){

        return alarmError;
    }
}
