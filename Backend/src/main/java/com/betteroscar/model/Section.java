package com.betteroscar.model;

import java.util.List;

public class Section {

  private int id;

  private int crn;

  private String code;

  private String instructor;

  private List<SectionTime> times;

  private int capacity;

  private int remaining;

  private int waitlistCapacity;

  private int waitlistRemaining;

  public int getID() {
    return id;
  }

  public int getCrn() {
    return crn;
  }

  public String getCode() {
    return code;
  }

  public String getInstructor() {
    return instructor;
  }

  public List<SectionTime> getTimes() {
    return times;
  }

  public int getCapacity() {
    return capacity;
  }

  public int getRemaining() {
    return remaining;
  }

  public int getWaitlistCapacity() {
    return waitlistCapacity;
  }

  public int getWaitlistRemaining() {
    return waitlistRemaining;
  }
}
