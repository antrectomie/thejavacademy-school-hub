package com.thejavacademy.userservice.service.adapters;

import com.thejavacademy.userservice.exception.UserServiceException;
import com.thejavacademy.userservice.model.dto.FriendshipRequest;
import com.thejavacademy.userservice.model.entity.Friendship;

import java.util.List;

public interface FriendshipStorageAdapter{


    List<Friendship> getFriendships(String id);
    Friendship getFriendship(String userOneId, String userTwoId);
    Friendship create(Friendship friendship);
    void delete(Friendship friendship) throws UserServiceException;
    void updateFriendship(FriendshipRequest friendshipRequest);
}
