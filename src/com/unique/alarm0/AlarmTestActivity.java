package com.unique.alarm0;

import java.util.Calendar;  

import android.app.Activity;  
import android.app.AlarmManager;  
import android.app.PendingIntent;  
import android.app.TimePickerDialog;  
import android.app.TimePickerDialog.OnTimeSetListener;  
import android.content.Intent;  
import android.os.Bundle;  
import android.view.View;  
import android.view.View.OnClickListener;  
import android.widget.Button;  
import android.widget.TimePicker;  
import android.widget.Toast;  
  
public class AlarmTestActivity extends Activity implements OnClickListener {  
    private Button setAlarmBtn;  
    // 声明闹钟管理器AlarmManager对象  
    private AlarmManager alarmManager; 
    static PendingIntent pendingIntent;
  
    @Override  
    public void onCreate(Bundle savedInstanceState) {  
        super.onCreate(savedInstanceState);  
        setContentView(R.layout.main);  
        // 获取AlarmManager服务对象  
        alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);  
        setAlarmBtn = (Button) findViewById(R.id.button1);  
        setAlarmBtn.setOnClickListener(this);  
    }  
  
    @Override  
    public void onClick(View v) {  
        // 当前设备上的系统时间  
        Calendar cal = Calendar.getInstance();  
        // 弹出设置时间的窗口  
        new TimePickerDialog(this, new OnTimeSetListener() {  
  
            @Override  
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {  
                // 启动指定组件  
                Intent intent = new Intent(AlarmTestActivity.this,  
                        AlarmActivity.class);  
                // 创建PendingIntent对象，封装Intent  
                pendingIntent = PendingIntent.getActivity(  
                        AlarmTestActivity.this, 0, intent, 0);  
                Calendar setCal = Calendar.getInstance();  
                // 根据用户选择的时间设置定时器时间  
                setCal.set(Calendar.HOUR_OF_DAY, hourOfDay);  
                setCal.set(Calendar.MINUTE, minute);  
                // 设置AlarmManager将在Calendar对应的时间启动指定组件  
                alarmManager.set(AlarmManager.RTC_WAKEUP,  
                        setCal.getTimeInMillis(), pendingIntent);  
                // 显示闹铃设置成功的提示信息  
                Toast.makeText(AlarmTestActivity.this, "闹铃设置成功啦",  
                        Toast.LENGTH_SHORT).show();  
                alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, 0, 200000,
						pendingIntent); // 200秒重复
            }  
        }, cal.get(Calendar.HOUR_OF_DAY), cal.get(Calendar.MINUTE), true)  
                .show();  
    }  
}  