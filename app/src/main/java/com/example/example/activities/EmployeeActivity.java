package com.example.example.activities;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.examle.R;
import com.example.example.db.DBHelper;
import com.example.example.logic.Environment;
import com.example.example.objects.Employee;

import java.util.Calendar;
import java.util.Date;

public class EmployeeActivity extends AppCompatActivity {

    private EditText name;
    private EditText last_name;
    private TextView birthday;
    private Spinner city;
    private Spinner post;

    private Button delete_button;

    Employee employee;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee);

        init();
    }

    private void init() {
        // toolBar
        Toolbar tb = (Toolbar) findViewById(R.id.toolbar);
        TextView toolBarTitle = (TextView) findViewById(R.id.toolbar_title);
        TextView save = (TextView) findViewById(R.id.save);
        View cancel_button = (View) findViewById(R.id.cancel_button);
        
        setSupportActionBar(tb);
        final ActionBar ab = getSupportActionBar();
        if (ab != null) {
            ab.setDisplayShowHomeEnabled(false);
            ab.setDisplayHomeAsUpEnabled(false);
            ab.setDisplayShowCustomEnabled(true);
            ab.setDisplayShowTitleEnabled(false);
        }
        // font path
        String fontPath = "fonts/primaryFontBold.ttf";
        // load font
        Typeface tf = Typeface.createFromAsset(getAssets(), fontPath);
        // apply font
        toolBarTitle.setTypeface(tf);
        save.setTypeface(tf);

        // click back
        cancel_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // finish
                onBackPressed();
            }
        });

        // click save
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO
                saveEmployee();
            }
        });

        // Views
        name = (EditText) findViewById(R.id.name);
        last_name = (EditText) findViewById(R.id.last_name);
        birthday = (TextView) findViewById(R.id.birthday);
        city = (Spinner) findViewById(R.id.city);
        post = (Spinner) findViewById(R.id.post);
        delete_button = (Button) findViewById(R.id.delete_button);

        // click delete
        delete_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DBHelper.getInstance(EmployeeActivity.this).removeEmployeeById(employee.getId());
                finish();
            }
        });

        ArrayAdapter<String> adapterCity = new ArrayAdapter<String>(EmployeeActivity.this, android.R.layout.simple_spinner_item, Environment.cities);
        adapterCity.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        city.setAdapter(adapterCity);

        ArrayAdapter<String> adapterPost = new ArrayAdapter<String>(EmployeeActivity.this, android.R.layout.simple_spinner_item, Environment.post);
        adapterPost.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        post.setAdapter(adapterPost);

        birthday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setDate().show();
            }
        });

        // get extras
        Intent intent = getIntent();
        int id = intent.getIntExtra(Environment.ID, -1);

        if(id != -1) {
            employee = DBHelper.getInstance(EmployeeActivity.this).getEmployeeById(id);
            name.setText(employee.getFirstName());
            last_name.setText(employee.getLastName());
            birthday.setText(employee.getStringBirthday());
            int index = Environment.cities.indexOf(employee.getCity());
            city.setSelection(index);
            index = Environment.post.indexOf(employee.getCity());
            post.setSelection(index);
            // set title
            toolBarTitle.setText(getString(R.string.edit_employee));
        }
        else {
            employee = new Employee();
            birthday.setText(employee.getStringBirthday());
            delete_button.setVisibility(View.GONE);

            // set title
            toolBarTitle.setText(getString(R.string.add_employee));
        }
    }

    DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {
            Calendar calendar = Calendar.getInstance();
            calendar.set(Calendar.YEAR, year);
            calendar.set(Calendar.MONTH, monthOfYear);
            calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

            employee.setBirthday(calendar.getTime());
            birthday.setText(employee.getStringBirthday());
        }
    };

    private DatePickerDialog setDate() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(employee.getBirthday());
        DatePickerDialog dialog = new DatePickerDialog(EmployeeActivity.this, date,
                calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH));
        dialog.getDatePicker().setMaxDate(new Date().getTime());
        return dialog;

    }

    private void saveEmployee() {
        if(name.getText().toString().isEmpty()) {
            name.setError(getString(R.string.error_name));
            return;
        }
        employee.setFirstName(name.getText().toString());
        employee.setLastName(last_name.getText().toString());
        employee.setCity(city.getSelectedItem().toString());
        employee.setPost(post.getSelectedItem().toString());

        DBHelper.getInstance(EmployeeActivity.this).saveEmployee(employee);

        finish();
    }
}
