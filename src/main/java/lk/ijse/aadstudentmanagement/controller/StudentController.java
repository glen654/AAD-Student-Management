package lk.ijse.aadstudentmanagement.controller;


import jakarta.json.JsonException;
import jakarta.json.bind.Jsonb;
import jakarta.json.bind.JsonbBuilder;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lk.ijse.aadstudentmanagement.dao.impl.StudentDaoImpl;
import lk.ijse.aadstudentmanagement.dto.StudentDto;
import lk.ijse.aadstudentmanagement.util.UtilProcess;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.UUID;

@WebServlet(urlPatterns = "")
public class StudentController extends HttpServlet {
    Connection connection;
    static String SAVE_STUDENT = "INSERT INTO student (id,name,city,email,level) VALUES (?,?,?,?,?)";
    static String GET_STUDENT = "SELECT * FROM student WHERE id=?";
    static String UPDATE_STUDENT = "UPDATE student SET name=?,city=?,email=?,level=? WHERE id=?";
    static String DELETE_STUDENT = "DELETE FROM student WHERE id=?";

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
        try(var writer = resp.getWriter()) {
            Jsonb jsonb = JsonbBuilder.create();
            StudentDto studentDto = jsonb.fromJson(req.getReader(),StudentDto.class);
            studentDto.setId(UtilProcess.generateId());
            var studentDao = new StudentDaoImpl();
            if(studentDao.saveStudent(studentDto,connection)){
                writer.write("Save student Successfully");
                resp.setStatus(HttpServletResponse.SC_CREATED);
            }else{
                writer.write("Save student failed");
                resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
            }
        }catch (JsonException e){
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            throw new RuntimeException(e);
        }

    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //Todo: Get Student Details
        var studentId = req.getParameter("id");
        var studentDao = new StudentDaoImpl();
        try(var writer = resp.getWriter()) {
            var student = studentDao.getStudent(studentId,connection);
            System.out.println(student);
            resp.setContentType("application/json");
            var jsonb = JsonbBuilder.create();
            jsonb.toJson(student,writer);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
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
