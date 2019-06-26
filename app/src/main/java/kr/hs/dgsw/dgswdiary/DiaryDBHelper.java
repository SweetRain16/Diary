package kr.hs.dgsw.dgswdiary;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.Nullable;

import java.sql.SQLData;
import java.util.ArrayList;

public class DiaryDBHelper extends SQLiteOpenHelper {
    public DiaryDBHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "create table diary ( date text primary key, title text, content text)";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String sql = "drop table diary";
        db.execSQL(sql);
        onCreate(db);
    }

    public void insert(DiaryBean diary){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("date" , diary.getDate());
        values.put("title", diary.getTitle());
        values.put("content", diary.getContent());
        db.insert("diary", null, values);
    }

    public DiaryBean getDiarybyDate(String date){

        SQLiteDatabase db = getReadableDatabase();
        String sql = "select * from diary where date = '" + date +"';";
        Cursor cursor  = db.rawQuery(sql, null);
        DiaryBean diaryBean = new DiaryBean();

        while (cursor.moveToNext()){
            diaryBean.setDate(cursor.getString(cursor.getColumnIndex("date")));
            diaryBean.setTitle(cursor.getString(cursor.getColumnIndex("title")));
            diaryBean.setContent(cursor.getString(cursor.getColumnIndex("content")));
        }

        return diaryBean;


    }

    public ArrayList<DiaryBean> getAll(){
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.query("diary", null, null, null, null, null, null);
        ArrayList<DiaryBean> result = new ArrayList<>();

        while (cursor.moveToNext()){
            DiaryBean diary = new DiaryBean();
            diary.setDate(cursor.getString(cursor.getColumnIndex("date")));
            diary.setContent(cursor.getString(cursor.getColumnIndex("title")));
            diary.setContent(cursor.getString(cursor.getColumnIndex("content")));
            result.add(diary);
        }
        return result;
    }

    public void delete (String date){
        SQLiteDatabase db = getWritableDatabase();
        String sql = "delete from diary where date = '" + date + "';";

        db.execSQL(sql);
    }

    public void update (DiaryBean diary){
        SQLiteDatabase db = getWritableDatabase();
        String sql = "update diary set title = '" + diary.getTitle() + "', content = '" + diary.getContent() + "' where date = '" + diary.getDate() + "';";

        db.execSQL(sql);
    }
}
