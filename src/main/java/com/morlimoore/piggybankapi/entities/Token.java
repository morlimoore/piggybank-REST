package com.morlimoore.piggybankapi.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="tokens")
public class Token extends BaseEntity {

    @Column(nullable = false)
    private String token;

    @Column(nullable = false, length = 25)
    private String purpose;

    @Column(name = "is_valid", nullable = false)
    private Boolean isValid;

    //TODO: Investigate possible wrong use of annotation mapping
    @ManyToOne
    @JoinColumn(name="user_id", nullable = false)
    private User user;
}
