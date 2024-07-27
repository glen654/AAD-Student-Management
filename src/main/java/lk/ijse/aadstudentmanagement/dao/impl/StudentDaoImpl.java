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
        return null;
    }

    @Override
    public boolean saveStudent(StudentDto studentDto, Connection connection) {
        return false;
    }

    @Override
    public boolean deleteStudent(String studentId, Connection connection) {
        return false;
    }

    @Override
    public boolean updateStudent(String studentId, StudentDto studentDto, Connection connection) {
        return false;
    }
}
