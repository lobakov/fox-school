package ua.com.foxminded.sqljdbcschool.entity;

import java.util.HashSet;
import java.util.Set;

public class Course {

    private Long id;
    private String name;
    private String description;
    private Set<Student> students;

    public Course() {

    }

    public Course(String name) {
        this.name = name;
        this.students = new HashSet<>();
    }

    public Course(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public Long getId() {
        return this.id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescription() {
        return this.description;
    }

    public boolean addStudent(Student student) {
        return this.students.add(student);
    }

    public boolean removeStudent(Student student) {
        return this.students.remove(student);
    }

    public void setStudent(Set<Student> students) {
        this.students = students;
    }

    public Set<Student> getStudents() {
        return this.students;
    }

    @Override
    public String toString() {
        return "Course [name=" + name + "]";
    }
}
