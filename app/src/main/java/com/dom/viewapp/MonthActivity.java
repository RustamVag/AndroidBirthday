package com.dom.viewapp;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MonthActivity extends AppCompatActivity {

    private static final List<BirthdayItem> birthdays = new ArrayList<BirthdayItem>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.month);

        int pos = getIntent().getExtras().getInt("monthNumber");

        Month month = new Month(pos+1);
        //String[] list = new String[month.getCount()];

        TextView tv = (TextView) findViewById(R.id.textView2);
        tv.setText(month.getName());

        Integer d,m,y;
        m = month.getNumber();

        for (Integer i = 1; i <= month.getCount(); i++)
        {
            //list[i-1] = i.toString() + "." + m.toString() + ".2016";
            birthdays.add(new BirthdayItem("Обычный день",i.toString() + "." + m.toString() + ".2016" ));
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
            startActivity(intent);
        }

        return true;
    }

    // Класс BirthdayItem
    private static class BirthdayItem
    {
        public final String name;

        public final String date;

        public BirthdayItem(String name, String date)
        {
            this.name = name;
            this.date = date;
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
            ((TextView) convertView.findViewById(R.id.list_item_name))
                    .setText(birthdayItem.name);
            ((TextView) convertView.findViewById(R.id.list_item_date))
                    .setText(birthdayItem.date);
            return convertView;
        }
    }
}
