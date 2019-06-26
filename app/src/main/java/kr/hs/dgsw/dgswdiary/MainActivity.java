package kr.hs.dgsw.dgswdiary;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    DiaryDBHelper dbHelper;
    CalendarView calendarView;
    Button diaryWrite;
    Button diaryRead;
    String selectDate;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        diaryWrite = findViewById(R.id.diaryWrite);
        diaryRead = findViewById(R.id.diaryRead);
        calendarView = findViewById(R.id.calendarView);

        dbHelper = new DiaryDBHelper(this, "diary", null, 1);

        maxDate();

        onSelectDate();
    }

    public void maxDate(){
        Long today = System.currentTimeMillis();
        calendarView.setMaxDate(today);
    }

    public void onDiary(View view){
        Intent intent = new Intent(this, DiaryActivity.class);
        intent.putExtra("selectDate", onSelectDate());

        startActivity(intent);
    }


    public String onSelectDate (){
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
            selectDate = String.valueOf(year) + "년 " + String.valueOf(month + 1) + "월 " + String.valueOf(dayOfMonth) + "일";
            Log.i("DATE", selectDate);
            if (isDiary() == false){
                Log.i("IS", "false");

                diaryRead.setVisibility(View.GONE);
                diaryWrite.setVisibility(View.VISIBLE);
            }else {
                diaryRead.setVisibility(View.VISIBLE);
                diaryWrite.setVisibility(View.GONE);
            }
            }
        });


        return selectDate;
    }

    public boolean isDiary(){
        Log.i("IS", dbHelper.getDiarybyDate(selectDate).getDate() + "__" + dbHelper.getDiarybyDate(selectDate).getTitle());
        if (dbHelper.getDiarybyDate(selectDate).getDate() == null){
            return false;
        }
        return true;
    }

    public void onReadDiary(View view){
        Intent intent = new Intent(this, ReadDiaryActivity.class);
        intent.putExtra("selectDate", onSelectDate());

        startActivity(intent);
    }
}
