package com.morlimoore.piggybankapi.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="tokens")
public class Token {

    @Id
    @Column(unique = true, nullable = false)
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String token;

    @Column(nullable = false, length = 25)
    private String purpose;

    @Column(nullable = false)
    private Boolean is_valid;

    @CreationTimestamp
    @Column(nullable = false)
    private Timestamp created_at;

    @ManyToOne
    @JoinColumn(name="user_id", nullable = false)
    private User user;
}
