package com.betteroscar.model;

import java.util.List;

public class Term {

  private int id;

  private String termID;

  private String name;

  private List<Subject> subjects;

  public int getID() {
    return id;
  }

  public String getTermID() {
    return termID;
  }

  public String getName() {
    return name;
  }

  public List<Subject> getSubjects() {
    return subjects;
  }
}
