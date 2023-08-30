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
public class UserData {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String userName;
    @Id
    private String userHandle;
    private String password;
    private int roomNo;
    private int enabled;
    private String role;
}