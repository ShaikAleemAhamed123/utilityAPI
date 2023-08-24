package com.utility.utilityAPI.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;
import org.springframework.data.relational.core.mapping.Table;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name="expense")
public class Expense{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String payer;
    private String payee;
    private double amount;
    private String tag;
}
