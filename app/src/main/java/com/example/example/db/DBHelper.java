package com.example.example.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.example.managers.DateManager;
import com.example.example.objects.Employee;

import java.util.ArrayList;
import java.util.Date;

public class DBHelper extends SQLiteOpenHelper {

    private static final String EQUALS = " = ?";
    private static final String MORE = " > ?";
    private static final String NOT = " != ?";
    private static final String AND = " AND ";
    private static final String OR = " OR ";

    //V1
    private static final String ID = "ID";
    private static final String EMPLOYEE = "EMPLOYEE";
    private static final String FIRSTNAME = "FIRSTNAME";
    private static final String LASTNAME = "LASTNAME";
    private static final String BIRTHDAY = "BIRTHDAY";
    private static final String CITY = "CITY";
    private static final String POST = "POST";
    private static final String EDITDATE = "EDITDATE";

    private static DBHelper sInstance;
    private static final String DB_NAME = "mySuperDB.db";
    private static final int DB_VERSION = 1;

    public static synchronized DBHelper getInstance(Context context){
        if (sInstance == null) {
            sInstance = new DBHelper(context);
        }
        return sInstance;
    }

    private DBHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE EMPLOYEE (ID INTEGER PRIMARY KEY, FIRSTNAME VARCHAR, LASTNAME VARCHAR, BIRTHDAY INTEGER, CITY VARCHAR, POST VARCHAR, EDITDATE INTEGER)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion < 2) {

        }
    }

    public ArrayList<Employee> getEmployeeList() {
        ArrayList<Employee> employees = new ArrayList<>();
        Employee employee;
        Cursor c =  getReadableDatabase().query(EMPLOYEE, null, null,
                null, null, null, LASTNAME + ", " + FIRSTNAME + " ASC");
        if (c.moveToFirst()) {

            int idColIndex = c.getColumnIndex(ID);
            int firstNameColIndex = c.getColumnIndex(FIRSTNAME);
            int lastNameColIndex = c.getColumnIndex(LASTNAME);
            int birthdayColIndex = c.getColumnIndex(BIRTHDAY);
            int cityColIndex = c.getColumnIndex(CITY);
            int postColIndex = c.getColumnIndex(POST);

            do {
                employee = new Employee();
                employee.setId(c.getInt(idColIndex));
                employee.setFirstName(c.getString(firstNameColIndex));
                employee.setLastName(c.getString(lastNameColIndex));
                employee.setBirthday(new Date(c.getLong(birthdayColIndex)));
                employee.setCity(c.getString(cityColIndex));
                employee.setPost(c.getString(postColIndex));

                employees.add(employee);
            } while (c.moveToNext());
        }
        c.close();

        return employees;
    }

    public Employee getEmployeeById(int id) {
        Employee employee = new Employee();
        Cursor c =  getReadableDatabase().query(EMPLOYEE, null, ID + EQUALS,
                new String[]{String.valueOf(id)}, null, null, null);
        if (c.moveToFirst()) {

            int idColIndex = c.getColumnIndex(ID);
            int firstNameColIndex = c.getColumnIndex(FIRSTNAME);
            int lastNameColIndex = c.getColumnIndex(LASTNAME);
            int birthdayColIndex = c.getColumnIndex(BIRTHDAY);
            int cityColIndex = c.getColumnIndex(CITY);
            int postColIndex = c.getColumnIndex(POST);

            employee.setId(c.getInt(idColIndex));
            employee.setFirstName(c.getString(firstNameColIndex));
            employee.setLastName(c.getString(lastNameColIndex));
            employee.setBirthday(new Date(c.getLong(birthdayColIndex)));
            employee.setCity(c.getString(cityColIndex));
            employee.setPost(c.getString(postColIndex));
        }
        c.close();

        return employee;
    }

    public void saveEmployee(Employee employee) {
        ContentValues cv = new ContentValues();
        Cursor c =  getReadableDatabase().query(EMPLOYEE, null, ID + EQUALS,
                new String[]{String.valueOf(employee.getId())}, null, null, null);

        String id = "";
        if (c.moveToFirst()) {
            int idColIndex = c.getColumnIndex(ID);
            id = c.getString(idColIndex);
        }
        c.close();

        cv.put(FIRSTNAME, employee.getFirstName());
        cv.put(LASTNAME, employee.getLastName());
        cv.put(BIRTHDAY, employee.getBirthday().getTime());
        cv.put(CITY, employee.getCity());
        cv.put(POST, employee.getPost());
        cv.put(EDITDATE, DateManager.getDateManager().getCurrentDate().getTime());

        if (id.isEmpty()) {
            getWritableDatabase().insert(EMPLOYEE, null, cv);
        }
        else {
            getWritableDatabase().update(EMPLOYEE, cv, ID + EQUALS,
                    new String[]{id});
        }
    }

    public void removeEmployeeById(int employeeId) {
        Cursor c =  getReadableDatabase().query(EMPLOYEE, null, ID + EQUALS,
                new String[]{String.valueOf(employeeId)}, null, null, null);

        String id = "";

        if (c.moveToFirst()) {
            int idColIndex = c.getColumnIndex(ID);
            id = c.getString(idColIndex);
        }
        c.close();

        if (!id.isEmpty()) {
            getWritableDatabase().delete(EMPLOYEE, ID + "=" + id, null);
        }
    }
}