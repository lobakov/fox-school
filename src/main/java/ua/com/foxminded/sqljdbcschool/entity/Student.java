package ua.com.foxminded.sqljdbcschool.entity;

import java.util.HashSet;
import java.util.Set;

public class Student {

    private Long id;
    private Long groupId;
    private String firstName;
    private String lastName;
    private Set<Course> courses;
    private Group group;

    public Student() {

    }

    public Student(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.group = new Group();
        this.courses = new HashSet<>();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

    public void setGroupId(Long id) {
        this.groupId = id;
    }

    public Long getGroupId() {
        return this.groupId;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getFirstName() {
        return this.firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getLastName() {
        return this.lastName;
    }

    public boolean addCourse(Course course) {
        return this.courses.add(course);
    }

    public boolean removeCourse(Course course) {
        return this.courses.remove(course);
    }

    public void setCourses(Set<Course> courses) {
        this.courses = courses;
    }

    public Set<Course> getCourses() {
        return this.courses;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((firstName == null) ? 0 : firstName.hashCode());
        result = prime * result + ((lastName == null) ? 0 : lastName.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (!(obj instanceof Student)) {
            return false;
        }
        Student other = (Student) obj;
        return (id == other.id) &&
               (firstName.equals(other.firstName)) &&
               (lastName.equals(other.lastName));
    }

    @Override
    public String toString() {
        String group = (this.group == null) ? "none" : (this.group.getName());
        return id + ". " + firstName + " " + lastName + "  group: " + group;
    }
}
