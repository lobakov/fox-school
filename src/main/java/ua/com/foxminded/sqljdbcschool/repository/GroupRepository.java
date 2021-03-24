package ua.com.foxminded.sqljdbcschool.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import ua.com.foxminded.sqljdbcschool.entity.Course;
import ua.com.foxminded.sqljdbcschool.entity.Group;
import ua.com.foxminded.sqljdbcschool.service.ConnectionService;

public class GroupRepository extends JdbcSchoolRepository<Group> {

    public GroupRepository(ConnectionService connectionService) {
        super(connectionService);
    }

    public List<Group> findAllGroupsByStudentCount(Long count) {
        List<Group> result = new ArrayList<>();
        String request = "SELECT courses.id, courses.name, courses.description FROM"
                + "(SELECT course_id FROM students_courses WHERE student_id = ?)"
                + "LEFT JOIN courses ON course_id = id";
        try (Connection connection = connectionService.getConnection();
                        PreparedStatement statement = connection.prepareStatement(request)) {
            statement.setLong(1, studentId);
            try (ResultSet resultSet = statement.executeQuery(request)) {
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

    @Override
    public void save(Group group) {
        String sqlStatement = "INSERT INTO groups (name) VALUES (?)";
        try (Connection connection = connectionService.getConnection();
                PreparedStatement statement = connection.prepareStatement(sqlStatement,
                        Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, group.getName());
            statement.executeUpdate();
            try (ResultSet keys = statement.getGeneratedKeys()) {
                if (keys.next()) {
                    group.setId(keys.getLong(1));
                }
            } catch (SQLException sqlex) {
                sqlex.printStackTrace();
            }
        } catch (SQLException sqlex) {
            sqlex.printStackTrace();
        }
    }
}
