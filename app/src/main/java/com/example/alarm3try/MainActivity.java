package com.example.alarm3try;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.example.alarm3try.databinding.ActivityMainBinding;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    private int alarmNo;        // 실제 적용하기 위해서는 별도로 관리해야함 (SharedPreferences 이용)

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityMainBinding binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.btnNotification1.setOnClickListener(view -> {
            // 1분뒤 알림 (알림을 표시하기 위해 알람 설정)
            setAlarm(1);
        });

        binding.btnNotification3.setOnClickListener(view -> {
            // 3분뒤 알람 (알림을 표시하기 위해 알람 설정)
            setAlarm(3);
        });
    }

    /* 알림을 표시하기 위해 알람 설정 */
    private void setAlarm(int minute) {
        this.alarmNo++;

        // requestCode -> alarmNo 로 설정
        Intent intent = new Intent(this, NotificationReceiver.class);
        intent.putExtra("alarm_no", alarmNo);   // 알람번호
        intent.putExtra("title", "알림 테스트"); // 제목
        intent.putExtra("message", minute + "분전에 알림을 설정했습니다."); // 메시지
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, this.alarmNo, intent, PendingIntent.FLAG_IMMUTABLE);

        // 알람 시간 설정
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MINUTE, minute);

        // 정확한 시간 알람 설정
        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
        // 여기 왜 에러임???

        Toast.makeText(this, "알림을 표시하기 위해 알람 설정 완료", Toast.LENGTH_SHORT).show();
    }
}

