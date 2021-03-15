package ua.com.foxminded.sqljdbcschool.entity;

import java.util.HashSet;
import java.util.Set;

public class Group {

    private Long id;
    private String name;
    private Set<Student> students;

    public Group() {

    }

    public Group(String name) {
        this.name = name;
        this.students = new HashSet<>();
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

    public boolean addStudent(Student student) {
        return this.students.add(student);
    }

    public boolean removeStudent(Student student) {
        return this.students.remove(student);
    }

    public void setStudent(Set<Student> students) {
        this.students = students;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        result = prime * result + ((students == null) ? 0 : students.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (!(obj instanceof Group)) {
            return false;
        }
        Group other = (Group) obj;
        return //(id == other.id) &&
               (name.equals(other.name));
        //&&       (students.equals(other.students));
    }

    public Set<Student> getStudents() {
        return this.students;
    }

    @Override
    public String toString() {
        return "Group: " + name;
    }
}
