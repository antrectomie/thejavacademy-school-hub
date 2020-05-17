package com.thejavacademy.userservice.mapper;

import com.thejavacademy.userservice.model.dto.ActionType;
import com.thejavacademy.userservice.model.dto.FriendshipRequest;
import com.thejavacademy.userservice.model.entity.Friendship;
import com.thejavacademy.userservice.model.messages.FriendshipEvent;

import java.util.UUID;

public class FriendshipRequestMapper {


    public static Friendship dtoToEntity(FriendshipRequest friendshipRequest) {
        Friendship friendship = new Friendship(friendshipRequest.getUserOneId(), friendshipRequest.getUserTwoId());
        friendship.setRelationshipStatus(friendshipRequest.getActionType());
        friendship.setActionUserId(friendshipRequest.getTriggeredBy());
        if (friendshipRequest.getId() == null && friendshipRequest.getActionType().equals(ActionType.REQUESTED)) {
            friendship.setId(UUID.randomUUID().toString());
        } else {
            friendship.setId(friendshipRequest.getId());
        }
        return friendship;
    }

    public static FriendshipEvent requestToEvent(FriendshipRequest friendshipRequest) {
        FriendshipEvent friendshipEvent = new FriendshipEvent();
        friendshipEvent.setFrom(friendshipRequest.getTriggeredBy());
        friendshipEvent.setTo(friendshipRequest.to());
        friendshipEvent.setActionType(mapActionType(friendshipRequest.getActionType()));
        return friendshipEvent;
    }

    private static com.thejavacademy.userservice.model.messages.ActionType mapActionType(ActionType actionType) {
        com.thejavacademy.userservice.model.messages.ActionType actionType1 = null;
        switch (actionType) {
            case DELETE:
                actionType1 = com.thejavacademy.userservice.model.messages.ActionType.DELETED;
                break;
            case ACCEPT:
                actionType1 = com.thejavacademy.userservice.model.messages.ActionType.ACCEPTED;
                break;
            case REQUESTED:
                actionType1 = com.thejavacademy.userservice.model.messages.ActionType.REQUESTED;
                break;
            case DENIED:
                actionType1 = com.thejavacademy.userservice.model.messages.ActionType.DENIED;
                break;
        }
        return actionType1;
    }

}
