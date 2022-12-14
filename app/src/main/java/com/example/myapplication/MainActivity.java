package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabaseLockedException;
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

import org.w3c.dom.Text;

import java.lang.reflect.Array;

public class MainActivity extends AppCompatActivity {//implements AdapterView.OnItemSelectedListener{

    private TextView selectedTextView = null;

    Button button_type; // кнопка выбора студент или школьник
    Button button_classic; // кнопка выбора классический тип расписания
    Button button_even_odd; // кнопка выбора типа расписания с различным расписанием на чётной и нечётной неделях
    RadioButton RB1; // студент
    RadioButton RB2; // школьник

    //Button button4;
    int day;
    int number;
    boolean week = false;
    DBHelper dbHelper;
    SQLiteDatabase database;

    String[][] Array = new String [6][8];
    String[][] Array2 = new String [6][8];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Window w = getWindow();
        for (int i = 0; i < Array.length;++i){

            for (int j = 0; j < Array[0].length;++j){
                Array[i][j] = null;
            }
        }
        w.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
        setContentView(R.layout.activity_main); // начальная сцена
        button_type = (Button) findViewById(R.id.btnConfirmMain);
        RB1 = (RadioButton)  findViewById(R.id.btn_student);
        RB2 = (RadioButton)  findViewById(R.id.btn_schoolboy);
        button_type.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                database = dbHelper.getWritableDatabase();
                ContentValues contentValues = new ContentValues();
                /*
                database.execSQL("INSERT INTO TEACHERS(NAME) VALUES ('Куприянов')");
                database.execSQL("INSERT INTO TEACHERS(NAME) VALUES ('Логанова')");
                database.execSQL("INSERT INTO CABINETS(NAME) VALUES ('219-1')");
                database.execSQL("INSERT INTO DAYS(NAME) VALUES ('Понедельник')");
                database.execSQL("INSERT INTO SUBJECTS(NAME) VALUES ('Основы трансляции')");
                database.execSQL("INSERT INTO TYPES_CLASS(NAME) VALUES ('Лекция')");
                database.execSQL("INSERT INTO SCHEDULE (SUBJECT, TEACHER, DAY, CABINET, TIME, NOTE, TYPE_CLASS, EVEN)\n" +
                        "VALUES (1, 2, 1, 1, '13:30', 'Лучшая каморка', 1, 1)");

                 */
                Cursor cursor =  database.query(DBHelper.TABLE_SCHEDULE_NAME, null,null,null,null,null,null);
                if (cursor.moveToFirst())
                {
                    int subjectIndex = cursor.getColumnIndex(DBHelper.KEY_SCHEDULE_SUBJECT);
                    int teacherIndex = cursor.getColumnIndex(DBHelper.KEY_SCHEDULE_TEACHER);
                    int dayIndex = cursor.getColumnIndex(DBHelper.KEY_SCHEDULE_DAY);
                    int cabinetIndex = cursor.getColumnIndex(DBHelper.KEY_SCHEDULE_CABINET);
                    int timeIndex = cursor.getColumnIndex(DBHelper.KEY_SCHEDULE_TIME);
                    int noteIndex = cursor.getColumnIndex(DBHelper.KEY_SCHEDULE_NOTE);
                    int type_classIndex = cursor.getColumnIndex(DBHelper.KEY_SCHEDULE_TYPE_CLASS);
                    int evenIndex = cursor.getColumnIndex(DBHelper.KEY_SCHEDULE_EVEN);
                    do
                    {
                        String str = "ID_SUBJECT =" + cursor.getInt(subjectIndex) +
                                ", ID_TEACHER = " + cursor.getInt(teacherIndex) +
                                ", ID_DAY = " + cursor.getInt(dayIndex) +
                                ", ID_CABINET = " + cursor.getInt(cabinetIndex) +
                                ", TIME = " + cursor.getString(timeIndex) +
                                ", NOTE = " + cursor.getString(noteIndex) +
                                ", ID_TYPE_CLASS = " + cursor.getInt(type_classIndex) +
                                ", EVEN = " + cursor.getInt(evenIndex);
                        Log.d("mLog", str);
                    }
                    while(cursor.moveToNext());
                }
                else
                {
                    Log.d("mLog","0 rows");
                }
                cursor.close();

                Cursor c = database.rawQuery("SELECT SCHEDULE.ID as ID, SUBJECTS.NAME as SUBJECT, TEACHERS.NAME as TEACHER, TYPES_CLASS.NAME as TYPE_CLASS, CABINETS.NAME as CABINET, SCHEDULE.TIME as TIME, SCHEDULE.NOTE as NOTE, SCHEDULE.EVEN as EVEN\n" +
                        "FROM SCHEDULE\n" +
                        "INNER JOIN SUBJECTS ON SCHEDULE.SUBJECT = SUBJECTS.ID\n" +
                        "INNER JOIN TEACHERS ON SCHEDULE.TEACHER = TEACHERS.ID\n" +
                        "INNER JOIN DAYS ON SCHEDULE.DAY = DAYS.ID\n" +
                        "INNER JOIN CABINETS ON SCHEDULE.CABINET = CABINETS.ID\n" +
                        "INNER JOIN TYPES_CLASS ON SCHEDULE.TYPE_CLASS = TYPES_CLASS.ID", null);
                c.moveToFirst();
                //TODO Annotation
                @SuppressLint("Range") String str = "ID =" + c.getInt(c.getColumnIndex("ID")) +
                        ", SUBJECT = " + c.getString(c.getColumnIndex("SUBJECT")) +
                        ", TEACHER = " + c.getString(c.getColumnIndex("TEACHER")) +
                        ", TYPE_CLASS = " + c.getString(c.getColumnIndex("TYPE_CLASS")) +
                        ", CABINET = " + c.getString(c.getColumnIndex("CABINET")) +
                        ", TIME = " + c.getString(c.getColumnIndex("TIME")) +
                        ", NOTE = " + c.getString(c.getColumnIndex("NOTE")) +
                        ", EVEN = " + c.getString(c.getColumnIndex("EVEN"));
                Log.d("mLog", str);
                c.close();

                if(RB2.isChecked()) { // Проверка что выбран RadioButton школьник
                    setContentView(R.layout.select_even_or_odd_param_form);

                    button_classic = (Button) findViewById(R.id.btnSelectClassicType);
                    button_even_odd = (Button) findViewById(R.id.btnSelectEvenOddType);
                    button_classic.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            setContentView(R.layout.week_redactor_form);
                        }
                    });

                    button_even_odd.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            setContentView(R.layout.odd_week_redactor_form);


                    onLoadOddRedactor();

                        }
                    });
                }
                if(RB1.isChecked()) { // Проверка что выбран RadioButton студент
                    setContentView(R.layout.university_selection_form);
                    button_type = (Button) findViewById(R.id.btnMoveToConstructorUSF);
                    button_type.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            setContentView(R.layout.select_even_or_odd_param_form);
                            button_classic = (Button) findViewById(R.id.btnSelectClassicType);
                            button_even_odd = (Button) findViewById(R.id.btnSelectEvenOddType);
                            button_classic.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    setContentView(R.layout.week_redactor_form);
                                }
                            });

                            button_even_odd.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    onLoadOddRedactor();

                                }
                            });
                        }
                    });
                }
            }
        });

        dbHelper = new DBHelper(this);


        /*
        TextView[] days = new TextView[] {
                findViewById(R.id.monday),
                findViewById(R.id.tuesday),
                findViewById(R.id.wednesday),
                findViewById(R.id.thursday),
                findViewById(R.id.friday),
                findViewById(R.id.saturday)
        };

        TextView[] classes = new TextView[] {
                findViewById(R.id.class1),
                findViewById(R.id.class2),
                findViewById(R.id.class3),
                findViewById(R.id.class4),
                findViewById(R.id.class5),
                findViewById(R.id.class6),
                findViewById(R.id.class7),
                findViewById(R.id.class8)
        };
        days[0].performClick();
        selectedTextView = days[0];
        selectedTextView.setTextSize(25);

        for(TextView day : days)
        {
            day.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View view)
                {
                    activateTextView((TextView)view, classes);
                }
            });
        }

        for (TextView item : classes)
        {
            item.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View view)
                {
                    if(item.getText()=="")
                    {
                        setContentView(R.layout.adding_class_form);
                        item.setBackgroundColor(0x808080);
                    }
                    else
                    {

                    }
                }
            });
        }

 */
    }

    private void onLoadOddRedactor() {
        setContentView(R.layout.odd_week_redactor_form);
        Button nextBtn = findViewById(R.id.btnConfirmMain);
        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                number = 0;
                onLoadEvenRedactor();
            }
        });
                TextView[] days = new TextView[]{
                        findViewById(R.id.monday),
                        findViewById(R.id.tuesday),
                        findViewById(R.id.wednesday),
                        findViewById(R.id.thursday),
                        findViewById(R.id.friday),
                        findViewById(R.id.saturday)
                };

        days[day].setBackgroundResource(R.color.teal_200);

                TextView[] classes = new TextView[]{
                        findViewById(R.id.class1),
                        findViewById(R.id.class2),
                        findViewById(R.id.class3),
                        findViewById(R.id.class4),
                        findViewById(R.id.class5),
                        findViewById(R.id.class6),
                        findViewById(R.id.class7),
                        findViewById(R.id.class8)
                };
                for (int temp = 0; temp < 8; ++temp) {
                    classes[temp].setText(Array[day][temp]);
                }
                for (int j = 0; j < 8; ++j) {
                    int nclass = j;
                    classes[j].setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            number = nclass;
                            setContentView(R.layout.adding_class_form);
                            Button addBtn = findViewById(R.id.btnAddASF);
                            addBtn.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    EditText Text = findViewById(R.id.classLabelASF);
                                    Array[day][number] = Text.getText().toString();
                                    onLoadOddRedactor();
                                }
                            });
                        }
                    });
                }
                for (int j = 0; j < 6; ++j) {
                    int nday = j;
                    days[j].setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            day = nday;

                            for (int temp = 0; temp < 8; ++temp) {
                                classes[temp].setText(Array[day][temp]);
                            }
                            for (int i = 0; i < 6; ++i) {
                                days[i].setBackgroundResource(R.color.white);
                            }
                            days[day].setBackgroundResource(R.color.teal_200);
                        }
                    });
                }
                if (days[0].getDrawingCacheBackgroundColor() != 0) {
                    days[1].setBackgroundColor(days[0].getDrawingCacheBackgroundColor());
                }
    }

    private void onLoadEvenRedactor() {
        setContentView(R.layout.even_week_redactor_form);
        Button nextBtn = findViewById(R.id.btnConfirmMain);
        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                number = 0;
                onLoadEvenWeek();
            }
        });
        TextView[] days = new TextView[]{
                findViewById(R.id.monday),
                findViewById(R.id.tuesday),
                findViewById(R.id.wednesday),
                findViewById(R.id.thursday),
                findViewById(R.id.friday),
                findViewById(R.id.saturday)
        };

        days[day].setBackgroundResource(R.color.teal_200);

        TextView[] classes = new TextView[]{
                findViewById(R.id.class1),
                findViewById(R.id.class2),
                findViewById(R.id.class3),
                findViewById(R.id.class4),
                findViewById(R.id.class5),
                findViewById(R.id.class6),
                findViewById(R.id.class7),
                findViewById(R.id.class8)
        };
        for (int temp = 0; temp < 8; ++temp) {
            classes[temp].setText(Array2[day][temp]);
        }
        for (int j = 0; j < 8; ++j) {
            int nclass = j;
            classes[j].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    number = nclass;
                    setContentView(R.layout.adding_class_form);
                    Button addBtn = findViewById(R.id.btnAddASF);
                    addBtn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            EditText Text = findViewById(R.id.classLabelASF);
                            Array2[day][number] = Text.getText().toString();
                            onLoadEvenRedactor();
                        }
                    });
                }
            });
        }
        for (int j = 0; j < 6; ++j) {
            int nday = j;
            days[j].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    day = nday;

                    for (int temp = 0; temp < 8; ++temp) {
                        classes[temp].setText(Array2[day][temp]);
                    }
                    for (int i = 0; i < 6; ++i) {
                        days[i].setBackgroundResource(R.color.white);
                    }
                    days[day].setBackgroundResource(R.color.teal_200);
                }
            });
        }
        if (days[0].getDrawingCacheBackgroundColor() != 0) {
            days[1].setBackgroundColor(days[0].getDrawingCacheBackgroundColor());
        }
    }

    private void onLoadEvenWeek() {
        setContentView(R.layout.main_schedule_form);

        TextView[] days = new TextView[]{
                findViewById(R.id.monday),
                findViewById(R.id.tuesday),
                findViewById(R.id.wednesday),
                findViewById(R.id.thursday),
                findViewById(R.id.friday),
                findViewById(R.id.saturday)
        };

        days[day].setBackgroundResource(R.color.teal_200);
        Button buttonNext = findViewById(R.id.btnNext);
        Button buttonPrev = findViewById(R.id.btnPrev);
        TextView[] classes = new TextView[]{
                findViewById(R.id.class1),
                findViewById(R.id.class2),
                findViewById(R.id.class3),
                findViewById(R.id.class4),
                findViewById(R.id.class5),
                findViewById(R.id.class6),
                findViewById(R.id.class7),
                findViewById(R.id.class8)
        };
        for (int temp = 0; temp < 8; ++temp) {
            if(week)
                classes[temp].setText(Array[day][temp]);
            else {classes[temp].setText(Array2[day][temp]);
            };
        }

        buttonNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                number = 0;
                week = !(week);
                onLoadEvenWeek();
            }
        });
        buttonPrev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                number = 5;
                week = !(week);
                onLoadEvenWeek();
            }
        });
        for (int j = 0; j < 6; ++j) {
            int nday = j;
            days[j].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    day = nday;

                    for (int temp = 0; temp < 8; ++temp) {
                        if(week)
                            classes[temp].setText(Array[day][temp]);
                        else {classes[temp].setText(Array2[day][temp]);
                        };
                    }
                    for (int i = 0; i < 6; ++i) {
                        days[i].setBackgroundResource(R.color.white);
                    }
                    days[day].setBackgroundResource(R.color.teal_200);
                }
            });
        }
    }

    public void activateTextView(TextView curTextView, TextView[] classes)
    {
        if(selectedTextView != curTextView)
        {
            curTextView.setTextSize(25);
            curTextView.setBackgroundColor(0xFF03DAC5);
            disableTextView();
            selectedTextView = curTextView;
            clearAllClasses(classes);
        }
    }



    public void disableTextView()
    {
        selectedTextView.setTextSize(20);
        selectedTextView.setBackgroundColor(0);
    }

    public void clearAllClasses(TextView[] classes)
    {
        for (TextView item : classes)
        {
            item.setText("");
        }
    }
}