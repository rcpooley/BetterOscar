package com.betteroscar.model;

import java.util.List;

public class Subject {

    private int id;

    private String abbreviation;

    private String name;

    private List<Course> courses;

    public int getID() {
        return id;
    }

    public String getAbbreviation() {
        return abbreviation;
    }

    public String getName() {
        return name;
    }

    public List<Course> getCourses() {
        return courses;
    }
}
