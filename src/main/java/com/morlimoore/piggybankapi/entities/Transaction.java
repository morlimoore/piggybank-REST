package com.morlimoore.piggybankapi.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="transactions")
public class Transaction {

    @Id
    @Column(unique = true, nullable = false)
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 25)
    private String type;

    @Column(nullable = false)
    private Long amount;

    @Column(nullable = false)
    @CreationTimestamp
    private Timestamp created_at;

    @Column(length = 100)
    private String remarks;

    @ManyToOne
    @JoinColumn(name="user_id", nullable = false)
    private User user;
}
