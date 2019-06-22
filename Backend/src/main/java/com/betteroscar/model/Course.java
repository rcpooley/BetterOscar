package com.betteroscar.model;

import java.util.List;

public class Course {

    private int id;

    private String num;

    private String name;

    private int credits;

    private String prerequisites;

    public int getID() {
        return id;
    }

    public String getNum() {
        return num;
    }

    public String getName() {
        return name;
    }

    public int getCredits() {
        return credits;
    }

    public String getPrerequisites() {
        return prerequisites;
    }

    public List<Section> getSections() {
        return sections;
    }

    private List<Section> sections;

}
