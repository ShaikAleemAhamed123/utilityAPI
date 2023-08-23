package com.utility.utilityAPI.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
public class Expense{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String payer;
    private String payee;
    private double amount;
    private String tag;
}
