package com.thejavacademy.userservice.mapper;

import com.thejavacademy.userservice.model.UserElastic;
import com.thejavacademy.userservice.model.dto.UserContact;
import com.thejavacademy.userservice.model.dto.UserIdentity;
import com.thejavacademy.userservice.model.dto.UserResponse;
import com.thejavacademy.userservice.model.entity.User;
import com.thejavacademy.userservice.model.messages.UserEvent;

public class UserMapper {


    public static UserIdentity entityToDto(User user) {
        UserIdentity userIdentity = buildUserIdentity(user);
        return userIdentity;
    }

    public static UserEvent toUserEvent(User user) {
        UserEvent userEvent = new UserEvent();
        if (user != null) {
            userEvent.setId(user.getId());
            userEvent.setUsername(user.getUsername());
            userEvent.setEmail(user.getEmail());
            userEvent.setLastName(user.getLastName());
            userEvent.setPhoneNumber(user.getPhoneNumber());
            userEvent.setProfilePicture(user.getProfilePicture());
        }
        //return Optional.of(userEvent);
        return userEvent;
    }

    public static UserResponse userToUserResponse(User user) {
        UserResponse userResponse = new UserResponse();
        UserContact userContact = new UserContact();
        userContact.setPhoneNumber(user.getPhoneNumber());
        userContact.setEmail(user.getEmail());
        userResponse.setUserContact(userContact);
        userResponse.setUserIdentity(buildUserIdentity(user));
        userResponse.setProfilePicture(user.getProfilePicture());
        return userResponse;
    }

    public static UserIdentity buildUserIdentity(User user) {
        UserIdentity userIdentity = new UserIdentity();
        userIdentity.setUsername(user.getUsername());
        userIdentity.setProfilePicture(user.getProfilePicture());
        userIdentity.setLastName(user.getLastName());
        userIdentity.setFirstName(user.getFirstName());
        userIdentity.setEmail(user.getEmail());
        userIdentity.setId(user.getId());
        return userIdentity;
    }

    public static UserElastic toUserElastic(User user) {
        return UserElastic.builder()
                .id(user.getId())
                .username(user.getUsername())
                .email(user.getEmail())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .build();
    }

    public static UserIdentity toUserIdentityDto(UserElastic userElastic) {
        return UserIdentity.builder()
                .id(userElastic.getId())
                .username(userElastic.getUsername())
                .firstName(userElastic.getFirstName())
                .lastName(userElastic.getLastName())
                .profilePicture(userElastic.getProfilePicture())
                .build();
    }
}
