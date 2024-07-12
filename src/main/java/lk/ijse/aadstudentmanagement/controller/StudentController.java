package lk.ijse.aadstudentmanagement.controller;


import jakarta.json.bind.Jsonb;
import jakarta.json.bind.JsonbBuilder;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lk.ijse.aadstudentmanagement.dto.StudentDto;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.UUID;

@WebServlet(urlPatterns = "")
public class StudentController extends HttpServlet {
    Connection connection;
    static String SAVE_STUDENT = "INSERT INTO student VALUES(?,?,?,?,?)";
    static String GET_STUDENT = "SELECT * FROM student WHERE id = ?";

    @Override
    public void init() throws ServletException {
        try{
            var driver = getServletContext().getInitParameter("driver-class");
            var dbUrl = getServletContext().getInitParameter("dbURL");
            var userName = getServletContext().getInitParameter("dbUsername");
            var password = getServletContext().getInitParameter("dbPassword");
            Class.forName(driver);
            this.connection = DriverManager.getConnection(dbUrl,userName,password);
        }catch (ClassNotFoundException | SQLException e){
            e.printStackTrace();
        }

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //Todo: Save Student
        if(!req.getContentType().toLowerCase().startsWith("application/json") || req.getContentType() == null){
            resp.sendError(HttpServletResponse.SC_UNSUPPORTED_MEDIA_TYPE);
        }
        String id = UUID.randomUUID().toString();
        Jsonb jsonb = JsonbBuilder.create();// This is the entry point Building a JSON b type object
        StudentDto studentDto = jsonb.fromJson(req.getReader(), StudentDto.class);// Read the JSON by get reader method and bind to student dto class
        studentDto.setId(id);
        System.out.println(studentDto);

        try {
            var preparedStatement = connection.prepareStatement(SAVE_STUDENT);
            preparedStatement.setString(1,studentDto.getId());
            preparedStatement.setString(2,studentDto.getName());
            preparedStatement.setString(3,studentDto.getCity());
            preparedStatement.setString(4,studentDto.getEmail());
            preparedStatement.setString(5,studentDto.getLevel());

            if(preparedStatement.executeUpdate() != 0){
                resp.getWriter().write("Student Saved");
            }else{
                resp.getWriter().write("Student Not Saved");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //Todo: Get Student Details
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //Todo: Update Student
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //Todo: Delete Student
    }
}
