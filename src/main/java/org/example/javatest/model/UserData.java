package org.example.javatest.model;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "users")
public class UserData {
    @Id
    @Column(name = "user_name")
    private String userName;
    private String password;
}
