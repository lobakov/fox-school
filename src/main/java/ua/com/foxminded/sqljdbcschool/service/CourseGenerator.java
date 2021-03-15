package ua.com.foxminded.sqljdbcschool.service;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

import ua.com.foxminded.sqljdbcschool.entity.Course;
import ua.com.foxminded.sqljdbcschool.utility.Randomizer;

public class CourseGenerator {

    private static final String[] COURSE_NAMES = new String[] {
            "Algebra", "Astronomy", "Biology", "Chemistry", "Economics",
            "English", "Geometry", "Geography", "History", "Physics"
    };

    private List<Course> courses;

    public CourseGenerator() {
        courses = new ArrayList<>();
        for (String course: COURSE_NAMES) {
            courses.add(new Course(course));
        }
    }

    public Supplier<Course> random = () -> {
        return courses.get(Randomizer.randomize(0, courses.size() - 1));
    };

    public List<Course> getCourses() {
        return this.courses;
    }
}
