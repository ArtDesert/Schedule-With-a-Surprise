package com.example.myapplication;
import com.example.myapplication.*;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import java.io.IOException;
import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabaseLockedException;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import java.util.Calendar;
import java.util.Date;
import org.w3c.dom.Text;

import java.lang.reflect.Array;
import java.util.GregorianCalendar;

public class MainActivity extends AppCompatActivity {//implements AdapterView.OnItemSelectedListener{

    private TextView selectedTextView = null;
    int ParsWeek; //Отображаемая неделя
    int ParsDay; //Отображаемый день
    int nowYear;
    int nowDay;
    int nowMonth;
    String ParsDate;
    TextView mInfoTextView; // кнопка выбора студент или школьник
    TextView LableWeek; // Отображение недели
    TextView LableMonth; // Отображение месяца
    Button LableDay;
    String MonthName;
    Button button_classic; // кнопка выбора классический тип расписания
    Button button_even_odd; // кнопка выбора типа расписания с различным расписанием на чётной и нечётной неделях
    RadioButton RB1; // студент
    RadioButton RB2; // школьник
    Boolean Net = true;
    Calendar calendar;
    //Button button4;
    int day;
    int number;
    boolean week = false;
    DBHelper dbHelper;
    SQLiteDatabase database;
    Schedule schedule;
    String[][] Array = new String[6][8];
    String[][] Array2 = new String[6][8];

    Date currentTime;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dbHelper = new DBHelper(this);
        database = dbHelper.getWritableDatabase();
        //dbHelper.onUpgrade(database,0,0);
        Window w = getWindow();
       // calendar = new GregorianCalendar(2023,0,11);
        calendar = new GregorianCalendar(Calendar.getInstance().get(Calendar.YEAR), Calendar.getInstance().get(Calendar.MONTH),Calendar.getInstance().get(Calendar.DAY_OF_WEEK)==1?Calendar.getInstance().get(Calendar.DAY_OF_MONTH)+1 : Calendar.getInstance().get(Calendar.DAY_OF_MONTH));
        nowYear = calendar.get(Calendar.YEAR);
        nowMonth = calendar.get(Calendar.MONTH);
        nowDay = calendar.get(Calendar.DAY_OF_MONTH);
        w.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
        setContentView(R.layout.main_schedule_form); // начальная сцена
        ScheduleList();
        CatTask catTask = new CatTask();
        catTask.execute();



    }

    public boolean isNetworkAvailable(Context context) {
            ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = cm.getActiveNetworkInfo();
            if (networkInfo != null && networkInfo.isConnected()) {
                return true;
            } else {
                return false;
            }
    }

    class CatTask extends AsyncTask<Void, Void, Void> {


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            if (isNetworkAvailable(getBaseContext())) {
                try {
                    schedule = new Schedule("https://ssau.ru/rasp?groupId=531873790");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                Net = false;
            }


            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            if (Net) {
                dbHelper.onUpgrade(database,0,0);
               /* mInfoTextView.setText(schedule.getWeeks(2).getDays(0).getDate());
                mInfoTextView = findViewById(R.id.class2);
                mInfoTextView.setText(schedule.getWeeks(0).getDays(3).getDayOfTheWeek());
                mInfoTextView = findViewById(R.id.class3);
                mInfoTextView.setText(schedule.getWeeks(1).getDays(2).getSubjects(3).getType());
                mInfoTextView = findViewById(R.id.class5);
                mInfoTextView.setText(temp);
                mInfoTextView = findViewById(R.id.class4);*/
               /* database.execSQL("CREATE TABLE IF NOT EXISTS "+ TABLE_PARSING_NAME +
                        " (" + KEY_PARSING_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                        KEY_PARSING_NUMBER + " INTEGER NOT NULL," +
                        KEY_PARSING_SUBJECT + " VARCHAR(30) NOT NULl," +
                        KEY_PARSING_TEACHER + " VARCHAR(30) NOT NULL," +
                        KEY_PARSING_DAY + " INTEGER NOT NULL," +
                        KEY_PARSING_DATE + " VARCHAR(10) NOT NULL," +
                        KEY_PARSING_CABINET + " VARCHAR(15) NOT NULL," +
                        KEY_PARSING_TYPE_SUBJECT + " VARCHAR(12) NOT NULL," +
                        KEY_PARSING_TIME + " VARCHAR(12)," +
                        KEY_PARSING_NOTE + " TEXT," +
                        KEY_PARSING_WEEK + " INTEGER NOT NULL)" );*/
                //database.execSQL("DELETE FROM PARSING");

                for (int week = 0; week < 22; ++week) {
                    for (int day = 0; day < 6; ++day) {
                        for (int subjectNum = 0; subjectNum < schedule.getWeeks(week).getDays(day).getSubjectsCount(); ++subjectNum) {
                            database.execSQL("INSERT INTO PARSING(NUMBER, SUBJECT, TEACHER, DAY, DATE, CABINET, TYPE_SUBJECT, TIME, WEEK)" +
                                    "VALUES (" + Integer.toString(subjectNum+1) + ", '" + schedule.getWeeks(week).getDays(day).getSubjects(subjectNum).getName(0) + "', '" + schedule.getWeeks(week).getDays(day).getSubjects(subjectNum).getLecturer(0) + "', " +
                                    Integer.toString(day) + ", '" +
                                    schedule.getWeeks(week).getDays(day).getDate() + "', '" +
                                    schedule.getWeeks(week).getDays(day).getSubjects(subjectNum).getLocation(0) + "', '" +
                                    schedule.getWeeks(week).getDays(day).getSubjects(subjectNum).getType() + "', '" +
                                    schedule.getWeeks(week).getDays(day).getSubjects(subjectNum).getTime() + "', " +
                                    schedule.getWeeks(week).getWeekNum() + ")");
                        }
                    }

                }
/*
                Cursor c = database.rawQuery("SELECT * FROM PARSING AS PS where DATE like '" + ParsDate + "'", null);
                 c.moveToFirst();
                mInfoTextView.setText(Integer.toString(c.getInt(1))+c.getString(2)+c.getString(8)+c.getCount());
                ClearTV();
                for (int sub = 0; sub < c.getCount(); ++sub){
                    mInfoTextView.findViewById(R.id.class1);
                    if (c.getString(8).equals("08:00 09:35"))    {mInfoTextView=findViewById(R.id.class1);}
                    else if(c.getString(8).equals("09:45 11:20")){mInfoTextView = findViewById(R.id.class2);}
                    else if(c.getString(8).equals( "11:30 13:05")){mInfoTextView = findViewById(R.id.class3);}
                    else if(c.getString(8).equals("13:30 15:05")){mInfoTextView = findViewById(R.id.class4);}
                    else if(c.getString(8).equals("15:15 16:50")){mInfoTextView = findViewById(R.id.class5);}
                    else if(c.getString(8).equals( "17:00 18:35")){mInfoTextView = findViewById(R.id.class6);};
                    mInfoTextView.setText(c.getString(2) + "\n" + c.getString(3) + "\n" + c.getString(6) + "\n" +c.getString(8));
                    c.moveToNext();
                }*/
                //mInfoTextView.setText(Integer.toString(c.getCount()));
            }
            else { /*mInfoTextView.setText("Нет инета!");*/}

        }
    }

    private void ScheduleList(){
        setContentView(R.layout.main_schedule_form); // начальная сцена
        ParsDay =  calendar.get(Calendar.DAY_OF_WEEK);
        ParsDate = (calendar.get(Calendar.DAY_OF_MONTH)<10?("0"+calendar.get(Calendar.DAY_OF_MONTH)):(calendar.get(Calendar.DAY_OF_MONTH))) + "." + ((calendar.get(Calendar.MONTH)+1)<10?("0"+(calendar.get(Calendar.MONTH)+1)):((calendar.get(Calendar.MONTH)+1))) + "." + Integer.toString(calendar.get(Calendar.YEAR));
        nowYear = calendar.get(Calendar.YEAR);
        nowMonth = calendar.get(Calendar.MONTH);
        nowDay = calendar.get(Calendar.DAY_OF_MONTH);
        TextView[] days = new TextView[]{
                findViewById(R.id.monday),
                findViewById(R.id.tuesday),
                findViewById(R.id.wednesday),
                findViewById(R.id.thursday),
                findViewById(R.id.friday),
                findViewById(R.id.saturday)
        };
        TextView[] numberDays = new TextView[]{
                findViewById(R.id.textView6),
                findViewById(R.id.textView5),
                findViewById(R.id.textView4),
                findViewById(R.id.textView3),
                findViewById(R.id.textView2),
                findViewById(R.id.textView)
        };
        int DeltaDay = ParsDay-2;
        for (int i = 0; i< numberDays.length;++i) {
            numberDays[i].setText(Integer.toString(new GregorianCalendar(nowYear,nowMonth, nowDay - DeltaDay + i).get(Calendar.DAY_OF_MONTH)));
        }
        days[ParsDay-2].setBackgroundResource(R.color.teal_200);
        Cursor c = database.rawQuery("SELECT * FROM PARSING AS PS where DATE like '" + ParsDate + "'", null);
        c.moveToFirst();
        ClearTV();
        LableWeek = findViewById(R.id.everOrAdd);
        LableMonth = findViewById(R.id.month);
        //days[ calendar.get(Calendar.DAY_OF_WEEK)-2].setBackgroundResource(R.color.teal_200);
        if(calendar.get(Calendar.MONTH)>=8){
            ParsWeek = (calendar.get(Calendar.DAY_OF_YEAR)-ParsDay+7 - 237)/7;
        }
        else{ParsWeek = (calendar.get(Calendar.DAY_OF_YEAR) - 26)/7;};
        IsMonth(calendar.get(Calendar.MONTH));
        LableWeek.setText(ParsWeek + " неделя");
        LableMonth.setText( MonthName + " " + calendar.get(Calendar.YEAR));
        for (int sub = 0; sub < c.getCount(); ++sub){
            if (c.getString(8).equals("08:00 09:35"))    {mInfoTextView=findViewById(R.id.class1);}
            else if(c.getString(8).equals("09:45 11:20")){mInfoTextView = findViewById(R.id.class2);}
            else if(c.getString(8).equals( "11:30 13:05")){mInfoTextView = findViewById(R.id.class3);}
            else if(c.getString(8).equals("13:30 15:05")){mInfoTextView = findViewById(R.id.class4);}
            else if(c.getString(8).equals("15:15 16:50")){mInfoTextView = findViewById(R.id.class5);}
            else if(c.getString(8).equals( "17:00 18:35")){mInfoTextView = findViewById(R.id.class6);};
            if (c.getString(7).equals("Лекция"))    {mInfoTextView.setTextColor(Color.GREEN);}
            else if(c.getString(7).equals("Практика")){mInfoTextView.setTextColor(Color.RED);}
            else if(c.getString(7).equals("Лабораторная")){mInfoTextView.setTextColor(Color.BLUE);}
            mInfoTextView.setText(c.getString(2) + "\n" + c.getString(3) + "\n" + c.getString(6) + "\n" +c.getString(8));
            c.moveToNext();
        }
        Button prevBt = (Button) findViewById(R.id.btnPrev);
        Button nextBt = (Button) findViewById(R.id.btnNext);
        prevBt.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                nowDay -=7;
                calendar = new GregorianCalendar(nowYear,nowMonth,nowDay);
                ScheduleList();
            }
        });
        nextBt.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                nowDay +=7;
                calendar = new GregorianCalendar(nowYear,nowMonth,nowDay);
                ScheduleList();
            }
        });
        for(int i = 0; i < days.length;++i)
        {
            int nday = i;
            TextView day = days[i];
            day.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View view)
                {
                    int DeltaDay = ParsDay-2;
                    int temp = nowDay - DeltaDay + nday;
                    nowDay = temp;
                    calendar = new GregorianCalendar(nowYear,nowMonth,nowDay);
                    ScheduleList();
                }
            });
        }
    }


    private void IsMonth(int mon){
        switch (mon){
            case 0:
                MonthName = "январь";
                break;
            case 1:
                MonthName = "февраль";
                break;
            case 2:
                MonthName = "март";
                break;
            case 3:
                MonthName = "апрель";
                break;
            case 4:
                MonthName = "май";
                break;
            case 5:
                MonthName = "июнь";
                break;
            case 6:
                MonthName = "июль";
                break;
            case 7:
                MonthName = "август";
                break;
            case 8:
                MonthName = "сентябрь";
                break;
            case 9:
                MonthName = "октябрь";
                break;
            case 10:
                MonthName = "ноябрь";
                break;
            case 11:
                MonthName = "декабрь";
                break;
        }
    }

    private void ClearTV(){
        mInfoTextView=findViewById(R.id.class1);
        mInfoTextView.setText(" ");

        mInfoTextView=findViewById(R.id.class2);
        mInfoTextView.setText(" ");
        mInfoTextView=findViewById(R.id.class3);
        mInfoTextView.setText(" ");
        mInfoTextView=findViewById(R.id.class4);
        mInfoTextView.setText(" ");
        mInfoTextView=findViewById(R.id.class5);
        mInfoTextView.setText(" ");
        mInfoTextView=findViewById(R.id.class6);
        mInfoTextView.setText(" ");}
}