package lk.ijse.aadstudentmanagement.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
@AllArgsConstructor
@NoArgsConstructor
@Data
public class StudentDto implements Serializable {
    private String id;
    private String name;
    private String city;
    private String email;
    private String level;
}
