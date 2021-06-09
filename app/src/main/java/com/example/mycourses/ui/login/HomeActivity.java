package com.example.mycourses.ui.login;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityOptionsCompat;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.example.mycourses.R;

public class HomeActivity extends AppCompatActivity {
    private itemCursorAdapter cursorAdapter;
    private itemDbAdapter dbAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        final GridView itemView = (GridView) findViewById(R.id.courses_list_view);



        dbAdapter = new itemDbAdapter(this);
        dbAdapter.open();
        if(savedInstanceState == null){
//            dbAdapter.deleteAllCourses();
//            dbAdapter.createCourse("ABE 543","INTRODUCTION TO MAGNETISM",
//                    2 ,false,"Mr Ajala");
//            dbAdapter.createCourse("ABE 545","INTRODUCTION TO MAGNETISM",
//                    1 ,true,"Mr Ajala");
//            dbAdapter.createCourse("ABE 545","INTRODUCTION TO MAGNETISM",
//                    1 ,true,"Mr Ajala");
//            insertSomeCourses();
        }

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
                this,
                R.layout.grid_item,
                R.id.course_code,
                new String[]{"AMB101","SYS221","BNT131","AMP101","SYE221","TNT131","SMB101","DYS221","ENT131"}
        );

        Cursor cursor = dbAdapter.fetchAllCourses();
        String[] from = new String[]{dbAdapter.COL_COURSE,dbAdapter.COL_TITLE,
                dbAdapter.COL_UNIT,dbAdapter.COL_LECTURER};
        int[] to =  new int[]{R.id.course_code,R.id.course_title,R.id.course_unit,
                R.id.lecturer};

        cursorAdapter = new itemCursorAdapter(
                this,
                R.layout.grid_item,
                cursor,
//                new String[]{dbAdapter.COL_COURSE,dbAdapter.COL_TITLE,
//                        dbAdapter.COL_UNIT,dbAdapter.COL_CARRY,dbAdapter.COL_LECTURER},
//                new int[]{R.id.course_code,R.id.course_title,R.id.course_unit,
//                        R.id.carry_tab,R.id.lecturer},
                from,
                to,
                0
        );




        itemView.setAdapter(cursorAdapter);
        dbAdapter.close();

        itemView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(HomeActivity.this,"Clicked: "+position,
                        Toast.LENGTH_SHORT).show();
//                itemView.setNumColumns(1);
                Intent intent = new Intent(HomeActivity.this,courseActivity.class);
                ActivityOptionsCompat compat = ActivityOptionsCompat.makeSceneTransitionAnimation(HomeActivity.this);
                startActivity(intent,compat.toBundle());


            }
        });


    }

//    public  void changeView(int viewType){
//        LinearLayout test = findViewById(R.id.test_grid);
//        ViewGroup.LayoutParams params = test.getLayoutParams();
//        params.width= 1000;
//        test.setLayoutParams(params);
//        test.requestLayout();
//
//        test.requestLayout();
//    }

}