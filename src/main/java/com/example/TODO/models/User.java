package com.example.TODO.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Data;
import jakarta.validation.constraints.Email;
@Entity
@Data
@Builder
public class User {
    @Id
    @GeneratedValue
    Long id;
    @Email
    String email;
    String password;
}
