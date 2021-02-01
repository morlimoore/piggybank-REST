package com.morlimoore.piggybankapi.entities;

import com.morlimoore.piggybankapi.util.TransactionEnum;
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

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 25)
    private TransactionEnum type;

    @Column(nullable = false)
    private Long amount;

    @Column(length = 100, nullable = false)
    private String remarks;

    @ManyToOne(cascade = CascadeType.ALL)
    private User user;
}
