package com.dom.viewapp;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v4.app.DialogFragment;;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class BirthdayActivity extends AppCompatActivity {

    // Константы
    public static final int COMMAND_NEW = 1;
    public static final int COMMAND_EDIT = 2;

    EditText dateEdit, nameEdit;
    Button addButton;

    DBHelper dbHelper;

    public Integer day, month;
    private Integer birthdayId, monthNumber, dayNumber, command;
    private String birthdayName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.birthday);

        // Читаем данные из MonthActivity
        birthdayId = getIntent().getExtras().getInt("birthdayId");
        monthNumber = getIntent().getExtras().getInt("monthNumber");
        dayNumber = getIntent().getExtras().getInt("dayNumber");
        birthdayName = getIntent().getExtras().getString("birthdayName");
        command = getIntent().getExtras().getInt("command");

        dateEdit = (EditText) findViewById(R.id.editBdDate);
        dateEdit.setRawInputType(0x00000000);

        nameEdit = (EditText) findViewById(R.id.editBdName);
        addButton = (Button) findViewById(R.id.add_button);

        Log.d("Отладка 2:","date = " + dayNumber + "-" + monthNumber + ", name = " + birthdayName);
        // Обработка поступивших команд команд
        if (command == COMMAND_EDIT)
        {
            String dateText = dayNumber + "-" + monthNumber;
            dateEdit.setText(dateText);
            nameEdit.setText(birthdayName);
            addButton.setText("СОХРАНИТЬ");
        }

        dbHelper = new DBHelper(this);


        // Выбор даты
        dateEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                //Toast.makeText(getApplicationContext(), "Ты нажал на дату!", Toast.LENGTH_LONG).show();
                DialogFragment dateDialog = new DatePicker();
                dateDialog.show(getSupportFragmentManager(), "datePicker");
            }
        });

        // Добавить запись
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String name = nameEdit.getText().toString();
                String date = dateEdit.getText().toString();

                String[] dats = date.split("-");

                Integer monthDate = Integer.parseInt(dats[1]);


                SQLiteDatabase database = dbHelper.getWritableDatabase();

                ContentValues contentValues = new ContentValues();

                contentValues.put(dbHelper.KEY_NAME, name);
                contentValues.put(dbHelper.KEY_DAY, dats[0]);
                contentValues.put(dbHelper.KEY_MONTH, monthDate);

                if (command == COMMAND_EDIT)
                {
                    String whereText = DBHelper.KEY_ID + "=" + birthdayId;
                    database.update(dbHelper.TABLE_BIRTHDAYS, contentValues, whereText, null);
                    Toast.makeText(getApplicationContext(), "Запись изменена", Toast.LENGTH_LONG).show();
                    monthNumber--;

                }
                else
                {
                    database.insert(dbHelper.TABLE_BIRTHDAYS, null, contentValues);
                    Toast.makeText(getApplicationContext(), "Добавлена новая запись", Toast.LENGTH_LONG).show();
                }

                Intent intent = new Intent(getApplicationContext(), MonthActivity.class);
                intent.putExtra("monthNumber", monthNumber);
                startActivity(intent);
            }

        });

    }

}
