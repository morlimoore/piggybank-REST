package com.morlimoore.piggybankapi.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="users")
public class User extends BaseEntity {

    @Column(name = "first_name", nullable = false, length = 25)
    private String firstName;

    @Column(name = "last_name", nullable = false, length = 25)
    private String lastName;

    @Column(unique = true, nullable = false, length = 40)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(name = "phone_number", nullable = false, length = 25)
    private String phoneNumber;

    @Column(name = "date_of_birth", nullable = false)
    private Date dateOfBirth;

    @Column(name = "is_enabled", nullable = false)
    private Boolean isEnabled;

    @Column(nullable = false, length = 25)
    private String role;

    public User(String firstName, String lastName,
                String email, String password, Date dateOfBirth,
                Boolean isEnabled, String role) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.dateOfBirth = dateOfBirth;
        this.isEnabled = isEnabled;
        this.role = role;
    }

//    public List<String> getRoles() {
//        if (role.length() > 0) {
//            return Arrays.asList(role.split(","));
//        }
//        return new ArrayList<>();
//    }
}
