package com.thejavacademy.userservice.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserIdentity {

    @JsonProperty(required = true)
    private String id;
    @JsonProperty(required = true)
    private String username;
    @JsonProperty(required = true)
    private String lastName;
    private String firstName;
    private String email;
    private String profilePicture;

}
