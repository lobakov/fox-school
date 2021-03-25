package ua.com.foxminded.sqljdbcschool.service;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

import ua.com.foxminded.sqljdbcschool.entity.Course;
import ua.com.foxminded.sqljdbcschool.utility.Randomizer;

public class CourseCreator {

    private static final String[] COURSE_NAMES = new String[] {
            "algebra", "astronomy", "biology", "chemistry", "economics",
            "english", "geometry", "geography", "history", "physics"
    };

    private static final String[] COURSE_DESCRIPTIONS = new String[] {
            "Algebra course", "Astronomy course", "Biology course", "Chemistry course", "Economics course",
            "English course", "Geometry course", "Geography course", "History course", "Physics course"
    };

    private List<Course> courses;

    public CourseCreator() {
        courses = new ArrayList<>();
        for (int i = 0; i < COURSE_NAMES.length; i++) {
            Course course = new Course(COURSE_NAMES[i], COURSE_DESCRIPTIONS[i]);
            courses.add(course);
        }
    }

    public Supplier<Course> random = () -> {
        return courses.get(Randomizer.randomize(0, courses.size() - 1));
    };

    public List<Course> getCourses() {
        return this.courses;
    }
}
