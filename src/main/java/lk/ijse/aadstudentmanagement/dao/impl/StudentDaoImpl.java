package lk.ijse.aadstudentmanagement.dao.impl;

import lk.ijse.aadstudentmanagement.dao.StudentDao;
import lk.ijse.aadstudentmanagement.dto.StudentDto;

import java.sql.Connection;
import java.sql.SQLException;

public class StudentDaoImpl implements StudentDao {
    static String SAVE_STUDENT = "INSERT INTO student (id,name,city,email,level) VALUES (?,?,?,?,?)";
    static String GET_STUDENT = "SELECT * FROM student WHERE id=?";
    static String UPDATE_STUDENT = "UPDATE student SET name=?,city=?,email=?,level=? WHERE id=?";
    static String DELETE_STUDENT = "DELETE FROM student WHERE id=?";
    @Override
    public StudentDto getStudent(String studentId, Connection connection) throws SQLException {
        var studentDto = new StudentDto();
        try {
            var preparedStatement = connection.prepareStatement(GET_STUDENT);
            preparedStatement.setString(1,studentId);
            var resultSet = preparedStatement.executeQuery();
            while(resultSet.next()){
                studentDto.setId(resultSet.getString("id"));
                studentDto.setName(resultSet.getString("name"));
                studentDto.setCity(resultSet.getString("city"));
                studentDto.setEmail(resultSet.getString("email"));
                studentDto.setLevel(resultSet.getString("level"));
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return studentDto;

    }

    @Override
    public boolean saveStudent(StudentDto studentDto, Connection connection) {
        try {
            var preparedStatement = connection.prepareStatement(SAVE_STUDENT);
            preparedStatement.setString(1,studentDto.getId());
            preparedStatement.setString(2,studentDto.getName());
            preparedStatement.setString(3,studentDto.getCity());
            preparedStatement.setString(4,studentDto.getEmail());
            preparedStatement.setString(5,studentDto.getLevel());
            return preparedStatement.executeUpdate() != 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean deleteStudent(String studentId, Connection connection) {
        return false;
    }

    @Override
    public boolean updateStudent(String studentId, StudentDto studentDto, Connection connection) {
        try {
            var preparedStatement = connection.prepareStatement(UPDATE_STUDENT);
            preparedStatement.setString(1,studentDto.getName());
            preparedStatement.setString(2,studentDto.getCity());
            preparedStatement.setString(3,studentDto.getEmail());
            preparedStatement.setString(4,studentDto.getLevel());
            return preparedStatement.executeUpdate() != 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
