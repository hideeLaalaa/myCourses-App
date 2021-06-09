package com.example.mycourses.ui.login;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.mycourses.ui.login.Course;

import java.security.PrivateKey;

public class itemDbAdapter {

    public static final String COL_ID = "_id";
    public static final String COL_COURSE = "course";
    public static final String COL_TITLE = "title";
    public static final String COL_UNIT = "unit";
    public static final String COL_CARRY = "carry";
    public static final String COL_LECTURER = "lecturer";

    //colid
    public static final int INDEX_ID = 0;
    public static final int INDEX_COURSE = INDEX_ID+1;
    public static final int INDEX_TITLE = INDEX_ID+2;
    public static final int INDEX_UNIT = INDEX_ID+3;
    public static final int INDEX_CARRY = INDEX_ID+4;
    public static final int INDEX_LECTURER = INDEX_ID+5;

    //defining sort types
    public static final int SORT_DEFAULT = 0;
    public static final int SORT_NAME = 1;
    public static final int SORT_UNIT = 2;
    public static final int SORT_CARRY = 3;
    public static final int SORT_LECTURER = 4;

    //used for logging
    private static final String TAG = "itemDbAdapter";

    private DatabaseHelper dbHelper;
    private SQLiteDatabase db;

    private static final String DATABASE_NAME = "db_course";
    private static final String TABLE_NAME = "tb_course";
    private static final int DATABASE_VERSION = 1;

    private final Context xCtx;

    public itemDbAdapter(Context ct){ xCtx=ct; }

    public  void open() throws SQLException{
        dbHelper = new DatabaseHelper(xCtx);
        db=dbHelper.getWritableDatabase();
    }

    public void close(){
        if(dbHelper !=null)
            dbHelper.close();
    }

    //------------CRUD OPERATIONS------------//

    //--------CREATE
    public void createCourse(String COL_COURSE,String COL_TITLE,int COL_UNIT,Boolean COL_CARRY,
                             String COL_LECTURER){
        ContentValues values = new ContentValues();
        values.put(this.COL_COURSE,COL_COURSE);
        values.put(this.COL_TITLE,COL_TITLE);
        values.put(this.COL_UNIT,COL_UNIT);
        values.put(this.COL_CARRY,COL_CARRY);
        values.put(this.COL_LECTURER,COL_LECTURER);
        db.insert(TABLE_NAME,null,values);
    }
    public void createCourse(Course course){
        ContentValues values = new ContentValues();
        values.put(this.COL_COURSE,course.getxCourse());
        values.put(this.COL_TITLE,course.getxTitle());
        values.put(this.COL_UNIT,course.getxUnit());
        values.put(this.COL_CARRY,course.getxCarry());
        values.put(this.COL_LECTURER,course.getxLecturer());
        db.insert(TABLE_NAME,null,values);
    }

    //--------READ
    public Course fetchCourseById(int id){
        Cursor cursor = db.query(TABLE_NAME,new String[]{COL_ID,COL_COURSE,COL_TITLE,COL_UNIT,COL_CARRY,
                        COL_LECTURER},COL_ID+"=?",new String[]{String.valueOf(id)} ,
                null,null,null,null);
        if (cursor!=null)
            cursor.moveToFirst();

        return new Course(
                cursor.getInt(INDEX_ID),
                cursor.getString(INDEX_COURSE),
                cursor.getString(INDEX_TITLE),
                cursor.getInt(INDEX_UNIT),
                cursor.getInt(INDEX_CARRY),
                cursor.getString(INDEX_LECTURER)
        );
    }

    public Cursor fetchCourseCursor(String text){
        Cursor c;
        String sql = "SELECT * FROM "+TABLE_NAME+" WHERE course LIKE '%" +text +"%'";

        c=db.rawQuery(sql,null);
        if (c!=null)
            c.moveToFirst();
        return c;
    }

    public Cursor fetchAllCourses(){
        Cursor c;
        String sql = "SELECT * FROM "+TABLE_NAME;
        c=db.rawQuery(sql,null);
        if (c!=null)
            c.moveToFirst();
        return c;
    }

    public Cursor fetchSortedCourses(int type,int mode){
        if (type==SORT_DEFAULT)
            return fetchAllCourses();
        Cursor c=null;
        if (type==SORT_NAME){
            String sql = "SELECT * FROM "+TABLE_NAME+" ORDER BY course "+(mode==0?"ASC":"DESC");
            c=db.rawQuery(sql,null);
        }
        if (type==SORT_UNIT){
            String sql = "SELECT * FROM "+TABLE_NAME+" ORDER BY unit "+(mode==0?"ASC":"DESC");
            c=db.rawQuery(sql,null);
        }
        if (type==SORT_CARRY){
            String sql = "SELECT * FROM "+TABLE_NAME+" ORDER BY carry "+(mode==0?"ASC":"DESC");
            c=db.rawQuery(sql,null);
        }if (type==SORT_LECTURER){
            String sql = "SELECT * FROM "+TABLE_NAME+" ORDER BY lecturer "+(mode==0?"ASC":"DESC");
            c=db.rawQuery(sql,null);
        }
        if (c!=null)
            c.moveToFirst();
//        return cursor;
        return c;
    }

    //--------UPDATE
    public void updateCourse(Course course){
        ContentValues values = new ContentValues();
        values.put(this.COL_COURSE,course.getxCourse());
        values.put(this.COL_TITLE,course.getxTitle());
        values.put(this.COL_UNIT,course.getxUnit());
        values.put(this.COL_CARRY,course.getxCarry());
        values.put(this.COL_LECTURER,course.getxLecturer());
        db.update(TABLE_NAME,values,COL_ID+"=?",
                new String[]{String.valueOf(course.getxId() )});
    }

    //--------DELETE
    public void deleteCourseById(int xId){
        db.delete(TABLE_NAME,COL_ID+"=?",
                new String[]{String.valueOf(xId)});
    }

    public  void deleteAllCourses(){
        db.delete(TABLE_NAME,null,null);
    }





    //----------------------------------------------------------------------
    //SQL statement used to create the database
    public static final String DATABASE_CREATE =
            "CREATE TABLE if not exists "+TABLE_NAME+
                    " ( "  + COL_ID + " INTEGER PRIMARY KEY autoincrement, "+
                    COL_COURSE + " TEXT, "+
                    COL_TITLE + " TEXT, "+
                    COL_UNIT + " INTEGER, "+
                    COL_CARRY + " INTEGER, "+
                    COL_LECTURER + " TEXT );";

    private static  class DatabaseHelper extends SQLiteOpenHelper {

        DatabaseHelper(Context context){
            super(context,DATABASE_NAME,null,DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            Log.w(TAG,DATABASE_CREATE);
            db.execSQL("DROP TABLE IF EXISTS "+ TABLE_NAME);
            db.execSQL(DATABASE_CREATE);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            Log.w(TAG,"Upgrading db from version "+oldVersion+" to "
                    +newVersion+ ", which will detsroy all old data");
            db.execSQL("DROP TABLE IF EXISTS "+ TABLE_NAME);
            onCreate(db);
        }

    }


}
