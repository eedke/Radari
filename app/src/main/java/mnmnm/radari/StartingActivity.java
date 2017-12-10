package mnmnm.radari;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.RingtoneManager;
import android.os.SystemClock;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import com.orm.SugarContext;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;

public class StartingActivity extends AppCompatActivity implements View.OnClickListener {
    Button btnStarting1, btnStarting2, btnStarting3, btnStarting4, btnStartingRefresh;
    TextView tvStarting1;
    CheckBox checkBox1, checkBox2, checkBox3, checkBox4;
    private SharedPreferences loginPreferences;
    private SharedPreferences.Editor loginPrefsEditor;
    private Boolean saveCheckbox1, saveCheckbox2, saveCheckbox3, saveCheckbox4;
    public GetDataFromJSON g;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SugarContext.init(this);
        setContentView(R.layout.activity_starting);

        btnStarting1 = findViewById(R.id.btnStarting1);
        btnStarting2 = findViewById(R.id.btnStarting2);
        btnStarting3 = findViewById(R.id.btnStarting3);
        btnStarting4 = findViewById(R.id.btnStarting4);
        btnStartingRefresh = findViewById(R.id.btnStartingRefresh);

        checkBox1 = findViewById(R.id.checkBox1);
        checkBox2 = findViewById(R.id.checkBox2);
        checkBox3 = findViewById(R.id.checkBox3);
        checkBox4 = findViewById(R.id.checkBox4);

        tvStarting1 = findViewById(R.id.tvStarting1);
        Calendar c = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        String strDate = sdf.format(c.getTime());
        tvStarting1.setText("ŽZH RADARI BOSNA I HERCEGOVINA\n\n" + "DANA " + strDate + (checkDate(strDate) ? (" IMA ZAKAZANIH RADARA") : (" NEMA ZAKAZANIH RADARA") ));

        btnStarting1.setOnClickListener(this);
        btnStarting2.setOnClickListener(this);
        btnStarting3.setOnClickListener(this);
        btnStarting4.setOnClickListener(this);
        btnStartingRefresh.setOnClickListener(this);

        loginPreferences = getSharedPreferences("loginPrefs", MODE_PRIVATE);
        loginPrefsEditor = loginPreferences.edit();


        saveCheckbox1 = loginPreferences.getBoolean("saveButton1", false);
        if (saveCheckbox1 == true) {
            checkBox1.setChecked(true);
        }
        saveCheckbox2 = loginPreferences.getBoolean("saveButton2", false);
        if (saveCheckbox2 == true) {
            checkBox2.setChecked(true);

        }
        saveCheckbox3 = loginPreferences.getBoolean("saveButton3", false);
        if (saveCheckbox3 == true) {
            checkBox3.setChecked(true);
        }
        saveCheckbox4 = loginPreferences.getBoolean("saveButton4", false);
        if (saveCheckbox4 == true) {
            checkBox4.setChecked(true);
        }

        checkBox1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    scheduleNotification(getApplicationContext(), 33);
                    setRecurringAlarm();
                }else {
                    String ns = Context.NOTIFICATION_SERVICE;
                    NotificationManager nMgr = (NotificationManager) getApplicationContext().getSystemService(ns);
                    nMgr.cancel(110);
                    nMgr.cancel(33);
                }
                loginPrefsEditor.putBoolean("saveButton1", b);
                loginPrefsEditor.commit();
            }
        });
        checkBox2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                loginPrefsEditor.putBoolean("saveButton2", b);
                loginPrefsEditor.commit();

            }
        });
        checkBox3.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                loginPrefsEditor.putBoolean("saveButton3", b);
                loginPrefsEditor.commit();
            }
        });
        checkBox4.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                loginPrefsEditor.putBoolean("saveButton4", b);
                loginPrefsEditor.commit();
            }
        });

        g = new GetDataFromJSON(StartingActivity.this);
    }

    @Override
    public void onClick(View v) {
        Intent i = new Intent(StartingActivity.this, MainActivity.class);

        switch (v.getId()){
            case R.id.btnStarting1:
                i.putExtra("opcina", "PU Grude");
                startActivity(i);
                break;
            case R.id.btnStarting2:
                i.putExtra("opcina", "PU Ljubuski");
                startActivity(i);
                break;
            case R.id.btnStarting3:
                i.putExtra("opcina", "PU Posusje");
                startActivity(i);
                break;
            case R.id.btnStarting4:
                i.putExtra("opcina", "PU Siroki Brijeg");
                startActivity(i);
                break;
            case R.id.btnStartingRefresh:
                g.getData();
                break;
        }
    }
    public boolean checkDate(String date1){

        List<SugarRadars> allRadars = SugarRadars.listAll(SugarRadars.class);

        if(allRadars.isEmpty()){
            return false;
        }else{
            for(int i = 0;i<allRadars.size();i++) {
                if (allRadars.get(i).getDate().equals(date1)) {
                    return true;
                }
            }
        }
        return false;
    }

    private void setRecurringAlarm(){
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 9);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        Intent intent1 = new Intent(StartingActivity.this, AlarmReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(StartingActivity.this, 110,intent1, PendingIntent.FLAG_UPDATE_CURRENT);
        AlarmManager am = (AlarmManager) StartingActivity.this.getSystemService(StartingActivity.this.ALARM_SERVICE);
        am.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_HALF_DAY, pendingIntent);

    }

    public void scheduleNotification(Context context, int notificationId) {//delay is after how much time(in millis) from current time you want to schedule the notification
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 6);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context)
                .setContentTitle("Raaaaaaaaadari")
                .setContentText("Danas ima/nema radara...nez dok ne napravim ovo")
                .setAutoCancel(true)
                .setSmallIcon(android.R.drawable.btn_plus)
                .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION));

        Intent intent = new Intent(context, StartingActivity.class);
        PendingIntent activity = PendingIntent.getActivity(context, notificationId, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(activity);

        Notification notification = builder.build();



        Intent notificationIntent = new Intent(context, MyNotificationPublisher.class);
        notificationIntent.putExtra(MyNotificationPublisher.NOTIFICATION_ID, notificationId);
        notificationIntent.putExtra(MyNotificationPublisher.NOTIFICATION, notification);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, notificationId, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,  calendar.getTimeInMillis(), AlarmManager.INTERVAL_HALF_DAY, pendingIntent);
    }
}
