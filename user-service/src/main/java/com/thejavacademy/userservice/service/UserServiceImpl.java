package com.thejavacademy.userservice.service;

import com.thejavacademy.userservice.exception.UserServiceException;
import com.thejavacademy.userservice.model.dto.SearchUserResponse;
import com.thejavacademy.userservice.model.dto.UserResponse;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import static com.thejavacademy.userservice.exception.UserServiceException.ExceptionType.EMTPY_USER_ID;
import static com.thejavacademy.userservice.exception.UserServiceException.ExceptionType.USER_NOT_FOUND;

@Service
public class UserServiceImpl implements UserService {


    private UserStorageManager userStorageManager;

    public UserServiceImpl(UserStorageManager userStorageManager) {
        this.userStorageManager = userStorageManager;
    }

    @Override
    public SearchUserResponse searchUser(String term, int page, int limit) {
        PageRequest pageRequest = PageRequest.of(page, limit);
        return userStorageManager.searchUser(term, pageRequest);
    }

    @Override
    public SearchUserResponse getFriends(String id) {
        if (id == null || id.isBlank()) {
            throw new UserServiceException(EMTPY_USER_ID);
        }
        userStorageManager.getUserById(id).orElseThrow(() -> new UserServiceException(USER_NOT_FOUND));
        try {
            return userStorageManager.getUserFriends(id);
        } catch (RuntimeException ex) {
            throw new UserServiceException(UserServiceException.ExceptionType.SERVER_ERROR);
        }

    }

    @Override
    public UserResponse getUser(String id) {
        return null;
    }
}
