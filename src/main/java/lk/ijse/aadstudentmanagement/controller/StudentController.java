package lk.ijse.aadstudentmanagement.controller;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
@AllArgsConstructor
@NoArgsConstructor
@Data
public class StudentController implements Serializable {
    private String id;
    private String name;
    private String city;
    private String email;
    private String level;
}
