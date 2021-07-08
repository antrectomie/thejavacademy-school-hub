package com.thejavacademy.userservice.model.entity;

import com.thejavacademy.userservice.model.dto.ActionType;
import com.thejavacademy.userservice.util.FriendshipType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Friendship {

    @Id
    private String id;
    private String userOneId;
    private String userTwoId;
    private ActionType relationshipStatus;
    private String actionUserId;

    public String getFriendId(String id){
        return id.equals(this.userOneId) ? userTwoId : userOneId;
    }

    public void setFriends(String userOneId, String userTwoId){
        if(userOneId.compareTo(userTwoId) < 0){
            this.userOneId = userOneId;
            this.userTwoId = userTwoId;
        }else{
            this.userOneId = userTwoId;
            this.userTwoId = userOneId;
        }
    }
}



