package com.morlimoore.piggybankapi.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="transactions")
public class Transaction extends BaseEntity {

    @Column(nullable = false, length = 25)
    private String type;

    @Column(nullable = false)
    private Long amount;

    @Column(length = 100)
    private String remarks;

    @ManyToOne
    @JoinColumn(name="user_id", nullable = false)
    private User user;
}
