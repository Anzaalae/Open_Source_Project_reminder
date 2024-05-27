package com.example.alarm3try;


import android.content.Context;
import android.content.SharedPreferences;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class AlarmUtils {
    private static final String PREFS_NAME = "alarms_prefs";
    private static final String ALARMS_KEY = "alarms";

    public static void saveAlarms(Context context, List<Alarm> alarms) {
        SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        Gson gson = new Gson();
        String json = gson.toJson(alarms);
        editor.putString(ALARMS_KEY, json);
        editor.apply();
    }

    public static List<Alarm> getAlarms(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        Gson gson = new Gson();
        String json = prefs.getString(ALARMS_KEY, null);
        Type type = new TypeToken<List<Alarm>>() {}.getType();
        return json == null ? new ArrayList<>() : gson.fromJson(json, type);
    }
}