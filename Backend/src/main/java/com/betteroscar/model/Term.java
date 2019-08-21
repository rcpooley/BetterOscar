package com.betteroscar.model;

import java.util.List;

public class Term {

  private int id;

  private String termID;

  private String name;

  /**
   * Constructs a new term object.
   *
   * @param id The term's ID
   * @param termID The term's GT ID
   * @param name The term's name
   */
  public Term(int id, String termID, String name) {
    this.id = id;
    this.termID = termID;
    this.name = name;
  }

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
    return null;
  }

  @Override
  public String toString() {
    return "Term {id=" + id + ", term_id=" + termID + ", name=" + name + "}";
  }
}
