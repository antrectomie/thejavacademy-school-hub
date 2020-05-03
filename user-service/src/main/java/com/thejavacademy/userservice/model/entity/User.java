package com.thejavacademy.userservice.model.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;


import javax.persistence.*;
import java.util.Objects;

@Data
@Entity
@NoArgsConstructor
public class User {

    @Id
    private String id;
    private String username;
    @Column(name="lastname")
    private String lastName;
    @Column(name="firstname")
    private String firstName;
    private String profilePicture;
    private String email;
    private String phoneNumber;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return id.equals(user.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
