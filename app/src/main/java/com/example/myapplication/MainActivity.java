package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
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