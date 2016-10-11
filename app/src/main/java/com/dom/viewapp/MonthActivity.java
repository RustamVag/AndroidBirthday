package com.dom.viewapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class MonthActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.month);

        int pos = getIntent().getExtras().getInt("monthNumber");

        Month month = new Month(pos+1);
        String[] list = new String[month.getCount()];

        TextView tv = (TextView) findViewById(R.id.textView2);
        tv.setText(month.getName());

        Integer d,m,y;
        m = month.getNumber();

        for (Integer i = 1; i <= month.getCount(); i++)
        {
            list[i-1] = i.toString() + "." + m.toString() + ".2016";
        }

        ListView lv = (ListView) findViewById(R.id.listView2);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, list);
        lv.setAdapter(adapter);

    }

    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
}
