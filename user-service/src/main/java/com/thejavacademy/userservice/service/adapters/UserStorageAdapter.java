package com.thejavacademy.userservice.service.adapters;

import com.thejavacademy.userservice.model.dto.UserIdentity;
import com.thejavacademy.userservice.model.entity.User;

import java.util.List;
import java.util.Optional;

public interface UserStorageAdapter {

    List<UserIdentity> getUserFriends(String idOne);

    Optional<User> getUserById(String id);

    User save(User user);

    void deleteUser(String id);

    List<UserIdentity> getUsers();
}
