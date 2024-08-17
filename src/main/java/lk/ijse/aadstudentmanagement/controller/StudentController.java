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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@WebServlet(urlPatterns = "/student", loadOnStartup = 2)
public class StudentController extends HttpServlet {
    static Logger logger = LoggerFactory.getLogger(StudentController.class);//adding student controller for logging
    Connection connection;

    @Override
    public void init() throws ServletException {
        logger.info("Initializing StudentController with call init method");// applying the log for the student controller
        try{
            /*var driver = getServletContext().getInitParameter("driver-class");
            var dbUrl = getServletContext().getInitParameter("dbURL");
            var userName = getServletContext().getInitParameter("dbUsername");
            var password = getServletContext().getInitParameter("dbPassword");*/

            var ctx = new InitialContext();
            DataSource pool = (DataSource) ctx.lookup("java:comp/env/jdbc/studentRegistration");
            this.connection = pool.getConnection();
        }catch (NamingException | SQLException e){
            e.printStackTrace();
        }

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //Todo: Save Student
        if(!req.getContentType().toLowerCase().startsWith("application/json") || req.getContentType() == null){
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST);//this is the worse-case secenario not the happy path
        }
        try(var writer = resp.getWriter()) {
            //This process calls deserialization
            Jsonb jsonb = JsonbBuilder.create();//create a jsonb type object
            //bind the student dto properties to the json object req.getReader look and
            // get the request and bind to the dto class
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
            //This is serialization
            var jsonb = JsonbBuilder.create();
            //Bind java object properties to the json object properties
            jsonb.toJson(student,writer);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //Todo: Update Student
        if(!req.getContentType().toLowerCase().startsWith("application/json") || req.getContentType() == null){
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
        }
        try(var writer = resp.getWriter()){
            var studentId = req.getParameter("id");
            Jsonb jsonb = JsonbBuilder.create();
            var studentDao = new StudentDaoImpl();
            var updateStudent = jsonb.fromJson(req.getReader(), StudentDto.class);
            if(studentDao.updateStudent(studentId,updateStudent,connection)){
                resp.setStatus(HttpServletResponse.SC_NO_CONTENT);
            }else{
                writer.write("Update Failed");
                resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
            }
        }catch (JsonException e){
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            e.printStackTrace();
        }


    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //Todo: Delete Student
        var studentId = req.getParameter("id");
        try(var writer = resp.getWriter()){
            var studentDao = new StudentDaoImpl();
            if(studentDao.deleteStudent(studentId,connection)){
                resp.setStatus(HttpServletResponse.SC_NO_CONTENT);
            }else{
                resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
                writer.write("Delete failed");
            }
        }catch (Exception e){
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            throw new RuntimeException(e);
        }
    }
}
