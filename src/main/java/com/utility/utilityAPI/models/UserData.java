package com.utility.utilityAPI.models;

import jakarta.persistence.*;
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
    @Column(columnDefinition = "int default 0")
    private int roomNo;
    @Column(columnDefinition = "int default 1")
    private int enabled;
    @Column(columnDefinition = "varchar(255) default 'ROLE_USER'")
    private String role;
}
