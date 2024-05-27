package com.example.alarm3try;

import androidx.appcompat.app.AppCompatActivity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;
import androidx.recyclerview.widget.LinearLayoutManager;
import com.example.alarm3try.databinding.ActivityMainBinding;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private List<Alarm> alarmList;
    private AlarmAdapter alarmAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityMainBinding binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        alarmList = AlarmUtils.getAlarms(this);
        alarmAdapter = new AlarmAdapter(this, alarmList);

        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        binding.recyclerView.setAdapter(alarmAdapter);

        binding.btnNotification1.setOnClickListener(view -> {
            setAlarm(1);
        });

        binding.btnNotification3.setOnClickListener(view -> {
            setAlarm(3);
        });
    }

    private void setAlarm(int minute) {
        int alarmId = (int) System.currentTimeMillis();
        String title = "알림 테스트";
        String message = minute + "분 전에 알림을 설정했습니다.";
        long triggerTime = System.currentTimeMillis() + (minute * 60 * 1000);

        Alarm alarm = new Alarm();
        alarm.setId(alarmId);
        alarm.setTitle(title);
        alarm.setMessage(message);
        alarm.setTriggerTime(triggerTime);
        alarm.setEnabled(true);

        alarmList.add(alarm);
        AlarmUtils.saveAlarms(this, alarmList);
        alarmAdapter.notifyDataSetChanged();

        Intent intent = new Intent(this, NotificationReceiver.class);
        intent.putExtra("alarm_no", alarmId);
        intent.putExtra("title", title);
        intent.putExtra("message", message);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, alarmId, intent, PendingIntent.FLAG_IMMUTABLE);

        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        alarmManager.set(AlarmManager.RTC_WAKEUP, triggerTime, pendingIntent);

        Toast.makeText(this, "알림을 표시하기 위해 알람 설정 완료", Toast.LENGTH_SHORT).show();
    }
}

//public class MainActivity extends AppCompatActivity {
//
//    private int alarmNo;        // 실제 적용하기 위해서는 별도로 관리해야함 (SharedPreferences 이용)
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        ActivityMainBinding binding = ActivityMainBinding.inflate(getLayoutInflater());
//        setContentView(binding.getRoot());
//
//        binding.btnNotification1.setOnClickListener(view -> {
//            // 1분뒤 알림 (알림을 표시하기 위해 알람 설정)
//            setAlarm(1);
//        });
//
//        binding.btnNotification3.setOnClickListener(view -> {
//            // 3분뒤 알람 (알림을 표시하기 위해 알람 설정)
//            setAlarm(3);
//        });
//    }
//
//    /* 알림을 표시하기 위해 알람 설정 */
//    private void setAlarm(int minute) {
//        this.alarmNo++;
//
//        // requestCode -> alarmNo 로 설정
//        Intent intent = new Intent(this, NotificationReceiver.class);
//        intent.putExtra("alarm_no", alarmNo);   // 알람번호
//        intent.putExtra("title", "알림 테스트"); // 제목
//        intent.putExtra("message", minute + "분전에 알림을 설정했습니다."); // 메시지
//        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, this.alarmNo, intent, PendingIntent.FLAG_IMMUTABLE);
//
//        // 알람 시간 설정
//        Calendar calendar = Calendar.getInstance();
//        calendar.add(Calendar.MINUTE, minute);
//
//        // 정확한 시간 알람 설정
//        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
//
//        // 알림 시간 설정
//        long triggerTime = System.currentTimeMillis() + ((long)minute * 60 * 1000); // 현재 시간으로부터 'minute' 분 후
//        alarmManager.set(AlarmManager.RTC_WAKEUP, triggerTime, pendingIntent);
//        //alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
//        // 여기 왜 에러임???
//
//        Toast.makeText(this, "알림을 표시하기 위해 알람 설정 완료", Toast.LENGTH_SHORT).show();
//    }
//}