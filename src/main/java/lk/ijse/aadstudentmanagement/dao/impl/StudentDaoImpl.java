package lk.ijse.aadstudentmanagement.dao.impl;

import lk.ijse.aadstudentmanagement.dao.StudentDao;
import lk.ijse.aadstudentmanagement.dto.StudentDto;

import java.sql.Connection;
import java.sql.SQLException;

public class StudentDaoImpl implements StudentDao {
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
