package com.example.examle.activities;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.examle.R;
import com.example.examle.adapters.ClickEmployee;
import com.example.examle.adapters.CustomAdapter;
import com.example.examle.db.DBHelper;
import com.example.examle.logic.Example;
import com.example.examle.objects.Employee;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements ClickEmployee{

    private static long back_pressed;

    // employee list
    private RecyclerView employee_list;
    private CustomAdapter adapter;
    private ArrayList<Employee> employees;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();
    }

    private void init() {
        // toolBar
        Toolbar tb = (Toolbar) findViewById(R.id.toolbar);
        TextView toolBarTitle = (TextView) findViewById(R.id.toolbar_title);
        View add_button = (View) findViewById(R.id.add_button);

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
        // set title
        toolBarTitle.setText(getString(R.string.employee_list));

        // click add employee
        add_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, EmployeeActivity.class);
                startActivity(intent);
            }
        });

        // employee list init
        employee_list = (RecyclerView) findViewById(R.id.employee_list);
        employee_list.setNestedScrollingEnabled(false);
        employee_list.setLayoutManager(new LinearLayoutManager(MainActivity.this));
        adapter = new CustomAdapter(MainActivity.this);
        employee_list.setAdapter(adapter);
    }

    // refresh list
    private void refreshAdapters() {
        // employee list load
        employees = DBHelper.getInstance(MainActivity.this).getEmployeeList();
        adapter.setData(employees);
    }

    @Override
    public void onClickEmployee(Employee employee) {
        Intent intent = new Intent(MainActivity.this, EmployeeActivity.class);
        intent.putExtra(com.example.examle.logic.Environment.ID, employee.getId());
        startActivity(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();
        refreshAdapters();
    }

    @Override
    public void onBackPressed(){
        if (back_pressed + 2000 > System.currentTimeMillis()) {
            super.onBackPressed();

            Example.exit(this);
        } else
            Toast.makeText(getBaseContext(), R.string.text_again_exit,
                    Toast.LENGTH_SHORT).show();
        back_pressed = System.currentTimeMillis();
    }
}
