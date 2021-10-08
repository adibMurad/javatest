package org.example.javatest.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "users")
public class UserData {
    @Id
    @Column(name = "user_name")
    @NotBlank(message = "User name is mandatory.")
    private String userName;
    @NotNull(message = "Password is mandatory.")
    private String password;
}
