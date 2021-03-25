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
import ua.com.foxminded.sqljdbcschool.entity.Student;
import ua.com.foxminded.sqljdbcschool.service.ConnectionService;

public class GroupRepository extends JdbcSchoolRepository<Group> {

    public GroupRepository(ConnectionService connectionService) {
        super(connectionService);
    }

    public Long getStudentCountByGroupId(Long id) {
        String query = "SELECT COUNT(*) FROM students WHERE group_id = ?";
        long result = 0;

        try (Connection connection = connectionService.getConnection();
                    PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setLong(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
               if (resultSet.next()) {
                    result  = resultSet.getLong("count");
                }
            } catch (SQLException sqlex) {
                sqlex.printStackTrace();
            }
        } catch (SQLException sqlex) {
            sqlex.printStackTrace();
        }
        return result;
    }

    public Group findGroupById(Long id) {
        String query = "SELECT * FROM groups WHERE id = ?";
        Group group = null;
        try (Connection connection = connectionService.getConnection();
                    PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setLong(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
               while (resultSet.next()) {
                   Long groupId  = resultSet.getLong("id");
                   String name = resultSet.getString("name");
                   group = new Group(name);
                   group.setId(groupId);
               }
            } catch (SQLException sqlex) {
                sqlex.printStackTrace();
            }
        } catch (SQLException sqlex) {
            sqlex.printStackTrace();
        }
        return group;
    }

    public List<Group> findAllGroupsByStudentCount(Long count) {
        List<Group> result = new ArrayList<>();
        String request;

        if (count > 0) {
            request = "SELECT groups.id, groups.name, count FROM groups JOIN "
                    + "(SELECT group_id, count(*) FROM students WHERE group_id IS NOT NULL GROUP BY group_id) AS cnt "
                    + "ON cnt.group_id = groups.id WHERE count <= ? ORDER BY count";
        } else {
            request = "SELECT groups.id, groups.name FROM groups WHERE groups.id NOT IN (SELECT group_id FROM students "
                    + "WHERE group_id IS NOT NULL)";
        }

        try (Connection connection = connectionService.getConnection();
                        PreparedStatement statement = connection.prepareStatement(request)) {
            if (count > 0) {
                statement.setLong(1, count);
            }
            try (ResultSet resultSet = statement.executeQuery()) {
                while(resultSet.next()){
                    Long id  = resultSet.getLong("id");
                    String name = resultSet.getString("name");
                    Group group = new Group(name);
                    group.setId(id);
                    result.add(group);
                }
            } catch (SQLException sqlex) {
                sqlex.printStackTrace();
            }
        } catch (SQLException sqlex) {
            sqlex.printStackTrace();
        }
        System.out.println(result);
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
