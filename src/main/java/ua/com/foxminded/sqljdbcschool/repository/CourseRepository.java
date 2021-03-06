package ua.com.foxminded.sqljdbcschool.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import ua.com.foxminded.sqljdbcschool.entity.Course;
import ua.com.foxminded.sqljdbcschool.service.ConnectionService;

public class CourseRepository extends JdbcSchoolRepository<Course> {

    public CourseRepository(ConnectionService connectionService) {
        super(connectionService);
    }

    public List<Course> findAllCoursesByStudentId(Long studentId) {
        List<Course> result = new ArrayList<>();
        String request = "SELECT courses.id, courses.name, courses.description FROM"
                + "(SELECT course_id FROM students_courses WHERE student_id = ?) AS cid "
                + "LEFT JOIN courses ON cid.course_id = courses.id";
        try (Connection connection = connectionService.getConnection();
                        PreparedStatement statement = connection.prepareStatement(request)) {
            statement.setLong(1, studentId);
            try (ResultSet resultSet = statement.executeQuery()) {
                while(resultSet.next()){
                    Long id  = resultSet.getLong("id");
                    String name = resultSet.getString("name");
                    String description = resultSet.getString("description");
                    Course course = new Course(name, description);
                    course.setId(id);
                    result.add(course);
                }
            } catch (SQLException sqlex) {
                sqlex.printStackTrace();
            }
        } catch (SQLException sqlex) {
            sqlex.printStackTrace();
        }
        return result;
    }

    public List<Course> findAll() {
        List<Course> result = new ArrayList<>();
        String sqlStatement = "SELECT * FROM courses";

        try (Connection connection = connectionService.getConnection();
                    Statement statement = connection.createStatement();
                    ResultSet resultSet = statement.executeQuery(sqlStatement)) {
            while(resultSet.next()){
                Long id  = resultSet.getLong("id");
                String name = resultSet.getString("name");
                String description = resultSet.getString("description");
                Course course = new Course(name, description);
                course.setId(id);
                result.add(course);
            }
        } catch (SQLException sqlex) {
            sqlex.printStackTrace();
        }
        return result;
    }

    @Override
    public Course save(Course course) {
        String sqlStatement = "INSERT INTO courses (name, description) VALUES (?, ?)";
        try (Connection connection = connectionService.getConnection();
                PreparedStatement statement = connection.prepareStatement(sqlStatement,
                        Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, course.getName());
            statement.setString(2, course.getDescription());
            statement.executeUpdate();
            try (ResultSet keys = statement.getGeneratedKeys()) {
                if (keys.next()) {
                    course.setId(keys.getLong(1));
                }
            } catch (SQLException sqlex) {
                sqlex.printStackTrace();
            }
        } catch (SQLException sqlex) {
            sqlex.printStackTrace();
        }
        return course;
    }
}
