package com.thejavacademy.userservice.model;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Builder
public class UserElastic {


    private String id;
    private String username;
    private String email;
    private String firstName;
    private String lastName;
    private String profilePicture;

}
