package com.dom.viewapp;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MonthActivity extends AppCompatActivity {

    private static final List<BirthdayItem> birthdays = new ArrayList<BirthdayItem>();
    private static final List<Birthday> birth = new ArrayList<Birthday>();
    DBHelper dbHelper;
    int pos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.month);

        // Определяем месяц
        pos = getIntent().getExtras().getInt("monthNumber");

        Month month = new Month(pos+1);
        //String[] list = new String[month.getCount()];

        TextView tv = (TextView) findViewById(R.id.textView2);
        tv.setText(month.getName());

        Integer d,m,y;
        m = month.getNumber();

        // Чтение из БД
        dbHelper = new DBHelper(this);
        SQLiteDatabase database = dbHelper.getWritableDatabase();
        Cursor cursor = database.query(DBHelper.TABLE_BIRTHDAYS, null, null, null, null, null, null);

        birth.clear();
        if (cursor.moveToFirst()) {
            int idIndex = cursor.getColumnIndex(DBHelper.KEY_ID);
            int nameIndex = cursor.getColumnIndex(DBHelper.KEY_NAME);
            int dayIndex = cursor.getColumnIndex(DBHelper.KEY_DAY);
            int monthIndex = cursor.getColumnIndex(DBHelper.KEY_MONTH);
            do {
                Log.d("mLog", "ID = " + cursor.getInt(idIndex) +
                        ", name = " + cursor.getString(nameIndex) +
                        ", day = " + cursor.getInt(dayIndex) +
                        ", month = " + cursor.getInt(monthIndex));
                birth.add( new Birthday(cursor.getString(nameIndex), cursor.getInt(dayIndex), cursor.getInt(monthIndex)) );
            } while (cursor.moveToNext());
        } else
            Log.d("mLog","0 rows");

        cursor.close();

        birthdays.clear();
        // Составление списка
        for (Integer i = 1; i <= month.getCount(); i++)
        {
            //list[i-1] = i.toString() + "." + m.toString() + ".2016";
            BirthdayItem bi = new BirthdayItem(" ",i.toString() + "." + m.toString() + ".2016" );
            for(Birthday b: birth)
            {
                if ((b.getDay() == i) && (b.getMonth() == m))
                {
                    bi.name = b.getName();
                    bi.state = 1;
                }
            }
            birthdays.add(bi);
        }

        ListView lv = (ListView) findViewById(R.id.listView2);

        //ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, list);
        ArrayAdapter<BirthdayItem> adapter = new BirthdayAdapter(this);
        lv.setAdapter(adapter);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        int id = item.getItemId();

        if (id == R.id.action_add)
        {
            Intent intent = new Intent(this, BirthdayActivity.class);
            intent.putExtra("monthNumber", pos);
            startActivity(intent);
        }

        return true;
    }

    // Класс BirthdayItem
    private static class BirthdayItem
    {
        public  String name;

        public  String date;

        public Integer state;

        public BirthdayItem(String name, String date)
        {
            this.name = name;
            this.date = date;
            this.state = 0;
        }
    }

    // Создаем адаптер
    private class BirthdayAdapter extends ArrayAdapter<BirthdayItem>
    {
        public BirthdayAdapter(Context context) {
            super(context, android.R.layout.simple_list_item_2, birthdays);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            BirthdayItem birthdayItem = getItem(position);

            if (convertView == null) {
                convertView = LayoutInflater.from(getContext())
                        .inflate(R.layout.custom_list_item, null);
            }

            RelativeLayout layout = (RelativeLayout) convertView.findViewById(R.id.view_background);

            TextView tvName = (TextView) convertView.findViewById(R.id.list_item_name);
            TextView tvDate = (TextView) convertView.findViewById(R.id.list_item_date);
            if (birthdayItem.state == 1)
            {
                //tvName.setBackgroundResource(R.color.colorBirthday);
                //layout.setBackgroundColor(getResources().getColor(R.color.colorBirthday));
                tvDate.setTextColor(getResources().getColor(R.color.colorBirthday));
            }
            tvName.setText(birthdayItem.name);
            tvDate.setText(birthdayItem.date);

            //((TextView) convertView.findViewById(R.id.list_item_name)).setText(birthdayItem.name);
            //((TextView) convertView.findViewById(R.id.list_item_date)).setText(birthdayItem.date);
            return convertView;
        }
    }
}
