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

import java.util.ArrayList;

public class DiaryActivity extends AppCompatActivity {
    DiaryDBHelper dbHelper;
    TextView dateTextView;
    EditText titleEditText;
    EditText contentEditText;
    String selectDate;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diary);

        dateTextView = findViewById(R.id.dateTextView);
        titleEditText = findViewById(R.id.titleEditText);
        contentEditText = findViewById(R.id.contentEditText);

        dbHelper = new DiaryDBHelper(this, "diary", null, 1);

        setDateTextView();
    }

    private void setDateTextView(){
        Intent intent = getIntent();
        selectDate = intent.getStringExtra("selectDate");
        dateTextView.setText(selectDate);
    }

    private void closeThis(){
        this.finish();
    }

    public void onSaveDiary (View view){
        if (isEditText() == true){
            addDiary();
        }
        this.finish();
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

    private void addDiary(){
        DiaryBean diary = new DiaryBean();

        diary.setDate(selectDate);
        diary.setTitle(titleEditText.getText().toString());
        diary.setContent(contentEditText.getText().toString());

        Log.i("DIARY", diary.getDate() + "___" + diary.getTitle() + "__" + diary.getContent());

        dbHelper.insert(diary);

        Toast.makeText(this, "일기가 저장되었습니다", Toast.LENGTH_SHORT).show();
    }

    private boolean isEditText(){
        String title = titleEditText.getText().toString();
        String content = contentEditText.getText().toString();
        if (title.isEmpty() && content.isEmpty()){
            Toast.makeText(this, "제목과 일기를 채워주세요", Toast.LENGTH_SHORT).show();
            return false;
        }else if (content.isEmpty()){
            Toast.makeText(this, "일기를 채워주세요.", Toast.LENGTH_SHORT).show();
            return false;
        }else if (title.isEmpty()){
            Toast.makeText(this, "제목을 채워주세요.", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }


}
