package dao.dto;

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
public class PersonDto {
    private int id;
    private String email;
    private String firstName;
    private String lastName;
    private LocalDate dob;
    private String password;
    private String address;
    private String phone;

    public PersonDto(String email, String firstName, String lastName, LocalDate dob, String password, String address, String phone) {
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.dob = dob;
        this.password = password;
        this.address = address;
        this.phone = phone;
    }
}
