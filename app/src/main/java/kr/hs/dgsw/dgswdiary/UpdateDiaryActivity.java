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

public class UpdateDiaryActivity extends AppCompatActivity {
    String selectDate;
    TextView dateTextView;
    EditText titleEditText;
    EditText contentEditText;
    DiaryDBHelper dbHelper;
    DiaryBean diary = new DiaryBean();
    ReadDiaryActivity activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_diary);

        dateTextView = findViewById(R.id.dateTextView);
        titleEditText = findViewById(R.id.titleEditText);
        contentEditText = findViewById(R.id.contentEditText);
        dbHelper = new DiaryDBHelper(this, "diary", null, 1);
        activity = (ReadDiaryActivity)ReadDiaryActivity.readDiaryActivity;

        setDateTextView();
        setDiaryEditText();
    }

    public void onUpdateDiary(View v){
        updateDiary();

        this.finish();
        activity.finish();
    }

    private void setDateTextView(){
        Intent intent = getIntent();
        selectDate = intent.getStringExtra("selectDate");
        dateTextView.setText(selectDate);
    }

    private void updateDiary(){
        diary.setTitle(titleEditText.getText().toString());
        diary.setContent(contentEditText.getText().toString());

        Log.i("Date", diary.getDate());
        Log.i("TITLE", diary.getTitle());
        Log.i("CONTENT", diary.getContent());

        dbHelper.update(diary);

        Toast.makeText(this, "일기 수정이 완료되 었습니다.", Toast.LENGTH_SHORT);
    }

    private void setDiaryEditText(){
        diary = dbHelper.getDiarybyDate(selectDate);

        titleEditText.setText(diary.getTitle());
        contentEditText.setText(diary.getContent());
    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);

        alertDialogBuilder
                .setTitle("일기 삭제")
                .setMessage("정말로 일기 작성을 그만두겠습니까?")
                .setPositiveButton("아니요",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        })
                .setNegativeButton("네",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                closeThis();
                            }
                        });

        AlertDialog alertDialog = alertDialogBuilder.create();

        alertDialog.show();
    }

    private void closeThis(){
        this.finish();
    }
}
