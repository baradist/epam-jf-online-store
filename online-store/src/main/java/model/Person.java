package model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

/**
 * Created by Oleg Grigorjev on 13.05.2016.
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Person {
    private int id;
    private String email;
    private String firstName;
    private String lastName;
    private LocalDate dob;
    private String password;
    private String address;
    private String phone;

    public enum Role {
        VISITOR, CUSTOMER, MANAGER, ADMINISTRATOR
    }
}
