package com.morlimoore.piggybankapi.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="users")
public class User {

    @Id
    @Column(unique = true, nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 25)
    private String first_name;

    @Column(nullable = false, length = 25)
    private String last_name;

    @Column(unique = true, nullable = false, length = 40)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false, length = 25)
    private String phone_number;

    @Column(nullable = false)
    private Date date_of_birth;

    @CreationTimestamp
    @Column(nullable = false)
    private Timestamp created_at;

    @Column(nullable = false)
    private Boolean is_active;

    @Column(nullable = false, length = 25)
    private String role;

    public User(String first_name, String last_name,
                String email, String password, Date date_of_birth,
                Boolean is_active, String role) {
        this.first_name = first_name;
        this.last_name = last_name;
        this.email = email;
        this.password = password;
        this.date_of_birth = date_of_birth;
        this.is_active = is_active;
        this.role = role;
    }

//    public List<String> getRoles() {
//        if (role.length() > 0) {
//            return Arrays.asList(role.split(","));
//        }
//        return new ArrayList<>();
//    }
}
