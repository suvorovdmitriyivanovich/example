package com.example.examle.objects;

import com.example.examle.managers.DateManager;

import java.util.Calendar;
import java.util.Date;

public class Employee {

    private int id;
    private String firstName;
    private String lastName;
    private Date birthday;
    private String city;
    private String post;

    public Employee() {
        id = -1;
        firstName = "";
        lastName = "";
        birthday = DateManager.getDateManager().getStartDate();
        city = "";
        post = "";
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFullName() {
        return lastName + " " + firstName;
    }

    public Date getBirthday() {
        return birthday;
    }

    public String getStringBirthday() {
        String date = DateManager.getDateManager().getDateStringFormat(birthday);
        if(date == null)
            return "";
        else
            return date;
    }

    public String getAge() {
        Calendar dob = Calendar.getInstance();
        Calendar today = Calendar.getInstance();

        dob.setTime(birthday);

        int age = today.get(Calendar.YEAR) - dob.get(Calendar.YEAR);

        if (today.get(Calendar.DAY_OF_YEAR) < dob.get(Calendar.DAY_OF_YEAR)){
            age--;
        }

        String sAge = String.valueOf(age);

        if (sAge.length() > 1 && sAge.charAt(sAge.length()-2) == '1') {
            sAge = sAge + " років";
        }
        else if (sAge.length() > 0 && sAge.charAt(sAge.length()-1) == '1') {
            sAge = sAge + " рік";
        }
        else if (sAge.length() > 0 && (sAge.charAt(sAge.length()-1) == '2' || sAge.charAt(sAge.length()-1) == '3' || sAge.charAt(sAge.length()-1) == '4')) {
            sAge = sAge + " роки";
        }
        else {
            sAge = sAge + " років";
        }
        return sAge;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getPost() {
        return post;
    }

    public void setPost(String post) {
        this.post = post;
    }
}
