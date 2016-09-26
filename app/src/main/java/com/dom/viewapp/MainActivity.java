package com.dom.viewapp;

import android.app.ListActivity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    String[] firstList = {"Январь", "Февраль", "Март", "Апрель", "Май", "Июнь", "Июль", "Август", "Сентябрь", "Октябрь", "Ноябрь", "Декабрь"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        //Date date = new Date();
        //Calendar calendar = new GregorianCalendar();
        //int mon = calendar.get(Calendar.MONTH);


        ListView lv = (ListView) findViewById(R.id.listView);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, firstList);
        lv.setAdapter(adapter);
    }


   /* protected void onListItemClick(ListView l, View v, int position,long id) {
        super.onListItemClick(l, v, position, id);
        Intent intent = new Intent(MainActivity.this, MonthActivity.class);
        intent.putExtra("monthNumber", position);
        startActivity(intent);
    }; */
}
