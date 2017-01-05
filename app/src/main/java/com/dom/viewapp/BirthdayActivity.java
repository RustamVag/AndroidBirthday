package com.dom.viewapp;

import android.Manifest;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
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
import android.widget.TextView;
import android.widget.Toast;

public class BirthdayActivity extends AppCompatActivity {

    // Константы
    public static final int COMMAND_NEW = 1;
    public static final int COMMAND_EDIT = 2;
    public  static final Integer LOCATION_NULL = 500;

    EditText dateEdit, nameEdit;
    Button addButton, locationButton;
    TextView textLat, textLong;

    DBHelper dbHelper;

    public Integer day, month;
    private Integer birthdayId, monthNumber, dayNumber, command;
    private String birthdayName;
    private Double latitude, longitude;

    LocationManager locationManager;
    LocationListener listener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.birthday);

        // Инициализируем геолокацию
        //MyLocationListener.SetUpLocationListener(this);

        // Читаем данные из MonthActivity
        birthdayId = getIntent().getExtras().getInt("birthdayId");
        monthNumber = getIntent().getExtras().getInt("monthNumber");
        dayNumber = getIntent().getExtras().getInt("dayNumber");
        birthdayName = getIntent().getExtras().getString("birthdayName");
        command = getIntent().getExtras().getInt("command");
        latitude = getIntent().getExtras().getDouble("latitude");
        longitude = getIntent().getExtras().getDouble("longitude");

        dateEdit = (EditText) findViewById(R.id.editBdDate);
        dateEdit.setRawInputType(0x00000000);
        nameEdit = (EditText) findViewById(R.id.editBdName);
        addButton = (Button) findViewById(R.id.add_button);

        locationButton = (Button) findViewById(R.id.location_button);
        textLat = (TextView) findViewById(R.id.textLat);
        textLong = (TextView) findViewById(R.id.textLong);

        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);


        listener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                textLat.append("\n " + location.getLatitude());
                textLong.append("\n "  + location.getLongitude());
            }

            @Override
            public void onStatusChanged(String s, int i, Bundle bundle) {

            }

            @Override
            public void onProviderEnabled(String s) {

            }

            @Override
            public void onProviderDisabled(String s) {

                Intent i = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivity(i);
            }
        };

        configure_button();

        Log.d("Отладка 2:","date = " + dayNumber + "-" + monthNumber + ", name = " + birthdayName);
        // Обработка поступивших команд команд
        if (command == COMMAND_EDIT)
        {
            String dateText = dayNumber + "-" + monthNumber;
            dateEdit.setText(dateText);
            nameEdit.setText(birthdayName);
            addButton.setText("СОХРАНИТЬ");
            if (latitude < LOCATION_NULL) textLat.setText(latitude.toString()); else textLat.setText("Не определено");
            if (longitude < LOCATION_NULL) textLong.setText(longitude.toString()); else textLong.setText("Не определено");

        }
        else
        {
            textLat.setText("Не определено");
            textLat.setText("Не определено");
            latitude = Double.parseDouble(LOCATION_NULL.toString());
            longitude = Double.parseDouble(LOCATION_NULL.toString());
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

                latitude = Double.parseDouble(textLat.getText().toString());
                longitude = Double.parseDouble(textLong.getText().toString());

                String[] dats = date.split("-");

                Integer monthDate = Integer.parseInt(dats[1]);


                SQLiteDatabase database = dbHelper.getWritableDatabase();

                ContentValues contentValues = new ContentValues();

                contentValues.put(dbHelper.KEY_NAME, name);
                contentValues.put(dbHelper.KEY_DAY, dats[0]);
                contentValues.put(dbHelper.KEY_MONTH, monthDate);
                contentValues.put(dbHelper.KEY_LAT, latitude);
                contentValues.put(dbHelper.KEY_LONG, longitude);

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

        /*
        locationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Location location = MyLocationListener.imHere;
                Double latitude = location.getLatitude();
                Double longitude = location.getLongitude();
                textLat.setText("Широта: " + latitude.toString());
                textLong.setText("Долгота:" + longitude.toString());
                Toast.makeText(getApplicationContext(), "Координаты установлены", Toast.LENGTH_LONG).show();
            }
        });
        */
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case 10:
                configure_button();
                break;
            default:
                break;
        }
    }

    void configure_button(){
        // first check for permissions
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(new String[]{Manifest.permission.ACCESS_COARSE_LOCATION,Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.INTERNET}
                        ,10);
            }
            return;
        }
        // this code won't execute IF permissions are not allowed, because in the line above there is return statement.
        locationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //noinspection MissingPermission
                locationManager.requestLocationUpdates("gps", 5000, 0, listener);
            }
        });
    }

    // Геолокация



}
