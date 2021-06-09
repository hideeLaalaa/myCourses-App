package com.example.mycourses.ui.login;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

import com.example.mycourses.R;

public class itemCursorAdapter extends SimpleCursorAdapter {

    public itemCursorAdapter(Context context, int layout, Cursor c,
                             String[] from, int[] to,int carry) {
        super(context, layout, c, from, to,carry);

    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return super.newView(context, cursor, parent);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        super.bindView(view, context, cursor);

//        View carryTab = view.findViewById(R.id.carry_tab);
//        boolean carry = new Random().nextBoolean();
//        if (carry)
//            carryTab.setBackgroundColor(Color.RED);
//        else
//            carryTab.setBackgroundColor(Color.GREEN);

        ViewHolder viewHolder = (ViewHolder) view.getTag();
        if (viewHolder==null){
            viewHolder = new ViewHolder();
            viewHolder.carryId = cursor.getColumnIndexOrThrow(itemDbAdapter.COL_CARRY);
            viewHolder.carryTab = view.findViewById(R.id.carry_tab);
            viewHolder.unitId = cursor.getColumnIndexOrThrow(itemDbAdapter.COL_UNIT);
            viewHolder.unitView = view.findViewById(R.id.course_unit);
            viewHolder.titleId = cursor.getColumnIndexOrThrow(itemDbAdapter.COL_TITLE);
            viewHolder.titleView = view.findViewById(R.id.course_title);
            viewHolder.lectId = cursor.getColumnIndexOrThrow(itemDbAdapter.COL_LECTURER);
            viewHolder.lectView = view.findViewById(R.id.lecturer);
            view.setTag(viewHolder);
        }

        viewHolder.carryTab.setBackgroundColor(
                cursor.getInt(viewHolder.carryId)==0?Color.GREEN:Color.RED
        );
        viewHolder.unitView.setText(
            cursor.getString(viewHolder.unitId)+" Unit"
        );
        viewHolder.titleView.setText(
                cursor.getString(viewHolder.titleId)
        );
        viewHolder.lectView.setText(
                cursor.getString(viewHolder.lectId)
        );

    }

    static class ViewHolder{
        int carryId;
        View carryTab;
        int unitId;
        TextView unitView;
        int titleId;
        TextView titleView;
        int lectId;
        TextView lectView;

    }
}
