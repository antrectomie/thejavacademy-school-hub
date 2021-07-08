package com.thejavacademy.userservice.service;

import com.thejavacademy.userservice.mapper.FriendshipRequestMapper;
import com.thejavacademy.userservice.model.dto.FriendshipRequest;
import com.thejavacademy.userservice.model.entity.Friendship;
import com.thejavacademy.userservice.service.adapters.FriendshipStorageAdapter;
import org.springframework.stereotype.Service;

@Service
public class FriendshipService {

    private FriendshipStorageAdapter friendshipStorageAdapter;
    private KafkaFriendshipProducer kafkaFriendshipProducer;

    public FriendshipService(FriendshipStorageAdapter friendshipStorageAdapter, KafkaFriendshipProducer kafkaFriendshipProducer) {
        this.friendshipStorageAdapter = friendshipStorageAdapter;
        this.kafkaFriendshipProducer = kafkaFriendshipProducer;
    }


    public Friendship getFriendship(String userOneId, String userTwoId) {
        return friendshipStorageAdapter.getFriendship(userOneId, userTwoId);
    }

    public void updateFriendship(FriendshipRequest friendshipRequest) {
        friendshipStorageAdapter.updateFriendship(friendshipRequest);
        kafkaFriendshipProducer.sendFriendshipEvent(FriendshipRequestMapper.requestToEvent(friendshipRequest));
    }

}
