package com.betteroscar.test;

import com.betteroscar.model.Course;
import com.betteroscar.model.Section;
import com.betteroscar.model.SectionTime;
import com.betteroscar.model.Subject;
import com.betteroscar.model.Term;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TestingData {

  public static TestingData create() {
    return new TestingData();
  }

  private List<Term> terms;

  private Map<Term, List<Subject>> subjects;

  private Map<Subject, List<Course>> courses;

  private Map<Course, List<Section>> sections;

  private Map<Section, List<SectionTime>> sectionTimes;

  private TestingData() {
    terms = new ArrayList<>();
    subjects = new HashMap<>();
    courses = new HashMap<>();
    sections = new HashMap<>();
    sectionTimes = new HashMap<>();
  }

  public List<Term> getTerms() {
    return terms;
  }

  public List<Subject> getSubjects(Term term) {
    return subjects.getOrDefault(term, new ArrayList<>());
  }

  public List<Course> getCourses(Subject subject) {
    return courses.getOrDefault(subject, new ArrayList<>());
  }

  public List<Section> getSections(Course course) {
    return sections.getOrDefault(course, new ArrayList<>());
  }

  public List<SectionTime> getSectionTimes(Section section) {
    return sectionTimes.getOrDefault(section, new ArrayList<>());
  }

  public TestingData addTerms(Term... terms) {
    this.terms.addAll(Arrays.asList(terms));
    return this;
  }

  public TestingData addSubjects(Term term, Subject... subjects) {
    List<Subject> s = getSubjects(term);
    s.addAll(Arrays.asList(subjects));
    this.subjects.put(term, s);
    return this;
  }

  public TestingData addCourses(Subject subject, Course... courses) {
    List<Course> c = getCourses(subject);
    c.addAll(Arrays.asList(courses));
    this.courses.put(subject, c);
    return this;
  }

  public TestingData addSections(Course course, Section... sections) {
    List<Section> s = getSections(course);
    s.addAll(Arrays.asList(sections));
    this.sections.put(course, s);
    return this;
  }

  public TestingData addSectionTimes(Section section, SectionTime... times) {
    List<SectionTime> s = getSectionTimes(section);
    s.addAll(Arrays.asList(times));
    this.sectionTimes.put(section, s);
    return this;
  }
}
