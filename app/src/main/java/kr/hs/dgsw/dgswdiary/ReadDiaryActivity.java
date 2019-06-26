package kr.hs.dgsw.dgswdiary;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class ReadDiaryActivity extends AppCompatActivity {
    DiaryDBHelper dbHelper;
    TextView dateTextView;
    TextView titleTextView;
    TextView contentTextView;
    String selectDate;
    public static ReadDiaryActivity readDiaryActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read_diary);

        dateTextView = findViewById(R.id.dateTextView);
        titleTextView = findViewById(R.id.titleTextView);
        contentTextView = findViewById(R.id.contentTextView);
        readDiaryActivity = ReadDiaryActivity.this;

        dbHelper = new DiaryDBHelper(this, "diary", null, 1);

        setDiaryData();
    }

    private void setDateTextView(){
        Intent intent = getIntent();
        selectDate = intent.getStringExtra("selectDate");
        dateTextView.setText(selectDate);
    }

    private void setDiaryData(){
        setDateTextView();
        DiaryBean diary = dbHelper.getDiarybyDate(selectDate);

        titleTextView.setText(diary.getTitle());
        contentTextView.setText(diary.getContent());
    }

    public void onMainScreen(View view){

        this.finish();
    }

    public void startMainScreen(){

        this.finish();
    }


    public void onDeleteDiary(View view){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);

        alertDialogBuilder
            .setTitle("일기 삭제")
            .setMessage("정말로 일기를 삭제하시겠습니까?")
            .setPositiveButton("삭제",
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            deleteDiary();
                            startMainScreen();
                        }
                    })
            .setNegativeButton("취소",
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });

        AlertDialog alertDialog = alertDialogBuilder.create();

        alertDialog.show();



    }

    private void deleteDiary(){
        dbHelper.delete(selectDate);
        Toast.makeText(this, "일기가 삭제되었습니다.", Toast.LENGTH_SHORT).show();
    }

    public void onUpdate(View view){
        Intent intent = new Intent(this, UpdateDiaryActivity.class);
        intent.putExtra("selectDate", selectDate);

        startActivity(intent);

    }
}