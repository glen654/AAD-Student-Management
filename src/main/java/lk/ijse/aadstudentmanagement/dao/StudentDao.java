package lk.ijse.aadstudentmanagement.dao;

import lk.ijse.aadstudentmanagement.dto.StudentDto;

import java.sql.Connection;
import java.sql.SQLException;

public interface StudentDao{
    StudentDto getStudent(String studentId, Connection connection) throws SQLException;

    boolean saveStudent(StudentDto studentDto, Connection connection);

    boolean deleteStudent(String studentId,Connection connection);

    boolean updateStudent(String studentId,StudentDto studentDto,Connection connection);
}
