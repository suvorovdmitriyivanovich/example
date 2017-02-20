package com.example.example.logic;

import java.util.ArrayList;

public class Environment {

    public static String ID = "id";

    public static ArrayList<String> cities = new ArrayList<String>() {{
        add("Київ");
        add("Харків");
        add("Одеса");
        add("Львів");
    }};

    public static ArrayList<String> post = new ArrayList<String>() {{
        add("лікар");
        add("фармацевт");
        add("медсестра");
        add("фельдшер");
        add("водій");
    }};
}
